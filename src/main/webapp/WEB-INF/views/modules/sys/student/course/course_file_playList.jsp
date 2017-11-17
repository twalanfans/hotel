<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!--videoList-->
	<div class="videoList">
		<div class="videoListTitle fa fa-align-justify">课程列表</div>
		<!--视频分类-->
		<div class="videoClassify">
			<ul class="clearfix">
				<li class="listNa onVideo"><a href="javascript:;" title="${listName}">${listName}</a></li>
				<li class="br0"><a href="javascript:;">历史观看记录</a></li>
			</ul>
		</div>
		<div class="videoListContent visia" >
		<c:forEach items="${courseVideoList}" var="courseVideoList" begin="0" end="${courseNum}">
		<input type="text" id="courseId" style="display:none" value="${courseVideoList.courseId}">
			<ul id="hasWatchList">
				<li>
					<a href="javascript:;">
						<div class="videoIco fa fa-circle"></div>
						<div class="videoInfo">
							<div class="course"  onclick="playVideo(${courseVideoList.fileId})">
								<p>${courseVideoList.fileName}</p>
								<p class="fa fa-clock-o">${courseVideoList.videoTimeLong}</p>
								<p>${courseVideoList.videoIntroduce}</p>
							</div>
						</div>
					</a>
				</li>
			</ul>
		</c:forEach>
			<div class="videoMore" id="hasWatchBtn" onclick="loadMore();">加载更多</div>
		</div>
		
		<div class="videoListContent">
			<c:forEach items="${historyVideoList}" var="historyVideoList" begin="0" end="${courseNum}">
			<ul>
				<li>
					<a href="javascript:;">
						<div class="videoIco fa fa-circle"></div>
						<div class="videoInfo">
							<div class="course" onclick="playVideo(${historyVideoList.fileId})">
								<p>${historyVideoList.fileName}</p>
								<p class="fa fa-clock-o">${historyVideoList.videoTimeLong}</p>
								<p>${historyVideoList.videoIntroduce}</p>
							</div>
						</div>
					</a>
				</li>
			</ul>
		</c:forEach>
			<div class="videoMore" id="recommend" onclick="loadMore();">加载更多</div>
		</div>
	</div>
<script type="text/javascript">
	//选项卡切换
	$(".videoClassify ul li").click(function(){
		var videoIndex = $(this).index();
		$(".videoClassify ul li").removeClass('onVideo');
		$(this).addClass("onVideo");
		$(".videoListContent").eq(videoIndex).show().siblings(".videoListContent").hide();
	});
	
	function playVideo(fileId){
		$('.videoPlay').load("${contextPath}/course/videoPlay?fileId="+fileId);
	}
	
	function loadMore(){
		var courseId = $('#courseId').val();
		var listName = $('.listNa a').val();
		$('.videoPlayList').load("${contextPath}/course/queryCourseVideoList?courseId="+courseId+"&listName="+listName+"&courseNum=${courseNum}");
	}
</script>