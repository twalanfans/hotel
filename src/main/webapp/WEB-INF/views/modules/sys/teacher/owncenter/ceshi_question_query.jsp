<!-- ceshi_question_query.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/quesBasket.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/zTree.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/zTreeStyle.css" />
<div class="testManagement">
	<!-- 栏目说明 -->
	<div class="navExplain">
		<span>我的试题</span>
	</div>
	<!--测试题搜索-->
	<div class="searchBeaf clearfix">
		<form action="" method="post" id="testSearchStart">
			<span class="condition mb8 mr5">
				<lebel>课程名称</lebel>
				<input type="text" value="${courseName}" id="searchCourseName" class="inpt" />
			</span>
			<span class="condition knowledgeSearch mb8 mr5">
				<lebel>知识点</level>
				<input type="text" id="searchKnowledgeId" class="inpt" value="" readonly  style="display:none;">
				<input type="text" placeholder="选择知识点" id="knowledge" readonly class="inpt" value="${knowledge}" >
				<!--知识点查询选择框-->
				<div class="searchByKnowledge" style="z-index:99999;display:none">
					<div>
						<select onchange="showTreeByCourse(this.value)" class="inpt">
							<option value="">—请选择课程对应的知识点—</option>
							<c:forEach items="${courseList}"  var ="courseList">
							<option value="${courseList.courseId}">${courseList.courseName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="knowledgeTree">
						<ul id="treeDemo" class="ztree"></ul>
					</div>
					<div class="tck clearfic">
						<a href="javascript:;" class="confirmRelate commonBtn">确认</a>
						<a href="javascript:;" class="cancelRelate commonBtn">关闭</a>
					</div>
				</div>
			</span>
			<span class="mb8 mr5">
				<a href="javascript:;" class="defaultBtn" ng-click="search();">搜索</a>
			</span>
			<span class="mb8 mr5">
				<a href="javascript:;" class="addExams defaultBtn">添加试题</a>
			</span>
			<span class="mb8 mr5">
				<a href="javascript:;" id="batchDelete" class="defaultBtn">删除所选</a>
			</span>
		</form>
	</div>
	<!-- 搜索结果 -->
	<div class="allTable">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>
						<input type="checkbox" id="selallbtn1" />
						<label for="selallbtn1">全选</label>
					</td>
					<td>所属课程</td>
					<td>试题名称</td>
					<td>录入时间</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<tr ng-repeat="(k, v) in quesData">
					<td>
						<input type="checkbox" class="selrolebtn1" name="testId" value="{{v.testId}}">&nbsp;&nbsp;&nbsp;
						<a class="fa fa-edit editQues" title="编辑试题信息" testId="{{v.testId}}"></a>
					</td>
					<td>{{v.courseName}}</td>
					<td>{{v.questionTitle | truncateFil : 12}}</td>
					<td>{{v.createTime | dateFil}}</td>
					<td>
						<input type="checkbox" value="v.checkStatus" ng-model="v.checkStatus" ng-true-value="1" ng-false-value="0" ng-click="mark(k, v.testId, v.checkStatus);" id="{{'list'+v.testId}}" style="display:none;" />
						<label for="{{'list'+v.testId}}" ng-if="v.checkStatus=='0'" class="notJoin joinBtn fa fa-plus">加入试题篮</label>
						<label for="{{'list'+v.testId}}" ng-if="v.checkStatus==undefined" class="notJoin joinBtn fa fa-plus">加入试题篮</label>
						<label for="{{'list'+v.testId}}" ng-if="v.checkStatus=='1'" class="hasJoin joinBtn fa fa-minus">移出试题篮</label>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<div class="pageList">
	<span>每页<b>{{pageNum}}</b>条</span>
	<span>当前第<b>{{currentPage}}</b>页</span>
	<span>
		<a href="javascript:;" ng-click="page('index');" ng-if="currentPage!=1">首页</a>
		<span ng-if="currentPage==1">首页</span>
	</span>
	<span>
		<a href="javascript:;" ng-click="page('prev');" ng-if="currentPage!=1">上一页</a>
		<span ng-if="currentPage==1">上一页</span>
	</span>
	<span>
		<a href="javascript:;" ng-click="page('next');">下一页</a>
	</span>
	<span>
		<span>尾页</span>
	</span>
</div>
<!-- 用户操作提示框 -->
<div class="promptBox">
	<div class="promptTxt">
		<p>提示</p>
		<p>
			<i class="fa fa-exclamation-circle fl" style="margin:20px 5px 0 20px;"></i>
			<span>删除将不可恢复，是否确定删除？</span>
		</p>
	</div>
	<div class="promptBtn">
		<a href="javascript:;" id="confirmBtn" class="commonBtn">确认</a>
		<a href="javascript:;" id="cancelBtn" class="defaultBtn">取消</a>
	</div>
</div>
<!-- 遮罩层 -->
<div class="clear"></div>
<!--试题蓝-->
<div class="searchArea">
	<div class="addTestListBox clearfix">
		<h4 class="TestListTit">当前试题篮中共有【{{quesNum}}】道题</h4>
		<div>
			<span class="clearTestBtn" ng-click="clearPaper();">清空试卷</span>
			<a href="javascript:;" class="makeTestBtn" ng-click="showPaper();">查看试卷</a>
		</div>
		<a href="javascript:;" class="closeBtn"></a>
	</div>
</div>
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript">
	//试题篮伸缩
	$('.closeBtn').on('click',function(){
		var addTestListBox=$('.addTestListBox');
		if(parseInt(addTestListBox.css('right'))>0){
			addTestListBox.stop(true).animate({'right':'-182px'},500);
		}
		else{
			addTestListBox.stop(true).animate({'right':'2px'},500);
		}
	});
	
	//多次用到的全局变量
	var ceshiVariable = {
		testId : []
	};
	
	//弹出子窗口
	function editTestQuetion(url,windowName){
		var tool ="height=500,width=630,top=40,left=500,menubar=yes, alwaysRaised=yes,scrollbars=yes";
		window.open(url,windowName,tool);
	}
	
	$('body').on('click', '.editQues', function(){
		var testId = $(this).attr('testId');
		var tool ="height=500,width=630,top=40,left=500,menubar=yes, alwaysRaised=yes,scrollbars=yes";
		window.open('${contextPath}/testonline/insertOrEditceshiQuestion?testId='+testId, '试题编辑', tool);
	});
	
	//点击添加试题按钮
	$(".addExams").click(function(){
		editTestQuetion('${contextPath}/testonline/insertOrEditceshiQuestion','添加试题');
	});
	
	//选择对应的课程，展示在课程搜索框中
	$('.searchByKnowledge select option').click(function(){
		$('#searchCourseName').val($(this).html());
	});
	
	var $prompt = $('.promptTxt p:eq(1) span'),
		$promptBox = $('.promptBox');
	
	//点击删除按钮
	$("#batchDelete").click(function(){
		var quesList = window.localStorage.questionIdStr;
		if(quesList && JSON.parse(quesList).length){
			alert('您的试题篮中还有未组卷的试题，若要删除，请先清空试题篮！');
			return;
		}
		$('input[name="testId"]:checked').each(function(){ 
			ceshiVariable.testId.push(this.value);
		});  
		if(ceshiVariable.testId.length<1){
			alert("请选择需要删除的试题！");
			return;
		}else{
			$prompt.html('删除将不可恢复，是否确定删除！');
			$promptBox.fadeIn();
		}
	})

	//点击确认，开始删除testId对应的课程
	$('#confirmBtn').on('click',function(){
		$promptBox.fadeOut();
		$.ajax({
			type:'post',
			url:'${contextPath}/testonline/deleteTestQuestion',
			data:'testId='+ceshiVariable.testId,
			dataType:'json',
			success:function(str){
				if(str=="success"){
					alert('删除成功');
					window.location.reload();
				}else{
					alert('删除失败，请重新操作');
				}
			}
		});
	});
	
	//取消删除
	$('#cancelBtn').on('click', function(){
		$promptBox.fadeOut();
	});
	
	//选择知识点展示对应的zTree数据
	function showTreeByCourse(courseId){
		$.ajax({
			url : "${contextPath}/knowledge/fetchKnowledgeList",//从后台获取与课件相关联的知识点信息url
			data : JSON.stringify({
				"courseId" : courseId
			}),
			async : false,
			type : "POST",
			contentType: "application/json; charset=utf-8",
			success : function(data){
				var zsdTree = eval(data).success;
				if(zsdTree==null || zsdTree==""){
					var errorMessage = eval(data).error;
					if(confirm('该课程下暂没有知识点，请先上传！')){
						window.location.href = '${ctx}/chooseLogin#/zsdEdit';
					}
				}else{
					$.fn.zTree.init($("#treeDemo"), setting, zsdTree);
					$("#selectAll").bind("click", selectAll);
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo"); 
					treeObj.expandAll(false); 
					$(".chooseKnowledge").show();
				}
			}
		});
	}	
	
	/*zTree树开始*/
	var setting = {
		view: {
			selectedMulti: false
		},
		check:{
			enable: true,
			chkStyle: "checkbox",
			chkboxType: {"Y":"ps","N":"ps"}
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};            
	var treeNodes;
	function selectAll() {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
	}
	/*zTree树结束*/
	
	//点击知识点展示
	$("#knowledge").click(function(){
		$(".searchByKnowledge").show();
	})
	
	//从zTree选好知识点后点击确认
	$(".confirmRelate").click(function(){
			var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
			var treeNodes=treeObj.getCheckedNodes(true);
			var v="";
			var nodes = [];
			for(var i=0;i<treeNodes.length;i++){
				 v+=treeNodes[i].name + ",";
				 nodes.push(treeNodes[i].id);
			}
			$('#searchKnowledgeId').val(nodes);
			$('#knowledge').val(v);
			$('.searchByKnowledge').hide();
	});
	
	//取消选择知识点
	$(".cancelRelate").click(function(){
			$('.searchByKnowledge').hide();
	});
	
	//全选/取消
	$('#selallbtn1').on('click',function(){
		var $selrolebtn1 = $('.selrolebtn1');
		if($(this).prop('checked')){
			$selrolebtn1.prop('checked',true);
		}else{
			$selrolebtn1.prop('checked',false);
		}
	});
</script>