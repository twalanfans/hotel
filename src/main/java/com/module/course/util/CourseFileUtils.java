package com.module.course.util;

import com.common.utils.SpringContextHolder;
import com.module.course.dao.CourseDao;
import com.module.course.dao.CourseFileDao;

public class CourseFileUtils {
	
	private static CourseFileDao courseFileDao = SpringContextHolder.getBean(CourseFileDao.class);
	
	public int deleteCourseFileByCourseId(int  courseId)
	{
		//	return courseFileDao.deleteCourseFileByCourseId(courseId);
		return 0;
	}
}
