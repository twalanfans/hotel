package com.module.resourlib.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.web.BaseController;

/**
 * 资源库Controller类
 * @author yuanzhonglin
 * @2016-7-21
 */
@Controller
public class ResourceLibController extends BaseController{
	/**
	 * 资源库-案例库
	 * 2016-7-21
	 */
	@RequestMapping(value="${adminPath}/resourceLib/caseLib")
	public String queryCaseLib(){
		return "modules/sys/student/resourlib/student_caselib_query";
	}
	/**
	 * 资源库-知识库
	 * 2016-7-21
	 */
	@RequestMapping(value="${adminPath}/resourceLib/knowledgeLib")
	public String queryKnoLib(){
		return "modules/sys/student/resourlib/student_caselib_query";
	}
	/**
	 * 资源库-试题库
	 * 2016-7-21
	 */
	@RequestMapping(value="${adminPath}/resourceLib/examQues")
	public String queryExamQues(){
		return "modules/sys/student/resourlib/student_caselib_query";
	}
	/**
	 * 资源库-交互式电子书
	 * 2016-7-21
	 */
	@RequestMapping(value="${adminPath}/resourceLib/eBook")
	public String queryEBook(){
		return "modules/sys/student/resourlib/student_caselib_query";
	}
	
	
}
