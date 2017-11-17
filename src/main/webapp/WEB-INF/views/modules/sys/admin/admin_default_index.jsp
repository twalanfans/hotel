<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/admin/admin_default_index.css" />
<div class="todo">
	<!-- 管理员简介及相关操作功能 -->
	<div class="adminMessage">
		<div class="adminBrief">
			<span><img src="${filePath}/${userDetail.photo}"></span>
			<span>
				<p><b>${userDetail.userName}</b></p>
				<p>数据宝贵，删除需谨慎！</p>
			</span>
		</div>
		<div class="adminFunc">
			<ul>
				<li>
					<a ui-sref="department"  ui-sref-opts="{reload:true}">班级管理</a>
				</li>
				<li>
					<a ui-sref="filemanagement({pageNum : 1})" ui-sref-opts="{reload:true}">课件资料</a>
				</li>
				<li>
					<a ui-sref="reviewattachment({pageNum : 1, status : ''})" ui-sref-opts="{reload:true}"">课件审核</a>
				</li>
				<li>
					<a ui-sref="questionMana({pageNum : 1 , createUser : ''})" ui-sref-opts="{reload:true}">问答管理</a>
				</li>
			</ul>
		</div>
	</div>
	<!-- 栏目说明 -->
	<div class="navExplain">
		<span>待办事项：教师认证<font style="color:#50a9ea; font-weight:bold">${pageInfo1.total}</font>条，课件审核<font style="color:#50a9ea; font-weight:bold">${pageInfo.total}</font>条</span>
	</div>
	<!-- 代办事项列表 -->
	<div class="allTable">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>待办类型</tdh>
					<td>信息</td>
					<td>状态</td>
					<td>创建时间</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${pageInfo1.list}"  var="userList" >
				<tr>
					<td>
						<a ui-sref="usermanagement({pageNum:1})" ui-sref-opts="{reload:true}">新用户认证</a>
					</td>
					<td>${userList.userName}</td>
					<td>未认证</td>
					<td>${userList.createTime}</td>
				</tr>
			</c:forEach>
			<c:forEach items="${pageInfo.list}" var="fileList" >
				<tr>
					<td>
						<a ui-sref="reviewattachment({pageNum : 1, status : ''})" ui-sref-opts="{reload:true}">课件审核</a>
					</td>
					<td>${fileList.fileName}</td>
					<td>待审核</td>
					<td>${fileList.createTime}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>	
	</div>
</div>