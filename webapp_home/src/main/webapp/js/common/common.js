/**
 * js工具类
 * Created by wangzhongjun on 2015-12-10.
 */

// o全局变量
window.o = {};

//单个文件下载url
var  singleFilePath = 'monthFile/dayFileDownload?file=';
//检查文件是否存在url
var checkFilePath =  'monthFile/checkFilePath?file=';
//下载全部
var allFilePath =  'monthFile/dayAllFileDownload?file=';
/**
 * 等到请求参数
 * @param name 参数名称
 * @returns value 参数对应的值
 */
o.getQueryString = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
};

/**
 *
 * @param smill 开始时间
 * @param emill 结束时间
 * @returns {string}
 */
o.dateDiff = function (smill, emill) {
    try {
        var diffValue = parseInt(emill) - parseInt(smill);
        var result = '',
            minute = 1000 * 60,
            hour = minute * 60,
            day = hour * 24,
            halfamonth = day * 15,
            month = day * 30,
            year = month * 12,

            _year = diffValue / year,
            _month = diffValue / month,
            _week = diffValue / (7 * day),
            _day = diffValue / day,
            _hour = diffValue / hour,
            _min = diffValue / minute;

        if (_year >= 1) result = parseInt(_year) + "年前";
        else if (_month >= 1) result = parseInt(_month) + "个月前";
        else if (_week >= 1) result = parseInt(_week) + "周前";
        else if (_day >= 1) result = parseInt(_day) + "天前";
        else if (_hour >= 1) result = parseInt(_hour) + "小时前";
        else if (_min >= 1) result = parseInt(_min) + "分钟前";
        else result = "刚刚";
        return result;
    } catch (e) {
        return "";
    }
};

o.fmoney = function (s, n) {
    n = n > 0 && n <= 20 ? n : 2;
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
    t = "";
    for (i = 0; i < l.length; i++) {
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
    }
    return t.split("").reverse().join("") + "." + r;
};

/**
 *
 * @param mills 时间戳
 * @param format 格式（默认：yyyy-MM-dd）
 * @returns {string}
 */
o.formatDate = function (mills, format) {
    try {
        var _format = format || "yyyy-MM-dd";
        if (_mills = parseInt(mills)) {
            return new Date(_mills).format(_format);
        }
    } catch (e) {
        return "";
    }
};

// 日期格式化
// new Date().format("yyyy-MM-dd");
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    };

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

//获取AppInfo键值对数组的信息
function getAppInfo(url, id, name, opts) {
    var appInfoArray = {};
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        async: false,
        data: opts,
        success: function (data) {
            $(data).each(function (i) {
                appInfoArray[data[i][id]] = data[i][name];
                $("#" + id).append("<option value='" + data[i][id] + "'>" + data[i][name] + "</option>");
            });
        },
        error: function (data) {
            console.log("数据请求失败！");
        }
    });
    return appInfoArray;
}

//获取AppInfo键值对数组的信息
function getDataList(url, id, name, opts) {
    var appInfoArray = {};
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        async: false,
        data: opts,
        success: function (data) {
            $(data).each(function (i) {
                appInfoArray[data[i][id]] = data[i][name];
            });
        },
        error: function (data) {
            console.log("数据请求失败！");
        }
    });
    return appInfoArray;
}


//获取AppInfo键值对数组的信息
function getDatas(url, opts) {
    var appInfoArray = {};
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        async: false,
        data: opts,
        success: function (data) {
            if (data) {
                appInfoArray = data;
            }
        },
        error: function (data) {
            console.log("数据请求失败！");
        }
    });
    return appInfoArray;
}

//获取AppInfo键值对数组的信息
function getAjaxData(url, opts) {
    var datas;
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        async: false,
        data: opts,
        success: function (data) {
           datas = data;
        },
        error: function (data) {
            console.log("数据请求失败！");
        }
    });
    return datas;
}


//获取时间差
function getDateDiff(sDate1, sDate2) {
    //sDate1和sDate2是yyyy-MM-dd格式
    var aDate, oDate1, oDate2, iDays;
    aDate = sDate1.split("-");
    oDate1 = (new Date).setFullYear(aDate[0], aDate[1], aDate[2]);
    aDate = sDate2.split("-");
    oDate2 = (new Date).setFullYear(aDate[0], aDate[1], aDate[2]);
    iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
    return iDays; //返回相差天数
}

