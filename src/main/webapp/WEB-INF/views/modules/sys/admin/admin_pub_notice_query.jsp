<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/calendar.css">
<style type="text/css">
	.sentmessagebox1{left:50%;margin-left:-150px;width:280px;padding:10px;display:none;background-color:#fff;position:fixed;top:20%;z-index:9999;}
	.sentmessagehead{margin-bottom:10px;}
	.sentmessage p{margin-bottom:10px;}
	.noticeContent{float:left;line-height:150px;}
	.messagecontent{height:150px;resize:none;}
	#loading{display:none;}
	.chehuiBtn{height: 30px;line-height: 30px;padding: 7px 16px;color: #333;background-color: #ff7979;font-size: 12px;cursor: pointer;}
    .publishBtn{height: 30px;line-height: 30px;padding: 7px 16px;color: #333;background-color:#67d45f;font-size: 12px;cursor: pointer;}
	.saveResult{width:90%;word-break:break-all;height:200px;line-height:200px;background:url(${ctxStatic}/images/video_loading.gif) center no-repeat;padding:10px;position:absolute;left:4px;top:20%;display:none;}
</style>
<div class="messagesearchbox mlDistance">
	<!-- 栏目说明 -->
	<div class="navExplain">
		<span>发布公告</span>
	</div>
	<!-- 搜索条件 -->
	<div class="searchBeaf">
		<form action="" id="Search" method="post">
			<span class="mb8 mr5">
				<label for='searchTitle'>公告标题：</label>
				<input type="text" value="${title}" id="searchTitle" class="inpt" />
			</span>
			<span class="mb8 mr5">
				<label for="searchStatus">公告状态:</label>
				<select id="searchStatus" class="inpt">
					<option value="">——请选择——</option>
					<option value="0">废弃</option>
					<option value="1">发布中</option>
				</select>
			</span>
			<span class="mb8 mr5">
				<label for='searchStartTime'>开始时间：</label>
				<input type="text" value="${startTime}"  id="searchStartTime" class="inpt" readonly />
			</span>
			<span class="mb8 mr5">
				<label for='searchEndTime'>结束时间：</label>
				<input type="text" value="${endTime}"  id="searchEndTime" class="inpt" readonly />
			</span>
			<span class="mb8 mr5">
				<a ui-sref="receiveNotice({pageNum : 1})" ui-sref-opts="{reload:true}" class="defaultBtn">搜索</a>
			</span>
			<span class="sentmessagebtn mb8">
				<a href="javascript:;" class="defaultBtn">发布新公告</a>
			</span>
			<span class="mb8 mr5">
				<a href="javascript:;" onclick="delNotice();" class="defaultBtn">删除</a>
			</span>
		</form>
	</div>
	<!-- 搜索结果 -->
	<div class="allTable">
		<table cellpadding="0" cellspacing="0">
			<thead>
				<tr>
					<td>
						<label for="selallbtn1">全选&nbsp;&nbsp;</label>
						<input type="checkbox" id="selallbtn1" style="display:none" />
					</td>
					<td>公告标题</td>
					<td>公告对象</td>
					<td>发布状态</td>
					<td>发布时间</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${noticeList.dataRows}"  var="noticeList" >
				<tr>
					<td><input type="checkbox" class="selrolebtn1" name="noticeId" value="${noticeList.noticeId}"></td>
					<td class="textcontent"><a href="javascript:;" onclick="noticeDetail( ${noticeList.noticeId});" title="查看公告详情">${noticeList.title}</a></td>
					<c:if test="${noticeList.target=='0'}"><td>全体人员</td></c:if>
					<c:if test="${noticeList.target=='1'}"><td>学生</td></c:if>
					<c:if test="${noticeList.target=='2'}"><td>教师</td></c:if>
					<c:if test="${noticeList.status=='0'}">
					<td>已废弃&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="changeStatus('${noticeList.noticeId}','1')" class="publishBtn">发布</a></td>
					</c:if>
					<c:if test="${noticeList.status=='1'}">
					<td>发布中&nbsp;&nbsp;&nbsp;<a href="javascript:;" onclick="changeStatus('${noticeList.noticeId}','0')" class="chehuiBtn">撤回</a></td>
					</c:if>
					<td><fmt:formatDate value="${noticeList.pubTime}" pattern="yyyy-MM-dd HH:mm"/></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<!-- 翻页 -->
<div class="pageList">
	<span>共<b>${noticeList.records}</b>条</span>
	<span>每页<b>${noticeList.rows}</b>条</span>
	<span>当前第<b>${noticeList.page}</b>页</span>
	<span>
		<c:if test="${noticeList.page == 1 || noticeList.total == 0}">
			<span>首页</span>
			<span>上一页</span>
		</c:if>
		<c:if test="${noticeList.page != 1 && noticeList.total > 1}">
			<a href="javascript:;" ng-click="shouye(1);">首页</a>
			<a href="javascript:;" ng-click="prevPage(${noticeList.page-1})">上一页</a>
		</c:if>
		<c:if test="${noticeList.page == noticeList.total || noticeList.total == 0}">
			<span>下一页</span>
			<span>尾页</span>
		</c:if>
		<c:if test="${noticeList.page != noticeList.total && noticeList.total != 0}">
			<a href="javascript:;" ng-click="nextPage(${noticeList.page+1})">下一页</a>
			<a href="javascript:;" ng-click="endPage(${noticeList.total})">尾页</a>
		</c:if>
	</span>
</div>
<div class="sentmessagebox1">
	<div class="shadow"></div>
	<div class="formbox">
		<h4 class="sentmessagehead clearfix">
			<span>发送新公告：</span>
		</h4>
		<form class="sentmessage" action="" method="post" id="pubNoticeForm">
			<p>
				<label for="toTarget">收件人:</label>
				<select id="toTarget" class="inpt">
					<option value="0">全体</option>
					<option value="1">学生</option>
					<option value="2">教师</option>
				</select>
			</p>
			<p>
				<label for="messagetit">标题：</label>
				<input type="text" value="" name="title" id="messagetit" class="messagetit inpt" placeholder="标题" />
			</p>
			<p>
				<label for="messagecontent" class="noticeContent">正文：</label>
				<textarea class="messagecontent inpt" id="messagecontent" name="notices" placeholder="正文"></textarea>
			</p>
			<p>
				<label for="file">附件：</label>
				<input type="file"  name="file" id="file" class="inpt" />
			</p>
			<p>
				<a href="javascript:;" id="confirmSub" class="commonBtn">提交</a>
				<a href="javascript:;" id="cancelSub" class="defaultBtn">取消</a>
			</p>
		</form>
	</div>
	<div class="saveResult"></div>
</div>
<!-- 用户操作提示框 -->
<div class="promptBox">
	<div class="promptTxt">
		<p>提示</p>
		<p>
			<i class="fa fa-exclamation-circle fl" style="margin:20px 5px 0 20px;"></i>
			<span>删除将不可恢复，是否确定删除？</span>
		</p>
	</div>
	<div class="promptBtn">
		<a href="javascript:;" id="confirmBtn" class="commonBtn">确认</a>
		<a href="javascript:;" id="cancelBtn" class="defaultBtn">取消</a>
	</div>
</div>
<!-- 遮罩层 -->
<div class="clear"></div>
<iframe id="ifile" style="display:none"></iframe>
<script type="text/javascript" src="${ctxStatic}/js/calendar.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script type="text/javascript">
	//配置日历表组件
	$(function (){
		$("#searchStartTime,#searchEndTime").manhuaDate({					       
			Event : "click",//可选				       
			Left : -40,//弹出时间停靠的左边位置
			Top : -16,//弹出时间停靠的顶部边位置
			fuhao : "-",//日期连接符默认为-
			isTime : true,//是否开启时间值默认为false
			beginY : 1949,//年份的开始默认为1949
			endY :2100//年份的结束默认为2049
		});
		
	});

	//改变公告发布状态
	function changeStatus(noticeId,status){
		$.ajax({
			type:'post',
			url:'${contextPath}/notice/updateSysNotice',
			data:"noticeId="+noticeId+"&status="+status,
			dataType:'json',
			success:function(str){
				if(str=="success"){
					alert('操作成功');
					window.location.reload();
				}else{
					alert('操作失败,请重新操作！');
				}
			}
		});
	}

	//查看公告详情
	function noticeDetail(noticeId){
		var url = "${contextPath}/notice/queryNoticeDetail?noticeId="+noticeId;
		var tool ='height=500,width=800,top=150,left=600,menubar=yes, alwaysRaised=yes'
		window.open(url,'公告详细信息',tool);
	} 

	$('.closebtn').on('click',function(){
		$(this).parent().hide();
	});
 
	var noticeId = [];
	function delNotice(){
		$('input[name="noticeId"]:checked').each(function(){ 
			noticeId.push($(this).val());
		});
		if(noticeId.length<1){
			alert("请选择需要删除的项！");
			return;
		}
		$('.promptBox').fadeIn();
	}		

	//关闭弹窗
	$('#cancelSub').click(function(){
		$('.clear').eq(0).hide();
		$('.sentmessagebox1').fadeOut();
	});
	
	//确认删除
	$('#confirmBtn').on('click',function(){
		var $prompt = $('.promptTxt p:eq(1) span'),
			$promptBox = $('#promptBox');
		$.ajax({
			type : 'post',
			url : '${contextPath}/notice/deleteSysNotice',
			data : 'noticeId='+noticeId,
			dataType : 'json',
			success : function(str){
				if(str=="success"){
					window.location.reload();
				}else{
					$prompt.html('操作失败！');
					$promptBox.fadeOut();
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

	$('.sentmessagebtn a').on('click',function(){
		$('.clear').eq(0).show();
		$('.sentmessagebox1').fadeIn();
	});
	
	//取消提交
	$('#cancelBtn').click(function(){
		$('.promptBox ').fadeOut();
	});
	
	//提交功能
	$('#confirmSub').on('click',function(){
		var toTarget = $('#toTarget').val();
		if(toTarget==null || toTarget==""){
			alert("请选择收件人！");
			return;
		}
		var title = document.getElementById("messagetit").value;
		if(title==null || title==""){
			alert("请填写标题！");
			return;		
		}
		var messagecontent = document.getElementById("messagecontent").value;
		if(messagecontent==null || messagecontent==""){
			alert("请填写正文！");
			return;		
		}
		$('.saveResult').fadeIn();
		$('.alerttext i').removeClass('fa-exclamation-circle').addClass('fa-check-circle');
		$.ajaxFileUpload({
	        url: '${contextPath}/notice/insertSysNotice',
	        secureuri: false,
	        fileElementId: 'file',
	        dataType: 'json',
	        data: {target : toTarget, title : title, notices : messagecontent},
			success:function(data, status){
				if(data.status=="success"){
					alert('公告发布成功');
					$('.clear').eq(0).hide();
					$('.sentmessagebox1').fadeOut();
					$('.saveResult').fadeOut();
					window.location.reload();
				}else{
					alert('公告发布失败，请重新操作！');
					$promptBox.fadeOut();
				}
			}
		});
	});
	
	//查看公告详情
	function msgDetail(messageId){
		var url = "${contextPath}/message/queryMessageDetail?messageId="+messageId;
		var tool ='height=500,width=800,top=150,left=600,menubar=yes, alwaysRaised=yes'
		window.open(url,'短消息详细信息',tool);
	}
	
</script>