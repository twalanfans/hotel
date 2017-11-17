<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<title>文件列表</title>
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
	<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/ajaxfileupload.js"></script>
	<style type="text/css">
		.saveResult{
			line-height:175px;
			background:url(${ctxStatic}/images/loading.gif) center 30px no-repeat  
			rgba(0,0,0,0.2);
		}
		.defautBack{background:url("${ctxStatic}/images/database1.jpg") center no-repeat;	width:100%;height:100%;background-size: 100% 100%;}
		.noStudyPlan{padding:40px;}
		.noStudyPlan .noPlayPrompt span{display:block;height:48px;line-height:48px;float:left;}
		.noStudyPlan .noPlayPrompt span:nth-child(2){font-size:18px;margin-left:10px;display:inline;}
		.noStudyPlan p{padding:20px 0px;color:#666;}
		.uploadFileWindow{width:100%;max-width:300px;border:1px solid #ccc;background-color:#fff;display:none;position:fixed;top:32%;left:46%;margin-left: -140px;}
		.uploadFileTitle{height:30px;line-height:30px;padding-left:10px;background-color:#76bf64;color:#fff;font-size:12px;}
		.uploadFileContent>p{width:270px;margin:20px auto; font-size:12px;}
		.uploadFileContent>p .searchBtn{margin-left:20px;}
		.addImg{font-size:100px;color:#ccc}
		.databaseTit{font-size:16px;padding:20px 10px;}
	</style>
</head>
<body>

<div>
	<h3 class="databaseTit" id="databaseTit">资料库分类导航：<a href="javascript:;">${classFilePath}</a></h3>
	<c:if test="${fileNum=='0' && classId!='' && fileName==''}">
		<div class="noStudyPlan">
			<div class="noPlayPrompt clearfix">
				<span><img src="${ctxStatic}/images/noplan.png" width="48" height="48" /></span>
				<span>文件列</span>
			</div>
			<p>抱歉，该文件夹下尚未发现文件！您可以：</p>
			<c:if test="${isShow=='yes'}">
			<p>
				<a href="javascript:;" onclick="uploadFile();" class="commonBtn">上传文件</a>
			</p>
			</c:if>
	 	</div>
	</c:if>
	<c:if test="${(fileName!='' && classId!='' && dataType!='3')||dataType!='3' && fileNum!='0'}">
		<div class="sentmessagebox admin_sentuser_message p10_6">
			<div class="searchBeaf">
				<span class="mb8 mr5">
					<label>文件名：</label>
					<input type="text" name="fileName" id="searchFileName" class="inpt" value="${fileName}"/>
				</span>
				<span class="mb8 mr5">
					<input type="button" onclick="search();" value="搜索" />
				</span>
				<c:if test="${isShow=='yes'}">
				<span class="mb8 mr5" >
					<a href="javascript:;" onclick="deleteFile();" class="defaultBtn">删除</a>
					<a href="javascript:;" onclick="uploadFile();" class="defaultBtn">上传文件</a>
				</span>
				</c:if>
			</div>
			<div class="allTable">
				<table cellpadding="0" cellspacing="0">
					<thead>
						<tr>
							<td>
								<label for="selallbtn1">全选&nbsp;&nbsp;</label><input type="checkbox" id="selallbtn1" style="display:none">
							</td>
							<td>文件名称</td>
							<td>文件大小</td>
							<td>创建时间</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageInfo.list}"  var="fileList" >
						<tr>
							<td><input type="checkbox" class="selrolebtn1" name="fileId" value="${fileList.attachId}"></td>
							<td><a href="#" onclick="downLoad(${fileList.attachId});">${fileList.fileName}</a></td>
							<td>${fileList.fileSize}</td>
							<td><fmt:formatDate value="${fileList.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<!-- 翻页 -->
			<div class="pageList">
				<span>共<b>${pageInfo.total}</b>条</span>
				<span>每页<b>${pageInfo.pageSize}</b>条</span>
				<span>当前第<b>${pageInfo.pageNum}</b>页</span>
				<span>
					<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}"><span>首页</span><span>上一页</span></c:if>
		          	<c:if test="${pageInfo.pageNum != 1 && pageInfo.pages > 1}">
		            	<a href="#" onclick="pageTurn('1')">首页</a>
		            	<a href="#" onclick="pageTurn(${pageInfo.pageNum-1})">上一页</a>
	            	</c:if>
	            	<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span>下一页</span><span>尾页</span></c:if>
		          	<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
		            	<a href="#" onclick="pageTurn(${pageInfo.pageNum+1})">下一页</a>
		            	<a href="#" onclick="pageTurn(${pageInfo.pages})">尾页</a>
	            	</c:if>
				</span>
			</div>
		</div>
	</c:if>
	<c:if test="${classId!='' && dataType=='3' && fileNum!='0'}">
		<div style="padding-left:10px;">
			<c:if test="${isShow=='yes'}">
			<span>
				<label for="selallbtn1" class="commonBtn">全选</label>
				<input type="checkbox" id="selallbtn1" style="display:none" />
			</span>
				<span class="mb8 mr5">
					<a href="javascript:;" onclick="uploadFile();" class="commonBtn">上传图片</a>
				</span>
				<span class="mb8">
					<a href="javascript:;" onclick="deleteFile();" class="commonBtn">删除所选图片</a>
				</span>
			</c:if>
			<ul class="clearfix uploadimglist">
				<c:forEach items="${pageInfo.list}"  var="fileList" >					
						<li class="fl">
							<img src="${filePath}/${fileList.filePath}"  class="imgDetail" title="查看大图"/>
							<input type="checkbox" class="selrolebtn1" name="fileId" value="${fileList.attachId}">
							<a href="javascript:;" onclick="downLoad(${fileList.attachId});" class="uploadimgname" title="下载图片">${fileList.fileName}</a>
						</li>
				</c:forEach>
			</ul>
			<!-- 翻页 -->
			<div class="pageList">
				<span>共<b>${pageInfo.total}</b>条</span>
				<span>每页<b>${pageInfo.pageSize}</b>条</span>
				<span>当前第<b>${pageInfo.pageNum}</b>页</span>
				<span>
					<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}"><span>首页</span><span>上一页</span></c:if>
		          	<c:if test="${pageInfo.pageNum != 1 && pageInfo.pages > 1}">
		            	<a href="#" onclick="pageTurn('1')">首页</a>
		            	<a href="#" onclick="pageTurn(${pageInfo.pageNum-1})">上一页</a>
	            	</c:if>
	            	<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span>下一页</span><span>尾页</span></c:if>
		          	<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
		            	<a href="#" onclick="pageTurn(${pageInfo.pageNum+1})">下一页</a>
		            	<a href="#" onclick="pageTurn(${pageInfo.pages})">尾页</a>
	            	</c:if>
				</span>
			</div>
		</div>
	</c:if>
</div>
<div class="saveResult"></div>
<div class="tanbox">
		<div class="shadow"></div>
		<div class="tantextbox">
			<div class="tantit clearfix"><h4 class="fl">温馨提示</h4><a href="javascript:;" class=" fa fa-times-circle-o fr closeBtn"></a></div>
			<p class=" alerttext clearfix" ><i class="fa fa-exclamation-circle fl"></i><span class="fl"></span></p>
			<div class="actbtn clearfix"><a href="javascript:;" class="cancelbtn">取消</a><a href="javascript:;" class="confirmbtn">确定</a></div>
			<div class="actbtn clearfix hide actbtn1"><a href="javascript:;" class="cancelbtn1" onclick="refresh();">关闭</a><a href="javascript:;" class="confirmbtn1" onclick="refresh();">确定</a></div>
		</div>
</div>
<!-- 上传文件窗口 -->
<c:if test="${isShow=='yes'}">
<div class="uploadFileWindow">
	<div class="uploadFileTitle">附件上传</div>
	<div class="uploadFileContent">
		<c:if test="${dataType!='3'}">
			<p><input type="file"  id="wordFile" name="wordFile"/></p>
		</c:if>
		<c:if test="${dataType=='3'}">
			<p><input type="file"  id="imgFile" name="imgFile" class="inpt" /></p>
		</c:if>
		<p>
			<a href="javascript:;" class="commonBtn" onclick="confirmUpload();">开始上传</a>
			<a href="javascript:;" class="commonBtn" onclick="closeUpload();">取消上传</a>	
		</p>
	</div>
</div>
</c:if>
<iframe id="ifile" style="display:none"></iframe> 
</body>
<script type="text/javascript">
$(document).scrollTop(0);
function refresh(){
	$('.show_file_query').load("${contextPath}/courseFile/showFileDetailPage?classId=${classId}"+"&dataType=${dataType}"+"&filePath=${classFilePath}"+"&fileName=${fileName}"+"&u-si=${userId}");
}
function pageTurn(pageNum){
	$('.show_file_query').load("${contextPath}/courseFile/showFileDetailPage?page="+pageNum+"&classId=${classId}"+"&dataType=${dataType}"+"&filePath=${classFilePath}"+"&fileName=${fileName}"+"&u-si=${userId}");
}

function search(){
	var fileName = document.getElementById("searchFileName").value;
	$('.show_file_query').load("${contextPath}/courseFile/showFileDetailPage?classId=${classId}"+"&dataType=${dataType}"+"&filePath=${classFilePath}"+"&fileName="+fileName+"&u-si=${userId}");
}

$('.imgDetail').click(function(){
	var adress = $(this).attr("src");
	var tool ='height=567,width=1000,top=20,left=400,menubar=yes, alwaysRaised=yes';
 	window.open(adress,'图片浏览',tool);
})

function uploadFile(){
	$('.uploadFileWindow').fadeIn();
}
function closeUpload(){
	$('.uploadFileWindow').fadeOut();
}

function confirmUpload(){
	$('.uploadFileWindow').hide();
	if('${dataType}'=="3"){			//案例库
		if($('#imgFile').val()==""){
			alert("您还未选择图片！")
			return false;
		}
		if(!$('#imgFile').val().match(/.jpg|.png|.gif|.bmp/i)){
			alert("图片库只能上传图片文件！");
			return false;
		}
		$(".saveResult").html('文件上传中，请稍后...');
		$(".saveResult").fadeIn();
			$.ajaxFileUpload({
				type : "post",
				url: "${contextPath}/courseFile/uploadAttachFile",
				secureuri: false,
				fileElementId: "imgFile",
				dataType: 'json',
				data : {
					dataType : "3",
					classId :'${classId}',
					filePath : '${classFilePath}'
				},
				success: function(data){
					$(".saveResult").fadeOut();
					if(data.status=="success"){
					//	alert("上传成功！");		
						refresh();
					}else{
						alert("上传失败！");			
					}
				}
			})
	}else{
		if($('#wordFile').val()==""){
			alert("您还未选择文件！");
			return;
		}
		$(".saveResult").html('文件上传中，请稍后...');
		$(".saveResult").fadeIn();
			$.ajaxFileUpload({
				type : "post",
				url: "${contextPath}/courseFile/uploadAttachFile",
				secureuri: false,
				fileElementId: "wordFile",
				dataType: 'json',
				data : {
					dataType : "2",
					classId : '${classId}',
					filePath : '${classFilePath}'
				},
				success: function(data){
					$(".saveResult").fadeOut();
					if(data.status=="success"){
						refresh();
					}else{
						alert("上传失败！");
					}
				}
		})
	}
}

var fileIds = [];
function deleteFile(){
		$('input[name="fileId"]:checked').each(function(){ 
			fileIds.push($(this).val());
		});  
		if(fileIds.length<1){
			alert("请选择需要删除的项！");
			return 
		}
		$('.alerttext span').html('删除将不可恢复，是否确定删除！');
		$('.tanbox').fadeIn();
}

/*弹窗*/
$('.tantit a,.cancelbtn').on('click',function(){
	$('.tanbox').fadeOut();
	$('.alerttext i').removeClass('fa-check-circle').addClass('fa-exclamation-circle');
	$('.alerttext i').removeClass('fa-times-circle').addClass('fa-exclamation-circle');
});
$('.confirmbtn').on('click',function(){
	$('.actbtn').hide();
	$('.actbtn1').show();
	$.ajax({
		type:'post',
		url:'${contextPath}/courseFile/deleteAttachFileByAttachId',
		data:'fileId='+fileIds,
		dataType:'json',
		success:function(str){
			if(str=="success"){
				$('.alerttext span').html('删除成功！');
				$('.alerttext i').removeClass('fa-exclamation-circle').addClass('fa-check-circle');
				$('.tanbox').fadeIn();
			}else{
				$('.alerttext span').html('删除失败！');
				$('.alerttext i').removeClass('fa-exclamation-circle').addClass('fa-times-circle');
				$('.tanbox').fadeIn();
			}
		}
	});
});

//-----全选/取消
$('#selallbtn1').on('click',function(){
	if($('#selallbtn1').prop('checked')){
		$('.selrolebtn1').prop('checked',true);
	}else{
		$('.selrolebtn1').prop('checked',false);
	}
});

function downLoad(fileId){  
	document.getElementById("ifile").src = "${contextPath}/courseFile/downLoadAttachFile?fileId="+fileId; 
}
</script>
</html>