package com.module.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.utils.SpringContextHolder;
import com.module.sys.dao.DepartmentDao;
import com.module.sys.entity.Department;

/**
 * @Description  部门Service
 * @Author  yuanzhonglin
 * @Date  2016年7月6日 
 */
@Service
@Transactional(readOnly = true)
public class DeptManageService {

	@Autowired
	private DepartmentDao departmentDao;
	
	/**
	 * 部门列表List
	 * @return
	 */
	@Transactional(readOnly=false)
	public static List<Department> showAllDepart(){
		DepartmentDao departmentDao = SpringContextHolder.getBean(DepartmentDao.class);
		List<Department> departList =null;
		try {
			departList = departmentDao.findAllList(new Department());
		} catch (Exception e) {
			e.getMessage();
		}
		return departList;
	}
	/**
	 * 部门列表List
	 * @return
	 */
	@Transactional(readOnly=false)
	public static List<Department> queryDepartmentBySchoolId(String schoolId){
		DepartmentDao departmentDao = SpringContextHolder.getBean(DepartmentDao.class);
		List<Department> departList =null;
		try {
			departList = departmentDao.queryDepartment(schoolId);
		} catch (Exception e) {
			e.getMessage();
		}
		return departList;
	}

	/**
	 * 部门增加
	 * @param department
	 */
	@Transactional(readOnly=false)
	public static int insertDepart(Department department) {
		DepartmentDao departmentDao = SpringContextHolder.getBean(DepartmentDao.class);
		int ret =0;
		try {
			ret = departmentDao.insertAndReturn(department);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}


	/**
	 * 部门删除
	 * 2016-7-29
	 */
	@Transactional(readOnly=false)
	public void deleteDepart(Department department) {
		DepartmentDao departmentDao = SpringContextHolder.getBean(DepartmentDao.class);
		try {
			departmentDao.delete(department);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public Department getDepartNum(){
		DepartmentDao departmentDao = SpringContextHolder.getBean(DepartmentDao.class);
		Department depart = departmentDao.getDepartNum();
		return depart;
	}

	/**
	 * 修改参数
	 * @param department
	 */
	public void update(Department department) {
		DepartmentDao departmentDao = SpringContextHolder.getBean(DepartmentDao.class);
		try {
			departmentDao.update(department);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
