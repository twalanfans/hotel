package com.module.course.dao;

import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.course.entity.CourseStudyLog;
import com.module.course.entity.DataAnalysis;

@MyBatisDao
public interface CourseStudylogDao extends CrudDao<CourseStudyLog>{
	
	public int insertCourseStudylog(CourseStudyLog cs);
	
	public int updateCourseStudylog(CourseStudyLog cs);
	
	//获取某个用在某个课程上的全部学习记录
	public List<CourseStudyLog> fetchUserCourseStudylogByCourseId(CourseStudyLog courseStudyLog);
	
	public String queryTestQuestionNum(CourseStudyLog courseStudyLog);
	
	public String getLogId(CourseStudyLog courseStudyLog);
	
	public String queryAskQuestionNum(CourseStudyLog courseStudyLog);
	
	@SuppressWarnings("rawtypes")
	public List queryUserCourseStudyTime(CourseStudyLog courseStudyLog);
}
