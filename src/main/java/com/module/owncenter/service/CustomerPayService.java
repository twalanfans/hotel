package com.module.owncenter.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.common.utils.SpringContextHolder;
import com.module.owncenter.dao.CustomerPayDao;
import com.module.owncenter.entity.CustomerDetail;

@Service
@Transactional(readOnly = true)
public class CustomerPayService {

	public static List<CustomerDetail> queryOrderByUser(CustomerDetail user){
		CustomerPayDao dao = SpringContextHolder.getBean(CustomerPayDao.class);
		List<CustomerDetail> userList = dao.queryOrderByUser(user);
		return userList;
	}
	/**
	 * 创建订单
	 */
	public static int createOrder(CustomerDetail user){
		CustomerPayDao dao = SpringContextHolder.getBean(CustomerPayDao.class);
		int ret = 0;
		try {
			ret = dao.createOrder(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static int updateOrder(CustomerDetail user){
		CustomerPayDao dao = SpringContextHolder.getBean(CustomerPayDao.class);
		int ret = 0;
		try {
			ret = dao.updateOrder(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}
