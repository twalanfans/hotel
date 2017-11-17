package com.module.course.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.persistence.annotation.MyBatisDao;
import com.module.course.entity.StudyPlan;
@MyBatisDao
public interface StudyPlanDao {

	public List<StudyPlan> fetchStudyPlanByUserId(StudyPlan sp);
	
	public  int insertStudyPlan(StudyPlan sp);

	public  int deletePlan(StudyPlan sp);

	public  int deletePlanDetail(StudyPlan sp);
	
	public List<StudyPlan> selectByPrimaryKey(@Param("planId")Integer planId);
}
