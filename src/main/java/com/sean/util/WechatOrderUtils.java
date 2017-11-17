package com.sean.util;

import com.common.config.Global;
import com.common.utils.DateUtils;
import com.module.owncenter.entity.CustomerDetail;
import com.module.owncenter.service.CustomerPayService;
import com.sean.beans.RandomStringGenerator;
import com.sean.beans.WechatOrder;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by seanid on 2016/1/15.
 * 微信统一下单工具类
 */
public class WechatOrderUtils {

	/**
	 * 根据code获取access_token
	 */
	public static Map<String, String> getOpenId(String appid, String secret, String code){
		Map<String,String> map =new HashMap<String, String>();
		String access_token ="";
		JSONObject result = new JSONObject();
		try {
			access_token = HttpUtils.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+secret+"&code="+code+"&grant_type=authorization_code");
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("获取access_token的http请求错误 ", null);
		}
		try {
			result = JSONObject.fromObject(access_token);
			map.put("openid", result.getString("openid"));
			map.put("access_token", result.getString("access_token"));
			map.put("refresh_token", result.getString("refresh_token"));
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("未获取到openid--- ", null);
		}
		System.out.println("-------获取到基础信息------"+map.toString());
        return map;
	}
	
	/**
	 * 根据access_token获取用户信息
	 */
	public static Map<String, String> userDetail(String accessToken, String openid){
		Map<String,String> map =new HashMap<String, String>();
		String response ="";
		JSONObject result = new JSONObject();
		try {
			response = HttpUtils.get("https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openid+"&lang=zh_CN");
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("获取用户信息时http请求错误 ", null);
		}
		try {
			result = JSONObject.fromObject(response);
			map.put("openid", result.getString("openid"));
			map.put("nickname", result.getString("nickname"));
			map.put("sex", result.getString("sex"));
			map.put("headimg", result.getString("headimgurl"));
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("未获取到信息--- ", null);
		}
		System.out.println("-------获取到基础信息------"+map.toString());
		return map;
	}
	
	/**
	 * 查询微信的关注者
	 */
	public static Map<String,Object> userList(String accessToken, String nextOpenId){
		Map<String, Object> map = new HashMap<String, Object>();
		String response ="";
		try {
			String url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+accessToken;
			if(!"".equals(nextOpenId)){
				url += "&next_openid"+nextOpenId;
			}
			response = HttpUtils.get(url);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("获取用户列表时http请求错误 ", null);
		}
		try {
			JSONObject result = JSONObject.fromObject(response);
			map.put("openidList", result.getString("data"));
			map.put("count", result.getString("count"));
			map.put("nextOpenId", result.getString("next_openid"));
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("未获取到用户列表--- ", null);
		}
		return map;		
	}
	
	public static String getTicket(){
		String id = "";
		return id;
	}
	
	public static String getAccessToken(){	
		String response ="";
		JSONObject result = new JSONObject();
		try {
			String secret = Global.getConfig("payKey");
			String appid = Global.getConfig("appID");
			response = HttpUtils.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret);
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("获取accessToken时http请求错误 ", null);
		}
		String accessToken = "";
		try {
			result = JSONObject.fromObject(response);
			accessToken = result.getString("access_token");
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("未获取到accessToken", null);
		}
		return accessToken;
	}
	
    /**
     * 统一下单
     * @param detail    订单详情，必填
     * @param desc      商品或订单描述，必填
     * @param openid    公众号调起时需要的OPENID，选填，不填传“”
     * @param ip        下订单时的IP，必填
     * @param goodSn    业务系统商品编号，必填
     * @param orderSn   业务系统订单编号，必填
     * @param amount    金额，必填
     * @param type      支付类型，分为三种，JSAPI表示公众号调起的支付，NATIVE用于PC端网页调起的扫码支付，APP用于APP端调起的支付
     * @return          返回对象中封装了网页和APP调起支付控件需要的参数，根据不同的支付类型，有不同的返回参数
     */
    public static synchronized JSONObject createOrder(String detail, String desc, String openid, String ip, String goodSn, String amount, String type) {

    	JSONObject result = new JSONObject();
        // 1、参数校验
        if (StringUtils.isBlank(detail) || StringUtils.isBlank(desc) || StringUtils.isBlank(ip)
                || StringUtils.isBlank(goodSn) || StringUtils.isBlank(amount)
                || StringUtils.isBlank(type)) {
            Log.error("微信支付统一下单请求错误：请求参数不足", null);
            result.put("status", "error");
            result.put("msg", "请求参数不足");
            result.put("obj", null);
            return result;
        }
        
        double relAmount = 0;// 对应微信支付的真实数目
        try {//进行格式转换异常获取，保证数目正确
            relAmount = Double.parseDouble(amount) * 100;
        } catch (Exception e) {
            Log.error("微信支付统一下单请求错误：请求金额格式错误", e);
            result.put("status", "error");
            result.put("msg", "请求金额格式错误");
            result.put("obj", null);
            return result;
        }
        if (relAmount == 0) {//微信支付的支付金额必须为大于0的int类型，单位为分
            Log.error("微信支付统一下单请求错误：请求金额不能为0", null);
            result.put("status", "error");
            result.put("msg", "请求金额不能为0");
            result.put("obj", null);
            return result;
        }
        String total_fee =String.valueOf(relAmount); 
        total_fee = total_fee.substring(0, total_fee.lastIndexOf("."));

        // 2、获取系统配置信息
        String wx_order = Global.getConfig("orderUrl");//获取统一下单接口地址
        String mchappid = Global.getConfig("appID");// 商户appid
        String mchid = Global.getConfig("mchID");// 商户ID
        String wx_callback = Global.getConfig("callback");// 获取微信支付回调接口
        String wx_key = Global.getConfig("payKey");//微信商户后台设置的key

        // 发送报文模板,其中部分字段是可选字段
        String xml = "" +
                "<xml>" +
                "<appid>APPID</appid>" +//公众号ID
                "<device_info>WEB</device_info>" +//设备信息
                "<detail>DETAIL</detail>" +//商品详情
                "<body>BODY</body>" +//商品描述
                "<mch_id>MERCHANT</mch_id>" +//微信给的商户ID
                "<nonce_str>randomString</nonce_str>" +//32位随机字符串
                "<notify_url><![CDATA[URL_TO]]></notify_url>" +//信息通知页面
                "<openid>UserFrom</openid>" +//支付的用户ID
                "<fee_type>CNY</fee_type>" +//支付货币，不改
                "<spbill_create_ip>IP</spbill_create_ip>" +//用户IP
                "<time_start>START</time_start>" +//订单开始时间
                "<time_expire>STOP</time_expire>" +//订单结束时间
                "<goods_tag>WXG</goods_tag>" +//商品标记，不改
                "<product_id>GOODID</product_id>" +//商品ID
                "<limit_pay>no_credit</limit_pay>" +//支付范围，默认不支持信用卡支付，不改
                "<out_trade_no>PAY_NO</out_trade_no>" +//商城生成的订单号
                "<total_fee>TOTAL</total_fee>" +//总金额
                "<trade_type>TYPE</trade_type>" +//交易类型，JSAPI，NATIVE，APP，WAP
                "<sign>SIGN</sign>" +//加密字符串
                "</xml>";

        //生成订单起始时间，订单2天内有效
        DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss");
        String start_time = df.format(new Date());
        String stop_time = df.format(new Date().getTime() + 2 * 24 * 60 * 60 * 1000);
        String orderSn = RandomStringGenerator.getPayNo(18);	//18位订单号
        String nonce_str = RandomStringGenerator.getRandomStringByLength(32);
        //3、xml数据封装
        xml = xml.replace("MERCHANT", mchid);
        xml = xml.replace("APPID", mchappid);
        xml = xml.replace("randomString", nonce_str);
        xml = xml.replace("DETAIL", detail);
        xml = xml.replace("BODY", desc);
        xml = xml.replace("URL_TO", wx_callback);
        xml = xml.replace("IP", ip);
        xml = xml.replace("START", start_time);
        xml = xml.replace("STOP", stop_time);
        xml = xml.replace("GOODID", goodSn);
        xml = xml.replace("PAY_NO",	orderSn);
        xml = xml.replace("TOTAL",total_fee);
        xml = xml.replace("TYPE", type);
        if ("NATIVE".equalsIgnoreCase(type)) {
            xml = xml.replace("<openid>UserFrom</openid>", openid);
        } else {
            xml = xml.replace("UserFrom", openid);
        }

        // 4、加密
        Map<String, String> map = new HashMap<String, String>();
        map.put("device_info", "WEB");
        map.put("detail", detail);
        map.put("body", desc);
        map.put("mch_id", mchid);
        map.put("appid", mchappid);
        map.put("nonce_str", nonce_str);
        map.put("notify_url", wx_callback);
        map.put("fee_type", "CNY");
        map.put("spbill_create_ip", ip);
        map.put("time_start", start_time);
        map.put("time_expire", stop_time);
        map.put("goods_tag", "WXG");
        map.put("product_id", goodSn);
        map.put("limit_pay", "no_credit");
        map.put("out_trade_no", orderSn);
        map.put("total_fee", total_fee);
        map.put("trade_type", type);     
        if (("JSAPI".equalsIgnoreCase(type))) {
            map.put("openid", openid);
        }

        String sign = SignatureUtils.signature(map, wx_key);
        xml = xml.replace("SIGN", sign);

        // 5、请求
        String response = "";
        try {//注意，此处的httputil一定发送请求的时候一定要注意中文乱码问题，中文乱码问题会导致在客户端加密是正确的，可是微信端返回的是加密错误
            response = HttpUtils.post(wx_order, xml);
        } catch (Exception e) {
            Log.error("微信支付统一下单失败:http请求失败", e);
            result.put("status", "error");
            result.put("msg", "http请求失败");
            result.put("obj", null);
            return result;
        }

        //6、处理请求结果
        XStream s = new XStream(new DomDriver());
        s.alias("xml", WechatOrder.class);
        WechatOrder order = (WechatOrder) s.fromXML(response);

        if ("SUCCESS".equals(order.getReturn_code()) && "SUCCESS".equals(order.getResult_code())) {
            Log.error("微信支付统一下单请求成功：" + order.getPrepay_id(), null);
            String email = desc.substring(0, desc.lastIndexOf("-"));
            CustomerDetail user = new CustomerDetail();
            	user.setOpenid(openid);
            	user.setEmail(email);
            	user.setOrderNo(orderSn);
            	user.setTotalFee(amount);
            	user.setCreateTime(Timestamp.valueOf(DateUtils.getDateTime()));
            	user.setStatus("0");
            int ret = CustomerPayService.createOrder(user);
            if(ret==0){
            	result.put("msg", "系统生成订单时出现错误！");
            	result.put("status", "error");
            	result.put("obj", null);
            	return result;
            }
        }else if ("SUCCESS".equals(order.getReturn_code()) && "FAIL".equals(order.getResult_code())){
        	Log.error(order.getErr_code_des(), null);
        	if("INVALID_REQUEST".equals(order.getErr_code()))	{
        		result.put("msg", "请勿重复提交订单！");
        	}
        	result.put("status", "error");
        	result.put("obj", null);
            return result;
        }
        HashMap<String, String> back = new HashMap<String, String>();

        //生成客户端调时需要的信息对象
        if ("JSAPI".equalsIgnoreCase(type)) {
            //网页调起的时候
            String time = Long.toString(System.currentTimeMillis());
            back.put("appId", mchappid);
            back.put("timeStamp", time);
            back.put("nonceStr", "5K8264ILTKCH16CQ2502SI8ZNMTM67VS");
            back.put("package", "prepay_id=" + order.getPrepay_id());
            back.put("signType", "MD5");
            String sign2 = SignatureUtils.signature(back, wx_key);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("appId", mchappid);
            jsonObject.put("timeStamp", time);
            jsonObject.put("nonceStr", "5K8264ILTKCH16CQ2502SI8ZNMTM67VS");
            jsonObject.put("package", "prepay_id=" + order.getPrepay_id());
            jsonObject.put("signType", "MD5");
            jsonObject.put("paySign", sign2);

            result.put("obj", jsonObject);
            return result;

        }  else if ("NATIVE".equalsIgnoreCase(type)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("url", order.getCode_url());
            result.put("status", "success");
            result.put("msg", "下单成功");
            result.put("obj", jsonObject);
            return result;


        } 
        return result;
    }


}
