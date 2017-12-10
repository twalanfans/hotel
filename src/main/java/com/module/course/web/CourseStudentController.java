package com.module.course.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.models.TimeBean;
import com.common.utils.DateUtils;
import com.common.utils.WeekdayUtils;
import com.common.web.BaseController;
import com.github.pagehelper.PageInfo;
import com.module.course.entity.Course;
import com.module.course.entity.CourseFile;
import com.module.course.entity.CourseNotes;
import com.module.course.entity.CourseStudyLog;
import com.module.course.entity.StudyPlan;
import com.module.course.entity.StudyPlanDetail;
import com.module.course.service.CourseFileService;
import com.module.course.service.CourseManageService;
import com.module.course.service.CourseStudylogService;
import com.module.course.service.StudyPlanDetailService;
import com.module.course.service.StudyPlanService;
import com.module.sys.entity.User;
import com.module.sys.utils.UserUtils;

/**
 * @Description  学生理论教学模块的课程学习
 * @Author 
 * @Date  2016年7月12日 
 */

@Controller
public class CourseStudentController extends BaseController{

	@Autowired
	private CourseManageService courseManageService;
	
	@Autowired
	private CourseFileService courseFileService;
	
	@Autowired
	private StudyPlanService studyPlanService;
	
	@Autowired
	private StudyPlanDetailService studyPlanDetailService;
	
	/**
	 * 课程学习视频播放
	 * yuanzhonglin
	 * 2016-8-20
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value="${adminPath}/course/studentCoursePlay")
	public String defaultCoursePlay(HttpServletRequest request){
		String userId = UserUtils.getUser().toString();

		List courseList = courseManageService.queryStudentCourse(new Course(userId));  //获取所有课程
			
		request.setAttribute("courseList", courseList);  	//课程导航

		return "modules/sys/student/course/student_coursestudy_video";
	}
	
	/**
	 * 课程视频播放列
	 * yuanzhonglin
	 * 2016-9-1
	 */
	@RequestMapping(value="course/queryCourseVideoList")
	public String courseVideoList(HttpServletRequest request,HttpServletResponse response) throws Exception{
			String courseId = request.getParameter("courseId")==null?"":request.getParameter("courseId");
			CourseFile courseFile = new CourseFile();
				courseFile.setCourseId(courseId);
				courseFile.setCourseType("1");
				courseFile.setStatus("1");
			List<CourseFile> courseVideoList = new ArrayList<CourseFile>();
			try {
				courseVideoList = courseFileService.getCourseFileByCourseId(courseFile);			
			} catch (Exception e) {
				e.printStackTrace();
			}		
			return renderString(response,courseVideoList);
	}
	
