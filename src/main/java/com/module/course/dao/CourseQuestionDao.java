package com.module.course.dao;

import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.course.entity.CourseQuestion;

@MyBatisDao
public interface CourseQuestionDao extends CrudDao<CourseQuestion>{
	public List<CourseQuestion> queryQuestionListById(CourseQuestion courseQuestion);
	
	public List<CourseQuestion> queryQuestionList(CourseQuestion courseQuestion);
	
	public List<CourseQuestion> queryAnswerList(CourseQuestion courseQuestion);
	
	public List<CourseQuestion> queryCommentList(CourseQuestion courseQuestion);
	
	public List<CourseQuestion> queryCommentByAnswerId(CourseQuestion courseQuestion);
	
	public List<CourseQuestion> queryQuestionDetail(CourseQuestion courseQuestion);
	
	public CourseQuestion queryAnswerDetail(CourseQuestion courseQuestion);

	public int deleteQuestion(CourseQuestion courseQuestion);
	
	public int deleteAnswer(CourseQuestion courseQuestion);
	
	public int deleteComment(CourseQuestion courseQuestion);
	/**
	 * 在线问答的问题顶踩
	 */
	public int updateUpDown(CourseQuestion cq);
	
	public int saveQuestionComment(CourseQuestion cq);
	
	public int saveCourseAskQuestion(CourseQuestion cq);
	
	public int saveAnswerQuestion(CourseQuestion cq);
}
