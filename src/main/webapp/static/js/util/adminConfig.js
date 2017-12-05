/*
 * @Description：管理员require配置文件
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
		adminRouter : './router/adminRouter',
		departCtr : './controller/admin/departCtr',
		userCtr : './controller/admin/userCtr',
		checkFileCtr : './controller/admin/checkFileCtr',
		fileCtr : './controller/admin/fileCtr',
		questionCtr : './controller/admin/questionCtr',
		commentCtr : './controller/admin/commentCtr',
		recMsgCtr : './controller/admin/recMsgCtr',
		sendMsgCtr : './controller/admin/sendMsgCtr',
		pubNoticeCtr : './controller/admin/pubNoticeCtr'
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

require(['adminRouter'], function(app){
	angular.bootstrap(document, ['app']);
});