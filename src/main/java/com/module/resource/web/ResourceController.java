package com.module.resource.web;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.utils.DateUtils;
import com.common.utils.SpringContextHolder;
import com.common.web.BaseController;
import com.module.resource.entity.Resource;
import com.module.resource.service.ResourceService;
import com.module.sys.service.LogService;
import com.module.sys.service.RoleManageService;
import com.module.sys.utils.UserUtils;

/**
 * 资源管理Controller
 * @author yuanzhonglin
 * @date 2016-8-5
 */
@Controller
public class ResourceController extends BaseController{

private static LogService log = SpringContextHolder.getBean(LogService.class);
	
	public static final String LOG_INSERT = "1"; //日志类型-增加
	public static final String LOG_DELETE = "2"; //日志类型-删除
	public static final String LOG_UPDATE = "3"; //日志类型-修改
	@Autowired
	private  ResourceService resourceService;
	
	@Autowired
	private RoleManageService roleManageService;
	
	/**
	 * 保存资源到制定角色
	 * yuanzhonglin
	 * 2016-8-5
	 */
	@RequestMapping(value="role/insertResourceToRole")
	public String insertResourceToRole(HttpServletRequest request, HttpServletResponse response){
		String roleId =request.getParameter("roleId")==null?"":request.getParameter("roleId");
		String resourceId =request.getParameter("resourceId")==null?"":request.getParameter("resourceId");
		String idList[] = roleId.split(",");
		try {
			Resource resource = new Resource();
			for(int i=0; i<idList.length; i++){
				resource.setResourceId(resourceId);
				resource.setRoleId(idList[i]);
				resourceService.insertResourceToRole(resource);
			}
			return renderString(response,"success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response,"error");
		}
	}
	
	@RequestMapping(value="resource/queryNewGroup")
	public String queryNewGroup(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		String resourceId = request.getParameter("resourceId")==null?"":request.getParameter("resourceId");
		ResourceService resourceService = new ResourceService();
		try {
			Resource resource = new Resource();
				resource.setCreateUser(UserUtils.getUser().toString());
				resource.setResourceId(resourceId);
			List<Resource> list = resourceService.queryNewGroup(resource);
			return renderString(response,list);
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response,"error");
		}
	}
	
	@RequestMapping(value="resource/inertCourseResource")
	public String inertCourseResource(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		String courseId = request.getParameter("courseId")==null?"":request.getParameter("courseId");
		ResourceService resourceService = new ResourceService();
		try {
			Resource resource = new Resource();
				resource.setCreateUser(UserUtils.getUser().toString());
				resource.setRelationId(courseId);
				resource.setCreateTime(Timestamp.valueOf(DateUtils.getDateTime()));
				resource.setResourceType("1");
			resourceService.saveNewResource(resource);
			return renderString(response,resource.getResourceId());
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response,"error");
		}
	}
	
	
}
