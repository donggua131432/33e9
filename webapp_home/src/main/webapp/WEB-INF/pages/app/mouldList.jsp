<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>控制中心</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <style>
        #txt_template .error {margin-left: 10px;}
    </style>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/validate-extend.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script id="integralTemplate" type="text/x-jquery-tmpl">
	<tr class="date_hg">
		<td>\${num}</td>
		<td>\${name}</td>
		<td>\${type}</td>
		<td>\${id}</td>
		<td>
		{{if audit_status=='02'}}
		<font color="#FF0000">审核不通过 </font>
		{{/if}}
		{{if audit_status=='00'}}
		审核中...
		{{/if}}
		{{if audit_status=='01'}}
		审核通过
		{{/if}}
		</td>

		<td>
		{{if audit_status=='00'||audit_status=='01'}}

				  <a href="javascript:void(0);" onclick="tempView('\${id}');">查看  </a>
				  <a href="javascript:void(0);" onclick="tempCanel('\${id}','\${appid}','\${v_path}')";><font color="#FF0000">  删除 </font></a>
		{{/if}}
		{{if audit_status=='02'}}
				  <a href="javascript:void(0);" onclick="tempView('\${id}');">查看   </a>
				  <a href="javascript:void(0);" onclick="tempSubmit('\${id}','\${appid}');">重新提交</a>
				  <a href="javascript:void(0);" onclick="tempCanel('\${id}','\${appid}','\${v_path}');"><font color="#FF0000">删除</font></a>
		{{/if}}
		</td>
	</tr>
</script>
</head>
<body>
<!--遮罩start-->
<div id="shadeWin" class="shade_on_win" style="display: none;"></div>
<!--遮罩end-->

<!-- header start -->
<jsp:include page="../common/controlheader.jsp"/>
<!-- header end -->

