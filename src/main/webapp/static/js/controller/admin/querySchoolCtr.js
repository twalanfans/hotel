/*
 * @Description：发件箱angular控制器
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', function($scope, $state){
		$scope.shouye=function(pageNum){
			$state.go('schoolManageQuery',{'pageNum':pageNum});
		};
		$scope.prevPage=function(pageNum){
			$state.go('schoolManageQuery',{'pageNum':pageNum});
		};
		$scope.nextPage=function(pageNum){
			$state.go('schoolManageQuery',{'pageNum':pageNum});
		};
		$scope.endPage=function(pageNum){
			$state.go('schoolManageQuery',{'pageNum':pageNum});
		};
		$scope.pageTurn=function(pageNum){
			$state.go('queryCodeListPage',{'pageNum':pageNum});
		};
	}];
});