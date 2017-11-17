<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
	.criticismFile{color:#b1b111;font-size:16px;line-height:27px;}
	.download{margin:10px 10px 0 10px;font-size:16px;line-height:27px;cursor:pointer;}
	.fileDetail{margin-left:10px;}
</style>
<!-- 栏目说明 -->
<div class="navExplain ">
	<span>考试资料</span>
</div>
<!-- 资料展示 -->
<div class="allTable">
	<table cellpadding="0" cellspacing="0">
		<thead>
			<tr>
				<td>文件名</td>
				<td>备注</td>
				<td>大小</td>
				<td>下载</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${criticismFile}" var="criticismFile" >
			<tr>
				<td>
					<a href="javascript:;"  title="查看内容" filePath="${filePath}/${criticismFile.filePath}" onclick="queryDetail(this);">${criticismFile.fileName}</a>
				</td>
				<td>${criticismFile.fileIntroduce}</td>
				<td>${criticismFile.fileSize}</td>
				<td>
					<span class="fa fa-download download fl" onclick="downLoad(${criticismFile.fileId});"></span>
				</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<!-- 资料下载 -->
<iframe id="ifile" style="display:none"></iframe> 
<script type="text/javascript">
	function downLoad(fileId){  
		document.getElementById("ifile").src = "${contextPath}/test/downFile?fileId="+fileId; 
	}
	
	function queryDetail(thisClass){
		var url = $(thisClass).attr("filePath");
		var tool ='height=800,width=1000,top=20,left=400,menubar=yes, alwaysRaised=yes'
		window.open(url,'文件详情',tool);
	}
</script>
