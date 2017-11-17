<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<c:if test="${listName=='所有视频'}">
	<div class="videoTitle"></div>
	<video width="100%"  controls="controls"  src=""  poster=""  id="video"></video>
</c:if>
<c:if test="${listName!='所有视频'}">
	<div class="videoTitle">${courseVideo.fileName}</div>
	<video width="100%"  controls="controls"  src="${filePath}/${courseVideo.filePath}"  poster="${filePath}/${courseVideo.videoImg}"  id="video"></video>
</c:if>