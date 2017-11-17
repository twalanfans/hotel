package com.module.sys.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.module.sys.entity.Dictionary;
import com.module.sys.utils.DictionUtils;

public class DictionaryServlet  extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		PrintWriter out = response.getWriter(); 
		
		List<Dictionary> dictList=new ArrayList<Dictionary>();
		Dictionary dict=new Dictionary();
		String dictCode = request.getParameter("dictCode");
		dictCode="109";
		dictList=DictionUtils.findListByCode(dictCode);
		if(dictList==null ||dictList.isEmpty())
		{
			dictList=new ArrayList<Dictionary>();
		}
		Gson gson = new Gson(); 
		map.put("success",dictList);
		out.print(gson.toJson(map));
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}


