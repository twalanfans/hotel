package com.module.course.entity;

import com.common.persistence.DataEntity;

/**
 * 课程文件实体类Entity
 * @author yuanzhonglin
 * @date 2016-7-15
 */
public class CourseFile extends DataEntity<CourseFile>{
	
	private static final long serialVersionUID = 1L;
	private String courseId;
	private String courseName;
	private String userId;
	private String fileId; // 文件Id
	private String fileName; // 文件名
	private String fileType; // 文件类型
	private String courseType; //课程类型
	private String videoImg; // 视频图片
	private String videoIntroduce; // 视频简介
	private String videoTimeLong; // 视频时长
	private String fileSize; // 文件大小
	private String status; // 状态
	private Object createTime; // 创建时间
	private String filePath; // 文件路径
	private String logId;
	
	public CourseFile(){
	}
	public CourseFile(String courseType){
		this.courseType = courseType;
	}
    public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getVideoTimeLong() {
		return videoTimeLong;
	}
	public void setVideoTimeLong(String videoTimeLong) {
		this.videoTimeLong = videoTimeLong;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getCourseType() {
        return courseType;
    }
    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }
    public String getVideoImg() {
        return videoImg;
    }
    public void setVideoImg(String videoImg) {
        this.videoImg = videoImg;
    }
    public String getVideoIntroduce(){
        return videoIntroduce;
    }
    public void setVideoIntroduce(String videoIntroduce) {
        this.videoIntroduce = videoIntroduce;
    }
    public String getFileSize() {
        return fileSize;
    }
    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
    public String getStatus() {
        return status;
    }
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public Object getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Object createTime) {
		this.createTime = createTime;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
