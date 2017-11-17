<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/testpaper.css" />
<!-- 栏目说明 -->
<div class="navExplain">
	<span>考证自测</span>
</div>
<div class="testBox">
	<div class="selTestBox">
		<div>
			<h3 class="makePaperChooseKnowledge">第一步:选择知识点</h3>
			<ul>
				<select id="knowledgeId" class="inpt">
					<option value="">—请选择—</option>
				<c:forEach items="${knowledgeIdList}"  var ="knowledge">
					<option value="${knowledge.knowledgeId}">${knowledge.knowledgeTitle}</option>
				</c:forEach>
				</select>
			</ul>
		</div>
		<div>
			<h3 class="makePaperChooseKnowledge">第二步:选择题类型</h3>
			<select class="selTestType inpt">
				<option value="1">客观题</option>
				<option value="2">主观题</option>
			</select>
		</div>
		<a href="javascript:;" class="commonBtn startTestBtn" onclick="testStart();">测试开始</a>
	</div>
	<div class="showIframeBox" id="showIframeBox">
		<div class="iframeShadow"></div>
		<div class="iframeBox">
			<!-- <h5><span class="testTitle">随机答题测试</span><a href="javascript:;" class="fa fa-times-circle-o  closeBtn"></a></h5> -->
			<div id="testOnlineFrame"></div>
		</div>
	</div>
</div>
<script type="text/javascript">
	var questionClass="";
	$('#knowledgeId').on('change',function(){
		if($(this).val()!=""){
			$('.selTestType').on('change',function(){
				var _this=this;
				if($(this).val()==1){
					chooseClass(1,_this);
				}
				else if($(this).val()==2){
					chooseClass(2,_this);
				}
			});
		}else{
			$('#testOnlineFrame').empty();
		}
	});
	function chooseClass(n,obj){
		questionClass = n;
	}
	//开始组卷
	function testStart(){
		var knowledgeId = $('#knowledgeId').val();
		/* document.getElementById("testOnlineFrame").src="${contextPath}/testonline/testQuestionOnline?knowledgeId="+knowledgeId+"&questionClass="+questionClass; */
		$('#testOnlineFrame').load("${contextPath}/testonline/testQuestionOnline?knowledgeId="+knowledgeId+"&questionClass="+questionClass);
	}
</script>