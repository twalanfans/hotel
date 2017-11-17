package com.module.course.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.common.utils.SpringContextHolder;
import com.module.course.dao.CoursefileKnowledgeDao;
import com.module.course.entity.CoursefileKnowledge;

@Service
@Transactional(readOnly=true)
public class CoursefileKnowledgeService {
	
	/**
	 * 资料库类别
	 */
	@Transactional(readOnly=true)
	public List<CoursefileKnowledge> showFileClass(CoursefileKnowledge file){
		CoursefileKnowledgeDao cfkDao = SpringContextHolder.getBean(CoursefileKnowledgeDao.class);
		List<CoursefileKnowledge> fileClass = cfkDao.findAllList(file);
		return fileClass;
	}
	
	@Transactional(readOnly=false)
	public void addClassFile(CoursefileKnowledge file){
		CoursefileKnowledgeDao cfkDao = SpringContextHolder.getBean(CoursefileKnowledgeDao.class);
			cfkDao.addFileClass(file);
	}
	
	@Transactional(readOnly=false)
	public void updateClassFileName(CoursefileKnowledge file){
		CoursefileKnowledgeDao cfkDao = SpringContextHolder.getBean(CoursefileKnowledgeDao.class);
		cfkDao.updateClassFileName(file);
	}
	
	@Transactional(readOnly=false)
	public void deleteClassFile(CoursefileKnowledge file){
		CoursefileKnowledgeDao cfkDao = SpringContextHolder.getBean(CoursefileKnowledgeDao.class);
		cfkDao.delete(file);
	}
	
}
