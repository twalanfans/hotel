/*
 * @Description：教师端课程资料angular控制器
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', '$stateParams', function($scope, $state, $stateParams){
		//课程名称
		$scope.courseName = $stateParams.courseName;
		$scope.shouye=function(pageNum){
			$state.go('teacherCourse',{'pageNum':pageNum});
		};
		$scope.prevPage=function(pageNum){
			$state.go('teacherCourse',{'pageNum':pageNum});
		};
		$scope.nextPage=function(pageNum){
			$state.go('teacherCourse',{'pageNum':pageNum});
		};
		$scope.endPage=function(pageNum){
			$state.go('teacherCourse',{'pageNum':pageNum});
		};
		$scope.fetchCourseFile=function(courseId){
			$state.go('fetchCourseFile',{'courseId':courseId,'courseType':'1'});
		};
		$scope.searchComC=function(){
			var courseName = document.getElementById('courseName').value;
			$state.go('fetchCommonCourseList',{'courseName':courseName});
		};
		$scope.fetchMyCourseFileListPage = function(courseId){
			$state.go('lookMyCourseFile', {pageNum : 1, courseId : courseId, courseType : 1});
		}
	}];
});