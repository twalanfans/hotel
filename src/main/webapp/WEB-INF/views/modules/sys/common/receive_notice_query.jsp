<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!-- 栏目说明 -->
<div class="navExplain">
	<span>系统公告</span>
</div>
<!-- 搜索条件 -->
<div class="searchBeaf">
		<span class="mb8 mr5">
			<label for='searchTitle'>公告标题：</label>
			<input type="text" value="${title}" id="searchTitle" class="inpt" />
		</span>
		<span class="mb8 mr5">
			<label for='searchStartTime'>开始时间：</label>
			<input type="text" value="${startTime}" class="searchStartTime inpt"  id="searchStartTime" readonly />
		</span>
		<span class="mb8 mr5">
			<label for='searchEndTime'>结束时间：</label>
			<input type="text" value="${endTime}" class="searchEndTime inpt" id="searchEndTime" readonly />
		</span>
		<span class="mb8">
			<a ui-sref="receiveNotice({pages : 1})" ui-sref-opts="{reload:true}" class="defaultBtn messagesearchbtn">搜索</a>
		</span>
</div>
<!-- 搜索结果 -->
<div class="allTable">
	<table cellpadding="0" cellspacing="0"> 
		<thead>
			<tr>
				<td class="textcontent">公告标题</td>
				<td class="textcontent">公告内容</td>
				<td>发布状态</td>
				<td>发布时间</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${noticeList.dataRows}"  var="noticeList" >
			<tr>
				<td class="textcontent">${noticeList.title}</td>
				<td class="textcontent">
					<a href="javascript:;" onclick="noticeDetail( ${noticeList.noticeId},'${noticeList.status}');" title="查看公告详情" class="noticTitle">${fn:substring(noticeList.notices, 0, 14)}...</a>
				</td>
				<c:if test="${noticeList.status=='0'}"><td>已失效</td></c:if>
				<c:if test="${noticeList.status=='1'}"><td>发布中</td></c:if>
				<td><fmt:formatDate value="${noticeList.pubTime}" pattern="yyyy-MM-dd HH:mm"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<!-- 翻页 -->
<div class="pageList">
	<span>共<b>${noticeList.records}</b>条</span>
	<span>每页<b>${noticeList.rows}</b>条</span>
	<span>当前第<b>${noticeList.page}</b>页</span>
	<span>
		<c:if test="${noticeList.page == 1 || noticeList.total == 0}"><span>首页</span><span>上一页</span></c:if>
		<c:if test="${noticeList.page != 1 && noticeList.total > 1}">
			<a href="javascript:;" ng-click="shouye(1);">首页</a>
			<a href="javascript:;" ng-click="prevPage(${noticeList.page-1})">上一页</a>
		</c:if>
		<c:if test="${noticeList.page == noticeList.total || noticeList.total == 0}"><span>下一页</span><span>尾页</span></c:if>
		<c:if test="${noticeList.page != noticeList.total && noticeList.total != 0}">
			<a href="javascript:;" ng-click="nextPage(${noticeList.page+1})">下一页</a>
			<a href="javascript:;" ng-click="endPage(${noticeList.total})">尾页</a>
		</c:if>
	</span>
</div>
<iframe id="ifile" style="display:none"></iframe>
<script type="text/javascript" src="${ctxStatic}/js/calendar.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script type="text/javascript">
	//配置日历表组件
	$(function(){
		$("input.searchStartTime,input.searchEndTime").manhuaDate({					       
			Event : "click",//可选				       
			Left : 0,//弹出时间停靠的左边位置
			Top : -16,//弹出时间停靠的顶部边位置
			fuhao : "-",//日期连接符默认为-
			isTime : true,//是否开启时间值默认为false
			beginY : 1949,//年份的开始默认为1949
			endY :2100//年份的结束默认为2049
		});
	})
	
	//查看公告详情 
	function noticeDetail(noticeId, status){
		if(status=="0"){
			alert("该公告已失效，无法查看其详情！")
			return;
		}
		var url = "${contextPath}/notice/queryNoticeDetail?noticeId="+noticeId;
		var tool ='height=500,width=800,top=150,left=600,menubar=yes, alwaysRaised=yes';
		window.open(url,'_blank',tool);
	 } 
</script>