/*
 * @Description：视频播放窗口
 * @Date：2017-6-2
 * @Author：赵一鸣
 * */

(function(){
	var videoPlay = {
		//视频播放弹出窗
		videoWindow : null,
		
		//视频播放器
		videoPlay : null,
		
		//视频播放窗口头部信息
		videoName : null,
		
		//关闭窗口的按钮
		closeBtn : null,
		
		//点击播放
		queryOnline : null,
		
		//初始化执行
		init : function(videoPlayOptions){
			this.videoWindow = videoPlayOptions.videoWindow;
			this.videoPlay = videoPlayOptions.videoPlay;
			this.videoName = videoPlayOptions.videoName;
			this.closeBtn = videoPlayOptions.closeBtn;
			this.queryOnline = videoPlayOptions.queryOnline;
			this.startPlay();
			this.closeWindow();
		},
		
		//开始播放
		startPlay : function(){
			var queryOnlineLen = this.queryOnline.length;
			for(var i=0; i<queryOnlineLen; i++){
				this.queryOnline[i].index = i;
				this.queryOnline[i].onclick = function(){
					var filePath = this.getAttribute('filePath');
					var fileName = this.getAttribute('fileName');
					videoPlay.videoWindow.style.display = 'block';
					videoPlay.videoPlay.setAttribute('src', filePath);
					videoPlay.videoName.innerText = fileName;
				}
			}
		},
		
		//关闭播放窗口并停止播放视频
		closeWindow : function(){
			this.closeBtn.onclick = function(){
				videoPlay.videoPlay.pause();
				videoPlay.videoWindow.style.display = 'none';
			}
		}
	};
	
	window.videoPlay = videoPlay;
})();