//加载下拉框
function ajaxLoadSelect(url, id, name, opts) {
    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        data: opts,
        success: function (data) {
            $(data).each(function (i) {
                $("#" + id).append("<option value='" + data[i][id] + "'>" + data[i][name] + "</option>");
            })
        },
        error: function () {
            console.log("数据请求失败！");
        }
    });
}

//生成12个月的月份数组
function monthArray(now) {
    var d = new Date(now.replace(/[^\d]/g, "/") + "1"), split1 = now.substring(4, 5), split2 = now.substring(7, 8),
        result = [now];
    for (var i = 0; i < 11; i++) {
        d.setMonth(d.getMonth() - 1);
        var m = d.getMonth() + 1;
        m = m < 10 ? "0" + m : m;
        result.push(d.getFullYear() + split1 + m + split2);
    }
    return result;
}

//获取每月天数的数组
function getMonthDaysArray(year, month, day) {
    var days = new Date(year, month, day).getDate();
    dayArray = [];
    for (var i = 1; i <= days; i++) {
        if (i.toString().length <= 1) {
            dayArray.push("0" + i);
        } else {
            dayArray.push(i.toString());
        }
    }
    return dayArray;
}

function getLastDay(year, month) {
    var new_year = year;  //取当前的年份
    var new_month = month++;//取下一个月的第一天，方便计算（最后一天不固定）
    if (month > 12)      //如果当前大于12月，则年份转到下一年
    {
        new_month -= 12;    //月份减
        new_year++;      //年份增
    }
    var new_date = new Date(new_year, new_month, 1);        //取当年当月中的第一天
    return (new Date(new_date.getTime() - 1000 * 60 * 60 * 24)).getDate();//获取当月最后一天日期
}

//文本限制输入规则
function inputLimitRule(ruleType, obj) {
    //智能输入数字
    if (ruleType == "number") {
        $(obj).val($(obj).val().replace(/[^\d]/g, ''));
    }
}
//转化指定位数的书不足补零  num表示数字  n表示指定的位数
function padLeft(num, n) {
    var len = 0;
    if (num == "" || num == undefined) {
        len = 0;
        num = "";
    } else {
        len = num.toString().length;
    }
    while (len < n) {
        num = "0" + num;
        len++;
    }
    return num.toString();
}

//信息提示框打开
function openMsgDialog(msg) {
    $("#alertMsg").html(msg);
    $("#shadeWin").show();
    $("#showMsgDialog").show();
}

//信息提示框关闭
function closeMsgDialog() {
    $("#shadeWin").hide();
    $("#showMsgDialog").hide();
}

//导出数据提示框
function showExportCallRecord() {
    $("#shadeWin").show();
    $("#re_download_billed").show();
}

function closeExportCallRecord() {
    $("#shadeWin").hide();
    $("#re_download_billed").hide();
}

/**
 * 空值转换（待完善）
 * @param d
 * @param v
 * @returns {*}
 */
function ifnull(d, v) {
    if (d) {
        return d;
    }
    return v;
}

//打开窗口
function openDialog(title, url, w, h) {
    layer_show(title, url, w, h);
}

/**
 * 省份、城市市 联动
 * @param pcode 省份容器id
 * @param ccode 城市容器id
 * @param handler 下拉框第一个 如 '--请选择---'
 * @param _WEB_ROOT 网站的根路径
 */
function initPCArea(pcode, ccode, handler, _WEB_ROOT) {

    if (handler) $("#" + pcode).append('<option value="">' + handler + '</option>');

    $.ajax({
        type: "post",
        url: _WEB_ROOT + "dicdata/provinces",
        dataType: "json",
        async: false,
        //data:{'pid':id},
        success: function (result) {

            $.each(result, function (i, item) {
                $("#" + pcode).append("<option id='" + item['id'] + "' value='" + item['pcode'] + "' >" + item['pname'] + "</option>");
            })
        }
    });

    if (!ccode) {
        return
    }

    if (handler) {
        $("#" + ccode).append('<option value="">' + handler + '</option>');
    }

    $("#" + pcode).on("change", function () {
        var pcode = $(this).val();
        $("#" + ccode).empty();
        $.ajax({
            type: "post",
            url: _WEB_ROOT + "dicdata/citys",
            dataType: "json",
            async: false,
            data: {'pcode': pcode},
            success: function (result) {
                if (handler) $("#" + ccode).append('<option value="">' + handler + '</option>');
                $.each(result, function (i, item) {
                    $("#" + ccode).append("<option id='" + item['id'] + "' value='" + item['ccode'] + "' >" + item['cname'] + "</option>");
                })
            }
        });
    });
}


