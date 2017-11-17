package com.module.owncenter.dao;

import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.course.entity.DataAnalysis;

@MyBatisDao
public interface DataAnalysisDao extends CrudDao<DataAnalysis>{

	@SuppressWarnings("rawtypes")
	public List queryUserCourseStudyTime(DataAnalysis dataAnalysis);
	
	public String queryTestQuestionNumByDate(DataAnalysis dataAnalysis);
	
	public String queryAskQuestionNumByDate(DataAnalysis dataAnalysis);
	
}
