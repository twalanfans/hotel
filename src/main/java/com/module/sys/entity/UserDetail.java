package com.module.sys.entity;

import java.sql.Timestamp;

import com.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
* @Description  
* @author  yuanzhonglin
* @CreateTime  2016年6月26日 
*/
public class UserDetail extends DataEntity<UserDetail> {
	
	private static final long serialVersionUID = 1L;
	private String userId;  //  用户ID
	private String roleId; //角色Id
	private String userName;  //  真实姓名 
	private String sex;  //  性别
	private String loginName; //登陆账号
	private String phone;  //  电话
	private String email;  //  邮箱
	private String birthday;  //  出生年月
	private String photo;  //  头像路径
	private String schoolId;
	private String collegeName;  //  所在学校
	private String departName;  //  所在系
	private String professionName;  //  所在专业
	private String gradeName;   //  所属班级
	private String address;  //  家庭地址
	private String postCode;  //  邮编
	private String userType; //用户类型 1：学生。2：老师 0：审核员
	private String lastLoginTime;
	private String status; //用户状态
	private Timestamp createTime; 
	private Timestamp updateTime; 
	private String remark;  
	
	
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public UserDetail(){
		super();
	}
	public UserDetail(String userId){
		super();
		this.userId=userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public String getProfessionName() {
		return professionName;
	}
	public void setProfessionName(String professionName) {
		this.professionName = professionName;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
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
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
}
