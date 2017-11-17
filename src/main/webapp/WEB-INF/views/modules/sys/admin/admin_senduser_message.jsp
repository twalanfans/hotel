<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<!--tkd-->
		<title>发送短消息</title>
		<meta name="Keywords" content="">
		<meta name="Description" content="">
		<!--css-->
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/admin/admin_senduser_message.css" />
	</head>
	<body>
		<div class="sentmessagebox admin_sentuser_message">
			<div class="searchBeaf">
				<form action="${ctx}/message/sendMessagePage" id="Search" method="post">
					<span class="mb8 mr5">
						<label>系：</label>
						<input type="text" name="departName" value="${departName}" class="inpt" />
					</span>
					<span class="mb8 mr5">
						<label>专业：</label>
						<input type="text" name="professionName" value="${professionName}" class="inpt" />
					</span>
					<span class="mb8 mr5">
						<label>班级：</label>
						<input  type="text" name="gradeName" value="${gradeName}" class="inpt" />
					</span>
					<span class="mb8 mr5">
						<label>用户名：</label>
						<input type="text" name="userName" value="${userName}" class="inpt" />
					</span>
					<span class="mb8 mr5">
						<label for="userType">用户身份:</label>
						<select id="userType" name="userType" class="inpt">
							<option value="">——请选择——</opstion>
							<option value="0">管理员</option>
							<option value="1">学生</option>
							<option value="2">教师</option>
						</select>
					</span>
					<span class="mb8 mr5">
						<input type="submit" value="搜索" style="border:color:#333;padding:8px 16px;cursor:pointer;background-color:#f7f7f7;border:1px solid #ddd;font-size:12px;" />
					</span>
					<span class="mb8">
						<a href="javascript:;" class="adduserlistbtn defaultBtn">给下列所选用户发送短消息</a>
					</span>
				</form>
			</div>
			<div class="allTable">
				<table cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<td>
								<label for="selallbtn"><input type="checkbox" id="selallbtn" style="display:none;">全选</label>
								
							</td>
							<td>用户名</td>
							<td>用户身份</td>
							<td>系</td>
							<td>专业</td>
							<td>班级</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${userList.dataRows}"  var="userList" >
						<tr>
							<td>
								<input type="checkbox" class="selrolebtn" name="userId" value="${userList.userId}">
							</td>
							<td class="userName">${userList.userName}</td>
							<c:if test="${userList.userType=='0' }"><td>管理员</td></c:if>
							<c:if test="${userList.userType=='1' }"><td>学生</td></c:if>
							<c:if test="${userList.userType=='2' }"><td>老师</td></c:if>
							<td>${userList.departName}</td>
							<td>${userList.professionName}</td>
							<td>${userList.gradeName}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- 翻页 -->
			<div class="pageList" ng-controller="sendMsgController">
				<span>共<b>${userList.records}</b>条</span>
				<span>每页<b>${userList.rows}</b>条</span>
				<span>当前第<b>${userList.page}</b>页</span>
				<span>
					<c:if test="${userList.page == 1 || userList.total == 0}">
	            		<a href="javascript:;">首页</a>
						<a href="javascript:;">上一页</a>
	            	</c:if>
	            	<c:if test="${userList.page != 1 && userList.total > 1}">
		            	<a href="#" onclick="javascript:window.location.href='${ctx}/message/sendMessagePage?page=1&departName=${departName}&gradeName=${gradeName}&userName=${userName}&userType=${userType}'">首页</a>
		            	<a href="#" onclick="javascript:window.location.href='${ctx}/message/sendMessagePage?page=${userList.page-1}&departName=${departName}&gradeName=${gradeName}&userName=${userName}&userType=${userType}'">上一页</a>
	            	</c:if>
	            	<c:if test="${userList.page == userList.total || userList.total == 0}">
			            <a href=javascript:;>下一页</a>
						<a href="javascript:;">尾页</a>
	            	</c:if>
	            	<c:if test="${userList.page != userList.total && userList.total != 0}">
		            	<a href="#" onclick="javascript:window.location.href='${ctx}/message/sendMessagePage?page=${userList.page+1}&departName=${departName}&gradeName=${gradeName}&userName=${userName}&userType=${userType}'">下一页</a>
		            	<a href="#" onclick="javascript:window.location.href='${ctx}/message/sendMessagePage?page=${userList.total}&departName=${departName}&gradeName=${gradeName}&userName=${userName}&userType=${userType}'">尾页</a>
	            	</c:if>
				</span>
			</div>
			<div class="shadow"></div>
			<div class="formBox">
				<h4 class="sentmessagehead clearfix">发送新消息:</h4>
				<form class="sentmessage" action="#" method="post" autocomplete="off">
					<p>
						<label>收件人：</label>
						<input type="text" class="emailUserId" value="" style="display:none"/>
						<input type="text" class="email" value="" readonly />			
					</p>
					<p>
						<label for="messagetit">标&nbsp;&nbsp;&nbsp;&nbsp;题：</label>
						<input type="text" value=""  id="title" class="inpt" placeholder="标题不能为空" maxlength="50" />
					</p>
					<p>
						<label for="messagecontent" class="sendMessageContent">正&nbsp;&nbsp;&nbsp;&nbsp;文：</label>
						<textarea class="messagecontent inpt" id="messagecontent" name="notices" placeholder="不少于10个字" maxlength="500"></textarea>
					</p>
					<a href="javascript:;" class="sentbtn3 commonBtn">提交</a>
					<a href="javascript:;" class="cancelSentBtn3 commonBtn">取消</a>
				</form>
			</div>
		</div>
		<!-- 遮罩层 -->
		<div class="clear"></div>
		<!-- js -->
		<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
		<script type="text/javascript">
			//添加收件人
			$('.adduserlistbtn').on('click',function(){
				var userId = [];
				$('input[name="userId"]:checked').each(function(){ 
					userId.push($(this).val()); 
				});  
				if(userId.length<1){
					alert("请至少选择一位用户！");
					return;
				}
				var userName = [];
				$('input[name="userId"]:checked').each(function(){ 
					userName.push($(this).parent().siblings('.userName').html());
				});  
				$('.emailUserId').val(userId);
				$('.email').val(userName);
				$('.formBox,.clear').show();
			});
			
			//提交功能
			$('.sentbtn3').on('click',function(){
				var userId = $('.emailUserId').val();
				var title = document.getElementById("title").value;
				if(title==null || title==""){
					alert("请填写标题！");
					return;		
				}
				var messagecontent = document.getElementById("messagecontent").value;
				if(messagecontent==null || messagecontent==""){
					alert("请填写正文！");
					return;			
				}
				$.ajax({
					type:'post',
					url:'${contextPath}/message/sendToUser',
					data:"userId="+userId+"&title="+title+"&messagecontent="+messagecontent, 
					dataType:'json',
					success:function(data){
						if(data=="success"){
							alert('消息发送成功！');
							$('.formBox,.clear').hide();
							window.opener.location.reload();
						}else{
							alert('消息发送失败，请重新操作！');
						}
					}
				})
			});
			
			//取消提交
			$('.cancelSentBtn3').click(function(){
				if(confirm('确认取消发送？')){
					$('.formBox,.clear').hide();
				}
			});
			
			//关闭发送短消息窗口
			$('.closebtn1').on('click',function(){
				$('.shadow').fadeOut();
			});
			
			//全选/取消
			$('#selallbtn').on('click',function(){
				if($('#selallbtn').prop('checked')){
					$('.selrolebtn').prop('checked',true);
				}else{
					$('.selrolebtn').prop('checked',false);
				}
			});
			
			//输入字数限制
			checkTextNum($('.messagecontent'));
			function checkTextNum(obj){
				obj.on('keyup',function(){
					var oText=$(this).val();
					if(oText.length<=500){
						$(this).siblings('.errortext').html('剩余输入'+(500-oText.length)+'个字！');
						$(this).siblings('.errortext').css({'color':'red','size':'6'});
					}
					else if(oText.length>=500){
						$(this).val(oText);
					}
				});	
			};
		</script>
	</body>
</html>