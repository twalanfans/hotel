package com.module.course.util;

import java.util.ArrayList;
import java.util.List;

import com.module.course.entity.KnowledgeItem;

/** 
 * 森林节点管理类 
 *  
 * @author  
 * 
 */  
public class ForestNodeManager {  
    private List<KnowledgeItem> list;// 森林的所有节点  
  
    public ForestNodeManager(KnowledgeItem[] items) {  
        list = new ArrayList<KnowledgeItem>();  
        for (KnowledgeItem treeNode : items) {  
            list.add(treeNode);  
        }  
    }  
  
    public ForestNodeManager(List<KnowledgeItem> items) {  
        list = items;  
    }  
  
    /** 
     * 根据节点ID获取一个节点 
     *  
     * @param id 
     *            节点ID 
     * @return 对应的节点对象 
     */  
    public KnowledgeItem getTreeNodeAT(int id) {  
       for (KnowledgeItem treeNode : list) {  
            if (treeNode.getId() == id)  
                return treeNode;  
        }  
        return null;  
    }  


/** 
* 获取树的根节点【一个森林对应多颗树】 
*  
* @return 树的根节点集合 
*/  
public List<KnowledgeItem> getRoot() {  
    List<KnowledgeItem> roots = new ArrayList<KnowledgeItem>();  
     for (KnowledgeItem treeNode : list) {  
         if (treeNode.getParentId() == 0)  
             roots.add(treeNode);  
     }  
     return roots;  
 }  
}  
