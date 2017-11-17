<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<title>公告详情</title>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
	</head>
	<body>
	<div class="allTable">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td colspan="2">详细信息</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="bgcol3">公告主题</td>
					<td>${notice.title}</td>
				</tr>
				<tr>
					<td class="bgcol3">公告内容</td>
					<td>${notice.notices}</td>
				</tr>
				<tr>
					<td class="bgcol3">发件人</td>
					<td>${notice.pubUser}</td>
				</tr>
				<tr>
					<td class="bgcol3">发布时间</td>
					<td><fmt:formatDate value="${notice.pubTime}" pattern="yyyy-MM-dd HH:mm"/></td>
				</tr>
				<c:if test="${notice.filePath!=''}">
				<tr>
					<td class="bgcol3">公告附件</td>
					<td><a href="javaScript:;"  onclick="downLoad(${notice.noticeId});" style="color:blue;">${notice.fileName}</a></td>
				</tr>
				</c:if>
			</tbody>
		</table>
	</div>
	<a href="javascript:;" id="close" class="commonBtn close">关闭</a>
	<iframe id="ifile" style="display:none"></iframe>
</body>
</html>
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/sonWindow.js"></script>
<script type="text/javascript">
	//关闭窗口
	$('.closeWindow').click(function(){
		window.close();
	});
	//下载附件
	function downLoad(noticeId){  
		document.getElementById("ifile").src = "${contextPath}/notice/downLoadFile?noticeId="+noticeId; 
	}
</script>
