/*
 * @Description：用户注册
 * @Date：2017-5-29
 * @Author：赵一鸣
 * */

(function(){
	var hotelRegist = {
		//用户名
		userName : document.getElementById('userName'),
		userNameVal : '',
		
		//手机号
		loginName : document.getElementById('loginName'),
		loginNameVal : '',
		
		//密码
		password : document.getElementById('password'),
		passwordVal : '',
		
		//确认密码
		checkPwd : document.getElementById('checkPwd'),
		checkPwdVal : '',
		
		//性别
		sex : document.getElementById('sex'),
		sexVal : '',
		
		//注册身份
		userType : document.getElementById('userType'),
		userTypeVal : '',
		
		//部门或班级列表
		departOption : document.getElementsByClassName('departOption'),
		
		//选中的部门或班级ID
		departId : '',
		
		//邮箱
		email : document.getElementById('email'),
		emailVal : '',
		
		//验证码
		validateCode : document.getElementById('validateCode'),
		validateCodeVal : '',
		codeBtn : document.getElementById('codeBtn'),
		countTimer : '',
		
		//遮罩层
		clear : document.getElementById('clear'),
		
		//姓名正则
		regUserName : null,
		
		//手机号码正则
		regLoginName : null,
		
		//密码正则
		regPassword : null,
		
		//邮箱正则
		regEmail : null,
		
		//判断手机号是否已经被注册
		isUse : 'yes',
		
		//检查手机号是否被注册url
		checkPhone : '',
		
		//邮箱验证码url
		emailValidate : '',
		
		//短信验证码url
		SmsValidate : '',
		
		//提交注册信息url
		registerUser : '',
		
		//注册成功跳转到登录页面url
		toLogin : '',
		
		//初始化执行
		init : function(registOptions){
			this.regUserName = registOptions.regUserName;
			this.regLoginName = registOptions.regLoginName;
			this.regPassword = registOptions.regPassword;
			this.regEmail = registOptions.regEmail;
			this.checkPhone = registOptions.checkPhone;
			this.emailValidate = registOptions.emailValidate;
			this.SmsValidate = registOptions.SmsValidate;
			this.registerUser = registOptions.registerUser;
			this.toLogin = registOptions.toLogin;
			this.chooseUserType();
			this.chooseDepart();
			this.blurValidate();
			this.getCode();
			this.toRegist();
		},
		
		//选择身份
		chooseUserType : function(){
			var departOption = document.getElementsByClassName('departOption');
			this.userType.onchange = function(){
				$('#chooseDepart').val('');
				if(this.value != '0'){
					hotelRegist.userTypeVal = this.value;
					hotelRegist.departOption[0].className = 'departOption hide';
					hotelRegist.departOption[1].className = 'departOption hide';
				}else{
					hotelRegist.userTypeVal = '';
					hotelRegist.departId = '';
					alert('请选择注册身份！');
				}
			}
		},
		
		//选择部门或班级
		chooseDepart : function(){
			var chooseDepart = document.getElementById('chooseDepart');
			var clear = document.getElementById('clear');
			//展示教师或学生对应的下拉菜单
			chooseDepart.onclick = function(){
				switch(this.userTypeVal){
					case '1':
						this.departOption[0].className = 'departOption show';
						this.departOption[1].className = 'departOption hide';
						this.clear.className = 'show';
						break;
					case '2':
						this.departOption[0].className = 'departOption hide';
						this.departOption[1].className = 'departOption show';
						this.clear.className = 'show';
						break;
					default:
						alert('请先选择注册身份！');
						this.departOption[0].className = 'departOption hide';
						this.departOption[1].className = 'departOption hide';
				}
				
			}.bind(hotelRegist);
			
			//下拉菜单伸缩
			$(".xi").click(function(){
				$(this).siblings('.xi').find('.zys').stop(true).slideUp('slow');
				$(this).find(".zys").stop(true).slideDown('slow');	
			});
			$(".zy").click(function(){
				$(this).siblings('.zy').find('.bj').stop(true).slideUp('slow');
				$(this).find('.bj').stop(true).slideDown("slow");
			});
			$(".bj input").click(function(){
				$("#chooseDepart").val($(this).parents(".zys").siblings("span").html()+"-"+$(this).parents(".bj").siblings("span").html()+"-"+$(this).siblings("span").html());
				$(".departOption,#clear").addClass('hide').removeClass('show');
			});
			$(".xiInpt").change(function(){
				$("#chooseDepart").val($(this).siblings("span").html());
				$(".departOption,#clear").addClass('hide').removeClass('show');
			})
			$(".zyInpt").change(function(){
				$("#chooseDepart").val($(this).parents(".zys").siblings("span").html()+"-"+$(this).siblings("span").html());
				$(".departOption,#clear").addClass('hide').removeClass('show');
			})
		},
		
		//鼠标失去焦点做格式验证
		blurValidate : function(){
			var This = hotelRegist;
			This.userName.onblur = function(){
				This.userNameVal = this.value;
				if(!(This.regUserName).test(this.value)){
					alert('姓名最多5个汉字！');
					this.blur();
				}
			}
			This.loginName.onblur = function(){
				var loginNamePrompt = document.getElementById('loginNamePrompt');
				if(!(This.regLoginName).test(this.value)){
					alert('手机号码格式不正确，请重新输入！');
					this.blur();
					return false;
				}
				This.loginNameVal = this.value;
				$.ajax({
					type : 'POST',
					url : hotelRegist.checkPhone,
					data : 'loginName='+This.loginNameVal,
					dataType : 'json',
					cache: false,
					success:function(data){
						if(data == 'noCan'){
							loginNamePrompt.innerText = '该手机号已被注册，请重新输入！';
							loginNamePrompt.className = 'loginNamePrompt show red';
							hotelRegist.isUse = "no";
						}else{
							loginNamePrompt.innerText = '该手机号可以使用！';
							loginNamePrompt.className = 'loginNamePrompt show green';
							hotelRegist.isUse = "yes";
						}
					}
				 });
			}
			This.password.onblur = function(){
				This.passwordVal = this.value;
				if(!(This.regPassword).test(this.value)){
					alert('密码必须大于8位数,且同时包含数字+大小写字母+下划线！');
					this.blur();
				}
			}
			This.checkPwd.onblur = function(){
				This.checkPwdVal = this.value;
				if(!(This.regPassword).test(this.value) || this.value !== This.passwordVal){
					alert('两次输入的密码不一致，请重新输入！');
					this.blur();
				}
			}
			This.sex.onchange = function(){
				hotelRegist.sexVal = this.value;
				if(this.value == '2'){
					alert('请选择性别！');
					this.blur();
				}
			}
			This.email.onblur = function(){
				This.emailVal = this.value;
				if(!(This.regEmail).test(this.value)){
					alert('邮箱格式不正确，请重新输入！');
					this.blur();
				}
			}
		},
		
		//获取验证码
		/*getCode : function(){
			hotelRegist.validateCode.onkeyup = function(){
				this.value = this.value.replace(/\D/g, '');
			}
			hotelRegist.codeBtn.onclick = function(){
				if(!(hotelRegist.regEmail).test(hotelRegist.emailVal)){
					alert('请先输入您的邮箱号码！');
					return false;
				}
				this.disabled = true;
				$.ajax({
					url : hotelRegist.emailValidate,
					type : 'post',
					data : 'email='+hotelRegist.email.value,
					dataType : 'json',
					success : function(data){
						if(data != "error"){
							hotelRegist.validateCodeVal = data;
							hotelRegist.countDown();
						}else{
							alert("验证码发送失败！")
						}
					}
				});
			}
		},*/
		//获取验证码
		getCode : function(){
			hotelRegist.validateCode.onkeyup = function(){
				this.value = this.value.replace(/\D/g, '');
			}
			hotelRegist.codeBtn.onclick = function(){
				if(!(hotelRegist.regEmail).test(hotelRegist.emailVal)){
					alert('请先输入您的邮箱号码！');
					return false;
				}
				this.disabled = true;
				$.ajax({
					url : hotelRegist.SmsValidate,
					type : 'post',
					data : 'loginName='+hotelRegist.loginName.value,
					dataType : 'json',
					success : function(data){
						if(data != "error"){
							hotelRegist.validateCodeVal = data;
							hotelRegist.countDown();
						}else{
							alert("验证码发送失败！")
						}
					}
				});
			}
		},
		
		//验证码倒计时
		countDown : function(){
			var time = 60;
			hotelRegist.countTimer = setInterval(startCountDown, 1000);
			startCountDown();
			function startCountDown(){
				time -= 1;
				hotelRegist.codeBtn.value = time + '秒后重新获取';
				if(time == 0){
					hotelRegist.clearTimer();
				}
			}
		},
		
		//清除定时器
		clearTimer : function(){
			clearInterval(hotelRegist.countTimer);
			hotelRegist.codeBtn.disabled = false;
			hotelRegist.codeBtn.value = '获取验证码';
		},
		
		//点击注册
		toRegist : function(){
			$('#registBtn').click(function(){
				if(!(hotelRegist.regUserName).test(hotelRegist.userNameVal)){
					alert('姓名格式不正确，请重新输入');
					return false;
				}
				if(!(hotelRegist.regLoginName).test(hotelRegist.loginNameVal)){
					alert('手机号码格式不正确，请重新输入！');
					return false;
				}
				if(hotelRegist.isUse == 'no'){
					alert('该手机号已经被注册，请使用其他手机号！');
					return false;
				}
				if(!(hotelRegist.regPassword).test(hotelRegist.passwordVal)){
					alert('密码必须大于8位数,且同时包含数字+大小写字母+下划线！');
					return false;
				}
				if(!(hotelRegist.regPassword) || hotelRegist.checkPwdVal != hotelRegist.passwordVal){
					alert('两次输入的密码不一致，请重新输入！');
					return false;
				}
				if(hotelRegist.sexVal == '' || hotelRegist.sexVal == '2'){
					alert('请选择性别！');
					return false;
				}
				if(hotelRegist.userTypeVal == '' || hotelRegist.userTypeVal =='0'){
					alert('请选择注册身份！');
					return false;
				}
				if(hotelRegist.userTypeVal == '1'){
					hotelRegist.departId = $(".selectSDept:checked").val();    
					
				}else if(hotelRegist.userTypeVal == '2'){
					hotelRegist.departId = $(".selectTDept:checked").val();    
				}
				switch(hotelRegist.userTypeVal){
					case '1':
						hotelRegist.departId = $(".selectSDept:checked").val();
						break;
					case '2':
						hotelRegist.departId = $(".selectTDept:checked").val();  
						break;
					default:
						return;
				}
				if(hotelRegist.departId == '' || hotelRegist.departId == undefined){
					alert("请选择您所在部门或班级！");
					return false;
				}
				if(!(hotelRegist.regEmail).test(hotelRegist.emailVal)){
					alert('邮箱格式不正确，请重新输入！');
					return false;
				}
				if(hotelRegist.validateCode.value == '' || hotelRegist.validateCodeVal != hotelRegist.validateCode.value){
					alert('验证码不正确，请重新输入！');
					return false;
				}
				$.ajax({
					type : "POST",
					url : hotelRegist.registerUser,
					data : "userName="+hotelRegist.userNameVal+"&loginName="+hotelRegist.loginNameVal
							+"&email="+hotelRegist.emailVal+"&password="+hotelRegist.passwordVal+"&departId="+hotelRegist.departId+"&userType="+hotelRegist.userTypeVal
							+"&sex="+hotelRegist.sexVal,
					dataType : 'json',
					cache : false,
					success:function(data){
						if(data == "success"){
							alert("注册成功！");
							window.location.href = hotelRegist.toLogin; 
						}else{
							alert("注册失败！");
							window.location.reload();
						}
					 }
				});
			});
		}
	};
	
	window.hotelRegist = hotelRegist;
})();