package com.module.testonline.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.aliyun.oss.OSSClient;
import com.common.config.Global;
import com.common.utils.DateUtils;
import com.common.utils.JsonUtil;
import com.common.web.BaseController;
import com.github.pagehelper.PageInfo;
import com.module.course.entity.Course;
import com.module.course.entity.KnowledgeItem;
import com.module.course.service.CommCourseService;
import com.module.course.service.CourseManageService;
import com.module.course.service.KnowledgeItemService;
import com.module.course.util.CourseUtils;
import com.module.course.util.KnowledgeItemUtils;
import com.module.owncenter.service.StudentManageService;
import com.module.sys.entity.Role;
import com.module.sys.entity.UserDetail;
import com.module.sys.service.RoleManageService;
import com.module.sys.utils.FileDownAndUpload;
import com.module.sys.utils.TranslateUtil;
import com.module.sys.utils.UserUtils;
import com.module.testonline.entity.TestPaper;
import com.module.testonline.entity.TestQuestion;
import com.module.testonline.service.TestOnlineService;

/**
 * 在线测试Controller类
 * @author yuanzhonglin
 * @2016-7-21
 */
@Controller
public class TestOnlineController extends BaseController{
	
	@Autowired
	private TestOnlineService testOnlineService;
	
	@Autowired
	private CourseManageService courseManageService;
	/**
	 * 教师测试题目管理
	 * 2016-9-6
	 */
	@RequestMapping(value="${adminPath}/testonline/ceshiQuestion")
	public String queryTeacherCeshiQuestion(HttpServletRequest request) throws Exception{
		String userId = UserUtils.getUser().toString();
		List<Course> courseList = CourseUtils.queryTeacherCourse(new Course(userId));
		request.setAttribute("courseList", courseList);
		request.setAttribute("userId", userId);		//当前用户
		return "modules/sys/teacher/owncenter/ceshi_question_query";
	}
	
	@RequestMapping(value="${adminPath}/testonline/ajaxGetQuestion",method=RequestMethod.POST)
	public String getAllquestion(HttpServletResponse response)throws Exception{
		String courseName = request.getParameter("courseName")==null?"":request.getParameter("courseName");
		courseName = new String(courseName.getBytes("ISO8859-1"), "UTF-8");
		System.out.println("得到courseName============="+courseName);
		String knowledgeId = request.getParameter("knowledgeId")==null?"":request.getParameter("knowledgeId");
		String pageNo = request.getParameter("page")==null?"":request.getParameter("page");
		String IdList[] =null;
		String knowledgeIds="";
		if(StringUtils.isNotBlank(knowledgeId)){
			IdList= knowledgeId.split(",");
			for(int i = 0; i<IdList.length; i++){
				knowledgeIds+="'"+IdList[i]+"',";
			}
			knowledgeIds = knowledgeIds.substring(0, knowledgeIds.length()-1);
		}
		String userId = UserUtils.getUser().toString();
		TestQuestion question = new TestQuestion();
			question.setCourseName(courseName);
			question.setKnowledgeId(knowledgeIds);
			question.setCreateUser(userId);
		List<TestQuestion> list = testOnlineService.queryTeacherCeshiQuestion(Integer.parseInt(pageNo),question);	
		return renderString(response,list);
	}
	/**
	 * 教师添加试题页初始化
	 * 2016-9-7
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="testonline/insertOrEditceshiQuestion")
	public String insertOrEditceshiQuestion(HttpServletRequest request){
		String testId = request.getParameter("testId")==null?"":request.getParameter("testId");
		//String   courseId = request.getParameter("courseId");
		List<KnowledgeItem> list= new ArrayList<KnowledgeItem>();
		String KnowledgeContent="";
		
		if(!testId.equals("")){
			TestQuestion question = new TestQuestion();
				question.setTestId(testId);
			question = testOnlineService.editCeshiQuestion(question);
			request.setAttribute("TestQuestion", question);
			String courseId = question.getCourseId();
			list= KnowledgeItemUtils.getKnowledgeItemListByTestQuestionId(courseId,testId);
			if(list== null ||list.isEmpty())
				list= new ArrayList<KnowledgeItem>();
			for(int i=0;i<list.size();i++){
				if(list.get(i).getChecked()){
					KnowledgeContent=KnowledgeContent+list.get(i).getTitle()+";";
				}
			}
			request.setAttribute("knowledgeContent",KnowledgeContent);
		}
		List<Course> courseList = CourseUtils.queryTeacherCourse(new Course(UserUtils.getUser().toString()));
		List questionType = TranslateUtil.questionTranslate();
		request.setAttribute("testId", testId);
		request.setAttribute("courseList", courseList);
		request.setAttribute("questionType", questionType);
		return "modules/sys/teacher/owncenter/ceshi_question_insert";
	}
	
	/**
	 * 教师查看购物车试题初始化页面
	 */
	@RequestMapping(value="${adminPath}/testonline/queryChooseQuestion")
	public String queryChooseQuestion(){
		return "modules/sys/teacher/owncenter/preview_question_detail";
	}
	
