/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/hotelsimusys/hotelsimusys">Hotelsimusys</a> All rights reserved.
 */
package com.module.sys.entity;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.common.persistence.DataEntity;

/**
 * 菜单Entity
 * @author yuanzhonglin
 * @version 2016-05-15
 */
public class Menu extends DataEntity<Menu> {

	private static final long serialVersionUID = 1L;
	private String parentId;	// 父级菜单
	private String name; 	// 名称 
	private Integer sort; 	// 排序
	private String href; 	// 链接
	private String permission; 	// 权限标识
	private String isShow; 	// 是否在菜单中显示（1：显示；0：不显示）
	private String remark; //菜单备注
	private String userId;
	
	public Menu(){
		super();
	}
	
	public Menu(String id){
		super(id);
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemarks(String remarks) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Length(min=1, max=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=0, max=2000)
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

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@NotNull
	public Integer getSort() {
		return sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Length(min=1, max=1)
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return name;
	}
}