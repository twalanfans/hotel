/*
 * @Description：弹出框页面禁止操作系列方法
 * @Date：2017-5-11
 * @Author：赵一鸣
 * */

(function(){
	var sonWindow = {
		//文档对象
		doc : document,
		//关闭按钮dom
		close : document.getElementById('close'),
		
		//初始化执行
		init : function(){
			var resolution = document.body.clientWidth || document.documentElement.clientWidth;
			this.close.innerHTML = resolution >= 769 ? '关闭' : '返回';
			this.windowClose();
			this.windowCloseBefore();
			this.noRightKey();
			this.noRenovate();
		},
		
		//关闭窗口
		windowClose : function(){
			this.close.onclick = function(){
				window.close();
			}
		},
		
		//监听页面关闭
		windowCloseBefore : function(){
			window.onbeforeunload = function(){
				window.opener.onload(true);
			}
		},
		
		//禁止右键点击
		noRightKey : function(){
			this.doc.oncontextmenu = function(){
				return false;
			};
		},
		
		//禁止f5刷新
		noRenovate : function(){
			this.doc.onkeydown = function(event){
				if(event.keyCode==116){          
			         event.keyCode = 0;          
			         event.cancelBubble = true;          
			       	 return false;          
			    }          
			}
		}
	};
	
	sonWindow.init();
})();