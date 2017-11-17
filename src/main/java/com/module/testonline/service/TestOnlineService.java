package com.module.testonline.service;
import java.util.List;

import org.noo.pagination.page.PageContext;
import org.noo.pagination.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.common.utils.SpringContextHolder;
import com.github.pagehelper.PageHelper;
import com.module.course.dao.CourseNoteDao;
import com.module.course.entity.CourseNotes;
import com.module.owncenter.dao.StudentDao;
import com.module.sys.entity.UserDetail;
import com.module.testonline.dao.TestOnlineDao;
import com.module.testonline.dao.TestPaperDao;
import com.module.testonline.entity.TestPaper;
import com.module.testonline.entity.TestQuestion;

/**
 * @Description  课程管理服务Service
 * @Author  yuanzhonglin
 * @Date  2016年7月12日 
 */
@Service
@Transactional(readOnly=true)
public class TestOnlineService {

	@Autowired
	private TestOnlineDao testOnlineDao;
	
	/**
	 * 老师测试试题管理查询
	 * yuanzhonglin
	 * 2016-9-7
	 */
	@Transactional(readOnly=true)
	public List<TestQuestion> queryTeacherCeshiQuestion(int pageNo, TestQuestion testQuestion){
		TestOnlineDao testOnlineDao = SpringContextHolder.getBean(TestOnlineDao.class);
	    int pageSize = 10;
	    PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
		List<TestQuestion> list = testOnlineDao.queryTeacherCeshiQuestion(testQuestion);
		return list;
	}
	
	@Transactional(readOnly=true)
	public List<TestQuestion> queryCommonQuestion(TestQuestion testQuestion){
		TestOnlineDao testOnlineDao = SpringContextHolder.getBean(TestOnlineDao.class);
		Pagination pager = PageContext.getPageContext();  //分页
		int pageNo = pager.getCurrentPage();
	    int pageSize = pager.getPageSize();
	    PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
		List<TestQuestion> list = testOnlineDao.queryTeacherCeshiQuestion(testQuestion);
		return list;
	}
	
	public TestQuestion editCeshiQuestion(TestQuestion testQuestion){
		TestOnlineDao testOnlineDao = SpringContextHolder.getBean(TestOnlineDao.class);
		TestQuestion question = testOnlineDao.editCeshiQuestion(testQuestion);
		return question;
	}
	
	/**
	 * 教师加入购物车查看试题详情
	 */
	public static List<TestQuestion> questionDetailList(TestQuestion question){
		TestOnlineDao testOnlineDao = SpringContextHolder.getBean(TestOnlineDao.class);
		List<TestQuestion> list = testOnlineDao.questionDetailList(question);
		return list;
	}

	/**
	 * 学生添加课程笔记
	 * @date 2016-7-20
	 */
	@Transactional(readOnly=false)
	public void insertNotes(CourseNotes courseNotes){
		CourseNoteDao courseDao = SpringContextHolder.getBean(CourseNoteDao.class);
		courseDao.saveCourseNotes(courseNotes);
	}
	
	/**
	 * 保存教师上传的试题
	 * @date 2016-9-9
	 */
	@Transactional(readOnly=false)
	public void saveCreateTestQuestion(TestQuestion testQuestion){
		TestOnlineDao testOnlineDao = SpringContextHolder.getBean(TestOnlineDao.class);
		testOnlineDao.saveCreateTestQuestion(testQuestion);
	}
	@Transactional(readOnly=false)
	public void saveCreateTestAnswer(TestQuestion testQuestion){
		TestOnlineDao testOnlineDao = SpringContextHolder.getBean(TestOnlineDao.class);
		testOnlineDao.saveCreateTestAnswer(testQuestion);
	}
	@Transactional(readOnly=false)
	public void saveCreateTestKnowledge(TestQuestion testQuestion){
		TestOnlineDao testOnlineDao = SpringContextHolder.getBean(TestOnlineDao.class);
		testOnlineDao.saveCreateTestKnowledge(testQuestion);
	}
	
	@Transactional(readOnly=false)
	public static void editQuestionScore(TestPaper testPaper){
		TestOnlineDao testOnlineDao = SpringContextHolder.getBean(TestOnlineDao.class);
		testOnlineDao.editQuestionScore(testPaper);
	}
	
