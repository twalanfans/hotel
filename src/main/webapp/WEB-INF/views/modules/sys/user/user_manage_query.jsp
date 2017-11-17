<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<title>激活码管理系统</title>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
	    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/teacher/sendTestPaper.css" />
	</head>
<body>
<div class="navExplain" style="text-align:center;margin-top:20px;height:40px">
	<h1>用户管理</h1>
</div>
<!-- 搜索条件 -->
<div class="searchBeaf">
	<form action="${ctx}/user/queryAdminListPage" id="search" method="post">
		<span class="mb8 mr5">
			<label for='userName'>用户名称：</label>
			<input type="text" value="${userName}" name="userName" class="inpt" />
		</span>
		<span class="mb8 mr5">
			<input type="submit"  class="defaultBtn" value="搜索" />
		</span>
	</form>
	<span class="mb8 mr5">
		<a href="javascript:;" id="deleteUser" class="defaultBtn">删除</a>
	</span>
	<span class="mb8 mr5">
		<a href="javascript:;" class="defaultBtn" id="addUser">新建用户</a>
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
				<td>账号</td>
				<td>姓名</td>
				<td>学校</td>
				<td>上次登录</td>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${pageInfo.list}"  var="userList" >
			<tr>
				<td>
				<c:if test="${userList.userId=='superadmin'}">
					<input type="checkbox" class="selrolebtn1" disabled />
				</c:if>
				<c:if test="${userList.userId!='superadmin'}">
					<input type="checkbox" class="selrolebtn1" name="userId" value="${userList.userId}" />
				</c:if>
				</td>
				<td>${userList.loginName}</td>
				<td>${userList.userName}</td>
				<td>${userList.schoolName}</td>
				<td><fmt:formatDate value="${userList.loginTime}" pattern="yyyy-MM-dd HH:mm"/></td>
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
			<a href="javascript:;" onclick="pageTurn('1')">首页</a>
			<a href="javascript:;" onclick="pageTurn(${pageInfo.pageNum-1})">上一页</a>
		</c:if>
		<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span>下一页</span><span>尾页</span></c:if>
          	<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
			<a href="javascript:;" onclick="pageTurn(${pageInfo.pageNum+1})">下一页</a>
			<a href="javascript:;" onclick="pageTurn(${pageInfo.pages})">尾页</a>
		</c:if>
	</span>
</div>

<div class="uploadFileWindow">
	<div class="uploadFileTitle">添加学校管理员</div>
	<div class="uploadFileContent">
		<p>登录账号：
			<input type="text" id="loginName" maxlength="15"  class="inpt" />
		</p>
		<p>用户名：
			<input type="text" id="userName" maxlength="8"  class="inpt" />
		</p>
		<p>所在学校：
			<select id="schoolId" class="inpt">
						<option value="">—请选择—</option>
				<c:forEach items="${schoolList}"  var="schoolList" >
						<option value="${schoolList.schoolId}">${schoolList.schoolName}</option>
				</c:forEach>
			</select>
		</p>
		<p>登录密码：
			<input type="password" id="password" class="inpt" maxlength="50"/>
		</p>
		<p>确认密码：
			<input type="password" id="cpassword"  class="inpt" maxlength="50"/><font color="red" class="errorMsg"></font>
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
</body>
</html>
<script type="text/javascript" type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript">
	//删除用户，弹出提示框
	var userId = [];
	$('#deleteUser').click(function(){
		$('input[name="userId"]:checked').each(function(){ 
			userId.push($(this).val());
		});
		if(userId.length<1){
			alert("请选择需要删除的项！");
			return;
		}
		$('.promptBox').fadeIn();
	});

	//确认删除用户
	$('#confirmBtn').on('click',function(){
		$.ajax({
			type:'post',
			url:'${contextPath}/user/deleteUser',
			data:'userId='+userId,
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

$("#addUser").on('click',function(){
	$('.uploadFileWindow').fadeIn();
})	
	
function closeWin(){
	$('.uploadFileWindow').fadeOut();
}
function confirmCreate(){
	var loginName = $("#loginName").val();
	if(loginName==""){
		alert("请填写用户登录账号！");
		return
	}
	var userName = $("#userName").val();
	if(userName==""){
		alert("请填写用户名！");
		return
	}
	var schoolId = $("#schoolId").val();
	if(schoolId==""){
		alert("请选择用户所在学校！");
		return
	}
	var password = $("#password").val();
	if(password==""){
		alert("请填写用户密码！");
		return
	}
	var cpassword = $("#cpassword").val();
	if(password!=cpassword){
		$(".errorMsg").html("两次输入的密码不一致！");
		return
	}else{
		$(".errorMsg").html("");
	}

	$.ajax({
		type:'post',
		url:'${ctx}/user/createUser',
		data:'loginName='+loginName+"&userName="+userName+"&password="+password+"&schoolId="+schoolId,
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