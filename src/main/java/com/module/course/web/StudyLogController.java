package com.module.course.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.common.utils.DateUtils;
import com.common.utils.IpTool;
import com.common.web.BaseController;
import com.module.course.entity.CourseStudyLog;
import com.module.course.service.CourseFileService;
import com.module.course.service.CourseStudylogService;
import com.module.sys.utils.UserUtils;

/**
 * @Description  学习管理日志Controller
 * @Author  yuanzhonglin
 * @Date  2016年7月13日 
 */
@Controller
public class StudyLogController extends BaseController{
	
	@Autowired
	private CourseStudylogService courseStudylogService;
	
	@Autowired
	private CourseFileService courseFileService;
	
	/**
	 * 学生视频学习日志
	 * yuanzhonglin
	 * 2016-8-31
	 */
	@RequestMapping(value="student/courseVideoStudyLog")
	public String courseStudyLog(HttpServletRequest request,HttpServletResponse response){
		String logId = request.getParameter("logId")==null?"":request.getParameter("logId");
		String courseId = request.getParameter("courseId")==null?"":request.getParameter("courseId");
		String fileId = request.getParameter("fileId")==null?"":request.getParameter("fileId");
		String videoPlayTime = request.getParameter("videoPlayTime")==null?"":request.getParameter("videoPlayTime");
		String st= request.getParameter("studyTimelong");
		int timeS = Integer.parseInt(st);
		int timeMin = timeS/60;		//将秒转为分钟
		if(timeS%60>=30){		//四舍五入
			timeMin = timeMin+1;
		}
		String userId = UserUtils.getUser().toString();
		String logip =IpTool.getRemoteAddr(request);
		CourseStudyLog studyLog = new CourseStudyLog();
			studyLog.setCourseId(courseId);
			studyLog.setCourseFileId(fileId);
			studyLog.setVideoPlayTime(videoPlayTime);
			studyLog.setStudyTimeLong(timeMin);		
			studyLog.setLogTime(DateUtils.getDateTime());
			studyLog.setUserId(userId);
			studyLog.setLogIp(logip);
		try {
			if(logId.equals("")){
				logId = courseStudylogService.getLogId(studyLog);
				if(logId==null){
					courseStudylogService.saveCourseStudylog(studyLog);
					logId = studyLog.getLogId();
				}
			}else{
				studyLog.setLogId(logId);
				courseStudylogService.updateCourseStudylog(studyLog);
			}
		}catch(Exception e) {
				e.printStackTrace();
				return renderString(response, "error");
		}
		return renderString(response, logId);
	}
	
	
	
}
