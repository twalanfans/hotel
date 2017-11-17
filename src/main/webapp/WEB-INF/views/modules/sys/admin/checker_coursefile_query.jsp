<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/admin/checker_coursefile_query.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/videoPlay.css" />
<!-- 栏目说明 -->
<div class="navExplain">
	<span>课件审核</span>
</div>
<!--mainBox-->
<div class="mainBox clearfix">
	<!-- 审核通过或不通过切换查看 -->
	<div class="navListContent">
		<ul class="checkstatuslist clearfix">
		<c:if test="${status=='1'}">
			<li ui-sref="reviewattachment({pageNum : 1, status : ''})" ui-sref-opts="{reload:true}">未审核</li>
			<li ui-sref="reviewattachment({pageNum : 1, status : 1})" ui-sref-opts="{reload:true}" class="active">已审核</li>
		</c:if>
		<c:if test="${status!='1'}">
			<li ui-sref="reviewattachment({pageNum : 1, status : ''})" ui-sref-opts="{reload:true}" class="active">未审核</li>
			<li ui-sref="reviewattachment({pageNum : 1, status : 1})" ui-sref-opts="{reload:true}" >已审核</li>
		</c:if>
		</ul>
	</div>
	<!-- 审核详情 -->
	<div class="testSearchResult teacherCourse" >
		<div class="adminReviewHandle">
			<!-- 搜索 -->
			<div id="testSearchStart">
				<div class="condition">
					<span>&nbsp;&nbsp;课件名</span>
					<input type="text" ng-model="fileName" id="searchFileName" class="inpt" />
					<a ui-sref="reviewattachment({pageNum : 1, status : this.status, fileName : this.fileName})" ui-sref-opts="{reload:true}" class="defaultBtn">搜索</a>
				</div>
			</div>
		</div>
		<div class="allTable">
			<table cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<td>
							<label for="selallbtn">全选</label>
							<input type="checkbox" id="selallbtn" style="display:none" />
						</td>
						<td class="textContent">课件名</td>
						<td>大小</td>
						<td>状态</td>
						<td>时间</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pageInfo.list}"  var ="checkFileList">
						<tr>
							<td>
								<input type="checkbox" class="selrolebtn" name="fileId" userId="${checkFileList.userId}" fileName="${checkFileList.fileName}" value="${checkFileList.fileId}">
							</td>
							<td class="textContent"><a href="javascript:;"  class="queryOnline" fileId="${checkFileList.fileId}" filePath="${filePath}/${checkFileList.filePath}" style="color:blue;" title="在线播放">${checkFileList.fileName}</a></td>
							<td>${checkFileList.fileSize}</td>
							<c:if test="${checkFileList.status=='0'}"><td>未审核</td></c:if>
							<c:if test="${checkFileList.status=='1'}"><td>审核通过</td></c:if>
							<c:if test="${checkFileList.status=='2'}"><td>审核未通过</td></c:if>
							<td>${checkFileList.createTime}</td>
						</tr>
					</c:forEach>
				</tbody>
		 	</table>
	 	</div>
	</div>
	<!-- 点击审核通过或不通过 -->
	<c:if test="${status=='' || status==null }">
	<div class="functionButtonBox clearfix mb8">
		<a href="javascript:;" onclick="confirmPass(1);" class="defaultBtn">审核通过</a>
		<a href="javascript:;" onclick="confirmPass(2);" class="defaultBtn">审核不通过</a>
	</div>
	</c:if>
	<!-- 翻页 -->
	<div class="pageList clearfix">
		<span class="fl">共<b>${pageInfo.total}</b>条</span>
		<span class="fl">每页<b>${pageInfo.pageSize}</b>条</span>
		<span class="fl">当前第<b>${pageInfo.pageNum}</b>页</span>
		<span class="fl clearfix">
			<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}"><span class="fl">首页</span><span>上一页</span></c:if>
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
<!-- 视频播放窗口 -->
<div class="videoplaybox videoWindow" id="videoWindow" style="display:none;">
	<p class="clearfix">
		<span class="videoName" id="videoName">视频信息</span>
		<a href="javascript:;" class="fr closebtn" id="closeBtn">X</a>
	</p>
	<video controls="controls" autoplay="autoplay" id="videoPlay">
		您的浏览器不支持此格式！
	</video>
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
	<div class="promptBtn">
		<a href="javascript:;" id="cancelBtn" class="defaultBtn">取消</a>
		<a href="javascript:;" id="confirmBtn" class="commonBtn">确认</a>
	</div>
