package com.module.questiononline.service;

import java.util.List;

import org.noo.pagination.page.PageContext;
import org.noo.pagination.page.Pagination;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.utils.SpringContextHolder;
import com.github.pagehelper.PageHelper;
import com.module.questiononline.dao.QuestionOnlineDao;
import com.module.questiononline.entity.QuestionOnline;
import com.module.testonline.dao.TestPaperDao;
import com.module.testonline.entity.TestPaper;

@Service
@Transactional(readOnly=true)
public class QuestionOnlineService {

	
	public List<QuestionOnline> allQuestionOnline(QuestionOnline questionOnline){
		QuestionOnlineDao questionOnlineDao = SpringContextHolder.getBean(QuestionOnlineDao.class);
		List<QuestionOnline> list = questionOnlineDao.queryQuestionOnline(questionOnline);
		return list;
	}
	
	public List<QuestionOnline> queryCommentOnline(QuestionOnline questionOnline){
		QuestionOnlineDao questionOnlineDao = SpringContextHolder.getBean(QuestionOnlineDao.class);
		List<QuestionOnline> list = questionOnlineDao.queryCommentOnline(questionOnline);
		return list;
	}
	
	public List<QuestionOnline> queryQuestionList(QuestionOnline questionOnline){
		QuestionOnlineDao questionOnlineDao = SpringContextHolder.getBean(QuestionOnlineDao.class);
		Pagination pager = PageContext.getPageContext();  //分页
		int pageNo = pager.getCurrentPage();
	    int pageSize = pager.getPageSize();
	    PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
		List<QuestionOnline> list = questionOnlineDao.queryQuestionOnline(questionOnline);
		return list;
	}
	
	public List<QuestionOnline> queryCommentList(QuestionOnline questionOnline){
		QuestionOnlineDao questionOnlineDao = SpringContextHolder.getBean(QuestionOnlineDao.class);
		Pagination pager = PageContext.getPageContext();  //分页
		int pageNo = pager.getCurrentPage();
		int pageSize = pager.getPageSize();
		PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
		List<QuestionOnline> list = questionOnlineDao.commentList(questionOnline);
		return list;
	}
	
	@Transactional(readOnly=false)
	public void updateClickNum(QuestionOnline questionOnline){
		QuestionOnlineDao questionOnlineDao = SpringContextHolder.getBean(QuestionOnlineDao.class);
		questionOnlineDao.updateClickNum(questionOnline);
	}
	
	@Transactional(readOnly=false)
	public void saveNewQuestion(QuestionOnline questionOnline){
		QuestionOnlineDao questionOnlineDao = SpringContextHolder.getBean(QuestionOnlineDao.class);
		questionOnlineDao.saveNewQuestion(questionOnline);
	}
	
	@Transactional(readOnly=false)
	public void saveComment(QuestionOnline questionOnline){
		QuestionOnlineDao questionOnlineDao = SpringContextHolder.getBean(QuestionOnlineDao.class);
		questionOnlineDao.saveComment(questionOnline);
	}
	
	@Transactional(readOnly=false)
	public void commentUpTG(QuestionOnline questionOnline){
		QuestionOnlineDao questionOnlineDao = SpringContextHolder.getBean(QuestionOnlineDao.class);
		questionOnlineDao.commentUpTG(questionOnline);
	}
	
	@Transactional(readOnly=false)
	public static void deleteQuestionAndComment(String questionId){
		QuestionOnlineDao questionOnlineDao = SpringContextHolder.getBean(QuestionOnlineDao.class);
		questionOnlineDao.deleteQuestionOnline(questionId);
		questionOnlineDao.deleteCommentByQue(questionId);
	}
	@Transactional(readOnly=false)
	public static void deleteCommentById(String commentId){
		QuestionOnlineDao questionOnlineDao = SpringContextHolder.getBean(QuestionOnlineDao.class);
		questionOnlineDao.deleteCommentById(commentId);
	}
	
	public QuestionOnline questionDetail(String questionId){
		QuestionOnlineDao questionOnlineDao = SpringContextHolder.getBean(QuestionOnlineDao.class);
		QuestionOnline questionDetail = questionOnlineDao.questionDetail(questionId);
		return questionDetail;
	}

}
