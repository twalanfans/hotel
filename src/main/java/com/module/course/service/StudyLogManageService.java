package com.module.course.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.noo.pagination.page.PageContext;
import org.noo.pagination.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.module.course.dao.StudyLogDao;
import com.module.course.entity.Course;
import com.module.course.entity.StudyLog;
import com.module.course.entity.StudyPlan;
import com.module.course.util.CourseUtils;
import com.module.course.util.StudyPlanUtils;
import com.module.sys.service.LogService;
import com.module.sys.utils.UserUtils;

/**
 * 学习日志管理服务Service
 * @author yuanzhonglin
 * @date 2016-7-13
 */
@Service
@Transactional(readOnly = true)
public class StudyLogManageService {
	
	@Autowired
	private StudyLogDao studyLogDao;
	
	/**
	 * @学习日志管理服务
	 * @author yuanzhonglin
	 * @date 2016-7-13
	 */
	public List<StudyLog> studyLogByUserId(){
			List<StudyLog> studyLogList =null;
			String userId = UserUtils.getUser().toString();
			try {
				studyLogList = studyLogDao.findList(new StudyLog(userId));
			} catch (Exception e) {
				e.getMessage();
			}
			return studyLogList;
	}

	/**
	 * 创建保存个人学习日志
	 * @author yuanzhonglin
	 * @date 2016-7-13
	 */
	@SuppressWarnings("static-access")
	public void createStudyLog(StudyLog studyLog) {
		HttpServletRequest request =null;
		String userId = UserUtils.getUser().toString(); //获取用户Id
		LogService log = new LogService();
		String logIp = log.getIp2(request);//获取当前Ip地址
		studyLog.setUserId(userId);
		studyLog.setLogTime(new Date());
		studyLog.setLogIp(logIp);
		studyLogDao.insert(studyLog);
		
	}
	
	
	
}
