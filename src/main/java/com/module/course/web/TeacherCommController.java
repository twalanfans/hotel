package com.module.course.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.utils.FileOperateUtil;
import com.common.utils.SpringContextHolder;
import com.common.utils.WeekdayUtils;
import com.common.web.BaseController;
import com.github.pagehelper.PageInfo;
import com.module.course.entity.Course;
import com.module.course.entity.CourseFile;
import com.module.course.entity.CourseFileattach;
import com.module.course.service.CommCourseService;
import com.module.course.service.CourseFileService;
import com.module.course.service.CourseFileattachService;
import com.module.course.service.CourseManageService;
import com.module.course.util.CourseUtils;
import com.module.owncenter.service.StudentManageService;
import com.module.sys.dao.UserDao;
import com.module.sys.dao.UserDetailDao;
import com.module.sys.entity.Role;
import com.module.sys.entity.User;
import com.module.sys.entity.UserDetail;
import com.module.sys.service.RoleManageService;
import com.module.sys.utils.QuestionTypeEnum;
import com.module.sys.utils.TranslateUtil;
import com.module.sys.utils.UserUtils;
import com.module.testonline.entity.TestQuestion;
import com.module.testonline.service.TestOnlineService;

/**
 * 老师的公共资源Controller
 * @author  
 * @date 2016-7-25
 */
@Controller
public class TeacherCommController extends BaseController{
		
	@Autowired
	private CourseFileService courseFileService;
	
	@Autowired
	private CourseManageService courseManageService;
	
	@Autowired
	private CourseFileattachService courseFileattachService;
	
	
	/**
	 * 教师登录默认首页
	 */
	@RequestMapping(value="${adminPath}/default/teacherWec")
	public String defaultPage(HttpServletRequest request){
		String userId = UserUtils.getUser().toString();
		UserDetail userDetail = UserUtils.getUserDetail(userId);
		List<Course> courseList = CourseUtils.queryTeacherCourse(new Course(userId));
		String greetings = WeekdayUtils.getGreetings("教师");
		request.setAttribute("greetings", greetings);
		request.setAttribute("userDetail", userDetail);
		request.setAttribute("courseList", courseList);
		request.setAttribute("courseNum", courseList.size());
		return "modules/sys/teacher/welcome";
	}
	
	/**
	 * 老师的有权的公共课程列表入口页面
	 */
	@RequestMapping(value="${adminPath}/commResourse/teacherCoursePage")
	public String queryTeacherCommCourse(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String courseName = request.getParameter("courseName")==null?"": request.getParameter("courseName");
		System.out.println("UserUtils里面------"+UserUtils.getUser().toString());//只返回一个UserId
		
		UserDao userDao = SpringContextHolder.getBean(UserDao.class);
		User user2 = new User();
		user2.setUserId(UserUtils.getUser().toString());
		User user = userDao.findUserByUserId(user2);
		String schoolId = user.getSchoolId();
		System.out.println("查出来的学校ID------"+schoolId);
		
		courseName=new String(courseName.getBytes("ISO8859_1"),"UTF-8");
		Course course = new Course();
		course.setCourseName(courseName);
		course.setSchoolId(schoolId);
		System.out.println("course------"+course);
		List<Course> list = courseManageService.queryTeacherCommCourse("1",course);
		request.setAttribute("pageInfo", new PageInfo<Course>(list));
		return "modules/sys/teacher/commresource/teacher_comm_course";
	}
	
