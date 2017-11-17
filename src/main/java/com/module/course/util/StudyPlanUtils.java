package com.module.course.util;

import java.util.ArrayList;
import java.util.List;

import org.noo.pagination.page.PageContext;
import org.noo.pagination.page.Pagination;
import org.springframework.transaction.annotation.Transactional;
import com.common.utils.SpringContextHolder;
import com.github.pagehelper.PageHelper;
import com.module.course.dao.StudyPlanDao;
import com.module.course.entity.StudyPlan;

public class StudyPlanUtils {
	private static StudyPlanDao studyPlanDao = SpringContextHolder.getBean(StudyPlanDao.class);
	
	/**
	 * 保存新建计划
	 */
	public int insertStudyPlan(StudyPlan sp){
		int ret= studyPlanDao.insertStudyPlan(sp);
		return ret;
	}
	
	@Transactional(readOnly=true)
	public List<StudyPlan> queryStudyPlanList(StudyPlan sp){
		Pagination pager = PageContext.getPageContext();  //分页
		int pageNo = pager.getCurrentPage();
	    int pageSize = pager.getPageSize();
	    PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
		List<StudyPlan> list = studyPlanDao.fetchStudyPlanByUserId(sp);
		return list;
	}
	
	public List<StudyPlan> fetchStudyPlanByPlanId(int planId){
		List<StudyPlan> list = new ArrayList<StudyPlan>();
		list = studyPlanDao.selectByPrimaryKey(planId);
		return list;
	}
}
