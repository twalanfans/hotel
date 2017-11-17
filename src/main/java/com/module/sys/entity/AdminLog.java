package com.module.sys.entity;

import com.common.persistence.DataEntity;

/**
 * @Description  
 * @Author  yuanzhonglin
 * @Date  2016年7月13日 
 */
public class AdminLog extends DataEntity<AdminLog>{

	private static final long serialVersionUID = 1L;
	
	private String id;  //  日志主键Id,数据库表是int类型
	private String logController;  //  对应控制器的名字
	private String logAction;  //  控制器里的方法
	private String logData;  //  日志数据
	private String logOpType;  //  操作类型
	private String logOpTime;  //  操作时间
	private String logOpuser;  //  操作用户Id
	private String logIp;  //  用户发送日志的Ip地址
	
	public AdminLog(){
		super();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogController() {
		return logController;
	}
	public void setLogController(String logController) {
		this.logController = logController;
	}
	public String getLogAction() {
		return logAction;
	}
	public void setLogAction(String logAction) {
		this.logAction = logAction;
	}
	public String getLogData() {
		return logData;
	}
	public void setLogData(String logData) {
		this.logData = logData;
	}
	public String getLogOpType() {
		return logOpType;
	}
	public void setLogOpType(String logOpType) {
		this.logOpType = logOpType;
	}
	public String getLogOpTime() {
		return logOpTime;
	}
	public void setLogOpTime(String logOpTime) {
		this.logOpTime = logOpTime;
	}
	public String getLogOpuser() {
		return logOpuser;
	}
	public void setLogOpuser(String logOpuser) {
		this.logOpuser = logOpuser;
	}
	public String getLogIp() {
		return logIp;
	}
	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}
	@Override
	public String toString() {
		return "AdminLog [id=" + id + ", logController=" + logController
				+ ", logAction=" + logAction + ", logData=" + logData
				+ ", logOpType=" + logOpType + ", logOpTime=" + logOpTime
				+ ", logOpuser=" + logOpuser + ", logIp=" + logIp + "]";
	}
	
}
