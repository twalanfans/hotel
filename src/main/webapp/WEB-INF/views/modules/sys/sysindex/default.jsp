<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<div ng-app="myapp">
				<!--test-->
				<div class="test">
					<!--testTop-->
					<div class="testTop">
						<!--name-->
						<div class="testName">酒店管理仿真实训教学软件</div>
						<!--position-->
						<div class="position drift">
							<ul>
								<li>您现在所在位置：</li>
								<li><i class="fa fa-home"></i><a href="#">首页</a> > </li>
								<li><a href="#">面包屑导航</a> > </li>
								<li><a href="#">面包屑导航</a></li>
							</ul>
						</div>
						</div>
					<!--testBottom-->
					<div class="testBottom">
						<div  ui-view id="form"></div>
					</div>
				</div>
</div>

<!-- <script src="js/angular-min.js"></script>
<script src="js/angular-ui-router.js"></script>
<script src="js/echarts.min.js"></script>
<script language="javascript" src="js/systemmanagement1.js"></script> -->