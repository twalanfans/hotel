<!-- student_mycourse_query.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 栏目说明 -->
<div class="navExplain">
	<span>我的课程</span>
</div>
<!-- 搜索 -->
<div class="searchBeaf" >
	<form method="post" id="testSearchStart">
		<span class="mb8 mr5">
			<label>课程名称</label>
			<input type="text" id="searchCourseName" class="inpt"  value="${courseName}" />
		</span>
		<span class="mb8 mr5">
			<label>主讲教师</label>
			<input type="text" id="searchTeacherName" class="inpt"  value="${teacherName}" />
		</span>
		<span class="mb8">
			<a ui-sref="studentHasCourse({pageNum : 1})" ui-sref-opts="{reload:true}" class="defaultBtn">搜索</a>
		</span>
	</form>
</div>
<!-- 搜索结果 -->
<div class="allTable">
	<table cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<td>课程名称</td>
				<td>教师名称</td>
				<td>创建时间</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageInfo.list}"  var ="courseList">
			<tr>
				<td>
					<a ui-sref="lookCourseFile({courseId : ${courseList.courseId}, courseType : '', pageNum : 1, fileName : ''})" ui-sref-opts="{reload:true}">${courseList.courseName}</a>
				</td>
				<td>${courseList.teacherName}</td>
				<td><fmt:formatDate value="${courseList.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
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
			<a href="javascript:;" ng-click="shouye(1);">首页</a>
			<a href="javascript:;" ng-click="prevPage(${pageInfo.pageNum-1})">上一页</a>
		</c:if>
		<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span>下一页</span><span>尾页</span></c:if>
          	<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
			<a href="javascript:;" ng-click="nextPage(${pageInfo.pageNum+1})">下一页</a>
			<a href="javascript:;" ng-click="endPage(${pageInfo.pages})">尾页</a>
		</c:if>
	</span>
</div>