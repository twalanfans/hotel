<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="messagesearchbox mlDistance" >
	<!-- 栏目说明 -->
	<div class="navExplain">
		<span>所有评论</span>
	</div>
	<!-- 搜索条件 -->
	<div class="searchBeaf">
		<form id="Search"  method="post">
			<span class="mb8 mr5">
				<label for='createUser'>评论用户：</label>
				<input type="text" value="${createUser}" id="createUser" class="inpt" />
			</span>
			<span class="mb8 mr5">
				<label for='content'>评论内容：</label>
				<input type="text" value="${content}"  id="content" class="inpt" />
			</span>
			<span class="mb8 mr5">
				<a ui-sref="commentMana({pageNum : 1})" ui-sref-opts="{reload:true}"  class="defaultBtn">搜索</a>
			</span>
			<span class="mb8 mr5">
				<a href="javascript:;" onclick="deleteComment();" class="defaultBtn">删除</a>
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
					<td>评论用户</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${pageInfo.list}"  var="commentList" >
				<tr>
					<td><input type="checkbox" class="selrolebtn1" name="commentId" value="${commentList.commentId}"></td>
					<td>${fn:substring(commentList.content,0,40)}...</td>
					<td>${commentList.createUser}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<!-- 翻页 -->
<div class="pageList">
	<span class="fl">共<b>${pageInfo.total}</b>条</span>
	<span class="fl">每页<b>${pageInfo.pageSize}</b>条</span>
	<span class="fl">当前第<b>${pageInfo.pageNum}</b>页</span>
	<span class="clearfix">
		<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}"><span class="fl">首页</span><span class="fl">上一页</span></c:if>
          	<c:if test="${pageInfo.pageNum != 1 && pageInfo.pages > 1}">
			<a href="javascript:;" ng-click="shouye(1);" class="fl">首页</a>
			<a href="javascript:;" ng-click="prevPage(${pageInfo.pageNum-1})" class="fl">上一页</a>
		</c:if>
		<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span class="fl">下一页</span><span class="fl">尾页</span></c:if>
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
	//删除
	var commentId = [];
	function deleteComment(){
			$('input[name="commentId"]:checked').each(function(){ 
				commentId.push($(this).val());
			});  
			if(commentId.length<1){
				alert("请选择需要删除的项！");
				return;
			}
			$('.promptBox').fadeIn();
	}

	//确认删除
	$('#confirmBtn').on('click',function(){
		$.ajax({
			type:'post',
			url:'${contextPath}/question/deleteCommentOnline',
			data:'commentId='+commentId,
			dataType:'json',
			success:function(str){
				if(str=="success"){
					$('.promptTxt p:eq(1) span').html('删除成功！');
					$('.promptBox').fadeOut();
					window.location.reload();
				}else{
					$('.promptTxt p:eq(1) span').html('删除失败，请重新操作！');
					$('.promptBox').fadeOut();
				}
			}
		});
	});

	//取消删除用户
	$('#cancelBtn').on('click', function(){
		$('.promptBox').fadeOut();
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