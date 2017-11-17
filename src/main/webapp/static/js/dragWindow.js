/*
 * @Description：弹出框拖动
 * @Date：2017-5-10
 * @Author：赵一鸣
 * */

(function(){
	var dragWindow = {
		//拖动对象
		dragObj : '',
		
		//document对象
		doc : document,
		
		//初始化执行
		init : function(dragObj){
			this.dragObj = dragObj;
			this.dragFunc();
		},
		
		//拖动方法
		dragFunc : function(){
			this.dragObj.onmousedown = function(ev){
				//事件对象
				var ev = ev || window.event;
				
				//鼠标指针按下时与窗口左侧之间的距离
				var l = ev.clientX - this.offsetLeft;
				var t = ev.clientY - this.offsetTop;
				
				//窗口移动过程中
				dragWindow.doc.onmousemove = function(ev){
					var ev = ev || window.event;
					//移动过程中，窗口的坐标
					this.dragObj.style.left = ( ev.clientX - l ) + 'px';
					this.dragObj.style.top = ( ev.clientY - t ) + 'px';
				}.bind(dragWindow);
				
				//鼠标抬起
				dragWindow.doc.onmouseup = function(){
					this.onmousemove = null;
					this.onmouseup = null;
				};
			};
		}
	};
	
	window.dragWindow = dragWindow;
})();