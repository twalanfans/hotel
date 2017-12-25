package com.module.sys.web;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.common.config.Global;
import com.common.sms.Sms;
import com.common.web.BaseController;
import com.module.sys.service.MailCheckService;
import com.module.sys.service.SystemService;
import com.module.sys.utils.UserUtils;

/**
 * 
 * @author aprwu
 * <p>Title</p>FindPasswordController
 * 2017年11月17日下午6:28:58
 */
@Controller
public class FindPasswordController extends BaseController{
	
	//  注入SystemService
	@Autowired
	private SystemService systemService;
	
	//产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    //static final String accessKeyId = "LTAIpmnv1i7nd6wG";
    static final String accessKeyId = Global.getAccessKeyId();
    //static final String accessKeySecret = "2TxTiomPsmqtSH3qSUSiKwcX6jmBjs";
    static final String accessKeySecret = Global.getAccessKeySecret();
    
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
        System.out.println();
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
	 * 短信验证码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/SmsValidate")
	public String SmsCAPTCHA(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String validate = "";
        Random random = new Random();
        for(int i=0; i<=5; i++){
        	int arrIdx = random.nextInt(9);
        	int[] num = {0,1,2,3,4,5,6,7,8,9};
        	String getNum = String.valueOf(num[arrIdx]);
        	validate += getNum;
        }
        System.out.println("validate------"+validate);//用的是这个验证码，在Sms.java中生成的是发送给手机，但是没用
		String loginName = request.getParameter("loginName");
		SendSmsResponse sendSms = Sms.sendSms(loginName, validate);
		System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + sendSms.getCode());
        System.out.println("Message=" + sendSms.getMessage());
        System.out.println("RequestId=" + sendSms.getRequestId());
        System.out.println("BizId=" + sendSms.getBizId());
      //查明细
        if(sendSms.getCode() != null && sendSms.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = Sms.querySendDetails(sendSms.getBizId(),loginName);
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                System.out.println("SmsSendDetailDTO["+i+"]:");
                System.out.println("Content=" + smsSendDetailDTO.getContent());
                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
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