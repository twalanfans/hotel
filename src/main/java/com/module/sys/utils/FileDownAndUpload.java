package com.module.sys.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.common.config.Global;
import com.common.utils.CutImgUtils;
import com.common.utils.DateTime;
import com.common.utils.UserAgentUtils;
import com.common.web.BaseController;
import com.module.course.entity.CourseFile;
import com.module.course.service.UserFileService;
import com.module.owncenter.service.CheckerManageService;

@Controller
public class FileDownAndUpload extends BaseController implements ProgressListener {
        private long bytesRead = 0;
        private long totalBytes = -1;
        private boolean succeed = false;
       @Override
        public void progressChanged(ProgressEvent progressEvent) {
            long bytes = progressEvent.getBytes();
            ProgressEventType eventType = progressEvent.getEventType();
            switch (eventType) {
            case TRANSFER_STARTED_EVENT:
                System.out.println("Start to download......");
                break;
            case RESPONSE_CONTENT_LENGTH_EVENT:
                this.totalBytes = bytes;
                break;
            case RESPONSE_BYTE_TRANSFER_EVENT:
                this.bytesRead += bytes;
                if (this.totalBytes != -1) {
                    int percent = (int)(this.bytesRead * 100.0 / this.totalBytes);
                    System.out.println( "进度: " + percent + "%");
                } else {
                    System.out.println("下载出现问题");
                }
                break;
            case TRANSFER_COMPLETED_EVENT:
                this.succeed = true;
                System.out.println("下载完成！");
                break;
            case TRANSFER_FAILED_EVENT:
                System.out.println("下载失败！");
                break;
            default:
                break;
            }
        }
        public boolean isSucceed() {
            return succeed;
        }

		public static void main(String[] args) { 
		    String endpoint = Global.getConfig("endpoint");
		    String accessKeyId = Global.getConfig("accessKeyId");
		    String accessKeySecret = Global.getConfig("accessKeySecret");
		    String bucketName = Global.getConfig("bucketName");
		    String key = "courseFile/ea907830/bb38480c/2016120117.mp4";
		    OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		    try {            
		            // 带进度条的下载
		    	ossClient.getObject(new GetObjectRequest(bucketName, key).<GetObjectRequest>withProgressListener(new FileDownAndUpload()),  new File("d:/我的视屏.mp4"));
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    ossClient.shutdown();
		}
		
		/*OSS文件上传*/
		public static String uploadFile(String orgFileName, String subDir, MultipartFile file) throws IOException{
			String bigRealFilePath="";
			try {
				Date baseDate = new Date();
				String newFileName =DateTime.toDate("yyyyMMddHHmmss", baseDate); //重组文件名
				Pattern p = Pattern.compile("\\s|\t|\r|\n");
				Matcher m = p.matcher(orgFileName);
				orgFileName = m.replaceAll("_");
				
				String newFilePath= UserFileService.getNewFilePath(); //UUID生成路径
				String realFilePath = subDir +"/"+newFilePath;
				bigRealFilePath = realFilePath  +  newFileName.concat(".").concat(FilenameUtils.getExtension(orgFileName).toLowerCase());
				
				String endpoint = Global.getConfig("endpoint");
				// accessKey请登录https://ak-console.aliyun.com/#/查看
				String accessKeyId = Global.getConfig("accessKeyId");
				String accessKeySecret = Global.getConfig("accessKeySecret");
				String bucketName = Global.getConfig("bucketName");
				// 创建OSSClient实例
				OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
				//	InputStream inputStream = new FileInputStream("D:/课程试题默认数据20161028.sql");
				ossClient.putObject(bucketName, bigRealFilePath, new ByteArrayInputStream(file.getBytes()));
				// 关闭client
				ossClient.shutdown();
			} catch (OSSException e) {
				e.printStackTrace();
			} catch (ClientException e) {
				e.printStackTrace();
			}
			return bigRealFilePath;
		}
		
		
		/**OSS流文件下载**/
		@RequestMapping(value="test/downFile")
		public void  downLoadFile(HttpServletRequest request ,HttpServletResponse response) throws IOException{
			String fileId = request.getParameter("fileId");
			CourseFile courseFile = new CourseFile();
			courseFile.setFileId(fileId);
			CheckerManageService checkerManageService = new CheckerManageService();
	    	List<CourseFile> list = checkerManageService.getCourseFileDetail(courseFile);
	    	if(!list.isEmpty()){
	    		try {
					CourseFile file = list.get(0);
					String endpoint = Global.getConfig("endpoint");
					String accessKeyId = Global.getConfig("accessKeyId");
					String accessKeySecret = Global.getConfig("accessKeySecret");
					String bucketName = Global.getConfig("bucketName");
					String key = file.getFilePath();
					// 创建OSSClient实例
					OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
					OSSObject ossObject = ossClient.getObject(bucketName, key);
					String length = String.valueOf(ossObject.getObjectMetadata().getContentLength());
					// 读Object内容
					request.setCharacterEncoding("UTF-8");
					response.setContentType("application/octet-stream; charset=utf-8");
					response.setHeader("Content-disposition", "attachment;" + UserAgentUtils.encodeFileName(request, file.getFileName()));
					response.setHeader("Content-Length",length);
					BufferedInputStream bis = new BufferedInputStream(ossObject.getObjectContent());
					BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
					byte[] buff = new byte[2048];
					int bytesRead;
					while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
						bos.write(buff, 0, bytesRead);
					}
					bis.close();
					bos.close();
					// 关闭client
					ossClient.shutdown();
				} catch (OSSException e) {
					e.printStackTrace();
				} catch (ClientException e) {
					e.printStackTrace();
				}
	    	}
		}
		
