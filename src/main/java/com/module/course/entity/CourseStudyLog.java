package com.module.course.entity;

import com.common.persistence.DataEntity;

/**
 * 学习日志实体类entity
 * @author yuanzhonglin
 * @date 2016-7-13
 */
public class CourseStudyLog extends DataEntity<CourseStudyLog>{
	
		private static final long serialVersionUID = 1L;
		private String logId;  //  日志Id
		private String courseId;  //  课程Id
		private String courseFileId;  //  课程Id
		private String userId;  //  用户Id
		private String studyProgress;  //  学习进度
		private String logTime;  //  记录日志时间
		private String logIp;  //  用户Ip
		private int studyTimeLong;  //  学习时长
		private String videoPlayTime;
		
		public CourseStudyLog() {
			
		}
		public String getCourseFileId() {
			return courseFileId;
		}
		public void setCourseFileId(String courseFileId) {
			this.courseFileId = courseFileId;
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
		public String getLogTime() {
			return logTime;
		}
		public void setLogTime(String logTime) {
			this.logTime = logTime;
		}
		public String getLogIp() {
			return logIp;
		}
		public void setLogIp(String logIp) {
			this.logIp = logIp;
		}
		public int getStudyTimeLong() {
			return studyTimeLong;
		}
		public void setStudyTimeLong(int studyTimeLong) {
			this.studyTimeLong = studyTimeLong;
		}
		public String getVideoPlayTime() {
			return videoPlayTime;
		}
		public void setVideoPlayTime(String videoPlayTime) {
			this.videoPlayTime = videoPlayTime;
		}
}
