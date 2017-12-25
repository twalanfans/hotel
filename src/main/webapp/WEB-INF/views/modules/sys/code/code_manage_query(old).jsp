<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/teacher/sendTestPaper.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/calendar.css">
<style type="text/css">
.showIframeBox {
	position: fixed;
	left: 0;
	top: 0;
	display: none;
	z-index: 99;
	width: 100%;
	height: 100%;
}

.showQRCode {
	position: fixed;
	left: 50%;
	width: 256px;
	margin-left: -128px;
	top: 50%;
	margin-top: -145px;
	height: 290px;
	z-index: 99;
	display: none;
}

.closeBox {
	text-align: center;
	position: relative;
	z-index: 10;
	color: #fff;
	top: 103px;
	line-height: 40px;
	padding: 0 20px;
}

.quxiao {
	color: red;
	font-size: 25px;
	cursor: pointer;
}

.shadowBox {
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.6);
	position: absolute;
	left: 0;
	top: 0;
}

#codeFrame {
	position: relative;
	top: 100px;
}
</style>
<div>
	<div class="navExplain">
		<span>激活码管理</span>
	</div>
	<div class="testPaper">
		<!--搜索-->
		<div class="searchPanel">
			<form action="${ctx}/code/queryCodeListPage" id="codeForm"
				method="post">
				<span> <label>激活码：</label> <input type="text" class="inpt"
					name="keyId" value="${keyId}" />
				</span> <span> <label>使用量：</label> <select id="useNum" name="useNum"
					class="inpt">
						<option value="">—请选择—</option>
						<option value="0">0</option>
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
				</select>
				</span> <input type="submit" value="搜索" class="commonBtn" />
			</form>
			<div style="margin:16px">
				<span> <a href="javascript:;" class="commonBtn"
					id="deleteKey">删除</a>
				</span> <span> <a href="javascript:;" class="commonBtn"
					onclick="madeKeyCode()">在线生成</a>
				</span> <span style="margin-left:1050px"> <input value="打印或打包"
					type="button" class="commonBtn" onclick="printPaper()" />
				</span>
			</div>
		</div>
		<!--组卷记录-->
		<div class="allTable">
			<table cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<td><label for="allCheck">全选</label> <input type="checkbox"
							id="allCheck" /></td>
						<td>激活码</td>
						<td>剩余使用量</td>
						<td>所属学校</td>
						<td>生产日期</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${pageInfo.list}" var="codeList">
						<tr>
							<td><input type="checkbox" class="checkChoose" name="keyId"
								value="${codeList.keyId}" /></td>
							<td><a href="javascript:;"
								filePath="${filePath}/${codeList.qrCodePath}"
								class="showQRCodeBtn">${codeList.keyId}</a></td>
							<td>${codeList.useNum}</td>
							<td>${codeList.schoolName}</td>
							<td><fmt:formatDate value="${codeList.createTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!--翻页-->
		<div class="pageList">
			<span>共<b>${pageInfo.total}</b>条
			</span> <span>每页<b>${pageInfo.pageSize}</b>条
			</span> <span>当前第<b>${pageInfo.pageNum}</b>页
			</span> 
			<span> <%-- <a href="#" onclick="pageTurn('1')">首页</a>
					<a href="#" onclick="pageTurn(${pageInfo.pageNum-1})">上一页</a>
					<a href="#" onclick="pageTurn(${pageInfo.pageNum+1})">下一页</a>
					<a href="#" onclick="pageTurn(${pageInfo.pages})">尾页</a> --%> 
				<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}">
					<span>首页</span>
					<span>上一页</span>
				</c:if> <c:if test="${pageInfo.pageNum != 1 && pageInfo.pages > 1}">
					<a href="javascript:;" ng-click="pageTurn('1')">首页</a>
					<a href="javascript:;" ng-click="pageTurn(${pageInfo.pageNum-1})">上一页</a>
				</c:if> <c:if
					test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}">
					<span>下一页</span>
					<span>尾页</span>
				</c:if>
					<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
					<a href="javascript:;" ng-click="pageTurn(${pageInfo.pageNum+1})">下一页</a>
					<a href="javascript:;" ng-click="pageTurn(${pageInfo.pages})">尾页</a>
				</c:if>
			</span>
		</div>
	</div>

	<div class="uploadFileWindow">
		<div class="uploadFileTitle">在线生成激活码</div>
		<div class="uploadFileContent">
			<p>
				激活码数量： <input type="number" name="codeNum" id="codeNum" min="1"
					class="inpt" />
			</p>
			<p>
				分发学校： <select id="schoolId" class="inpt">
					<option value="">—请选择—</option>
					<c:forEach items="${schoolList}" var="schoolList">
						<option value="${schoolList.schoolId}">${schoolList.schoolName}</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<a href="javascript:;" class="commonBtn" onclick="confirmMade();">开始生成</a>
				<a href="javascript:;" class="commonBtn" onclick="closeWin();">取消</a>
			</p>
		</div>
	</div>

	<div class="promptBox">
		<div class="promptTxt">
			<p>提示</p>
			<p>
				<i class="fa fa-exclamation-circle fl"
					style="margin:20px 5px 0 20px;"></i> <span>删除将不可恢复，是否确定删除？</span>
			</p>
		</div>
		<div class="promptBtn">
			<a href="javascript:;" id="confirmBtn" class="commonBtn">确认</a> <a
				href="javascript:;" id="cancelBtn" class="defaultBtn">取消</a>
		</div>
	</div>

	<div class="showIframeBox">
		<div class="shadowBox"></div>
		<div class="closeBox clearfix">
			<span style="font-size:20px; color:orange">激活码打印页</span><span
				class="fa fa-times-circle-o quxiao fr"></span>
		</div>
		<iframe src="" id="codeFrame" frameborder="0" width="100%"
			height="500px"></iframe>
	</div>
	<img src="" class="showQRCode" border="2" />
