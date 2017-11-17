<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
		<title>我参与过的问题</title>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/common.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/question.css" />
	</head>
	<body>
		<div class="news">
			<!--栏目提示-->
			<form action="${ctx}/question/myAnswerOnline" id="questionForm" method="post" class="searchPanel">
					<label>问题标题：</label>
					<input type="text" name="title" value="${title}" class="inpt"/>
					<input type="submit" value="搜索" class="commonBtn">
			</form>
			<div class="contentHead clearfix">
				<div class="pageHeading">
					<h3>我参与过的问题</h3>
				</div>
			</div>
			<!--我回答过的问题-->
			<div class="showList">
				<ul>
				  <c:if test="${questionNum!='0'}">
					<c:forEach items="${questionList}"  begin="0" end="${showNum}" var="questionList"  varStatus="status">
						<li class="clearfix">
						<p class="fl">
							<a href="${contextPath}/question/questionContent?questionId=${questionList.questionId}+&clickNum=${questionList.clickNum}">${questionList.title}</a>
						</p>
						<div class="clearfix fr publishInfo">
							<h4 class="fl mr20">${questionList.content}</h4>
							<div class="fl clearfix">
								<p class="fl mr20">评论时间：<fmt:formatDate value="${questionList.createTime}" pattern="yyyy-MM-dd HH:mm"/></p>
								<p class="fl"><i  class="fa fa-thumbs-o-up mr20"></i><span>${questionList.thinkGood}</span></p>	
							</div>
						</div>
						</li>
					</c:forEach>
				  </c:if>
				  <c:if test="${questionNum=='0'}">
				  	<li><p>您还没有参与过任何问题回答！</p></li>
				  </c:if>
				</ul>
			</div>
			<!--翻页-->
			<div class="pageList">
				<p class="pageListBar">共<b>${questionNum}</b>条</p>
				<p class="addMoreBtnBox"><a href="javascript:;" onclick="loadMore()" class="addMoreBtn">再加载15条</a></p>
			</div>
		</div>
	</body>
<script type="text/javascript">
	function loadMore(){
		document.getElementById("questionForm").action="${ctx}/question/myAnswerOnline?showNum=${showNum}";
		document.getElementById("questionForm").submit();
	}
</script>
</html>