<%--
  Created by IntelliJ IDEA.
  User: lixin
  Date: 2016/8/4
  Time: 9:29
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
    <script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>
    <script src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script src="${appConfig.resourcesRoot}/js/laydate/laydate.js"></script>
    <script type="text/javascript">
        function show(){
            $("#show_tip").css("display","block");
        }
        function hide(){

            $("#show_tip").css("display","none");
        }

    </script>
</head>
<body>
<div id="toshow"></div>
<jsp:include page="../common/controlheader.jsp"/>
<section>
    <div id="sec_main">
        <jsp:include page="../common/controlleft.jsp"/>
            <script type="text/javascript">showSubMenu('zn','smart_cloud','callcenter');</script>
        <div class="main3">
            <div class="container7">
            <div class="msg">
                <h3>呼叫中心列表</h3>
                <jsp:include page="../common/message.jsp"/>
            </div>
            <div class="Prepaid_records">

                <div class="demand" style="border-bottom:1px dashed #DDDDDD;">
                    <div class="create_app_common">
                        <label class="create_app_title_left" >应用名称:</label>
                        <span  class="ringCommon">${appInfo.appName}</span>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left" >APP&nbsp;&nbsp;ID:</label>
                        <span  class="ringCommon padLeft5">${appInfo.appid}</span>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left" >应用号码:</label>
                        <span  class="ringCommon">${appInfo.callNo}</span>
                    </div>
                    <div class="create_app_common" style="height:34px;">
                        <label class="create_app_title_left" >缺省调度:</label>
                        <c:choose>
                            <c:when test="${flag eq 1}">
                                <span id="toupdate" >
                                     <span class="switch demo4">
                                         <input type="checkbox" id="checkbox" name="checkbox">
                                         <label><i></i></label>
                                     </span>
                                     <span class="ringCommon marLeft5" id="closed" style="display: none;">已关闭</span>
                                     <span class="marLeft5" id="open">
                                         <span class="ringCommon marRight20" style="color:#5691E1">已开启</span>
                                         <select id="callCenter" class="zyd_open_width">
                                             <option value="">请选择</option>
                                             <c:forEach items="${callCenter}" var="cc">
                                                 <c:if test="${Default.subid eq cc.subid}">
                                                     <option value="${cc.subid}" selected>${cc.ccname}</option>
                                                 </c:if>
                                                 <c:if test="${Default.subid ne cc.subid}">
                                                     <option value="${cc.subid}">${cc.ccname}</option>
                                                 </c:if>
                                             </c:forEach>
                                         </select>
                                         <button type="button" id="submit" name="submit"  >提交</button>
                                     </span>
                                </span>
                            </c:when>

                            <c:when test="${flag eq 0}">
                                <span id="toupdate">
                                     <span class="switch demo3">
                                         <input type="checkbox" id="checkbox1" name="checkbox1">
                                         <label><i></i></label>
                                     </span>
                                     <span class="ringCommon marLeft5" id="close" >已关闭</span>
                                     <span class="marLeft5" id="opend" style="display: none;">
                                         <span class="ringCommon marRight20" style="color:#5691E1">已开启</span>
                                         <select id="callCenter1" class="zyd_open_width">
                                             <option value="">请选择</option>
                                             <c:forEach items="${callCenter}" var="cc">
                                                 <option value="${cc.subid}">${cc.ccname}</option>
                                             </c:forEach>
                                         </select>
                                         <button type="button" id="submit1" name="submit1"  >提交</button>
                                     </span>
                                </span>
                            </c:when>

                        </c:choose>

                    </div>
                </div>

                <div class="date clear">
                    <span class="c7_common">呼叫中心名称：</span>
                    <input type="text" class="input_style" id="ccname" style="width:200px;"/>
                    <button type="button" onclick="search();" class="md_btn marLeft90">查询</button>

                    <table class="table_style marTop10 clear"  id="calllist">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>创建时间</th>
                            <th>呼叫中心名称</th>
                            <th>最大话务员数量</th>
                        </tr>
                        </thead>
                        <tbody id="czjllog">
                        </tbody>
                    </table>
                    <div id="pagination" class="f_right app_sorter"></div>
                </div>

                </div>
            </div>
        </div>

</section>

<!-- 充值记录 -->
<script id="czlog" type="text/x-jquery-tmpl">
	<tr class="date_hg">
	    <td>\${num}</td>
		<td>\${ctime}</td>
		<td>\${ccname}</td>
		<td>\${cco_max_num}</td>

	</tr>
</script>

<script src="${appConfig.resourcesRoot}/js/common/common.js"></script>
<script src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
<script src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
<script>
    // 查询呼叫中心列表
    var calllist = $("#calllist").page({
        url:"<c:url value='/callCenter/pageCallList'/>",
        integralTemplate : "#czlog",
        integralBody : "#czjllog",
        dataRowCallBack : dataRowCallBack
    });

    function search() {
        var param = {
            params : { ccname : $("#ccname").val() }
        };
        calllist.reload(param);
    }

    $('#checkbox').click(function() {
        var checked = $("#checkbox").get(0).checked;
//        alert(checked);
        if (checked = true){
        $.ajax({
            type: "post",//使用post方法访问后台
            dataType: "json",//返回json格式的数据
            url: "<c:url value='/callCenter/resetdefaultCc'/>",
            data:{'checked':checked},//要发送的数据
            success: function (data) {
                if(data.code=='00') {
                    window.location.reload();
                }
            }
        });
        }
    });

    $('#checkbox1').click(function() {
        var checked = $("#checkbox1").get(0).checked;
//        alert(checked);
        if (checked = false){
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/callCenter/resetdefaultCc'/>",
                data:{'checked':checked},//要发送的数据
                success: function (data) {
                    if(data.code=='00') {
                        window.location.reload();
                    }
                }
            });
        }
    });


    $('#submit').click(function() {
        var ccid = $('#callCenter').val();
        var checked = $("#checkbox").get(0).checked;
        var flag = 0;
//        alert(checked);
        $.ajax({
            type: "post",//使用post方法访问后台
            dataType: "json",//返回json格式的数据
            url: "<c:url value='/callCenter/defaultCc'/>",
            data:{'ccid':ccid,'checked':checked},//要发送的数据
            success: function (data) {
                if(data.code=='00') {
                    window.location.reload();
                }
                if(data.code=='01') {
                    alert("请选择");
                }
            }
        });
    });

    $('#submit1').click(function() {
        var ccid = $('#callCenter1').val();

        var checked = $("#checkbox1").get(0).checked;
//        alert(checked);
        var flag = 1;
        $.ajax({
            type: "post",//使用post方法访问后台
            dataType: "json",//返回json格式的数据
            url: "<c:url value='/callCenter/fucdefaultCc'/>",
            data:{'ccid':ccid,'checked':checked},//要发送的数据
            success: function (data) {
                if(data.code=='00') {
                    window.location.reload();
                }
                if(data.code=='01') {
                    window.location.reload();
                }
            }
        });
    });


    $('#update').click(function() {
//        alert(1);
        $('#toupdate').show();
        $('#toshow1').hide();
    });

    function dataRowCallBack(row,num) {
        row.num = num;
        // TODO:
        if (row.ctime) {
            row.ctime = o.formatDate(row.ctime, "yyyy-MM-dd hh:mm:ss");
        }
        return row;
    }

</script>
</body>
</html>