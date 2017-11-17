<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page import= "com.module.course.entity.Course"%> 
<%@ page import= "com.module.course.service.CourseManageService"%> 
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/teacher/welcometeacher.css" />
<div class="todo" style="margin:35px 10px 10px 20px">
	<!-- 教师简介及相关操作功能 -->
	<div class="adminMessage">
		<div class="adminBrief">
			<span><img src="${filePath}/${userDetail.photo}" /></span>
			<span><font color="blue">${userDetail.userName}</font>${greetings}</span>
		</div>
		<div class="adminFunc">
			<ul>
				<li>
					<a ui-sref="studentGroup" ui-sref-opts="{reload:true}">群组管理</a>
				</li>
				<li>
					<a ui-sref="quesAnswOnline({showNum : '', title : ''})" ui-sref-opts="{reload:true}">问答管理</a>
				</li>
				<li>
					<a ui-sref="teacherPaperGrade({pageNum : 1})" ui-sref-opts="{reload:true}">试卷判分</a>
				</li>
				<li>
					<a ui-sref="makeTestPaper({pageNum : 1})" ui-sref-opts="{reload:true}">试卷管理</a>
				</li>
			</ul>
		</div>
	</div>
	<!-- 栏目说明 -->
	<div class="navExplain">
		<span>您当前共有<font color="#1278f6">${courseNum}</font>个课程</span>
	</div>
	<div class="allTable">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>课程名称</td>
					<td>创建时间</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="courseList" items="${courseList}">
				<tr>
					<td>
						<a ui-sref="lookMyCourseFile({pageNum : 1, courseId : ${courseList.courseId}, courseType : 1, fileName : ''})" ui-sref-opts="{reload:true}">${courseList.courseName}</a>
					</td>
					<td><fmt:formatDate value="${courseList.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
					<td>
						<a href="javascript:;" title="查看简介" class="showCourseIntroduce" style="cursor:default;">查看简介&nbsp;&nbsp;|</a>
						<a href="javascript:;" title="授予群组" onclick="haveGroup(${courseList.courseId});">授予群组</a>
						<div class="courseIntroduce">
							<p>
								<b>课程简介：</b>
							</p>
							<p>${courseList.introduce}</p>
						</div>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<script type="text/javascript">
	function haveGroup(courseId){
		var url = "${contextPath}/role/haveRelationGroup?courseId="+courseId;
		var tool ='height=500,width=800,top=150,left=600,menubar=yes, alwaysRaised=yes'
		window.open(url,'课程群组关联',tool);
	}
	
	$('.showCourseIntroduce').hover(function(){
		$('.courseIntroduce').hide();
		$(this).siblings('.courseIntroduce').show();
	}, function(){
		$('.courseIntroduce').hide();
	});
</script>