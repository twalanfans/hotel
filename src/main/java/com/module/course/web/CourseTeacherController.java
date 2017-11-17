package com.module.course.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.common.config.Global;
import com.common.models.Page;
import com.common.utils.DateUtils;
import com.common.utils.JsonUtil;
import com.common.utils.NumUtil;
import com.common.utils.SpringContextHolder;
import com.common.utils.UserAgentUtils;
import com.common.web.BaseController;
import com.github.pagehelper.PageInfo;
import com.module.course.dao.KnowledgeDao;
import com.module.course.entity.Course;
import com.module.course.entity.CourseFile;
import com.module.course.entity.CourseFileattach;
import com.module.course.entity.CoursefileKnowledge;
import com.module.course.entity.KnowledgeItem;
import com.module.course.service.CourseFileService;
import com.module.course.service.CourseFileattachService;
import com.module.course.service.CourseManageService;
import com.module.course.service.CoursefileKnowledgeService;
import com.module.course.service.KnowledgeItemService;
import com.module.course.util.CourseUtils;
import com.module.course.util.KnowledgeItemUtils;
import com.module.course.util.ReadKnowledgeFromExcel;
import com.module.sys.utils.FileDownAndUpload;
import com.module.sys.utils.UserUtils;

/**
 * 老师的课程Controller
 * @author  
 * @date 2016-7-25
 */
@Controller
public class CourseTeacherController extends BaseController{
		
	@Autowired
	private CourseFileService courseFileService;
	
	@Autowired
	private CoursefileKnowledgeService coursefileKnowledgeService;
	
	@Autowired
	private CourseManageService courseManageService;
	
	@Autowired
	private CourseFileattachService courseFileattachService;
	
	/**
	 * 老师的所有课程列表
	 */
	@RequestMapping(value="${adminPath}/course/teacherCoursePage")
	public String queryTeacherCourse(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String courseName = request.getParameter("courseName")==null?"": request.getParameter("courseName");
		courseName=new String(courseName.getBytes("ISO8859_1"),"UTF-8");
		String userId = UserUtils.getUser().toString();
		Course course = new Course();
			course.setUserId(userId);
			course.setCourseName(courseName);
		request.setAttribute("courseName", courseName);
		Page page = courseManageService.queryTeacherCourse(course);
		request.setAttribute("courseList", page);
		return "modules/sys/teacher/owncenter/teacher_mycourse_query";
	}
	/**
	 * 获取老师所有课程列表(本页面)
	 */
	@RequestMapping(value="/course/teacherCourseList")
	public String queryTeacherCourseList(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();	
		try{
			 List<Course> courseList = CourseUtils.queryTeacherCourse(new Course(UserUtils.getUser().toString()));
			 map.put("success", courseList); 
		}catch (Exception e){
  			e.printStackTrace();
  			return renderString(response, "error:获取教师课程列表失败");
  		}
    	return renderString(response, map);
	}
	
    /**
     * 老师课程课件上传页面初始化
     * 2016-7-25
     */
    @RequestMapping(value = "${adminPath}/uploadFile/queryCourseFilePage")
    public String fetchMyCourseFile(HttpServletRequest request) throws Exception{
		String courseId = request.getParameter("courseId")==null?"":request.getParameter("courseId");
		String courseType = request.getParameter("courseType")==null?"":request.getParameter("courseType");
		String fileName = request.getParameter("fileName")==null?"":request.getParameter("fileName");
		fileName=new String(fileName.getBytes("ISO8859_1"),"UTF-8");
		CourseFile courseFile = new CourseFile();
			courseFile.setCourseId(courseId);
			courseFile.setCourseType(courseType);
			courseFile.setFileName(fileName);
		List<CourseFile> fieList = courseFileService.courseFileListByCourseId(courseFile);
		List<Course> courseList = CourseUtils.queryTeacherCourse(new Course(UserUtils.getUser().toString()));
		request.setAttribute("pageInfo",new PageInfo<CourseFile>(fieList));
		request.setAttribute("courseList", courseList);
		request.setAttribute("courseId", courseId);
		request.setAttribute("courseType", courseType);
		request.setAttribute("fileName", fileName);
    	return "modules/sys/teacher/owncenter/course_file_manage";
    }
	
