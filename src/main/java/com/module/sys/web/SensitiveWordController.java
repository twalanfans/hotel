package com.module.sys.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.web.BaseController;
import com.module.sys.service.SensitiveWordService;

@Controller
public class SensitiveWordController extends BaseController{
	
	/**
     * 敏感词过滤
     * 
     * @param text
     * @return
     */
    @RequestMapping(value = "word/sensitiveWord")
    public String sensitiveWordFiltering(HttpServletRequest request, HttpServletResponse response){
    	String text = request.getParameter("text");
    	String result="";
	        try
	        {
	        	SensitiveWordService  filter = SensitiveWordService.getInstance();  
	             
	            result = filter.replaceSensitiveWord(text, 1, "*");  
	        }
	        catch (Exception e)
	        {
	            System.out.println("过滤敏感词出错，请联系维护人员");
	            e.printStackTrace();
	         }
	      return renderString(response, result);
    }

}
