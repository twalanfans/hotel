<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">	
		<title>教师系统</title>
		<!-- 图标 -->
		<link rel="shortcut icon" type="image/ico" href="${ctxStatic}/images/favicon.ico" />
		<!-- css -->
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css" />
		<!-- 日历插件 -->
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/calendar.css" />
		<!-- jquery -->
		<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js" ></script>
		<!-- echarts -->
		<script type="text/javascript" src="${ctxStatic}/js/echarts.min.js"></script>
	</head>
	<body class="ng-cloak" ng-cloak>
		<!--外层-->
		<div class="wrap">
			<!--左侧-->
			<div class="wrapLeft">
				<!--logo-->
				<div class="logo">
					<a ui-sref="welcometeacher" class="router">
						<img src="${ctxStatic}/images/logo.png" alt="logo" />
					</a>
				</div>
				<!--下拉导航-->
				<ul class="nav">
					<li>
						<a href="javascript:;" class="tmp slide"><i class="fa fa-check-square-o"></i>备课教学</a>
						<ul class="navList">
							<li><a ui-sref="teacherCourse({pageNum : 1})" ui-sref-opts="{reload:true}" class="router">课程资料</a></li>
							<li><a ui-sref="zsdEdit" ui-sref-opts="{reload:true}" class="router">知识点管理</a></li>
							<li><a ui-sref="ceshiQuestion" ui-sref-opts="{reload:true}" class="router">我的试题</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:;" class="tmp slide"><i class="fa fa-check-square-o"></i>测评解答</a>
						<ul class="navList">
							<li><a ui-sref="quesAnswOnline({showNum : '', title : ''})" ui-sref-opts="{reload:true}" class="router">在线解答</a></li>
							<li><a ui-sref="makeTestPaper({pageNum : 1})" ui-sref-opts="{reload:true}"  class="router">我的试卷</a></li>
							<li><a ui-sref="teacherPaperGrade({pageNum : 1, studentName : '', status : '2'})" ui-sref-opts="{reload:true}" class="router">试卷判分</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:;" class="router tmp slide"><span><i class="fa fa-hacker-news"></i>资源库</span></a>
						<ul class="navList">
							<li><a ui-sref="fetchCommonCourseList({courseName : '', pageNum : 1})" ui-sref-opts="{reload:true}" class="router">公共资源</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:;" class="router tmp slide"><span><i class="fa fa-hacker-news"></i>职业考试</span></a>
						<ul class="navList">
							<li><a ui-sref="criticismService" ui-sref-opts="{reload:true}" class="router">考试资料</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:;" class="tmp slide"><i class="fa fa-check-square-o"></i>我的中心</a>
						<ul class="navList">
							<li><a ui-sref="teacherDataAnalysis" ui-sref-opts="{reload:true}" class="router">数据分析</a></li>
							<li><a ui-sref="studentGroup" ui-sref-opts="{reload:true}" class="router">群组学生管理</a></li>
							<li><a ui-sref="personalEdit" ui-sref-opts="{reload:true}" class="router">个人信息</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:;" class="tmp slide"><i class="fa fa-check-square-o"></i>短消息</a>
						<ul class="navList">
							<li><a ui-sref="receiveMessage({pageNum : 1})" ui-sref-opts="{reload:true}" class="router">收件箱</a></li>
							<li><a ui-sref="sendMessage({pageNum : 1})" ui-sref-opts="{reload:true}" class="router">发件箱</a></li>
						</ul>
					</li>
				</ul>
			</div>
			<!--右侧-->
			<div class="wrapRight">
				<!--顶部-->
				<div class="header clearfix">
					<!--伸缩按钮-->
					<div class="navBar"><i class="fa fa-bars"></i></div>
					<!--个人设置或退出选择菜单-->
					<%@ include file="message_notice_tip.jsp"%>
				</div>
				<!--主体部分-->
				<div ui-view class="main" id="main" ></div>
			</div>
		</div>
		<!-- js -->
		<script type="text/javascript" src="${ctxStatic}/js/main.js"></script>
		<script type="text/javascript">
			$('.main').height($(window).height()-60);
			var publicPath = '${ctxStatic}/js/';
			var ctx = '${ctx}';
		</script>
		<script type="text/javascript" src="${ctxStatic}/js/require.js" data-main="${ctxStatic}/js/util/teacherConfig.js"></script>
	</body>
</html>