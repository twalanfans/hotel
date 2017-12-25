package com.module.code.entity;

import java.sql.Timestamp;

import com.common.persistence.DataEntity;
import com.common.persistence.annotation.Comment;
/**
 * @Description 测试试题实体类
 * @Author yuanzhonglin
 * @Date 2016年9月7日
 */
public class KeyCode extends DataEntity<KeyCode> {

	@Comment("")
	private static final long serialVersionUID = 1L;
	@Comment("id")
	private String keyId; 
	@Comment("剩余使用次数")
	private int useNum; 
	@Comment("学校Id")
	private String schoolId; 
	@Comment("学校名称")
	private String schoolName; 
	@Comment("路径")
	private String qrCodePath; 
	@Comment("状态")
	private String status; 
	@Comment("生成时间")
	private Timestamp createTime; 
	@Comment("第一次登陆时间")
	private Timestamp firstLogin; 
	@Comment("是否是测试版激活码")
	private String isCeshi;
	
	
	
	public Timestamp getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(Timestamp firstLogin) {
		this.firstLogin = firstLogin;
	}

	public String getIsCeshi() {
		return isCeshi;
	}

	public void setIsCeshi(String isCeshi) {
		this.isCeshi = isCeshi;
	}

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
