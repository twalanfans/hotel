package com.module.sys.web;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.common.config.Global;
import com.common.security.shiro.session.SessionDAO;
import com.common.utils.CookieUtils;
import com.common.utils.Log;
import com.common.utils.StringUtils;
import com.common.web.BaseController;
import com.google.common.collect.Maps;
import com.module.sys.dao.MessageDao;
import com.module.sys.entity.User;
import com.module.sys.entity.UserDetail;
import com.module.sys.security.SystemAuthorizingRealm.Principal;
import com.module.sys.utils.UserUtils;
import com.common.utils.CacheUtils;
import com.common.servlet.ValidateCodeServlet;
import com.common.utils.IdGen;
import com.module.sys.security.FormAuthenticationFilter;
import com.module.sys.service.SystemService;
/**
 * 登录Controller
 * @author yuanzhonglin
 * @version 2016-7-13
 */
@Controller
public class LoginController extends BaseController{
	
	@Autowired
	private SessionDAO sessionDAO;
	
	@Autowired
	private MessageDao messageDao;
	
	/**
	 * 访问首页
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response) {
		Principal principal = UserUtils.getPrincipal();
		String kickout = request.getParameter("kickout");
		if("1".equals(kickout)){
			return "modules/sys/kickout";
		}
		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}
		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			return "redirect:" + adminPath+"/chooseLogin";
		}	
		return "modules/sys/sysLogin";
	}
	
	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {

		Principal principal = UserUtils.getPrincipal();
		 // 如果已经登录，则跳转到管理首页
		if(principal != null){
			return "redirect:" + adminPath +"/chooseLogin";
		}
		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);
		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
			message = "用户或密码错误, 请重试.";
		}
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);
		if (logger.isDebugEnabled()){
			logger.debug("login fail, active session size: {}, message: {}, exception: {}", 
				sessionDAO.getActiveSessions(false).size(), message, exception);
		}
		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			model.addAttribute("isValidateCodeLogin", isValidateCodeLogin(username, true, false));
		}
		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());
		//清除登录缓存
		return "modules/sys/sysLogin";
	}
	
	/**
	 * 登录成功，进入管理首页
	 */
	@RequestMapping(value = "${adminPath}/chooseLogin" , method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Principal principal = UserUtils.getPrincipal();
		if(principal==null){
			return "modules/sys/sysLogin";
		}
		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);
		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}
		UserDetail userDetail = UserUtils.getUserDetail(UserUtils.getUser().toString());
		System.out.println(userDetail);
		String userType = userDetail.getUserType();
		String indexUrl = "modules/sys/sysLogin";
		if (userType.equals("0")) { 		//管理员
			indexUrl = "modules/sys/system_admin_manage";
		}else if(userType.equals("1")){ 		//学生
			indexUrl = "modules/sys/system_student_manage";
		}else if(userType.equals("2")){ 		//老师
			indexUrl = "modules/sys/system_teacher_manage";
		}else{
			UserUtils.clearCache(UserUtils.getUser());
			request.setAttribute("message", "您还未获取管理员认证，不能登录此系统！请尽快联系管理员!");
			UserUtils.clearCache(UserUtils.getUser());
			UserUtils.getSubject().logout();
		}
		request.setAttribute("userDetail", userDetail);
		return indexUrl;
	}
	
	/**
	 * 用户退出清除缓存
	 * @author yuanzhonglin
	 * @date 2016-7-1
	 */
	@RequestMapping(value = "/logout")
	public String logout(){
		UserUtils.clearCache(UserUtils.getUser());
		UserUtils.getSubject().logout();
		return "modules/sys/sysLogin";
	}
		
@RequestMapping(value = "/appLogin")
	public void appLogin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String un = request.getParameter("UserName")==null?"":request.getParameter("UserName");
		String userName = new String(un.getBytes("ISO8859-1"), "UTF-8");
		String passWord = request.getParameter("PassWord");
		try {
			User user = UserUtils.getUserByLoginName(userName);
			Writer out = response.getWriter();
			System.out.println("Login-Start....-----m:"+userName+"---passWord:----"+passWord);
			if(user==null){
				out.write("no");		//不存在该用户
				Log.error("不存在此用户--- ", null);
				System.out.println("---不存在此用户----");
			}else{
				String userPassWord = user.getPassword();
				if(SystemService.validatePassword(passWord, userPassWord)){
					String status = user.getStatus();
					if(!status.equals("1")){
						out.write("error");		//登录失败
						System.out.println("---不存在此用户----");
					}else{
						out.write(userName);			//登录成功。返回用户Id
						System.out.println("---登录成功！----");
					}
				}else{
					out.write("pe"); 			//密码错误
					System.out.println("---失败！----");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			 Log.error("用户校验失败！---", e);
		}
	}
	/**
		 * 是否是验证码登录
		 * @param useruame 用户名
		 * @param isFail 计数加1
		 * @param clean 计数清零
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
			Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
			if (loginFailMap==null){
				loginFailMap = Maps.newHashMap();
				CacheUtils.put("loginFailMap", loginFailMap);
			}
			Integer loginFailNum = loginFailMap.get(useruame);
			if (loginFailNum==null){
				loginFailNum = 0;
			}
			if (isFail){
				loginFailNum++;
				loginFailMap.put(useruame, loginFailNum);
			}
			if (clean){
				loginFailMap.remove(useruame);
			}
			return loginFailNum >= 1;
		}
		
}
