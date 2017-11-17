<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<title>组卷下发学生</title>
	    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
	    <style type="text/css">
	    	.sendType{padding:10px 0 0 10px;}
			.sendOption{width:47%;line-height:30px;padding:0 6px;margin-bottom:10px;display:inline-block;background-color:#6bc5a4;color:#fff;text-align:center;}
			.userSend.on,.groupSend.on{background-color:#2ca6f3;}
			.groupUser{padding:0 0 0 15px;}
			.groupUser ul li{height:30px;line-height:30px;border-bottom:1px dashed #ccc;}
			.confirmSendPaper{text-align:center;}
	    </style>
	</head>
<body>
	<div>
		<div class="sendType clearfix">
			<a href="javascript:;" class="userSend sendOption on">发送给具体的学生</a>
			<a href="javascript:;" class="groupSend sendOption" >发送给群组中的学生</a>
		</div>
		<div class="testSearchResult teacherCourse">
			<div class="clearfix searchBeaf" >
				<form action="${contextPath}/testonline/querySendPaperUser" method="post" id="testSearchStart" >
					<span class="mb8 mr5">
						<label>专业名称</label>
						<input type="text" name="professionName" value="${professionName}" class="inpt" />
					</span>
					<span class="mb8 mr5">
						<label>班级名称</label>
						<input type="text" name="gradeName" value="${gradeName}" class="inpt" />
					</span>
					<span class="mb8 mr5">
						<label>学生名称</label>
						<input type="text" name="userName" value="${userName}" class="inpt" />
					</span>
					<input type="submit" value="查询" class="addBtutton defaultBtn" style="border:none;"  />
				</form>
			</div>
			<div class="tablebox allTable">
				<table cellpadding="0" cellspacing="0" >
					<thead>
						<tr>
							<td>
								<input type="checkbox" id="selallbtn" />
								<label for="selallbtn">全选</label>
							</td>
							<td>专业</td>
							<td>班级</td>
							<td>用户名</td>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${userList}"  var ="userList">
						<tr>
							<td><input type="checkbox" class="selrolebtn" name="userId" value="${userList.userId}"></td>
							<td>${userList.professionName}</td>
							<td>${userList.gradeName}</td>
							<td>${userList.userName}</td>
						</tr>
					</c:forEach>
					</tbody>
				 </table>
			</div>
			<a href="javascript:;" onclick="sendToUser();" class="commonBtn" style="float:left;">确认发送</a>
		</div>
		<div class="groupUser" style="display:none">
			<c:if test="${roleNum>0}">
				<ul>
					<c:forEach items="${roleList}" var="roleList">
						<li>
							<input type="radio" name="roleId" value="${roleList.roleId}" />
							<label for="roleId">${roleList.name}</label></li>
						</c:forEach>
					</ul>
					<a href="javascript:;" onclick="sendToGroup();" class="commonBtn" style="float:left;">确认发送</a>
				</c:if>
				<c:if test="${roleNum==0}">
					<span>您还没有创建任何群组，您可以去"群组学生管理"栏添加！</span>
				</c:if>
		</div>
		<a href="javascript:;" id="close" class="commonBtn">关闭</a>
	</div>
</body>
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/sonWindow.js"></script>
<script type="text/javascript">
	$(".userSend").click(function(){
		$('.sendType a').removeClass('on');
		$(this).addClass('on');
		$(".testSearchResult").show();
		$(".groupUser").hide();
	})
	$(".groupSend").click(function(){
		$('.sendType a').removeClass('on');
		$(this).addClass('on');
		$(".testSearchResult").hide();
		$(".groupUser").show();
	})
	//全选/取消
	$('#selallbtn').on('click',function(){
		if($('#selallbtn').prop('checked')){
			$('.selrolebtn').prop('checked',true);
		}else{
			$('.selrolebtn').prop('checked',false);
		}
	});
	//确认发送
	function sendToUser(){
		var userId = [];
		$('input[name="userId"]:checked').each(function(){ 
			userId.push($(this).val());
		});  
		if(userId.length<1){
			alert("您还未选择试卷下发的学生！");
			return;
		}	
		$.ajax({
			type:'post',
			url:'${contextPath}/testonline/sendPaperToStudent',
			data:'userId='+userId+'&paperId=${paperId}',
			dataType:'json',
			success:function(str){
				if(str.status=="success"){
					if(str.repeatUser!="" && str.repeatUser!=null){
						alert("试卷下发成功,其中<"+str.repeatUser+">下发过该试卷，已为你跳过这些用户！");
					}else{
						alert("试卷下发成功！");
					}
					window.close();
				}else if(str.status=="noUser"){
					alert('当前群组下还未添加学生用户！');
				}else{
					alert("试卷下发失败！");
				}
			}
		});
	}
	
	function sendToGroup(){
		var roleId = $('input[name="roleId"]:checked').val();
		if(roleId=="" || roleId==null){
			alert("请选择要下发试卷的群组！");
			return;
		}	
		$.ajax({
			type:'post',
			url:'${contextPath}/testonline/sendPaperToStudent',
			data:'roleId='+roleId+'&paperId=${paperId}',
			dataType:'json',
			success:function(str){
				if(str.status=="success"){
					if(str.repeatUser!="" && str.repeatUser!=null){
						alert("试卷下发成功,其中<"+str.repeatUser+">下发过该试卷，已为你跳过这些用户！");
					}else{
						alert("试卷下发成功！");
					}
					window.close();
				}else if(str.status=="noUser"){
					alert('当前群组下还未添加学生用户！');
				}else{
					alert("试卷下发失败！");
				}
			}
		});
	}

	//返回上一页，并关闭弹窗
	$('.confirmbtn').on('click',function(){
		window.close();
	});
	//监听window页面是否关闭
	window.onbeforeunload = function(){
		window.opener.document.getElementsByClassName('clear')[0].style.display = 'none';
	}

	document.onkeydown = function(event){
		if(event.keyCode==116){          
             event.keyCode = 0;          
             event.cancelBubble = true;          
           	 return false;          
        }          
	}
</script>
</html>
