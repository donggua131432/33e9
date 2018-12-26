<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <title>ECC配置</title>
</head>
<script type="text/javascript">

    function closeEditDialog(){
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }


    $(function() {

        $("input[name=opertion1][value='${relayBusinessAreaCode1.opertion}']").iCheck('check');
        $("input[name=opertion2][value='${relayBusinessAreaCode2.opertion}']").iCheck('check');
        $("input[name=opertion3][value='${relayBusinessAreaCode3.opertion}']").iCheck('check');
        $("input[name=opertion4][value='${relayBusinessAreaCode4.opertion}']").iCheck('check');

        var opertion1 = ${relayBusinessAreaCode1.opertion};
        if(opertion1==0 || opertion1==1){
            $("#content1").attr("disabled", false);
            $("#content1").val('${relayBusinessAreaCode1.content}');
        }
        var opertion2 = ${relayBusinessAreaCode2.opertion};
        if(opertion2==0 || opertion2==1){
            $("#content2").attr("disabled", false);
            $("#content2").val('${relayBusinessAreaCode2.content}');

        }

        var opertion3= ${relayBusinessAreaCode3.opertion};
        if(opertion3==0 || opertion3==1){
            $("#content3").attr("disabled", false);
            $("#content3").val('${relayBusinessAreaCode3.content}');
        }
        var opertion4 = ${relayBusinessAreaCode4.opertion};
        if(opertion4==0 || opertion4==1){
            $("#content4").attr("disabled", false);
            $("#content4").val('${relayBusinessAreaCode4.content}');
        }



        var opertion5 = ${relayBusinessZero1.opertion};
        if(opertion5==0){
            $("#operator1-0").attr("disabled", false);
            $("#operator1-1").attr("disabled", false);
            $("#operator1-2").attr("disabled", false);
            $("#operator1-3").attr("disabled", false);
        }
        var opertion6 = ${relayBusinessZero2.opertion};
        if(opertion6==0){
            $("#operator2-0").attr("disabled", false);
            $("#operator2-1").attr("disabled", false);
            $("#operator2-2").attr("disabled", false);
            $("#operator2-3").attr("disabled", false);
        }
        var opertion7 = ${relayBusinessZero3.opertion};
        if(opertion7==0){
            $("#operator3-0").attr("disabled", false);
            $("#operator3-1").attr("disabled", false);
            $("#operator3-2").attr("disabled", false);
            $("#operator3-3").attr("disabled", false);
        }
        var opertion8 = ${relayBusinessZero4.opertion};
        if(opertion8==0){
            $("#operator4-0").attr("disabled", false);
            $("#operator4-1").attr("disabled", false);
            $("#operator4-2").attr("disabled", false);
            $("#operator4-3").attr("disabled", false);
        }
        checkedBox('operator1s', '${relayBusinessZero1.operator}');
        checkedBox('operator2s', '${relayBusinessZero2.operator}');
        checkedBox('operator3s', '${relayBusinessZero3.operator}');
        checkedBox('operator4s', '${relayBusinessZero4.operator}');

        $("input[name=opertion5][value='${relayBusinessZero1.opertion}']").iCheck('check');
        $("input[name=opertion6][value='${relayBusinessZero2.opertion}']").iCheck('check');
        $("input[name=opertion7][value='${relayBusinessZero3.opertion}']").iCheck('check');
        $("input[name=opertion8][value='${relayBusinessZero4.opertion}']").iCheck('check');
        /*$("input[name=opertion5]:checked").val('${relayBusinessZero1.opertion}');*/

        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });

        $("#opertion1-1").on("change", function() {
            $("#content1").attr("disabled", false);
            $("#content1").val("");
        });

        $("#opertion1-2").on("change", function() {
            $("#content1").attr("disabled", false);
            $("#content1").val("");
        });

        $("#opertion1-3").on("change", function() {
            $("#content1").attr("disabled", true);
            $("#content1").val("");
        });

        $("#opertion2-1").on("change", function() {
            $("#content2").attr("disabled", false);
            $("#content2").val("");
        });

        $("#opertion2-2").on("change", function() {
            $("#content2").attr("disabled", false);
            $("#content2").val("");
        });

        $("#opertion2-3").on("change", function() {
            $("#content2").attr("disabled", true);
            $("#content2").val("");
        });

        $("#opertion3-1").on("change", function() {
            $("#content3").attr("disabled", false);
            $("#content3").val("");
        });

        $("#opertion3-2").on("change", function() {
            $("#content3").attr("disabled", false);
            $("#content3").val("");
        });

        $("#opertion3-3").on("change", function() {
            $("#content3").attr("disabled", true);
            $("#content3").val("");
        });

        $("#opertion4-1").on("change", function() {
            $("#content4").attr("disabled", false);
            $("#content4").val("");
        });

        $("#opertion4-2").on("change", function() {
            $("#content4").attr("disabled", false);
            $("#content4").val("");
        });

        $("#opertion4-3").on("change", function() {
            $("#content4").attr("disabled", true);
            $("#content4").val("");
        });

        $("#opertion5-1").on("change", function() {
            $("#operator1-0").attr("disabled", false);
            $("#operator1-1").attr("disabled", false);
            $("#operator1-2").attr("disabled", false);
            $("#operator1-3").attr("disabled", false);
        });

        $("#opertion5-2").on("change", function() {
            $("#operator1-0").attr("checked", false);
            $("#operator1-1").attr("checked", false);
            $("#operator1-2").attr("checked", false);
            $("#operator1-3").attr("checked", false);
            $("#operator1-0").attr("disabled", true);
            $("#operator1-1").attr("disabled", true);
            $("#operator1-2").attr("disabled", true);
            $("#operator1-3").attr("disabled", true);
        });

        $("#opertion5-3").on("change", function() {
            $("#operator1-0").attr("checked", false);
            $("#operator1-1").attr("checked", false);
            $("#operator1-2").attr("checked", false);
            $("#operator1-3").attr("checked", false);
            $("#operator1-0").attr("disabled", true);
            $("#operator1-1").attr("disabled", true);
            $("#operator1-2").attr("disabled", true);
            $("#operator1-3").attr("disabled", true);
        });

        $("#opertion6-1").on("change", function() {
            $("#operator2-0").attr("disabled", false);
            $("#operator2-1").attr("disabled", false);
            $("#operator2-2").attr("disabled", false);
            $("#operator2-3").attr("disabled", false);
        });

        $("#opertion6-2").on("change", function() {
            $("#operator2-0").attr("checked", false);
            $("#operator2-1").attr("checked", false);
            $("#operator2-2").attr("checked", false);
            $("#operator2-3").attr("checked", false);
            $("#operator2-0").attr("disabled", true);
            $("#operator2-1").attr("disabled", true);
            $("#operator2-2").attr("disabled", true);
            $("#operator2-3").attr("disabled", true);
        });

        $("#opertion6-3").on("change", function() {
            $("#operator2-0").attr("checked", false);
            $("#operator2-1").attr("checked", false);
            $("#operator2-2").attr("checked", false);
            $("#operator2-3").attr("checked", false);
            $("#operator2-0").attr("disabled", true);
            $("#operator2-1").attr("disabled", true);
            $("#operator2-2").attr("disabled", true);
            $("#operator2-3").attr("disabled", true);
        });

        $("#opertion7-1").on("change", function() {
            $("#operator3-0").attr("disabled", false);
            $("#operator3-1").attr("disabled", false);
            $("#operator3-2").attr("disabled", false);
            $("#operator3-3").attr("disabled", false);
        });

        $("#opertion7-2").on("change", function() {
            $("#operator3-0").attr("checked", false);
            $("#operator3-1").attr("checked", false);
            $("#operator3-2").attr("checked", false);
            $("#operator3-3").attr("checked", false);
            $("#operator3-0").attr("disabled", true);
            $("#operator3-1").attr("disabled", true);
            $("#operator3-2").attr("disabled", true);
            $("#operator3-3").attr("disabled", true);
        });

        $("#opertion7-3").on("change", function() {
            $("#operator3-0").attr("checked", false);
            $("#operator3-1").attr("checked", false);
            $("#operator3-2").attr("checked", false);
            $("#operator3-3").attr("checked", false);
            $("#operator3-0").attr("disabled", true);
            $("#operator3-1").attr("disabled", true);
            $("#operator3-2").attr("disabled", true);
            $("#operator3-3").attr("disabled", true);
        });

        $("#opertion8-1").on("change", function() {
            $("#operator4-0").attr("disabled", false);
            $("#operator4-1").attr("disabled", false);
            $("#operator4-2").attr("disabled", false);
            $("#operator4-3").attr("disabled", false);
        });

        $("#opertion8-2").on("change", function() {
            $("#operator4-0").attr("checked", false);
            $("#operator4-1").attr("checked", false);
            $("#operator4-2").attr("checked", false);
            $("#operator4-3").attr("checked", false);
            $("#operator4-0").attr("disabled", true);
            $("#operator4-1").attr("disabled", true);
            $("#operator4-2").attr("disabled", true);
            $("#operator4-3").attr("disabled", true);
        });

        $("#opertion8-3").on("change", function() {
            $("#operator4-0").attr("checked", false);
            $("#operator4-1").attr("checked", false);
            $("#operator4-2").attr("checked", false);
            $("#operator4-3").attr("checked", false);
            $("#operator4-0").attr("disabled", true);
            $("#operator4-1").attr("disabled", true);
            $("#operator4-2").attr("disabled", true);
            $("#operator4-3").attr("disabled", true);
        });


        // 表单校验
        $("#form-relay-ecc").Validform({
            tiptype:2,
            ajaxPost:true,
            callback:function(data){
                $.Showmsg(data.msg);
                setTimeout(function(){
                    $.Hidemsg();
                    if(data.code == "ok"){
                        //刷新父级页面
                        parent.location.replace(parent.location.href);
                        //关闭当前窗口
//                        layer_close();
                    }
                },2000);
            },
            datatype : {
                "content1": function (gets, obj, curform, datatype) {

                    var content1 = gets.trim();
                    var opertion1 = $("input[name=opertion1]:checked").val();
//                    var opertion1 = $("[name='opertion1']").val();
                    if(opertion1==0){
                        if (content1){
                            var matchReg = /^\d{0,30}$/;
                            if (!matchReg.test(content1)) {
                                return "请输入0-30位数字！";
                            }
                        }
                    }else if(opertion1==1){
                        if (content1){
                            var matchReg = /^\d+$/ ;
                            if (!matchReg.test(content1)) {
                                return "请输入小于等于30的整数！";
                            }
                            if (!(content1 >= 0 && content1 <= 30)) {
                                return "请输入小于等于30的整数！";
                            }
                            if (content1 > 0 && content1.indexOf("0") == 0) {
                                return "请输入小于等于30的整数！";
                            }
                        }

                    }

                    return true;
                },
                "content2": function (gets, obj, curform, datatype) {

                    var content2 = gets.trim();
                    var opertion2 = $("input[name=opertion2]:checked").val();
                    if(opertion2==0){
                        if (content2){
                            var matchReg = /^\d{0,30}$/;
                            if (!matchReg.test(content2)) {
                                return "请输入0-30位数字！";
                            }
                        }
                    }else if(opertion2==1){
                        if (content2){
                            var matchReg = /^\d+$/ ;
                            if (!matchReg.test(content2)) {
                                return "请输入小于等于30的整数！";
                            }
                            if (!(content2 >= 0 && content2 <= 30)) {
                                return "请输入小于等于30的整数！";
                            }
                            if (content2 > 0 && content2.indexOf("0") == 0) {
                                return "请输入小于等于30的整数！";
                            }
                        }

                    }

                    return true;
                },
                "content3": function (gets, obj, curform, datatype) {

                    var content3 = gets.trim();
                    var opertion3 = $("input[name=opertion3]:checked").val();
                    if(opertion3==0){
                        if (content3){
                            var matchReg = /^\d{0,30}$/;
                            if (!matchReg.test(content3)) {
                                return "请输入0-30位数字！";
                            }
                        }
                    }else if(opertion3==1){
                        if (content3){
                            var matchReg = /^\d+$/ ;
                            if (!matchReg.test(content3)) {
                                return "请输入小于等于30的整数！";
                            }
                            if (!(content3 >= 0 && content3 <= 30)) {
                                return "请输入小于等于30的整数！";
                            }
                            if (content3 > 0 && content3.indexOf("0") == 0) {
                                return "请输入小于等于30的整数！";
                            }
                        }

                    }

                    return true;
                },
                "content4": function (gets, obj, curform, datatype) {

                    var content4 = gets.trim();
                    var opertion4 = $("input[name=opertion4]:checked").val();
                    if(opertion4==0){
                        if (content4){
                            var matchReg = /^\d{0,30}$/;
                            if (!matchReg.test(content4)) {
                                return "请输入0-30位数字！";
                            }
                        }
                    }else if(opertion4==1){
                        if (content4){
                            var matchReg = /^\d+$/ ;
                            if (!matchReg.test(content4)) {
                                return "请输入小于等于30的整数！";
                            }
                            if (!(content4 >= 0 && content4 <= 30)) {
                                return "请输入小于等于30的整数！";
                            }
                            if (content4 > 0 && content4.indexOf("0") == 0) {
                                return "请输入小于等于30的整数！";
                            }
                        }

                    }

                    return true;
                }
            }

        });
    });




