/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/hotelsimusys/hotelsimusys">Hotelsimusys</a> All rights reserved.
 */
package com.module.sys.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import com.common.persistence.DataEntity;
import com.module.sys.utils.UserUtils;

/**
 * 机构部门Entity
 * @author yuanzhonglin
 * @date 2016-6-21
 */
public class Department extends DataEntity<Department>{

	private static final long serialVersionUID = 1L;
	private String departId;  //  部门Id
	private String parentId;  //  父部门ID
	private String departName;  //  部门名称
	private String level;  //机构等级  
	private String departNum; //系数量
	private String proNum; //专业数量
	private String gradeNum; //班级数量
	private String remark;  //  备注
	private String createUser;	// 创建者
	private String createTime;	// 创建日期
	private String updateUser;	// 更新者
	private String updateTime;	// 更新日期
	
	public Department(){
		super();
	}

	public String getDepartId() {
		return departId;
	}

	public void setDepartId(String departId) {
		this.departId = departId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getDepartNum() {
		return departNum;
	}

	public void setDepartNum(String departNum) {
		this.departNum = departNum;
	}

	public String getProNum() {
		return proNum;
	}

	public void setProNum(String proNum) {
		this.proNum = proNum;
	}

	public String getGradeNum() {
		return gradeNum;
	}

	public void setGradeNum(String gradeNum) {
		this.gradeNum = gradeNum;
	}

	@Override
	public String toString() {
		return departName;
	}

}