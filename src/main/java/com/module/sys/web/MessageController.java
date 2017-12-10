package com.module.sys.web;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.models.Page;
import com.common.utils.DateUtils;
import com.common.utils.RandomStringGenerator;
import com.common.web.BaseController;
import com.module.sys.entity.Message;
import com.module.sys.entity.UserDetail;
import com.module.sys.service.MessageService;
import com.module.sys.utils.UserUtils;

/**
 * 短消息Controller类
 * @author yuanzhonglin
 * @date 2016-7-13
 */
@Controller
public class MessageController extends BaseController{
	
	@Autowired
	private MessageService messageService ;
	
	/**
	 * 查看短消息详细信息
	 * 2016-8-1
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value="message/queryMessageDetail")
	public String queryMessageDetail(HttpServletRequest request){
		String messageId = request.getParameter("messageId")==null?"":request.getParameter("messageId");
		String userId = UserUtils.getUser().toString();
		Message message = new Message();
			message.setMessageId(messageId);
			message.setToUser(userId);
		List messageList = messageService.queryMessageDetai(message);
		request.setAttribute("messageList", messageList);
		return "modules/sys/admin/admin_sendmessage_detail_query";
	}
	
	/**
	 * 1.管理员发送短消息初始化页面 2.查询所有用户
	 * @author yuanzhonglin
	 * @date 2016-7-13
	 */
	@RequestMapping(value="${adminPath}/message/sendMessagePage")
	public String sendMessage(HttpServletRequest request) throws Exception{
		String dn = request.getParameter("departName")==null?"":request.getParameter("departName");
		String departName = new String(dn.getBytes("ISO8859-1"), "UTF-8");
		String gn = request.getParameter("gradeName")==null?"":request.getParameter("gradeName");
		String gradeName = new String(gn.getBytes("ISO8859-1"), "UTF-8");
		String pn = request.getParameter("professionName")==null?"":request.getParameter("professionName");
		String professionName = new String(pn.getBytes("ISO8859-1"), "UTF-8");
		String un = request.getParameter("userName")==null?"":request.getParameter("userName");
		String userName = new String(un.getBytes("ISO8859-1"), "UTF-8");
		String userType = request.getParameter("userType")==null?"":request.getParameter("userType");
		UserDetail userdetail = new UserDetail();
			userdetail.setDepartName(departName);  
			userdetail.setGradeName(gradeName);
			userdetail.setProfessionName(professionName);
			userdetail.setUserName(userName);
			userdetail.setUserType(userType);
		try {
			Page page = messageService.userList(userdetail);
			request.setAttribute("userList", page);
			request.setAttribute("departName", departName);
			request.setAttribute("gradeName", gradeName);
			request.setAttribute("professionName", professionName);
			request.setAttribute("userName", userName);
			request.setAttribute("userType", userType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "modules/sys/admin/admin_senduser_message";
	}
	
	/**
	 * 发送短消息
	 * @author yuanzhonglin
	 * @date 2016-7-13
	 */
	@RequestMapping(value="message/sendToUser")
	public String sendUserMessage(HttpServletRequest request,HttpServletResponse response){
		String result = "";
		String toUser = request.getParameter("userId");  //接收人用户Id
		String subject = request.getParameter("title");  //短消息标题
		String allMessage = request.getParameter("messagecontent");  //短消息内容
		String fromUser = UserUtils.getUser().toString(); //当前发件人用户Id
		String userList[]=toUser.split(",");
		Message message = new Message();
			message.setFromUser(fromUser);
			message.setSubject(subject);
			message.setAllMessage(allMessage);
			message.setSendTime(Timestamp.valueOf(DateUtils.getDateTime()));
		try {
			for(int i=0; i<userList.length;i++){
				message.setMessageId(RandomStringGenerator.getRandomStringByLength(16));
				message.setToUser(userList[i]);
				messageService.sendUersMessage(message);
			}
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result ="error";
		}
		return renderString(response,result);
	}
	
	/**
	 * 删除短消息
	 * 2016-8-1
	 */
	@RequestMapping(value="message/deleteMessage")
	public String deleteMessage(HttpServletRequest request,HttpServletResponse response){
		String messageId = request.getParameter("messageId");
		String result="";
		String idList[]=messageId.split(",");
		Message message = new Message();
		try {
			for(int i=0; i<idList.length; i++){
				message.setMessageId(idList[i]);
				messageService.deleteMessage(message);
			}
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result="error";
		}
		return renderString(response,result);
		
	}
}
