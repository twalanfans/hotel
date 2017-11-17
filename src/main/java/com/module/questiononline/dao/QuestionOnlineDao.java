package com.module.questiononline.dao;

import java.util.List;
import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.questiononline.entity.QuestionOnline;


@MyBatisDao
public interface QuestionOnlineDao extends CrudDao<QuestionOnline>{

	public List<QuestionOnline> queryQuestionOnline(QuestionOnline questionOnline);
	
	public List<QuestionOnline> queryCommentOnline(QuestionOnline questionOnline);

	public List<QuestionOnline> commentList(QuestionOnline questionOnline);
	
	public int updateClickNum(QuestionOnline questionOnline);
	
	public int saveNewQuestion(QuestionOnline questionOnline);
	
	public int saveComment(QuestionOnline questionOnline);

	public int commentUpTG(QuestionOnline questionOnline);

	public int saveQuestionComment(QuestionOnline questionOnline);
	
	public int deleteQuestionOnline(String questionId);
	
	public int deleteCommentByQue(String questionId);
	
	public int deleteCommentById(String commentId);
	
	public QuestionOnline questionDetail(String questionId);
	
}
