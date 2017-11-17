/*
 * @Description：学生端我的课程angualr控制器
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', function($scope, $state){
		$scope.shouye=function(pageNum){
			$state.go('studentHasCourse',{'pageNum':pageNum});
		};
		$scope.prevPage=function(pageNum){
			$state.go('studentHasCourse',{'pageNum':pageNum});
		};
		$scope.nextPage=function(pageNum){
			$state.go('studentHasCourse',{'pageNum':pageNum});
		};
		$scope.endPage=function(pageNum){
			$state.go('studentHasCourse',{'pageNum':pageNum});
		};
	}];
});