	@RequestMapping(value="course/addCourseFile")
	public String addCourseFile(HttpServletRequest request) throws Exception{
		String courseId = request.getParameter("courseId")==null?"":request.getParameter("courseId");
		String courseName = request.getParameter("courseName")==null?"":request.getParameter("courseName");
		courseName=new String(courseName.getBytes("ISO8859_1"),"UTF-8");
		request.setAttribute("courseId", courseId);
		request.setAttribute("courseName", courseName);
		return "modules/sys/teacher/owncenter/teacher_upload_courseFile";
	}

	/**
	 * 上传微课保存
	 * @date 2016-7-15
	 */
	@RequestMapping(value="uploadFile/saveCourseFile")
	public String uploadFile(HttpServletRequest request,HttpServletResponse response){
	    Map<String, Object> map = new HashMap<String, Object>();
	    String cfName=request.getParameter("fileName");
		String courseId= request.getParameter("courseId");
		String videoIntroduce = request.getParameter("videoIntroduce");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
        MultipartFile file = multipartRequest.getFile("fileToUpload"); 
        MultipartFile imgfile = multipartRequest.getFile("spfm"); 
		CourseFile cf= new CourseFile();
			cf.setCourseId(courseId);
			cf.setCourseType("1");
			cf.setVideoIntroduce(videoIntroduce);
			cf.setCreateTime(DateUtils.getCurrDate("yyyy-MM-dd"));
			cf.setStatus("0");
		try {
			if(	imgfile != null && imgfile.getSize()>0) {
				CourseFile cfnew = savaToTargetFile(imgfile,3,cfName);
				cf.setVideoImg(cfnew.getFilePath());
			}
			if (file != null && file.getSize()>0){
				CourseFile cfnew = savaToTargetFile(file,1,cfName);
				cf.setFileName(cfName+cfnew.getFileType());
				cf.setFileSize(String.valueOf(NumUtil.divideNumber(file.getSize(), 1024*1024))+"MB");
				cf.setFilePath(cfnew.getFilePath());
			}
			int ret = CourseFileService.saveCourseFile(cf);
			if(ret>0){
				map.put("status", "success");
			}else{
				map.put("status", "error");			
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "error");
		}
		return ajaxHtml(JsonUtil.getJsonString4JavaPOJO(map), response);
	}
	
	public CourseFile savaToTargetFile(MultipartFile file,int type,String fileName) throws IOException{
		String orgFileName = file.getOriginalFilename().equals("")?fileName: file.getOriginalFilename();
		String subDir;
		if(type ==1){
			subDir="courseFile";
		}else if(type==2){
			subDir="knowledgeItem";
		}else if(type==3){
			subDir="img";
		}else{
			subDir="other";
		}
		String bigRealFilePath = FileDownAndUpload.uploadFile(orgFileName, subDir, file);
		String fileType = orgFileName.substring(orgFileName.lastIndexOf("."), orgFileName.length());
		CourseFile cf = new CourseFile();
			cf.setFilePath(bigRealFilePath);
			cf.setFileType(fileType);
		return cf;
	}
	
