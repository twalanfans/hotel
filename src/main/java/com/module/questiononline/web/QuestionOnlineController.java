package com.module.questiononline.web;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.aliyun.oss.OSSClient;
import com.common.config.Global;
import com.common.utils.DateUtils;
import com.common.utils.JsonUtil;
import com.common.web.BaseController;
import com.github.pagehelper.PageInfo;
import com.module.questiononline.entity.QuestionOnline;
import com.module.questiononline.service.QuestionOnlineService;
import com.module.sys.utils.FileDownAndUpload;
import com.module.sys.utils.UserUtils;

@Controller
public class QuestionOnlineController extends BaseController{
	
	@Autowired
	private QuestionOnlineService questionOnlineService;
	
	@RequestMapping(value="${adminPath}/question/allQuestionOnline")
	public String allQuestionOnline(HttpServletRequest request) throws Exception{
		String title = request.getParameter("title")==null?"":request.getParameter("title");
		title = new String(title.getBytes("ISO8859-1"), "UTF-8");
		String sn = request.getParameter("showNum")==null?"":request.getParameter("showNum");
		int showNum =15;
		if(!sn.isEmpty()){
			showNum=Integer.parseInt(sn)+15;  //每点击加载更多展示多15条数据
		}
		QuestionOnline question = new QuestionOnline();
			question.setTitle(title);
		List<QuestionOnline> questionList = questionOnlineService.allQuestionOnline(question);
		request.setAttribute("questionNum", questionList.size());
		request.setAttribute("showNum", showNum);
		request.setAttribute("title", title);
		request.setAttribute("questionList", questionList);
		return "modules/askquest/question_online";
	}
	@RequestMapping(value="${adminPath}/question/myAnswerOnline")
	public String myAnswerOnline(HttpServletRequest request) throws Exception{
		String title = request.getParameter("title")==null?"":request.getParameter("title");
		title = new String(title.getBytes("ISO8859-1"), "UTF-8");
		String sn = request.getParameter("showNum")==null?"":request.getParameter("showNum");
		int showNum =15;
		if(!sn.isEmpty()){
			showNum=Integer.parseInt(sn)+15;  //每点击加载更多展示多15条数据
		}
		QuestionOnline question = new QuestionOnline();
			question.setCreateUser(UserUtils.getUser().toString());
			question.setTitle(title);
		List<QuestionOnline> questionList = questionOnlineService.queryCommentOnline(question);
		request.setAttribute("questionNum", questionList.size());
		request.setAttribute("showNum", showNum);
		request.setAttribute("title", title);
		request.setAttribute("questionList", questionList);
		return "modules/askquest/my_comment";
	}
	
	@RequestMapping(value="question/questionContent")
	public String questionContent(HttpServletRequest request) throws Exception{
		String sn = request.getParameter("showNum")==null?"":request.getParameter("showNum");
		String questionId = request.getParameter("questionId")==null?"":request.getParameter("questionId");
		String clickNum = request.getParameter("c_num")==null?"":request.getParameter("c_num");
		int showNum =20;
		if(!sn.isEmpty()){
			showNum=Integer.parseInt(sn)+15;  //每点击加载更多展示多15条数据
		}
		QuestionOnline question = new QuestionOnline();
			question.setQuestionId(questionId);
		if(!clickNum.equals("")){
			question.setClickNum(Integer.parseInt(clickNum)+1);
			questionOnlineService.updateClickNum(question);
		}
		QuestionOnline questionDetail = questionOnlineService.questionDetail(questionId);
		String attachImg = questionDetail.getAttachImg();
		String img[]=null;
		if(attachImg!=null && !"".equals(attachImg)){
			img = attachImg.split(",");
		}
		List<QuestionOnline> commentList = questionOnlineService.queryCommentOnline(question);
		request.setAttribute("commentNum", commentList.size());
		request.setAttribute("showNum", showNum);
		request.setAttribute("questionDetail", questionDetail);
		request.setAttribute("commentList", commentList);
		request.setAttribute("attachImg", img);
		return "modules/askquest/question_content";
	}
	
