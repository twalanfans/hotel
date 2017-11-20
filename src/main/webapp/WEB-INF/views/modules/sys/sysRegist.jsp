<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!doctype html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<title>酒店服务管理专业管理系统</title>
		<!--css-->
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/regist.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css" />
	</head>
	<body>
		<div class="registWrap">
			<h1>用户注册</h1>
			<div class="registContent">
				<div class="logo"><img src="${ctxStatic}/images/login-logo.png" alt="logo" width="118" height="113" /></div>
				<div class="promptTxt">请填写您的注册信息：</div>
				<div class="registMessage">
					<input type="text" placeholder="姓名(最多5个汉字)" id="userName" maxlength="5"  />
					<input type="text" placeholder="手机号码" id="loginName" />
					<p class="loginNamePrompt hide" id="loginNamePrompt"></p>
					<input type="password" placeholder="密码(最少8位:数字+大小写字母+下划线)" id="password" />
					<input type="password" placeholder="确认密码" id="checkPwd" />
					<select id="sex">
						<option value="2">---请选择性别---</option>
						<option value="1">男</option>
						<option value="0">女</option>
					</select>
					<select id="userType">
						<option value="0">---请选择注册身份---</option>
						<option value="1">学生</option>
						<option value="2">教师</option>
					</select>
					<input type="text" id="chooseDepart" placeholder="请选择所在部门或班级" readonly  />
					<div class="departOption hide">
						<ul class="list">
							<c:forEach items="${departList}"  var="depart1"  varStatus="idxStatus">
							<c:if test="${depart1.parentId eq '1' }">
							<li class="xi">
								<span class="firstFloor">${depart1.departName}</span>
								<ul class="zys">
									<c:forEach items="${departList}"  var="depart2"  varStatus="idxStatus">
									<c:if test="${depart2.parentId == depart1.departId }">
										<li class="zy">
										<span class="secondFloor">${depart2.departName}</span>
										<ul class="bj">
										<c:forEach items="${departList}"  var="depart3"  varStatus="idxStatus">
										<c:if test="${depart3.parentId == depart2.departId }">
											<li class="thirdFloor">
												<input type="radio" name="b"  class="selectSDept" value="${depart3.departId}">
												<span>${depart3.departName}</span>
											</li>
										</c:if>
										</c:forEach>
										</ul>
										</li>
									</c:if>
									</c:forEach>
								</ul>
							</li>
							</c:if>
							</c:forEach>
						</ul>
					</div>
					<div class="departOption hide">
						<ul class="list">
							<c:forEach items="${departList}"  var="depart1"  varStatus="idxStatus">
							<c:if test="${depart1.parentId eq '1' }">
							<li class="xi">
								<input type="radio" name="b"  class="selectTDept xiInpt" value="${depart1.departId}">
								<span>${depart1.departName}</span>
								<ul class="zys">
									<c:forEach items="${departList}"  var="depart2"  varStatus="idxStatus">
									<c:if test="${depart2.parentId == depart1.departId }">
									<li class="zy">
										<input type="radio" name="b"  class="selectTDept zyInpt" value="${depart2.departId}" />
										<span>${depart2.departName}</span>
										<ul class="bj">
										<c:forEach items="${departList}"  var="depart3"  varStatus="idxStatus">
										<c:if test="${depart3.parentId == depart2.departId }">
											<li class="thirdFloor">
												<input type="radio" name="b"  class="selectTDept" value="${depart3.departId}">
												<span>${depart3.departName}</span>
											</li>
										</c:if>
										</c:forEach>
										</ul>
									</li>
									</c:if>
									</c:forEach>
								</ul>
							</li>
							</c:if>
							</c:forEach>
						</ul>
					</div>
					<input type="text" placeholder="邮箱" id="email" />
					<div class="getCode clear">
						<input type="text" placeholder="请填写验证码" id="validateCode" maxlength="6" />
						<input type="button" id="codeBtn" value="获取验证码" />
					</div>
					<div class="registBtn" id="registBtn"><i class="fa fa-check"></i></div>
				</div>
			</div>
		</div>
		<div id="clear" class="hide"></div>
	</body>
</html>
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/regist.js"></script>
<script type="text/javascript">
	//注册配置项
	hotelRegist.init({
		regUserName : /^[\u4e00-\u9fa5]{2,5}$/,//用户名正则
		regLoginName : /^((13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8})$/,//手机号正则
		regPassword : /(?=.*\d+)(?=.*[a-z])(?=.*[A-Z])(?=.*_).{8,}/,//密码正则
		regEmail : /\w+@(qq|163|126|sina|gmail)(\.[A-Za-z])(\.[A-Za-z])?/,//邮箱正则
		checkPhone : '${contextPath}/checkPhone',//检查手机号是否被注册url
		emailValidate : '${contextPath}/emailValidate',//邮箱验证码url
		registerUser : '${contextPath}/registeruser',//提交注册信息url
		toLogin : '${ctx}/login'//注册成功跳转到登录页面url
	});
</script>