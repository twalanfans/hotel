package com.common.utils;



	import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.common.models.TimeBean; 
	 
	public class WeekdayUtils {
		
	public static List<TimeBean> getDateList(Date current){ 
	
		 List<TimeBean> list = new ArrayList<TimeBean>();
		 //格式化日期 
		 SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd"); 
		//格式化星期
		 SimpleDateFormat sdfWeek = new SimpleDateFormat("EEE"); 
		 
		 Date fdate; 
		 Long fTime=current.getTime(); 
		 //两周循环显示（14天） 
		 for(int i=0;i<7;i++){ 
			 fdate = new Date();
			 //fdate临时存储计算出来的时间（当前时间+未来第几天的时间i*24小时*3600秒*1000毫秒）
			 fdate.setTime(fTime+ (i * 24 * 3600000));
			 TimeBean tb=new TimeBean(); 
			 //获取当前日期 
			 tb.setDate(sdfDate.format(fdate)); 
			 //获取当前星期几 格式‘星期*’ 
			 tb.setWeek(sdfWeek.format(fdate)); 
	
			 //添加到list集合中
			list.add(tb); 
		} 
		return list; 
		} 
	
	public static String getGreetings(String role){
		String greetings = "";
		String currentTime = DateUtils.getDateTime();
		String hour[] = currentTime.split(" ");
		String h1 = hour[1];
		String h2[] = h1.split(":");
		int h = Integer.parseInt(h2[0]);
		if(h>0 && h<=5){
			greetings = role+",已经凌晨了，注意休息哦！";
		}else if(h>5 && h<=7){
			greetings = role+",早上好！";
		}else if(h>7 && h<=12){
			greetings = role+",上午好！";
		}else if(h>12 && h<=13){
			greetings = role+",吃饭时间到，注意饮食哦！";
		}else if(h>13 && h<=17){
			greetings = role+",下午好！";
		}else if(h>17 && h<=19){
			greetings = "夕阳无限好，只是近黄昏！";
		}else if(h>19 && h<=24){
			greetings = role+",晚上好！";
		}
		return greetings;
	}
}
