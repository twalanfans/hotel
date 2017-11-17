<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcuticon" type="image/ico" href="${ctxStatic}/img/favicon.ico">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/personalcenter1.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/common.css">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/pc.css" media="screen and (min-width:1000px)">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/pad.css" media="screen and (min-width:796px) and (max-width:999px)">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/mobile.css" media="screen and (max-width:795px)">
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css">
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="test/javascript" src="${ctxStatic}/js/hotel.js"></script>
<script src="${ctxStatic}/js/angular-min.js"></script>
<script src="${ctxStatic}/js/angular-ui-router.js"></script>
<script src="${ctxStatic}/js/echarts.min.js"></script>
<script language="javascript" src="${ctxStatic}/js/systemmanagement1.js"></script>
<title>路上科技用户首页</title>
</head>
<body ng-app="myapp">
<!--top-->
	<div class="sTop clearfix">
			<!--left-->
			<div class="sTopLeft">
				<%@ include file="left.jsp"%>
			</div>
			<!--right-->
			<div class="sTopRight drift">
				<!--nav-->
				<div class="nav clearfix">
					<%@ include file="head.jsp"%>
				</div>
				<div class="viewwin" id="viewwin">
					<%@ include file="default.jsp"%>
				</div>
				<div ui-view>
					
				</div>
			</div>
	</div>
	</body>
</html>
<script type="text/javascript">
	//点击【退出】跳转页面
	$(".sTop .sTopRight .nav .navBar ul li:eq(4)").click(function(){
		if(confirm("您确认退出吗?")){
		    window.location = "${contextPath}/logout";
		}  
	})
</script>