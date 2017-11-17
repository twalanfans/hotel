/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/hotelsimusys/hotelsimusys">Hotelsimusys</a> All rights reserved.
 */
package com.module.sys.dao;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.sys.entity.AdminLog;
import com.module.sys.entity.Dictionary;
import com.module.sys.entity.Log;

/**
 * 日志DAO接口
 * @author hotelsimusys
 * @version 2016-05-16
 */

@MyBatisDao
public interface LogDao extends  CrudDao<Log> {

	/**
	 * 保存日志
	 * @param adminLog
	 */
		
	public void insertLog(AdminLog adminLog);
}
