package com.module.sys.service;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.utils.DateUtils;
import com.common.utils.SpringContextHolder;
import com.common.utils.StringUtils;
import com.module.sys.dao.LogDao;
import com.module.sys.entity.AdminLog;
import com.module.sys.utils.UserUtils;

/**
 * @Description系统日志服务
 * @Author 吴迪
 * @Date 2016年7月13日
 */
@Service
@Transactional(readOnly = true)
public class LogService {

	private static LogDao logDao = SpringContextHolder.getBean(LogDao.class);
	//private LogDao logDao;
	/**
	 * 记录操作日志
	 * @param request
	 */
	@Transactional(readOnly=false)
	public void insertLog(String logAction,String logType,HttpServletRequest request){
		String userId = UserUtils.getUser().toString();
		String logController = request.getRequestURI();
		//  启用线程 调用logDao保存数据
		new SaveLogThread(logController,logAction,logType,userId,request).start();
	}

	//  获取当前用户操作时间
	/**
	 * 获取当前Ip地址
	 * @param request
	 * @return
	 */
	public static String getIp2(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
	
	/**
     * 直接接受request
     * 注：request中提供的getQueryString方法只对Get方法才能生效，
     * 在我们不知道方法的情况下最好重写getQueryString
     * @param request
     * @return
     */
@SuppressWarnings("rawtypes")
public static String getQueryString(HttpServletRequest request) {
        boolean first = true;
        StringBuffer strbuf = new StringBuffer("");
        Enumeration emParams = request.getParameterNames();
        //do-while
        do {
            if (!emParams.hasMoreElements()) {
                break;
            }
            
            String sParam = (String) emParams.nextElement();
            String[] sValues = request.getParameterValues(sParam);    
            
            String sValue = "";            
            for (int i = 0; i < sValues.length; i++) {
                sValue = sValues[i];
                if (sValue != null && sValue.trim().length() != 0 && first == true) {
                    //第一个参数
                    first = false;                    
                    strbuf.append(sParam).append("=").append(sValue);
                } else if (sValue != null && sValue.trim().length() != 0 && first == false) {
                    strbuf.append("&").append(sParam).append("=").append(sValue);
                }
            }
        } while (true);

        return strbuf.toString();
    }

  	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread{
		
		private String logController;
		private String logAction;
		private String logType;
		private String userId;
		private HttpServletRequest request;
		
		
		public SaveLogThread(String logController,String logAction,String logType,String userId,HttpServletRequest request){
			super(SaveLogThread.class.getSimpleName());
			this.logController = logController;
			this.logAction=logAction;
			this.logType=logType;
			this.userId=userId;
			this.request=request;
		}
		
		@Override
		public void run() {
			AdminLog adminLog = new AdminLog();
			//  将参数赋值给实体类
			String getIp = getIp2(request);
			String messageBody=getQueryString(request);
			String loginTime=DateUtils.getDateTime();
			adminLog.setLogController(logController);
			adminLog.setLogAction(logAction);
			adminLog.setLogOpType(logType);
			adminLog.setLogIp(getIp);
			adminLog.setLogOpTime(loginTime);
			adminLog.setLogData(messageBody);
			adminLog.setLogOpuser(userId);
			logDao.insertLog(adminLog);
		}
	}
}
