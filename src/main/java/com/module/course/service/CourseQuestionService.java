package com.module.course.service;

import java.util.ArrayList;
import java.util.List;

import org.noo.pagination.page.PageContext;
import org.noo.pagination.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.common.models.Page;
import com.common.service.BaseService;
import com.common.utils.SpringContextHolder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.module.course.dao.CourseQuestionDao;
import com.module.course.entity.CourseQuestion;

/**
 * @Description  角色管理服务类
 * @Author  yuanzhonglin
 * @Date  2016年7月8日 
 */
@Service
@Transactional(readOnly = true)
public class CourseQuestionService extends BaseService{
	
	@Autowired
	private CourseQuestionDao courseQuestionDao;
	
	
	/**
	 * 通过教师ID查询所有试题
	 * @param courseQuestion
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<CourseQuestion> allQuestionsList(CourseQuestion courseQuestion){
		
		List<CourseQuestion> list = courseQuestionDao.queryQuestionListById(courseQuestion);
		
		return list;
	}
	
	/**
	 * 查询所有课程集合
	 * yuanzhonglin
	 * 2016-8-11
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly=false)
	public Page queryQuestionList(CourseQuestion courseQuestion) {
			Pagination pager = PageContext.getPageContext();  //分页
			int pageNo = pager.getCurrentPage();
		    int pageSize = pager.getPageSize();
			PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
			List<CourseQuestion> list = courseQuestionDao.queryQuestionList(courseQuestion);
			
			PageInfo pageinfo = new PageInfo(list);
			int total = (int) (long)pageinfo.getTotal();
			List<CourseQuestion> list1=pageinfo.getList();
			List<CourseQuestion> list2= new ArrayList<CourseQuestion>();
			for(int i=0;i<list1.size();i++)
			{
				CourseQuestion cq =list1.get(i);
				list2.add(cq);
			}
			Page page = new Page(pageinfo.getPageNum(), pageinfo.getPageSize(),pageinfo.getPages(),total,list2);
			return page;
	}
	
	/**
	 * 在线问答课程题目
	 * yuanzhonglin
	 * 2016-9-2
	 */
	public List<CourseQuestion> studentQuesAnswOnline(CourseQuestion cq){
		CourseQuestionDao courseQuestionDao =SpringContextHolder.getBean(CourseQuestionDao.class);
		List<CourseQuestion> list = courseQuestionDao.queryQuestionList(cq);
		return list;
	}
	/**
	 * 在线问答的顶踩记录
	 * yuanzhonglin
	 * 2016-9-5
	 */
	@Transactional(readOnly=false)
	public void courseQuestionUpDown(CourseQuestion courseQuestion){
		CourseQuestionDao courseQuestionDao =SpringContextHolder.getBean(CourseQuestionDao.class);
		courseQuestionDao.updateUpDown(courseQuestion);
	}
	
	/**
	 * 在线问答学生评论
	 * yuanzhonglin
	 * 2016-9-5
	 */
	@Transactional(readOnly=false)
	public void questionComment(CourseQuestion courseQuestion){
		CourseQuestionDao courseQuestionDao =SpringContextHolder.getBean(CourseQuestionDao.class);
		courseQuestionDao.saveQuestionComment(courseQuestion);
	}
	
	/**
	 * 在线问答课程提问
	 * yuanzhonglin
	 * 2016-9-6
	 */
	@Transactional(readOnly=false)
	public void saveCourseAskQuestion(CourseQuestion courseQuestion){
		CourseQuestionDao courseQuestionDao =SpringContextHolder.getBean(CourseQuestionDao.class);
		courseQuestionDao.saveCourseAskQuestion(courseQuestion);
	}
	
	/**
	 * 在线问答课问题回答
	 * yuanzhonglin
	 * 2016-9-6
	 */
	@Transactional(readOnly=false)
	public void saveAnswerQuestion(CourseQuestion courseQuestion){
		CourseQuestionDao courseQuestionDao =SpringContextHolder.getBean(CourseQuestionDao.class);
		courseQuestionDao.saveAnswerQuestion(courseQuestion);
	}
	
