package com.module.exam.dao;

import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.exam.entity.CriticismExam;

@MyBatisDao
public interface CriticismDao extends CrudDao<CriticismExam>{
	
	public List<CriticismExam> queryCriticismFile();
	
	public List<CriticismExam> queryCriticismKnowledge();

	public List<CriticismExam> queryCriticismPaperList(String userId);
	
	@SuppressWarnings("rawtypes")
	public List queryCriticismQuestion(CriticismExam criticismExam);
	
	public int saveCriticismPaper(CriticismExam criticismExam);
	
	public int saveCriticismPaperQuestion(CriticismExam criticismExam);
}
