package com.module.version.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.module.version.dao.VersionDao;
import com.module.version.entity.Version;
@Service
@Transactional(
		isolation=Isolation.READ_COMMITTED,
		propagation=Propagation.REQUIRED,
		readOnly=false,
		rollbackFor=Exception.class
	)
public class VersionServiceImpl implements VersionService {
	
	@Resource
	private VersionDao versionDao;

	@Override
	public List<Version> selectVersion() {
		List<Version> list = versionDao.checkVersion();
		return list;
	}

	@Override
	public Version selectVersionById(String id) {
		Version version = versionDao.selectVersion(id);
		return version;
	}

	@Override
	public List<Version> queryAllLostVersion() {
		List<Version> lostVersion = versionDao.queryAllLostVersion();
		return lostVersion;
	}
	

}
