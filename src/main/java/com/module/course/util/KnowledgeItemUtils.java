package com.module.course.util;

import java.util.ArrayList;
import java.util.List;
import com.common.utils.DateUtils;
import com.common.utils.SpringContextHolder;
import com.module.course.dao.KnowledgeDao;
import com.module.course.entity.KnowledgeItem;

public class KnowledgeItemUtils {
	private static KnowledgeDao knowledgeDao = SpringContextHolder.getBean(KnowledgeDao.class);
	  List<KnowledgeItem> level1List=new ArrayList<KnowledgeItem>();
      List<KnowledgeItem> level2List=new ArrayList<KnowledgeItem>();
      List<KnowledgeItem> level3List=new ArrayList<KnowledgeItem>();
     
      
	public void addKnowledgeFromExcel(List<KnowledgeItem> listItem,String courseId,String userId){
		
		for(int i=0;i<listItem.size();i++){
			KnowledgeItem item= new KnowledgeItem();
			item = listItem.get(i);
			item.setCourseId(courseId);
			item.setCreateBy(userId);
			item.setCreateTime(DateUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
			if(item.getLevel()== 1)
				level1List.add(item);
			else if(item.getLevel()== 2)	
				level2List.add(item);
			else if(item.getLevel()== 3)	
				level3List.add(item);
			else
				continue;
		}
		for(int i=0;i<level1List.size();i++)
		{
			KnowledgeItem item= new KnowledgeItem();
			item= level1List.get(i);
			
			knowledgeDao.saveKnowledgeItem(item);
			level1List.get(i).setId(item.getId());
			
		}
		for(int i=0;i<level2List.size();i++)
		{
			KnowledgeItem item= new KnowledgeItem();
			item= level2List.get(i);
			String title= item.getParentTitle();
			for(int j=0;j<level1List.size();j++)
			{
				KnowledgeItem item1= new KnowledgeItem();
				item1= level1List.get(j);
				String title1 = item1.getTitle();
				if(title.equals(title1))
				{
					item.setParentId(item1.getId());
					break;
				}
				
			}
			knowledgeDao.saveKnowledgeItem(item);
		
		}
		for(int i=0;i<level3List.size();i++)
		{
			KnowledgeItem item= new KnowledgeItem();
			item= level3List.get(i);
			String title= item.getParentTitle();
			for(int j=0;j<level2List.size();j++)
			{
				KnowledgeItem item1= new KnowledgeItem();
				item1= level2List.get(j);
				String title1 = item1.getTitle();
				if(title.equals(title1))
				{
					item.setParentId(item1.getId());
					break;
				}
				
			}
			knowledgeDao.saveKnowledgeItem(item);
		
		}
	}
	
	public List<KnowledgeItem>	findKnowledgeItemAllById(int id){
		List<KnowledgeItem> list = new ArrayList<KnowledgeItem>();
		
		list= knowledgeDao.findKnowledgeItemAllById(id);
			return list;
	}
	public List<KnowledgeItem>	getKnowledgeListByParentId(String courseId, int parentId){
		List<KnowledgeItem> list = new ArrayList<KnowledgeItem>();
		
		list= knowledgeDao.selectKnowledgeByParentId(parentId,courseId);
			return list;
	}
	public KnowledgeItem getKnowledgeBean(String courseId, int parentId,int level){   
		try{   
			//��ȡ���ڵ�
			List<KnowledgeItem> knode = knowledgeDao.selectKnowledgeById(parentId);
			KnowledgeItem node;
			if(knode==null || knode.isEmpty())
				node  = new KnowledgeItem();
			else
				node= knode.get(0);
					
			List<KnowledgeItem> list = knowledgeDao.selectKnowledgeByParentId(parentId,courseId);//ÿ�β�ѯ���ϼ�Ϊ�ķ��� 
			//System.out.println(list.size());
			if(list != null && list.size() > 0){   
				for(int i=0;i<list.size();i++){   
					KnowledgeItem n= getKnowledgeBean(courseId,list.get(i).getId(),level+1);   //�ݹ��ѯ
					node.getChildren().add(n);
				} 
			}
			else{   
				level--;
			}
			return node;
		}catch(Exception e){   
			e.printStackTrace();  
			return null;
		}
	
	}   
		public void getKnowledgeListAll(List<KnowledgeItem> sortList,String courseId, int parentId,int level){   

		KnowledgeItem bean = null;   
		List<KnowledgeItem> list = new ArrayList<KnowledgeItem>();   
		System.out.println("level="+level);
		try{   
			list = knowledgeDao.selectKnowledgeByParentId(parentId,courseId);//ÿ�β�ѯ���ϼ�Ϊ�ķ��� 
			System.out.println(list.size());
			if(list != null && list.size() > 0){   
				for(int i=0;i<list.size();i++){   
					bean = (KnowledgeItem)list.get(i);  
					//bean.setLevel(level+1);			//��ӵȼ��ֶ�
					sortList.add(bean);   
					getKnowledgeListAll(sortList,courseId,bean.getId(),level+1);   //�ݹ��ѯ
				}   
			}else{   
				level--;
			}
		}catch(Exception e){   
			e.printStackTrace();   
		}
	
	}   
	public static void deleteKnowledgeItemByCourseId(String courseId){
		knowledgeDao.deleteKnowledgeByCourseId(courseId);
	}
	public List<KnowledgeItem> getKnowledgeById(int knowledgeItemId)
	{
		return knowledgeDao.selectKnowledgeById(knowledgeItemId);
	}
	//ɾ��֪ʶ�㣬Ӧ��ͬʱɾ��֪ʶ��Ϳμ�������Ķ�Ӧ��¼
	public int deleteKnowledgeItemById(int KnowledgeItemId)
	{
		int id = KnowledgeItemId;
		
		return knowledgeDao.deleteKnowledgeById(id);
		
	}
	public int addKnowledgeItemSingle(KnowledgeItem item)
	{
		return knowledgeDao.saveKnowledgeItem(item);
	}
	public static  int getCourseKnowLedgeCount(String courseId){
		return knowledgeDao.getKnowledgeItemCountByCourseId(courseId);
	}
	public static List<KnowledgeItem> getCourseKnowledgeItemAll(String courseId)
	{
		return knowledgeDao.selectKnowledgeByCourseId(courseId);
	}
	
	public static List<KnowledgeItem> getKnowledgeItemListByCourseFileId(String courseId,String courseFileId)
	{
		return knowledgeDao.getKnowledgeItemListByCourseFileId(courseId,courseFileId);
	}
	public static List<KnowledgeItem> getKnowledgeItemListByTestQuestionId(String courseId,String courseFileId)
	{
		return knowledgeDao.getKnowledgeItemListByTestQuestionId(courseId,courseFileId);
	}
	

}
