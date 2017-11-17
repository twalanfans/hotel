<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page import= "com.module.sys.utils.DictionUtils"%> 
<%@ page import= "com.module.sys.entity.Dictionary"%> 
<style type="text/css">
	.editCourseBox{width:270px;padding:10px;z-index:9999;position:fixed;display:none;background-color:#fff;margin-left:-145px;left:50%;top:70px;}
	.editTitle{height:30px;line-height:30px;}
	.editContent p{margin-bottom:10px;}
	@media only screen and (min-width:769px){
		.editCourseBox{top:15%;left:50%;}
	}
</style>
<div class="testSearch mlDistance ml10 clearfix">
	<!-- 栏目说明 -->
	<div class="navExplain">
		<span>我的课程</span>
	</div>
	<!-- 搜索条件 -->
	<div class="searchCondition clearfix searchBeaf" >
			<span class="mb8 mr5">
				<label for='searchTitle'>课程名称：</label>
				<input type="text" id="courseName" class="inpt" name="courseName" value="${courseName}">
			</span>
			<span class="mb8 mr5">
				<a ui-sref="teacherCourse({pageNum : 1})" ui-sref-opts="{reload:true}" class="defaultBtn">搜索</a>
			</span>
			<span class="mb8">
				<a href="javascript:;" id="teacherAddCourse" class="defaultBtn">添加课程</a>
			</span>
	</div>
	<!-- 搜索结果 -->
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
			<c:forEach items="${courseList.dataRows}"  var = "course">
				<tr>
					<td class="teacherCourseList">
						<a ui-sref="lookMyCourseFile({pageNum : 1, courseId : ${course.courseId}, courseType : 1, fileName : ''})" ui-sref-opts="{reload:true}">${course.courseName}</a>
					</td>
					<td><fmt:formatDate value="${course.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
					<td>
						<a href="javascript:;" onclick="teacherCourseEdit(${course.courseId})">编辑</a>
						<a href="javascript:;" onclick="deleteCourse(${course.courseId})">删除</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- 翻页 -->
	<div class="pageList">
		<span>共<b>${courseList.records}</b>条</span>
		<span>每页<b>${courseList.rows}</b>条</span>
		<span>当前第<b>${courseList.page}</b>页</span>
		<span>
			<c:if test="${courseList.page == 1 || courseList.total == 0}"><span>首页</span><span>上一页</span></c:if>
			<c:if test="${courseList.page != 1 && courseList.total > 1}">
			<a href="javascript:;" ng-click="shouye(1);">首页</a>
			<a href="javascript:;" ng-click="prevPage(${courseList.page-1})">上一页</a>
			</c:if>
			<c:if test="${courseList.page == courseList.total || courseList.total == 0}"><span>下一页</span><span>尾页</span></c:if>
			<c:if test="${courseList.page != courseList.total && courseList.total != 0}">
			<a href="javascript:;" ng-click="nextPage(${courseList.page+1})">下一页</a>
			<a href="javascript:;" ng-click="endPage(${courseList.total})">尾页</a>
			</c:if>
		</span>
	</div>
</div>
<!-- 遮罩层 -->
<div class="clear"></div>
<!-- 编辑框 -->
<div class="editCourseBox" hidden="true">
	<div class="editTitle"><h4>编辑课程</h4></div>
	<div class="editContent">
		<!--课程名称--> 
		<p class="kcmc startEdit">
			<span>课程名称</span>
			<input type="text" name="kcmc" id="kcmc" class="inpt" />
		</p>
		<!--课程简介-->
		<p class="kcjj clearfix">
			<span style="float:left;line-height:100px;">课程简介</span>
			<textarea name="kcjj" id="kcjj" class="inpt" style="height:100px;"></textarea>
		</p>
		<!--是否公开此课程-->
		<p class="subject">
			<span>是否公开</span>
			<span>
				<select id="isCommon" class="inpt">
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
			</span>
		</p>
		<!--保存或取消-->
		<div class="tck clearfix">
			<span>
				<a href="javascript:;" class="qr commonBtn">确认</a>
			</span>
			<span>
				<a href="javascript:;" class="bc commonBtn">保存</a>
			</span>
			<span>
				<a href="javascript:;" class="qx commonBtn">取消</a>
			</span>
		</div>
	</div>
</div> 
<!-- 用户操作提示框 -->
<div class="promptBox">
	<div class="promptTxt">
		<p>提示</p>
		<p>
			<i class="fa fa-exclamation-circle fl" style="margin-right:5px;"></i>
			<span>删除将不可恢复，是否确定删除？</span>
		</p>
	</div>
	<div class="promptBtn">
		<a href="javascript:;" id="cancelBtn" class="defaultBtn">取消</a>
		<a href="javascript:;" id="confirmBtn" class="commonBtn">确认</a>
	</div>
</div>
<script type="text/javascript">
	//多次用到的全局变量
	var teacherCourseVariable = {
		$clear : $(".clear"),
		$editCourseBox : $(".editCourseBox"),
		$qr : $('.qr'),
		$bc : $(".bc"),
		$kcmc : $('#kcmc'),
		$kcjj : $('#kcjj'),
		$newSubjectWindow : $(".newSubjectWindow")
	};

	//删除所选课程
	var courseIds = "";
	function deleteCourse(courseId){
		courseIds = courseId;
		$('.promptBox').fadeIn();
	}
	
	//确认删除
	$('#confirmBtn').on('click',function(){
		var $prompt = $('.promptTxt p:eq(1) span'),
			$promptBox = $('.promptBox');
		$.ajax({
			url  : "${contextPath}/course/deleteCourse",
			data : JSON.stringify({
				"courseId" : courseIds//课程ID
			}),
			async : true,
			type : "POST",
			contentType: "application/json; charset=utf-8",
			success : function(str){
				if(str=="success"){
					$prompt.html('操作成功！');
					$promptBox.fadeOut();
					window.location.reload();
				}else{
					$prompt.html('操作失败！');
					$promptBox.fadeOut();
				}
			}
		});
	})
	
	//取消删除
	$('#cancelBtn').click(function(){
		$('.promptBox').fadeOut();
	});
		
	//编辑功能		
	var courseNames ="";
	function teacherCourseEdit(courseId){
		teacherCourseVariable.$clear.show();
		teacherCourseVariable.$editCourseBox.show();
		teacherCourseVariable.$qr.show();
		teacherCourseVariable.$bc.hide();
		courseIds = courseId;
		$.ajax({
			url : "${contextPath}/course/courseDetail",
			data : JSON.stringify({
				"courseId" : courseId//课程ID
			}),
			type : "POST",
			contentType: "application/json; charset=utf-8",
			async : true,
			success : function(message){
				teacherCourseVariable.$kcmc.val(message.success.courseName);
				courseNames = message.success.courseName;
				$("#subject option").eq(message.success.subjectId).attr("selected","selected");
				$("#isCommon option").eq(message.success.isCommon).attr("selected","selected");
				teacherCourseVariable.$kcjj.val(message.success.introduce);
			},
			error : function(errorMessage){
				alert(errorMessage);
			}
		});
	}
	
	//确认编辑
	teacherCourseVariable.$qr.click(function(){
		qrtj("${contextPath}/course/updateCourse");
	});
	
	//添加课程
	$("#teacherAddCourse").click(function(){
		teacherCourseVariable.$clear.show();
		teacherCourseVariable.$editCourseBox.show();
		teacherCourseVariable.$qr.hide();
		teacherCourseVariable.$bc.show();
		teacherCourseVariable.$kcmc.val("");
		teacherCourseVariable.$kcjj.val("");
	})
	
	//取消按钮
	$(".qx").click(function(){
		teacherCourseVariable.$editCourseBox.hide();
		teacherCourseVariable.$clear.hide();
	})
	
	//添加课程确认保存
	teacherCourseVariable.$bc.click(function(){
		qrtj("${ctx}/course/createCourse");
	});
	
	//ajax向后台传输确认添加的课程内容
	function qrtj($url){
		var courseName = teacherCourseVariable.$kcmc.val(),
			courseIntroduce = teacherCourseVariable.$kcjj.val();
		if(courseName==""||courseName==null){
			alert("课程名称不能为空！");
			return;
		}
		if(courseNames == courseName){ //	如果课程名称没做更改
			courseName = "";
		}
		if(courseIntroduce==""||courseIntroduce==null){
			alert("请填写课程简介！");
			return;
		}
		var isCommon = $('#isCommon').val();
		$('.actbtn').hide();
		$('.actbtn1').show();
		$.ajax({
			url : $url,
			type : "POST",
			async : true,
			contentType: "application/json; charset=utf-8",
			data : JSON.stringify({
				"courseId" : courseIds,
				"courseName" : courseName,
				"isCommon" : isCommon,
				"introduce" : courseIntroduce
			}),
			datatype : "json",
			success : function(message){
				if(message=="success"){
					alert('保存成功');
					window.location.reload();
				}else if(message=="exist"){
					alert('保存失败,该课程名称已存在！');
				}else{
					alert('保存失败');
				}
			}
		});
		teacherCourseVariable.$editCourseBox.hide();
		teacherCourseVariable.$clear.hide();
	}
</script>