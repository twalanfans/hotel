package com.module.course.dao;

import java.util.List;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.course.entity.CourseFile;

/**
 * @Description  课程文件接口
 * @Author yuanzhonglin
 * @Date  2016年7月15日 
 */

@MyBatisDao
public interface CourseFileDao extends CrudDao<CourseFile>{
	public int insertCourseFile(CourseFile cf);
	public int updateCourseFileByFileId(CourseFile cf);
	public int deleteCourseFileById(String fileId);
	public int deleteCourseFileByCourseId(String courseId);
	public List<CourseFile> getCourseFileByCourseId(CourseFile cf);
	public List<CourseFile> getCourseFileById(CourseFile cf);
	public List<CourseFile> findAllCourseFile(CourseFile cf);
	public List<CourseFile> findAllListChk(CourseFile cf);
	public List<CourseFile> queryNeedDeal();
	public List<CourseFile> historyVideoList(String userId);
	public int deleteSource(CourseFile courseFile);
	public int changFileStatus(CourseFile courseFile);
	public int updateFileTimeLong(CourseFile courseFile);
	
}
