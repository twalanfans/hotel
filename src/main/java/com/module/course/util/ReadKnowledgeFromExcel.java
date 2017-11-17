package com.module.course.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.common.config.Global;
import com.common.utils.ArrayUtil;
import com.module.course.entity.KnowledgeItem;

public class ReadKnowledgeFromExcel {
	       //总行数
	       private int totalRows = 0;  
	      
	       //总条数
	       private int totalCells = 0; 
	       
	       //错误信息接收器
	       private String errorMsg;
	             
	       //构造方法
	       public ReadKnowledgeFromExcel(){}
	       
	       //得到总行数
	       public int getTotalRows()  { return totalRows;} 
	       
	       //得到总列数
	       public int getTotalCells() {  return totalCells;} 
	       
	       public String getErrorInfo() { return errorMsg; }  
	       
	     /**
	      * 描述：验证EXCEL文件
	      * @param filePath
	      * @return
	      */
	     public boolean validateExcel(String filePath){
	           if (filePath == null || !(WDWUtil.isExcel2003(filePath) || WDWUtil.isExcel2007(filePath))){  
	               errorMsg = "文件名不是excel格式";  
	               return false;  
	           }  
	           return true;
	     }
	       
	     
	     /**描述 :读EXCEL文件
	      * @param fielName
	      * @return
	      */
	     public List<KnowledgeItem> getExcelInfo(String fileName,String courseId){
	         
	         //把spring文件上传的MultipartFile转换成File
//	          CommonsMultipartFile cf= (CommonsMultipartFile)Mfile; 
//	          DiskFileItem fi = (DiskFileItem)cf.getFileItem();
//	          File file = fi.getStoreLocation(); 
	          
	         List<KnowledgeItem> itemList=new ArrayList<KnowledgeItem>();
	         InputStream is = null;  
	         try{
	             //验证文件名是否合格
	             if(!validateExcel(fileName)){
	                 return null;
	             }
	             //判断文件时2003版本还是2007版本
	             boolean isExcel2003 = true; 
	             if(WDWUtil.isExcel2007(fileName)){
	                 isExcel2003 = false;  
	             }
	      //       is = new FileInputStream(fileName);
	            String endpoint = Global.getConfig("endpoint");
				String accessKeyId = Global.getConfig("accessKeyId");
				String accessKeySecret = Global.getConfig("accessKeySecret");
				String bucketName = Global.getConfig("bucketName");
				// 创建OSSClient实例
				OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
				OSSObject ossObject = ossClient.getObject(bucketName, fileName);
				BufferedInputStream bis = new BufferedInputStream(ossObject.getObjectContent());
	            itemList=getExcelInfo(fileName,bis, isExcel2003); 
	            bis.close();
	         }catch(Exception e){
	             e.printStackTrace();
	         }
	       
	         return itemList;
	     }
	     /**
	      * 此方法两个参数InputStream是字节流。isExcel2003是excel是2003还是2007版本
	      * @param is
	      * @param isExcel2003
	      * @return
	      * @throws IOException
	      */
	     public  List<KnowledgeItem> getExcelInfo(String filePath,InputStream is,boolean isExcel2003){
	         
	          List<KnowledgeItem> itemList=new ArrayList<KnowledgeItem>();
	          try{
	              /** 根据版本选择创建Workbook的方式 */
	              Workbook wb = null;
	              //当excel是2003时
	              if(isExcel2003){
	                  wb = new HSSFWorkbook(is); 
	              }
	              else{
	                  wb = new XSSFWorkbook(is); 
	              }
	              itemList=readExcelValue(wb,filePath);
	          }
	          catch (IOException e)  {  
	              e.printStackTrace();  
	          }  
	          return itemList;
	     }
	     /**
	      * 读取Excel里面的信息
	      * @param wb
	      * @return
	      */
	     private List<KnowledgeItem> readExcelValue(Workbook wb,String filePath){ 
	    	 String tempStr ="";
	    	 String level1="";
	    	 String level2="";
	    	 String level3="";
	          //得到第一个shell  
	          Sheet sheet=wb.getSheetAt(0);
	          
	          //得到Excel的行数
	          this.totalRows=sheet.getPhysicalNumberOfRows();
	          
	          //得到Excel的列数(前提是有行数)
	          if(totalRows>=1 && sheet.getRow(0) != null){
	               this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
	          }
	          
	          List<KnowledgeItem> itemList=new ArrayList<KnowledgeItem>();
	         
	          ArrayList<String> level2TitleList= new ArrayList<String>();
	           //循环Excel行数,从第二行开始。标题不入库
	          for(int r=1;r<totalRows;r++){
	              Row row = sheet.getRow(r);
	              if (row == null) continue;
	              
	              KnowledgeItem item = new KnowledgeItem();
	              //循环Excel的列
	              for(int c = 0; c <this.totalCells; c++){    
	                  Cell cell = row.getCell(c); 
	                 // System.out.println("Cell="+cell.toString());
	                  if (null != cell){
	                	  //第一列
	                      if(c==0){
	                          //获得第一列<用户名>，放到到用户基本信息bean中。
	                    	  if(row.getCell(0)!=null){
	                              row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
	                              tempStr=row.getCell(0).getStringCellValue();
	                    	  	}
	                    	 // tempStr = cell.getStringCellValue();
	                    	  
	                    	  if(tempStr==null ||"".equals(tempStr.trim())){
	                    		 // System.out.println("tempStr is NULL");
	                    	  }
	                    	  else
	                    	  {
	                    		  KnowledgeItem item1 = new KnowledgeItem();
	                    		  level1=tempStr.trim();
	                    		  item1.setParentId(0);
	                    		  item1.setParentTitle("null");
	                    		  item1.setLevel(1);
	                    		  item1.setTitle(cell.getStringCellValue());
	                    		  item1.setFilePath(filePath);
	                    		  itemList.add(item1);
	                    	  }  
	                    	 
	                      }
	                      //获得第二列
	                      else if(c==1){
	                          /**
	                           * 处理：使用POI读excel文件，当遇到特殊格式的字串，比如“13612345678”，等等，
	                           * 这样的本来是一个字符串，但是POI在读的时候总是以数值型识别，由此，这样的电话号码读出来后总是1.3XXX+E4
	                           */
	                         
	                    	  if(row.getCell(1)!=null){
	                              row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
	                              tempStr=row.getCell(1).getStringCellValue();
	                         }
	                    	  //tempStr = cell.getStringCellValue();
	                    	  if(tempStr==null ||"".equals(tempStr.trim())){
	                    	  }
	                    	  else{
	                    		  KnowledgeItem item2 = new KnowledgeItem();
	                    		  level2=tempStr.trim();
	                    		  
	                    		  if(ArrayUtil.str_in_array(level2TitleList,level2)>=0){
	                    			  level2=level1+"_"+level2;
	                    			  System.out.println("newLevel2="+level2);
	                    		  }
	                    		  System.out.println("tempStr==========="+level2);
	                    		  level2TitleList.add(level2);
	                    		  item2.setLevel(2);
	                    		  item2.setParentTitle(level1);
	                    		  item2.setTitle(level2);
	                    		  item2.setFilePath(filePath);
	                    		  level2TitleList.add(level2);
	                    		  itemList.add(item2);
	                    	  }
	                      }
	                      //第三列
	                      else if(c==2){
	                    	  KnowledgeItem item3 = new KnowledgeItem();
	                    	  if(row.getCell(2)!=null){
	                              row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
	                              tempStr=row.getCell(2).getStringCellValue();
	                          }
	                    	  if(tempStr==null ||"".equals(tempStr.trim()))
	                    	  {
	                    	  }
	                    	  else
	                    	  {
	 
		                    	  level3=tempStr.trim();
	                    		  item3.setLevel(3);
	                    		  //item3.setParentId(0);
	                    		  item3.setParentTitle(level2);
	                    		  item3.setTitle(tempStr);
	                    		  item3.setFilePath(filePath);
	                    		  level3= tempStr;
	                    		  if(row.getCell(3)!=null){
		                              row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
		                              tempStr=row.getCell(3).getStringCellValue();
		                         }
	                    		  item3.setDescription(tempStr);
	                    		  itemList.add(item3);
			                      }
	                      }
	                      //第四列<用户地址>,放到到用户基本信息bean中。
	                      
	                  }
	              }
	           }
	          return itemList;
	     }
	     /**  
		  * @描述：工具类 
		  * 检验是否是EXCEL文件
		  */   
		 static class WDWUtil {  
		      // @描述：是否是2003的excel，返回true是2003 
		     public static boolean isExcel2003(String filePath)  {  
		         return filePath.matches("^.+\\.(?i)(xls)$");  
		     }  
		   
		      //@描述：是否是2007的excel，返回true是2007 
		     public static boolean isExcel2007(String filePath)  {  
		         return filePath.matches("^.+\\.(?i)(xlsx)$");  
		     }  
		 }
		

	 }
	