	/**
	 * 更新试题
	 */
	@Transactional(readOnly=false)
	public void updateTestQuestion(TestQuestion testQuestion){
		TestOnlineDao testOnlineDao = SpringContextHolder.getBean(TestOnlineDao.class);
		testOnlineDao.updateTestQuestion(testQuestion);
	}
	@Transactional(readOnly=false)
	public void updateTestAnswer(TestQuestion testQuestion){
		TestOnlineDao testOnlineDao = SpringContextHolder.getBean(TestOnlineDao.class);
		testOnlineDao.updateTestAnswer(testQuestion);
	}
	/**
	 * 教师删除课程测试题目
	 * yuanzhonglin
	 * 2016-9-7
	 */
	@Transactional(readOnly=false)
	public void deleteTestQuestion(String testId) {
		TestOnlineDao testOnlineDao = SpringContextHolder.getBean(TestOnlineDao.class);
		testOnlineDao.deleteTestQuestion(testId);
		testOnlineDao.deleteTestAnswer(testId);
		testOnlineDao.deleteQuestionKnowledge(testId);
	}
	@Transactional(readOnly=false)
	public void deletTestPaper(TestPaper testPaper) {
		TestOnlineDao testOnlineDao = SpringContextHolder.getBean(TestOnlineDao.class);
		testOnlineDao.deleteUserPaper(testPaper);
		int num = testOnlineDao.deletTestPaper(testPaper);
		if(num>0){
			testOnlineDao.deletePaperAndQuestion(testPaper);
		}
	}
	
	@Transactional(readOnly=true)
	public TestQuestion queryTestQuestionById(TestQuestion testQuestion){
		TestOnlineDao testOnlineDao = SpringContextHolder.getBean(TestOnlineDao.class);
		TestQuestion list = testOnlineDao.editCeshiQuestion(testQuestion);
		return list;
	}
	@Transactional(readOnly=false)
	public int updateTestQuestionKnowledgeLink(String testId,List<TestQuestion> qList){
		TestOnlineDao testOnlineDao = SpringContextHolder.getBean(TestOnlineDao.class);
		try{
			testOnlineDao.deleteQuestionKnowledge(testId);
			TestQuestion tq = new TestQuestion();
			for(int i=0;i<qList.size();i++){
				tq = qList.get(i);
				testOnlineDao.saveCreateTestKnowledge(tq);
			}
		}catch (Exception e) {
  			e.printStackTrace();
  			return -1;
  		}
		return 0;
	}

	/**
	 * 教师保存组卷
	 * 2016-9-13
	 */
	@Transactional(readOnly=false)
	public void saveTestPaper(TestPaper testPaper) {
		TestPaperDao testPaperDao = SpringContextHolder.getBean(TestPaperDao.class);
		testPaperDao.saveTestPaper(testPaper);
	}
	@Transactional(readOnly=false)
	public void savePaperQuestion(TestPaper testPaper) {
		TestPaperDao testPaperDao = SpringContextHolder.getBean(TestPaperDao.class);
		testPaperDao.savePaperQuestion(testPaper);
	}
	
	/**
	 * 将计算后的试题总分保存
	 */
	@Transactional(readOnly=false)
	public void updatePaperScore(TestPaper testPaper){
		TestPaperDao testPaperDao = SpringContextHolder.getBean(TestPaperDao.class);
		testPaperDao.updatePaperScore(testPaper);
	}
	
	/**
	 * 将生成的组卷发送给学生
	 */
	@Transactional(readOnly=false)
	public void sendPaperToUser(TestPaper testPaper){
		TestPaperDao testPaperDao = SpringContextHolder.getBean(TestPaperDao.class);
		testPaperDao.sendPaperToUser(testPaper);
	}
	
	/**
	 * 试题随机抽
	 */
	public static List<TestQuestion> queryQuestionRandom(TestQuestion question){
		TestOnlineDao testPaperDao = SpringContextHolder.getBean(TestOnlineDao.class);
		List<TestQuestion> list = testPaperDao.queryQuestionRandom(question);
		return list;
	}
	
