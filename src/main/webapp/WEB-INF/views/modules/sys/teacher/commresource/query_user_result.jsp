<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="allTable">
	<h3 style="margin-bottom:10px;font-size:14px;">共找到 ${resultNum} 条相关结果。。</h3>
	<table cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<td>手机号</td>
				<td>用户名</td>
				<td>性别</td>
			</tr>
		</thead>
		<tbody>
			<c:if test="${resultNum!='0'}">
			<c:forEach items="${userList}"  var="userList"  varStatus="status">
			<tr>
				<td>
					<a href="javascript:;" onclick="dataAnalysis('${userList.userId}','${userList.remark}')" >${userList.loginName}</a>
				</td>
				<td>${userList.userName}</td>
				<td>
					<c:if test="${userList.sex=='0'}">
						<span>女</span>
					</c:if>
					<c:if test="${userList.sex=='1'}">
						<span>男</span>
					</c:if>
				</td>
			</tr>
			</c:forEach>
		  </c:if>
		  <c:if test="${resultNum=='0'}">
		  	<tr>
		  		<td colspan="3">
		  			<b>没有找到该用户！</b>
		  		</td>
		  	</tr>
		  </c:if>
		</tbody>
	</table>
</div>
<div class="showDataAnalysisBox" id="showDataAnalysisBox">
<script type="text/javascript">
	function dataAnalysis(userId,userName){
		$('.allTable').hide();
		$('#showDataAnalysisBox').load("${ctx}/ownCenter/dataAnalysis?userId="+userId+"&userName="+userName);
	}
</script>