/*
 * @Description：找回密码第一步：验证邮箱和手机号
 * @Date：2017-5-30
 * @Author：赵一鸣
 * */
(function(){
	var findPwd = {
		//手机号
		loginName : document.getElementById('loginName'),
		
		//邮箱
		email : document.getElementById('email'),
		
		//验证码
		validateCode : document.getElementById('validateCode'),
		
		//发送验证码按钮
		codeBtn : document.getElementById('codeBtn'),
		
		//接收到的验证码
		sessionCode : '',
		
		//获取验证码url
		getCodeUrl : '',
		
		//检测手机号和邮箱是否属于同一人url
		checkPhoneAndEmail : '',
		
		//重置密码url
		resetPwdUrl : '',
		
		//倒计时
		countTimer : '',
		
		//邮箱号正则
		regEmail : null,
		
		//手机号正则
		regLoginName : null,
		
		//初始化执行
		init : function(findPwdOptions){
			this.regLoginName = findPwdOptions.regLoginName;
			this.regEmail = findPwdOptions.regEmail;
			this.getCodeUrl = findPwdOptions.getCodeUrl;
			this.checkPhoneAndEmail = findPwdOptions.checkPhoneAndEmail;
			this.resetPwdUrl = findPwdOptions.resetPwdUrl;
			this.toFindPassword();
			this.getCode();
		},
		
		//验证手机号和邮箱号格式
		validateFormat : function(){
			var This = findPwd;
			if(!(findPwd.regLoginName).test(This.loginName.value)){
				return false;
			}
			if(!(findPwd.regEmail).test(This.email.value)){
				return false;
			}
			return true;
		},
		
		//开始找回密码
		toFindPassword : function(){
			var findPwdBtn = document.getElementById('findPwdBtn');
			findPwdBtn.onclick = function(){
				if(!findPwd.validateFormat()){
					alert('手机号码或邮箱号码格式有误，请重新填写！');
					return false;
				}
				if(findPwd.sessionCode != findPwd.validateCode.value){
					alert('验证码填写错误，请稍后重试！');
					return false;
				}
				window.localStorage.loginName = findPwd.loginName.value;
				//跳转到重置密码页面
				window.location = findPwd.resetPwdUrl + findPwd.loginName.value;
			}
		},
		
		//获取验证码
		getCode : function(){
			findPwd.validateCode.onkeyup = function(){
				this.value = this.value.replace(/\D/g, '');
			}
			findPwd.codeBtn.onclick = function(){
				if(!(findPwd.regLoginName).test(findPwd.loginName.value)){
					alert('请先输入您的手机号码！');
					return false;
				}
				if(!(findPwd.regEmail).test(findPwd.email.value)){
					alert('请先输入您的邮箱号码！');
					return false;
				}
				//首先确认手机号和邮箱是否属于同一个人
				$.ajax({
					url : findPwd.checkPhoneAndEmail,
					type : 'post',
					data:'loginName=' + findPwd.loginName.value+'&email=' + findPwd.email.value,
					dataType : 'json',
					success : function(data){
						switch(data){
							case 'no':
								alert('该邮箱不是此账号的联系邮箱！');
								break;
							case 'noExist':
								alert('不存在此用户！');
								break;
							case 'success':
								findPwd.codeBtn.disabled = true;
								//然后从后端获取验证码
								$.ajax({
									url : findPwd.getCodeUrl,
									type : 'post',
									data : 'email='+findPwd.email.value,
									dataType : 'json',
									success : function(data){
										if(data != "error"){
											findPwd.sessionCode = data;
											findPwd.countDown();
										}else{
											alert("验证码发送失败！")
										}
									}
								});
								break;
							default:
								alert('系统错误，请重新获取验证码！');
						}
					}
				});
			};
		},
		
		//倒计时
		countDown : function(){
			var time = 60;
			findPwd.countTimer = setInterval(startCountDown, 1000);
			startCountDown();
			function startCountDown(){
				time -= 1;
				findPwd.codeBtn.value = time + '秒后重新获取';
				if(time == 0){
					findPwd.clearTimer();
				}
			}
		},
		
		//清除定时器
		clearTimer : function(){
			clearInterval(findPwd.countTimer);
			findPwd.codeBtn.disabled = false;
			findPwd.codeBtn.value = '获取验证码';
		},
	};
	
	window.findPwd = findPwd;
})();