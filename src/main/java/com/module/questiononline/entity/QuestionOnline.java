package com.module.questiononline.entity;

import java.sql.Timestamp;

import com.common.persistence.DataEntity;

public class QuestionOnline extends DataEntity<QuestionOnline> {

	private static final long serialVersionUID = 1L;
	private String questionId;   
	private String commentId; 
	private String title; 
	private String content; 
	private String attachImg; 
	private String photo; 
	private String replyId; 
	private int clickNum; 
	private int thinkGood; 
	private Timestamp createTime; 
	private String createUser; 
	private String userId; 
	private String replyUser; 
	
	public QuestionOnline(){
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAttachImg() {
		return attachImg;
	}

	public void setAttachImg(String attachImg) {
		this.attachImg = attachImg;
	}

	public int getClickNum() {
		return clickNum;
	}

	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getReplyUser() {
		return replyUser;
	}

	public void setReplyUser(String replyUser) {
		this.replyUser = replyUser;
	}

	public int getThinkGood() {
		return thinkGood;
	}

	public void setThinkGood(int thinkGood) {
		this.thinkGood = thinkGood;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReplyId() {
		return replyId;
	}

	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}

	
}
