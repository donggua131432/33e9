 <%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>新增子账号</title>
</head>
<script type="text/javascript">
    var isSubmit = false;
    var maxCon = 0;
    $(function(){

        maxCon = $('#relayNum option:checked').attr("data-max") || 0;

        selectCalledType('${calledTypes}');

        selectCalledLimit('${calledLimits}');

        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });

        $("#form-member-add").Validform({
            tiptype:2,
            ajaxPost:true,
            datatype: {
                "subName" : /^[\u4E00-\u9FA5-a-zA-Z0-9]{2,30}$/,
                "f1_2-2" : /^((10000)|((10000)[.]{1}([0]{0,1})?)|([0-9]{1,4})|(([0-9]{1,4})[.]{1}([0-9]{0,1})?))$/,
                "maxCon" : function(gets, obj, curform, datatype){
                    if (!gets) {
                        return true;
                    }
                    if (!$("#relayNum").val()) {
                        return "请先选择中继";
                    }
                    if (!/^(0|[1-9][0-9]*)$/.test(gets)) {
                        return "请填写大于等于0的整数";
                    }
                    if (parseInt(gets) > parseInt(maxCon)) {
                        return "最大并发数不应大于中继的最大并发数" + maxCon;
                    }
                    return true;
                }
            },
            beforeSubmit:function(curform){
                //在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
                //这里明确return false的话表单将不会提交;
                if (isSubmit) {
                    return false;
                }
                isSubmit = true;

                return true;
            },
            callback:function(data){
                $.Showmsg(data.msg);
                setTimeout(function(){
                    $.Hidemsg();
                    if(data.code == "ok"){
                        //刷新父级页面
                        parent.location.replace(parent.location.href);
                        //关闭当前窗口
                        layer_close();
                    } else {
                        isSubmit = false;
                    }
                },2000);
            }
        });

        $("input[name='cycle']").on('ifChecked', function(e){
            var discount =  numberResult($('#discount').val());
            if (this.value == 6) {
                $("#ratePer").text('${sipRate.per6}' + '元');
                $("#discountPrice").text($.calcEval("${sipRate.per6}*"+discount+"/100").toFixed(4) + "元");
            }
            if (this.value == 60) {
                $("#ratePer").text('${sipRate.per60}' + '元');
                $("#discountPrice").text($.calcEval("${sipRate.per60}*"+discount+"/100").toFixed(4) + "元");
            }
        });

        // 中继选择发生变化是
        $("#relayNum").on('change', function(e){
            maxCon = $('option:checked', this).attr("data-max") || 0;


            $.ajax({
                type: "POST",
                url: "<c:url value='/sip/getSipBasic'/>",
                data: {"relayNum" : $("#relayNum option:selected").val()},
                success: function(data){
                    $("#ip1").text(data.sipBasic==""||data.sipBasic.ipport1==""?"":data.sipBasic.ipport1.split(":")[0]);
                    $("#port1").text(data.sipBasic==""||data.sipBasic.ipport1==""?"":data.sipBasic.ipport1.split(":")[1]);
                    $("#ip2").text(data.sipBasic==""||data.sipBasic.ipport2==""?"":data.sipBasic.ipport2.split(":")[0]);
                    $("#port2").text(data.sipBasic==""||data.sipBasic.ipport2==""?"":data.sipBasic.ipport2.split(":")[1]);
                    $("#ip3").text(data.sipBasic==""||data.sipBasic.ipport3==""?"":data.sipBasic.ipport3.split(":")[0]);
                    $("#port3").text(data.sipBasic==""||data.sipBasic.ipport3==""?"":data.sipBasic.ipport3.split(":")[1]);
                    $("#ip4").text(data.sipBasic==""||data.sipBasic.ipport4==""?"":data.sipBasic.ipport4.split(":")[0]);
                    $("#port4").text(data.sipBasic==""||data.sipBasic.ipport4==""?"":data.sipBasic.ipport4.split(":")[1]);
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("请求失败");
                }
            });
        });

    });

    // 费率
    function discountPrice(obj){
        var discount =  numberResult($(obj).val());
        var cycle = $("input[name='cycle']:checked").val();
        var per = '${sipRate.per6}';
        if (cycle == 6) {
            per = '${sipRate.per6}';
        }
        if (cycle == 60) {
            per = '${sipRate.per60}';
        }
        $("#discountPrice").text($.calcEval(per + "*"+discount+"/100").toFixed(4) + "元");
    }

    // 初选 被叫号码类型
    function selectCalledType(arr) {
        if (arr) {
            var arrs = arr.split(",");
            $("input[name='calledType']").val(arrs);
        }
    }


    // 初选 被叫号码限制
    function selectCalledLimit(arr) {
        if (arr) {
            var arrs = arr.split(",");
            $("input[name='calledLimit']").val(arrs);
        }
    }
