<!-- teacher_make_testpaper.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style>
	.startZj div{margin-bottom:20px;}
	.startZj p{font-weight:bold;margin-bottom:5px;}
	.zjContent li{overflow:hidden;margin-bottom:10px;}
	.mainWrapBottom{background-color:#fff;padding:10px;}
	.startZj{position:relative;}
	.saveResult{width:280px;word-break:break-all;height:200px;line-height:200px;background:url(${ctxStatic}/images/video_loading.gif) center no-repeat;padding:10px;position:absolute;left:50%;margin-left:-150px;top:20%;display:none;}
</style>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/zTree.css" />
<div class="mlDistance clearfix">
	<!-- 栏目说明 -->
	<div class="navExplain">
		<span>随堂组卷</span>
	</div>
	<!--mainContent-->
	<div class="mainContent">
		<div class="mainWrap">
			<div class="mainWrapBottom">
				<!--开始组卷-->
				<div class="startZj" style="display:none">
					<div class="searchBeaf">
						<a href="javascript:;" class="defaultBtn" onclick="paperHistory();">返回查看组卷记录</a>
					</div>
					<!--一级分类-->
					<div class="firstLevel clearfix">
						<p>第一步：选择课程</p>
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
					<div class="secondLeve2">
						<p class="makePaperChooseKnowledge">第二步：选择知识点</p>
						<div class="searchByKnowledge">
						<div class="knowledgeTree">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
						</div>
					</div>
					<!--添加详情-->
					<ul class="zjContent">
						<p>第三步：添加详情</p>
						<li>
							<label>试卷名称：</label>
							<input type="text" id="paperName" placeholder="请输入试卷名称" class="inpt" />
						</li>
						<li>
							<label>建议用时：</label>
							<input type="text" id="testTime" class="inpt" placeholder="请输入试卷测试时长" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="2" />分钟
						</li>
						<li>
							<label>试题题数：</label>
							<input type="text" class="testTotalNum inpt" id="testTotalNum" placeholder="请输入试题数量" onkeyup="this.value=this.value.replace(/\D/g,'')" />
							<p class="checkTotalNum"></p>
						</li>
						<li>
							<label>备注说明：</label>
							<input type="text" class="paperDiscription inpt" id="paperDiscription"/><font color="red">可不填</font>
						</li>
						<li>
							<a href="javascript:;" class="commonBtn" onclick="makeTestPaper();">开始组卷</a>
						</li>
					</ul>
					<div class="saveResult"></div>
				</div>
				<!-- 组卷记录 -->
				<div class="resourceContent">
					<div class="searchBeaf">
						<a href="javascript:;" class="deletePaper defaultBtn mr5">删除所选试卷</a>
						<a href="javascript:;" class="defaultBtn" onclick="makePaper();">开始组卷</a>
					</div>
					<span>&nbsp;&nbsp;&nbsp;试卷名称：</span>
					<input type="text" id="searchPaperName" value="${paperName}" class="inpt">
					<a ui-sref="makeTestPaper({pageNum : 1})" ui-sref-opts="{reload:true}" class="defaultBtn">查询</a>
					<div class="allTable clearfix">
						<table cellpadding="0" cellspacing="0">
							<thead>
								<tr>
									<td><input type="checkbox" id="selallbtn" /><label for="selallbtn">全选</label></td>
									<td>试卷名称</td>
									<td>组卷时间</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pageInfo.list}"  var ="paperList">
								<tr>
									<td>
										<input type="checkbox" class="selbtn" name="paperId" value="${paperList.paperId}"/>
									</td>
									<td>
										<a href="javascript:;" onclick="queryPaperDetail(${paperList.paperId},1);">${paperList.paperName}</a>
									</td>
									<td><fmt:formatDate value="${paperList.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!-- 翻页 -->
					<div class="pageList">
						<span>共<b>${pageInfo.total}</b>条</span>
						<span>每页<b>${pageInfo.pageSize}</b>条</span>
						<span>当前第<b>${pageInfo.pageNum}</b>页</span>
						<span>
							<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}"><span>首页</span><span>上一页</span></c:if>
					          	<c:if test="${pageInfo.pageNum != 1 && pageInfo.pages > 1}">
								<a href="javascript:;" ng-click="shouye(1);">首页</a>
								<a href="javascript:;" ng-click="prevPage(${pageInfo.pageNum-1})">上一页</a>
							</c:if>
							<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span>下一页</span><span>尾页</span></c:if>
					          	<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
								<a href="javascript:;" ng-click="nextPage(${pageInfo.pageNum+1})">下一页</a>
								<a href="javascript:;" ng-click="endPage(${pageInfo.pages})">尾页</a>
							</c:if>
						</span>
					</div>	
				</div>
			</div>
		</div>
	</div>
