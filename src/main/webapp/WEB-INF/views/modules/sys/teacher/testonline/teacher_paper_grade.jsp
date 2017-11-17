<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="navExplain">
	<span>试卷判分</span>
</div>
<div class="testPaper">
	<!--搜索-->
	<div class="searchPanel clearfix">
			<span>
				<label>学生姓名：</label>
				<input type="text" class="inpt" id="studentName"  ng-model="studentName" placeholder="关键字查询"  />
			</span>
	<span class="mb8 mr5">
				<label>试卷状态</label>
				<select id="status" class="inpt" ng-model="status">
					<option value="">—请选择—</option>
					<option value="0">未开始</option>
					<option value="1">答题中</option>
					<option value="2">待打分</option>
					<option value="3">已打分</option>
				</select>
			</span>
			<span>
				<a ui-sref="teacherPaperGrade({pageNum : 1, studentName : this.studentName, status : this.status})" ui-sref-opts="{reload:true}" class="defaultBtn" id="searchPaper">搜索</a>
			</span>
			<span>
				<a href="javascript:;" class="defaultBtn" onclick="deletePaper()" title="撤回发送给用户的试卷">撤回</a>
			</span>
	</div>
	<!--组卷记录-->
	<div class="allTable">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>
						<label for="allCheck">全选</label>
						<input type="checkbox" id="allCheck" />
					</td>
					<td>试卷名称</td>
					<td>答题用户</td>
					<td>答题状态</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pageInfo.list}"  var ="paperList">
					<tr>
						<td><input type="checkbox" class="checkChoose" name="paperId" stuId="${paperList.studentId}" value="${paperList.paperId}" /></td>
						<td>${paperList.paperName}</td>
						<td>${paperList.studentName}</td>
						<c:if test="${paperList.status=='0'}">
							<td>未开始</td>
						</c:if>
						<c:if test="${paperList.status=='1'}">
							<td>答题中</td>
						</c:if>
						<c:if test="${paperList.status=='2'}">
							<td><a href="javascript:;" onclick="lookResult('${paperList.paperId}','${paperList.studentId}')" >待打分</a></td>
						</c:if>
						<c:if test="${paperList.status=='3'}">
							<td><a href="javascript:;" onclick="lookResult('${paperList.paperId}','${paperList.studentId}')" >已打分</a></td>
							<%-- <td><a ui-sref="lookTestResult({paperId : '${paperList.paperId}', studentId :  '${paperList.studentId}' })">已打分</a></td> --%>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!--翻页-->
	<div class="pageList clearfix">
		<span class="fl">共<b>${pageInfo.total}</b>条</span>
		<span class="fl">每页<b>${pageInfo.pageSize}</b>条</span>
		<span class="fl">当前第<b>${pageInfo.pageNum}</b>页</span>
		<span class="clearfix fl">
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
		</span>
	</div>
</div>
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript">
	$("#status option[value='${status}']").attr("selected","selected");
	function deletePaper(){
		var paperId =[]; var studentId=[];
		$('input[name="paperId"]:checked').each(function(){ 
			paperId.push($(this).val());
			studentId.push($(this).attr("stuId"));
		});  
		if(paperId.length<1){
			alert("请选择需要撤回的项！");
			return 
		}
		if(confirm("确认撤回吗？撤回之后对方试卷信息会丢失！")){
			$.ajax({
				type:'post',
				url:'${contextPath}/testonline/revokeTestPaper',
				data:'paperId='+paperId+"studentId="+studentId,
				dataType:'json',
				success:function(str){
					if(str=="success"){
						alert("试卷撤回成功！");
						window.location.reload();
					}else{
						alert("试卷撤回失败！");
					}
				}
			});
		}
	}
	
	function lookResult(paperId,userId){
		var url = "${contextPath}/testonline/queryStudentPaperAnswer?paperId="+paperId+"&userId="+userId+"&type=te1";
		var tool ="height=800,width=850,top=40,left=500,menubar=yes, alwaysRaised=yes,scrollbars=yes";
		window.open(url,'试卷测试结果',tool);
	}
	
	//-----全选/取消
	$('#allCheck').on('click',function(){
		if($('#allCheck').prop('checked')){
			$('.checkChoose').prop('checked',true);
		}else{
			$('.checkChoose').prop('checked',false);
		}
	});
	
</script>