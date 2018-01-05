package com.module.version.entity;

import com.common.persistence.DataEntity;

public class Version extends DataEntity<Version>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//id做平台控制
	private String id;
	//版本
	private String version;
	//路劲
	private String path;
	//更新内容
	private String content;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Version(String id, String version, String path, String content) {
		super();
		this.id = id;
		this.version = version;
		this.path = path;
		this.content = content;
	}
	public Version() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public Version(String id) {
		super(id);
		// TODO 自动生成的构造函数存根
	}
	@Override
	public String toString() {
		return "Version [id=" + id + ", version=" + version + ", path=" + path
				+ ", content=" + content + "]";
	}
	
	
	
	
	
}
