<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/admin/department_query.css" />
<div class="usermanagementbox">
	<!-- 最新系、专业、班级管理  开始-->
	<!-- 栏目说明 -->
	<div class="navExplain">
		<span>系·专业·班级管理</span>
	</div>
	<c:forEach items="${school}"  var="school" >
	<div class="classCount">
		<%-- <span class="levelName" departId="1" departLevel="0">${Department.departName}</span> --%>
		<span>学校名称：${school.schoolName}</span>&nbsp;&nbsp;共有<span>${Department.departNum}</span>个系，<span>${Department.proNum}</span>个专业，<span>${Department.gradeNum}</span>个班级!
	</div>
	<div class="level">
		<div class="schoolName levelExplain clearfix">
			
			<span class="levelHandle mb8" style="display:inline-block;">
				<a href="javascript:;" class="addDepart">添加系</a>
				<a href="javascript:;" class="editDepart">编辑</a>
			</span>
		</div>
		<%-- <c:forEach items="${departList}"  var="depart1" > --%>
		<c:forEach items="${departList}"  var="depart1" >
		<c:if test="${depart1.parentId eq '1' }">
		<div class="level1 levels">
			<div class="levelExplain explain1 clearfix">
				<span class="levelName" departId="${depart1.departId}" departLevel="1">${depart1.departName}</span>
				<span class="levelHandle">
					<a href="javascript:;" class="addDepart">添加下一级</a>
					<a href="javascript:;" class="editDepart">编辑</a>
					<a href="javascript:;" class="deleteDepart">删除</a>
				</span>
			</div>
			<c:forEach items="${departList}"  var="depart2" >
			<c:if test="${depart2.parentId == depart1.departId }">	
			<div class="level2 levels">
				<div class="levelExplain explain2 clearfix">
					<span class="levelName" departId="${depart2.departId}" departLevel="2">${depart2.departName}</span>
					<span class="levelHandle">
						<a href="javascript:;" class="addDepart">添加下一级</a>
						<a href="javascript:;" class="editDepart">编辑</a>
						<a href="javascript:;" class="deleteDepart">删除</a>
					</span>
				</div>
				<c:forEach items="${departList}"  var="depart3" >
				<c:if test="${depart3.parentId == depart2.departId }">
				<div class="level3 levels">
					<div class="levelExplain explain3 clearfix">
						<span class="levelName" departId="${depart3.departId}" departLevel="3">${depart3.departName}</span>
						<span class="levelHandle">
							<a href="javascript:;" class="editDepart">编辑</a>
							<a href="javascript:;" class="deleteDepart">删除</a>
						</span>
					</div>
				</div>
				</c:if>
				</c:forEach>
			</div>
			</c:if>
			</c:forEach>
		</div>
		</c:if>
		</c:forEach>
	</div>
	</c:forEach>
	<!-- 结束 -->
	<!-- 系、专业、班级编辑框 -->
	<div class="classEditBox">
		<p>
			<label>
				<span>名称</span>
			</label>
			<input type="text" id="departName" class="inpt" />
		</p>
		<p>
			<span class="commonBtn" id="confirmEdit">确认</span>
			<span class="commonBtn" id="cancelEdit">取消</span>
		</p>
	</div>
	<!-- 结束 -->
