/*
 * @Description：管理员系统angular路由配置
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define(['app', 'angularAMD'], function(app, angularAMD){
	return app.config(['$urlRouterProvider', '$stateProvider', function($urlRouterProvider, $stateProvider){
		$urlRouterProvider.otherwise('/home');
		
		//管理员首页
		$stateProvider.state('home', angularAMD.route({
			url : '/home',
			templateUrl : 'user/adminDefaultIndex'
		}))
		
		//系、专业、班级管理
		.state('department', angularAMD.route({
			url : '/department',
			templateUrl : function(){
				return 'depart/showAllDepart';
			},
			controllerUrl : 'departCtr'
		}))
		
		//用户管理
		.state('usermanagement', angularAMD.route({
			url : '/usermanagement/{pageNum}/{userName}/{loginName}/{userType}',
			templateUrl : function($routeParams){
				return 'user/showAllUserPage?page='+$routeParams.pageNum+'&userName='+$routeParams.userName+'&loginName='+$routeParams.loginName+'&userType='+$routeParams.userType;
			},
			controllerUrl : 'userCtr'
		}))
		
		//创建用户
		.state('createUser', angularAMD.route({
			url : '/createUser',
			templateUrl:'user/createNewUser',
		}))
		
		//课件审核
		.state('reviewattachment', angularAMD.route({
			url : '/reviewattachment/{pageNum}/{status}/{fileName}',
			templateUrl : function($routeParams){
				return 'checker/queryCheckFilePage?page='+$routeParams.pageNum+'&fileName='+$routeParams.fileName+'&status='+$routeParams.status;
			},
			controllerUrl : 'checkFileCtr'
		}))
		
		//所有课件
		.state('filemanagement', angularAMD.route({
			url : '/filemanagement/{pageNum}/{fileName}/{status}',
			templateUrl : function($routeParams){
				return 'course/showAllCourseFilePage?page='+$routeParams.pageNum+'&fileName='+$routeParams.fileName+'&status='+$routeParams.status;
			},
			controllerUrl : 'fileCtr'
		}))
		
		//提问管理
		.state('questionMana', angularAMD.route({
			url : '/questionMana/{pageNum}/{createUser}',
			templateUrl:function($routeParams){
				return 'question/questionOnlinePage?page='+$routeParams.pageNum+'&createUser='+$routeParams.createUser;
			},
			controllerUrl : 'questionCtr'
		}))
		
		//评论管理
		.state('commentMana', angularAMD.route({
			url : '/commentMana/{pageNum}',
			templateUrl:function($routeParams){
				var createUserVal = $("#createUser").val();
				var contentVal = $("#content").val();
				var createUser = createUserVal == undefined ? "" : createUserVal;
				var content = contentVal == undefined ? "" : contentVal;
				return 'question/commentOnlinePage?page='+$routeParams.pageNum+'&createUser='+createUser+'&content='+content;
			},
			controllerUrl : 'commentCtr'
		}))
		
		//收件箱
		.state('receiveMessage', angularAMD.route({
			url : '/receiveMessage/{pageNum}',
			templateUrl:function($routeParams){
				var fromUser=$("#searchFromUser").val()==undefined ?"":$("#searchFromUser").val();
				var subject=$("#searchSubject").val()==undefined ?"":$("#searchSubject").val();
				var isRead=$("#searchIsRead").val()==undefined ?"":$("#searchIsRead").val();
				return 'common/receiveMessagePage?page='+$routeParams.pageNum+'&fromUser='+fromUser+'&subject='+subject+'&isRead='+isRead;
			},
			controllerUrl : 'recMsgCtr'
		}))
		
		
		//发件箱
		.state('sendMessage', angularAMD.route({
			url : '/sendMessage/{pageNum}',
			templateUrl : function($routeParams){
				var toUser=$("#searchToUser").val()==undefined ?"":$("#searchToUser").val();
				var subject=$("#searchSubject").val()==undefined ?"":$("#searchSubject").val();
				return 'common/sendMessagePage?page='+$routeParams.pageNum+'&toUser='+toUser+'&subject='+subject;
			},
			controllerUrl : 'sendMsgCtr'
		}))
		
		//发布公告
		.state('receiveNotice', angularAMD.route({
			url : '/receiveNotice/{pageNum}',
			templateUrl:function($routeParams){
				var title=$("#searchTitle").val()==undefined ?"":$("#searchTitle").val();
				var status=$("#searchStatus").val()==undefined ?"":$("#searchStatus").val();
				var startTime = $("#searchStartTime").val()==undefined ?"":$("#searchStartTime").val();
				var endTime = $("#searchEndTime").val()==undefined ?"":$("#searchEndTime").val();
				return 'notice/pubNoticePage?page='+$routeParams.pageNum+'&status='+status+'&title='+title+'&startTime='+startTime+'&endTime='+endTime;
			},
			controllerUrl : 'pubNoticeCtr'
		}))
		
		//激活码需求
		//学校列表
		.state('schoolManageQuery',angularAMD.route({
			url:'/schoolManageQuery/{pageNum}',
			templateUrl : function($routeParams){
				var pageInfo=$("#searchPageInfo").val()==undefined ?"":$("#searchPageInfo").val();
				var schoolName=$("#searchSchoolName").val()==undefined ?"":$("#searchSchoolName").val();
				return 'school/querySchoolPage?page='+$routeParams.pageNum+'&pageInfo='+pageInfo+'&schoolName='+schoolName;
			},
			controllerUrl : 'sendMsgCtr'
			})
		)
		//激活码生成
		.state('queryCodeListPage',angularAMD.route({
			url:'/queryCodeListPage/{pageNum}',
			templateUrl : function($routeParams){
				var keyId=$("#searchkeyId").val()==undefined ?"":$("#searchkeyId").val();
				var useNum=$("#searchuseNum").val()==undefined ?"":$("#searchuseNum").val();
				return 'code/queryCodeListPage?page='+$routeParams.pageNum+'&keyId='+keyId+'&useNum='+useNum;
			},
			controllerUrl : 'sendMsgCtr'
			})
		)
		//学籍号
		.state('queryStudentCodeListPage',angularAMD.route({
			url:'/queryStudentCodeListPage/{pageNum}',
			templateUrl : function($routeParams){
				var keyId=$("#searchkeyId").val()==undefined ?"":$("#searchkeyId").val();
				var useNum=$("#searchuseNum").val()==undefined ?"":$("#searchuseNum").val();
				return 'code/queryStudentCodeListPage?page='+$routeParams.pageNum+'&keyId='+keyId+'&useNum='+useNum;
			},
			controllerUrl : 'sendMsgCtr'
			})
		)
		//管理员列表
		/*.state('queryAdminCode',angularAMD.route({
			url:'/queryAdminCode/{pageNum}',
			templateUrl : function($routeParams){
				var pageInfo=$("#searchPageInfo").val()==undefined ?"":$("#searchPageInfo").val();
				var schoolName=$("#searchSchoolName").val()==undefined ?"":$("#searchSchoolName").val();
				return 'common/querySchoolPage?page='+$routeParams.pageNum+'&pageInfo='+pageInfo+'&schoolName='+schoolName;
			},
			controllerUrl : 'sendMsgCtr'
			})
		)*/
		
		
		/*//激活码翻页
		.state('passwordChange', angularAMD.route({
			url : '/passwordChange',
			templateUrl : 'ownCenter/updatePassword'
		}))
		document.getElementById("codeForm").action="${ctx}/code/queryCodeListPage?page="+pageNum;
		document.getElementById("codeForm").submit();*/
		
		
		//个人信息编辑
		.state('personalEdit', angularAMD.route({
			url : '/personalEdit',
			templateUrl : 'ownCenter/editUserDetail'
		}))
		
		//修改密码
		.state('passwordChange', angularAMD.route({
			url : '/passwordChange',
			templateUrl : 'ownCenter/updatePassword'
		}))
		
		
		
		
		
	}]);
});