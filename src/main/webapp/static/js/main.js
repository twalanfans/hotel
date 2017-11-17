/*
 * @Description：酒店后台主框架JS效果
 * @Date：2017-5-12
 * @Author：赵一鸣
 * */

(function(){
	var mainFrame = {
		//mark标识符：作为左侧菜单伸缩的依据
		mark : false,

		//左侧列表dom
		$wrapLeft : $('.wrapLeft').eq(0),

		//头部dom
		$header : $('.header').eq(0),

		//右侧列表dom
		$wrapRight : $('.wrapRight').eq(0),

		//右上角下拉列表dom
		$menuList : $('.menuList').eq(0),

		//文档dom
		doc : document.body || document.documentElement,

		//自动缩放定时器
		resizeTimer : null,
		
		//初始化执行
		init : function(){
			window.onresize = this.autoWidth;
			this.autoWidth();
			this.menuList();
			$('.navBar').click(function(){
				mainFrame.autoFlex();
			});
			$('.wrapLeft').click(function(){
				if(this.doc.clientWidth <= 768){
					this.autoFlex();
				}
			}.bind(mainFrame));
		},
		
		//自动根据分辨率调整mark值
		autoWidth : function(){
			if(this.resizeTimer){
				clearTimeout(this.resizeTimer);
			}
			this.resizeTimer = setTimeout(function(){
				mainFrame.mark = mainFrame.doc.clientWidth > 1000 ? true : false;
			}, 300);
			
		},
		
		//左侧菜单伸缩
		autoFlex : function(){
			var wrapLeft = this.$wrapLeft;
			var header = this.$header;
			var wrapRight = this.$wrapRight;
			if(this.mark){
				wrapLeft.animate({left:'-230px'}, 'slow');
				header.animate({left:'0'}, 'slow');
				wrapRight.animate({marginLeft:'0px'}, 'slow');
			}else{
				wrapLeft.animate({left:'0'}, 'slow');
				header.animate({left:'230px'}, 'slow');
				wrapRight.animate({marginLeft:'230px'}, 'slow');
			}
			this.mark = !this.mark;
		},
		
		//右上角个人信息显示或隐藏
		menuList : function(){
			var $menuList = $('.menuList').eq(0);
			$('.menu').hover(function(){
				$menuList.stop(true).slideDown();
			}, function(){
				$menuList.stop(true).slideUp();
			});
		}
	};
	
	mainFrame.init();
})();