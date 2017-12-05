package com.module.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.sys.entity.User;
import com.module.sys.entity.UserDetail;

/**
 * 用户详情接口Dao
 * @author yuanzhonglin
 * @date 2016-8-8
 */
@MyBatisDao
public interface UserDetailDao extends CrudDao<UserDetail>{
		
	public List<UserDetail> queryUserList(UserDetail userDetail);
	
	@SuppressWarnings("rawtypes")
	public List getEmail(String loginName);
	/**
	 * 管理员新建用户保存
	 * 2016-8-9
	 */
	public int insertUser(User user);

	public int insertLoginCount(UserDetail userDetail);
	/**
	 * 用户注册时,保存用户详情信息
	 * @param user
	 * @return
	 */
	public int insertUserDetail(UserDetail userDetail);
	
	/**
	 * 通过角色Id查用户集合
	 * 2016-8-9
	 */
	public List<UserDetail> queryUserByRole(UserDetail userDetail);
	
	public int delete(User user);
	
	public User fetchUserDetailByUserId(@Param("userId")String userId);
	
}
