<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/student/welcomestudent.css">
<!-- 管理员简介及相关操作功能 -->
<div class="adminMessage clearfix">
	<div class="adminBrief">
			<span>
				<img src="${filePath}/${userDetail.photo}" />
			</span>
			<span><font color="blue">${userDetail.userName}</font>${greetings}</span>
	</div>
	<div class="adminFunc">
		<ul>
			<li >
				<a ui-sref="studentCourseStudy" ui-sref-opts="{reload:true}">课程学习</a>
			</li>
			<li>
				<a ui-sref="quesAnswOnline({showNum : '', title : ''})" ui-sref-opts="{reload:true}" >在线问答</a>
			</li>
			<li>
				<a ui-sref="studentPaperTest({pageNum : 1})" ui-sref-opts="{reload:true}">在线测试</a>
			</li>
			<li>
				<a ui-sref="studentErrorNotes({pageNum : 1})" ui-sref-opts="{reload:true}">错题记录</a>
			</li>
		</ul>
	</div>
</div>
<div class="myhelplistbox clearfix">
	<div class="myhelplist_l">
		<h3>我的课程</h3>
		<ul>
			<c:forEach items="${courseList}"  var="courseList">
				<li ui-sref="lookCourseFile({courseId : ${courseList.courseId}, courseType : '', pageNum : 1, fileName : ''})">
					<p class="fs18">${courseList.courseName}</p>
					<p>教师：${courseList.teacherName}</p>
					<span class="fa fa-angle-right morebtn"></span>
				</li>
			</c:forEach>
		</ul>
	</div>
	<div class="myhelplist_r">
		<h3>我的计划</h3>
		<div class="myplan_allbox">
			<ul class="myplan_all">
				<li>
					<h4>今天:${today} (目标/完成)</h4>
					<c:if test="${studyPlanDetail==null}">
						<ul class="myplanlist"><span>您还没有制定今天的学习计划，现在就去<a ui-sref="studentAddStudyPlan" ui-sref-opts="{reload:true}"><font font-size="16">制定</font></a>吧</span></ul>
					</c:if>
					<ul class="myplanlist">
						<li>
							<p class="clearfix">
								<span class="fl fa fa-map-marker mapicon"></span>
								<span class="fl">微课时长(分钟)：</span>
								<a href="javascript:;" class="fl">${studyPlanDetail.courseCount}/${studyPlanDetail.studyTimeLong}</a>
							</p>
						</li>
						<li>
							<p class="clearfix">
								<span class="fl fa fa-map-marker mapicon"></span>
								<span class="fl">组卷测题数：</span>
								<a href="javascript:;" class="fl">${studyPlanDetail.testCount}/${studyPlanDetail.testQuestionNum}</a>
							</p>
						</li>
						<li>
							<p class="clearfix">
								<span class="fl fa fa-map-marker mapicon"></span>
								<span class="fl">在线提问数：</span>
								<a href="javascript:;" class="fl">${studyPlanDetail.askCount}/${studyPlanDetail.askQuestionNum}</a>
							</p>
						</li>
					</ul>
					<span class=" yuanicon"></span>
				</li>
				
			</ul>
		</div>
	</div>
</div>