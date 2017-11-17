<!-- course_resource_query.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="testSearchResult teacherCourse" >
		<form action="" method="post" id="testSearchStart" >
			<div class="condition">
				<span>&nbsp;&nbsp;课程名称</span>
				<input type="text" id="courseName" name="courseName" value="${courseName}">
			</div>
			<div class="condition">
				<span>主讲教师</span>
				<input type="text" id="teacherName" name="teacherName" value="${teacherName}">
			</div>
			<input type="button"  onclick="search();" value="搜索"  class="messagesearchbtn" >
		</form><br/><br/>
		<input type="button" value="确认添加" onclick="addCourse();" class="addBtutton">
		<!-- </div> -->
		<div class="tablebox">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td><label for="selallbtn">全选&nbsp;&nbsp;</label><input type="checkbox" id="selallbtn" style="display:none"></td>
					<td>课程名称</td>
					<td>课程简介</td>
					<td>主讲教师</td>
					<td>创建时间</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${courseList.dataRows}"  var ="courseList">
				<tr>
					<td><input type="checkbox" class="selrolebtn" name="courseId" value="${courseList.courseId}"></td>
					<td class="courseName">${courseList.courseName}</td>
					<c:if test="${courseList.introduce==null || courseList.introduce==''}"><td>——</td></c:if>
					<c:if test="${courseList.introduce!=null && courseList.introduce!=''}"><td>${courseList.introduce}</td></c:if>
					<td>${courseList.teacherName}</td>
					<td>${courseList.createTime}</td>
				</tr>
			</c:forEach>
			</tbody>
		 </table>
		 </div>
	</div>
		<div class="pageBarbox clearfix">
			<p class="otherInfo">
				共&nbsp;${courseList.records}&nbsp;条&nbsp;&nbsp;每页&nbsp;${courseList.rows}&nbsp;条&nbsp;&nbsp;当前第&nbsp;${courseList.page}&nbsp;页
			</p>
			<div class="epages clearfix">
            	<c:if test="${courseList.page == 1 || courseList.total == 0}">
	            	<a href="javascript:;">首页</a>
	            	<a href="javascript:;">上一页</a>
            	</c:if>
            	<c:if test="${courseList.page != 1 && courseList.total > 1}">
            	<a href="#"  onclick="javascript:pageTurn( '${ctx}/resource/insertResourcePage?page=1&courseName=${courseName}&teacherName=${teacherName}&isShow=1');">首页</a>
            	<a href="#"  onclick="javascript:pageTurn( '${ctx}/resource/insertResourcePage?page=${courseList.page-1}&courseName=${courseName}&teacherName=${teacherName}&isShow=1');">上一页</a>
            	</c:if>
            	<c:if test="${courseList.page == courseList.total || courseList.total == 0}">
	            	<a href="javascript:;">下一页</a>
	            	<a href="javascript:;">尾页</a>
            	</c:if>
            	<c:if test="${courseList.page != courseList.total && courseList.total != 0}">
            	<a href="#" onclick="javascript:pageTurn( '${ctx}/resource/insertResourcePage?page=${courseList.page+1}&courseName=${courseName}&teacherName=${teacherName}&isShow=1');">下一页</a>
            	<a href="#" onclick="javascript:pageTurn( '${ctx}/resource/insertResourcePage?page=${courseList.total}&courseName=${courseName}&teacherName=${teacherName}&isShow=1');">尾页</a>
            	</c:if>
            </div>
		</div>
<script>
		function addCourse(){
			var courseId =[]; 
			$('input[name="courseId"]:checked').each(function(){ 
				courseId.push($(this).val()); 
			});  
			if(courseId.length<1){
				alert("请至少选择一个课程！");
				return 
			}
			var courseName = [];
			$('input[name="courseId"]:checked').each(function(){ 
				courseName.push($(this).parent().siblings('.courseName').html());
			}); 
			$('.addCourseId').val(courseId);
			$('.addCourseName').val(courseName);
			$('.showCourse').hide();
			$('.showOther').hide();
		}
		
		function pageTurn(url){
			$(".showCourse").load(url);
		}
		/*---查询--*/
		function search(){
			var courseName = document.getElementById("courseName").value;
			var teacherName = document.getElementById("teacherName").value;
			$(".showCourse").load("${ctx}/resource/insertResourcePage?courseName="+courseName+"&teacherName="+teacherName+"&isShow=1");
		}
		//-----全选/取消
		$('#selallbtn').on('click',function(){
			if($('#selallbtn').prop('checked')){
				$('.selrolebtn').prop('checked',true);
			}else{
				$('.selrolebtn').prop('checked',false);
			}
		});
</script>