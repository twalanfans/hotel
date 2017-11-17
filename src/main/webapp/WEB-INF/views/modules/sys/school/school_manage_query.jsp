<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/teacher/sendTestPaper.css" />

<!-- 搜索条件 -->
<div>
	<div class="navExplain">
		<span>学校列表</span>
	</div>
<div class="searchBeaf">
	<form action="${ctx}/school/querySchoolPage" id="search" method="post">
		<span class="mb8 mr5">
			<label for='schoolName'>学校名称：</label>
			<input type="text" value="${schoolName}" name="schoolName" class="inpt" />
		</span>
		<span class="mb8 mr5">
			<input type="submit"  class="defaultBtn" value="搜索" />
		</span>
	</form>
	<span class="mb8 mr5">
		<a href="javascript:;" id="deleteSchool" class="defaultBtn">删除</a>
	</span>
	<span class="mb8 mr5">
		<a href="javascript:;" id="addSchool" class="defaultBtn" >添加学校</a>
	</span>
</div>
<!-- 搜索结果 -->
<div class="allTable">
	<table cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<td>
					<label for="selallbtn1">全选&nbsp;&nbsp;</label>
					<input type="checkbox" id="selallbtn1" style="display:none" />
				</td>
				<td>校名</td>
				<td>拥有激活码数</td>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${pageInfo.list}"  var="schoolList" >
			<tr>
				<td>
					<c:if test="${schoolList.schoolId=='0'}">
						<input type="checkbox" class="selrolebtn1" disabled />
					</c:if>
					<c:if test="${schoolList.schoolId!='0'}">
						<input type="checkbox" class="selrolebtn1" name="schoolId" value="${schoolList.schoolId}" />
					</c:if>
				</td>
				<td>${schoolList.schoolName}</td>
				<td>${schoolList.codeNum}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</div>
<!-- 翻页 -->
<div class="pageList">
	<span>共<b>${pageInfo.total}</b>条</span>
	<span>每页<b>${pageInfo.pageSize}</b>条</span>
	<span>当前第<b>${pageInfo.pageNum}</b>页</span>
	<span>
		<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}"><span>首页</span><span>上一页</span></c:if>
          	<c:if test="${pageInfo.pageNum != 1 && pageInfo.pages > 1}">
			<a href="javascript:;" ng-click="pageTurn1('1')">首页</a>
			<a href="javascript:;" ng-click="pageTurn1(${pageInfo.pageNum-1})">上一页</a>
		</c:if>
		<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span>下一页</span><span>尾页</span></c:if>
          	<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
			<a href="javascript:;" ng-click="pageTurn1(${pageInfo.pageNum+1})">下一页</a>
			<a href="javascript:;" ng-click="pageTurn1(${pageInfo.pages})">尾页</a>
		</c:if>
	</span>
</div>
<div class="uploadFileWindow">
	<div class="uploadFileTitle">添加学校</div>
	<div class="uploadFileContent">
		<p>学校名称：
			<input type="text" id="schoolName" maxlength="32"  class="inpt" />
		</p>
		<p>
			<a href="javascript:;" class="commonBtn" onclick="confirmCreate();">确认添加</a>
			<a href="javascript:;" class="commonBtn" onclick="closeWin();">取消</a>	
		</p>
	</div>
</div>

<!-- 用户操作提示框 -->
<div class="promptBox">
	<div class="promptTxt">
		<p>提示</p>
		<p>
			<i class="fa fa-exclamation-circle fl" style="margin:20px 5px 0 20px;"></i>
			<span>删除将不可恢复，是否确定删除？</span>
		</p>
	</div>
	<div class="promptBtn">
		<a href="javascript:;" id="confirmBtn" class="commonBtn">确认</a>
		<a href="javascript:;" id="cancelBtn" class="defaultBtn">取消</a>
	</div>
</div>
<ul class="showuserinfobox"></ul>
</div>
<script type="text/javascript" type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript">
	//删除用户，弹出提示框
	var schoolId = [];
	$('#deleteSchool').click(function(){
		$('input[name="schoolId"]:checked').each(function(){ 
			schoolId.push($(this).val());
		});  
		if(schoolId.length<1){
			alert("请选择需要删除的项！");
			return;
		}
		$('.promptBox').fadeIn();
	});

	//确认删除用户
	$('#confirmBtn').on('click',function(){
		$.ajax({
			type:'post',
			url:'${ctx}/school/deleteSchool',
			data:'schoolId='+schoolId,
			dataType:'json',
			success:function(str){
				if(str!="error"){
					$('.promptTxt p:eq(1) span').html("删除成功,本次共删除"+str+"个！");
					$('.promptBox').show();
					setTimeout(function(){
						window.location.reload();
					},2000);
				}else{
					$('.promptTxt p:eq(1) span').html('删除失败，请重新操作！');
					$('.promptBox').show();
					setTimeout(function(){
						window.location.reload();
					},2000);
				}
			}
		});
	});

	//取消删除用户
	$('#cancelBtn').on('click', function(){
		$('.promptBox').fadeOut();
	});

$("#addSchool").on('click',function(){
		$('.uploadFileWindow').fadeIn();
})	
	
function closeWin(){
	$('.uploadFileWindow').fadeOut();
}
function confirmCreate(){
	$('.uploadFileWindow').hide();
	var schoolName = $("#schoolName").val();
	$.ajax({
		type:'post',
		url:'${ctx}/school/createSchool',
		data:'schoolName='+schoolName,
		dataType:'json',
		success:function(str){
			if(str=="error"){
				alert("添加失败！");
				window.location.reload();
			}else{
				alert("添加成功！");
				window.location.reload();
			}
		}
	});
}
	
	//全选/取消
	$('#selallbtn1').on('click',function(){
		if($('#selallbtn1').prop('checked')){
			$('.selrolebtn1').prop('checked',true);
		}else{
			$('.selrolebtn1').prop('checked',false);
		}
	});
	
	function pageTurn(pageNum){
		document.getElementById("search").action="${ctx}/school/querySchoolPage?page="+pageNum;
		document.getElementById("search").submit();
	}
</script>