</script>
<body>
<div class="pd-20">
    <form action="<c:url value='/sip/editSub'/>" method="post" class="form form-horizontal" id="form-member-add">

        <input type="hidden" id="appid" name="appid" value="${appid}"/>
        <input type="hidden" id="sid" name="sid" value="${sid}"/>
        <input type="hidden" id="subid" name="subid" value="${relayInfo.subid}"/>
        <input type="hidden" id="oldRelayNum" name="oldRelayNum" value="${relayInfo.relayNum}"/>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>子账号名称：</label>
            <div class="formControls col-6">
                <input type="text" id="subName" name="subName" value="${relayInfo.subName}" datatype="subName" maxlength="30" class="input-text" nullmsg="请输入子账号名称" errormsg="子账号名称格式不正确">
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>中继名称：</label>
            <div class="formControls col-6">
                <select id="relayNum" name="relayNum" style="width: 370px;height: 31px;" datatype="*" nullmsg="请选择中继">
                    <option value="">请选择中继</option>
                    <c:forEach items="${relays}" var="relay">
                        <c:if test="${relay.relayNum eq relayInfo.relayNum}">
                            <option data-max="${relay.maxConcurrent}" value="${relay.relayNum}" selected>${relay.relayName}</option>
                        </c:if>
                        <c:if test="${relay.relayNum ne relayInfo.relayNum}">
                            <option data-max="${relay.maxConcurrent}" value="${relay.relayNum}">${relay.relayName}</option>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>选择开关：</label>
            <div class="formControls col-6">
                <div class="skin-minimal">
                    <div class="check-box">
                        <c:if test="${relayInfo.numFlag eq '01'}">
                            <input type="radio" name="numFlag" value="01" id="numFlag01" datatype="*" nullmsg="请选择开关" checked>
                        </c:if>
                        <c:if test="${relayInfo.numFlag ne '01'}">
                            <input type="radio" name="numFlag" value="01" id="numFlag01" datatype="*" nullmsg="请选择开关">
                        </c:if>
                        <label for="numFlag00">打开</label>
                    </div>
                    <div class="check-box">
                        <c:if test="${relayInfo.numFlag eq '00'}">
                            <input type="radio" name="numFlag" value="00" id="numFlag00" checked>
                        </c:if>
                        <c:if test="${relayInfo.numFlag ne '00'}">
                            <input type="radio" name="numFlag" value="00" id="numFlag00">
                        </c:if>
                        <label for="numFlag01">关闭</label>
                    </div>
                </div>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>被叫号码类型：</label>
            <div class="formControls col-6">
                <div class="skin-minimal">
                    <div class="check-box">
                        <input type="checkbox" name="calledType" value="0" id="calledType_0" datatype="*" nullmsg="请选择被叫号码类型">
                        <label for="calledType_0">电信</label>
                    </div>
                    <div class="check-box">
                        <input type="checkbox" name="calledType" value="1" id="calledType_1">
                        <label for="calledType_1">移动</label>
                    </div>
                    <div class="check-box">
                        <input type="checkbox" name="calledType" value="2" id="calledType_2">
                        <label for="calledType_2">联通</label>
                    </div>
                    <div class="check-box">
                        <input type="checkbox" name="calledType" value="3" id="calledType_3">
                        <label for="calledType_3">其他</label>
                    </div>
                </div>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>被叫号码限制：</label>
            <div class="formControls col-7">
                <div class="skin-minimal">
                    <div class="check-box">
                        <input type="checkbox" name="calledLimit" value="0" id="calledLimit_0" datatype="*"  nullmsg="请选择被叫号码限制">
                        <label for="calledLimit_0">手机</label>
                    </div>
                    <div class="check-box">
                        <input type="checkbox" name="calledLimit" value="1" id="calledLimit_1">
                        <label for="calledLimit_1">固话</label>
                    </div>
                    <div class="check-box">
                        <input type="checkbox" name="calledLimit" value="2" id="calledLimit_2">
                        <label for="calledLimit_2">400</label>
                    </div>
                    <div class="check-box">
                        <input type="checkbox" name="calledLimit" value="3" id="calledLimit_3">
                        <label for="calledLimit_3">95XX</label>
                    </div>
                    <div class="check-box">
                        <input type="checkbox" name="calledLimit" value="4" id="calledLimit_4">
                        <label for="calledLimit_4">其他</label>
                    </div>
                </div>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">最大并发数：</label>
            <div class="formControls col-6">
                <input id="maxConcurrent" name="maxConcurrent" datatype="maxCon" class="input-text" value="${relayInfo.maxConcurrent}"/>
            </div>
            <div class="col-3"></div>
        </div>

        <h4 class="page_title">中继信息:</h4>
        <div id="IP_Port">
        <div class="row cl">
            <label class="form-label col-3">外域对端IP：</label>
            <div class="formControls col-2">
                <label id="ip1">${fn:substringBefore(sipBasic.ipport1, ":")} </label>
            </div>
            <label class="form-label col-1">端口：</label>
            <div class="formControls col-2">
                <label id="port1">${fn:substringAfter(sipBasic.ipport1, ":")} </label>
            </div>
            <div class="formControls col-3"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">外域本端IP：</label>
            <div class="formControls col-2">
                <label id="ip2">${fn:substringBefore(sipBasic.ipport2, ":")} </label>
            </div>
            <label class="form-label col-1">端口：</label>
            <div class="formControls col-2">
                <label id="port2">${fn:substringAfter(sipBasic.ipport2, ":")} </label>
            </div>
            <div class="formControls col-3"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">内域对端IP：</label>
            <div class="formControls col-2">
                <label id="ip3">${fn:substringBefore(sipBasic.ipport3, ":")} </label>
            </div>
            <label class="form-label col-1">端口：</label>
            <div class="formControls col-2">
                <label id="port3">${fn:substringAfter(sipBasic.ipport3, ":")} </label>
            </div>
            <div class="formControls col-3"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">内域本端IP：</label>
            <div class="formControls col-2">
                <label id="ip4">${fn:substringBefore(sipBasic.ipport4, ":")} </label>
            </div>
            <label class="form-label col-1">端口：</label>
            <div class="formControls col-2">
                <label id="port4">${fn:substringAfter(sipBasic.ipport4, ":")} </label>
            </div>
            <div class="formControls col-3"></div>
        </div>
        </div>

        <h4 class="page_title">费率配置</h4>

        <div class="row cl">
            <label class="form-label col-3">计费单位：</label>
            <div class="formControls col-6">
                <div class="skin-minimal">
                    <div class="check-box">
                        <c:if test="${rate.cycle eq 60}">
                            <input type="radio" name="cycle" value="60" id="cycle_60" checked="checked">
                        </c:if>
                        <c:if test="${rate.cycle ne 60}">
                            <input type="radio" name="cycle" value="60" id="cycle_60">
                        </c:if>
                        <label for="cycle_60">分钟</label>
                    </div>
                    <div class="check-box">
                        <c:if test="${rate.cycle eq 6}">
                            <input type="radio" name="cycle" value="6" id="cycle_6" checked="checked">
                        </c:if>
                        <c:if test="${rate.cycle ne 6}">
                            <input type="radio" name="cycle" value="6" id="cycle_6">
                        </c:if>
                        <label for="cycle_6">6秒</label>
                    </div>
                </div>
            </div>
            <div class="formControls col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">标准价：</label>
            <div class="formControls col-6">
                <c:if test="${rate.cycle eq 6}">
                    <label id="ratePer">${sipRate.per60}元</label>
                </c:if>
                <c:if test="${rate.cycle eq 60}">
                    <label id="ratePer">${sipRate.per60}元</label>
                </c:if>
            </div>
            <div class="formControls col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">折扣率：</label>
            <div class="formControls col-6">
                <c:if test="${rate.cycle eq 6}">
                    <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="7" datatype="f1_2-2" value="${rate.per6Discount/10}" name="discount" id="discount" onkeyup="discountPrice(this);" onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于10000且精度不超过1位的小数！"/>
                </c:if>
                <c:if test="${rate.cycle eq 60}">
                    <input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="7" datatype="f1_2-2" value="${rate.per60Discount/10}" name="discount" id="discount" onkeyup="discountPrice(this);" onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于10000且精度不超过1位的小数！"/>
                </c:if>
                <label for="discount">%</label>
            </div>
            <div class="formControls col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">折后价：</label>
            <div class="formControls col-6">
                <c:if test="${rate.cycle eq 6}">
                    <label id="discountPrice">${rate.per6Discount * sipRate.per6 / 1000 }元</label>
                </c:if>
                <c:if test="${rate.cycle eq 60}">
                    <label id="discountPrice">${rate.per60Discount * sipRate.per60 / 1000 }元</label>
                </c:if>
            </div>
            <div class="formControls col-3"></div>
        </div>

        <div class="row cl">
            <div class="col-8 col-offset-4">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset"  onclick="layer_close();" value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>