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
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui.min.css" />
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]-->
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
    <!--[endif]-->

    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/sui.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/plugins/Validform/5.3.2/Validform.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/plugins/Validform/5.3.2/Validform.extends.js"></script>
    <script src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
    <script src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
    <script id="numberManagerTemplate" type="text/x-jquery-tmpl">
		<tr class="date_hg">
			<td>\${num}</td>
			<td>\${number}</td>
			<td>\${number_status}</td>
			<td>
			    {{if number_status=='审核不通过'}}
				    <a href="javascript:void(0);" onclick="appReCommitInit('\${id}');">重新提交</a>
				{{/if}}
			    {{if number_status!='已删除'}}
				    <a href="javascript:void(0);" onclick="appCanel('\${id}');">删除</a>
				{{/if}}
			</td>
		</tr>
	</script>
    <script type="text/javascript" >
        var numberManagerTable;
        $(function(){
            var uploadCallback ='${uploadCallback}';
            if(uploadCallback=='yes'){
                $("#appid").val();
                openAddNumbersDialog();
                reloadTable();
            }
            if(uploadCallback=='no'){
                $("#appid").val();
                openAddNumbersDialog();
                window.open("<c:url value='/numMgr/downLoadErrorAppNum'/>", "_blank");
                reloadTable();
            }
            numberManagerTable = $("#numberManagerTable").page({
                url:"<c:url value='/numMgr/pageNumber'/>?appid=${appid}",
                pageSize:30,
                integralBody:"#numberManagerTbody",// 将模版加入到某个元素的ID
                integralTemplate:"#numberManagerTemplate",// tmpl中的模版ID
                pagination:"#numberManagerPagination", // 分页选项容器
                dataRowCallBack:dataRowCallBack
            });
            if(uploadCallback!=null&&uploadCallback!='') {
                $("#shadeWin").show();
            }
        });
        function dataRowCallBack(row,num){
            //序号列
            row.num = num;
            //号码状态
            if(row.number_status == 00){
                row.number_status = "待审核";
            }else if(row.number_status == 01){
                row.number_status = "审核通过";
            }
            else if(row.number_status == 02){
                row.number_status = "审核不通过";
            }
            else if(row.number_status == 03){
                row.number_status = "已删除";
            }
            return row;
        }
        function searchCcDispatch(){
            var formData = {};
            $("#searchForm").find(":input[name]").each(function(){
                formData["params["+$(this).attr('name')+"]"] = $.trim($(this).val());
            });
            $("#ccareacityTable").reload(formData);
        }

        //单个添加号码
        function openAddNumberDialog(){
            $("#addNumberDialog").find("h4").text("单个添加");
            $("#shadeWin").show();
            $("#addNumberDialog").show();
        }

        //批量导入
        function openAddNumbersDialog(){
            $("#addNumbersDialog").find("h4").text("批量导入");
            $("#shadeWin").show();
            $("#addNumbersDialog").show();
        }

        //提交号码
        function submitNumberForm(){
            var number = $("#number").val();
            if(number==null || number==""){
                dispatchTipMsg("showTipMsg","号码不能为空！");
                return;
            }
            var matchNumber = /^[0-9]{5,30}$/;
            if(!matchNumber.test(number)){
                dispatchTipMsg("showTipMsg","号码格式错误！");
                return;
            }
            var result = false;
            $.ajax({
                async:false,
                type: "post",
                url: "<c:url value="/numMgr/uniqueAname"/>",
                dataType: "json",
                data: {appid:'${appid}',number:number},
                success: function (data) {
                    if (data.code == "ok") {
                        return result = true;
                    }
                    if (data.code == "Passed") {
                        dispatchTipMsg("showTipMsg","该号码已存在！");
                        return;
                    }
                    if (data.code == "Pending") {
                        dispatchTipMsg("showTipMsg","该号码正在审核中！");
                        return;
                    }
                },
                error: function () {
                    dispatchTipMsg("showTipMsg","数据请求异常！");
                    return;
                }
            });

            if(result==true){
                //提交Number
                $.ajax({
                    type:"post",
                    url:"<c:url value="/numMgr/submitAppNumber1"/>",
                    dataType:"json",
                    async:false,
                    data:{appid:'${appid}',number:number},
                    success : function(data) {
                        if(data.status == "1"){
                            dispatchTipMsg("showTipMsg","数据提交成功！");
                            setTimeout("closeAddDispatchDialog('addNumberDialog')",600);
                            setTimeout("reloadTable()",600);
                        }
                        if(data.status == "0"){
                            dispatchTipMsg("showTipMsg","数据提交失败！");
                            setTimeout("closeAddDispatchDialog('addNumberDialog')",600);
                            setTimeout("reloadTable()",600);
                        }
                    },
                    error:function(){
                        dispatchTipMsg("showTipMsg","存在错误的数据格式，请求失败！");
                    }
                });
            }
        }

        function reloadTable(){
            $("#numberManagerTable").reload();
        }
        function dispatchTipMsg(id,obj){
            $("#"+id).text(obj).show();
            setTimeout(function(){
                $("#"+id).hide();
            },2000);
        }

        //关闭删除对话框
        function closeDelDispatchDialog(dialogId){
            $("#shadeWin").show();
            $("#"+dialogId).hide();
        }
        //关闭对话框
        function closeAddDispatchDialog(dialogId){
            $("#shadeWin").hide();
            $("#"+dialogId).hide();
        }
        $(function(){
            $('#numbersButton').click(function() {
                console.log("start");
                var url = $("#numbers").val();
                $("#errormsg").html('');

                if (!url){
                    $("#errormsg").html('<label class="error" style="display: block">文件为空，请选择文件上传! </label>');
                    setTimeout("document.getElementById('errormsg').style.display='none'",2000);
                    return;
                }

                // 格式是否正确
                if ((".XLS" != url.substring(url.lastIndexOf(".")).toLocaleUpperCase()) && (".XLSX" != url.substring(url.lastIndexOf(".")).toLocaleUpperCase()) &&
                        (".xls" != url.substring(url.lastIndexOf("."))) && (".xlsx" != url.substring(url.lastIndexOf(".")))) {
                    $("#errormsg").html('<label class="error">文件格式不正确! </label>');
                    return;
                }

                $(this).attr("disabled","disabled");
                $('#numbersForm').submit();

            });
        });

        function  delNumber(){
            var id = numberid;
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/numMgr/del'/>",
                data:{'id':id},//要发送的数据
                success: function(result){//data为返回的数据，在这里做数据绑定
                    if(result.code == 0){
                        reloadTable();
                        closeDelDispatchDialog('delTemplate');
                        openMsgDialog(result.msg);
                    }else{
                        openMsgDialog("信息删除失败！");
                    }

                },
                error  : function(data){
                    openMsgDialog("数据请求失败！");
                }
            });
        }

        var numberid;
        function appCanel(id){
            $("#shadeWin").show();
            $("#delTemplate").show();
            numberid=id;
        }

        function appReCommitInit(id){
            $("#shadeWin").show();
            $("#reCommit").show();
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/numMgr/getNumberInfo'/>",
                data:{'id':id},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data.numberStatus=='02'){
                        data.numberStatus='审核不通过';
                    }
                    if(data != null){
                        $("#id").val(data.id);
                        $("#numberReCommit").val(data.number);
                        $("#numberStauts").text(data.numberStatus);
                        $("#remark").text(data.remark);
                    }
                },
                error  : function(data){
                    dispatchTipMsg("showTipMsg2","出现异常！");
                    $('#shadeWin').hide();
                    setTimeout("closeAddDispatchDialog('reCommit')",500);
                    setTimeout("reloadTable()",500);
                }
            });
        }

        function reSubmitNumber(){
            var id = $("#id").val();
            var number = $("#numberReCommit").val();
            if(number==null || number==""){
                dispatchTipMsg("showTipMsg2","号码不能为空！");
                return;
            }
            var matchNumber = /^[0-9]{5,30}$/;
            if(!matchNumber.test(number)){
                dispatchTipMsg("showTipMsg2","号码格式错误！");
                return;
            }
            var result = false;
            $.ajax({
                async:false,
                type: "post",
                url: "<c:url value="/numMgr/uniqueAname"/>",
                dataType: "json",
                data: {appid:'${appid}',number:number},
                success: function (data) {
                    if (data.code == "ok") {
                        return result = true;
                    }
                    if (data.code == "Passed") {
                        dispatchTipMsg("showTipMsg2","该号码已存在！");
                        return;
                    }
                    if (data.code == "Pending") {
                        dispatchTipMsg("showTipMsg2","该号码正在审核中！");
                        return;
                    }
                },
                error: function () {
                    dispatchTipMsg("showTipMsg2","数据请求异常！");
                    return;
                }
            });

            if(result==true){
                //提交Number
                $.ajax({
                    type:"post",
                    url:"<c:url value="/numMgr/reCommit"/>",
                    dataType:"json",
                    async:false,
                    data:{id:id,number:number},
                    success : function(data) {
                        if(data.status == "1"){
                            dispatchTipMsg("showTipMsg2","重新提交成功！");
                            setTimeout("closeAddDispatchDialog('reCommit')",600);
                            setTimeout("reloadTable()",600);

                        }
                        if(data.status == "0"){
                            dispatchTipMsg("showTipMsg2","提交失败！");
                            setTimeout("closeAddDispatchDialog('reCommit')",600);
                            setTimeout("reloadTable()",600);
                        }
                    },
                    error:function(){
                        dispatchTipMsg("showTipMsg2","存在错误的数据格式，请求失败！");
                    }
                });
            }
        }

        function closeDiv(divid){
            $('#' + divid ).hide();
            $('#shadeWin').hide();
        }

        // 导出报表
        function downloadReport() {
            var formData = {};
            formData["number"] = $("#serachNumber").val();
            formData["numberStatus"] = $("#number_status").val();
            formData["appid"] = '${appid}';
            numberManagerTable.downloadReport("<c:url value='/numMgr/downloadAppNumReport'/>",formData);
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
        <script type="text/javascript">showSubMenu('kf','appMgr','numMgr');</script>
        <!-- 左侧菜单 end -->
        <div class="main3">
            <!--消息显示-->
            <div class="modal-box  phone_t_code" id="showMsgDialog" style="display: none;">
                <div class="modal_head">
                    <h4>消息提示</h4>
                    <div class="p_right"><a href="javascript:void(0);" onclick="closeMsgDialog();" class="common_icon close_txt"></a></div>
                </div>
                <div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
                    <div class="app_delete_main" style="height: 50px; padding: 20px;">
                        <p class="app_delete_common"><i class="attention"></i><span id="alertMsg"></span></p>
                    </div>
                </div>
                <div class="mdl_foot" style="margin-left:220px;">
                    <button type="button" onclick="closeMsgDialog();" class="true">确&nbsp;&nbsp;定</button>
                </div>
            </div>

            <div class="container17" >
                <div class="msg">
                    <h3>号码管理>${appName}</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="records_msg">
                    <div class="recordNote">
                        <span class="WarmPrompt">温馨提醒：</span>
                        <p>专线语音及语音通知通过本页面进行号码提交审核</p>
                    </div>
                    <div class="addNum">
                        <span class="ringTip">号码添加：<button type="button" onclick="openAddNumberDialog();" class="addNumBtn marLeft30">单个添加</button><button type="button" class="addNumBtn marLeft15" onclick="openAddNumbersDialog();">批量导入</button></span>
                    </div>
                    <div class="clear">
                        <div class="record_select">
                            <div class="re_span_common">
                                <p class="ringTip">号码池列表</p>
                                <form id="searchForm">
                                    <span class="check_font">号码：</span>
                                    <input type="text" id="serachNumber" name="number" size="18" maxlength="64" class="input_style" style="width:220px;"/>
                                    <span class="check_font call_left">号码状态：</span>
                                    <select id="number_status" name="number_status" style="width:177px;">
                                        <option value="">请选择</option>
                                        <option value="00">待审核</option>
                                        <option value="01">审核通过</option>
                                        <option value="02">审核不通过</option>
                                        <option value="03">已删除</option>
                                    </select>
                                    <button type="button" class="md_btn marLeft30" style="margin-top:-6px;" onclick="searchCcDispatch();">查询</button>
                                </form>
                            </div>
                        </div>
                        <div class="record_down f_right" style="margin-top:50px">
                            <span class="open_down_billed" onclick="downloadReport();"><i class="common_icon download_txt"></i>导出号码</span>
                        </div>
                        <!--查询弹层-->
                        <div id="msgDialog" class="modal-box  phone_t_code" style="display: none;">
                            <div class="modal_head">
                                <h4>号码池列表导出</h4>
                                <div class="p_right"><a href="javascript:void(0);" onclick="closeDiv('msgDialog');" class="common_icon close_txt"></a></div>
                            </div>
                            <div class="modal-common_body mdl_pt_body">
                                <div class="test_success test_success_left">
                                    <i class="attention"></i>
                                    <span>您暂无数据</span>
                                </div>
                            </div>
                        </div>
                        <table class="table_style" id="numberManagerTable">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>号码</th>
                                <th>号码状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="numberManagerTbody">

                            </tbody>
                        </table>
                        <div class="f_right app_sorter"  id="numberManagerPagination"></div>
                    </div>
                </div>
                <!--单个添加号码-->
                <div id="addNumberDialog" class="modal-box  phone_t_code"  style="display: none">
                    <div class="modal_head">
                        <h4>单个添加</h4>
                        <div class="p_right"><a href="javascript:void(0);" onclick="closeAddDispatchDialog('addNumberDialog');" class="common_icon close_txt"></a></div>
                    </div>
                    <div class="modal-common_body mdl_pt_body marLeft28">
                        <form id="numberForm">
                            <div class="marTop100">
                                <div class="marLeft30">
                                    <label  class="create_app_title_left APP_state font14" >号码：</label>
                                    <input type="text" id="number" name="number" class="input_style" datatype="uniqueAname" style="width:300px;" maxlength="30" placeholder="示例:4008006001"/>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div  style="margin-left:40px;height: 10px; width: 500px; text-align: center;"><span id="showTipMsg" style="color: red;display: none;"></span></div>
                    <div class="mdl_foot" style="margin:80px 0 0 225px;">
                        <button type="button" onclick="submitNumberForm();" class="true">提&nbsp;&nbsp;交</button>
                    </div>
                </div>

                <!-- 批量导入号码-->
                <div id="addNumbersDialog" class="modal-box  phone_t_code"  style="display: none">
                    <div class="modal_head">
                        <h4>批量导入</h4>
                        <div class="p_right"><a href="javascript:void(0);" onclick="closeAddDispatchDialog('addNumbersDialog');" class="common_icon close_txt"></a></div>
                    </div>
                    <form id="numbersForm" name="numbersForm" action="<c:url value='/numMgr/appNumExcelUpload'/>" method="post" enctype="multipart/form-data">
                        <input type="hidden" value="${appid}" id="appid" name="appid" />
                        <div class="modal-common_body mdl_pt_body">
                            <div class="marTop50 marLeft90">
                                <label  class="create_app_title_left APP_state font14" >批量导入模板:</label>
                                <span class="ringDownL_icon"></span><a href="${appConfig.resourcesRoot}/download/导入号码池模板.xls" class="ringDownLoad">下载</a>
                            </div>
                            <div class="marTop20 marLeft90">
                                <label  class="create_app_title_left APP_state font14" >批量导入号码:</label>
                            </div>
                            <div class="marTop20" id="voice_template">
                                <input type="text" id="app_name1" name="numbersFile" class="l_input_style" style="margin-left:100px;width:250px;"/>
                                    <span class="parentPosition">
                                       <button type="button" class="browse">浏览</button>
                                       <input type="file" name="numbers" id="numbers" class="childPosition" onchange="document.getElementById('app_name1').value=this.value"/>
                                    </span>
                                <div><i class="formatTip ringTip">*支持excel2003及以上格式</i></div>
                                <div id="errormsg" class="Error_box" style="margin: 15px 0 -25px 103px;width: 400px;">
                                    <c:if test="${not empty error}">
                                        <label class="error">${error}</label>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                        <div class="mdl_foot" style="margin:50px 0 0 225px;">
                            <button type="button" class="true" id="numbersButton" name="numbersButton">上&nbsp;&nbsp;传</button>
                        </div>
                    </form>
                </div>
                <!--重新提交-->
                <div class="modal-box  phone_t_code Popups" id="reCommit" style="display:none">
                    <div class="modal_head">
                        <h4>重新提交</h4>
                        <div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('reCommit');"></a></div>
                    </div>
                    <div class="modal-common_body mdl_pt_body">
                        <form id="reCommitForm">
                            <input type="hidden" value="${id}" id="id" name="id" />
                            <div class="marTop25 marLeft90">
                                <label  class="create_app_title_left font14" style="margin-right:0">号码：</label>
                                <input type="text" id="numberReCommit" name="number" class="input_style" style="width:230px;"/>
                            </div>
                            <div class="marTop25 marLeft90">
                                <label  class="create_app_title_left font14" style="margin-right:0">审核状态：</label>
                                <span id="numberStauts" name="numberStauts"></span>
                            </div>
                            <div class="marTop25 marLeft90">
                                <label  class="create_app_title_left font14" style="margin-right:0">备注：</label>
                                <span id="remark" name="remark" style="word-wrap:break-word"></span>
                            </div>
                        </form>
                    </div>
                    <div  style="margin-left:40px;height: 10px; width: 500px; text-align: center;"><span id="showTipMsg2" style="color: red;display: none;"></span></div>
                    <div class="mdl_foot" style="margin:50px 0 0 225px;">
                        <button type="button" class="true"onclick="reSubmitNumber()">提&nbsp;&nbsp;交</button>
                    </div>
                </div>
                <!--删除号码-->
                <div class="modal-box  phone_t_code" id="delTemplate" style="display: none;">
                    <div class="modal_head">
                        <h4>删除号码</h4>
                        <div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('delTemplate');"></a></div>
                    </div>
                    <div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
                        <div class="app_delete_main">
                            <p class="app_delete_common"><i class="attention"></i>该号码一旦删除后，将不能恢复</p>
                            <p class="app_delete_common">确定要删除该号码？</p>
                        </div>
                    </div>
                    <div  style="margin-left:40px;height: 10px; width: 500px; text-align: center;"><span id="showTipMsg1" style="color: red;display: none;"></span></div>
                    <div class="mdl_foot" style="margin:50px 0 0 150px;">
                        <button type="button" class="true" onclick="delNumber()">确&nbsp;&nbsp;认</button>
                        <button type="button" class="false" onclick="closeDiv('delTemplate');">取&nbsp;&nbsp;消</button>
                    </div>
                </div>
            </div>
        </div>
      </div>
</section>
</body>
</html>
