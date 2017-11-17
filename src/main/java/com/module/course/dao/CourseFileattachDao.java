package com.module.course.dao;

import java.util.List;
import com.common.persistence.annotation.MyBatisDao;
import com.module.course.entity.CourseFileattach;

/**
 * mayicheng
 * 16.9.2
 */
@MyBatisDao
public interface CourseFileattachDao {

	//遍历数据
	public List<CourseFileattach> selectByClassId(CourseFileattach coursefileattach);
	
	public CourseFileattach attachFileDetail(CourseFileattach coursefileattach);

	//添加数据的方法
	public int insert(CourseFileattach coursefileattach);
	
	//更新数据的方法
	public void updateByPrimaryKeySelective(CourseFileattach coursefileattach);
	
	//删除数据的方法
	public void deleteByPrimaryKey(CourseFileattach coursefileattach);
	
	public void deleteByCourseFileId(CourseFileattach coursefileattach);

}