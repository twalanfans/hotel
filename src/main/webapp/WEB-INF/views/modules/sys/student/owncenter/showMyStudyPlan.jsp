<!-- showMyStudyPlan.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page import= "com.module.course.entity.StudyPlan"%> 
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/calendar.css" />
<!-- 栏目说明 -->
<div class="navExplain">
	<span>我的学习计划</span>
</div>
<!-- 学习计划搜索 -->
<div class="searchBeaf">
	<span class="mb8 mr5">
		<label>计划名称</label>
		<input type="text" id="planName" class="inpt" value="${planName}"/>
	</span>
	<span class="mb8 mr5">
		<label>开始时间</label>
		<input type="text" name="seeMyPlan" id="startTime" class="form-control inpt" readonly value="${startTime}" />
	</span>
	<span class="mb8 mr5">
		<a href="javascript:;" class="defaultBtn" id="planSearchBtn" ng-click="myStudyPlanPage('1');">搜索</a>
	</span>
	<span class="mb8">
		<a ui-sref="studentAddStudyPlan" ui-sref-opts="{reload:true}" class="defaultBtn" id="addNewPlan">新建计划</a>
	</span>
	<span class="mb8">
		<a href="javascript:;" class="defaultBtn" id="deletePlan">删除计划</a>
	</span>
</div>
<!-- 学习计划展示 -->
<div class="allTable">
	<table>
		<thead>
			<tr>
				<td><input type="checkbox" id="selallbtn" /><label for="selallbtn">全选</label></td>
				<td>计划名称</td>
				<td>计划开始日期</td>
				<td>计划结束日期</td>
				<td>创建时间</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="plan" items="${pageInfo.list}">   
			<tr>
				<td>
					<input type="checkbox" class="selbtn" name="planId" value="${plan.id}"/>
				</td>
				<td class="planName">
					<a ui-sref="studentEditStudyPlanDetail({planId : ${plan.id} })" ui-sref-opts="{reload:true}">${plan.title}</a>
				</td>
				<td>${plan.startTime}</td>
				<td>${plan.endTime}</td>    
				<td><fmt:formatDate value="${plan.createtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>    
			</tr> 
  			</c:forEach>   
		</tbody>
	</table>
</div>
<!-- 翻页 -->
<div class="pageList">
	<span>共<b>${pageInfo.total}</b>条</span>
	<span>每页<b>${pageInfo.pageSize}</b>条</span>
	<span>当前第<b>${pageInfo.pageNum}</b>页</span>
	<span>
		<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}"><span>首页</span><span>上一页</span></c:if>
       	<c:if test="${pageInfo.pageNum != 1 && pageInfo.pages > 1}">
       	<a href="javascript:;" ng-click="myStudyPlanPage('1')">首页</a>
       	<a href="javascript:;" ng-click="myStudyPlanPage('${pageInfo.pageNum-1}')">上一页</a>
       	</c:if>
       	<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span>下一页</span><span>尾页</span></c:if>
       	<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
       	<a href="javascript:;" ng-click="myStudyPlanPage('${pageInfo.pageNum+1}')">下一页</a>
       	<a href="javascript:;" ng-click="myStudyPlanPage('${pageInfo.pages}')">尾页</a>
       	</c:if>
	</span>
</div>
<script type="text/javascript" src="${ctxStatic}/js/calendar.js"></script>
<script type="text/javascript">
	$(function(){
		//配置日历表组件
		$("input.form-control").manhuaDate({					       
			Event : "click",//可选				       
			Left : 0,//弹出时间停靠的左边位置
			Top : -16,//弹出时间停靠的顶部边位置
			fuhao : "-",//日期连接符默认为-
			isTime : false,//是否开启时间值默认为false
			beginY : 1949,//年份的开始默认为1949
			endY :2100//年份的结束默认为2049
		});
	});
	
	//全选/取消
	$('#selallbtn').on('click',function(){
		if($('#selallbtn').prop('checked')){
			$('.selbtn').prop('checked',true);
		}else{
			$('.selbtn').prop('checked',false);
		}
	});
	
	$("#deletePlan").click(function(){
		var planIds = [];
		$('input[name="planId"]:checked').each(function(){ 
			planIds.push(this.value);
		});  
		if(planIds.length<1){
			alert("请选择需要删除的项！");
			return;
		}
		if(!confirm("删除将不可恢复，是否确认删除？")) return
		$.ajax({
			type:'post',
			url:'${contextPath}/course/deletePlan',
			data:'planId='+planIds,
			dataType:'json',
			success:function(str){
				if(str=="success"){
					alert("删除成功！");
					window.location.reload();
				}else{
					alert("删除失败！");
					window.location.reload();
				}
			}
		});
	})
</script>