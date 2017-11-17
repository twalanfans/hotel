/*
 * @Description：教师公共资源查看课程详情
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', function($scope, $state){
		//课程
		$scope.chooseCourse=function(courseId){
			$state.go('fetchCourseFile', {pageNum : 1, courseId : courseId, fileName : ''});
		};
		
		//课程资源
		$scope.chooseCourseType=function(courseType){
			$state.go('fetchCourseFile', {pageNum : 1, courseType : courseType, fileName : ''});
		};
		
		//课件搜索
		$scope.searchFile=function(courseId,courseType){
			var fileName = document.getElementById("fileName").value;
			$state.go('fetchCourseFile', {courseId : courseId, courseType : courseType, fileName : fileName});
		};
		
		//根据试题类型搜索
		$scope.searchQuestion=function(courseId,courseType){
			var questionType = document.getElementById("questionType").value;
			$state.go('fetchCourseFile', {courseId : courseId, courseType : courseType, questionType : questionType});
		};
		
		$scope.shouye=function(pageNum){
			$state.go('fetchCourseFile',{'pageNum':pageNum});
		};
		$scope.prevPage=function(pageNum){
			$state.go('fetchCourseFile',{'pageNum':pageNum});
		};
		$scope.nextPage=function(pageNum){
			$state.go('fetchCourseFile',{'pageNum':pageNum});
		};
		$scope.endPage=function(pageNum){
			$state.go('fetchCourseFile',{'pageNum':pageNum});
		};
	}];
});