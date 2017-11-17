/*
 * @Description：学生端我的课程中查看课程详情angular控制器
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', function($scope, $state){
		$scope.chooseCourse=function(courseId){
			$state.go('lookCourseFile',{courseId : courseId, fileName : ''});
		};
		$scope.chooseCourseType=function(courseType){
			$state.go('lookCourseFile',{'courseType':courseType});
		};
		$scope.playVideo=function(fileId){
			$state.go('studentCourseStudy',{'fileId':fileId});
		};
		$scope.searchFile=function(courseId,courseType){
			var fileName = document.getElementById("fileName").value;
			$state.go('lookCourseFile',{'courseId':courseId,'courseType':courseType,'fileName':fileName});
		};
		$scope.shouye=function(pageNum){
			$state.go('lookCourseFile',{'pageNum':pageNum});
		};
		$scope.prevPage=function(pageNum){
			$state.go('lookCourseFile',{'pageNum':pageNum});
		};
		$scope.nextPage=function(pageNum){
			$state.go('lookCourseFile',{'pageNum':pageNum});
		};
		$scope.endPage=function(pageNum){
			$state.go('lookCourseFile',{'pageNum':pageNum});
		};
	}];
});