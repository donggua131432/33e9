<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>控制中心页面</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/sui.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script id="sipPhoneNumberTemplate" type="text/x-jquery-tmpl">
        <tr class="date_hg">
            <td><input type="checkbox" name="checkbox" value="\${id}"></td>
            <td id="sipphonecheck">\${sip_phone}</td>
            <td>\${number}</td>
            <td>
            {{if audit_status=='不通过'}}
            \${showNum}
            {{/if}}
            {{if audit_status!='不通过'}}
            \${show_num}
            {{/if}}
            </td>
            <td>\${audit_status}</td>
            <td>\${pname}</td>
            <td>\${cname}</td>
            <td>\${pwd}</td>
            <td>\${sip_realm}</td>
            <td>\${ip_port}</td>
            <td>\${atime}</td>
            <td class="\${longDistanceCss} blueText">
                {{html longDistanceHtml}}
            </td>
            <td class="\${callSwitchCss} blueText">
                {{html callSwitchHtml}}
            </td>
            <td>
                <a href="javascript:void(0);" onclick="editShowNum('\${id}');">编辑</a>
            </td>
        </tr>
    </script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
    <script type="text/javascript">
        var sipPhoneNumberTable;
        $(function(){
            initPCArea('pcode','ccode','全部','<c:url value="/"/>');

            sipPhoneNumberTable = $("#sipPhoneNumberTable").page({
                url:"<c:url value='/sipPhoneAppMgr/getSipPhoneNumList'/>",
                pageSize:30,
                integralBody:"#sipPhoneNumberTbody",// 将模版加入到某个元素的ID
                integralTemplate:"#sipPhoneNumberTemplate",// tmpl中的模版ID
                pagination:"#sipPhoneNumberPagination", // 分页选项容器
                param:{"appid":"${appInfo.appid}"},
                dataRowCallBack:dataRowCallBack
            });

            function dataRowCallBack(row,num) {
                if (row.atime) {
                    row.atime = o.formatDate(row.atime, "yyyy-MM-dd hh:mm:ss");
                }

                if (row.audit_status == "00") {
                    row.audit_status = "审核中";
                } else if (row.audit_status == "01") {
                    row.audit_status = "通过";
                }else if (row.audit_status == "02") {
                    row.audit_status = "不通过";
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

                var checkbox = "";
                if (!checkbox) {
                    checkbox = document.getElementById("checkbox");
                }
                if (checkbox.checked) {
                    checkbox.checked = false;
                }

                return row;
            }
            // 全选框
            $("#checkbox").on("change", function() {
                console.log(this.checked);
                if (this.checked) {
                    $("#sipPhoneNumberTbody input[type='checkbox']").each(function(i) {
                        this.checked = true;
                    });
                } else {
                    $("#sipPhoneNumberTbody input[type='checkbox']").each(function(i) {
                        this.checked = false;
                    });
                }
            });

            $('#delete').click(function() {
                var strId = getid();
                $.ajax({
                    type: "post",//使用post方法访问后台
                    dataType: "json",//返回json格式的数据
                    url: "<c:url value='/sipPhoneAppMgr/deleteSpApplyNum'/>",
                    data:{'strId':strId},//要发送的数据
                    success: function (data) {
                        if(data.code=='00') {
                            $("#sipPhoneNumberTable").reload();
                            $('#shadeWin').hide();
                            $('#delTemplate').hide();
                        } else {
                            $('#shadeWin').show();
                            $('#choose_message').show();
                        }
                    },
                    error: function (data) {
                        alert("系统异常，请稍后重试！");
                    }
                });
            });

            $('#spNumEditButton').click(function() {
                var showNum = $("#showNum").val();
                var matchNumber = /^[0-9]{5,30}$/;
                if(!matchNumber.test(showNum)){
                    dispatchTipMsg("showTipMsg","请输入合格的外显号码！");
                    return;
                }
                if(showNum==null || showNum==''){
                    dispatchTipMsg("showTipMsg","请输入外显号码！");
                    return;
                }
                var oldShowNum = $("#oldShowNum").val();
                if(showNum == oldShowNum){
                    dispatchTipMsg("showTipMsg","请输入新的外显号码！");
                    return;
                }

                $('#spNumSubmit').submit();
            });

            $("#tstatus").on("click", function() {
                showDiv("ustatus");

                $.ajax({
                    type:"post",
                    url:"<c:url value="/sipPhoneAppMgr/updateSipstatus"/>" + $("#ustatusparams").val(),
                    dataType:"json",
                    async:false,
                    //data:{id:id},
                    success : function(data) {
                        if(data.code == 'ok'){
                            sipPhoneNumberTable.reload();
                        }else{
                            alert("操作失败！");
                        }
                    },
                    error:function(){
                        alert("请求失败！");
                    }
                });
            });
        });
        function getid(){
            //获得checkbox的id值
            var strId = "";
            $("#sipPhoneNumberTbody input[type='checkbox']:checked").each(function(i){
                console.log(i);
                if(i < $("#sipPhoneNumberTbody input[type='checkbox']:checked").length-1){
                    strId += $(this).val() + ",";
                }else{
                    strId += $(this).val();
                }
            });
            return strId;
        }

        function getsipphone(){
            //获得checkbox的sip号码值
            var strSipphone = "";
            $("#sipPhoneNumberTbody input[type='checkbox']:checked").each(function(i){
                console.log(i);
                if(i < $("#sipPhoneNumberTbody input[type='checkbox']:checked").length-1){
                    strSipphone += $(this).parent().next().text() + ",";
                }else{
                    strSipphone += $(this).parent().next().text();
                }
            });
            return strSipphone;
        }

        function flagdelete(){
            var strId = getid();
            var strSipphone = getsipphone();
            if(strId.length > 0){
                $("#shadeWin").show();
                $("#delTemplate").show();
                var p = document.getElementById("psipphone");
                p.innerHTML ="";
                p.innerHTML += strSipphone;

            } else {
                $("#shadeWin").show();
                $("#choose_message").show();
            }
        }
        function showDiv(divid){
            $('#' + divid ).hide();
            $('#shadeWin').hide();
        }

        function dispatchTipMsg(id,obj){
            $("#"+id).text(obj).show();
            setTimeout(function(){
                $("#"+id).hide();
            },2000);
        }

        function updateSipStatus(id,type,status,title,msg){

            var smsg = '<i class="attention"></i>' + msg;
            $("#stitle").html(title);
            $("#smsg").html(smsg);

            $("#shadeWin").show();
            $("#ustatus").show();

            $("#ustatusparams").val("?id=" + id + "&" + type + "=" + status);

        }

        function editShowNum(id){

            $.ajax({
                type:"post",
                url:"<c:url value="/sipPhoneAppMgr/toShowNumEdit"/>",
                dataType:"json",
                async:false,
                data:{id:id},
                success : function(data) {
                    if(data){
                        $("#shadeWin").show();
                        $("#editTemplate").show();

                        $('#sipphoneId').text(data.sipphone);
                        $('#fixphoneId').text(data.fixphone);
                        if (data.auditStatus =='00'){
                            $('#showNum').val(data.auditShowNum);
                        }else{
                            $('#showNum').val(data.showNum);
                        }

                        $('#id').val(data.id);
                        $('#appid').val(data.appid);
                        $('#oldShowNum').val(data.showNum);
                        if (data.auditStatus =='00'){
                            $('#auditStatus').text("审核中");
                            $('#statusOUT').hide();
                        }else if (data.auditStatus =='01'){
                            $('#auditStatus').text("通过");
                            $('#statusOUT').hide();
                        }else if (data.auditStatus =='02'){
                            $('#auditStatus').text("不通过");
                            $('#statusOUT').show();
                            if(data.auditCommon == null){
                                $('#auditCommon').text('无');
                            }else {
                                $('#auditCommon').text(data.auditCommon);
                            }
                        }
                    }else{
                        alert("出现异常");
                    }
                },
                error:function(){
                    dispatchTipMsg("showTipMsg","请求失败！");
                }
            });
        }

        //查询
        function searchSipNumberPool(){
            var formData = {};
            $("#searchForm").find(":input[name]").each(function(){
                formData["params["+$(this).attr('name')+"]"] = $.trim($(this).val());
            });
            $("#sipPhoneNumberTable").reload(formData);
        }

        // 导出报表
        function downloadReport() {
            var formData = {};
            formData["sipphone"] = $("#sipphone").val();
            formData["fixphone"] = $("#fixphone").val();
            formData["showNum1"] = $("#showNum1").val();
            formData["pcode"] = $("#pcode").val();
            formData["ccode"] = $("#ccode").val();
            formData["appid"] = '${appInfo.appid}';
            sipPhoneNumberTable.downloadReport("<c:url value='/sipPhoneAppMgr/downloadSipNumReport'/>",formData);
        }

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
        <script type="text/javascript">showSubMenu('sphone','sipPhoneMgr','sipPhoneNumMgr');</script>
        <!-- 左侧菜单 end -->
        <!--右边内容-->
        <div class="main3">
            <div class="msg">
                <h3>号码管理>应用号码管理</h3>
                <jsp:include page="../common/message.jsp"/>
            </div>
            <div class="container" >
                <div class="application_msg">
                    <div class="app_select Yh_number">
                        <p class="check_font">应用名称:<span class="Yh_numberMg">${appInfo.appName}</span></p>
                        <p class="check_font">APP&nbsp;&nbsp;ID:<span class="Yh_numberMg">${appInfo.appid}</span> </p>
                        <form id="searchForm">
                            <p class="check_font f_left">
                                <span>SIP号码 <input type="text" id="sipphone" name="sipphone" maxlength="32" class="input_W150"></span>
                                <span>绑定固话 <input type="text" id="fixphone" name="fixphone" class="input_W150"></span>
                                <span>外显号码 <input type="text" id="showNum1" name="showNum" class="input_W150"></span>
                                <span>省份<select class="Yh_city" id="pcode" name="pcode"></select></span>
                                <span>城市<select class="Yh_city" id="ccode" name="ccode"></select></span>
                            </p>
                            <button type="button" class="md_btn f_right Yh_numberTop" onclick="searchSipNumberPool();">查询</button>
                        </form>
                        <div class="clear">
                            <span class="open_down_billed" onclick="flagdelete();"><i class="NumberDeal clearNum_txt deleteRM"></i>删除号码</span>
                            <span class="open_down_billed"><i class="view_ico"></i><a class="ahover" href="<c:url value='/sipPhoneAppMgr/applySipPhoneRecord'/>?appid=${appInfo.appid}">查看申请记录</a></span>
                            <span class="open_down_billed f_right" onclick="downloadReport();"><i class="common_icon download_txt"></i>导出记录</span>
                        </div>
                    </div>
                    <!--查询弹层-->
                    <div id="msgDialog" class="modal-box  phone_t_code" style="display: none;">
                        <div class="modal_head">
                            <h4>应用号码列表导出</h4>
                            <div class="p_right"><a href="javascript:void(0);" onclick="showDiv('msgDialog');" class="common_icon close_txt"></a></div>
                        </div>
                        <div class="modal-common_body mdl_pt_body">
                            <div class="test_success test_success_left">
                                <i class="attention"></i>
                                <span>您暂无数据</span>
                            </div>
                        </div>
                    </div>
                    <div class="application clear Yh_numberTop">
                        <table class="table_style Nunber_table" id="sipPhoneNumberTable">
                            <thead>
                            <tr>
                                <th width="5%"><input id="checkbox" type="checkbox">全选</th>
                                <th width="10%">SIP号码</th>
                                <th width="10%">绑定固话号码</th>
                                <th width="10%">外显号码</th>
                                <th width="6%">外显号码状态</th>
                                <th width="5%">省份</th>
                                <th width="7%">城市</th>
                                <th width="8%">认证密码</th>
                                <th width="7%">SIP REALM</th>
                                <th width="10%">IP:PORT</th>
                                <th width="7%">生成时间</th>
                                <th width="6%">长途状态</th>
                                <th width="6%">号码状态</th>
                                <th width="6%">号码管理</th>
                            </tr>
                            </thead>
                            <tbody id="sipPhoneNumberTbody">

                            </tbody>
                        </table>
                        <!--分页-->
                        <div class="f_right app_sorter"  id="sipPhoneNumberPagination"></div>
                        <!--分页 end-->
                        <!--号码编辑 弹层-->
                        <div class="modal-box  phone_t_code Popups" id="editTemplate" style="display: none;">
                            <div class="modal_head">
                                <h4>号码编辑</h4>
                                <div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="showDiv('editTemplate')"></a></div>
                            </div>
                            <form id="spNumSubmit" name="spNumSubmit" action="<c:url value='/sipPhoneAppMgr/showNumEdit'/>" method="post" enctype="multipart/form-data">
                                <input type="hidden" id="id" name="id" />
                                <input type="hidden" id="appid" name="appid" />
                                <input type="hidden" id="oldShowNum" name="oldShowNum"/>
                                <div class="modal-common_body mdl_pt_body">
                                    <div class="marTop25 marLeft90">
                                        <label class="create_app_title_left font14" style="margin-right:0">&nbsp;SIP号码：</label>
                                        <span id="sipphoneId"></span>
                                    </div>
                                    <div class="marTop25 marLeft90">
                                        <label class="create_app_title_left font14" style="margin-right:0">固话号码：</label>
                                        <span id="fixphoneId"></span>
                                    </div>
                                    <div class="marTop25 marLeft90">
                                        <label class="create_app_title_left font14" style="margin-right:0">显示号码：</label>
                                        <input type="text" id="showNum"name="showNum" class="input_style" style="width:170px; text-align: left;padding-left:5px;" maxlength="30"/>
                                        <span id="showTipMsg" style="color: red;display: none;"></span>
                                    </div>
                                    <div class="marTop25 marLeft90">
                                        <label class="create_app_title_left font14" style="margin-right:0">当前状态：</label>
                                        <span id="auditStatus"></span>
                                    </div>
                                    <div class="marTop25 marLeft90" id="statusOUT" style="display: none">
                                        <label class="create_app_title_left font14 f_left" style="margin-right:0">原&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;因：</label>
                                        <span id="auditCommon" style="width:400px;margin-bottom:20px;word-wrap:break-word"></span>
                                    </div>
                                </div>
                            </form>
                            <div class="mdl_foot" style="margin:25px 0 0 170px;">
                                <button type="button" id="spNumEditButton" name="spNumEditButton" class="true">提&nbsp;&nbsp;交</button>
                                <button type="button" class="false" onclick="showDiv('editTemplate')">取&nbsp;&nbsp;消</button>
                            </div>
                        </div>
                        <!--号码编辑 弹层 end-->
                        <div class="modal-box  phone_t_code" style="display:none;" id="choose_message">
                            <div class="modal_head">
                                <h4>提示信息</h4>
                                <div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="showDiv('choose_message')"></a></div>
                            </div>
                            <div class="modal-common_body mdl_pt_body set_paw_left">
                                <div class="test_success test_success_left">
                                    <i class="attention"></i>
                                    <span>您没有进行选择</span>
                                </div>
                            </div>
                        </div>
                        <!--删除号码 弹层-->
                        <div class="modal-box  phone_t_code" id="delTemplate" style="display:none;">
                            <div class="modal_head">
                                <h4>删除号码</h4>
                                <div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="showDiv('delTemplate')"></a></div>
                            </div>
                            <div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
                                <div class="app_delete_main">
                                    <p class="app_delete_common"><i class="attention"></i>确认删除如下SIP号码？</p>
                                    <p class="NunberDele" id="psipphone" style="text-align:center;"></p>
                                </div>
                            </div>
                            <div class="mdl_foot" style="margin-left:140px;margin-top:-30px;">
                                <button type="button" class="false NumberBtn" id="delete">确&nbsp;&nbsp;认</button>
                                <button type="button" class="true" onclick="showDiv('delTemplate')">取&nbsp;&nbsp;消</button>
                            </div>
                        </div>
                        <!--删除号码 弹层 end-->
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
        </div>
        <!--右边内容 end-->
    </div>
</section>
</body>
</html>
