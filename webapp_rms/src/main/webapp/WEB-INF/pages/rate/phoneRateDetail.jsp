<%--
  Created by IntelliJ IDEA.
  User: dukai
  Date: 2016/6/1
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>云话机详情</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
        $(function(){
            //模态框显示时回调事件
            $('#updateDateDialog').on('show', function (){
                if("${phoneRate.forever}" == "true"){
                    $("#startDate,#endDate").attr("disabled","disabled").val("");
                    $("#checkForever").prop("checked","checked");
                    $("#forever").val(1);
                }else{
                    $("#startDate,#endDate").removeAttr("disabled");
                    $("#checkForever").removeAttr("checked");
                    $("#forever").val(0);
                }
            });

            loadDateStatus(${phoneRate.forever});

            $("#checkForever").click(function(){
                if($(this).is(":checked") == false){
                    $("#startDate,#endDate").removeAttr("disabled");
                    $(this).removeAttr("checked");
                    $("#forever").val(0);
                }else{
                    $("#startDate,#endDate").attr("disabled","disabled").val("");
                    $(this).attr("checked","checked");
                    $("#dateMsg").html('<span class="Validform_checktip Validform_right"></span>');
                    $("#forever").val(1);
                }
            })

            $("form").Validform({
                tiptype:2,
                ajaxPost:true,
                datatype: {
                    "f3" : /^([0-9]{1,3})$/,
                    "f3-2" : /^(([0-9]{1,3})|(([0-9]{1,3})[.]{1}([0-9]{0,2})?))$/,
                    "f7-3" : /^(([0-9]{1,7})|(([0-9]{1,7})[.]{1}([0-9]{0,3})?))$/,
//                    "f1_2-2" : /^((100)|((100)[.]{1}([0]{0,1})?)|([0-9]{1,2})|(([0-9]{1,2})[.]{1}([0-9]{0,1})?))$/,
                    "f1_2-2" :/^((3000)|((3000)[.]{1}([0]{0,1})?)|([0-2]?[0-9]{1,3})|(([0-2]?[0-9]{1,3})[.]{1}([0-9]{0,1})?))$/,
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
                    }
                }
            });
        });

        //修改信息
        function updateMaskRate(dialogId){
            var discountMatch = /^((3000)|((3000)[.]{1}([0]{0,1})?)|([0-2]?[0-9]{1,3})|(([0-2]?[0-9]{1,3})[.]{1}([0-9]{0,1})?))$/,
                 numMinCostMatch = /(^[0-9]{1,6})+(\.[0-9]{0,2})?$/,
//                    rentMatch = /^(([0-9]{1,7})|(([0-9]{1,7})[.]{1}([0-9]{0,3})?))$/,
                    submitStatus = true;
            if(dialogId == 'updateDateDialog'){
                if($("#checkForever").is(":checked")){
                    submitStatus = true;
                }else{
                    if($("#startDate").val() != '' &&  $("#endDate").val() != ''){
                        submitStatus = true;
                    }else{
                        submitStatus = false;
                    }
                }
            }

            if(dialogId == 'updateRestADiscountDialog'){
                if($('#restADiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateRestBDiscountDialog'){
                if($('#restBDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateRestBSipDiscountDialog'){
                if($('#restBSipphoneDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateRestRecordingDiscountDialog'){
                if($('#restRecordingDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }


            if(dialogId == 'updateSipcallDiscountDialog'){
                if($('#sipcallDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateSipcallSipDiscountDialog'){
                if($('#sipcallsipDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateSipcallReordingDiscountDialog'){
                if($('#sipcallRecordingDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }



            if(dialogId == 'updateCallBackDiscountDialog'){
                if($('#backcallDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateReordingCallBackDiscountDialog'){
                if($('#backcallRecordingDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateNumRentDiscountDialog'){

                if($('#numRentDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateNumMinCostDialog'){
                if($('#numMinCost').val().trim().match(numMinCostMatch) == null) submitStatus = false;
            }



            if(submitStatus == true){
                $.ajax({
                    type:"post",
                    url:"<c:url value='/spRate/udpatePhoneRate'/>",
                    dataType:"json",
                    data:"feeid=${phoneRate.feeid}&"+$("#"+dialogId).find('form').serialize(),
                    success : function(data) {
                        layer.msg(data.info);
                        $("#"+dialogId).modal('hide');
                        location.replace(location.href);
                    },
                    error: function(data){
                        layer.msg(data.info);
                    }
                })
            }
        }


        function loadDateStatus(obj){
            if(obj == true){
                $("#startEndDate").text("永久有效");
                $("#startDate,#endDate").attr("disabled","disabled");
                $("#checkForever").attr("checked","checked");
                $("#forever").val(1);
            }else{
                $("#startEndDate").text("${phoneRate.startDate} 到 ${phoneRate.endDate}");
                $("#startDate,#endDate").removeAttr("disabled");
                $("#checkForever").removeAttr("checked");
                $("#forever").val(0);
            }
        }

        function phoneRateDialogClose(){
            parent.location.replace(parent.location.href);
            layer_close();
        }

        function discountPrice(obj){
            var discount =  numberResult($(obj).val());
            if($(obj).attr("id") == "restADiscount"){
                $("input[name = 'restADiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "restBDiscount"){
                $("input[name = 'restBDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "restBSipphoneDiscount"){
                $("input[name = 'restBSipphoneDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "restRecordingDiscount"){
                $("input[name = 'restRecordingDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "sipcallDiscount"){
                $("input[name = 'sipcallDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "sipcallsipDiscount"){
                $("input[name = 'sipcallsipDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "sipcallRecordingDiscount"){
                $("input[name = 'sipcallRecordingDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "backcallDiscount"){
                $("input[name = 'backcallDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "backcallRecordingDiscount"){
                $("input[name = 'backcallRecordingDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "numRentDiscount"){
                $("input[name = 'numRentDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "numMinCost"){
                $("input[name = 'numMinCost']").val($.calcEval(discount));
            }
        }
    </script>
</head>
<body>
<div class="pd-20">
    <form class="form form-horizontal" id="form-admin-add">
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>Account ID：</label>
            <div class="formControls col-10 form-text">
                <span id="sid">${phoneRate.userAdmin.sid}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>客户名称：</label>
            <div class="formControls col-10 form-text">
                <span id="name">${phoneRate.authenCompany.name}</span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>有效时间：</label>
            <div class="formControls col-10 form-text">
                <label><span id="startEndDate"></span></label>
                <label style="margin-left: 15px"><a data-toggle="modal" href="#updateDateDialog" class="btn btn-primary size-MINI radius">修改</a></label>
            </div>
        </div>


        <div class="row cl">
            <label class="form-label col-2">费用项目：</label>
            <div class="formControls col-10">
                <table style="margin-top: -10px;">
                    <tr>
                        <!-- 云话机回拨A路 start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>云话机回拨A路</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.restA}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${phoneRate.restADiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateRestADiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="restADiscountPrice">${standardRate.restA*phoneRate.restADiscount/1000}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>
                        <!-- 云话机回拨A路 end -->


                        <!-- 云话机回拨B路 start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>云话机回拨B路</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.restB}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${phoneRate.restBDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateRestBDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.restB*phoneRate.restBDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>
                        <!-- 云话机回拨B路 end -->


                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>云话机回拨B路Sip Phone</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.restBSipphone}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${phoneRate.restBSipphoneDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateRestBSipDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label>${standardRate.restBSipphone*phoneRate.restBSipphoneDiscount/1000}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>


                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>云话机回拨录音</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.restRecording}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${phoneRate.restRecordingDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateRestRecordingDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.restRecording*phoneRate.restRecordingDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <!-- 云话机直拨 start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>云话机直拨</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.sipcall}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${phoneRate.sipcallDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateSipcallDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.sipcall*phoneRate.sipcallDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>
                        <!-- 云话机直拨 end -->

                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>云话机直拨Sip Phone</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.sipcallsip}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${phoneRate.sipcallsipDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateSipcallSipDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.sipcallsip*phoneRate.sipcallsipDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>

                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>云话机直拨录音</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.sipcallRecording}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${phoneRate.sipcallRecordingDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateSipcallReordingDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.sipcallRecording*phoneRate.sipcallRecordingDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>
                        <td width="25%">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="4">
                            <!-- 分割线 -->
                            <hr style="border:1px dotted;margin-top: 10px; width: 99%"/>
                            <!-- 分割线 -->
                        </td>
                    </tr>
                    <tr>
                        <!-- 云话机回呼 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>云话机回呼</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.backcall}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${phoneRate.backcallDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateCallBackDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.backcall*phoneRate.backcallDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>
                        <!-- 云话机回呼 end -->

                        <!-- 云话机回呼录音 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>云话机回呼录音</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.backcallRecording}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${phoneRate.backcallRecordingDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateReordingCallBackDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.backcallRecording*phoneRate.backcallRecordingDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>
                        <!-- 云话机回呼录音 end -->

                        <!-- 单号码月租 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>单号码月租</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.numRent}</label>元</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${phoneRate.numRentDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateNumRentDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.numRent*phoneRate.numRentDiscount/1000}" pattern="0.0000"/></label>元</dd>
                                    </dl>
<%--                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">计费单位：</label></dt>
                                        <dd class="form-text"><label>分钟</label></dd>
                                    </dl>--%>
                                    <dl class="cl permission-list2">
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>
                        <!-- 单号码月租 end -->

                        <!-- 单号码低消 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>单号码低消</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">单号码低消：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${phoneRate.numMinCost}</span></label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateNumMinCostDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
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
                            </div>
                        </td>
                        <!-- 单号码低消 end -->
                    </tr>
                </table>
            </div>
        </div>

        <div class="row cl">
            <div class="col-offset-2">
                <input class="btn btn-default radius" type="button" onclick="phoneRateDialogClose();"  value="&nbsp;&nbsp;关闭&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>

<!-- 修改日期 -->
<div id="updateDateDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改日期时间</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body" style="height:80px;">
        <div class="row cl">
            <form>
                <label class="form-label col-2"><span class="c-red">*</span>有效时间：</label>
                <div class="formControls col-10">
                    <span><input type="text" readonly="readonly" value="${phoneRate.startDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="startDate" name="startDate" datatype="dateCheck" style="width:186px;" class="input-text Wdate" nullmsg="请填写时间或选择永久有效"/>-
                    <input type="text" readonly="readonly" value="${phoneRate.endDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})" id="endDate" name="endDate" datatype="dateCheck" style="width:186px;" class="input-text Wdate" nullmsg="请填写时间或选择永久有效"/></span>
                    <div>
                        <input type="checkbox" id="checkForever" datatype="dateCheck" nullmsg="请填写时间或选择永久有效"/>
                        <label class="c-666" for="checkForever">永久有效</label>
                        <input type="hidden" id="forever" name="forever"/>
                    </div>
                </div>
                <div id="dateMsg" class="col-2"  style="width: 200px; margin-left: 75px;"></div>
            </form>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateDateDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<!-- 修改云话机回拨A路折扣率 -->
<div id="updateRestADiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改云话机回拨A路折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 150px;" maxlength="6" value="${phoneRate.restADiscount/10}" id="restADiscount" name="oldMaskaDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="restADiscount" value="${phoneRate.restADiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateRestADiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!-- 修改云话机回拨B路折扣率 -->
<div id="updateRestBDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改云话机回拨B路折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${phoneRate.restBDiscount/10}" id="restBDiscount" name="oldMaskbDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="restBDiscount" value="${phoneRate.restBDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateRestBDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!-- 修改云话机回拨B路Sip Phone折扣率 -->
<div id="updateRestBSipDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改云话机回拨B路SipPhone折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${phoneRate.restBSipphoneDiscount/10}" id="restBSipphoneDiscount" name="oldRecordingDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="restBSipphoneDiscount" value="${phoneRate.restBSipphoneDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateRestBSipDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!-- 修改云话机回拨录音折扣率 -->
<div id="updateRestRecordingDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改云话机回拨录音折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${phoneRate.restRecordingDiscount/10}" id="restRecordingDiscount" name="oldRecordingDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="restRecordingDiscount" value="${phoneRate.restRecordingDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateRestRecordingDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!-- 修改云话机直拨折扣率 -->
<div id="updateSipcallDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改云话机直拨折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${phoneRate.sipcallDiscount/10}" id="sipcallDiscount" name="oldRecordingDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="sipcallDiscount" value="${phoneRate.sipcallDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateSipcallDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<!-- 修改云话机直拨SipPhone折扣率 -->
<div id="updateSipcallSipDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改云话机直拨SipPhone折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${phoneRate.sipcallsipDiscount/10}" id="sipcallsipDiscount" name="oldRecordingDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="sipcallsipDiscount" value="${phoneRate.restBSipphoneDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateSipcallSipDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<!-- 修改云话机直拨录音折扣率 -->
<div id="updateSipcallReordingDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改云话机直拨录音折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${phoneRate.sipcallRecordingDiscount/10}" id="sipcallRecordingDiscount" name="oldRecordingDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="sipcallRecordingDiscount" value="${phoneRate.sipcallRecordingDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateSipcallReordingDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!-- 修改云话机回呼通话扣率 -->
<div id="updateCallBackDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改云话机回呼通话扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${phoneRate.backcallDiscount/10}" id="backcallDiscount" name="oldCallbackDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="backcallDiscount" value="${phoneRate.backcallDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateCallBackDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!-- 修改云话机回呼录音通话扣率 -->
<div id="updateReordingCallBackDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改云话机回呼录音通话扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${phoneRate.backcallRecordingDiscount/10}" id="backcallRecordingDiscount" name="oldRecordingCallbackDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="backcallRecordingDiscount" value="${phoneRate.backcallRecordingDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateReordingCallBackDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<!-- 修改单号码月租扣率 -->
<div id="updateNumRentDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改单号码月租扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${phoneRate.numRentDiscount/10}" id="numRentDiscount" name="oldNumRentDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="numRentDiscount" value="${phoneRate.numRentDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateNumRentDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<!-- 修改单号码低消 -->
<div id="updateNumMinCostDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改单号码低消</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-3">单号码低消：</label>
                    <div class="formControls col-5">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="9" value="${phoneRate.numMinCost}" id="numMinCost" name="oldNumMinCost"  datatype="f1_2-3" onkeyup="discountPrice(this);" onpaste="return false" errormsg="请输入小于1000000且精度不超过2位的小数！" />
                        <input type="hidden" name="numMinCost" value="${phoneRate.numMinCost}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateNumMinCostDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>




</body>
</html>
