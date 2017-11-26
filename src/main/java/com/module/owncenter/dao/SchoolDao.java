package com.module.owncenter.dao;

import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.owncenter.entity.School;

@MyBatisDao
public interface SchoolDao  extends CrudDao<School> {

	public List<School> querySchoolList(School s);
	
	public List<School> queryAllSchool();
	
	public int createSchool(School s);

	public int deleteById(String schoolId);
	
}
