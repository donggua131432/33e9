/**
 * validform datatype扩展
 * 参数
 * gets是获取到的表单元素值，
 * obj为当前表单元素，
 * curform为当前验证的表单，
 * regxp['*6-16']为内置的一些正则表达式的引用;
 *
 * 返回
 * true 为验证通过
 * 字符串为错误信息
 *
 * 本代码依赖于 common.js
 */

// 校验手机格式
$.Datatype.mobile = function(gets,obj,curform,regxp){

    if (o._regex.mobile.test(gets)) {
        return true;
    }

    return "手机号格式不正确";
};

// 校验邮箱格式
$.Datatype.email = function(gets,obj,curform,regxp){

    if (o._regex.email.test(gets)) {
        return true;
    }

    return "邮箱号格式不正确";
};

// 校验QQ格式
$.Datatype.qq = function(gets,obj,curform,regxp){

    if (o._regex.qq.test(gets)) {
        return true;
    }

    return "QQ号格式不正确";
};
// 校验图片
$.Datatype.img = function(gets,element,curform,regxp){
// 图片格式
    var reg = /^gif|jpg|jpeg|png$/;

    try{
        var path = gets.substring(value.lastIndexOf(".")).substring(1).toLowerCase();
        if(!reg.test(path)){
            return "文件格式不正确";
        }
    }catch(e){
        return "文件格式不正确";
    }

    var fileSize = 0;

    if(element.files){ // element.files 是chrome，firefox等浏览器的对戏那个，如果是ie的话他的值就是false
        console.log("element.files");
        var thisFile = element.files[0]; // 获取文件的对像
        fileSize = thisFile.size;//获取当前上传的文件的大小
    } else { // 如果是ie
        console.log("element.files else");
    }

    console.log(fileSize);

    return fileSize < 2097152 ? true : "文件过大，最大支持2M";
};


// 校验唯一性
/**
 * 表单元素中请添加属性data-ajax
 * 如：
 *  <input dataType="_ajax" data-ajax="{url:'xxx/xxx',param:[{name:'u',value:'v'},{name:'u',value:'v'}],msg:{a:'xxx',b:'xxx'},_regex:{type:'email',msg:'格式不正确'},recheck:{id:'mobile',eq:'false',msg:'不能和注册手机号相同'}}"/>
 *  or
 *  <input dataType="_ajax" data-ajax="{url:'xxx/xxx',param:[{name:'u',value:'v'},{name:'u',value:'v'}],msg:'手机号不存在',_regex:{type:'email',msg:'格式不正确'},recheck:{id:'mobile',eq:'false',msg:'不能和注册手机号相同'},fn:{sfn:'sfn'}}"/>
 *
 */
$.Datatype._ajax = function(gets,obj,curform,regxp){

    var params = $(obj).attr("data-ajax");
    var name = $(obj).attr("name");

    if (!params) {
        return "请设置属性data-ajax";
    }

    var jp = {};
    try {
        jp = eval( '(' + params + ')' ); // 转换为json对象
    } catch (e) {
        console.log(e);
        return "data-ajax的格式不正确";
    }

    if (jp._regex && o._regex[jp._regex.type] && !o._regex[jp._regex.type].test(gets)) {
        var rm = jp._regex.msg || "格式不正确";
        return rm;
    }

    if (jp.recheck) {
        var recheck = jp.recheck;
        var rvalue = $("#" + recheck.id).val();
        if ((rvalue == $(obj).val()) && (recheck.eq == 'false')){
            return recheck.msg;
        }

        if ((rvalue != $(obj).val()) && (recheck.eq != 'false')){
            return recheck.msg;
        }
    }

    var sfn = false;
    var efn = false;
    if (jp.fn) {
        sfn = jp.fn.sfn || false;
        efn = jp.fn.efn || false;
    }

    if (jp.url) {
        var returResult = true;
        var d = jp.param || [],
            g = jp.msg;
        d.push({name:name, value:$(obj).val()});
        $.ajax({
            async:false,
            type: "post",
            url: jp.url,
            dataType: "json",
            data: d,
            success: function (data) {

                if (typeof data == 'string') {
                    returResult = g[data] || true;
                }

                if (typeof data == 'object') {

                    if (data.code) {
                        if (data.code == 'ok') {
                            returResult = true;

                            if (sfn) {
                                window[sfn](data);
                            }

                        } else {
                            returResult = data.msg || g[data];
                        }
                    } else {
                        if (typeof g == 'string') {
                            returResult = g;
                        }
                    }


                }

            },
            error: function () {
                returResult = "数据请求异常";
            }
        });

        return returResult;
    }

    return true;

};
