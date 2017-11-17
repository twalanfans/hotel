package com.module.course.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.common.web.BaseController;
import com.github.pagehelper.PageInfo;
import com.module.course.entity.CourseFile;
import com.module.course.service.CourseFileService;
/**
 * 课程文件管理Controller
 * @author yuanzhonglin
 * @date 2016-8-11
 */
@Controller
public class CourseFileController extends BaseController{
	
	@Autowired
	private CourseFileService courseFileService;
	
	/**
	 * 查询所有课程文件
	 * 2016-8-11
	 */
	@RequestMapping(value="${adminPath}/course/showAllCourseFilePage")
	public String showAllCourseFile(HttpServletRequest request) throws Exception{
		String fn = request.getParameter("fileName")==null?"":request.getParameter("fileName");
		String fileName = new String(fn.getBytes("ISO8859-1"), "UTF-8");
		String status = request.getParameter("status")==null?"":request.getParameter("status");
		CourseFile file = new CourseFile();
			file.setFileName(fileName);
			file.setStatus(status);
		List<CourseFile> list = courseFileService.showAllCourseFile(file);
		request.setAttribute("pageInfo",new PageInfo<CourseFile>(list));
		request.setAttribute("fileName", fileName);
		request.setAttribute("status", status);
		return "modules/sys/admin/course_file_manage_query";
	}
	
}
