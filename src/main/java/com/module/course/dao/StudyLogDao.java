package com.module.course.dao;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.course.entity.StudyLog;

/**
 * 学习日志接口dao
 * @author yuanzhonglin
 * @date 2016-7-13
 */
@MyBatisDao
public interface StudyLogDao extends CrudDao<StudyLog>{
		
		
}
