<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<title>北京五中教学管理系统</title>
		<!--css-->
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/regist.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css" />
	</head>
	<body>
		<div class="registWrap">
			<h1>密码重置</h1>
			<div class="registContent">
				<div class="logo"><img src="${ctxStatic}/images/login-logo.png" alt="logo" width="118" height="113" /></div>
				<div class="registMessage">
					<input type="password" id="password" placeholder="密码(最少8位:数字+大小写字母+下划线)" />
					<input type="password" id="pwdConfirm" placeholder="确认密码" />
					<div class="loginBtn" id="findPwdBtn"><i class="fa fa-check"></i></div>
				</div>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript">
	(function(){
		var resetPwd = {
			//密码
			password : document.getElementById('password'),
			
			//确认密码
			pwdConfirm : document.getElementById('pwdConfirm'),
			
			//找回密码按钮
			findPwdBtn : document.getElementById('findPwdBtn'),
			
			//密码正则
			regPwd : /(?=.*\d+)(?=.*[a-z])(?=.*[A-Z])(?=.*_).{8,}/,
			
			//初始化执行
			init : function(){
				this.toResetPwd();
			},
			
			//确认重置
			toResetPwd : function(){
				this.findPwdBtn.onclick = function(){
					if(!(this.regPwd).test(this.password.value)){
						alert('密码必须最少8位，且同时包含数字+大小写字母+下划线');
						return false;
					}
					if(this.password.value != this.pwdConfirm.value){
						alert('两次输入的密码不一致，请重新输入！');
						return false;
					}
					$.ajax({
						url : "${contextPath}/forSubmit",
						data : "loginName=" + window.localStorage.loginName + "&password=" + resetPwd.password.value,
						type : "POST",
						success : function(data){
							if(data == "1"){
								alert("修改密码成功！");
								window.localStorage.loginName = '';
								window.location.href = "${ctx}/login"; 
							} else{
								alert("修改密码失败！");
							}
						}
				 	});
				}.bind(resetPwd);
			}
		};
		
		resetPwd.init();
	})();
</script>