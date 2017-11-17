/*
 * @Description：教师端我的试题angular控制器(其中的关于加入试题篮的代码与学生端错题记录类似，因为$scope在angular自定义服务中无法被注入，以及试题ID等字段名称不一致，所以暂时只能分开写)
 * @Date：2017-6-12
 * @Author：赵一鸣
 * */

define([], function(){
	return ['$scope', '$http', '$state', function($scope, $http, $state){
		//当前页数，默认第一页
        $scope.currentPage = 1;
        //每页数量
        $scope.pageNum = 10;
        //加入试卷的试题数量
        $scope.quesNum = window.localStorage.questionIdStr ? JSON.parse(window.localStorage.questionIdStr).length : 0;
        
        //搜索
        $scope.search = function(){
        	$scope.currentPage = 1;
        	$scope.quesList();
        };
        
        //试题列表
        $scope.quesList = function(){
        	var courseName = $('#searchCourseName').val();
        	var knowledgeId = $("#searchKnowledgeId").val();
            $http({
            	url : ctx + '/testonline/ajaxGetQuestion?courseName?courseName='+courseName+'&knowledgeId='+knowledgeId+'&page='+$scope.currentPage,
            	method : 'post',
            	async : false
            }).then(function(result){
                //每页展示的数据
				$scope.quesData = result.data;
                $scope.checkedQuestion();
            });
        };
        $scope.quesList();
		
        //加入或移出试卷
        $scope.questionIdArry = window.localStorage.questionIdStr ? JSON.parse(window.localStorage.questionIdStr) : [];
        $scope.mark = function(_index, id, status){
			//加入
            if(status == '0' || status == undefined){
                $scope.questionIdArry.push(id);
                window.localStorage.questionIdStr = JSON.stringify($scope.questionIdArry);
                $scope.quesNum += 1;
                return;
            }
			//移出
            if(status == '1'){
                var questionIdArryLen = $scope.questionIdArry.length;
                for(var i=0; i<questionIdArryLen; i++){
                    if($scope.questionIdArry[i] == id){
                        $scope.questionIdArry.splice(i, 1);
                        window.localStorage.questionIdStr = JSON.stringify($scope.questionIdArry);
                        break;
                    }
                }
                $scope.quesNum -= 1;
            }
        };
        
        //选中已经加入购物车的试题
        $scope.checkedQuestion = function(){
            if(!window.localStorage.questionIdStr){
                return false;
            }
            var quesDataLen = $scope.quesData.length;
            var questionIdStrLen = JSON.parse(window.localStorage.questionIdStr).length;
            for(var j=0; j<quesDataLen; j++){
                for(var k=0; k<questionIdStrLen; k++){
                    if($scope.quesData[j].testId== JSON.parse(window.localStorage.questionIdStr)[k]){
                        $scope.quesData[j].checkStatus = '1';
                        break;
                    }
                }
            }
        };
        
        //查看试卷中的试题
	    $scope.showPaper = function(){
	    	var questionIdStr = window.localStorage.questionIdStr;
	    	if(questionIdStr && JSON.parse(questionIdStr).length){
	    		$state.go('quesInPaper', {quesId : JSON.parse(questionIdStr)});
	    	}else{
	    		alert('试题篮中暂没有任何试题，快去添加吧！');
	    	}
	    };
	    
	    //清空试卷
	    $scope.clearPaper = function(){
	    	window.localStorage.questionIdStr = '';
	    	$scope.questionIdArry = [];
	    	var quesDataLen = $scope.quesData.length;
	    	for(var i=0; i<quesDataLen; i++){
	    		$scope.quesData[i].checkStatus = '0';
	    	}
	    	$scope.quesNum = 0;
	    };
        
        //翻页
        $scope.page = function(mark){
            switch(mark){
                case 'index':
                    $scope.currentPage = 1;
                    break;
                case 'prev':
                    $scope.currentPage -= 1;
                    break;
                case 'next':
                    $scope.currentPage += 1;
                    break;
                default:
                    alert('非法操作');
                    return;
            }
            $scope.quesList();
            $scope.checkedQuestion();
        };
	}];
});