package com.module.course.entity;

import java.util.Date;


public class StudyPlanDetail {
    private int id;
    private int planId;
    private String courseList;
    private int courseCount;
    private int testCount;
    private int askCount;	//计划提问数
    private String courseNameList;
    private int studyTimeLong;
    private int testQuestionNum;
    private int askQuestionNum;	//实际课程提问数
    private int testPaperTime;	
    private Date planTime;
    private Object updateTime;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPlanId() {
        return planId;
    }
    public void setPlanId(int planId) {
        this.planId = planId;
    }
    public String getCourseList() {
		return courseList;
	}
	public void setCourseList(String courseList) {
		this.courseList = courseList;
	}
	public int getCourseCount() {
		return courseCount;
	}
	public void setCourseCount(int courseCount) {
		this.courseCount = courseCount;
	}
	public int getStudyTimeLong() {
		return studyTimeLong;
	}
	public void setStudyTimeLong(int studyTimeLong) {
		this.studyTimeLong = studyTimeLong;
	}
	public int getTestCount() {
        return testCount;
    }
    public void setTestCount(int testCount) {
        this.testCount = testCount;
    }
    public int getAskCount() {
        return askCount;
    }
    public void setAskCount(int askCount) {
        this.askCount = askCount;
    }
    public Date getPlanTime() {
        return planTime;
    }
    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
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
	public Object getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Object updateTime) {
		this.updateTime = updateTime;
	}
	public String getCourseNameList() {
		return courseNameList;
	}
	public void setCourseNameList(String courseNameList) {
		this.courseNameList = courseNameList;
	}
	public int getTestPaperTime() {
		return testPaperTime;
	}
	public void setTestPaperTime(int testPaperTime) {
		this.testPaperTime = testPaperTime;
	}
	
    
}