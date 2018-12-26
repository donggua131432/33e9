<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/2/20
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>查看语音验证码费率</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function(){
            //模态框显示时回调事件
            $('#updateDateDialog').on('show', function (){
                if("${voiceVerifyRate.forever}" == "true"){
                    $("#startDate,#endDate").attr("disabled","disabled").val("");
                    $("#checkForever").prop("checked","checked");
                    $("#forever").val(1);
                }else{
                    $("#startDate,#endDate").removeAttr("disabled");
                    $("#checkForever").removeAttr("checked");
                    $("#forever").val(0);
                }
            });


            loadDateStatus(${voiceVerifyRate.forever});

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


            $("form").Validform({
                tiptype:2,
                ajaxPost:true,
                datatype: {
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
        function updateVoiceVerifyRate(dialogId){
            var discountMatch = /^((3000)|((3000)[.]{1}([0]{0,1})?)|([0-2]?[0-9]{1,3})|(([0-2]?[0-9]{1,3})[.]{1}([0-9]{0,1})?))$/,submitStatus = true;
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
            if(dialogId == 'updateVoiceaDiscountDialog'){
                if($("#per60Discount").val().match(discountMatch) == null) submitStatus = false;

            }

            if(dialogId == 'updateVoiceaDiscountDialog1'){
                if($("#perDiscount").val().match(discountMatch) == null) submitStatus = false;

            }

            if(submitStatus == true){
                $.ajax({
                    type:"post",
                    url:"<c:url value='/voiceVerifyRate/udpateVoiceVerifyRate'/>",
                    dataType:"json",
                    data:"feeid=${voiceVerifyRate.feeid}&"+$("#"+dialogId).find('form').serialize(),
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
                $("#checkForever").attr("checked",true);
                $("#forever").val(1);
            }else{
                $("#startEndDate").text("${voiceVerifyRate.startDate} 到 ${voiceVerifyRate.endDate}");
                $("#startDate,#endDate").removeAttr("disabled");
                $("#checkForever").attr("checked",false);
                $("#forever").val(0);
            }
        }

        function voiceRateDialogClose(){
            parent.location.replace(parent.location.href);
            layer_close();
        }

        function discountPrice(obj){
            var discount =  numberResult($(obj).val());
            if(discount >= 0){
                if($(obj).attr("id") == "per60Discount"){
                    $("input[name = 'per60Discount']").val($.calcEval(discount+"*10"));
                    $("#per60DiscountPrice").text($.calcEval("${standardRate.per60}*"+discount+"/100").toFixed(4));
                }
                if($(obj).attr("id") == "perDiscount"){
                    $("input[name = 'perDiscount']").val($.calcEval(discount+"*10"));
                    $("#perDiscountPrice").text($.calcEval("${standardRate.per}*"+discount+"/100").toFixed(4));
                }
            }
        }

        // 切换tab
        function switchTab(sid, hid) {
            $(sid).show().find('input').attr("disabled",false);
            $(hid).hide().find('input').attr("disabled",true);
            $("input:file").each( function() {//遍历页面里所有的input:file
                var id =this.id;
                var ignore = $("#"+id).attr("ignore")
                if(ignore){
                    $('#'+this.id+"_error").empty().hide();
                }
            });
        }
    </script>
</head>
<body>
<div class="pd-20">
    <div class="form form-horizontal">
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>Account ID：</label>
            <div class="formControls col-10 form-text">
                <span id="sid">${voiceVerifyRate.userAdmin.sid}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>客户名称：</label>
            <div class="formControls col-10 form-text">
                <span id="name">${voiceVerifyRate.authenCompany.name}</span>
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
                        <!-- 语音验证码费用 start -->
                        <td width="55%">
                            <div class="row cl">
                                <dd class="formControls col-12">
                                    <dl class="permission-list">
                                        <dt><label>语音验证码费用</label></dt>
                                        <dd>
                                            <c:if test="${voiceVerifyRate.feeMode == '00'}">
                                                <dl class="cl permission-list2">
                                                    <dt><label class="form-label">计费方式：</label></dt>
                                                    <dd>
                                                        <input type="radio" id="acountType2" name="feeMode" value="00" onclick="switchTab('#pattern_one', '#pattern_two');" checked="checked"><label for="acountType2">按分钟计费</label>
                                                        <input type="radio" id="acountType1" name="feeMode" value="01" onclick="switchTab('#pattern_two', '#pattern_one');"><label for="acountType1">按条计费</label>
                                                    </dd>
                                                </dl>
                                                <dd id="pattern_one">
                                                    <dl class="cl permission-list2">
                                                        <dt><label class="form-label">标准价格：</label></dt>
                                                        <dd class="form-text"><label>${standardRate.per60}</label>元/分钟</dd>
                                                    </dl>
                                                    <dl class="cl permission-list2">
                                                        <dt><label class="form-label">折扣率：</label></dt>
                                                        <dd class="form-text">
                                                            <label><span>${voiceVerifyRate.per60Discount/10}</span>%</label>
                                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateVoiceaDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                                        </dd>
                                                    </dl>
                                                    <dl class="cl permission-list2">
                                                        <dt><label class="form-label">折后价：</label></dt>
                                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.per60*voiceVerifyRate.per60Discount/1000}" pattern="0.0000"/></label>元/分钟</dd>
                                                    </dl>
                                                </dd>
                                                <dd id="pattern_two" style="display: none;">
                                                    <dl class="cl permission-list2">
                                                        <dt><label class="form-label">标准价格：</label></dt>
                                                        <dd class="form-text"><label>${standardRate.per}</label>元/条</dd>
                                                    </dl>
                                                    <dl class="cl permission-list2">
                                                        <dt><label class="form-label">折扣率：</label></dt>
                                                        <dd class="form-text">
                                                            <label><span>${standardRate.perDiscount/10}</span>%</label>
                                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateVoiceaDiscountDialog1" class="btn btn-primary size-MINI radius">修改</a></label>
                                                        </dd>
                                                    </dl>
                                                    <dl class="cl permission-list2">
                                                        <dt><label class="form-label">折后价：</label></dt>
                                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.per*standardRate.perDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
                                                    </dl>
                                                </dd>
                                            </c:if>
                                            <c:if test="${voiceVerifyRate.feeMode == '01'}">
                                                <dl class="cl permission-list2">
                                                    <dt><label class="form-label">计费方式：</label></dt>
                                                    <dd>
                                                        <input type="radio" id="acountType3" name="feeMode" value="00" onclick="switchTab('#pattern_one', '#pattern_two');"><label for="acountType3">按分钟计费</label>
                                                        <input type="radio" id="acountType4" name="feeMode" value="01" onclick="switchTab('#pattern_two', '#pattern_one');"checked="checked"><label for="acountType4">按条计费</label>
                                                    </dd>
                                                </dl>
                                                <dd id="pattern_one" style="display: none;">
                                                    <dl class="cl permission-list2">
                                                    <dt><label class="form-label">标准价格：</label></dt>
                                                    <dd class="form-text"><label>${standardRate.per60}</label>元/分钟</dd>
                                                    </dl>
                                                    <dl class="cl permission-list2">
                                                        <dt><label class="form-label">折扣率：</label></dt>
                                                        <dd class="form-text">
                                                            <label><span>${standardRate.per60Discount/10}</span>%</label>
                                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateVoiceaDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                                                        </dd>
                                                    </dl>
                                                    <dl class="cl permission-list2">
                                                        <dt><label class="form-label">折后价：</label></dt>
                                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.per60*standardRate.per60Discount/1000}" pattern="0.0000"/></label>元/分钟</dd>
                                                    </dl>
                                                    </dd>
                                                <dd id="pattern_two">
                                                    <dl class="cl permission-list2">
                                                    <dt><label class="form-label">标准价格：</label></dt>
                                                    <dd class="form-text"><label>${standardRate.per}</label>元/条</dd>
                                                    </dl>
                                                    <dl class="cl permission-list2">
                                                        <dt><label class="form-label">折扣率：</label></dt>
                                                        <dd class="form-text">
                                                            <label><span>${voiceVerifyRate.perDiscount/10}</span>%</label>
                                                            <label style="margin-left: 15px"><a data-toggle="modal" href="#updateVoiceaDiscountDialog1" class="btn btn-primary size-MINI radius">修改</a></label>
                                                        </dd>
                                                    </dl>
                                                    <dl class="cl permission-list2">
                                                        <dt><label class="form-label">折后价：</label></dt>
                                                        <dd class="form-text"><label><fmt:formatNumber value="${standardRate.per*voiceVerifyRate.perDiscount/1000}" pattern="0.0000"/></label>元/分钟</dd>
                                                    </dl>
                                                </dd>
                                            </c:if>
                                        </dd>
                                    </dl>
                                </div>
                        </td>
                        <!-- 语音通知费用 end -->

                        <td width="50%">&nbsp;</td>
                    </tr>
                </table>
            </div>
        </div>


        <div class="row cl">
            <div class="col-offset-2">
                <input class="btn btn-default radius" type="button" onclick="voiceRateDialogClose();"  value="&nbsp;&nbsp;关闭&nbsp;&nbsp;"/>
            </div>
        </div>
    </div>
</div>

<!- 修改日期 -->
<div id="updateDateDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改日期时间</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <div class="row cl">
            <form>
                <label class="form-label col-2"><span class="c-red">*</span>有效时间：</label>
                <div class="formControls col-10">
                    <span><input type="text" readonly="readonly" value="${voiceVerifyRate.startDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="startDate" name="startDate" style="width:186px;" class="input-text Wdate" nullmsg="请填写时间或选择永久有效"/>-
                    <input type="text" readonly="readonly" value="${voiceVerifyRate.endDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})" id="endDate" name="endDate" style="width:186px;" class="input-text Wdate" nullmsg="请填写时间或选择永久有效"/></span>
                    <div>
                        <input type="checkbox" id="checkForever" datatype="dateCheck" nullmsg="请填写时间或选择永久有效"/>
                        <label class="c-666" for="checkForever">永久有效</label>
                        <input type="hidden" id="forever" name="forever"/>
                        <input type="hidden" id="updateDateFlag" name="updateDateFlag" value="1"/>
                    </div>
                </div>
                <div id="dateMsg" class="col-2"  style="width: 200px; margin-left: 75px;"></div>
            </form>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" id="updateDateSubmit" onclick="updateVoiceVerifyRate('updateDateDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<!- 修改语音验证码费用折扣率 -->
<div id="updateVoiceaDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改语音验证码费用折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <c:if test="${voiceVerifyRate.feeMode == '00'}">
                            <input type="text" id="per60Discount" name="oldPer60Discount" value="${voiceVerifyRate.per60Discount/10}" class="input-text radius size-S" style="width: 150px;" maxlength="6" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false"  errormsg="请输入小于等于3000且精度不超过两位的小数！"/>%
                            <input type="hidden" name="per60Discount" value="${voiceVerifyRate.per60Discount}"/>
                        </c:if>
                        <c:if test="${voiceVerifyRate.feeMode == '01'}">
                            <input type="text" id="per60Discount" name="oldPer60Discount" value="${standardRate.per60Discount/10}" class="input-text radius size-S" style="width: 150px;" maxlength="6" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false"  errormsg="请输入小于等于3000且精度不超过两位的小数！"/>%
                            <input type="hidden" name="per60Discount" value="${standardRate.per60Discount}"/>
                        </c:if>
                            <input type="hidden" name="feeMode" value="00"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateVoiceVerifyRate('updateVoiceaDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<!- 修改语音验证码费用折扣率 换计费模式 -->
<div id="updateVoiceaDiscountDialog1" class="modal hide fade">
    <div class="modal-header">
        <h4>修改语音验证码费用折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <c:if test="${voiceVerifyRate.feeMode == '00'}">
                            <input type="text" id="perDiscount" name="oldPerDiscount" value="${standardRate.perDiscount/10}" class="input-text radius size-S" style="width: 150px;" maxlength="6" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false"  errormsg="请输入小于等于3000且精度不超过两位的小数！"/>%
                            <input type="hidden" name="perDiscount" value="${standardRate.perDiscount}"/>
                        </c:if>
                        <c:if test="${voiceVerifyRate.feeMode == '01'}">
                            <input type="text" id="perDiscount" name="oldPerDiscount" value="${voiceVerifyRate.perDiscount/10}" class="input-text radius size-S" style="width: 150px;" maxlength="6" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false"  errormsg="请输入小于等于3000且精度不超过两位的小数！"/>%
                            <input type="hidden" name="perDiscount" value="${voiceVerifyRate.perDiscount}"/>
                        </c:if>
                        <input type="hidden" name="feeMode" value="01"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateVoiceVerifyRate('updateVoiceaDiscountDialog1')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

</body>
</html>
