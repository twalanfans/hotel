/*
 * @Description：教师require配置文件
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
		teacherRouter : './router/teacherRouter',
		myCtr : './controller/teacher/myCtr',
		myFileCtr : './controller/teacher/myFileCtr',
		quesAnswCtr : './controller/teacher/quesAnswCtr',
		myPaperListCtr : './controller/teacher/myPaperListCtr',
		paperGradeCtr : './controller/teacher/paperGradeCtr',
		courseFileCtr : './controller/teacher/courseFileCtr',
		recMsgCtr : './controller/teacher/recMsgCtr',
		sendMsgCtr : './controller/teacher/sendMsgCtr',
		pubNoticeCtr : './controller/teacher/pubNoticeCtr',
		teacherQuesCtr : './controller/teacher/teacherQuesCtr',
		quesInPaperCtr : './controller/teacher/quesInPaperCtr'
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

require(['teacherRouter'], function(app){
	angular.bootstrap(document, ['app']);
});
