package com.module.course.service;


import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import com.common.utils.FileOperateUtil;

/**
 * 
 * 创建人：yanxb <br>
 * 创建时间：2016-7-18 <br>
 * 功能描述： 用户上传下载文件操作DAO<br>
 */
@Service("userFileService")
public class UserFileService {
	
	
//	在根路径下用UUID生成新的路径，用于保存文件
	public static String getNewFilePath(){
		  String uuid = UUID.randomUUID().toString();
		 
		  String newName = uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) ;
		   
		  String newFilePath =newName.substring(0,8)+"/"+newName.substring(8,16)+"/";
	
	return newFilePath;
}
	
	 /**
		 * @param response 
		 * @param filePath		//文件完整路径(包括文件名和扩展名)
		 * @param fileName		//下载后看到的文件名
		 * @return  文件名
		 */
		public static void fileDownload(final HttpServletResponse response, String filePath, String fileName) throws Exception{  
			     
			    byte[] data = FileOperateUtil.toByteArray2(filePath); 
			    //System
			    fileName = URLEncoder.encode(fileName, "UTF-8");  
			    response.reset();  
			    response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");  
			    response.addHeader("Content-Length", "" + data.length);  
			    response.setContentType("application/octet-stream;charset=UTF-8");  
			    OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());  
			    outputStream.write(data);  
			    outputStream.flush();  
			    outputStream.close();
			    response.flushBuffer();
			    
			} 
	
}