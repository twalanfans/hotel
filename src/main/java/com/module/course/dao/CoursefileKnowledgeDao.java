package com.module.course.dao;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.course.entity.CoursefileKnowledge;


@MyBatisDao
public interface CoursefileKnowledgeDao  extends CrudDao<CoursefileKnowledge>{
	
	public int addFileClass(CoursefileKnowledge file);
	
	public int updateClassFileName(CoursefileKnowledge file);
	
}
