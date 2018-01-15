<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
    .clossBox{font-size:19px; color:#eaedf1;}
    .courseList{overflow:auto;}
    .alreadyCourse{background-color:#fff;}
    .alreadyCourseListBox{width:100%;}
    .alreadyCourseListBox,.resourceTitle{padding:10px 0 0px 10px;background-color:#fff;}
    .alreadyCourseList li,.resourceTitle ul li{float:left;height:30px;line-height:30px;padding:0 10px;cursor:pointer;}
	@media only screen and (max-width:768px){
		.videoPlayBox{width:300px;left:50%;margin-left:-150px;}
		.videoTit span{padding-left:120px;}
	}
</style>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/videoPlay.css" />
<!-- 栏目说明 -->
<div class="navExplain">
	<span>资源库</span>
</div>
<div class="studentCourse">
	<!--课程名称-->
	<div class="studentCourseList" >
		<!--选择已有课程-->
		<div class="alreadyCourse clearfix">
			<div class="alreadyCourseContent clearfix">
				<div class="alreadyCourseListBox  fl">
					<span style="float:left;display:inline-block;height:30px;line-height:30px;"><b>所有课程：</b></span>
					<div class="courseList">
						<ul class="alreadyCourseList clearfix">
							<c:forEach items="${courseList}"  var ="courseList">
								<c:if test="${courseList.courseId==courseId}">
									<li class="hasChoose on">${courseList.courseName}</li>
								</c:if>
								<c:if test="${courseList.courseId!=courseId}">
									<li ng-click="chooseCourse(${courseList.courseId});">${courseList.courseName}</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--课程下边的资源库-->
	<div class="studentResources">
		<div class="resourceTitle clearfix">
			<span style="float:left;display:inline-block;height:30px;line-height:30px;"><b>教师资源：</b></span>
			<ul>
				<c:if test="${courseType=='0'}"><li ng-click="chooseCourseType(0);" class="resourceChoose">试题库</li></c:if>
				<c:if test="${courseType!='0'}"><li ng-click="chooseCourseType(0);">试题库</li></c:if>
				<c:if test="${courseType=='1' || courseType==''}"><li class="resourceChoose" ng-click="chooseCourseType(1);">微课视频</li></c:if>
				<c:if test="${courseType!='1' && courseType!=''}"><li ng-click="chooseCourseType(1);">微课视频</li></c:if>
				<c:if test="${courseId!=''}"><li class="dataBase" onclick="chooseCourseType(1,'${courseId}');">教师课件</li></c:if>
			</ul>
		</div>
		<div class="resourceTitle clearfix">
			<span style="float:left;display:inline-block;height:30px;line-height:30px;"><b>系统资源：</b></span>
			<ul>
				<li class="dataBase" onclick="chooseCourseType(2,'');">案例库</li>
				<li class="dataBase" onclick="chooseCourseType(3,'');">图片库</li>	
				<li class="dataBase" onclick="chooseCourseType(4,'');">标准工作流程库</li>	
				<li class="dataBase" onclick="chooseCourseType(5,'');">法律法规及规范标准库</li>	
			</ul>
		</div>
		<c:if test="${courseType!='0'}">
		<div class="searchBeaf">
			<span class="mb8 mr5">
				<label for='fileName'>课件名称：</label>
				<input type="text" value="${fileName}" id="fileName" class="inpt" />
			</span>
			<span class="mb8">
				<a href="javascript:;" ng-click="searchFile('${courseId}','${courseType}');" class="defaultBtn">搜索</a>
			</span>	
		</div>
		<div class="allTable">
			<table cellpadding="0" cellspacing="0" >
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
							<td>${courseFileList.fileName}</td>
							<c:if test="${courseFileList.fileSize==''|| courseFileList.fileSize==null}">
								<td>——</td>
							</c:if>
							<c:if test="${courseFileList.fileSize!='' && courseFileList.fileSize!=null}">
								<td>${courseFileList.fileSize}</td>
							</c:if>
							<td class="introduce" style="display:none">${courseFileList.videoIntroduce}</td>
							<td>${courseFileList.createTime}</td>
							<td>
								<a href="javascript:;" filepath="${filePath}/${courseFileList.filePath}" filename="${courseFileList.fileName}" class="queryOnline">在线播放</a>|
								<a href="javascript:;" onclick="downLoad('${courseFileList.fileId}');">下载</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		</c:if>
		<c:if test="${courseType=='0'}">
		<div class="searchBeaf">
			<span class="mb8 mr5">
				<label for='searchToUser'>试题类型：</label>
				<select id="questionType" class="inpt" selectDict="${questionType}">
					<option value="">—请选择—</option>
				</select>
			</span>
			<span class="mb8">
				<a href="javascript:;" ng-click="searchQuestion('${courseId}','${courseType}');" class="defaultBtn">搜索</a>
			</span>
		</div>
		<div class="resourceContent allTable">
			<table cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<td>所属课程</td>
						<td>试题名称</td>
						<td>试题类型</td>
					</tr>
				</thead>
				<tbody id="coursefileList1">
					<c:forEach items="${pageInfo.list}"  var = "questionList">
						<tr>
							<td>${questionList.courseName}</td>
							<td>
								<a href="javascript:;" onclick="queryQuestionDetail(${questionList.testId});" >${fn:substring(questionList.questionTitle, 0, 15)}...</a>
							</td>
							<c:if test="${questionList.type=='11'}"><td>单选题</td></c:if>
							<c:if test="${questionList.type=='12'}"><td>多选题</td></c:if>
							<c:if test="${questionList.type=='21'}"><td>名词解释</td></c:if>
							<c:if test="${questionList.type=='22'}"><td>简答题</td></c:if>
							<c:if test="${questionList.type=='23'}"><td>案例分析</td></c:if>
							<c:if test="${questionList.type=='24'}"><td>翻译</td></c:if>
							<c:if test="${questionList.type=='25'}"><td>填空</td></c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		</c:if>
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
<script type="text/javascript" src="${ctxStatic}/js/dictionTranslate.js"></script>
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

	//系统资源颜色切换
	$(document).on('click','.dataBase', function(){
		$(this).addClass('hasChoose').siblings('li').removeClass('hasChoose');
	});
	if('${courseType}'=="0"){
		optionDict('questionType');   //试题题型的数据字典取值
		$("#questionType option[value='${selectType}']").attr("selected","selected");
	}
	function queryQuestionDetail(testId){
		  var url = "${contextPath}/commonResource/fetchQuestionDetail?testId="+testId;
		  var tool ='height=500,width=700,top=20,left=400,menubar=yes, alwaysRaised=yes'
		  window.open(url,'试题详情',tool);
	}
	
	//选择课程，背景色变化
	$(".studentCourseList ul li").click(function(){
		$(this).addClass("hasChoose").siblings("li").removeClass("hasChoose");
	})
	$('.resourceTitle ul li').each(function(){
		if($(this).hasClass('resourceChoose')){
			$(this).addClass('hasChoose').siblings().removeClass('hasChoose');
		}
	});
	
	function chooseCourseType(type,courseId){
		var courseName = $(".hasChoose").html();
		var url = "${contextPath}/courseFile/dataBaseQuery?type="+type+"&is_cmn=2a57b8v48xc"+"&cos_id="+courseId+"&courseName="+courseName;
		var tool ='height=720,width=1500,top=40,left=200,menubar=yes, alwaysRaised=yes'
	 	window.open(url,'资料库',tool);
	}
	
	//在线播放
	var totalTime;  //视频总时长
	var oVideo=$('.videoPlay').get(0);
	$('.queryOnline').click(function(){
		var adress = $(this).attr("filePath");
		var name = $(this).attr('fileName');
		$('.videoPlay').attr('src',adress);
		$('.videoTit span').text(name);
		$('.videoPlayBox').stop(true).fadeIn();
	})
	$('.closeBtn').on('click',function(){
		$('.videoPlay').attr('src','');
		$('.videoPlayBox').stop(true).fadeOut();
	});
	
	function downLoad(fileId){  
		document.getElementById("ifile").src = "${contextPath}/test/downFile?fileId="+fileId; 
	}
	
	//切换课程列表
	var oLeft=0;
	$('.prevbtn').on('click',function(){
		oLeft=$('.alreadyCourseList').css('left');
			$('.alreadyCourseList').css({'width':115*$('.alreadyCourseList li').length+'px'});
		if(parseInt($('.alreadyCourseList').css('left'))<=0){
			$('.alreadyCourseList').stop(true).animate({'left':parseInt(oLeft)+114*3+3+'px'},'slow');
		}else{
			$('.alreadyCourseList').stop(true).animate({'left':-114*$('.alreadyCourseList li').length+3+114*3+'px'},'slow');
		}
	});
	$('.nextbtn').on('click',function(){
		$('.alreadyCourseList').css({'width':115*$('.alreadyCourseList li').length+'px'});
		oLeft=$('.alreadyCourseList').css('left');
		if(Math.abs(parseInt($('.alreadyCourseList').css('left')))<(parseInt($('.alreadyCourseList').css('width'))-114*3)){
			$('.alreadyCourseList').stop(true).animate({'left':parseInt(oLeft)-114*3-3+'px'},'slow');
		}else{
			$('.alreadyCourseList').stop(true).animate({'left':0},'slow');
		}
	});
</script>