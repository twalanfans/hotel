<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">	
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/testpaper.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css">
		<style type="text/css">
			.Aright{font-size:25px; color:green;}
			.Aerror{font-size:25px; color:red;}
			.gradeScore{width: 100px;height: 24px;background-color: #a9e2e2;border-radius: 6px;}
			.testPaper ul li div{padding:10px;}
			.allTable ul li P{width:100%;overflow:auto;}
			.fa{font-size:20px;}
		</style>
	</head>
	<body>
		<div class="testPaper">
			<div class="allTable">
				<ul>
					<c:forEach items="${result}"  var ="result">
						<li class="clearfix">
							<div>
								<h4>试题详情：<span>(${result.questionScore}分)</span></h4>
								<p><img src="${filePath}/${result.questionFilePath}"></p>
							</div>
							<div>
								<h4>标准答案：</h4>
								<p class="textContent">${result.standarAnswer}</p>
								<c:if test="${result.discription!='' && result.discription!=null}">
									<p>备注：${result.discription}</p>
								</c:if>
							</div>
							<div>
								<h4>用户作答：</h4>
								<p class="textContent">${result.userAnswer}</p>
							</div>	
							<div>
								<c:if test="${paperType=='2'}">
									<c:if test="${type!='te1'}">		<!-- 用户不为出题人 -->
										<span><b>加入错题集：</b></span>
									</c:if>
								</c:if>
								<c:if test="${paperType=='2'}">		
									<c:if test="${type!='te1'}">		
										<c:if test="${result.isAddNotes=='1'}">
											<span style="color:#f00;">已添加</span>
										</c:if>
										<c:if test="${result.isAddNotes=='0'}">
											<span><a href="javaScript:;" class="fa fa-plus-square" onclick="addErrorNotes(${result.noteId});"></a></span>
										</c:if>
									</c:if>
								</c:if>
							</div>	
							<c:if test="${status=='2'}">
								<div>
									<span><b>判题：</b></span>
									<c:if test="${result.questionType=='11' || result.questionType=='12'}">
										<span class="getScore">
											<a href="javascript:;" class="Aright fa fa-check" onclick="makeGrade(${result.questionScore},this)"></a>
											<a href="javascript:;" class="Aerror fa fa-times" onclick="makeGrade('0',this)"></a>
										</span>
									</c:if>
									<c:if test="${result.questionType!='11' && result.questionType!='12'}">
										<span class="getScore">
											<a href="javascript:;" class="Aright fa fa-check" onclick="makeGrade(${result.questionScore},this)"></a><a href="javascript:;" class="Aerror fa fa-times" onclick="makeGrade('0',this)"></a>
											<input	type="button" value="给予具体分数" class="detailScore commonBtn" onclick="detailScore(this)" />
										</span>
									</c:if>
								</div>
							</c:if>
						</li>
					</c:forEach>
				</ul>
				<c:if test="${status=='2'}">
				<a href="javascript:;" onclick="sumGrade();" class="commonBtn">确认计算成绩</a>
				</c:if>
				<c:if test="${status=='3'}">
				<div class="paperTotalScore" style="text-align:center;font-size: 34px;color:red">本套试卷总得分：${paperScore}</div>
				</c:if>
				<a href="javascript:;" id="close" class="commonBtn">关闭</a>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/strStandard.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/sonWindow.js"></script>
<script type="text/javascript">
	//文本内容格式标准化
	(function(){
		var textContent = document.getElementsByClassName('textContent');
		var textContentLen = textContent.length;
		for(var i=0; i<textContentLen; i++){
			textContent[i].innerHTML = strStandard.init(textContent[i].innerHTML);
		}
	})();
	
	//加入错题集
	function addErrorNotes(noteId){
		$.ajax({
			type:"post",
			url:"${contextPath}/student/updateErrorNotes",
			data:"noteId="+noteId+"&isAdd=1",
			dataType:"json",
			success:function(str){
				if(str=="success"){
					window.location.reload();
				}else{
					alert("添加失败！");
				}
			}
		});
	}	
	
	function detailScore(thisClass){
		$(thisClass).parent().html("<input	type=\"text\" placeholder=\"1~2位数字\" class=\"gradeScore\" maxlength=\"2\" />");
	}
	
	var scoreTotal=[];
	function makeGrade(score,thisClass){
		scoreTotal.push(score);
		if(score==0){
			$(thisClass).parent().html("<span class=\"Aerror fa fa-times\"></span>");
		}else{
			$(thisClass).parent().html("<span class=\"Aright fa fa-check\"></span>");
		}
	}
	
	//计算本套试卷总成绩
	function sumGrade(){
		$(".gradeScore").each(function(i){
			scoreTotal.push(this.value);
		})
		$.ajax({
			type:"post",
			url:"${contextPath}/testonline/sumGrade",
			data:"score="+scoreTotal+"&paperId=${paperId}"+"&userId=${userId}",
			dataType:"json",
			success:function(result){
				if(result!="error"){
					window.location.href="${contextPath}/testonline/queryStudentPaperAnswer?paperId=${paperId}"+"&userId=${userId}";
					window.opener.location.reload();
				}else{
					alert("计算成绩失败！");
				}
			}
		});
	}
</script>