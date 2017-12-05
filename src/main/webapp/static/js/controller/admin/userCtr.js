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
		//学校与系联动
		//首次运行加载
		$http({
			url:'${contextPath}/departmentQuery',
			method:'post',
			data:{ 'schoolId' : $scope.schoolValue },
			/*dataType:'json'*/
			
		}).then(function successCallback(data){
			$scope.schoolList=data;

		},function errorCallback(data){
			alert("数据出错!");
		});
		//后续schoolValue变化数据加载
		$scope.selSchool=function(schoolValue){
			$http({
				url:'${contextPath}/departmentQuery',
				method:'post',
				data:{ 'schoolId' :schoolValue },
				/*dataType:'json'*/
				
			}).then(function successCallback(data){
				console.log(data);
				$scope.schoolList=data;

			},function errorCallback(data){
				alert("数据出错!");
			});
			
			
		};
		/*angular.module('myApp', [])
		.controller('myCtrl', ['$scope','$http', function($scope,$http) {
			$scope.selSchool = function(data) {
				$http({
					url:'${contextPath}/departmentQuery',
					method:'post',
					data:{ 'schoolId' : $scope.schoolValue },
					dataType:'json',
				}).then(function successCallback(jsonString){
					$scope.schoolList=jsonString;
		
				},function errorCallback(jsonString){
					console.log("error2"+jsonString);
					alert("数据出错!");
				});
					
			   };
			}]
		);*/
		
		
		
		
		
	}];
});