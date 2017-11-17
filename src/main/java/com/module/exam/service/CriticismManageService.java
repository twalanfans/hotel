package com.module.exam.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.utils.SpringContextHolder;
import com.module.exam.dao.CriticismDao;
import com.module.exam.entity.CriticismExam;
import com.module.sys.utils.UserUtils;

/**
 * @Description  职业资格考试服务Service
 * @Author  yuanzhonglin
 * @Date  2016年9月29日 
 */
@Service
@Transactional(readOnly=true)
public class CriticismManageService {

	@Autowired
	private CriticismDao criticismDao;
	
	/**
	 * 考证服务资料下载初始页
	 */
	@Transactional(readOnly=true)
	public List<CriticismExam> queryCriticismServiceFile(){
		List<CriticismExam> list = criticismDao.queryCriticismFile();
		return list;
	}
	
	/**
	 * 考证自测查询知识点
	 */
	@Transactional(readOnly=true)
	public List<CriticismExam> queryCriticismKnowledge(){
		List<CriticismExam> list = criticismDao.queryCriticismKnowledge();
		return list;
	}
	
	/**
	 * 查询考证知识点的所有题的Id
	 */
	@SuppressWarnings("rawtypes")
	public List queryCriticismQuestion(CriticismExam criticismExam){
		List list = criticismDao.queryCriticismQuestion(criticismExam);
		return list;
	}
	
	/**
	 * 查询个人所有组的考证服务卷
	 */
	public List<CriticismExam> queryCriticismPaperList(){
		String userId = UserUtils.getUser().toString();
		List<CriticismExam> list = criticismDao.queryCriticismPaperList(userId);
		return list;
	}
	
	/**
	 * 将组成的考证服务自测卷保存
	 */
	@Transactional(readOnly=false)
	public void saveCriticismPaper(CriticismExam criticismExam){
		CriticismDao criticismDao = SpringContextHolder.getBean(CriticismDao.class);
		criticismDao.saveCriticismPaper(criticismExam);
	}
	@Transactional(readOnly=false)
	public void saveCriticismPaperQuestion(CriticismExam criticismExam){
		CriticismDao criticismDao = SpringContextHolder.getBean(CriticismDao.class);
		criticismDao.saveCriticismPaperQuestion(criticismExam);
	}
}