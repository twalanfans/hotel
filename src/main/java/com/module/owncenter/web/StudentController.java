package com.module.owncenter.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.common.models.Page;
import com.common.models.TimeBean;
import com.common.utils.DateUtils;
import com.common.utils.JsonUtil;
import com.common.utils.WeekdayUtils;
import com.common.web.BaseController;
import com.github.pagehelper.PageInfo;
import com.module.course.entity.Course;
import com.module.course.entity.CourseStudyLog;
import com.module.course.entity.StudyPlan;
import com.module.course.entity.StudyPlanDetail;
import com.module.course.service.CourseManageService;
import com.module.course.service.CourseQuestionService;
import com.module.course.service.CourseStudylogService;
import com.module.course.service.StudyPlanDetailService;
import com.module.owncenter.service.StudentManageService;
import com.module.sys.entity.Message;
import com.module.sys.entity.Notice;
import com.module.sys.entity.User;
import com.module.sys.entity.UserDetail;
import com.module.sys.service.MessageService;
import com.module.sys.service.NoticeManageService;
import com.module.sys.service.SystemService;
import com.module.sys.utils.FileDownAndUpload;
import com.module.sys.utils.UserUtils;
import com.module.testonline.entity.TestPaper;
import com.module.testonline.service.TestOnlineService;

/**
 * @Description  个人中心Controller
 * @Author  yuanzhonglin
 * @Date  2016年7月12日 
 */
@Controller
public class StudentController extends BaseController{
	
	@Autowired
	private StudentManageService studentManageService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private CourseQuestionService courseQuestionService;
	
	@Autowired
	private CourseManageService courseManageService;
		
	/**
	 * 学生默认首页
	 */
	@RequestMapping(value="${adminPath}/student/defaultIndex")
	public String dataAnalysis(HttpServletRequest request){
		String userId = UserUtils.getUser().toString();
		UserDetail userDetail = UserUtils.getUserDetail(userId);
		String greetings = WeekdayUtils.getGreetings("同学");
		CourseManageService service = new CourseManageService();
		List<Course> courseList = service.queryStudentCourse(new Course(userId));
		String today = DateUtils.getDate();
		StudyPlan planDetail = new StudyPlan();
			planDetail.setUserid(userId);
			planDetail.setStartTime(today);
		StudyPlanDetail studyPlanDetail = StudyPlanDetailService.queryStudyPlanDetail(planDetail);
		if(studyPlanDetail!=null){
			CourseStudyLog log = new CourseStudyLog();
			log.setUserId(userId);
			int totalTimeLong=0;
			String courseIdList = studyPlanDetail.getCourseList();
			String startDate = DateUtils.formatDateTime(studyPlanDetail.getPlanTime());
			log.setLogTime(startDate);
				//------微课观看总时长
				if(courseIdList!=null){
					String courseIds[] =courseIdList.split(",") ;
					for(int j=0; j<courseIds.length; j++){
						log.setCourseId(courseIds[j]);
						List<CourseStudyLog> courseLog = CourseStudylogService.fetchUserCourseStudylogByCourseId(log);
						if(!courseLog.isEmpty()){
							for(int h=0; h<courseLog.size(); h++){
								CourseStudyLog courseStudyLog = courseLog.get(h);
								totalTimeLong += courseStudyLog.getStudyTimeLong();
							}
						}
					}
				}
				studyPlanDetail.setStudyTimeLong(totalTimeLong);	//当天实际观看视频总时长
				String testNum = CourseStudylogService.queryTestQuestionNum(log);		//当天实际组卷自测题数
				String askQuestionNum = CourseStudylogService.queryAskQuestionNum(log);
				studyPlanDetail.setTestQuestionNum(Integer.parseInt(testNum));
				studyPlanDetail.setAskQuestionNum(Integer.parseInt(askQuestionNum));
		}
		request.setAttribute("studyPlanDetail", studyPlanDetail);
		request.setAttribute("userDetail", userDetail);
		request.setAttribute("today", today);
		request.setAttribute("greetings", greetings);
		request.setAttribute("courseList", courseList);
		return "modules/sys/student/student_welcome_data";
	}
	
