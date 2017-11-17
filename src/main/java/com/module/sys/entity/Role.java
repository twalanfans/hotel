package com.module.sys.entity;

import com.common.persistence.DataEntity;

/**
 * 角色Entity
 * @author yuanzhonglin
 * @date  2016年7月9日
 */
public class Role extends DataEntity<Role> {
	
	private static final long serialVersionUID = 1L;
	private String roleId;  //  角色ID
	private String userId; // 用户Id
	private String resourceId; //资源Id
	private String menuId;  //  菜单Id
	private String name; 	// 角色名称
	private String remark;  //角色备注
	private String createUser; // 创建者
	private String createTime; // 创建时间
	private String updateUser; // 更新者
	private String updateTime; // 更新时间
	private String status; 
	
	public Role() {
		super();
	}
	public Role(String id) {
		super(id);
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
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
