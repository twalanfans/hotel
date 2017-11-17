package com.module.course.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.utils.SpringContextHolder;
import com.module.course.dao.CourseStudylogDao;
import com.module.course.entity.CourseStudyLog;
import com.module.course.entity.DataAnalysis;
import com.module.owncenter.dao.DataAnalysisDao;

@Service
@Transactional(readOnly=true)
public class CourseStudylogService {
		
	/**
	 *	记录视频学习日志
	 *  yuanzhonglin
	 *  2016-8-31
	 */
	@Transactional(readOnly=false)
	public void saveCourseStudylog(CourseStudyLog cs){
		CourseStudylogDao csDao = SpringContextHolder.getBean(CourseStudylogDao.class);
		try {
			csDao.insertCourseStudylog(cs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 视频课程学习日志更新
	 * yuanzhonglin
	 * 2016-8-31
	 */
	@Transactional(readOnly=false)
	public void updateCourseStudylog(CourseStudyLog cs){
		CourseStudylogDao csDao = SpringContextHolder.getBean(CourseStudylogDao.class);
		csDao.updateCourseStudylog(cs);
	}
	
	/**
	 * 获取某个用在某个课程上的全部学习记录
	 */
	public static List<CourseStudyLog> fetchUserCourseStudylogByCourseId(CourseStudyLog courseStudyLog){
		CourseStudylogDao courseStudylogDao = SpringContextHolder.getBean(CourseStudylogDao.class);
		List<CourseStudyLog> csList = new ArrayList<CourseStudyLog>();
		csList = courseStudylogDao.fetchUserCourseStudylogByCourseId(courseStudyLog);
		return csList;
	}
	
	/**
	 * 获取视频播放历史LogId
	 */
	public String getLogId(CourseStudyLog courseStudyLog){
		CourseStudylogDao courseStudylogDao = SpringContextHolder.getBean(CourseStudylogDao.class);
		String logId = courseStudylogDao.getLogId(courseStudyLog);
		return logId;
	}
	
	/**
	 * 获取用户某一天的自测题数
	 */
	public static String queryTestQuestionNum(CourseStudyLog courseStudyLog){
		CourseStudylogDao courseStudylogDao = SpringContextHolder.getBean(CourseStudylogDao.class);
		String questionNum = courseStudylogDao.queryTestQuestionNum(courseStudyLog);
		return questionNum;
	}
	
	/**
	 * 获取用户某一天的在线提问数
	 */
	public static String queryAskQuestionNum(CourseStudyLog courseStudyLog){
		CourseStudylogDao courseStudylogDao = SpringContextHolder.getBean(CourseStudylogDao.class);
		String askNum = courseStudylogDao.queryAskQuestionNum(courseStudyLog);
		return askNum;
	}
	
	/**
	 * 获取从startTime-endTime的微课学习时长
	 */
	@SuppressWarnings("rawtypes")
	public static List userCourseStudyTime(CourseStudyLog courseStudyLog){
		CourseStudylogDao courseStudylogDao = SpringContextHolder.getBean(CourseStudylogDao.class);
		List csList = courseStudylogDao.queryUserCourseStudyTime(courseStudyLog);
		return csList;
	}
	
}
