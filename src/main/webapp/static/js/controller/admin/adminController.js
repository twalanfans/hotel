/*管理员系统angular控制器*/
var app=angular.module('myapp',['ui.router']);
app.controller('departController', function($scope, $http,$state,$stateParams,$templateCache){
	$scope.refresh=function(){
		$templateCache.removeAll();
		$state.reload('department');
	};
});

app.controller('commentController', function($scope, $http,$state,$stateParams,$templateCache){
	$scope.search=function(){
		$templateCache.removeAll();
		$state.reload('commentMana');
	};
	$scope.shouye=function(pageNum){
		$state.go('commentMana',{'pageNum':pageNum});
	};
	$scope.prevPage=function(pageNum){
		$state.go('commentMana',{'pageNum':pageNum});
	};
	$scope.nextPage=function(pageNum){
		$state.go('commentMana',{'pageNum':pageNum});
	};
	$scope.endPage=function(pageNum){
		$state.go('commentMana',{'pageNum':pageNum});
	};
});

app.controller('questionController', function($scope, $http,$state,$stateParams,$templateCache){
	$scope.search=function(){
			var createUser = document.getElementById('createUser').value;
			$templateCache.removeAll();
			$state.go('questionMana',{'createUser':createUser},{reload:true});
	};
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
});

app.controller('fileController', function($scope, $http,$state,$stateParams,$templateCache){
	$scope.search=function(){
		$templateCache.removeAll();
		$state.reload('filemanagement');
	};
	$scope.shouye=function(pageNum){
		$state.go('filemanagement',{'pageNum':pageNum});
	};
	$scope.prevPage=function(pageNum){
		$state.go('filemanagement',{'pageNum':pageNum});
	};
	$scope.nextPage=function(pageNum){
		$state.go('filemanagement',{'pageNum':pageNum});
	};
	$scope.endPage=function(pageNum){
		$state.go('filemanagement',{'pageNum':pageNum});
	};
});
	
app.controller('userController', function($scope, $http,$state,$stateParams,$templateCache){
	$scope.insertUser=function(){
		$state.go('createUser');
	};
	$scope.search=function(){
		$templateCache.removeAll();
		$state.reload('usermanagement');
	};
	$scope.shouye=function(pageNum){
		$state.go('usermanagement',{'pageNum':pageNum});
	};
	$scope.prevPage=function(pageNum){
		$state.go('usermanagement',{'pageNum':pageNum});
	};
	$scope.nextPage=function(pageNum){
		$state.go('usermanagement',{'pageNum':pageNum});
	};
	$scope.endPage=function(pageNum){
		$state.go('usermanagement',{'pageNum':pageNum});
	};
});

app.controller('pubNoticeController', function($scope, $http,$state,$stateParams,$templateCache){
	$scope.search=function(){
		$templateCache.removeAll();
		$state.reload('receiveNotice');
	};
	$scope.shouye=function(pageNum){
		$state.go('receiveNotice',{'pageNum':pageNum});
	};
	$scope.prevPage=function(pageNum){
		$state.go('receiveNotice',{'pageNum':pageNum});
	};
	$scope.nextPage=function(pageNum){
		$state.go('receiveNotice',{'pageNum':pageNum});
	};
	$scope.endPage=function(pageNum){
		$state.go('receiveNotice',{'pageNum':pageNum});
	};
});

app.controller('recMsgController', function($scope, $http,$state,$stateParams,$templateCache){
	$scope.search=function(){
		$templateCache.removeAll();
		$state.reload('receiveMessage');
	};
	$scope.shouye=function(pageNum){
		$state.go('receiveMessage',{'pages':pageNum});
	};
	$scope.prevPage=function(pageNum){
		$state.go('receiveMessage',{'pages':pageNum});
	};
	$scope.nextPage=function(pageNum){
		$state.go('receiveMessage',{'pages':pageNum});
	};
	$scope.endPage=function(pageNum){
		$state.go('receiveMessage',{'pages':pageNum});
	};
});

app.controller('sendMsgController', function($scope, $http,$state,$stateParams,$templateCache){
	$scope.search=function(){
		$templateCache.removeAll();
		$state.reload('sendMessage');
	};
	$scope.shouye=function(pageNum){
		$state.go('sendMessage',{'pages':pageNum});
	};
	$scope.prevPage=function(pageNum){
		$state.go('sendMessage',{'pages':pageNum});
	};
	$scope.nextPage=function(pageNum){
		$state.go('sendMessage',{'pages':pageNum});
	};
	$scope.endPage=function(pageNum){
		$state.go('sendMessage',{'pages':pageNum});
	};
});

app.controller('checkFileController', function($scope, $http,$state,$stateParams,$templateCache){
	$templateCache.removeAll();
	$scope.search=function(){
		$state.reload('reviewattachment');
	};
	$scope.lookCheck=function(status){
		$state.go('reviewattachment',{'status':status});
	};
	$scope.shouye=function(pageNum){
		$state.go('reviewattachment',{'pageNum':pageNum});
	};
	$scope.prevPage=function(pageNum){
		$state.go('reviewattachment',{'pageNum':pageNum});
	};
	$scope.nextPage=function(pageNum){
		$state.go('reviewattachment',{'pageNum':pageNum});
	};
	$scope.endPage=function(pageNum){
		$state.go('reviewattachment',{'pageNum':pageNum});
	};
});

app.controller('mycontroller', function($scope, $compile, $stateParams,$injector){
	$injector.get('$templateCache').removeAll();//设置局部页面数据变化刷新
});