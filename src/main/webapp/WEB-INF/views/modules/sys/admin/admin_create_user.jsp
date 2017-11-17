<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/admin/admin_create_user.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/calendar.css" />
<style type="text/css">
	.inpt{width:85%;}
</style>
<!-- 栏目说明 -->
<div class="navExplain">
	<span>添加用户</span>
</div>
<div class="editInfoPrompt">填写注册用户信息，方便我们更好的为您服务！</div>
	<!--基本信息-->
	<div class="basicInfos">
		<div class="major required">
			<label><i>*</i>手机号码：</label>
			<input type="text" name="loginName" id="loginName" class="inpt" />
		</div>
		<div class="realName required">
			<label><i>*</i>真实姓名：</label>
			<input type="text"  id="userName" class="inpt" name="userName" maxlength="5" />
		</div>
		<div class="major required">
			<label><i>*</i>选择性别：</label>
			<label class="sexText">男</label>
			<input type="radio"  name="sex"  value="1"  class="selectSex">
			<label class="sexText">女</label>
			<input type="radio"  name="sex"  value="0"  class="selectSex">
		</div>
		<div class="major required">
			<label><i>*</i>注册身份：</label>
			<select id="userType" class="inpt">
				<option value="">—请选择—</option>
				<option value="1">学生</option>
				<option value="2">老师</option>
			</select>
		</div>
		<div class="teacherDepart" style="display:none">
			<div class="choose options">
				<label><i>*</i>选择部门：</label>
				<input type="text" readonly class="editInpt inpt startChoose" />
				<ul class="list">
					<c:forEach items="${departList}"  var="depart1"  varStatus="idxStatus">
					<c:if test="${depart1.parentId eq '1' }">
					<li class="xi">
						<input type="radio" name="b"  class="selectSDept xiInpt" value="${depart1.departId}">
						<span>${depart1.departName}</span>
						<ul class="zys">
							<c:forEach items="${departList}"  var="depart2"  varStatus="idxStatus">
							<c:if test="${depart2.parentId == depart1.departId }">
							<li class="zy">
								<input type="radio" name="b"  class="selectSDept zyInpt" value="${depart2.departId}" />
								<span>${depart2.departName}</span>
								<ul class="bj">
								<c:forEach items="${departList}"  var="depart3"  varStatus="idxStatus">
								<c:if test="${depart3.parentId == depart2.departId }">
									<li class="thirdFloor">
										<input type="radio" name="b"  class="selectSDept bjInpt" value="${depart3.departId}">
										<span>${depart3.departName}</span>
									</li>
								</c:if>
								</c:forEach>
								</ul>
							</li>
							</c:if>
							</c:forEach>
						</ul>
					</li>
					</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="studentDepart" style="display:none">
			<div class="choose options">
				<label><i>*</i>选择班级：</label>
				<input type="text" readonly class="editInpt inpt startChoose"/>
				<ul class="list">
					<c:forEach items="${departList}"  var="depart1"  varStatus="idxStatus">
					<c:if test="${depart1.parentId eq '1' }">
					<li class="xi">
						<input type="hidden" class="xiInpt"  />
						<span>${depart1.departName}</span>
						<ul class="zys">
							<li class="zy">
							<c:forEach items="${departList}"  var="depart2"  varStatus="idxStatus">
							<c:if test="${depart2.parentId == depart1.departId }">
								<input type="hidden" class="zyInpt"  />
								<span>${depart2.departName}</span>
								<ul class="bj">
								<c:forEach items="${departList}"  var="depart3"  varStatus="idxStatus">
								<c:if test="${depart3.parentId == depart2.departId }">
									<li>
										<input type="radio" name="b"  class="selectSDept bjInpt" value="${depart3.departId}">
										<span>${depart3.departName}</span>
									</li>
								</c:if>
								</c:forEach>
								</ul>
							</c:if>
							</c:forEach>
							</li>
						</ul>
					</li>
					</c:if>
					</c:forEach>
				</ul>
			</div>
		</div>
		<div class="birthday required">
			<label><i>*</i>出生日期：</label>
			<input type="text" name="birthday" class="inpt" id="birthday"  readonly />
		</div>
		<div class="whereClass required">
			<label><i>*</i>邮政编码：</label><input type="text" name="postCode" class="inpt" id="postCode" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength=6 />
		</div>
		<div class="whereClass required">
			<label><i>*</i>家庭住址：</label><input type="text" name="address" class="inpt" id="address" maxlength=125 />
		</div>
		<div class="whereClass required">
			<label><i>*</i>邮箱地址：</label><input type="text" name="email" class="inpt" id="email" />
		</div>
		<div class="whereClass required">
			<label><i>*</i>登录密码：</label><input type="password" class="inpt" id="newPwd" placeholder="至少8位(数字+大小写字母+下划线)" />
		</div>
		<div class="whereClass required">
			<label><i>*</i>确认密码：</label><input type="password" class="inpt" id="checkPwd" />
		</div>
		<a href="javascript:;" id="saveUserDetail" class="commonBtn">保存</a>
	</div>
