<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>

    <title>编辑SIP应用</title>
    <script type="text/javascript">

        $(function(){

            initDicData("voiceCode", "voiceCode", false, '${appInfo.appInfoExtra.voiceCode}');
            initDicData("appType", "appType", '全部', '${appInfo.appType}');
            checkedBox('valueAddeds', '${appInfo.appInfoExtra.valueAdded}');

            $("#name").select2({
                placeholder: "请选择客户名称",
                ajax: {
                    url: "<c:url value='/userAdmin/getCompanyNameAndSidByPage'/>",
                    dataType: 'json',
                    delay: 250,
                    data: function (params) {
                        return {
                            params: {name:params.term}, // search term
                            page: params.page
                        };
                    },
                    processResults: function (data, params) {
                        params.page = params.page || 1;

                        var items = [];

                        $.each(data.rows,function(i,v){
                            items.push({id:v.sid, text:v.name});
                        });

                        return {
                            results: items,
                            pagination: {
                                more: (params.page * 30) < data.total
                            }
                        };
                    },
                    cache: true
                }

            });

            $("#name").on("change",function(){
                var sid=this.value;
                $("#sid").val(sid);
                $.ajax({
                    type:"post",
                    url:"<c:url value='/ccinfo/getAppInfoBySid'/>",
                    dataType:"json",
                    data:{"sid":sid},
                    success:function(data){
                        $("#appName").html(data.appName);
                        $("#appid").val(data.appid);
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

            $('#appButton').click(function() {
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

                    $('#form-member-add').submit();
            });

            $('#urlCheck').click(function() {
                if($('#urlCheck').is(':checked')) {
                    $('#urlStatus').val("00");
                    $('#callbackUrl').removeAttr('disabled');
                    $('#feeUrl').removeAttr('disabled');
                }
                else{
                    $('#callbackUrl').attr("disabled","true");
                    $('#callbackUrl').removeClass().empty();
                    $('#callbackUrl-error').removeClass().empty();
                    $('#urlStatus').val("01");

                    $('#feeUrl').attr("disabled","true");
                    $('#feeUrl').removeClass().empty();
                    $('#feeUrl-error').removeClass().empty();
                }
            });


            $("#form-member-add").Validform({
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
//                            layer_close();
                        }
                    },2000);
                },
                datatype: {
                    "checkAppName":function(gets, obj, curform, datatype){
                        var appName = gets.trim(), matchReg = /^[\s]*[\u4E00-\u9FA50-9a-zA-Z\d\(\)]*[\s]*$/, checkNum = 0;
                        if (matchReg.test(appName)) {
                            if(appName.length < 1 || appName.length > 33){
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

                    }
                }
            });
        });

        //信息提示框关闭
        function closeMsgDialog(){
            $("#shadeWin").hide();
        }


    </script>
</head>
<body>
<!--遮罩start-->
<div id="shadeWin" class="shade_on_win" style="display: none"></div>
<!--遮罩end-->
<div class="pd-20">

    <form action="<c:url value='/sp/addApp'/>" method="post" class="form form-horizontal" id="form-member-add" >
           <div class="row cl">
               <label class="form-label col-3">客户名称：</label>
               <div class="formControls col-5">
                   <select id="name" style="width: 306px" nullmsg="客户名称不能为空！" datatype="*"></select>
               </div>
               <div class="col-4"> </div>
           </div>

           <div class="row cl">
               <label class="form-label col-3">Account ID：</label>
               <div class="formControls col-5">
                   <input type="text" class="input-text" value="" placeholder="请输入账户ID" id="sid" name="sid" datatype="*2-32" nullmsg="账户ID不能为空！" readonly/>
               </div>
               <div class="col-4"> </div>
           </div>

            <div class="row cl">
                <label class="form-label col-3">应用名称：</label>
                <div class="formControls col-5">
                    <input type="text" class="input-text" value="" placeholder="请输入应用名称" id="appName" name="appName" datatype="checkAppName" nullmsg="应用名称不能为空！" maxlength="20"/>
                </div>
                <div class="col-4"></div>
            </div>

           <div class="row cl">
               <label class="form-label col-3">应用类型：</label>
               <div class="formControls col-5">
                   <select id="appType" name="appType" style="width: 306px" nullmsg="应用类型不能为空！" datatype="*"></select>
               </div>
               <div class="col-4"> </div>
           </div>

           <div class="row cl">
               <label class="form-label col-3">增值业务:</label>
               <div class="formControls col-5">
                   <div class="skin-minimal">
                       <input type="checkbox" id="valueAdded-0" name="valueAddeds" value="0" class="start1 "/>
                       <label for="valueAdded-0">SIP phone间回拨</label>
                       <input type="checkbox" id="valueAdded-1" name="valueAddeds" value="1" class="start1 Yh_left50"/>
                       <label for="valueAdded-1">SIP phone间直拨</label>
                   </div>
               </div>
               <div class="col-4"> </div>
           </div>

           <div class="row cl">
               <label class="form-label col-3">录音开关:</label>
               <div class="formControls col-5">
                   <div class="skin-minimal">
                       <input type="checkbox" id="recordingType-2" name="recordingTypes" value="0" class="start1 "/>
                       <label for="recordingType-2">回拨录音</label>
                       <input type="checkbox" id="recordingType-0" name="recordingTypes" value="1" class="start1 Yh_left50"/>
                       <label for="recordingType-0">直播录音</label>
                       <input type="checkbox" id="recordingType-1" name="recordingTypes" value="2" class="start1 Yh_left50"/>
                       <label for="recordingType-1">回呼录音</label>
                   </div>
               </div>
               <div class="col-4"> </div>
           </div>

           <div class="row cl">
               <label class="form-label col-3">语音编码：</label>
               <div class="formControls col-5">
                   <select class="input-text" id="voiceCode" name="voiceCode" datatype="*" nullmsg="请选择">
                   </select>
               </div>
               <div class="col-4"></div>
           </div>

           <div class="row cl">
               <label class="form-label col-3">应用回调:</label>
               <div class="formControls col-5">
                   <input type="hidden" id="urlStatus" name="urlStatus" value="00"/>
                   <input type="checkbox" id="urlCheck" name="urlCheck"  checked/>
                   <label for="urlStatus">启用</label>
               </div>
               <div class="col-4"></div>
           </div>

           <div class="create_app_common">
               <div class="callback_address_left">
                   <div class="callback_address">状态回调地址URL:<span class="Yh_Examples">(示例：http://www.33e9ecloud.com/notifyServer)</span></div>
                   <textarea name="callbackUrl" id="callbackUrl"  maxlength="100" style="padding:5px 5px;font-size: 15px;" cols="73" rows="4"></textarea>
               </div>
               <div class="callback_address_left Yh_addtop">
                   <div class="callback_address">计费回调地址URL:<span class="Yh_Examples">(示例：http://www.33e9ecloud.com/notifyServer)</span></div>
                   <textarea name="feeUrl" id="feeUrl" maxlength="100" style="padding:5px 5px;font-size: 15px;" cols="73" rows="4"></textarea>
               </div>
               <label style=""></label>
           </div>

       <div class="row cl">
            <div class="col-1"> </div>
            <div class="col-7 col-offset-3">
                <%--<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;确定&nbsp;&nbsp;"/>--%>
                <button type="button" name="appButton" id="appButton" class="btn btn-primary radius" >&nbsp;&nbsp;确定&nbsp;&nbsp;</button>
                <input class="btn btn-default radius" type="reset"  value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
            <div class="col-2"> </div>
        </div>
    </form>
</div>
</body>
</html>
