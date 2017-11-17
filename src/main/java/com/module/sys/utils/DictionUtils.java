package com.module.sys.utils;

import java.util.List;

import com.common.utils.SpringContextHolder;
import com.module.sys.dao.DictionDao;
import com.module.sys.entity.Dictionary;

/**
 * 字典工具类
 * @author yuanzhonglin
 * @version 2016-6-30
 */
public class DictionUtils {
		
	private static DictionDao dictionDao = SpringContextHolder.getBean(DictionDao.class);

	/**
	 * 根据字典编号查询 key、value值
	 * @param dicCode
	 * @return list集合
	 */
	//根据字典类型获取该类型字典列表
	public static List<Dictionary>  findListByCode(String codeId){
		Dictionary dictionary = new Dictionary();
		dictionary.setCodeId(codeId);
		List<Dictionary> dictlist = dictionDao.findAllListByCode(dictionary); 
		return  dictlist;
	}
	
	/**
	 * 根据字典类型获取最大字典编码对应记录
	 */
	public static List<Dictionary>  findMaxRecordByCode(String codeId){
		Dictionary dictionary = new Dictionary();
		dictionary.setCodeId(codeId);
		//List<Dictionary> departTypelist = dictionDao.findAllList(dictionary); 
		List<Dictionary> dictlist = dictionDao.findMaxRecordByCode(dictionary); 
		return  dictlist;
	}
	/**
	 * 新建一条字典记录 
	 */
	public static Dictionary  addDictionRecord(Dictionary dictitem){
		//List<Dictionary> departTypelist = dictionDao.findAllList(dictionary); 
		int ret = dictionDao.insertDictRecord(dictitem); 
		if(ret>=0)
			return dictitem;
		return  null;
	}
}
