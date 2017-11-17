<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
	.reciveMessage{margin:10px;}
</style>
<div class="ml10 messagesearchbox searchCondition clearfix">
	<!-- 栏目说明 -->
	<div class="navExplain">
		<span>收件箱</span>
	</div>
	<!-- 搜索条件 -->
	<div class="searchBeaf">
		<span class="mb8 mr5">
			<label for='searchFromUser'>发件人：</label>
			<input type="text" value="${fromUser}"  id="searchFromUser" class="inpt" />
		</span>
		<span class="mb8 mr5">
			<label for='searchSubject'>&nbsp;&nbsp;&nbsp;标题：</label>
			<input type="text" value="${subject}"  id="searchSubject" class="inpt" />  
		</span>
		<span class="mb8 mr5">
			<label for="searchIsRead">&nbsp;&nbsp;&nbsp;状态：</label>
			<select  id="searchIsRead" class="searchIsRead inpt">
				<option value="">全部</option>
				<option value="0">未读</option>
				<option value="1">已读</option>
			</select>
		</span>
		<span class="mb8 mr5">
			<a ui-sref="receiveMessage({pages : 1})" ui-sref-opts="{reload:true}" class="defaultBtn">搜索</a>
		</span>
		<span class="mb8">
			<a href="javascript:;" id="delMessage" class="defaultBtn">删除选中项</a>
		</span>
	</div>
	<!-- 搜索结果 -->
	<div class="allTable">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>
						<input type="checkbox" id="selallbtn1" />
						<label for="selallbtn1">全选</label>
					</td>
					<td>标题</td>
					<td>发件人</td>
					<td>状态</td>
					<td>发送日期</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${messageList.dataRows}"  var="messageList" >
				<tr>
					<td><input type="checkbox" class="selrolebtn1" name="messageId"  value="${messageList.messageId}"></td>
					<td><a href="javascript:;" onclick="msgDetail('${messageList.messageId}');" title="查看消息详情">${messageList.subject}</a></td>
					<td>${messageList.fromName}</td>
					<c:if test="${messageList.isRead=='0' }"><td class="${messageList.messageId}">未读</td></c:if>
					<c:if test="${messageList.isRead=='1' }"><td>已读</td></c:if>
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
$("#searchIsRead option[value='${status}']").attr("selected","selected");
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
		$('.promptTxt p:eq(1) span').html('审核之后状态将不可更改！');
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
	
	//查看详情，弹出子窗口
	function msgDetail(messageId){
		$("."+messageId).html("已读");
		var url = "${contextPath}/message/queryMessageDetail?messageId="+messageId;
		var tool ='height=500,width=800,top=150,left=600,menubar=yes, alwaysRaised=yes'
		window.open(url,'短消息详细信息',tool);
	}
	
	//关闭弹窗
	$('#cancelBtn').click(function(){
		$('.promptBox').fadeOut();
	});
	
	//全选或取消
	$('#selallbtn1').on('click',function(){
		if($('#selallbtn1').prop('checked')){
			$('.selrolebtn1').prop('checked',true);
		}else{
			$('.selrolebtn1').prop('checked',false);
		}
	});
</script>