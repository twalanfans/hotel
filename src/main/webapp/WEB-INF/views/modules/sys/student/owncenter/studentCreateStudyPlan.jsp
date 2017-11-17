<!-- studentCreateStudyPlan.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!--日历样式表-->
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/calendar.css" />
<style type="text/css">
	.target_time{background-color:#fff;padding:10px;}
	.target_time p{margin-bottom:10px;}
</style>
<!-- 栏目说明 -->
<div>
	<div class="navExplain">
		<span>开始创建学习计划</span>
	</div>
	<div class="startPlan">
		<div class="target_time">
			<form id="form1" action="${contextPath}/student/createStudyPlanDetail" method="post">
				<p>
					<span>计划名称：</span>
					<input type="text" class="planTarget inpt" id="planTarget"  required />
				</p>
				<p>
					<span>计划类别：</span>
					<select name="planType" id="planType" class="inpt" required>
						<option value="1">周计划</option>
					</select>
				</p>
				<p class="clearfix">
					<span>学习周期：</span>
					<input type="text" class="mh_date inpt" id="startDate" readonly="true" />
					<i id="endDate"  style="display:none">至
						<span></span>-
						<span></span>-
						<span></span>
					</i>
				</p>
				<a href="javascript:;" onclick="myStudyPlanPageDetail();" class="commonBtn">开始创建</a>
			</form>
		</div>
	</div>
	<div class="promptBox" >
		<div class="promptTxt">
			<p>提示</p>
			<p class="clearfix">
				<i class="fa fa-exclamation-circle fl" style="margin-right:5px;"></i>
				<span class="fl" style="margin-top:4px;">删除将不可恢复，是否确定删除？</span>
			</p>
		</div>
		<div class="promptBtn">
			<a ui-sref="myStudyPlanPage({pageNum : 1, startDate : '', title : ''})"  ui-sref-opts="{reload:true}" id="cancelBtn" class="defaultBtn">查看所有计划</a>
			<a href="javascript:;" ng-click="editStudyPlanDetail('');" id="confirmBtn" class="commonBtn">编辑此计划</a>
		</div>
	</div>
	<input type="text" id="planId" style="display:none;" />
</div>
<script type="text/javascript" src="${ctxStatic}/js/calendar.js"></script>
<script type="text/javascript">
//开始创建学习计划
function myStudyPlanPageDetail(){
	var title=document.getElementById('planTarget').value;
	var type=document.getElementById('planType').value;
	var startDate=document.getElementById('startDate').value;
	var endDate= document.getElementById('endDate').value;
	var currentDate="${currentDate}";
	var startTime = new Date(startDate); 
		var $promptTxt = $('.promptTxt p:eq(1) span');
	if(title==null || title==""){
		 alert("计划名称不能为空！");
		 return false;
	}else if(new Date().getTime(currentDate) > startTime){
		alert("计划日期不能早于当前日期！");
		return false;
	}else{
		$.ajax({
			type:'post',
			url:'${contextPath}/student/saveNewStudyPlan',
			data:"type="+type+"&title="+title+"&startDate="+startDate+"&endDate="+endDate,
			dataType:'json',
			success:function(str){
					switch(str){
						case 'error':
							alert('计划制定失败，请重新操作！');
							break;
						case 'exist':
							alert('计划制定失败,未来几天已有计划,请重新选择日期！');
							break;
						default:
							$('#planId').val(str);
							$('.promptTxt p:eq(1) span').html('计划制定成功！');
							$('.promptBox').fadeIn();
					}
				}
			});
		}
	};

	//配置日历表组件
	$("input.mh_date").manhuaDate({					       
		Event : "click",//可选				       
		Left : 0,//弹出时间停靠的左边位置
		Top : -16,//弹出时间停靠的顶部边位置
		fuhao : "-",//日期连接符默认为-
		isTime : false,//是否开启时间值默认为false
		beginY : 1949,//年份的开始默认为1949
		endY :2100//年份的结束默认为2049
	});
	
	//自动匹配学习计划结束的日期
	var currentTime = new Date().getFullYear()+"-"+(new Date().getMonth()+1)+"-"+new Date().getDate();
	$("body").on("click",".tablebg",function(){
		setTimeout(function(){
			//获取当前日期的value
			var start = $("#startDate").val();
			
				//去掉"-"，转化为数组
				var startArry = start.split("-");
				//分别获取到开始日期的年、月、日
				var startYear = startArry[0];
				var startMonth = startArry[1];
				var startDate = startArry[2];
				var endDate = parseInt(startDate)+6;
				//定义截至日期
				var end = new Date();
				//设置截止日期的年月日（开始日期往后推7天）
				end.setFullYear(startYear);
				end.setMonth(startMonth);
				end.setDate(endDate);
				//将截止日期显示出来
				$("#endDate span").eq(0).html(end.getFullYear());
				$("#endDate span").eq(1).html(end.getMonth());
				$("#endDate span").eq(2).html(end.getDate());
				$("#endDate").show();	
			},100)
	})
	
	$(".planTarget").blur(function(){
		var temp = '${splist}';
		temp= temp.replace("[","").replace("]","");
		var splist =  temp.split(",");
		for(var i=0;i<splist.length;i++)
			{
			var str1= splist[i];
			splist[i]=str1.trim();
			}
		
		var str = $(this).val();
		for(var i=0; i<splist.length; i++){
			if(splist[i]==str){
				alert("目标名称重复，请重新输入");
			}
		}
	})
</script>
