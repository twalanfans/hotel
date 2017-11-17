package com.module.course.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.common.models.TimeBean;
import com.common.utils.DateUtils;
import com.common.utils.SpringContextHolder;
import com.module.course.dao.StudyPlanDetailDao;
import com.module.course.entity.StudyPlan;
import com.module.course.entity.StudyPlanDetail;
import com.module.course.util.StudyPlanDetailUtils;

@Service("StudyPlanDetailService")
public class StudyPlanDetailService {
	
	public static void createMyStudyPlanDetailWeekly(StudyPlan sp,List<TimeBean> daylist){
		StudyPlanDetailUtils spdu = new StudyPlanDetailUtils();
		for(int i=0;i<daylist.size();i++){
			StudyPlanDetail spditem = new  StudyPlanDetail();
			spditem.setAskCount(0);
			spditem.setCourseCount(0);
			spditem.setUpdateTime(DateUtils.strToDateTime(DateUtils.getDateTime()));
			spditem.setTestCount(0);
			spditem.setPlanId(sp.getId());
			spditem.setPlanTime(DateUtils.strToDate(daylist.get(i).getDate()));
			spdu.insertMyStudyPlanDetail(spditem);
		}
	}
	
	public static StudyPlanDetail queryStudyPlanDetail(StudyPlan studyPlanl){
		StudyPlanDetailDao studyPlanDetailDao = SpringContextHolder.getBean(StudyPlanDetailDao.class);
		List<StudyPlanDetail> list = studyPlanDetailDao.queryStudyPlanDetail(studyPlanl);
		if(list.size()>0){
			StudyPlanDetail planDetail = list.get(0);
			return planDetail;
		}else{
			return null;
		}
	}
	/**
	 * 学习计划详情保存
	 */
	public void updateStudyPlanDetail(StudyPlanDetail studyPlanDetail){
		StudyPlanDetailDao studyPlanDetailDao = SpringContextHolder.getBean(StudyPlanDetailDao.class);
		studyPlanDetailDao.updateMyStudyPlanDetailByDetailId(studyPlanDetail);
	}
	
	public List<StudyPlanDetail> fetchStudyPlanDetailByPlanId(int planId){
		List<StudyPlanDetail> spdlist = new ArrayList<StudyPlanDetail>();
		StudyPlanDetailUtils spdu = new StudyPlanDetailUtils();
		spdlist= spdu.fetchMyStudyPlanDetailByPlanId(planId);
		return spdlist;
	}
	
	
}
