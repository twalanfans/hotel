/*
 * @Description：教师试卷判分angular控制器
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', '$stateParams', function($scope, $state, $stateParams){
		//学生名称
		$scope.studentName = '';
		//试卷状态值
		$scope.status = $stateParams.status;
		
		$scope.search=function(){
			$state.reload('teacherPaperGrade');
		};
		$scope.shouye=function(pageNum){
			$state.go('teacherPaperGrade',{'pageNum':pageNum});
		};
		$scope.prevPage=function(pageNum){
			$state.go('teacherPaperGrade',{'pageNum':pageNum});
		};
		$scope.nextPage=function(pageNum){
			$state.go('teacherPaperGrade',{'pageNum':pageNum});
		};
		$scope.endPage=function(pageNum){
			$state.go('teacherPaperGrade',{'pageNum':pageNum});
		};
	}];
});