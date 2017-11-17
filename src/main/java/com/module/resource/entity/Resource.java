package com.module.resource.entity;

import java.sql.Timestamp;

import com.common.persistence.DataEntity;

/**
 * 资源实体类entity
 * @author yuanzhonglin
 * @date 2016-8-4
 */
public  class Resource extends DataEntity<Resource>{
	
	private static final long serialVersionUID = 1L;
	private String resourceId;  //  ID
	private String resourceType;  //  类型
	private String relationId;  // 	关联ID
	private String resourceName; //资源名称
	private String description;  //  描述
	private String href;  // 资源实现路径
	private String permission;  // 权限标识
	private String createUser;  // 创建人
	private Timestamp createTime;  //  创建时间
	private String remark;  //  备注
	private String roleId;
	private String roleName;
	
	public Resource(){
		super();
	}

	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getRelationId() {
		return relationId;
	}
	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
