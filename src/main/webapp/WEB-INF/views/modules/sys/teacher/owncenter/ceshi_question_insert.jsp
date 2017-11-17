<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" />
		<title>试题编辑</title>
		<!--css-->
		<link type="text/css" rel="stylesheet" href="${ctxStatic}/css/comStyle.css" />
		<link type="text/css" rel="stylesheet" href="${ctxStatic}/css/font.css" />
		<link type="text/css" rel="stylesheet"  href="${ctxStatic}/css/teacher/ceshi_question_insert.css" />
		<link type="text/css" rel="stylesheet" href="${ctxStatic}/css/zTreeStyle.css" />
	</head>
<body style="background-color:#fff;padding:5%;">
<!--试题编辑框-->
<div class="addAndEditQuetion">
	<!--试题内容编辑-->
	<div class="editTitle" style="width:390px;margin:20px auto 20px auto">
		<h4>编辑试题(填写以下内容)</h4>
	</div>
	<div class="editContent" style="width:390px;margin:0px auto;">
		<!--所属课程-->
		<div class="coursewareType startEdit examsBelong">
			<span>选择课程</span>
			<select name="examsBelong" id="examsBelong" class="inpt">
				<option value="">—请选择—</option>
				<c:forEach items="${courseList}"  var ="courseList">
				<option value="${courseList.courseId}">${courseList.courseName}</option>
				</c:forEach>
			</select>
			<p class="errorPrompt"></p>
		</div>
		<!--试题名称-->
		<div class="kjtime startEdit examsName">
			<span>试题名称</span>
			<input type="text" name="examsName" id="examsName" class="editInpt inpt" placeholder="试题名称" value="${TestQuestion.questionTitle}">
			<p class="errorPrompt"></p>
		</div>
		<!--试题内容-->
		<div class="examsContents">
			<span>试题内容</span>
			<input type="file" name="examsContents" id="examsContents" class="inpt" placeholder="试题内容" />
			<div id="uploadImg" style="overflow:auto;">
				<c:if test="${TestQuestion.questionFilePath!=''&&TestQuestion.questionFilePath!=null}">
				<img src="${filePath}/${TestQuestion.questionFilePath}" />
				</c:if>
			</div>
			<p class="errorPrompt"></p>
		</div>
		<!--试题类别-->
		<div class="coursewareType startEdit examsClassify">
			<span>试题类别</span>
			<select id="questionType" class="inpt" placeholder="试题类别" selectDict="${questionType}" onchange="chooseQuestType(this.value)" class="editInpt">
					<option>—请选择—</option>
			</select>
			<p class="errorPrompt"></p>
		</div><br/>
		<!--选项个数-->
		<div class="chooseNum" style="display:none;">
			<span>选项个数</span>
			<select id="selectNum" placeholder="选项个数" onchange="chooseAnswerNum(this.value)" class="editInpt inpt">
				<option value="">—请选择—</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="6">6</option>
				<option value="7">7</option>
				<option value="8">8</option>
				<option value="9">9</option>
			</select>
			<p class="errorPrompt"></p>
		</div>
		<!--关联知识点-->
		<div class="examsKnowledge">
			<input type="text" placeholder="请关联知识点" id="knowledge" value="${knowledgeContent}" knowledgeId="" readonly class="editInpt inpt" />
			<a href="javascript:;" id="examsKnowledge" class="defaultBtn">点击关联</a>
			<p class="errorPrompt"></p>
		</div>
	<!--设置答案-->
	<div class="editTitle">
		<span>试题答案</span>
	</div>
	<div class="editContent">
		<!--选择题-->
		<div class="radioChoose" id="answerChoose" style="display:block;"></div>
		<div class="chooseAnswerBox" style="display:block">${TestQuestion.answerContent}</div>
		<!--问答-->
		<div class="questionAndAnswer clearfix" style="display:none;">
			<textarea id="answerText" class="inpt" style="width:350px;height:150px;">${TestQuestion.answerContent}</textarea>
		</div>
		<div class="examsOptions" style="display:none;">
			<span>答案备注</span>
			<input type="text" name="answerRemark" id="answerRemark" class="inpt" value="${TestQuestion.answerRemark}" />
		</div>
		<div>是否对学生开放：<select id="openStatus"><option value="1">是</option><option value="0">否</option></select></div>
		<!--保存或取消-->
		<div class="tck clearfix">
			<a href="javascript:;" class="commonBtn bc" onclick="saveTestQuestion();">保存试题</a>
			<a href="javascript:;" class="commonBtn qx">关闭窗口</a>
		</div>
		</div>
	</div>
	</div>