<section>
    <div id="sec_main">

        <!-- 左侧菜单 start -->
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('kf','appMgr','mouldList');</script>
        <!-- 左侧菜单 end -->

        <div class="main3">

            <div class="container1" >
                <div class="msg">
                    <h3>模板管理</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="ringDroid">
                    <div class="recordNote" style="height: 120px;">
                        <span class="WarmPrompt">温馨提醒：</span>
                        <p>1、模板审核通过后可通过接口调用使用，审核不通过或待审核模板不能使用。</p>
                        <p>2、目前审核通过后不能进行编辑，如有新的模板需求，可以提交新模板进行审核，并删除旧模板。</p>
                        <p>3、上传语音文件最大10M。</p>
                    </div>
                    <div class="record_select">
                        <div class="Open_interface_record re_span_common">
                            <span class="check_font">模板名称：</span>
                            <input type="text" id="Mname" name="name" class="input_style"/>
                            <span class="check_font call_left">模板&nbsp;ID：</span>
                            <input type="text" id="Mid" name="id" class="input_style"/>
                            <span class="check_font call_left">审核状态：</span>
                            <select name="On_state" id="selectStatus" style="width:177px;">
                                <option value="">请选择</option>
                                <option value="01">审核通过</option>
                                <option value="02">审核不通过</option>
                                <option value="00">待审核</option>
                            </select>
                        </div>
                    </div>
                    <div class="clear">
                        <span class="createTemplate" onclick="openAddDispatchDialog('add','');"><b class="New_icon"></b>创建模板</span>
                        <button type="button" id="tempSearch" class="md_btn f_right" style="margin-top: -15px">查询</button>
                    </div>


                    <div class="application">
                        <table class="table_style" id="apptable">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>模板名称</th>
                                <th>模板类型</th>
                                <th>模板&nbsp;ID</th>
                                <th>审核状态</th>
                                <th>操作</th>
                                <%--<th style="width:15.6%">审核意见</th>--%>
                            </tr>
                            </thead>
                            <tbody id="integralBody">

                            </tbody>
                        </table>
                        <div class="f_right app_sorter"  id="pagination"></div>
                    </div>
                </div>

                <%--<input type="hidden"  id="appid" name="appid" value="${appid}"/>--%>


                <!--创建模板文本文件-->
                <div class="modal-box  phone_t_code" id="addTempVoice" style="display: none">
                    <div class="modal_head">
                        <h4>创建模板</h4>
                        <div class="p_right"><a href="javascript:void(0);" onclick="closeAddDispatchDialog('addTempVoice');" class="common_icon close_txt"></a></div>
                    </div>

                    <form id="voiceForm" name="voiceForm" action="<c:url value='/mouldMgr/save'/>" method="post" enctype="multipart/form-data">
                        <div class="modal-common_body mdl_pt_body">
                            <div style="margin-top:20px;">
                                <div>
                                    <label  class="create_app_title_left APP_state font14" >模板类型:</label>
                                    <select name="type" id="templateStatus" onchange="changeReportType(this);" style="width:177px;">
                                        <option value="00" selected="selected">语音文件</option>
                                        <option value="01">文本文件</option>
                                    </select>
                                </div>
                                <div>
                                    <label  class="create_app_title_left APP_state font14" ></label>
                                </div>

                                <div class="create_app_common" id="name_template">
                                    <label class="create_app_title_left APP_state font14">模板名称:</label>
                                    <span class="create_app_title_left"><input type="text" onkeyup="checkVoiceName(this);" id="name" name="name" maxlength="10" class="l_input_style" style="width:177px;"/></span>
                                    <%--<div id="errorVoiceNameMsg" class="Error_box" style="margin: 15px 0 -25px 103px;width: 400px;"></div>--%>
                                </div>

                                <div class="marTop30" id="voice_template" style="display:block ">
                                    <input type="text" id="voiceUrl1" name="voiceFile1" class="l_input_style" style="margin-left:103px;width:250px;"/>
                                    <span class="parentPosition">
                                        <input type="hidden"  id="appid" name="appid" value="${appid}"/>
                                       <button type="button" class="browse">浏览</button>
                                       <input type="file" class="childPosition" name="voiceFile" id="vUrl" onchange="document.getElementById('voiceUrl1').value=this.value"/>
                                    </span>
                                    <div><i class="formatTip ringTip">*支持wav或mp3格式，且文件小于10M。</i></div>
                                    <div id="errormsg" class="Error_box" style="margin: 15px 0 -25px 103px;width: 400px;">
                                        <c:if test="${not empty error}">
                                            <c:if test="${error eq 'error'}"><label class="error">上传失败，请上传WAV或MP3格式铃声！</label></c:if>
                                            <c:if test="${error eq 'size'}"><label class="error">上传失败，您上传的文件大于10M！ </label> </c:if>
                                            <c:if test="${error eq 'null'}"><label class="error">文件为空，请选择铃声上传 </label></c:if>
                                            <c:if test="${error eq 'repeat'}"><label class="error">提交失败，该应用存在待审核的铃声 </label></c:if>
                                        </c:if>

                                    </div>

                                </div>

                                <div  id="txt_template" style="display:none;margin-top:-20px;">
                                    <label  class="create_app_title_left APP_state font14" >文本内容:</label><br/>
                                    <textarea name="tContent" id="tContent" maxlength="200" class="padding5 marLeft12 marTop5" cols="65" rows="5"></textarea>
                                    <%--<div id="errormsg3" class="Error_box" style="margin: 15px 0 -25px 103px;width: 400px;">--%>
                                    <%--</div>--%>
                                </div>
                        </div>
                        </div>
                        <div class="mdl_foot" style="margin-left:175px;" id="voice_submit">
                            <button type="button" id="voiceButton" name="voiceButton" class="true">提&nbsp;&nbsp;交</button>
                        </div>
                        <div class="mdl_foot" style="text-align: center;margin-left:-12px;" id="txt_submit" style="display:none">
                            <button type="button" id="txtButton" name="txtButton" class="true" style="display:none;margin-top:-10px" >提&nbsp;&nbsp;交</button>
                        </div>
                    </form>
                </div>



                <!-- 查看模板语音文件-->
                <div class="modal-box  phone_t_code" id="viewTemp_box" style="display: none">
                    <div class="modal_head">
                        <h4>查看模板</h4>
                        <div class="p_right"><a href="javascript:void(0);" onclick="closeAddDispatchDialog('viewTemp_box');" class="common_icon close_txt"></a></div>

                    </div>

                    <div class="modal-common_body mdl_pt_body">
                        <div>
                            <label  class="create_app_title_left APP_state font14">模板类型:</label>
                            <span id="temp_type">语音文件</span>
                        </div>
                        <div class="marTop20">
                            <label  class="create_app_title_left APP_state font14" >模板名称:</label>
                            <span id="tempName">${tempVoice.name}</span>
                        </div>
                        <div class="marTop20">
                            <label  class="create_app_title_left APP_state font14" >模板&nbsp;&nbsp;ID:</label>
                            <span  style="margin-left:3px;" id="tempID"></span>
                        </div>
                        <div class="marTop20">
                            <label  class="create_app_title_left APP_state font14" >审核状态:</label>
                            <span id="tempStatus">审核不通过</span>
                        </div>
                        <input type="hidden" id="tempUrl" />
                        <div class="marTop20" id="statusOUT" style="display: none">
                            <label  class="create_app_title_left APP_state font14" >审核不通过原因:</label>
                            <span class="checkTip" id="checkTip">
                                ${tempVoice.auditCommon}
                            </span>
                        </div>

                        <div class="marTop20" id="text_temp" style="display: none">
                            <label  class="create_app_title_left APP_state font14" >文本内容:</label>
                                 <span class="checkTip" id="reset_txt">
                                     ${tempVoice.tContent}
                                 </span>
                        </div>

                        <div class="marTop20" id="temp_genStatus" style="display: none">
                            <label  class="create_app_title_left APP_state font14" >生成状态:</label>
                            <span id="genStatus">未生成</span>
                        </div>

                        <div class="marTop20"  >
                            <label  class="create_app_title_left APP_state font14" ></label>
                        </div>

                        <button type="button" id="downButton" onclick="downloadVoice();"class="downLoadVoiceBtn">下载语音文件</button>
                    </div>
                </div>




                <!-- 重新提交语音文件-->
                <div class="modal-box  phone_t_code"  id="submitTemp_box" style="display: none;top:50%;margin-top:-240px;height:480px;">
                    <div class="modal_head">
                        <h4>重新提交模板</h4>
                        <div class="p_right"><a href="javascript:void(0);" onclick="closeAddDispatchDialog('submitTemp_box');" class="common_icon close_txt"></a></div>

                    </div>
                    <form id="voiceSubmit" name="voiceSubmit" action="<c:url value='/mouldMgr/update'/>" method="post" enctype="multipart/form-data">

                        <div class="modal-common_body mdl_pt_body">
                            <div>
                                <input type="hidden" id="id" name="id" value="${tempVoice.id}" />
                                <input type="hidden" id="subtype" name="type" value="${tempVoice.type}"/>
                                <input type="hidden" id="appid2" name="appid" />
                                <label  class="create_app_title_left APP_state font14">模板类型:</label>
                                <span id="type">语音文件</span>
                            </div>
                            <div class="marTop20">
                                <label  class="create_app_title_left APP_state font14" >模板名称:</label>
                                <span id="submitName">${tempVoice.name}</span>
                            </div>
                            <div class="marTop20">
                                <label  class="create_app_title_left APP_state font14" >模板&nbsp;&nbsp;ID:</label>
                                <span  style="margin-left:3px;" id="submitID">${tempVoice.id}</span>
                            </div>
                            <div class="marTop20">
                                <label  class="create_app_title_left APP_state font14" >审核状态:</label>
                                <span id="tempS">审核不通过</span>
                            </div>
                            <div class="marTop20" id="statusOUT1">
                                <label  class="create_app_title_left APP_state font14" >审核不通过原因:</label>
                                <span class="checkTip" id="commont">
                                    ${tempVoice.auditCommon}
                                </span>
                            </div>
                            <div class="marTop20"  >
                                <label  class="create_app_title_left APP_state font14" ></label>
                            </div>


                            <div  id="txt_template_sub" style="display:none;margin-top:-20px;">
                                <label  class="create_app_title_left APP_state font14" >文本内容:</label><br/>
                                    <textarea name="tContent" id="tContent_sub"  maxlength="200" class="padding5 marLeft12 marTop5" cols="65" rows="5"></textarea>
                            </div>


                            <button type="button" id="downSubButton" onclick="downloadVoice();" class="downLoadVoiceBtn" >下载语音文件</button>


                            <div class="marTop20" id="voice_template_sub">
                                <label  class="create_app_title_left APP_state font14" >替换内容:</label><br/>
                                <input type="text" id="voiceUrl2" name="voiceFile2" class="l_input_style" style="margin-left:10px;width:250px;"/>
                                    <span class="parentPosition">
                                       <button type="button" class="browse">浏览</button>
                                       <input type="file" class="childPosition" name="voiceFile" id="vUrl2" onchange="document.getElementById('voiceUrl2').value=this.value"/>

                                    </span>
                                <div><i class="reset_formatTip ringTip">*支持wav或mp3格式，且文件小于10M。</i></div>
                                <div id="errormsg2" class="Error_box" style="margin: 15px 0 -25px 10px;width: 400px;">
                                    <c:if test="${not empty error}">
                                        <c:if test="${error eq 'errorSub'}"><label class="error">上传失败，请上传WAV格式铃声！ </label></c:if>
                                        <c:if test="${error eq 'size'}"><label class="error">上传失败，您上传的文件大于10M！ </label> </c:if>
                                        <c:if test="${error eq 'null'}"><label class="error">文件为空，请选择铃声上传 </label></c:if>
                                        <c:if test="${error eq 'repeat'}"><label class="error">提交失败，该应用存在待审核的铃声 </label></c:if>
                                    </c:if>
                                </div>
                            </div>

                            </div>
                            <div class="mdl_foot" style="margin-left:225px;" id="voice_submit_sub">
                                <button type="button" id="submitTemp" class="true" >提&nbsp;&nbsp;交</button>
                            </div>
                            <div class="mdl_foot" style="margin-left:225px;" id="txt_submit_sub" style="display:none">
                                <button type="button" id="txtSubmitTemp" class="true" style="display:none;margin-top:-10px">提&nbsp;&nbsp;交</button>
                            </div>

                    </form>
                </div>


                <!--清空模板-->
                <div class="modal-box  phone_t_code" id="delTemplatevois"  style="display: none;">
                    <div class="modal_head">
                        <h4>删除模板</h4>
                        <div class="p_right"><a href="javascript:void(0);" onclick="closeAddDispatchDialog('delTemplatevois');" class="common_icon close_txt"></a></div>

                    </div>
                    <div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
                        <input type="hidden" id="appid1" name="appid" />
                        <input type="hidden" id="vType" name="vType"  />
                        <input type="hidden" id="vPath1" name="vPath"  />
                        <div class="app_delete_main">
                            <p class="app_delete_common"><i class="attention"></i>确定要删除该模板？</p>
                            <p class="delTemplateTip">删除后将不能继续使用该模板下发语音通知，</p>
                            <p class="delTemplateTip">但已请求成功语音通知会正常下发！</p>
                        </div>
                    </div>
                    <div class="mdl_foot" style="margin-left:140px;">
                        <button type="button" id="tempDelButton" class="false">确&nbsp;&nbsp;认</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="true" onclick="closeAddDispatchDialog('delTemplatevois');">取&nbsp;&nbsp;消</button>
                    </div>
                </div>
        </div>
        </div>
    </div>
