/*
 * @Description：教师系统angular路由配置
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define(['app', 'angularAMD', 'truncateFil', 'dateFil'], function(app, angularAMD){
	return app.config(['$urlRouterProvider', '$stateProvider', function($urlRouterProvider, $stateProvider){
		$urlRouterProvider.otherwise('/welcometeacher');
		
		//教师首页
		$stateProvider.state('welcometeacher', angularAMD.route({
			url : '/welcometeacher',
			templateUrl : 'default/teacherWec'
		}))
		
		//教师首页点击课程查看相关资源
		.state('fetchCourseFileListPage', angularAMD.route({
			url : '/fetchCourseFileListPage/{courseId}/{courseName}/{title}/{tqType}/{courseType}/{pageNum}',
			templateUrl:function($routeParams){
				return 'commonResource/fetchCourseFileListPage?page='+$routeParams.pageNum+'&courseId='+$routeParams.courseId+'&title='+$routeParams.title+'&tqType='+$routeParams.tqType+'&courseName='+$routeParams.courseName+'&courseType='+$routeParams.courseType;
			},
			controllerUrl : 'myCtr'
		}))
		
		//课程资料
		.state('teacherCourse', angularAMD.route({
			url : '/teacherCourse/{pageNum}',
			templateUrl : function($routeParams){
				var courseName=$("#courseName").val()==undefined ?"":$("#courseName").val();
				return 'course/teacherCoursePage?courseName='+courseName+'&page='+$routeParams.pageNum;
			},
			controllerUrl : 'myCtr'
		}))
		
		//教师课程详情
		.state('lookMyCourseFile', angularAMD.route({
			url : '/lookMyCourseFile/{pageNum}/{courseId}/{courseType}/{fileName}',
			templateUrl:function($routeParams){
				return 'uploadFile/queryCourseFilePage?page='+$routeParams.pageNum+'&courseId='+$routeParams.courseId+'&courseType='+$routeParams.courseType+'&fileName='+$routeParams.fileName;
			},
			controllerUrl : 'myFileCtr'
		}))
		
		//知识点管理
		.state('zsdEdit', angularAMD.route({
			url : '/zsdEdit',
			templateUrl : 'uploadFile/uploadKnowledgeFile'
		}))
		
		//我的试题
		.state('ceshiQuestion', angularAMD.route({
			url : '/ceshiQuestion',
			templateUrl : function(){
				var courseName=$("#searchCourseName").val()==undefined ?"":$("#searchCourseName").val();
				var knowledgeId=$("#searchKnowledgeId").val()==undefined ?"":$("#searchKnowledgeId").val();
				var knowledge=$("#knowledge").val()==undefined ?"":$("#knowledge").val();
				return 'testonline/ceshiQuestion?courseName='+courseName+'&knowledgeId='+knowledgeId+'&knowledge='+knowledge;
			},
			controllerUrl : 'teacherQuesCtr'
		}))
		
		//查看试卷中的试题
		.state('quesInPaper', angularAMD.route({
			url : '/quesInPaper/{quesId}',
			templateUrl : function(){
				return 'testonline/queryChooseQuestion';
			},
			controllerUrl : 'quesInPaperCtr'
		}))
		
		//在线解答
		.state('quesAnswOnline', angularAMD.route({
			url : '/quesAnswOnline/{showNum}/{title}',
			templateUrl:function($routeParams){
				return 'question/allQuestionOnline?showNum='+$routeParams.showNum+'&title='+$routeParams.title;
			},
			controllerUrl : 'quesAnswCtr'
		}))
		
		//我的试卷
		.state('makeTestPaper', angularAMD.route({
			url : '/makeTestPaper/{pageNum}',
			templateUrl:function($routeParams){
				var paperName=$("#searchPaperName").val() == undefined ? "" : $("#searchPaperName").val();
				return 'testonline/makeTestPaperPage?paperName='+paperName+'&page='+$routeParams.pageNum;
			},
			controllerUrl : 'myPaperListCtr'
		}))
		
		//试卷判分
		.state('teacherPaperGrade', angularAMD.route({
			url : '/teacherPaperGrade/{pageNum}/{studentName}/{status}',
			templateUrl:function($routeParams){
				return 'testonline/testPaperGradePage?studentName='+$routeParams.studentName+'&status='+$routeParams.status+'&page='+$routeParams.pageNum;
			},
			controllerUrl : 'paperGradeCtr'
		}))
		
		//公共资源
		.state('fetchCommonCourseList', angularAMD.route({
			url : '/fetchCommonCourseList/{courseName}/{pageNum}',
			templateUrl:function($routeParams){
				return 'commResourse/teacherCoursePage?page='+$routeParams.pageNum+'&courseName='+$routeParams.courseName;
			},
			controllerUrl : 'myCtr'
		}))
		
		//公共资源中查看课程详情
		.state('fetchCourseFile', angularAMD.route({
			url : '/fetchCourseFile/{pageNum}/{courseId}/{courseType}/{fileName}/{questionType}',
			templateUrl : function($routeParams){
				return 'commonResource/fetchCourseFilePage?page='+$routeParams.pageNum+'&courseId='+$routeParams.courseId+'&courseType='+$routeParams.courseType+'&fileName='+$routeParams.fileName+'&questionType='+$routeParams.questionType;
			},
			controllerUrl : 'courseFileCtr'
		}))
		
		//职业资格考试
		.state('criticismService', angularAMD.route({
			url:'/criticismService',
			templateUrl : 'professionExam/criticismService'
		}))
		
		//教师数据分析
		.state('teacherDataAnalysis', angularAMD.route({
			url : '/teacherDataAnalysis',
			templateUrl : 'user/dataAnalysis'
		}))
		
		//群组管理
		.state('studentGroup', angularAMD.route({
			url : '/studentGroup',
			templateUrl : 'ownCenter/studentGroupManage'
		}))
		
		//个人信息修改
		.state('personalEdit', angularAMD.route({
			url : '/personalEdit',
			templateUrl : 'ownCenter/editUserDetail'
		}))
		
		//密码修改
		.state('passwordChange', angularAMD.route({
			url : '/passwordChange',
			templateUrl : 'ownCenter/updatePassword'
		}))
		
		//收件箱
		.state('receiveMessage', angularAMD.route({
			url:'/receiveMessage/{pageNum}',
			templateUrl:function($routeParams){
				var fromUser=$("#searchFromUser").val()==undefined ?"":$("#searchFromUser").val();
				var subject=$("#searchSubject").val()==undefined ?"":$("#searchSubject").val();
				var isRead=$("#searchIsRead").val()==undefined ?"":$("#searchIsRead").val();
				return 'common/receiveMessagePage?page='+$routeParams.pageNum+'&fromUser='+fromUser+'&subject='+subject+'&isRead='+isRead;
			},
			controllerUrl : 'recMsgCtr'
		}))
		
		//收件箱
		.state('sendMessage', angularAMD.route({
			url : '/sendMessage/{pageNum}',
			templateUrl:function($routeParams){
				var toUser=$("#searchToUser").val()==undefined ?"":$("#searchToUser").val();
				var subject=$("#searchSubject").val()==undefined ?"":$("#searchSubject").val();
				return 'common/sendMessagePage?page='+$routeParams.pageNum+'&toUser='+toUser+'&subject='+subject;
			},
			controllerUrl : 'sendMsgCtr'
		}))
		
		//教师端公告
		.state('receiveNotice', angularAMD.route({
			url : '/receiveNotice/{pageNum}',
			templateUrl:function($routeParams){
				var title=$("#searchTitle").val()==undefined ?"":$("#searchTitle").val();
				var startTime = $("#searchStartTime").val()==undefined ?"":$("#searchStartTime").val();
				var endTime = $("#searchEndTime").val()==undefined ?"":$("#searchEndTime").val();
				return 'common/receiveNoticePage?page='+$routeParams.pageNum+'&status='+status+'&title='+title+'&startTime='+startTime+'&endTime='+endTime;
			},
			controllerUrl : 'pubNoticeCtr'
		}))
	}]);
});