	/**
	 * 查询课程问题回答
	 * yuanzhonglin
	 * 2016-8-12
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly=false)
	public Page queryAnswerList(CourseQuestion courseQuestion) {
		Pagination pager = PageContext.getPageContext();  //分页
		int pageNo = pager.getCurrentPage();
		int pageSize = pager.getPageSize();
		PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
		List<CourseQuestion> list = courseQuestionDao.queryAnswerList(courseQuestion);
		
		PageInfo pageinfo = new PageInfo(list);
		int total = (int) (long)pageinfo.getTotal();
		List<CourseQuestion> list1=pageinfo.getList();
		List<CourseQuestion> list2= new ArrayList<CourseQuestion>();
		for(int i=0;i<list1.size();i++)
		{
			CourseQuestion cq =list1.get(i);
			list2.add(cq);
		}
		Page page = new Page(pageinfo.getPageNum(), pageinfo.getPageSize(),pageinfo.getPages(),total,list2);
		return page;
	}
	
	public CourseQuestion answerDetail(CourseQuestion courseQuestion) {
		CourseQuestion answer = courseQuestionDao.queryAnswerDetail(courseQuestion);
		return answer;
	}
	
	/**
	 * 查询课程问题评论
	 * yuanzhonglin
	 * 2016-8-15
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly=false)
	public Page queryCommentList(CourseQuestion courseQuestion) {
		Pagination pager = PageContext.getPageContext();  //分页
		int pageNo = pager.getCurrentPage();
		int pageSize = pager.getPageSize();
		PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
		List<CourseQuestion> list = courseQuestionDao.queryCommentList(courseQuestion);
		
		PageInfo pageinfo = new PageInfo(list);
		int total = (int) (long)pageinfo.getTotal();
		List<CourseQuestion> list1=pageinfo.getList();
		List<CourseQuestion> list2= new ArrayList<CourseQuestion>();
		for(int i=0;i<list1.size();i++)
		{
			CourseQuestion cq =list1.get(i);
			list2.add(cq);
		}
		Page page = new Page(pageinfo.getPageNum(), pageinfo.getPageSize(),pageinfo.getPages(),total,list2);
		return page;
	}
	
	@Transactional(readOnly=false)
	public void deleteQuestion(CourseQuestion courseQuestion){
		CourseQuestionDao courseQuestionDao =SpringContextHolder.getBean(CourseQuestionDao.class);
		courseQuestionDao.deleteQuestion(courseQuestion);
		deleteAnswer(courseQuestion);
		deleteComment(courseQuestion);
	}
	
	@Transactional(readOnly=false)
	public void deleteAnswer(CourseQuestion courseQuestion){
		CourseQuestionDao courseQuestionDao =SpringContextHolder.getBean(CourseQuestionDao.class);
		courseQuestionDao.deleteAnswer(courseQuestion);
	}
	
	@Transactional(readOnly=false)
	public void deleteComment(CourseQuestion courseQuestion){
		CourseQuestionDao courseQuestionDao =SpringContextHolder.getBean(CourseQuestionDao.class);
		courseQuestionDao.deleteComment(courseQuestion);
	}
	
	/**
	 * 通过问题Id查询问题详情（评论及回复）
	 * yuanzhonglin
	 * 2016-8-11
	 */
	@SuppressWarnings("rawtypes")
	public List queryQuestionDetail(CourseQuestion courseQuestion){
		List detailList = new ArrayList();
			try {
				detailList = courseQuestionDao.queryQuestionDetail(courseQuestion);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return detailList;
	}
	
	/**
	 * 通过answerId查询回答评论（评论及回复）
	 */
	@SuppressWarnings("rawtypes")
	public List queryCommentByAnswerId(CourseQuestion courseQuestion){
		List detailList = new ArrayList();
			try {
				detailList = courseQuestionDao.queryCommentByAnswerId(courseQuestion);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return detailList;
	}
	
}
