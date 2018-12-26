<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/7/12
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>查看中继配置</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/validation/validate-extend.js"></script>
    <script id="sipRepeaterNumberTemplate" type="text/x-jquery-tmpl">
        <tr class="date_hg">
            <td>\${num}</td>
            <td>\${number}</td>
            <td>\${create_time}</td>
        </tr>
    </script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
    <script type="text/javascript">
        $(function(){
            //获取被叫号码类型转化成bit值进行翻译
            var callTypeArray=parseInt("${sipRelayInfo.calledType}").toString(2).split(""), callTypeStr="", cycle="${sipRelayInfo.cycle}";
            for(var i=0; i<callTypeArray.length; i++){
                if(callTypeArray[callTypeArray.length-1-i]==1){
                    if(i==0){
                        callTypeStr += "电信 ";
                    }else if(i==1){
                        callTypeStr += "移动 ";
                    }else if(i==2){
                        callTypeStr += "联通 ";
                    }else if(i==4){
                        callTypeStr += "其它 ";
                    }
                }
            }
            $("#callTypeSpan").text(callTypeStr);

            //设置标准费率配置
            if(cycle == 6){
                $("#cycleSpan").text(cycle);
                $("#standardPriceSpan").text(new Number("${sipStandardRate.per6}").toFixed(3));
                $("#discountPriceSpan").text(new Number("${sipStandardRate.per6*sipRelayInfo.per6Discount/1000}").toFixed(3));
            }else if(cycle == 60){
                $("#cycleSpan").text(cycle);
                $("#standardPriceSpan").text(new Number("${sipStandardRate.per60}").toFixed(3));
                $("#discountPriceSpan").text(new Number("${sipStandardRate.per60*sipRelayInfo.per60Discount/1000}").toFixed(3));
            }else{
                $("#cycleSpan").text("${sipStandardRate.cycle}");
                if("${sipStandardRate.cycle}" == 6){
                    $("#standardPriceSpan").text(new Number("${sipStandardRate.per6}").toFixed(3));
                    $("#discountPriceSpan").text(new Number("${sipStandardRate.per6*sipStandardRate.per6Discount/1000}").toFixed(3));
                }else if("${sipStandardRate.cycle}" == 60){
                    $("#standardPriceSpan").text("${sipStandardRate.per60}");
                    $("#discountPriceSpan").text(new Number("${sipStandardRate.per60*sipStandardRate.per60Discount/1000}").toFixed(3));
                }
            }

            //客户IP Port
            /*$("#customerIpSpan").text("${sipBasic.ipport1}".split(":")[0]);
            $("#customerPortSpan").text("${sipBasic.ipport1}".split(":")[1]);
            //平台IP Port
            $("#webIpSpan").text("${sipBasic.ipport2}".split(":")[0]);
            $("#webPortSpan").text("${sipBasic.ipport2}".split(":")[1]);*/



            $("#sipRepeaterNumberTable").page({
                url:"<c:url value='/sipRepeaterList/getSipRepeaterNumPool'/>",
                pageSize:30,
                integralBody:"#sipRepeaterNumberTbody",// 将模版加入到某个元素的ID
                integralTemplate:"#sipRepeaterNumberTemplate",// tmpl中的模版ID
                pagination:"#sipRepeaterNumberPagination", // 分页选项容器
                param:{"subid":"${sipRelayInfo.subid}"},
                dataRowCallBack:dataRowCallBack
            });
        });

        function dataRowCallBack(row,num) {
            //序号列
            row.num = num;
            if (row.create_time) {
                row.create_time = o.formatDate(row.create_time, "yyyy-MM-dd hh:mm:ss");
            }
            return row;
        }


        //查询
        function searchSipRepeaterNumber(){
            var formData = {};
            $("#searchForm").find(":input[id]").each(function(){
                formData["params["+$(this).attr('id')+"]"] = $.trim($(this).val());
            });
            $("#sipRepeaterNumberTable").reload(formData);
        }

        /** 导出记录 */
        function downloadSipReportNumPool() {
            var formData = "";
            formData += "&params[subid]=${sipRelayInfo.subid}";
            $('#searchForm').find(':input[name]').each(function(){
                formData += "&params["+$(this).attr('name')+"]=" + $.trim($(this).val());
            });
            formData = formData.substring(1);
            window.open("<c:url value='/sipRepeaterList/downloadSipRepeaterNum'/>?"+formData);
        }
    </script>
