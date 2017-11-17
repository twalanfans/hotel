/*
 * @Description：截取字符串angular控制器
 * @Date：2017-6-12
 * @Author：赵一鸣
 * */

define(['app'], function(app){
	return app.filter('truncateFil', function(){
		return function(str, len){
			return str.length > 12 ? String(str).substr(0, len) + new String('.').repeat(3) : str;
		};
	});
});