</div>
<iframe id="ifile" style="display:none"></iframe>
<script type="text/javascript" src="${ctxStatic}/js/videoPlay.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/dragWindow.js"></script>
<script type="text/javascript">
	(function(){
		//视频播放窗口配置项
		videoPlay.init({
			videoWindow : document.getElementById('videoWindow'),//视频播放窗口
			videoPlay : document.getElementById('videoPlay'),//视频video
			videoName : document.getElementById('videoName'),//视频播放窗口头部显示的视频信息
			closeBtn : document.getElementById('closeBtn'),//关闭按钮
			queryOnline : document.getElementsByClassName('queryOnline')//点击播放视频
		});
		
		//视频播放窗口拖动配置项
		var videoWindow = document.getElementById('videoWindow');
		dragWindow.init(videoWindow);
	})();
	
	var fileId = []; var userId = [];  var fileName = []; var status;
	//审核通过或不通过
	function confirmPass(sta){
		status=sta;
		$('input[name="fileId"]:checked').each(function(){ 
			fileId.push($(this).val());
			userId.push($(this).attr("userId"));
			fileName.push($(this).attr("fileName"));
		});  
		if(fileId.length<1){
			alert("请选择审核项！");
			return;
		}
		$('.promptTxt p:eq(1) span').html('审核之后状态将不可更改！');
		$('.promptBox').fadeIn();
	}
	
	//关闭弹窗
	$('#cancelBtn').click(function(){
		$('.promptBox').fadeOut();
	});
	
	//确认
	$('#confirmBtn').on('click',function(){
		var $prompt = $('.promptTxt p:eq(1) span'),
			$promptBox = $('.promptBox');
		$.ajax({
			type:'post',
			url:'${contextPath}/checker/checkFileStatus',
			data:'fileId='+fileId+'&status='+status+"&userId="+userId+"&fileName="+fileName,
			dataType:'json',
			success:function(str){
				if(str=="success"){
					$prompt.html('操作成功！');
					$promptBox.fadeOut();
					window.location.reload();
				}else{
					$prompt.html('操作失败！');
					$promptBox.fadeOut();
				}
			}
		});
	})

	//在线查看课件
	var oVideo = document.getElementById('videoPlay');
	$('.queryOnline').click(function(){
		var adress = $(this).attr("filePath");
		var fileId = $(this).attr("fileId");
		$('.videoPlay').attr('src',adress);
		$(".videoName").html("当前播放："+$(this).html());
		var totalTime ="";
		oVideo.addEventListener("canplay",function(){
			totalTime = formatTime(oVideo.duration); 
		});
		setTimeout(function(){
			$.ajax({
				type:'post',
				url:'${contextPath}/checker/updateFileTimeLong',
				data:'fileId='+fileId+'&timeLong='+totalTime,
				dataType:'json',
				success:function(str){
					if(str=="error"){
						alert("查看视频信息途中遇到问题！");
					}
				}
			});
		},3000);
	})
	//时间格式标准化 h:m:s
	function formatTime(str){
		var str = parseInt(str);
		var m = zeroFill(Math.floor(str/60));
		var s = zeroFill(Math.floor(str%60));
		return "00" + ":" + m + ":" + s;
	}
	//补0
	function zeroFill(time){
		if(time<10){
			return "0"+time;
		}else{
			return time;
		}
	}
	//全选/取消
	$('#selallbtn').on('click',function(){
		if($('#selallbtn').prop('checked')){
			$('.selrolebtn').prop('checked',true);
		}else{
			$('.selrolebtn').prop('checked',false);
		}
	});
</script>