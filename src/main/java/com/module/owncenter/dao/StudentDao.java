package com.module.owncenter.dao;

import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.sys.entity.User;
import com.module.sys.entity.UserDetail;

/**
 * 机构DAO接口
 * @author hotelsimusys
 * @version 2016-05-16
 */ 
@MyBatisDao
public interface StudentDao  extends CrudDao<UserDetail> {
	/**
	 * 密码修改页面初始化
	 * @author yuanzhonglin
	 * @date 2016-7-15
	 */
	public List<UserDetail> userNameAndId(UserDetail userDetail);
	
	public List<UserDetail> fetchUserDetailById(UserDetail userDetail);

	public List<UserDetail> queryAllStudent(UserDetail userDetail);
	
	
}
