<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
	#searchContent{padding:0 10px;border: 1px solid #ccc;}
	.searchContentBox{width:270px;margin:10px auto 45px auto;}
	.searchContentBox label,.searchContentBox input{line-height:30px;float:left;display:inline;font-size:14px;}
	#resultIframe{border:0;width:100%;min-height:450px;}
</style>
<div>
	<div class="navExplain">
		<span>数据分析</span>
	</div>
	<div class="searchContentBox clearfix">
		<input type="text" id="searchContent" placeholder="请输入手机号或用户名搜索" />
		<a href="javascript:;" id="searchBtn" class="defaultBtn">搜索</a>
	</div>
	<div id="resultIframe"></div>
</div>
<script type="text/javascript">
	$("#searchBtn").on('click',function(){
		var content = $("#searchContent").val();
		if(content=="" || content==null){ 
			return;
		}
		$('#resultIframe').load("${contextPath}/user/queryDataAnalysis?content="+content);
	})
</script>