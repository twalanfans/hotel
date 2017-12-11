package com.module.owncenter.web;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.utils.DateUtils;
import com.common.utils.RandomStringGenerator;
import com.common.utils.SpringContextHolder;
import com.common.web.BaseController;
import com.github.pagehelper.PageInfo;
import com.module.course.entity.CourseFile;
import com.module.course.service.CourseFileService;
import com.module.owncenter.service.CheckerManageService;
import com.module.sys.entity.Message;
import com.module.sys.service.LogService;
import com.module.sys.service.MessageService;
import com.module.sys.utils.UserUtils;

/**
 * @Description  审核员Controller
 * @Author  yuanzhonglin
 * @Date  2016年8月16日 
 */
@Controller
public class CheckerController extends BaseController{
	private static LogService log = SpringContextHolder.getBean(LogService.class);
	public static final String LOG_INSERT = "1"; //日志类型-增加
	public static final String LOG_DELETE = "2"; //日志类型-删除
	public static final String LOG_UPDATE = "3"; //日志类型-修改
	@Autowired
	private CheckerManageService checkerManageService;
	
	/**
	 * 查询所有课程文件
	 * 2016-8-11
	 */
	@RequestMapping(value="${adminPath}/checker/queryCheckFilePage")
	public String queryCheckFile(HttpServletRequest request) throws Exception{
		String fn = request.getParameter("fileName")==null?"":request.getParameter("fileName");
		String fileName = new String(fn.getBytes("ISO8859-1"), "UTF-8");
		String status = request.getParameter("status")==null?"":request.getParameter("status");
		CourseFile file = new CourseFile();
			file.setFileName(fileName);
			file.setStatus(status);
		List<CourseFile> fileList = checkerManageService.queryCheckFile(file);
		request.setAttribute("pageInfo", new PageInfo<CourseFile>(fileList));
		request.setAttribute("fileName", fileName);
		request.setAttribute("status", status);
		return "modules/sys/admin/checker_coursefile_query";
	}
	
	/**
	 * 文件通过审核
	 * 2016-8-16
	 */
	@RequestMapping(value="checker/checkFileStatus")
	public String checkFileStatus(HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.insertLog( "checkFileStatus", LOG_UPDATE, request); //记录管理员操作日志
		String fileId = request.getParameter("fileId");
		String userId = request.getParameter("userId");
		String fileName = request.getParameter("fileName");
		String status = request.getParameter("status")==null?"0":request.getParameter("status");
		String idList[]=fileId.split(",");
		String userList[] = userId.split(",");
		String fileList[] = fileName.split(",");
		CourseFile file = new CourseFile();
			file.setStatus(status);
		Message message = new Message();
		MessageService service =new MessageService();
		try {
			for(int i=0; i<idList.length; i++){
				file.setFileId(idList[i]);
				checkerManageService.changFileStatus(file);
				message.setMessageId(RandomStringGenerator.getRandomStringByLength(16));
				message.setFromUser(UserUtils.getUser().toString());
				message.setToUser(userList[i]);
				message.setSubject("<font style=\"font-weight: bold\">(系统通知)文件上传审核通知</font>");
				if("1".equals(status)){
					message.setAllMessage("您上传的文件《"+fileList[i]+"》已审核<font style=\"font-weight: bold; color:green\">通过</font>【系统通知，勿回】 ");
				}else{
					message.setAllMessage("您上传的文件《"+fileList[i]+"》 审核<font style=\"font-weight: bold; color:red\">未通过</font>，如有疑问，请联系管理员！【系统通知，勿回】");
				}
				message.setSendTime(Timestamp.valueOf(DateUtils.getDateTime()));
				service.sendUersMessage(message);
			}
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	@RequestMapping(value="/checker/updateFileTimeLong")
	public String updateFileTimeLong(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String fileId = request.getParameter("fileId");
		String timeLong = request.getParameter("timeLong")==null?"00:00":request.getParameter("timeLong");
		CourseFile file = new CourseFile();
			file.setFileId(fileId);
			file.setVideoTimeLong(timeLong);
		try {
			CourseFileService.updateFileTimeLong(file);
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	/**
	 * 下载待审核的课件
	 * 2016-8-17
	 */
/*	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="checker/downLoadFile")
	public String downLoadFile(HttpServletRequest request ,HttpServletResponse response){
		String fileId = request.getParameter("fileId");
		Map map =new HashMap();
		CourseFile courseFile = new CourseFile();
			courseFile.setFileId(fileId);
    	List<CourseFile> list = checkerManageService.getCourseFileDetail(courseFile);
		try {
			CourseFile file = list.get(0);
			FileOperateUtil.download(request, response, "application/octet-stream; charset=utf-8", file.getFilePath(), file.getFileName());
			return null;
		} catch (IOException e) {
			map.put("status", "error");
		}
		return ajaxJson(JsonUtil.getJsonString4JavaPOJO(map), response);
	}*/
	
	 /**获取文件大小**/
 /*   @RequestMapping(value = "checker/getFileSize")
    public String getFileSize(HttpServletRequest request, HttpServletResponse response) {
		String fileId = request.getParameter("fileId");
		CourseFile courseFile = new CourseFile();
		courseFile.setFileId(fileId);
    	List<CourseFile> list = checkerManageService.getCourseFileDetail(courseFile);
    	if(list.size() != 0){
    		CourseFile file = list.get(0);
    		File f = new File(file.getFilePath());
    		if (f.exists()){
    			Long fileLength = new File(file.getFilePath()).length();
    			return renderString(response,fileLength.toString());
    		}else{
    			return renderString(response,"0");
    		}
    	}
    	return renderString(response,"0");
	}*/
	
}
