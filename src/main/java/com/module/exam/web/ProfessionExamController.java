package com.module.exam.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.utils.DateUtils;
import com.common.web.BaseController;
import com.module.exam.entity.CriticismExam;
import com.module.exam.service.CriticismManageService;
import com.module.sys.utils.UserUtils;
import com.module.testonline.entity.TestPaper;
import com.module.testonline.entity.TestQuestion;
import com.module.testonline.service.TestOnlineService;

/**
 * 职业资格考试Controller类
 * @author yuanzhonglin
 * @date 2016-7-21
 */
@Controller
public class ProfessionExamController extends BaseController{
	
	@Autowired
	private CriticismManageService criticismManageService;
	
	/**
	 * 职业资格考试的考证服务
	 */
	@RequestMapping(value="${adminPath}/professionExam/criticismService")
	public String criticismService(HttpServletRequest request){
		List<CriticismExam> list = criticismManageService.queryCriticismServiceFile();
		request.setAttribute("criticismFile", list);
		return "modules/sys/student/exam/student_criticismfile_download";
	}
	
	/**
	 * 职业资格考试的考证自测
	 */
	@RequestMapping(value="${adminPath}/professionExam/criticismTest")
	public String criticismTest(HttpServletRequest request){
		List<CriticismExam> list = criticismManageService.queryCriticismKnowledge();
		List<CriticismExam> criticismPaper = criticismManageService.queryCriticismPaperList();
		request.setAttribute("knowledgeIdList", list);
		request.setAttribute("paperList", criticismPaper);
		return "modules/sys/student/exam/student_criticism_testservice";
	}
	
}
