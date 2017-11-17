package com.module.sys.entity;
import java.sql.Timestamp;

import com.common.persistence.DataEntity;

/**
 * 短消息实体类entity
 * @author yuanzhonglin
 * @date 2016-7-13
 */
public class Message extends DataEntity<Message>{
	
		private static final long serialVersionUID = 1L;
		private String messageId; //短消息Id
		private String fromUser;  //  发送人
		private String toUser;  //  收件人
		private String fromName; //发件用户
		private String photo; //用户头像
		private String replayId;  // 回复Id
		private String subject;  //  短消息标题
		private String allMessage;  //  消息内容
		private String smallMessage;  //  消息体内容
		private String isRead;  //  是否已读
		private String type;  //  类型
		private String module;  //  模块
		private Timestamp sendTime;  //  发送时间
		private Timestamp readTime;  //  阅读时间
		private String status;  //  状态
		
		public Message(){
		
		}
		
		public Message(String userId){
			this.toUser = userId;
		}
		
		public String getMessageId() {
			return messageId;
		}
		public void setMessageId(String messageId) {
			this.messageId = messageId;
		}
		public String getFromName() {
			return fromName;
		}
		public void setFromName(String fromName) {
			this.fromName = fromName;
		}
		public String getFromUser() {
			return fromUser;
		}
		public void setFromUser(String fromUser) {
			this.fromUser = fromUser;
		}
		public String getToUser() {
			return toUser;
		}
		public void setToUser(String toUser) {
			this.toUser = toUser;
		}
		public String getReplayId() {
			return replayId;
		}
		public void setReplayId(String replayId) {
			this.replayId = replayId;
		}
		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getAllMessage() {
			return allMessage;
		}
		public void setAllMessage(String allMessage) {
			this.allMessage = allMessage;
		}
		public String getSmallMessage() {
			return smallMessage;
		}
		public void setSmallMessage(String smallMessage) {
			this.smallMessage = smallMessage;
		}
		public String getPhoto() {
			return photo;
		}

		public void setPhoto(String photo) {
			this.photo = photo;
		}

		public String getIsRead() {
			return isRead;
		}
		public void setIsRead(String isRead) {
			this.isRead = isRead;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getModule() {
			return module;
		}
		public void setModule(String module) {
			this.module = module;
		}
		public Timestamp getSendTime() {
			return sendTime;
		}
		public void setSendTime(Timestamp sendTime) {
			this.sendTime = sendTime;
		}
		public Timestamp getReadTime() {
			return readTime;
		}
		public void setReadTime(Timestamp readTime) {
			this.readTime = readTime;
		}

		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
}
