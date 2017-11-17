package com.module.course.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.course.entity.KnowledgeItem;
@MyBatisDao
public interface KnowledgeDao extends CrudDao<KnowledgeItem> {

	public int saveKnowledgeItem(@Param("item")KnowledgeItem item);
	public int updateKnowledgeItem(KnowledgeItem item);
	public List<KnowledgeItem> findKnowledgeItemAllById(@Param("id")Integer id);
	public List<KnowledgeItem> selectKnowledgeById(@Param("id")Integer id);
	public List<KnowledgeItem> selectKnowledgeByParentId(@Param("parentId")Integer parentId,@Param("courseId")String courseId);
	public List<KnowledgeItem> selectKnowledgeByCourseId(@Param("courseId")String courseId);
	public int deleteKnowledgeByCourseId(@Param("courseId")String courseId);
	public int deleteKnowledgeById(@Param("id")Integer id);
	public int  deleteKnowledgeByParentId(@Param("parentId")Integer parentId);
	public int  getKnowledgeItemCountByCourseId(@Param("courseId")String courseId);
	public List<KnowledgeItem>  getKnowledgeItemListByCourseFileId(@Param("courseId")String courseId,@Param("courseFileId")String courseFileId);
	public List<KnowledgeItem>  getKnowledgeItemListByTestQuestionId(@Param("courseId")String courseId,@Param("questionId")String questionId);
	public List<KnowledgeItem>  getKnowledgeTitleListByTestQuestionId(@Param("courseId")String courseId,@Param("questionId")String questionId);
	
}
