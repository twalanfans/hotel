package com.module.code.web;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.web.BaseController;
import com.github.pagehelper.PageInfo;
import com.module.code.entity.KeyCode;
import com.module.code.service.KeyCodeService;
import com.module.owncenter.entity.School;
import com.module.owncenter.service.SchoolService;
import com.module.sys.entity.User;
import com.module.sys.utils.FileDownAndUpload;
import com.module.sys.utils.UserUtils;

/**
 * 在线测试Controller类
 * @author yuanzhonglin
 * @2016-7-21
 */
@Controller
public class KeyCodeController extends BaseController{
	
	@RequestMapping(value="${adminPath}/code/queryCodeListPage")
	public String queryCodeList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String keyId = request.getParameter("keyId")==null?"":request.getParameter("keyId");
		String useNum = request.getParameter("useNum")==null?"":request.getParameter("useNum");
		KeyCode key = new KeyCode();
			key.setKeyId(keyId);
			if(!"".equals(useNum)){
				key.setUseNum(Integer.parseInt(useNum));
			}
		List<KeyCode> code = KeyCodeService.codeList(key);
		List<School> schoolList = SchoolService.querySchool(null);
		request.setAttribute("pageInfo", new PageInfo<KeyCode>(code));
		request.setAttribute("keyId", keyId);
		request.setAttribute("useNum", useNum);
		request.setAttribute("schoolList", schoolList);
		return "modules/sys/code/code_manage_query";
	}
	
	@RequestMapping(value="${adminPath}/code/createKeyCode")
	public String createKeyCode(HttpServletRequest request,HttpServletResponse response){
		String codeNum = request.getParameter("codeNum");
		String schoolId = request.getParameter("schoolId")==null?"0":request.getParameter("schoolId");
		int num =Integer.parseInt(codeNum);
		KeyCode code = new KeyCode();
			code.setUseNum(3);
			code.setStatus("0");;
			code.setSchoolId(schoolId);
		int ret=0;
		try {
			for(int i=0; i<num; i++){
				ret += KeyCodeService.createKeyCode(code);	
			}
			if(ret>0){
				return renderString(response, ret);				
			}else{
				return renderString(response, "error");				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	/**
	 * 生成激活码第二种方法
	 * @author hasee
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="${adminPath}/code/insertKeyCode")
	public String createKeyCode2(HttpServletRequest request,HttpServletResponse response){
		String codeNum = request.getParameter("codeNum");
		String schoolId = request.getParameter("schoolId")==null?"0":request.getParameter("schoolId");
		int num =Integer.parseInt(codeNum);
		KeyCode code = new KeyCode();
			code.setUseNum(3);
			code.setSchoolId(schoolId);
		try {
			KeyCodeService.insertKeyCode(code, schoolId,num);
			if(num>0){
				return renderString(response, num);				
			}else{
				return renderString(response, "error");				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	@RequestMapping(value="/code/checkKeyCode")
	public void checkKeyCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String keyId = request.getParameter("keyId")==null?"":request.getParameter("keyId");		
		String tip = request.getParameter("tip")==null?"":request.getParameter("tip");		//软件扫描标识
		Writer out = response.getWriter();
		System.out.println("check-Start....-----code:"+keyId+"-----tip--------"+tip);
		try {
			if("app".equals(tip)){
				KeyCode code = KeyCodeService.getCodeById(keyId);
				if(code!=null){
					int num = code.getUseNum();
					int useNum = num-1;
					if(num>0){
						KeyCode key = new KeyCode();
							key.setKeyId(keyId);
							key.setUseNum(useNum);
							key.setStatus("1");
						KeyCodeService.updateCodeInfo(key);
						System.out.println("还剩："+useNum+" 次激活。。。");
						out.write(useNum+"");
					}else{
						//激活次数已耗尽
						KeyCode key = new KeyCode();
							key.setKeyId(keyId);
							key.setUseNum(0);
							key.setStatus("2");
						KeyCodeService.updateCodeInfo(key);
						out.write("useOut");
					}
				}else{
					//无效的激活码
					out.write("invalid");
				}				
			}else{
				response.setContentType("text/html;charset=utf-8");    
				out.write("<script>window.location.href='http://www.javayuan.com/qrcheck.html';</script>");
			}
			out.flush();  
		} catch (Exception e) {
			e.printStackTrace();
			out.write("error");
		}
	}
	
	@RequestMapping(value="${adminPath}/code/printKeyCode")
	public String printKeyCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String keyNum = request.getParameter("keyNum")==null?"":request.getParameter("keyNum");
		String schoolId = UserUtils.getUser().getSchoolId();
		KeyCode key = new KeyCode();
			key.setUseNum(3);
			if(!"0".equals(schoolId)){
				key.setSchoolId(schoolId);
			}
		List<KeyCode> code = KeyCodeService.queryCodeList(key);
		int totalNum = code.size();
		int needNum = totalNum;
		if(!"".equals(keyNum)){
			needNum = Integer.parseInt(keyNum);
		}
		request.setAttribute("needNum", needNum);
		request.setAttribute("codeList", code);
		request.setAttribute("schoolName", UserUtils.getUser().getSchoolName());
		return "modules/sys/code/print_code_topaper";
	}
	
	@RequestMapping(value="${adminPath}/code/queryCodeListBySchoolPage")
	public String queryCodeListBySchool(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String keyId = request.getParameter("keyId")==null?"":request.getParameter("keyId");
		String useNum = request.getParameter("useNum")==null?"":request.getParameter("useNum");
		User user = UserUtils.getUser();
		KeyCode key = new KeyCode();
			key.setKeyId(keyId);
			if(!"".equals(useNum)){
				key.setUseNum(Integer.parseInt(useNum));
			}
			key.setSchoolId(user.getSchoolId());
		List<KeyCode> code = KeyCodeService.codeList(key);
		request.setAttribute("pageInfo", new PageInfo<KeyCode>(code));
		request.setAttribute("keyId", keyId);
		request.setAttribute("useNum", useNum);
		request.setAttribute("userDetail", user);
		return "modules/sys/code/query_admin_code";
	}
	
	@RequestMapping(value="${adminPath}/code/deleteKeyCode")
	public String deleteKeyCode(HttpServletRequest request,HttpServletResponse response){
		String keyId = request.getParameter("keyId");
		String idList[]=keyId.split(",");
		try {
			int ret=0;
			for(int i=0; i<idList.length; i++){
				String id = idList[i];
				String filePath = KeyCodeService.getCodeById(id).getQrCodePath();
				if(StringUtils.isNotBlank(filePath)){
					FileDownAndUpload.deleteFile(filePath);					
				}
				ret += KeyCodeService.deleteKeyCode(id);
			}
			return renderString(response, ret);
		} catch (Exception e) {
			e.printStackTrace();
			return renderString(response, "error");
		}
	}
	
	@RequestMapping(value="${adminPath}/code/packageToZip")
	public void packageToZip(HttpServletRequest request, HttpServletResponse response)throws Exception {
		String filePath = request.getParameter("filePath");
		String name = request.getParameter("name");
		name = new String(name.getBytes("ISO8859-1"), "UTF-8");
		String path[] = filePath.split(",");
		KeyCodeService.packageToZip(request, response, path, name);
	}
	@RequestMapping(value="${adminPath}/code/queryExcel")
	public void queryExcel(HttpServletRequest request, HttpServletResponse response)throws Exception {
		Integer num = Integer.parseInt(request.getParameter("num"));
		String keyId = request.getParameter("keyId")==null?"":request.getParameter("keyId");
		String useNum = request.getParameter("useNum")==null?"":request.getParameter("useNum");
		String filePath = request.getParameter("filePath");
		String name = request.getParameter("name");
		KeyCode key = new KeyCode();
			key.setKeyId(keyId);
			if(!"".equals(useNum)){
				key.setUseNum(Integer.parseInt(useNum));
			}
		List<KeyCode> code = KeyCodeService.codeList(key);
		List<School> schoolList = SchoolService.querySchool(null);
		request.setAttribute("pageInfo", new PageInfo<KeyCode>(code));
		request.setAttribute("keyId", keyId);
		request.setAttribute("useNum", useNum);
		request.setAttribute("schoolList", schoolList);
		KeyCodeService.queryExcel(request, response,num,name);
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
