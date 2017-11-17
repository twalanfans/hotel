package com.module.owncenter.dao;

import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.course.entity.CourseFile;

@MyBatisDao
public interface CheckerDao  extends CrudDao<CourseFile>{
	
		public List<CourseFile> queryNeedDeal();
		
		public int changFileStatus(CourseFile courseFile);

}
