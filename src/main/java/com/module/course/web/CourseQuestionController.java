package com.module.course.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.common.web.BaseController;
import com.module.course.entity.CourseQuestion;
import com.module.course.service.CourseQuestionService;

/**
 * 课程提问问题管理
 * yuanzhonglin
 * 2016-8-11
 */
@Controller
public class CourseQuestionController extends BaseController{
	
	@Autowired
	private CourseQuestionService courseQuestionService;
	
	/**
	 * 删除课程问题
	 * 2016-8-12
	 */
	@RequiresRoles("admin") 
	@RequestMapping(value="course/deleteCourseQuestion")
	public String deleteCourseQuestion(HttpServletRequest request,HttpServletResponse response){
		String questionId = request.getParameter("questionId");
		String idList[]=questionId.split(",");
		CourseQuestion courseQuestion = new CourseQuestion();
		try {
			for(int i=0; i<idList.length; i++){
				courseQuestion.setQuestionId(idList[i]);
				courseQuestionService.deleteQuestion(courseQuestion);
			}
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	/**
	 * 删除课程答复
	 * 2016-8-12
	 */
	@RequiresRoles("admin") 
	@RequestMapping(value="course/deleteCourseAnswer")
	public String deleteCourseAnswer(HttpServletRequest request,HttpServletResponse response){
		String answerId = request.getParameter("answerId");
		String idList[]=answerId.split(",");
		CourseQuestion courseQuestion = new CourseQuestion();
		try {
			for(int i=0; i<idList.length; i++){
				courseQuestion.setAnswerId(idList[i]);
				courseQuestionService.deleteAnswer(courseQuestion);
			}
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	/**
	 * 删除课程评论
	 * 2016-8-15
	 */
	@RequiresRoles("admin") 
	@RequestMapping(value="course/deleteCourseComment")
	public String deleteCourseComment(HttpServletRequest request,HttpServletResponse response){
		String commentId = request.getParameter("commentId");
		String idList[]=commentId.split(",");
		CourseQuestion courseQuestion = new CourseQuestion();
		try {
			for(int i=0; i<idList.length; i++){
				courseQuestion.setCommentId(idList[i]);
				courseQuestionService.deleteComment(courseQuestion);
			}
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	/**
	 * 查询课程问题的回答及评论详情
	 * 2016-8-11
	 */
	@RequiresRoles("admin") 
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="course/showCourseQuestionDetail")
	public String showCourseQuestionDetail(HttpServletRequest request) throws Exception{
		String questionId = request.getParameter("questionId")==null?"":request.getParameter("questionId");
		CourseQuestion courseQuestion = new CourseQuestion();
			courseQuestion.setQuestionId(questionId);
		List list = courseQuestionService.queryQuestionDetail(courseQuestion);
		request.setAttribute("questionDetail", list);
		return "modules/sys/admin/admin_course_question_detail";
	}
	
}
