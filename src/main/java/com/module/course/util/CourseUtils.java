package com.module.course.util;
import java.util.List;

import com.common.utils.SpringContextHolder;
import com.module.course.dao.CourseDao;
import com.module.course.entity.Course;


public class CourseUtils {
	private static CourseDao courseDao = SpringContextHolder.getBean(CourseDao.class);
	
	public static List<Course> queryTeacherCourse(Course course){
		List<Course> list = courseDao.queryTeacherCourse(course);
		return list;
	}

	public static List<Course> fetchCourseByUserId(Course course){
		List<Course> list  = courseDao.queryTeacherCourse(course);
		return list;
	}
	
	public int updateCourseCompleteStatus(Course course){
		int ret = courseDao.updateCourseCompleteStatus(course);
		return ret;
	}

	public int updateCourse(Course course){
		int ret=courseDao.updateCourse(course);
		return ret;
	}
	
	public int deleteCourseById(String courseId){
		int ret=courseDao.deleteCourseById(courseId);
		return ret;
	}
	
	public int insertCourse(Course course){
		int ret=courseDao.insertCourse(course);
		return ret;
	}
}
