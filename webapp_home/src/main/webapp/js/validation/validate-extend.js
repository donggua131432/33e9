//扩展validate库
// 邮编
jQuery.validator.addMethod("positive", function(value, element) {
	var tel = /^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$/;
	return tel.test(value);
}, "大于0!");

// email
jQuery.validator.addMethod("email", function(value, element) {
	if(value){
		var tel = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		return tel.test(value);
	}
	return true;
}, "请输入有效的电子邮件!");

// 密码（8-16位英文加数字，不可使用各类符号）
jQuery.validator.addMethod("password", function(value, element) {
	if(value){
		var tel = /^(\w*(?=\w*\d)(?=\w*[a-zA-Z])\w*)$/;
		var length = /^[0-9a-zA-Z]{8,16}$/;
		return tel.test(value) && length.test(value);
	}
	return true;
}, "请输入8-16位密码，英文加数字，不能使用各类符号！");

// 用户名(邮箱或手机号)
jQuery.validator.addMethod("username",function(value,element,params){

	var email = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var mobile = /^(\+\d+)?1[3458]\d{9}$/;

	if(value){
		return mobile.test(value) || email.test(value);
	}
	return true;
},"请输入正确的邮箱或手机号！");

// 手机号
jQuery.validator.addMethod("mobile",function(value,element,params){
	var mobile =  /^1\d{10}$/;
	if(value){
		return mobile.test(value);
	}
	return true;
},"请输入正确的手机号！");

// 电话
jQuery.validator.addMethod("telephone",function(value,element,params){
	if(value){
		return /^0\d{2,3}-?\d{7,8}$/.test(value) || /^1\d{10}$/;
	}else{
		return true;
	}
},"请输入正确的电话号码！");

