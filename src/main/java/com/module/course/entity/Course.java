package com.module.course.entity;

import java.sql.Timestamp;

import com.common.persistence.DataEntity;
/**
 * @Description 课程实体类
 * @Author yuanzhonglin
 * @Version 1.0
 * @Date 2016年7月12日
 */
public class Course extends DataEntity<Course> {

	private static final long serialVersionUID = 1L;
	private String courseId; // 课程Id
	private String userId; // 用户Id
	private String courseName; // 课程名称
	private String teacherId; // 主讲教师
	private String teacherName; // 主讲教师姓名
	private String courseType; // 课程类型
	private String introduce; //课程简介
	private String timelong;  //时长
	private String playTime; //播放时长
	private String imgpath; // 图片url
	private Timestamp createTime; // 课程添加时间
	private String status; // 状态
	private String fileName; // 文件名称
	private String isCommon;  //是否是公共资源 1：是 0:不是
	private String name;//知识点批量文件名称
	
	public Course(){
	}
	public Course(String userId){
		this.userId = userId;
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
	public String getTimelong() {
		return timelong;
	}
	public void setTimelong(String timelong) {
		this.timelong = timelong;
	}
	public String getPlayTime() {
		return playTime;
	}
	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}
	public String getImgpath() {
		return imgpath;
	}
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
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
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getIsCommon() {
		return isCommon;
	}
	public void setIsCommon(String isCommon) {
		this.isCommon = isCommon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
