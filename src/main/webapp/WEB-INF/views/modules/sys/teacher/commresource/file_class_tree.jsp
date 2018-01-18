<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8" />
	<title>文件分类树</title>
	<meta http-equiv="X-UA-Compatible" content="IE=11,IE=10,IE=9,IE=8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/alert.css" />	
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/font.css">
	<script type="text/javascript" src="${ctxStatic}/js/jquery.min.js"></script>
	<style type="text/css">
		.folder{color:#efef0f;}
		.addFileWindow{width:100%;max-width:300px;height:150px;border:1px solid #ccc;background-color:#fff;display:none;position:fixed;top:32%;left:4%;border-radius:6px;}
		.addFileTitle{height:30px;line-height:30px;padding-left:10px;background-color:#76bf64;color:#fff;font-size:12px;border-radius:6px 6px 0 0;}
		.addFileContent>p{width:270px;margin:20px auto; font-size:12px;}
		.addFileContent>p .searchBtn{margin-left:20px;border-radius:6px;}
		.cateTit h3{font-size:16px;height:30px;line-height:30px;text-align:center;background-color:#1a19a0;color:#fff;margin-bottom:10px;}
		.cateTit .searchBtn{margin-left:10px;}
		.cate1{padding-left:20px;}
		.cname{line-height:30px;padding:0 6px;}
		.selCname{background-color:rgba(26, 25, 160, 0.4);border-radius:4px;}
		.waistband,.cname{color:#fff;float:left;display:inline;}
		.cate .waistband{width:20px;text-align:center;font-size:26px;}
		.folderIcon{margin-top:2px;width:26px;}
	</style>
</head>
<body>
<div class="mask"></div>
<div class="cate">
	<div class="cateTit clearfix">
		<input type="hidden" class="waistband" />
		<c:if test="${dataType=='1' }">
			<h3 >课件资料(${courseName})</h3>
		</c:if>
		<c:if test="${dataType=='2' }">
			<h3 >案例库</h3>
		</c:if>
		<c:if test="${dataType=='3' }">
			<h3 >图片库</h3>
		</c:if>
		<c:if test="${dataType=='4' }">
			<h3 >标准工作流程库</h3>
		</c:if>
		<c:if test="${dataType=='5' }">
			<h3 >法律法规及规范标准库</h3>
		</c:if>
		<c:if test="${isCommon=='2357abv48xc'}">
			<input type="button" class="searchBtn" onclick="addClass('0',1);" value="添加分类"/>
		</c:if>
	</div>
	<c:forEach items="${fileClassList}"  var="class1" >
	<c:if test="${class1.parentId eq '0' }">
		<div class="cate1 cates">
			<p class="clearfix">
				<span class="waistband" status="false">+</span>
				<a href="javascript:;" class="cname clearfix" onclick="showFileDetail(${class1.classId},'${class1.className}','${class1.createUser}',this);"><span class="fa fa-folder-open folder fl folderIcon"></span><span class="fl">${class1.className}</span></a>
				<c:if test="${isCommon=='2357abv48xc'}">
				<ul class="menu">
					<li class="addCate" onclick="addClass(${class1.classId},2);">新建文件夹</li>
					<li class="updateCate" onclick="editClass(${class1.classId})">重命名</li>
					<li class="deleteCate" onclick="deleteClass(${class1.classId})">删除</li>
				</ul>
				</c:if>
			</p>
			<c:forEach items="${fileClassList}"  var="class2">
			<c:if test="${class2.parentId == class1.classId }">	
				<div class="cate2 cates">
					<p class="clearfix">
						<span class="waistband" status="false">+</span>
						<a href="javascript:;" class="cname clearfix" onclick="showFileDetail(${class2.classId},'${class1.className}/${class2.className}','${class1.createUser}',this);"><span class="fa fa-folder-open folder fl folderIcon"></span><span class="fl">${class2.className}</span></a>
						<c:if test="${isCommon=='2357abv48xc'}">
						<ul class="menu">
							<li class="addCate" onclick="addClass(${class2.classId},3);">新建文件夹</li>
							<li class="updateCate" onclick="editClass(${class2.classId})">重命名</li>
							<li class="deleteCate" onclick="deleteClass(${class2.classId})">删除</li>
						</ul>
						</c:if>
					</p>
					<c:forEach items="${fileClassList}"  var="class3" >
					<c:if test="${class3.parentId == class2.classId }">
						<div class="cate3 cates">
							<p class="clearfix">
								<span class="waistband" status="false">+</span>
								<a href="javascript:;" class="cname clearfix" onclick="showFileDetail(${class3.classId},'${class1.className}/${class2.className}/${class3.className}','${class1.createUser}',this);"><span class="fa fa-folder-open folder fl folderIcon"></span><span class="fl">${class3.className}</span></a>
								<c:if test="${isCommon=='2357abv48xc'}">
								<ul class="menu">
									<li class="addCate" onclick="addClass(${class3.classId},4);">新建文件夹</li>
									<li class="updateCate" onclick="editClass(${class3.classId})">重命名</li>
									<li class="deleteCate" onclick="deleteClass(${class3.classId})">删除</li>
								</ul>
								</c:if>
							</p>
							<c:forEach items="${fileClassList}"  var="class4" >
							<c:if test="${class4.parentId == class3.classId }">
								<div class="cate4 cates">
									<p class="clearfix">
										<input type="hidden" class="waistband" />
										<a href="javascript:;" class="cname clearfix" onclick="showFileDetail(${class4.classId},'${class1.className}/${class2.className}/${class3.className}/${class4.className}','${class1.createUser}',this);"><span class="fa fa-folder-open folder folderIcon fl"></span><span class="fl">${class4.className}</span></a>
										<c:if test="${isCommon=='2357abv48xc'}">
										<ul class="menu">
											<li class="updateCate" onclick="editClass(${class4.classId})">重命名</li>
											<li class="deleteCate" onclick="deleteClass(${class4.classId})">删除</li>
										</ul>
										</c:if>
									</p>
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
	</c:if>
	</c:forEach>
</div>
<div class="addFileWindow">
	<div class="addFileTitle">文件夹命名</div>
	<div class="addFileContent">
			<p><label>文件夹名称：<input type="text"  id="newClassName"  /></label></p>
		<p>
			<input type="button" class="searchBtn"  onclick="confirmSave();" value="保存"/>
			<input type="button" class="searchBtn"  onclick="closeFileWindow();" value="关闭"/>
		</p>
	</div>
</div>
</body>
<script type="text/javascript">
	//文件详情
	function showFileDetail(classId,filePath,user,obj){
		$('.cname').removeClass('selCname');
		$(obj).addClass('selCname');
		var url = "${contextPath}/courseFile/showFileDetailPage?classId="+classId+"&dataType=${dataType}"+"&filePath="+filePath+"&fileName="+"&u-si="+user;
	 	$('.show_file_query').load(url);
	}
	
	/* $('.cname').on('contextmenu',function(ev){
		var ev = ev || winow.event;
			$('.menu').hide();
			$(this).parent().siblings('.menu').show();
			return false;
	}); */
	//初始化菜单，判断是否有子级
	$('.cate1').each(function(){
		var $this = $(this);
		if($this.find('.cates').length==0){
			$this.find('.waistband').text('-');
			$this.find('.waistband').attr('status','true');
		}
	});
	$('.cate2').each(function(){
		var $this = $(this);
		if($this.find('.cates').length==0){
			$this.find('.waistband').text('-');
			$this.find('.waistband').attr('status','true');
		}
	});
	$('.cate3').each(function(){
		var $this = $(this);
		if($this.find('.cates').length==0){
			$this.find('.waistband').text('-');
			$this.find('.waistband').attr('status','true');
		}
	});
	$('.cate4').each(function(){
		var $this = $(this);
		if($this.find('.cates').length==0){
			$this.find('.waistband').text('-');
			$this.find('.waistband').attr('status','true');
		}
	});

	//次级栏目显示或隐藏
	$('.waistband').click(function(){
		var $this = $(this);
		if($this.attr('status')=='false'){
			$this.parent('p').siblings('.cates').show();
			$this.text('-');
			$this.attr('status','true');
		}else{
			$this.parent('p').siblings('.cates').hide();
			$this.text('+');
			$this.attr('status','false');
		}
	});
	
	//添加栏目窗口隐藏
	function closeFileWindow(){
		$('.addFileWindow').fadeOut();
		$('#newClassName').val("");
	//	$('.mask').hide();
	}
	//新建文件夹
	var classId="",level="";
	function addClass(classIds,levels){
		classId = classIds;
		level = levels;
		$('.addFileWindow').show();
	}
	
	//重命名
	function editClass(classIds){
		classId = classIds;
		$('.addFileWindow').show();
	}
	
	function refreshWin(){
		window.location.reload();
	}
	
	function confirmSave(){
		var className = $('#newClassName').val();
		if(className==""||className==null){
			alert("请填写文件夹名！");
			return
		}
		if(level!=""){
			$.ajax({
				url : '${contextPath}/courseFile/addClassFile',
				type : 'post',
				dataType : 'json',
				data : {
					'classId' : classId,
					'className' : className,
					'dataType' : '${dataType}',
					'courseId' : '${courseId}',
					'level' : level
				},
				success : function(data){
					if(data=="error"){
						alert('文件夹创建失败！');
						refreshWin();
					}else{
						alert("成功！");			
						refreshWin();
					}
				}
			});
		}else{
			$.ajax({
				url : '${contextPath}/courseFile/updateClassFileName',
				type : 'post',
				dateType : 'json',
				data : {
					'className' : className,
					'classId' : classId
				},
				success : function(data){
					if(data=="success"){
						alert("更新成功！");
						refreshWin();
					}else{
						alert('更新失败！');
						refreshWin();
					}
				}		
			});
		}
	}

	function deleteClass(classId){
		if(confirm('删除将导致该文件夹下所有文件删除且不可恢复，是否确定删除！'));
		$.ajax({
			url : '${contextPath}/courseFile/deleteAttachFileByClassId',
			type : 'post',
			dateType : 'json',
			data : {
				'classId' : classId
			},
			success : function(data){
				if(data=="success"){
					alert('删除成功！');
					refreshWin();
				}else{
					alert('删除失败！');
					refreshWin();
				}
			}
		});
	}
	
	$('body').click(function(){
		$('.menu').hide();
	})
</script>
</html>