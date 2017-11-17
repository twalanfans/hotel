package com.module.testonline.entity;

import java.sql.Timestamp;

import com.common.persistence.DataEntity;
/**
 * @Description 测试试题实体类
 * @Author yuanzhonglin
 * @Date 2016年9月7日
 */
public class TestPaper extends DataEntity<TestPaper> {

	private static final long serialVersionUID = 1L;
	private String noteId; 		//答题详情主键
	private String paperId;   //试卷Id
	private String questionId;   //试题Id
	private String paperName;		//试卷名称 
	private String paperScore;		//试卷总分
	private String courseId;   
	private String courseName;   
	private String questionTotal;    //试卷总题数
	private String questionScore;    
	private String userAnswer;		//学生答案    
	private String standarAnswer;	
	private String answerScore;		//学生试题得分    
	private String testTimeLong;  
	private String paperType; 
	private String knowledgeList;   
	private String discription;   
	private String createUser;	
	private Timestamp createTime; 	
	private String questionTitle; 	
	private String questionFilePath; 	
	private String answerFilePath; 	
	private String selectNum; 	
	private String questionType; 	
	private String questionClass; 	
	private String studentId; 	
	private String studentName; 	
	private String teacherId; 	
	private String status; 	
	private String startTime;
	private String endTime;
	private String isAddNotes; 	//是否加入错题集
	
	public TestPaper(){
	}

	public String getNoteId() {
		return noteId;
	}
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	public String getPaperId() {
		return paperId;
	}

	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public String getStandarAnswer() {
		return standarAnswer;
	}

	public void setStandarAnswer(String standarAnswer) {
		this.standarAnswer = standarAnswer;
	}

	public String getAnswerScore() {
		return answerScore;
	}

	public void setAnswerScore(String answerScore) {
		this.answerScore = answerScore;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getPaperName() {
		return paperName;
	}

	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getQuestionScore() {
		return questionScore;
	}

	public void setQuestionScore(String questionScore) {
		this.questionScore = questionScore;
	}

	public String getQuestionTotal() {
		return questionTotal;
	}

	public void setQuestionTotal(String questionTotal) {
		this.questionTotal = questionTotal;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getQuestionFilePath() {
		return questionFilePath;
	}

	public void setQuestionFilePath(String questionFilePath) {
		this.questionFilePath = questionFilePath;
	}

	public String getTestTimeLong() {
		return testTimeLong;
	}

	public void setTestTimeLong(String testTimeLong) {
		this.testTimeLong = testTimeLong;
	}

	public String getPaperType() {
		return paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
	}

	public String getKnowledgeList() {
		return knowledgeList;
	}

	public void setKnowledgeList(String knowledgeList) {
		this.knowledgeList = knowledgeList;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getAnswerFilePath() {
		return answerFilePath;
	}

	public void setAnswerFilePath(String answerFilePath) {
		this.answerFilePath = answerFilePath;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getSelectNum() {
		return selectNum;
	}
	public void setSelectNum(String selectNum) {
		this.selectNum = selectNum;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
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

	public String getPaperScore() {
		return paperScore;
	}

	public void setPaperScore(String paperScore) {
		this.paperScore = paperScore;
	}

	public String getQuestionClass() {
		return questionClass;
	}

	public void setQuestionClass(String questionClass) {
		this.questionClass = questionClass;
	}

	public String getIsAddNotes() {
		return isAddNotes;
	}

	public void setIsAddNotes(String isAddNotes) {
		this.isAddNotes = isAddNotes;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	
	
}