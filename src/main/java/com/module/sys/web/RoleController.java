package com.module.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.utils.DateUtils;
import com.common.web.BaseController;
import com.module.course.entity.Course;
import com.module.course.service.CourseManageService;
import com.module.course.util.CourseUtils;
import com.module.resource.entity.Resource;
import com.module.resource.service.ResourceService;
import com.module.sys.entity.Role;
import com.module.sys.service.RoleManageService;
import com.module.sys.utils.UserUtils;

/**
 * @Description  角色controller
 * @Author  yuanzhonglin
 * @Date  2016年7月8日 
 */

@Controller
public class RoleController extends BaseController{
	
	@Autowired
	private RoleManageService roleManageService;
	
	/**
	 * 课程已关联群组查询
	 * 2016-8-4
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="role/haveRelationGroup")
	public String haveRelationGroup(HttpServletRequest request)  throws Exception{
		String courseId = request.getParameter("courseId")==null?"":request.getParameter("courseId").toString();
		ResourceService resourceService = new ResourceService();
		List<Resource> list = resourceService.haveRelationGroup(courseId);		
		List reList = resourceService.courseResource(courseId);
		int num = reList.size();		//是否加入课程资源
		String resourceId="";
		if(num>0){
			resourceId = reList.get(0).toString();
		}
		List<Course> courseList = CourseUtils.queryTeacherCourse(new Course(UserUtils.getUser().toString()));
		request.setAttribute("courseId", courseId);
		request.setAttribute("resourceId", resourceId);
		request.setAttribute("groupList", list);
		request.setAttribute("courseList", courseList);
		return "modules/sys/teacher/owncenter/course_relation_resource";
	}
	
	/**
	 * 角色增加
	 * 2016-8-5
	 */
	@RequestMapping(value="role/insertRole")
	public String insertRole(HttpServletRequest request,HttpServletResponse response){
		String user = UserUtils.getUser().toString();
		String name = request.getParameter("name");
//		String remark = request.getParameter("remark");
		Role role = new Role();
			role.setName(name);
			role.setCreateUser(user);
			role.setCreateTime(DateUtils.getDateTime());
		try {
			roleManageService.insertRole(role);
			return renderString(response, role.getRoleId());
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	/**
	 * 角色修改
	 * 2016-8-8
	 */
	@RequestMapping(value="role/updateRole")
	public String updateRole(HttpServletRequest request,HttpServletResponse response){
		String roleId = request.getParameter("roleId");
		String name = request.getParameter("name")==null?"":request.getParameter("name");
		String remark = request.getParameter("remark")==null?"":request.getParameter("remark");
		Role role = new Role();
		role.setRoleId(roleId);
		role.setName(name);
		role.setRemark(remark);
		try {
			roleManageService.updateRole(role);
			return renderString(response,"success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response,"error");
		}
	}
	
	/**
	 * 删除角色
	 * 2016-8-4
	 */
	@RequestMapping(value="role/deleteRole")
	public String deleteRole(HttpServletRequest request,HttpServletResponse response){
		String roleId = request.getParameter("roleId");
		String idList[]=roleId.split(",");
		Role role = new Role();
		try {
			for(int i=0; i<idList.length; i++){
				role.setRoleId(idList[i]);
				roleManageService.deleteRole(role);
			}
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	@RequestMapping(value="role/removeStudent")
	public String removeStudent(HttpServletRequest request,HttpServletResponse response){
		String roleId = request.getParameter("roleId");
		String userId = request.getParameter("userId");
		String idList[]=userId.split(",");
		Role role = new Role();
			role.setRoleId(roleId);
		try {
			for(int i=0; i<idList.length; i++){
				role.setUserId(idList[i]);
				roleManageService.removeStudent(role);
			}
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	/**
	 * 角色匹配资源删除
	 * 2016-8-8
	 */
	@RequestMapping(value="role/deleteRoleResource")
	public String deleteRoleResource(HttpServletRequest request,HttpServletResponse response){
		String resourceId = request.getParameter("resourceId");
		String roleId = request.getParameter("roleId");
		String idList[]=roleId.split(",");
		Role role = new Role();
		try {
			for(int i=0; i<idList.length; i++){
				role.setRoleId(idList[i]);
				role.setResourceId(resourceId);
			roleManageService.deleteRoleResource(role);
			}
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
}
