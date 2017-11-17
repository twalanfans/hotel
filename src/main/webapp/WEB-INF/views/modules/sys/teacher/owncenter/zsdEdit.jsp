<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/teacher/zsdEdit.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/zTreeStyle.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/zTree.css" />
<div class="zsd knowledgeManage">
	<!-- 栏目说明 -->
	<div class="navExplain">
		<span>知识点管理</span>
	</div>
	<!--选择已有课程-->
	<div class="alreadyCourse">
		<div class="alreadyCourseList clearfix">
			<span class="mb8 mr5">
				<input type="text" value="" readonly placeholder="选择课程下的知识点" id="chooseMyCourse" class="inpt" />
				<ul class="myCourse"></ul>	
			</span>
			<span class="mb8 mr5">
				<a class="tree-add-base defaultBtn">添加一级知识点</a>
			</span>
			<span class="mb8 mr5">
				<a href="javascript:;" class="defaultBtn" id="downLoad">下载知识点模板</a>
			</span>
			<span class="mb8">
				<a href="javascript:;" class="defaultBtn" id="upexcel">导入知识点Excel</a>
			</span>	
		</div>
	</div>
	<!--知识点展示-->
	<div class="zsdTitle clearfix">
		
	</div>
	<!--知识点添加和编辑-->
	<div class="zsdContent">
		<div class="content_wrap">
		<div class="zTreeDemoBackground left">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
 	</div>
</div>
<!-- 遮罩层 -->
<div class="clear"></div>
<div class="uploadModel" hidden="true">
	<div class="modelInpt">
		<input type="file" class="inpt" name="file" id="fileToUpload">
	</div>
	<div class="modelUploadConfirm clearfix">
		<a href="javascript:;" class="modelConfirm commonBtn">确认</a>
		<a href="javascript:;" class="modelCancel commonBtn">取消</a>
	</div>
</div>
<!-- 用户操作提示框 -->
<div class="promptBox">
	<div class="promptTxt">
		<p>提示</p>
		<p>
			<i class="fa fa-exclamation-circle fl" style="margin-right:5px;"></i>
			<span>删除将不可恢复，是否确定删除？</span>
		</p>
	</div>
