package com.module.resource.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.service.BaseService;
import com.common.utils.SpringContextHolder;
import com.module.resource.dao.ResourceDao;
import com.module.resource.entity.Resource;

/**
 * 资源服务
 * @author yuanzhonglin
 * @date 2016-8-4
 */
@Service
@Transactional(readOnly = true)
public class ResourceService extends BaseService{
	
	public List<Resource> haveRelationGroup(String courseId){
		ResourceDao resourceDao = SpringContextHolder.getBean(ResourceDao.class);
		List<Resource> list = resourceDao.haveRelationGroup(courseId);
		return list;
	}
	
	public List<Resource> queryNewGroup(Resource resource){
		ResourceDao resourceDao = SpringContextHolder.getBean(ResourceDao.class);
		List<Resource> list = resourceDao.queryNewGroup(resource);
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	public List courseResource(String courseId){
		ResourceDao resourceDao = SpringContextHolder.getBean(ResourceDao.class);
		List list = resourceDao.courseResource(courseId);
		return list;
	}
	
	/**
	 * 向制定角色添加资源
	 * 2016-8-5
	 */
	@Transactional(readOnly=false)
	public void insertResourceToRole(Resource resource){
		ResourceDao resourceDao = SpringContextHolder.getBean(ResourceDao.class);
		try {
			resourceDao.insertResourceToRole(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关联新资源
	 * 2016-8-11
	 */
	@Transactional(readOnly=false)
	public void saveNewResource(Resource resource){
		ResourceDao resourceDao = SpringContextHolder.getBean(ResourceDao.class);
		try {
			resourceDao.saveNewResource(resource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取用户有权的所有资源列表
	 * 2016-8-11
	 */
	@Transactional(readOnly=true)
	public List<Resource> fetchResourceListByUserId(String userId,Resource resource){
		ResourceDao resourceDao = SpringContextHolder.getBean(ResourceDao.class);
		List<Resource> rslist= new ArrayList<Resource>();
		try {
			rslist = resourceDao.fetchResourceListByUserid(userId,resource);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return rslist;
	}
}
