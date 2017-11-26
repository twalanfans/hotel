package com.module.owncenter.entity;

import java.util.List;

import com.common.persistence.DataEntity;
import com.module.sys.entity.Department;

/**
 * 字典Entity
 * @author ThinkGem
 * @version 2013-05-15
 */
public class School extends DataEntity<School> {

	private static final long serialVersionUID = 1L;
	private String schoolId;	
	private String schoolName;	
	private String codeNum;	
	private List<Department> department;

	public School() {
	}
	
	public List<Department> getDepartment() {
		return department;
	}


	public void setDepartment(List<Department> department) {
		this.department = department;
	}


	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getCodeNum() {
		return codeNum;
	}

	public void setCodeNum(String codeNum) {
		this.codeNum = codeNum;
	}


	public School(String schoolId, String schoolName, String codeNum,
			List<Department> department) {
		super();
		this.schoolId = schoolId;
		this.schoolName = schoolName;
		this.codeNum = codeNum;
		this.department = department;
	}
	
}