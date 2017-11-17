package com.module.course.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.common.utils.DateUtils;
import com.module.sys.entity.Dictionary;
import com.module.sys.utils.DictionUtils;
//单例模式，教师新建科目
@Service

public class SubjectManageService {
private volatile static SubjectManageService uniqueInstance;
public static final String SUBJECT_DICT_CODE = "102";
   
    public static SubjectManageService getInstance()
    {
        if ( uniqueInstance == null )
        {
            synchronized ( SubjectManageService.class )
            {
                if ( uniqueInstance == null )
                {
                    uniqueInstance = new SubjectManageService();
                }
            }
        }
        return uniqueInstance;
    }
    @Transactional(readOnly=false)
    public synchronized static Dictionary addSubject(String userId,String title)
    {
    	List<Dictionary> list = new ArrayList<Dictionary>();
    	String code=SUBJECT_DICT_CODE;
    	Dictionary dict = new Dictionary();
    	int value;
    	String tempStr;
    	int ret =0;
    	Dictionary dictnew;
    	try{
	    	list = DictionUtils.findMaxRecordByCode(code);
	    	if(list==null || "".equals(list)||list.get(0)==null)
	    	{
	    		value=101;
	    	}
	    	else
	    	{
	    		tempStr=list.get(0).getValue();
	    		value=Integer.parseInt(tempStr)+1;
	    	}
	    	dict.setValue(Integer.toString(value));
	    	dict.setCodeId(code);
	    	dict.setDictDescription("科目");
	    	dict.setLabel(title);
	    	
	    	dict.setCreateByUser(userId);
	    	dict.setCreateTime(DateUtils.strToDateTime(DateUtils.getDateTime()));
	    	dictnew =DictionUtils.addDictionRecord(dict);
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		return null;
	    	}
    	
    	return dictnew;
    }
    
    
  
}
