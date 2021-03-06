<%--
  Created by IntelliJ IDEA.
  User: dukai
  Date: 2016/6/1
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>添加云话机费率</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function(){
            $("#checkForever").click(function(){
                if($(this).is(":checked") == false){
                    $("#startDate,#endDate").removeAttr("disabled");
                    $(this).attr("checked",false);
                    $("#forever").val(0);
                }else{
                    $("#startDate,#endDate").attr("disabled","disabled").val("");
                    $(this).attr("checked",true);
                    $("#dateMsg").html('<span class="Validform_checktip Validform_right"></span>');
                    $("#forever").val(1);
                }
            })

            $("#addMaskRateForm").Validform({
                tiptype:2,
                ajaxPost:true,
                callback:function(data){
                    setTimeout(function(){
                        $.Hidemsg();
                        if(data.status == 0){
                            //刷新当前页面
                            location.replace(location.href);

                            //刷新父级页面
                            //parent.location.replace(parent.location.href);
                            //layer_close();
                        }
                    },2000);
                },
                datatype: {
                    "f2" : /^([0-9]{1,2})$/,
                    "f3" : /^([0-9]{1,3})$/,
                    "f7-3" : /^(([0-9]{1,7})|(([0-9]{1,7})[.]{1}([0-9]{0,3})?))$/,
                    "f3-2" : /^(([0-9]{1,3})|(([0-9]{1,3})[.]{1}([0-9]{0,2})?))$/,
//                    "f1_2-2" : /^((100)|((100)[.]{1}([0]{0,1})?)|([0-9]{1,2})|(([0-9]{1,2})[.]{1}([0-9]{0,1})?))$/,
                    "f1_2-2" : /^((3000)|((3000)[.]{1}([0]{0,1})?)|([0-2]?[0-9]{1,3})|(([0-2]?[0-9]{1,3})[.]{1}([0-9]{0,1})?))$/,
                    "f1_2-3" : /(^[0-9]{1,6})+(\.[0-9]{0,2})?$/,
                    "dateCheck": function (gets, obj, curform, datatype){
                        if($("#checkForever").is(":checked")){
                            $("#startDate").removeClass("Validform_error");
                            $("#endDate").removeClass("Validform_error");
                            return true;
                        }else{
                            if($("#startDate").val() != '' &&  $("#endDate").val() != ''){
                                $("#startDate").removeClass("Validform_error");
                                $("#endDate").removeClass("Validform_error");
                                return true;
                            }else{
                                $("#dateMsg").html('<span class="Validform_checktip Validform_wrong">请填写时间或选择永久有效</span>');
                                return false;
                            }
                        }
                    },
                    "checkedCallSid":function(gets, obj, curform, datatype){
                        var sid = gets.trim(), checkStatus = false, matchReg = /^([0-9a-zA-Z]{32})$/;
                        if(matchReg.test(sid)){
                            $.ajax( {
                                type:"post",
                                url:"<c:url value='/userAdmin/getUserAdminWithCompany'/>",
                                dataType:"json",
                                async:false,
                                data:{'sid':sid},
                                success : function(data) {
                                    if(data.status == 0){
                                        $("#name").text(data.userAdminResult.authenCompany.name).removeAttr("style");
                                        checkStatus = true;
                                    }
                                }
                            });
                        }else{
                            $("#name").text("客户名称自动匹配").css("color","#cccccc");
                            return "开发者ID不合法！";
                        }
                        if(checkStatus == true){
                            return true;
                        }else{
                            $("#name").text("客户名称自动匹配").css("color","#cccccc");
                            return "开发者ID不存在！";
                        }
                    }
                }
            });
        });

        function closeFeeRateDialog(){
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }

        function discountPrice(obj){


            var discount =  numberResult($(obj).val());

            if(discount >= 0){
                if($(obj).attr("id") == "restADiscount"){
                    $("input[name = 'restADiscount']").val($.calcEval(discount+"*10"));
                    $("#restADiscountPrice").text($.calcEval("${phoneRate.restA}*"+discount+"/100").toFixed(4));
                }else if($(obj).attr("id") == "restBDiscount"){
                    $("input[name = 'restBDiscount']").val($.calcEval(discount+"*10"));
                    $("#restBDiscountPrice").text($.calcEval("${phoneRate.restB}*"+discount+"/100").toFixed(4));
                }else if($(obj).attr("id") == "restBSipphoneDiscount"){
                    $("input[name = 'restBSipphoneDiscount']").val($.calcEval(discount+"*10"));
                    $("#restBSipphoneDiscountPrice").text($.calcEval("${phoneRate.restBSipphone}*"+discount+"/100").toFixed(4));
                }else if($(obj).attr("id") == "restRecordingDiscount"){
                    $("input[name = 'restRecordingDiscount']").val($.calcEval(discount+"*10"));
                    $("#restRecordingDiscountPrice").text($.calcEval("${phoneRate.restRecording}*"+discount+"/100").toFixed(4));
                }

                else if($(obj).attr("id") == "sipcallDiscount"){
                    $("input[name = 'sipcallDiscount']").val($.calcEval(discount+"*10"));
                    $("#sipcallDiscountPrice").text($.calcEval("${phoneRate.sipcall}*"+discount+"/100").toFixed(4));
                }else if($(obj).attr("id") == "sipcallsipDiscount"){
                    $("input[name = 'sipcallsipDiscount']").val($.calcEval(discount+"*10"));
                    $("#sipcallsipDiscountPrice").text($.calcEval("${phoneRate.sipcallsip}*"+discount+"/100").toFixed(4));
                }else if($(obj).attr("id") == "sipcallRecordingDiscount"){
                    $("input[name = 'sipcallRecordingDiscount']").val($.calcEval(discount+"*10"));
                    $("#sipcallRecordingDiscountPrice").text($.calcEval("${phoneRate.sipcallRecording}*"+discount+"/100").toFixed(4));
                }

                else if($(obj).attr("id") == "backcallDiscount"){
                    $("input[name = 'backcallDiscount']").val($.calcEval(discount+"*10"));
                    $("#backcallDiscountPrice").text($.calcEval("${phoneRate.backcall}*"+discount+"/100").toFixed(4));
                }else if($(obj).attr("id") == "backcallRecordingDiscount"){
                    $("input[name = 'backcallRecordingDiscount']").val($.calcEval(discount+"*10"));
                    $("#backcallRecordingDiscountPrice").text($.calcEval("${phoneRate.backcallRecording}*"+discount+"/100").toFixed(4));
                }

                else if($(obj).attr("id") == "numRentDiscount"){
                    $("input[name = 'numRentDiscount']").val($.calcEval(discount+"*10"));
                    $("#numRentDiscountPrice").text($.calcEval("${phoneRate.numRent}*"+discount+"/100").toFixed(4));
                }
            }
        }

    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value='/spRate/submitPhoneRate'/>" method="post" class="form form-horizontal" id="addMaskRateForm">
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>Account ID：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="" placeholder="请输入开发者Account ID" id="sid" name="sid" datatype="checkedCallSid" errormsg="Account ID不合法！" nullmsg="Account ID不能为空"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>客户名称：</label>
            <div class="formControls col-7">
                <span id="name" style="color: #cccccc">客户名称自动匹配</span>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>有效时间：</label>
            <div class="formControls col-7">
                <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{\'%y-%M-%d\'}',maxDate:'#F{$dp.$D(\'endDate\')}'})" readonly="readonly" id="startDate" name="startDate" datatype="dateCheck"  style="width:186px;" class="input-text Wdate" nullmsg="请填写时间或选择永久有效"/>-
                <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})" readonly="readonly" id="endDate" name="endDate" datatype="dateCheck" style="width:186px;" class="input-text Wdate" nullmsg="请填写时间或选择永久有效"/>
                <div>
                    <input type="checkbox" id="checkForever" datatype="dateCheck" nullmsg="请填写时间或选择永久有效"/>
                    <label class="c-666" for="checkForever">永久有效</label>
                    <input type="hidden" id="forever" name="forever"/>
                </div>
            </div>
            <div id="dateMsg" class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2">费用项目：</label>
            <div class="formControls col-10" >
                <table style="margin-top: -10px;width:100%;">
                    <tr>
                        <!-- 云话机回拨A路 start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>云话机回拨A路</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${phoneRate.restA}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd><label class=""><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="restADiscount" onkeyup="discountPrice(this);" onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                        <input type="hidden" name="restADiscount" value="1000"/>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="restADiscountPrice">${phoneRate.restA}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                        <input type="hidden" name="feeMode" value="分钟"/>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                                <div class="col-2"></div>
                            </div>
                        </td>
                        <!-- 云话机回拨A路 end -->

                        <!-- 云话机回拨B路 start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>云话机回拨B路</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${phoneRate.restB}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd><label><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="restBDiscount" onkeyup="discountPrice(this);"  onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                        <input type="hidden" name="restBDiscount" value="1000"/>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="restBDiscountPrice">${phoneRate.restB}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                                <div class="col-2"></div>
                            </div>
                        </td>
                        <!-- 云话机回拨B路 end -->

                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>云话机回拨B路Sip Phone</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${phoneRate.restBSipphone}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd><label class=""><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="restBSipphoneDiscount" onkeyup="discountPrice(this);" onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                        <input type="hidden" name="restBSipphoneDiscount" value="1000"/>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="restBSipphoneDiscountPrice">${phoneRate.restBSipphone}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                        <input type="hidden" name="feeMode" value="分钟"/>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                                <div class="col-2"></div>
                            </div>
                        </td>
                        <!-- 隐私回拨A路 end -->

                        <!-- 云话机回拨录音 start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>云话机回拨录音</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${phoneRate.restRecording}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd><label><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="restRecordingDiscount" onkeyup="discountPrice(this);"  onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                        <input type="hidden" name="restRecordingDiscount" value="1000"/>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="restRecordingDiscountPrice">${phoneRate.restRecording}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                                <div class="col-2"></div>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td colspan="4">
                            <!-- 分割线 -->
                            <hr style="border:1px dotted;margin-top: 10px; width: 100%"/>
                            <!-- 分割线 -->
                        </td>
                    </tr>
                    <!--  云话机直拨-->
                    <tr>
                        <!--  云话机直拨start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>云话机直拨</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${phoneRate.sipcall}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd><label class=""><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="sipcallDiscount" onkeyup="discountPrice(this);" onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                        <input type="hidden" name="sipcallDiscount" value="1000"/>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="sipcallDiscountPrice">${phoneRate.sipcall}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                        <input type="hidden" name="feeMode" value="分钟"/>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                                <div class="col-2"></div>
                            </div>
                        </td>
                        <!-- 云话机直拨 end -->

                        <!-- 云话机直拨Sip Phone start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>云话机直拨Sip Phone</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${phoneRate.sipcallsip}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd><label><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="sipcallsipDiscount" onkeyup="discountPrice(this);"  onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                        <input type="hidden" name="sipcallsipDiscount" value="1000"/>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="sipcallsipDiscountPrice">${phoneRate.sipcallsip}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                                <div class="col-2"></div>
                            </div>
                        </td>
                        <!-- 云话机直拨Sip Phone end -->

                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>云话机直拨录音</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${phoneRate.sipcallRecording}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd><label class=""><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="sipcallRecordingDiscount" onkeyup="discountPrice(this);" onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                        <input type="hidden" name="sipcallRecordingDiscount" value="1000"/>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="sipcallRecordingDiscountPrice">${phoneRate.sipcallRecording}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                        <input type="hidden" name="feeMode" value="分钟"/>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                                <div class="col-2"></div>
                            </div>
                        </td>
                        <!-- 云话机直拨录音 end -->

                        <td width="25%">
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <!-- 分割线 -->
                            <hr style="border:1px dotted;margin-top: 10px; width: 100%"/>
                            <!-- 分割线 -->
                        </td>
                    </tr>
                    <tr>
                        <!-- 云话机回呼 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>云话机回呼</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${phoneRate.backcall}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd><label><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="backcallDiscount" onkeyup="discountPrice(this);"  onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                        <input type="hidden" name="backcallDiscount" value="1000"/>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="backcallDiscountPrice">${phoneRate.backcall}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                                <div class="col-2"></div>
                            </div>
                        </td>
                        <!-- 云话机回呼 end -->

                        <!-- 云话机回呼录音 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>云话机回呼录音</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${phoneRate.backcallRecording}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd><label><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="backcallRecordingDiscount" onkeyup="discountPrice(this);"  onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                        <input type="hidden" name="backcallRecordingDiscount" value="1000"/>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="backcallRecordingDiscountPrice">${phoneRate.backcallRecording}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                                <div class="col-2"></div>
                            </div>
                        </td>
                        <!-- 云话机回呼录音 end -->

                        <!--  单号码月租 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>单号码月租</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${phoneRate.numRent}</label>元</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd><label><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="numRentDiscount" onkeyup="discountPrice(this);"  onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                        <input type="hidden" name="numRentDiscount" value="1000"/>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="numRentDiscountPrice">${phoneRate.numRent}</label>元</dd>
                                    </dl>
                                    <%--<dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>--%>
                                    <dl class="cl permission-list2">
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                                <div class="col-2"></div>
                            </div>
                        </td>
                        <!--  单号码月租 end -->

                        <!--  单号码低消 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>单号码低消</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">单号码低消：</label></dt>
                                        <dd><label><input type="text" class="form-text"style="width: 100px;" maxlength="9"  datatype="f1_2-3" id="numMinCost"  name="numMinCost" onpaste="return false"  nullmsg="单号码低消不允许为空" errormsg="请输入小于1000000精度不超过2位的小数！" /></label></dd>

                                    </dl>
                                    <dl class="cl permission-list2">
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                    </dl>
                                    <dl class="cl permission-list2">
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                    </dl>
                                    <dl class="cl permission-list2">
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                    </dl>
                                    <dl class="cl permission-list2">
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                    </dl>

                                    </dd>
                                    </dl>
                                </div>
                                <div class="col-2"></div>
                            </div>
                        </td>
                        <!--  单号码低消 end -->
                    </tr>
                </table>
            </div>
        </div>

        <div class="row cl">
            <div class="col-9 col-offset-2">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset"  onclick="closeFeeRateDialog();"  value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>
