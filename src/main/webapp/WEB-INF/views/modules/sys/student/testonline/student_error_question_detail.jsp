<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<title>错题详情</title>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
		<style type="text/css">
			.errorQuestion{padding:10px;}
			.errorQuestion p{margin-bottom:15px;font-size:14px;overflow:auto;}
			.errorQuestion p span{display:block;margin-bottom:10px;}
			.errorQuestion p span:nth-child(1){font-weight:bold;}
		</style>
	</head>
	<body>
		<div class="errorQuestion">
			<p>
				<span>试题详情：</span>
				<img src="${filePath}/${questionDetail.questionFilePath}" />
			</p>
			<p>
				<span>个人答案：</span>
				<span>${questionDetail.userAnswer}</span>
			</p>
			<p>
				<span>标准答案：</span>
				<span>${questionDetail.standarAnswer}</span>
			</p>
			<c:if test="${questionDetail.discription!=null && questionDetail.discription!=''}">
				<p>
					<span>答案备注:</span>
					<span>${questionDetail.discription}</span>
				</p>
			</c:if>
		</div>
		<a href="javascript:;" id="close" class="commonBtn close">关闭</a>
	</body>
</html>
<script type="text/javascript" src="${ctxStatic}/js/sonWindow.js"></script>