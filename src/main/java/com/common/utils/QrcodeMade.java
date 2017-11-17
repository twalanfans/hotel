package com.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.common.config.Global;
import com.common.web.BaseController;
import com.module.sys.utils.FileDownAndUpload;
import com.swetake.util.Qrcode;

@Controller
public class QrcodeMade extends BaseController {

	  @RequestMapping("/lsutil/qrcode")
	  public void qrcodeMade(HttpServletRequest request, HttpServletResponse response) throws IOException{
		 	String tmp = request.getParameter("url")==null?"":request.getParameter("url");
	        //调用框架生成二维码
	        Qrcode handler = new Qrcode();
	        handler.setQrcodeErrorCorrect('M');
	        handler.setQrcodeEncodeMode('B');
	        handler.setQrcodeVersion(6);

	        byte[] contentBytes = tmp.getBytes("UTF-8");

	        BufferedImage bufImg = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);

	        Graphics2D gs = bufImg.createGraphics();

	        gs.setBackground(Color.WHITE);
	        gs.clearRect(0, 0, 256, 256); //140

	        //设定图像颜色：BLACK
	        gs.setColor(Color.BLACK);

	        //设置偏移量  不设置可能导致解析出错
	        int pixoff = 4;  //2
	  
	        //输出内容：二维码
	        if(contentBytes.length > 0 && contentBytes.length < 124) {
	            boolean[][] codeOut = handler.calQrcode(contentBytes);
	            for(int i = 0; i < codeOut.length; i++) {
	                for(int j = 0; j < codeOut.length; j++) {
	                    if(codeOut[j][i]) {
	                        gs.fillRect(j * 6 + pixoff, i * 6 + pixoff,6, 6);		//3
	                    }
	                }
	            }           
	        } else {
	            Log.error("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ",null);
	        }
	        gs.dispose();
	        bufImg.flush();
	        ImageIO.write(bufImg, "jpg", response.getOutputStream());
	  }
	  
	  public static String qrcodeMade(String code, String schoolId) throws IOException{
		  	String qrCodePath = "";
	        Qrcode handler = new Qrcode();
	        handler.setQrcodeErrorCorrect('M');
	        handler.setQrcodeEncodeMode('B');
	        handler.setQrcodeVersion(6);
	        String tmp = Global.getConfig("qrCodeUrl")+code;		//二维码带参数路劲
	        byte[] contentBytes = tmp.getBytes("UTF-8");
	        BufferedImage bufImg = new BufferedImage(256, 290, BufferedImage.TYPE_INT_RGB);

	        Graphics2D gs = bufImg.createGraphics();

	        gs.setBackground(Color.WHITE);
	        gs.clearRect(0, 0, 256, 290); //140
	        //设定图像颜色：BLACK
	        gs.setColor(Color.BLACK);
	        //设置偏移量  不设置肯能导致解析出错
	        int pixoff = 4;  //2  
	        //输出内容：二维码
	        if(contentBytes.length > 0 && contentBytes.length < 124) {
	            boolean[][] codeOut = handler.calQrcode(contentBytes);
	            for(int i = 0; i < codeOut.length; i++) {
	                for(int j = 0; j < codeOut.length; j++) {
	                    if(codeOut[j][i]) {
	                        gs.fillRect(j * 6 + pixoff, i * 6 + pixoff,6, 6);		//3
	                    }
	                }
	           }
	           String logo =Global.getConfig("logoPath");
	           Image img = ImageIO.read(new File(logo));	//实例化一个Image对象
	           gs.drawImage(img, 86, 86, null);
	           gs.setBackground(Color.WHITE);
	           gs.setFont(new Font("微软雅黑",Font.BOLD,20)); 
	           gs.drawString(code, 7, 273);
	        } else {
	            Log.error("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ",null);
	        }
	        gs.dispose();
	        bufImg.flush();   
	        try {
				ByteArrayOutputStream bs = new ByteArrayOutputStream();  
				ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);  
				ImageIO.write(bufImg, "jpg", imOut);  
				InputStream is = new ByteArrayInputStream(bs.toByteArray());
				qrCodePath = FileDownAndUpload.qrCodeUpload(schoolId+"/"+code+"/", is);
				if(bs!=null){
					bs.close();					
				}
				if(imOut!=null){
					imOut.close();					
				}
				if(is!=null){
					is.close();					
				}
				return qrCodePath;
			} catch (Exception e) {
				e.printStackTrace();
				Log.error("QRCode upload to oss error ！！！",null);
				return "";
			}
	  }
	  
	  
	
}
