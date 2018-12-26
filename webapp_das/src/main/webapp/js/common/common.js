/**
 * js工具类
 * Created by wangzhongjun on 2015-12-10.
 */

// o全局变量
window.o = {};

// 根路径
o.root_url = '/webapp_rms/';

// 正则表达式
o._regex = {
	email    : /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/, //邮箱
	zip_code : /^(?!(0[0-9]{0,}$))[0-9]{1,}[.]{0,}[0-9]{0,}$/,					// 邮编
	mobile   : /^1\d{10}$/,											// 手机号
	qq       : /^[1-9]\d{4,12}$/,									// QQ号
	companyName:/^[\u4E00-\u9FA5-a-zA-Z\(\)]*$/,				//公司名称
	businessLicense:/^[0-9A-Z\-]{15}$/,								//营业执照编号
	taxReg:/^[0-9-a-zA-Z]{15}$/,                                            //税务登记号
	creditNo:/^[0-9a-zA-Z]{18}$/,									   //统一信用代码
	relayNum:/^[0-9]{0,5}$/,									   //中继编号
	relayName:/^[\u4E00-\u9FA5-0-9a-zA-Z]{0,30}$/,									   //中继名称
	ip:       /^(((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))\.){3}((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))$/,   //中继配置IP规则
	port:     /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,     //中继配置端口号 0-65535
	maxConcurrent:/^([0-9]{0,1}|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/     //最大并发数限制0-65535  可为空

};

/**
 * 等到请求参数
 * @param name 参数名称
 * @returns value 参数对应的值
 */
o.getQueryString = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null) return unescape(r[2]); return null;
};

o.hours = ['00','01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24'];
o.hours_for_days = ['00','01','02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23'];
o.min_5 = [
	"00:00","00:05","00:10","00:15","00:20","00:25","00:30","00:35","00:40","00:45","00:50","00:55",
	"01:00","01:05","01:10","01:15","01:20","01:25","01:30","01:35","01:40","01:45","01:50","01:55",
	"02:00","02:05","02:10","02:15","02:20","02:25","02:30","02:35","02:40","02:45","02:50","02:55",
	"03:00","03:05","03:10","03:15","03:20","03:25","03:30","03:35","03:40","03:45","03:50","03:55",
	"04:00","04:05","04:10","04:15","04:20","04:25","04:30","04:35","04:40","04:45","04:50","04:55",
	"05:00","05:05","05:10","05:15","05:20","05:25","05:30","05:35","05:40","05:45","05:50","05:55",
	"06:00","06:05","06:10","06:15","06:20","06:25","06:30","06:35","06:40","06:45","06:50","06:55",
	"07:00","07:05","07:10","07:15","07:20","07:25","07:30","07:35","07:40","07:45","07:50","07:55",
	"08:00","08:05","08:10","08:15","08:20","08:25","08:30","08:35","08:40","08:45","08:50","08:55",
	"09:00","09:05","09:10","09:15","09:20","09:25","09:30","09:35","09:40","09:45","09:50","09:55",
	"10:00","10:05","10:10","10:15","10:20","10:25","10:30","10:35","10:40","10:45","10:50","10:55",
	"11:00","11:05","11:10","11:15","11:20","11:25","11:30","11:35","11:40","11:45","11:50","11:55",
	"12:00","12:05","12:10","12:15","12:20","12:25","12:30","12:35","12:40","12:45","12:50","12:55",
	"13:00","13:05","13:10","13:15","13:20","13:25","13:30","13:35","13:40","13:45","13:50","13:55",
	"14:00","14:05","14:10","14:15","14:20","14:25","14:30","14:35","14:40","14:45","14:50","14:55",
	"15:00","15:05","15:10","15:15","15:20","15:25","15:30","15:35","15:40","15:45","15:50","15:55",
	"16:00","16:05","16:10","16:15","16:20","16:25","16:30","16:35","16:40","16:45","16:50","16:55",
	"17:00","17:05","17:10","17:15","17:20","17:25","17:30","17:35","17:40","17:45","17:50","17:55",
	"18:00","18:05","18:10","18:15","18:20","18:25","18:30","18:35","18:40","18:45","18:50","18:55",
	"19:00","19:05","19:10","19:15","19:20","19:25","19:30","19:35","19:40","19:45","19:50","19:55",
	"20:00","20:05","20:10","20:15","20:20","20:25","20:30","20:35","20:40","20:45","20:50","20:55",
	"21:00","21:05","21:10","21:15","21:20","21:25","21:30","21:35","21:40","21:45","21:50","21:55",
	"22:00","22:05","22:10","22:15","22:20","22:25","22:30","22:35","22:40","22:45","22:50","22:55",
	"23:00","23:05","23:10","23:15","23:20","23:25","23:30","23:35","23:40","23:45","23:50","23:55",
	"24:00"
];

/**
 *
 * @param smill 开始时间
 * @param emill 结束时间
 * @returns {string}
 */