function downloadReport(mills, eleId, busPath,url) {

    $("#" + eleId).html("");

    var  sysPath = url+singleFilePath +busPath;
    var  checkPath = url+checkFilePath+busPath;
    var  downAllPath =url+allFilePath+busPath;

    var now = mills ? new Date(parseInt(mills)) : new Date();
    var last_day = getLastDay(now.getFullYear(), now.getMonth() + 1);
    var _year = now.getFullYear();
    var _month = now.getMonth() + 1;
    _month = (_month > 9) ? "" + _month : "0" + _month;
    var curr_day = now.getDate();
    var year_month = _year+ ''+ _month;
    var days = []; // arr [day,disable (bool)]
    for (var i = 1; i <= last_day; i++) {
        days.push([i, i >= curr_day]);
    }
    var allPath = downAllPath+'/'+year_month;

    var days_html = '';

    var monthUrl = checkPath + '/' + year_month;
    var datas = getAjaxData(monthUrl, null);

    var isDownloadAll = false;
    for (var i = 0; i < 4; i++) {

        days_html += '<p class="dateBox">';
        for (var j = 0; j < 8; j++) {
            if ((i * 8 + j) < last_day) {
                if (days[i * 8 + j][1]) {
                    days_html += '<a class="disableCss">' + (days[i * 8 + j][0]) + '</a>';
                } else {
                    var fileName = days[i * 8 + j][0];
                    if( days[i * 8 + j][0]<10){
                        fileName = "0"+days[i * 8 + j][0];
                    }
                    var   fileName_txt = _year + "-" + _month  + '-' + fileName + '.txt';
                    var   fileName_csv = _year + "-" + _month  + '-' + fileName + '.csv';
                    if (datas.indexOf(fileName_txt) > -1) {
                        var checkUrl = sysPath + '/' + year_month + '/' + fileName_txt;
                        days_html += '<a href="' + checkUrl + '">' + (days[i * 8 + j][0]) + '</a>';
                        isDownloadAll = true;
                    }else if(datas.indexOf(fileName_csv) > -1) {
                        var checkUrl = sysPath + '/' + year_month + '/' + fileName_csv;
                        days_html += '<a href="' + checkUrl + '">' + (days[i * 8 + j][0]) + '</a>';
                        isDownloadAll = true;
                    } else {
                        days_html += '<a class="disableCss">' + (days[i * 8 + j][0]) + '</a>';
                    }
                }
            }

        }
        days_html += '</p>';
    }

    var download_all = '<a href="'+allPath+'" target="_blank" class="downAll '+ (isDownloadAll? '':'disableCs') +'"><i class="common_icon download_txt"></i>导出全部</a>';

    var model = '\
    <div class="modal-box  phone_t_code" id="dataModel" style="height:360px;" >\
        <div class="modal_head">\
        <h4>导出本月话单</h4>\
        <div class="p_right"><a href="javascript:closeExportCallCurMonthRecord();" class="common_icon close_txt"></a></div>\
        </div>\
        <div class="modal-common_body mdl_pt_body" style="margin-left:25px;">\
        <div class="BilledHistoryBox">\
        <div class="f_left marLeft50">\
        <div class="titlebox">\
        <span class="historyTitle">本月通话记录—' + _year + '年' + (_month) + '月</span>'
        + download_all +
        '</div>\
        <div class="contentBox">'
        + days_html +
        '</div>\
        </div>\
        </div>\
        </div>\
        </div>\
        ';

    $("#" + eleId).html(model);

    $(".dateBox>a").click(function(){
        if($(this).hasClass("disableCss")){
            return false;
        }else{
            $(this).addClass("checkBg");
        }
    });

    return model;
}

function  downLoadDayAll(url){

}

//导出数据提示框
function showExportCallCurMonthRecord(eleId) {
    $("#shadeWin").show();
    $("#dataModel").show();
}

function closeExportCallCurMonthRecord(eleId) {
    $("#shadeWin").hide();
    $("#dataModel").hide();
}