</script>
<div class="pd-20">
    <form action="<c:url value='/relay/updateEccConfig'/>" method="post" class="form form-horizontal" id="form-relay-ecc">
        <h4 class="page_title">中继信息</h4>
        <input type="hidden" name="relayId" id="relayId" value="${sipBasic.id}"/>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>中继名称：</label>
            <div class="formControls col-6">
                ${sipBasic.relayName}
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>中继编号：</label>
            <div class="formControls col-6">
                ${sipBasic.relayNum}
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>中继区号：</label>
            <div class="formControls col-6">
                ${sipBasic.areacode}
            </div>
            <div class="col-2"></div>
        </div>


        <h4 class="page_title">区号业务</h4>
        <div class="row cl">
            <label class="form-label col-3">内域拨打外域-主叫号码</label>
            <div class="formControls col-5 ">
                <div class="radio-box">
                    <input type="radio" id="opertion1-1"  name="opertion1" datatype="*" value="0" >
                    <label for="opertion1-1">增加区号</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion1-2" value="1" name="opertion1">
                    <label for="opertion1-2">删除区号</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion1-3" value="2" checked name="opertion1">
                    <label for="opertion1-3">不操作</label>
                </div>
            </div>

            <div class=" formControls col-3" >
                <input type="text" class="input-text" value=""  id="content1" name="content1"  disabled datatype="content1" maxlength="30" errormsg="请输入0-30位数字" />
            </div>
            <div class="col-1"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">内域拨打外域-被叫号码</label>
            <div class="formControls col-5 ">
                <div class="radio-box">
                    <input type="radio" id="opertion2-1"  name="opertion2" datatype="*" value="0" nullmsg="请选择区号！">
                    <label for="opertion2-1">增加区号</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion2-2" value="1" name="opertion2">
                    <label for="opertion2-2">删除区号</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion2-3" value="2" checked name="opertion2">
                    <label for="opertion2-3">不操作</label>
                </div>
            </div>

            <div class=" formControls col-3" >
                <input type="text" class="input-text" value=""  id="content2" name="content2"  disabled datatype="content2" maxlength="30" errormsg="请输入0-30位数字"/>
            </div>
            <div class="col-1"></div>
        </div>


        <div class="row cl">
            <label class="form-label col-3">外域拨打内域-主叫号码</label>
            <div class="formControls col-5">
                <div class="radio-box">
                    <input type="radio" id="opertion3-1"  name="opertion3" datatype="*" value="0" nullmsg="请选择区号！">
                    <label for="opertion3-1">增加区号</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion3-2" value="1" name="opertion3">
                    <label for="opertion3-2">删除区号</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion3-3" value="2" checked name="opertion3">
                    <label for="opertion3-3">不操作</label>
                </div>
            </div>

            <div class=" formControls col-3" >
                <input type="text" class="input-text" value=""  id="content3" name="content3" disabled datatype="content3" maxlength="30" errormsg="请输入0-30位数字"/>
            </div>
            <div class="col-1"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">外域拨打内域-被叫号码</label>
            <div class="formControls col-5 ">
                <div class="radio-box">
                    <input type="radio" id="opertion4-1"  name="opertion4" datatype="*" value="0" nullmsg="请选择区号！">
                    <label for="opertion4-1">增加区号</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion4-2" value="1" name="opertion4">
                    <label for="opertion4-2">删除区号</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion4-3" value="2" checked name="opertion4">
                    <label for="opertion4-3">不操作</label>
                </div>
            </div>

            <div class=" formControls col-3" >
                <input type="text" class="input-text" value=""  id="content4" name="content4"  disabled datatype="content4" maxlength="30" errormsg="请输入0-30位数字"/>
            </div>
            <div class="col-1"></div>
        </div>

        </br>
        <h4 class="page_title">0前缀业务</h4>

        <div class="row cl">
            <label class="form-label col-3">内域拨打外域-主叫号码</label>
            <div class="col-5 ">
                <div class="radio-box">
                    <input type="radio" id="opertion5-1"  name="opertion5" datatype="*" value="0" >
                    <label for="opertion5-1">增加0前缀</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion5-2" value="1" name="opertion5">
                    <label for="opertion5-2">删除0前缀</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion5-3" value="2" checked name="opertion5">
                    <label for="opertion3-3">不操作</label>
                </div>
            </div>

            <div class="col-4">
                <input type="checkbox" id="operator1-0" name="operator1s" value="0" disabled/>
                <label for="operator1-0">移动</label>

                <input type="checkbox" id="operator1-1" name="operator1s" value="1" disabled />
                <label for="operator1-1">联通</label>

                <input type="checkbox" id="operator1-2" name="operator1s" value="2"  disabled/>
                <label for="operator1-2">电信</label>

                <input type="checkbox" id="operator1-3" name="operator1s"  value="3" disabled/>
                <label for="operator1-3">其他</label>

            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">内域拨打外域-被叫号码</label>
            <div class=" col-5 ">
                <div class="radio-box">
                    <input type="radio" id="opertion6-1"  name="opertion6" datatype="*" value="0" >
                    <label for="opertion6-1">增加0前缀</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion6-2" value="1" name="opertion6">
                    <label for="opertion6-2">删除0前缀</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion6-3" value="2" checked name="opertion6">
                    <label for="opertion6-3">不操作</label>
                </div>
            </div>

            <div class="col-4">
                <input type="checkbox" id="operator2-0" name="operator2s" value="0" disabled/>
                <label for="operator2-0">移动</label>

                <input type="checkbox" id="operator2-1" name="operator2s" value="1"  disabled/>
                <label for="operator2-1">联通</label>

                <input type="checkbox" id="operator2-2" name="operator2s" value="2"  disabled/>
                <label for="operator2-2">电信</label>

                <input type="checkbox" id="operator2-3" name="operator2s"  value="3" disabled/>
                <label for="operator2-3">其他</label>

            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">外域拨打内域-主叫号码</label>
            <div class=" col-5 ">
                <div class="radio-box">
                    <input type="radio" id="opertion7-1"  name="opertion7" datatype="*" value="0" >
                    <label for="opertion7-1">增加0前缀</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion7-2" value="1" name="opertion7">
                    <label for="opertion7-2">删除0前缀</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion7-3" value="2" checked name="opertion7">
                    <label for="opertion7-3">不操作</label>
                </div>
            </div>

            <div class="col-4">
                <input type="checkbox" id="operator3-0" name="operator3s" value="0" disabled/>
                <label for="operator3-0">移动</label>

                <input type="checkbox" id="operator3-1" name="operator3s" value="1"  disabled/>
                <label for="operator3-1">联通</label>

                <input type="checkbox" id="operator3-2" name="operator3s" value="2"  disabled/>
                <label for="operator3-2">电信</label>

                <input type="checkbox" id="operator3-3" name="operator3s"  value="3" disabled/>
                <label for="operator3-3">其他</label>

            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">外域拨打内域-被叫号码</label>
            <div class=" col-5 ">
                <div class="radio-box">
                    <input type="radio" id="opertion8-1"  name="opertion8" datatype="*" value="0" >
                    <label for="opertion8-1">增加0前缀</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion8-2" value="1" name="opertion8">
                    <label for="opertion8-2">删除0前缀</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="opertion8-3" value="2" checked name="opertion8">
                    <label for="opertion8-3">不操作</label>
                </div>
            </div>

            <div class="col-4">
                <input type="checkbox" id="operator4-0" name="operator4s" value="0" class="start1 " disabled/>
                <label for="operator4-0">移动</label>

                <input type="checkbox" id="operator4-1" name="operator4s" value="1" class="start1 Yh_left50" disabled />
                <label for="operator4-1">联通</label>

                <input type="checkbox" id="operator4-2" name="operator4s" value="2" class="start1 Yh_left50" disabled/>
                <label for="operator4-2">电信</label>

                <input type="checkbox" id="operator4-3" name="operator4s"  value="3" class="start1 Yh_left50" disabled/>
                <label for="operator4-3">其他</label>

            </div>
        </div>

        </br>
        <div class="row cl">
            <div class="col-3 col-offset-5">
                <input class="btn btn-primary radius" type="submit"  id="submitButton" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset" onclick="closeEditDialog();"   value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
        </div>

    </form>
</div>

</body>
</html>