<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div class=" grouplist_l">
			<div class="searchBeaf">
				<span class="mb8 mr5">
					<label class="searchinfo">学生姓名：</label>
					<input type="text"  placeholder="请输入姓名" value="${userName}"  id="userName" class="inpt" />
				</span>
				<span class="mb8 mr5">
					<label class="searchinfo">所属班级：</label>
					<input type="text" placeholder="请输入班级" value="${gradeName}"  id="gradeName" class="inpt" />
				</span>
				<span class="mb8 mr5">
					<a href="javascript:;" class="defaultBtn" onclick="searchUser();">学生查询</a>
				</span>
				<span class="mb8">
					<a href="javascript:;" onclick="addToGroup();" class="defaultBtn">加入群组</a>
				</span>
			</div>
			<div class="allTable">
				<table cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<td>
								<input type="checkbox" id="selallbtn"  style="display:none;"/>
								<label for="selallbtn">全选</label>
							</td>
							<td>班级</td>
							<td>姓名</td>
							<td>性别</td>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${pageInfo.list}"  var="userList" >
						<tr>
							<td><input type="checkbox" class="selrolebtn" name="userId" value="${userList.userId}"/></td>
							<td>${userList.gradeName}</td>
							<td>${userList.userName}</td>
							<c:if test="${userList.sex=='1'}"><td>男</td></c:if>
							<c:if test="${userList.sex=='0'}"><td>女</td></c:if>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- 翻页 -->
			<div class="pageList clearfix">
				<span class="fl" onclick="test();">共<b>${pageInfo.total}</b>条</span>
				<span class="fl">每页<b>${pageInfo.pageSize}</b>条</span>
				<span class="fl">当前第<b>${pageInfo.pageNum}</b>页</span>
				<span class="fl clearfix">
					<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}"><span class="fl">首页</span><span class="fl">上一页</span></c:if>
		          	<c:if test="${pageInfo.pageNum != 1 && pageInfo.pages > 1}">
		            	<a href="javascript:;" onclick="pageTurnJoin('1')"  class="fl">首页</a>
		            	<a href="javascript:;" onclick="pageTurnJoin(${pageInfo.pageNum-1})" class="fl">上一页</a>
	            	</c:if>
	            	<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span class="fl">下一页</span><span class="fl">尾页</span></c:if>
		          	<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
		            	<a href="javascript:;" onclick="pageTurnJoin(${pageInfo.pageNum+1})" class="fl">下一页</a>
		            	<a href="javascript:;" onclick="pageTurnJoin(${pageInfo.pages})" class="fl">尾页</a>
	            	</c:if>
				</span>
			</div>
	</div>
<script type="text/javascript">
	//全选/取消
	$('#selallbtn').on('click',function(){
		if($('#selallbtn').prop('checked')){
			$('.selrolebtn').prop('checked',true);
		}else{
			$('.selrolebtn').prop('checked',false);
		}
	});
	
	function searchUser(){
		var userName = $('#userName').val();
		var gradeName = $('#gradeName').val();
		$('.allStudent').load("${contextPath}/ownCenter/allStudentQueryPage?userName="+userName+"&gradeName="+gradeName);
	}
	
	function pageTurnJoin(pageNum){
		$('.allStudent').load("${contextPath}/ownCenter/allStudentQueryPage?page="+pageNum+"&studentName=${studentName}"+"&gradeName=${gradeName}");
	}
	
	function addToGroup(){
		var userId = [];
		$('input[name="userId"]:checked').each(function(){ 
			userId.push($(this).val());
		});  
		if(userId.length<1){
			userPrompt('请先选择需要加入相应群组的学生！');
			return;
		}	
		var roleId=$('#comRoleId').val();
		if(roleId=="" || roleId==null){
			userPrompt('请先选择相应的群组！');
			return;
		}
		$.ajax({
			type:'post',
			url:'${contextPath}/user/addUserToRole',
			data:'roleId=' + roleId + '&userId=' + userId,
			dataType:'json',
			success:function(str){
				if(str.status=="success"){
					$('.studentGroup').load("${contextPath}/ownCenter/groupStudentPage?roleId="+roleId+"&studentName=");
					userPrompt('添加成功！');
				}else{
					userPrompt('添加失败！');
				}
			}
		});
	}
</script>