package com.module.sys.service;

import java.util.List;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.noo.pagination.page.PageContext;
import org.noo.pagination.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.common.service.BaseService;
import com.common.utils.SpringContextHolder;
import com.github.pagehelper.PageHelper;
import com.module.sys.dao.RoleDao;
import com.module.sys.dao.UserDetailDao;
import com.module.sys.entity.Role;
import com.module.sys.entity.UserDetail;
import com.module.sys.utils.UserUtils;

/**
 * @Description  角色管理服务类
 * @Author  yuanzhonglin
 * @Date  2016年7月8日 
 */
@Service
@Transactional(readOnly = true)
public class RoleManageService extends BaseService{

	@Autowired
	private RoleDao roleDao;
	
	/**
	 * 初始化查询所有角色列表
	 */
	public static List<Role> showAllRole(){	
		RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
		List<Role> roleList = roleDao.queryAllRoleByUserId(UserUtils.getUser().toString());  
		return roleList;
	}
	
	/**
	 * 判断当前用户是否拥有任一角色
	 * @param rolelist,多个角色之间用";"隔开
	 * @return
	 */
	public static boolean hasAnyRoles(String rolelist)
	{
		Subject subject =UserUtils.getSubject();
		
		String[] rolesArray = rolelist.split(";");  
		 
	    if (rolesArray == null || rolesArray.length == 0) {  
	           //no roles specified, so nothing to check - allow access.  
	            return false;  
	        }  

	    List<String> roles = CollectionUtils.asList(rolesArray);  
	    boolean[] hasRoles = subject.hasRoles(roles);  
	    for (boolean hasRole : hasRoles) {  
	            if (hasRole) {  
	                return true;  
	           }  
	       }  
	        return false;  
	    
		}
	
	/**
	 * 通过角色Id查其所有用户
	 * 2016-8-9
	 */
	@Transactional(readOnly=true)
	public static List<UserDetail> queryUserByRole(UserDetail userDetail){
		Pagination pager = PageContext.getPageContext();  //分页
		int pageNo = pager.getCurrentPage();
		int pageSize = pager.getPageSize();
		PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
		List<UserDetail> list = userList(userDetail);  
		return list;
	}
	
	public static List<UserDetail> userList(UserDetail userDetail){
		UserDetailDao userDetailDao = SpringContextHolder.getBean(UserDetailDao.class);
		List<UserDetail> list = userDetailDao.queryUserByRole(userDetail);  
		return  list;
	}
	
	/**
	 * 管理员增加角色
	 * @param role
	 */
	@Transactional(readOnly=false)
	public void insertRole(Role role) {
		RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
			try {
				roleDao.insert(role);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	/**
	 * 管理员删除角色
	 * @param role
	 */
	@Transactional(readOnly=false)
	public void deleteRole(Role role) {
		RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
		roleDao.delete(role);
		roleDao.deleteRoleResource(role);
		roleDao.removeStudent(role);
	}

	/**
	 * 管理员管理更新角色
	 * @param role
	 */
	@Transactional(readOnly=false)
	public void updateRole(Role role){
		RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
		roleDao.update(role);
	}
	
	/**
	 * 角色权限对应信息维护(查询)
	 * @return
	 */
	@Transactional(readOnly=false)
	public void deleteRoleResource(Role role) {
		RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
		roleDao.deleteRoleResource(role);
	}
	/**
	 * 给用户授予角色
	 * 2016-8-9
	 */
	@Transactional(readOnly=false)
	public static void addUserToRole(Role role) {
		RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
		roleDao.addUserToRole(role);
		roleDao.changeUserStatus(role);
	}
	
	@Transactional(readOnly=false)
	public void removeStudent(Role role) {
		RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
			roleDao.removeStudent(role);
	}
	
}
