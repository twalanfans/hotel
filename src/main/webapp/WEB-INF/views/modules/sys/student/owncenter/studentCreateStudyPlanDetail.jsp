<!-- studentCreateStudyPlanDetail.jsp -->
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<style type="text/css">
	.planTitle{line-height:26px;}
	.allTable td{width:16.666%;text-align:center;}
	.chooseCourse ul{padding:10px 0;}
	.chooseCourse li{padding-left:10px;text-align:left;line-height:30px;}
	input[type=button]{background-color:#65cea7;color:#fff;}
	input[type=button]:disabled{background-color:#f7f7f7;color:#333;}
	.subject{position:relative;clear:both;padding:0;}
	.todayCourse{overflow:hidden;margin:0 auto;width:150px;white-space:nowrap;}
	.chooseCourse{position:absolute;top:86%;right:0;left:50%;z-index:9999;display:none;margin-left:-74px;padding:4px 0;max-width:150px;border:1px solid #ccc;background:#fff;}
	.promptTxt p:nth-child(2) i{line-height:70px;}
	.subjectCheck{margin-top:8px;margin-right:4px;}
	.courseName{width:100px;white-space:nowrap;}
	.courseName span{display:block;overflow:hidden;}
	.defaultBtn{margin-right:10px;}
	.commonBtn{margin-right:0;}
</style>
<div class="startPlan">
	<form method="post" action="">
		<!--学习目标-->
		<div class="learnObjective planTitle">
			<span>完成进度表示：</span>
			<span>(计划/实际)</span>　
		</div>
		<!--类别-->
		<div class="category planTitle">
			<span>计划类别：</span>
			<span>周计划</span>
		</div>
		<!--时间周期-->
		<div class="cycle planTitle">
			<span>计划周期：</span>
			<span>
				<i class="startTime">${StudyPlan.startTime}</i>
				至
				<i>${StudyPlan.endTime}</i>
			</span>
		</div>
		<div class="planContent allTable" style="overflow:inherit">
			<table cellpadding="0" cellspacing="0">
				<thead>
					<tr>
						<td>日期</td>
						<td>课程</td>
						<td>微课时长(分钟)</td>
						<td>测试练习数</td>
						<td>答疑提问数</td>
						<td>操作</td>
					</tr>
				</thead>
			
				<tbody>
					<c:forEach var="planDetailList" items="${planDetailList}">   
					<tr>
						<td>
							<p class="planDate"><fmt:formatDate value="${planDetailList.planTime}" pattern="yyyy-MM-dd"/></p>
						</td>
						<td class="subject">
							<div class="courseIdList" style="display:none"></div>
							<c:if test="${planDetailList.courseList!='' && planDetailList.courseList!=null}">
								${planDetailList.courseNameList}
							</c:if>
							<c:if test="${planDetailList.courseList=='' || planDetailList.courseList==null}">
								<div class="todayCourse inpt"  id="mondayPlan" ></div>
								    <div class="chooseCourse">
									    <ul>
											<c:forEach var="courseList" items="${courseList}">   
											<li class="clearfix">
												<input type="checkbox" value="${courseList.courseId}" class="subjectCheck fl" />
												<p class="fl courseName"><span>${courseList.courseName}</span></p>
											</li>
											</c:forEach>
										</ul>
										<input  type="button" class="confirmCourse" value="确认" />
									</div>
							</c:if>
 						</td>
						<td class="courseCount">
	 						<c:if test="${planDetailList.courseList!='' && planDetailList.courseList!=null}">
									${planDetailList.courseCount}/${planDetailList.studyTimeLong}
							</c:if>
							<c:if test="${planDetailList.courseList=='' || planDetailList.courseList==null}">
								<input type="number" name="courseCount"  min="0" value="${planDetailList.courseCount}" class="inpt" />
							</c:if>
						</td>
						<td class="testCount">
							<c:if test="${planDetailList.courseList!='' && planDetailList.courseList!=null}">
									${planDetailList.testCount}/${planDetailList.testQuestionNum}
							</c:if>
							<c:if test="${planDetailList.courseList=='' || planDetailList.courseList==null}">
								<input type="number" name="testCount" min="0"  value="${planDetailList.testCount}" class="inpt" />
							</c:if>
						</td>
						<td class="askCount">
							<c:if test="${planDetailList.courseList!='' && planDetailList.courseList!=null}">
									${planDetailList.askCount}/${planDetailList.askQuestionNum}
							</c:if>
							<c:if test="${planDetailList.courseList=='' || planDetailList.courseList==null}">
								<input type="number" name="askCount"  min="0" value="${planDetailList.askCount}" class="inpt" />
							</c:if>
						</td>
						<td>
								<div class="detailId" style="display:none">${planDetailList.id}</div>
							<c:if test="${planDetailList.courseList!='' && planDetailList.courseList!=null}">
								<input type="button" class="confirmPlan searchBtn disableBtn" disabled="disabled" value="确认计划" />
							</c:if>
							<c:if test="${planDetailList.courseList=='' || planDetailList.courseList==null}">
								<input type="button" class="confirmPlan searchBtn" value="确认计划" />
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</form>
</div>
<div class="promptBox">
	<div class="promptTxt">
		<p>提示</p>
		<p class="clearfix">
			<i class="fa fa-exclamation-circle fl" style="margin-right:5px;"></i>
			<span class="fl">删除将不可恢复，是否确定删除？</span>
		</p>
	</div>
	<div class="promptBtn">
		<a ui-sref="myStudyPlanPage({pageNum : 1, startDate : '', title : ''})" class="defaultBtn" ui-sref-opts="{reload:true}">返回上页面</a>
		<a href="javascript:;" id="confirmBtn" class="commonBtn" >继续制定计划</a>
	</div>
</div>
<script type="text/javascript">
	//通过时间判断是否允许用户编辑计划
	var currentTime ='${currentDay}';
	$('.planDate').each(function(){
		var $todayPlanDate = $(this).text();
		var $todayPlanTime = new Date($todayPlanDate);
		if(new Date().getTime(currentTime) > $todayPlanTime){
			$(this).parents('tr').find('input').attr('disabled',true);
			$(this).parents('tr').find('.confirmPlan').addClass('disableBtn');
		}else{
			$(this).parents('tr').find('.todayCourse').on('click',function(){
				$(this).siblings('.chooseCourse').slideDown('slow');
			});
		}
	});
	
	//点击确定选择今日课程、
	$('.confirmCourse').click(function(){
		var courseIdArry =[];
		var myTodayCourseArry = [];
		$(this).siblings('ul').find('input:checked').each(function(){
			myTodayCourseArry.push($(this).siblings('p').children().html());
			courseIdArry.push($(this).val());
		});
		var myTodayCourseStr = myTodayCourseArry.join(',');
		var courseIdList = courseIdArry.join(",");
		var chooseCourse = $(this).parents('.chooseCourse');
		chooseCourse.siblings('.todayCourse').html(myTodayCourseStr);
		chooseCourse.siblings('.courseIdList').html(courseIdList);
		chooseCourse.slideUp('slow');
	});
	
	var planDetailId = "";
	var courseId = "";
	var courseName = "";
	var courseNum = 0;
	var testNum = 0;
	var submitQuestionNum = 0;
	var testTime = 0;
	var objDisabled=null;
	$('.confirmPlan').on('click',function(){
		objDisabled=$(this);
		planDetailId = $(this).siblings('.detailId').html();
		courseId = $(this).parents('td').siblings('.subject').find('.courseIdList').html();
		courseName = $(this).parents('td').siblings('.subject').find('.todayCourse').html();
		courseNum = $(this).parents('td').siblings('.courseCount').find('input').val();
		testNum = $(this).parents('td').siblings('.testCount').find('input').val();
		submitQuestionNum = $(this).parents('td').siblings('.askCount').find('input').val();
		if(courseId==''||courseName==''){
			alert('请选择课程!');
			return;
		}
		if(!courseNum||!testNum||!submitQuestionNum||courseNum<=0||testNum<=0||submitQuestionNum<=0){
			alert('数据不能为空且必须为正整数!');
			return;
		}
		$('.promptTxt p:eq(1) span').html('确认该计划将不可修改，是否确定此计划！');
		$('.promptBox').fadeIn();
	});
	
	$('#confirmBtn').on('click',function(){
		var oTr = objDisabled.parents('tr');
		$.ajax({
			type:'post',
			url:'${contextPath}/student/updateStudyPlanDetail',
			data:'planDetailId='+planDetailId+"&courseId="+courseId+"&courseName="+courseName+"&courseNum="+courseNum
					+"&testNum="+testNum+"&askQuestionNum="+submitQuestionNum,
			dataType:'json',
			success:function(str){
				if(str!="success"){
					alert('定制失败，请重新操作！');
					return;
				}
				$('.promptBox').fadeOut();
				oTr.find('.subject').text(courseName);
				oTr.find('.courseCount').text(Math.round(courseNum)+'/0');
				oTr.find('.testCount').text(Math.round(testNum)+'/0');
				oTr.find('.askCount').text(Math.round(submitQuestionNum)+'/0');
				oTr.find('input[type="button"]').prop('disabled',true);
				oTr.find('.todayCourse').off('click');
			}
		});
	});
</script>