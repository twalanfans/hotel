package com.module.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.service.BaseService;
import com.common.utils.SpringContextHolder;
import com.module.sys.dao.DictionDao;
import com.module.sys.dao.MenuDao;
import com.module.sys.entity.Menu;

/**
 * 菜单资源管理服务
 * @author yuanzhonglin
 * @date 2016-7-6
 */
@Service
@Transactional(readOnly = true)
public class MenuManageService extends BaseService{
	
	//  注入MenuDao 
	@Autowired
	private MenuDao menuDao;
	
	
	/**
	 * 管理员总菜单资源展示
	 * @date 2016-7-6
	 */
	@Transactional(readOnly=false)
	public static List<Menu> showAllMenu(){
		MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
		List<Menu> menuList =null;
		try {
			menuList= menuDao.findAllList(new Menu());
		} catch (Exception e) {
			e.getMessage();
		}
		return menuList;
	}

	/**
	 * 菜单增加及分配路径资源
	 * @param menu
	 */
	@Transactional(readOnly=false)
	public void insertMenu(Menu menu){
		menuDao.insert(menu); 
	}
	
	/**
	 * 菜单删除
	 * @author yuanzhonglin
	 * @date 2016-7-6
	 */
	@Transactional(readOnly=false)
	public void deleteMenu(Menu menu){
		menuDao.delete(menu); 
	}
	/**
	 * 菜单配置参数修改
	 * @author yuanzhonglin
	 * @date 2016-7-6
	 */
	@Transactional(readOnly=false)
	public void updateMenu(Menu menu){
		menuDao.update(menu); 
	}

}
