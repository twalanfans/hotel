package com.module.course.entity;

import java.util.Date;

import com.common.persistence.DataEntity;
import com.module.sys.entity.Message;

/**
 * 学习日志实体类entity
 * @author yuanzhonglin
 * @date 2016-7-13
 */
public class StudyLog extends DataEntity<Message>{
	
		private static final long serialVersionUID = 1L;
		private String logId;  //  日志Id
		private String courseId;  //  课程Id
		private String userId;  //  用户Id
		private String studyProgress;  //  学习进度
		private Date logTime;  //  记录日志时间
		private String logIp;  //  用户Ip
		private String studyTimeLong;  //  学习时长
		
		public StudyLog(String userId){
			super();
			this.userId = userId;
		}
		
		public StudyLog() {
			super();
		}

		public String getLogId() {
			return logId;
		}
		public void setLogId(String logId) {
			this.logId = logId;
		}
		public String getCourseId() {
			return courseId;
		}
		public void setCourseId(String courseId) {
			this.courseId = courseId;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getStudyProgress() {
			return studyProgress;
		}
		public void setStudyProgress(String studyProgress) {
			this.studyProgress = studyProgress;
		}
		public Date getLogTime() {
			return logTime;
		}
		public void setLogTime(Date date) {
			this.logTime = date;
		}
		public String getLogIp() {
			return logIp;
		}
		public void setLogIp(String logIp) {
			this.logIp = logIp;
		}
		public String getStudyTimeLong() {
			return studyTimeLong;
		}
		public void setStudyTimeLong(String studyTimeLong) {
			this.studyTimeLong = studyTimeLong;
		}
}
