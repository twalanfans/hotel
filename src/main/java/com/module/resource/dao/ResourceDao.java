package com.module.resource.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.resource.entity.Resource;

/**
 * 资源接口Dao
 * @author yuanzhonglin
 * @date 2016-8-4
 */
@MyBatisDao
public interface ResourceDao extends CrudDao<Resource>{

	public List<Resource> haveRelationGroup(String courseId);
	
	public List<Resource> queryNewGroup(Resource  resource);

	@SuppressWarnings("rawtypes")
	public List courseResource(String courseId);
	
	/**
	 * 向制定角色增加资源
	 * 2016-8-5
	 */
	public int insertResourceToRole(Resource  resource);
	
	/**
	 * 关联新资源
	 * 2016-8-11
	 */
	public int saveNewResource(Resource  resource);
/**
	 * 查询用户对应资源
	 */
	public List<Resource> fetchResourceListByUserid(@Param("userId")String userId,@Param("resource")Resource resource);
}
