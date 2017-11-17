package com.module.course.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.persistence.annotation.MyBatisDao;
import com.module.course.entity.StudyPlan;
import com.module.course.entity.StudyPlanDetail;

@MyBatisDao

public interface StudyPlanDetailDao {

	public int insertMyStudyPlanDetail(StudyPlanDetail spd);
	public List<StudyPlanDetail> selectMyStudyPlanDetailByPlanId(@Param("planId")Integer planId);
	public int updateMyStudyPlanDetailByDetailId(StudyPlanDetail spd);
	
	public List<StudyPlanDetail> queryStudyPlanDetail(StudyPlan studyPlanl);
}
