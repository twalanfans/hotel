<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<style type="text/css">
	.videoClass{padding:15px;}
	.classTitle{font-size:14px;color:#fff;}
	.classContent{width:100%;}
	.classContent a{display:inline-block;margin-right:15px;height:30px;line-height:30px;font-size:14px;color:#fff;padding:0 10px;}
	.videoBox .videoPlayList{overflow-y:auto;padding:10px;background-color:rgba(0,0,0,0.5);}
	.videoBox .videoPlayList a{font-size:14px;color:#fff;}
	.videoBox .videoPlayList a:hover{color:#65cea7;}
	.videoList{overflow:auto;}
	.videoIco{float:left;margin-right:10px;}
	.videoList ul li{margin-bottom:25px;}
	.courseStudyNote{background-color:#fff;padding:15px;}
	.videoListTit{padding-bottom: 2px; line-height: 30px;text-align: center;font-size: 14px;color: #FF920B; border-bottom: 2px solid #FF920B;margin-bottom:20px;cursor: pointer;position:relative;}
	.videoListTitList{
		line-height: 30px;text-align: center;font-size: 14px;color:#fff;position:absolute;
		left:0;top:100%;display:none;width:100%;background-color:rgba(0,0,0,0.8);
	}
	.videoListTitList li:hover{color:#FF920B;background-color:rgba(204, 204, 204, 0.4);}
	.videoList .videoListTitList li{margin-bottom:0;}
	@media only screen and (min-width:769px){
		.videoBox .videoPlay{width:49%;padding-left:1%;float:left;}
		.videoBox .videoPlayList{float:right;width:47%;height:100%;}
	}
	@media only screen and (max-width:768ox){
		.videoBox .videoPlay{width:99%;padding-left:1%;}
		.videoBox .videoPlayList{width:99%;height:100%;}
	}
	#videoListMore{display:none;text-align:center;}
	.classTitle a{color:#65cea7;}
	.classTitle a:hover{text-decoration:underline;}
</style>
<!-- 栏目说明 -->
<div class="navExplain">
	<span>课程学习</span>
</div>
<div style="background-color:#393E46;">
	<!--视频栏目分类-->
	<div class="videoClass clearfix">
		<div class="classTitle">
			<b>选择观看对应课程下的视频</b>
			<a href="javascript:;" onclick="chooseCourse();">历史观看记录</a></div>
		<div class="classContent clearfix">
			
		</div>
	</div>
	<!--课程视频列表-->
	<div class="videoBox clearfix">
		<!--videoPlay-->
		<div class="videoPlay">
			<video width="100%"  controls="controls" autoplay  src=""  poster=""  id="video"></video>
		</div>
		<div class="videoPlayList">
			<div class="videoList">
				<h4 class="videoListTit">
					<div>点击选择观看对应课程下的视频</div>
					<ul class=" videoListTitList">
						<c:forEach items="${courseList}" var="courseList">
							<li onclick="chooseCourse('${courseList.courseId}',this);">
								${courseList.courseName}
							</li>
						</c:forEach>
					</ul>
				</h4>
				<ul id="videoList"></ul>
				<span class="defaultBtn" id="videoListMore">加载更多</span>
			</div>
		</div>
	</div>
</div>
<!-- 课堂笔记 -->
<div class="courseStudyNote">
	<%@ include file="course_video_studynote.jsp"%>
</div>
<script type="text/javascript">
	//检测video播放器高度
	function videoHeight(){
		var vHeight = $('#video').height();
		return vHeight;
	}
	$('.videoList').height(videoHeight());
	
	window.onresize = function(){
		if(window.clientWidth>768){
			$('.videoList').height(videoHeight());
		}
	}
	
	//切换课程
	$('.classContent a').click(function(){
		$(this).addClass('hasChoose').siblings('a').removeClass('hasChoose');
	});

	//当前课程下的视频列表
	var videoListData = '';
	//当前记录索引
	var num = 0;
	//记载次数
	var count = 1;
	//视频列表记录总数
	var card = '';
	//课程ID
	var courseId = '';
	//视频文件ID
	var fileId = '';
	//判断是否已经选择课程
	var isChooseCourse = false;
	
	//创建li标签
	var videoList = document.getElementById('videoList');
	function createLi(j){
		var oLi = document.createElement('li');
		var oA = document.createElement('a');
			oA.href='javascript:;';
			oA.setAttribute('filePath', videoListData[j].filePath);
			oA.setAttribute('videoImg', videoListData[j].videoImg);
			oA.setAttribute('class', 'thisVideo');
			oA.setAttribute('fileId', videoListData[j].fileId);
		var oDiv1 = document.createElement('div');
			oDiv1.setAttribute('class', 'videoIco fa fa-film');
		var oDiv2 = document.createElement('div');
			oDiv2.setAttribute('class', 'videoInfo');
		var oDiv3 = document.createElement('div');
			oDiv3.setAttribute('class', 'course');
		var oP1 = document.createElement('p');
			oP1.innerText = videoListData[j].fileName;
		
		oA.appendChild(oDiv1);
		oA.appendChild(oDiv2);
		oDiv2.appendChild(oDiv3);
		oDiv3.appendChild(oP1);
		oLi.appendChild(oA);
		videoList.appendChild(oLi);
	}
	
	//视频列表
	chooseCourse();
	$('#video').attr('src', '${filePath}'+'/'+$('.videoList li a').attr('filePath'));
	$('.videoListTit').on('click',function(){
		$('.videoListTitList').stop(true).slideToggle();
	});
	function chooseCourse(cId,obj){
		var _this=obj;
		videoList.innerHTML = '';
		//当前课程下的视频列表
		videoListData = '';
		//当前记录索引
		num = 0;
		//记载次数
		count = 1;
		//视频列表记录总数
		card = '';
		//获取课程ID
		courseId = cId;
		//加载课程数据
		if(courseId){
			$.ajax({
				url : '${contextPath}/course/queryCourseVideoList',
				type : 'post',
				data : {
					courseId : courseId
				},
				async : false,
				dataType : 'json',
				success : function(result){
					isChooseCourse = true;
					videoListData = result;
					card = videoListData.length;
					getVideoData();
					$('.videoListTit>div').text($(_this).text());
				}
			});
		}else{
			$.ajax({
				url : '${contextPath}/course/courseVideoHistory',
				type : 'post',
				async : false,
				dataType : 'json',
				success : function(result){
					isChooseCourse = false;
					videoListData = result;
					card = videoListData.length;
					getVideoData();
					$('.videoListTit>div').text('点击选择观看对应课程下的视频');
				}
			});
		}
		
		//加载课堂笔记
		$('.courseStudyNote').load("${contextPath}/course/queryCourseNotes?courseId="+courseId);
		
		//显示加载更多按钮
		$('#videoListMore').css({display:'block'});
	}
	
	//加载视频数据
	function getVideoData(){
		for(var i=num; i<count*10; i++){
			if(num<card){
				createLi(num);
				num += 1;
				$('#videoListMore').text('加载更多');
			}else{
				$('#videoListMore').text('亲，没有更多视频了，赶紧让老师去上传吧！');
			}
		}
	}
	
	//加载更多
	var videoListMore = document.getElementById('videoListMore');
	videoListMore.addEventListener('click', function(){
		count += 1;
		getVideoData();
	});
	
	//点击观看对应的视频
	$('body').on('click', '.thisVideo', function(){
		//改变视频文件ID全局变量
		fileId = $(this).attr('fileId');
		$('#video').attr('src', '${filePath}'+'/'+$(this).attr('filePath'));
		$('.thisVideo').css({color:'#fff'});
		$(this).css({color:'#65cea7'});
	});

	//记录视频播放时长
	var oDate = new Date();
	var oVideo = document.getElementById("video");
	var mark = "play";
	var timer;	
	var totalTime;  //视频总时长
	var studyTime = 0;
	var studyTimer;  //课程在线播放时长
	var logId = "";
	
	oVideo.addEventListener("canplay",function(){
		totalTime = formatTime(oVideo.duration);  
	});
	
	oVideo.addEventListener("pause",function(){   //暂停视频监听
		if(!courseId){
			return false;
		}
		mark = "pause";
		clearInterval(timer);
		clearInterval(studyTimer);
	})
	oVideo.addEventListener("play",function(){   //播放视频
		if(!courseId){
			return false;
		}
		mark = "play";
		studyTimer = setInterval(function(){
			studyTime+=1;
		},1000);
		timer = setInterval(function(){
			if(mark=="play"){
				var currentTime = formatTime(oVideo.currentTime);
				$.ajax({
					url : "${contextPath}/student/courseVideoStudyLog",  //后台记录视频播放时长
					data : {
						"logId":logId,  //记录播放日志Id
						"courseId":courseId,
						"fileId":fileId,
						"videoPlayTime" :  currentTime,   //播放进度时间
						"totalTimelong" : totalTime,
						"studyTimelong" :  studyTime		//实际观看时长
					},
					success : function(data){
						if(data!="error"){
							logId = data;
						}
					}
				});
			}
		}, 10000);
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
</script>