package com.module.sys.web;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.mapper.JsonMapper;
import com.common.utils.DateUtils;
import com.common.utils.IdGen;
import com.common.web.BaseController;
import com.module.owncenter.entity.School;
import com.module.owncenter.service.SchoolService;
import com.module.sys.entity.Department;
import com.module.sys.entity.User;
import com.module.sys.entity.UserDetail;
import com.module.sys.service.DeptManageService;
import com.module.sys.service.RegisterService;
import com.module.sys.service.SystemService;

/**
 * @Description  用户注册Controller类
 * @author  yuanzhonglin
 * @CreateDate  2016年6月22日
 */
@Controller
public class RegisterController extends BaseController{

	//  注入RegisterService
	@Autowired
	private RegisterService registerService;
	//  注入SystemService
	@Autowired
	private SystemService systemService;
	
	
	//  跳转到注册页面
	@SuppressWarnings("static-access")
	@RequestMapping(value="/register")
	public String toRegister(HttpServletRequest request){
		DeptManageService dept = new DeptManageService();
		SchoolService schoolService = new SchoolService();
		List<School> SchoolList = schoolService.queryAllSchool();
		for (School school : SchoolList) {
			System.out.println("school------"+school);
		}
		List<Department> departList = dept.showAllDepart();
		System.out.println("departList------"+departList);
		request.setAttribute("school", SchoolList);
		request.setAttribute("departList", departList);
		return "modules/sys/sysRegist2";
	}
	/**
	 * 学校部门联动查询
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="/departmentQuery",method=RequestMethod.POST)
	@ResponseBody
	public void SchoolToDepartment(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String schoolId = request.getParameter("schoolId");
		System.out.println("schoolId------"+schoolId);
		DeptManageService deptManageService = new DeptManageService();
		List<Department> departList = deptManageService.queryDepartmentBySchoolId(schoolId);
		System.out.println("department------"+departList);
		//request.setAttribute("departList", departList);
		/*
		 * [
	        {
	            "departId": "53",
	            "departName": "清华园",
	            "parentId":"1",
	            "department": [
	                {
	                    "departId": "57",
	                    "departName": "清华园1",
	                    "parentId":"53",
	                    "department": [
	                        {
	                            "departId": "99",
	                            "departName": "清华园12",
	                            "parentId": "57"
	                        },
	                        {
	                            "departId": "98",
	                            "departName": "清华园13",
	                            "parentId": "57"
	                        }
	                    ]
	                },
	                {
	                    "departId": "58",
	                    "departName": "清华园2",
	                    "parentId":"53",
	                    "department": [
	                        {
	                            "departId": "97",
	                            "departName": "清华园22",
	                            "parentId": "58"
	                        },
	                        {
	                            "departId": "96",
	                            "departName": "清华园23",
	                            "parentId": "58"
	                        }
	                    ]
	                }
	            ]
	        }
		]
		*/
		
		List<Department> xi = new ArrayList<Department>();//系
		List<Department> zy = new ArrayList<Department>();//专业
		List<Department> bj = new ArrayList<Department>();//班级
		for (int i = 0; i < departList.size(); i++) {
			if(departList.get(i).getLevel().equals("1")){
				departList.get(i).setDepartment(zy);
				xi.add(departList.get(i));
				
			}
			if(departList.get(i).getLevel().equals("2")){
				departList.get(i).setDepartment(bj);
				zy.add(departList.get(i));
				
			}
			if(departList.get(i).getLevel().equals("3")){
				bj.add(departList.get(i));
			}
		}
		//转json
		String jsonString = JsonMapper.toJsonString(xi);
		System.out.println("jsonString------"+jsonString);
		//输出
		PrintWriter out = response.getWriter();
		out.print(jsonString);	
		
	}
	/**
	 * 注册校验手机是否已注册
	 * @author yuanzhonglin
	 * @date 2016-7-19
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/checkPhone")
	
	public String checkLoginName(HttpServletRequest request,HttpServletResponse response){
		String loginName = request.getParameter("loginName");
		String use = "";
		User user = new User();
		user.setLoginName(loginName);
		RegisterService registerService = new RegisterService();
		try {
			List list = registerService.checkPhone(user);
			if(list.size()!=0){  //查询条数不为0说明数据已存在
				use="noCan";  //不能使用该手机号
			}else{
				use="can";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return renderString(response, use);
	}
	/**
	 *  注册,验证注册信息,并跳转到登录页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/registeruser",method = RequestMethod.POST)
	public  String registerSuccess(HttpServletRequest request, HttpServletResponse response){
		String userType = request.getParameter("userType").toString();
		String schoolId = request.getParameter("schoolId");
		System.out.println("schoolId验证------"+schoolId);
		//  分别调用对象
		User user = this.translateRequestToUser(request);
		UserDetail userDetail = this.translateRequestToUserDetail(request);
		user.setCreateTime(Timestamp.valueOf(DateUtils.getDateTime()));
		//  使用UUID生成ID
		String uuid = IdGen.uuid();
		user.setUserId(uuid);
		userDetail.setUserId(uuid);
		//如果用户注册身份为学生可直接授权、教师得通过管理员审核
		if(userType.equals("1")){   //userType 1:学生  2：老师
			user.setStatus("1");		//可用
		}else{
			user.setStatus("2");		//待认证
		}
		try{
			registerService.saveUser(user, userDetail); 
		}catch(Exception e){
			e.printStackTrace();
			return renderString(response, "error");
		}
        return renderString(response, "success");
	}
	
	@SuppressWarnings("static-access")
	private User translateRequestToUser(HttpServletRequest request){
		//  创建user对象
		User user  = new User();
		user.setLoginName(request.getParameter("loginName"));  //登陆名
		user.setPassword(new SystemService().entryptPassword( request.getParameter("password")));  //生成密文密码
		user.setUserType(request.getParameter("userType")); //用户类型
		user.setDepartId(request.getParameter("departId"));//所在部门
		user.setSchoolId(request.getParameter("schoolId"));
		
		System.out.println("schoolId注册------"+request.getParameter("schoolId"));
		return user;
	}
	private UserDetail translateRequestToUserDetail(HttpServletRequest request){
		//  创建userDetail对象
		UserDetail userDetail  = new UserDetail();
		userDetail.setUserName(request.getParameter("userName"));  //真实姓名
		userDetail.setSex(request.getParameter("sex")); //性别
		userDetail.setEmail(request.getParameter("email"));		//邮箱
		userDetail.setPhone(request.getParameter("loginName"));			//手机号
		return userDetail;
	}	
	
	//教师认证
	@RequestMapping(value="/teacherPass")
	public String teacherPass(HttpServletRequest request,HttpServletResponse response){
		String userId = request.getParameter("userId");
		try {
			registerService.teacherPass(userId);
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "success");
		} 
	}

}
