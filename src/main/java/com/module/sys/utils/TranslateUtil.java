package com.module.sys.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 常用类型的数据字典
 * @author yuanzhonglin
 * @date 2016-7-22
 */
public class TranslateUtil {
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public List sexTranslate(){
			List sexList = new ArrayList();
			Map map = new HashMap<String, String>();
				map.put("1", "男");
				map.put("2", "女");
			sexList.add(map);
			return sexList;
		}
		
		/**
		 * 测试题型
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static List questionTranslate(){
			List questionType = new ArrayList();
			Map map = new HashMap();
				map.put("11","单选题");
				map.put("12","多选题");
				map.put("21","名词解释");
				map.put("22","简答题");
				map.put("23","案例分析");
				map.put("24","翻译");	
				map.put("25","填空");	
			questionType.add(map);
			return questionType;
		}
		
}
