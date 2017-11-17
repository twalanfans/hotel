package com.module.course.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class KnowledgeItem{
	
    private int id;

    private String courseId;

    private int parentId;

    private int pId;
    
    private String name;

    private int level;

    private String title;

    private String filePath;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private String updateTime;

    private String description;
    
    private String parentTitle;
    
    private boolean checked;
    
    public List<KnowledgeItem> children = new ArrayList<KnowledgeItem>();  
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
    public int getPId(){
        return pId;
    }

    public void setPId(int pId) {
        this.pId = pId;
    }
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle == null ? null : parentTitle.trim();
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
    public List<KnowledgeItem> getChildren() {  
            return children;  
        }  
    public void setChildren(List<KnowledgeItem> children) {  
           this.children = children;  
      }
    public boolean getChecked() {  
        return checked;  
    }  
    public void setChecked(boolean checked) {  
       this.checked = checked;  
    }
   @Override  
   
   public String toString() {  
          return "TreeNode [id=" + id + ", pId=" + pId + ", title=" + title  + ", level=" + level 
                   + ", children=" + children + "],checked=" +checked;  
       }  

}