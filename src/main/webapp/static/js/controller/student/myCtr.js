/*
 * @Description：学生学习计划angular控制器
 * @Date：2017-6-5
 * @Author：袁忠林、陈沐雨、赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', function($scope, $state){
		$scope.studentHasCourse=function(pageNum){		
			$state.go('studentHasCourse',{'pageNum':pageNum});
		};
		$scope.myStudyPlanPage=function(pageNum){
			var title=document.getElementById('planName').value;
			var startDate=document.getElementById('startTime').value;
			$state.go('myStudyPlanPage',{'pageNum':pageNum,'title':title,'startDate':startDate});
		};
		
		//编辑/查看学习计划详情
		$scope.editStudyPlanDetail=function(planId){
			if(planId==""){
				planId = document.getElementById('planId').value;
			}		
			$state.go('studentEditStudyPlanDetail',{'planId':planId});
		};
		
		//新建学习计划
		$scope.studentAddStudyPlan=function(){
			$state.go('studentAddStudyPlan');
		};
	}];
});