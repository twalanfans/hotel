package com.module.version.service;

import java.util.List;

import com.module.version.entity.Version;

public interface VersionService {
	List<Version> selectVersion();
	Version selectVersionById(String id);
	List<Version> queryAllLostVersion();
}	
