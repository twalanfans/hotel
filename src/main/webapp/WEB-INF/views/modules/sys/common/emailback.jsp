<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<title>邮箱发送中</title>
	</head>
	<body>
		<div class="registSuccess">
			<div class="prompt">
				<div class="promptImg">
					<img src="${ctxStatic}/images/registSuccess.png" width="30" height="30" />
				</div>
				<div class="promptTxt">
					<c:if test="${emailType  eq '1'}">
					激活地址已发送至您的邮箱，请注意查收！
					</c:if>
				</div>
				<div class="promptTxt">
					<c:if test="${emailType ne '1'}">
					<p><font color="red">密码重置地址已发送至您的邮箱，请注意查收！</font></p>
		       		</c:if> 
				</div>
			</div>
		   <c:if test="${emailName=='qq'}">
		 		<div class="turnEmail"><span class="time">4</span>秒后跳转QQ邮箱登录页<span><a href="https://mail.qq.com" >立即跳转</a></span></div>
		   </c:if>
		   <c:if test="${emailName=='126'}">
		 		<div class="turnEmail"><span class="time">4</span>秒后跳转网易126邮箱登录页<span><a href="http://www.126.com" >立即跳转</a></span></div>
		   </c:if>
		   <c:if test="${emailName=='163'}">
		 		<div class="turnEmail"><span class="time">4</span>秒后跳转网易163邮箱登录页<span><a href="http://www.163.com" >立即跳转</a></span></div>
		   </c:if>
		   <c:if test="${emailName=='sohu'}">
		 		<div class="turnEmail"><span class="time">4</span>秒后跳转搜狐邮箱登录页<span><a href="http://mail.sohu.com/fe/#/login" >立即跳转</a></span></div>
		   </c:if>
		   <c:if test="${emailName=='sina'}">
		 		<div class="turnEmail"><span class="time">4</span>秒后跳转搜狐邮箱登录页<span><a href="http://mail.sina.com.cn/" >立即跳转</a></span></div>
		   </c:if>
		   <c:if test="${emailName=='vip'}">
		 		<div class="turnEmail"><span class="time">4</span>秒后跳转VIP163邮箱登录页<span><a href="http://vip.163.com/" >立即跳转</a></span></div>
		   </c:if>
		</div>
	</body>
	<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
	<script type="text/javascript">
		var timer = setInterval(autoTime,1000);
		var $timeTxt = $('.time').text();
		function autoTime(){
			if($timeTxt<=1){
				window.location.href=$('.time').siblings('span').find('a').attr('href');
				clearInterval(timer);
			}
			$timeTxt-=1;
			$('.time').text($timeTxt);
		}
	</script>
</html>
