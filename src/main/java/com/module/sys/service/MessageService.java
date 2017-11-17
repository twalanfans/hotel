package com.module.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.noo.pagination.page.PageContext;
import org.noo.pagination.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.models.Page;
import com.common.utils.SpringContextHolder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.module.owncenter.dao.StudentDao;
import com.module.sys.dao.MessageDao;
import com.module.sys.dao.UserDao;
import com.module.sys.entity.Message;
import com.module.sys.entity.UserDetail;

/**
 * 短消息服务类Service
 * @author yuanzhonglin
 * @date 2016-7-13
 */
@Service
@Transactional(readOnly = true)
public class MessageService {
		
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private UserDao userDao;
		
	/**
	 * 查询用户收件箱数据List
	 * @author yuanzhonglin
	 * @date 2016-7-13
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page messageList(Message message){
			MessageDao messageDao = SpringContextHolder.getBean(MessageDao.class);
			Pagination pager = PageContext.getPageContext();  //分页
			int pageNo = pager.getCurrentPage();
		    int pageSize = pager.getPageSize();
			PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
			
			List<Message> messageList = messageDao.queryAllMessage(message);
			PageInfo pageinfo = new PageInfo(messageList);
			int total = (int) (long)pageinfo.getTotal();
			List<Message> list1=pageinfo.getList();
			List<Message> list2= new ArrayList<Message>();
			for(int i=0;i<list1.size();i++)
			{
				Message msgList =list1.get(i);
				list2.add(msgList);
			}
			Page page = new Page(pageinfo.getPageNum(), pageinfo.getPageSize(),pageinfo.getPages(),total,list2);
			return page;
		}
	
	/**
	 * 查询已查看/未查看短信List
	 * @author yuanzhonglin
	 * @date 2016-7-13
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly=false)
	public Page sendMessage(Message message){
		MessageDao messageDao = SpringContextHolder.getBean(MessageDao.class);
		Pagination pager = PageContext.getPageContext();  //分页
		int pageNo = pager.getCurrentPage();
	    int pageSize = pager.getPageSize();
		PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
		List<Message> messageList = messageDao.querySendMessage(message);
		
		PageInfo pageinfo = new PageInfo(messageList);
		int total = (int) (long)pageinfo.getTotal();
		List<Message> list1=pageinfo.getList();
		List<Message> list2= new ArrayList<Message>();
		for(int i=0;i<list1.size();i++)
		{
			Message msgList =list1.get(i);
			list2.add(msgList);
		}
		Page page = new Page(pageinfo.getPageNum(), pageinfo.getPageSize(),pageinfo.getPages(),total,list2);
		return page;
	}
	/**
	 * 查看短消息详情
	 * 2016-8-1
	 */
	@Transactional(readOnly=false)
	public List<Message> queryMessageDetai(Message message){
		List<Message> messageList =null;
		try {
			messageList = messageDao.queryMessageDetai(message);
			messageDao.updateMsgRead(message);
		} catch (Exception e) {
			e.getMessage();
		}
		return messageList;
	}
	
	/**
	 * 查询接收人用户列表List
	 * @author yuanzhonglin
	 * @date 2016-7-13
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly=false)
	public Page userList(UserDetail userdetail){
		StudentDao studentDao = SpringContextHolder.getBean(StudentDao.class);
		Pagination pager = PageContext.getPageContext();  //分页
		int pageNo = pager.getCurrentPage();
	    int pageSize = pager.getPageSize();
		PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
		
		List<UserDetail> userList = studentDao.findAllList(userdetail);
		PageInfo pageinfo = new PageInfo(userList);
		
		int total = (int) (long)pageinfo.getTotal();
		List<UserDetail> list1=pageinfo.getList();
		List<UserDetail> list2= new ArrayList<UserDetail>();
		for(int i=0;i<list1.size();i++)
		{
			UserDetail userDetail =list1.get(i);
			list2.add(userDetail);
		}
		Page page = new Page(pageinfo.getPageNum(), pageinfo.getPageSize(),pageinfo.getPages(),total,list2);
		return page;
	}
		
	@Transactional(readOnly=false)
	public void sendUersMessage(Message message){
		MessageDao messageDao = SpringContextHolder.getBean(MessageDao.class);
		messageDao.insert(message);
	}
	
	@Transactional(readOnly=false)
	public void deleteMessage(Message message){
		MessageDao messageDao = SpringContextHolder.getBean(MessageDao.class);
		try {
			messageDao.delete(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