	/**
	 * 购物车试题详情
	 */
	@RequestMapping(value="${adminPath}/testonline/questionDetailList")
	public String queryChooseQuestion(HttpServletRequest request, HttpServletResponse response){
		String questionId = request.getParameter("quesId");
		TestQuestion question = new TestQuestion();
			question.setTestId(questionId);
		List<TestQuestion> list = TestOnlineService.questionDetailList(question);
		return renderString(response,list);
	}
	
	/**
	 * 保存教师创建试题
	 * yuanzhonglin
	 * 2016-9-9
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="testonline/saveCreateTestQuestion")
	public String saveCreateTestQuestion(MultipartHttpServletRequest request, HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = handler(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String courseId = request.getParameter("courseId");  
		String title = request.getParameter("title");  
		String questionType = request.getParameter("questionType");  
		String selectNum = request.getParameter("selectNum");  
		String answerContent = request.getParameter("answerContent");  
		String knowledgeId = request.getParameter("knowledgeId");  
		String answerRemark = request.getParameter("answerRemark");  
		String status = request.getParameter("status");
		String userId = UserUtils.getUser().toString();  
		String questionClass ="2";  
		if(questionType.equals("11") || questionType.equals("12")){
			questionClass = "1";  //1:客观题   2：主观题
		}
		TestQuestion testQuestion = new TestQuestion(); 
			testQuestion.setCourseId(courseId);
			testQuestion.setQuestionTitle(title);
			testQuestion.setType(questionType);
			testQuestion.setQuestionClass(questionClass);
			testQuestion.setSelectNum(selectNum);
			testQuestion.setAnswerContent(answerContent);
			testQuestion.setAnswerRemark(answerRemark);
			testQuestion.setQuestionFilePath(result.get("questionImgPath").toString());
			testQuestion.setCreateUser(userId);
			testQuestion.setCreateTime(Timestamp.valueOf(DateUtils.getDateTime()));
			testQuestion.setStatus(status);
		try {
			testOnlineService.saveCreateTestQuestion(testQuestion);
			String questionId = testQuestion.getTestId();
			testQuestion.setTestId(questionId);
			testOnlineService.saveCreateTestAnswer(testQuestion);
			
			String idList[]=knowledgeId.split(",");
			for(int i=0; i<idList.length; i++){
				testQuestion.setKnowledgeId(idList[i]);
				testOnlineService.saveCreateTestKnowledge(testQuestion);
			}
			result.put("status", "success");
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
		}
		return ajaxHtml(JsonUtil.getJsonString4JavaPOJO(result), response);
	}
	
	/**
	 * 更新试题
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="testonline/updateTestQuestion")
	public String updateTestQuestion(MultipartHttpServletRequest request, HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result = handler(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String courseId = request.getParameter("courseId");  
		String title = request.getParameter("title");  
		String questionType = request.getParameter("questionType");  
		String selectNum = request.getParameter("selectNum");  
		String answerContent = request.getParameter("answerContent");  
		String knowledgeId = request.getParameter("knowledgeId");  
		String answerRemark = request.getParameter("answerRemark");  
		String testId = request.getParameter("testId");
		String status = request.getParameter("status");
		String questionClass ="2";  
		if(questionType.equals("11") || questionType.equals("12")){
			questionClass = "1";  //1:客观题   2：主观题
		}else if(questionClass.equals("2")){
			selectNum = "";
		}
		TestQuestion testQuestion = new TestQuestion(); 
			testQuestion.setCourseId(courseId);
			testQuestion.setQuestionTitle(title);
			testQuestion.setType(questionType);
			testQuestion.setQuestionClass(questionClass);
			testQuestion.setSelectNum(selectNum);
			testQuestion.setAnswerContent(answerContent);
			testQuestion.setAnswerRemark(answerRemark);
			testQuestion.setTestId(testId);
			testQuestion.setQuestionFilePath(result.get("questionImgPath").toString());
			testQuestion.setUpdateTime(DateUtils.getDateTime());
			testQuestion.setStatus(status);
		try {
			testOnlineService.updateTestQuestion(testQuestion);
			testOnlineService.updateTestAnswer(testQuestion);
			String tempStr="";
			if(!knowledgeId.equals("")){
				String[] nodes= knowledgeId.split(",");
				List<TestQuestion> qList = new ArrayList<TestQuestion>();
				for(int i=0;i<nodes.length;i++){
					TestQuestion item =new TestQuestion();
					item.setTestId(testId);
					tempStr = nodes[i];
					item.setKnowledgeId(tempStr);
					qList.add(item);
				}
				TestOnlineService tos = new TestOnlineService();
				tos.updateTestQuestionKnowledgeLink(testId,qList);
			}
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
		String questionFolder = "question";		//试题图片基础路径
		Map map =new HashMap();	
		MultipartFile questionImg = request.getFile("examsContents");
		if(questionImg.isEmpty()){// step-2 判断file
			map.put("questionImgPath", "");
		}else{
			String orgFileName = questionImg.getOriginalFilename();		//文件名 + 文件类型
			String finalImgFilePath = FileDownAndUpload.uploadFile(orgFileName, questionFolder, questionImg);
			map.put("questionImgPath", finalImgFilePath);
		}
		return map;
	}
	
	/**
	 * 删除试卷
	 */
	@RequestMapping(value="testonline/deleteTestPaper")
	public String deleteTestPaper(HttpServletRequest request,HttpServletResponse response){
		String paperId = request.getParameter("paperId");
		String idList[] = paperId.split(",");
		try {
			TestPaper testPaper = new TestPaper();
			testPaper.setCreateUser(UserUtils.getUser().toString());
			for(int i=0; i<idList.length; i++){
				testPaper.setPaperId(idList[i]);
				testOnlineService.deletTestPaper(testPaper);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error"); 
		}
		return renderString(response, "success"); 
	}
	
	/**
	 *教师撤回推送的试卷 
	 */
	@RequestMapping(value="testonline/revokeTestPaper")
	public String revokeTestPaper(HttpServletRequest request,HttpServletResponse response){
		String paperId = request.getParameter("paperId");
		String studentId = request.getParameter("studentId");
		String idList[] = paperId.split(",");
		try {
			TestPaper testPaper = new TestPaper();
			testPaper.setCreateUser(studentId);
			for(int i=0; i<idList.length; i++){
				testPaper.setPaperId(idList[i]);
				testOnlineService.deletTestPaper(testPaper);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error"); 
		}
		return renderString(response, "success"); 
	}
	
	/**
	 * 教师测试题删除
	 * yuanzhonglin
	 * 2016-9-7
	 */
	@RequestMapping(value="testonline/deleteTestQuestion")
	public String deleteRole(HttpServletRequest request,HttpServletResponse response){
		String testIds = request.getParameter("testId");
		String idList[]=testIds.split(",");
		TestQuestion question = new TestQuestion();
		try {
			String endpoint = Global.getConfig("endpoint");
			String accessKeyId = Global.getConfig("accessKeyId");
			String accessKeySecret = Global.getConfig("accessKeySecret");
			String bucketName = Global.getConfig("bucketName");
			// 创建OSSClient实例
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			for(int i=0; i<idList.length; i++){
				question.setTestId(idList[i]);
				question = testOnlineService.queryTestQuestionById(question);
				String filePath = question.getQuestionFilePath();
				if(filePath!=null && !("").equals(filePath)){
					ossClient.deleteObject(bucketName,filePath);
				}	
				testOnlineService.deleteTestQuestion(idList[i]);
			}
			ossClient.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
		return renderString(response, "success");
	}
	
	/**
	 * 教师随堂组卷
	 * 2016-9-12
	 */
	@RequestMapping(value="${adminPath}/testonline/makeTestPaperPage")
	public String unitTest(HttpServletRequest request) throws Exception{
		String paperName = request.getParameter("paperName")==null?"":request.getParameter("paperName");
		paperName = new String(paperName.getBytes("ISO8859-1"), "UTF-8");
		String userId = UserUtils.getUser().toString();
		TestPaper testPaper = new TestPaper();
			testPaper.setCreateUser(userId);
			testPaper.setPaperName(paperName);
		List<TestPaper> list = TestOnlineService.queryPaperByUser(testPaper);
		List<Course> courseList = CourseUtils.queryTeacherCourse(new Course(userId));
		List<Course> commCourse = CommCourseService.queryCommCourse();
		request.setAttribute("courseList", courseList);
		request.setAttribute("commCourse", commCourse);
		request.setAttribute("paperName", paperName);
		request.setAttribute("pageInfo", new PageInfo<TestPaper>(list));
		return "modules/sys/teacher/testonline/teacher_make_testpaper";
	}
	
	/**
	 * 组卷试卷查看及编辑修改
	 */
	@RequestMapping(value="testonline/paperDetail")
	public String paperDetail(HttpServletRequest request){
		String paperId = request.getParameter("paperId")==null?"":request.getParameter("paperId");
		String paperType = request.getParameter("paperType")==null?"":request.getParameter("paperType");
		List<TestPaper> testPaper = testOnlineService.paperDetail(paperId);
		request.setAttribute("testPaper", testPaper);
		request.setAttribute("paperId", paperId);
		request.setAttribute("paperType", paperType);
		return "modules/sys/teacher/testonline/teacher_paper_detail_query";
	}
	
	@RequestMapping(value="${adminPath}/testonline/editQuestionScore")
	public String editQuestionScore(HttpServletRequest request,HttpServletResponse response){
		String score = request.getParameter("score")==null?"":request.getParameter("score");
		String paperId = request.getParameter("paperId")==null?"":request.getParameter("paperId");
		String questionId = request.getParameter("questionId")==null?"":request.getParameter("questionId");
		TestPaper paper = new TestPaper();
			paper.setQuestionScore(score);
			paper.setPaperId(paperId);
			paper.setQuestionId(questionId);
		try {
			TestOnlineService.editQuestionScore(paper);
			return renderString(response, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	/**
	 * 组卷试卷下发给学生初始化页
	 */
	@RequestMapping(value="testonline/querySendPaperUser")
	public String querySendPaperUser(HttpServletRequest request)throws Exception{
		String paperId = request.getParameter("paperId")==null?"":request.getParameter("paperId");
		String pn = request.getParameter("professionName")==null?"":request.getParameter("professionName");
		String professionName = new String(pn.getBytes("ISO8859-1"), "UTF-8");
		String gn = request.getParameter("gradeName")==null?"":request.getParameter("gradeName");
		String gradeName = new String(gn.getBytes("ISO8859-1"), "UTF-8");
		String un = request.getParameter("userName")==null?"":request.getParameter("userName");
		String userName = new String(un.getBytes("ISO8859-1"), "UTF-8");
		System.out.println("UserUtils.getUser().getUserId()------"+UserUtils.getUser().getUserId());
		String schoolId = UserUtils.getUser().getSchoolId();
		UserDetail userDetail = new UserDetail();
			userDetail.setProfessionName(professionName);
			userDetail.setGradeName(gradeName);
			userDetail.setUserName(userName);
			userDetail.setSchoolId(schoolId);
		
		
		//List<UserDetail> userList = testOnlineService.querySendPaperUser(schoolId);
		
		List<UserDetail> userList = testOnlineService.querySendPaperUser(userDetail);
		List<Role> roleList = RoleManageService.showAllRole();
		request.setAttribute("userList", userList);
		request.setAttribute("roleList", roleList);
		request.setAttribute("roleNum", roleList.size());
		request.setAttribute("paperId", paperId);
		return "modules/sys/teacher/testonline/teacher_sendPaper_toUser";
	}
	
	/**
	 * 教师组卷下发给学生保存
	 */
	@RequestMapping(value="testonline/sendPaperToStudent")
	public String sendPaperToUser(HttpServletRequest request, HttpServletResponse response){
		String paperId = request.getParameter("paperId")==null?"":request.getParameter("paperId"); 
		String userId = request.getParameter("userId")==null?"":request.getParameter("userId"); 
		String roleId = request.getParameter("roleId")==null?"":request.getParameter("roleId"); 
		Map<String,Object> map = new HashMap<String, Object>();
		TestPaper testPaper = new TestPaper();
			testPaper.setPaperId(paperId);
		try {
			StringBuffer ss = new StringBuffer();
			if(!"".equals(roleId)){			//选择单个用户发送
				UserDetail userdetail = new UserDetail();
				userdetail.setRoleId(roleId);
				List<UserDetail> userList = RoleManageService.userList(userdetail);
				int userNum = userList.size();
				if(userNum!=0){
					for(int i=0; i<userList.size(); i++){
						String Id = userList.get(i).getUserId();
						testPaper.setStudentId(Id);
						List<TestPaper> paperList = TestOnlineService.paperList(testPaper);
						if(paperList.size()>0){
							ss.append(paperList.get(0).getStudentName());
							ss.append("、");
						}else{
							testPaper.setCreateUser(Id);
							testOnlineService.sendPaperToUser(testPaper);
						}
					}
					map.put("status", "success");
				}else{
					map.put("status", "noUser");
					return renderString(response, map);
				}
			}else{
				String idList[]=userId.split(",");
				for(int i=0; i<idList.length; i++){
					String Id = idList[i];
					testPaper.setStudentId(Id);
					List<TestPaper> paperList = TestOnlineService.paperList(testPaper);
					if(paperList.size()>0){
						ss.append(paperList.get(0).getStudentName());
						ss.append("、");
					}else{
						testPaper.setCreateUser(Id);
						testOnlineService.sendPaperToUser(testPaper);
					}
				}
				map.put("status", "success");
			}
			map.put("repeatUser", ss);		//下发试卷重复的用户名
		}catch(Exception e) {
			e.printStackTrace();
			map.put("status", "error");
		}
		return renderString(response, map);
	}
	/**
	 * 教师生成组卷
	 * 2016-9-12
	 */
	@RequestMapping(value="testonline/saveTestPaper")
	public String save(HttpServletRequest request, HttpServletResponse response){
		String courseId = request.getParameter("courseId")==null?"":request.getParameter("courseId");
		String knowledgeId = request.getParameter("knowledgeId")==null?"":request.getParameter("knowledgeId");
		String paperName = request.getParameter("paperName")==null?"":request.getParameter("paperName");
		String testTime = request.getParameter("testTime")==null?"":request.getParameter("testTime");
		String testTotalNum = request.getParameter("testTotalNum")==null?"":request.getParameter("testTotalNum");
		String paperType = request.getParameter("paperType")==null?"":request.getParameter("paperType");
		String paperId = "";
		TestPaper testPaper = new TestPaper();
			testPaper.setCourseId(courseId);
			testPaper.setKnowledgeList(knowledgeId);
			testPaper.setPaperName(paperName);
			testPaper.setTestTimeLong(testTime);
			testPaper.setPaperType(paperType);
			testPaper.setQuestionTotal(testTotalNum);
			testPaper.setCreateUser(UserUtils.getUser().toString());
			testPaper.setCreateTime(Timestamp.valueOf(DateUtils.getDateTime()));
	try{
		String knowledgeIds="";
		if(!knowledgeId.equals("")){
			String IdList[]= knowledgeId.split(",");
			for(int i = 0; i<IdList.length; i++){
				knowledgeIds+="'"+IdList[i]+"',";
			}
			knowledgeIds = knowledgeIds.substring(0, knowledgeIds.length()-1);
		}
		TestQuestion question = new TestQuestion();
			question.setCourseId(courseId);
			question.setKnowledgeId(knowledgeIds);
        String questionIds="";
        String qId[]=null;
    	List<TestQuestion> questionList = TestOnlineService.queryQuestionRandom(question);
    	int questionNum = questionList.size();
    	int totalNum = Integer.parseInt(testTotalNum);
		Random random = new Random();	//创建随机对象
		if(questionNum>totalNum){
			testOnlineService.saveTestPaper(testPaper);
			paperId = testPaper.getPaperId();
			for(int i=0; i<totalNum; i++){
				int arrIdx = random.nextInt(questionNum); 
				String testId = questionList.get(arrIdx).getTestId();
					if(isExist(testId,qId)){
						testPaper.setQuestionId(testId);
						testPaper.setPaperId(paperId);
						testPaper.setQuestionScore("5"); 
						testOnlineService.savePaperQuestion(testPaper);
						questionIds += testId+",";	//获客观题Id
						qId =questionIds.split(",");
					}else{
						i-=1;
					}		
			}
			testPaper.setPaperScore(String.valueOf(totalNum*10));
			testPaper.setPaperId(paperId);
			testOnlineService.updatePaperScore(testPaper);
			return renderString(response, paperId);
		}else{
			return renderString(response, "over");
		}
	}catch (Exception e){
		e.printStackTrace();
		return renderString(response, "error");
	}
}
	
	public static boolean isExist(String id, String[] ids){
		if(ids!=null){
			for(int i=0; i<ids.length; i++){
				if(id.equals(ids[i])){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 教师选题组卷
	 */
	@RequestMapping(value="testonline/chooseQuesToPaper")
	public String chooseQuesToPaper(HttpServletRequest request,HttpServletResponse response){
		String questionId = request.getParameter("questionId");
		String paperName = request.getParameter("paperName");
		String paperTime = request.getParameter("paperTime");
		String idList[] = questionId.split(",");
		TestPaper testPaper = new TestPaper(); 
			testPaper.setPaperName(paperName);
			testPaper.setTestTimeLong(paperTime);
			testPaper.setQuestionTotal(String.valueOf(idList.length));
			testPaper.setPaperType("2"); 		//2:教师组卷  	1：学生组卷
			testPaper.setCreateUser(UserUtils.getUser().toString());
			testPaper.setCreateTime(Timestamp.valueOf(DateUtils.getDateTime()));
		String paperId = "";
		try {
			testOnlineService.saveTestPaper(testPaper);
			paperId = testPaper.getPaperId();
			TestPaper question = new TestPaper();
			for(int i=0; i<idList.length;i++){
				question.setQuestionId(idList[i]);
				question.setPaperId(paperId);
				question.setQuestionScore("5");  		//每道题10分
				testOnlineService.savePaperQuestion(question);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
		return renderString(response, "success");
	}
	//试题知识点关联增加
	@RequestMapping(value="/testonline/confirmKnowledgeLink")
	public String confirmQustionKnowledgeLink(HttpServletRequest request,HttpServletResponse response){
		String tempStr; 
		Map<String, Object> map = new HashMap<String, Object>();
		String   questionId = request.getParameter("testId");
		String treeNodes = request.getParameter("treeNodes");
		//删除某个课件对应的所有知识点关联记录
		String[] nodes =null;
		if(!treeNodes.equals("")){
			nodes= treeNodes.split(",");
		}
		List<TestQuestion> qList = new ArrayList<TestQuestion>();
		for(int i=0;i<nodes.length;i++){
			TestQuestion item =new TestQuestion();
			item.setTestId(questionId);;
			tempStr = nodes[i];
			item.setKnowledgeId(tempStr);
			qList.add(item);
		}
		try{
			TestOnlineService tos = new TestOnlineService();
			tos.updateTestQuestionKnowledgeLink(questionId,qList);
		}catch (Exception e) {
  			e.printStackTrace();
  			map.put("error","更新试题知识点异常");
    		return renderString(response,map );
  		}
		return renderString(response, "success");
	
	}
	
	//取得试题知识点关联列表
	@RequestMapping(value="/testonline/fetchKnowledgeLink")
	public String fetchCourseFileKnowledgeLinkList(@RequestBody TestQuestion tq,HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		String   questionId = tq.getTestId();
		String   courseId = tq.getCourseId();
	
		List<KnowledgeItem> list= new ArrayList<KnowledgeItem>();
		try{
				list= KnowledgeItemUtils.getKnowledgeItemListByTestQuestionId(courseId,questionId);
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
			}catch (Exception e) {
	  			e.printStackTrace();
	  			map.put("error","获取试题知识点异常");
	    		return renderString(response,map );
	  		}
	}
	
	@RequestMapping(value="${adminPath}/testonline/testOnlineRandom")
	public String testOnlineRandom(HttpServletRequest request, HttpServletResponse response){
		String userId = UserUtils.getUser().toString();
		Course course = new Course();
			course.setUserId(userId);
			course.setIsCommon("0");
		List<Course> courseList = courseManageService.queryStudentCourse(course);
		List<Course> commCourse = CommCourseService.queryCommCourse();
		request.setAttribute("courseList", courseList);
		request.setAttribute("commCourse", commCourse);
		return "modules/sys/student/testonline/student_testquestion_random";
	}
	
	@RequestMapping(value="testonline/testQuestionOnline")
	public String criticismTest(HttpServletRequest request,HttpServletResponse response){
		String courseId = request.getParameter("courseId")==null?"":request.getParameter("courseId");
		String knowledgeId = request.getParameter("knowledgeId")==null?"":request.getParameter("knowledgeId");
		String questionClass = request.getParameter("questionClass")==null?"":request.getParameter("questionClass");
		String userId = UserUtils.getUser().toString();
		String knowledgeIds="";
		if(!knowledgeId.equals("")){
			String IdList[]= knowledgeId.split(",");
			for(int i = 0; i<IdList.length; i++){
				knowledgeIds+="'"+IdList[i]+"',";
			}
			knowledgeIds = knowledgeIds.substring(0, knowledgeIds.length()-1);
		}
		TestQuestion question = new TestQuestion();
			question.setKnowledgeId(knowledgeIds);
			question.setQuestionClass(questionClass);
			question.setCourseId(courseId);
			question.setCreateUser(userId);
		List<TestQuestion> questionList = TestOnlineService.queryQuestionRandom(question);
		Random random = new Random();	//创建随机对象
		int questionNum = questionList.size();
		if(questionNum>0){
			int arrIdx = random.nextInt(questionNum); 
			TestQuestion questions = questionList.get(arrIdx);
			TestPaper testPaper = new TestPaper();
				testPaper.setQuestionId(questions.getTestId());
				testPaper.setStudentId(userId);
			StudentManageService service = new StudentManageService();
			List<TestPaper> isAddError = service.queryMyErrorNotes(0,testPaper);
			request.setAttribute("questionDetail", questions);
			request.setAttribute("questionCode", arrIdx);
			request.setAttribute("isAddError", isAddError.size());
			request.setAttribute("knowledgeId", knowledgeId);
			request.setAttribute("questionClass", questionClass);
		}
		request.setAttribute("questionNum", questionNum);
		return "modules/sys/student/exam/answer_question_online";
	}

	@RequestMapping(value="testonline/testPaper")
	public String testPaper(HttpServletRequest request){
		String paperId = request.getParameter("paperId")==null?"":request.getParameter("paperId");
		String sortNum = request.getParameter("sortNum");
		if(sortNum.equals("0")){
			TestPaper testPaper = new TestPaper();
				testPaper.setCreateUser(UserUtils.getUser().toString());
				testPaper.setPaperId(paperId);
				testPaper.setStartTime(DateUtils.getDateTime());
				testPaper.setStatus("1");
				testPaper.setPaperScore("0");
			 testOnlineService.updateTestPaperDetail(testPaper);	
		}
		int sort = Integer.parseInt(sortNum);
		List<TestPaper> questionList = testOnlineService.paperDetail(paperId);	
		int totalNum = questionList.size();
		TestPaper questionDetail = new TestPaper();
		if(totalNum!=sort){
			questionDetail = questionList.get(sort);
			request.setAttribute("over", "0");
		}else{
			request.setAttribute("over", "1");
			questionDetail = questionList.get(sort-1);
		}
		request.setAttribute("questionDetail", questionDetail);
		request.setAttribute("totalNum", totalNum);
		request.setAttribute("sortNum", sort+1);
		return "modules/sys/student/testonline/student_answer_paper";
	}
	
	/**
	 * 学生解答教师组卷提交
	 */
	@RequestMapping(value="testonline/submitTeacherPaper")
	public String submitTeacherPaper(HttpServletRequest request, HttpServletResponse response){
		String paperId = request.getParameter("paperId")==null?"":request.getParameter("paperId");
		String testUser = UserUtils.getUser().toString();
		TestPaper testPaper = new TestPaper();
			testPaper.setPaperId(paperId);
			testPaper.setStudentId(testUser);
			testPaper.setStatus("2");
			testPaper.setEndTime(DateUtils.getDateTime());
		try {
			testOnlineService.updateTestPaperDetail(testPaper);
			return renderString(response,"success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response,"error");
		}	
	}
	
	@RequestMapping(value="testonline/sumGrade")
	public String sumGrade(HttpServletRequest request, HttpServletResponse response){
		String score = request.getParameter("score")==null?"":request.getParameter("score");
		String paperId = request.getParameter("paperId")==null?"":request.getParameter("paperId");
		String userId = request.getParameter("userId")==null?"":request.getParameter("userId");
		String queScore[] =score.split(",");
		int totalScore = 0;
		try {
			for(int i=0; i<queScore.length; i++){
				totalScore += Integer.parseInt(queScore[i]);
			}
			TestPaper testPaper = new TestPaper();
				testPaper.setPaperScore(String.valueOf(totalScore));
				testPaper.setPaperId(paperId);
				testPaper.setCreateUser(userId);
				testPaper.setStatus("3");		//计算分数
			testOnlineService.updateTestPaperDetail(testPaper);
			return renderString(response,"success");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return renderString(response,"error");
		}
	}
	
	/**
	 * 学生点下一题时记录答题详情
	 */
	@RequestMapping(value="testonline/saveAnswerEveryQuestionDetail")
	public String saveAnswerEveryQuestionDetail(HttpServletRequest request, HttpServletResponse response){
		String questionId = request.getParameter("questionId")==null?"":request.getParameter("questionId");
		String paperId = request.getParameter("paperId")==null?"":request.getParameter("paperId");
		String userAnswer = request.getParameter("userAnswer")==null?"":request.getParameter("userAnswer");
		String standarAnswer = request.getParameter("standarAnswer")==null?"":request.getParameter("standarAnswer");
		String isAdd = request.getParameter("isAdd")==null?"":request.getParameter("isAdd");
		String userId = UserUtils.getUser().toString();
		TestPaper tp = new TestPaper();
			tp.setQuestionId(questionId);
			tp.setPaperId(paperId);
			tp.setUserAnswer(userAnswer);
			tp.setStandarAnswer(standarAnswer);
			tp.setCreateTime(Timestamp.valueOf(DateUtils.getDateTime()));
			tp.setStudentId(userId);
			tp.setIsAddNotes(isAdd);
		try {
			testOnlineService.saveAnswerEveryQuestionDetail(tp);
			return renderString(response,"success");
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response,"error");
		}
	}
	
	/**
	 * 教师试卷打分初始化页展示
	 */
	@RequestMapping(value="${adminPath}/testonline/testPaperGradePage")
	public String teacherPaperGrade(HttpServletRequest request)throws Exception{
		String studentName = request.getParameter("studentName")==null?"":request.getParameter("studentName");
		studentName = new String(studentName.getBytes("ISO8859-1"), "UTF-8");
		String status = request.getParameter("status")==null?"":request.getParameter("status");
		String createUser = UserUtils.getUser().toString();
		TestPaper testPaper = new TestPaper();
			testPaper.setStatus(status);
			testPaper.setCreateUser(createUser);
			testPaper.setStudentName(studentName);
		List<TestPaper> studentPaper = TestOnlineService.queryStudentPaper(testPaper);
		request.setAttribute("pageInfo", new PageInfo<TestPaper>(studentPaper));
		request.setAttribute("studentName", studentName);
		request.setAttribute("status", status);
		return "modules/sys/teacher/testonline/teacher_paper_grade";
	}
	
	@RequestMapping(value="testonline/queryStudentPaperAnswer")
	public String queryStudentPaperAnswer(HttpServletRequest request)throws Exception{
		String type = request.getParameter("type")==null?"":request.getParameter("type");
		String paperId = request.getParameter("paperId")==null?"":request.getParameter("paperId");
		String userId = request.getParameter("userId")==null?"":request.getParameter("userId");
		if(userId.equals("")){
			userId = UserUtils.getUser().toString();
			TestPaper testPaper = new TestPaper();
				testPaper.setPaperId(paperId);
				testPaper.setCreateUser(userId);
				testPaper.setStatus("2");
				testPaper.setEndTime(DateUtils.getDateTime());
			testOnlineService.updateTestPaperDetail(testPaper);
		}
		TestPaper testPaper = new TestPaper();
			testPaper.setPaperId(paperId);
			testPaper.setCreateUser(userId);
		List<TestPaper> result = testOnlineService.queryTestPaperResult(testPaper);
		if(result.size()>0){
			testPaper = result.get(0);
		}
		request.setAttribute("paperType", testPaper.getPaperType());
		request.setAttribute("result", result);
		request.setAttribute("status", testPaper.getStatus());
		request.setAttribute("paperId", testPaper.getPaperId());
		request.setAttribute("userId", testPaper.getCreateUser());
		request.setAttribute("paperScore", testPaper.getPaperScore());
		request.setAttribute("type", type);
		return "modules/sys/teacher/testonline/test_paper_result";
	}
	
}
