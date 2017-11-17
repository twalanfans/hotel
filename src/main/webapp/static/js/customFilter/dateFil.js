/*
 * @Description：时间格式规范angular控制器
 * @Date：2017-6-12
 * @Author：赵一鸣
 * */

define(['app'], function(app){
	return app.filter('dateFil', function(){
		return function(str){
			var date = new Date(str);
			return date.getFullYear() + '-' + (date.getMonth()+1) + '-' + date.getDate();
		};
	});
});