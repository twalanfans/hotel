/*
 * @Description：教师课程详情angular控制器
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', '$stateParams', function($scope, $state, $stateParams){
		//课程ID
		$scope.courseId = $stateParams.courseId;
		
		//资源类型
		$scope.courseType = $stateParams.courseType;
		
		//课件名称
		$scope.fileName = '';
		
		//选择课程
		$scope.chooseCourse=function(courseId){
			$state.go('lookMyCourseFile',{'pageNum' : 1, 'courseId' : courseId, 'courseType' : $scope.courseType, 'fileName' : ''});
		};
		
		//选择资源类型
		$scope.chooseCourseType=function(courseType){
			$state.go('lookMyCourseFile',{'pageNum' : 1, 'courseId' : $scope.courseId, 'courseType' : courseType, 'fileName' : ''});
		};
		
		$scope.shouye=function(pageNum){
			$state.go('lookMyCourseFile',{'pageNum':pageNum});
		};
		$scope.prevPage=function(pageNum){
			$state.go('lookMyCourseFile',{'pageNum':pageNum});
		};
		$scope.nextPage=function(pageNum){
			$state.go('lookMyCourseFile',{'pageNum':pageNum});
		};
		$scope.endPage=function(pageNum){
			$state.go('lookMyCourseFile',{'pageNum':pageNum});
		};
	}];
});