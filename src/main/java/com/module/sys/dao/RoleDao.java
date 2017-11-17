/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/hotelsimusys/hotelsimusys">Hotelsimusys</a> All rights reserved.
 */
package com.module.sys.dao;

import java.util.List;
import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.sys.entity.Role;

/**
 * 角色DAO接口
 * @author hotelsimusys
 * @version 2016-08-04
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

	public Role getByName(Role role);
	
	public int deleteRoleResource(Role role);
	
	public int changeUserStatus(Role role);
	/**
	 * 角色权限对应信息维护(查询)
	 */
	public List<Role> showAllRoleMenu(Role role);
	
	public List<Role> isHaveBaseRole(Role role);
	
	public List<Role> queryAllRoleByUserId(String userId);
	
	public int addUserToRole(Role role);
	
	public int removeStudent(Role role);
	
}
