/*
 * @Description：教师我的试卷angular控制器
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', '$templateCache', function($scope, $state, $templateCache){
		$templateCache.removeAll();
		$scope.search=function(){
			$state.reload('makeTestPaper');
		};
		$scope.shouye=function(pageNum){
			$state.go('makeTestPaper',{'pageNum':pageNum});
		};
		$scope.prevPage=function(pageNum){
			$state.go('makeTestPaper',{'pageNum':pageNum});
		};
		$scope.nextPage=function(pageNum){
			$state.go('makeTestPaper',{'pageNum':pageNum});
		};
		$scope.endPage=function(pageNum){
			$state.go('makeTestPaper',{'pageNum':pageNum});
		};
	}];
});