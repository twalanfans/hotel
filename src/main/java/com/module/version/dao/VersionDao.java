package com.module.version.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.persistence.annotation.MyBatisDao;
import com.module.version.entity.Version;
@MyBatisDao
public interface VersionDao {
	//查平台和版本
	List<Version> checkVersion();
	//单独平台查询
	Version selectVersion(@Param("id")String id);
	//查询历代版本
	List<Version> queryAllLostVersion();
}	
