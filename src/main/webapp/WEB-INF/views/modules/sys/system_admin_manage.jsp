<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">	
		<title>管理员系统</title>
		<!-- 图标 -->
		<link rel="shortcut icon" type="image/ico" href="${ctxStatic}/images/favicon.ico" />
		<!-- css -->
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css" />
		<!-- jquery -->
		<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js" ></script>
	</head>
	<body class="ng-cloak" ng-cloak>
		<!--外层-->
		<div class="wrap">
			<!--左侧-->
			<div class="wrapLeft">
				<!--logo-->
				<div class="logo">
					<a ui-sref="home" ui-sref-opts="{reload:true}" class="router">
						<img src="${ctxStatic}/images/logo.png" alt="logo" />
					</a>
				</div>
				<!--下拉导航-->
				<ul class="nav">
					<li>
						<a href="javascript:;" class="tmp slide"><i class="fa fa-check-square-o"></i>机构管理</a>
						<ul class="navList">
							<li><a ui-sref="department" ui-sref-opts="{reload:true}" class="router">班级管理</a></li>
							<li><a ui-sref="usermanagement({pageNum : 1, userName : '', loginName : '', userType : ''})" ui-sref-opts="{reload:true}" class="router">用户管理</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:;" class="tmp slide"><i class="fa fa-check-square-o"></i>课件管理</a>
						<ul class="navList">
							<li><a ui-sref="reviewattachment({pageNum : 1, status : '', fileName : ''})" ui-sref-opts="{reload:true}" class="router">课件审核</a></li>
							<li><a ui-sref="filemanagement({pageNum : 1, fileName : '', status : '0'})" ui-sref-opts="{reload:true}" class="router">所有课件</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:;" class="tmp slide"><i class="fa fa-check-square-o"></i>问答管理</a>
						<ul class="navList">
							<li><a ui-sref="questionMana({pageNum : 1 , createUser : ''})" ui-sref-opts="{reload:true}" class="router">提问管理</a></li>
							<li><a ui-sref="commentMana({pageNum : 1})" ui-sref-opts="{reload:true}" class="router">评论管理</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:;" class="tmp slide"><i class="fa fa-check-square-o"></i>短消息</a>
						<ul class="navList">
							<li><a ui-sref="receiveMessage({pageNum : 1})" ui-sref-opts="{reload:true}" class="router">收件箱</a></li>
							<li><a ui-sref="sendMessage({pageNum : 1})" ui-sref-opts="{reload:true}" class="router">发件箱</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:;" class="router tmp slide"><span><i class="fa fa-hacker-news"></i>系统公告</span></a>
						<ul class="navList">
							<li><a ui-sref="receiveNotice({pageNum : 1})" ui-sref-opts="{reload:true}" class="router">发布公告</a></li>
						</ul>
					</li>
					<li>
						<a href="javascript:;" class="router tmp slide"><span><i class="fa fa-hacker-news"></i>激活码</span></a>
						<ul class="navList">
							<li><a ui-sref="schoolManageQuery({pageNum : 1})" ui-sref-opts="{reload:true}" class="router">学校列表</a></li>
							<!-- <li><a ui-sref="queryAdminCode({pageNum : 1})" ui-sref-opts="{reload:true}" class="router">管理员列表</a></li> -->
							<li><a ui-sref="queryCodeListPage({pageNum : 1})" ui-sref-opts="{reload:true}" class="router">激活码生成</a></li>
							<li><a ui-sref="queryStudentCodeListPage({pageNum : 1})" ui-sref-opts="{reload:true}" class="router">学籍号管理</a></li>
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
				<div ui-view class="main" id="main"></div>
			</div>
		</div>
		<!-- js -->
		<script type="text/javascript" src="${ctxStatic}/js/main.js"></script>
		<script type="text/javascript">
			$('.main').height($(window).height()-60);
			var publicPath = '${ctxStatic}/js/';
		</script>
		<script type="text/javascript" src="${ctxStatic}/js/require.js" data-main="${ctxStatic}/js/util/adminConfig.js"></script>
	</body>
</html>