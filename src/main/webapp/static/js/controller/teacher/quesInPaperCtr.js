/*
 * @Description：教师端查看试卷中的试题angular控制器
 * @Date：2017-6-12
 * @Author：赵一鸣
 * */

define([], function(){
	return ['$scope', '$http', '$stateParams', function($scope, $http, $stateParams){
		//向后端请求数据
		$http({
			url : ctx + '/testonline/questionDetailList?quesId='+$stateParams.quesId,
        	method : 'post'
		}).then(function(result){
			$scope.previewPaper = result.data;
		});
		
		//上下移动
		$scope.move = function(_index, direction){
			if(direction == 'down'){
				$scope.previewPaper[_index+1] = $scope.previewPaper.splice(_index, 1, $scope.previewPaper[_index+1])[0];
			}else if(direction == 'up'){
				$scope.previewPaper[_index-1] = $scope.previewPaper.splice(_index, 1, $scope.previewPaper[_index-1])[0];
			}
		};
	}];
});