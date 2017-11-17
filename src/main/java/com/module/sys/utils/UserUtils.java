package com.module.sys.utils;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.noo.pagination.page.PageContext;
import org.noo.pagination.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.common.utils.CacheUtils;
import com.common.utils.SpringContextHolder;
import com.github.pagehelper.PageHelper;
import com.module.sys.dao.MenuDao;
import com.module.sys.dao.MessageDao;
import com.module.sys.dao.RoleDao;
import com.module.sys.dao.SysNoticeDao;
import com.module.sys.dao.UserDao;
import com.module.sys.dao.UserDetailDao;
import com.module.sys.entity.Menu;
import com.module.sys.entity.Message;
import com.module.sys.entity.Notice;
import com.module.sys.entity.Role;
import com.module.sys.entity.User;
import com.module.sys.entity.UserDetail;
import com.module.sys.security.SystemAuthorizingRealm.Principal;

/**
 * 用户工具类
 * 
 * @author yuanzhonglin
 * @version 2016-06-05
 */
public class UserUtils {

	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);

	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_LIST_BY_DEPART_ID_ = "oid_";
	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_DEPART_LIST = "departList";

	@Autowired
	private MessageDao messageDao;

	/**
	 * 根据ID获取用户
	 * 
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id) {
		User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
		if (user == null) {
			user = userDao.get(id);
			if (user == null) {
				return null;
			}
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE,
					USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}
		return user;
	}

	/**
	 * 根据登录名获取用户
	 * 
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName) {

		User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_
				+ loginName);
		if (user == null) {
			user = userDao.getByLoginName(new User(null, loginName));
			if (user == null) {
				return null;
			}
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
			CacheUtils.put(USER_CACHE,
					USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
		}

		return user;
	}

	/**
	 * 校验3d软件登录
	 * 
	 * @param loginName
	 * @return
	 */
	public static User getUserByLoginName(String loginName) {
		User user = userDao.getByLoginName(new User(null, loginName));
		return user;
	}

	/**
	 * 根据登录ID获取用户
	 * 
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getUserByUserId(String userId) {

		User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + userId);
		if (user == null) {
			user = new User();
			user.setUserId(userId);
			user = userDao.findUserByUserId(user);
			if (user == null) {
				return null;
			}
			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
		}

		return user;
	}

	/**
	 * 用户退出清理登录信息缓存
	 */
	public static void clearLoginCache() {
		UserUtils.clearCache(getUser());
	}

	/**
	 * /** 清除当前用户缓存
	 */
	public static void clearCache() {
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_MENU_LIST);
		removeCache(CACHE_DEPART_LIST);
		UserUtils.clearCache(getUser());
	}

	/**
	 * 清除指定用户缓存
	 * 
	 * @param user
	 */
	public static void clearCache(User user) {
		CacheUtils.remove(USER_CACHE, USER_CACHE_ID_ + user.getId());
		CacheUtils.remove(USER_CACHE,
				USER_CACHE_LOGIN_NAME_ + user.getLoginName());
	}

	/**
	 * 获取当前用户
	 * 
	 * @return 取不到返回 new User()
	 */
	public static User getUser() {
		Principal principal = getPrincipal();
		if (principal != null) {
			User user = getByLoginName(principal.getLoginName());
			if (user != null) {
				return user;
			}
			return new User();
		}
		return new User();
	}

	/**
	 * 获取当前用户角色列表
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Role> getRoleList() {
		List<Role> roleList = (List<Role>) getCache(CACHE_ROLE_LIST);
		if (roleList == null) {
			String userId = getUser().toString();
			Role role = new Role();
			role.setUserId(userId);
			roleList = roleDao.findList(role);
		}
		return roleList;
	}

	/**
	 * 删除用户 2016-8-9
	 */
	public void deleteUser(User user) {
		UserDao userDao = SpringContextHolder.getBean(UserDao.class);
		UserDetailDao userDetailDao = SpringContextHolder
				.getBean(UserDetailDao.class);
		userDao.delete(user);
		userDetailDao.delete(user);
		userDao.deleteRoleByUser(user);
	}

	/**
	 * 获取系统用户列表 yuanzhonglin 2016-8-8
	 */
	public List<UserDetail> queryAllUserList(UserDetail userDetail) {
		Pagination pager = PageContext.getPageContext(); // 分页
		int pageNo = pager.getCurrentPage();
		int pageSize = pager.getPageSize();
		PageHelper.startPage(pageNo, pageSize); // 拦截器分页开始
		List<UserDetail> userList = queryUser(userDetail);
		return userList;
	}

	public static List<UserDetail> queryUser(UserDetail userDetail) {
		UserDetailDao userDetailDao = SpringContextHolder
				.getBean(UserDetailDao.class);
		List<UserDetail> userList = userDetailDao.queryUserList(userDetail);
		return userList;
	}

	/**
	 * 用户登录公告查询 2016-8-29
	 */
	public static List<Notice> userNotice() {
		SysNoticeDao sysNoticeDao = SpringContextHolder
				.getBean(SysNoticeDao.class);
		List<Notice> userNotice = sysNoticeDao.userNotice(UserUtils.getUser()
				.toString());
		return userNotice;
	}

	/**
	 * 用户登录待办短信查询 2016-8-26
	 */
	@Transactional(readOnly = true)
	public static List<Message> userNeedDeail() {
		MessageDao messageDao = SpringContextHolder.getBean(MessageDao.class);
		List<Message> list = messageDao.queryUserMessage(new Message(UserUtils
				.getUser().toString()));
		return list;
	}

	public static UserDetail getUserDetail(String userId) {
		UserDetailDao userDetailDao = SpringContextHolder
				.getBean(UserDetailDao.class);
		UserDetail userDetail = userDetailDao.get(userId);
		return userDetail;
	}

	@SuppressWarnings("rawtypes")
	public static List getEmail(String loginName) {
		UserDetailDao userDetailDao = SpringContextHolder
				.getBean(UserDetailDao.class);
		List email = userDetailDao.getEmail(loginName);
		return email;
	}

	/**
	 * 获取当前用户授权菜单
	 * 
	 * @return
	 */
	public static List<Menu> getMenuList() {
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST);
		User user = getUser();

		if (menuList == null) {
			String userId = user.toString();
			if (userId.equals("superadmin")) {
				menuList = menuDao.findAllList(new Menu());
			} else {
				Menu m = new Menu();
				m.setUserId(userId);
				menuList = menuDao.findByUserId(m);
			}
			putCache(CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}

	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return principal;
			}
			// subject.logout();
		} catch (UnavailableSecurityManagerException e) {

		} catch (InvalidSessionException e) {

		}
		return null;
	}

	public static Session getSession() {
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null) {
				session = subject.getSession();
			}
			if (session != null) {
				return session;
			}
			// subject.logout();
		} catch (InvalidSessionException e) {

		}
		return null;
	}

	// ============== User Cache ==============

	public static Object getCache(String key) {
		return getCache(key, null);
	}

	public static Object getCache(String key, Object defaultValue) {
		Object obj = getSession().getAttribute(key);
		return obj == null ? defaultValue : obj;
	}

	public static void putCache(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
		getSession().removeAttribute(key);
	}

}
