<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/calendar.css" />
<%-- <!doctype html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<title>激活码管理详情页</title>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
	    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/teacher/sendTestPaper.css" />
	    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css" /> --%>
		<style type="text/css">
			.showIframeBox{position:fixed;left:0;top:0;display:none;z-index:99;width:100%;height:100%;}
			.shadowBox{width:100%;height:100%;background-color:rgba(0,0,0,0.6);position:absolute;left:0;top:0;}
			#codeFrame{position:relative;top:100px;}
		 	.welcome{float: right;margin-right: 55px; margin-top: 10px;}
			.userName{display:inline-block;padding:6px 14px;background-color:#fff;color:black;font-size:17px;}
			.logoutBtn{display:inline-block;padding:6px 14px;cursor:pointer;background-color:#f43d36;color:#fff;font-size:17px;}
		</style>
	<!-- </head>
	<body> -->
	<div class="welcome">
		<span class="userName">你好，${userDetail.userName}</span>&nbsp;&nbsp;
		<span onclick="logOut()" class="logoutBtn">退出</span>
	</div></br>
	<div class="navExplain" style="text-align:center;margin-top:35px;height:40px">
	<h1>${userDetail.schoolName}激活码管理</h1>
	</div>
		<div class="testPaper">
			<!--搜索-->
			<div class="searchPanel">
				<form action="${ctx}/code/queryCodeListBySchoolPage" id="codeForm" method="post">
					<span>
						<label>激活码：</label>
						<input type="text" class="inpt" name="keyId" value="${keyId}"/>
					</span>
					<span>
						<label>使用量：</label>
						<select id="useNum" name="useNum" class="inpt">
							<option value="">—请选择—</option>
							<option value="0">0</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
						</select>
					</span>
					<input	type="submit" value="搜索" class="commonBtn" />
				</form>
				<div style="margin:16px">
					<span>
		    			<input value="打印" type="button" class="commonBtn" onclick="printPaper()" />
					</span>
				</div>
			</div>
			<!--组卷记录-->
			<div class="allTable">
				<table cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<td>激活码</td>
							<td>剩余使用量</td>
							<td>所属学校</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageInfo.list}"  var ="codeList">
							<tr>
								<td>${codeList.keyId}</td>
								<td>${codeList.useNum}</td>
								<td>${codeList.schoolName}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!--翻页-->
		<div class="pageList">
			<span>共<b>${pageInfo.total}</b>条</span>
			<span>每页<b>${pageInfo.pageSize}</b>条</span>
			<span>当前第<b>${pageInfo.pageNum}</b>页</span>
			<span>
				<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}"><span>首页</span><span>上一页</span></c:if>
		          	<c:if test="${pageInfo.pageNum != 1 && pageInfo.pages > 1}">
					<a href="javascript:;" onclick="pageTurn('1')">首页</a>
					<a href="javascript:;" onclick="pageTurn(${pageInfo.pageNum-1})">上一页</a>
				</c:if>
				<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span>下一页</span><span>尾页</span></c:if>
		          	<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
					<a href="javascript:;" onclick="pageTurn(${pageInfo.pageNum+1})">下一页</a>
					<a href="javascript:;" onclick="pageTurn(${pageInfo.pages})">尾页</a>
				</c:if>
			</span>
		</div>
		</div>			
	
	<div class="showIframeBox">
		<div class="shadowBox"></div>
		<div class="closeBox">激活码打印页<span class="fa fa-times-circle-o quxiao"></span></div>
		<iframe  src=""  id="codeFrame" frameborder="0"  width="100%" height="500px"></iframe>	
	</div>
<!-- </body>
</html> -->
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript">
$("#useNum option[value='${useNum}']").attr("selected","selected");

function printPaper(){
	if("${pageInfo.total}"<10){
		alert("当前激活码数未超过10条不可打印！");
		return
	}
	document.getElementById('codeFrame').src="${ctx}/code/printKeyCode";
	$('.showIframeBox').fadeIn();
}
	
function pageTurn(pageNum){
	document.getElementById("codeForm").action="${ctx}/code/queryCodeListBySchoolPage?page="+pageNum;
	document.getElementById("codeForm").submit();
}

function logOut(){
	if(confirm("您确认退出吗?")){
	    window.location.href = "${contextPath}/logout";
	}  
}
</script>