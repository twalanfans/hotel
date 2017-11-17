<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<title>上传微课</title>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/teacher/course_file_manage.css" />
	</head>
	<body class="bgcol_white">
	<div class="sentmessagebox admin_sentuser_message">
			<div class="searchCondition" style="padding:10px;">
				<form action="${contextPath}/courseStudent/queryCaseFilePage" id="Search" method="post">
					<div class="condition">
						<label>当前课程：</label>
						<input type="text"  value="${courseName}" class="inpt" disabled />
					</div>
					<div class="condition">
						<label>视频名称：</label>
						<input type="text"  id="fileName" class="inpt" />
					</div>
					<div class="condition clearfix">
						<label style="height:150px;float:left;line-height:150px;">视频简介：</label>
						<textarea cols="33" rows="4" id="fileIntroduce" class="inpt" style="height:150px;float:left;"></textarea>
					</div>
					<div class="condition">
						<label>视频封面：</label>
						<input type="file" name="spfm" id="spfm" class="inpt" placeholder="选择图片" />
					</div>
					<div class="condition">
						<label>上传视频：</label>
						<input type="file" name="fileToUpload" id="fileToUpload" class="inpt" onchange="controlSize(this)" />
						<p style="color:#f00;line-height:20px;">暂支持MP4格式视频</p>
					</div>
				</form>
			</div>
			<div class="commonIndent">
				<a href="javascript:;" onclick="uploadFile();" class="commonBtn">确认上传</a>
			</div>
			<div class="saveResult"></div>
	</div>
<!-- 用户操作提示框 -->
<div class="promptBox">
	<div class="promptTxt">
		<p>提示</p>
		<p>
			<i class="fa fa-exclamation-circle fl" style="margin-right:5px;"></i>
			<span>删除将不可恢复，是否确定删除？</span>
		</p>
	</div>
</div>
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script type="text/javascript">
	//限制视频文件最大500M
	var videoSize = false;
	function controlSize(source){
		var file = source.files[0];
		if(file.size / Math.pow(1000, 2) > 500){
			videoSize = false;
		}else{
			videoSize = true;
		}
	}
	
	//确认上传
	function uploadFile(){
		var fileName = $("#fileName").val();
		if(fileName==""||fileName==null){
			alert("请填写视频名称！")
			return false;
		}
		var fileIntroduce= $("#fileIntroduce").val();
		if(fileIntroduce==""||fileIntroduce==null){
			alert("请填写视频简介！")
			return false;
		}
		var fengMianType = $('#spfm').val();
		if(fengMianType!=""){
			if(!fengMianType.match(/.jpg|.png|.gif|.bmp/i)){
				alert("微课封面必须为图片");
				return false;
			}
		}
		if($('#fileToUpload').val()==""){
			alert("您还未选择视频文件！")
			return false;
		}
		if(!$('#fileToUpload').val().match(/.mp4/i)){
			alert("微课请选择MP4格式的文件！");
			return false;
		}
		if( !videoSize ){
			alert('视频最大不能超过500M！');
			return false;
		}
		var temp = ["spfm","fileToUpload"];
		$(".saveResult").fadeIn();
		var $promptTxt = $('.promptTxt p:eq(1) span'),
			$promptBox = $('.promptBox');
		$.ajaxFileUpload({
			type : "post",
			url: "${contextPath}/uploadFile/saveCourseFile",
			secureuri: false,
			fileElementId: temp,
			dataType: 'json',
			data : {
				courseType:"1",
				fileName :fileName,
				videoIntroduce :fileIntroduce,
				courseId : '${courseId}' //课程ID
			},
			success: function(data){
				$(".saveResult").fadeOut();
				if(data.status=="success"){
					$promptTxt.html('课件添加成功，已提交管理员等待审核！');
					$promptBox.fadeIn();
					setTimeout(function(){
						$promptBox.fadeOut();
						window.parent.document.getElementsByClassName('clear')[0].style.display = 'none';
						window.parent.document.getElementsByClassName('myIframe')[0].style.display = 'none';
					},1600);
				}else{
					$promptTxt.html('课件添加失败，请重新操作！');
					setTimeout(function(){
						$promptBox.fadeOut();
					},1600);
				}
			},
		})
	}
</script>
</body>
</html>