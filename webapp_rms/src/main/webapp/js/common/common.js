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
	m95xxx    : /^95\d{3}$/, 										//95xxx
	qq       : /^[1-9]\d{4,12}$/,									// QQ号
	companyName:/^[\u4E00-\u9FA5-a-zA-Z\(\)]*$/,				//公司名称
	businessLicense:/^[0-9A-Z\-]{15}$/,								//营业执照编号
	taxReg:/^[0-9-a-zA-Z]{15}$/,                                            //税务登记号
	creditNo:/^[0-9a-zA-Z]{18}$/,									   //统一信用代码
	relayNum:/^[0-9]{0,5}$/,									   //中继编号
	relayName:/^[\u4E00-\u9FA5-0-9a-zA-Z]{0,30}$/,									   //中继名称
	ip:       /^(((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))\.){3}((\d{1,2})|(1\d{2})|(2[0-4]\d)|(25[0-5]))$/,   //中继配置IP规则
	port:     /^([0-9]|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,     //中继配置端口号 0-65535
	maxConcurrent:/^([0-9]{0,1}|[1-9]\d|[1-9]\d{2}|[1-9]\d{3}|[1-5]\d{4}|6[0-4]\d{3}|65[0-4]\d{2}|655[0-2]\d|6553[0-5])$/,     //最大并发数限制0-65535  可为空
	url : /^((http):\/\/)?(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i

};

o._check = function(_regex, value) {
	try {
		return _regex.test(value);
	} catch (e){

	}
	return false;
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
}

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
}

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
}

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
	}
	
	if(/(y+)/.test(format)) { 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 
	
	for(var k in o) { 
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
	} 
	return format;
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

/**
 * 导出报表
 * @param $thead 表头 jquery 对象
 * @param columns datatable的column
 * @param search_param 搜索参数
 * @param columnDefs 列转换
 */
function datatables_download_report($thead,columns,search_param,columnDefs){
	window.open();
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

/**
 * 省份、城市市 联动
 * @param pcode 省份容器id
 * @param ccode 城市容器id
 * @param handler 下拉框第一个 如 '--请选择---'
 * @param selectdiy 是否选择自定义 省份、城市 selectdiy：true
 */
function initPCArea(pcode, ccode, handler, selectP, selectC, selectdiy){
	var diy = !!selectdiy;

	if(handler) $("#"+pcode).append('<option value="">' + handler + '</option>');

	$.ajax({
		type:"post",
		url:_ROOT + "dicdata/provinces",
		dataType:"json",
		async:false,
		data:{'diy':diy},
		success : function(result) {

			$.each(result,function(i,item){
				if (item['pcode'] == selectP) {
					$("#"+pcode).append("<option id='"+item['id']+"' value='"+item['pcode']+"' selected>"+item['pname']+"</option>");
				} else {
					$("#"+pcode).append("<option id='"+item['id']+"' value='"+item['pcode']+"'>"+item['pname']+"</option>");
				}
			})
		}
	});

	if(!ccode) {return}

	if(handler) {$("#"+ccode).append('<option value="">' + handler + '</option>');}

	$("#"+pcode).on("change",function(){
		var _pcode = $(this).val();
		initcity(_pcode, ccode, handler, selectC,selectdiy);
	});

	if (selectC) {
		initcity(selectP, ccode, handler, selectC,selectdiy);
	}
}

function initcity(pcode, ccode,handler, selectC,selectdiy) {
	var diy = !!selectdiy;

	$("#"+ccode).empty();
	$.ajax({
		type:"post",
		url:_ROOT + "dicdata/citys",
		dataType:"json",
		async:false,
		data:{'pcode' : pcode, diy:diy},
		success : function(result) {
			if(handler) $("#"+ccode).append('<option value="">' + handler + '</option>');
			$.each(result,function(i,item){
				if (item['ccode'] == selectC) {
					$("#"+ccode).append("<option id='"+item['id']+"' value='"+item['ccode']+"' data-code='"+item['areaCode']+"' selected>"+item['cname']+"</option>");
				} else {
					$("#"+ccode).append("<option id='"+item['id']+"' value='"+item['ccode']+"' data-code='"+item['areaCode']+"'>"+item['cname']+"</option>");
				}

			})
		}
	});
}

/**
 * 字典数据 下拉选项
 * @param eleId 容器id
 * @param typekey 字典类型
 * @param handler 下拉框第一个 如 '--请选择---'
 * @param selected 选择项
 */
function initDicData(eleId, typekey, handler, selected){

	if(handler) $("#"+eleId).append('<option value="">' + handler + '</option>');

	$.ajax({
		type:"post",
		url:_ROOT + "dicdata/type",
		dataType:"json",
		async:false,
		data:{'typekey' : typekey},
		success : function(result) {
			var lists = result.data;
			$.each(lists,function(i,item){
				if (selected == item['code']) {
					$("#"+eleId).append("<option id='"+item['id']+"' value='"+item['code']+"' selected>"+item['name']+"</option>");
				} else {
					$("#"+eleId).append("<option id='"+item['id']+"' value='"+item['code']+"'>"+item['name']+"</option>");
				}
			});
		}
	});

}

/**
 *  下拉选项
 *
 */
function initSelect(eleId, url, key, value, handler, selected, data){

	var selectedName = "";

	if(handler) $("#"+eleId).append('<option value="">' + handler + '</option>');

	$.ajax({
		type:"post",
		url:url,
		dataType:"json",
		async:false,
		success : function(result) {
			var lists = result;
			$.each(lists,function(i,item){
				if (selected == item[key]) {
					selectedName = item[value];
					$("#"+eleId).append("<option value='"+item[key]+"' data-diy='" + data + "' selected>"+item[value]+"</option>");
				} else {
					$("#"+eleId).append("<option value='"+item[key]+"' data-diy='" + data + "'>"+item[value]+"</option>");
				}
			});
		}
	});

	return selectedName;

}

// 选择复选框
function checkedBox(eleName, bits) {
	console.log(bits);
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