	/**
	 * 某个课程下的课件列表
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="${adminPath}/commonResource/fetchCourseFilePage")
	public String listCourseFileattach(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String courseId = request.getParameter("courseId")==null?"":request.getParameter("courseId");
		String courseType = request.getParameter("courseType")==null?"":request.getParameter("courseType");
		String fileName = request.getParameter("fileName")==null?"":request.getParameter("fileName");
		fileName=new String(fileName.getBytes("ISO8859_1"),"UTF-8");
		String questionType = request.getParameter("questionType")==null?"":request.getParameter("questionType");
		if(courseType.equals("0")){
			TestOnlineService testOnlineService = new TestOnlineService();
			TestQuestion testQuestion =new TestQuestion();
				testQuestion.setCourseId(courseId);
				testQuestion.setType(questionType);
			List<TestQuestion> quetionList = testOnlineService.queryCommonQuestion(testQuestion);
			System.out.println("questionList------"+quetionList);
			List quesType = TranslateUtil.questionTranslate();
			request.setAttribute("questionType", quesType);
			request.setAttribute("selectType", questionType);
			request.setAttribute("pageInfo", new PageInfo<TestQuestion>(quetionList));
		}else{
			CourseFile courseFile = new CourseFile();
			courseFile.setCourseId(courseId);
			courseFile.setCourseType(courseType);
			courseFile.setFileName(fileName);
			courseFile.setStatus("1");
			List<CourseFile> list  = courseFileService.courseFileListByCourseId(courseFile);
			System.out.println("courseFileListByCourseId------"+list);
			request.setAttribute("pageInfo", new PageInfo<CourseFile>(list));
		}
		UserDao userDao = SpringContextHolder.getBean(UserDao.class);
		User user2 = new User();
		user2.setUserId(UserUtils.getUser().toString());
		User user = userDao.findUserByUserId(user2);
		String schoolId = user.getSchoolId();
		System.out.println("查出来的学校ID2------"+schoolId);
		Course course = new Course();
		course.setSchoolId(schoolId);
		List courseList = courseManageService.queryTeacherCommCourse("0",course);
		System.out.println("courseList------"+courseList);
		request.setAttribute("courseList", courseList);
		request.setAttribute("courseId", courseId);
		request.setAttribute("courseType", courseType);
		request.setAttribute("fileName", fileName);
		return "modules/sys/teacher/commresource/teacher_comm_course_file";
	}
	
	@RequestMapping(value="commonResource/fetchQuestionDetail")
	public String fetchQuestionDetail(HttpServletRequest request, HttpServletResponse response){
		String testId = request.getParameter("testId");
		
		TestQuestion tq = new TestQuestion();
		TestQuestion tqnew = new TestQuestion();
		
		tq.setTestId(testId);
		TestOnlineService tos = new TestOnlineService();
		tqnew =  tos.editCeshiQuestion(tq);
		String courseId = tqnew.getCourseId();
		CommCourseService ccs = new CommCourseService();

		//获取课程名称
		Course course = new Course();
		course=ccs.getCourseDetail(courseId);
		if(course!=null)
			tqnew.setCourseName(course.getCourseName());
		//获取题型文字
		request.setAttribute("tqtype", tqnew.getType());
		request.setAttribute("selectnum", tqnew.getSelectNum());
		int qttype = Integer.parseInt(tqnew.getType());
		String typeName=QuestionTypeEnum.getName(qttype);
		tqnew.setType(typeName);
		
		request.setAttribute("tqnew", tqnew);
		return "modules/sys/teacher/commresource/teacher_comm_test_detail";
	}	
	
	/**
	 * 下载课件附件
	 * 2016-8-17
	 */
	@RequestMapping(value="courseFile/downloadAttachFile")
	public void downLoadFile(HttpServletRequest request ,HttpServletResponse response){
		String fileId = request.getParameter("fileId");
		CourseFileattach coursefileattach = new CourseFileattach();
		coursefileattach.setAttachId(fileId);
		CourseFileattach cf = courseFileattachService.attachFileDetail(coursefileattach);
		try {
			FileOperateUtil.download(request, response, "application/octet-stream; charset=utf-8", cf.getFilePath(), cf.getFileName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	 /**获取文件大小**/
    @RequestMapping(value = "courseFile/attachFileSize")
    public String getFileSize(HttpServletRequest request, HttpServletResponse response) {
		String fileId = request.getParameter("fileId");
		CourseFileattach coursefileattach = new CourseFileattach();
		coursefileattach.setAttachId(fileId);
		CourseFileattach cf= courseFileattachService.attachFileDetail(coursefileattach);
		if(cf!=null){
			File f = new File(cf.getFilePath());
			if (f.exists()){
				Long fileLength = new File(cf.getFilePath()).length();
				return renderString(response,fileLength.toString());
			}
		}
		return renderString(response,"0");
	}
    
    /************学生群组管理**********/
@RequestMapping(value="${adminPath}/ownCenter/studentGroupManage")
public String studentGroupManage(HttpServletRequest request) throws Exception{
	StudentManageService student = new StudentManageService();
	//取到当前老师的ID，通过ID获得当前老师的Id
	System.out.println("UserUtils.getUser().getUserId()------"+UserUtils.getUser().getUserId());
	String schoolId = UserUtils.getUser().getSchoolId();
	UserDetail userDetail = new UserDetail();
	userDetail.setSchoolId(schoolId);
	List<UserDetail> user = student.queryAllStudent(userDetail);
	//List<UserDetail> queryAllStudentBySchoolId = student.querUserDetail(userId);
	List<Role> roleList = RoleManageService.showAllRole();
	request.setAttribute("pageInfo", new PageInfo<UserDetail>(user));
	request.setAttribute("roleList", roleList);
	return "modules/sys/teacher/commresource/student_group_manage";
}	

@RequestMapping(value="ownCenter/allStudentQueryPage")
public String allStudentQuery(HttpServletRequest request) throws Exception{
	String gradeName = request.getParameter("gradeName")==null?"":request.getParameter("gradeName");
	gradeName = new String(gradeName.getBytes("ISO8859-1"), "UTF-8");
	String userName = request.getParameter("userName")==null?"":request.getParameter("userName");
	userName = new String(userName.getBytes("ISO8859-1"), "UTF-8");
	UserDetail userdetail = new UserDetail();
		userdetail.setGradeName(gradeName);
		userdetail.setUserName(userName);
	try {
		StudentManageService student = new StudentManageService();
		List<UserDetail> user = student.queryAllStudent(userdetail);
		request.setAttribute("pageInfo", new PageInfo<UserDetail>(user));
		request.setAttribute("gradeName", gradeName);
		request.setAttribute("userName", userName);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return "modules/sys/teacher/commresource/all_student_query";
}	

@RequestMapping(value="ownCenter/groupStudentPage")
public String groupStudent(HttpServletRequest request) throws Exception{
	String studentName = request.getParameter("studentName")==null?"":request.getParameter("studentName");
	studentName = new String(studentName.getBytes("ISO8859-1"), "UTF-8");
	String roleId = request.getParameter("roleId")==null?"":request.getParameter("roleId");
	UserDetail userdetail = new UserDetail();
		userdetail.setRoleId(roleId);
		userdetail.setUserName(studentName);
	List<UserDetail> userList = RoleManageService.queryUserByRole(userdetail);
	List<Role> roleList = RoleManageService.showAllRole();
	request.setAttribute("pageInfo1", new PageInfo<UserDetail>(userList));
	request.setAttribute("roleList", roleList);
	request.setAttribute("roleId", roleId);
	request.setAttribute("studentName", studentName);
	return "modules/sys/teacher/commresource/source_student_group";
}	
	
  
}
    