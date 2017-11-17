<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/calendar.css">
<style type="text/css">
	.mainBox{background-color:#fff;overflow:hidden;padding:0 10px 10px 10px;}
	.basicInfo p{margin-bottom:10px;}
	.required{margin-bottom:10px;}
	.navListContents,.navLists{margin-bottom:10px;}
	.navListContents ul li{float:left;padding:5px 10px;cursor:pointer;}
	.editInfoPrompt,.modificatePrompts{height: 32px;line-height: 32px;border: 1px solid #ddd;color: #555;background-color: #F9F9F9;padding: 0px 12px;margin: 10px 0px;}
	.startmodificat p{height:30px;margin-bottom:10px;}
	.summary,.summary p{margin-bottom:10px;}
	@media only screen and (min-width:1000px) {
		.inpt{width:85%;}
	}
</style>
<!-- 栏目说明 -->
	<div class="navExplain">
		<span>个人信息修改</span>
	</div>
<!--mainBox-->
<div class="mainBox clearfix ml10">
	<div class="navLists clearfix">
		<div class="navListContents">
			<ul>
				<li class=" active" onclick="editInfo();"><b>信息修改</b></li>
				<li onclick="changePassword();"><b>密码修改</b></li>
			</ul>
		</div>
	</div>
	<div class="mainContents">
		<div class="mainWrap">
			<div class="editInfos">
				<div class="editInfoPrompt">请完善以下信息，方便我们更好的为您服务!</div>
				<div class="basicInfo">
					<div class="realName required">
						<span>真实姓名：</span>
						<span><input type="text"  class="editInpt inpt"  id="userName"  value="${userDetail.userName}" maxlength="5" /></span>
					</div>
					<div class="realName required">
					    <span>更改头像：</span>
					    <span><input type="file"  class="editInpt inpt"  id="headPic" name="headPic"></span>
					</div>
					<div class="birthday required">
						<span>出生日期：</span>
						<span>
							<input type="text" name="birthday"  class="editInpt birthday inpt" id="birthday" value="${userDetail.birthday}" readonly/>
						</span>
					</div>
				<c:if test="${userDetail.departName!='' && userDetail.departName!=null}">
					<div class="major required">
						<span>所在院系：</span>
						<span>${userDetail.departName}</span>
					</div>
				</c:if>
				<c:if test="${userDetail.professionName!='' && userDetail.professionName!=null}">
					<div class="major required">
						<span>所在专业：</span>
						<span>${userDetail.professionName}</span>
					</div>
				</c:if>
				<c:if test="${userDetail.gradeName!='' && userDetail.gradeName!=null}">
					<div class="whereClass required">
						<span>所在班级：</span>
						<span>${userDetail.gradeName}</span>
					</div>
				</c:if>
				</div>
				<div class="contactInfo">
					<div class="mobileNumber required">
						<span>手机号码：</span>
						<span>
							<input type="number" class="editInpt inpt"  id="phone"  value="${userDetail.phone}">
						</span>
						<span class="checkPhone"></span>
					</div>
					<div class="emailNumber required">
						<span>绑定邮箱：</span>
						<span>
							<input type="text" class="editInpt inpt"  id="email"  value="${userDetail.email}">
						</span>
					</div>
				</div>
				<div class="personalStatement">
					<p>个人说明</p>
					<textarea maxlength="125" class="inpt" style="width:250px;height:150px;resize:none;">${userDetail.remark}</textarea>
					<span class="errortext"></span>
				</div>
				<div class="commonBtn" onclick="saveUserDetail()">保存</div>
			</div>
		</div>
	</div>
	<!-- 修改密码 -->
	<div class="passwordModifications" style="display:none;">
	<div class="modificatePrompts">请保存好自己的登录密码，以便下次使用！</div>
	<div class="personBasicInfos clearfix">
		<div class="avatar">
				<img src="${filePath}/${userDetail.photo}" width="80" height="80">
		</div>
		<div class="summary">
			<p>
				<span>用户名：</span>
				<span>${userDetail.userName}</span>
			</p>
			<p>
				<span>登录账号：</span>
				<span class="loginName">${userDetail.loginName}</span>
			</p>
		</div>
	</div>
	<!--开始修改密码-->
	<div class="startmodificat">
		<p>
			<span>&nbsp;&nbsp;&nbsp;原密码：</span>
			<input type="password" id="oldPwd" class="inpt" />
		</p>
		<p>
			<span>&nbsp;&nbsp;&nbsp;新密码：</span>
			<input type="password" id="newPwd" class="inpt" />
		</p>
		<p>
			<span>确认密码：</span>
			<input type="password" id="checkPwd" class="inpt" />
			<span class="errortext"></span>
		</p>
	</div>
	<!--保存密码-->
	<div class="savePwd commonBtn"  onclick="saveNewPassword()">保存</div>
</div>
</div>
<!-- 用户操作提示框 -->
<div class="promptBox">
	<div class="promptTxt">
		<p>提示</p>
		<p>
			<i class="fa fa-exclamation-circle fl" style="margin:20px 5px 0 20px;"></i>
			<span></span>
		</p>
	</div>
</div>
<script type="text/javascript" src="${ctxStatic}/js/ajaxfileupload.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/calendar.js"></script>
<script type="text/javascript">
	//配置日历表组件
	$(function (){
		$("input.birthday").manhuaDate({					       
			Event : "click",//可选				       
			Left : 0,//弹出时间停靠的左边位置
			Top : -16,//弹出时间停靠的顶部边位置
			fuhao : "-",//日期连接符默认为-
			isTime : true,//是否开启时间值默认为false
			beginY : 1949,//年份的开始默认为1949
			endY :2100//年份的结束默认为2049
		});
	});
	
	//个人信息和密码切换
	function editInfo(){
		$('.mainContents').show();
		$('.passwordModifications').hide();
	}
	function changePassword(){
		$('.passwordModifications').show();
		$('.mainContents').hide();
	}
	
	//个人说明默认值
	if($('.personalStatement textarea').val()==null || $('.personalStatement textarea').val()==""){
		$('.personalStatement textarea').val('此人很懒，未留下任何信息！');
	}
	
	//切换分类，颜色变化
	$(".navListContents ul li").click(function(){
		$(this).addClass("active").siblings("li").removeClass("active");
	})
	
	//校验手机格式
	var isRightPhone="1";
	$("#phone").on('keyup',function(){
		 $(this).attr("phone",$(this).val()); //将当前值存入自定义属性
	}).blur(function(){
		 var phone=$(this).attr("phone")||$(this).val();
		 var $checkPhone = $('.checkPhone');
		 if(!(/^((13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8})$/.test(phone))){ 
			 alert('手机格式有误，请重新输入！');
			 isRightPhone='0';
		 }else{
			 isRightPhone='1';
		 }
	})
	
	//个人说明输入字数限制
	$('.personalStatement textarea').keyup(checkTextNum);
	function checkTextNum(){
		var $this = $(this);
		var $thisVal=$this.val();
		var $errorText = $this.siblings('.errortext');
		if($thisVal.length<=125){
			$errorText.html('你还能输入'+(125-$thisVal.length)+'个字！').css('color','#f00');
		}else{
			alert('您好，个人说明最多能输入125个字');
		}
	};
	
	//保存修改的个人信息
	function saveUserDetail(){
		var $prompt = $('.promptTxt p:eq(1) span'),
			$promptBox = $('.promptBox');
		 var userName = document.getElementById('userName').value;
		 if(userName==''){ 
	        alert('真实姓名不能为空！');
	        return; 
		  }
		 if( /^[\"\{\}\[\]\(\)\\<\>%]+$/i.test(userName) ){
			 alert("真实姓名中含有特殊字符，请重填！"); 
			 return;
		 }
		 var birthday = document.getElementById('birthday').value;
		 if(new Date(birthday).getTime() >= new Date().getTime()){
			 alert('亲，您的生日必须是小于今天日期的哦！');
			 return;
		 }
		 var phone = document.getElementById('phone').value;
		 if(isRightPhone!='1'){
			 alert('手机格式有误，请重新输入');
			 return;
		 }
		 var email = document.getElementById('email').value;
		 if(!(/\w+@\w+(\.[a-zA-z]+)(\w+[a-zA-Z]+)?/.test(email))){
			 	alert("邮箱号输入有误，请重新填写！");
			 	return;
		 }
		 var remark = $('.personalStatement textarea').val();
		 $.ajaxFileUpload({
			type:"POST",
			url: '${contextPath}/ownCenter/updateUserDetail',
			secureuri: false,
			fileElementId: "headPic",
			data: {
				  "userName":userName,
				  "birthday":birthday,
				  "phone":phone,
				  "email":email,
				  "remark":remark,
				  "loginName": $(".loginName").html()
			},
			dataType:'json',
			success:function(data){
				if(data.status=="success"){
					$prompt.html('操作成功！');
					$promptBox.fadeIn();
					setTimeout(function(){
						window.location.reload();
					},1000);
				}else{
					$prompt.html('操作失败！');
					$promptBox.fadeOut();
				}
			}
		}) 
	}
	
	//上传头像验证
	$("#headPic").change(function(){
	 	var $val = $(this).val();
	 	if(!$val.match(/.jpg|.gif|.jpeg|.png|.bmp/i)){
	 		alert("图片格式必须是jpg、png、gif、bmp中的一种");
	 		$(this).val("");
	 	}
	})
	
	//验证密码
	var isCheckPassword;
	var $this;
	$("#checkPwd").on('keyup',function(){
		$this = $(this);
		$this.attr("data-oval",$this.val()); //将当前值存入自定义属性
	}).blur(function(){
		var $errortext = $('.startmodificat').children().children('.errortext');
	    var oldVal=($this.attr("data-oval")); //获取原值
	    var newVal1=$('#newPwd').val(); //获取当前值
	    if (oldVal!=newVal1){
	    	$errortext.html('您输入的两次密码不一致，请重新输入！').css('color','red');
			isCheckPassword ="no";
	    }else{
	    	$errortext.html('');
	    	isCheckPassword = "yes";
	    }    
	});
	
	//保存修改密码	
	function saveNewPassword(){
		var $prompt = $('.promptTxt p:eq(1) span'),
			$promptBox = $('.promptBox');
		if(isCheckPassword=="no"){
			alert("您输入的两次密码不一致，不能修改密码！");
			return;
		}
		var newPwd = document.getElementById("newPwd").value;
		var oldPwd = document.getElementById("oldPwd").value;
		if(!(/(?=.*\d+)(?=.*[a-z])(?=.*[A-Z])(?=.*_).{8,}/).test(newPwd)){
			alert('密码必须大于8位数，同时包含数字、大小写字母和下划线！');
			return;
		}
 		$.ajax({
			type:"POST",
			url: '${contextPath}/ownCenter/changePassword',
			data: "oldPwd="+oldPwd+"&newPwd="+newPwd,
			dataType:'json',
			cache: false,
			success:function(data){
				switch(data){
					case 'success':
						if(confirm('密码修改成功，即将退出用新密码登录！')){
							window.location.href = "${contextPath}/logout";
						}
						break;
					case 'fail':
						$prompt.html('密码修改失败，原密码验证不通过！');
						$promptBox.fadeIn();
						setTimeout(function(){
							$promptBox.fadeOut();
						},1500);
						break;
					case 'error':
						$prompt.html('修改失败，请重新操作！');
						$promptBox.fadeIn();
						setTimeout(function(){
							$promptBox.fadeOut();
						},1500);
						break;
				}
			}
		}) 
	}
	
	//关闭弹窗
	$('#cancelBtn').click(function(){
		$('.promptBox').fadeOut();
	});
</script>