	@RequestMapping(value="course/courseVideoHistory")
	public String courseVideoHistory(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String userId = UserUtils.getUser().toString();	
		List<CourseFile> historyVideoList = new ArrayList<CourseFile>();
		try {			
			historyVideoList = courseFileService.historyVideoList(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return renderString(response,historyVideoList);
	}
	/**
	 * 视频课程笔记展示
	 * @date 2016-7-20
	 */
	@RequestMapping(value="course/queryCourseNotes")
	public String queryCourseNotes(HttpServletRequest request){
		String courseId = request.getParameter("courseId")==null?"":request.getParameter("courseId");
		String num = request.getParameter("courseNum")==null?"":request.getParameter("courseNum");
		int courseNum =10;
		if(!num.equals("")){
			courseNum=Integer.parseInt(num)+10;  //每点击加载更多展示多15条数据
		}
		String userId = UserUtils.getUser().toString();
		CourseNotes courseNotes = new CourseNotes();
			courseNotes.setCourseId(courseId);
			courseNotes.setUserId(userId);
		List<CourseNotes> noteList = courseManageService.queryNotes(courseNotes);  
		request.setAttribute("noteList", noteList);
		request.setAttribute("noteNum", noteList.size());
		request.setAttribute("courseId", courseId);
		request.setAttribute("courseNum", courseNum);
		return "modules/sys/student/course/course_video_studynote";
	}
	/**
	 * 视频课程笔记保存
	 * @date 2016-7-20
	 */
	@RequestMapping(value="course/saveCourseNotes")
	public String courseNotes(HttpServletRequest request,HttpServletResponse response){
			String courseId = request.getParameter("courseId")==null?"":request.getParameter("courseId");
			String noteContent = request.getParameter("notesText");
			String userId = UserUtils.getUser().toString();
			CourseNotes note = new CourseNotes();
			note.setUserId(userId);   
			note.setUpdateTime(Timestamp.valueOf(DateUtils.getDateTime()));
			note.setNoteContent(noteContent);
			note.setCourseId(courseId);
			try {
				courseManageService.insertNotes(note);
				return renderString(response, "success");
			} catch (Exception e) {
				e.printStackTrace();
				return renderString(response, "error");
			}
	}

	/**
	 * 学生的所有课程查询
	 * yuanzhonglin
	 * 2016-8-29
	 */
	@RequestMapping(value="${adminPath}/student/myCourseListPage")
	public String myCourseListPage(HttpServletRequest request) throws Exception{
		String courseName = request.getParameter("courseName")==null?"":request.getParameter("courseName");
		//String courseName = new String(cn.getBytes("ISO8859-1"), "UTF-8");
		String teacherName = request.getParameter("teacherName")==null?"":request.getParameter("teacherName");
		//String teacherName = new String(tn.getBytes("ISO8859-1"), "UTF-8");
		String userId = UserUtils.getUser().toString();
		Course course = new Course();
			course.setCourseName(courseName);
			course.setTeacherName(teacherName);
			course.setUserId(userId);
		List<Course> list = courseManageService.queryUserCourse(course);
		request.setAttribute("pageInfo", new PageInfo<Course>(list));
		request.setAttribute("courseName", courseName);
		request.setAttribute("teacherName", teacherName);
        return "modules/sys/student/owncenter/student_mycourse_query";
    }
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="${adminPath}/student/courseFileListPage")
	public String fetchCourseFileList(HttpServletRequest request) throws Exception{
		String courseId = request.getParameter("courseId")==null?"":request.getParameter("courseId");
		String courseType = request.getParameter("courseType")==null?"":request.getParameter("courseType");
		String fileName = request.getParameter("fileName")==null?"":request.getParameter("fileName");
		//fileName=new String(fileName.getBytes("ISO8859_1"),"UTF-8");
		CourseFile courseFile = new CourseFile();
			courseFile.setCourseId(courseId);
			courseFile.setCourseType(courseType);
			courseFile.setFileName(fileName);
			courseFile.setStatus("1");
		String userId = UserUtils.getUser().toString();
		Course course = new Course();
			course.setUserId(userId);
		List courseList = courseManageService.queryStudentCourse(course);
		List<CourseFile> cfList  = courseFileService.courseFileListByCourseId(courseFile);
		request.setAttribute("pageInfo",new PageInfo<CourseFile>(cfList));
		request.setAttribute("courseList", courseList);
		request.setAttribute("courseId", courseId);
		request.setAttribute("courseType", courseType);
		request.setAttribute("fileName", fileName);
    	return "modules/sys/student/course/student_mycourse_file";
    }
	
	@RequestMapping(value="${adminPath}/student/myStudyPlanPage")
	public String myStudyPlanInit(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String planName = request.getParameter("planName")==null?"":request.getParameter("planName");
		//planName= new String(planName.getBytes("ISO8859_1"),"UTF-8");
		String startDate = request.getParameter("startDate")==null?"":request.getParameter("startDate");
		String userId = UserUtils.getUser().toString();
		StudyPlan sp= new StudyPlan();
			sp.setTitle(planName);
			sp.setStartTime(startDate);
			sp.setUserid(userId);
		StudyPlanService sps = new StudyPlanService();
		List<StudyPlan> spList = sps.fetchStudyPlanByUserIdPage(sp);
		request.setAttribute("myStudyPlanList", spList);
		request.setAttribute("pageInfo", new PageInfo<StudyPlan>(spList));
		return "modules/sys/student/owncenter/showMyStudyPlan";	
	}
	
	@RequestMapping(value="course/deletePlan")
	public String deletePlan(HttpServletRequest request,HttpServletResponse response){
			String planId = request.getParameter("planId")==null?"":request.getParameter("planId");
			String ids[] = planId.split(",");
			String userId = UserUtils.getUser().toString();
			StudyPlan plan = new StudyPlan();
				plan.setUserid(userId);
			int ret = 0;
			try {
				for(int i=0; i<ids.length; i++){
					plan.setId(Integer.parseInt(ids[i]));
					ret = StudyPlanService.deletePlan(plan);
				}
				if(ret>0){
					return renderString(response, "success");
				}else{
					return renderString(response, "error");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return renderString(response, "error");
			}
	}
	
	//学生新建学习计划页面初始化
	@RequestMapping(value="${adminPath}/student/createStudyPlan")
	public String createStudyPlan(HttpServletRequest request,HttpServletResponse response){
		String userId = UserUtils.getUser().toString();
		User user =UserUtils.getUserByUserId(userId);
		String currentDate= DateUtils.getCurrDate("yyyy-M-dd");
		StudyPlanService sps = new StudyPlanService();
		List<StudyPlan> splist = sps.fetchStudyPlanByUserId(userId);
		request.setAttribute("splist", splist);
		request.setAttribute("currentDate", currentDate);
		request.setAttribute("User", user);
		return "modules/sys/student/owncenter/studentCreateStudyPlan";
	}

	@RequestMapping(value="student/saveNewStudyPlan")
	public String createStudyPlanDetail(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String title =request.getParameter("title");
		String startDate =  request.getParameter("startDate");
		String type = request.getParameter("type");
		String userId = UserUtils.getUser().toString();
		StudyPlan sp= new StudyPlan();
			sp.setStartTime(startDate);
			sp.setUserid(userId);
		StudyPlanService sps = new StudyPlanService();
		try {
			List<StudyPlan> spList = sps.fetchStudyPlanByUserIdPage(sp);
			if(spList.size()!=0){
				return renderString(response, "exist");
			}else{
				sp.setUserid(userId);
				sp.setTitle(title);
				sp.setType(type);
				sp.setStartTime(startDate);
				sp.setEndTime(DateUtils.dateToStr(DateUtils.getDateAfter(DateUtils.strToDate(startDate),6)));
				sp.setCreatetime(DateUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			}
			StudyPlanService.insertMyStudyPlan(sp);			//计划基本信息保存
			int planId = sp.getId();
			List<TimeBean> daylist = WeekdayUtils.getDateList(DateUtils.strToDate(startDate));
			StudyPlanDetailService.createMyStudyPlanDetailWeekly(sp,daylist);
			return renderString(response, planId);
		}catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	//学生更新学习计划学习计划,提交确认按钮更新
	@RequestMapping(value="student/updateStudyPlanDetail")
	public String updateStudyPlanDetail(HttpServletRequest request,HttpServletResponse response){
		String planDetailId = request.getParameter("planDetailId");
		String courseId = request.getParameter("courseId");
		String courseName = request.getParameter("courseName");
		String courseNum = request.getParameter("courseNum");
		String testNum = request.getParameter("testNum");
		String askQuestionNum = request.getParameter("askQuestionNum");
		try {
			StudyPlanDetail studyPlanDetail = new StudyPlanDetail();
				studyPlanDetail.setId(Integer.parseInt(planDetailId));
				studyPlanDetail.setCourseList(courseId);
				studyPlanDetail.setCourseNameList(courseName);
				studyPlanDetail.setCourseCount(Integer.parseInt(courseNum));
				studyPlanDetail.setTestCount(Integer.parseInt(testNum));
				studyPlanDetail.setAskCount(Integer.parseInt(askQuestionNum));
				studyPlanDetail.setUpdateTime(DateUtils.getDateTime());
			StudyPlanDetailService spds = new StudyPlanDetailService();
			spds.updateStudyPlanDetail(studyPlanDetail);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
		return renderString(response, "success");
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="${adminPath}/student/editStudyPlanDetail")
	public String editStudyPlanDetail(HttpServletRequest request,HttpServletResponse response){
		String userId = UserUtils.getUser().toString();
		String tempStr = request.getParameter("planId");
		int planId = Integer.parseInt(tempStr);
		//获取该计划
		StudyPlan studyPlan = studyPlanService.fetchMyStudyPlanById(planId);
		//获取该计划Id对应计划详情列表		
		List<StudyPlanDetail> planDetailList = studyPlanDetailService.fetchStudyPlanDetailByPlanId(planId);
		List<StudyPlanDetail> planDetailList1 = new ArrayList<StudyPlanDetail>();
		CourseStudyLog log = new CourseStudyLog();
		log.setUserId(userId);
		for(int i=0; i<planDetailList.size(); i++){
			int totalTimeLong=0;
			StudyPlanDetail studyPlanDetail = planDetailList.get(i);
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
				studyPlanDetail.setStudyTimeLong(totalTimeLong);	//当天实际观看视频总时长
				String testNum = CourseStudylogService.queryTestQuestionNum(log);		//当天实际组卷自测题数
				String askQuestionNum = CourseStudylogService.queryAskQuestionNum(log);
				studyPlanDetail.setTestQuestionNum(Integer.parseInt(testNum));
				studyPlanDetail.setAskQuestionNum(Integer.parseInt(askQuestionNum));
				planDetailList1.add(studyPlanDetail);
			}else{
				planDetailList1.add(studyPlanDetail);
			}
			//----组卷自测题数
			
		}
		//获取用可有权使用的课程资源列表
		List courseList = courseManageService.queryStudentCourse(new Course(userId));
		request.setAttribute("StudyPlan", studyPlan);
		request.setAttribute("planDetailList", planDetailList1);
		request.setAttribute("courseList", courseList);
		request.setAttribute("planId", planId);
		
		//判断当前日期是否大于计划开始日期，如果大于开始日期，不让修改计划详情，只显示，如果小于，可以修改
		Date currentDay = DateUtils.getCurrentDate("yyyy-MM-dd");
		request.setAttribute("currentDay", DateUtils.dateToStr(currentDay));
		return "modules/sys/student/owncenter/studentCreateStudyPlanDetail";
	}
		
	/**
	 * 计算课程所有微课视频的总时长
	 */
	@RequestMapping(value="student/courseVideoTimeLong")
	public String courseVideoTimeLong(HttpServletRequest request,HttpServletResponse response){
		String courseId = request.getParameter("courseId");
		String courseList[] = courseId.split(",");
		CourseFile fileDetail = new CourseFile();
			fileDetail.setCourseType("1"); 		//微课
			fileDetail.setStatus("1");
		int totalTimeLong=0;
		try {
			for(int i=0; i<courseList.length;i++){
					fileDetail.setCourseId(courseList[i]);
				List<CourseFile> file = courseFileService.courseFileListByCourseId(fileDetail);
				if(!file.isEmpty()){
					for(int j=0; j<file.size(); j++){
						fileDetail = file.get(j);
						totalTimeLong +=  Integer.parseInt(fileDetail.getVideoTimeLong());
					}
				}
			}
			return renderString(response,totalTimeLong);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
}