	 /**
     * 老师删除课件
     * 2016-7-29
     */
    @RequestMapping(value = "courseFile/deleteCourseFile")
    public String deleteCourseFile(HttpServletRequest request, HttpServletResponse response){
    	String fileId = request.getParameter("fileId");
		String fileIdList[] = fileId.split(",");
		try {
			String endpoint = Global.getConfig("endpoint");
			String accessKeyId = Global.getConfig("accessKeyId");
			String accessKeySecret = Global.getConfig("accessKeySecret");
			String bucketName = Global.getConfig("bucketName");
			// 创建OSSClient实例
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			CourseFile coursefile = new CourseFile();
			for(int i=0; i<fileIdList.length; i++){
				coursefile.setFileId(fileIdList[i]);
				List<CourseFile> list = courseFileService.fetchCourseFileById(coursefile);
				if(list.size()!=0){
					coursefile = list.get(0);
					String filePath = coursefile.getFilePath();
					if(filePath!=null && !("").equals(filePath)){
						ossClient.deleteObject(bucketName,filePath);
					}	
				}
				courseFileService.deleteCourseFileById(fileIdList[i]);
			}
			ossClient.shutdown();
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
    }

	 /**
     * 老师查看课件详情
     * 2016-7-29
     */
    @RequestMapping(value = "courseFile/fetchCourseFileDetail")
	public String fetchCourseFileDetail(@RequestBody CourseFile cf,HttpServletResponse response){
		List<CourseFile> cfList = courseFileService.fetchCourseFileById(cf);
		cf = cfList.get(0);
		return  renderString(response, cf);
	}
	
	/**
     * 老师修改课件
     * 2016-7-29
     */
	@RequestMapping(value="courseFile/updateCourseFile")
	public String updateCourseFileDetailAttach(HttpServletRequest request,HttpServletResponse response)throws Exception{
			String cfName=request.getParameter("fileName");
			String courseId= request.getParameter("courseId");		
			String videoIntroduce = request.getParameter("fileIntroduce");
			String fileId = request.getParameter("fileId");
			if(checkSameCourseFile(courseId,cfName,fileId)){
				return renderString(response,"exist");
			}
			CourseFile cf= new CourseFile();
				cf.setFileId(fileId);
				cf.setVideoIntroduce(videoIntroduce);
				cf.setFileName(cfName);
				cf.setCreateTime(DateUtils.getCurrDate("yyyy-MM-dd"));
			try {
				courseFileService.updateCourseFile(cf);
				return renderString(response, "success");
			} catch (Exception e) {
				e.printStackTrace();
				return renderString(response, "error");
			}
		}
	
     /**
     * 老师新建课程
     * 2016-7-29
     */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="${adminPath}/course/createCourse")
	public String createCourse(@RequestBody Course course,HttpServletResponse response){
		String userId = UserUtils.getUser().toString();
		course.setStatus("1");
		course.setTeacherId(userId);
		course.setCreateTime(Timestamp.valueOf(DateUtils.getDateTime()));
		try {
				List list = CourseManageService.checkIsExist();
				for(int i=0;i<list.size();i++){
					String courseName1=list.get(i).toString(); 
					if(courseName1.equals(course.getCourseName())){
						return renderString(response, "exist");
					}
				}
				CourseManageService.createCourse(course);
				return renderString(response,"success");	
			} catch(Exception e){
				e.printStackTrace();
				return renderString(response,"error");	
			}
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/course/updateCourse")
	public String updateCourse(@RequestBody Course course,BindingResult result,HttpServletResponse response){
		String userId = UserUtils.getUser().toString();
		String courseName = course.getCourseName();
		course.setTeacherId(userId);
		course.setCreateTime(Timestamp.valueOf(DateUtils.getDateTime()));
		try {
				List list = CourseManageService.checkIsExist();
				for(int i=0;i<list.size();i++){
					String courseName1=list.get(0).toString(); 
					if(courseName1.equals(courseName)){
						return renderString(response, "exist");
					}
				}
				CourseManageService.updateCourse(course);
				return renderString(response, "success");
			} catch (Exception e) {
				e.printStackTrace();
				return renderString(response, "error");
			}
	}
	
	/**
     * 老师删除课程
     * 2016-7-29
     */
	@RequestMapping(value="course/deleteCourse")
	public String deleteCourse(@RequestBody Course course,HttpServletResponse response){
		String courseId = course.getCourseId();
    	try{
			courseManageService.deleteCourse(courseId);
			return renderString(response, "success");
		}catch (Exception e){
			e.printStackTrace();
       		return renderString(response,"error");
		}
	}

	@RequestMapping(value="course/courseDetail")
	public String courseDetail(@RequestBody Course course,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
    	try {
    		List<Course> list  =courseManageService.findCourseDetail(course);
			if(list== null||list.isEmpty())
				return renderString(response, "error");
			course= list.get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
    	map.put("success", course); 
    	return renderString(response, map);
}
	 /**
     * 老师课程课件上传页面初始化
     * 2016-7-25
     */
    @RequestMapping(value = "${adminPath}/uploadFile/uploadKnowledgeFile")
    public String uploadKnowledgeFile(HttpServletRequest request){
    	String courseId = request.getParameter("courseId");
    	request.setAttribute("courseId", courseId);
    	return "modules/sys/teacher/owncenter/zsdEdit";
    }
	
	/**
	 * 校验是否已导入过知识点文档
	 */
	@RequestMapping(value="uploadFile/checkKnowledgeFile")
	public String checkKnowledgeFile(HttpServletRequest request, HttpServletResponse response){
		String courseId=request.getParameter("id");
		if(KnowledgeItemUtils.getCourseKnowLedgeCount(courseId)>0){ 
    		return renderString(response,"no");
		}
		return renderString(response,"ok");
	}
	
	/**
	 * 上传知识点文件并保存到知识点表中
	 */
	@RequestMapping(value="uploadFile/saveKnowledgeFile")
	public String uploadKnowledgeFile(HttpServletRequest request,HttpServletResponse response,MultipartHttpServletRequest mulRequest) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
        MultipartFile file = multipartRequest.getFile("file"); 
        String courseId=request.getParameter("id");
        List<KnowledgeItem> knowList = KnowledgeItemUtils.getCourseKnowledgeItemAll(courseId);
        if(knowList.size()>0){
        	KnowledgeItem item = knowList.get(0);
        	String filePath = item.getFilePath();
        	if(filePath!=null&& !filePath.equals("")){
        		FileDownAndUpload.deleteFile(filePath);
        	}
        	KnowledgeItemUtils.deleteKnowledgeItemByCourseId(courseId);
        }
        CourseFile cfnew = savaToTargetFile(file,2,"");
		if(cfnew == null){
			map.put("error", "文件写入错误！");
			return renderString(response, map);
		}
		try {
			ReadKnowledgeFromExcel rkf = new ReadKnowledgeFromExcel();
			List<KnowledgeItem> list =rkf.getExcelInfo(cfnew.getFilePath() ,courseId);
			KnowledgeItemUtils kiu = new KnowledgeItemUtils();
			kiu.addKnowledgeFromExcel(list,courseId,UserUtils.getUser().toString());
			map.put("status", "success");
		}catch (Exception e) {
			e.printStackTrace();
			map.put("error","处理知识点批量到如文件异常！");
    		return renderString(response,map);
		}
		return ajaxHtml(JsonUtil.getJsonString4JavaPOJO(map), response);
	}

	/**
     * 老师删除知识点
     * 2016-7-29
     */
	@RequestMapping(value="/knowledge/deleteKnowledge")
	public String deleteKnowledge(@RequestBody KnowledgeItem ki,HttpServletRequest request,HttpServletResponse response){
			
		int knowledgeItemId=0;
		Map<String, Object> map = new HashMap<String, Object>();
		List<KnowledgeItem> list= new ArrayList<KnowledgeItem>();
		String userId = UserUtils.getUser().toString();

		knowledgeItemId = ki.getId();
    	KnowledgeItemUtils kiu = new KnowledgeItemUtils();
    	list = kiu.getKnowledgeById(knowledgeItemId);
    	if(list==null ||list.isEmpty()){
    		map.put("error","知识点不存在！");
    		return renderString(response,map );
    	}
    	KnowledgeItem item = list.get(0);
		String courseId= item.getCourseId();
		//判断该课程是否为该教师所建
    	if(!(CourseManageService.courseIsOwner(userId,courseId))){
			map.put("error","没有权限！");
    		return renderString(response,map );
    	}
    	KnowledgeItemService kis= new KnowledgeItemService();
    	try {
    		 list= new ArrayList<KnowledgeItem>();
    		 kiu.getKnowledgeListAll(list,courseId,knowledgeItemId,item.getLevel());
    		 list.add(item);
    		 for(int i=0;i<list.size();i++){
    			 kis.deleteKnowledgeItemById(list.get(i).getId());
    		 }
    	} catch (Exception e) {
			e.printStackTrace();
			map.put("error","删除知识点出现异常！");
    		return renderString(response,map );
		}
    	return renderString(response, "success");
}

	/**
     * 老师新建知识点
     * 2016-7-29
     */
	@RequestMapping(value="/knowledge/addKnowledge")
	public String addKnowledge(@RequestBody KnowledgeItem ki,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		KnowledgeItem item = new KnowledgeItem();
		
		String userId = UserUtils.getUser().toString();
    	String courseId = ki.getCourseId();
		//判断该课程是否为该教师所建
    	if(!(CourseManageService.courseIsOwner(userId,courseId))){
    		map.put("error","没有权限！");
    		return renderString(response,map );
    	}
    	try {
    		KnowledgeItemUtils kiu = new KnowledgeItemUtils();
    		item=ki;
    		item.setCreateBy(userId);
			item.setCreateTime(DateUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
    		int ret =kiu.addKnowledgeItemSingle(item);
			if(ret<=0){
				map.put("error","增加知识点失败！");
	    		return renderString(response,map );
			}
			item.setPId(item.getParentId());
			item.setName(item.getTitle());
			map.put("success", item);
    		return renderString(response,map );
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error","增加知识点出现异常！");
    		return renderString(response,map );
		}
}
	/**
     * 老师更新知识点
     * 2016-7-29
     */
	@RequestMapping(value="/knowledge/updateKnowledge")
	public String updateKnowledge(HttpServletRequest request, HttpServletResponse response){		
		String knowledgeId = request.getParameter("id")==null?"":request.getParameter("id");
		String title = request.getParameter("title")==null?"":request.getParameter("title");
		String userId = UserUtils.getUser().toString();
    	try {
    		KnowledgeDao knowledgeDao = SpringContextHolder.getBean(KnowledgeDao.class);
    		KnowledgeItem item = new KnowledgeItem();
				item.setUpdateBy(userId);
				item.setUpdateTime(DateUtils.getDateTime());
				item.setId(Integer.parseInt(knowledgeId));
				item.setTitle(title);
				knowledgeDao.updateKnowledgeItem(item);
			return renderString(response, "success");
		}catch (Exception e){
			e.printStackTrace();
    		return renderString(response,"error");
		}
}
	/**
     * 老师查看某课程ID对应知识点列表,返回格式为json array
     * 2016-7-29
     */
	@RequestMapping(value="/knowledge/fetchKnowledgeList")
	public String fetchKnowledgeTree(@RequestBody Course course,HttpServletRequest request,HttpServletResponse response){
			
		Map<String, Object> map = new HashMap<String, Object>();
		String courseId = course.getCourseId();
		
		List<KnowledgeItem> list= new ArrayList<KnowledgeItem>();
		list = KnowledgeItemUtils.getCourseKnowledgeItemAll(courseId);
		if(list== null || list.isEmpty()){
			list = new ArrayList<KnowledgeItem>();
		}
		KnowledgeItemService  treeBuilder = new KnowledgeItemService();	
		List<KnowledgeItem> tree= new ArrayList<KnowledgeItem>();
        tree = treeBuilder.merge(list);
        map.put("success", tree); 
    	return renderString(response, map);
}
	/**
     * 老师查看某课程ID对应知识点列表,返回list,暂时不用
     * 2016-7-29
     */
	@RequestMapping(value="/knowledge/fetchKnowledgeListNew")
	public String fetchKnowledgeListNew( @RequestBody KnowledgeItem ki,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		String userId = UserUtils.getUser().toString();

		String courseId = ki.getCourseId();
		int parentId = ki.getParentId();
		//判断该课程是否为该教师所建
		if(!(CourseManageService.courseIsOwner(userId,courseId))){
			map.put("error","没有权限！");
    		return renderString(response,map);
    	}
		List<KnowledgeItem> list= new ArrayList<KnowledgeItem>();
		try {
	    	KnowledgeItemUtils kiu = new KnowledgeItemUtils();
	    	list=kiu.getKnowledgeListByParentId(courseId,parentId);
	    	if(list==null ||list.isEmpty()){
	    		map.put("error","知识点不存在！");
	    		return renderString(response,map);
	    		}
	       } catch (Exception e) {
			e.printStackTrace();
			map.put("error","知识点查询异常！");
    		return renderString(response,map);	
    		}
		map.put("success", list); 
    	return renderString(response, map);
}
	/**
     * 老师查看知识点详情
     * 2016-7-29
     */
	@RequestMapping(value="/knowledge/fetchKnowledgeDetail")
	public String fetchKnowledgeDetail(@RequestBody KnowledgeItem ki,HttpServletResponse response){
			
		int KnowledgeItemId=0;
		Map<String, Object> map = new HashMap<String, Object>();

		List<KnowledgeItem> list= new ArrayList<KnowledgeItem>();
		try {
	    	KnowledgeItemUtils kiu = new KnowledgeItemUtils();
	    	list = kiu.getKnowledgeById(KnowledgeItemId);
	    	if(list==null ||list.isEmpty()){
	    		map.put("error", "知识点不存在！"); 
	    		return renderString(response, map);
	    	}
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
    	map.put("success", list.get(0)); 
    	return renderString(response, map);
	}
	
	//课件知识点关联列表
	@RequestMapping(value="/courseFile/fetchKnowledgeLink")
	public String fetchCourseFileKnowledgeLinkList(@RequestBody CourseFile cf,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		List<KnowledgeItem> list= new ArrayList<KnowledgeItem>();
		String courseFileId = cf.getFileId();
		String courseId = cf.getCourseId();
		list= KnowledgeItemUtils.getKnowledgeItemListByCourseFileId(courseId,courseFileId);
		if(list==null ||list.isEmpty()||list.size()==0){
			list = new ArrayList<KnowledgeItem>();
			map.put("error","当前课程没有知识点，请先创建知识点再进行关联！");
			return renderString(response, map);	
		}
		List<KnowledgeItem> listTree= new ArrayList<KnowledgeItem>();
		KnowledgeItemService  kis = new KnowledgeItemService();
		listTree = kis.merge(list);
		map.put("success",listTree);
    	return renderString(response, map);		
	}

	//判断同一课程下是否存在同名课件
	public boolean checkSameCourseFile(String CourseId,String cfName,String fileId){	
		boolean result = false;
		List<CourseFile> list = courseFileService.courseFileListByCourseId(new CourseFile());
		if(list.isEmpty()){
			result = false;
		}else{
			for(int i=0;i<list.size();i++){
				String cfName1=list.get(i).getFileName().trim();
				cfName= cfName.trim();
				if(cfName1.equals(cfName)){
					result = true;
				}
			}
		}	
		return result;
	}
	
	
	/**************************************************案例库。。图片库**************************/
	//案例库、图片库初始化
	@RequestMapping(value="courseFile/dataBaseQuery")
	public String dataBaseQuery(HttpServletRequest request) throws Exception{
		String isCommon = request.getParameter("is_cmn");
		String courseName = request.getParameter("courseName")==null?"":request.getParameter("courseName");
		courseName=new String(courseName.getBytes("ISO8859_1"),"UTF-8");
		String courseId = request.getParameter("cos_id")==null?"":request.getParameter("cos_id");
		String type = request.getParameter("type");	//2-案例库   3-图片库 4-标准工作流程库
		CoursefileKnowledge file = new CoursefileKnowledge();
			file.setDataType(type);
			file.setCourseId(courseId);
		List<CoursefileKnowledge> fileClass = coursefileKnowledgeService.showFileClass(file);
		request.setAttribute("fileClassList", fileClass);
		request.setAttribute("dataType", type);
		request.setAttribute("isCommon", isCommon);
		request.setAttribute("courseName", courseName);
		request.setAttribute("courseId", courseId);
		request.setAttribute("classId","");
		request.setAttribute("fileNum","0");
		return "modules/sys/teacher/commresource/database_file_query";
	}
	
	//展示类别下的所有资料文件
	@RequestMapping(value="courseFile/showFileDetailPage")
	public String showFileDetail(HttpServletRequest request) throws Exception{
		String classId = request.getParameter("classId");			//操作  1：查看类下面的文件 
		String dataType = request.getParameter("dataType");
		String filePath = request.getParameter("filePath");
		filePath=new String(filePath.getBytes("ISO8859_1"),"UTF-8");
		String fileName = request.getParameter("fileName");
		fileName=new String(fileName.getBytes("ISO8859_1"),"UTF-8");
		String userId = request.getParameter("u-si");
		String user = UserUtils.getUser().toString();
		if(userId.equals(user)){
			request.setAttribute("isShow","yes");
		}else{
			request.setAttribute("isShow","no");
		}
		CourseFileattach file = new CourseFileattach();
			file.setClassId(classId);
			file.setFileName(fileName);
		List<CourseFileattach> fileList = courseFileattachService.listFileattach(file);
		request.setAttribute("pageInfo",new PageInfo<CourseFileattach>(fileList));
		request.setAttribute("dataType",dataType);
		request.setAttribute("classId",classId);
		request.setAttribute("fileName",fileName);
		request.setAttribute("userId",userId);
		request.setAttribute("classFilePath",filePath);
		request.setAttribute("fileNum",fileList.size());
		return "modules/sys/teacher/commresource/show_file_query";
	}
	
	//新建文件夹分类
	@RequestMapping(value="courseFile/addClassFile")
	public String addClassFile(HttpServletRequest request,HttpServletResponse response){
		String classId = request.getParameter("classId");			
		String courseId = request.getParameter("courseId");			
		String className = request.getParameter("className");			
		String dataType = request.getParameter("dataType");	
		String level = request.getParameter("level");	
		CoursefileKnowledge file = new CoursefileKnowledge();
			file.setClassId(classId);
			file.setClassName(className);
			file.setDataType(dataType);
			file.setCourseId(courseId);
			file.setLevel(level);
			file.setCreateUser(UserUtils.getUser().toString());
			try {
				coursefileKnowledgeService.addClassFile(file);
			} catch (Exception e) {
				e.printStackTrace();
				return renderString(response, "error");
			}
		return renderString(response, file);
	}
	
	@RequestMapping(value="courseFile/uploadAttachFile")
	public String uploadAttachFile(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String classId = request.getParameter("classId");			
		String filePath = request.getParameter("filePath");		
		String dataType = request.getParameter("dataType");	
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String realFilePath="";
			String fileSize = "";
			String orgFileName="";
			if(dataType.equals("3")){
				MultipartFile mfile = multipartRequest.getFile("imgFile"); 
				fileSize = String.valueOf(NumUtil.divideNumber(mfile.getSize(), 1024*1024))+"MB";
				String subDir = "imgBase/"+filePath;
				orgFileName = mfile.getOriginalFilename();
				realFilePath = FileDownAndUpload.uploadAttachFile(orgFileName, subDir, mfile);
			}else{
				MultipartFile mfile = multipartRequest.getFile("wordFile"); 
				fileSize = String.valueOf(NumUtil.divideNumber(mfile.getSize(), 1024*1024))+"MB";
				String subDir = "caseBase/"+filePath;
				orgFileName = mfile.getOriginalFilename();
				realFilePath = FileDownAndUpload.uploadAttachFile(orgFileName, subDir, mfile);	
			}
			 CourseFileattach cfa = new CourseFileattach();
			 	cfa.setClassId(classId);
			 	cfa.setFileName(orgFileName);
				cfa.setFileType((orgFileName.substring(orgFileName.lastIndexOf(".")+1,orgFileName.length())).toLowerCase());
				cfa.setFilePath(realFilePath);
				cfa.setFileSize(fileSize);
				cfa.setCreateTime(Timestamp.valueOf(DateUtils.getDateTime()));
				cfa.setCreateBy(UserUtils.getUser().toString());
			courseFileattachService.saveCourseFile(cfa);
			map.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "error");
		}
		return ajaxHtml(JsonUtil.getJsonString4JavaPOJO(map), response);
	}
	
	/**
	 * 删除文件夹下的所有文件
	 */
	@RequestMapping(value="courseFile/deleteAttachFileByClassId")
	public String deleteAttachFileByClassId(HttpServletRequest request,HttpServletResponse response){
		String classId = request.getParameter("classId");
	    CourseFileattach coursefileattach = new CourseFileattach();
	    	coursefileattach.setClassId(classId);
		try {		
			List<CourseFileattach> attachFile = courseFileattachService.listFileattach(coursefileattach);
			String endpoint = Global.getConfig("endpoint");
			String accessKeyId = Global.getConfig("accessKeyId");
			String accessKeySecret = Global.getConfig("accessKeySecret");
			String bucketName = Global.getConfig("bucketName");
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			if(attachFile.size()!=0){
				for(int i=0; i<attachFile.size(); i++){
					coursefileattach = attachFile.get(i);
					String filePath = coursefileattach.getFilePath();
					if(filePath!=null && !("").equals(filePath)){
						// 创建OSSClient实例
						ossClient.deleteObject(bucketName,filePath);
						courseFileattachService.deleteByPrimaryKey(coursefileattach);
					}
				}
			 }
			CoursefileKnowledge file = new CoursefileKnowledge();
				file.setClassId(classId);
			coursefileKnowledgeService.deleteClassFile(file);
			ossClient.shutdown();
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	/**
	 * 删除资料库的（单个/批量）文件
	 */
	@RequestMapping(value="courseFile/deleteAttachFileByAttachId")
	public String deleteAttachFileByAttachId(HttpServletRequest request,HttpServletResponse response){
		String fileId = request.getParameter("fileId");
		String fileIdList[] = fileId.split(",");
		try {
			String endpoint = Global.getConfig("endpoint");
			String accessKeyId = Global.getConfig("accessKeyId");
			String accessKeySecret = Global.getConfig("accessKeySecret");
			String bucketName = Global.getConfig("bucketName");
			// 创建OSSClient实例
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			CourseFileattach coursefileattach = new CourseFileattach();
			for(int i=0; i<fileIdList.length; i++){
				coursefileattach.setAttachId(fileIdList[i]);
				CourseFileattach attachFile = courseFileattachService.attachFileDetail(coursefileattach);
				String filePath = attachFile.getFilePath();
				if(filePath!=null && !("").equals(filePath)){
					ossClient.deleteObject(bucketName,filePath);
				}	
				courseFileattachService.deleteByPrimaryKey(coursefileattach);
			}
			ossClient.shutdown();
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	/**
	 * 修改文件分类命名
	 */
	@RequestMapping(value="courseFile/updateClassFileName")
	public String updateClassFileName(HttpServletRequest request, HttpServletResponse response){
		String classId = request.getParameter("classId");
		String className = request.getParameter("className");
		CoursefileKnowledge file = new CoursefileKnowledge();
			file.setClassId(classId);
			file.setClassName(className);
		try {
			coursefileKnowledgeService.updateClassFileName(file);
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		} 
	}
	
	@RequestMapping(value="courseFile/downLoadAttachFile")
	public void downLoadAttachFile(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String fileId = request.getParameter("fileId");
		CourseFileattach coursefileattach = new CourseFileattach();
			coursefileattach.setAttachId(fileId);
		try {
			CourseFileattach attachFile = courseFileattachService.attachFileDetail(coursefileattach);
				String endpoint = Global.getConfig("endpoint");
				String accessKeyId = Global.getConfig("accessKeyId");
				String accessKeySecret = Global.getConfig("accessKeySecret");
				String bucketName = Global.getConfig("bucketName");
				String key = attachFile.getFilePath();
				// 创建OSSClient实例
				OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
				OSSObject ossObject = ossClient.getObject(bucketName, key);
				String length = String.valueOf(ossObject.getObjectMetadata().getContentLength());
				// 读Object内容
				request.setCharacterEncoding("UTF-8");
				response.setContentType("application/octet-stream; charset=utf-8");
				response.setHeader("Content-disposition", "attachment;" + UserAgentUtils.encodeFileName(request, attachFile.getFileName()));
				response.setHeader("Content-Length",length);
				BufferedInputStream bis = new BufferedInputStream(ossObject.getObjectContent());
				BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
				bis.close();
				bos.close();
				// 关闭client
				ossClient.shutdown();
		} catch (OSSException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
	
}
