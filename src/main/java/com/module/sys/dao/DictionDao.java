package com.module.sys.dao;

import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.sys.entity.Dictionary;

@MyBatisDao
public interface DictionDao extends  CrudDao<Dictionary>{
	
	public  List<Dictionary> findDicList(Dictionary dictionary);
	
	public Dictionary findDepartList(Dictionary dictionary);
	
	public List<Dictionary> findMaxRecordByCode(Dictionary dictionary);

	public int  insertDictRecord(Dictionary dictionary);
	public  List<Dictionary> findAllListByCode(Dictionary dictionary);
}
