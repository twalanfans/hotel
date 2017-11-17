package com.module.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.utils.DateUtils;
import com.common.utils.SpringContextHolder;
import com.common.web.BaseController;
import com.module.sys.entity.Department;
import com.module.sys.service.DeptManageService;
import com.module.sys.service.LogService;
import com.module.sys.utils.UserUtils;

/**
 * @Description  部门Controller
 * @Author  yuanzhonglin
 * @Date  2016年7月6日 
 */
@Controller
public class DeptController extends BaseController{
	
	private static LogService log = SpringContextHolder.getBean(LogService.class);
	
	public static final String LOG_INSERT = "1"; //日志类型-增加
	public static final String LOG_DELETE = "2"; //日志类型-删除
	public static final String LOG_UPDATE = "3"; //日志类型-修改
	
	@Autowired
	private DeptManageService deptManageService ;
		
	/**
	 * 初始化查询部门列表
	 * @author yuanzhonglin
	 * @date 2016-7-13
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	@RequestMapping(value="${adminPath}/depart/showAllDepart")
	public String showDepartList(HttpServletRequest request){
		DeptManageService deptManageService = new DeptManageService();
		List departList = deptManageService.showAllDepart();
		Department departNum = deptManageService.getDepartNum();
		request.setAttribute("departList", departList);
		request.setAttribute("Department", departNum);
		return "modules/sys/admin/department_query";
	}
	
	/**
	 * 管理员树形增加部门
	 * @author yuanzhonglin
	 * @date 2016-7-8
	 */
	@RequestMapping(value="/depart/insertDepart")
	public String insertDepart(HttpServletRequest request,HttpServletResponse response){
		String departId = request.getParameter("departId")==null?"":request.getParameter("departId");
		String departName = request.getParameter("departName")==null?"--":request.getParameter("departName");
		String level = request.getParameter("level")==null?"":request.getParameter("level");
		Department department = new Department();
			department.setCreateUser(UserUtils.getUser().toString());
			department.setCreateTime(DateUtils.getDateTime());
			department.setDepartName(departName);
			department.setParentId(departId);
			department.setLevel(level);
		//  生成新部门插入到表中
		try {
			int ret = DeptManageService.insertDepart(department);
			if(ret>0){
				String departIds = department.getDepartId();
				department.setDepartId(departIds);			
			}else{
				return renderString(response, "error");
			}
		}catch(Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
		return renderString(response,department);
	}


	/**
	 * 管理员部门删除
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="depart/deleteDepart")
	public String delete(HttpServletRequest request,HttpServletResponse response){
		log.insertLog("delete", LOG_DELETE, request);	//记录管理员操作日志
		String departId = request.getParameter("departIds")==null?"":request.getParameter("departIds");	
		try {
			String idList[] = departId.split(",");
			Department department = new Department();
			DeptManageService deptManageService = new DeptManageService();
			for(int i=0; i<idList.length; i++){
				department.setDepartId(idList[i]);
				deptManageService.deleteDepart(department);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
		return renderString(response, "success");
	}
	/**
	 * 管理员部门配置参数修改
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="depart/updateDepart")
	public String update(HttpServletRequest request,HttpServletResponse response){
		String departId = request.getParameter("departId");	
		String departName = request.getParameter("departName");	
		try {
			Department department = new Department();
			DeptManageService deptManageService = new DeptManageService();
			department.setUpdateTime(DateUtils.getDateTime()); 
			department.setUpdateBy(UserUtils.getUser());
			department.setDepartId(departId);
			department.setDepartName(departName);
			deptManageService.update(department);
			return renderString(response,departName);
		} catch (Exception e) {
			return renderString(response, "error");
		}
		
	}
}
