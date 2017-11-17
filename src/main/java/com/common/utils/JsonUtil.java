package com.common.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;





public class JsonUtil {

	    /**
	     * 从一个JSON 对象字符格式中得到一个java对象
	     * @param jsonString
	     * @param pojoCalss
	     * @return
	     */
	    public static Object getObject4JsonString(String jsonString,Class pojoCalss){
	        Object pojo;
	        JSONObject jsonObject = JSONObject.fromObject( jsonString );  
	        pojo = JSONObject.toBean(jsonObject,pojoCalss);
	        return pojo;
	    }
	    
	    /**
	     * 从json HASH表达式中获取一个map，改map支持嵌套功能
	     * @param jsonString
	     * @return
	     */
	    @SuppressWarnings("rawtypes")
		public static Map getMap4Json(String jsonString){
	        JSONObject jsonObject = JSONObject.fromObject( jsonString );  
	        Iterator  keyIter = jsonObject.keys();
	        String key;
	        Object value;
	        Map valueMap = new HashMap();

	        while( keyIter.hasNext())
	        {
	            key = (String)keyIter.next();
	            value = jsonObject.get(key);
	            valueMap.put(key, value);
	        }
	        
	        return valueMap;
	    }
	    
	    
	    /**
	     * 从json数组中得到相应java数组
	     * @param jsonString
	     * @return
	     */
	    public static Object[] getObjectArray4Json(String jsonString){
	        JSONArray jsonArray = JSONArray.fromObject(jsonString);
	        return jsonArray.toArray();
	        
	    }
	    
	    
	    /**
	     * 从json对象集合表达式中得到一个java对象列表
	     * @param jsonString
	     * @param pojoClass
	     * @return
	     */
	    public static List getList4Json(String jsonString, Class pojoClass){
	        
	        JSONArray jsonArray = JSONArray.fromObject(jsonString);
	        JSONObject jsonObject;
	        Object pojoValue;
	        
	        List list = new ArrayList();
	        for ( int i = 0 ; i<jsonArray.size(); i++){
	            
	            jsonObject = jsonArray.getJSONObject(i);
	            pojoValue = JSONObject.toBean(jsonObject,pojoClass);
	            list.add(pojoValue);
	            
	        }
	        return list;

	    }
	    
	    /**
	     * 从json数组中解析出java字符串数组
	     * @param jsonString
	     * @return
	     */
	    public static String[] getStringArray4Json(String jsonString){
	        
	        JSONArray jsonArray = JSONArray.fromObject(jsonString);
	        String[] stringArray = new String[jsonArray.size()];
	        for( int i = 0 ; i<jsonArray.size() ; i++ ){
	            stringArray[i] = jsonArray.getString(i);
	            
	        }
	        
	        return stringArray;
	    }
	    
	    /**
	     * 从json数组中解析出javaLong型对象数组
	     * @param jsonString
	     * @return
	     */
	    public static Long[] getLongArray4Json(String jsonString){
	        
	        JSONArray jsonArray = JSONArray.fromObject(jsonString);
	        Long[] longArray = new Long[jsonArray.size()];
	        for( int i = 0 ; i<jsonArray.size() ; i++ ){
	            longArray[i] = jsonArray.getLong(i);
	            
	        }
	        return longArray;
	    }
	    
	    /**
	     * 从json数组中解析出java Integer型对象数组
	     * @param jsonString
	     * @return
	     */
	    public static Integer[] getIntegerArray4Json(String jsonString){
	        
	        JSONArray jsonArray = JSONArray.fromObject(jsonString);
	        Integer[] integerArray = new Integer[jsonArray.size()];
	        for( int i = 0 ; i<jsonArray.size() ; i++ ){
	            integerArray[i] = jsonArray.getInt(i);
	            
	        }
	        return integerArray;
	    }
	    
	    /**
	     * 从json数组中解析出java Integer型对象数组
	     * @param jsonString
	     * @return
	     */
	    public static Double[] getDoubleArray4Json(String jsonString){
	        
	        JSONArray jsonArray = JSONArray.fromObject(jsonString);
	        Double[] doubleArray = new Double[jsonArray.size()];
	        for( int i = 0 ; i<jsonArray.size() ; i++ ){
	            doubleArray[i] = jsonArray.getDouble(i);
	            
	        }
	        return doubleArray;
	    }
	    
	    
	    /**
	     * 将java对象转换成json字符串
	     * @param javaObj
	     * @return
	     */
	    public static String getJsonString4JavaPOJO(Object javaObj){
	        
	        JSONObject json;
	        json = JSONObject.fromObject(javaObj);
	        return json.toString();
	        
	    }
	    
	    public static String string2Json(String s) {        
	        StringBuffer sb = new StringBuffer();        
	        for (int i=0; i<s.length(); i++) {  
	            char c = s.charAt(i);    
	             switch (c){  
	             case '\"':        
	                 sb.append("\\\"");        
	                 break;        
	             case '\\':        
	                 sb.append("\\\\");        
	                 break;        
	             case '/':        
	                 sb.append("\\/");        
	                 break;        
	             case '\b':        
	                 sb.append("\\b");        
	                 break;        
	             case '\f':        
	                 sb.append("\\f");        
	                 break;        
	             case '\n':        
	                 sb.append("\\n");        
	                 break;        
	             case '\r':        
	                 sb.append("\\r");        
	                 break;        
	             case '\t':        
	                 sb.append("\\t");        
	                 break;        
	             default:        
	                 sb.append(c);     
	             }  
	         }      
	        return sb.toString();     
	        }  