		/**公用下载方法*/
		public static void commonDownLoad(String filePath, String fileName,HttpServletRequest request, HttpServletResponse response) throws IOException{
			try {
				String endpoint = Global.getConfig("endpoint");
				String accessKeyId = Global.getConfig("accessKeyId");
				String accessKeySecret = Global.getConfig("accessKeySecret");
				String bucketName = Global.getConfig("bucketName");
				// 创建OSSClient实例
				OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
				OSSObject ossObject = ossClient.getObject(bucketName, filePath);
				String length = String.valueOf(ossObject.getObjectMetadata().getContentLength());
				// 读Object内容
				request.setCharacterEncoding("UTF-8");
				response.setContentType("application/octet-stream; charset=utf-8");
				response.setHeader("Content-disposition", "attachment;" + UserAgentUtils.encodeFileName(request, fileName));
				response.setHeader("Content-Length",length);
				BufferedInputStream bis = new BufferedInputStream(ossObject.getObjectContent());
				BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
				byte[] buff = new byte[2048];
				int bytesRead;
				while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
					bos.write(buff, 0, bytesRead);
				}
				bis.close();
				bos.close();
				// 关闭client
				ossClient.shutdown();
			} catch (OSSException e) {
				e.printStackTrace();
			} catch (ClientException e) {
				e.printStackTrace();
			}
		}
		
		/*OSS案例图片库文件上传*/
		public static String uploadAttachFile(String orgFileName, String filePath, MultipartFile file) throws IOException{
			String bigRealFilePath="";
			try {
				Pattern p = Pattern.compile("\\s|\t|\r|\n");
				Matcher m = p.matcher(orgFileName);
				orgFileName = m.replaceAll("_");
				bigRealFilePath = filePath + "/" + orgFileName;
				
				String endpoint = Global.getConfig("endpoint");
				// accessKey请登录https://ak-console.aliyun.com/#/查看
				String accessKeyId = Global.getConfig("accessKeyId");
				String accessKeySecret = Global.getConfig("accessKeySecret");
				String bucketName = Global.getConfig("bucketName");
				// 创建OSSClient实例
				OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
				//	InputStream inputStream = new FileInputStream("D:/课程试题默认数据20161028.sql");
				ossClient.putObject(bucketName, bigRealFilePath, new ByteArrayInputStream(file.getBytes()));
				// 关闭client
				ossClient.shutdown();
			} catch (OSSException e) {
				e.printStackTrace();
			} catch (ClientException e) {
				e.printStackTrace();
			}
			return bigRealFilePath;
		}
		
	public static void deleteFile(String filePath){
		String endpoint = Global.getConfig("endpoint");
		String accessKeyId = Global.getConfig("accessKeyId");
		String accessKeySecret = Global.getConfig("accessKeySecret");
		String bucketName = Global.getConfig("bucketName");
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		ossClient.deleteObject(bucketName,filePath);
		ossClient.shutdown();
	}
	
	public static String cutImgAndUpload(String orgFileName, String filePath, MultipartFile file) throws IOException{
		String bigRealFilePath="";
		try {
			Pattern p = Pattern.compile("\\s|\t|\r|\n");
			Matcher m = p.matcher(orgFileName);
			orgFileName = m.replaceAll("_");
			bigRealFilePath = filePath + "/" + orgFileName;
			
			//将图片进行裁剪
			InputStream input = file.getInputStream();
	        BufferedImage bufferedimage=ImageIO.read(input);
	        int width = bufferedimage.getWidth();
	        int height = bufferedimage.getHeight();
	        //目标将图片裁剪成 宽200，高200
	        if (width > 200){
	            	bufferedimage=CutImgUtils.cropImage(bufferedimage,(int) ((width - 200) / 2),0,(int) (width - (width-200) / 2),(int) (height));
		        if (height > 200) {
		            bufferedimage=CutImgUtils.cropImage(bufferedimage,0,(int) ((height - 200) / 2),200,(int) (height - (height - 200) / 2));
	            }
	        }else{
	            if (height > 200) {
	                bufferedimage=CutImgUtils.cropImage(bufferedimage,0,(int) ((height - 200) / 2),(int) (width),(int) (height - (height - 200) / 2));
	            }
	        }
	        ByteArrayOutputStream os = new ByteArrayOutputStream();
	        ImageIO.write(bufferedimage, "jpg", os);
	        InputStream is = new ByteArrayInputStream(os.toByteArray());
		        
			String endpoint = Global.getConfig("endpoint");
			// accessKey请登录https://ak-console.aliyun.com/#/查看
			String accessKeyId = Global.getConfig("accessKeyId");
			String accessKeySecret = Global.getConfig("accessKeySecret");
			String bucketName = Global.getConfig("bucketName");
			// 创建OSSClient实例
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			//	InputStream inputStream = new FileInputStream("D:/课程试题默认数据20161028.sql");
			ossClient.putObject(bucketName, bigRealFilePath,is);
			// 关闭client
			ossClient.shutdown();
		} catch (OSSException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return bigRealFilePath;
	}
	/**
	 * 二维码生成图片上传
	 */
	public static String qrCodeUpload(String filePath, InputStream input) throws IOException{
		String bigRealFilePath="";
		try {
			String newFileName =DateTime.toDate("yyyyMMddHHmmss", new Date()); //重组文件名	
			String realFilePath = "QrCode/"+filePath;
			bigRealFilePath = realFilePath  +  newFileName.concat(".").concat("jpg");
			
			String endpoint = Global.getConfig("endpoint");
			// accessKey请登录https://ak-console.aliyun.com/#/查看
			String accessKeyId = Global.getConfig("accessKeyId");
			String accessKeySecret = Global.getConfig("accessKeySecret");
			String bucketName = Global.getConfig("bucketName");
			// 创建OSSClient实例
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			// InputStream inputStream = new FileInputStream("D:/课程试题默认数据20161028.sql");
			ossClient.putObject(bucketName, bigRealFilePath, input);
			// 关闭client
			ossClient.shutdown();
			return bigRealFilePath;
		} catch (OSSException e) {
			e.printStackTrace();
			return "";
		} catch (ClientException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
}