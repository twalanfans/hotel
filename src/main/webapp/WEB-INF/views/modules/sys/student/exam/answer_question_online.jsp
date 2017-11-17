<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%String selectRadio[] = {"A","B","C","D","E","F","G","H","I"}; %>
<div class="testContentBox">
	<c:if test="${questionNum!='0'}">
	<dl>
		<dt>
			<c:if test="${questionDetail.type=='11'}">
				<h4 class="clearfix">单选题（共有 ${questionNum} 题，当前题号：${questionCode+1}）</h4>
			</c:if>
			<c:if test="${questionDetail.type=='12'}">
				<h4 class="clearfix">多选题（共有 ${questionNum} 题，当前题号：${questionCode+1}） </h4>
			</c:if>
			<c:if test="${questionDetail.type=='21'}">
				<h4 class="clearfix">填空题（共有 ${questionNum} 题，当前题号：${questionCode+1}）</h4>
			</c:if>
			<c:if test="${questionDetail.type=='22'}">
				<h4 class="clearfix">简答题（共有 ${questionNum} 题，当前题号：${questionCode+1}）</h4>
			</c:if>
			<c:if test="${questionDetail.type=='23'}">
				<h4 class="clearfix">案例分析（共有 ${questionNum} 题，当前题号：${questionCode+1}）</h4>
			</c:if>
			<c:if test="${questionDetail.type=='24'}">
				<h4 class="clearfix">翻译（共有 ${questionNum} 题，当前题号：${questionCode+1}）</h4>
			</c:if>
			<c:if test="${questionDetail.type=='25'}">
				<h4 class="clearfix">填空（共有 ${questionNum} 题，当前题号：${questionCode+1}）</h4>
			</c:if>
		</dt>
		<dd class="clearfix">
			<div class="questionTit">	
				<img src="${filePath}/${questionDetail.questionFilePath}">
			</div>
			<c:if test="${questionDetail.type=='11'}">
					<div class="clearfix selAnswer">
						<span class="fl">选择答案：</span>
						<ol class="AnswerContent clearfix fl" >
							<c:forEach items="<%=selectRadio%>"  begin="0" end="${questionDetail.selectNum-1}"  var ="selectRadio"  varStatus="status1">
								<li class="dxOption selectRadioBtn fl">
									<input type="radio" class="userAnswer" value="${selectRadio}" name="userAnswer">
									<label>${selectRadio}</label>
								</li>
							</c:forEach>
						</ol>
					</div>
					
			</c:if>
			<c:if test="${questionDetail.type=='12'}">
					<div class="clearfix selAnswer">
						<span class="fl">选择答案：</span>
						<ol class=" f2 AnswerContent clearfix fl" >
							<c:forEach items="<%=selectRadio%>"  begin="0" end="${questionDetail.selectNum-1}"  var ="selectRadio"  varStatus="status1">
								<li class="dxOption selectRadioBtn fl" style="margin-right:25px;">
									<input type="checkbox" class="userAnswer"  value="${selectRadio}" name="userAnswer">
									<label>${selectRadio}</label>
								</li>
							</c:forEach>
						</ol>
					</div>	
			</c:if>
			<c:if test="${questionDetail.type!='11' && questionDetail.type!='12'}">
					<div class="questionTit">填写答案：</div>
					<ol>
						<li class="dxOption">
							<textarea class="textAnswer" id="userAnswer" ></textarea>
						</li>
					</ol>
			</c:if>
			<div class="standarAnswer" style="display:none">
				<p class="standarAnswerContent"><span>正确答案:</span>${questionDetail.answerContent}</p>
			</div>
			<div class="controlBox">
				<input type="text"	id="standarAnswer"  value="${questionDetail.answerContent}" style="display:none">
				<input type="button" value="查看答案" onclick="lookAnswer()" class="commonBtn"/>
				<input type="button" value="下一题" onclick="nextQuestion()" class="commonBtn"/>
				<c:if test="${isAddError>0}">
					<input type="button" id="" value="已加入错题" class="changeBtn status_readOnly" disabled="disabled"/>
				</c:if>
				<c:if test="${isAddError==0}">
					<input type="button" id="addError" value="加入错题集"  class="commonBtn mr0" onclick="addErrorNotes('${questionDetail.testId}')" />
				</c:if>
			</div>
		</dd>
	</dl>
	</c:if>
	<c:if test="${questionNum=='0'}">
	<div style="text-align:center; font-size:28px; color:red">试题库存紧张，正在更新中。。。敬请期待！</div>
	</c:if>
</div>
<script type="text/javascript">
	function nextQuestion(){
		$('#testOnlineFrame').load("${contextPath}/testonline/testQuestionOnline?knowledgeId=${knowledgeId}"+"&questionClass=${questionClass}");
	}
	
	function lookAnswer(){
		var userAnswer ="";
		if("${questionClass}"=="1"){
			userAnswer = $(".userAnswer:checked").val();
		}else{
			userAnswer = $("#userAnswer").val();
		}
		if(userAnswer=="" || userAnswer==null){
			if(confirm("您还未作答，确定现在就查看答案吗？")){
				$('.standarAnswer').show();
			}
		}else{
			$('.standarAnswer').show();
		}
	}
	
	function addErrorNotes(questionId){
		var userAnswer="";
	    if("${questionClass}"=="1"){
			var chooseAnswer =[];
		    $(".userAnswer").each(function(i){
				if(this.checked){
					chooseAnswer.push(this.value);
				}
			});
	   	 	userAnswer = chooseAnswer.join("、");
	    }else{
	    	userAnswer = $("#userAnswer").val();
	    }
		$.ajax({
			type:"post",
			url:"${contextPath}/testonline/saveAnswerEveryQuestionDetail",
			data:"questionId="+questionId+"&userAnswer="+userAnswer+"&standarAnswer="+$("#standarAnswer").val()+"&isAdd=1",
			dataType:"json",
			success:function(str){
				if(str=="success"){
					$("#addError").val("已加入错题").css('background-color','#ccc');
					$("#addError").attr('disabled','disabled');
				}else{
					alert("添加失败！");
				}
			}
		});
	}
</script>