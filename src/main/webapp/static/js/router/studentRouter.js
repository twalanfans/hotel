/*
 * @Description：学生系统angular路由配置
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define(['app', 'angularAMD', 'truncateFil', 'dateFil'], function(app, angularAMD){
	return app.config(['$urlRouterProvider', '$stateProvider', function($urlRouterProvider, $stateProvider){
		$urlRouterProvider.otherwise('/welcomestudent');
		
		//学生首页
		$stateProvider.state('welcomestudent', angularAMD.route({
			url : '/welcomestudent',
			templateUrl : 'student/defaultIndex',
		}))
		
		//微课学习
		.state('studentCourseStudy', angularAMD.route({
			url : '/studentCourseStudy/{fileId}/{courseNum}/{courseId}',
			templateUrl : function($routeParams){
				return 'course/studentCoursePlay?fileId='+$routeParams.fileId+'&courseNum='+$routeParams.courseNum+'&courseId='+$routeParams.courseId;
			}
		}))
		
		//在线问答
		.state('quesAnswOnline', angularAMD.route({
			url : '/quesAnswOnline/{showNum}/{title}',
			templateUrl:function($routeParams){
				return 'question/allQuestionOnline?showNum='+$routeParams.showNum+'&title='+$routeParams.title;
			},
			controllerUrl : 'quesAnswCtr'
		}))
		
		//考证服务
		.state('criticismService', angularAMD.route({
			url : '/criticismService',
			templateUrl : 'professionExam/criticismService'
		}))
		
		//考证自测
		.state('criticismTest', angularAMD.route({
			url : '/criticismTest',
			templateUrl : 'professionExam/criticismTest'
		}))
		
		//答题自测
		.state('testOnlineRandom', angularAMD.route({
			url : '/testOnlineRandom',
			templateUrl : 'testonline/testOnlineRandom'
		}))
		
		//学生端我的试卷
		.state('studentPaperTest', angularAMD.route({
			url : '/studentPaperTest/{pageNum}/{paperName}/{status}',
			templateUrl : function($routeParams){
				return 'student/studentTestPaperPage?page='+$routeParams.pageNum+'&paperName='+$routeParams.paperName+'&status='+$routeParams.status;
			},
			controllerUrl : 'testPaperCtr'
		}))
		
		//学生错题记录
		.state('studentErrorNotes', angularAMD.route({
			url : '/studentErrorNotes',
			templateUrl:function($routeParams){
				return 'student/myErrorNotesPage';
			},
			controllerUrl : 'errorNotesCtr'
		}))
		
		//学生端收件箱
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
		
		//学生端收件箱
		.state('sendMessage', angularAMD.route({
			url : '/sendMessage/{pageNum}',
			templateUrl : function($routeParams){
				var toUser=$("#searchToUser").val()==undefined ?"":$("#searchToUser").val();
				var subject=$("#searchSubject").val()==undefined ?"":$("#searchSubject").val();
				return 'common/sendMessagePage?page='+$routeParams.pageNum+'&toUser='+toUser+'&subject='+subject;
			},
			controllerUrl : 'sendMsgCtr'
		}))
		
		//学生端我的课程
		.state('studentHasCourse', angularAMD.route({
			url : '/studentHasCourse/{pageNum}',
			templateUrl : function($routeParams){
				var courseName=$("#searchCourseName").val()==undefined ?"":$("#searchCourseName").val();
				var teacherName=$("#searchTeacherName").val()==undefined ?"":$("#searchTeacherName").val();
				return 'student/myCourseListPage?page='+$routeParams.pageNum+'&courseName='+courseName+'&teacherName='+teacherName;
			},
			controllerUrl : 'courseCtr'
		}))
		
		//学生端查看课程详情
		.state('lookCourseFile', angularAMD.route({
			url : '/lookCourseFile/{courseId}/{courseType}/{pageNum}/{fileName}',
			templateUrl : function($routeParams){
				return 'student/courseFileListPage?page='+$routeParams.pageNum+'&courseId='+$routeParams.courseId+'&courseType='+$routeParams.courseType+'&fileName='+$routeParams.fileName;
			},
			controllerUrl : 'courseFileCtr'
		}))
		
		//学生学习计划
		.state('myStudyPlanPage', angularAMD.route({
			url : '/myStudyPlanPage/{pageNum}/{startDate}/{title}',
			templateUrl : function($routeParams){
				return 'student/myStudyPlanPage?planName='+$routeParams.title+'&startDate='+$routeParams.startDate+'&page='+$routeParams.pageNum+'&r='+Math.random();
			},
			controllerUrl : 'myCtr'
		}))
		
		//学习计划详情
		.state('studentEditStudyPlanDetail', angularAMD.route({
			url : '/studentEditStudyPlanDetail/{planId}',
			templateUrl : function($routeParams){
				return 'student/editStudyPlanDetail?planId='+$routeParams.planId+'&r='+Math.random();
			},
			controllerUrl : 'myCtr'
		}))
		
		//添加学习计划
		.state('studentAddStudyPlan', angularAMD.route({
			url : '/studentAddStudyPlan',
			templateUrl : 'student/createStudyPlan',
			controllerUrl : 'myCtr'
		}))
		
		//学生数据分析
		.state('studentDataAnalysis', angularAMD.route({
			url : '/studentDataAnalysis',
			templateUrl : 'ownCenter/dataAnalysis'
		}))
		
		//个人信息修改
		.state('personalEdit', angularAMD.route({
			url : '/personalEdit',
			templateUrl:'ownCenter/editUserDetail',
		}))
		
		//密码修改
		.state('passwordChange', angularAMD.route({
			url : '/passwordChange',
			templateUrl:'ownCenter/updatePassword'
		}))
		
		//学生公告
		.state('receiveNotice', angularAMD.route({
			url : '/receiveNotice/{pageNum}',
			templateUrl : function($routeParams){
				var title=$("#searchTitle").val()==undefined ?"":$("#searchTitle").val();
				var startTime = $("#searchStartTime").val()==undefined ?"":$("#searchStartTime").val();
				var endTime = $("#searchEndTime").val()==undefined ?"":$("#searchEndTime").val();
				return 'common/receiveNoticePage?page='+$routeParams.pageNum+'&status='+status+'&title='+title+'&startTime='+startTime+'&endTime='+endTime;
			},
			controllerUrl : 'pubNoticeCtr'
		}))
	}]);
});