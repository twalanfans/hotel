package com.module.course.dao;

import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.course.entity.CourseNotes;

@MyBatisDao
public interface CourseNoteDao extends CrudDao<CourseNotes>{
	/**
	 * 保存课程笔记
	 * @date 2016-7-20
	 */
	public int saveCourseNotes(CourseNotes courseNotes);
	
	/**
	 * 查询当前用户的对应课程笔记
	 * @date 2016-7-20
	 */
	public List<CourseNotes> queryNotes(CourseNotes courseNotes);
	
}
