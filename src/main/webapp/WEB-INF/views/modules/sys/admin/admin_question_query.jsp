<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="navExplain">
	<span>提问管理</span>
</div>
<div class="messagesearchbox mlDistance" >
	<!-- 搜索条件 -->
	<div class="searchBeaf">
		<form id="Search"  method="post">
			<span class="mb8 mr5">
				<label for='createUser'>提问人：</label>
				<input type="text" id="createUser" class="inpt" ng-model="createUser" />
			</span>
			<span class="mb8 mr5">
				<a ui-sref="questionMana({pageNum : 1 , createUser : this.createUser})" ui-sref-opts="{reload:true}" class="defaultBtn">搜索</a>
			</span>
			<span class="mb8 mr5">
				<a href="javascript:;" onclick="deleteQuestion();" class="defaultBtn">删除</a>
			</span>
		</form>
	</div>
	<!-- 搜索结果 -->
	<div class="allTable">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>
						<label for="selallbtn1">全选&nbsp;&nbsp;</label>
						<input type="checkbox" id="selallbtn1" style="display:none" />
					</td>
					<td>标题</td>
					<td>提问人</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${pageInfo.list}"  var="questionList" >
				<tr id="${questionList.questionId}">
					<td>
						<input type="checkbox" class="selrolebtn1" name="questionId" value="${questionList.questionId}" />
					</td>
					<td>
						<a href="javascript:;" onclick="lookQuesContent( ${questionList.questionId});" style="color:blue" title="查看问题详情">${fn:substring(questionList.title,0,10)}</a>
					</td>
					<td>${questionList.createUser}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<!-- 翻页 -->
<div class="pageList clearfix">
	<span class="fl">共<b>${pageInfo.total}</b>条</span>
	<span class="fl">每页<b>${pageInfo.pageSize}</b>条</span>
	<span class="fl">当前第<b>${pageInfo.pageNum}</b>页</span>
	<span class="fl clearfix">
		<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}"><span class="fl">首页</span><span class="fl">上一页</span></c:if>
        <c:if test="${pageInfo.pageNum != 1 && pageInfo.pages > 1}">
			<a href="javascript:;" ng-click="shouye(1);" class="fl">首页</a>
			<a href="javascript:;" ng-click="prevPage(${pageInfo.pageNum-1})" class="fl">上一页</a>
		</c:if>
		<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span  class="fl">下一页</span><span class="fl">尾页</span></c:if>
        <c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
			<a href="javascript:;" ng-click="nextPage(${pageInfo.pageNum+1})" class="fl">下一页</a>
			<a href="javascript:;" ng-click="endPage(${pageInfo.pages})" class="fl">尾页</a>
		</c:if>
	</span>
</div>
<!-- 用户操作提示框 -->
<div class="promptBox">
	<div class="promptTxt">
		<p>提示</p>
		<p>
			<i class="fa fa-exclamation-circle fl" style="margin:20px 5px 0 20px;"></i>
			<span>删除将不可恢复，是否确定删除？</span>
		</p>
	</div>
	<div class="promptBtn">
		<a href="javascript:;" id="confirmBtn" class="commonBtn">确认</a>
		<a href="javascript:;" id="cancelBtn" class="defaultBtn">取消</a>
	</div>
</div>
<script type="text/javascript">
	//查看问题评论详情
	function lookQuesContent(questionId){
		  var url = "${contextPath}/question/questionContent?questionId="+questionId+"&clickNum=";
		  var tool ="height=500,width=800,top=150,left=600,menubar=yes, alwaysRaised=yes";
		  window.open(url,'问题评论详情',tool);
	}
	
	//删除
	var questionId = [];
	function deleteQuestion(){
			$('input[name="questionId"]:checked').each(function(){ 
				questionId.push($(this).val());
			});  
			if(questionId.length<1){
				alert("请选择需要删除的项！");
				return;
			}
			$('.promptBox').fadeIn();
	}
	
	//取消删除用户
	$('#cancelBtn').on('click', function(){
		$('.promptBox').fadeOut();
	});

	$('#confirmBtn').on('click',function(){
		var $promptTxt = $('.promptTxt p:eq(1) span'),
			$promptBox = $('.promptBox');
		$.ajax({
			type:'post',
			url:'${contextPath}/question/deleteQuestionOnline',
			data:'questionId='+questionId,
			dataType:'json',
			success:function(str){
				if(str=="success"){
					$promptTxt.html('删除成功！');
					$promptBox.fadeOut();
					window.location.reload();
				}else{
					$promptTxt.html('删除失败，请重新操作！');
					$promptBox.fadeOut();
				}
			}
		});
	});

	//全选/取消
	$('#selallbtn1').on('click',function(){
		if($('#selallbtn1').prop('checked')){
			$('.selrolebtn1').prop('checked',true);
		}else{
			$('.selrolebtn1').prop('checked',false);
		}
	});
</script>