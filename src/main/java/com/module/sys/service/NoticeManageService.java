package com.module.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.noo.pagination.page.PageContext;
import org.noo.pagination.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.common.models.Page;
import com.common.utils.SpringContextHolder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.module.sys.dao.SysNoticeDao;
import com.module.sys.entity.Notice;

/**
 * 公告管理服务类
 * @author yuanzhonglin
 * @date 2016-7-11
 */
@Service
@Transactional(readOnly = true)
public class NoticeManageService {
	
		@Autowired
		private SysNoticeDao sysNoticeDao;
		
		/**
		 * 系统公告信息列表
		 * @author yuanzhonglin
		 * @date 2016-7-11
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Transactional(readOnly=false)
		public Page showAllNotice(Notice notice){
			SysNoticeDao sysNoticeDao = SpringContextHolder.getBean(SysNoticeDao.class);
			Pagination pager = PageContext.getPageContext();  //分页
			int pageNo = pager.getCurrentPage();
		    int pageSize = pager.getPageSize();
			PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
			
			List<Notice> noticeList = sysNoticeDao.findAllList(notice);  //查询公告
			PageInfo pageinfo = new PageInfo(noticeList);
			int total = (int) (long)pageinfo.getTotal();
			List<Notice> list1=pageinfo.getList();
			List<Notice> list2= new ArrayList<Notice>();
			for(int i=0;i<list1.size();i++)
			{
				Notice noticeLis =list1.get(i);
				list2.add(noticeLis);
			}
			Page page = new Page(pageinfo.getPageNum(), pageinfo.getPageSize(),pageinfo.getPages(),total,list2);
			return page;
		}
		
		/**
		 * 系统公告信息生成
		 * @date 2016-7-11
		 */
		@Transactional(readOnly=false)
		public void insertNotice(Notice notice){
			SysNoticeDao sysNoticeDao = SpringContextHolder.getBean(SysNoticeDao.class);
			try {
				sysNoticeDao.insert(notice);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 1.下载文件校验是否同步或是否存在
		 * 2.查看公告内容详情
		 * 2016-8-3
		 */
		@Transactional(readOnly=false)
		public List<Notice> getNoticeDetail(Notice notice){
			SysNoticeDao sysNoticeDao = SpringContextHolder.getBean(SysNoticeDao.class);
			List<Notice> noticeList = sysNoticeDao.getNoticeDetail(notice);
			return noticeList;
		}
		/**
		 * 系统公告信息删除
		 * @date 2016-7-11
		 */
		@Transactional(readOnly=false)
		public void deleteNotice(Notice notice){
			SysNoticeDao sysNoticeDao = SpringContextHolder.getBean(SysNoticeDao.class);
			try {
				sysNoticeDao.delete(notice);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 系统公告信息修改
		 * @date 2016-7-11
		 */
		@Transactional(readOnly=false)
		public void updateNotice(Notice notice){
			sysNoticeDao.update(notice);
		}
		
		/**
		 * 系统公告信息状态修改
		 * @date 2016-8-15
		 */
		@Transactional(readOnly=false)
		public void updateNoticeStatus(){
			sysNoticeDao.updateNoticeStatus();
		}
}