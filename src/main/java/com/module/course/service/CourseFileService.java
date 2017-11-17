package com.module.course.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.noo.pagination.page.PageContext;
import org.noo.pagination.page.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.utils.SpringContextHolder;
import com.github.pagehelper.PageHelper;
import com.module.course.dao.CourseFileDao;
import com.module.course.entity.CourseFile;

/**
 * @Description  课程管理服务Service
 * @Author  yuanzhonglin
 * @Date  2016年7月12日 
 */
@Service
@Transactional(readOnly=true)
public class CourseFileService {
		
	@Autowired
	private CourseFileDao courseFileDao;
	/**
	 * 视频播放详情
	 * yuanzhonglin
	 * 2016-9-1
	 */
	public List<CourseFile> courseVideoDetail(CourseFile cf){
		CourseFileDao courseFileDao = SpringContextHolder.getBean(CourseFileDao.class);
		List<CourseFile> list =courseFileDao.getCourseFileById(cf);
		return list;
	}
	
	/*
	 * 学生课程学习历史观看
	 * yuanzhonglin
	 * 2016-9-1
	 */
	public List<CourseFile> historyVideoList(String userId){
		CourseFileDao courseFileDao = SpringContextHolder.getBean(CourseFileDao.class);
		List<CourseFile> list =courseFileDao.historyVideoList(userId);
		return list;
	}
	
	/**
	 * 更新视频文件时长
	 * yuanzhonglin
	 * 2016-8-31
	 */
	@Transactional(readOnly=false)
	public static void updateFileTimeLong(CourseFile courseFile){
		CourseFileDao courseFileDao = SpringContextHolder.getBean(CourseFileDao.class);
		courseFileDao.updateFileTimeLong(courseFile);
	}
	
	@Transactional(readOnly=false)
	public static int saveCourseFile(CourseFile coursefile){
		CourseFileDao courseFileDao = SpringContextHolder.getBean(CourseFileDao.class);
		int ret =0;
		try {
			ret = courseFileDao.insertCourseFile(coursefile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	@Transactional(readOnly=false)
	public void updateCourseFile(CourseFile coursefile){
		CourseFileDao courseFileDao = SpringContextHolder.getBean(CourseFileDao.class);
		courseFileDao.updateCourseFileByFileId(coursefile);
	}
	@Transactional(readOnly=false)
	public void deleteCourseFileById(String courseFileId){
		CourseFileDao courseFileDao = SpringContextHolder.getBean(CourseFileDao.class);
		courseFileDao.deleteCourseFileById(courseFileId);
	}
	
	@Transactional(readOnly=false)
	public void deleteCourseFileByCourseId(String courseId){
		CourseFileDao courseFileDao = SpringContextHolder.getBean(CourseFileDao.class);
		courseFileDao.deleteCourseFileByCourseId(courseId);
	}
	
	/**
	 * 课程下的文件查询
	 * yuanzhonglin	
	 * 2016-8-30
	 */
	@Transactional(readOnly=true)
	public List<CourseFile> courseFileListByCourseId(CourseFile courseFile){
		Pagination pager = PageContext.getPageContext();  //分页
		int pageNo = pager.getCurrentPage();
	    int pageSize = pager.getPageSize();
	    PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
	    List<CourseFile> cfList  = getCourseFileByCourseId(courseFile);
	    return cfList;
	}
	
	@Transactional(readOnly=true)
	public List<CourseFile> getCourseFileByCourseId(CourseFile courseFile){
		CourseFileDao courseFileDao = SpringContextHolder.getBean(CourseFileDao.class);
		List<CourseFile> cfList = courseFileDao.getCourseFileByCourseId(courseFile);
		return cfList;
	}
	
	public List<CourseFile> showAllCourseFile(CourseFile courseFile) {
			CourseFileDao courseFileDao = SpringContextHolder.getBean(CourseFileDao.class);
			Pagination pager = PageContext.getPageContext();  //分页
			int pageNo = pager.getCurrentPage();
		    int pageSize = pager.getPageSize();
			PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
			List<CourseFile> list = courseFileDao.findAllList(courseFile);
		return list;
	}
	
	@Transactional(readOnly=true)
	public List<CourseFile> fetchCourseFileById(CourseFile cf){
		List<CourseFile> cfList = courseFileDao.getCourseFileById(cf);
		return cfList;
	}

}