	@RequestMapping(value="question/saveNewQuestion")
	public String saveNewQuestion(MultipartHttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		String questionTitle = request.getParameter("questionTitle"); 
	    String questionContent = request.getParameter("questionContent"); 
	    String userId = UserUtils.getUser().toString();
	    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
        MultipartFile imgFile1 = multipartRequest.getFile("attachImg1"); 
        MultipartFile imgFile2 = multipartRequest.getFile("attachImg2"); 
        MultipartFile imgFile3 = multipartRequest.getFile("attachImg3"); 
        String attachImgPath="";
        try {
        if(imgFile1.getSize()>0){
        	attachImgPath += FileDownAndUpload.uploadAttachFile(imgFile1.getOriginalFilename(), "quesAttachImg/"+questionTitle.substring(0,3)+"_"+userId.substring(0,8),imgFile1)+",";
        }
        if(imgFile2.getSize()>0){
        	attachImgPath += FileDownAndUpload.uploadAttachFile(imgFile2.getOriginalFilename(), "quesAttachImg/"+questionTitle.substring(0,3)+"_"+userId.substring(0,8),imgFile2)+",";
        }
        if(imgFile3.getSize()>0){
        	attachImgPath += FileDownAndUpload.uploadAttachFile(imgFile3.getOriginalFilename(), "quesAttachImg/"+questionTitle.substring(0,3)+"_"+userId.substring(0,8), imgFile3);
        }
        QuestionOnline question = new QuestionOnline();
        	question.setTitle(questionTitle);
        	question.setContent(questionContent);
        	question.setCreateUser(userId);
        	question.setCreateTime(Timestamp.valueOf(DateUtils.getDateTime()));
        	question.setAttachImg(attachImgPath);
        	questionOnlineService.saveNewQuestion(question);
			map.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "error");
		}
        return ajaxHtml(JsonUtil.getJsonString4JavaPOJO(map), response);
	}
	
	@RequestMapping(value="question/saveComment")
	public String saveComment(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String commentId = request.getParameter("commentId")==null?"":request.getParameter("commentId");
		String questionId = request.getParameter("questionId");
		String content = request.getParameter("content"); 
		content = new String(content.getBytes("ISO8859-1"), "UTF-8");
		try {
			String userId = UserUtils.getUser().toString();
			QuestionOnline question = new QuestionOnline();
				if(commentId.equals("")){
					commentId = "0";
				}
				question.setReplyId(commentId);
				question.setQuestionId(questionId);
				question.setContent(content);
				question.setCreateUser(userId);
				question.setCreateTime(Timestamp.valueOf(DateUtils.getDateTime()));
			questionOnlineService.saveComment(question);
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	@RequestMapping(value="question/commentUpTG")
	public String commentUpTG(HttpServletRequest request,HttpServletResponse response){
		String commentId = request.getParameter("commentId");
		String thinkGood = request.getParameter("thinkGood");
		try {
			int goodNum = Integer.parseInt(thinkGood)+1;
			QuestionOnline question = new QuestionOnline();
				question.setCommentId(commentId);
				question.setThinkGood(goodNum);
			questionOnlineService.commentUpTG(question);
			return renderString(response, goodNum);
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	@RequestMapping(value="${adminPath}/question/questionOnlinePage")
	public String showAllQuestion(HttpServletRequest request) throws Exception{
		String cu = request.getParameter("createUser")==null?"":request.getParameter("createUser");
		String createUser = new String(cu.getBytes("ISO8859-1"), "UTF-8");
		QuestionOnline question = new QuestionOnline();
			question.setCreateUser(createUser);
		List<QuestionOnline> list = questionOnlineService.queryQuestionList(question);
		request.setAttribute("pageInfo", new PageInfo<QuestionOnline>(list));
		request.setAttribute("createUser", createUser);
		return "modules/sys/admin/admin_question_query";
	}
	
	@RequestMapping(value="${adminPath}/question/commentOnlinePage")
	public String commentOnline(HttpServletRequest request) throws Exception{
		String createUser = request.getParameter("createUser")==null?"":request.getParameter("createUser");
		createUser = new String(createUser.getBytes("ISO8859-1"), "UTF-8");
		String content = request.getParameter("content")==null?"":request.getParameter("content");
		content = new String(content.getBytes("ISO8859-1"), "UTF-8");
		QuestionOnline question = new QuestionOnline();
			question.setCreateUser(createUser);
			question.setContent(content);
		List<QuestionOnline> list = questionOnlineService.queryCommentList(question);
		request.setAttribute("pageInfo", new PageInfo<QuestionOnline>(list));
		request.setAttribute("createUser", createUser);
		request.setAttribute("content", content);
		return "modules/sys/admin/admin_comment_query";
	}
	
	@RequestMapping(value="question/deleteQuestionOnline")
	public String deleteQuestionOnline(HttpServletRequest request,HttpServletResponse response){
		String questionId = request.getParameter("questionId");
		String idList[]=questionId.split(",");
		try {
			String endpoint = Global.getConfig("endpoint");
			String accessKeyId = Global.getConfig("accessKeyId");
			String accessKeySecret = Global.getConfig("accessKeySecret");
			String bucketName = Global.getConfig("bucketName");
			// 创建OSSClient实例
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			for(int i=0; i<idList.length; i++){
				String quesId = idList[i];
				QuestionOnline questionDetail = questionOnlineService.questionDetail(quesId);
				String attachImg = questionDetail.getAttachImg();
				String img[]=null;
				if(attachImg!=null && attachImg!=""){
					img = attachImg.split(",");
				}
				if(img!=null){
					for(int f=0; f<img.length; f++){
						// 删除Object
						ossClient.deleteObject(bucketName, img[f]);
					}
				}
				QuestionOnlineService.deleteQuestionAndComment(quesId);
			}
			ossClient.shutdown();
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	@RequestMapping(value="question/deleteCommentOnline")
	public String deleteCommentOnline(HttpServletRequest request,HttpServletResponse response){
		String commentId = request.getParameter("commentId");
		String idList[]=commentId.split(",");
		try {
			for(int i=0; i<idList.length; i++){
				QuestionOnlineService.deleteCommentById(idList[i]);
			}
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
}
