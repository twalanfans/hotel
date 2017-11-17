package com.module.sys.web;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.common.config.Global;
import com.common.utils.DateUtils;
import com.common.utils.IdGen;
import com.common.web.BaseController;
import com.module.sys.entity.Department;
import com.module.sys.entity.Role;
import com.module.sys.entity.User;
import com.module.sys.entity.UserDetail;
import com.module.sys.service.DeptManageService;
import com.module.sys.service.MailCheckService;
import com.module.sys.service.RegisterService;
import com.module.sys.service.RoleManageService;
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
		List<Department> departList = dept.showAllDepart();
		request.setAttribute("departList", departList);
		return "modules/sys/sysRegist";
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