	public static List<TestPaper> queryPaperByUser(TestPaper testPaper){
		TestPaperDao testPaperDao = SpringContextHolder.getBean(TestPaperDao.class);
		Pagination pager = PageContext.getPageContext();  //分页
		int pageNo = pager.getCurrentPage();
		int pageSize = pager.getPageSize();
		PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
		List<TestPaper> list = testPaperDao.queryPaperByUser(testPaper);
		return list;
	}
	
	/**
	 * 试卷的编辑修改及查看
	 */
	public List<TestPaper> paperDetail(String paperId){
		TestPaperDao testPaperDao = SpringContextHolder.getBean(TestPaperDao.class);
		List<TestPaper> paperDetail = testPaperDao.queryPaperDetail(paperId);
		return paperDetail;
	}
	/**
	 * 试卷总分计算
	 */
	public String sumPaperScore(String paperId){
		TestPaperDao testPaperDao = SpringContextHolder.getBean(TestPaperDao.class);
		String paperScore = testPaperDao.getPaperScore(paperId);
		return paperScore;
	}
	/**
	 * 试卷下发学生List集合查询
	 */
	public List<UserDetail> querySendPaperUser(UserDetail userDetail){
		StudentDao studentDao = SpringContextHolder.getBean(StudentDao.class);
		List<UserDetail> userList = studentDao.queryAllStudent(userDetail);
		return userList;
	}
	
	public List<UserDetail> errorQuestionDetail(UserDetail userDetail){
		StudentDao studentDao = SpringContextHolder.getBean(StudentDao.class);
		List<UserDetail> userList = studentDao.queryAllStudent(userDetail);
		return userList;
	}
	
	@Transactional(readOnly=false)
	public void updateTestPaperDetail(TestPaper tp){
		TestPaperDao testPaperDao = SpringContextHolder.getBean(TestPaperDao.class);
		testPaperDao.updateTestPaperDetail(tp);
	}

	@Transactional(readOnly=false)
	public void saveAnswerEveryQuestionDetail(TestPaper testPaper){
		TestPaperDao testPaperDao = SpringContextHolder.getBean(TestPaperDao.class);
		testPaperDao.saveAnswerEveryQuestionDetail(testPaper);
	}
	
	/**
	 * 试卷评分后保存
	 */
	@Transactional(readOnly=false)
	public void testPaperGrade(TestPaper testPaper){
		TestPaperDao testPaperDao = SpringContextHolder.getBean(TestPaperDao.class);
		testPaperDao.testPaperGrade(testPaper);
	}
	
	@Transactional(readOnly=false)
	public static void changePaperAnStatus(){
		TestPaperDao testPaperDao = SpringContextHolder.getBean(TestPaperDao.class);
		testPaperDao.changePaperAnStatus();
	}
	
	public static List<TestPaper> queryStudentPaper(TestPaper testPaper){
		Pagination pager = PageContext.getPageContext();  //分页
		int pageNo = pager.getCurrentPage();
	    int pageSize = pager.getPageSize();
	    PageHelper.startPage(pageNo,pageSize);  //拦截器分页开始
	    List<TestPaper> studentPaper = paperList(testPaper);
		return studentPaper;
	}
	
	public static List<TestPaper> paperList(TestPaper testPaper){
		TestPaperDao testPaperDao = SpringContextHolder.getBean(TestPaperDao.class);
		List<TestPaper> studentPaper = testPaperDao.queryStudentPaper(testPaper);
		return studentPaper;
	}
	
	/**
	 * 保存每道题得分
	 */
	@Transactional(readOnly=false)
	public void saveEveryAnswerScore(TestPaper testPaper){
		TestPaperDao testPaperDao = SpringContextHolder.getBean(TestPaperDao.class);
		testPaperDao.saveEveryAnswerScore(testPaper);
	}
	
	public List<TestPaper> queryTestPaperResult(TestPaper testPaper){
		TestOnlineDao testOnlineDao = SpringContextHolder.getBean(TestOnlineDao.class);
		List<TestPaper> list = testOnlineDao.queryTestPaperResult(testPaper);
		return list;
	}
	
}
