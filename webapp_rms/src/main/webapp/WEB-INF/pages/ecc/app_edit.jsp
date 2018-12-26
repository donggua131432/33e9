<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>编辑云总机应用</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/plugins/jquery.form.js"></script>
    <script type="text/javascript">
        var datatables = "";
        var search_param = {};

        $(function(){

            initDicData("voiceCode", "voiceCode", false, '${appInfo.appInfoExtra.voiceCode}');

            checkedBox('recordingTypes', '${appInfo.appInfoExtra.recordingType}');
            checkedBox('valueAddeds', '${appInfo.appInfoExtra.valueAdded}');

            // 初选 被叫号码类型
            function selectCalledType(arr) {
                if (arr) {
                    var arrs = arr.split(",");
                    $("input[name='calledType']").val(arrs);
                }
            }

            $('.skin-minimal input').iCheck({
                checkboxClass: 'icheckbox-blue',
                radioClass: 'iradio-blue',
                increaseArea: '20%'
            });

            $("#form-member-add").Validform({
                tiptype:2,
                ajaxPost:true,
                beforeSubmit : function(curform) {
                    var $feeUrl = $('#feeUrl').val();
                    var $callbackUrl = $('#callbackUrl').val();


                    if ($('#urlCheck').is(':checked') &&  $feeUrl ==""  && $callbackUrl ==""){

                        $("#shadeWin").show();
                        layer.alert('请至少填写一种回调地址URL！');

                        return false;
                    }

                    else if($feeUrl !=""){
                        if (!checkUrl($feeUrl)){ layer.alert('请填写正确的URL');
                            return  false;
                        }

                    }
                    else if($callbackUrl !=""){
                        if (!checkUrl($callbackUrl)){ layer.alert("请填写正确的URL");
                            return false;
                        }
                    }
                    return true;
                },
                callback:function(data){
                    $.Showmsg(data.msg);
                    setTimeout(function(){
                        $.Hidemsg();
                        if(data.code == "ok"){
                            //刷新父级页面
                            //parent.location.replace(parent.location.href);
                            //关闭当前窗口
                            //layer_close();
                            $("#form-member-add :input").not(":submit,:button").attr("disabled", true);
                            $("#toSaveApp").hide();
                            $("#toEditApp").show();
                            if (data.data) {
                                $("#quotaVal").text(data.data.quota);
                                $("#quota").val(data.data.quota);
                                $("#quota1").val("");
                            }
                        }
                    },2000);
                },
                datatype: {
                    "checkAppName":function(gets, obj, curform, datatype){
                        var appName = gets.trim(), matchReg = /^[\s]*[\u4E00-\u9FA50-9a-zA-Z\d\(\)]*[\s]*$/, checkNum = 0;
                        if (matchReg.test(appName)) {
                            if(appName.length < 1 || appName.length > 20){
                                checkNum = 1;
                            }
                        }else{
                            return "应用名称只能由数字、字母、汉字组成！";
                        }
                        if(checkNum == 0){
                            $("#appName").val(appName);
                            return true;
                        }else if(checkNum == 1){
                            return "应用名称应为2-20位数字、字母、汉字组成！";
                        }

                    },
                    "quota" : function(gets, obj, curform, datatype) {

                        if (!gets) {
                            return true;
                        }

                        var matchReg = /^(0|[1-9][0-9]{0,8})$/;
                        if ($('#limitCheck').is(':checked') && !matchReg.test(gets)) {
                            return "格式错误";
                        }
                        return true;
                    }
                }
            });

            initPCArea("pid", "cid", "请选择", "${eccInfo.pcode}", "${eccInfo.cityid}");

            $("#toEditApp").on("click",function(){
                $("#form-member-add :input").not(":submit,:button").attr("disabled", false);
                $("#toSaveApp").show();
                $("#toEditApp").hide();
            });

           /* $("#toSaveIVR").on("click",function(){
                $("#form-eccid-edit :input").not(":submit,:button").attr("disabled", true);
                $("#toSaveIVR").hide();
                $("#toEditIVR").show();
            });
*/
            $("#toEditIVR").on("click",function(){
                $("#form-eccid-edit :input").not(":submit,:button").attr("disabled", false);
                $("#toSaveIVR").show();
                $("#toEditIVR").hide();
            });

            $("#transferNum").attr("disabled", ${not eccInfo.transfer});
            $("#transfer").iCheck('${eccInfo.transfer ? "check" : "uncheck"}');

            $("#transfer").on('ifChecked', function(event){ //如果是选中，点击后则为不选中
                $("#transferNum").attr("disabled", false);
            });
            $("#transfer").on('ifUnchecked', function(event){ //如果不选中，点击后则为选中
                $("#transferNum").attr("disabled", true);
                $("#ivrVoiceNeed0").iCheck("uncheck");
                $("#ivrVoiceNeed1").iCheck("check");
            });

            $("#form-eccid-edit").Validform({
                tiptype:2,
                ajaxPost:true,
                beforeSubmit:function(curform){
                    curform.ajaxSubmit({
                        type:'post',
                        url:'<c:url value='/eccAppInfo/saveOrUpdateIVR'/>',
                        success:function(data){
                            if (data.code == 'ok') {
                                layer.alert("保存成功",{icon:1});
                                $("#form-eccid-edit :input").not(":submit,:button").attr("disabled", true);
                                $("#toSaveIVR").hide();
                                $("#toEditIVR").show();
                                $("#eccid").val(data.msg);

                                if (data.data) {
                                    var href = '<c:url value="/eccAppInfo/downloadIvr?url="/>' + data.data;
                                    var vname = $("#ivrVoiceName").val();
                                    $("#download").attr("href", href);
                                    if (vname) {
                                        $("#download").attr("download", vname + ".wav");
                                    } else {
                                        $("#download").attr("download", "IVR提示音.wav");
                                    }
                                }
                            } else {
                                layer.alert(data.msg,{icon:2});
                            }

                        }
                    });
                    return false;
                },
                datatype: {
                    "switchboard" : function(gets,obj,curform,regxp) {
                        $("#switchboardId").val("");

                        var reg = /^[0-9]{5,30}$/;
                        if (!reg.test(gets)) {
                            return "格式不正确";
                        }
                        var r = false;
                        var cityid = $("#cid").val();
                        if (!cityid) {
                            return "请先选择城市";
                        }
                        var eccid = $("#eccid").val();
                        $.ajax({
                            type:"post",
                            url:'<c:url value="/eccAppInfo/checkSwitchboard"/>',
                            dataType:"json",
                            async:false,
                            data:{'switchboard' : gets, 'eccid' : eccid, 'appid' : '${appInfo.appid}', 'cityid':cityid },
                            success : function(result) {
                                if (result.code == 'ok') {
                                    r = true;
                                    $("#switchboardId").val(result.msg);
                                } else {
                                    r = result.msg;
                                }
                            }
                        });
                        return r;
                    },
                    "transferNum" : function(gets,obj,curform,regxp) {

                        var checked = $("#transfer").is(':checked');
                        if (!checked) {
                            return true;
                        }

                        var reg = /^[0-9]{5,30}$/;
                        if (!reg.test(gets)) {
                            return "格式不正确";
                        }

                        return true;
                    },
                    "ivrVoiceName" : function (gets,obj,curform,regxp) {
                        if (!gets) {
                            return true;
                        }
                        var reg = /^[\u4E00-\u9FA5-a-zA-Z0-9]{0,20}$/;
                        if (!reg.test(gets)) {
                            return "格式不正确";
                        }

                        return true;
                    }
                }
            });

            // 回调地址url
            $('#urlCheck').click(function() {
                if($('#urlCheck').is(':checked')) {
                    $('#urlStatus').val("00");
                    $('#callbackUrl').removeAttr('disabled');
                    $('#feeUrl').removeAttr('disabled');
                }
                else{
                    $('#callbackUrl').attr("disabled","true");
                    //$('#callbackUrl').removeClass().empty();
                    $('#urlStatus').val("01");

                    $('#feeUrl').attr("disabled","true");
                    //$('#feeUrl').removeClass().empty();
                    //$('#feeUrl-error').removeClass().empty();
                }
            });

            // 应用限额
            $('#limitCheck').click(function() {
                if($('#limitCheck').is(':checked')) {
                    $('#limit').show();
                    $('#limitFlag').val("01");
                }
                else{
                    $('#limit').hide();
                    $('#limitFlag').val("00");
                }
            });


        });

        function checkUrl(url) {
            var re=/^(http):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i
            if(!re.test(url)) {
                return false;
            } else {
                return true;
            }
        };

    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value='/sp/updateApp'/>" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${appInfo.id}" name="id"/>
        <input type="hidden" value="${appInfo.appid}" name="appid"/>
        <div class="form form-horizontal" >
            <div class="row cl">
                <label class="form-label col-2">客户名称：</label>
                <div class="formControls col-4">
                    <span>${appInfo.companyName}</span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">Account ID：</label>
                <div class="formControls col-4">
                    <span>${appInfo.sid}</span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">应用名称：</label>
                <div class="formControls col-3">
                    <input type="text" class="input-text" value="${appInfo.appName}" placeholder="请输入应用名称" id="appName" name="appName" datatype="checkAppName" nullmsg="应用名称不能为空！" maxlength="20"/>
                </div>
                <div class="col-1"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">APP ID：</label>
                <div class="formControls col-4">
                    <span>${appInfo.appid}</span>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-2">录音开关：</label>
                <div class="formControls col-3">
                    <div class="skin-minimal">
                        <div class="check-box">
                            <input type="checkbox" id="recordingType-3" name="recordingTypes" value="3">
                            <label for="recordingType-3">呼入录音</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" id="recordingType-4" name="recordingTypes" value="4">
                            <label for="recordingType-4">SIP phone呼出录音</label>
                        </div>
                    </div>
                </div>
                <div class="col-1"></div>
            </div>


            <div class="row cl">
                <label class="form-label col-2">语音编码：</label>
                <div class="formControls col-3">
                    <select class="input-text" id="voiceCode" name="voiceCode" datatype="*" nullmsg="请选择">
                    </select>
                </div>
                <div class="col-1"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">状态：</label>
                <div class="formControls col-2">
                    <div style="margin-top: 3px;">
                        <c:if test="${appInfo.status eq '00'}">
                            <span class="label label-success radius">正常</span>
                        </c:if>
                        <c:if test="${appInfo.status eq '01'}">
                            <span class="label label-warning radius">冻结</span>
                        </c:if>
                        <c:if test="${appInfo.status eq '02'}">
                            <span class="label label-defaunt radius">禁用</span>
                        </c:if>
                    </div>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-2">消费限额:</label>
                <div class="formControls col-5">
                    <input type="hidden" id="quota" name="quota" value="${appInfo.quota}"/>
                    <input type="hidden" id="limitFlag" name="limitFlag" value="${appInfo.limitFlag}"/>
                    <input type="checkbox" id="limitCheck" name="limitCheck" value="${appInfo.limitFlag}" <c:if test="${appInfo.limitFlag eq '01'}">checked="checked"</c:if> />
                    开启

                    <span id="limit" <c:if test="${appInfo.limitFlag ne '01'}">style="display: none"</c:if>>
                        当前用户限额消费 <span id="quotaVal">${appInfo.quota}</span> 元，现修改至
                        <select name="limitStatus" id="limitStatus" class="input-text" style="width: 100px;">
                            <option value="00">额度提升</option>
                            <option value="01">额度下调</option>
                        </select>
                        <input type="text" id="quota1" name="quota1" maxlength="9" class="input-text" datatype="quota" style="width: 100px;"/><span>元 </span>
                    </span>

                </div>
                <div class="col-4"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-2">应用回调:</label>
                <div class="formControls col-2">
                    <input type="hidden" id="urlStatus" name="urlStatus" value="00"/>
                    <input type="checkbox" id="urlCheck" name="urlCheck"  <c:if test="${appInfo.urlStatus eq '00'}">checked</c:if>/>
                    <label for="urlStatus">启用</label>
                </div>
                <div class="col-4"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-2">状态回调地址URL:</label>
                <div class="formControls col-3">
                    <div class="callback_address"><span class="Yh_Examples">(示例：http://www.33e9ecloud.com/notifyServer)</span></div>
                    <textarea name="callbackUrl" id="callbackUrl"  maxlength="100" style="padding:5px 5px;font-size: 15px;" cols="73" rows="4" <c:if test="${appInfo.urlStatus ne '00'}">disabled</c:if>><c:out value="${appInfo.callbackUrl}"/></textarea>
                </div>

            </div>
            <div class="row cl">
                <label class="form-label col-2">计费回调地址URL:</label>
                <div class="formControls col-3">
                    <div class="callback_address"><span class="Yh_Examples">(示例：http://www.33e9ecloud.com/notifyServer)</span></div>
                    <textarea name="feeUrl" id="feeUrl" maxlength="100" style="padding:5px 5px;font-size: 15px;" cols="73" rows="4" <c:if test="${appInfo.urlStatus ne '00'}">disabled</c:if>><c:out value="${appInfo.appInfoExtra.feeUrl}"/></textarea>
                </div>
                <label style=""></label>
            </div>
        </div>

        <div class="row cl">
            <div class="col-1"> </div>
            <div class="col-7 col-offset-3">
                <input class="btn btn-primary radius" type="submit"  id="toSaveApp" value="&nbsp;&nbsp;保存&nbsp;&nbsp;"/>
                <input class="btn btn-primary radius" type="button"  id="toEditApp" value="&nbsp;&nbsp;编辑&nbsp;&nbsp;" style="display: none;"/>
        </div>
            <div class="col-2"></div>
        </div>
    </form>

    <h4 class="page_title"></h4>

    <form action="" method="post" class="form form-horizontal" id="form-eccid-edit" enctype="multipart/form-data">
        <input type="hidden" value="${eccInfo.id}" name="id" id="eccid"/>
        <input type="hidden" value="${appInfo.appid}" name="appid"/>
        <input type="hidden" value="${eccInfo.switchboardId}" name="switchboardId" id="switchboardId"/>
        <input type="hidden" value="${eccInfo.cityid}" name="oldCityid"/>
        <h5>总机设置</h5>
        <div class="form form-horizontal" >
            <div class="row cl">
                <label class="form-label col-2">省份：</label>
                <div class="formControls col-1">
                    <select id="pid" name="pid" class="input-text" style="width:120px;"></select>
                </div>

                <label class="form-label col-1">城市：</label>
                <div class="formControls col-1">
                    <select id="cid" name="cityid" class="input-text" style="width:130px;" datatype="*" nullmsg="请选择"></select>
                </div>
                <div class="col-3"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">总机号码：</label>
                <div class="formControls col-3">
                    <input type="text" class="input-text" value="${eccInfo.switchboard}" placeholder="请输入总机号码" id="switchboard" name="switchboard" datatype="switchboard" nullmsg="总机号码不能为空！" maxlength="30"/>
                </div>
                <div class="col-3"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">转接开关：</label>
                <div class="formControls col-3">
                    <div class="skin-minimal">
                        <div class="check-box">
                            <input type="checkbox" id="transfer" name="transfer" value="1">
                            <label for="transfer">开启</label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">转接号码：</label>
                <div class="formControls col-3">
                    <input type="text" class="input-text" value="${eccInfo.transferNum}" placeholder="请输入转接号码" id="transferNum" name="transferNum" datatype="transferNum" nullmsg="请输入转接号码" maxlength="30"/>
                </div>
                <div class="col-2"></div>
            </div>

        </div>

        <h5>IVR设置</h5>

        <div class="form form-horizontal" >

            <div class="row cl">
                <label class="form-label col-2">IVR提示音：</label>
                <div class="formControls col-3">
                    <div class="skin-minimal">
                        <c:if test="${empty eccInfo.id}">
                            <div class="radio-box">
                                <input type="radio" id="ivrVoiceNeed1" name="ivrVoiceNeed" value="1" checked />
                                <label for="ivrVoiceNeed1">需要</label>
                            </div>
                            <div class="radio-box">
                                <input type="radio" id="ivrVoiceNeed0" name="ivrVoiceNeed" value="0">
                                <label for="ivrVoiceNeed0">不需要</label>
                            </div>
                        </c:if>
                        <c:if test="${not empty eccInfo.id}">
                            <div class="radio-box">
                                <input type="radio" id="ivrVoiceNeed1" name="ivrVoiceNeed" value="1" <c:if test="${eccInfo.ivrVoiceNeed}">checked</c:if> />
                                <label for="ivrVoiceNeed1">需要</label>
                            </div>
                            <div class="radio-box">
                                <input type="radio" id="ivrVoiceNeed0" name="ivrVoiceNeed" value="0" <c:if test="${not eccInfo.ivrVoiceNeed}">checked</c:if>>
                                <label for="ivrVoiceNeed0">不需要</label>
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="col-3"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-2">IVR提示音上传：</label>
                <div class="formControls col-3">
                    <div class="skin-minimal">
                        <input type="file" class="input-text" name="video" value="${eccInfo.transferNum}" datatype="empty|video"/>
                    </div>
                </div>
                <div class="col-3"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-2">IVR提示音名称：</label>
                <div class="formControls col-3">
                    <div class="skin-minimal">
                        <input type="text" class="input-text" value="${eccInfo.ivrVoiceName}" placeholder="请输入提示音名称" id="ivrVoiceName" name="ivrVoiceName" datatype="empty|ivrVoiceName" maxlength="30"/>
                    </div>
                </div>
                <div class="col-3"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-2">IVR提示音查看：</label>
                <div class="formControls col-3">
                    <c:if test="${empty eccInfo.ivrVoiceUrl}">
                        <a id="download" style="color: blue" href="<c:url value="/eccAppInfo/downloadIvr?url=zj_ivr_welcome.wav"/>" download="IVR默认提示音.wav">下载</a>
                    </c:if>
                    <c:if test="${not empty eccInfo.ivrVoiceUrl}">
                        <a id="download" style="color: blue" href="<c:url value="/eccAppInfo/downloadIvr?url=${eccInfo.ivrVoiceUrl}"/>" download="${eccInfo.ivrVoiceName}">下载</a>
                    </c:if>
                </div>
                <div class="col-1"></div>
            </div>

        </div>

        <div class="row cl">
            <div class="col-1"> </div>
            <div class="col-7 col-offset-3">
                <input class="btn btn-primary radius" type="submit" id="toSaveIVR" value="&nbsp;&nbsp;保存&nbsp;&nbsp;"/>
                <input class="btn btn-primary radius" type="button" id="toEditIVR" value="&nbsp;&nbsp;编辑&nbsp;&nbsp;" style="display: none"/>
            </div>
            <div class="col-2"> </div>
        </div>
    </form>

    <!------------------------------------------------------------------------------------------------ 分界线 -------------------------------------------------------------------------------------->

    <h4 class="page_title"></h4>

    <div class="text-l">
        接听号码：<input type="text" id="phone" name="phone" class="input-text" placeholder="接听号码" style="width:180px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        分机号码：<input type="text" id="subNum" name="subNum" class="input-text" placeholder="分机号码" style="width:180px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        外显号码：<input type="text" id="showNum" name="showNum" class="input-text" placeholder="外显号码" style="width:180px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        接听类型：<select id="numType" name="numType" class="input-text" style="width:180px;">
            <option value="">全部</option>
            <option value="01">SIP号码</option>
            <option value="02">手机</option>
            <option value="03">固话</option>
        </select>
        <br/><br/>
        省份：<select id="pcode" name="pcode" class="input-text" style="width:180px;"></select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        城市：<select id="ccode" name="ccode" class="input-text" style="width:180px;"></select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        号码状态：<select id="callSwitchFlag" name="callSwitchFlag" class="input-text" style="width:180px;">
            <option value="">全部</option>
            <option value="00">开启</option>
            <option value="01">禁用</option>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        长途权限：<select id="longDistanceFlag" name="longDistanceFlag" class="input-text" style="width:180px;">
        <option value="">全部</option>
        <option value="00">开启</option>
        <option value="01">关闭</option>
    </select>
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="deleteShowNums();" class="btn btn-primary radius">删除</a>
        <a href="javascript:void(0);" onclick="openAddDialog('单个添加','<c:url value="/eccAppInfo/toAddSubNum?appid=${appInfo.appid}&sid=${appInfo.sid}"/>','600','400');" class="btn btn-primary radius">单个添加</a>
        <a href="javascript:void(0);" onclick="openAddDialog('批量导入','<c:url value="/eccAppInfo/toImportSubNum?appid=${appInfo.appid}&eccid=${eccInfo.id}"/>','600','260');" class="btn btn-primary radius">批量导入</a>
        <a href="javascript:void(0);" onclick="downloadReport('');" class="btn btn-primary radius r">导出</a>
    </div>

    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="5%"><input type="checkbox" value=""/>序号</th>
                <th width="10%">接听号码类型</th>
                <th width="8%">接听号码</th>
                <th width="8%">外显号码</th>
                <th width="7%">分机号</th>
                <th width="5%">省份</th>
                <th width="5%">城市</th>
                <th width="7%">认证密码</th>
                <th width="5%">SIP REALM</th>
                <th width="10%">IP：PORT</th>
                <th width="10%">创建时间</th>
                <th width="5%">长途开关</th>
                <th width="5%">号码状态</th>
                <th width="10%">操作</th>
            </tr>
            </thead>
        </table>
    </div>

</div>
</body>
<script type="text/javascript">

    $(document).ready(function() {
        initPCArea("pcode", "ccode", "全部");

        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/eccAppInfo/pageExtention?params[appid]=${appInfo.appid}'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // ac.name companyName, ua.sid, ai.appid appId, ai.app_name appName, ai.status, ai.create_date createDate
        // an.long_distance_flag longDistanceFlag, an.call_switch_flag callSwitchFlag,
        var columns = [
            { data: 'rowNO' },
            { data: 'numType'},
            { data: 'fixphone'},
            { data: 'showNum' },
            { data: 'subNum' },
            { data: 'pname'},
            { data: 'cname'},
            { data: 'pwd'},
            { data: 'sipRealm'},
            { data: 'ipPort'},
            { data: 'atime'},
            { data: 'longDistanceFlag'},
            { data: 'callSwitchFlag'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,5,6,7,8,9,10,11,12],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [0],
                "render": function(data, type, full) {
                    return '<input type="checkbox" name="shownumid" value="'+full.id+'"/>&nbsp;&nbsp;' + data;
                }
            },
            {
                "targets": [1],
                "render": function(data, type, full) {
                    if (data == '01') {
                        return 'SIP号码';
                    }
                    if (data == '02') {
                        return '手机';
                    }
                    if (data == '03') {
                        return '固话';
                    }
                    return '';
                }
            },
            {
                "targets": [2],
                "render": function(data, type, full) {
                    if (full.numType == '01') {
                        return full.sipphone
                    }
                    return full.fixphone;
                }
            },
            {
                "targets": [3,7,8,9],
                "render": function(data, type, full) {
                    if (full.numType == '01') {
                        return data;
                    }
                    return '';
                }
            },
            {
                "targets": [10],
                "render": function(data, type, full) {
                    if (full.addtime) {
                        return o.formatDate(full.addtime, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.addtime;
                }
            },
            {
                "targets": [11],
                "render": function(data, type, full) {
                    if (full.numType == '01') {
                        if (data == '00') {
                            return '<span class="label label-success radius">开启</span>';
                        }
                        if (data == '01') {
                            return '<span class="label label-defaunt radius">关闭</span>';
                        }
                        return data;
                    }
                    return "";

                }
            },
            {
                "targets": [12],
                "render": function(data, type, full) {
                    if (data == '00') {
                        return '<span class="label label-success radius">开启</span>';
                    }
                    if (data == '01') {
                        return '<span class="label label-defaunt radius">禁用</span>';
                    }
                    return data;
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false, // callSwitchFlag
                "render": function(data, type, full) {
                    var _ex_in = "";
                    var phone = full.numType == '01' ? full.sipphone : full.fixphone;
                    _ex_in += '<a title="编辑" href="javascript:;" onclick="openUserDialog(\'编辑\',\'<c:url value="/eccAppInfo/toEditSubNum?id=' + full.id + '&appid=${appInfo.appid}&eccid=${eccInfo.id}"/>\',\'600\',\'400\');" class="ml-5" style="text-decoration:none">编辑</a>';

                    if (full.numType == '01') {
                        if (full.longDistanceFlag == '00') {
                            _ex_in += '<a title="长途开关" href="javascript:;" onclick="updateSipstatus(\'01\',\'longDistanceFlag\',\''+ phone +'\',\'确定关闭长途？\',\''+ full.id +'\');" class="ml-5" style="text-decoration:none">长途关闭</a>';
                        } else {
                            _ex_in += '<a title="长途开关" href="javascript:;" onclick="updateSipstatus(\'00\',\'longDistanceFlag\',\''+ phone +'\',\'确定开启长途？\',\''+ full.id +'\');" class="ml-5" style="text-decoration:none">长途开启</a>';
                        }
                    }

                    if (full.callSwitchFlag == '00') {
                        _ex_in += '<a title="号码开关" href="javascript:;" onclick="updateSipstatus(\'01\',\'callSwitchFlag\',\''+ phone +'\',\'确定禁用号码？\',\''+ full.id +'\');" class="ml-5" style="text-decoration:none">号码禁用</a>';
                    } else {
                        _ex_in += '<a title="号码开关" href="javascript:;" onclick="updateSipstatus(\'00\',\'callSwitchFlag\',\''+ phone +'\',\'确定开启号码？\',\''+ full.id +'\');" class="ml-5" style="text-decoration:none">号码开启</a>';
                    }

                    return _ex_in;
                }
            }
        ];

        datatables = $(".table-sort").dataTable({
            "processing": false, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "dom": 'rtilp<"clear">',
            "aaSorting": [[ 10, "desc" ]], // 默认的排序列（columns）从0开始计数
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                params:{
                    "phone" : $("#phone").val(),
                    "subNum" : $("#subNum").val(),
                    "showNum" : $("#showNum").val(),
                    "numType" : $("#numType").val(),
                    "pcode" : $("#pcode").val(),
                    "ccode" : $("#ccode").val(),
                    "callSwitchFlag" : $("#callSwitchFlag").val(),
                    "longDistanceFlag" : $("#longDistanceFlag").val()
                }
            };
            datatables.fnDraw();
        });

        $(window).resize(function(){
            $(".table-sort").width("100%");
        });
    });

    // 导出报表
    function downloadReport(type) {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        //
        window.open("<c:url value='/eccAppInfo/downloadReport'/>?params[appid]=" + '${appInfo.appid}'
                + "&params[phone]=" + $("#phone").val()
                + "&params[subNum]=" + $("#subNum").val()
                + "&params[showNum]=" + $("#showNum").val()
                + "&params[numType]=" + $("#numType").val()
                + "&params[pcode]=" + $("#pcode").val()
                + "&params[ccode]=" + $("#ccode").val()
                + "&params[callSwitchFlag]=" + $("#callSwitchFlag").val()
                + "&params[longDistanceFlag]=" + $("#longDistanceFlag").val()
        );
    }

    // 单个添加
    function openAddDialog(title,url,w,h){
        var eccid = $("#eccid").val();
        if (!eccid) {
            layer.alert("请先保存总机设置",{icon:5});
            return
        }
        layer_show(title,url,w,h);
    }

    /*增加*/
    function openUserDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }

    function openRelayDialog(title,url,w,h) {
        layer_full(title,url,w,h);
    }


    /*  */
    function deleteShowNums(){
        var showNumIds = "",length = 0;
        var appid = "${appInfo.appid}";
        $("input[name='shownumid']:checked").each(function(){
            showNumIds += "&id=" + this.value;
            length ++;
        });
        if (!showNumIds) {
            layer.alert("请选择要删除的数据", {icon:0});
            return
        }
        showNumIds = showNumIds.substring(1);
        layer.confirm('是否删除这 ' + length + ' 个分机号？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/eccAppInfo/deleteSubNums'/>?" + showNumIds+ "&appid=" +appid,
                dataType:"json",
                //data:{"subid":''},
                success:function(data){
                    layer.alert(data.msg, {icon: 1});
                    datatables.fnDraw();
                }
            });
        });
    }




    function updateSipstatus(status,type,sipphone,msg,id) {
        msg = msg + '<br>接听号码：' + sipphone;
        layer.confirm(msg, {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/eccAppInfo/updateSubNumStatus'/>?id=" + id + "&" + type + "=" + status,
                dataType:"json",
                //data:{name:type,value:status},
                success:function(data){
                    layer.msg(data.msg, {icon: 1});
                    datatables.fnDraw();
                }
            });
        });
    }
</script>
</html>
