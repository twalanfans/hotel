package com.module.sys.entity;

import java.sql.Timestamp;

import com.common.persistence.DataEntity;

/**
 * 系统公告实体类
 * @author yuanzhonglin
 * @date 2016-7-11
 */
public class Notice extends DataEntity<Department>{
	
	private static final long serialVersionUID = 1L;
	private String noticeId;  //  公告ID
	private String type;  //  公告类型
	private String target;  //  目标对象 1：学生  2：老师  3：审核员  4：超管
	private String title;  //	公告标题
	private String notices;  //	公告内容
	private String pubUser;  //	发布人 
	private Timestamp pubTime;  //	发布时间
	private String status;  //	公告状态 
	private String filePath;  //	公告附件地址 
	private String fileName;  //	附件名称
	private String startTime;
	private String endTime;
	private String userId;
	
	public Notice(){
		super();
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNotices() {
		return notices;
	}

	public void setNotices(String notices) {
		this.notices = notices;
	}

	public String getPubUser() {
		return pubUser;
	}

	public void setPubUser(String pubUser) {
		this.pubUser = pubUser;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Timestamp getPubTime() {
		return pubTime;
	}
	public void setPubTime(Timestamp pubTime) {
		this.pubTime = pubTime;
	}
	public String getStatus() {
		return status;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return title;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
}