// 身份证
jQuery.validator.addMethod("identity",function(value,element,params){
	if(value){
		return /(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(value);
	}else{
		return true;
	}
},"请输入正确的身份证号！");
// 邮编
jQuery.validator.addMethod("qq", function(value, element,params) {
	var qq = /^[1-9]\d{4,12}$/;
	if(value) {
		return qq.test(value);
	}else{
		return true;
	}
},"请输入正确的qq号！");

//应用名称校验规则
jQuery.validator.addMethod("appName", function(value, element,params) {
	//var reg =  /^[\u4E00-\u9FA50-9a-zA-Z\d\(\)]*$/;
	var reg = /^[\s]*[\u4E00-\u9FA50-9a-zA-Z\d\(\)]*[\s]*$/;

	if(value) {
		return reg.test(value);
	}else{
		return true;
	}
},"请输入正确的应用名称！");


//文本内容校验规则
jQuery.validator.addMethod("tContent", function(value, element,params) {
	var reg = /^[\s\S]*[\u4E00-\u9FA50-9a-zA-Z\d\(\)\,\.\;\'\"\?\？\。\、\，\:\+\=\*]*[\s]*$/;
	//var reg = /^[\u4E00-\u9FA50-9a-zA-Z\(\)\-\、\（\）\"\"\“\”\,\，]{0,200}$/;
	if(value) {
		return reg.test(value);
	}else{
		return true;
	}
},"请输入正确的文本内容！");


//URL校验规则
jQuery.validator.addMethod("appUrl", function(value, element,params) {
	//var strRegex = "^((https|http|ftp|rtsp|mms)?://)"
	//var strRegex ="/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;"
	var reg=/^(http):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i
	if(value) {
		var flag = reg.test(value);
		return flag;
	}else{
		return true;
	}
},"请输入正确的URL！");

// 手机验证码
jQuery.validator.addMethod("vcode",function(value,element,params){
	if(value){
		return /(^\d{6}$)/.test(value);
	}else{
		return true;
	}
},"请输入6位数验证码！");

// 消费限额
jQuery.validator.addMethod("quota",function(value,element,params){
	if(value){
		//return /(^\d+(\.\d+)?$)/.test(value);  //支持整数和小数 /^(\d|[1-9]\d+)(\.\d+)?$/
		//return /([1-9]\d*(\.\d*[1-9])?)|(0\.\d*[1-9])/.test(value); //大于0的数
		  return /^([1-9]+\d*(\.[0-9]{1,2})?|0\.[0-9]{1,2})$/.test(value); //大于0的数
	}else{
		return true;
	}
},"请输入正确的消费限额！");

//公司名称校验规则
jQuery.validator.addMethod("companyName", function(value, element,params) {
	var reg = /^[\u4E00-\u9FA5a-zA-Z\(\)\-\（\）\"\"\“\”\,\，]{0,30}$/;
	if(value) {
		return reg.test(value);
	}else{
		return true;
	}
},"格式不正确，请重新输入！");

// 公式地址
jQuery.validator.addMethod("address", function(value, element,params) {
	var reg = /^[\u4E00-\u9FA50-9a-zA-Z\(\)\-\、\（\）\"\"\“\”\,\，]{0,50}$/;
	if(value) {
		return reg.test(value);
	}else{
		return true;
	}
},"格式不正确，请重新输入！");

// 统一社会信用代码
jQuery.validator.addMethod("creditNo", function(value, element,params) {
	var reg = /^[0-9a-zA-Z]{18}$/;
	if(value) {
		return reg.test(value);
	}else{
		return true;
	}
},"格式不正确，请重新输入！");

// 注册号
jQuery.validator.addMethod("registerNo", function(value, element,params) {
	var reg = /^[0-9A-Z\-]{15}$/;
	if(value) {
		return reg.test(value);
	}else{
		return true;
	}
},"格式不正确，请重新输入！");

// 税务登记号
jQuery.validator.addMethod("taxRegNo", function(value, element,params) {
	var reg = /^[0-9A-Z\-]{15}$/;
	if(value) {
		return reg.test(value);
	}else{
		return true;
	}
},"格式不正确，请重新输入！");

// 营业执照号
jQuery.validator.addMethod("businessLicenseNo", function(value, element,params) {
	var reg = /^[0-9A-Z\-]{15}$/;
	if(value) {
		return reg.test(value);
	}else{
		return true;
	}
},"格式不正确，请重新输入！");

// 企业法人
jQuery.validator.addMethod("legalRepresentative", function(value, element,params) {
	var reg = /^[\u4E00-\u9FA5a-zA-Z]{2,20}$/;
	if(value) {
		return reg.test(value);
	}else{
		return true;
	}
},"格式不正确，请重新输入！");

// 公司电话
jQuery.validator.addMethod("telno", function(value, element,params) {
	var reg = /^[0-9\-\(\)]{3,15}$/;
	if(value) {
		return reg.test(value);
	}else{
		return true;
	}
},"格式不正确，请重新输入（最少三位数字）！");

// 网址
jQuery.validator.addMethod("website", function(value, element,params) {
	var reg = /^(http(s)?:\/\/)?(www\.)?[\w-]+\.\w{2,4}(\/)?$/;
	if(value) {
		return reg.test(value);
	}else{
		return true;
	}
},"格式不正确，请重新输入！");

// SIP号码数量
jQuery.validator.addMethod("amount", function(value, element,params) {
	var reg = /^([1-9]\d{0,10})$/;
	if(value) {
		return reg.test(value);
	}else{
		return true;
	}
},"格式不正确，请重新输入！");
// 绑定固话比例
jQuery.validator.addMethod("rate", function(value, element,params) {
	var reg = /^([1-9]\d{0,10})$/;
	if(value) {
		return reg.test(value);
	}else{
		return true;
	}
},"格式不正确，请重新输入！");

// 选择复选框
function checkedBox(eleName, bits) {
	if (bits) {
		var bit = parseInt(bits);
		var binaryStr = bit.toString(2);

		var arrs = [];
		for (var i=0; i<binaryStr.length; i++) {
			if ((bit & (1 << i)) != 0) {
				arrs.push(i);
			}
		}
		if (arrs) {
			$("input[name='" + eleName + "']").val(arrs);
		}
	}
}