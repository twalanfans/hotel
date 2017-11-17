/*$.fn.sinaEmotion = function(target) {
        target = target
                || function() {
                    return $(this).parents('.studentcommenttext').find(
                            'textarea,input[type=text]').eq(0);
                };
 }*/
/*$('.addcommentbtn').on('click',function(){
	var reg=/[`~!@#\$\^\+<>:"\{\}\\\/']/img;
	if(!$(this).siblings('textarea').val()||reg.test($(this).siblings('textarea').val())){
		alert('回复不能为空或包含特殊符号，请重试！');
		$(this).siblings('textarea').val('');
		$(this).parents('.studentcommenttext').slideUp();
		//$(this).parents('.studentcommenttext').siblings('.btnbox').children('.addcomment').animate({'width':'30%','opacity':'1'},'slow');
		return;
	}
	var oLi=document.createElement('li');
	var oP=document.createElement('p');
	oP.innerHTML=$(this).siblings('textarea').val();
	oLi.appendChild(oP);
	$(this).parents('.studentcommenttext').slideUp();
//	$(this).parents('.studentcommenttext').siblings('.btnbox').children('.addcomment').animate({'width':'30%','opacity':'1'},'slow');
	$(this).parents('.studentcommenttext').siblings('ul').children('li').children('.studentcomment').prepend(oLi).parseEmotion();//parseEmotion()解析表情;
	$(this).siblings('textarea').val('');
	ulH=parseInt(ulH)+oLi.offsetHeight;
});
$('.addface').on('click',function(event){
	    if(! $('#sinaEmotion').is(':visible')){
	        $(this).sinaEmotion();
	        event.stopPropagation();
	    }
}); */
//qq表情，插件不全
	$('.addface').qqFace({
		id : 'facebox', //表情盒子的ID
		assign:'CommentText', //给那个控件赋值
		path:'../static/images/face/'	//表情存放的路径
	});
	/*$(".addCommentBtn").click(function(){
		var reg=/[`~!@#\$\^\+<>:"\{\}\\\/']/img;
		if(!$(this).siblings('textarea').val()||reg.test($(this).siblings('textarea').val())){
			alert('回复不能为空或包含特殊符号，请重试！');
			$(this).parents('.addCommentBtnBox').siblings('.addCommentContent').children().children('.CommentText').val('');
			//$(this).parents('.addCommentBox').slideUp();
			//$(this).parents('.studentcommenttext').siblings('.btnbox').children('.addcomment').animate({'width':'30%','opacity':'1'},'slow');
			return;
		}
		var oLi=document.createElement('li');
		var oP=document.createElement('p');
		oP.innerHTML=$(this).siblings('textarea').val();
		alert(replace_em(<li><p>'+$(this).siblings('textarea').val()+'</p></li>));
		oLi.appendChild(oP);
		//$(this).parents('.addanswerbox').slideUp();
		console.log(('<li><p>'+$(this).siblings('textarea').val()+'</p></li>'));
	//	$(this).parents('.studentcommenttext').siblings('.btnbox').children('.addcomment').animate({'width':'30%','opacity':'1'},'slow');
		$(this).parents('.addCommentBtnBox').siblings('.newsContent').children('.answer').prepend(
			'<li class="answerContent">'
				+'<li class="answerContent clearfix">'
					+'<div class="clearfix fl">'
						+'<div class="fl clearfix commentUserInfo">'
							+'<img src="${ctxStatic}/images/uploadimgbtn.jpg" alt="" title="${commentList.createUser}" class="userPic" />'
							+'<p class="userInfo">${commentList.createUser}</p>'
						+'</div>'
						+'<div class="fr clearfix">'
							+'<span class="fl">${commentList.replyUser}</span>'
							+'<a href="javascript:;" class="fl addReply">回复</a>'
						+'</div>'
					+'</div>'
					+'<div class="commentContentBox fl clearfix">'
						+'<p class="commentContent">'+replace_em($(this).siblings('textarea').val())+'</p>'
						+'<div class="replyUserInfo fr">'
							+'<p class="fl">${commentList.createTime}</p>'
							+'<a href="javascript:;" class="fl addReply">回复&nbsp;</a>'
							+'<p class="fl"><i class="fa fa-thumbs-o-up"></i>&nbsp;<b>${commentList.thinkGood}</b></p>'
						+'</div>'
					+'</div>'
			+'</li>');//parseEmotion()解析表情;
		$(this).parents('.addCommentBtnBox').siblings('.addCommentContent').children().children('.CommentText').val('');
		//ulH=parseInt(ulH)+oLi.offsetHeight;
	});*/

//查看结果
function replace_em(str){
	str = str.replace(/\</g,'&lt;');
	str = str.replace(/\>/g,'&gt;');
	str = str.replace(/\n/g,'<br/>');
	str = str.replace(/\[em_([0-9]*)\]/g,'<img src="../static/images/face/$1.gif" border="0" />');
	return str;
}



