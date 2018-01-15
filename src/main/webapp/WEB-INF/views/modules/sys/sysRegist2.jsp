<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!doctype html>
<html lang="en" >
	<head>
		<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<title>酒店服务管理专业管理系统</title>
		<!--css-->
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/regist.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css" />
	</head>
	<body ng-controller="userCtr">
		<div class="registWrap">
			<h1>用户注册</h1>
			<div class="registContent">
				<div class="logo"><img src="${ctxStatic}/images/login-logo.png" alt="logo" width="300"  /></div>
				<div class="promptTxt">请填写您的注册信息：</div>
				<div class="registMessage">
					<input type="text" placeholder="姓名(最多5个汉字)" id="userName" maxlength="5"  autocomplete="off"/>
					<input type="text" placeholder="手机号码" id="loginName" autocomplete="off"/>
					<p class="loginNamePrompt hide" id="loginNamePrompt"></p>
					<input type="password" placeholder="密码(最少8位:数字+大小写字母+下划线)" id="password" autocomplete="off"/>
					<input type="password" placeholder="确认密码" id="checkPwd" autocomplete="off"/>
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
					
    				<div>
					<select id="school" class="regSchool"  ng-change="selSchool()" ng-model="schoolValue">
						<c:forEach items="${school}"  var="school"  varStatus="idxStatus">
							<option value="${school.schoolId}" >${school.schoolName}</option>
    					</c:forEach>
					</select>
   					</div>
   					
					<input type="text" id="chooseDepart" placeholder="请选择所在部门或班级" readonly  />
					<div class="departOption hide">
						<ul class="list">
							<li class="xi" ng-repeat="xiItem in schoolList" title="{{xiItem.parentId}}" ng-if="xiItem.parentId==1">
								<span class="firstFloor">{{xiItem.departName}}</span>
								<ul class="zys">
									<li class="zy" ng-repeat="zysItem in xiItem.department" ng-if="zysItem.parentId==xiItem.departId">
										<span class="secondFloor">{{zysItem.departName}}</span>
										<ul class="bj">
											<li class="thirdFloor" ng-repeat="bjItem in zysItem.department" ng-if="bjItem.parentId==zysItem.departId">
												<input type="radio" name="b"  class="selectSDept" value="{{bjItem.departId}}">
												<span>{{bjItem.departName}}</span>
											</li>
										</ul>
									</li>
								</ul>
							</li>
						</ul>
					</div>
					<div class="departOption hide">
						<ul class="list">
							<li class="xi" ng-repeat="xiItem in schoolList" title="{{xiItem.parentId}}" ng-if="xiItem.parentId==1">
								<input type="radio" name="b"  class="selectTDept xiInpt" value="{{xiItem.departId}}">
								<span>{{xiItem.departName}}</span>
								<ul class="zys">
									<li class="zy" ng-repeat="zysItem in xiItem.department" ng-if="zysItem.parentId==xiItem.departId">
										<input type="radio" name="b"  class="selectTDept zyInpt" value="{{zysItem.departId}}" />
										<span>{{zysItem.departName}}</span>
										<ul class="bj">
											<li class="thirdFloor" ng-repeat="bjItem in zysItem.department" ng-if="bjItem.parentId==zysItem.departId">
												<input type="radio" name="b"  class="selectTDept" value="{{bjItem.departId}}">
												<span>{{bjItem.departName}}</span>
											</li>
										</ul>
									</li>
								</ul>
							</li>
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
	regPassword : /(?=.*\d+)(?=.*[a-z])(?=.*[A-Z])(?=.*).{8,}/,//密码正则
	regEmail : /\w+@(qq|163|126|sina|gmail)(\.[A-Za-z])(\.[A-Za-z])?/,//邮箱正则
	checkPhone : '${contextPath}/checkPhone',//检查手机号是否被注册url
	//emailValidate : '${contextPath}/emailValidate',//邮箱验证码url
	SmsValidate : '${contextPath}/SmsValidate',//手机验证码url
	registerUser : '${contextPath}/registeruser',//提交注册信息url
	regSchoolId : $('.regSchool').val(),	//   var leve = $("#ddlRegType ").val();
	toLogin : '${ctx}/login'//注册成功跳转到登录页面url
	
});
	var publicPath = '${ctxStatic}/js/';
</script>
<%-- <script type="text/javascript" src="${ctxStatic}/js/require.js" data-main="${ctxStatic}/js/util/adminConfig.js"></script> --%>
<script type="text/javascript" src="${ctxStatic}/js/angular.min.js"></script>
<script type="text/javascript">
var userRegist=angular.module('userRegist', []);
userRegist.controller('userCtr', ['$scope','$http', function($scope,$http) {
	//第一次访问
	console.log($scope.schoolValue);
	$http({
			url:'${contextPath}/departmentQuery',
			method:'post',
			params:{ 'schoolId' : $scope.schoolValue },
			dataType:'json',
		}).then(function successCallback(data){
			console.log("data------"+data.data);
			$scope.schoolList=data.data;
	
		},function errorCallback(data){
			console.log("error2"+data.data);
			alert("数据出错!");
	}); 
	//学校ID改变访问
	$scope.selSchool = function(schoolValue) {
		console.log($scope.schoolValue);
		$http({
			url:'${contextPath}/departmentQuery',
			method:'post',
			params:{ 'schoolId' : $scope.schoolValue },
			dataType:'json',
		}).then(function successCallback(data){
			console.log(data.data);
			$scope.schoolList=data.data;

		},function errorCallback(data){
			console.log("error2"+data.data);
			alert("数据出错!");
		});
	 };
}]);
/**
 *测试用json数据 
 [
    {
        "departId": "53",
        "departName": "清华园",
        "parentId": "1",
        "level": "1",
        "zyValue": [
            {
                "departId": "57",
                "departName": "清华园1",
                "parentId": "53",
                "level": "2",
                "bjValue": [
                    {
                        "departId": "99",
                        "departName": "清华园12",
                        "level": "3",
                        "parentId": "57"
                    },
                    {
                        "departId": "98",
                        "departName": "清华园13",
                        "level": "3",
                        "parentId": "57"
                    },
                    {
                        "departId": "98",
                        "departName": "清华园14",
                        "level": "3",
                        "parentId": "57"
                    }
                ]
            },
            {
                "departId": "58",
                "departName": "清华园2",
                "level": "1",
                "parentId": "53",
                "bjValue": [
                    {
                        "departId": "97",
                        "departName": "清华园22",
                        "level": "3",
                        "parentId": "58"
                    },
                    {
                        "departId": "96",
                        "departName": "清华园23",
                        "level": "3",
                        "parentId": "58"
                    }
                ]
            }
        ]
    }
]
 */
angular.bootstrap(document, ['userRegist']); 
</script>
