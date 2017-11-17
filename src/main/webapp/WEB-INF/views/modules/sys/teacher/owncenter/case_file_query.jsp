<!-- case_file_query.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<!--tkd-->
		<title>资料文档列表</title>
		<meta name="Keywords" content="">
		<meta name="Description" content="">
		<!--css-->
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/common.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css" />
	    <link rel="stylesheet" href="${ctxStatic}/css/alert.css" />
		<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
	</head>
	<body>
		<div class="sentmessagebox admin_sentuser_message">
			<div class="searchCondition">
				<form action="${contextPath}/courseStudent/queryCaseFilePage" id="Search" method="post">
					<div class="condition">
						<label>文件名：</label>
						<input type="text" name="fileName" value="${fileName}"/>
					</div>
					<input type="submit" value="搜索" class="searchBtn"/>
				</form>
			</div>
			<div class="allTable">
				<table cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<td>文件名</td>
							<td>文件大小</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageInfo.list}"  var ="attachFileList">
						<tr>
							<td><a href="javascript:;" class="showDetail">${attachFileList.fileName}</a></td>
							<td class="introduce" style="display:none">${attachFileList.fileName}</td>
							<td>${attachFileList.fileSize}</td>
							<td><a href="javascript:;" onclick="downLoadAtt('${attachFileList.attachId}');">下载</a></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	<div class="pagenum pageBar clearfix">
		<ul class="otherInfo">
			<li><label>共&nbsp;${pageInfo.total}&nbsp;条&nbsp;&nbsp;每页&nbsp;${pageInfo.pageSize}&nbsp;条&nbsp;&nbsp;当前第&nbsp;${pageInfo.pageNum}&nbsp;页</label></li>
		</ul>
		<div class="epages">
           	<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}"><span>首页</span><span>上一页</span></c:if>
           	<c:if test="${pageInfo.pageNum != 1 && pageInfo.pages > 1}">
            	<a href="#" onclick="javascript:window.location.href='${contextPath}/courseStudent/queryCaseFilePage?page=1&fileName=${fileName}'">首页</a>
            	<a href="#" onclick="javascript:window.location.href='${contextPath}/courseStudent/queryCaseFilePage?page=${pageInfo.pageNum-1}&fileName=${fileName}'">上一页</a>
           	</c:if>
           	<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span>下一页</span><span>尾页</span></c:if>
		    <c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
            	<a href="#" onclick="javascript:window.location.href='${contextPath}/courseStudent/queryCaseFilePage?page=${pageInfo.pageNum+1}&fileName=${fileName}'">下一页</a>
            	<a href="#" onclick="javascript:window.location.href='${contextPath}/courseStudent/queryCaseFilePage?page=${pageInfo.pages}&fileName=${fileName}'">尾页</a>
           	</c:if>
         </div>
	</div>
</div>
<iframe id="ifile" style="display:none"></iframe> 
<!-- 简介展示详情 -->
<ul class="showDetailBox clearfix"></ul>
<div class="tanbox">
	<div class="shadow"></div>
	<div class="tantextbox">
	<div class="tantit clearfix"><h4 class="fl">发送提示</h4><a href="javascript:;" class=" fa fa-times-circle-o fr"></a></div>
	<p class=" alerttext clearfix" ><i class="fa fa-check-circle fl"></i><span class="fl"></span></p>
	<div class="actbtn clearfix"><a href="javascript:;" class="cancelbtn">关闭</a><a href="javascript:;" class="confirmbtn">确定</a></div>
	</div>
</div>
<!-- 遮罩层 -->
<div class="clear"></div>
</body>
<script type="text/javascript">
	/*鼠标划过课件名称显示课件简介*/
	$('.showDetail').hover(function(e){
		$('.showDetailBox').empty();
		$('.showDetailBox').append(
			'<li class="fl">课件简介：'+$(this).parents("td").siblings(".introduce").html()+'</li>'
		);
		$('.showDetailBox').css({'left':e.clientX+20+'px','top':e.clientY+'px'});
		$('.showDetailBox').stop().fadeIn();
	},function(){
		$('.showDetailBox').stop().fadeOut();
		}
	);	

//弹窗
$('.tantit a,.cancelbtn').on('click',function(){
	$('.tanbox').fadeOut();
});
$('.confirmbtn').on('click',function(){
	$('.tanbox').fadeOut();
	$('.shadow').fadeOut();
});

function downLoad(fileId){  
	var isSynchronized = false;
	 $.ajax({
	        type: "post",
	        dataType: "json",
	        async:false,
	        url: "${contextPath}/checker/getFileSize",
	        data: {"fileId":fileId},
	        success: function(data) {
	        	if(data != 0){
	        		isSynchronized = true;
	        	}
	        }
	 });
	 if(isSynchronized){
		 document.getElementById("ifile").src = "${contextPath}/checker/downLoadFile?fileId="+fileId; 
	 }else{
		 $('.alerttext span').html('未找到该文件,可能未同步,请稍后尝试！');
		 $('.tanbox').fadeIn();
	 }
}
</script>
</html>