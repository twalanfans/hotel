package com.module.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.service.BaseService;
import com.common.utils.SpringContextHolder;
import com.module.sys.dao.UserDao;
import com.module.sys.dao.UserDetailDao;
import com.module.sys.entity.User;
import com.module.sys.entity.UserDetail;

/**
 * @Description
 * @author yuanzhonglin
 * @version 0.1
 * @CreateDate 2016年6月22日
 */

@Service
@Transactional(readOnly = true)
public class RegisterService extends BaseService {

	// 注入UserDao
	@Autowired
	private UserDao userDao;

	// 保存用户注册信息
	@Transactional(readOnly = false)
	public void saveUser(User user, UserDetail userDetail) {
		UserDetailDao userDetailDao = SpringContextHolder.getBean(UserDetailDao.class);
		userDao.insert(user); // 注册用户
		userDetailDao.insertUserDetail(userDetail); // 保存用户详情
	}

	/**
	 * 注册校验手机号是否已被注册
	 * 
	 * @date 2016-7-19
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly = false)
	public List checkPhone(User user) {
		UserDao userDao = SpringContextHolder.getBean(UserDao.class);
		List<User> countList = new ArrayList();
		countList = userDao.checkPhone(user);
		return countList;
	}

	/**
	 * 管理员新建用户 2016-8-8
	 */
	@Transactional(readOnly = false)
	public void saveUserByAdmin(User user, UserDetail userDetail) {
		UserDetailDao userDetailDao = SpringContextHolder
				.getBean(UserDetailDao.class);
		userDetailDao.insertUserDetail(userDetail); // 保存在用户详情表
		userDetailDao.insertUser(user); // 保存在用户表
	}

	/**
	 * @author yuanzhonglin
	 * @date 2016-6-29 用户邮箱激活注册
	 */
	@Transactional(readOnly = false)
	public void teacherPass(String userId) {
		userDao.teacherPass(userId);
	}

}
