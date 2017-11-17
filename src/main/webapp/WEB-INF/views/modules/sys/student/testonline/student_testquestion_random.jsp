<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/testpaper.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/zTreeStyle.css" />
<style type="text/css">
		.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
</style>
<div class="navExplain">
	<span>答题自测</span>
</div>			
<div class="startZj selTestBox" >
	<!--一级分类-->
	<div class="firstLevel clearfix">
		<p class="makePaperChooseKnowledge">第一步：选择课程</p>
		<select id="selectCourse" class="inpt" onchange="showTreeByCourse(this.value)">
			<option value="">—请选择—</option>
		<c:forEach items="${courseList}"  var ="courseList">
			<option value="${courseList.courseId}">${courseList.courseName}</option>
		</c:forEach>
		<c:forEach items="${commCourse}"  var ="commCourse">
			<option value="${commCourse.courseId}">${commCourse.courseName}</option>
		</c:forEach>
		</select>
	</div>
	<!--二级分类-->
	<ul class="secondLeve2">
		<p class="makePaperChooseKnowledge">第二步：选择知识点</p>
		<div class="searchByKnowledge">
			<div class="knowledgeTree">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
		</div>
	</ul>
	<div>
		<p class="makePaperChooseKnowledge">第三步：选择题类型</p>
		<select class="selTestType inpt">
				<option  value="">—请选择—</option>
				<option  value="1">客观题</option>
				<option value="2">主观题</option>
		</select>
	</div>
	<a href="javascript:;" class="commonBtn startTestBtn" onclick="testStart();">测试开始</a>
</div>

<div class="showIframeBox" id="showIframeBox">
	<div class="iframeShadow"></div>
	<div class="iframeBox">
		<div id="testOnlineFrame"></div>
	</div>
</div>
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.core.min.js"></script><!--ztree功能js-->
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.core-3.5.js"></script><!--ztree的核心js-->
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.excheck-3.5.js"></script><!--ztree的复选框功能js-->
<script>
var questionClass="";
function chooseClass(n,obj){
	questionClass = n;
}

function testStart(){
	var courseId = $("#selectCourse").val();
	if(courseId=="" || courseId==null){
		alert("请选择试题所属的试题！");
		return
	}
	var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
	var treeNodes=treeObj.getCheckedNodes(true);
	var nodes = [];
	for(var i=0;i<treeNodes.length;i++){
		 nodes.push(treeNodes[i].id);
	}
	$('#testOnlineFrame').load("${contextPath}/testonline/testQuestionOnline?courseId="+courseId+"&knowledgeId="+nodes+"&questionClass="+questionClass);
	//document.getElementById("testOnlineFrame").src="${contextPath}/testonline/testQuestionOnline?courseId="+courseId+"&knowledgeId="+nodes+"&questionClass="+questionClass;	
}

//选择课程之后，显示对应的知识点
function showTreeByCourse(courseId){
	var $treeDemo = $('#treeDemo');
	if(courseId==""){
		return;
	}
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
				var errorMessage=	eval(data).error;
				$treeDemo.html("该课程还未关联知识点！");
				$treeDemo.css({"color":"red"});
			}else{
				$.fn.zTree.init($treeDemo, setting, zsdTree);
				$("#selectAll").bind("click", selectAll);
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo"); 
				treeObj.expandAll(false); 
				if($('#treeDemo').html()!=""||$('#treeDemo').html()!=null){
					$('.selTestType,.startTestBtn').fadeIn();
					$('.selTestType').on('change',function(){
						var _this=this;
						if($(this).val()==1){
							chooseClass(1,_this);
						}
						else if($(this).val()==2){
							chooseClass(2,_this);
						}
					});
				}else{
					$('.selTestType,.startTestBtn').fadeOut();
				}
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
</script>