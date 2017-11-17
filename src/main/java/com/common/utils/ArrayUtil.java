package com.common.utils;

import java.util.ArrayList;

public class ArrayUtil {
	public static int  str_in_array (String[] strs, String s){
	    for(int i = 0; i < strs.length; i++){
	        if(strs[i].equals(s)){
	            return i;
	         }
	    }
	    return -1;
	}


public static int  str_in_array (ArrayList<String> list, String s)
{
	
		//String[] array=(String[])list.toArray();  
		int size=list.size();  
		
        String[] array=new String[size]; 
        
        for(int i=0;i<list.size();i++){  
            array[i]=(String)list.get(i);  
        }  
       
		return str_in_array(array,s);
	}

}
