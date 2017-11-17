/*
 * @Description：管理员班级管理angular控制器
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', '$templateCache', function($scope, $state, $templateCache){
		$scope.refresh = function(){
			$templateCache.removeAll();
			$state.reload('department');
		};
	}];
});