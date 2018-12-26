<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
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
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/sui.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script id="applyRecordTemplate" type="text/x-jquery-tmpl">
        <tr class="date_hg">
            <td>\${atime}</td>
            <td>\${pname}</td>
            <td>\${cname}</td>
            <td>\${amount}</td>
            <td>\${rate}</td>
            <td>
            {{if auditStatus=='不通过'}}
				<font color="#FF0000">\${auditStatus}</font>
			{{/if}}
			{{if auditStatus!='不通过'}}
				\${auditStatus}
			{{/if}}
		    </td>
            <td>
                <a href="\${url}"><button type="button">查看</button></a>
                <button type="button" class="del Yh_numberMg" onclick="applyRecordDelete('\${id}');">删除</button>
            </td>
        </tr>
    </script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
    <script type="text/javascript">
        $(function(){
            $("#applyRecord").page({
                url:"<c:url value='/sipPhoneAppMgr/getApplayRecordList'/>?appid="+'${appInfo.appid}',
                pageSize:30,
                integralBody:"#applyRecordTbody",// 将模版加入到某个元素的ID
                integralTemplate:"#applyRecordTemplate",// tmpl中的模版ID
                pagination:"#applyRecordPagination", // 分页选项容器
                dataRowCallBack:dataRowCallBack
            });

        });
        function dataRowCallBack(row,num) {
            if (row.atime) {
                row.atime = o.formatDate(row.atime, "yyyy-MM-dd hh:mm:ss");
            }

            if (row.auditStatus == "02") {
                row.auditStatus = "不通过";
            } else if (row.auditStatus == "01") {
                row.auditStatus = "通过";
            }else  {
                row.auditStatus = "审核中";
            }
            row.url = "<c:url value='/sipPhoneAppMgr/applyRecordDetail'/>?id="+row.id;
            return row;
        }

        var numberid;
        function applyRecordDelete(id){
            $("#shadeWin").show();
            $("#delTemplate1").show();
            numberid=id;
        }
        function closeDiv(divid){
            $('#' + divid ).hide();
            $('#shadeWin').hide();
        }

        function  delApplyRecord(){
            var id = numberid;
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/sipPhoneAppMgr/delApplyRecord'/>",
                data:{'id':id},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data){
                        dispatchTipMsg("showTipMsg1","申请记录删除成功！");
                        setTimeout("closeAddDispatchDialog('delTemplate1')",600);
                        setTimeout("reloadTable()",600);
                    }else{
                        dispatchTipMsg("showTipMsg1","出现异常！");
                        setTimeout("closeAddDispatchDialog('delTemplate1')",600);
                        setTimeout("reloadTable()",600);
                    }

                },
                error  : function(data){
                    dispatchTipMsg("showTipMsg1","出现异常！");
                    setTimeout("closeAddDispatchDialog('delTemplate1')",600);
                    setTimeout("reloadTable()",600);
                }
            });
        }

        //关闭对话框
        function closeAddDispatchDialog(dialogId){
            $("#"+dialogId).hide();
            $("#shadeWin").hide();
        }

        function dispatchTipMsg(id,obj){
            $("#"+id).text(obj).show();
            setTimeout(function(){
                $("#"+id).hide();
            },2000);
        }

        function reloadTable(){
            $("#applyRecord").reload();
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
            <div class="container" >
                <div class="msg">
                    <h3>号码管理>申请记录</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="application_msg">
                    <div class="app_select Yh_number">
                        <p class="check_font">应用名称:<span class="Yh_numberMg">${appInfo.appName}</span></p>
                        <p class="check_font f_left">APP&nbsp;&nbsp;ID:<span class="Yh_numberMg">${appInfo.appid}</span> </p>
                        <a href="<c:url value='/sipPhoneAppMgr/applyForSipPhone'/>?appid=${appInfo.appid}">
                        <button type="button" class="md_btn f_right Yh_numberTop">申请号码</button></a>
                    </div>
                     <div class="application clear">
                        <table class="table_style Nunber_table" id="applyRecord">
                            <thead>
                            <tr>
                                <th width="17%">申请时间</th>
                                <th width="13%">省份</th>
                                <th width="14%">城市</th>
                                <th width="14%">SIP号码数量</th>
                                <th width="14%">SIP/固话号码比</th>
                                <th width="12%">状态</th>
                                <th width="16%">操用</th>
                            </tr>
                            </thead>
                            <tbody id="applyRecordTbody">

                            </tbody>
                        </table>
                         <div class="f_right app_sorter"  id="applyRecordPagination"></div>
                         <!--删除记录 弹层-->
                         <div class="modal-box  phone_t_code" id="delTemplate" style="display:none;" >
                             <div class="modal_head">
                                 <h4>删除记录</h4>
                                 <div class="p_right"><a href="#" class="common_icon close_txt"></a></div>
                             </div>
                             <div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
                                 <div class="app_delete_main">
                                     <p class="app_delete_common"><i class="attention"></i>该记录一旦删除后，将不能恢复</p>
                                     <p class="app_delete_common">确定要删除该记录？</p>
                                 </div>
                             </div>
                             <div class="mdl_foot" style="margin-left:140px;">
                                 <button type="button"class="false NumberBtn">确&nbsp;&nbsp;认</button>
                                 <button type="button"class="true">取&nbsp;&nbsp;消</button>
                             </div>
                         </div>
                         <!--删除记录 弹层 end-->
                    </div>
                </div>
                </div>
               </div>
        <!--右边内容 end-->
        <!--删除号码-->
        <div class="modal-box  phone_t_code" id="delTemplate1" style="display: none;">
            <div class="modal_head">
                <h4>删除申请记录</h4>
                <div class="p_right"><a href="javascript:void(0);" class="common_icon close_txt" onclick="closeDiv('delTemplate1');"></a></div>
            </div>
            <div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
                <div class="app_delete_main">
                    <p class="app_delete_common"><i class="attention"></i>该申请记录一旦删除后，将不能恢复</p>
                    <p class="app_delete_common">确定要删除该申请记录？</p>
                </div>
            </div>
            <div  style="margin-left:40px;height: 10px; width: 500px; text-align: center;"><span id="showTipMsg1" style="color: red;display: none;"></span></div>
            <div class="mdl_foot" style="margin:50px 0 0 150px;">
                <button type="button" class="true" onclick="delApplyRecord()">确&nbsp;&nbsp;认</button>
                <button type="button" class="false" onclick="closeDiv('delTemplate1');">取&nbsp;&nbsp;消</button>
            </div>
        </div>
 </div>
</section>
</body>
</html>
