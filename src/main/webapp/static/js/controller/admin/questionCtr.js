/*
 * @Description：管理员提问管理angular控制器
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', '$stateParams', function($scope, $state, $stateParams){
		//提问人
		$scope.createUser = $stateParams.createUser;
		
		$scope.shouye=function(pageNum){
			$state.go('questionMana',{'pageNum':pageNum});
		};
		$scope.prevPage=function(pageNum){
			$state.go('questionMana',{'pageNum':pageNum});
		};
		$scope.nextPage=function(pageNum){
			$state.go('questionMana',{'pageNum':pageNum});
		};
		$scope.endPage=function(pageNum){
			$state.go('questionMana',{'pageNum':pageNum});
		};
	}];
});