	    // 将数组转换成JSON   
	    public static String array2json(Object object) {   
	        JSONArray jsonArray = JSONArray.fromObject(object);   
	        return jsonArray.toString();   
	    }   
	   
	    // 将JSON转换成数组,其中valueClz为数组中存放的对象的Class   
	    public static Object json2Array(String json, Class valueClz) {   
	        JSONArray jsonArray = JSONArray.fromObject(json);   
	        return JSONArray.toArray(jsonArray, valueClz);   
	    }   
	   
	    // 将Collection转换成JSON   
	    public static String collection2json(Object object) {   
	        JSONArray jsonArray = JSONArray.fromObject(object);   
	        return jsonArray.toString();   
	    }   
	   
	    // 将JSON转换成Collection,其中collectionClz为Collection具体子类的Class,   
	    // valueClz为Collection中存放的对象的Class   
	    public static Collection json2Collection(String json, Class collectionClz,   
	            Class valueClz) {   
	        JSONArray jsonArray = JSONArray.fromObject(json);   
	        return JSONArray.toCollection(jsonArray, valueClz);   
	    }   
	   
	    // 将Map转换成JSON   
	    public static String map2json(Object object) {   
	        JSONObject jsonObject = JSONObject.fromObject(object);   
	        return jsonObject.toString();   
	    }   
	   
	    // 将JSON转换成Map,其中valueClz为Map中value的Class,keyArray为Map的key   
	    @SuppressWarnings("rawtypes")
		public static Map json2Map(Object[] keyArray, String json, Class valueClz) {   
	        JSONObject jsonObject = JSONObject.fromObject(json);   
	        Map classMap = new HashMap();   
	   
	        for (int i = 0; i < keyArray.length; i++) {   
	            classMap.put(keyArray[i], valueClz);   
	        }   
	   
	        return (Map) JSONObject.toBean(jsonObject, Map.class, classMap);   
	    }   
	   
	    // 将POJO转换成JSON   
	    public static String bean2json(Object object) {   
	        JSONObject jsonObject = JSONObject.fromObject(object);   
	        return jsonObject.toString();   
	    }   
	   
	    // 将JSON转换成POJO,其中beanClz为POJO的Class   
	    public static Object json2Object(String json, Class beanClz) {   
	        return JSONObject.toBean(JSONObject.fromObject(json), beanClz);   
	    }   
	   
	    // 将String转换成JSON   
	    public static String string2json(String key, String value) {   
	        JSONObject object = new JSONObject();   
	        object.put(key, value);   
	        return object.toString();   
	    }   
	   
	    // 将JSON转换成String   
	    public static String json2String(String json, String key) {   
	        JSONObject jsonObject = JSONObject.fromObject(json);   
	        return jsonObject.get(key).toString();   
	    } 
	    //将list数组转换成 JSON,label为标志
	    public static JSONObject List2Json(String Label,List<?> T)
	    {
	    	Map<String,Object> map = new HashMap<String,Object>(); 
	    	map.put(Label, T); 
	    	JSONObject jsonObjectFromMap = JSONObject.fromObject(map); 
	    	//System.out.println(jsonObjectFromMap);
	    	return jsonObjectFromMap;
	    }
	    
/*	  public static <T> String List2Json(List<T> list)
		    {
		    	ResponseData rs = new ResponseData();
		    	if(list==null||list.isEmpty())
		    	{
		    		rs.setResult("ERROR");
		    		list = new  ArrayList<T>();
		    		rs.setResultList(list);
		    		
		    	}
		    	else
		    	{
			    	rs.setResult("SUCCESS");
					rs.setResultList(list);
			    }
				Gson gson = new Gson();  
		        //创建一个返回值对象          
		        //将学生对象转换成JSON串  
		        String response = gson.toJson(rs);
		        System.out.println(response.toString());
		        return response;
		    }*/

	    /**
	     * @param args
	     */
	    public static void main(String[] args) {
	        	

	    }
	
	   /* public static void main(String[] args) { 
	    	//当是对象的时候 
	    	Student student = new Student(); 
	    	student.setAge(18); 
	    	student.setName("zhangsan"); 
	    	student.setSex("male"); 
	    	JSONObject jsonObject = JSONObject.fromObject(student); 
	    	System.out.println(jsonObject);//输出{"age":18,"name":"zhangsan","sex":"male"} 
	    	//当是数组或list的时候 
	    	Student[] stus = new Student[5]; 
	    	List<Student> stuList = new ArrayList<Student>(); 
	    	for (int i = 0; i < stus.length; i++) { 
	    	stus[i] = new Student(); 
	    	stus[i].setAge(i*10+8); 
	    	stus[i].setName("zhang"+i); 
	    	stus[i].setSex("male"); 
	    	//添加到list,一会儿用 
	    	System.out.println("stu[i]="+stus[i].toString());
	    	stuList.add(stus[i]); 
	    	} 
	    	JSONObject obj= List2Json("SUCCESS",stuList);
	    	System.out.println(obj.toString());
	    }*/
	}

