<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
		<style>
				.printfList{width:100%;margin-top:20px;}
				.printfList li{border:1px solid #ccc;line-height:30px;padding:0 4px;width:19%;margin-left:-1px;margin-top:-1px;}
				.printfList li span{margin-right:10px;}
		</style>
	</head>
<body>
<div class="searchBeaf">
	<form action="${ctx}/code/printKeyCode" id="codeForm" method="post">
		<span>
			 <label>当前激活码数：</label>
			 <input type="number" class="inpt" min="10" name="keyNum" id="keyNum" value="${needNum}"/>
		</span>
		<span class="printBtn"><input type="button" value="打印" onclick="printCode()"></span>
		<span class="printBtn"><input type="button" value="打成压缩包" onclick="packageZip()"></span>
		<span class="printBtn"><input type="button" value="导出" onclick="queryExcel()"></span>
	</form>
	<ul class="printfList clearfix">
		<c:forEach items="${codeList}"  var ="codeList" end="${needNum-1}">
			<li class="clearfix fl"><span class="fl"><img style="width:200px; height:220px" src="${filePath}/${codeList.qrCodePath}"></span></li>
			<div class="filePath" style="display:none">${codeList.qrCodePath}</div>
		</c:forEach>
	</ul>
</div>
<iframe src="" id="downLoadPackage" style="display:none"></iframe>
<iframe src="" id="daochu1" style="display:none"></iframe>
</body>
<script type="text/javascript" type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
</html>
<script>
	$("#keyNum").blur(function(){
		var num = $("#keyNum").val();
		if(num=="" || num<3){
			alert("数量必须不小于3个");
			return
		}
		document.getElementById("codeForm").submit();
	})
	
	function printCode(){
		$(".printBtn").hide();
		window.print();
	}
	
	function packageZip(){		
		if(confirm("是否确认导出当前展示的${needNum}张二维码图片？")){	
			var filePath = [];
			$('.filePath').each(function(){ 
				filePath.push($(this).html());
			});  
			if(filePath.length<3){
				alert("必须3张以上二维码图片才可导出！");
				return;
			}
			document.getElementById('downLoadPackage').src='${ctx}/code/packageToZip?filePath='+filePath+"&name=${schoolName}";
		}
	}
	function queryExcel(){		
		if(confirm("是否确认导出当前展示的${needNum}条激活码？")){	
			var num = ${needNum};
			console.log(num);
			document.getElementById('daochu1').src='${ctx}/code/queryExcel?num='+num+"&name=${schoolName}";
		}
	}
</script>
