<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 栏目说明 -->
<div class="navExplain">
	<span>用户管理</span>
</div>
<!-- 搜索条件 -->
<div class="searchBeaf">
	<span class="mb8 mr5">
		<label for='searchUserName'>用户名称：
		<input type="text" ng-model="userName"  id="searchUserName" class="inpt" />
	</span>
	<span class="mb8 mr5">
		<label for='searchLoginName'>登陆账号：
		<input type="text" ng-model="loginName" id="searchLoginName" class="inpt" />
	</span>
	<span class="mb8 mr5">
		<label for='searchUserType'>用户类型：</label>
		<select ng-model="userType" id="searchUserType" class="inpt">
			<option value="">全部</option>
			<option value="0">管理员</option>
			<option value="1">学生</option>
			<option value="2">老师</option>
		</select>
	</span>
	<span class="mb8 mr5">
		<a ui-sref="usermanagement({pageNum : 1, userName : this.userName, loginName : this.loginName, userType : this.userType})" ui-sref-opts="{reload:true}" class="defaultBtn">搜索</a>
	</span>
	<span class="mb8 mr5">
		<a href="javascript:;" id="deleteUser" class="defaultBtn">删除</a>
	</span>
	<span class="mb8 mr5">
		<a href="javascript:;" ng-click="insertUser();" class="defaultBtn">新建用户</a>
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
				<td>性别</td>
				<td>身份</td>
				<td>状态</td>
				<td>创建时间</td>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${pageInfo.list}"  var="userList" >
			<tr>
				<td>
					<input type="checkbox" class="selrolebtn1" name="userId" value="${userList.userId}" />
				</td>
				<td>
					<a href="javascript:;" class="userDetail" onclick="queryUserDetail('${userList.userId}')" style="color:blue" title="查看该用户详情">${userList.loginName}</a>
				</td>
				<td class="showuserinfo">${userList.userName}</td>
				<c:if test="${userList.sex=='1'}"><td>男</td></c:if>
				<c:if test="${userList.sex=='0'}"><td>女</td></c:if>
				<c:if test="${userList.userType=='0'}"><td>管理员</td></c:if>
				<c:if test="${userList.userType=='1'}"><td>学生</td></c:if>
				<c:if test="${userList.userType=='2'}"><td>老师</td></c:if>
				<c:if test="${userList.status=='1'}"><td>可用</td></c:if>
				<c:if test="${userList.status=='2'}"><td><a href="javascript:;" onclick="teacherPass('${userList.userId}')" title="认证该用户为教师身份">认证通过</a></td></c:if>
				<td><fmt:formatDate value="${userList.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
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
				<a href="javascript:;"  ng-click="shouye(1)">首页</a>
				<a href="javascript:;" ng-click="prevPage(${pageInfo.pageNum-1})">上一页</a>
			</c:if>
			<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span>下一页</span><span>尾页</span></c:if>
	          	<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
				<a href="javascript:;" ng-click="nextPage(${pageInfo.pageNum+1})">下一页</a>
				<a href="javascript:;" ng-click="endPage(${pageInfo.pages})">尾页</a>
			</c:if>
		</span>
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
<script type="text/javascript">
	$("#searchUserType option[value='${userType}']").attr("selected","selected");
	//查看用户详情
	function queryUserDetail(userId){
		var url = "${contextPath}/user/queryUserDetail?userId="+userId;
		var tool ='height=700,width=800,top=150,left=600,location=no,menubar=no,toolbar=no,alwaysRaised=yes,status=no,scrollbars=yes';
		window.open(url,'用户详细信息',tool);
	}

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
				if(str=="success"){
					$('.promptTxt p:eq(1) span').html('删除成功！');
					$('.promptBox').fadeOut();
					window.location.reload();
				}else{
					$('.promptTxt p:eq(1) span').html('删除失败，请重新操作！');
					$('.promptBox').fadeOut();
				}
			}
		});
	});

	//取消删除用户
	$('#cancelBtn').on('click', function(){
		$('.promptBox').fadeOut();
	});
	
	//认证通过老师身份
	function teacherPass(userId){
		if(confirm("确定将该用户认证为教师身份吗？")){
			$.ajax({
				type:'post',
				url:'${contextPath}/teacherPass',
				data:'userId='+userId,
				dataType:'json',
				success:function(str){
					if(str=="success"){
						alert('认证成功');
						window.location.reload();
					}else{
						alert('认证失败，请重新操作！');
					}
				}
			});
		}
	}
	
	//全选/取消
	$('#selallbtn1').on('click',function(){
		if($('#selallbtn1').prop('checked')){
			$('.selrolebtn1').prop('checked',true);
		}else{
			$('.selrolebtn1').prop('checked',false);
		}
	});
</script>