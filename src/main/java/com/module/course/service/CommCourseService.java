package com.module.course.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.utils.SpringContextHolder;
import com.module.course.dao.CourseDao;
import com.module.course.dao.KnowledgeDao;
import com.module.course.entity.Course;
import com.module.course.entity.KnowledgeItem;
import com.module.course.util.CourseUtils;
import com.module.sys.utils.UserUtils;

/**
 * @Description  课程管理服务Service
 * @Author  yuanzhonglin
 * @Date  2016年7月12日 
 */
@Service("CommCourseService")
@Transactional(readOnly=true)
public class CommCourseService {
	
	@Transactional(readOnly=true)
	public String getKnowledgeByTestId(String courseId,String testId){
		KnowledgeDao knowledgeDao = SpringContextHolder.getBean(KnowledgeDao.class);
		String ki="";
		 List<KnowledgeItem> kilist=knowledgeDao.getKnowledgeTitleListByTestQuestionId(courseId,testId);
		 if(kilist==null ||kilist.isEmpty())
		 {
			 kilist=new ArrayList<KnowledgeItem>();
			 return null;
		 }
		 for(int i=0;i<kilist.size();i++)
		 {
			 ki=ki+kilist.get(i).getTitle()+";";
		 }
		 
		 return ki;
	}

	@Transactional(readOnly=true)
	public Course getCourseDetail(String courseId){
		CourseDao courseDao = SpringContextHolder.getBean(CourseDao.class);
		List<Course> courselist=courseDao.fetchCourseById(courseId);
		if(courselist ==null || courselist.isEmpty())
			return null;
		else
			return courselist.get(0);
		
	}
	
	@Transactional(readOnly=true)
	public static List<Course> queryCommCourse(){
		CourseDao courseDao = SpringContextHolder.getBean(CourseDao.class);
		List<Course> list  = courseDao.queryCommonCourse(new Course());
		return list;
	}
	
}
