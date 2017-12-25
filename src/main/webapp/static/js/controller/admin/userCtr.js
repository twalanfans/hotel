/*
 * @Description：用户管理angular控制器
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', '$stateParams','$http', function($scope, $state, $stateParams,$http){
		//用户名称
		$scope.userName = $stateParams.userName;
		
		//登录帐号（手机号）
		$scope.loginName = $stateParams.loginName;
		
		//用户类型
		$scope.userType = $stateParams.userType;
		
		//创建用户
		$scope.insertUser = function(){
			$state.go('createUser');
		};
		
		$scope.shouye = function(pageNum){
			$state.go('usermanagement', {'pageNum' : pageNum});
		};
		$scope.prevPage=function(pageNum){
			$state.go('usermanagement', {'pageNum' : pageNum});
		};
		$scope.nextPage=function(pageNum){
			$state.go('usermanagement', {'pageNum' : pageNum});
		};
		$scope.endPage=function(pageNum){
			$state.go('usermanagement', {'pageNum' : pageNum});
		};
		
	}];
});