package com.module.sys.entity;

import java.util.Date;
import com.common.persistence.DataEntity;

/**
 * @author yuanzhonglin
 * @date 2016-6-30
 * @数据字典类
 */
@SuppressWarnings("serial")
public class Dictionary extends DataEntity<Dictionary> {
		private int dictId;
		
		private String codeId;
		
		private String value;

	    private String label;

	    private String status;
	    
	    private String dictDescription;

	    private Integer dictIndex;

	    private String createByUser;

	    private Date createTime;

	    private String modifyBy;

	    private Date modifyTime;

	    public int getDictId() {
	        return dictId;
	    }

	    public void setDictId(int dictId) {
	        this.dictId = dictId;
	    }

	    public String getCodeId() {
	        return codeId;
	    }

	    public void setCodeId(String codeId) {
	        this.codeId = codeId == null ? null : codeId.trim();
	    }

	    public String getValue() {
	        return value;
	    }

	    public void setValue(String value) {
	        this.value = value == null ? null : value.trim();
	    }

	    public String getLabel() {
	        return label;
	    }

	    public void setLabel(String label) {
	        this.label = label == null ? null : label.trim();
	    }

	    public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status == null ? null : status.trim();
	    }
	    public String getDictDescription() {
	        return dictDescription;
	    }

	    public void setDictDescription(String dictDescription) {
	        this.dictDescription = dictDescription == null ? null : dictDescription.trim();
	    }
	    public Integer getDictIndex() {
	        return dictIndex;
	    }

	    public void setDictIndex(Integer dictIndex) {
	        this.dictIndex = dictIndex;
	    }

	    public String getCreateByUser() {
	        return createByUser;
	    }

	    public void setCreateByUser(String createByUser) {
	        this.createByUser = createByUser == null ? null : createByUser.trim();
	    }
		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public String getModifyBy() {
	        return modifyBy;
	    }

	    public void setModifyBy(String modifyBy) {
	        this.modifyBy = modifyBy == null ? null : modifyBy.trim();
	    }

	    public Date getModifyTime() {
	        return modifyTime;
	    }

	    public void setModifyTime(Date modifyTime) {
	        this.modifyTime = modifyTime;
	    }
}