</div>
<iframe id="ifile" style="display:none"></iframe>
<!-- 文件上传js插件 -->
<script type="text/javascript" src="${ctxStatic}/js/ajaxfileupload.js"></script>
<!--zTree功能js-->
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.core.min.js"></script>
<!--ztrezTreee的核心js-->
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.core-3.5.js"></script>
<!--zTree的复选框功能js-->
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.excheck-3.5.js"></script>
<!-- zTree编辑功能js -->
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.exedit-3.5.js"></script>
<script type="text/javascript">
	(function(){
		//定义信息提示框变量
		var $promptTxt = $('.promptTxt p:eq(1) span'),
			$promptBox = $('.promptBox');
		
		//点击选择课程
		var message,$courseId="";
		$(".alreadyCourseList").on("click","li",function(){
			$courseId = $(this).attr("courseId");
			$("#chooseMyCourse").val($(this).html());
			showListByCourseId();
		});
		
		function showListByCourseId(){
			$.ajax({
				url : "${contextPath}/knowledge/fetchKnowledgeList",
				data : JSON.stringify({
					"courseId" : $courseId//课程ID
				}),
				async : false,
				type : "POST",
				contentType: "application/json; charset=utf-8",
				success : function(zsdMessage){
					message = eval(zsdMessage).success;
					$.fn.zTree.init($("#treeDemo"), setting, message);
					if(message==''||message==null){
						alert('所选课程暂没有对应的知识点，您可以在线添加');
						$('.tree-add-base').show();
					}else{
						$("#selectAll").bind("click", selectAll);
						var treeObj = $.fn.zTree.getZTreeObj("treeDemo"); 
						treeObj.expandAll(false); 
					}
				}
			});
		}
		//点击导入excel模板
		$("#upexcel").click(function(){
			if($courseId==""){
				alert("请先选择相应的课程再导入知识点模板！");
				return;
			}
			$(".clear").show();
			$(".uploadModel").show();
		})
		//确认上传功能
		function uploadFile() {
			$promptTxt.html('知识点正在导入中，请稍后！');
			$promptBox.fadeIn();
			//获取参数提交后台
			$.ajax({
				url  : "${contextPath}/uploadFile/checkKnowledgeFile",
				data : "id="+$courseId,
				type : "POST",
				success : function(str){
					if(str=="no"){
						if(confirm('该课程下已存在相应知识点，是否覆盖？')){
							uploadKnowledge();
						}
					}else{
						uploadKnowledge();
					}
				}
			});
		}
		
		//上传点方法
		function uploadKnowledge(){
			$.ajaxFileUpload({
				url: '${contextPath}/uploadFile/saveKnowledgeFile',
				secureuri: false,
				fileElementId: "file",
				dataType: 'json',
				data: {id: $courseId},
				success: function(data) {
					if (data.status == 'success'){
						alert('知识点导入成功，您可以重新选择查看对应课程下的知识点！');
						setTimeout(function(){
							window.location.reload();
						},1500);
					}else{
						alert('知识点导入失败，请重新操作！');
					}
				}
			})
		}
			
		//获取文件路径，并将其作为课件标题
		$("#fileToUpload").change(function(){
			var $this = $(this);
			var $fileVal = $this.val();
			if(!$fileVal.match(/.xlsx/)){
				alert('必须上传EXCEL表格模板');
				$this.val('');
				return;
			}else{
				$(".fileTitle").val($fileVal);
				if(window.FileReader){
					var reader = new FileReader();
					var file = $this.get(0).files[0];
					var fileUrl = reader.readAsDataURL(file);
					reader.onload = function(e){
						//确认上传操作
						$(".modelConfirm").click(function(){
							$(".clear").hide();
							$(".uploadModel").hide();
							uploadFile();
						});
					}
				}
			}
		})
		
		//取消上传
		$(".modelCancel").click(function(){
			if(confirm("文件还未上传，确认取消？")){
				$(".clear").hide();
				$(".uploadModel").hide();
			}
		})
		
		//知识点模板下载
		var downLoad = document.getElementById('downLoad');
		downLoad.onclick = function(){
			document.getElementById("ifile").src = "${contextPath}/test/downFile?fileId=5"; 
		}

		//课程展示和伸缩
		$("#chooseMyCourse").focus(function(){
			$('.clear').show();
			$.ajax({
				url : "${contextPath}/course/teacherCourseList",
				success : function(data){
					var data = eval(data.success);
					var courseStr = "";
					for(var i=0; i<data.length; i++){
						courseStr += "<li courseId="+data[i].courseId+">"+data[i].courseName+"</li>";
					}
					$(".alreadyCourseList ul").html(courseStr);
				}
				});
					$(this).siblings("ul").slideDown("slow");
				}).blur(function(){
					$(this).siblings("ul").slideUp("slow");
					$('.clear').hide();
		})
		
		//删除本节知识点
		function remov(treeId,treeNode){
			var $this = $(this);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			var sendKnowledgeId=treeNode.id;
			$.ajax({
				url : "${contextPath}/knowledge/deleteKnowledge",
				type: "post",
				contentType: "application/json; charset=utf-8",
				data : JSON.stringify({
					"id" : sendKnowledgeId, //本级知识点ID
					"courseId" : $courseId
				}),
				success : function(){
					$this.parent().parent().remove();
					alert("删除成功");
				},
				error : function(){
					alert("删除失败，请重新操作");
				}
			})
		};
		
		//前后台交互（保存）
		function save(treeId, treeNode){
			var $this = $(this);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			var sendKnowledgeId=treeNode.id;
			var $thisVal = treeNode.name;
			$.ajax({
				url : "${contextPath}/knowledge/updateKnowledge",
				type : "POST",
				data : {
					"id" : sendKnowledgeId, //本级知识点ID
					"courseId" : $courseId,  //课程ID
					"title" : $thisVal   //更新后的title值
				},
				success : function(str){
					if(str=="success"){
						alert("更新成功！")
					}else{
						alert("更新失败！")
					}
				}
			});
		};
		
		//前后台交互（新增节点）
		function addItem (treeId, treeNode,pNodeId){
			var $this = $(this);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			var sendKnowledgeId = treeNode.id;
			var thisId;
			if(pNodeId == null){
				$thisId = treeNode.id;
			}else{
				$thisId = pNodeId;
			}
			$.ajax({
				url : "${contextPath}/knowledge/addKnowledge",
				data : JSON.stringify({
					"parentId" : $thisId, //父级知识点ID
					"courseId" : $courseId,   //课程ID
					"title" : "new node" + (newCount++)  //知识点title
				}),
				async : false,
				type : "post",
				contentType : "application/json; charset=utf-8",
				success : function(data){
					var recv = eval(data).success;
					var id = recv.id;
					newCreateId = id;
				}
			});
		};
		
		//添加一级知识点操作
		$(".tree-add-base").click(function(treeId,treeNode){
			if($('#chooseMyCourse').val()==''){
				alert('请先选择课程');
				return;
			}else{
				$.ajax({
					url : "${contextPath}/knowledge/addKnowledge",
					data : JSON.stringify({
						"parentId" : 0, //父级知识点ID
						"courseId" : $courseId,   //课程ID
						"title" : "new node" + (newCount++)  //知识点title
					}),
					type : "post",
					contentType : "application/json; charset=utf-8",
					success : function(data){
						var recv = eval(data).success;
						newCreateId = recv.id;
						var zTree = $.fn.zTree.getZTreeObj("treeDemo");
						var newId = newCreateId;
						var newId = newCreateId;
						zTree.addNodes(treeNode, {id:newId, pId:0, name:"new node" + (newCount++)});
						return false;
					}
				});
			}
		});
		
		//zTree配置
		var setting = {
		    view: {
		       	addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false
			},
			edit: {
				enable: true,
				//给节点额外增加属性来控制“重命名”、“删除”图标的显示或隐藏
				showRenameBtn:showRenameBtn,
				showRemoveBtn:showRemoveBtn
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeDrag: beforeDrag,
				beforeEditName: beforeEditName,
				beforeRemove: beforeRemove,
				beforeRename: beforeRename,
				onRemove: onRemove,
				onRename: onRename
			}
		};      
		var log, className = "dark";
			function beforeDrag(treeId, treeNodes) {
			return false;
		}
		//编辑节点
		function beforeEditName(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
		}
		//删除节点
		function beforeRemove(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.selectNode(treeNode);
			return confirm("确认删除【"+treeNode.name+"】吗？");
		}
		//鼠标滑过显示添加、编辑、删除按钮
		function onRemove(e, treeId, treeNode) {
			showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			remov(treeId, treeNode);
		}
		//重新编辑之后，判断节点内容是否为空
		function beforeRename(treeId, treeNode, newName) {
			className = (className === "dark" ? "":"dark");
			showLog("[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			if(newName.length==0) {
				alert("知识点名称不能为空");
				var zTree = $.fn.zTree.getZTreeObj("treeDemo");
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}
			return true;
		}
		function onRename(e, treeId, treeNode) {
			showLog("[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
			save(treeId, treeNode);
		}
		function showLog(str) {
			if (!log) log = $("#log");
				log.append("<li class='"+className+"'>"+str+"</li>");
			if(log.children("li").length > 8) {
				log.get(0).removeChild(log.children("li")[0]);
			}
		}
		function getTime() {
			var now= new Date(),
			h=now.getHours(),
			m=now.getMinutes(),
			s=now.getSeconds(),
			ms=now.getMilliseconds();
			return (h+":"+m+":"+s+ " " +ms);
		}
		//是否显示编辑按钮
		function  showRenameBtn(treeId, treeNode){
			//获取节点所配置的noEditBtn属性值
			if(treeNode.noEditBtn != undefined && treeNode.noEditBtn){
				return false;
			}else{
				return true;
			}
		}
		//是否显示删除按钮
		function showRemoveBtn(treeId, treeNode){
			//获取节点所配置的noRemoveBtn属性值
			if(treeNode.noRemoveBtn != undefined && treeNode.noRemoveBtn){
				return false;
			}else{
				return true;
			}
		}
		//在addHoverDom中判断第0级的节点不要显示“新增”按钮(最后一级没有新增按钮)
		var newCount = 1;
		var newCreateId = 0;
		function addHoverDom(treeId, treeNode) {
			if(treeNode.level >=2){
				return false;
			}else{
				//给节点添加"新增"按钮
				var sObj = $("#" + treeNode.tId + "_span");
				if (treeNode.editNameFlag || $("#addBtn_"+treeNode.id).length>0) return;
				var addStr = "<span class='button add' id='addBtn_" + treeNode.id
				+ "' title='add node' onfocus='this.blur();'></span>";
				sObj.after(addStr);
				var btn = $("#addBtn_"+treeNode.id);
				if (btn) btn.bind("click", function(){
					var zTree = $.fn.zTree.getZTreeObj("treeDemo");
					var newId = newCreateId;
					addItem(treeId, treeNode,null);
					var newId = newCreateId;
					zTree.addNodes(treeNode, {id:newId, pId:treeNode.id, name:"new node" + (newCount)});
					return false;
				});
			}
		};
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.id).unbind().remove();
		};
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		}
	})();
</script>