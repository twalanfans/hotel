/*
 * @Description：在线解答angular控制器
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', function($scope, $state){
		$scope.loadMore=function(showNum){
			$state.go('quesAnswOnline',{'showNum':showNum});
		};
		$scope.queryQuestion=function(showNum){
			var title = document.getElementById("title").value;
			$state.go('quesAnswOnline',{'showNum':showNum,'title':title});
		};
	}];
});