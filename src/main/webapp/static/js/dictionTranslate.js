/*数据字典下拉选择*/
function optionDict(selectId){
	var str = document.getElementById(selectId).getAttribute("selectDict");
	var arry1 = str.split('[{');
	var arry2 = arry1[1].split('}]');
	var newStr = arry2[0];
	var arry3 = newStr.split(',');
	var newArry = [];
	for(var i=0; i<arry3.length; i++){
		newArry.push(arry3[i].replace(/\s/g,''));
	}
	var tmpArry = [];
	for(var i=0; i<newArry.length; i++){
		tmpArry.push(newArry[i].split('='));
		var options = document.createElement('option');
		options.innerHTML = tmpArry[i][1];
		options.value = tmpArry[i][0];
		document.getElementById(selectId).appendChild(options);
	}
}