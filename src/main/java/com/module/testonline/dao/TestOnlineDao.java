package com.module.testonline.dao;
import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.testonline.entity.TestPaper;
import com.module.testonline.entity.TestQuestion;


/**
 * @Description  课程Dao接口
 * @Author  yuanzhonglin
 * @Date  2016年7月12日 
 */

@MyBatisDao
public interface TestOnlineDao extends CrudDao<TestQuestion>{

	public List<TestQuestion> queryTeacherCeshiQuestion(TestQuestion testQuestion);
	
	public List<TestQuestion> queryQuestionRandom(TestQuestion question);
	
	public List<TestQuestion> questionDetailList(TestQuestion question);
	
	public List<TestPaper> queryTestPaperResult(TestPaper testPaper);
	
	public int deletTestPaper(TestPaper testPaper);

	public int editQuestionScore(TestPaper testPaper);
	
	public int deletePaperAndQuestion(TestPaper testPaper);

	public int deleteUserPaper(TestPaper testPaper);
	
	public int deleteTestQuestion(String testId);
	
	public int deleteTestAnswer(String testId);
	
	public int deleteQuestionKnowledge(String testId);
	
	public int saveCreateTestQuestion(TestQuestion testQuestion);		//保存试题详情
	
	public int saveCreateTestAnswer(TestQuestion testQuestion);
	
	public int saveCreateTestKnowledge(TestQuestion testQuestion);		//保存试题知识点
	
	public TestQuestion editCeshiQuestion(TestQuestion testQuestion); 
	
	public int updateTestQuestion(TestQuestion testQuestion);
	
	public int updateTestAnswer(TestQuestion testQuestion);		
	
}
