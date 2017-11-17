package com.module.course.entity;

import java.util.Date;

import com.common.persistence.DataEntity;

public class DataAnalysis extends DataEntity<DataAnalysis>{

	private static final long serialVersionUID = 1L;
	private String dataType;
	private String userId;
    private int studyTimeLong;
    private int testQuestionNum;
    private int askQuestionNum;	//课程提问数
    private int testPaperTime;	//组卷自测用时
    private Date startTime;	
    private Date endTime;	
	
	public DataAnalysis() {
	}

	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getStudyTimeLong() {
		return studyTimeLong;
	}
	public void setStudyTimeLong(int studyTimeLong) {
		this.studyTimeLong = studyTimeLong;
	}
	public int getTestQuestionNum() {
		return testQuestionNum;
	}
	public void setTestQuestionNum(int testQuestionNum) {
		this.testQuestionNum = testQuestionNum;
	}
	public int getAskQuestionNum() {
		return askQuestionNum;
	}
	public void setAskQuestionNum(int askQuestionNum) {
		this.askQuestionNum = askQuestionNum;
	}
	public int getTestPaperTime() {
		return testPaperTime;
	}
	public void setTestPaperTime(int testPaperTime) {
		this.testPaperTime = testPaperTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}