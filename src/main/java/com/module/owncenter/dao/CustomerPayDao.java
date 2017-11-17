/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/hotelsimusys/hotelsimusys">Hotelsimusys</a> All rights reserved.
 */
package com.module.owncenter.dao;

import java.util.List;
import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.owncenter.entity.CustomerDetail;


@MyBatisDao
public interface CustomerPayDao extends CrudDao<CustomerDetail> {
	
	public List<CustomerDetail> queryOrderByUser(CustomerDetail user);			//查询用户订单
	
	public int createOrder(CustomerDetail user);

	public int updateOrder(CustomerDetail user);
	
}
