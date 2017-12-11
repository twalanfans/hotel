package com.module.sys.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
import com.common.utils.UserAgentUtils;
import com.common.web.BaseController;
import com.module.sys.entity.Notice;
import com.module.sys.service.NoticeManageService;
import com.module.sys.utils.FileDownAndUpload;
import com.module.sys.utils.UserUtils;

/**
 * @Description  系统公告Controller
 * @Author  yuanzhonglin
 * @Date  2016年7月11日 
 */
@Controller
public class NoticeController extends BaseController {
	
	@Autowired
	private NoticeManageService noticeManageService ;
		
	/**
	 * 初始化查询系统公告列表
	 * @date 2016-7-11
	 */
	@RequestMapping(value="${adminPath}/notice/pubNoticePage")
	public String pubNotice(HttpServletRequest request) throws Exception{
		String ti = request.getParameter("title")==null?"":request.getParameter("title");
		String title = new String(ti.getBytes("ISO8859-1"), "UTF-8");
		String status = request.getParameter("status")==null?"":request.getParameter("status");   
		String startTime = request.getParameter("startTime")==null?"":request.getParameter("startTime");   
		String endTime = request.getParameter("endTime")==null?"":request.getParameter("endTime");   
		Notice notice = new Notice();
			notice.setTitle(title);
			notice.setStatus(status);
			notice.setStartTime(startTime);
			notice.setEndTime(endTime);
		Page page = noticeManageService.showAllNotice(notice);
		request.setAttribute("noticeList", page);
		request.setAttribute("title", title);
		request.setAttribute("status", status);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		return "modules/sys/admin/admin_pub_notice_query";
	}
	
	/**
	 * 接收公告
	 */
	@RequestMapping(value="${adminPath}/common/receiveNoticePage")
	public String receiveNotice(HttpServletRequest request) throws Exception{
		String ti = request.getParameter("title")==null?"":request.getParameter("title");
		String title = new String(ti.getBytes("ISO8859-1"), "UTF-8");
		String startTime = request.getParameter("startTime")==null?"":request.getParameter("startTime");   
		String endTime = request.getParameter("endTime")==null?"":request.getParameter("endTime");   
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setStartTime(startTime);
		notice.setEndTime(endTime);
		String userId = UserUtils.getUser().toString();
		if(!"superadmin".equals(userId)){
			notice.setUserId(userId);
		}
		NoticeManageService noticeManageService = new NoticeManageService();
		Page page = noticeManageService.showAllNotice(notice);
		request.setAttribute("noticeList", page);
		request.setAttribute("title", title);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		return "modules/sys/common/receive_notice_query";
	}
	
	/**
	 * 系统公告信息生成
	 * @date 2016-7-11
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="notice/insertSysNotice")
	public String insertNotice(MultipartHttpServletRequest request, HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = handler(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String target = request.getParameter("target");  //对象
		String title = request.getParameter("title");  //标题
		String notices = request.getParameter("notices");  //内容
		String userId = UserUtils.getUser().toString();
		Notice notice = new Notice();
			notice.setTitle(title);
			notice.setNotices(notices);
			notice.setPubUser(userId);
			notice.setFilePath(result.get("filePath").toString());
			notice.setFileName(result.get("fileName").toString());
			notice.setPubTime(Timestamp.valueOf(DateUtils.getDateTime()));
		try {
			notice.setTarget(target);
			noticeManageService.insertNotice(notice);
			result.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
		}
		return ajaxHtml(JsonUtil.getJsonString4JavaPOJO(result), response);
	}
	
	/**
	 * 处理文件上传
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map handler(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException{
		String folder = "pubNoticeFile";		//基础路径
		Map map =new HashMap();
		MultipartFile file = request.getFile("file");
		if (file.isEmpty()) {// step-2 判断file
			map.put("filePath", "");
			map.put("fileName", "");
			return map;
		}
		String orgFileName = file.getOriginalFilename();		//文件名 + 文件类型
		String bigRealFilePath = FileDownAndUpload.uploadFile(orgFileName, folder, file);
		map.put("filePath", bigRealFilePath);
		map.put("fileName", orgFileName);
		return map;
	}
	
	/**
	 * 下载公告附件
	 * 2016-8-3
	 */
	@RequestMapping(value="notice/downLoadFile")
	public void downLoadFile(HttpServletRequest request ,HttpServletResponse response) throws IOException{
		String noticeId = request.getParameter("noticeId");
    	Notice notice = new Notice();
    		notice.setNoticeId(noticeId);
    	List<Notice> list = noticeManageService.getNoticeDetail(notice);
		try {
			Notice file = list.get(0);
			String endpoint = Global.getConfig("endpoint");
			String accessKeyId = Global.getConfig("accessKeyId");
			String accessKeySecret = Global.getConfig("accessKeySecret");
			String bucketName = Global.getConfig("bucketName");
			String key = file.getFilePath();
			// 创建OSSClient实例
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			OSSObject ossObject = ossClient.getObject(bucketName, key);
			String length = String.valueOf(ossObject.getObjectMetadata().getContentLength());
			// 读Object内容
			request.setCharacterEncoding("UTF-8");
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-disposition", "attachment;" + UserAgentUtils.encodeFileName(request, file.getFileName()));
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
	
	 /**获取文件大小**/
	@ResponseBody
    @RequestMapping(value = "notice/getFileSize")
	public String getFileSize(HttpServletRequest request) {
		String noticeId = request.getParameter("noticeId");
    	Notice notice = new Notice();
    		notice.setNoticeId(noticeId);
    	List<Notice> list = noticeManageService.getNoticeDetail(notice);
    	if(list.size() != 0){
    		Notice file = list.get(0);
        	Long fileLength = new File(file.getFilePath()).length();
        	return fileLength.toString();
    	}
    	return (new Long(0L)).toString();
	}
	/**
	 * 系统公告信息删除
	 * @date 2016-7-11
	 */
	@RequestMapping(value="notice/deleteSysNotice")
	public String deleteNotice(HttpServletRequest request,HttpServletResponse response){
		String noticeId = request.getParameter("noticeId"); 
		String result="";
		String idList[]=noticeId.split(",");
		Notice notice = new Notice();
		try {
			for(int i=0; i<idList.length; i++){
			notice.setNoticeId(idList[i]);
			noticeManageService.deleteNotice(notice);
			}
			result = "success";
		}catch (Exception e) {
			result = "error";
		}
		return renderString(response, result);
	}
	
	/**
	 * 系统公告信息修改
	 * @date 2016-7-11
	 */
	@RequestMapping(value="notice/updateSysNotice")
	public String updateNotice(HttpServletRequest request,HttpServletResponse response){
		String noticeId = request.getParameter("noticeId"); 
		String status = request.getParameter("status");  //状态
		Notice notice = new Notice();
			notice.setNoticeId(noticeId);
			notice.setStatus(status);
		try {
			noticeManageService.updateNotice(notice);
			return renderString(response,"success");
		} catch (Exception e) {
			return renderString(response, "error");
		}
	}
	/**
	 * 公告详情查看
	 * 2016-8-3
	 */
	@RequestMapping(value="notice/queryNoticeDetail")
	public String queryNoticeDetail(HttpServletRequest request){
		String noticeId = request.getParameter("noticeId");
		Notice notice = new Notice();
		notice.setNoticeId(noticeId);
		List<Notice> list = noticeManageService.getNoticeDetail(notice);
		if(list.size()>0){
			notice  =  list.get(0);
		}
		request.setAttribute("notice", notice);
		return "modules/sys/admin/admin_pub_notice_detail";
	}
}
