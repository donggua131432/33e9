<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <title>新增中继</title>
</head>
<script type="text/javascript">
    var isSubmit = false;
    $(function(){
        $("input[name='sipBus']").val([${bus}]);

        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });
        // 表单校验
        $("#form-relay-add").Validform({
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
                        layer_close();
                    }
                },2000);
            },
            datatype : {
                "empty": /^\s*$/,
                areacode : function(gets, obj, curform, datatype){

                    $("#cityname1").text("");

                    if (!gets) {
                        return true;
                    }

                    var reg = /^[0-9]{2,8}$/;
                    if (!reg.test(gets)) {
                        return "输入的格式不正确";
                    }

                    var exits = false;

                    $.ajax({
                        type: "POST",
                        url: "<c:url value='/cityMgr/getCityNameByAreaCode'/>",
                        data: {"areacode" : gets},
                        async : false,
                        success: function(msg){
                            if (msg.code == 'ok') {
                                $("#cityname1").text(msg.data.cname);
                                exits = true;
                            } else {
                                exits = true;
                            }
                        }
                    });
                    return exits;
                },
                ipport : function(gets, obj, curform, datatype) {
                    var ip1,port1,ip2,port2,ip3,port3,ip4,port4;
                    var data = {};

                    if (obj.attr('id') == 'ip1' || obj.attr('id') == 'ip2' || obj.attr('id') == 'ip3' || obj.attr('id') == 'ip4') {
                        if (!o._regex.ip.test(gets)) {
                            return "格式错误";
                        }
                    } else if (obj.attr('id') == 'port1' || obj.attr('id') == 'port2' || obj.attr('id') == 'port3' || obj.attr('id') == 'port4') {
                        if (!o._regex.port.test(gets)) {
                            return "格式错误";
                        }
                    }

                    ip1 = $("#ip1").val();
                    ip2 = $("#ip2").val();
                    ip3 = $("#ip3").val();
                    ip4 = $("#ip4").val();
                    port1 = $("#port1").val();
                    port2 = $("#port2").val();
                    port3 = $("#port3").val();
                    port4 = $("#port4").val();

                    if (obj.attr('id') == 'ip2' || obj.attr('id') == 'ip4' || obj.attr('id') == 'port2' || obj.attr('id') == 'port4') {
                        if (!ip2 || !ip4 || !port2 || !port4) {
                            return true;
                        }
                        if (ip2 == ip4 && port2 == port4) {
                            return "本端的两个IP端口不能相同";
                        }
                    }

                    if (obj.attr('id') == 'ip1' || obj.attr('id') == 'ip2' || obj.attr('id') == 'port1' || obj.attr('id') == 'port2') {
                        if (!ip1 || !ip2 || !port1 || !port2) {
                            return true;
                        }

                        data = {id : '${sipBasic.id}', ipport1 : ip1 + ":" + port1, ipport2 : ip2 + ":" + port2};

                    } else if (obj.attr('id') == 'ip3' || obj.attr('id') == 'ip4' || obj.attr('id') == 'port3' || obj.attr('id') == 'port4') {
                        if (!ip3 || !ip4 || !port3 || !port4) {
                            return true;
                        }
                        data = {id : '${sipBasic.id}', ipport3 : ip3 + ":" + port3, ipport4 : ip4 + ":" + port4};
                    }

                    var exits = false;

                    $.ajax({
                        type: "POST",
                        url: "<c:url value='/relay/editcheckUnique'/>",
                        data: data,
                        async : false,
                        success: function(msg){
                            if (msg.code == 'ok') {
                                exits = true;
                            } else {
                                exits = '组合已存在';
                            }
                        }
                    });

                    return exits;
                }
            }
        });

        // 加载所属行业列表
        getTradeOption();
        $("input[name='relayType']:checked").click();

        <%--console.info(${sipBasic.ipport1});--%>

        $('.skin-minimal input#FSProxy').on('ifChecked', function(event){
            //如果是选中，点击后则为不选中
            $("#isFSProxy input").each(function(){
                //$(this).removeAttr("disabled");
                $(this).attr("disabled", false);
                $(this).removeAttr("ignore");

//                location.replace(location.href);
//                parent.location.replace(parent.location.href);
                //$("#isFSProxy").html($("#isFSProxy").data("isFSProxy"));
            })
        });
        $('.skin-minimal input#FSProxy').on('ifUnchecked', function(event){
            //如果不选中，点击后则为选中

            $("#isFSProxy input").each(function(){
                $(this).attr("disabled", true);
                $(this).attr("ignore", "ignore");

            })
        });

        <c:if test="${sipBasic.ipport1 ne ''}">
        $("#FSProxy").iCheck('check');
