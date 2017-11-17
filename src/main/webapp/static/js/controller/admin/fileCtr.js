/*
 * @Description：管理员所有课件angular控制器
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', '$stateParams', function($scope, $state, $stateParams){
		//课件名称
		$scope.fileName = $stateParams.fileName;
		
		//审核状态
		$scope.status = $stateParams.status;
		
		$scope.search=function(){
			$state.reload('filemanagement');
		};
		$scope.shouye=function(pageNum){
			$state.go('filemanagement',{'pageNum':pageNum});
		};
		$scope.prevPage=function(pageNum){
			$state.go('filemanagement',{'pageNum':pageNum});
		};
		$scope.nextPage=function(pageNum){
			$state.go('filemanagement',{'pageNum':pageNum});
		};
		$scope.endPage=function(pageNum){
			$state.go('filemanagement',{'pageNum':pageNum});
		};
	}];
});