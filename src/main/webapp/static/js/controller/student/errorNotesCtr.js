/*
 * @Description：学生错题记录angular控制器(其中的关于加入试题篮的代码与教师端我的试题组卷类似，因为$scope在angular自定义服务中无法被注入，以及试题ID等字段名称不一致，所以暂时只能分开写)
 * @Date：2017-6-14
 * @Author：赵一鸣
 * */

define([], function(){
	return ['$scope', '$state', '$http', function($scope, $state, $http){
		//当前第几页
        $scope.currentPage = 1;
        //每页记录条数
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
        	var title = $("#searchTitle").val();
        	var startTime = $("#searchStartTime").val();
        	var endTime = $("#searchEndTime").val();
            $http({
            	url : ctx + '/student/queryMyErrorNotes?title='+title+'&startTime='+startTime+'&endTime='+endTime+'&page='+$scope.currentPage,
            	method : 'post',
            	async : false
            }).then(function(result){
                //每页展示的数据
                $scope.quesData = result.data;
                //默认选中已经被加入试题篮的试题
                $scope.checkedQuestion();
            });
        };
        $scope.quesList();
        
        //加入或移除试卷
        $scope.questionIdArry = window.localStorage.questionIdStr ? JSON.parse(window.localStorage.questionIdStr) : [];
        $scope.mark = function(_index, id, status){
            if(status == '0' || status == undefined){
                $scope.questionIdArry.push(id);
                window.localStorage.questionIdStr = JSON.stringify($scope.questionIdArry);
                $scope.quesNum += 1;
                return;
            }
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
                    if($scope.quesData[j].noteId== JSON.parse(window.localStorage.questionIdStr)[k]){
                        $scope.quesData[j].checkStatus = '1';
                        break;
                    }
                }
            }
        };
        
        //清空试卷
	    $scope.clearPaper = function(){
	    	var quesDataLen = $scope.quesData.length;
	    	for(var i=0; i<quesDataLen; i++){
	    		$scope.quesData[i].checkStatus = '0';
	    	}
	    	window.localStorage.questionIdStr = '';
	    	$scope.questionIdArry = [];
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