//        $("#FSProxy").attr("checked",true);
        </c:if>

        <c:if test="${sipBasic.ipport1 eq ''}">
        $("#FSProxy").iCheck('uncheck');
        </c:if>

        $("#useType").on("change", function() {
            if (this.value == '00' || this.value == '') {
                $("#busType").attr("disabled", false);
                $("#busType").attr("ignore", "");
            }
            if (this.value == '01' || this.value == '02') {
                $("#busType").val('');
                $("#busType").attr("disabled", true);
                $("#busType").attr("ignore", "ignore");
            }
        });

        fillCityName($("input[name='areacode']").val());
    });

    // 加载所属行业列表
    function getTradeOption() {
        $.ajax({
            type: "POST",
            url: "<c:url value='/dicdata/type'/>",
            data: {"typekey" : "relayType"},
            success: function(msg){
                if (msg.data) {
                    $("#relayType").html("");
                    var options = "<option value=''>请选择</option>";
                    $.each(msg.data, function(n,v) {
                        options += "<option value='" + v.code + "'>" + v.name + "</option>";
                    });
                    $("#relayType").append(options);
                    $("#relayType").val('${sipBasic.relayType}');

                }
            }
        });
    }

    function closeEditDialog(){
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
    }

    function fillCityName(gets) {
        if (gets) {
            $.ajax({
                type: "POST",
                url: "<c:url value='/cityMgr/getCityNameByAreaCode'/>",
                data: {"areacode" : gets},
                success: function(msg){
                    if (msg.code == 'ok') {
                        $("#cityname1").text(msg.data.cname);
                        exits = true;
                    } else {
                        exits = msg.msg;
                    }
                }
            });
        }
    }

