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
	return this.optional(element);
}, "格式不符合！");

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
jQuery.validator.addMethod("qq", function(value, element) {
	var qq = /^[1-9]\d{5,12}$/;
	if(value) {
		return qq.test(value);
	}else{
		return true;
	}
},"请输入正确的qq号！");

// 手机验证码
jQuery.validator.addMethod("vcode",function(value,element,params){
	if(value){
		return /(^\d{6}$)/.test(value);
	}else{
		return true;
	}
},"请输入6位数验证码！");