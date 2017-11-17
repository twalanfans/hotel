package com.module.owncenter.service;

import java.util.ArrayList;
import java.util.List;

import org.noo.pagination.page.PageContext;
import org.noo.pagination.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.models.Page;
import com.common.utils.SpringContextHolder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.module.course.dao.CourseFileDao;
import com.module.course.entity.CourseFile;


/**
 * @Description  审核员管理服务
 * @Author  yuanzhonglin
 * @Date  2016年8月16日 
 */
@Service
@Transactional(readOnly = true)
public class CheckerManageService {
	
	@Autowired
	private CourseFileDao courseFileDao;
	
	/**
	 * 审核员首页默认待办显示
	 * yuanzhonglin
	 */
	public List<CourseFile> queryNeedToDeal(){
			List<CourseFile> course =new ArrayList<CourseFile>();
			try {
				course = courseFileDao.queryNeedDeal();
			} catch (Exception e) {
				e.getMessage();
			}
			return course;
	}
	
	/**
	 * 课程所有文件查询
	 * 2016-8-16
	 */
	@Transactional(readOnly=false)
	public List<CourseFile> queryCheckFile(CourseFile courseFile) {
		CourseFileDao checkerDao = SpringContextHolder.getBean(CourseFileDao.class);
			Pagination pager = PageContext.getPageContext();  //分页
			int pageNo = pager.getCurrentPage();
		    int pageSize = pager.getPageSize();
		    PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
		    List<CourseFile> list = checkerDao.findAllListChk(courseFile);
		  return list;
	}
	
	/**
	 * 批量审核文件-通过/未通过
	 * yuanzhonglin
	 * 2016-8-16
	 */
	@Transactional(readOnly=false)
	public void changFileStatus(CourseFile courseFile){
		CourseFileDao checkerDao = SpringContextHolder.getBean(CourseFileDao.class);
		checkerDao.changFileStatus(courseFile);
	}
	
	/**
	 * 下载获取文件信息
	 * 2016-8-17
	 */
	public List<CourseFile> getCourseFileDetail(CourseFile courseFile){
		CourseFileDao courseFileDao = SpringContextHolder.getBean(CourseFileDao.class);
		List<CourseFile> fileList = courseFileDao.getCourseFileById(courseFile);
		return fileList;
	}
	
}
