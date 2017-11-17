<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>课程资源授予</title>
	<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">	
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
	<style type="text/css">
		.uploadFileWindow{width:100%;max-width:300px;border:1px solid #ccc;background-color:#fff;display:none;position:fixed;top:32%;left:50%;margin-left:-150px;border-radius:6px;}
		.uploadFileTitle{height:30px;line-height:30px;padding-left:10px;background-color:#76bf64;color:#fff;font-size:12px;border-radius:6px 6px 0 0;}
		.uploadFileContent>p{width:270px;margin:20px auto; font-size:12px;}
		.uploadFileContent>p .searchBtn{margin-left:20px;border-radius:6px;}
		#chooseCourseForm{line-height:40px;}
	</style>
</head>
<body>
<div class="mainBox clearfix">
	<!-- 搜索条件 -->
	<div class="searchBeaf">
		<form id="chooseCourseForm">
			<lable for="courseId">当前课程：</lable>
			<select name="courseId" id="courseId" class="inpt" onchange="chooseCourse();">
				<option value="">—请选择—</option>
				<c:forEach var="courseList" items="${courseList}" >
				<option value="${courseList.courseId}">${courseList.courseName}</option>
				</c:forEach>
			</select>
			<a href="javascript:;" class="defaultBtn" onclick="revokeCourse();">撤回资源</a>
			<a href="javascript:;" class="defaultBtn" onclick="relationNewGroup();">授予新群组</a>
		</form>
	</div>
	<div class="allTable">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td><input type="checkbox" id="selallbtn" style="display:none"><label for="selallbtn">全选&nbsp;&nbsp;</label></td>
					<td>群组名称</td>
					<td>创建时间</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${groupList}"  var ="groupList">
					<tr>
						<td><input type="checkbox" class="selrolebtn" name="roleId" value="${groupList.roleId}"></td>
						<td>${groupList.roleName}</td>
						<td><fmt:formatDate value="${groupList.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
					</tr>
				</c:forEach>
			</tbody>
	 	</table>
 	</div>
</div>
<div class="uploadFileWindow">
	<div class="uploadFileTitle">群组选择</div>
	<div class="uploadFileContent">
		<div id="newGroup"></div>
		<p>
			<a href="javascript:;" class="commonBtn" onclick="confirmSave();">确认授予</a>
			<a href="javascript:;" class="commonBtn" onclick="closeWin();">取消</a>
		</p>
	</div>
</div>
</body>
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript">
	$("#courseId option[value='${courseId}']").attr("selected","selected");
	
	//全选/取消
	$('#selallbtn').on('click',function(){
		if($('#selallbtn').prop('checked')){
			$('.selrolebtn').prop('checked',true);
		}else{
			$('.selrolebtn').prop('checked',false);
		}
	});
	
	//撤回课程资源
	function revokeCourse(){
		var roleId = [];
		$('input[name="roleId"]:checked').each(function(){ 
			roleId.push($(this).val());
		});  
		if(roleId.length<1){
			alert("请选取要撤回课程资源的群组！");
			retur;
		}	
		$.ajax({
			type:'post',
			url:'${contextPath}/role/deleteRoleResource',
			data:'resourceId=${resourceId}'+'&roleId='+roleId,
			dataType:'json',
			success:function(str){
				if(str=="success"){
					chooseCourse();
				}else{
					alert("撤回失败！");
				}
			}
		});
	}
	
	//获取群组
	function relationNewGroup(){
		if("${courseId}"==""){
			alert("请先选择课程！");
			return;
		}
		if("${resourceId}"==""){
			$.ajax({
				type:'post',
				url:'${contextPath}/resource/inertCourseResource',
				data:'courseId=${courseId}',
				dataType:'json',
				success:function(str){
					if(str !="error"){
						ajax(str);
					}else{
						alert("获取新群组失败！");
					}
				}
			});
		}else{
			ajax("${resourceId}");
		}
	}
	
	function ajax(resourceId){
		$.ajax({
			type:'post',
			url:'${contextPath}/resource/queryNewGroup',
			data:'resourceId='+resourceId,
			dataType:'json',
			success:function(str){
				if(str !="error"){
					var html="";
					if(str.length>0){
						for(var i=0; i<str.length; i++){
							html+="</br><p>&nbsp;&nbsp;<input type=\"checkbox\" name=\"newRoleId\" value=\""+str[i].roleId+"\"/>"+str[i].roleName+"</p>";
						}
					}else{
						html="</br><p>&nbsp;&nbsp;当前没有新的群组了！</p>";
					}
					document.getElementById("newGroup").innerHTML=html;
					$(".uploadFileWindow").fadeIn();
				}else{
					alert("获取新群组失败！");
				}
			}
		});
	}
	
	//确认授予
	function confirmSave(){
		var roleId = [];
		$('input[name="newRoleId"]:checked').each(function(){ 
			roleId.push($(this).val());
		});
		if(roleId.length<1){
			alert("请选取要授予课程资源的群组！");
			return;
		}
		$.ajax({
			type:'post',
			url:'${contextPath}/role/insertResourceToRole',
			data:'resourceId=${resourceId}'+'&roleId='+roleId,
			dataType:'json',
			success:function(str){
				if(str=="success"){
					chooseCourse();
				}else{
					alert("群组课程资源授予失败！");
				}
			}
		});
	}
	
	//取消授予
	function closeWin(){
		$(".uploadFileWindow").fadeOut();
	}
	
	//选择课程
	function chooseCourse(){
		document.getElementById("chooseCourseForm").action="${contextPath}/role/haveRelationGroup";
		document.getElementById("chooseCourseForm").submit();
	}
</script>
</html>