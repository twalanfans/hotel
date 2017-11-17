<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>我的试卷</title>
		<meta name="Keywords" content="">
		<meta name="Description" content="">
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/common.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/testpaper.css" />
	</head>
	<body>
		<div class="testPaper">
			<!--搜索-->
			<div class="searchPanel clearfix">
				<form action="${ctx}/testonline/myselfPaperPage" id="paperForm" method="post" class="fl">
					<span>
						<label>试卷名称：</label>
						<input type="text" class="inpt" name="paperName" value="${paperName}"/>
					</span>
					<span>
						<label>试卷状态</label>
						<select id="status" name="status" class="sel">
							<option value="">—请选择—</option>
							<option value="0">未使用</option>
							<option value="1">使用中</option>
						</select>
					</span>
					<input	type="submit" value="搜索" class="commonBtn" />
				</form>
				<a href="javascript:;" class="commonBtn fl" onclick="deletePaper()">删除</a>
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
							<td>状态</td>
							<td>创建时间</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageInfo.list}"  var ="paperList">
							<tr>
								<td><input type="checkbox" class="checkChoose" name="paperId" value="${paperList.paperId}" /></td>
								<td><a href="javascript:;" class="editName" onclick="paperDetail(${paperList.paperId})" title="试卷详情">${paperList.paperName}</a></td>
								<c:if test="${paperList.status=='0'}">
									<td>未使用</td>
								</c:if>
								<c:if test="${paperList.status=='1'}">
									<td>使用中</td>
								</c:if>
								<td><fmt:formatDate value="${paperList.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!--翻页-->
			<div class="pageList">
				<span>共<b>${pageInfo.total}</b>条</span>
				<span>每页<b>${pageInfo.pageSize}</b>条</span>
				<span>当前第<b>${pageInfo.pageNum}</b>页</span>
				<span>
					<a href="#" onclick="pageTurn('1')">首页</a>
					<a href="#" onclick="pageTurn(${pageInfo.pageNum-1})">上一页</a>
					<a href="#" onclick="pageTurn(${pageInfo.pageNum+1})">下一页</a>
					<a href="#" onclick="pageTurn(${pageInfo.pages})">尾页</a>
				</span>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript">
alert(123);
	//错题组卷
	$("#status option[value='${status}']").attr("selected","selected");
	function deletePaper(){
		var paperId =[];
		$('input[name="paperId"]:checked').each(function(){ 
			paperId.push($(this).val());
		});  
		if(paperId.length<1){
			alert("请选择需要删除的项！");
			return 
		}
		if(confirm("确认删除吗？删除之后将不可恢复")){
			$.ajax({
				type:'post',
				url:'${contextPath}/testonline/deleteTestPaper',
				data:'paperId='+paperId,
				dataType:'json',
				success:function(str){
					if(str=="success"){
						alert("试卷删除成功！");
						window.location.reload();
					}else{
						alert("试卷删除失败！");
					}
				}
			});
		}
	}
	
	function paperDetail(paperId){
		var url = "${ctx}/testonline/paperDetail?paperId="+paperId+"&userType=te1";
		var tool ='height=1000,width=900,top=150,left=600,menubar=yes, alwaysRaised=yes'
		window.open(url,'试卷详情页',tool);
	}
	
	//-----全选/取消
	$('#allCheck').on('click',function(){
		if($('#allCheck').prop('checked')){
			$('.checkChoose').prop('checked',true);
		}else{
			$('.checkChoose').prop('checked',false);
		}
	});
	
	function pageTurn(pageNum){
		document.getElementById("paperForm").action="${ctx}/testonline/myselfPaperPage?page="+pageNum;
		document.getElementById("paperForm").submit();
	}
</script>