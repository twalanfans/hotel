<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/question.css" />
<style type="text/css">
	.saveResult{width:90%;z-index:10001;word-break:break-all;height:200px;line-height:200px;background:url(${ctxStatic}/images/video_loading.gif) center no-repeat;padding:10px;position:absolute;left:4px;top:20%;display:none;}
</style>
<!-- 栏目说明 -->
<div class="navExplain">
	<span>在线解答</span>
</div>
<div class="news">
	<div class="searchBeaf">
		<span class="mb8 mr5">
			<label>问题标题：</label>
			<input type="text" id="title" value="${title}" class="inpt" />
		</span>
		<span class="mb8 mr5">
			<a href="javascript:;" ng-click="queryQuestion('${showNum}')" class="defaultBtn">搜索</a>
		</span>
		<span class="mb8">
			<a href="javascript:;" title="我要提问" id="toAsk" class="defaultBtn">我要提问</a>
		</span>
	</div>
	<!--提问列表-->
	<div class="showList">
		<ul>
		  <c:if test="${questionNum!='0'}">
			<c:forEach items="${questionList}"  begin="0" end="${showNum}" var="questionList"  varStatus="status">
			<li class="clearfix">
				<p class="fl">
					<span style="background-color:#79cdde;width:25px;height:25px;text-align:center">${status.count}</span>&nbsp;
					<a href="javascript:;" onclick="lookQuesContent('${questionList.questionId}','${questionList.clickNum}')" >${fn:substring(questionList.title, 0, 35)}...</a>
				</p>
				<div class="clearfix fr publishInfo">
					<h4 class="fl mr20"><fmt:formatDate value="${questionList.createTime}" pattern="yyyy-MM-dd HH:mm"/></h4>
					<div class="fl">
						<i class="fa fa-thumbs-o-up"></i>
						<span>${questionList.clickNum}</span>
					</div>
				</div>
			</li>
			</c:forEach>
		  </c:if>
		  <c:if test="${questionNum=='0'}">
		  	<li><p>您还没有提过问题！</p></li>
		  </c:if>
		</ul>
	</div>
	<!--翻页-->
	<div class="pageList">
		<p class="pageListBar">共<b>${questionNum}</b>条</p>
		<c:if test="${questionNum>15}">
			<p class="addMoreBtnBox"><a href="javascript:;" ng-click="loadMore('${showNum}')" class="addMoreBtn">查看更多</a></p>
		</c:if>
		<c:if test="${questionNum<=15}">
			<p class="addMoreBtnBox"><span class="addMoreBtn">没有更多了。。</span></p>
		</c:if>
	</div>
</div>
<div class="askWindow" id="askWindow">
	<h3 class="askTit">开始提问：</h3>
	<p>
		<input type="text" placeholder="问题标题" id="questionTitle" />
	</p>
	<p>
		<textarea placeholder="问题内容" id="questionContent"></textarea>
	</p>
	<p>
		<h4 class="uploadImgTit">添加图片</h4>
		<ul class="uploadImgBox clearfix">
			<li>
				<img class="imgBtn" src="${ctxStatic}/images/uploadimgbtn.jpg" alt="" />
				<input type="file" name="attachImg1" id="attachImg1" class="uploadFileBtn" />
				<i class="clearImgBtn fa fa-times-circle"></i>
			</li>
			<li>
				<img class="imgBtn" src="${ctxStatic}/images/uploadimgbtn.jpg" alt=""/>
				<input type="file" name="attachImg2" id="attachImg2" class="uploadFileBtn" />
				<i class="clearImgBtn fa fa-times-circle"></i>
			</li>
			<li>
				<img class="imgBtn" src="${ctxStatic}/images/uploadimgbtn.jpg" alt=""/>
				<input type="file" name="attachImg3" id="attachImg3" class="uploadFileBtn" />
				<i class="clearImgBtn fa fa-times-circle"></i>
			</li>
		</ul>
	</p>
	<p>
		<a href="javascript:;" class="commonBtn" id="confirmAsk">提交问题</a>
		<a href="javascript:;" class="commonBtn" id="cancelAsk">取消</a>
	</p>
