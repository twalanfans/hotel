package com.module.testonline.entity;

import java.sql.Timestamp;

import com.common.persistence.DataEntity;
/**
 * @Description 测试试题实体类
 * @Author yuanzhonglin
 * @Date 2016年9月7日
 */
public class TestQuestion extends DataEntity<TestQuestion> {

	private static final long serialVersionUID = 1L;
	private String testId;   //试题主键Id
	private String questionTitle; 
	private String questionClass;   //主观。客观题 
	private String type;    //单选、多选
	private String selectNum;  //选项数量
	private String questionFilePath;   //问题图片地址 
	private String courseId;   //课程Id
	private String courseName;   //课程名称 
	private String answerContent;   //答案内容
	private String knowledgeId;   //知识点
	private String status;		//状态 
	private String answerRemark;		//答案备注
	private Timestamp createTime; 	//创建时间 
	private String createUser; 	//创建人
	private String updateTime; 	//更新时间 
	
	public TestQuestion(){
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getAnswerRemark() {
		return answerRemark;
	}

	public void setAnswerRemark(String answerRemark) {
		this.answerRemark = answerRemark;
	}

	public String getKnowledgeId() {
		return knowledgeId;
	}

	public void setKnowledgeId(String knowledgeId) {
		this.knowledgeId = knowledgeId;
	}

	public String getQuestionClass() {
		return questionClass;
	}

	public void setQuestionClass(String questionClass) {
		this.questionClass = questionClass;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSelectNum() {
		return selectNum;
	}

	public void setSelectNum(String selectNum) {
		this.selectNum = selectNum;
	}

	public String getQuestionFilePath() {
		return questionFilePath;
	}

	public void setQuestionFilePath(String questionFilePath) {
		this.questionFilePath = questionFilePath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
