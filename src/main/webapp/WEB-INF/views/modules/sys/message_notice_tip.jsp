<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page import="java.util.List" %>
<%@ page import="com.common.utils.DateUtils" %>
<%@ page import="com.module.sys.entity.Message" %>
<%@ page import="com.module.sys.entity.Notice" %>
<%@ page import="com.module.sys.utils.UserUtils" %>
<% 
	String nowTime = DateUtils.getDateTime(); 
	List<Message> userMessage = UserUtils.userNeedDeail();				//用户短消息查询
	int messageCount = userMessage.size();
	List<Notice> userNotice = UserUtils.userNotice();    //用户公告查询
	int userNoticeCount= userNotice.size();
	request.setAttribute("userMessage", userMessage);
	request.setAttribute("messageCount", messageCount);
	request.setAttribute("userNotice", userNotice);
	request.setAttribute("userNoticeCount", userNoticeCount);
%>
<div class="menu">
	<a href="javascript:;" class="menuBtn">
		<img src="${filePath}/${userDetail.photo}" alt="个人头像" width="26" height="26" />
		<span>${userDetail.userName}</span>
		<i class="fa fa-sort-desc"></i>
	</a>
	<ul class="menuList">
		<li><a ui-sref="receiveMessage({pageNum : 1})"><i class="fa fa fa-cog"></i>新消息(${messageCount})</a></li>
		<li><a ui-sref="receiveNotice({pageNum : 1})"><i class="fa fa fa-cog"></i>新公告(${userNoticeCount})</a></li>
		<li><a ui-sref="personalEdit" class="router"><i class="fa fa fa-cog"></i>个人中心</a></li>
		<li><a href="javascript:;" id="quit"><i class="fa fa fa-sign-out"></i>退出</a></li>
	</ul>
</div>
<script type="text/javascript">
	(function(){
		//退出
		var quit = document.getElementById('quit');
		quit.onclick = function(){
			var quesId = window.localStorage.questionIdStr;
			if(quesId && JSON.parse(quesId).length){
				if(confirm('您当前的试题篮还有未组卷的试题，现在退出会清空试题篮，继续退出？')){
					window.localStorage.questionIdStr = '';
					window.location = "${contextPath}/logout";
				}else{
					window.location = '${userDetail.userType}'== '2' ? '${ctx}/chooseLogin#/ceshiQuestion' : '${ctx}/chooseLogin#/studentErrorNotes';
				}
			}else{
				if(confirm('确认退出？')){
					window.location = "${contextPath}/logout";
				}
			}
		}
	})();
</script>