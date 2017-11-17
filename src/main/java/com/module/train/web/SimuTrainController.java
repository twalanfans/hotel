package com.module.train.web;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.web.BaseController;

/**
 * 仿真实训Controller类
 * @author yuanzhonglin
 * @date 2016-7-21
 */
@Controller
public class SimuTrainController extends BaseController{
		
	/**
	 * 仿真实训的虚拟漫游初始化展示
	 * 2016-7-21
	 */
	@RequestMapping(value="${adminPath}/train/caseTable")
	public String queryCaseTable(){
		return "modules/sys/student/train/student_3D_workTable";
	}
	
	@RequestMapping(value="${adminPath}/train/VR_Rescource")
	public  String queryVR(HttpServletRequest request, HttpServletResponse response) throws Exception {  
		return "modules/sys/student/train/vr_resource_query";
	}  
	
	@RequestMapping(value="/save3DStudyFile")
	public  void save3DStudyFile(HttpServletRequest request, HttpServletResponse response) throws IOException {  
		String userId = request.getParameter("UserId");
		String workNumber = request.getParameter("WorkNum");
		String file = request.getParameter("TextureData");
		String workTitle = request.getParameter("workTitle");
		String fileType = request.getParameter("FileType");
		System.out.println("3d开启！！！！");
		Writer out = response.getWriter(); 
		out.write("success");
	 }  
}
