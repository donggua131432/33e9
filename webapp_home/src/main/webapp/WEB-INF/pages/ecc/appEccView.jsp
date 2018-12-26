<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/2/13
  Time: 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>控制中心</title>
    <link href="${appConfig.resourcesRoot}/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="${appConfig.resourcesRoot}/js/common/jquery-1.11.3.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/validate-extend.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>
    <script type="text/javascript">
        $(function(){
            ajaxDicData('${appConfig.authUrl}',"appType","appType","${appInfo.appType}","","",true);
            $("#spanAppType").text($("#appType").find("option:selected").text());
            checkedBox('recordingTypes', '${appExtra.recordingType}');
            checkedBox('valueAddeds', '${appExtra.valueAdded}');


            $("input[name='limitFlag']:checked").click();

            $("#userForm").validate({
                rules: {
                    quota1: {
                        required: true,
                        quota:true
                    },
                    quota_: {
                        required: true,
                        quota:true
                    }
                },
                messages: {
                    quota1: {
                        required:'请输入消费限额！',
                        quota:"请输入大于0的合法消费限额！"
                    },
                    quota_: {
                        required:'请输入消费限额！',
                        quota:"请输入大于0的合法消费限额！"
                    }
                },
                errorPlacement: function(error, element) {
                    if (element.next().is('span')) {
                        error.insertAfter(element.next());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });


        });

    </script>
</head>
<body>
<!--遮罩start-->
<div id="shadeWin" class="shade_on_win" style="display: none"></div>
<!--遮罩end-->

<jsp:include page="../common/controlheader.jsp"/>
<section>
    <div id="sec_main">
        <!-- 左侧菜单 start -->
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('ecc','yzj','appListEcc');</script>
        <!-- 左侧菜单 end -->
        <div class="main3">
            <div class="container15">
                <div class="msg">
                    <h3>创建应用>应用查看</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>

                <form id="userForm" name = "userForm" >
                <div class="create_application yzj_line">
                    <div class="app_edit_main">
                        <h2 class="yzj_tit create_app_common">基础信息</h2>
                        <div class="create_app_common">
                            <label class="create_app_title_left" >应用名称:</label>
                            <span>${appInfo.appName}</span>
                        </div>
                        <div class="create_app_common">
                            <label class="create_app_title_left" >应用类型:</label>
                            <span id="spanAppType"><select id="appType" name="appType"></select></span>
                        </div>
                        <div class="create_app_common">
                            <span class="create_app_title_left">APP&nbsp;&nbsp;&nbsp;ID:<span class="sm_account">${appInfo.appid}</span></span>

                        </div>
                        <div class="create_app_common">
                            <label class="create_app_title_left" >应用状态:</label>
                            <span>
                                <c:if test="${appInfo.status == '00'}">正常</c:if>
                                <c:if test="${appInfo.status == '01'}">冻结</c:if>
                                <c:if test="${appInfo.status == '02'}">已删除</c:if>
                            </span>
                        </div>

                        <div class="create_app_common ">
                            <label class="create_app_title_left" >限额消费:</label>
                            <c:if test="${appInfo.limitFlag == '01'}">
                                <input type="radio" id="openFlag" name="limitFlag" value="01" class="certificate"   checked="checked" /><span class="organ_msg_common">开启</span>
                                <input type="radio" id="closeFlag" name="limitFlag" value="00" class="certificate"  /><span class="organ_msg_common">关闭</span>
                            </c:if>
                            <c:if test="${appInfo.limitFlag == '00'}">
                                <input type="radio" id="openFlag" name="limitFlag" value="01" class="certificate"    /><span class="organ_msg_common">开启</span>
                                <input type="radio" id="closeFlag" name="limitFlag" value="00" class="certificate"  checked="checked"/><span class="organ_msg_common">关闭</span>
                            </c:if>
                            <button type="button" class="true" id="saveQuota">保存</button>
                        </div>

                        <div class=" create_app_name_note createTop" id="limit" style="display: none;">

                            <c:if test="${not empty appInfo.quota }">
                                <span class="organ_msg_common">当前用户限额消费 ${appInfo.quota} 元，现修改至</span>
                                <select name="limitStatus" id="limitStatus" >
                                    <option value="00">额度提升</option>
                                    <option value="01">额度下调</option>
                                </select>
                                <input type="text" id="quota1" name="quota1" size="50" maxlength="9"  class="l_input_style inputK"/><span>元 </span>

                                <%--<button type="button" class="true" id="saveQuota">确&nbsp;&nbsp;认</button>--%>
                            </c:if>

                            <c:if test="${appInfo.quota == null }">
                                <span class="organ_msg_common">当前应用消费无限额，现设置为</span>
                                <select name="limitStatus" id="limitStatus" >
                                    <option value="00">额度提升</option>
                                </select>
                                <input type="text" id="quota_" name="quota_" size="50" maxlength="9"  class="l_input_style inputK"/><span>元 </span>
                                <%--<button type="button" class="true" id="saveQuota">确&nbsp;&nbsp;认</button>--%>
                            </c:if>

                        </div>
                        <div class="create_app_common create_app_callback" id="closelimit" style="display: none;">
                        </div>


                        <div class="create_app_common">
                            <label  class="create_app_title_left" >录音开关:</label>
                            <input type="checkbox"  id="recordingType-2" name="recordingTypes" value="3" disabled class="start1 "/>
                            <label for="start1">呼入录音</label>
                            <input type="checkbox"  id="recordingType-0" name="recordingTypes" value="4" disabled class="start1 Yh_left50" />
                            <label for="start1">呼出录音</label>
                        </div>

                        <div class="create_app_common Yh_cation ">
                            <label class="create_app_title_left">应用回调:</label>
                            <input type="checkbox" id="start1" name="urlCheck" disabled <c:if test="${appInfo.urlStatus == '00'}">checked</c:if> />
                            <label for="start1">启用</label>
                        </div>


                        <div class="create_app_common">
                            <div class="callback_address_left APP_state" style="margin-left:103px;">
                                <c:if test="${appInfo.urlStatus == '00'}">
                                    <div class="callback_address">状态回调地址URL:</div>

                                    <span class="top5 c8">${appInfo.callbackUrl}</span>
                                </c:if>
                            </div>
                            <div class="callback_address_left Yh_addtop" style="margin-left:103px;">
                                <c:if test="${appInfo.urlStatus == '00'}">
                                    <div class="callback_address">计费回调地址URL:</div>
                                    <span class="top5 c8">${appExtra.feeUrl}</span>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
                </form>



                <div class="create_application yzj_line">
                    <div class="app_edit_main">
                        <h2 class="yzj_tit create_app_common">总机设置</h2>
                        <div class="create_app_common">
                            <label class="yzj_app_tit">省份：</label>
                            <span>${pname}</span>
                            <label class="yzj_Mgl yzj_app_tit">城市：</label>
                            <span>${cname}</span>
                        </div>
                        <div class="create_app_common">
                            <label class="create_app_title_left">总机号码:</label>
                            <span>${number}</span>
                        </div>
                        <div class="create_app_common">
                            <label class="create_app_title_left">转接开关:</label>
                            <c:if test="${appEcc.transfer == 'false' }">
                                <span>关闭</span>
                            </c:if>
                            <c:if test="${appEcc.transfer == 'true'}">
                                <span>开启</span>
                            </c:if>
                        </div>
                        <c:if test="${appEcc.transfer == 'true'}">
                            <div class="create_app_common">
                                <label class="create_app_title_left" >转接号码:</label>
                                <span>${appEcc.transferNum}</span>
                            </div>
                        </c:if>
                    </div>
            </div>
                <div class="create_application yzj_line">
                    <div class="app_edit_main">
                        <h2 class="yzj_tit create_app_common">IVR设置</h2>
                        <div class="create_app_common">
                            <label class="create_app_title_left">IVR提示音:</label>
                            <c:if test="${appEcc.ivrVoiceNeed == 'true'}">
                            <span>有</span>
                            </c:if>
                            <c:if test="${appEcc.ivrVoiceNeed == 'flase'}">
                                <span>无</span>
                            </c:if>
                        </div>

                       <c:if test="${appEcc.ivrVoiceNeed == 'true'}">
                        <div class="create_app_common">
                            <label class="create_app_title_left">IVR提示音名称:</label>
                            <span>${appEcc.ivrVoiceName}</span>
                        </div>
                        <div class="create_app_common">
                            <label class="create_app_title_left">IVR提示音查看:</label>
                            <span class="ringDownL_icon"></span><a href="<c:url value='/eccApp/download'/>?url=${appEcc.ivrVoiceUrl}" class="ringDownLoad">下载</a>

                            <%--<span>下载</span>--%>
                        </div>
                       </c:if>
                    </div>
                </div>


                <div class="create_application">
                    <div class="app_edit_main">
                        <h2 class="yzj_tit create_app_common">分机设置</h2>
                        <div class="app_select" id="searchForm">
                            <p class="f_left">
                                <span class="check_font">接听号码</span>
                                <input type="text" id="fixphone" name="fixphone"  class="l_input_style yzj_itW155"/>
                                <span class="check_font">分机号码</span>
                                <input type="text" id="subNum" name="subNum"  class="l_input_style yzj_itW155"/>
                                <span class="check_font">外显号码</span>
                                <input type="text" id="shownum" name="shownum" class="l_input_style yzj_itW155"/>
                                <span class="check_font">接听类型</span>
                                <select id="numType" name="numType" style="width:155px;">
                                    <option value="">全部</option>
                                    <option value="01">SIP号码</option>
                                    <option value="02">手机</option>
                                    <option value="03">固话</option>
                                </select>
                            </p>
                            <p class="f_left">
                                <span class="check_font">省&nbsp;&nbsp;&nbsp;&nbsp;  份</span>
                                <select id="pcode"  name="pcode" style="width:155px;margin-right:20px;">

                                </select>
                                <span class="check_font">城&nbsp;&nbsp;&nbsp;&nbsp;  市</span>
                                <select id="ccode" name="ccode" style="width:155px; margin-right:20px;">
                                </select>

                                <span class="check_font">号码状态 </span>
                                <select id="callSwitchFlag" name="callSwitchFlag" style="width:155px; margin-right:20px;">
                                    <option value="">全部</option>
                                    <option value="00">开启</option>
                                    <option value="01">禁用</option>
                                </select>
                                <span class="check_font">长途权限</span>
                                <select name="longDistanceFlag" style="width:155px;">
                                    <option value="">全部</option>
                                    <option value="00">开启</option>
                                    <option value="01">关闭</option>
                                </select>
                            </p>
                            <p class="f_right">
                                <button type="button" class="md_btn" style="margin-left:10px;margin-top:-10px;" onclick="search();">查询</button>
                            </p>
                        </div>
                    </div>
                </div>

                <div class="application clear" style="margin-left:80px;">
                    <table class="table_style Nunber_table yzj_table"  id="eccNum">
                        <thead>
                        <tr>
                            <th width="7%">序号</th>
                            <th width="14%">接听号码类型</th>
                            <th width="11%">接听号码</th>
                            <th width="11%">外显号码</th>
                            <th width="10%">分机号码</th>
                            <th width="7%">省份</th>
                            <th width="9%">城市</th>
                            <th width="13%">创建时间</th>
                            <th width="9%">长途状态</th>
                            <th width="9%">号码状态</th>
                        </tr>
                        </thead>
                        <tbody id="czjllog">

                        </tbody>
                    </table>
                    <div id="pagination" class="f_right app_sorter"></div>
                </div>
                <!--应用查看 end-->


                <!--开启/禁用号码 弹层-->
                <div class="modal-box  phone_t_code" id="ustatus" style="display:none;">
                    <div class="modal_head">
                        <h4 id="stitle">开启/禁用号码</h4>
                        <input id="ustatusparams" type="hidden">
                        <div class="p_right"><a href="javascript:showDiv('ustatus');" class="common_icon close_txt"></a></div>
                    </div>
                    <div class="modal-common_body mdl_pt_body" style="margin-left:-15px;padding-top:15px;">
                        <div class="app_delete_main">
                            <p class="app_delete_common" id="smsg"><i class="attention"></i>确定开启/关闭长途？</p>
                        </div>
                    </div>
                    <div class="mdl_foot" style="margin-left:140px;padding-top:20px;">
                        <button type="button" class="true" id="tstatus">确&nbsp;&nbsp;认</button>
                        <button type="button" class="false" onclick="showDiv('ustatus');">取&nbsp;&nbsp;消</button>
                    </div>
                </div>
                <!--开启/禁用号码 弹层 end-->
            </div>
        </div>
    </div>
</section>



<script id="czlog" type="text/x-jquery-tmpl">
        <tr class="date_hg">
            <td>\${num}</td>
            <td>\${numTypes}</td>

            {{if numType =='01'}}
			  <td>\${sipphone}</td>
		    {{/if}}

		    {{if numType == '02'}}
			  <td>\${fixphone}</td>
		    {{/if}}

		     {{if numType == '03'}}
			  <td>\${fixphone}</td>
		     {{/if}}

            <%--<td>\${sipphone}</td>--%>
            <td>\${shownum}</td>
            <td>\${subNum}</td>
            <td>\${pname}</td>
            <td>\${cname}</td>
            <td>\${addtime}</td>
            <td class="\${longDistanceCss} blueText">
                {{html longDistanceHtml}}
            </td>
            <td class="\${callSwitchCss} blueText">
                {{html callSwitchHtml}}
            </td>
        </tr>
</script>

<script src="${appConfig.resourcesRoot}/js/common/common.js"></script>
<script src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
<script src="${appConfig.resourcesRoot}/js/page/paging.js"></script>

<script type="text/javascript">
    var cityInfoArray = {};
    initPCArea('pcode','ccode','全部','<c:url value="/"/>');
    $(function(){
        cityInfoArray = getAppInfo("<c:url value='/seNumMgr/getCityInfo'/>","id","city");

        $('#openFlag').click(function() {
            $('#limit').show();
        });
        $('#closeFlag').click(function() {
            $('#closelimit').show();
            $('#limit').hide();
        });

        $("input[name='limitFlag']:checked").click();
    });

    //查询分机号码记录
    var table =
            $("#eccNum").page({
                url:"<c:url value='/eccApp/pageEccNum'/>",
                integralTemplate : "#czlog",
                integralBody : "#czjllog",
                param:{"appid":"${appInfo.appid}"},
                dataRowCallBack : dataRowCallBack
            });
    function search() {
        var formData = {};
//        $("#searchForm").find(":input[id]").each(function(i){
//            formData[$(this).attr("id")] = $(this).val().trim();
//        });

        $("#searchForm").find(":input[name]").each(function(){
            formData["params["+$(this).attr('name')+"]"] = $.trim($(this).val());
        });

        table.reload(formData);
    }


    $("#tstatus").on("click", function() {
        showDiv("ustatus");
        $.ajax({
            type:"post",
            url:"<c:url value="/eccApp/updateEccstatus"/>" + $("#ustatusparams").val(),
            dataType:"json",
            async:false,
            //data:{id:id},
            success : function(data) {
                if(data.code == 'ok'){
                    table.reload();
                }else{
                    alert("操作失败！");
                }
            },
            error:function(){
                alert("请求失败！");
            }
        });
    });


    function showDiv(divid){
        $('#' + divid ).hide();
        $('#shadeWin').hide();
    }

    function updateSipStatus(id,type,status,title,msg){

        var smsg = '<i class="attention"></i>' + msg;
        $("#stitle").html(title);
        $("#smsg").html(smsg);

        $("#shadeWin").show();
        $("#ustatus").show();

        $("#ustatusparams").val("?id=" + id + "&" + type + "=" + status);

    }




    function dataRowCallBack(row,num) {
        //序号列
        row.num = num;


        // TODO:
        if (row.addtime) {
            row.addtime = o.formatDate(row.addtime, "yyyy-MM-dd hh:mm:ss");
        }
        if (row.numType == "01") {
            row.numTypes = "SIP号码";
        }
        if (row.numType == "02") {
            row.numTypes = "手机";
        }
        if (row.numType == "03") {
            row.numTypes = "固话";
        }
        if (row.numType == null) {
            row.numTypes = "";
        }
        if (row.shownum == null) {
            row.shownum = "";
        }
        if (row.subNum == null) {
            row.subNum = "";
        }


        if (row.longDistanceFlag == "00") {
            row.longDistanceHtml = '<span class="pointer" onclick="updateSipStatus(\''+ row.id +'\',\'longDistanceFlag\',\'01\',\'关闭长途\',\'确定关闭长途？\')" title="点击关闭长途">关闭</span>';
            row.longDistanceCss = "blueBg";
        } else {
            row.longDistanceHtml = '<span class="pointer" onclick="updateSipStatus(\''+ row.id +'\',\'longDistanceFlag\',\'00\',\'开启长途\',\'确定开启长途？\')" title="点击开启长途">开启</span>';
            row.longDistanceCss = "redBg";
        }

        if (row.callSwitchFlag == "00") {
            row.callSwitchHtml = '<span class="pointer" onclick="updateSipStatus(\''+ row.id +'\',\'callSwitchFlag\',\'01\',\'禁用号码\',\'确定禁用号码？\')" title="点击禁用号码">禁用</span>';
            row.callSwitchCss = "blueBg";
        } else {
            row.callSwitchHtml = '<span class="pointer" onclick="updateSipStatus(\''+ row.id +'\',\'callSwitchFlag\',\'00\',\'开启号码\',\'确定开启号码？\')" title="点击开启号码">开启</span>';
            row.callSwitchCss = "redBg";
        }

        return row;
    }



    //保存消费限额信息
    $('#saveQuota').click(function() {
        var appid = '${appInfo.appid}';
        var id = '${appInfo.id}';
        var quota = '${appInfo.quota}';
        var limitFlag = $("input[name='limitFlag']:checked").val();
        var limitStatus = $('#limitStatus').val();
        var quota_ = $('#quota_').val();
        var quota1 = $('#quota1').val();
        $.ajax({
            type: "post",//使用post方法访问后台
            dataType: "json",//返回json格式的数据
            url: "<c:url value='/eccApp/updatelimitFlag'/>",
            data:{'appid':appid,'limitStatus':limitStatus,'limitFlag':limitFlag,'quota11':quota1,'quota1_':quota_ ,'quota2':quota,'id':id},//要发送的数据
            success: function (data) {
                if(data.code=='00') {
                    window.location.reload();
                }else{
                    alert("修改消费限额异常");
                }
            },
            error: function (data) {
                alert("出现异常");
            }
        });
    });


</script>
</body>
</html>
