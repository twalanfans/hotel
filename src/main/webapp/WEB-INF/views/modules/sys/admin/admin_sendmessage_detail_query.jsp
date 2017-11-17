<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<title>详细消息</title>
		<!--css-->
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
	</head>
	<body>
		<div class="allTable">
			<table cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<td colspan="2" class="showinformationtit">详细信息</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${messageList}"  var="messageList" >
					<tr>
						<td class="bgcol3">消息主题</td>
						<td>${messageList.subject}</td>
					</tr>
					<tr>
						<td class="bgcol3">消息内容</td>
						<td id="messageContent">${messageList.allMessage}</td>
					</tr>
					<tr>
						<td class="bgcol3">发送日期</td>
						<td><fmt:formatDate value="${messageList.sendTime}" pattern="yyyy-MM-dd HH:mm"/></td>
					</tr>
					<tr>
						<td class="bgcol3">发件人</td>
						<td>${messageList.fromUser}</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<a href="javascript:;" id="close" class="commonBtn close">关闭</a>
	</body>
</html>
<script type="text/javascript" src="${ctxStatic}/js/sonWindow.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/strStandard.js"></script>
<script type="text/javascript">
	(function(){
		var msgContent = document.getElementById('messageContent');
		msgContent.innerHTML = strStandard.init(msgContent.innerHTML);
	})();
</script>