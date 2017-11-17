package com.module.sys.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.common.persistence.DataEntity;

/**
 * 用户Entity
 * @author hotelsimusys
 * @version 2016-06-05
 */
public class User extends DataEntity<User> {

	private static final long serialVersionUID = 1L;
	private String userId;	//用户ID
	private String loginName;// 登录名
	private String password;// 密码
	private String userType;// 用户类型
	private String departId;	// 所属部门id
	private String lastLoginIp;	// 最后登陆IP
	private String lastLoginTime;	// 最后登陆日期
	private String status;
	private Timestamp createTime;
	private String roleId;
	private String schoolId;
	private String schoolName;
	public User() {
		super();
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
	
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
	public User(String userId, String loginName){
		super(userId);
		this.loginName = loginName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLoginName() {
		return loginName;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getLoginIp() {
		return lastLoginIp;
	}
	public void setLoginIp(String loginIp) {
		this.lastLoginIp = loginIp;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	@Override
	public String toString() {
		return userId;
	}
	public String getDepartId() {
		return departId;
	}
	public void setDepartId(String depatId) {
		this.departId = depatId;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}