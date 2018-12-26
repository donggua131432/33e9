<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>添加线路资源</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <style>
        #pertab tr{margin-bottom: 5px;}
    </style>
    <script type="text/javascript">
        $(function() {

            initPCArea("pcode", "cityid", "请选择");

            initSelect("relayNum", "<c:url value='/relay/getRelaysForRes'/>?useType=01", "relayNum", "relayName", "请选择");

            $("#addSupplier").Validform({
                tiptype:2,
                ajaxPost:true,
                callback:function(data){
                    $.Showmsg(data.msg);
                    setTimeout(function(){
                        $.Hidemsg();
                        if(data.code == 'ok'){
                            //刷新父级页面
                            parent.location.replace(parent.location.href);
                            layer_close();
                        }
                    },2000);
                },
                beforeSubmit:function(curform){
                    //在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
                    //这里明确return false的话表单将不会提交;
                    $("#pertab div[name='pertr']").each(function(index,obj){
                        var $obj = $(obj);

                        var opera = $obj.find("select[name='persOperator']").val();
                        var callt = $obj.find("select[name='persCallType']").val();
                        var numty = $obj.find("select[name='persNumType']").val();
                        var persp = $obj.find("input[name='persPer']").val();
                        var cycle = $obj.find("select[name='persCycle']").val();

                        $obj.find("input[name='resPers']").val(opera + "-" + callt + "-" + numty + "-" + persp + "-" + cycle);
                    });

                    return true;
                },
                datatype: {
                    "f7-4" : /^(([0-9]{1,7})|(([0-9]{1,7})[.]{1}([0-9]{0,4})?))$/,
                    "zh1-10":/^[\u4E00-\u9FA5\uf900-\ufa2d]{1,12}$/,
                    "empty": /^\s*$/,
                    "uniquePer" : function(gets,obj,curform,regxp){
                        var curr_pertr = obj.parents("div[name='pertr']");
                        curr_pertr.children("div[name='unq']").html('');

                        var curr_index = curr_pertr.index();
                        var curr_opera = curr_pertr.find("select[name='persOperator']").val();
                        var curr_callt = curr_pertr.find("select[name='persCallType']").val();
                        var curr_numty = curr_pertr.find("select[name='persNumType']").val();
                        var curr_value = curr_opera + curr_callt + curr_numty;
                        console.log(curr_value);

                        var isunq = true;

                        $("#pertab div[name='pertr']").each(function(index,obj){
                            var $obj = $(obj);
                            if (curr_index != index) {
                                var opera = $obj.find("select[name='persOperator']").val();
                                var callt = $obj.find("select[name='persCallType']").val();
                                var numty = $obj.find("select[name='persNumType']").val();
                                if (isunq && curr_opera == opera && curr_callt == callt && curr_numty == numty) {
                                    curr_pertr.children("div[name='unq']").append('<span class="Validform_checktip Validform_wrong">该组合已存在</span>');
                                    isunq = false;
                                    return;
                                }
                            }
                        });

                        return isunq;
                    },
                    "persPer" : function(gets,obj,curform,regxp){
                        var curr_pertr = obj.parents("div[name='pertr']");
                        curr_pertr.children("div[name='per']").html('');
                        if (!/^(([0-9]{1,7})|(([0-9]{1,7})[.]{1}([0-9]{0,4})?))$/.test(gets)) {
                            curr_pertr.children("div[name='per']").append('<span class="Validform_checktip Validform_wrong">请输入数字</span>');
                            return false;
                        }
                        return true;
                    }
                }
            });

            // 中继选择发生变化是
            $("#relayNum").on('change', function(e){

                $("#relayNums").text("");
                $("#ipport1").text("");
                $("#ipport2").text("");
                $("#ipport3").text("");
                $("#ipport4").text("");

                var relayNmu = $("#relayNum").val();
                if (!relayNmu) {
                    return;
                }
                $("#relayNums").text(relayNmu);
                $.ajax({
                    type: "POST",
                    url: "<c:url value='/sip/getSipBasic'/>",
                    data: {"relayNum" : relayNmu},
                    success: function(data){
                        $("#ipport1").text(data.sipBasic.ipport1);
                        $("#ipport2").text(data.sipBasic.ipport2);
                        $("#ipport3").text(data.sipBasic.ipport3);
                        $("#ipport4").text(data.sipBasic.ipport4);
                    },
                    error:function (XMLHttpRequest, textStatus, errorThrown) {
                        layer.alert("请求失败");
                    }
                });

            });

            $("#cityid").on("change", function() {
                $("#areaCode").text("");
                if (this.value) {
                    $("#areaCode").text($("option:selected",this).attr("data-code"));
                }
            });

            $("#pcode").on("change", function() {
                $("#areaCode").text("");
            });

        });

        function addPer(){
            if ($("div[name='pertr']").length >= 18) {
                return;
            }
            var per = '<div class="row cl" name="pertr">\
                        <label class="form-label col-1"></label>\
                        <div class="col-1">\
                            <select class="input-text" style="width: 60px" datatype="uniquePer" name="persOperator">\
                                <option value="00">移动</option>\
                                <option value="01">联通</option>\
                                <option value="02">电信</option>\
                            </select>\
                        </div>\
                        <div class="col-1">\
                            <select class="input-text" style="width: 60px" datatype="uniquePer" name="persCallType">\
                                <option value="00">市话</option>\
                                <option value="01">长途</option>\
                            </select>\
                        </div>\
                        <div class="col-1">\
                            <select class="input-text" style="width: 60px" datatype="uniquePer" name="persNumType">\
                                <option value="00">手机</option>\
                                <option value="01">固话</option>\
                                <option value="04">其他</option>\
                            </select>\
                        </div>\
                        <div class="col-2" name="unq">\
                        </div>\
                        <div class="col-2">\
                            <input class="input-text" style="width: 80px" name="persPer" datatype="persPer"/>\
                            &nbsp;元&nbsp;&nbsp;&nbsp;/&nbsp;\
                        </div>\
                        <div class="col-1">\
                            \
                            <select class="input-text" style="width: 60px" name="persCycle">\
                                <option value="6">6秒</option>\
                                <option value="60">分</option>\
                            </select>\
                        </div>\
                        <div class="col-1">\
                            <input type="hidden" name="resPers" value=""/>\
                            <a href="javascript:void(0);" onclick="delPer(this);" class="ml-5" style="text-decoration:none"><i style="font-size: 20px;" class="Hui-iconfont">&#xe631;</i></a>\
                        </div>\
                        <div class="col-2" name="per">\
                        </div>\
                     </div>';
            $("#pertab").append(per);
        }

        function delPer(obj) {
            $(obj).parents("div[name='pertr']").remove();
        }
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/relayRes/addRes"/>" method="post" class="form form-horizontal" id="addSupplier">
        <input type="hidden" value="${supId}" id="supId" name="supId"/>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>资源名称：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="" placeholder="请输入资源名称" id="resName" name="resName" datatype="/^[\u4E00-\u9FA50-9a-zA-Z]{1,20}$/" maxlength="20" errormsg="资源名称不合法" nullmsg="资源名称不能为空"/>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>中继名称：</label>
            <div class="formControls col-7">
                <select class="input-text" id="relayNum" name="relayNum" datatype="*" nullmsg="请选择中继">

                </select>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"></label>
            <div class="formControls col-7">
                中继ID：<span id="relayNums"></span>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"></label>
            <div class="formControls col-7">
                外域对端IP和端口：<span id="ipport1"></span>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3"></label>
            <div class="formControls col-7">
                外域本端IP和端口：<span id="ipport2"></span>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3"></label>
            <div class="formControls col-7">
                内域对端IP和端口：<span id="ipport3"></span>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3"></label>
            <div class="formControls col-7">
                内域本端IP和端口：<span id="ipport4"></span>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2">线路数：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="" placeholder="请输入线路数" id="relayCnt" name="relayCnt" datatype="empty|/^(0|[1-9][0-9]{0,7})$/" maxlength="10" errormsg="请输入整数"/>
            </div>
            <div class="col-3"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2">总低消：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="0" placeholder="总低消" id="relayRent" name="relayRent" datatype="empty|f7-4" maxlength="11" errormsg="请输入数字" />
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2">所属运营商：</label>
            <div class="formControls col-7">
                <select class="input-text" id="operator" name="operator" datatype="*" nullmsg="请选择运营商">
                    <option value="">请选择</option>
                    <option value="00">中国移动</option>
                    <option value="01">中国联通</option>
                    <option value="02">中国电信</option>
                    <option value="06">其他</option>
                </select>
            </div>
            <div class="col-3"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2">归属地：</label>
            <div class="formControls col-7">
                省份：<select class="input-text" style="width: 120px;" id="pcode" name="pcode"></select>
                &nbsp;&nbsp;
                城市：<select class="input-text" style="width: 120px;" id="cityid" name="cityid" datatype="*" nullmsg="请选择城市"></select>
                &nbsp;&nbsp;
                <span id="areaCode"></span>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2">签署类型：</label>
            <div class="formControls col-7">
                <select class="input-text" name="signType" id="signType" datatype="*" nullmsg="请选择签署类型">
                    <option value="">请选择</option>
                    <option value="00">运营商直签</option>
                    <option value="01">第三方资源</option>
                </select>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2">价格：</label>
            <div class="formControls col-7">
                <input class="input-text" style="width: 160px" name="per" datatype="f7-4" nullmsg="请输入价格"/>
                &nbsp;元&nbsp;/&nbsp;&nbsp;
                <select class="input-text" style="width: 60px" id="cycle" name="cycle">
                    <option value="6">6秒</option>
                    <option value="60">分</option>
                </select>
            </div>
            <div class="col-3"></div>
        </div>

        <h4 class="page_title"></h4>

        <div class="row cl">
            <label class="form-label col-1"></label>
            <div class="col-1">运营商</div>
            <div class="col-1">类型</div>
            <div class="col-1">落地号码</div>
            <div class="col-2"></div>
            <div class="col-2">价格</div>
            <div class="col-1">计费单位</div>
            <div class="col-1">
                <a href="javascript:void(0);" onclick="addPer();" class="ml-5" style="text-decoration:none"><i style="font-size: 20px;" class="Hui-iconfont">&#xe716;</i></a>
            </div>
            <div class="col-2">
            </div>
        </div>

        <div id="pertab">

        </div>

        <h4 class="page_title"></h4>

        <div class="row cl">
            <div class="col-offset-5">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;确定&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="button" onclick="layer_close();" value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>
