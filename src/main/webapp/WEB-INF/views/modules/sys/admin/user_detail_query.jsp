<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<!--tkd-->
		<title>用户详细消息</title>
		<meta name="Keywords" content="">
		<meta name="Description" content="">
		<!--css-->
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
	</head>
	<body>
		<div class="allTable" style="margin:10px;">
			<table cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<td colspan="2">详细信息</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>姓名</td>
						<td class="userInfo">${userDetail.userName}</td>
					</tr>
					<tr>
						<td>登陆账号</td>
						<td class="userInfo">${userDetail.loginName}</td>
					</tr>
					<tr>
						<td>性别</td>
						<td class="userInfo">
							<c:if test="${userDetail.sex=='1'}">男</c:if>
							<c:if test="${userDetail.sex=='0'}">女</c:if>
						</td>
					</tr>
					<tr>
						<td>用户类型</td>
						<td class="userInfo">
							<c:if test="${userDetail.userType=='1'}">学生</c:if>
							<c:if test="${userDetail.userType=='2'}">老师</c:if>
						</td>
					</tr>
					<tr>
						<td>联系电话</td>
						<td class="userInfo">${userDetail.phone}</td>
					</tr>
					<tr>
						<td>邮箱</td>
						<td class="userInfo">${userDetail.email}</td>
					</tr>
					<tr>
						<td>出生日期</td>
						<td class="userInfo">${userDetail.birthday}</td>
					</tr>
					<tr>
						<td>所在系</td>
						<td class="userInfo">${userDetail.departName}</td>
					</tr>
					<tr>
						<td>所在专业</td>
						<td class="userInfo">${userDetail.professionName}</td>
					</tr>
					<tr>
						<td>所在班级</td>
						<td class="userInfo">${userDetail.gradeName}</td>
					</tr>
					<tr>
						<td>家庭住址</td>
						<td class="userInfo">${userDetail.address}</td>
					</tr>
					<tr>
						<td>邮编</td>
						<td class="userInfo">${userDetail.postCode}</td>
					</tr>
					<tr>
						<td>创建时间</td>
						<td class="userInfo">${userDetail.createTime}</td>
					</tr>
					<tr>
						<td>更新时间</td>
						<td class="userInfo">${userDetail.updateTime}</td>
					</tr>
					<tr>
						<td>个性签名</td>
						<td class="userInfo">${userDetail.remark}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<a href="javascript:;" id="close" class="commonBtn close">关闭</a>
		<script type="text/javascript" src="${ctxStatic}/js/sonWindow.js"></script>
		<script type="text/javascript">
			(function(){
				//填补空白处
				var userInfo = document.getElementsByClassName('userInfo');
				var userInfoLen = userInfo.length;
				for(var i=0; i<userInfoLen; i++){
					if(userInfo[i].innerText==''){
						userInfo[i].innerText = '---';
					}
				}
			})();
		</script>
	</body>
</html>
