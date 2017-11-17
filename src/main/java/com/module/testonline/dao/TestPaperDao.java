package com.module.testonline.dao;

import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.testonline.entity.TestPaper;


/**
 * @Description  课程Dao接口
 * @Author  yuanzhonglin
 * @Date  2016年7月12日 
 */

@MyBatisDao
public interface TestPaperDao extends CrudDao<TestPaper>{

	public int saveTestPaper(TestPaper testPaper);		
	
	public int savePaperQuestion(TestPaper testPaper);	
	
	public int deleteErrorQuestion(TestPaper testPaper);	
	
	public int updatePaperScore(TestPaper testPaper);	
	
	public int sendPaperToUser(TestPaper testPaper);	
	
	public int updateTestPaperDetail(TestPaper testPaper);	
	
	public int saveAnswerEveryQuestionDetail(TestPaper testPaper);	
	
	public int testPaperGrade(TestPaper testPaper);	
	
	public int saveEveryAnswerScore(TestPaper testPaper);	
	
	public int updateErrorNotes(TestPaper testPaper);	
	
	public int changePaperAnStatus();	
	
	public List<TestPaper> queryPaperByUser(TestPaper testPaper);
	
	public List<TestPaper> queryPaperDetail(String paperId);
	
	public List<TestPaper> queryStudentPaper(TestPaper testPaper);
	
	public List<TestPaper> queryMyErrorNotes(TestPaper testPaper);
	
	public String getPaperScore(String paperId);
	
}
