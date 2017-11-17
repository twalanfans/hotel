package com.module.course.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.utils.SpringContextHolder;
import com.module.course.dao.StudyPlanDao;
import com.module.course.entity.StudyPlan;
import com.module.course.util.StudyPlanUtils;

@Service("StudyPlanService")
public class StudyPlanService {
	
	@Transactional(readOnly=true)
	public List<StudyPlan> fetchStudyPlanByUserId(String userId){
		StudyPlan sp= new StudyPlan();
			sp.setUserid(userId);
		StudyPlanUtils spu= new StudyPlanUtils();
		List<StudyPlan> list  = spu.queryStudyPlanList(sp);
		return list;
	}
	
	
	@Transactional(readOnly=false)
	public static void insertMyStudyPlan(StudyPlan sp){
		StudyPlanUtils spu= new StudyPlanUtils();
		spu.insertStudyPlan(sp);
	}
	
	@Transactional(readOnly=true)
	public List<StudyPlan> fetchStudyPlanByUserIdPage(StudyPlan sp){
		StudyPlanUtils spu= new StudyPlanUtils();
		List<StudyPlan> list = spu.queryStudyPlanList(sp);
		return list;
	}
	@Transactional(readOnly=true)
	public StudyPlan fetchMyStudyPlanById(int planId){
		StudyPlanUtils spu= new StudyPlanUtils();
		List<StudyPlan> list = spu.fetchStudyPlanByPlanId(planId);
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@Transactional(readOnly=false)
	public static int deletePlan(StudyPlan plan){
		StudyPlanDao studyPlanDao = SpringContextHolder.getBean(StudyPlanDao.class);
		int ret=0;
		try {
			ret = studyPlanDao.deletePlan(plan);
			if(ret>0){
				ret = studyPlanDao.deletePlanDetail(plan);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
