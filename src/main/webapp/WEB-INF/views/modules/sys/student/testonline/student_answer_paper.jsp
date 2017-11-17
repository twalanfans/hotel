<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%String selectRadio[] = {"A","B","C","D","E","F","G","H","I"}; %>
<style type="text/css">
	.testContentTit{text-align:center;}
	.testContentTit h2{font-size:20px;}
	.testContentTit p{font-size:12px;}
	.questionContent{margin:10px 0;padding:10px;border:1px solid #ccc;overflow-x:auto;}
	.questionContent:hover{border:1px solid #6bc5a4;}
	.dxOption{margin-right:20px;}
</style>
<div class="testContentBox">
	<c:if test="${over=='0'}">
		<div class="testContentTit">
			<h2>${questionDetail.paperName}</h2>
			<p>共&nbsp;${totalNum}&nbsp;题&nbsp;&nbsp;当前第&nbsp;${sortNum}&nbsp;题</p>
			<p><b style="color:#f00;">注意：在答题期间不可离开此页面，否则将会导致试卷无效，影响您的测试结果！</b></p>
		</div>
		<dl>
			<dt>
				<c:if test="${questionDetail.questionType=='11'}">
					<dd>题型：单选题</dd>
				</c:if>
				<c:if test="${questionDetail.questionType=='12'}">
					<dd>题型：多选题</dd>
				</c:if>
				<c:if test="${questionDetail.questionType=='21'}">
					<dd>题型：填空题</dd>
				</c:if>
				<c:if test="${questionDetail.questionType=='22'}">
					<dd>题型：简答题</dd>
				</c:if>
				<c:if test="${questionDetail.questionType=='23'}">
					<dd>题型：案例分析</dd>
				</c:if>
				<c:if test="${questionDetail.questionType=='24'}">
					<dd>题型：翻译题</dd>
				</c:if>
				<c:if test="${questionDetail.questionType=='25'}">
					<dd>题型：填空题</dd>
				</c:if>
			</dt>
			<dd>
				<div class="questionContent"><img src="${filePath}/${questionDetail.questionFilePath}"></div>
				<c:if test="${questionDetail.questionType=='11'}">
					<div class="clearfix AnswerContent">
						<span class="fl">选择答案：</span>
						<ol class="fl clearfix" >
							<c:forEach items="<%=selectRadio%>"  begin="0" end="${questionDetail.selectNum-1}"  var ="selectRadio"  varStatus="status1">
								<li class="dxOption selectRadioBtn fl">
									<input type="radio" class="userAnswer" name="userAnswer" value="${selectRadio}">
									<label>${selectRadio}</label>
								</li>
							</c:forEach>
						</ol>
					</div>
				</c:if>
				<c:if test="${questionDetail.questionType=='12'}">
					<div class="clearfix AnswerContent">
						<span class="fl">选择答案：</span>
						<ol class="fl f2 clearfix">
						<c:forEach items="<%=selectRadio%>"  begin="0" end="${questionDetail.selectNum-1}"  var ="selectRadio"  varStatus="status1">
							<li class="dxOption selectRadioBtn fl">
								<input type="checkbox" class="userAnswer" name="userAnswer" value="${selectRadio}">
								<label>${selectRadio}</label>
							</li>
						</c:forEach>
						</ol>
					</div>
				</c:if>
				<c:if test="${questionDetail.questionType!='11' && questionDetail.questionType!='12'}">
						<div class="questionTit">填写答案：</div>
						<ol>
							<li class="dxOption">
								<textarea class="textAnswer" id="userAnswer" ></textarea>
							</li>
						</ol>
				</c:if>
				<div class="standarAnswer" style="display:none">
					<textarea id="standarAnswer">${questionDetail.standarAnswer}</textarea>
				</div>
				<input type="button" value="下一题" onclick="nextQuestion('${questionDetail.paperId}','${questionDetail.questionId}')" class="commonBtn"/>
			</dd>
		</dl>
	</c:if>
	<c:if test="${over=='1'}">
		<div style="text-align:center" class="testContentTit">
			<p class="testOnlineTit">本次试题已全部作答!</p>
			<c:if test="${questionDetail.paperType=='1'}">
				<p><input type="button" value="查看结果" onclick="lookResult('${questionDetail.paperId}')" class="commonBtn"/></p>
			</c:if>
			<c:if test="${questionDetail.paperType=='2'}">
				<p><a href="javascript:;" onclick="submitPaper('${questionDetail.paperId}')" class="commonBtn">提交试卷</a></p>
			</c:if>
		</div>
	</c:if>
</div>
<script type="text/javascript" src="${ctxStatic}/js/strStandard.js"></script>
<script type="text/javascript">
	function nextQuestion(paperId,questionId){
		var userAnswer = "";
		 if("${questionDetail.questionClass}"=="1"){
			 var chooseAnswer =[];
			 $(".userAnswer").each(function(i){
					if(this.checked){
						chooseAnswer.push(this.value);
					}
			 });
		   	 userAnswer = chooseAnswer.join("、");
		 }else{
			 userAnswer =  strStandard.init($("#userAnswer").val());
		 }
		if(userAnswer=="" || userAnswer==null){
			alert("请先作答，再进行下一题！");
			return;
		}
		$.ajax({
			type:"post",
			url:"${contextPath}/testonline/saveAnswerEveryQuestionDetail",
			data:"paperId="+paperId+"&questionId="+questionId+"&userAnswer="+userAnswer+"&standarAnswer="+$("#standarAnswer").val()+"&isAdd=0",
			dataType:"json",
			success:function(str){
				if(str=="error"){
					alert("系统保存您的答题信息出错！");
				}
			}
		});
		window.localStorage.setItem('paperId',paperId);
		window.localStorage.setItem('sortNum',"${sortNum}");
		$('#testPaperFrame').load("${contextPath}/testonline/testPaper?paperId="+paperId+"&sortNum=${sortNum}");
		$('.resourceContent').fadeOut();
		$('#showIframeBox').stop(true).fadeIn();
	}
	
	function lookResult(paperId){
		var url = "${contextPath}/testonline/queryStudentPaperAnswer?paperId="+paperId+"&userId=";
		var tool ="height=800,width=850,top=40,left=500,menubar=yes, alwaysRaised=yes,scrollbars=yes";
		rebackPaperList();
		window.open(url,'试卷测试结果',tool);
	}
	
	function submitPaper(paperId){
		$.ajax({
			type:"post",
			url:"${contextPath}/testonline/submitTeacherPaper",
			data:"paperId="+paperId,
			dataType:"json",
			success:function(str){
				if(str=="success"){
					alert("试卷已提交至教师，等待打分！");
					rebackPaperList();
				}else{
					alert("试卷提交失败");
					window.location.reload();
				}
			}
		});
	}
	
	function rebackPaperList(){
		window.localStorage.removeItem('paperId');
		window.localStorage.removeItem('sortNum');
		$('#main').load("${ctx}/student/studentTestPaperPage?time"+new Date().getTime(),function(){
			window.location.reload();
		});
	}
</script>