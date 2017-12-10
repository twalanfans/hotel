package com.module.owncenter.service;

import java.util.ArrayList;
import java.util.List;

import org.noo.pagination.page.PageContext;
import org.noo.pagination.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.utils.SpringContextHolder;
import com.github.pagehelper.PageHelper;
import com.module.owncenter.dao.StudentDao;
import com.module.sys.entity.User;
import com.module.sys.entity.UserDetail;
import com.module.sys.utils.UserUtils;
import com.module.testonline.dao.TestPaperDao;
import com.module.testonline.entity.TestPaper;


/**
 * @Description  学生系统管理服务
 * @Author  yuanzhonglin
 * @Date  2016年7月12日 
 */
@Service
@Transactional(readOnly = true)
public class StudentManageService {
				
		@Autowired
		private static StudentDao studentDao = SpringContextHolder.getBean(StudentDao.class);

		@Autowired
		private static TestPaperDao testPaperDao = SpringContextHolder.getBean(TestPaperDao.class);
		
		/**
		 * 学生个人中心页面初始化
		 * @date 2016-7-12
		 */
		@Transactional(readOnly=false)
		public static List<UserDetail> querUserDetail(String userId){
				List<UserDetail> userDetail =new ArrayList<UserDetail>();
				try {
					userDetail = studentDao.findList(new UserDetail(userId));
				} catch (Exception e) {
					e.getMessage();
				}
				return userDetail;
		}
		
		/**
		 * 根据用户ID获取用户详情，用户修改密码初始化页面展示
		 * @date 2016-7-15
		 */
		
		@Transactional(readOnly=true)
		public  static UserDetail getUserDetailByUserId(){
				String userId = UserUtils.getUser().toString();
				UserDetail userDetail = new UserDetail();
				
				try {
					userDetail = UserUtils.getUserDetail(userId);
				} catch (Exception e) {
					e.getMessage();
				}
				return userDetail;
		}
		/**
		 * 学生个人信息修改
		 * @date 2016-7-14
		 */
		@Transactional(readOnly=false)
		public void updateUserDetail(UserDetail userDetail){
			studentDao.update(userDetail);
		}
		
		@Transactional(readOnly=true)
		public List<TestPaper> queryMyErrorNotes(int page,TestPaper testPaper){
			int pageSize = 10;
		    PageHelper.startPage(page,pageSize);  //拦截器分页开始
			List<TestPaper> list = testPaperDao.queryMyErrorNotes(testPaper);
			return list;
		}
		
		/**
		 * 将错题加入错题笔记中
		 */
		@Transactional(readOnly=false)
		public void updateErrorNotes(TestPaper testPaper){
			testPaperDao.updateErrorNotes(testPaper);
		}
		
		@Transactional(readOnly=false)
		public void deleteErrorQuestion(TestPaper testPaper){
			testPaperDao.deleteErrorQuestion(testPaper);
		}
		
		@Transactional(readOnly=true)
		public List<UserDetail> queryAllStudent(UserDetail userdetail){
			Pagination pager = PageContext.getPageContext();  //分页
			int pageNo = pager.getCurrentPage();
			int pageSize = pager.getPageSize();
			PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
			List<UserDetail> list = studentDao.queryAllStudent(userdetail);
			return list;
		}
		@Transactional(readOnly=true)
		public List<UserDetail> queryAllStudentBySchoolId(UserDetail userDetail){
			Pagination pager = PageContext.getPageContext();  //分页
			int pageNo = pager.getCurrentPage();
			int pageSize = pager.getPageSize();
			PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
			List<UserDetail> list = studentDao.queryAllStudent(userDetail);
			return list;
		}
}
