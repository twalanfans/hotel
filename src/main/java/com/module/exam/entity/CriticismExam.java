package com.module.exam.entity;

import com.common.persistence.DataEntity;

public class CriticismExam extends DataEntity<CriticismExam>{
		private static final long serialVersionUID = 1L;
		private String paperId;
		private String questionId;
		private String questionScore;
		private String courseName;
		private String fileIntroduce;
		private String fileId;
		private String fileSize;
		private String fileName; 
		private String filePath; 
		private String knowledgeId; 
		private String knowledgeTitle; 
		private String paperName; 
		private String testTotalNum; 
		private String createUser; 
		private String createTime; 
		
	public CriticismExam(){
		
	}

	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getQuestionScore() {
		return questionScore;
	}

	public void setQuestionScore(String questionScore) {
		this.questionScore = questionScore;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getFileIntroduce() {
		return fileIntroduce;
	}

	public void setFileIntroduce(String fileIntroduce) {
		this.fileIntroduce = fileIntroduce;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public String getKnowledgeTitle() {
		return knowledgeTitle;
	}

	public void setKnowledgeTitle(String knowledgeTitle) {
		this.knowledgeTitle = knowledgeTitle;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public String getTestTotalNum() {
		return testTotalNum;
	}

	public void setTestTotalNum(String testTotalNum) {
		this.testTotalNum = testTotalNum;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
