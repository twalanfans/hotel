<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
	.courseNote{display:none;position:fixed;top:25%;left:50%;margin-left:-140px;z-index:9999;padding:10px;width:260px;height:270px;background-color:#fff;border:1px solid #ccc;}
	.awake,.awakeContent{margin-bottom:10px;}
	.textarea{width:100%;height:200px;}
	.videoQuestion{margin-top:30px;}
	.addCommentTit{font-size:14px;}
	.noteTxt{line-height:25px;border-bottom:1px dashed #ccc;margin-bottom:10px;}
	.noteContent{font-size:14px;word-wrap:break-word;}
	.noteCreateTime{color:#aaa;}
</style>
<!-- 按钮 -->
<c:if test="${courseId!='' && courseId!=null}">
	<div class="videoBtn" >
		<span class="mb8">
			<a href="javascript:;" class="commonBtn addCourseNote">创建课堂笔记</a>
		</span>
	</div>
</c:if>

<!--课堂笔记-->
<div class="stylebox courseNote">
	<!--笔记-->
	<p class="awake">课堂笔记</p>
	<!--内容-->
	<p class="awakeContent">
		<textarea class="inpt textarea"></textarea>
	</p>
	<div class="saveResult" ></div>
	<!--提问按钮-->
	<p class="awakeBtn">
		<span class="mb8 mr5">
			<a href="javascript:;" id="awakeSubmit" class="commonBtn">提交</a>
		</span>
		<span class="mb8">
			<a href="javascript:;" id="awakeSubmit" class="closeVideoPopup commonBtn">关闭</a>
		</span>
	</p>
</div>
<!--笔记记录-->
<div class="videoQuestion">
	<div class="myNote clearfix">
		<c:if test="${courseId!='' && courseId!=null}">
		<div class="addCommentBox">
			<h4 class="addCommentTit">课堂笔记（${noteNum}）</h4>
			<c:if test="${noteNum==0}">
				<div style="text-align:center">当前还未找到相关课堂笔记</div>
			</c:if>
			<ul class="note clearfix">
				<c:forEach items="${noteList}" var="noteList" begin="0" end="${courseNum}" varStatus="idxStatus">
					<li class="noteTxt clearfix">
						<p class="noteContent">${noteList.noteContent}</p>
						<p class="noteCreateTime"><fmt:formatDate value="${noteList.updateTime}" pattern="yyyy-MM-dd HH:mm"/></p>
					</li>
				</c:forEach>
			</ul>
		</div>
		<!--查看更多课堂笔记-->
		<c:if test="${listName!='所有视频' && noteNum>9}">
			<a href="javascript:;" class="moreNode commonBtn">查看更多</a>
		</c:if>
		</c:if>
	</div>
</div>
<!-- 遮罩层 -->
<div class="clear"></div>
<script type="text/javascript">
	//点击显示课堂笔记弹窗
	$('.addCourseNote').click(function(){
		if(!isChooseCourse){
			alert('请先选择课程，才能创建课程笔记！');
			return;
		}
		$('.courseNote').show();
		$('.clear').show();
	});
	
	//关闭课程提问或笔记窗口
	$('.closeVideoPopup').click(function(){
		if(confirm('内容尚未保存，确定关闭？')){
			$('.stylebox').hide();
			$('.clear').hide();
			$('.courseNote').hide();
		}
	});
	
	//点击查看更多课堂笔记
	$(".moreNode").click(function(){
		$('.courseStudyNote').load("${contextPath}/course/queryCourseNotes?courseNum=${courseNum}&courseId=${courseId}");
	})
	
	//课程笔记提交
	$("#awakeSubmit").click(function(){
		if("${courseId}"=="" || "${courseId}"==null){
			alert("请先选择对应的课程！");
			return;
		}
		var notesText = $(".textarea").val();
		if(notesText.length>10){
			 $.ajax({
					type:"POST",
					url:"${contextPath}/course/saveCourseNotes",
					data:"notesText="+notesText+"&courseId=${courseId}",
					dataType:'json',
					cache: false,
					success:function(data){
						if(data=="success"){
							alert('笔记创建成功！');
						    setTimeout(function(){
								$('.courseStudyNote').load("${contextPath}/course/queryCourseNotes?courseId=${courseId}");
							},500); 
						    $('.clear').hide();
						}else{
							alert('笔记创建失败');
						}
					}
			 });
		}else{
			alert("至少输入10个字！");
		}
	});
</script>