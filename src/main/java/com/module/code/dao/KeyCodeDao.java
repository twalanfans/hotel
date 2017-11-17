package com.module.code.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.common.persistence.CrudDao;
import com.common.persistence.annotation.MyBatisDao;
import com.module.code.entity.KeyCode;


/**
 * @Description  激活码Dao接口
 * @Author  yuanzhonglin
 * @Date  2016年7月12日 
 */

@MyBatisDao
public interface KeyCodeDao extends CrudDao<KeyCode>{
	
	public List<KeyCode> queryCodeList(KeyCode keyCode);
	
	public int createCode(KeyCode keyCode);

	public int updateCodeInfo(KeyCode keyCode);

	public int deleteById(String keyId);
	
	public KeyCode getCodeById(String keyId);
	/**
	 * 生成激活码的第二种方式
	 * @author hasee
	 * @param keyCode
	 * @return
	 */
	public int insertCode(List<KeyCode> keyCode);
	//导出用的查询
	public List<KeyCode> queryExcel(@Param(value="num") Integer num);
}