</div>
<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript">
//弹出显示二维码
$('.showQRCodeBtn').on('click',function(ev){
	ev.stopPropagation();
	 var oShowQRCode=$('.showQRCode');
	 var oFilePath=$(this).attr('filePath');
	 oShowQRCode.attr({'src':oFilePath});
	 oShowQRCode.stop(true).fadeIn();
	 $(document).on('click',function(){
		 oShowQRCode.stop(true).fadeOut();
	 });
});
$("#useNum option[value='${useNum}']").attr("selected","selected");
function madeKeyCode(){
	$('.uploadFileWindow').fadeIn();
}
function closeWin(){
	$('.uploadFileWindow').fadeOut();
}

function printPaper(){
	if("${pageInfo.total}"<10){
		alert("当前激活码数未超过10条不可打印！");
		return
	}
	document.getElementById('codeFrame').src="${ctx}/code/printKeyCode";
	$('.showIframeBox').fadeIn();
}

$(".quxiao").click(function(){
	$(".showIframeBox").hide();
})

function confirmMade(){
	$('.uploadFileWindow').hide();
	var codeNum = $("#codeNum").val();
	var schoolId= $("#schoolId").val();
	$.ajax({
		type:'post',
		url:'${ctx}/code/createKeyCode',
		//url:'${ctx}/code/insertKeyCode',
		data:'codeNum='+codeNum+"&schoolId="+schoolId,
		dataType:'json',
		success:function(str){
			if(str=="error"){
				alert("生成失败！");
				window.location.reload();
			}else{
				alert("本次成功"+str+"个！");
				window.location.reload();
			}
		}
	});
}

var keyId = [];
$('#deleteKey').click(function(){
	$('input[name="keyId"]:checked').each(function(){ 
		keyId.push($(this).val());
	});  
	if(keyId.length<1){
		alert("请选择需要删除的项！");
		return;
	}
	$('.promptBox').fadeIn();
});

//确认删除用户
$('#confirmBtn').on('click',function(){
	$.ajax({
		type:'post',
		url:'${ctx}/code/deleteKeyCode',
		data:'keyId='+keyId,
		dataType:'json',
		success:function(str){
			if(str!="error"){
				$('.promptTxt p:eq(1) span').html("删除成功,本次共删除"+str+"个！");
				$('.promptBox').show();
				setTimeout(function(){
					window.location.reload();
				},2000);
			}else{
				$('.promptTxt p:eq(1) span').html('删除失败，请重新操作！');
				$('.promptBox').show();
				setTimeout(function(){
					window.location.reload();
				},2000);
			}
		}
	});
});

$('#cancelBtn').on('click', function(){
	$('.promptBox').fadeOut();
});

$('#allCheck').on('click',function(){
	if($('#allCheck').prop('checked')){
		$('.checkChoose').prop('checked',true);
	}else{
		$('.checkChoose').prop('checked',false);
	}
});
	
/* function pageTurn(pageNum){
	document.getElementById("codeForm").action="${ctx}/code/queryCodeListPage?page="+pageNum;
	document.getElementById("codeForm").submit();
} */
</script>