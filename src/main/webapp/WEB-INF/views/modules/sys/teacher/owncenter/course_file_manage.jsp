<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/teacher/course_file_manage.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/videoPlay.css" />
<!-- 栏目说明 -->
<div class="navExplain">
	<span>课程资料</span>
</div>
<div class="searchBeaf">
	<!--课程名称-->
	<div class="alreadyCourseContent clearfix">
		<div class="alreadyCourseListBox  fl">
			<span class="fl">课程：</span>
			<div class="courseList fl">
				<ul class="alreadyCourseList clearfix">
					<c:forEach items="${courseList}"  var ="courseList">
						<c:if test="${courseList.courseId==courseId}"><li class="hasChoose on">${courseList.courseName}</li></c:if>
						<c:if test="${courseList.courseId!=courseId}"><li ng-click="chooseCourse(${courseList.courseId});">${courseList.courseName}</li></c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<!--课程下边的资源库-->
	<div class="studentResources clearfix">
		<div class="resourceTitle clearfix">
			<span class="fl">资源：</span>
			<ul class="fl">
				<li class="resourceChoose" ng-click="chooseCourseType(1);">微课视频</li>
				<c:if test="${courseId!=''}"><li class="dataBase" onclick="chooseCourseType(1,'${courseId}');">课件资料</li></c:if>
			</ul>
		</div>
		<div class="messagesearchbox searchCondition" style="margin:0px;">
			<form class="clearfix">
				<span class="mb8 mr5">
					<input type="text" ng-model="fileName" placeholder="搜索课件" id="fileName" class="inpt" />
					<input type="text" value="${courseId}" id="searchCourseId" class="inpt" style="display:none"/>
					<input type="text" value="${courseType}" id="searchCourseType" class="inpt" style="display:none"/>
				</span>
				<span class="mb8 mr5">
					<a ui-sref="lookMyCourseFile({pageNum : 1, courseId : this.courseId, courseType : this.courseType, fileName : this.fileName})" ui-sref-opts="{reload:true}" class="defaultBtn">搜索</a>
				</span>
				<span class="mb8">
					<c:if test="${courseId!=''}">
						<a href="javascript:;" onclick="addCourseFile()" class="defaultBtn">上传微课</a>
					</c:if>
				</span>
			</form>
		</div>
		<div class="resourceContent">
			<ul class="resourceContentlist clearfix">
				<c:forEach items="${pageInfo.list}"  var ="courseFileList">
					<li class="showDetail">
						<p><a href="javascript:;"><img src="${filePath}/${courseFileList.videoImg}" /></a></p>
						<p class="resourcetit"><a href="javascript:;" class="videoFileName">${courseFileList.fileName}</a></p>
						<p style="display: none;" class="createTime">${courseFileList.createTime}</p>
						<p style="display: none;" class="fileSize">${courseFileList.fileSize}</p>
						<p style="display: none;" class="introduce">${courseFileList.videoIntroduce}</p>
						<p style="display: none;" class="filePath" filePath="${filePath}/${courseFileList.filePath}"></p>
						<c:if test="${courseFileList.status=='0'}"><p class="helpText">视频待审核。。。</p></c:if>
						<c:if test="${courseFileList.status=='1'}">
						<a href="javascript:;" class="playbtn" fileName="${courseFileList.fileName}" filePath="${filePath}/${courseFileList.filePath}">播放按钮</a>
						</c:if>
						<c:if test="${courseFileList.status=='2'}"><p class="helpText">审核未通过</p></c:if>
						<a href="javascript:;" class="fa fa-navicon navbtn"></a>
						<!-- 简介展示详情 -->
						<ul class="showDetailBox1 clearfix"></ul>
						<div class="sourceMenu">
							<ul>
								<li><a href="javascript:;" onclick="downLoad('${courseFileList.fileId}');">下载</a></li>
								<li><a href="javascript:;" onclick="editCourseFile('${courseFileList.fileId}', this);">编辑</a></li>
								<li><a href="javascript:;" onclick="deleteFile('${courseFileList.fileId}', this);">删除</a></li>
							</ul>
						</div>
					</li>
				</c:forEach>
			</ul>
		</div>
		<!-- 翻页 -->
		<div class="pageList clearfix">
			<span class="fl">共<b>${pageInfo.total}</b>条</span>
			<span class="fl">每页<b>${pageInfo.pageSize}</b>条</span>
			<span class="fl">当前第<b>${pageInfo.pageNum}</b>页</span>
			<span class="fl clearfix">
				<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}"><span class="fl">首页</span><span class="fl">上一页</span></c:if>
		          	<c:if test="${pageInfo.pageNum != 1 && pageInfo.pages > 1}">
					<a href="javascript:;" ng-click="shouye(1);" class="fl">首页</a>
					<a href="javascript:;" ng-click="prevPage(${pageInfo.pageNum-1})" class="fl">上一页</a>
				</c:if>
				<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span  class="fl">下一页</span><span class="fl">尾页</span></c:if>
		          	<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
					<a href="javascript:;" ng-click="nextPage(${pageInfo.pageNum+1})" class="fl">下一页</a>
					<a href="javascript:;" ng-click="endPage(${pageInfo.pages})" class="fl">尾页</a>
				</c:if>
			</span>
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
	</div>
	<!-- 遮罩层 -->
	<div class="clear"></div>
	<!-- 微课编辑 -->
	<div class="editCourseBox" hidden="true">
		<div class="editTitle"><h4>编辑文件</h4></div>
		<div class="editContent">
			<!--课程名称--> 
			<div class="kcmc startEdit">
				<span>视频名称</span>
				<input type="text"  id="editFileName" class="inpt" maxlength="50" placeholder="最多50个字"/>
			</div>
			<!--文件简介-->
			<div class="kcjj clearfix">
				<span style="height:150px;line-height:150px;float:left;">视频简介</span>
				<textarea id="fileIntroduce" class="inpt" cols="35" style="height:150px;float:left;"></textarea>
			</div>
			<div class="tck clearfix">
				<a href="javascript:;" class="bc commonBtn">保存</a>
				<a href="javascript:;" class="qx commonBtn">取消</a>
			</div><p></p>
		</div>
	</div>
