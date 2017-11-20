package com.lskj.testSms;

import org.junit.Test;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.common.sms.Sms;

public class SmsTest {
	
	@Test
	public void test1() throws ClientException{
		SendSmsResponse sendSms = Sms.sendSms();
		System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + sendSms.getCode());
        System.out.println("Message=" + sendSms.getMessage());
        System.out.println("RequestId=" + sendSms.getRequestId());
        System.out.println("BizId=" + sendSms.getBizId());
	}
	@Test
	public void test2(){
		System.out.println("1");
	}
}
