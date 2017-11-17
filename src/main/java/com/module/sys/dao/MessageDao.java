package com.module.sys.dao;

import java.util.List;
import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.sys.entity.Message;

/**
 * 短消息接口Dao
 * @author yuanzhonglin
 * @date 2016-7-13
 */
@MyBatisDao
public interface MessageDao extends CrudDao<Message>{
	
	public List<Message> queryAllMessage(Message message);
	
	public List<Message> queryMessageDetai(Message message);   
	
	public int updateMsgRead(Message message); //查看短消息之后更新消息查看状态
	
	public List<Message> querySendMessage(Message message);     //发件箱
	
	public List<Message> queryUserMessage(Message m);
	
	
}