</div>
<!-- 上传进度图片 -->
<div class="saveResult"></div>
<script type="text/javascript" src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/dragWindow.js"></script>
<script type="text/javascript">
	//提问框拖动
	(function(){
		var askWindow = document.getElementById('askWindow');
		dragWindow.init(askWindow);
	})();
	
	//点击“我要提问”，显示提问框
	var toAsk = document.getElementById('toAsk');
	toAsk.onclick = function(){
		askWindow.style.display = 'block';
	}
	
	//取消提问
	var cancelAsk = document.getElementById('cancelAsk');
	cancelAsk.onclick = function(){
		$('#questionContent,#questionTitle').val('');
		askWindow.style.display = 'none';
	}
	
	//查看问题详情
	function lookQuesContent(questionId,clickNum){
		  var url = "${contextPath}/question/questionContent?questionId="+questionId+"&c_num="+clickNum;
		  var tool ="height=900,width=1024,top=0,left=0,menubar=yes, alwaysRaised=yes";
		  window.open(url,'问题评论详情',tool);
	}
	
	//提交问题
	var confirmAsk = document.getElementById('confirmAsk');
	var questionContent = document.getElementById('questionContent');
	//上传进度图片
	var saveResult = document.getElementsByClassName('saveResult')[0];
	confirmAsk.onclick = submitQuestion;
	function submitQuestion(){
		var questionTitle = document.getElementById('questionTitle');
		var questionContent = document.getElementById('questionContent');
		if(questionTitle.value==''||questionTitle.value.length<4){
			alert('问题标题不能为空且必须超过4个汉字！');
			questionTitle.focus();
			return;
		}
		if(questionContent.value==''){
			alert('问题内容不能为空！');
			questionContent.focus();
			return;
		}
		saveResult.style.display = 'block';
		askWindow.style.display = 'none';
		$.ajaxFileUpload({
			url : '${contextPath}/question/saveNewQuestion',
			secureuri: false,
		    fileElementId: ["attachImg1","attachImg2","attachImg3"],
		    dataType: 'json',
		    data : {
		    	questionTitle : questionTitle.value,
		    	questionContent : questionContent.value
			}, 
			success : function(result){
				if(result.status=="success"){
					alert('问题提交成功');
					window.location.reload();
				}else{
					alert('问题提交失败，请重新操作！');
				}
				saveResult.style.display = 'none';
			}
		});
	}
	
	/*提问_上传图片*/
	$('.clearImgBtn').on('click',function(){
		$(this).siblings('.imgBtn').attr('src','${ctxStatic}/images/uploadimgbtn.jpg');
		$(this).siblings('input').val('');
		$(this).stop(true).fadeOut();
	});
	$('.uploadFileBtn').on('change',function(){
		showPreview($(this),$(this).siblings('.imgBtn'));
	});
	function showPreview(source,imgObj) {
      var file = $(source).get(0).files[0];
      //允许上传的文件类型
	  var fileTypeArry = ['jpg', 'jpeg', 'png', 'gif'];
	  //该文件类型
	  var fileType = file.type.substr(file.type.indexOf('/')+1, 5);
	  //判断该文件类型是否符合规范
	  var fileTypeArryLen = fileTypeArry.length;
	  //定义标识符
	  var mark = false;
	  //判断该文件类型是否在数组中
	  for(var i=0; i<fileTypeArryLen; i++){
		if(fileTypeArry[i] == fileType.toLowerCase()){
			mark = true;
			break;
		}
	  }
	  if(!mark){
		  alert('仅允许上传图片文件！');
		  return;
	  }
      if(window.FileReader && mark) {
          var fr = new FileReader();
          var portrait = $(imgObj).get(0);
          fr.onloadend = function(e) {
            portrait.src = e.target.result;
            imgObj.siblings('.clearImgBtn').stop(true).fadeIn();
          };
          fr.readAsDataURL(file);
      }
   }
</script>