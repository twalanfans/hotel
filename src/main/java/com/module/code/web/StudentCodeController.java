package com.module.code.web;

import java.io.FileInputStream;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.common.web.BaseController;
import com.github.pagehelper.PageInfo;
import com.module.code.entity.KeyCode;
import com.module.code.entity.StudentCode;
import com.module.code.service.KeyCodeService;
import com.module.code.service.StudentCodeService;
import com.module.owncenter.entity.School;
import com.module.owncenter.service.SchoolService;

/**
 * 
 * @ClassName : StudentCodeController
 * @Description : TODO(学籍号控制层)
 * @author : aprwu
 * @date : 2017年11月27日 上午10:49:13
 */
@Controller
public class StudentCodeController extends BaseController{
	@RequestMapping(value="${adminPath}/code/queryStudentCodeListPage")
	public String queryCodeList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<StudentCode> queryCodeList = StudentCodeService.codeList();
		System.out.println("queryCodeList------"+queryCodeList);
		request.setAttribute("queryCodeList", queryCodeList);
		request.setAttribute("pageInfo", new PageInfo<StudentCode>(queryCodeList));
		return "modules/sys/code/studentcode_manage_query";
	}
	
	/*
	 * poi导入学籍号
	 */
	@RequestMapping(value="${adminPath}/code/createStudentCode")
	public String createKeyCode(HttpServletRequest request,HttpServletResponse response){
		FileInputStream fie;
		Workbook workbook;
		try {
			fie = new FileInputStream("/home/StudentCode.xls");
			//fie = new FileInputStream("d://StudentCode.xls");
			workbook = new HSSFWorkbook(fie);
			Sheet sheet = workbook.getSheet("学籍号");
			//获得总行数
			int rowNum = sheet.getLastRowNum();
			Row row = null;
			//跳过标题行
			for(int i =1;i<=rowNum;i++){
				StudentCode code = new StudentCode();
				row = sheet.getRow(i);
				//遍历行
				String studentCode = row.getCell(0).getStringCellValue();
				String studentName = row.getCell(1).getStringCellValue();
				String appleId = row.getCell(2).getStringCellValue();
				String andriodId = row.getCell(3).getStringCellValue();
				String pc = row.getCell(4).getStringCellValue();
				String schoolId = row.getCell(5).getStringCellValue();
				String schoolName = row.getCell(6).getStringCellValue();
				code.setStudentCode(studentCode);
				code.setStudentName(studentName);
				code.setAppleId(appleId);
				code.setPc(pc);
				code.setAndriodId(andriodId);
				code.setSchoolId(schoolId);
				code.setSchoolName(schoolName);
				StudentCodeService.createKeyCode(code);
			} 
			return renderString(response, "ok");
		}	catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return renderString(response, "error");
		}
		
		
	}
	/*
	 * 客户端传进来学籍号和姓名进行对比
	 * http://localhost:8989/3DGlobe/code/checkStudentCode?studentCode=111115&studentName=小白&id=pc&terrace=udsidi9a8c555ds
	 * http://www.lushangznkj.com/hotel/code/checkStudentCode?studentCode=111115&studentName=小白&id=pc&terrace=udsidi9a8c555ds
	 */
	@RequestMapping(value="/code/checkStudentCode" ,method = RequestMethod.GET)
	public void checkKeyCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String studentCode = request.getParameter("studentCode")==null?"":request.getParameter("studentCode");		
		String studentName = request.getParameter("studentName")==null?"":request.getParameter("studentName");
		studentName = new String(studentName.getBytes("ISO8859-1"), "UTF-8");
		//设备编号
		String id = request.getParameter("id")==null?"":request.getParameter("id");		//软件扫描标识
		//设备对应的ID
		String terrace = request.getParameter("terrace")==null?"":request.getParameter("terrace");
		System.out.println("studentCode------"+studentCode);
		System.out.println("studentName------"+studentName);
		System.out.println("id------"+id);
		System.out.println("terrace------"+terrace);
		Writer out = response.getWriter();
		/*
		 * 传进来学籍号，姓名，设备ID，设备编号判断设备是什么
		 */
		StudentCode key = new StudentCode();
		key.setStudentCode(studentCode);
		key.setStudentName(studentName);
		
		StudentCode code = StudentCodeService.getCodeById(studentCode,studentName);
		System.out.println("code------"+code);
		try {
			if("pc".equals(id)){
				if(code.getPc() == null || code.getPc().equals("")){
					code.setPc(terrace);
					StudentCodeService.updateCodeInfo(code);
					out.write("ok");
				}else if(code.getPc().equals(terrace)){
					out.write("ok");
				}else{
					//无效的激活码
					out.write("该用户已绑定PC设备");
				}			
			}else if("android".equals(id)){
				if(code.getAndriodId() == null || code.getAndriodId().equals("")){
					code.setAndriodId(terrace);
					StudentCodeService.updateCodeInfo(code);
					out.write("ok");
				}else if(code.getAndriodId().equals(terrace)){
					out.write("ok");
				}else{
					//无效的激活码
					out.write("该用户已绑定Android设备");
				}
			}else{
				if(code.getAppleId() == null || code.getAppleId().equals("")){
					code.setAppleId(terrace);
					StudentCodeService.updateCodeInfo(code);
					out.write("ok");
				}else if(code.getAppleId().equals(terrace)){
					out.write("ok");
				}else{
					//无效的激活码
					out.write("该用户已绑定IOS设备");
				}
			}
			out.flush();  
		} catch (Exception e) {
			e.printStackTrace();
			out.write("学籍号与姓名不匹配");
		}
	}
	
	
	
	@RequestMapping(value="${adminPath}/code/deleteStudentCode")
	public String deleteKeyCode(HttpServletRequest request,HttpServletResponse response){
		String studentCode = request.getParameter("studentCode");
		System.out.println("studentCode------"+studentCode);
		String idList[]=studentCode.split(",");
		try {
			int ret=0;
			for(int i=0; i<idList.length; i++){
				String id = idList[i];
				ret += StudentCodeService.deleteKeyCode(id);
			}
			return renderString(response, ret);
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	
	public static void main(String[] args) throws Exception {   
		/*  
	       byte[] buffer = new byte[1024];   
	  
	       //生成的ZIP文件名为Demo.zip   
	  
	       String strZipName = "C:/Users/t/Desktop/aaa.rar";
	  
	       ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipName));   
	  
	       //需要同时下载的两个文件result.txt ，source.txt   
	  
	       File[] file1 = {new File("C:/Users/t/Desktop/111.txt"),new File("C:/Users/t/Desktop/222.txt"),new File("C:/Users/t/Desktop/333.txt")};   
	  
	       for(int i=0;i<file1.length;i++) {   
	  
	           FileInputStream fis = new FileInputStream(file1[i]);   
	  
	           out.putNextEntry(new ZipEntry(file1[i].getName()));   
	  
	           int len;   
	  
	           //读入需要下载的文件的内容，打包到zip文件   
	  
	          while((len = fis.read(buffer))>0) {   
	  
	           out.write(buffer,0,len);    
	  
	          }   
	  
	           out.closeEntry();   
	  
	           fis.close();   
	  
	       }   
	  
	        out.close();   */
			String a = "1111222233334444";
			StringBuffer b = new StringBuffer();
			b.append(a.substring(0, 4)+"-");
			b.append(a.substring(4, 8)+"-");
			b.append(a.substring(8, 12)+"-");
			b.append(a.substring(12, 16));
	  
	        System.out.println("生成-----"+b.toString());   
	  
	    }  
	
}
