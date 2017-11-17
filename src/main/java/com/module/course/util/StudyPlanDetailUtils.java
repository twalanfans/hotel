package com.module.course.util;

import java.util.ArrayList;
import java.util.List;

import com.common.utils.SpringContextHolder;
import com.module.course.dao.StudyPlanDetailDao;
import com.module.course.entity.StudyPlanDetail;

public class StudyPlanDetailUtils {
	private static StudyPlanDetailDao studyPlanDetailDao = SpringContextHolder.getBean(StudyPlanDetailDao.class);
	
	public int insertMyStudyPlanDetail(StudyPlanDetail spd){
		int ret=0;
		try {
			ret = studyPlanDetailDao.insertMyStudyPlanDetail(spd);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return ret;
	}
	
	public List<StudyPlanDetail> fetchMyStudyPlanDetailByPlanId(int planId){
		List<StudyPlanDetail> spdlist = new ArrayList<StudyPlanDetail>();
		try{
			spdlist= studyPlanDetailDao.selectMyStudyPlanDetailByPlanId(planId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return spdlist;
	}
	
		
	public int updateMyStudyPlanDetailByDetailId(StudyPlanDetail spd)
	{
		int ret;
		try {
				ret = studyPlanDetailDao.updateMyStudyPlanDetailByDetailId(spd);
			} catch (Exception e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
		}
		return ret;

	}

}
