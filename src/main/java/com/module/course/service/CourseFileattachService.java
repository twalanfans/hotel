package com.module.course.service;

import java.util.List;

import org.noo.pagination.page.PageContext;
import org.noo.pagination.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.common.utils.SpringContextHolder;
import com.github.pagehelper.PageHelper;
import com.module.course.dao.CourseFileattachDao;
import com.module.course.entity.CourseFileattach;

@Service
@Transactional(readOnly=true)
public class CourseFileattachService{

	@Autowired
	private CourseFileattachDao courseFileattachDao;
	
	@Transactional(readOnly=false)
	public void saveCourseFile(CourseFileattach cfa){
		CourseFileattachDao courseFileattachDao = SpringContextHolder.getBean(CourseFileattachDao.class);
		courseFileattachDao.insert(cfa);
	}
	
	@Transactional(readOnly=true)
	public List<CourseFileattach> listFileattach(CourseFileattach coursefileattach){
		CourseFileattachDao courseFileattachDao = SpringContextHolder.getBean(CourseFileattachDao.class);
		Pagination pager = PageContext.getPageContext();  //分页
		int pageNo = pager.getCurrentPage();
	    int pageSize = pager.getPageSize();
	    PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
	    List<CourseFileattach> list = courseFileattachDao.selectByClassId(coursefileattach);
		return list;
	}
	
	@Transactional(readOnly=true)
	public CourseFileattach attachFileDetail(CourseFileattach coursefileattach){
		
		CourseFileattach courseFileattach = courseFileattachDao.attachFileDetail(coursefileattach);
		
		return courseFileattach;
	}
	
	@Transactional(readOnly=false)
	public void deleteByPrimaryKey(CourseFileattach coursefileattach){
		courseFileattachDao.deleteByPrimaryKey(coursefileattach);
	}
	@Transactional(readOnly=false)
	public void deleteByCourseFileId(CourseFileattach coursefileattach){
		courseFileattachDao.deleteByCourseFileId(coursefileattach);
	}
	
	@Transactional(readOnly=false)
	public void updateByPrimaryKeySelective(CourseFileattach coursefileattach){
		courseFileattachDao.updateByPrimaryKeySelective(coursefileattach);
	}

}
