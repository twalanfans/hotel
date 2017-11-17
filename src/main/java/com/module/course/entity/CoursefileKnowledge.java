package com.module.course.entity;

import com.common.persistence.DataEntity;

/**
 * 案例、图片库类别
 */
public class CoursefileKnowledge extends DataEntity<CoursefileKnowledge>{
	private static final long serialVersionUID = 1L;
    private String classId;
    private String parentId;
    private String className;
    private String dataType;
    private String courseId;
    private String level;
    private String createUser;

    public CoursefileKnowledge(){
    	
    }

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getParentId() {
		return parentId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}

}