</section>

<script src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
<script src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
<script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>

<script type="text/javascript">
    var tempid;
    var appids;
    var vPath;
    var apptable;
    var siginForm;
    var siginSubForm;
    function tempCanel(id,appid,vpath){
        $("#shadeWin").show();
        $("#delTemplatevois").show();
        tempid = id;
        appids =appid;
        vPath = vpath;
        $('#appid1').val(appid);
        $('#vPath1').val(vPath);
    }


    function downloadVoice(){
        var a= $('#tempUrl').val();
        window.location.href="<c:url value='/mouldMgr/download'/>?url="+$('#tempUrl').val()+"&vType="+$('#vType').val();
    }
    function tempView(id){
        var id = id;
        $.ajax({
            type: "post",//使用post方法访问后台
            dataType: "json",//返回json格式的数据
            url: "<c:url value='/mouldMgr/view'/>",
            data:{'id':id},//要发送的数据
            success: function(data){//data为返回的数据，在这里做数据绑定
                if(data){
                    $('#shadeWin').show();
                    $('#viewTemp_box').show();
                    $('#tempID').text(data.id);
                    $('#submitID').text(data.id);
                    $('#tempName').text(data.name);
                    $('#submitName').text(data.name);
                    $('#tempUrl').val(data.vUrl);
                    $('#commont').text(data.auditCommon);
                    $('#appid1').val(data.appid);
                    $('#appid2').val(data.appid);
                    $('#vPath1').val(data.vPath);
                    $('#vType').val(data.type);

                    if (data.type =='00'){
                        $('#temp_type').text("语音文件");
                        $('#downButton').show();
                        $('#text_temp').hide();
                        if (data.auditStatus =='00'){
                            $('#tempStatus').text("审核中");
                            $('#statusOUT').hide();
                        }else if (data.auditStatus =='01'){
                            $('#tempStatus').text("审核通过");
                            $('#statusOUT').hide();
                        }else if (data.auditStatus =='02'){
                            $('#tempStatus').text("审核不通过");
                            $('#statusOUT').show();
                            $('#checkTip').text(data.auditCommon);
                        }
                    }else if (data.type =='01'){
                        $('#downButton').hide();
                        $('#temp_type').text("文本文件");
                        $('#text_temp').show();
                        $('#reset_txt').text(data.tContent);
                        if (data.auditStatus =='00'){
                            $('#tempStatus').text("审核中");
                            $('#statusOUT').hide();
                        }else if (data.auditStatus =='01'){
                            $('#tempStatus').text("审核通过");
                            $('#temp_genStatus').show();
                            $('#downButton').show();
                            if(data.vPath){
                                $('#genStatus').text("已完成");
                                $('#downButton').show();
                            }else{
                                $('#genStatus').text("生成中");
                                $('#downButton').hide();
                            }
                            $('#statusOUT').hide();
                        }else if (data.auditStatus =='02'){
                            $('#tempStatus').text("审核不通过");
                            $('#statusOUT').show();
                            $('#checkTip').text(data.auditCommon);
                        }
                    }




                }else{
                    alert("出现异常");
                }

            },
            error  : function(data){
                alert("出现异常");
            }
        });
    }

    function tempSubmit(id,appid){
        $("#errormsg2").html("");
        var id = id;
        appids =appid;
        $.ajax({
            type: "post",//使用post方法访问后台
            dataType: "json",//返回json格式的数据
            url: "<c:url value='/mouldMgr/view'/>",
            data:{'id':id,'appid':appid},//要发送的数据
            success: function(data){//data为返回的数据，在这里做数据绑定
                if(data){
                    $('#shadeWin').show();
                    $('#submitTemp_box').show();
                    $('#submitID').text(data.id);
                    $('#submitName').text(data.name);
                    $('#tempUrl').val(data.vUrl);
                    $('#commont').text(data.auditCommon);
                    $('#id').val(data.id);
                    $('#appid1').val(data.appid);
                    $('#appid2').val(data.appid);
                    $('#vPath1').val(data.vPath);
                    $('#subtype').val(data.type);

                    if (data.type =='00'){
                        $('#type').text("语音文件");
                        $("#voice_template_sub").show();
//                        $('#txt_template_sub').hide();

                    }else if (data.type =='01'){
                        $('#type').text("文本文件");
                        $("#voice_template_sub").hide();
                        $('#txt_template_sub').show();
                        $('#tContent_sub').text(data.tContent);
                        $('#downSubButton').hide();
                        $('#voice_submit_sub').hide();
                        $('#txt_submit_sub').show();
                        $('#txtSubmitTemp').show();


                    }

                }else{
                    alert("出现异常");
                }

            },
            error  : function(data){
                alert("出现异常");
            }
        });
    }

    $(function(){
        <c:if test="${error eq 'error'}">
            openAddDispatchDialog();
        </c:if>
        <c:if test="${error eq 'errorSub'}">
            $('#shadeWin').show();
            $('#submitTemp_box').show();
        </c:if>
        $('#tempSearch').click(function() {
            var param = {
                params : { auditStatus : $("#selectStatus").val() ,name :$("#Mname").val(),id :$("#Mid").val()}
            };
            $.fn.reload(param);
        });
        $('#tempDelButton').click(function() {
            var id = tempid;
            var appid = appids;
            var vPath = $('#vPath1').val();
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/mouldMgr/del'/>",
                data:{'id':id,'appid':appid,'vPath':vPath},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data){
                        $('#shadeWin').hide();
                        $('#delTemplatevois').hide();
                        $('#tempSearch').click();
                    }else{
                        alert("出现异常");
                    }

                },
                error  : function(data){
                    alert("出现异常");
                }
            });
        });
    })

    //新增或编辑话务调度的对话框
    function openAddDispatchDialog(dialogType,dispatchId){
        $("#shadeWin").show();
        $("#addTempVoice").show();
    }

    //关闭对话框
    function closeAddDispatchDialog(dialogId){
        $("#shadeWin").hide();
        $("#"+dialogId).find("form :input").each(function(){
            if($(this).attr("type") == "checkbox"){
                $(this).removeAttr("checked");
            }else if (this.nodeName == "SELECT"){
            }
            else{
                $(this).val("");
            }
            $(this).removeClass("error");
        });
        $("#"+dialogId).hide();
        apptable.reload({},true);
        siginForm.resetForm();
    }



    // 语音模板大小
    function getFileSize(element) {
        var fileSize = 0;
        if(element.files){ // element.files 是chrome，firefox等浏览器的对象，如果是ie的话他的值就是false

            var thisFile = element.files[0]; // 获取文件的对像
            fileSize = thisFile.size;//获取当前上传的文件的大小
        } else { // 如果是ie
            try {
                var obj_img = document.getElementById('tempimg');
                obj_img.dynsrc = element.value;
                fileSize = obj_img.fileSize;
            } catch (e){
                fileSize = 0;
            }
        }
        return fileSize;
    }

    $(function(){
        var appids ="${appid}";
        siginForm = $("#voiceForm").validate({
            rules: {
                name: {
                    required: true,
                    appName:true,
                    remote: {
                        url: "<c:url value='/mouldMgr/nameUnique'/>?appid="+appids,     //后台处理程序
                        type: "post",               //数据发送方式
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            name: function() {
                                return $("#name").val();
                            }
                        }
                    }
                },
                tContent : { // 文本文件
                    required: true,
                    tContent:true
                }
            },
            messages: {
                name: {
                    required: '请输入模板名称！',
                    appName : '格式错误！',
                    remote : '该模板名称已存在！'
                },
                tContent: {
                    required: '文本模板内容不能为空！',
                    tContent : '格式不正确，请重新输入！',
                }
            }

        });

        siginSubForm = $("#voiceSubmit").validate({
            rules: {
                tContent : { // 文本文件
                    required: true,
                    tContent:true
                }
            },
            messages: {
                tContent: {
                    required: '文本模板内容不能为空！',
                    tContent : '格式不正确，请重新输入！',
                }
            }

        });


        $('#voiceButton').click(function() {
//            console.log("start");
            var url = $("#vUrl").val();
            $("#errormsg").html('');

            if (!url){
                $("#errormsg").html('<label class="error">文件为空，请选择铃声上传! </label>');
                return;
            }

            // 格式是否正确
            if ((".WAV" != url.substring(url.lastIndexOf(".")).toLocaleUpperCase())&&(".MP3" != url.substring(url.lastIndexOf(".")).toLocaleUpperCase())) {
                $("#errormsg").html('<label class="error">文件格式不正确! </label>');
                return;
            }

            // 文件过大
            if (getFileSize(document.getElementById('vUrl')) > 10485760) {
                $("#errormsg").html('<label class = "error">上传失败，您上传的文件大于10M! </label>');
                return;
            }

//            $(this).attr("disabled","disabled");
            $('#voiceForm').submit();
        });

        $('#txtButton').click(function() {
//            $("#errormsg3").html('');
//            var tContent = $("#tContent").val();
//            $("#errormsg3").html('');
//            if (tContent == '' ||tContent == null ){
//                $("#errormsg3").html('<label class="error">内容3为空，请输入文本! </label>');
//                return;
//            }
            $('#voiceForm').submit();
        });


        $('#submitTemp').click(function() {
//            console.log("start");
            var url = $("#vUrl2").val();
            $("#errormsg2").html('');

            if (!url){
                $("#errormsg2").html('<label class="error">文件为空，请选择铃声上传! </label>');
                return;
            }

            // 格式是否正确
            if ((".WAV" != url.substring(url.lastIndexOf(".")).toLocaleUpperCase())&&(".MP3" != url.substring(url.lastIndexOf(".")).toLocaleUpperCase())) {
                $("#errormsg2").html('<label class="error">文件格式不正确! </label>');
                return;
            }

            // 文件过大
            if (getFileSize(document.getElementById('vUrl2')) > 10485760) {
                $("#errormsg2").html('<label class = "error">上传失败，您上传的文件大于10M! </label>');
                return;
            }

//            $(this).attr("disabled","disabled");
            $('#voiceSubmit').submit();
        });

        $('#txtSubmitTemp').click(function() {
            $("#errormsg2").html('');
//            $(this).attr("disabled","disabled");
            $('#voiceSubmit').submit();
        });


    });

    function checkVoiceName(obj){
        //匹配只能输入数字，字母，汉字
        var reg = /^([\u4e00-\u9fa5_a-zA-Z0-9]{0,10})$/;
        if(reg.test($(obj).val().trim())){
            $("#errorVoiceNameMsg").html('');
        }else{
            $("#errorVoiceNameMsg").html('<label class="error">格式错误，请重新输入!</label>');
        }
    }
    // 模板信息列表
    apptable =
    $("#apptable").page({
        url:"<c:url value='/mouldMgr/query'/>?appid=${appid}",
        integralTemplate : "#integralTemplate",
        integralBody : "#integralBody",
        pageSize:5,
        dataRowCallBack:dataRowCallBack
    });


    function search() {
        var param = {
            params : { auditStatus : $("#selectStatus").val() ,name :$("#Mname").val(),id :$("#Mid").val()}
        };
        $.fn.reload(param);

    }



    function dataRowCallBack(row,num) {
        //序号列
        row.num = num;
        if (row.type == '00') {
            row.type = "语音";
        }
        if (row.type == '01') {
            row.type = "文本";
        }
        return row;
    }

    function changeReportType(obj){
        var reportType = $(obj).val();
        if(reportType == "00"){
            $("#voice_template").show();
            $("#name_template").show();
            $("#voice_submit").show();

            $("#txt_template").hide();
            $("#txt_submit").hide();
        }else if(reportType == "01"){
            $("#voice_template").hide();
//            $("#name_template").hide();
            $("#voice_submit").hide();

            $("#txt_template").show();
            $("#txt_submit").show();
            $("#txtButton").show();
        }
    }

</script>

</body>
</html>
