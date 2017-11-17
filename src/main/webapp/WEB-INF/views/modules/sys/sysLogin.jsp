<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page import="com.common.config.Global" %>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<title>酒店服务管理专业管理系统</title>
		<link rel="shortcut icon" type="${ctxStatic}/image/ico" href="${ctxStatic}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/regist.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css" />
	</head>
	<body>
		<div class="registWrap">
			<h1>用户登录</h1>
			<div class="registContent">
				<div class="logo"><img src="${ctxStatic}/images/login-logo.png" alt="logo" width="118" height="113" /></div>
				<div class="registMessage">
					<form action="${ctx}/login" method="post" autocomplete="off" id="loginForm">
						<input type="text" id="username" name="username" placeholder="手机号码" />
						<input type="password" id="password" name="password" placeholder="密码至少8位数,包含数字+大小写字母+下划线" />
						<c:if test="${isValidateCodeLogin}">
						<div class="validateCode clear">
							<input type="text" name="validateCode" placeholder="请输入验证码" class="validateCodeInpt" />
							<img src="/hotel/servlet/validateCodeServlet" class="validateCode" id="validateCode" title="看不清？换一张" onclick="this.setAttribute('src', '/hotel/servlet/validateCodeServlet?' + new Date().getTime())" />
							<span class="refresh fa fa-refresh" id="refresh" title="看不清？换一张" onclick="document.getElementById('validateCode').setAttribute('src', '/hotel/servlet/validateCodeServlet?' + new Date().getTime())"></span>
						</div>
						</c:if>
						<div class="error alert-error ${empty message ? 'hide' : ''}">
							<label>${message}</label>
						</div>
						<div class="hint">
							<ul class="clear">
								<li>
									<input type="checkbox" id="check">
									<label for="check">记住密码</label>
								</li>
								<li>
									<a href="${contextPath}/forgetPassword">忘记密码？</a>|<a style="cursor:pointer" href="${contextPath}/register">注册</a>
								</li>
							</ul>
						</div>
						<div class="loginBtn" id="loginBtn"><i class="fa fa-check"></i></div>
					</form>
				</div>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript">
	(function(){
		var hotelLogin = {
			//登录按钮
			loginBtn : document.getElementById('loginBtn'),
			
			//doc对象
			doc : document.body || document.documentElement,
			
			//初始化执行
			init : function(){
				this.loginBtn.onclick = this.toLogin;
				this.doc.onkeydown = function(ev){
					var ev = ev || window.event;
					if(ev.keyCode == 13){
						hotelLogin.toLogin();
					}
				}
			},
			
			//登录验证
			toLogin : function(){
				//以下块注释，等项目正式上线的时候再取消
				
				/* var loginName = document.getElementById("username");
				var loginNameVal = loginName.value;
				if(loginName==null || loginNameVal=="" || !(/^((13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8})$/).test(loginNameVal)){
					alert('手机号码格式不正确，请重新输入！');
					return;
				}
				
				var password = document.getElementById("password");
				var passwordVal = password.value;
				if(password==null || passwordVal=="" || !(/(?=.*\d+)(?=.*[A-Z])(?=.*[a-z])(?=.*_).{8,}/).test(passwordVal)){
					alert('密码格式不正确，请重新输入！');
					return;
				} */
				
				document.getElementById("loginForm").submit();
			}
		};
		
		hotelLogin.init();
	})();
</script>