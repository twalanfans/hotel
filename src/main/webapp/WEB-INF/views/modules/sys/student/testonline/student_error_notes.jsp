<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/calendar.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/quesBasket.css" />
<style type="text/css">
	.addFileWindow{width:100%;max-width:310px;border:1px solid #ccc;background-color:#fff;display:none;position:fixed;top:32%;left:43%;}
	.addFileTitle{height:30px;line-height:30px;background-color:#6bc5a4;color:#fff;font-size:12px;padding-left:1%;}
	.addFileContent div{line-height:30px;padding-left:5px;}
	@media only screen and (max-width: 767px){
		.addFileWindow{left:10px;}
	}
</style>
<!-- 栏目说明 -->
<div class="navExplain">
	<span>错题记录</span>
</div>
<!-- 搜索 -->
<div class="searchBeaf" >
	<span class="mb8 mr5">
		<span>&nbsp;&nbsp;试题名称：</span>
		<input type="text"  id="searchTitle" class="inpt" />
	</span>
	<span class="mb8 mr5">
		<span>开始时间：</span>
		<input type="text"  id="searchStartTime" class="testTime inpt" readonly />
	</span>
	<span class="mb8 mr5">
		<span>结束时间：</span>
		<input type="text"  id="searchEndTime" class="testTime inpt" readonly />
	</span>
	<span class="mb8">
		<a href="javascript:;" ng-click="search();" class="defaultBtn">搜索</a>
		<a href="javascript:;" onclick="deleteErrorQues();" class="defaultBtn">删除</a>
	</span>
</div>
<!-- 搜索结果 -->
<div class="allTable">
	<table cellpadding="0" cellspacing="0" >
		<thead>
			<tr>
				<td>
					<input type="checkbox" id="selallbtn">
					<label for="selallbtn">全选</label>
				</td>
				<td>试题标题</td>
				<td>创建时间</td>
				<td>操作</td>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="(k, v) in quesData">
				<td>
					<input type="checkbox" class="selrolebtn" name="questionId" value="{{v.questionId}}" noteId="{{v.noteId}}" />
				</td>
				<td>
					<a href="javaScript:;" class="errorQuestionDetail" noteId="{{v.noteId}}" >{{v.questionTitle | truncateFil : 20}}</a>
				</td>
				<td>{{v.createTime | dateFil}}</td>
				<td>
					<input type="checkbox" value="v.checkStatus" ng-model="v.checkStatus" ng-true-value="1" ng-false-value="0" ng-click="mark(k, v.noteId, v.checkStatus);" id="{{'list'+v.noteId}}" style="display:none;">
					<label for="{{'list'+v.noteId}}" ng-if="v.checkStatus=='0'" class="notJoin joinBtn fa fa-plus">加入试卷</label>
					<label for="{{'list'+v.noteId}}" ng-if="v.checkStatus==undefined" class="notJoin joinBtn fa fa-plus">加入试卷</label>
					<label for="{{'list'+v.noteId}}" ng-if="v.checkStatus=='1'" class="hasJoin joinBtn fa fa-minus">移出试卷</label>
				</td>
			</tr>
		</tbody>
	 </table>
</div>
<!-- 翻页 -->
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
	<span>尾页</span>
</div>
<!--试题蓝-->
<div class="searchArea">
	<div class="addTestListBox clearfix">
		<h4 class="TestListTit">当前试题篮中共有【{{quesNum}}】道题</h4>
		<div>
			<span class="clearTestBtn" ng-click="clearPaper();">清空试卷</span>
			<a href="javascript:;" onclick="errorToPaper();" class="makeTestBtn">错题重组</a>
		</div>
		<a href="javascript:;" class="closeBtn"></a>
	</div>
</div>
<script type="text/javascript" src="${ctxStatic}/js/calendar.js"></script>
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
	//配置日历表组件
	$(function (){
		$("input.testTime").manhuaDate({					       
			Event : "click",//可选				       
			Left : 0,//弹出时间停靠的左边位置
			Top : -16,//弹出时间停靠的顶部边位置
			fuhao : "-",//日期连接符默认为-
			isTime : true,//是否开启时间值默认为false
			beginY : 1949,//年份的开始默认为1949
			endY :2100//年份的结束默认为2049
		});
	});
	
	//全选或取消
	$('#selallbtn').on('click',function(){
		if($('#selallbtn').prop('checked')){
			$('.selrolebtn').prop('checked',true);
		}else{
			$('.selrolebtn').prop('checked',false);
		}
	});
	
	//查看错题详情
	$('body').on('click', '.errorQuestionDetail', function(){
		var noteId = $(this).attr('noteId');
		var url = "${contextPath}/student/errorQuestionDetail?noteId="+noteId;
		var tool ="height=400,width=900,top=300,left=300,menubar=yes, alwaysRaised=yes,scrollbars=yes";
		window.open(url,'错题明细',tool);
	});
	
	function errorToPaper(){
		var questionId = [];
		var questionIdStr = window.localStorage.questionIdStr;
		if(!questionIdStr || JSON.parse(questionIdStr).length<3){
			alert('请最少选择3道题进行组卷！');
			return;
		}else{
			questionId = JSON.parse(questionIdStr);
		}
		$.ajax({
			url : '${contextPath}/testonline/errorQuesToPaper',
			data : "questionId="+questionId,
			type : 'post',
			dataType : 'json',
			success : function(result){
				if(result=="success"){
					window.localStorage.questionIdStr = '';
					window.location.href="${ctx}/chooseLogin#/studentPaperTest/1//0";
				}else{
					alert('组卷失败，请重新操作！');
				}
			}
		});
	}
	
	//点击确认，开始删除testId对应的课程
	function deleteErrorQues(){
		var questionIdStr = window.localStorage.questionIdStr;
		if(questionIdStr && JSON.parse(questionIdStr).length){
			alert('您现在的试题篮中尚有未生成试卷的试题，在执行删除操作之前，必须先清空试题篮!');
			return;
		}
		var noteId =[];
		$('input[name="questionId"]:checked').each(function(){ 
			noteId.push($(this).attr("noteId"));
		});  
		if(noteId.length<1){
			alert("请选择要删除的错题记录！");
			return;
		}
		$.ajax({
			type:'post',
			url:'${contextPath}/student/updateErrorNotes',
			data:"noteId="+noteId+"&isAdd=0",
			dataType:'json',
			success:function(str){
				if(str=="success"){
					alert('删除成功');
					window.location.reload();
				}else{
					alert('删除失败，请重新操作！');
				}
			}
		});
	}
</script>