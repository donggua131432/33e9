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
    <title>智能云费率</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function(){
            //模态框显示时回调事件
            $('#updateDateDialog').on('show', function (){
                if("${callRate.forever}" == "true"){
                    $("#startDate,#endDate").attr("disabled","disabled").val("");
                    $("#checkForever").prop("checked","checked");
                    $("#forever").val(1);
                }else{
                    $("#startDate,#endDate").removeAttr("disabled");
                    $("#checkForever").removeAttr("checked");
                    $("#forever").val(0);
                }
            });

            loadDateStatus(${callRate.forever});

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
                    "f7-2" : /^(([0-9]{1,7})|(([0-9]{1,7})[.]{1}([0-9]{0,4})?))$/,
                    "f3-2" : /^(([0-9]{1,3})|(([0-9]{1,3})[.]{1}([0-9]{0,2})?))$/,
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
                    },
                    "checkRelay":function(gets, obj, curform, datatype){
                        var val1 = $("#relayRent").val().trim();
                        $("#relayRent").removeAttr("ignore");
                        if(val1 != ""){
                            if(datatype["f7-2"].test(val1)){
                                $("#relayRent").removeClass("Validform_error");
                                return true;
                            }else{
                                return "E1线路单价请填写长度不超过7位且精度不超过4位的正数！";
                            }
                        }else{
                            $(obj).parent().parent().next().html("");
                            $("#relayRent").removeClass("Validform_error");
                            $("#relayRent").attr("ignore","ignore");
                        }
                    }
                }
            });
        });

        //修改信息
        function updateCallRate(dialogId){
            var  fThree = /^([0-9]{1,3})$/,relayRentMath=/^(([0-9]{1,7})|(([0-9]{1,7})[.]{1}([0-9]{0,4})?))$/;
                    discountMatch = /^((3000)|((3000)[.]{1}([0]{0,1})?)|([0-2]?[0-9]{1,3})|(([0-2]?[0-9]{1,3})[.]{1}([0-9]{0,1})?))$/, submitStatus = true;
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

            if(dialogId == 'updateCallinDiscountDialog'){
                if($('#callinDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateCalloutDiscountDialog'){
                if($('#calloutDiscount').val().trim().match(discountMatch) == null) submitStatus = false;
            }
            if(dialogId == 'updateMonthlyDialog'){
                if($('#relayRent').val().trim().match(relayRentMath) == null) submitStatus = false;
                if($('#relayCnt').val().trim().match(fThree) == null) submitStatus = false;
                if($('#relayCnt').val().trim() > 999 || $('#relayCnt').val().trim() < 0){
                    $("#relayCntMg").html('<span class="Validform_checktip Validform_wrong">线路条数为0-999条！</span>');
                    submitStatus = false;
                }
            }

            if(submitStatus == true){
               $.ajax({
                   type:"post",
                   url:"<c:url value='/callRate/udpateCallRate'/>",
                   dataType:"json",
                   data:"feeid=${callRate.feeid}&"+$("#"+dialogId).find('form').serialize(),
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
                $("#startEndDate").text("${callRate.startDate} 到 ${callRate.endDate}");
                $("#startDate,#endDate").removeAttr("disabled");
                $("#checkForever").removeAttr("checked");
                $("#forever").val(0);
            }
        }

        function callRateDialogClose(){
            parent.location.replace(parent.location.href);
            layer_close();
        }

        function discountPrice(obj){
            var discount =  numberResult($(obj).val());
            if($(obj).attr("id") == "callinDiscount"){
                $("input[name = 'callinDiscount']").val($.calcEval(discount+"*10"));
            }else if($(obj).attr("id") == "calloutDiscount"){
                $("input[name = 'calloutDiscount']").val($.calcEval(discount+"*10"));
            }
        }

        function relayCntValue(){
            var oldRelayCnt =  numberResult($("#oldRelayCnt").val()),
                 changeRelayCnt = $("#changeRelayCnt").val().trim(),
                 newRelayCnt = numberResult($("#newRelayCnt").val());

            //只允许输入数字
            $("#newRelayCnt").val($("#newRelayCnt").val().replace(/[^\d]/g, ''));

            if(changeRelayCnt == "add"){
                if($.calcEval(oldRelayCnt+"+"+newRelayCnt) > 999){
                    $("#newRelayCnt").val("");
                    $("#relayCnt").val(oldRelayCnt);
                    $("#relayCntMg").html('<span class="Validform_checktip Validform_wrong">线路条数不能超过999条！</span>');
                }else{
                    $("#relayCnt").val($.calcEval(oldRelayCnt+"+"+newRelayCnt));
                }
            }else if(changeRelayCnt == "sub"){
                if($.calcEval(oldRelayCnt+"-"+newRelayCnt) < 0){
                    $("#newRelayCnt").val("");
                    $("#relayCnt").val(oldRelayCnt);
                    $("#relayCntMg").html('<span class="Validform_checktip Validform_wrong">线路条数不能低于0条！</span>');
                }else{
                    $("#relayCnt").val($.calcEval(oldRelayCnt+"-"+newRelayCnt));
                }
            }
        }

    </script>
</head>
<body>
<div class="pd-20">
    <form class="form form-horizontal" id="form-admin-add">
        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>Account ID：</label>
            <div class="formControls col-8">
                <span id="sid">${callRate.userAdmin.sid}</span>
            </div>
            <div class="col-1"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>客户名称：</label>
            <div class="formControls col-8">
                <span id="name">${callRate.authenCompany.name}</span>
            </div>
            <div class="col-1"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>有效时间：</label>
            <div class="formControls col-8">
                <label><span id="startEndDate"></span></label>
                <label style="margin-left: 15px"><a data-toggle="modal" href="#updateDateDialog" class="btn btn-primary size-MINI radius">修改</a></label>
            </div>
            <div class="col-1"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">费用项目：</label>
            <div class="formControls col-8">
                <dl class="permission-list">
                    <dt><label>呼入</label></dt>
                    <dd>
                       <dl class="cl permission-list2">
                            <dt><label class="form-label">标准价格：</label></dt>
                            <dd><label class="">${standardRate.callin}</label>元/分钟</dd>
                       </dl>
                       <dl class="cl permission-list2">
                            <dt><label class="form-label">折扣率：</label></dt>
                            <dd>
                                <label class=""><span>${callRate.callinDiscount/10}</span>%</label>
                                <label style="margin-left: 15px"><a data-toggle="modal" href="#updateCallinDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                            </dd>
                       </dl>
                        <dl class="cl permission-list2">
                            <dt><label class="form-label">折后价：</label></dt>
                            <dd><label class=""><span id="callInDiscountPrice"><fmt:formatNumber value="${standardRate.callin*callRate.callinDiscount/1000}" pattern="0.0000"/></span></label>元/分钟</dd>
                        </dl>
                        <dl class="cl permission-list2">
                            <dt><label class="form-label">计费单位：</label></dt>
                            <dd><label class="">${callRate.feeMode}</label></dd>
                        </dl>
                     </dd>
                </dl>
            </div>
            <div class="col-1"></div>
        </div>


        <div class="row cl" style>
            <label class="form-label col-3"></label>
            <div class="formControls col-8">
                <dl class="permission-list">
                    <dt><label>呼出</label></dt>
                    <dd>
                        <dl class="cl permission-list2">
                            <dt><label class="form-label">标准价格：</label></dt>
                            <dd><label class="">${standardRate.callout}</label>元/分钟</dd>
                        </dl>
                        <dl class="cl permission-list2">
                            <dt><label class="form-label">折扣率：</label></dt>
                            <dd>
                                <label class=""><span>${callRate.calloutDiscount/10}</span>%</label>
                                <label style="margin-left: 15px"><a data-toggle="modal" href="#updateCalloutDiscountDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                            </dd>
                        </dl>
                        <dl class="cl permission-list2">
                            <dt><label class="form-label">折后价：</label></dt>
                            <dd><label class=""><span id="callOutDiscountPrice"><fmt:formatNumber value="${standardRate.callout*callRate.calloutDiscount/1000}" pattern="0.0000"/></span></label>元/分钟</dd>
                        </dl>
                        <dl class="cl permission-list2">
                            <dt><label class="form-label">计费单位：</label></dt>
                            <dd><label class="">${callRate.feeMode}</label></dd>
                        </dl>
                    </dd>
                </dl>
            </div>
            <div class="col-1"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">专线月租费：</label>
            <div class="formControls col-8">
                <label><span>${callRate.relayRent}</span>单价（元）</label>
                <label style="margin-left: 15px;"><span>${callRate.relayCnt}</span>条</label>
                <label style="margin-left: 15px"><a data-toggle="modal" href="#updateMonthlyDialog" class="btn btn-primary size-MINI radius">修改</a></label>
                <label style="margin-left: 10px">共计<span class="c-red">${callRate.relayRent*callRate.relayCnt}</span>元</label>
            </div>
            <div class="col-1"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">最低消费：</label>
            <div class="formControls col-8"><span id="minConsume">${callRate.minConsume}</span>元/月</div>
            <div class="col-1"></div>
        </div>
        <div class="row cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-default radius" type="button" onclick="callRateDialogClose();"  value="&nbsp;&nbsp;关闭&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>

<!- 修改日期 -->
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
                    <span><input type="text" readonly="readonly" value="${callRate.startDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="startDate" name="startDate" datatype="dateCheck" style="width:186px;" class="input-text Wdate" nullmsg="请填写时间或选择永久有效"/>-
                    <input type="text" readonly="readonly" value="${callRate.endDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})" id="endDate" name="endDate" datatype="dateCheck" style="width:186px;" class="input-text Wdate" nullmsg="请填写时间或选择永久有效"/></span>
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
        <button class="btn btn-primary size-M radius" onclick="updateCallRate('updateDateDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>

<!- 修改呼入折扣率 -->
<div id="updateCallinDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改呼入折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 150px;" maxlength="6" value="${callRate.callinDiscount/10}" id="callinDiscount" name="oldCallinDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="callinDiscount" value="${callRate.callinDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateCallRate('updateCallinDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!- 修改呼出折扣率 -->
<div id="updateCalloutDiscountDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改呼出折扣率</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">折扣率：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" value="${callRate.calloutDiscount/10}" id="calloutDiscount" name="oldCalloutDiscount" onkeyup="discountPrice(this);" datatype="f1_2-2" onpaste="return false" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%
                        <input type="hidden" name="calloutDiscount" value="${callRate.calloutDiscount}"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateCallRate('updateCalloutDiscountDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!- 修改专线月租费 -->
<div id="updateMonthlyDialog" class="modal hide fade" data-width="600px">
    <div class="modal-header">
        <h4>修改专线月租费</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">专线月租费：</label>
                    <div class="formControls col-6">
                        <label><input type="text" class="input-text radius size-S" style="width: 90px;" maxlength="11" value="${callRate.relayRent}" id="relayRent" name="relayRent" autocomplete="off" onpaste="return false" datatype="checkRelay" ignore="ignore" nullmsg="请输入E1线路单价！"/>单价（元）</label>
                    </div>
                    <div class="col-4" id="relayRentMg"></div>
                </div>
                <div class="row cl">
                    <label class="form-label col-2">当前线路为</label>
                    <div class="formControls col-6">
                        <label><input type="text" class="input-text radius size-S" id="oldRelayCnt" name="oldRelayCnt" style="width: 50px;" readonly value="${callRate.relayCnt}"/>条</label>
                        <label style="margin-left: 15px;">
                            <select class="select" style="width: 60px; height: 26px;" id="changeRelayCnt" onchange="relayCntValue()">
                                <option value="add" selected>新增</option>
                                <option value="sub">减去</option>
                            </select>
                        </label>
                        <label style="margin-left: 15px;"><input type="text" class="input-text radius size-S" style="width: 50px;" maxlength="3" id="newRelayCnt" datatype="f3" onkeyup="relayCntValue();" nullmsg="请输入条数" errormsg="请输入0-999的数字！"/>条</label>
                        <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次日00:00:00安排扣费，请确认修改</span>
                        <input type="hidden" id="relayCnt" name="relayCnt" value="${callRate.relayCnt}"/>
                    </div>
                    <div class="col-4" id="relayCntMg"></div>
                </div>
                <div class="row cl">

                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateCallRate('updateMonthlyDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>



</body>
</html>
