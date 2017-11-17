/*
 * @Description：学生端我的试卷angular控制器
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', '$stateParams', function($scope, $state, $stateParams){
		//试卷名称
		$scope.paperName = $stateParams.paperName;
		//状态
		$scope.status = $stateParams.status;
		
		$scope.shouye=function(pageNum){
			$state.go('studentPaperTest',{'pageNum':pageNum});
		};
		$scope.prevPage=function(pageNum){
			$state.go('studentPaperTest',{'pageNum':pageNum});
		};
		$scope.nextPage=function(pageNum){
			$state.go('studentPaperTest',{'pageNum':pageNum});
		};
		$scope.endPage=function(pageNum){
			$state.go('studentPaperTest',{'pageNum':pageNum});
		};
	}];
});