	/**
	 * 个人信息修改
	 * 2016-7-12
	 */
	@RequestMapping(value="ownCenter/updateUserDetail")
	public String updateUserDetail(HttpServletRequest request,HttpServletResponse response)throws Exception{
			Map<String, Object> map = new HashMap<String, Object>();
			String userName = request.getParameter("userName"); //真实姓名
		    String phone = request.getParameter("phone");			//手机号
		    String loginName = request.getParameter("loginName");	
		    String email = request.getParameter("email");			//邮箱
		    String birthday = request.getParameter("birthday");	//出身日期
		    String address = request.getParameter("address"); 	//地址
		    String remark = request.getParameter("remark");	//个人说明
		    String userId = UserUtils.getUser().toString();
		    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
	        MultipartFile imgFile = multipartRequest.getFile("headPic"); 
	        String headImgPath="";
	        if(imgFile.getOriginalFilename()!=""){
		         headImgPath = FileDownAndUpload.cutImgAndUpload(userId+".jpg", "userHeadImg/"+loginName, imgFile);
	        }
		    UserDetail userDetail  = new UserDetail();
		    	userDetail.setUserId(userId);
		    	userDetail.setUserName(userName);
		    	userDetail.setPhone(phone);
		    	userDetail.setPhoto(headImgPath);
		    	userDetail.setEmail(email);
		    	userDetail.setBirthday(birthday);
		    	userDetail.setAddress(address);
		    	userDetail.setRemark(remark);
		    	userDetail.setUpdateTime(Timestamp.valueOf(DateUtils.getDateTime()));
	    	try {
				studentManageService.updateUserDetail(userDetail);
				map.put("status", "success");
			} catch (Exception e) {
				e.printStackTrace();
				map.put("status", "error");
				return ajaxHtml(JsonUtil.getJsonString4JavaPOJO(map), response);
			}
	    return ajaxHtml(JsonUtil.getJsonString4JavaPOJO(map), response);
	}
	/**
	 * 个人中心密码修改
	 * @date 2016-7-12
	 */
	@RequestMapping(value="ownCenter/changePassword")
	public String changPassword(HttpServletRequest request,HttpServletResponse response){
			String result ="";
			String oldPassword = request.getParameter("oldPwd");	//旧密码
			String newPassword = request.getParameter("newPwd");
			User user = UserUtils.getUser();
		try {
			if (SystemService.validatePassword(oldPassword, user.getPassword())){	//将
				systemService.updatePassword(user.getLoginName(), newPassword);
				result = "success";
			}else{
				result = "fail";
			}
		} catch (Exception e) {
			result="error";
		}
		return renderString(response, result);
	}
	
	/**
	 * 初始化密码修改界面
	 * @date 2016-7-14
	 */
	@RequestMapping(value="${adminPath}/ownCenter/updatePassword")
	public String updatePassword(HttpServletRequest request){
			UserDetail userDetail = StudentManageService.getUserDetailByUserId(); 		//含userDetail对象的List集合
			request.setAttribute("userNameAndId", userDetail);
			return "modules/sys/student/owncenter/student_change_password";
	}
	
	/**
	 * 接收短消息
	 */
	@RequestMapping(value="${adminPath}/common/receiveMessagePage")
	public String showMessageList(HttpServletRequest request) throws Exception{
		String fu = request.getParameter("fromUser")==null?"":request.getParameter("fromUser");
		String fromUser = new String(fu.getBytes("ISO8859-1"), "UTF-8");
		String sb = request.getParameter("subject")==null?"":request.getParameter("subject");
		String subject = new String(sb.getBytes("ISO8859-1"), "UTF-8");
		String isRead = request.getParameter("isRead")==null?"":request.getParameter("isRead");   //查看状态：1：已查看 0：未查看
		String userId = UserUtils.getUser().toString();
		Message message = new Message();
			message.setFromUser(fromUser);
			message.setSubject(subject);
			message.setIsRead(isRead);
			message.setToUser(userId); //当前用户
		MessageService messageService = new MessageService();
		Page page = messageService.messageList(message);
		request.setAttribute("messageList", page);
		request.setAttribute("fromUser", fromUser);
		request.setAttribute("subject", subject);
		request.setAttribute("status", isRead);
		return "modules/sys/common/receive_message";
	}
	
