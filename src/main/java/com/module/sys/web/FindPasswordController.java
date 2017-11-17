package com.module.sys.web;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.common.web.BaseController;
import com.module.sys.service.MailCheckService;
import com.module.sys.service.SystemService;
import com.module.sys.utils.UserUtils;
import com.common.config.Global;

/**
 * @Description  忘记密码
 * @author  yuanzhonglin
 * @CreateDate  2016-6-29
 */
@Controller
public class FindPasswordController extends BaseController{
	
	//  注入SystemService
	@Autowired
	private SystemService systemService;
	/**
	 * @author yuanzhonglin
	 * @date 2016-6-29
	 * 忘记密码--第一步
	 */
	@RequestMapping(value = "/forgetPassword", method = RequestMethod.GET)
	public  String changPassword(){
		return "modules/sys/findpwd";
	}
	
	/**
	 * 忘记密码发送验证码
	 * 2016-11-16
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/checkPhoneAndEmail")
	public  String checkPhoneAndEmail(HttpServletRequest request,HttpServletResponse response){
		String email = request.getParameter("email").toString()==null?"":request.getParameter("email").toString();
		String loginName = request.getParameter("loginName").toString()==null?"":request.getParameter("loginName").toString();
        try {
        	List list = UserUtils.getEmail(loginName);
        	if(list.size()==0){
        		return renderString(response, "noExist");
        	}else if(list.size()==1){
        		String searchEmail = list.get(0).toString();
        		if(!searchEmail.equals(email))
        		return renderString(response, "no");
        	}
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
        return renderString(response,"success");
	}
	
	/**
	 * 忘记密码发送验证码
	 * 2016-11-16
	 */
	@RequestMapping(value = "/emailValidate")
	public  String emailValidate(HttpServletRequest request,HttpServletResponse response){
		String email = request.getParameter("email").toString()==null?"":request.getParameter("email").toString();
	  	String smtp = Global.getConfig("email.smtp");// smtp邮箱服务器
        String from = Global.getConfig("email.from");// 发件人
        String to = email; //收件人 ַ
        String copyto = "";// 邮件抄送人ַ
        String subject = "邮箱验证";// 邮件标题
        String validate = "";
        Random random = new Random();
        for(int i=0; i<=5; i++){
        	int arrIdx = random.nextInt(9);
        	int[] num = {0,1,2,3,4,5,6,7,8,9};
        	String getNum = String.valueOf(num[arrIdx]);
        	validate += getNum;
        }
        //String content = "<p>【路上科技】尊敬的用户：你好！</p></br> <p>你本次的【验证码】是：<font color=\"red\" size=\"18\">"+validate+"</font></p>";// 邮件内容
        String content = "<p>【路上科技】尊敬的用户：你好！</p></br> <p>你本次的【验证码】是："+validate+"</p>";// 邮件内容
        String username = Global.getConfig("email.userName");// 登录名
        String password = Global.getConfig("email.passWord");// 登录密码
        try {
			MailCheckService.sendAndCc(smtp, from, to, copyto, subject, content, username, password);
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
        return renderString(response,validate);
	}
	/**
	 * 忘记密码第二步
	 * @param request
	 */
	@RequestMapping(value = "/forgetPassword_2" , method = RequestMethod.GET)
	public  String  changPassword2(HttpServletRequest request){
		String loginName = request.getParameter("loginName").toString()==null?"":request.getParameter("loginName").toString();
		request.setAttribute("loginName", loginName);
	    return "modules/sys/findpwd_2";

	}
	
	@RequestMapping(value = "/forSubmit")
	public String saveUpdatePassword(HttpServletRequest request,HttpServletResponse response){
		try {
			String loginName = request.getParameter("loginName");
			String password = request.getParameter("password");
			systemService.updatePassword(loginName, password);
		} catch (Exception e) {
			return renderString(response, "0");
		}
		return renderString(response, "1");
	}
}