</div>
<!-- 日历插件 -->
<script type="text/javascript" src="${ctxStatic}/js/calendar.js"></script>
<script type="text/javascript">
	(function(){
		var isEmailRight,isCheckPassword;
		//初始化日历插件
		$("#birthday").manhuaDate({					       
			Event : "click",//可选				       
			Left : 0,//弹出时间停靠的左边位置
			Top : -16,//弹出时间停靠的顶部边位置
			fuhao : "-",//日期连接符默认为-
			isTime : true,//是否开启时间值默认为false
			beginY : 1949,//年份的开始默认为1949
			endY :2100//年份的结束默认为2049
		});
		
		//选择所添加的用户的角色是学生或老师
		$('#userType').change(function(){
			var chooseType = $(this).val(),
				$studentDepart = $('.studentDepart'),
				$teacherDepart = $('.teacherDepart');
			if(chooseType=='1'){
				$studentDepart.show();
				$teacherDepart.hide();
			}else if(chooseType=='2'){
				$studentDepart.hide();
				$teacherDepart.show();
			}else{
				$studentDepart.hide();
				$teacherDepart.hide();
			}
		});

		//系、专业、班级选择
		$(".startChoose").focus(function(){
			$(this).siblings('.list').slideDown('slow');
		});
		$(".xi").click(function(e){
			e.stopPropagation();
			$(".zys").stop(true).slideUp('slow');
			$(".bj").stop(true).slideUp("slow");
			if($(this).find(".zys").is(':visible')){
				$(this).find(".zys").stop(true).slideUp("slow");	
			}
			else{
				$(this).find(".zys").stop(true).slideDown("slow");
			}
		});
		$(".zy").click(function(e){
			e.stopPropagation();
			if(!$(this).find(".bj").is(':visible')){
				$(this).find(".bj").stop(true).slideDown("slow");
			}else{
				$(this).find(".bj").stop(true).slideUp("slow");
			}
		});
		$('.xiInpt').on('click change',function(e){
			e.stopPropagation();
			$(".startChoose").val($(this).siblings('span').text());
			$('.list').slideUp('slow');
		});
		$('.zyInpt').on('click change',function(e){
			e.stopPropagation();
			$('.startChoose').val($(this).parents('.xi').find('.xiInpt').siblings('span').text()+"-"+$(this).siblings('span').text());
			$('.list').slideUp('slow');
		});
		$(".bjInpt").on('change click',function(e){
			e.stopPropagation();
			$(".startChoose").val($(this).parents('.xi').find('.xiInpt').siblings('span').text()+"-"+$(this).parents('.zy').find('.zyInpt').siblings('span').text()+"-"+$(this).siblings('span').text());
			$('.list').slideUp('slow');
		});

		//校验是否手机号格式
		$("#loginName").on('keyup',function(){
			 $(this).attr("phone",$(this).val()); //将当前值存入自定义属性
		}).blur(function(){
			 var phone=$(this).attr("phone");
			 if(!(/^1[3|4|5|7|8]\d{9}$/.test(phone))){ 
				alert('手机格式有误,请重新输入!');
		        return; 
			 } 
			 if(!isHasPhone(phone)){
				 alert('该手机号已经被注册！');
			 }
		}); 
		
		//校验手机号是否已经被注册
		function isHasPhone(mobile){
			var mark = false;
			$.ajax({
				type:"POST",
				async:false,
				url:"${contextPath}/checkPhone",
				data:"loginName="+mobile,
				dataType:'json',
				success:function(data){
					if(data=="noCan"){
						mark = false;
					}else{
						mark = true;
					}
				}
			 });
			return mark;
		}

		//实时校验密码格式是否正确
		$("#checkPwd").on('keyup',function(){
		    $(this).attr("data-oval",$(this).val()); //将当前值存入自定义属性
		}).blur(function(){
		    var oldVal=$(this).attr("data-oval"); //获取原值
		    var newVal1=$('#newPwd').val(); //获取当前值
		    if (oldVal!=newVal1){
		    	alert('您两次输入的密码不一致，请重新输入！');
				isCheckPassword ="no";
		    }else{
		    	isCheckPassword ="yes";
		    }    
		});
		
		//实时校验邮箱格式是否正确
		$("#email").on('keyup',function(){
			 $(this).attr("email",$(this).val()); //将当前值存入自定义属性
		}).blur(function(){
			 var email=$(this).attr("email");
			 if(!(/\w+@\w+(\.[a-zA-z]+)(\w+[a-zA-Z]+)?/.test(email))){
					alert('邮箱格式输入有误，请重新输入！');
					isEmailRight ="no";
			 }else{
			    	isEmailRight ="yes";
			  }  
		});
		
		//判断用户生日是否大于当前日期
		function checkBirth(birthday){	
			return new Date(birthday).getTime() >= new Date().getTime() ? false : true;
		}
		
		//保存修改的个人信息
		$('#saveUserDetail').click(function(){
			//校验手机号
			var loginName = document.getElementById('loginName');
			var loginNameVal = loginName.value;
			if(loginName==null || loginName=='' || !(/^1[3|4|5|7|8]\d{9}$/.test(loginNameVal))){
				alert('手机号码格式有误，请重新输入！');
				return;
			}
			if(!isHasPhone(loginNameVal)){
				alert('该手机号已经被注册！');
				return;
			}
			
			//校验真实姓名
			var userName = document.getElementById('userName');
			var userNameVal = userName.value;
			if(userName==null || userName=='' || userNameVal==''){
				alert('真实姓名不能为空！');
				return;
			}
			
			//校验是否选择性别
			var sexVal = $(".selectSex:checked").val();
			if(sexVal==undefined){
				alert('请选择性别！');
				return;
			}
			
			//校验注册身份
			var userType = document.getElementById('userType');
			var userTypeVal = userType.value;
			if(userType==null || userTypeVal=='' || userTypeVal==''){
				alert('请选择注册身份！');
				return;
			}
			
			//校验所属班级
			var departIdVal = $(".selectSDept:checked").val();
			if(departIdVal==undefined){
				alert('请选择所属部门或班级！');
				return;
			}
			
			var birthday = document.getElementById('birthday');
			var birthdayVal = birthday.value;
			if(!checkBirth(birthdayVal)){
				alert('亲，请再仔细检查下您的出生日期哦！');
				return;
			}

			var postCode = document.getElementById('postCode');
			var postCodeVal = postCode.value;
			
			var address = document.getElementById('address');
			var addressVal = address.value;
	
			
			//校验邮箱地址
			var email = document.getElementById('email');
			var emailVal = email.value;
			if(email==null || email=='' || !(/\w+@\w+(\.[a-zA-z]+)(\w+[a-zA-Z]+)?/.test(emailVal))){
				alert('邮箱格式不正确，请重新输入！');
				return;
			}
			
			//校验密码
			var passwordVal = document.getElementById("newPwd").value;
			var checkPwdVal = document.getElementById('checkPwd').value;
			if(!(/(?=.*\d+)(?=.*[a-z])(?=.*[A-Z])(?=.*_).{8,}/).test(passwordVal)){
				alert('密码必须同时包括数字、大小写字母和下划线！');
				return;
			}
			if(passwordVal != checkPwdVal){
				alert('两次输入的密码不一致，请重新输入！');
				return;
			}
			
			//提交用户信息
			$.ajax({
				type:"POST",
				url:"${contextPath}/user/saveUserDetail",
				data:"userName="+userNameVal+"&loginName="+loginNameVal
						+"&email="+emailVal+"&password="+passwordVal+"&departId="+departIdVal+"&userType="+userTypeVal
						+"&sex="+sexVal+"&birthday="+birthdayVal+"&address="+addressVal+"&postCode="+postCodeVal,
				dataType:'json',
				cache: false,
				success:function(data){
					if(data =="success"){
						alert('注册成功');
						window.location.reload();
					}else if(data =="error"){
						alert('注册失败');
						window.location.reload();
					}
				 }
			});	 
		});
	})();
</script>