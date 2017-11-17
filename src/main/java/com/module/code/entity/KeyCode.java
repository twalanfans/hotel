package com.module.code.entity;

import java.sql.Timestamp;

import com.common.persistence.DataEntity;
/**
 * @Description 测试试题实体类
 * @Author yuanzhonglin
 * @Date 2016年9月7日
 */
public class KeyCode extends DataEntity<KeyCode> {

	private static final long serialVersionUID = 1L;
	private String keyId;   
	private int useNum; 
	private String schoolId; 
	private String schoolName; 
	private String qrCodePath; 
	private String status; 
	private Timestamp createTime; 
	
	public KeyCode(){
	}

	public String getKeyId() {
		return keyId;
	}
	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}
	public int getUseNum() {
		return useNum;
	}
	public void setUseNum(int useNum) {
		this.useNum = useNum;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getQrCodePath() {
		return qrCodePath;
	}
	public void setQrCodePath(String qrCodePath) {
		this.qrCodePath = qrCodePath;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
}
