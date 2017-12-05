<!-- teacher_comm_course.jsp -->
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 栏目说明 -->
<div class="navExplain">
	<span>公共资源</span>
</div>
<!-- 搜索 -->
<div class="searchBeaf">
	<span class="mb8 mr5">
		<label>课程名称</label>
		<input type="text" name="courseName" value="${courseName}" id="courseName" class="inpt" />
	</span>
	<span class="mb8">
		<a href="javascript:;" ng-click="searchComC();" class="defaultBtn">搜索</a>
	</span>
</div>
<!-- 搜索结果 -->
<div class="allTable">
	<table cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<td>课程名称</td>
				<td>所属教师</td>
				<td>创建时间</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pageInfo.list}"  var = "course">
			<tr>
				<td class="teacherCourseList">
					<a ui-sref="fetchCourseFile({'pageNum' : 1 , 'courseId' : ${course.courseId} , 'courseType' : '0' ,'fileName' : '' , 'questionType' : ''})">${course.courseName}</a>
				</td>
				<c:if test="${course.teacherName==null || course.teacherName==''}">
					<td>系统预装</td>
				</c:if>
				<c:if test="${course.teacherName!=null && course.teacherName!=''}">
					<td>${course.teacherName}</td>
				</c:if>
				<td><fmt:formatDate value="${course.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
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
       	<a ui-sref="fetchCommonCourseList({courseName : this.courseName, pageNum : '1'})">首页</a>
       	<a ui-sref="fetchCommonCourseList({courseName : this.courseName, pageNum : ${pageInfo.pageNum-1}})">上一页</a>
       	</c:if>
       	<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span>下一页</span><span>尾页</span></c:if>
       	<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
       	<a ui-sref="fetchCommonCourseList({courseName : this.courseName, pageNum : ${pageInfo.pageNum+1}})">下一页</a>
       	<a ui-sref="fetchCommonCourseList({courseName : this.courseName, pageNum : ${pageInfo.pages}})">尾页</a>
       	</c:if>
	</span>
</div>