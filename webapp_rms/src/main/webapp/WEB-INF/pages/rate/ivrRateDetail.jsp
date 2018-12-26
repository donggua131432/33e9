<%--
  Created by IntelliJ IDEA.
  User: dukai
  Date: 2017/2/10
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>云总机详情</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
        $(function(){
            //模态框显示时回调事件
            $('#updateDateDialog').on('show', function (){
                if("${ivrRate.forever}" == "true"){
                    $("#startDate,#endDate").attr("disabled","disabled").val("");
                    $("#checkForever").prop("checked","checked");
                    $("#forever").val(1);
                }else{
                    $("#startDate,#endDate").removeAttr("disabled");
                    $("#checkForever").removeAttr("checked");
                    $("#forever").val(0);
                }
            });

            loadDateStatus(${ivrRate.forever});

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
        function updateIvrRate(dialogId){
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

            if(dialogId == 'updateCallinSipDiscountDialog'){
                if($('#callinSipDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateCallinNonsipDiscountDialog'){
                if($('#callinNonsipDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateCallinDirectDiscountDialog'){
                if($('#callinDirectDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateCallinRecordingDiscountDialog'){
                if($('#callinRecordingDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }

            if(dialogId == 'updateCalloutLocalDiscountDialog'){
                if($('#calloutLocalDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateCalloutNonlocalDiscountDialog'){
                if($('#calloutNonlocalDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateCalloutRecordingDiscountDialog'){
                if($('#calloutRecordingDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }

            if(dialogId == 'updateIvrRentDialog'){
                if($('#ivrRent').val().trim().match(numMinCostMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateSipnumRentDiscountDialog'){
                if($('#sipnumRentDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateSipnumMinCostDialog'){
                if($('#sipnumMinCost').val().trim().match(numMinCostMatch) == null) submitStatus = false;
            }



            if(submitStatus == true){
                $.ajax({
                    type:"post",
                    url:"<c:url value='/ivrRate/updateIvrRate'/>",
                    dataType:"json",
                    data:"feeid=${ivrRate.feeid}&"+$("#"+dialogId).find('form').serialize(),
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
                $("#startEndDate").text("${ivrRate.startDate} 到 ${ivrRate.endDate}");
                $("#startDate,#endDate").removeAttr("disabled");
                $("#checkForever").removeAttr("checked");
                $("#forever").val(0);
            }
        }

        function ivrRateDialogClose(){
            parent.location.replace(parent.location.href);
            layer_close();
        }

        function discountPrice(obj){
            var discount =  numberResult($(obj).val());
            if($(obj).attr("id") == "callinSipDiscount"){
                $("input[name = 'callinSipDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "callinNonsipDiscount"){
                $("input[name = 'callinNonsipDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "callinDirectDiscount"){
                $("input[name = 'callinDirectDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "callinRecordingDiscount"){
                $("input[name = 'callinRecordingDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "calloutLocalDiscount"){
                $("input[name = 'calloutLocalDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "calloutNonlocalDiscount"){
                $("input[name = 'calloutNonlocalDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "calloutRecordingDiscount"){
                $("input[name = 'calloutRecordingDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "ivrRent"){
                $("input[name = 'ivrRent']").val($.calcEval(discount));
            }else if($(obj).attr("id") == "sipnumRentDiscount"){
                $("input[name = 'sipnumRentDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "sipnumMinCost"){
                $("input[name = 'sipnumMinCost']").val($.calcEval(discount));
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
                <span id="sid">${ivrRate.userAdmin.sid}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>客户名称：</label>
            <div class="formControls col-10 form-text">
                <span id="name">${ivrRate.authenCompany.name}</span>
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
                        <!-- 呼入总机-SIP start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>呼入总机-SIP</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.callinSip}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${ivrRate.callinSipDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateCallinSipDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label id="callinSipDiscountPrice">${standardRate.callinSip*ivrRate.callinSipDiscount/1000}</label>元/分钟</dd>
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
                        <!-- 呼入总机-SIP end -->

                        <!-- 呼入总机-非SIP start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>呼入总机-非SIP</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.callinNonsip}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${ivrRate.callinNonsipDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateCallinNonsipDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.callinNonsip*ivrRate.callinNonsipDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
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
                        <!-- 呼入总机-非SIP end -->

                        <!-- 呼入-直呼 start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>呼入-直呼</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.callinDirect}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${ivrRate.callinDirectDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateCallinDirectDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label>${standardRate.callinDirect*ivrRate.callinDirectDiscount/1000}</label>元/分钟</dd>
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
                        <!-- 呼入-直呼 end -->

                        <!-- 呼入-录音 start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>呼入-录音</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.callinRecording}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${ivrRate.callinRecordingDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateCallinRecordingDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.callinRecording*ivrRate.callinRecordingDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
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
                        <!-- 呼入-录音 end -->
                    </tr>
                    <tr>
                        <!-- 呼出-市话 start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>呼出-市话</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.calloutLocal}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${ivrRate.calloutLocalDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateCalloutLocalDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.calloutLocal*ivrRate.calloutLocalDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
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
                        <!-- 呼出-市话 end -->

                        <!-- 呼出-长途 start -->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>呼出-长途</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.calloutNonlocal}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${ivrRate.calloutNonlocalDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateCalloutNonlocalDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.calloutNonlocal*ivrRate.calloutNonlocalDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
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
                        <!-- 呼出-长途 end -->

                        <!-- 呼出-录音 start-->
                        <td width="25%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>呼出-录音</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.calloutRecording}</label>元/分钟</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${ivrRate.calloutRecordingDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateCalloutRecordingDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.calloutRecording*ivrRate.calloutRecordingDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
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
                        <!-- 呼出-录音 end-->
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
                        <!--  总机号码月租 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>总机号码月租</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">月租：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${ivrRate.ivrRent}</span></label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateIvrRentDialog" class="btn btn-primary size-MINI radius">修改</a></label>
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
                            </div>
                        </td>
                        <!--  总机号码月租 end -->

                        <!-- SIP号码月租 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>SIP号码月租</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">标准价格：</label></dt>
                                        <dd class="form-text"><label>${standardRate.sipnumRent}</label>元</dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折扣率：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${ivrRate.sipnumRentDiscount/10}</span>%</label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateSipnumRentDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                        </dd>
                                    </dl>
                                    <dl class="cl permission-list2">
                                        <dt><label class="form-label">折后价：</label></dt>
                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.sipnumRent*ivrRate.sipnumRentDiscount/1000}" pattern="0.0000"/></label>元</dd>
                                    </dl>
                                    </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>
                        <!-- SIP号码月租 end -->

                        <!-- SIP号码低消 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>SIP号码低消</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                        <dt><label class="form-label">SIP号码低消：</label></dt>
                                        <dd class="form-text">
                                            <label><span>${ivrRate.sipnumMinCost}</span></label>
                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateSipnumMinCostDialog" class="btn btn-primary size-MINI radius">修改</a></label>
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
                            </div>
                        </td>
                        <!-- SIP号码低消 end -->
                    </tr>
                </table>
            </div>
        </div>

        <div class="row cl">
            <div class="col-offset-2">
                <input class="btn btn-default radius" type="button" onclick="ivrRateDialogClose();"  value="&nbsp;&nbsp;关闭&nbsp;&nbsp;"/>
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
                    <span><input type="text" readonly="readonly" value="${ivrRate.startDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="startDate" name="startDate" datatype="dateCheck" style="width:186px;" class="input-text Wdate" nullmsg="请填写时间或选择永久有效"/>-
                    <input type="text" readonly="readonly" value="${ivrRate.endDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})" id="endDate" name="endDate" datatype="dateCheck" style="width:186px;" class="input-text Wdate" nullmsg="请填写时间或选择永久有效"/></span>
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
        <button class="btn btn-primary size-M radius" onclick="updateIvrRate('updateDateDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<!-- 修改呼入总机-SI折扣率 -->
<div id="updateCallinSipDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改呼入总机-SIP折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 150px;" maxlength="6" value="${ivrRate.callinSipDiscount/10}" id="callinSipDiscount" name="oldMaskaDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="callinSipDiscount" value="${ivrRate.callinSipDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateIvrRate('updateCallinSipDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!-- 修改呼入总机-非SIP折扣率 -->
<div id="updateCallinNonsipDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改呼入总机-非SIP折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${ivrRate.callinNonsipDiscount/10}" id="callinNonsipDiscount" name="oldMaskbDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="callinNonsipDiscount" value="${ivrRate.callinNonsipDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateIvrRate('updateCallinNonsipDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!-- 修改呼入-直呼折扣率 -->
<div id="updateCallinDirectDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改呼入-直呼折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${ivrRate.callinDirectDiscount/10}" id="callinDirectDiscount" name="oldRecordingDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="callinDirectDiscount" value="${ivrRate.callinDirectDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateIvrRate('updateCallinDirectDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!-- 修改呼入-录音折扣率 -->
<div id="updateCallinRecordingDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改呼入-录音折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${ivrRate.callinRecordingDiscount/10}" id="callinRecordingDiscount" name="oldRecordingDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="callinRecordingDiscount" value="${ivrRate.callinRecordingDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateIvrRate('updateCallinRecordingDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!-- 修改呼出-市话折扣率 -->
<div id="updateCalloutLocalDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改呼出-市话折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${ivrRate.calloutLocalDiscount/10}" id="calloutLocalDiscount" name="oldRecordingDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="calloutLocalDiscount" value="${ivrRate.calloutLocalDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateIvrRate('updateCalloutLocalDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<!-- 修改呼出-长途折扣率 -->
<div id="updateCalloutNonlocalDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改呼出-长途折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${ivrRate.calloutNonlocalDiscount/10}" id="calloutNonlocalDiscount" name="oldRecordingDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="calloutNonlocalDiscount" value="${ivrRate.callinDirectDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateIvrRate('updateCalloutNonlocalDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<!-- 修改呼出-录音折扣率 -->
<div id="updateCalloutRecordingDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改呼出-录音折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${ivrRate.calloutRecordingDiscount/10}" id="calloutRecordingDiscount" name="oldRecordingDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="calloutRecordingDiscount" value="${ivrRate.calloutRecordingDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateIvrRate('updateCalloutRecordingDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!-- 总机号码月租 -->
<div id="updateIvrRentDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>总机号码月租</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-3">月租：</label>
                    <div class="formControls col-5">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="9" value="${ivrRate.ivrRent}" id="ivrRent" name="oldIvrRent"  datatype="f1_2-3" onkeyup="discountPrice(this);" onpaste="return false" errormsg="请输入小于1000000且精度不超过2位的小数！" />
                        <input type="hidden" name="ivrRent" value="${ivrRate.ivrRent}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateIvrRate('updateIvrRentDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>



<!-- 修改SIP号码月租 -->
<div id="updateSipnumRentDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改SIP号码月租</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${ivrRate.sipnumRentDiscount/10}" id="sipnumRentDiscount" name="oldSipnumRentDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="sipnumRentDiscount" value="${ivrRate.sipnumRentDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateIvrRate('updateSipnumRentDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<!-- 修改SIP号码低消 -->
<div id="updateSipnumMinCostDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改SIP号码低消</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-3">SIP号码低消：</label>
                    <div class="formControls col-5">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="9" value="${ivrRate.sipnumMinCost}" id="sipnumMinCost" name="oldSipnumMinCost"  datatype="f1_2-3" onkeyup="discountPrice(this);" onpaste="return false" errormsg="请输入小于1000000且精度不超过2位的小数！" />
                        <input type="hidden" name="sipnumMinCost" value="${ivrRate.sipnumMinCost}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateIvrRate('updateSipnumMinCostDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>




</body>
</html>
