/*
 * @Description：教师或学生在textarea文本框中输入的内容格式标准化（替换换行符和空格符）
 * @Date：2017-5-15
 * @Author：赵一鸣
 * */

(function(){
	var strStandard = {
		//临时变量用于存储需要格式化的字符串
		tmpStr : '',
		
		//初始化执行
		init : function(str){
			this.tmpStr = str;
			this.strHandle.call(strStandard);
			return this.tmpStr;
		},
		
		//字符串处理
		strHandle : function(){
			if( (/\n/g).test(this.tmpStr) ){
				this.tmpStr = this.tmpStr.replace(/\n/g, '<br/>');
			}
			if( (/\s/).test(this.tmpStr) ){
				this.tmpStr = this.tmpStr.replace(/\s/g, '；');
			}
		}
	};
	
	window.strStandard = strStandard;
})();