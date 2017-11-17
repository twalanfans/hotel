<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
	.uploadFileWindow{width:300px;border:1px solid #ccc;background-color:#fff;display:none;position:fixed;top:32%;left:50%;margin-left:-150px;}
	.uploadFileTitle{height:30px;line-height:30px;padding-left:10px;background-color:#6bc5a4;color:#fff;font-size:12px;border-radius:6px 6px 0 0;}
</style>
<input type="text" id="comRoleId" value="${roleId}" style="display:none">
	<div class="grouplist_r">
			<!-- 搜索条件 -->
			<div class="searchBeaf clearfix">
				<span class="mb8 mr5 fl clearfix">
					<label class="searchinfo fl">姓名：<input type="text" id="studentName" placeholder="请输入姓名" class="inpt" value="${studentName}"/></label>
					<a href="javascript:;" class="defaultBtn fl" onclick="queryStudent('${roleId}');">查询</a>
				</span>
				<span class="mb8 fl">
					<a href="javascript:;" onclick="groupRemove();" class="defaultBtn">移出群组</a>
				</span>
			</div>
			<!-- 搜索结果 -->
			<div class="allTable">
				<table cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<td>
								<input type="checkbox" id="selallbtn1" class="inpt" style="display:none;"/>
								<label for="selallbtn1">全选</label>
							</td>
							<td>班级</td>
							<td>姓名</td>
							<td>性别</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageInfo1.list}"  var="userList" >
							<tr>
								<td><input type="checkbox" class="selstudentbtn" name="userId" value="${userList.userId}"/></td>
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
			<div class="pageList">
				<span>共<b>${pageInfo1.total}</b>条</span>
				<span>每页<b>${pageInfo1.pageSize}</b>条</span>
				<span>当前第<b>${pageInfo1.pageNum}</b>页</span>
				<span>
					<c:if test="${pageInfo1.pageNum == 1 || pageInfo1.pageNum == 0}"><span>首页</span><span>上一页</span></c:if>
		          	<c:if test="${pageInfo1.pageNum != 1 && pageInfo1.pages > 1}">
		            	<a href="javascript:;" onclick="pageTurnRemove('1')">首页</a>
		            	<a href="javascript:;" onclick="pageTurnRemove(${pageInfo1.pageNum-1})">上一页</a>
	            	</c:if>
	            	<c:if test="${pageInfo1.pageNum == pageInfo1.pages || pageInfo1.pages == 0}"><span>下一页</span><span>尾页</span></c:if>
		          	<c:if test="${pageInfo1.pageNum != pageInfo1.pages && pageInfo1.pages != 0}">
		            	<a href="javascript:;" onclick="pageTurnRemove(${pageInfo1.pageNum+1})">下一页</a>
		            	<a href="javascript:;" onclick="pageTurnRemove(${pageInfo1.pages})">尾页</a>
	            	</c:if>
				</span>
			</div>
		</div>
<script type="text/javascript">
	$("#roleId option[value='${roleId}']").attr("selected","selected");
	//全选/取消
	$('#selallbtn1').on('click',function(){
		if($('#selallbtn1').prop('checked')){
			$('.selstudentbtn').prop('checked',true);
		}else{
			$('.selstudentbtn').prop('checked',false);
		}
	});
	
	function queryStudent(roleId){
		var studentName = $('#studentName').val();
		$('.studentGroup').load("${contextPath}/ownCenter/groupStudentPage?roleId="+roleId+"&studentName="+studentName);
	}
	
	function pageTurnRemove(pageNum){
		var studentName = $('#studentName').val();
		$('.studentGroup').load("${contextPath}/ownCenter/groupStudentPage?page="+pageNum+"&studentName="+studentName+"&roleId=${roleId}");
	}
	
	function closeWin(){
		$('#groupName').val("");
		$('.uploadFileWindow').fadeOut();
	}
	
	function confirmSave(){
		var groupName = $('#groupName').val();
		if(groupName=="" || groupName==null){
			userPrompt('群组名称不能为空！');
			return
		}
		$.ajax({
			type:'post',
			url:'${contextPath}/role/insertRole',
			data:'name='+groupName,
			dataType:'json',
			success:function(str){
				if(str!="error"){
					queryStudent(str);
					userPrompt('群组新建成功！');
					window.location.reload();
				}else{
					userPrompt('群组新建失败！');
				}
			}
		});
	}
	
	//删除群组
	function deleteGroup(){
		if("${roleId}"==""){
			userPrompt('请先选择需要删除的群组！');
			return
		}
	 	if(!confirm("确定要删除该群组吗？")) return;
	 	$.ajax({
			type:'post',
			url:'${contextPath}/role/deleteRole',
			data:'roleId=${roleId}',
			dataType:'json',
			success:function(str){
				if(str=="success"){
					alert('删除成功！');
					window.location.reload();
				}else{
					userPrompt('新建群组失败！');
				}
			}
		});
	}
	
	function groupRemove(){
		var userId = [];
		$('input[name="userId"]:checked').each(function(){ 
			userId.push($(this).val());
		});  
		if(userId.length<1){
			userPrompt('请选取需要移除群组的学生！');
			return;
		}	
		$.ajax({
			type:'post',
			url:'${contextPath}/role/removeStudent',
			data:'roleId=${roleId}'+'&userId='+userId,
			dataType:'json',
			success:function(str){
				if(str=="success"){
					$('.studentGroup').load("${contextPath}/ownCenter/groupStudentPage?roleId=${roleId}"+"&studentName=");
					userPrompt('学生已被移除群组！');
				}else{
					userPrompt('移除群组失败，请重新操作！');
				}
			}
		});
	}
</script>