o.dateDiff = function(smill , emill){
	try {
        var diffValue = parseInt(emill) - parseInt(smill);
        var result = '',
            minute = 1000 * 60,
            hour = minute * 60,
            day = hour * 24,
            halfamonth = day * 15,
            month = day * 30,
            year = month * 12,

            _year = diffValue/year,
            _month = diffValue/month,
            _week = diffValue/(7*day),
            _day = diffValue/day,
            _hour = diffValue/hour,
            _min = diffValue/minute;

            if(_year>=1) result = parseInt(_year) + "年前";
            else if(_month>=1) result = parseInt(_month) + "个月前";
            else if(_week>=1) result = parseInt(_week) + "周前";
            else if(_day>=1) result = parseInt(_day) +"天前";
            else if(_hour>=1) result = parseInt(_hour) +"小时前";
            else if(_min>=1) result = parseInt(_min) +"分钟前";
            else result = "刚刚";
            return result;
	} catch (e) {
		return "";
	}
};

/**
 *
 * @param mills 时间戳
 * @param format 格式（默认：yyyy-MM-dd）
 * @returns {string}
 */
o.formatDate = function(mills , format){
	try {
		var _format = format || "yyyy-MM-dd";
		if(_mills = parseInt(mills)){
			return new Date(_mills).format(_format);
		}
	} catch (e) {
		return "";
	}
};

// 日期格式化
// new Date().format("yyyy-MM-dd");
Date.prototype.format = function(format){ 
	var o = { 
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(), //day 
		"h+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds() //millisecond 
	};
	
	if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 
	
	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
	} 
	return format;
};

/**
 * 空值转换（待完善）
 * @param d
 * @param v
 * @returns {*}
 */
function ifnull(d,v){
	if (d) {
		return d;
	}
	return v;
}

//生成12个月的月份数组
function monthArray(now){
	var d = new Date(now.replace(/[^\d]/g, "/") + "1"),split1 = now.substring(4,5),split2=now.substring(7,8),
			result = [now];
	for(var i = 0; i < 11; i++) {
		d.setMonth(d.getMonth() - 1);
		var m = d.getMonth() + 1;
		m = m < 10 ? "0" + m : m;
		result.push(d.getFullYear() + split1 + m + split2);
	}
	return result;
}

//获取时间差
function getDateDiff(sDate1, sDate2) {
	//sDate1和sDate2是yyyy-MM-dd格式
	var aDate, oDate1, oDate2, iDays;
	aDate = sDate1.split("-");
	oDate1 = (new Date).setFullYear(aDate[0],aDate[1],aDate[2]);
	aDate = sDate2.split("-");
	oDate2 = (new Date).setFullYear(aDate[0],aDate[1],aDate[2]);
	iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
	return iDays; //返回相差天数
}


//获取每月天数的数组
function getMonthDaysArray(year,month,day){
	var days=new Date(year, month, day).getDate();  dayArray=[];
	for (var i=1; i<=days; i++){
		if(i.toString().length<=1){
			dayArray.push("0"+i);
		}else{
			dayArray.push(i.toString());
		}
	}
	return dayArray;
}

function formToJson(data) {
	data=data.replace(/&/g,"\",\"");
	data=data.replace(/=/g,"\":\"");
	data="{\""+data+"\"}";
	return $.parseJSON(data);
}

// datatables访问数据封装处理
function datatables_ajax_data(d){
	var _draw = 0;
	var _page = 1;
	var _columns = [];
	var _pageSize = 30;
	var _order;
	var _start = 0;

	$.each(d, function(name, value){
		if (name == 'draw') {
			_draw = value;
		} else if (name == 'columns') {
			$.each(value, function(i, v){
				_columns.push(v.name || v.data);
			});
		} else if (name == 'order') {
			$.each(value, function(i, v){
				if (v.column) {
					_order = {"sidx" : v.column, "sord" : v.dir || 'asc'};
				}
			});
		} else if (name == 'length') {
			_pageSize = value;
		} else if (name == 'start') {
			_start = value;
		}
	});

	_page = (_start/_pageSize + 1);

	var page_data = {"draw" : _draw, "pageSize" : _pageSize, "page" : _page };

	if (_order) {
		return $.extend(page_data, {"sidx" : _columns[_order.sidx], "sord" : _order.sord});
	}
	return page_data;
}

//只返回数字
function numberResult(str){
	var num = str.trim();
	if(num == "" || isNaN(num) == true){
		num = 0;
	}
	return num;
}

//打开窗口
function openDialog(title,url,w,h){
	layer_show(title,url,w,h);
}

//省市级联
function cascadeArea(fatherId, sonId, url, isWhole){
	$("#"+fatherId).on("change",function(){
		var id = $(this).find("option:selected").attr("id"), code = $(this).find("option:selected").val();
		$("#"+sonId).empty();
		$.ajax({
			type:"post",
			url:url,
			dataType:"json",
			async:false,
			data:{'pid':id},
			success : function(result) {
				if(isWhole) $("#"+sonId).append('<option value="">全部</option>');
				$.each(result.data,function(i,item){
					$("#"+sonId).append("<option id='"+item['id']+"' value='"+item['code']+"' >"+item['name']+"</option>");
				})
			}
		});
	});
}

//文本限制输入规则
function inputLimitRule(ruleType,obj){
	//智能输入数字
	if(ruleType == "number"){
		$(obj).val($(obj).val().replace(/[^\d]/g, ''));
	}
}



//获取请求数据
function loadAjaxData(url,params) {
	var data;
	$.ajax({
		async: false,
		type: "POST",
		url: url,
		data: params,
		success: function(msg){
			data = msg;
		}
	});
	return data;
}