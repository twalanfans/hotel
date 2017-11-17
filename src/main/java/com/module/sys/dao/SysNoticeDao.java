package com.module.sys.dao;

import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.sys.entity.Notice;

@MyBatisDao
public interface SysNoticeDao extends CrudDao<Notice>{
	
	public List<Notice> getNoticeDetail(Notice notice);
	
	public int updateNoticeStatus();
	
	public List<Notice> userNotice(String userId);
	
}
