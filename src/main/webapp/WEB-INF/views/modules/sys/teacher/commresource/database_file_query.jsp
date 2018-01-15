<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>资料库管理</title>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/alert.css" />	
	<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/ajaxfileupload.js"></script>
	<style type="text/css">
		html,body{height:100%;/* overflow:auto; */}
		*{margin:0;padding:0;font-size:12px;}
		.clearfix:after{
			display:block;
			content:"";
			clear:both;
			overflow:hidden;
		}
		.clearfix{zoom:1;}
		.databases{background:url('${ctxStatic}/images/databasebg3.jpg') center repeat-y;background-size:100% 100%;}
		.cate span,.addCateWindow span{cursor:pointer;font-size:20px;}
		.cate>.searchBtn{margin-left:20px;}
		.cate1{padding-left:20px;padding-top:20px;}
		.cate2,.cate3{padding-top:10px;}
		.cate2{margin-left:15px;display:none;}
		.cate3{margin-left:30px;display:none;}
		.cate4{margin-left:35px;display:none;}
		.addCateWindow{width:300px;height:90px;border:1px solid #ccc;background-color:#fff;position:fixed;top:30%;left:50%;margin-left:-45px;z-index:2;display:none;}
		.mask{width:100%;height:100%;position:fixed;top:0px;right:0px;bottom:0px;left:0px;background:rgba(0,0,0,0.5);z-index:1;display:none;}
		.databases{clear:both;}
		.cname{font-size:16px;}
		@media all and (min-width:1000px){
			.file_class_tree{width:29%;overflow-y:auto;height:1000px;}
			.file_class_tree,.show_file_query{float:left;padding-bottom:2000px;margin-bottom:-2000px;}
			.show_file_query{width:70%;border-left:1px solid #000;height:100%;overflow-y:auto;}
		}
		.menu{width:100px;display:none;border-left:1px solid #000;border-top:1px solid #000; background-color:#fdfac9;}
		.menu li{width:100%;height:30px;line-height:30px;text-align:center; cursor:pointer;border-right:1px solid #000;border-bottom:1px solid #000;list-style:none;}
		.menu li:hover{background-color:#fff;font-weight:bold;}
	</style>
</head>
<body class="databases">
<div class=" clearfix">
	<div class="file_class_tree">
		<%@ include file="file_class_tree.jsp"%>
	</div>
	<div class="show_file_query">
		<%@ include file="show_file_query.jsp"%>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
	//移动端页面位置跳转
	(function(){
		$('.cname').click(function(){
			var doc = document.body || document.documentElement;
			if(doc.clientWidth < 1000){
				setTimeout(function(){
					doc.scrollTop = document.getElementById('databaseTit').offsetTop;
				}, 100);
			}
		});
	})();
</script>