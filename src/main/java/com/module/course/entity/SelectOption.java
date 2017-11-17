package com.module.course.entity;

public class SelectOption {

	private String id;
	
	private String name;
	
	private boolean checked;
	
	private String remark;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override  
	   
	   public String toString() {  
	          return "TreeNode [id=" + id + ", name=" + name  + ", checked=" +checked;  
	       }  
}
