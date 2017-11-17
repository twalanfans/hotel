<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%-- <script type="text/javascript" src="${ctxStatic}/js/defaultview.js"></script> --%>
<!--logo-->
<div class="logo">
	<img src="${ctxStatic}/images/logo.png">
</div>
<!--userState-->
<div class="userPanel clearfix">
	<div class="userPic">
		<img src="${ctxStatic}/images/userPic.jpg">
	</div>
	<div class="userInfo">
		<div class="userName">游客</div>
		<div class="userState">在线</div>
	</div>
</div>
<!--search-->
<div class="search clearfix">
	<form method="post">
		<input type="text" placeholder="查找" id="sTxt">
		<input type="button" id="sBtn">
	</form>
</div>

<div class="menu">
					<div class="menuTitle">主菜单</div>
					<div class="menuContent">
						<ul class="menuList">
						<c:set var="firstMenu" value="true"/>
						
						<c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1'}">
							<li>
								<span class="fa fa-coffee">${menu.name}</span>
								<ul>
									
									<c:if test="${(menu2.parent.id == menu.id) &&menu.isShow eq '1'}">
									<li><a href="" onClick="javaScript:showView( ' ${menu2.href}')" class="fa fa-circle-o">${menu2.name}</a></li>
									</c:if>
									
								</ul>
							</li>
						</c:if>
						
						</ul>
					</div>
					<input type="button" value="查看" onclick="javaScript:look()">
</div>		   	
<script type="text/javascript">
/* var app=angular.module('myapp',['ui.router']);
app.config(['$stateProvider','$urlRouterProvider',function($stateProvider,$urlRouterProvider){
	$urlRouterProvider.otherwise('home');
	$stateProvider.state('home',{
		url:'${contextPath}/defaultView',	
		tmplateUrl:'${contextPath}/logout'
	})
	.state('view',{
		url:'${contextPath}/defaultView',	
		tmplateUrl:'${contextPath}/logout'
	})
}]) */

function showView(url){
	if(url!=null){
		url = url.replace(/\s+/g,""); 
		$("#viewwin").load("${contextPath}/"+url);
	}
}
</script>