</head>
<body>
<!-- header start -->
<jsp:include page="../common/controlheader.jsp"/>
<!-- header end -->
<section>
    <div id="sec_main">
        <!-- 左侧菜单 start -->
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('sp','sipMgr','sipRepeaterList');</script>
        <!-- 左侧菜单 end -->


        <div class="main3">
            <div class="container" >
                <div class="msg">
                    <h3>子账号列表>${sipRelayInfo.subName}配置</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="sip_appDetail">
                    <div class="create_app_common">
                        <label class="create_app_title_left" >子账号名称:</label>
                        <span class="sipRelayCommonFont" style="margin-left: 18px;">${sipRelayInfo.subName}</span>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left" >子账号ID:</label>
                        <span class="sipRelayCommonFont">${sipRelayInfo.subid}</span>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left" >选号开关:</label>
                        <span class="sipRelayCommonFont">
                            <%--<input type="radio" style="margin-right:10px"/>是--%>
                             <c:if test="${sipRelayInfo.numFlag == '00'}">
                                 关闭
                             </c:if>
                             <c:if test="${sipRelayInfo.numFlag == '01'}">
                                 开启
                             </c:if>
                        </span>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left">被叫号码类型:</label>
                        <span class="ringCommon" id="callTypeSpan">
                             <%--<input type="checkbox" id="allSelect"/>
                             <label for="allSelect" style="font-size:13px;">全部</label>--%>
                        </span>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left" >费率配置:</label>
                        <span class="sipRelayCommonFont">计费单位: <%--<input type="radio" style="margin:0 10px"/>分钟--%><span id="cycleSpan"></span>秒</span>
                        <span class="sipRelayCommonFont" style="margin-left:50px;">标准价:<span class="mL20" id="standardPriceSpan"></span>元</span>
                        <span class="sipRelayCommonFont" style="margin-left:60px;">折扣价:<span class="mL20" id="discountPriceSpan"></span></span>
                    </div>
                    <!--div class="create_app_common">
                        <label class="create_app_title_left" >客户IP:</label>
                        <span class="ringCommon mL53" id="customerIpSpan"></span>
                        <label class="create_app_title_left mL105" >客户端口:</label>
                        <span class="ringCommon" id="customerPortSpan"></span>
                    </div>
                    <div class="create_app_common">
                        <label class="create_app_title_left" >平台IP:</label>
                        <span  class="ringCommon mL53" id="webIpSpan"></span>
                        <label class="create_app_title_left mL105">平台端口:</label>
                        <span class="ringCommon" id="webPortSpan"></span>
                    </div-->
                </div>
                <!--中继列表-->
                <div class="application_msg clear">
                    <div>
                        <p class="application">中继号码池列表</p>
                        <form id="searchForm">
                            <div>
                                <span class="check_font">号码：</span>
                                <input type="text" id="number" name="number" class="input_style"/>
                                <button type="button" class="md_btn call_left" onclick="searchSipRepeaterNumber();">查询</button>
                            </div>
                        </form>
                    </div>
                    <div class="record_down f_right">
                        <span class="open_down_billed" onclick="downloadSipReportNumPool();"><i class="common_icon download_txt"></i>导出记录</span>
                    </div>
                    <table class="table_style" id="sipRepeaterNumberTable">
                        <thead>
                        <tr>
                            <th width="20%">序号</th>
                            <th width="40%">号码</th>
                            <th width="40%">创建时间</th>
                        </tr>
                        </thead>
                        <tbody id="sipRepeaterNumberTbody">

                        </tbody>
                    </table>
                    <div class="f_right app_sorter"  id="sipRepeaterNumberPagination"></div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
