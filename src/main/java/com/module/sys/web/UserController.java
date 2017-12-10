package com.module.sys.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.models.Page;
import com.common.utils.DateUtils;
import com.common.utils.IdGen;
import com.common.utils.SpringContextHolder;
import com.common.web.BaseController;
import com.github.pagehelper.PageInfo;
import com.module.course.entity.CourseFile;
import com.module.owncenter.service.CheckerManageService;
import com.module.sys.entity.Department;
import com.module.sys.entity.Role;
import com.module.sys.entity.User;
import com.module.sys.entity.UserDetail;
import com.module.sys.service.DeptManageService;
import com.module.sys.service.LogService;
import com.module.sys.service.RegisterService;
import com.module.sys.service.RoleManageService;
import com.module.sys.service.SystemService;
import com.module.sys.utils.UserUtils;

/**
 * 用户管理Controller
 * @author yuanzhonglin
 * @date 2016-8-8
 */
@Controller
public class UserController extends BaseController{
	
private static LogService log = SpringContextHolder.getBean(LogService.class);
	
	@Autowired
	private RegisterService registerService;
	
	@Autowired
	private RoleManageService roleManageService;
	/**
	 * 初始化所有用户列表
	 * 2016-8-8
	 */
	@RequestMapping(value="${adminPath}/user/showAllUserPage")
	public String queryUserList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String userName = request.getParameter("userName")==null?"":request.getParameter("userName");
		//String userName = new String(na.getBytes("ISO8859-1"), "UTF-8");
		String loginName = request.getParameter("loginName")==null?"":request.getParameter("loginName");
		//String loginName = new String(ln.getBytes("ISO8859-1"), "UTF-8");
		String userType = request.getParameter("userType")==null?"":request.getParameter("userType");
		UserDetail userDetail = new UserDetail();
			userDetail.setUserName(userName);
			userDetail.setLoginName(loginName);
			userDetail.setUserType(userType);
		UserUtils userUtils = new UserUtils();
		List<UserDetail> users = userUtils.queryAllUserList(userDetail);
		request.setAttribute("pageInfo", new PageInfo<UserDetail>(users));
		request.setAttribute("userName", userName);
		request.setAttribute("loginName", loginName);
		request.setAttribute("userType", userType);
		return "modules/sys/admin/user_manage_query";
	}
	
	/**
	 * 用户详情
	 * 2016-8-8
	 */
	@RequestMapping(value="user/queryUserDetail")
	public String queryUserDetail(HttpServletRequest request){
		String userId = request.getParameter("userId");
		UserDetail user = UserUtils.getUserDetail(userId);
		request.setAttribute("userDetail", user);
		return "modules/sys/admin/user_detail_query";
	}
	
	/**
	 * 个人中心信息修改
	 * 2016-8-15
	 */
	@RequestMapping(value="${adminPath}/ownCenter/editUserDetail")
	public String editUserDetail(HttpServletRequest request, Model model){
		String userId = UserUtils.getUser().toString();
		UserDetail user = UserUtils.getUserDetail(userId);
		model.addAttribute("user", user);
		request.setAttribute("userDetail", user);
		return "modules/sys/common/edit_personal_information";
	}
	
	/**
	 * 新建用户页初始化
	 * 2016-8-8
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="${adminPath}/user/createNewUser")
	public String createNewUser(HttpServletRequest request){
		DeptManageService dept = new DeptManageService();
		List<Department> departList = dept.showAllDepart();
		request.setAttribute("departList", departList);
		return "modules/sys/admin/admin_create_user";
	}
	
	/**
	 * 删除用户
	 * 2016-8-9
	 */
	@RequestMapping(value="user/deleteUser")
	public String deleteUser(HttpServletRequest request,HttpServletResponse response){
		String userId = request.getParameter("userId");
		String idList[]=userId.split(",");
		User user = new User();
		UserUtils userUtils = new UserUtils();
		try {
			for(int i=0; i<idList.length; i++){
				user.setUserId(idList[i]);
				userUtils.deleteUser(user);
			}
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	/**
	 * 管理员待办页
	 */
	@RequestMapping(value="${adminPath}/user/adminDefaultIndex")
	public String adminDefaultIndex(HttpServletRequest request){
		UserUtils user = new UserUtils();
		UserDetail userDetail = new UserDetail();
			userDetail.setStatus("3");
		List<UserDetail> users = user.queryAllUserList(userDetail);
		CheckerManageService service = new CheckerManageService();
		List<CourseFile> fileList = service.queryCheckFile(new CourseFile());
		UserDetail detail = UserUtils.getUserDetail(UserUtils.getUser().toString());
		request.setAttribute("pageInfo1",new PageInfo<UserDetail>(users));
		request.setAttribute("pageInfo",new PageInfo<CourseFile>(fileList));
		request.setAttribute("userDetail",detail);
		return "modules/sys/admin/admin_default_index";
	}
	
	/**
	 * 用户授予角色
	 * 2016-8-9
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="user/addUserToRole")
	public String addUserToRole(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("userId");
		String roleId = request.getParameter("roleId");
		String idList[]=userId.split(",");
		Map<String,Object> map = new HashMap<String,Object>();
		Role role = new Role();
			role.setRoleId(roleId);
		try {
			for(int i=0; i<idList.length; i++){
				role.setUserId(idList[i]);
				roleManageService.addUserToRole(role);
			}
			map.put("status", "success");
			return renderString(response, map);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "error");
			return renderString(response, map);
		}
	}
	
	/**
	 * 教师页数据分析初始化
	 */
	@RequestMapping(value="${adminPath}/user/dataAnalysis")
	public String dataAnalysis(HttpServletRequest request){
		return "modules/sys/teacher/commresource/look_student_dataAnalysis";
	}
	
	@RequestMapping(value="/user/queryDataAnalysis")
	public String queryDataAnalysis(HttpServletRequest request)throws Exception{
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		//content = new String(content.getBytes("ISO8859-1"), "UTF-8");
		if(!content.equals("")){
			String text = content.substring(0, 1);
			UserDetail ud = new UserDetail();
			if(text.equals("1")){			//手机号查询
				ud.setLoginName(content);
			}else{					//用户名查询
				ud.setUserName(content);
			}
			char[] cont = content.toCharArray();		//将字符拆成char集合
			List<UserDetail> list = UserUtils.queryUser(ud);
			List<UserDetail> userList = new ArrayList<UserDetail>();
			if(list.size()>0){
				for(int i=0; i<list.size(); i++){
					UserDetail user = list.get(i);
					String textDeal = "";			//待处理字符串
					if(text.equals("1")){
						String loginName = "";
						textDeal = user.getLoginName();
						char[] qcont = textDeal.toCharArray();
						int num = 0;
						for(int f=0; f<qcont.length; f++){
							if(num<cont.length){
								if(cont[num]==qcont[f]){
									loginName += "<font color=\"red\">"+qcont[f]+"</font>";
									num = num+1;
								}else{
									loginName += qcont[f];
									num = num+1;
								}
							}else{
								loginName += qcont[f];
							}
						}
						user.setRemark(user.getUserName());
						user.setLoginName(loginName);
						userList.add(user);
					}else{
						String userName = "";
						textDeal = user.getUserName();
						char[] qcont = textDeal.toCharArray();
						int num = 0;
						for(int f=0; f<qcont.length; f++){
							if(num<cont.length){
								if(cont[num]==qcont[f]){
									userName += "<font color=\"red\">"+qcont[f]+"</font>";
									num = num+1;
								}else{
									userName += qcont[f];
									num = num+1;
								}
							}else{
								userName += qcont[f];
							}
						}
						user.setRemark(textDeal);
						user.setUserName(userName);
						userList.add(user);
					}
				}
			}
			request.setAttribute("content", content);
			request.setAttribute("userList", userList);
			request.setAttribute("resultNum", userList.size());
		}
		return "modules/sys/teacher/commresource/query_user_result";
	}
	
	
	
	/**
	 * 新建用户保存
	 * 2016-8-8
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value="user/saveUserDetail")
	public String saveUserDetail(HttpServletRequest request, HttpServletResponse response){
		String userName = request.getParameter("userName")==null?"":request.getParameter("userName");
		String loginName = request.getParameter("loginName")==null?"":request.getParameter("loginName");
		String email = request.getParameter("email")==null?"":request.getParameter("email");
		String password = request.getParameter("password")==null?"":request.getParameter("password");
		String departId = request.getParameter("departId")==null?"":request.getParameter("departId");
		String userType = request.getParameter("userType")==null?"":request.getParameter("userType");
		String sex = request.getParameter("sex")==null?"":request.getParameter("sex");
		String birthday = request.getParameter("birthday")==null?"":request.getParameter("birthday");
		String address = request.getParameter("address")==null?"":request.getParameter("address");
		String postCode = request.getParameter("postCode")==null?"":request.getParameter("postCode");
		UserDetail userDetail  = new UserDetail();
		
			String uuid = IdGen.uuid();
			userDetail.setUserId(uuid);
			userDetail.setUserName(userName);  //真实姓名
			userDetail.setEmail(email);		//邮箱
			userDetail.setUserType(userType);
			userDetail.setSex(sex); //性别
			userDetail.setBirthday(birthday); //出生日期
			userDetail.setAddress(address);  //现居地址
			userDetail.setPostCode(postCode);  //邮政编码
			userDetail.setPhone(loginName);			//手机号
		User user = new User();
			user.setUserId(uuid);
			user.setLoginName(loginName);
			user.setPassword(new SystemService().entryptPassword(password));
			user.setDepartId(departId);
			user.setUserType(userType);
			user.setCreateTime(Timestamp.valueOf(DateUtils.getDateTime()));
			try {
				registerService.saveUserByAdmin(user,userDetail);
				return renderString(response, "success");
			} catch (Exception e) {
				e.printStackTrace();
				return renderString(response, "error");
			}
	}
}
