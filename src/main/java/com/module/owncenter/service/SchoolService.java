package com.module.owncenter.service;

import java.util.List;
import org.noo.pagination.page.PageContext;
import org.noo.pagination.page.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.common.utils.SpringContextHolder;
import com.github.pagehelper.PageHelper;
import com.module.owncenter.dao.SchoolDao;
import com.module.owncenter.entity.School;

@Service
@Transactional(readOnly = true)
public class SchoolService {
	
	public static List<School> querySchoolList(School s){
		Pagination pager = PageContext.getPageContext();  //分页
		int pageNo = pager.getCurrentPage();
	    int pageSize = pager.getPageSize();
		PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
		List<School> list = querySchool(s);
		return list;
	}
	public static List<School> querySchool(School s){
		SchoolDao sDao = SpringContextHolder.getBean(SchoolDao.class);
		List<School> userList = sDao.querySchoolList(s);
		return userList;
	}
	public static List<School> queryAllSchool(){
		SchoolDao sDao = SpringContextHolder.getBean(SchoolDao.class);
		List<School> userList = sDao.queryAllSchool();
		return userList;
	}
	
	public static int deleteSchool(String schoolId){
		SchoolDao sDao = SpringContextHolder.getBean(SchoolDao.class);
		int ret=0;
		try {
			ret = sDao.deleteById(schoolId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static int createSchool(School s){
		SchoolDao sDao = SpringContextHolder.getBean(SchoolDao.class);
		int ret=0;
		try {
			ret = sDao.createSchool(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
}