</div>
<!----知识点zTree-->
<div class="searchByKnowledge relateKnowledge">
	<div class="knowledgeTree">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div class="tck clearfic">
		<a class="confirmRelate commonBtn">确认</a>
		<a class="cancelRelate commonBtn">关闭</a>
	</div>
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
</body>
</html>
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/ajaxfileupload.js"></script>
<!-- 数据字典下拉选择 -->
<script type="text/javascript" src="${ctxStatic}/js/dictionTranslate.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript">
	//试题题型的数据字典取值
	optionDict('questionType');   
	$("#questionType option[value='${TestQuestion.type}']").attr("selected","selected");
	$("#examsBelong option[value='${TestQuestion.courseId}']").attr("selected","selected");
	$("#selectNum option[value='${TestQuestion.selectNum}']").attr("selected","selected");
	if('${TestQuestion.selectNum}'!=""){
		$('.chooseNum').show();
	}
	
	$('#examsContents').change(function(){
		var img = $('#examsContents').val();
		if(!img.match(/.jpg|.png|.gif|.bmp/i)){
			alert("试题内容必须为图片！");
			$('#examsContents').val("");
		}
	})
	//本页面用到的全局变量
	var ceshiInsertVariable = {
		$clear : $('.clear'),//遮罩层
		isNameNull : '1',//判断试题名称是否为空,默认为空[1]
		$courseId : $('#examsBelong').val(),//所选课程id
		zsdTree : '',//知识点树
		$questionTypeVal : $('#questionType').val(),//问题类型
		answerContent : $('#answerText').val(),//用户回答的内容
		$examsNameVal : $('#examsName').val(),//试题名称
		answerRemark : $('#answerRemark').val(),//答案备注
		$examsContentsVal : $('#examsContents').val(),//试题内容
		$selectNumVal : $('#selectNum').val(),//单选或多选选项个数
		knowledge : $('#knowledge').attr('knowledgeId'),//知识点ID
		openStatus: $('#openStatus').val()
	};
	
	//进入子页面，首先判断testId是否为空，如果为空，说明是添加试题，如果不为空，说明是修改试题，点击关联知识点的时候要自动传入课程Id。
	if('${testId}'){
		knowledgeTree('${contextPath}/testonline/fetchKnowledgeLink',ceshiInsertVariable.$courseId,'${testId}');
		if(ceshiInsertVariable.$questionTypeVal=='11' || ceshiInsertVariable.$questionTypeVal=='12'){
			for(var i=1; i<=ceshiInsertVariable.$selectNumVal; i++){
				var answerValue="";
				if(i==1){answerValue="A"}if(i==2){answerValue="B"}if(i==3){answerValue="C"}if(i==4){answerValue="D"}if(i==5){answerValue="E"}if(i==6){answerValue="F"}if(i==7){answerValue="G"}
				if(i==8){answerValue="H"}if(i==9){answerValue="I"}
				if($('#questionType').val()=="11"){
					if(ceshiInsertVariable.$selectNumVal=="2"){
						if(i==1){answerValue="对"}if(i==2){answerValue="错"}
					}
					document.getElementById("answerChoose").innerHTML +="<input type=\"radio\" value=\""+answerValue+"\" name=\"radioChoose\" /><label>&nbsp;"+answerValue+"</label>&nbsp;&nbsp;&nbsp;";
				}else if($('#questionType').val()=="12"){
					document.getElementById("answerChoose").innerHTML +="<input type=\"checkbox\" value=\""+answerValue+"\" name=\"mutipleChoose\" /><label>&nbsp;"+answerValue+"</label>&nbsp;&nbsp;&nbsp;";
				}
				$('#answerChoose').show();
			}
			$('#answerChoose').show();
		}else{
			$('.questionAndAnswer').show();
			$('.examsOptions').show();
		}
	}
	//失去焦点验证试题名称
	$("#examsName").blur(function(){
		var $this = $(this);
		var $checkName = $this.siblings('.errorPrompt');
		 var questionName = $this.val();
		 if(questionName==""){
			 $checkName.html('试题名称不能为空！').css('color','#f00');
			 ceshiInsertVariable.isNameNull="1";
		 }else{
			 $checkName.html('');
			 ceshiInsertVariable.isNameNull="0";
		 }
	});
	
	//选择课程
	$('#examsBelong').click(function(){
		//课程ID
		ceshiInsertVariable.$courseId = $(this).val();
		if('${testId}'=='' || '${testId}'==null){
			knowledgeTree('${contextPath}/knowledge/fetchKnowledgeList',ceshiInsertVariable.$courseId,'');
		}else{
			knowledgeTree('${contextPath}/testonline/fetchKnowledgeLink',ceshiInsertVariable.$courseId,'${testId}');
		}
	});
	
	//获取zTree知识点数据
	function knowledgeTree(url,courseId,testId){
		$.ajax({
			url : url,//从后台获取与课件相关联的知识点信息url
			data : JSON.stringify({
				"courseId" : courseId,//对应的课程ID
				"testId" : testId//试题ID
			}),
			async : false,
			type : "POST",
			contentType: "application/json; charset=utf-8",
			success : function(data){
				ceshiInsertVariable.zsdTree = eval(data).success;
				if(ceshiInsertVariable.zsdTree!=null || ceshiInsertVariable.zsdTree!=''){
					$.fn.zTree.init($("#treeDemo"), setting, ceshiInsertVariable.zsdTree);
					$("#selectAll").bind("click", selectAll);
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo"); 
					treeObj.expandAll(false); 
					$(".chooseKnowledge").show();
				}
			}
		});
	}
	
	//失去焦点验证
	$('.editInpt').blur(blurValidate);
	function blurValidate(){
		var $this = $(this);
		var $thisVal = $this.val();
		var $errorPrompt = $this.siblings('.errorPrompt');
		var $placeholder = $this.attr('placeholder');
		if($thisVal==''){
			$errorPrompt.html($placeholder+"不能为空").css('color','#f00');
		}else if(new RegExp(/[\"\{\}\[\]\(\)\\<\>%]+/i).test($thisVal)){
			$errorPrompt.html($placeholder+"不能包含特殊字符").css('color','#f00');
		}else{
			$errorPrompt.html('');
		}
	}
	
	//选择试题类型
	$('#questionType').change(function(){
		answerSh();
		$('#answerChoose').hide();
	});
	
	//选择选项个数
	$('#selectNum').change(function(){
		answerSh();
		$('#answerChoose').show();
	});
	
	//用户所选答案显示或清空
	function answerSh(){
		$('.chooseAnswerBox').html('');
		$('#answerText').val('');
	}
	
	//保存试题
	function saveTestQuestion(){
		var $course = $('#examsBelong');
		if($course.val()==""){
			alert('还未选择课程！');
			return;
		}
		var $examsName = $('#examsName');
		ceshiInsertVariable.$examsNameVal = $examsName.val();
		if(new RegExp(/[\"\{\}\[\]\(\)\\<\>%]+/i).test(ceshiInsertVariable.$examsNameVal)){ 
			alert('试题名称不能包含特殊字符！');
		    return; 
		}else if(ceshiInsertVariable.$examsNameVal==''){
			alert('试题名称不能为空');
			return;
		}
		 var $examsContents = $('#examsContents');
		 var uploadImg = $('#uploadImg').html();
		 if($examsContents.val()==""&&uploadImg==""){
			 alert('试题内容还未上传！');
		     return; 
		 }
		 var $questionType = $('#questionType');
		 ceshiInsertVariable.$questionTypeVal = $questionType.val();
		 if(ceshiInsertVariable.$questionTypeVal==$questionType.find('option:eq(0)').html()||ceshiInsertVariable.$questionTypeVal==''){
			 alert('请选择试题类别！');
		     return; 
		 }
		 var $selectNum = $('#selectNum');
		 ceshiInsertVariable.$selectNumVal = $selectNum.val();
		 if(ceshiInsertVariable.$selectNumVal==''){
			 if(ceshiInsertVariable.$questionTypeVal=='11'||ceshiInsertVariable.$questionTypeVal=='12'){
				 alert('请选择选项个数！');
			     return; 
			 }
		 }
		 ceshiInsertVariable.knowledge = $('#knowledge').attr('knowledgeId');
		 if($('#knowledge').val()==''){
			if(ceshiInsertVariable.zsdTree==''||ceshiInsertVariable.zsdTree==null){
				alert('本课程下未发现知识点，请先上传');
				return;
			}else{
				alert('请选择知识点');
			    return; 
			}
		 }
		 var answerChoose =[];
		 if(ceshiInsertVariable.$questionTypeVal=="12"){	
			 $('input[name="mutipleChoose"]:checked').each(function(){ 
				 answerChoose.push($(this).val());
			 });
			 if(answerChoose.length<1 && $('.chooseAnswerBox').html()==""){
				 alert('请选择多选题正确答案');
			     return; 
			 }else if(answerChoose.length>=1){
				 $('#answerText').val(answerChoose.join('@'));  //选择题答案跟文本答案存一个变量
			 }
		 }else if(ceshiInsertVariable.$questionTypeVal=="11" && $('.chooseAnswerBox').html()==""){
		 	 answerChoose = $('input[name="radioChoose"]:checked').val();  
			 if(answerChoose.length<1){
				 alert('请选择单选题正确答案');
			     return; 
			 }else if(answerChoose.length>=1){
				 $('#answerText').val(answerChoose);  
			 }
		 }else{
			 if($('#answerText').val()=="" ){
				alert('请填写答案内容');
				return; 
			 }
		 }
		ceshiInsertVariable.answerContent = $('#answerText').val();
		//上传试题内容
		ceshiInsertVariable.answerRemark = $('#answerRemark').val();//答案备注
		//是否公开给学生
		ceshiInsertVariable.openStatus = $('#openStatus').val();
		if('${testId}'==''){
			testComfirm('${contextPath}/testonline/saveCreateTestQuestion','保存成功','保存出现问题');
		}else{
			$('#confirmBtn').html('继续修改');
			$('.promptBox').fadeIn();
			testComfirm('${contextPath}/testonline/updateTestQuestion','修改成功','修改失败');
		}
	}
	
	//确认保存或修改试题内容
	function testComfirm(url,ok,error){
		$.ajaxFileUpload({
			type:'post',
			url:url,
		    secureuri: false,
		    fileElementId: ["examsContents"],
		    dataType: 'json',
		    data : {
	    	    courseId : ceshiInsertVariable.$courseId,
		       	testId : '${testId}',
				title : ceshiInsertVariable.$examsNameVal, 
				answerRemark : ceshiInsertVariable.answerRemark, 
				questionType : ceshiInsertVariable.$questionTypeVal, 
				selectNum : ceshiInsertVariable.$selectNumVal, 
				answerContent : ceshiInsertVariable.answerContent, 
				knowledgeId : ceshiInsertVariable.knowledge,
				status : ceshiInsertVariable.openStatus
			}, 
			success:function(data){
				console.log(data);
				if(data.status=='success'){
					 $('.promptTxt p:eq(1) span').html(ok);
					 $('.promptBox').fadeIn();
				}else{
					$('.promptTxt p:eq(1) span').html(error);
					 $('.promptBox').fadeIn();
				}
			}
		});
	}
	
	//关闭提示
	$('#cancelBtn').click(function(){
		$('.promptBox').fadeOut();
	});
	
	//展示用户选中的答案
	$('.radioChoose').click(function(){
		var str = '';
		$(this).find('input:checked').each(function(){
			str += $(this).val()+' ';
		});
		$('.chooseAnswerBox').html(str);
		$('#answerText').val(str);
	})
	/*弹窗*/
	$('.tantit a,.cancelbtn,.qx').on('click',function(){
		window.close();
	});
	//点击继续添加
	$('#confirmBtn').on('click',function(){
		window.location.reload();
	});
	//选择试题类型
	function chooseQuestType(type){
		if(type=="11" || type=="12"){				//11:单选题 12:多选题
			$('.chooseNum').show();
			$('.radioChoose').show();
			$('.questionAndAnswer').hide();
			$('.examsOptions').hide();
		}else if(type=="21" || type=="22" || type=="23"|| type=="24" || type=="25"){
			$('.questionAndAnswer').show();
			$('.examsOptions').show();
			$('.chooseNum').hide();
			$('#answerChoose').hide();
		}
	}
	//选择答案个数
	function chooseAnswerNum(selectNum){
		$('#answerChoose').empty();
		for(var i=1; i<=selectNum; i++){
			var answerValue="";
			if(i==1){answerValue="A"}if(i==2){answerValue="B"}if(i==3){answerValue="C"}if(i==4){answerValue="D"}if(i==5){answerValue="E"}if(i==6){answerValue="F"}if(i==7){answerValue="G"}
			if(i==8){answerValue="H"}if(i==9){answerValue="I"}
			if($('#questionType').val()=="11"){
				if(selectNum=="2"){
					if(i==1){answerValue="对"}if(i==2){answerValue="错"}
				}
				document.getElementById("answerChoose").innerHTML +="<input type=\"radio\" value=\""+answerValue+"\" name=\"radioChoose\" /><label>&nbsp;"+answerValue+"</label>&nbsp;&nbsp;&nbsp;";
			}else if($('#questionType').val()=="12"){
				document.getElementById("answerChoose").innerHTML +="<input type=\"checkbox\" value=\""+answerValue+"\" name=\"mutipleChoose\" /><label>&nbsp;"+answerValue+"</label>&nbsp;&nbsp;&nbsp;";
			}
			$('#answerChoose').show();
		}
	}

	//点击开始关联知识点
	$("#examsKnowledge").click(function(){
		if(ceshiInsertVariable.$courseId==''){
			alert("请先选择课程！");
		}else{
			if(ceshiInsertVariable.zsdTree==''||ceshiInsertVariable.zsdTree==null){
					alert('本课程下未发现知识点，请先上传');
			}else{
				if('${testId}'=='' || '${testId}'==null){
					knowledgeTree('${contextPath}/knowledge/fetchKnowledgeList',ceshiInsertVariable.$courseId,'');
				}else{
					knowledgeTree('${contextPath}/testonline/fetchKnowledgeLink',ceshiInsertVariable.$courseId,'${testId}');
				}
				$(".relateKnowledge").show();	
				ceshiInsertVariable.$clear.show();
			}
		}
	});
	//确认选择的知识点
	$(".confirmRelate").click(function(){
		var treeObj=$.fn.zTree.getZTreeObj("treeDemo");
		var treeNodes=treeObj.getCheckedNodes(true);
		var nodeLen = treeNodes.length;//被选中的知识点个数
		var nodeId = [];//被选中的知识点ID
		var nodeName = [];//被选中的知识点NAME
		for(var i=0;i<nodeLen;i++){
			nodeId.push(treeNodes[i].id);
			nodeName.push(treeNodes[i].name);
		}
		$('#knowledge').val(nodeName.join(','));
		$('#knowledge').attr('knowledgeId',nodeId.join(','));
		$('.relateKnowledge').hide();
		ceshiInsertVariable.$clear.hide();
	});
	//取消选择知识点
	$(".cancelRelate").click(function(){
		$('.relateKnowledge').hide();
		ceshiInsertVariable.$clear.hide();
	});
	//上传试题图片内容
	$("#examsContents").change(function(){
		var $val = $(this).val();
		if(!$val.match(/.jpg|.gif|.jpeg|.png|.bmp/i)){
			alert("图片格式必须是jpg、png、gif、bmp中的一种");
		}else{
			if (window.FileReader){
				var reader = new FileReader();
				var file = $(this).get(0).files[0];
				var imgUrl = reader.readAsDataURL(file);
				var imgSize=(file.size/(1024*1024)).toFixed(2);	
				reader.onload = function(e){
					var image = new Image();
					image.src = e.target.result;
					$("#uploadImg").html(image);
				};
			}
		}	
	});
	//zTree树
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
	
	//检测页面关闭，刷新父页面
	window.onbeforeunload = function(){
		window.opener.location.reload();
	}
</script>