</div>
<!-- 遮罩层 -->
<div class="clear"></div>
<script type="text/javascript">
	//专业、班级展开或收缩
	$('.usermanagementbox').on('click','.level1',function(){
		$(this).find('.level2').slideDown('slow');
		$(this).siblings('.level1').find('.level2').slideUp('slow');
	});
	$('.usermanagementbox').on('click','.level2',function(){
		$(this).find('.level3').slideDown('slow');
		$(this).siblings('.level2').find('.level3').slideUp('slow');
	});
	
	//添加下一级
	var departId="",level="",$addDepart,parentId,childLevel;
	$('.usermanagementbox').on('click','.addDepart',function(){
		departId = '';
		departName = '';
		level = '';
		$addDepart = $(this);
		//获取本级ID和level
		departId = $(this).parent().siblings('.levelName').attr('departId');
		level = $(this).parent().siblings('.levelName').attr('departLevel');
		//计算用于生成子集的父级ID和本级level
		parentId = departId ? parseInt(departId) : 1;
		childLevel = level ? parseInt(level)+1 : 1;
		$('.classEditBox,.clear').show();
	});
	
	//编辑重命名
	$('.level').on('click','.editDepart',function(){
		departId = '';
		departName = '';
		level = '';
		$editDepart = $(this);
		departId = $editDepart.parent('.levelHandle').siblings('.levelName').attr('departid');
		$('.classEditBox,.clear').show();
	});
	
	//确认添加或更改
	$('#confirmEdit').click(function(){
		var departName = $('#departName').val();
		if(departName==""||departName==null){
			alert("请填写名称！");
			return;
		}
		if(level!=""){
			$.ajax({
				url : '${contextPath}/depart/insertDepart',
				type : 'post',
				dataType : 'json',
				data : {
					'departId' : departId ? departId : 1,
					'departName' : departName,
					'level' : level ? parseInt(level)+1 : 1
				},
				success : function(data){
					if(data!="error"){
						var parent = $addDepart.parent().parent();
						var newHtmlStr = '';
						if(childLevel==3){
							newHtmlStr = '<div class="levels level'+childLevel+'" style="display:block;"><div class="clearfix levelExplain explain'+childLevel+'"><span class="levelName" departId="'+data.departId+'" departLevel="'+childLevel+'">'+departName+'</span><span class="levelHandle"><a href="javascript:;" class="editDepart">编辑</a><a href="javascript:;" class="deleteDepart">删除</a></span></div></div>';
						}else{
							newHtmlStr = '<div class="levels level'+childLevel+'" style="display:block;"><div class="clearfix levelExplain explain'+childLevel+'"><span class="levelName" departId="'+data.departId+'" departLevel="'+childLevel+'">'+departName+'</span><span class="levelHandle"><a href="javascript:;" class="addDepart">添加下一级</a><a href="javascript:;" class="editDepart">编辑</a><a href="javascript:;" class="deleteDepart">删除</a></span></div></div>';
						}
						parent.after(newHtmlStr);
						$('#departName').val('');
						alert('添加成功');
					}else{
						$('#departName').val('');
						alert("添加失败！");			
					}
					$('.classEditBox,.clear').hide();
				}
			});
		}else{
			$.ajax({
				url : '${contextPath}/depart/updateDepart',
				type : 'post',
				dateType : 'json',
				data : {
					'departId' : departId,
					'departName' : departName
				},
				success : function(data){
					if(data!="error"){
						$editDepart.parent('span').siblings('span').text(data);
						$('.classEditBox,.clear').hide();
						$('#departName').val('');
						alert('更新成功！');
					}else{
						$('#departName').val('');
						alert('更新失败！');

					}
				}		
			});
		}
	});
	
	//删除
	$('.level').on('click','.deleteDepart',function(){
		departId = $(this).parent('span').siblings('span').attr('departid');
		var tmpArry = [];
		var deleteNode = $(this).parent().parent().parent('.levels').find('.levelName');
		var deleteNodeLen = deleteNode.length;
		for(var i=0; i<deleteNodeLen; i++){
			tmpArry.push(deleteNode[i].getAttribute('departid'));
		}
		if(confirm('是否确定删除？')){
			var $node = $(this).parent().parent().parent('.levels');
			$.ajax({
				type:'post',
				url:'${contextPath}/depart/deleteDepart',
				data:"departIds="+tmpArry, 
				dataType:'json',
				success:function(data){
					if(data=="success"){
						$node.remove();
						alert('删除成功！');
					}else{
						alert('删除失败！');
					}
				}
			});
		}
	});
	
	//取消
	$('#cancelEdit').click(function(){
		$('.classEditBox,.clear').hide();
	});
</script>