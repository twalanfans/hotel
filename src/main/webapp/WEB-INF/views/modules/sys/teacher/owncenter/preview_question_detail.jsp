<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
	.addFileWindow{width:300px;height:170px;border:1px solid #ccc;background-color:#fff;display:none;position:fixed;top:15%;left:50%;margin-left:-150px;left:50%;z-index:9999;}
	.addFileTitle{height:30px;line-height:30px;background-color:#f3f3f3;color:#000;font-size:12px;text-align:center;}
	.addFileContent p{margin:10px;}
	.handle{height:30px;margin-bottom:10px;}
	.paperContent{font-size:14px;}
	.paperContent ul li{margin-bottom:10px;}
	.quesContent{width:100%;overflow:auto;}
	.quesMove{height:30px;background-color:#f5f5f5;line-height:30px;padding:4px 0;}
	.moveBtn{display:inline-block;height:30px;line-height:30px;padding:0 10px;cursor:pointer;}
</style>
<!-- 栏目说明 -->
<div class="navExplain">
	<span>试卷预览</span>
</div>
<!-- 试卷篮中的试题 -->
<div>
	<div class="handle">
		<a href="javascript:;" class="commonBtn" id="makePaper" onclick="promptShow();">将以下试题生成试卷</a>
		<a href="javascript:;" class="commonBtn" id="print" onclick="paperPrint();">打印本套试卷</a>
	</div>
	<div id="printContent" class="paperContent">
		<ul>
			<li ng-repeat="(k, v) in previewPaper">
				<p class="mb8">
					<span>第{{k+1}}题：</span>
					<span ng-if="v.type=='11'">单选题</span>
					<span ng-if="v.type=='12'">多选题</span>
					<span ng-if="v.type=='21'">名词解释</span>
					<span ng-if="v.type=='22'">简答题</span>
					<span ng-if="v.type=='23'">案例分析</span>
					<span ng-if="v.type=='24'">翻译</span>
					<span ng-if="v.type=='25'">填空</span>
				</p>
				<div class="quesContent mb8">
					<img src="${filePath}/{{v.questionFilePath}}" />
				</div>
				<div class="quesMove mb8">
					<span ng-click="move(k, 'up');" ng-if="k!=0" class="moveBtn defaultBtn mr5">上移</span>
					<span ng-click="move(k, 'down');" ng-if="k!=(previewPaper.length-1)" class="moveBtn defaultBtn mr5">下移</span>
				</div>
				<input type="hidden" value="{{v.testId}}" class="ques" />
			</li>
		</ul>
	</div>
</div>
<!-- 试题生成试卷 -->
<div class="addFileWindow" id="addFileWindow">
	<div class="addFileTitle"><h4>试卷信息编辑</h4></div>
	<div class="addFileContent">
		<p>
			<label>试卷名称</label>
			<input type="text" id="paperName" class="inpt" maxlength="50" />
		</p>
		<p>
			<label>建议用时</label>
			<input type="text" id="paperTime" class="inpt" onkeyup="this.value=this.value.replace(/\D/g,'')" />
		</p>
		<p>
			<a href="javascript:;" class="commonBtn confirmbtn" onclick="confirmSave();">生成试卷</a>
			<a href="jvascript:;" class="commonBtn cancelbtn"  onclick="closeFileWindow();">放弃</a>
		</p>
	</div>
</div>
<div class="clear"></div>
<script type="text/javascript">
	//打印试卷	
	function paperPrint(){
	    var quesMove = document.getElementsByClassName('quesMove');
	    var quesMoveLen = quesMove.length;
	    for(var i=0; i<quesMoveLen; i++){
	    	quesMove[i].style.display = 'none';
	    }
	    document.body.innerHTML = document.getElementById('printContent').innerHTML;
	    window.print();
	    window.localStorage.questionIdStr = '';
	    window.location.reload();
	}
	
	//显示弹出框
	function promptShow(){
		$('#addFileWindow').show();
		$('.clear').eq(0).show();
	}
	
	//确认组卷
	function confirmSave(){
		var paperName = $('#paperName').val();
		var paperTime = $('#paperTime').val();
		if(paperName == 'undefined' || paperTime == 'undefined'){
			alert('请填写试卷名称或测试时长！');
			return false;
		}
		//所有试题ID集合
		var ques = $('.ques');
		var quesLen = $('.ques').length;
		var testId = [];
		for(var i=0; i<quesLen; i++){
			testId.push(ques[i].value);
		}
		//向后端发送数据
		$.ajax({
			type:'post',
			url:'${contextPath}/testonline/chooseQuesToPaper',
			data:'questionId='+testId+'&paperName='+paperName+'&paperTime='+paperTime,
			dataType:'json',
			success:function(str){
				if(str=="success"){
					if(confirm("试卷已生成，是否现在去查看呢？")){
						window.localStorage.questionIdStr = '';
						window.location.href = "${ctx}/chooseLogin#/makeTestPaper/1?r=" + Math.random();
					}
				}else{
					alert("操作失败，请重新组卷！");
				}
			}
		});
		$('.clear').eq(0).hide();
	}

	//放弃组卷
	function closeFileWindow(){
		$('#addFileWindow').hide();
		$('.clear').eq(0).hide();
		$('#paperName').val("");
		$('#paperTime').val("");
	}
</script>