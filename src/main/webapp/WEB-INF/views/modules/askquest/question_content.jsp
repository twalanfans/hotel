<!-- question_content.jsp -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!doctype html>
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" />
		<title>问题回答详情</title>
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/comStyle.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/admin/question_content.css" />
		<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/zoomify.min.css" />
		<style type="text/css">
			.commentContentBox{display:inline-block;width:100%;}
		</style>
	</head>
	<body>
		<div class="news">
			<!--这里是问题详情-->
			<div class="newsContent">
				<div class="articleTitle">
					<h3>${questionDetail.title}</h3>
					<ul class="clearfix addQuestionUserInfo">
						<li class="fl clearfix">
							<img src="${filePath}/${questionDetail.photo}"  title="查看大图" class="fl userPic" />
							<span class="fl userInfo">${questionDetail.createUser}</span>
						</li>
						<li class="fl"><fmt:formatDate value="${questionDetail.createTime}" pattern="yyyy年MM月dd日 HH:mm"/></li>
						<li class="fl"><i class="fa fa-eye"></i>&nbsp;<span>${questionDetail.clickNum}</span></li>
					</ul>
				</div>
				<div class="Content">
					<p class="questionContent">
						<span class="fa fa-question-circle"></span>${questionDetail.content}
					</p>
					<c:if test="${attachImg!=null}">
					<div class="clearfix enclosureList">
						<p class="fl">附图：</p>
						<c:forEach items="${attachImg}" var="attachImg" varStatus="status">
							<img src="${filePath}/${attachImg}" title="查看大图" class="figure fl"/>
						</c:forEach>
					</div>
					</c:if>
				</div></br>
				<ul class="answer">
					<c:if test="${commentNum!='0'}">
					<c:forEach items="${commentList}" begin="0" end="${showNum}" var="commentList1"  varStatus="status">
						<c:if test="${commentList1.replyId=='0'}">
						<li class="answerContent clearfix">
							<div class="clearfix">
								<div class="clearfix commentUserInfo">
									<img src="${filePath}/${commentList1.photo}"  class="userPic" />
									<span class="userInfo">${commentList1.createUser}</span>
								</div>
								<div class="commentContentBox clearfix">
									<div class="commentContent clearfix">
										<span class="commentContentText" cont="${commentList1.content}"></span>
										<span class="replyUserInfo fr clearfix">
											<a href="javascript:;" class="fl addReply" onclick="addComment('${commentList1.commentId}','${commentList1.createUser}','')">回复&nbsp;</a>
											<a href="javascript:;" class="fl" onclick="upTG('${commentList1.commentId}','${commentList1.thinkGood}',this)"><i class="fa fa-thumbs-o-up"></i>&nbsp;<b class="goodNum">${commentList1.thinkGood}</b></a>
										</span>
									</div>
									<div class="replyUserInfo fr clearfix">
										<p class="fl"><fmt:formatDate value="${commentList1.createTime}" pattern="MM-dd HH:mm"/></p>
									</div>
								</div>
							</div>
							<ul class="replyContent">
							<c:forEach items="${commentList}"  begin="0" end="${showNum}" var="commentList2" varStatus="status">
							 	<c:if test="${commentList2.replyId==commentList1.commentId}">
								<li class="clearfix">
									<img src="${filePath}/${commentList2.photo}" class="fl mr20 replyUserImg" style="float:left;border-radius:50%;" />
									<div class="fl clearfix">
										<p class="replyContentText" cont="${commentList2.createUser}：${commentList2.content}"></p>
										<div class="replyUserInfo clearfix">
											<span class="fl"><fmt:formatDate value="${commentList2.createTime}" pattern="MM-dd HH:mm"/></span>
											<a href="javascript:;" class="fl addReply" onclick="addComment('${commentList2.replyId}','${commentList2.createUser}','${commentList2.content}')">回复&nbsp;</a>
										</div>
									</div>
								</li>
								</c:if>
							 </c:forEach>	
							</ul>
						</li>
						</c:if>
					</c:forEach>
				  </c:if>
				  <c:if test="${commentNum=='0'}">
				  	<li class="answerContent">
				  		<p class="answerContentNull">当前还未有人评论回答，快来抢占沙发位吧！</p>
				  	</li>
				  </c:if>
				</ul>
			</div>
			<!--发表评论-->
			<form class="addCommentBox">
				<fieldset>
					<legend class="addCommentTit">发布评论</legend>
					<div class="addCommentContent clearfix">
						<label class="fl">评论内容:</label><textarea class="fl CommentText" id="commentText"></textarea>
					</div>
					<div class="addCommentBtnBox">
						<a href="javascript:;" class="commonBtn addface" >添加表情</a>
						<a href="javascript:;" class="commonBtn" onclick="confirmComment()" >提交</a>
						<a href="javascript:;" id="close" class="commonBtn">关闭</a>
					</div>
				</fieldset>
			</form>
		</div>
	</body>
</html>
<script type="text/javascript" type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/jquery.qqFace.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/addface.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/zoomify.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/sonWindow.js"></script>
<script type="text/javascript">
	//初始化点击图片放大
	$('.Content .figure').zoomify();
	
	$('.answerContent').on('click','.addReply', function(){
		if($(document).width()<1025){
			$('#askWindow').css({'left':'1%','top':'2%','width':'90%'});
		}
		
		$('html, body, .content').animate({scrollTop: $(document).height()}, 300); 
		return false; 
	});
	
	var commentIds="";
	var replyText = "";
	function addComment(commentId,userName,content){
		commentIds = commentId;
		if(content==""){
			replyText ="";
		}else if(content.length>9){
			replyText = "||@"+userName+":"+content.substring(0,8)+"...";
		}else{
			replyText = "||@"+userName+":";
		}
		$(".CommentText").attr("placeholder","回复 "+userName+":");
	}
	
	function confirmComment(){
		var content = document.getElementById("commentText");
		var contentText = content.value;
		if(contentText==''){
			alert('评论内容不能为空！');
			content.focus();
			return;
		}
		$.ajax({
			url : '${contextPath}/question/saveComment',
		    dataType: 'json',
		    data :"commentId="+commentIds+"&questionId=${questionDetail.questionId}"+"&content="+contentText+replyText,
			success : function(result){
					loadMore();
			}
		});
	}
	
	function loadMore(){
		window.location.href="${contextPath}/question/questionContent?showNum=${showNum}"+"&questionId=${questionDetail.questionId}"+"&clickNum=";
	}
	
	function upTG(commentId,thinkGood,classThis){
		$.ajax({
			url : '${contextPath}/question/commentUpTG',
		    dataType: 'json',
		    data :"commentId="+commentId+"&thinkGood="+thinkGood,
			success : function(result){
				if(result!="error"){
					$(classThis).children(".goodNum").html(result);
				}
			}
		});
	}
	$('.commentContentText,.replyContentText').each(function(){
		$(this).html(replace_em($(this).attr('cont')));
	});
</script>