</script>
<body>
<div class="pd-20">
    <form action="<c:url value='/relay/updateRelayInfo'/>" method="post" class="form form-horizontal" id="form-relay-add">

        <%--<div class="row cl">--%>
        <%--<div class="formControls col-5">--%>
        <h4 class="page_title">中继信息</h4>
            <input type="hidden" name="id" id="id" value="${sipBasic.id}">
            <input type="hidden" name="oldBusType" id="oldBusType" value="${sipBasic.busType}">
            <input type="hidden" name="oldUseType" id="oldUseType" value="${sipBasic.useType}">
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>中继名称：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="${sipBasic.relayName}" placeholder="请输入中继名称" id="relayName" name="relayName" datatype="_ajax" maxlength="30" data-ajax="{'url':'<c:url value="/relay/editcheckUnique"/>',param:[{name:'id',value:'${sipBasic.id}'}],msg:'该中继名称已经存在',_regex:{type:'relayName',msg:'名称格式不正确'}}" nullmsg="中继名称不能为空"/>
            </div>
            <div class="col-3"></div>
        </div>
        <c:if test="${flag eq 0}">
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>中继群编号：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="${sipBasic.relayNum}" placeholder="请输入中继号" id="relayNum" name="relayNum" datatype="_ajax" maxlength="5" data-ajax="{'url':'<c:url value="/relay/editcheckUnique"/>',param:[{name:'id',value:'${sipBasic.id}'}],msg:'该中继编号已经存在',_regex:{type:'relayNum',msg:'格式不正确'}}" nullmsg="中继编号不能为空"/>
            </div>
            <div class="col-3"></div>
        </div>
        </c:if>
        <c:if test="${flag eq 1}">
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>中继群编号：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="${sipBasic.relayNum}" placeholder="请输入中继号" id="relayNum1" name="relayNum" disabled="disabled" datatype="_ajax" maxlength="5" data-ajax="{'url':'<c:url value="/relay/editcheckUnique"/>',param:[{name:'id',value:'${sipBasic.id}'}],msg:'该中继编号已经存在',_regex:{type:'relayNum',msg:'格式不正确'}}" nullmsg="中继编号不能为空"/>
            </div>
            <div class="col-3"></div>
        </div>
        </c:if>

            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>类型：</label>
                <div class="formControls col-2">
                    <select id="useType" name="useType"  class="select" style="width:140px;height: 31px" datatype="*"  nullmsg="请选择！" >
                        <option value="">请选择</option>
                        <option value="00" <c:if test="${sipBasic.useType eq '00'}">selected</c:if>>客户</option>
                        <option value="01" <c:if test="${sipBasic.useType eq '01'}">selected</c:if>>供应商</option>
                        <option value="02" <c:if test="${sipBasic.useType eq '02'}">selected</c:if>>平台内部</option>
                    </select>
                </div>
                <div class="col-1"></div>
                <label class="form-label col-2">业务类型：</label>
                <div class="formControls col-2">
                    <select id="busType" name="busType" class="select" style="width:140px;height: 31px" <c:if test="${sipBasic.useType eq '01' or sipBasic.useType eq '02'}">disabled="disabled" ignore="ignore"</c:if> datatype="*"  nullmsg="请选择！" >
                        <option value="">请选择</option>
                        <option value="03" <c:if test="${sipBasic.busType eq '03'}">selected</c:if>>SIP</option>
                        <option value="05" <c:if test="${sipBasic.busType eq '05'}">selected</c:if>>云话机</option>
                        <option value="01" <c:if test="${sipBasic.busType eq '01'}">selected</c:if>>智能云调度</option>
                        <option value="91" <c:if test="${sipBasic.busType eq '91'}">selected</c:if>>呼叫中心代答中继</option>
                    </select>
                </div>
                <div class="col-3"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-2"><span class="c-red">*</span>中继方向：</label>
                <div class="formControls col-2">
                    <select id="relayType" name="relayType"  class="select"  style="width:140px;height: 31px" datatype="*"  nullmsg="请选择中继方向！" >
                    </select>
                </div>
                <div class="col-3"></div>
                <div class="formControls col-5">
                    <div class="skin-minimal">
                        <div class="check-box">
                            <input type="checkbox" id="FSProxy" value="" checked/>
                            <label id="FSProxyLable" for="FSProxy" style="">经过FS-Proxy</label>
                        </div>
                    </div>
                </div>

            </div>
            <div id="isFSProxy">

                <div class="row cl">
                    <label class="form-label col-2"><span class="c-red"></span>外域对端区号：</label>
                    <div class="formControls col-3">
                        <input type="text" class="input-text" style="width: 155px;" value="${sipBasic.areacode}" placeholder="请输入外域对端区号" name="areacode" datatype="areacode" maxlength="8"  errormsg="输入的格式不正确"/>
                        <span id="cityname1"></span>
                    </div>
                    <div class="col-3"></div>
                </div>

                <div class="row cl">
                    <label class="form-label col-2"><span class="c-red">*</span>外域对端IP：</label>
                    <div class="formControls col-2">
                        <input type="text" class="input-text" value="${ip1}" placeholder="请输入外域对端IP" id="ip1" name="ip1" datatype="ipport" maxlength="30" nullmsg="不能为空"  errormsg="格式错误"/>
                    </div>
                    <div class="col-1"></div>
                    <label class="form-label col-2">端口：</label>
                    <div class="formControls col-2">
                        <input type="text" class="input-text" value="${port1}" placeholder="请输入端口" id="port1" name="port1" datatype="ipport" maxlength="5" nullmsg="不能为空"  errormsg="格式错误"/>
                    </div>
                    <div class="col-3"></div>
                </div>

                <div class="row cl">
                    <label class="form-label col-2"><span class="c-red">*</span>外域本端IP：</label>
                    <div class="formControls col-2">
                        <input type="text" class="input-text" value="${ip2}" placeholder="请输入外域本端IP" id="ip2" name="ip2" datatype="ipport" maxlength="30" nullmsg="不能为空"  errormsg="格式错误"/>
                    </div>
                    <div class="col-1"></div>
                    <label class="form-label col-2">端口：</label>
                    <div class="formControls col-2">
                        <input type="text" class="input-text" value="${port2}" placeholder="请输入端口" id="port2" name="port2" datatype="ipport" maxlength="5" nullmsg="不能为空"  errormsg="格式错误"/>
                    </div>
                    <div class="col-3"></div>
                </div>

                <div class="row cl">
                    <label class="form-label col-2"><span class="c-red">*</span>内域对端IP：</label>
                    <div class="formControls col-2">
                        <input type="text" class="input-text" value="${ip3}" placeholder="请输入内域对端IP" id="ip3" name="ip3" datatype="ipport" maxlength="30" nullmsg="不能为空"  errormsg="格式错误"/>
                    </div>
                    <div class="col-1"></div>
                    <label class="form-label col-2">端口：</label>
                    <div class="formControls col-2">
                        <input type="text" class="input-text" value="${port3}" placeholder="请输入端口" id="port3" name="port3" datatype="ipport" maxlength="5" nullmsg="不能为空"  errormsg="格式错误"/>
                    </div>
                    <div class="col-3"></div>
                </div>

                <div class="row cl">
                    <label class="form-label col-2"><span class="c-red">*</span>内域本端IP：</label>
                    <div class="formControls col-2">
                        <input type="text" class="input-text" value="${ip4}" placeholder="请输入内域本端IP" id="ip4" name="ip4" datatype="ipport" maxlength="30" nullmsg="不能为空"  errormsg="格式错误"/>
                    </div>
                    <div class="col-1"></div>
                    <label class="form-label col-2">端口：</label>
                    <div class="formControls col-2">
                        <input type="text" class="input-text" value="${port4}" placeholder="请输入端口" id="port4" name="port4" datatype="ipport" maxlength="5" nullmsg="不能为空"  errormsg="格式错误"/>
                    </div>
                    <div class="col-3"></div>
                </div>

                <div class="row cl">
                    <label class="form-label col-2"><span class="c-red"></span>SIP-URI域名：</label>
                    <div class="formControls col-7">
                        <input type="text" class="input-text" value="${sipBasic.sipUrl}" placeholder="请输入SIP-URL域名" id="sipUrl" name="sipUrl" datatype="/^[\u4E00-\u9FA5-0-9a-zA-Z\(\)\.\-\_\[\]\~\!\*\')]*$/"  nullmsg="SIP-URL域名不能为空！" maxlength="64"/>
                    </div>
                    <div class="col-3"></div>
                </div>

                <div class="row cl">
                    <label class="form-label col-2"><span class="c-red"></span>出局R-URI：</label>
                    <div class="formControls col-7">
                        <input type="text" class="input-text" value="${sipBasic.sipOutnoPr}" placeholder="请输入出局R-URL" id="sipOutnoPr" name="sipOutnoPr" datatype="/^[0-9]{0,16}$/" nullmsg="出局R-URL不能为空！" maxlength="16"/>
                    </div>
                    <div class="col-3"></div>
                </div>

                <div class="row cl">
                    <label class="form-label col-2"><span class="c-red"></span>中继业务处理：</label>
                    <%--<div class="form-label col-2">(请输入16进制数)：0x</div>--%>
                    <div class="formControls col-7">
                        <%--<input type="text" class="input-text" value="${sipBusiness_16}" placeholder="如：3d" id="sipBusiness_16" name="sipBusiness_16" datatype="/^[0-9a-fA-F]*$/"  maxlength="8"/>--%>
                        <div class="skin-minimal">
                            <c:forEach items="${relayBus}" var="bus">
                                <div class="check-box" style="width: 260px;">
                                    <input type="checkbox" id="checkbox-${bus.code}" name="sipBus" value="${bus.code}">
                                    <label for="checkbox-${bus.code}">${bus.name}</label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="col-3"></div>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2"><span class="c-red"></span>并发呼叫限制：</label>
                <div class="formControls col-7">
                    <input type="text" class="input-text" value="${sipBasic.maxConcurrent}" placeholder="请输入并发数" id="maxConcurrent" name="maxCon" datatype="_ajax" data-ajax="{_regex:{type:'maxConcurrent',msg:'格式不正确'}}"  maxlength="5"/>
                </div>
                <div class="col-3"></div>
            </div>

</div>

<div class="row cl">
</div>

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