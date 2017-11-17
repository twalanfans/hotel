/*
 * @Description：教师发件箱angular控制器
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', function($scope, $state){
		$scope.search=function(){
			$state.reload('sendMessage');
		};
		$scope.shouye=function(pageNum){
			$state.go('sendMessage',{'pageNum':pageNum});
		};
		$scope.prevPage=function(pageNum){
			$state.go('sendMessage',{'pageNum':pageNum});
		};
		$scope.nextPage=function(pageNum){
			$state.go('sendMessage',{'pageNum':pageNum});
		};
		$scope.endPage=function(pageNum){
			$state.go('sendMessage',{'pageNum':pageNum});
		};
	}];
});