package com.module.code.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.code.entity.KeyCode;
import com.module.code.entity.StudentCode;


/**
 * 
 * @ClassName : StudentCodeDao
 * @Description : TODO(类的作用)
 * @author : aprwu
 * @date : 2017年11月27日 上午9:45:10
 */
@MyBatisDao
public interface StudentCodeDao extends CrudDao<StudentCode>{
	
	public List<StudentCode> queryCodeList();
	
	public StudentCode queryStudentCodeById(
			@Param("studentCode")String studentCode,
			@Param("studentName")String studentName);
	
	public int insertStudentCode(StudentCode studentCode);

	public int updateCodeInfo(StudentCode studentCode);

	public int deleteById(String studentCode);
	
	
}
