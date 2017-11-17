<!-- teacher_paper_detail_query.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
  <head>
	<meta charset="UTF-8">
	<title>试卷详情</title>
	<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">	
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/testpaper.css" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css" />
  	<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
  	<style>
  		.questScore{
  			width:100px; height:25px; border:1px; background-color:#a2c5ff;
  		}
  		.testPaperList{border:1px solid #ccc;margin-bottom:5px;padding:10px;}
  		.testPaperList:hover{box-shadow:3px 3px 3px #ccc;}
  		.fa{font-size:20px;}
  		.paperName{margin-bottom:10px;}
  	</style>
  </head>
  <body>
	<div class="testPaper">
		<div style="text-align:center;line-height:20px;">
			<c:forEach items="${testPaper}"  begin="0" end="0"  var ="paperDetail">
				<p><h2 class="paperName">${paperDetail.paperName}</h2></p>
				<p>建议时长：${paperDetail.testTimeLong}&nbsp;分钟</p>
				<p>组卷人：${paperDetail.createUser}</p>
			</c:forEach>
			<p>
				<a href="javascript:;" onclick="sendToUser();" class="commonBtn">试卷下发</a>
			</p>
		</div>
		<div style="padding:0 10px;">
			<c:forEach items="${testPaper}"  var ="questionList"  varStatus="status">
			<div class="testPaperList">
				<c:if test="${questionList.questionType=='11'}">
					<p><b>第${status.count}题：单选题</b></p>
				</c:if>
				<c:if test="${questionList.questionType=='12'}">
					<p><b>第${status.count}题：多选题</b></p>
				</c:if>
				<c:if test="${questionList.questionType=='21'}">
					<p><b>第${status.count}题：名词解释</b></p>
				</c:if>
				<c:if test="${questionList.questionType=='22'}">
					<p><b>第${status.count}题：简答题</b></p>
				</c:if>
				<c:if test="${questionList.questionType=='23'}">
					<p><b>第${status.count}题：案例分析</b></p>
				</c:if>
				<c:if test="${questionList.questionType=='24'}">
					<p><b>第${status.count}题：翻译</b></p>
				</c:if>
				<c:if test="${questionList.questionType=='25'}">
					<p><b>第${status.count}题：填空</b></p>
				</c:if>
					<p><img src="${filePath}/${questionList.questionFilePath}"></p>
					<h4>标准答案：</h4>
					<p class="questionAnswer">${questionList.standarAnswer}</p>
					<h4>试题分数：</h4>
					<p>
						<span class="defaultScore"><b class="scoreValue">${questionList.questionScore}</b>&nbsp;&nbsp;<a href="javascript:;" class="editBtn fa fa-edit" title="重置分数" ></a></span>
						<span class="editScore" style="display:none">
							<input type="text" value="${questionList.questionScore}" class="questScore" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="2" />&nbsp;&nbsp;
							<a href="javascript:;" class="confirmBtn fa fa-check" paperId="${questionList.paperId}" questionId="${questionList.questionId}"></a>
						</span>
					</p>
				</div>
			</c:forEach>
		</div>
	</div>
	</body>
</html>
<script type="text/javascript" src="${ctxStatic}/js/strStandard.js"></script>
<script type="text/javascript">	
	//试卷下发给学生
	function sendToUser(){
		var url = "${contextPath}/testonline/querySendPaperUser?paperId=${paperId}";
		var tool ="height=800,width=850,top=40,left=500,menubar=yes, alwaysRaised=yes,scrollbars=yes";
		window.open(url,'试卷下发学生',tool);
	}
	
	//试题答案格式标准化
	(function(){
		var questionAnswer = document.getElementsByClassName('questionAnswer');
		var questionAnswerLen = questionAnswer.length;
		for(var i=0; i<questionAnswerLen; i++){
			questionAnswer[i].innerHTML = strStandard.init(questionAnswer[i].innerHTML); 
		}
	})();
	
	//编辑
	$(".editBtn").click(function(){
		$(this).parent(".defaultScore").siblings(".editScore").show();
		$(this).parent(".defaultScore").hide();
	})
	
	//确认设置分数
	$(".confirmBtn").click(function(){
		var _this = $(this);
		var score = _this.siblings(".questScore").val();
		var paperId = _this.attr("paperId");
		var questionId = _this.attr("questionId");
		if(score>0){
			$.ajax({
				type:'post',
				url:'${ctx}/testonline/editQuestionScore',
				data:"score="+score+"&paperId="+paperId+"&questionId="+questionId, 
				dataType:'json',
				success:function(data){
					if(data=="success"){
						_this.parent().siblings(".defaultScore").find(".scoreValue").html(score);
						_this.parent(".editScore").hide();
						_this.parent().siblings(".defaultScore").show();
					}else{
						alert('分数编辑失败！');
						_this.parent(".editScore").hide();
						_this.parent().siblings(".defaultScore").show();
					}
				}
			});
		}
	})
</script>