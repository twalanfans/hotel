<!-- student_mystudy_plan.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="noStudyPlan">
	<div class="noPlayPrompt clearfix">
		<span><img src="${ctxStatic}/images/noPlan.png" width="48" height="48" /></span>
		<span>学习计划</span>
	</div>
	<p>抱歉，您尚未制定个人学习计划，请您：</p>
	<a class="toPlan clearfix" ui-sref="studentAddStudyPlan">立即制定</a>
 </div>