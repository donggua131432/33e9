<%--
  Created by IntelliJ IDEA.
  User: dukai
  Date: 2017/2/10
  Time: 14:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>添加云总机费率</title>
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
                if($(obj).attr("id") == "callinSipDiscount"){
                    $("input[name = 'callinSipDiscount']").val($.calcEval(discount+"*10"));
                    $("#callinSipDiscountPrice").text($.calcEval("${ivrRate.callinSip}*"+discount+"/100").toFixed(4));
                }else if($(obj).attr("id") == "callinNonsipDiscount"){
                    $("input[name = 'callinNonsipDiscount']").val($.calcEval(discount+"*10"));
                    $("#callinNonsipDiscountPrice").text($.calcEval("${ivrRate.callinNonsip}*"+discount+"/100").toFixed(4));
                }else if($(obj).attr("id") == "callinDirectDiscount"){
                    $("input[name = 'callinDirectDiscount']").val($.calcEval(discount+"*10"));
                    $("#callinDirectDiscountPrice").text($.calcEval("${ivrRate.callinDirect}*"+discount+"/100").toFixed(4));
                }else if($(obj).attr("id") == "callinRecordingDiscount"){
                    $("input[name = 'callinRecordingDiscount']").val($.calcEval(discount+"*10"));
                    $("#callinRecordingDiscountPrice").text($.calcEval("${ivrRate.callinRecording}*"+discount+"/100").toFixed(4));
                }else if($(obj).attr("id") == "calloutLocalDiscount"){
                    $("input[name = 'calloutLocalDiscount']").val($.calcEval(discount+"*10"));
                    $("#calloutLocalDiscountPrice").text($.calcEval("${ivrRate.calloutLocal}*"+discount+"/100").toFixed(4));
                }else if($(obj).attr("id") == "calloutNonlocalDiscount"){
                    $("input[name = 'calloutNonlocalDiscount']").val($.calcEval(discount+"*10"));
                    $("#calloutNonlocalDiscountPrice").text($.calcEval("${ivrRate.calloutNonlocal}*"+discount+"/100").toFixed(4));
                }else if($(obj).attr("id") == "calloutRecordingDiscount"){
                    $("input[name = 'calloutRecordingDiscount']").val($.calcEval(discount+"*10"));
                    $("#calloutRecordingDiscountPrice").text($.calcEval("${ivrRate.calloutRecording}*"+discount+"/100").toFixed(4));
                }else if($(obj).attr("id") == "sipnumRentDiscount"){
                    $("input[name = 'sipnumRentDiscount']").val($.calcEval(discount+"*10"));
                    $("#sipnumRentDiscountPrice").text($.calcEval("${ivrRate.sipnumRent}*"+discount+"/100").toFixed(4));
                }
            }
        }

    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value='/ivrRate/submitIvrRate'/>" method="post" class="form form-horizontal" id="addMaskRateForm">
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
                        <!-- 呼入总机-SIP start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>呼入总机-SIP</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">标准价格：</label></dt>
                                                <dd class="form-text"><label>${ivrRate.callinSip}</label>元/分钟</dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折扣率：</label></dt>
                                                <dd>
                                                    <label class="">
                                                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100"
                                                         id="callinSipDiscount" onkeyup="discountPrice(this);" onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                                                    </label>
                                                </dd>
                                                <input type="hidden" name="callinSipDiscount" value="1000"/>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折后价：</label></dt>
                                                <dd class="form-text"><label id="callinSipDiscountPrice">${ivrRate.callinSip}</label>元/分钟</dd>
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
                        <!-- 呼入总机-SIP end -->

                        <!-- 呼入总机-非SIP start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>呼入总机-非SIP</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">标准价格：</label></dt>
                                                <dd class="form-text"><label>${ivrRate.callinNonsip}</label>元/分钟</dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折扣率：</label></dt>
                                                <dd><label><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="callinNonsipDiscount" onkeyup="discountPrice(this);"  onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                                <input type="hidden" name="callinNonsipDiscount" value="1000"/>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折后价：</label></dt>
                                                <dd class="form-text"><label id="callinNonsipDiscountPrice">${ivrRate.callinNonsip}</label>元/分钟</dd>
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
                        <!-- 呼入总机-非SIP end -->

                        <!-- 呼入-直呼 start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>呼入-直呼</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${ivrRate.callinDirect}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd><label class=""><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="callinDirectDiscount" onkeyup="discountPrice(this);" onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                        <input type="hidden" name="callinDirectDiscount" value="1000"/>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="callinDirectDiscountPrice">${ivrRate.callinDirect}</label>元/分钟</dd>
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
                        <!-- 呼入-直呼 end -->

                        <!-- 呼入-录音 start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>呼入-录音</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${ivrRate.callinRecording}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd><label><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="callinRecordingDiscount" onkeyup="discountPrice(this);"  onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                        <input type="hidden" name="callinRecordingDiscount" value="1000"/>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="callinRecordingDiscountPrice">${ivrRate.callinRecording}</label>元/分钟</dd>
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
                    <!-- 呼入-录音 end -->

                    <tr>
                        <td colspan="4">
                            <!-- 分割线 -->
                            <hr style="border:1px dotted;margin-top: 10px; width: 100%"/>
                            <!-- 分割线 -->
                        </td>
                    </tr>

                    <tr>
                        <!-- 呼出-市话 start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>呼出-市话</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${ivrRate.calloutLocal}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd><label class=""><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="calloutLocalDiscount" onkeyup="discountPrice(this);" onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                        <input type="hidden" name="calloutLocalDiscount" value="1000"/>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="calloutLocalDiscountPrice">${ivrRate.calloutLocal}</label>元/分钟</dd>
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
                        <!-- 呼出-市话 end -->

                        <!-- 呼出-长途 start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>呼出-长途</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${ivrRate.calloutNonlocal}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd><label><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="calloutNonlocalDiscount" onkeyup="discountPrice(this);"  onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                        <input type="hidden" name="calloutNonlocalDiscount" value="1000"/>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="calloutNonlocalDiscountPrice">${ivrRate.calloutNonlocal}</label>元/分钟</dd>
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
                        <!-- 呼出-长途 end -->

                        <!-- 呼出-录音 start-->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>呼出-录音</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${ivrRate.calloutRecording}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd><label class=""><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="calloutRecordingDiscount" onkeyup="discountPrice(this);" onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                        <input type="hidden" name="calloutRecordingDiscount" value="1000"/>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="calloutRecordingDiscountPrice">${ivrRate.calloutRecording}</label>元/分钟</dd>
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
                        <!-- 呼出-录音 end -->

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
                        <!--  总机号码月租 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>总机号码月租</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                            <dt><label class="form-label">月租：</label></dt>
                                                <dd>
                                                    <label>
                                                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="9"  datatype="f1_2-3" id="ivrRent"  name="ivrRent"
                                                               onpaste="return false" nullmsg="总机号码月租不允许为空" errormsg="请输入小于1000000精度不超过2位的小数！" />
                                                    </label>
                                                </dd>
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
                        <!--  总机号码月租 end -->



                        <!--  SIP号码月租 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>SIP号码月租</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">标准价格：</label></dt>
                                                <dd class="form-text"><label>${ivrRate.sipnumRent}</label>元</dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折扣率：</label></dt>
                                                <dd><label><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="sipnumRentDiscount" onkeyup="discountPrice(this);"  onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                                <input type="hidden" name="sipnumRentDiscount" value="1000"/>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折后价：</label></dt>
                                                <dd class="form-text"><label id="sipnumRentDiscountPrice">${ivrRate.sipnumRent}</label>元</dd>
                                            </dl>
                                        </dd>
                                    </dl>
                                </div>
                                <div class="col-2"></div>
                            </div>
                        </td>
                        <!--  SIP号码月租 end -->

                        <!--  SIP号码低消 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dt><label>SIP号码低消</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">SIP号码低消：</label></dt>
                                                <dd>
                                                    <label>
                                                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="9"  datatype="f1_2-3" id="sipnumMinCost"
                                                               name="sipnumMinCost" onpaste="return false"  nullmsg="SIP号码低消不允许为空" errormsg="请输入小于1000000精度不超过2位的小数！" />
                                                    </label>
                                                </dd>
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
                        <!--  SIP号码低消 end -->
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
