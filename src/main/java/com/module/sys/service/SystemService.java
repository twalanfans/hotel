/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/hotelsimusys/hotelsimusys">Hotelsimusys</a> All rights reserved.
 */
package com.module.sys.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import  com.common.security.Digests;
import  com.common.security.shiro.session.SessionDAO;
import  com.common.service.BaseService;
import com.common.utils.DateUtils;
import  com.common.utils.Encodes;
import com.common.utils.SpringContextHolder;
import  com.common.utils.StringUtils;
import  com.common.web.Servlets;
import  com.module.sys.dao.MenuDao;
import  com.module.sys.dao.RoleDao;
import  com.module.sys.dao.UserDao;
import  com.module.sys.entity.Menu;
import  com.module.sys.entity.Role;
import  com.module.sys.entity.User;
import  com.module.sys.security.SystemAuthorizingRealm;
import  com.module.sys.utils.UserUtils;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author hotelsimusys
 * @version 2016-05-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService {
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	
	public SessionDAO getSessionDao() {
		return sessionDao;
	}

	@Autowired

	//-- User Service --//
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		return UserUtils.get(id);
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}
	
	/**
	 * @author yuanzhonglin
	 * @param loginName,newPassword
	 * @密码更新/修改
	 * @date 2016-6-29
	 */
	@Transactional(readOnly = false)
	public void updatePassword(String loginName, String newPassword) {
		User user = new User();
		user.setLoginName(loginName);
		user.setPassword(entryptPassword(newPassword));
		userDao.updatePassword(user);
	}
	
	@Transactional(readOnly = false)
	public void updateUserLoginInfo(User user) {
		
		// 保存上次登录信息
		user.setLastLoginIp(user.getLastLoginIp());
		user.setLastLoginTime(user.getLastLoginTime());
		// 更新本次登录信息
		user.setLastLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
		user.setLastLoginTime(DateUtils.getDateTime());
		userDao.updateLoginInfo(user);
	}
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 获得活动会话
	 * @return
	 */
	public Collection<Session> getActiveSessions(){
		return sessionDao.getActiveSessions(false);
	}
	
	//-- Role Service --//
	
	public Role getRole(String id) {
		return roleDao.get(id);
	}

	public Role getRoleByName(String name) {
		Role r = new Role();
		r.setName(name);
		return roleDao.getByName(r);
	}
	
	public List<Role> findRole(Role role){
		return roleDao.findList(role);
	}
	
	public List<Role> findAllRole(){
		return UserUtils.getRoleList();
	}
	
	//-- Menu Service --//
	
	public Menu getMenu(String id) {
		return menuDao.get(id);
	}

	public List<Menu> findAllMenu(){
		return UserUtils.getMenuList();
	}
	
	/**
	 * 获取Key加载信息
	 */
	public static boolean printKeyLoadMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    						欢迎使用hotel酒店管理服务系统");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
		return true;
	}
	
		
}