	/**
	 * 公用发送短消息
	 */
	@RequestMapping(value="${adminPath}/common/sendMessagePage")
	public String sendMessage(HttpServletRequest request) throws Exception{
			String tu = request.getParameter("toUser")==null?"":request.getParameter("toUser");
			String toUser = new String(tu.getBytes("ISO8859-1"), "UTF-8");
			String sb = request.getParameter("subject")==null?"":request.getParameter("subject");
			String subject = new String(sb.getBytes("ISO8859-1"), "UTF-8");
			Message message = new Message();
			message.setToUser(toUser);
			message.setSubject(subject);
			message.setFromUser(UserUtils.getUser().toString());
			MessageService messageService = new MessageService();
			Page page = messageService.sendMessage(message);
			request.setAttribute("messageList", page);
			request.setAttribute("toUser", toUser);
			request.setAttribute("subject", subject);
			return "modules/sys/common/send_message_query";
	}
	
	/**
	 * 学生个人错题集初始化
	 */
	@RequestMapping(value="${adminPath}/student/myErrorNotesPage")
	public String myErrorNotes(){
		return "modules/sys/student/testonline/student_error_notes";
	}
	
	@RequestMapping(value="${adminPath}/student/queryMyErrorNotes")
	public String myErrorNotesPage(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String title = request.getParameter("title")==null?"":request.getParameter("title");
		String questionTitle = new String(title.getBytes("ISO8859-1"), "UTF-8");
		String startTime = request.getParameter("startTime")==null?"":request.getParameter("startTime");
		String endTime = request.getParameter("endTime")==null?"":request.getParameter("endTime");
		String page = request.getParameter("page")==null?"":request.getParameter("page");
		String userId = UserUtils.getUser().toString();
		TestPaper testPaper = new TestPaper();
		testPaper.setQuestionTitle(questionTitle);
		testPaper.setStartTime(startTime);
		testPaper.setEndTime(endTime);
		testPaper.setStudentId(userId);
		List<TestPaper> notesList = studentManageService.queryMyErrorNotes(Integer.parseInt(page),testPaper);
		return renderString(response,notesList);
	}
	/**
	 * 学生-我的试卷
	 */
	@RequestMapping(value="${adminPath}/student/studentTestPaperPage")
	public String studentTestPaperPage(HttpServletRequest request)throws Exception{
		String paperName = request.getParameter("paperName")==null?"":request.getParameter("paperName");
		paperName = new String(paperName.getBytes("ISO8859-1"), "UTF-8");
		String status = request.getParameter("status")==null?"":request.getParameter("status");
		String userId = UserUtils.getUser().toString();
		TestPaper testPaper = new TestPaper();
			testPaper.setStudentId(userId);
			testPaper.setPaperName(paperName);
			testPaper.setStatus(status);
		List<TestPaper> paperList = TestOnlineService.queryStudentPaper(testPaper);
		try {
			//将状态为"答题中"且超过2小时的数据状态修改为"待打分"状态
			TestOnlineService.changePaperAnStatus();
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("pageInfo", new PageInfo<TestPaper>(paperList));
		request.setAttribute("paperName", paperName);
		request.setAttribute("currentUser", userId);
		request.setAttribute("status", status);
		return "modules/sys/student/testonline/student_paperTest_query";
	}
	 
	 /**
	  * 学生查看错题明细
	  */
	 @RequestMapping(value="student/errorQuestionDetail")
	 public String errorQuestionDetail(HttpServletRequest request)throws Exception{
		 String noteId = request.getParameter("noteId")==null?"":request.getParameter("noteId");
		 String userId = UserUtils.getUser().toString();
		 TestPaper testPaper = new TestPaper();
			testPaper.setStudentId(userId);
			testPaper.setNoteId(noteId);
		 List<TestPaper> list =  studentManageService.queryMyErrorNotes(0,testPaper);
		 if(list.size()>0){
			 testPaper = list.get(0);
		 }
		 request.setAttribute("questionDetail", testPaper);
		 return "modules/sys/student/testonline/student_error_question_detail";
	 }
	 
	 /**
	  * 学生添加错题集
	  */
   @RequestMapping(value="student/updateErrorNotes")
	public String updateErrorNotes(HttpServletRequest request, HttpServletResponse response){
		String noteId = request.getParameter("noteId")==null?"":request.getParameter("noteId");
		String isAdd = request.getParameter("isAdd")==null?"":request.getParameter("isAdd");
		TestPaper testPaper = new TestPaper();
			testPaper.setIsAddNotes(isAdd);
		String idList[] = noteId.split(",");
		try {
			for(int i=0; i<idList.length; i++){
				testPaper.setNoteId(idList[i]);
				studentManageService.updateErrorNotes(testPaper);
			}
			return renderString(response,"success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response,"error");
		}
	}
	 
   @RequestMapping(value="testonline/errorQuesToPaper")
	public String chooseQuesToPaper(HttpServletRequest request,HttpServletResponse response){
		String questionId = request.getParameter("questionId");
		String paperName = "错题组卷("+DateUtils.getDate()+")";
		String idList[] = questionId.split(",");
		String userId = UserUtils.getUser().toString();
		TestPaper testPaper = new TestPaper(); 
			testPaper.setPaperName(paperName);
			testPaper.setCreateUser(userId);
			testPaper.setCreateTime(Timestamp.valueOf(DateUtils.getDateTime()));
			testPaper.setPaperType("1");
			testPaper.setStatus("1");
		String paperId = "";
		try {
			//--------组卷开始
			TestOnlineService testOnlineService = new TestOnlineService();
			testOnlineService.saveTestPaper(testPaper);
			paperId = testPaper.getPaperId();
			TestPaper question = new TestPaper();
			for(int i=0; i<idList.length;i++){
				question.setQuestionId(idList[i]);
				question.setPaperId(paperId);
				question.setQuestionScore("10");
				testOnlineService.savePaperQuestion(question);
			}
			//-----组卷结束  并推送至组卷记录中
			testPaper.setPaperId(paperId);
			testPaper.setPaperScore("0");
			testPaper.setCreateUser(userId);
			testOnlineService.sendPaperToUser(testPaper);
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
		return renderString(response, "success");
	}
	
	/**
	 * 数据分析
	 */
	@RequestMapping(value="${adminPath}/ownCenter/dataAnalysis")
	public String dataAnalysis(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String today = DateUtils.getDate();
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName")==null?"":request.getParameter("userName");
		userName = new String(userName.getBytes("ISO8859-1"), "UTF-8");
		request.setAttribute("today", today);
		request.setAttribute("userId", userId);
		request.setAttribute("userName", userName);
		return "modules/sys/student/owncenter/student_data_analysis";
	}
	
	/**
	 * 数据分析
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="ownCenter/dataAnalysisDetail")
	public String dataAnalysisDetail(HttpServletRequest request,HttpServletResponse response){
		String dataType = request.getParameter("dataType");
		String startTime = request.getParameter("startTime");
		String userId = request.getParameter("userId")==null?"":request.getParameter("userId");
		try {
			if(userId.equals("")){
				userId = UserUtils.getUser().toString();
			}
			//------微课观看总时长
			int totalTimeLong=0;
			List<TimeBean> daylist = WeekdayUtils.getDateList(DateUtils.strToDate(startTime));		//从当前日期推后7天
			List list = new ArrayList();
			for(int i=0;i<daylist.size();i++){
				CourseStudyLog log = new CourseStudyLog();
				log.setUserId(userId);
				log.setLogTime(daylist.get(i).getDate());
				if(dataType.equals("1")){
					List studyTime = CourseStudylogService.userCourseStudyTime(log);
					if(studyTime.size()>0){
						for(int j=0; j<studyTime.size(); j++){
							totalTimeLong += Integer.parseInt(studyTime.get(j).toString());
						}
					}
					list.add(totalTimeLong);
					totalTimeLong = 0;	//重新初始化时长
				}else if(dataType.equals("2")){
					String testNum = CourseStudylogService.queryTestQuestionNum(log);	
					list.add(testNum);
				}else if(dataType.equals("3")){
					String askQuestionNum = CourseStudylogService.queryAskQuestionNum(log);
					list.add(askQuestionNum);
				}
			}
			for(int i=0;i<daylist.size();i++){
				list.add(daylist.get(i).getDate());
			}
			return renderString(response, list);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return renderString(response, "error");
		}		
	}
	
}
