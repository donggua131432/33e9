<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/2/1
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>新增智能云费率</title>
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

            $("#form-admin-add").Validform({
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
                    },
                    "checkRelay":function(gets, obj, curform, datatype){
                        var val1 = $("#relayRent").val().trim(), val2 = $("#relayCnt").val().trim();
                        $("#relayRent").removeAttr("ignore");
                        $("#relayCnt").removeAttr("ignore");
                        if(val1 != "" && val2 == ""){
                            if(datatype["f7-2"].test(val1)){
                                $("#relayCnt").addClass("Validform_error");
                                return "请输入E1线路条数！";
                            }else{
                                return "E1线路单价请填写长度不超过7位且精度不超过4位的正数！";
                            }
                        }else if(val1 == "" && val2 != ""){
                            if(datatype["f3"].test(val2)){
                                $("#relayRent").addClass("Validform_error");
                                return "请输入E1线路单价！";
                            }else{
                                return "E1线路条数请填写大于0小于1000数字！";
                            }
                        }else if (val1 != "" && val2 != ""){
                            if(datatype["f7-2"].test(val1) && datatype["f3"].test(val2)){
                                $("#relayRent").removeClass("Validform_error");
                                $("#relayCnt").removeClass("Validform_error");
                                return true;
                            }else{
                                if(datatype["f7-2"].test(val1) == false){
                                    return "E1线路单价请填写长度不超过7位且精度不超过4位的正数！";
                                }else if(datatype["f3"].test(val2) == false){
                                    return "E1线路条数请填写大于0小于1000数字！";
                                }
                            }
                        }else{
                            $(obj).parent().parent().next().html("");
                            $("#relayRent").removeClass("Validform_error");
                            $("#relayCnt").removeClass("Validform_error");
                            $("#relayRent").attr("ignore","ignore");
                            $("#relayCnt").attr("ignore","ignore");
                        }
                    }
                }
            });
        });

        function closeFeeRateDialog(){
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }

        function calculationMoney(){
            var relayRent=$("#relayRent").val(), relayCnt=$("#relayCnt").val(), totalFee=$.calcEval(relayRent+"*"+relayCnt);
            if(isNaN(totalFee) == false){
                $("#spanTotal").text(totalFee);
            }
        }

        function discountPrice(obj){
            var discount =  numberResult($(obj).val());
            if(discount >= 0){
                if($(obj).attr("id") == "callinDiscount"){
                    $("input[name = 'callinDiscount']").val($.calcEval(discount+"*10"));
                    $("#callinDiscountPrice").text($.calcEval("${callRate.callin}*"+discount+"/100").toFixed(4));
                }else if($(obj).attr("id") == "calloutDiscount"){
                    $("input[name = 'calloutDiscount']").val($.calcEval(discount+"*10"));
                    $("#calloutDiscountPrice").text($.calcEval("${callRate.callout}*"+discount+"/100").toFixed(4));
                }
            }
        }
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value='/callRate/submitCallRate'/>" method="post" class="form form-horizontal" id="form-admin-add">
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
            <div class="formControls col-7">
                <dl class="permission-list">
                    <dt><label>呼入</label></dt>
                    <dd>
                       <dl class="cl permission-list2">
                            <dt><label class="form-label">标准价格：</label></dt>
                            <dd><label class="">${callRate.callin}</label>元/分钟</dd>
                       </dl>
                       <dl class="cl permission-list2">
                            <dt><label class="form-label">折扣率：</label></dt>
                            <dd><label class=""><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="callinDiscount" onkeyup="discountPrice(this);" onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                            <input type="hidden" name="callinDiscount" value="1000"/>
                       </dl>
                        <dl class="cl permission-list2">
                            <dt><label class="form-label">折后价：</label></dt>
                            <dd><label class="" id="callinDiscountPrice">${callRate.callin}</label>元/分钟</dd>
                        </dl>
                        <dl class="cl permission-list2">
                            <dt><label class="form-label">计费单位：</label></dt>
                            <dd><label class="">分钟</label></dd>
                            <input type="hidden" name="feeMode" value="分钟"/>
                        </dl>
                     </dd>
                </dl>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl" style>
            <label class="form-label col-2"></label>
            <div class="formControls col-7">
                <dl class="permission-list">
                    <dt><label>呼出</label></dt>
                    <dd>
                        <dl class="cl permission-list2">
                            <dt><label class="form-label">标准价格：</label></dt>
                            <dd><label class="">${callRate.callout}</label>元/分钟</dd>
                        </dl>
                        <dl class="cl permission-list2">
                            <dt><label class="form-label">折扣率：</label></dt>
                            <dd><label class=""><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="calloutDiscount" onkeyup="discountPrice(this);"  onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                            <input type="hidden" name="calloutDiscount" value="1000"/>
                        </dl>
                        <dl class="cl permission-list2">
                            <dt><label class="form-label">折后价：</label></dt>
                            <dd><label class="" id="calloutDiscountPrice">${callRate.callout}</label>元/分钟</dd>
                        </dl>
                        <dl class="cl permission-list2">
                            <dt><label class="form-label">计费单位：</label></dt>
                            <dd><label class="">分钟</label></dd>
                        </dl>
                    </dd>
                </dl>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2">专线月租费：</label>
            <div class="formControls col-7">
                <label><input type="text" class="input-text radius size-S" style="width: 90px;" maxlength="11"  id="relayRent" name="relayRent" onkeyup="calculationMoney();" autocomplete="off" onpaste="return false" datatype="checkRelay" ignore="ignore" nullmsg="请输入E1线路单价！"/>单价（元）</label>
                <label style="margin-left: 15px;"><input type="text" class="input-text radius size-S" style="width: 55px;" maxlength="3" id="relayCnt" name="relayCnt" onkeyup="calculationMoney();" autocomplete="off" onpaste="return false" datatype="checkRelay" ignore="ignore" nullmsg="请输入E1线路条数！"/>条</label>
                <label style="margin-left: 10px">总计<span id="spanTotal" class="c-red">0</span>元</label>
            </div>
            <div class="col-2"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-2">最低消费：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" style="width: 91%" name="minConsume" disabled="disabled" id="minConsume" datatype="f3-2" ignore="ignore" errormsg="请填写小于1000且精度小于2位的正数！"/>元/月
            </div>
            <div class="col-2"></div>
        </div>
       <%-- <div class="row cl">
            <label class="form-label col-2">折扣：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" style="width: 91%" name="discount" id="discount" datatype="n1-3" ignore="ignore"/>%
            </div>
            <div class="col-2"></div>
        </div>--%>
        <div class="row cl">
            <div class="col-offset-2">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset"  onclick="closeFeeRateDialog();"  value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>
