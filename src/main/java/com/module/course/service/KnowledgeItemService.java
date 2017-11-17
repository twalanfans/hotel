package com.module.course.service;

import java.util.ArrayList;
import java.util.List;

import com.module.course.entity.KnowledgeItem;
import com.module.course.util.ForestNodeManager;
import com.module.course.util.KnowledgeItemUtils;

import net.sf.json.JSONArray;

public class KnowledgeItemService {
	
	 private List<KnowledgeItem> list;// 树的所有节点  
	   
	    public void  KnowledgeItemService(KnowledgeItem[] items) {  
	         list = new ArrayList<KnowledgeItem>();  
	         for (KnowledgeItem treeNode : items) {  
	             list.add(treeNode);  
	         }  
	     }  
	   
	     public void  KnowledgeItemService(List<KnowledgeItem> items) {  
	         this.list = items;  
	     }  
	   
	     /** 
	      * 根据节点ID获取一个节点 
	      *  
	      * @param id 
	      *            节点ID 
	      * @return 对应的节点对象 
	      */  
	    public KnowledgeItem  getTreeNodeAT(int id) {  
	        for (KnowledgeItem  treeNode : list) {  
	            if (treeNode.getId() == id)  
	                return treeNode;  
	        }  
	        return null;  
	    }  
	  
	     /** 
	      * 获取树的根节点 
	      *  
	      * @return 一棵树的根节点 
	      */  
	     public KnowledgeItem getRoot() {  
	         for (KnowledgeItem treeNode : list) {  
	             if (treeNode.getParentId() == 0)  
	                 return treeNode;  
	         }  
	        return null;  
	     }  

    /** 
         * 将节点数组归并为一个森林（多棵树）（填充节点的children域） 
         * 时间复杂度为O(n^2) 
         * @param items 节点域 
         * @return 多棵树的根节点集合 
         */  
        public static List<KnowledgeItem> merge(KnowledgeItem[] items){  
            ForestNodeManager forestNodeManager = new ForestNodeManager(items);  
           for (KnowledgeItem treeNode : items) {  
               if(treeNode.getParentId()!=0){  
            	   KnowledgeItem t = forestNodeManager.getTreeNodeAT(treeNode.getParentId());  
                   t.getChildren().add(treeNode);  
               }  
           }  
           return forestNodeManager.getRoot();  
       }  
         
   


    /** 
         * 将节点数组归并为一个森林（多棵树）（填充节点的children域） 
         * 时间复杂度为O(n^2) 
         * @param items 节点域 
         * @return 多棵树的根节点集合 
         */  
        public static List<KnowledgeItem> merge(List<KnowledgeItem> items){  
            ForestNodeManager forestNodeManager = new ForestNodeManager(items);  
            for (KnowledgeItem treeNode : items) { 
            	treeNode.setPId(treeNode.getParentId());
            	treeNode.setName(treeNode.getTitle());
            	if(treeNode.getParentId()!=0){  
                	KnowledgeItem t = forestNodeManager.getTreeNodeAT(treeNode.getParentId());  
                    if(t== null) continue;
                	t.getChildren().add(treeNode);  
                }  
                
            }  
           return forestNodeManager.getRoot();  
        }  
      //删除知识点，应该同时删除知识点和课件关联表的对应记录
    	public int deleteKnowledgeItemById(int KnowledgeItemId)
    	{
    		int id = KnowledgeItemId;
    		int ret;
    		KnowledgeItemUtils kiu = new KnowledgeItemUtils();
    		ret = kiu.deleteKnowledgeItemById(KnowledgeItemId);
    		
    		if(ret<0)
    			return -1;
    		
    		if(ret<0)
    			return -1;
    		
    		return ret;
    		
    	}
       
}
