<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<title>酒店服务管理专业管理系统</title>
		<!--css-->
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/regist.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css" />
	</head>
	<body>
		<div class="registWrap">
			<h1>找回密码</h1>
			<div class="registContent">
				<div class="logo"><img src="${ctxStatic}/images/login-logo.png" alt="logo" width="118" height="113" /></div>
				<div class="registMessage">
					<input type="text" id="loginName" placeholder="手机号码" />
					<input type="text" id="email" placeholder="邮箱" />
					<div class="getCode clear">
						<input type="text" placeholder="请填写验证码" id="validateCode" maxlength="6" />
						<input type="button" id="codeBtn" value="获取验证码" />
					</div>
					<div class="loginBtn" id="findPwdBtn"><i class="fa fa-check"></i></div>
				</div>
			</div>
		</div>
	</body>
</html>
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/findPwd.js"></script>
<script type="text/javascript">
	findPwd.init({
		regLoginName : /^((13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8})$/,
		regEmail : /\w+@(qq|163|126|sina|google)(\.[A-Za-z])(\.[A-Za-z])?/,
		getCodeUrl : '${contextPath}/emailValidate',
		checkPhoneAndEmail : '${contextPath}/checkPhoneAndEmail',
		resetPwdUrl : '${contextPath}/forgetPassword_2?loginName='
	});
</script>