</div>
<!-- 用户操作提示框 -->
<div class="promptBox">
	<div class="promptTxt">
		<p>提示</p>
		<p>
			<i class="fa fa-exclamation-circle fl" style="margin:20px 5px 0 20px;"></i>
			<span></span>
		</p>
	</div>
	<div class="promptBtn">
		<a href="javascript:;" id="confirmBtn" class="commonBtn">继续组卷</a>
		<a href="javascript:;" id="cancelBtn" class="defaultBtn">查看试卷</a>
	</div>
</div>
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.core.min.js"></script><!--ztree功能js-->
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.core-3.5.js"></script><!--ztree的核心js-->
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.excheck-3.5.js"></script><!--ztree的复选框功能js-->
<script type="text/javascript">
	//组卷记录与开始组卷切换显示
	function paperHistory(){
		$('.resourceContent').show();
		$('.startZj').hide();
	}
	
	function makePaper(){
		$('.startZj').show();
		$('.resourceContent').hide();
	}
	
	//全选/取消
	$('#selallbtn').on('click',function(){
		if($('#selallbtn').prop('checked')){
			$('.selbtn').prop('checked',true);
		}else{
			$('.selbtn').prop('checked',false);
		}
	});
	
	//选中课程之后展示对应的知识点
	function showTreeByCourse(courseId){
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
					alert('本课程下暂没有知识点，请先去上传知识点！');
					$('.searchByKnowledge').hide();
				}else{
					$.fn.zTree.init($("#treeDemo"), setting, zsdTree);
					$("#selectAll").bind("click", selectAll);
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo"); 
					treeObj.expandAll(false); 
					$('.searchByKnowledge').show();
				}
			}
		});
	}
	
	function queryPaperDetail(paperId,paperType){
		var url = "${contextPath}/testonline/paperDetail?paperId="+paperId+"&paperType="+paperType;
		var tool ='height=920,width=800,top=40,left=200,menubar=yes, alwaysRaised=yes,overflow=auto'
	 	window.open(url,'试卷详情',tool);
	}
	
	//点击开始组卷的各种验证
	var paperId;
	function makeTestPaper(){
		var courseId = $('#selectCourse').val();
		if(courseId==""){
			alert("您还未选择课程！");
			return;
		}
		var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
		var treeNodes=treeObj.getCheckedNodes(true);
		var nodes = [];
		for(var i=0;i<treeNodes.length;i++){
			 nodes.push(treeNodes[i].id);
		}
		if(nodes.length<1){
			alert("请选择知识点！");
			return;
		}
		var paperName = $('#paperName').val();
		if(paperName==""){
			alert("请填写试卷名称！");
			return;
		}
		var testTime = $('#testTime').val();
		var testTotalNum = $('#testTotalNum').val();
		if(testTotalNum==""){
			alert("请填写试卷总题数！");
			return;
		}
		$('.saveResult').fadeIn();
		$.ajax({
			type:'post',
			url:'${contextPath}/testonline/saveTestPaper',
			data:"courseId="+courseId+"&knowledgeId="+nodes+"&paperName="+paperName+"&testTime="+testTime+"&testTotalNum="
					+testTotalNum+"&paperDiscription="+$('#paperDiscription').val()+"&paperType=2", 
			success:function(data){	
				switch(data){
					case 'error':
						alert('组卷失败，请重新操作！');
						break;
					case 'over':
						alert('当前组卷试题数已超过库存数！');
						break;
					default:
						$('.promptTxt p:eq(1) span').html('组卷成功');
						$('.promptBox').fadeIn();
						paperId = data;
				}
				$('.saveResult').fadeOut();
			}
		});
	}
	
	//查看试卷
	$('#cancelBtn').on('click',function(){
		$('.promptBox').fadeOut();
		$('.mainWrapBottom').load("${contextPath}/testonline/paperDetail?paperId="+paperId+"&paperType=1");
	});
	
	//继续组卷
	$('#confirmBtn').on('click',function(){
		window.location.reload();
	});
	
	//点击删除按钮
	$(".deletePaper").click(function(){
		var paperIds = [];
		$('input[name="paperId"]:checked').each(function(){ 
			paperIds.push(this.value);
		});  
		if(paperIds.length<1){
			alert("请选择需要删除的项！");
			return;
		}
		if(!confirm("删除将不可恢复，是否确认删除？")) return
		$.ajax({
			type:'post',
			url:'${contextPath}/testonline/deleteTestPaper',
			data:'paperId='+paperIds,
			dataType:'json',
			success:function(str){
				if(str=="success"){
					alert("删除成功！");
					window.location.reload();
				}else{
					alert("删除失败！");
					window.location.reload();
				}
			}
		});
	})
	
	//zTree树开始
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
	/*开始组卷结束*/
</script>