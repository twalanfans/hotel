/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/hotelsimusys/hotelsimusys">Hotelsimusys</a> All rights reserved.
 */
package com.module.sys.dao;

import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.sys.entity.Department;
import com.module.sys.entity.User;
import com.module.sys.entity.UserDetail;

/**
 * 用户DAO接口
 * @author hotelsimusys
 * @version 2016-05-16
 */
@MyBatisDao

public interface UserDao extends CrudDao<User> {
	
	/**
	 * 根据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 登陆成功后查询用户真实姓名
	 */
	public String getUserName(String userId);
	
	/**
	 * 通过UserId获取用户列表，
	 * @param user
	 * @return
	 */
	public User findUserByUserId(User user);
		
	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);
	
	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);
	
	/**
	 * @author yuanzhonglin
	 * @date 2016-6-29
	 * 用户找回密码
	 */
	public int updatePassword(User user);
	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteRoleByUser(User user);
	
	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);
	
	/**
	 * 往用户角色中间表插入数据
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public int saveUserRole(User user);

	public int teacherPass(String userId);
	
	/**
	 * 校验手机号是否被注册
	 */
	public List<User> checkPhone(User user);
	
}
