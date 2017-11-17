package com.module.sys.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class ResponseData<T>  implements Serializable {  
		  
	    /** 
	     * Serializable 
	     */  
	    private static final long serialVersionUID = -2689979321936117293L;  
	      
	    private String result;  
	      
	    private List<T> resultList;  
	  
	    /** 
	     *  
	     * @return result 成功失败标志 
	     */  
	    public String getResult() {  
	        return result;  
	    }  
	  
	    /** 
	     *  
	     * @param name 成功失败标志  
	     */  
	    public void setResult(String result) {  
	        this.result = result;  
	    }  
	  
	    /** 
	     *  
	     * @return resultList 结果列表 
	     */  
	    public List<T> getResultList() {  
	        return resultList;  
	    }  
	  
	    /** 
	     *  
	     * @param  resultList 结果列表 
	     */  
	    public void setResultList(List<T> resultList) {  
	        this.resultList = resultList;  
	    }  
	  
	      
	}  

