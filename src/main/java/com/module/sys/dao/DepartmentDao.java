/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/hotelsimusys/hotelsimusys">Hotelsimusys</a> All rights reserved.
 */
package com.module.sys.dao;

import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.sys.entity.Department;

/**
 * 机构DAO接口
 * @author hotelsimusys
 * @version 2016-05-16
 */ 
@MyBatisDao
public interface DepartmentDao extends CrudDao<Department> {
	
	/**
	 * 部门信息插入
	 * @author yuanzhonglin
	 * @date 2016-7-8
	 */
	public int insertAndReturn(Department department);
	
	public Department getDepartNum();
	
	/**
	 * 部门信息插入
	 * @author yuanzhonglin
	 * @date 2016-7-8
	 */
	public int delete(Department department);
	/**
	 * 部门信息修改
	 * @author yuanzhonglin
	 * @date 2016-7-8
	 */
	public int update(Department department);
}
