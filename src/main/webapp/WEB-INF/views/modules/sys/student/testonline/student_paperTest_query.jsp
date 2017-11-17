<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/testpaper.css" />
<div class="navExplain">
	<span>我的试卷</span>
</div>
<!-- 组卷记录 -->
<div class="resourceContent">
	<div class="searchPanel searchBeaf">
		<span>
			<label>试卷名称</label>
			<input type="text" class="inpt" id="paperName" ng-model="paperName" />
		</span>
		<span>
			<label>状态</label>
			<select id="paperStatus" class="sel inpt" ng-model="status">
				<option value="">---请选择试卷状态---</option>
				<option value="0">待答题</option>
				<option value="2">待打分</option>
				<option value="3">已完成</option>
			</select>
		</span>
		<span>
			<a ui-sref="studentPaperTest({pageNum : 1, paperName : paperName, status : status})"" ui-sref-opts="{reload:true}" class="defaultBtn">搜索</a>
		</span>
		<span>
			<a href="javascript:;" class="defaultBtn" id="removeCheckPaper">删除</a>
		</span>
	</div>
	<div class="allTable">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>
						<label for="allCheck">全选</label>
						<input type="checkbox" id="allCheck" />
					</td>
					<td>试卷名称</td>
					<td>创建人</td>
					<td>日期</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pageInfo.list}"  var ="paperList">
					<tr>
						<td><input type="checkbox" class="checkPaper" paperId="${paperList.paperId}" /></td>
						<td>${paperList.paperName}</td>
						<td>${paperList.createUser}</td>
						<td><fmt:formatDate value="${paperList.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
						<c:if test="${paperList.status=='0'}"><td><a href="javascript:;"  onclick="testPaper(${paperList.paperId})">开始答题</a></td></c:if>
						<c:if test="${paperList.status=='1'}"><td class="testing">答题中</td></c:if>
						<c:if test="${paperList.status=='2'}">
							<c:if test="${paperList.teacherId==currentUser}">
								<td><a href="javascript:;" onclick="lookResult('${paperList.paperId}','${paperList.studentId}')" >待打分</a></td>
							</c:if>
							<c:if test="${paperList.teacherId!=currentUser}">
								<td>待教师打分</td>
							</c:if>
						</c:if>
						<c:if test="${paperList.status=='3'}"><td><a href="javascript:;"  onclick="lookResult('${paperList.paperId}','${paperList.studentId}')">查看成绩</a></td></c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- 翻页 -->
	<div class="pageList clearfix">
		<span class="fl">共<b>${pageInfo.total}</b>条</span>
		<span class="fl">每页<b>${pageInfo.pageSize}</b>条</span>
		<span class="fl">当前第<b>${pageInfo.pageNum}</b>页</span>
		<div class="clearfix fl">
			<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}"><span class="fl">首页</span><span class="fl">上一页</span></c:if>
	          	<c:if test="${pageInfo.pageNum != 1 && pageInfo.pages > 1}">
				<a href="javascript:;" ng-click="shouye(1);" class="fl">首页</a>
				<a href="javascript:;" ng-click="prevPage(${pageInfo.pageNum-1})" class="fl">上一页</a>
			</c:if>
			<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span  class="fl">下一页</span><span class="fl">尾页</span></c:if>
	          	<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
				<a href="javascript:;" ng-click="nextPage(${pageInfo.pageNum+1})" class="fl">下一页</a>
				<a href="javascript:;" ng-click="endPage(${pageInfo.pages})" class="fl">尾页</a>
			</c:if>
		</div>
	</div>
</div>
<div class="showIframeBox showTestBox" id="showIframeBox">
	<div class="iframeBox">
		<h5 class="clearfix"><a href="javascript:;"  class="fr endTestBtn commonBtn">返回</a></h5>
		<div id="testPaperFrame" ></div>
	</div>
</div>
<script type="text/javascript">
	$("#paperStatus option[value='${status}']").attr("selected","selected");
	//全选
	$('#allCheck').click(function(){
		if($(this).prop('checked')){
			$('.checkPaper').prop('checked', true);
		}else{
			$('.checkPaper').prop('checked', false);
		}
	});
	//删除所选试题
	$('#removeCheckPaper').click(function(){
		var checkPaperIdArry = [];
		var checkPaperLen = $('.checkPaper:checked').length;
		if(checkPaperLen==0){
			alert('请选择需要删除的试卷');
			return;
		}
		for(var i=0; i<checkPaperLen; i++){
			checkPaperIdArry.push($('.checkPaper:checked').eq(i).attr('paperId'));
		}
		if(confirm('确认删除吗？删除之后将不可恢复！')){
			$.ajax({
				url : '${contextPath}/testonline/deleteTestPaper',
				data : 'paperId='+checkPaperIdArry,
				type : 'post',
				dataType : 'json',
				success : function(result){
					if(result=="success"){
						$('.checkPaper:checked').parents('tr').remove();
						alert('删除成功');
					}else{
						alert('删除失败');
					}
				}
			});
		}
	});
	
	function testPaper(paperId){
		window.localStorage.setItem('paperId',paperId);
		$('#testPaperFrame').load("${contextPath}/testonline/testPaper?paperId="+paperId+"&sortNum=0");
		$('.resourceContent').hide();
		$('#showIframeBox').stop(true).show();
	}
	
	/*刷新页面保持答卷状态*/
	var paperListtatus="${paperList.status}";
	var paperId=window.localStorage.getItem('paperId');
	var sortNum=window.localStorage.getItem('sortNum');
	if(paperId){
		$('#testPaperFrame').load("${contextPath}/testonline/testPaper?paperId="+paperId+"&sortNum="+sortNum+"&time"+new Date().getTime());
		$('#showIframeBox').css('display','block');
		$('.resourceContent').css('display','none');
	}
	function lookResult(paperId,userId){
		var url = "${contextPath}/testonline/queryStudentPaperAnswer?paperId="+paperId+"&userId="+userId+"&type=common";
		var tool ="height=800,width=850,top=40,left=500,menubar=yes, alwaysRaised=yes,scrollbars=yes";
		window.open(url,'试卷测试结果',tool);
	}
	$('.endTestBtn').on('click',function(){
		var isOk=confirm('是否确定退出本次测试，退出将不能重新测试此试卷?');
		if(isOk){
			window.localStorage.removeItem('paperId');
			window.localStorage.removeItem('sortNum');
			$('#main').load("${ctx}/student/studentTestPaperPage?time"+new Date().getTime(),function(){
				window.location.reload();
			});
		}
	});
	
</script>