package com.module.owncenter.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.web.BaseController;
import com.github.pagehelper.PageInfo;
import com.module.owncenter.entity.School;
import com.module.owncenter.service.SchoolService;
import com.sean.beans.RandomStringGenerator;

@Controller
public class SchoolController extends BaseController{
	
	/**
	 * 初始化所有用户列表
	 * 2016-8-8
	 */
	@RequestMapping(value="${adminPath}/school/querySchoolPage")
	public String querySchoolPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String schoolName = request.getParameter("schoolName")==null?"":request.getParameter("schoolName");
		schoolName = new String(schoolName.getBytes("ISO8859-1"), "UTF-8");
		School s = new School();
			s.setSchoolName(schoolName);
		List<School> school = SchoolService.querySchoolList(s);
		request.setAttribute("pageInfo", new PageInfo<School>(school));
		request.setAttribute("schoolName", schoolName);
		return "modules/sys/school/school_manage_query";
	}
	
	@RequestMapping(value="${adminPath}/school/deleteSchool")
	public String deleteUser(HttpServletRequest request,HttpServletResponse response){
		String schoolId = request.getParameter("schoolId");
		String idList[]=schoolId.split(",");
		try {
			int ret=0;
			for(int i=0; i<idList.length; i++){
				ret += SchoolService.deleteSchool(idList[i]);
			}
			return renderString(response, ret);
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	@RequestMapping(value="${adminPath}/school/createSchool")
	public String createSchool(HttpServletRequest request, HttpServletResponse response){
		String schoolName = request.getParameter("schoolName")==null?"":request.getParameter("schoolName");
		School s = new School();
			s.setSchoolId(RandomStringGenerator.getRandomStringByLength(16));
			s.setSchoolName(schoolName);
		try{
			int ret = SchoolService.createSchool(s);
			if(ret>0){
				return renderString(response, "success");
			}else{
				return renderString(response, "error");
			}
		}catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
}
