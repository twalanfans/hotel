package com.module.course.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.common.persistence.DataEntity;
import com.module.sys.utils.UserUtils;

public class CourseNotes extends DataEntity<CourseNotes>{
	
		private static final long serialVersionUID = 1L;
		private String noteId; // 课程笔记Id
		private String userId; // 用户Id
		private String courseId; // 课程Id
		private String noteContent; // 课程笔记内容
		private Timestamp updateTime; // 记录笔记时间

		public CourseNotes(){
			super();
		}

		public String getNoteId() {
			return noteId;
		}

		public void setNoteId(String noteId) {
			this.noteId = noteId;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getCourseId() {
			return courseId;
		}

		public void setCourseId(String courseId) {
			this.courseId = courseId;
		}

		public String getNoteContent() {
			return noteContent;
		}

		public void setNoteContent(String noteContent) {
			this.noteContent = noteContent;
		}

		public Timestamp getUpdateTime() {
			return updateTime;
		}

		public void setUpdateTime(Timestamp updateTime) {
			this.updateTime = updateTime;
		}
		
}
