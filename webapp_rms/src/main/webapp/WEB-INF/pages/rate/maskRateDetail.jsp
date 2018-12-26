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
    <title>专号通详情</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
        $(function(){
            //模态框显示时回调事件
            $('#updateDateDialog').on('show', function (){
                if("${maskRate.forever}" == "true"){
                    $("#startDate,#endDate").attr("disabled","disabled").val("");
                    $("#checkForever").prop("checked","checked");
                    $("#forever").val(1);
                }else{
                    $("#startDate,#endDate").removeAttr("disabled");
                    $("#checkForever").removeAttr("checked");
                    $("#forever").val(0);
                }
            });

            loadDateStatus(${maskRate.forever});

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
                    "f7-3" : /^(([0-9]{1,7})|(([0-9]{1,7})[.]{1}([0-9]{0,4})?))$/,
                    "f1_2-2" : /^((3000)|((3000)[.]{1}([0]{0,1})?)|([0-2]?[0-9]{1,3})|(([0-2]?[0-9]{1,3})[.]{1}([0-9]{0,1})?))$/,
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
                    rentMatch = /^(([0-9]{1,7})|(([0-9]{1,7})[.]{1}([0-9]{0,4})?))$/,
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

            if(dialogId == 'updateMaskaDiscountDialog'){
                if($('#maskaDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateMaskbDiscountDialog'){
                if($('#maskbDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateReordingDiscountDialog'){
                if($('#recordingDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateCallBackDiscountDialog'){
                if($('#callbackDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateReordingCallBackDiscountDialog'){
                if($('#recordingCallbackDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateRentDialog'){
                if($('#rent').val().trim().match(rentMatch) == null) submitStatus = false;
            }


            if(submitStatus == true){
                $.ajax({
                    type:"post",
                    url:"<c:url value='/maskRate/udpateMaskRate'/>",
                    dataType:"json",
                    data:"feeid=${maskRate.feeid}&"+$("#"+dialogId).find('form').serialize(),
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
                $("#startEndDate").text("${maskRate.startDate} 到 ${maskRate.endDate}");
                $("#startDate,#endDate").removeAttr("disabled");
                $("#checkForever").removeAttr("checked");
                $("#forever").val(0);
            }
        }

        function maskRateDialogClose(){
            parent.location.replace(parent.location.href);
            layer_close();
        }

        function discountPrice(obj){
            var discount =  numberResult($(obj).val());
            if($(obj).attr("id") == "maskaDiscount"){
                $("input[name = 'maskaDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "maskbDiscount"){
                $("input[name = 'maskbDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "recordingDiscount"){
                $("input[name = 'recordingDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "callbackDiscount"){
                $("input[name = 'callbackDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "recordingCallbackDiscount"){
                $("input[name = 'recordingCallbackDiscount']").val($.calcEval(discount+"*10"));
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
                <span id="sid">${maskRate.userAdmin.sid}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>客户名称：</label>
            <div class="formControls col-10 form-text">
                <span id="name">${maskRate.authenCompany.name}</span>
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
                        <!-- 隐私回拨A路 start -->
                        <td width="50%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>隐私回拨A路</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">标准价格：</label></dt>
                                                <dd class="form-text"><label>${standardRate.maska}</label>元/分钟</dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折扣率：</label></dt>
                                                <dd class="form-text">
                                                    <label><span>${maskRate.maskaDiscount/10}</span>%</label>
                                                    <label style="margin-left: 15px"><a data-toggle="modal" href="#updateMaskaDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                                </dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折后价：</label></dt>
                                                <dd class="form-text"><label id="maskaDiscountPrice">${standardRate.maska*maskRate.maskaDiscount/1000}</label>元/分钟</dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">计费单位：</label></dt>
                                                <dd class="form-text"><label>${maskRate.feeMode}</label></dd>
                                            </dl>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>
                        <!-- 隐私回拨A路 end -->

                        <!-- 隐私回拨B路 start -->
                        <td width="50%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>隐私回拨B路</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">标准价格：</label></dt>
                                                <dd class="form-text"><label>${standardRate.maskb}</label>元/分钟</dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折扣率：</label></dt>
                                                <dd class="form-text">
                                                    <label><span>${maskRate.maskbDiscount/10}</span>%</label>
                                                    <label style="margin-left: 15px"><a data-toggle="modal" href="#updateMaskbDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                                </dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折后价：</label></dt>
                                                <dd class="form-text"><label><fmt:formatNumber value="${standardRate.maskb*maskRate.maskbDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">计费单位：</label></dt>
                                                <dd class="form-text"><label>${maskRate.feeMode}</label></dd>
                                            </dl>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>
                        <!-- 隐私回拨B路 end -->
                    </tr>
                    <tr>
                        <!-- 隐私回拨录音 start -->
                        <td width="50%">
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>隐私回拨录音</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">标准价格：</label></dt>
                                                <dd class="form-text"><label>${standardRate.recording}</label>元/分钟</dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折扣率：</label></dt>
                                                <dd class="form-text">
                                                    <label><span>${maskRate.recordingDiscount/10}</span>%</label>
                                                    <label style="margin-left: 15px"><a data-toggle="modal" href="#updateReordingDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                                </dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折后价：</label></dt>
                                                <dd class="form-text"><label><fmt:formatNumber value="${standardRate.recording*maskRate.recordingDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">计费单位：</label></dt>
                                                <dd class="form-text"><label>${maskRate.feeMode}</label></dd>
                                            </dl>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>
                        <!-- 隐私回拨录音 end -->
                        <td width="50%">&nbsp;</td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <!-- 分割线 -->
                            <hr style="border:1px dotted;margin-top: 10px; width: 99%"/>
                            <!-- 分割线 -->
                        </td>
                    </tr>
                    <tr>
                        <!-- 隐私回呼通话 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>隐私回呼通话</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">标准价格：</label></dt>
                                                <dd class="form-text"><label>${standardRate.callback}</label>元/分钟</dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折扣率：</label></dt>
                                                <dd class="form-text">
                                                    <label><span>${maskRate.callbackDiscount/10}</span>%</label>
                                                    <label style="margin-left: 15px"><a data-toggle="modal" href="#updateCallBackDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                                </dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折后价：</label></dt>
                                                <dd class="form-text"><label><fmt:formatNumber value="${standardRate.callback*maskRate.callbackDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">计费单位：</label></dt>
                                                <dd class="form-text"><label>${maskRate.feeMode}</label></dd>
                                            </dl>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>
                        <!-- 隐私回呼通话 end -->

                        <!-- 隐私回呼录音 start -->
                        <td>
                            <div class="row cl">
                                <div class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>隐私回呼录音</label></dt>
                                        <dd>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">标准价格：</label></dt>
                                                <dd class="form-text"><label>${standardRate.recordingCallback}</label>元/分钟</dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折扣率：</label></dt>
                                                <dd class="form-text">
                                                    <label><span>${maskRate.recordingCallbackDiscount/10}</span>%</label>
                                                    <label style="margin-left: 15px"><a data-toggle="modal" href="#updateReordingCallBackDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                                </dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折后价：</label></dt>
                                                <dd class="form-text"><label><fmt:formatNumber value="${standardRate.recordingCallback*maskRate.recordingCallbackDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">计费单位：</label></dt>
                                                <dd class="form-text"><label>${maskRate.feeMode}</label></dd>
                                            </dl>
                                        </dd>
                                    </dl>
                                </div>
                            </div>
                        </td>
                        <!-- 隐私回呼录音 end -->
                    </tr>
                </table>
            </div>
        </div>



        <div class="row cl">
            <label class="form-label col-2">号码占用费：</label>
            <div class="formControls col-10 form-text">
                <span>${maskRate.rent}</span>元/月
                <label style="margin-left: 15px"><a data-toggle="modal" href="#updateRentDialog" class="btn btn-primary size-MINI radius">修改</a></label>
            </div>
        </div>
        <div class="row cl">
            <div class="col-offset-2">
                <input class="btn btn-default radius" type="button" onclick="maskRateDialogClose();"  value="&nbsp;&nbsp;关闭&nbsp;&nbsp;"/>
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
                    <span><input type="text" readonly="readonly" value="${maskRate.startDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="startDate" name="startDate" datatype="dateCheck" style="width:186px;" class="input-text Wdate" nullmsg="请填写时间或选择永久有效"/>-
                    <input type="text" readonly="readonly" value="${maskRate.endDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})" id="endDate" name="endDate" datatype="dateCheck" style="width:186px;" class="input-text Wdate" nullmsg="请填写时间或选择永久有效"/></span>
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

<!-- 修改隐私回拨A路折扣率 -->
<div id="updateMaskaDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改隐私回拨A路折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 150px;" maxlength="6" value="${maskRate.maskaDiscount/10}" id="maskaDiscount" name="oldMaskaDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="maskaDiscount" value="${maskRate.maskaDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateMaskaDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!-- 修改隐私回拨B路折扣率 -->
<div id="updateMaskbDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改隐私回拨B路折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${maskRate.maskbDiscount/10}" id="maskbDiscount" name="oldMaskbDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="maskbDiscount" value="${maskRate.maskbDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateMaskbDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<!-- 修改隐私回拨录音扣率 -->
<div id="updateReordingDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改隐私回拨录音扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${maskRate.recordingDiscount/10}" id="recordingDiscount" name="oldRecordingDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="recordingDiscount" value="${maskRate.recordingDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateReordingDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!-- 修改隐私回呼通话扣率 -->
<div id="updateCallBackDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改隐私回呼通话扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${maskRate.callbackDiscount/10}" id="callbackDiscount" name="oldCallbackDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="callbackDiscount" value="${maskRate.callbackDiscount}"/>
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


<!-- 修改隐私回呼录音扣率 -->
<div id="updateReordingCallBackDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改隐私回呼录音扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${maskRate.recordingCallbackDiscount/10}" id="recordingCallbackDiscount" name="oldRecordingCallbackDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="recordingCallbackDiscount" value="${maskRate.recordingCallbackDiscount}"/>
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


<!-- 修改号码占用费 -->
<div id="updateRentDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改号码占用费</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-3">号码占用费：</label>
                    <div class="formControls col-4">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="10" value="${maskRate.rent}" id="rent" name="rent" onkeyup="discountPrice(this);" datatype="f7-3" onpaste="return false" errormsg="请填写正整数或者精度小于4位的小数！"/>元/月
                    </div>
                    <div class="col-5"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateMaskRate('updateRentDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>



</body>
</html>
