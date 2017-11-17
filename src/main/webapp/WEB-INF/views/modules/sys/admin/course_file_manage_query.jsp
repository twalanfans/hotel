<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="messagesearchbox" >
	<!-- 栏目说明 -->
	<div class="navExplain">
		<span>所有课件</span>
	</div>
	<!-- 搜索 -->
	<div class="searchBeaf">
		<div id="Search" >
			<span class="mb8 mr5">
				<label for='searchFileName'>课件名称：</label>
				<input type="text" ng-model="fileName" id="searchFileName" class="formClass inpt" />
			</span>
			<span class="mb8 mr5">
				<label for='searchStatus'>课件状态：</label>
				<select ng-model="status" id="searchStatus" class="searchStatus inpt">
					<option value="" >——请选择——</option>
					<option value="0" class="op">待审核</option>
					<option value="1" class="op">审核通过</option>
					<option value="2" class="op">审核不通过</option>
				</select>
			</span>
			<span class="mb8 mr5">
				<a href="javascript:;" ui-sref="filemanagement({pageNum : 1, fileName : fileName, status : status})" ui-sref-opts="{reload:true}" class="defaultBtn">搜索</a>
			</span>
			<span class="mb8 mr5">
				<a href="javascript:;" id="deleteFile" class="defaultBtn">删除</a>
			</span>
		</div>
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
					<td>所属课程</td>
					<td>名称</td>
					<td>状态</td>
					<td>大小</td>
					<td>时间</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${pageInfo.list}"  var="fileList" >
				<tr>
					<td><input type="checkbox" class="selrolebtn1" name="fileId" value="${fileList.fileId}"></td>
					<td>${fileList.courseName}</td>
					<td title="${fileList.fileName}">${fn:substring(fileList.fileName,0,10)}</td>
					<c:if test="${fileList.status=='0'}"><td>待审核</td></c:if>
					<c:if test="${fileList.status=='1'}"><td>审核通过</td></c:if>
					<c:if test="${fileList.status=='2'}"><td>审核不通过</td></c:if>
					<td>${fileList.fileSize}</td>
					<td>${fileList.createTime}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
</div>
<!-- 翻页 -->
<div class="pageList">
	<span>共<b>${pageInfo.total}</b>条</span>
	<span>每页<b>${pageInfo.pageSize}</b>条</span>
	<span>当前第<b>${pageInfo.pageNum}</b>页</span>
	<span>
		<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}"><span>首页</span><span>上一页</span></c:if>
          	<c:if test="${pageInfo.pageNum != 1 && pageInfo.pages > 1}">
			<a href="javascript:;" ng-click="shouye(1);">首页</a>
			<a href="javascript:;" ng-click="prevPage(${pageInfo.pageNum-1})">上一页</a>
		</c:if>
		<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span>下一页</span><span>尾页</span></c:if>
          	<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
			<a href="javascript:;" ng-click="nextPage(${pageInfo.pageNum+1})">下一页</a>
			<a href="javascript:;" ng-click="endPage(${pageInfo.pages})">尾页</a>
		</c:if>
	</span>
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
<script type="text/javascript">
$("#searchStatus option[value='${status}']").attr("selected","selected");
	//配置日历表组件
	$("#searchStartTime,#searchEndTime").on("click",function(e){
		e.stopPropagation();
		$(this).lqdatetimepicker({
			css : 'datetime-day',
			dateType : 'D',
			selectback : function(){
			}
		});
	});
 	
	//删除课件
	var fileId = [];
	$('#deleteFile').click(function(){
		$('input[name="fileId"]:checked').each(function(){ 
			fileId.push($(this).val());
		});  
		if(fileId.length<1){
			alert("请选择需要删除的项！");
			return;
		}
		$('.promptTxt p:eq(1) span').html('删除将不可恢复，是否确定删除！');
		$('.promptBox').fadeIn();
	});
	
	//确认删除
	$('#confirmBtn').on('click',function(){
		var $prompt = $('.promptTxt p:eq(1) span'),
			$promptBox = $('#promptBox');
		$.ajax({
			type:'post',
			url:'${contextPath}/courseFile/deleteCourseFile',
			data:'fileId='+fileId,
			dataType:'json',
			success:function(str){
				if(str=="success"){
					window.location.reload();
				}else{
					alert("操作失败！");
					$promptBox.fadeOut();
				}
			}
		});
	});
	
	//关闭弹窗
	$('#cancelBtn').click(function(){
		$('.promptBox').fadeOut();
	});
	
	//全选/取消
	$('#selallbtn1').on('click',function(){
		if($('#selallbtn1').prop('checked')){
			$('.selrolebtn1').prop('checked',true);
		}else{
			$('.selrolebtn1').prop('checked',false);
		}
	});
</script>