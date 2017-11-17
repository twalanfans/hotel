package com.module.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.common.utils.SpringContextHolder;
import com.common.web.BaseController;
import com.module.sys.entity.Menu;
import com.module.sys.service.LogService;
import com.module.sys.service.MenuManageService;

/**
 * @Description  菜单管理控制
 * @author  yuanzhonglin
 * @CreateDate  2016年6月29日
 */
@Controller
public class MenuController extends BaseController {

	private static LogService log = SpringContextHolder.getBean(LogService.class);
	
	public static final String LOG_INSET = "1"; //日志类型-增加
	public static final String LOG_DELETE = "2"; //日志类型-删除
	public static final String LOG_UPDATE = "3"; //日志类型-修改
	@Autowired
	private MenuManageService menuManageService;
	
	/**
	 * 初始化查询菜单列表
	 * @author yuanzhonglin
	 * @date 2016-7-6
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	@RequestMapping(value="menu/showAllMenu")
	public String showMenuList(HttpServletRequest request){
			List menuList = menuManageService.showAllMenu();
			request.setAttribute("menuList", menuList);
			return "modules/sys/admin/menu_manage";
	}

	/**
	 * 管理员树形增加菜单
	 * @author yuanzhonglin
	 * @date 2016-7-6
	 */
	@RequiresRoles("admin") 
	@RequestMapping(value="menu/insertMenu")
	public String insertMenu(HttpServletRequest request,HttpServletResponse response){
		log.insertLog("update", LOG_INSET, request);	//记录管理员操作日志
		String menuName = request.getParameter("menuName");   //菜单名称
		Menu menu = new Menu();
		menu.preInsert();  //获取创建人和创建时间
		menu.setName(menuName);
		menu.setHref("");
		try {
			menuManageService.insertMenu(menu);   //生成新菜单插入表中
			return renderString(response, "success");
		} catch (Exception e) {
			return renderString(response, "error");
		}
	}	
	
	/**
	 * 管理员菜单删除
	 * @author yuanzhonglin
	 * @date 2016-7-6
	 */
	@RequiresRoles("admin") 
	@RequestMapping(value="menu/deleteMenu")
	public String delete(HttpServletRequest request,HttpServletResponse response){
			log.insertLog( "delete", LOG_DELETE, request);	//记录管理员操作日志
			String menuId = request.getParameter("menuId");
			Menu menu = new Menu(menuId);
			menuManageService.deleteMenu(menu);
			return renderString(response, "error");
	}
	/**
	 * 管理员菜单配置参数修改
	 * @author yuanzhonglin
	 * @param name 
	 * @date 2016-7-6
	 */
	@RequiresRoles("admin") 
	@RequestMapping(value="menu/updateMenu")
	public String updateMenu(HttpServletRequest request,HttpServletResponse response){
		log.insertLog("updateMenu", LOG_UPDATE, request);	//记录管理员操作日志
//		String menuId = request.getParameter("menuId");
//		String name = request.getParameter("name");
//		String href = request.getParameter("href");
//		String isShow = request.getParameter("isShow");
		String menuId = "45";
		String name= "名称";
		String href = "url";
		String isShow = "2";
		try {
			Menu menu = new Menu(menuId);
			menu.preUpdate(); //获取更新人跟当前时间
			menu.setName(name);
			menu.setHref(href);
			menu.setIsShow(isShow);
			menuManageService.updateMenu(menu);
			return renderString(response, "success");
		} catch (Exception e) {
			return renderString(response, "error");
		}
	}
	
}
