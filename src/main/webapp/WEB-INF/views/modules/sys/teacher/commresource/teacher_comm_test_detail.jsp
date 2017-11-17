<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page import= "com.module.testonline.entity.TestQuestion"%>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>试题详情</title>
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
		<style type="text/css">
			body{font-size:14px;height:500px;}
			.commonCourseContent{padding:15px;}
			.commonCourseContent .testTitle{font-weight:bold;margin-bottom:10px;}
			.commonCourseContent .testContent .testShow{margin-bottom:10px;overflow:auto;}
			.commonCourseContent .testContent .testShow .spanBlock{display:block;margin-bottom:5px;}
			.commonCourseContent .testContent .testShow input{width:200px;}
			textarea{width:100%;min-height:10px;}
		</style>
	</head>
	<body>
		<div class="commonCourseContent">
			<div class="testContent">
				<!--所属课程-->
				<div class="testShow">
					<span>
						<b>所属课程：</b>
					</span>
					<span>${tqnew.courseName}</span>
				</div>
				<!--试题名称-->
				<div class="testShow">
					<span>
						<b>试题名称：</b>
					</span>
					<span>${tqnew.questionTitle}</span>
				</div>
				<!--试题类别-->
				<div class="testShow">
					<span>
						<b>试题类别：</b>
					</span>
					<span>${tqnew.type}</span>
				</div>
				<!--试题内容-->
				<div class="testShow">
					<span class="spanBlock">
						<b>试题内容：</b>
					</span>
					<img src="${filePath}/${tqnew.questionFilePath}" />
				</div>
				<!--试题答案-->
				<div class="testShow">
					<span class="spanBlock">
						<b>试题答案：</b>
					</span>
					<p>${tqnew.answerContent}</p>
				</div>
				<!--试题答案附加信息 -->
				<%
					TestQuestion tq= (TestQuestion)request.getAttribute("tqnew");
					String answerRemark=tq.getAnswerRemark();
					if(!(answerRemark==null||"".equals(answerRemark) || "null".equals(answerRemark)||answerRemark.isEmpty()))
							{
						
				%>
				<div class="testShow">
					<span class="spanBlock">
						<b>试题答案附加信息：</b>
					</span>
					<textarea readonly>${tqnew.answerRemark}</textarea>
				</div>
				<% }%>
				<a href="javascript:;" id="close" class="commonBtn close">关闭</a>
			<div>
		</div>
	</body>
</html>
<script type="text/javascript" src="${ctxStatic}/js/sonWindow.js"></script>