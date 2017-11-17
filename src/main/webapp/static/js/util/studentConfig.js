/*
 * @Description：学生require配置文件
 * @Date：2017-6-5
 * @Author：赵一鸣
 * */

require.config({
	baseUrl : publicPath + './',
	paths : {
		angular : './angular.min',
		uiRouter : './angular-ui-router',
		angularAMD : './angularAMD',
		app : './util/app',
		truncateFil : './customFilter/truncateFil',
		dateFil : './customFilter/dateFil',
		studentRouter : './router/studentRouter',
		quesAnswCtr : './controller/student/quesAnswCtr',
		testPaperCtr : './controller/student/testPaperCtr',
		errorNotesCtr : './controller/student/errorNotesCtr',
		recMsgCtr : './controller/student/recMsgCtr',
		sendMsgCtr : './controller/student/sendMsgCtr',
		courseCtr : './controller/student/courseCtr',
		courseFileCtr : './controller/student/courseFileCtr',
		myCtr : './controller/student/myCtr',
		pubNoticeCtr : './controller/student/pubNoticeCtr'
	},
	shim : {
		angular : {
			exports : 'angular'
		},
		angularAMD : {
		    deps : ['angular']
        },
		uiRouter : {
			exports : 'ui-router',
			deps : ['angularAMD']
		},
		app : {
			deps : ['uiRouter']
		}
	}
});

require(['studentRouter'], function(app){
	angular.bootstrap(document, ['app']);
});