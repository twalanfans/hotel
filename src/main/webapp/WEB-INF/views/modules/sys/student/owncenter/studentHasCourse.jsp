<!-- studentHasCourse.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import= "com.module.course.entity.Course"%> 
<%@ page import= "com.module.course.entity.CourseFile"%> 
<!--mainBox-->
<!-- 引入jstl标签库 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link href="css/popups.css" rel="stylesheet" type="text/css" /> 
<div class="mainBox clearfix" ng-controller="mycontroller">
	<!--mainContent-->
	<div style="padding:15px">
		<div class="mainWrap">
			<div class="mainWrapBottom">
				
				<!--已选课程展示-->
				<div class="alreadyChoose">
					<table cellpadding="0" cellspacing="0">
					<td width="80px">
						<thead>
							<tr>
								<td>科目</td>
								<td>课程名称</td>
								<td>讲师</td>
								<td>课程时长(分钟)</td>
								<td>学习进度</td>
								<td>操作</td>
							</tr>
						</thead>
						<tbody>
						<c:forEach var="course" items="${pageInfo.list}">   
  							<tr>
  							<td class="hide">${course.courseId}</td>  
  							<td>${course.subjectId}</td>
  							<td class="courseName">${course.courseName}</td>
  							<td>${course.teacherName}</td>
  							<td>${course.timelong}</td>    
   							<td>${course.timelong}</td>
   							<td>${course.timelong}</td>
   							</tr> 
   						</c:forEach>   
												
						</tbody>
					</table>
				</div>
				<!--加载更多-->
	<div class="pagerBar">
    	<div class="pageBarbox clearfix">
    		<ul class="otherInfo">
                <li><label>共&nbsp;${pageInfo.total}&nbsp;条&nbsp;&nbsp;每页&nbsp;${pageInfo.pageSize}&nbsp;条&nbsp;&nbsp;当前第&nbsp;${pageInfo.pageNum}&nbsp;页</label></li>
            </ul>
            <!-- epages -->
            <div class="epages clearfix">
            	<c:if test="${pageInfo.pageNum == 1 || pageInfo.pageNum == 0}"><span>首页</span><span>上一页</span></c:if>
            	<c:if test="${pageInfo.pageNum != 1 && pageInfo.pages > 1}">
            	<a href="#" ng-click="studentHasCourse('1')">首页</a>
            	<a href="#" ng-click="studentHasCourse('${pageInfo.pageNum-1}')">上一页</a>
            	</c:if>
            	<c:if test="${pageInfo.pageNum == pageInfo.pages || pageInfo.pages == 0}"><span>下一页</span><span>尾页</span></c:if>
            	<c:if test="${pageInfo.pageNum != pageInfo.pages && pageInfo.pages != 0}">
            	<a href="#" ng-click="studentHasCourse('${pageInfo.pageNum+1}')">下一页</a>
            	<a href="#" ng-click="studentHasCourse('${pageInfo.pages}')">尾页</a>
            	</c:if>
            </div>
            <!-- epages -->
        </div>
    </div>
				
			</div>
		</div>
	</div>
</div>
<div class="showinformation">
 	<div class="shadow"></div>
	<a href="javascript:;" class="closebtn fa fa-times-circle-o"></a>
	<div class="showlistbox">
		<table class="showlist">
			<thead>
				<tr>
					<th>课件名称</th>
					<th>课件类型</th>
					<th>课件时长</th>
					<th>课件简介</th>
					<th>课件缩略图</th>
					<th>视频链接</th>
				</tr>
			</thead>
			<tbody>
			<%-- <c:forEach var="coursefile" items="${cfList}">
				<tr>
					<td>${coursefile.fileName}</td>
					<td>${coursefile.fileType}</td>
					<td>${coursefile.videoOpentimeLong}</td>
					<td>${coursefile.introduce}</td>
					<td>${coursefile.videoImg}</td>
					<td><a href="#" class="coursewarename">${coursefile.videoId}</a></td>
				</tr>
				</c:forEach>   
			 --%>
			 </tbody>
		</table>
		<div class="showvideo">
			<video src="#" controls="controls" width="100%" height="100%">
				您的浏览器不支持此格式！
			</video>
		</div>
	</div>
	
</div>
<script type="text/javascript">
	$('.courseName').on('click',function(){
		var courseId=$(this).siblings('.hide').html();
	
		
		$.ajax({
			url:'${contextPath}/student/courseFileList',
			type:'POST',
			dataType:'json',
			data:{'courseId':courseId},
			success:function(data){
			var oData=eval(data.success);
			
			var arr = ["MP4", "mp4"];
			
			var str = "";
			for(var i=0; i<oData.length; i++){
				var type =oData[i].fileType; 
				
				var fileName=oData[i].fileName;
				if(type == "mp4" || type == "MP4" )
					str += "<tr><td>"+oData[i].fileName+"</td><td>"+oData[i].fileType+"</td><td>"+oData[i].videoOpentimeLong+"</td><td>"+oData[i].introduce+"</td><td>"+oData[i].videoImg+"</td><td><a href='javascript:;' class='coursewarename' optType='showvideo' fileName="+fileName+" videoUrl="+oData[i].videoId+">点击观看</a></td></tr>";
				else
					str += "<tr><td>"+oData[i].fileName+"</td><td>"+oData[i].fileType+"</td><td>"+oData[i].videoOpentimeLong+"</td><td>"+oData[i].introduce+"</td><td>"+oData[i].videoImg+"</td><td><a href='javascript:;' class='coursewarename' optType='download' fileName="+fileName+" videoUrl="+oData[i].videoId+">点击下载</a></td></tr>";
					
			}
			$(".showlist tbody").html(str);
		
			$('.showinformation').fadeIn();
			}
			
		}); 
		$('.showlist').on('click','.coursewarename',function(){
			if($(this).attr("optType")=='showvideo')
				{			
					$('.showvideo').fadeIn();
					$('.showvideo video').attr("src",$(this).attr("videoUrl"));	
				}
			else
				{
					videoUrl1=$(this).attr("videoUrl");
					
					downloadFile($(this).attr("fileName"),videoUrl1);
				}
				
		});
		$('.closebtn').on('click',function(){
			$('.showvideo').fadeOut();
			$('.showinformation').fadeOut();
		});
	});
	
	
function downloadFile(fileName, content){
		    var aLink = document.createElement('a');
		    var blob = new Blob([content]);
		    var evt = document.createEvent("HTMLEvents");
		    evt.initEvent("click", false, false);//initEvent 不加后两个参数在FF下会报错, 感谢 Barret Lee 的反馈
		    aLink.download = fileName;
		    aLink.href = URL.createObjectURL(blob);
		    aLink.dispatchEvent(evt);
		}

<!--

//-->
</script>
<!-- </html> -->