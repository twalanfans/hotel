<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/calendar.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/dataAnalysis.css" />
<div class="navExplain">
	<span>数据分析图</span>
</div>
<ul class="dataNav clearfix">
	<li class="on"><a href="javascript:;">微课时长</a></li>
	<li><a href="javascript:;">组卷测题数</a></li>
	<li><a href="javascript:;">在线提问数</a></li>
</ul>
<ul class="dataBox">
	<li class="dataShow">
		<div class="searchBeaf">
			<form method="post" id="testSearchStart" class="ng-pristine ng-valid">
				<span class="mb8 mr5">
					<span>开始时间（延后7天）：</span>
					<input type="text" readonly id="searchStartTime" class="testTime inpt" value="">
				</span>
			</form>
		</div>
		<div class="dataSheet">
			
		</div>
	</li>
</ul>
<script type="text/javascript" src="${ctxStatic}/js/calendar.js"></script>
<script type="text/javascript">
		$(function (){
			//配置日历表组件
			$("input.testTime").manhuaDate({					       
				Event : "click",//可选				       
				Left : 0,//弹出时间停靠的左边位置
				Top : -16,//弹出时间停靠的顶部边位置
				fuhao : "-",//日期连接符默认为-
				isTime : true,//是否开启时间值默认为false
				beginY : 1949,//年份的开始默认为1949
				endY :2100//年份的结束默认为2049
			});
			
			//默认加载数据分析统计图
			getDataSheet('.dataSheet','option1','观看微课时长数据统计','bar',1);
		});
		
		//加载数据类型默认为1，微课时长
		var dataType = "1";
		
		//选定日期之后加载
		$('#searchStartTime').blur(function(){
			if(dataType=="1"){
				setTimeout(function(){
					getDataSheet('.dataSheet','option1','观看微课时长数据统计','bar',1)
				},500);
			}else if(dataType=="2"){
				setTimeout(function(){
					getDataSheet('.dataSheet','option2','组卷测题数据统计','bar',2)
				},500);
			}else if(dataType=="3"){
				setTimeout(function(){
					getDataSheet('.dataSheet','option3','在线提问数据统计','bar',3)
				},500);
			}
		});
		
		
		//数据图切换
		function getDataSheet(obj,opt,tit,sheetType,dataType){
			//微课时长
			var dataSheet= echarts.init($(obj).get(0));
			var startTime = $("#searchStartTime").val();
			
			if(startTime==""){
				startTime = "${today}";
			}
			var opt={
				 title: {
	                text: tit
	            },
	            tooltip: {},
	            legend: {
	                data:[tit]
	            },
	            xAxis: {
	            	axisLabel:{
	            		interval:0,//横轴信息全部显示
	            		rotate:-30//60度角倾斜显示 
	            	},
	                data: ['2017/4/1','2017/4/2','2017/4/3','2017/4/4','2017/4/5','2017/4/6','2017/4/7']
	            },
	            yAxis: {},
	            series: [{
	                name: tit,
	                type: sheetType,
	                barWidth:'30',
	                data: [12,13,111,23,42,31,22]
	            }]
			};
			
			$.ajax({
				type:"post",
				url:"${contextPath}/ownCenter/dataAnalysisDetail",
				data:{
					dataType : dataType,
					startTime : startTime,
					userId : "${userId}"
				},
				dataType:'json',
				success:function(data){
					opt.series[0].data=[];
					opt.xAxis.data=[];
					for(var j=0; j<7; j++){
						opt.series[0].data.push(data[j]);
					}
					for(var i=7; i<14; i++){
						opt.xAxis.data.push(data[i]);
					}
					dataSheet.setOption(opt);
				},
				error:function(){
					alert('错误！');
				}
			});
		};
		
		//点击按钮,刷新数据图
		$('.dataNav').on('click','li',function(){
			$('.dataNav li').removeClass('on');
			$(this).addClass('on');
			switch($(this).index()){
				case 0:
					dataType = "1";
					getDataSheet('.dataSheet','option1','观看微课时长数据统计','bar',1);
					break;
				case 1:
					dataType = "2";
					getDataSheet('.dataSheet','option2','组卷测题数据统计','bar',2);
					break;
				case 2:
					dataType = "3";
					getDataSheet('.dataSheet','option3','在线提问数据统计','bar',3);
					break;
			};
		});
</script>