</div>
<iframe id="ifile" style="display:none"></iframe> 
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
<!-- 上传微课 -->
<div class="myIframe">
	<div class="uploadTitle">
		<span>上传微课</span>
		<span><a href="javascript:;" onclick="closeUploadWindow();" class="fa fa-times-circle-o clossBox"></a></span>
	</div>
	<iframe  id="mainFrame"  height="400" width="100%" scrolling="no"></iframe>
</div>
<script type="text/javascript" src="${ctxStatic}/js/ajaxfileupload.js"></script>
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
			queryOnline : document.getElementsByClassName('playbtn')//点击播放视频
		});
		
		//视频播放窗口拖动配置项
		var videoWindow = document.getElementById('videoWindow');
		dragWindow.init(videoWindow);
	})();
	
	//点击课程名称，显示详情 
	$('.navbtn').on('click',function(){
		$('.sourceMenu').hide();
		$(this).siblings('.sourceMenu').show();
		return false;
	});
	$('body').click(function(){
		$('.sourceMenu').hide();
	});
	
	//鼠标划过课件名称显示课件简介
	$('.resourcetit').hover(function(e){
		$('.showDetailBox1').empty();
		$(this).siblings('.showDetailBox1').append(
			'<li>视频简介：'+$(this).siblings(".introduce").html()+'</li>'+
			'<li>创建时间：'+$(this).siblings(".createTime").html()+'</li>'+
			'<li>视频大小：'+$(this).siblings(".fileSize").html()+'</li>'
		);
		$(this).siblings('.showDetailBox1').stop().fadeIn();
	},function(){
		$(this).siblings('.showDetailBox1').stop().fadeOut();
		}
	);	
	
	$('.resourceTitle ul li').each(function(){
		if($(this).hasClass('resourceChoose')){
			$(this).addClass('on');
		}
	});
	
	//选中相应的资源，字体颜色发生变化
	$('.resourceTitle ul li').each(function(){
		if($(this).hasClass('resourceChoose')){
			$(this).addClass('hasChoose').siblings().removeClass('hasChoose');
		}
	});
	
	$(function(){
		//选择课程，背景色变化
		$(".studentCourseList ul li").click(function(){
			$(this).addClass("hasChoose").siblings("li").removeClass("hasChoose");
		})
		$(".resourceTitle ul li").click(function(){
			$(this).addClass("resourceChoose").siblings("li").removeClass("resourceChoose");
		})
	});
	
	function chooseCourseType(type,courseId){
		var courseName = $(".hasChoose").html();
		var url = "${contextPath}/courseFile/dataBaseQuery?type="+type+"&cos_id="+courseId+"&courseName="+courseName+"&is_cmn=2357abv48xc";
		var tool ='height=720,width=1500,top=40,left=200,menubar=yes, alwaysRaised=yes'
	 	window.open(url,'课件资料',tool);
	};
	
	//打开上传窗口
	function addCourseFile(){
		var courseId ="${courseId}";
		var courseName =$('.hasChoose').html();
		var url = "${contextPath}/course/addCourseFile?courseId="+courseId+"&courseName="+courseName;
	 	document.getElementById("mainFrame").src=url;
	 	$('.myIframe').show();
	 	$('.clear').show();
	};
	
	function closeUploadWindow(){
		$('.myIframe').hide();
	 	$('.clear').hide();
	}
		
	/*删除课件开始*/
	var fileIds="";
	function deleteFile(fileId, This){
		fileIds = fileId;
		if(confirm('删除之后不可恢复，确认删除？')){
			$.ajax({
				url  : "${contextPath}/courseFile/deleteCourseFile",//后台删除课件的路径
				data : "fileId="+fileIds,	//课件ID
				type : "POST",
				success : function(str){
					if(str=="success"){
						alert('删除成功');
						$(This).parents('.showDetail').remove();
					}else{
						alert('删除失败');
					}
				}
			});
		}
	}
	/*删除课件结束*/
	
	/*编辑课件开始*/
	var fileNames="";
	//视频信息临时变量
	var videoInfo = {
		name : '',
		introduce : ''
	};
	//文件对象临时变量
	var fileObj = '';
	//编辑视频信息
	function editCourseFile(courseFileId, This){
		fileIds=courseFileId;
		fileObj = This;
		$.ajax({
			url : "${contextPath}/courseFile/fetchCourseFileDetail",//编辑更新的后台url地址
			data : JSON.stringify({
				"fileId" : courseFileId,
				"courseId" : '${courseId}'
			}),
			type : "POST",
			contentType: "application/json; charset=utf-8",
			async : true,
			success : function(editMessage){
				var editMessage = eval(editMessage);
				$("#editFileName").val(editMessage.fileName);//课件名称
				fileNames = editMessage.fileName;
				$("#fileIntroduce").val(editMessage.videoIntroduce);//课件简介
				$('.clear').show();
				$('.editCourseBox').show();
				videoInfo.name = editMessage.fileName;
				videoInfo.introduce = editMessage.videoIntroduce;
			},
		});
	};
	
	//确认保存
	$(".bc").click(function(){
		$('.actbtn').hide();
		$('.actbtn1').show();
		var fileName = $("#editFileName").val();
		if(fileName==null || fileName==""){
			alert("文件名不能为空！");
			return
		}
		if(fileNames==fileName){
			fileName="";
		}
		var fileIntroduce = $("#fileIntroduce").val();
		$.ajax({
			url: "${contextPath}/courseFile/updateCourseFile",
			dataType: 'JSON',
			type : "POST",
			data : {
				"fileId": fileIds,
				"fileName" : fileName,//课件名称
				"fileIntroduce" : fileIntroduce,//课件简介
				"courseId" : '${courseId}'
			},
			success: function(str){
				$('.clear').hide();
				$('.editCourseBox').hide();
				if(str=="success"){
					videoInfo.name = fileName;
					videoInfo.introduce = fileIntroduce;
					alert('保存成功！');
					$(fileObj).parents('.sourceMenu').siblings('.resourcetit').find('.videoFileName').text(videoInfo.name);
				}else if(str=="exist"){
					alert('该文件名已存在，请重新编辑！');
				}else if(str=="error"){
					alert('保存失败！');
				}
			}
		})
	});
	
	//取消编辑保存
	$('.qx').click(function(){
		$('.clear').hide();
		$('.editCourseBox').hide();
		$("#editFileName").val('');
		$("#fileIntroduce").val('');
	});
	
	//视频下载
	function downLoad(fileId){  
		document.getElementById("ifile").src = "${contextPath}/test/downFile?fileId="+fileId; 
	};
	
	/*点击页码翻页*/
	var pagenum=0;
	$('.epagesnum').on('click','li',function(){
		$('.epagesnum li').removeClass('on');
		if($(this).children().eq(0).text()<=1){
			$(this).addClass('on');
			return;
		}
		if($(this).index()==$('.epagesnum li').length-1){
			pagenum=$('.epagesnum li').eq($('.epagesnum li').length-1).children().eq(0).html();
			$('.epagesnum li').each(function(){
				$(this).html('<a href="javascript:;">'+pagenum+'</a>');
				pagenum++;
			});
			$('.epagesnum li').eq(0).addClass('on');
		}
		else if($(this).index()==0){
			pagenum=$('.epagesnum li').eq(0).children().eq(0).text()-$('.epagesnum li').length+1;
			$('.epagesnum li').each(function(){
				$(this).html('<a href="javascript:;">'+pagenum+'</a>');
				pagenum++;
			});
			$('.epagesnum li').eq($('.epagesnum li').length-1).addClass('on');
		}
		else{
			$(this).addClass('on');
		}
	});
</script>