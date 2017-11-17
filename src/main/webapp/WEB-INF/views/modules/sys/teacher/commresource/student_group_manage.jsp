<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
	.selectGroup{padding:15px 0;}
	.groupTab ul li{float:left;margin-bottom:10px;font-weight:bold;margin-right:20px;cursor:pointer;}
	.active{color:#1278f6;border-bottom:2px solid #1278f6;}
</style>
<!-- 栏目说明 -->
<div class="navExplain">
	<span>用户群组管理</span>
</div>
<div class="controllergroupbox">
	<!-- 所有群组 -->
	<div class="selectGroup clearfix">
		<span class="mb8 mr5 fl">
			<label>现有群组：</label>
			<select class="selectlist inpt" id="roleId" onchange="queryStudent(this.value);">
				<option value="">—请选择—</option>
				<c:forEach items="${roleList}"  var="roleList" >
				<option value="${roleList.roleId}">${roleList.name}</option>
				</c:forEach>
			</select>
		</span>
		<span class="mb8 mr5 fl">
			<a href="javascript:;" class="defaultBtn" onclick="addGroup();">新建群组</a>
		</span>
		<span class="mb8 mr5 fl">
			<a href="javascript:;" class="defaultBtn" onclick="deleteGroup();">删除群组</a>
		</span>
	</div>
	<!-- 加入群组和移除群组选项卡切换 -->
	<div class="groupTab clearfix">
		<ul>
			<li class="active">将学生加入群组</li>
			<li>将学生移出群组</li>
		</ul>
	</div>
	<div class="grouplistbox clearfix">
		<div class="allStudent groupContentTab">
			<%@ include file="all_student_query.jsp"%>
		</div>
		<div class="studentGroup groupContentTab" style="display:none;">
			<%@ include file="source_student_group.jsp"%>
		</div>
	</div>
</div>
<div class="uploadFileWindow">
	<div class="uploadFileTitle">
		<h4>新建群组</h4>
	</div>
	<div class="uploadFileContent clearfix">
		<p style="padding:10px;">群组名称<input type="text"  id="groupName" class="inpt" placeholder="请输入群组名称" /></p>
		<p style="padding:0 0 10px 10px">
			<span class="mb8 mr5">
				<a href="javascript:;" class="commonBtn" onclick="confirmSave();">确认</a>
			</span>
			<span class="mb8 mr5">
				<a href="javascript:;" class="commonBtn" onclick="closeWin();">放弃</a>
			</span>
		</p>
	</div>
</div>
<style type="text/css">
	.userPrompt{position:fixed;top:30%;left:50%;margin-left:-155px;width:310px;height:100px;background-color:#fff;border:1px solid #ccc;}
	.userPrompt p{height:30px;line-height:30px;margin-bottom:10px;text-align:center;}
	.promptTitle{background-color:#6bc5a4;color:#fff;}
</style>
<div class="userPrompt " style="display:none;">
	<p class="promptTitle ">
		<b>温馨提示：</b>
	</p>
	<p class="promptContent"></p>
</div>
<script type="text/javascript">
	//群组切换选项卡
	$('.groupTab ul li').click(function(){
		var $index = $(this).index();
		$(this).addClass('active').siblings('li').removeClass('active');
		$('.groupContentTab').eq($index).show().siblings('.groupContentTab').hide();
	});
	
	//用户提示：显示/隐藏
	function userPrompt(field){
		$('.userPrompt').fadeIn('slow');
		$('.promptContent').text(field);
		setTimeout(function(){
			$('.userPrompt').fadeOut('slow');
		},2500);
	}
	
	//添加群组
	function addGroup(){
		$('.uploadFileWindow').fadeIn();
	}
</script>