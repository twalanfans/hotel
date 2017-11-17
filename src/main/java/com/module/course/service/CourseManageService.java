package com.module.course.service;

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
import com.module.course.dao.CourseDao;
import com.module.course.dao.CourseNoteDao;
import com.module.course.entity.Course;
import com.module.course.entity.CourseNotes;
import com.module.course.util.CourseUtils;
import com.module.sys.utils.UserUtils;

/**
 * @Description  课程管理服务Service
 * @Author  yuanzhonglin
 * @Date  2016年7月12日 
 */
@Service("CourseManageService")
@Transactional(readOnly=true)
public class CourseManageService {

	@Autowired
	private CourseDao courseDao;
	
	/**
	 * 老师的课程列表
	 * @param course
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly=false)
	public Page queryTeacherCourse(Course course) {
			Pagination pager = PageContext.getPageContext();  //分页
			int pageNo = pager.getCurrentPage();
		    int pageSize = pager.getPageSize();
		    PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
		    
		    List<Course> list = CourseUtils.queryTeacherCourse(course);
			PageInfo pageinfo = new PageInfo(list);
			int total = (int) (long)pageinfo.getTotal();
			List<Course> list1=pageinfo.getList();
			List<Course> list2= new ArrayList<Course>();
			
			for(int i=0;i<list1.size();i++){
				Course course1 =list1.get(i);
				list2.add(course1);
			}
			Page page = new Page(pageinfo.getPageNum(), pageinfo.getPageSize(),pageinfo.getPages(),total,list2);
			return page;
	}
	
	@Transactional(readOnly=true)
	public List<Course> queryTeacherCommCourse(String isPage,Course course){
		if(isPage.equals("1")){
			Pagination pager = PageContext.getPageContext();  //分页
			int pageNo = pager.getCurrentPage();
			int pageSize = pager.getPageSize();
			PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
		}
		List<Course> list = courseDao.queryCommonCourse(course);
		return list;
	}
	@Transactional(readOnly=true)
	public List<Course> queryUserCourse(Course course) {
		Pagination pager = PageContext.getPageContext();  //分页
		int pageNo = pager.getCurrentPage();
	    int pageSize = pager.getPageSize();
	    PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
	    List<Course> list = queryStudentCourse(course);
		return list;
	}
	public List<Course> queryStudentCourse(Course course){
		CourseDao courseDao = SpringContextHolder.getBean(CourseDao.class);
		List<Course> list = courseDao.queryCourseByUserId(course);
		return list;
	}
	
	/**
	 * 查询课程名称是否已存在
	 */
	@SuppressWarnings("rawtypes")
	public static List checkIsExist(){
		CourseDao courseDao = SpringContextHolder.getBean(CourseDao.class);
		List list = courseDao.checkIsExist();
		return list;
	}
	
	@Transactional(readOnly=false)
	public static int createCourse(Course course){
		CourseUtils cu =new CourseUtils();
		int ret = cu.insertCourse(course);
		return ret;
	}
	
	@Transactional(readOnly=false)
	public int polishCourse(Course course) 	{
		CourseUtils cu =new CourseUtils();
		int ret = cu.updateCourseCompleteStatus(course);
		return ret;
	}
	@Transactional(readOnly=false)
	public int unPolishCourse(Course course) 	{
		int ret=0;
		/*course.setIsComplete("9");*/
		
		CourseUtils cu =new CourseUtils();
		ret = cu.updateCourseCompleteStatus(course);
		
		return ret;
	}
	//课程审核完成
	@Transactional(readOnly=false)
	public int auditCourseOk(Course course) 	{
		int ret=0;
		course.setStatus("0");
		CourseUtils cu =new CourseUtils();
		ret = cu.updateCourseCompleteStatus(course);
		
		return ret;
	}
	
//删除课程
	@Transactional(readOnly=false)
	public int deleteCourse(String courseId) 	{
		int ret=0;
		Course course = new Course();
		course.setCourseId(courseId);
		course.setStatus("0");//0代表逻辑删除
		try {
			 CourseUtils cu =new CourseUtils();
			 ret = cu.updateCourseCompleteStatus(course);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return ret;
	}
	@Transactional(readOnly=false)
	public static void updateCourse(Course course) 	{
		 CourseUtils cu =new CourseUtils();
		 cu.updateCourse(course);
	}
	@Transactional(readOnly=true)
	public List<Course> findCourseDetail(Course course){
		CourseDao courseDao = SpringContextHolder.getBean(CourseDao.class);
		String courseId= course.getCourseId();
		List<Course> list  = courseDao.fetchCourseById(courseId);	
		return list;
	}
	
	/**
	 * 学生课程学习初始化-查询课程笔记
	 * @date 2016-7-20
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Transactional(readOnly=false)
	public List<CourseNotes> queryNotes(CourseNotes courseNotes) {
		CourseNoteDao courseNoteDao = SpringContextHolder.getBean(CourseNoteDao.class);
		List notesList= courseNoteDao.queryNotes(courseNotes);
		return notesList;
	}
	/**
	 * 学生添加课程笔记
	 * @date 2016-7-20
	 */
	@Transactional(readOnly=false)
	public void insertNotes(CourseNotes courseNotes) {
		CourseNoteDao courseDao = SpringContextHolder.getBean(CourseNoteDao.class);
		courseDao.saveCourseNotes(courseNotes);
	}
	
	/**
	 * 判断该课程是否为该教师所建
	 * @date 2016-7-29
	 */
	public static boolean courseIsOwner(String userId,String courseId){
		CourseDao courseDao = SpringContextHolder.getBean(CourseDao.class);
		List<Course> lc = courseDao.fetchCourseById(courseId);
		if(lc==null ||lc.isEmpty()){
			return false;
		}
		for(int i=0;i<lc.size();i++){
			Course course= new Course();
			course= lc.get(i);
			if(userId.equals(course.getTeacherId())){
				return true;
			}
		}
		return false;
	}
	
}
