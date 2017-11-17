<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
	.sendMessage{margin:0px 10px;}
</style>
<div class="messagesearchbox" >
	<!-- 栏目说明 -->
	<div class="navExplain">
		<span>发件箱</span>
	</div>
	<!-- 搜索条件 -->
	<div class="searchBeaf">
		<form action="" id="Search" method="post">
			<span class="mb8 mr5">
				<label for='searchToUser'>收件人：</label>
				<input type="text" value="${toUser}" id="searchToUser" class="inpt" />
			</span>
			<span class="mb8 mr5">
				<label for='searchSubject'>标&nbsp;&nbsp;&nbsp;&nbsp;题：</label>
				<input type="text" value="${subject}"  id="searchSubject" class="inpt" />
			</span>
			<span class="mb8 mr5">
				<a ui-sref="sendMessage({pages : 1})" ui-sref-opts="{reload:true}" class="defaultBtn">搜索</a>
			</span>
			<span class="mb8 mr5">
				<a href="javascript:;" id="sentmessagebtn" class="defaultBtn">发送新消息</a>
			</span>
			<span class="mb8 mr5">
				<a href="javascript:;" id="delMessage" class="defaultBtn">删除选中项</a>
			</span>
		</form>
	</div>
	<!-- 搜索结果 -->
	<div class="allTable">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>
						<label for="selallbtn1">全选&nbsp;&nbsp;<input type="checkbox" id="selallbtn1" style="display:none;"/></label>
					</td>
					<td>标题</td>
					<td>收件人</td>
					<td>发送日期</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${messageList.dataRows}"  var="messageList">
				<tr>
					<td>
						<input type="checkbox" class="selrolebtn1" name="messageId" value="${messageList.messageId}" />
					</td>
					<td>
						<a href="javascript:;" onclick="msgDetail('${messageList.messageId}');" title="查看消息详情">${messageList.subject}</a>
					</td>
					<td>${messageList.toUser}</td>
					<td><fmt:formatDate value="${messageList.sendTime}" pattern="yyyy-MM-dd HH:mm"/></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<!-- 翻页 -->
<div class="pageList">
	<span>共<b>${messageList.records}</b>条</span>
	<span>每页<b>${messageList.rows}</b>条</span>
	<span>当前第<b>${messageList.page}</b>页</span>
	<span>
		<c:if test="${messageList.page == 1 || messageList.total == 0}"><span>首页</span><span>上一页</span></c:if>
		<c:if test="${messageList.page != 1 && messageList.total > 1}">
			<a href="javascript:;" ng-click="shouye(1);">首页</a>
			<a href="javascript:;" ng-click="prevPage(${messageList.page-1})">上一页</a>
		</c:if>
		<c:if test="${messageList.page == messageList.total || messageList.total == 0}"><span>下一页</span><span>尾页</span></c:if>
		<c:if test="${messageList.page != messageList.total && messageList.total != 0}">
			<a href="javascript:;" ng-click="nextPage(${messageList.page+1})">下一页</a>
			<a href="javascript:;" ng-click="endPage(${messageList.total})">尾页</a>
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
	//被选中的messageId数组，删除选中项
	var messageId = [];
	$('#delMessage').click(function(){
		$('input[name="messageId"]:checked').each(function(){ 
			messageId.push($(this).val());
		});  
		if(messageId.length<1){
			alert("请选择需要删除的项！");
			return;
		}
		$('.promptTxt p:eq(1) span').html('删除之后不可恢复！');
		$('.promptBox').fadeIn();
	});
	
	//确认删除
	$('#confirmBtn').on('click',function(){
		var $prompt = $('.promptTxt p:eq(1) span'),
			$promptBox = $('#promptBox');
		$.ajax({
			type:'post',
			url:'${contextPath}/message/deleteMessage',
			data:'messageId='+messageId,
			dataType:'json',
			success:function(str){
				if(str=="success"){
					$prompt.html('操作成功！');
					$promptBox.fadeOut();
					window.location.reload();
				}else{
					$prompt.html('操作失败！');
					$promptBox.fadeOut();
				}
			}
		});
	});
	
	//关闭弹窗
	$('#cancelBtn').click(function(){
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
	
	//查看短消息详情
	function msgDetail(messageId){
		var url = "${contextPath}/message/queryMessageDetail?messageId="+messageId;
		var tool ='height=500,width=1000,top=150,left=600,menubar=yes, alwaysRaised=yes'
		window.open(url,'短消息详细信息',tool);
	}
	
	//点击发送消息
	$('#sentmessagebtn').on('click',function(){
		var url = "${ctx}/message/sendMessagePage";
		var tool ='height=700,width=1300,top=150,left=200,menubar=yes, alwaysRaised=yes'
		window.open(url,'发送短信息',tool);
	});
</script>