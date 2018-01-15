<!-- student_mycourse_file.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/videoPlay.css" />
<style type="text/css">
	.chooseOptions{line-height:30px;background-color:#fff;padding:10px 0 0 10px;}
	.chooseOptions span{float:left;}
	.chooseOptions ul li{float:left;padding:0 10px;cursor:pointer;}
</style>
<!--学生端我的课程展示-->
<div class="studentCourse">
	<!--课程名称-->
	<div class="studentCourseList clearfix chooseOptions" >
		<span>
			<b>所有课程：</b>
		</span>
		<ul class="clearfix">
			<c:forEach items="${courseList}"  var ="courseList">
				<c:if test="${courseList.courseId==courseId}"><li class="hasChoose fl">${courseList.courseName}</li></c:if>
				<c:if test="${courseList.courseId!=courseId}"><li  class="fl" ng-click="chooseCourse(${courseList.courseId});">${courseList.courseName}</li></c:if>
			</c:forEach>
		</ul>
	</div>
	<!--课程下边的资源库-->
	<div class="studentResources">
		<div class="resourceTitle clearfix chooseOptions">
			<span>
				<b>教师资源：</b>
			</span>
			<ul>
				<c:if test="${courseType=='1' || courseType==''}"><li class="hasChoose resourceChoose" ng-click="chooseCourseType(1);">微课视频</li></c:if>
				<c:if test="${courseType!='1' && courseType!=''}"><li ng-click="chooseCourseType(1);">微课视频</li></c:if>
				<c:if test="${courseId!=''}"><li class="dataBase" onclick="chooseCourseType(1,'${courseId}');">教师课件</li></c:if>
			</ul>
		</div>
		<div class="resourceTitle clearfix chooseOptions">
			<span>
				<b>系统资源：</b>
			</span>
			<ul>
				<li class="dataBase" onclick="chooseCourseType(2,'');">案例库</li>
				<li class="dataBase" onclick="chooseCourseType(3,'');">图片库</li>	
				<li class="dataBase" onclick="chooseCourseType(4,'');">标准工作流程库</li>	
				<li class="dataBase" onclick="chooseCourseType(5,'');">法律法规及规范标准库</li>
			</ul>
		</div>
		<div class="searchBeaf clearfix">
				<span class="mb8 mr5">
					<label for='fileName'>课件名称：</label>
					<input type="text" value="${fileName}" id="fileName" class="inpt" />
				</span>
				<span class="mb8">
					<a href="javascript:;" ng-click="searchFile('${courseId}','${courseType}');" class="defaultBtn">搜索</a>
				</span>
		</div>
		<div class="allTable">
			<table cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<td>课件名称</td>
						<td>文件大小</td>
						<td>上传时间</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody id="coursefileList1">
					<c:forEach items="${pageInfo.list}"  var ="courseFileList">
						<tr>
							<td>
								<span class="fa fa-file-text"></span>
								<span title="${courseFileList.videoIntroduce}" class="showDetail">${courseFileList.fileName}</span>
							</td>
							<c:if test="${courseFileList.fileSize==''|| courseFileList.fileSize==null}">
								<td>——</td>
							</c:if>
							<c:if test="${courseFileList.fileSize!='' && courseFileList.fileSize!=null}">
								<td>${courseFileList.fileSize}</td>
							</c:if>
							<td class="introduce" style="display:none">${courseFileList.videoIntroduce}</td>
							<td><fmt:formatDate value="${courseFileList.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
							<td>
								<a href="javascript:;" class="queryOnline" fileName="${courseFileList.fileName}"  filePath="${filePath}/${courseFileList.filePath}">在线播放</a>|
								<a href="javascript:;" onclick="downLoad('${courseFileList.fileId}');">下载</a>
							</td>
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
<!-- 简介展示详情 -->
<ul class="showDetailBox clearfix"></ul>
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
	
	$('body').click(function(){
		$('.sourceMenu').hide();
	})
	
	$('.resourceTitle ul li').each(function(){
		if($(this).hasClass('resourceChoose')){
			$(this).addClass('on');
		}
	});
	//选择课程，背景色变化
	$(".studentCourseList ul li").click(function(){
		$(this).addClass("hasChoose").siblings("li").removeClass("hasChoose");
	});
	$(".resourceTitle ul li").click(function(){
		$(this).addClass("hasChoose").siblings("li").removeClass("hasChoose");
	})
	/*弹窗*/
	$('.tantit a,.cancelbtn,.confirmbtn').on('click',function(){
		$('.tanbox').fadeOut();
	});
	
	function chooseCourseType(type,courseId){
		var courseName = $(".hasChoose").html();
		var url = "${contextPath}/courseFile/dataBaseQuery?type="+type+"&is_cmn=2a57b8v48xc"+"&cos_id="+courseId+"&courseName="+courseName;
		var tool ='height=720,width=1500,top=40,left=200,menubar=yes, alwaysRaised=yes'
	 	window.open(url,'资料库',tool);
	}
	
	//下载
	function downLoad(fileId){  
		document.getElementById("ifile").src = "${contextPath}/test/downFile?fileId="+fileId; 
	}
</script>