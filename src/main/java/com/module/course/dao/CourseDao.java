package com.module.course.dao;

import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.course.entity.Course;

/**
 * @Description  课程Dao接口
 * @Author  yuanzhonglin
 * @Date  2016年7月12日 
 */

@MyBatisDao
public interface CourseDao extends CrudDao<Course>{

	public List<Course> queryTeacherCourse(Course course);
	public List<Course> fetchCourseById(String courseId);
	public int updateCourseCompleteStatus(Course course);
	public int updateCourse(Course course);
	public int deleteCourseById(String courseId);
	public int insertCourse(Course course);
	@SuppressWarnings("rawtypes")
	public List checkIsExist();
	public List<Course> queryCommonCourse(Course course);//查询本校课程
	public List<Course> queryCourseByUserId(Course course);
}
