<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
    <!--[if lt IE 9]>
    <script src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="http://g.alicdn.com/sj/lib/jquery/dist/jquery.min.js"></script>
    <script type="text/javascript">

    </script>
</head>
<body>
<!--遮罩start-->
<div id="shadeWin" class="shade_on_win" style="display: none;"></div>
<!--遮罩end-->

<jsp:include page="../common/controlheader.jsp"/>

<section>
    <div id="sec_main">

        <!-- 左侧菜单 start -->
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('zn','smart_cloud','monthProc');</script>
        <!-- 左侧菜单 end -->

        <div class="main3">

            <div class="container13">
                <div class="msg">
                    <h3>月结账单</h3>
                </div>
                <div class="bill_msg">
                    <%--<div class="customer_requirements">--%>
                        <%--<span>客户需知：我司于此对账单发出之日起5个工作日内仍未收到阁下之反馈，一切数据均以此对账单为准并视为你方已认可此数据。所有价格都为人民币报价，账单价格含有17%增值税，呼入本地免费，呼出单价不分市话和长途。</span>--%>
                    <%--</div>--%>
                    <div class="bill_select">

                        <input type="hidden" id="maxDate2" value="${ym2}"/>
                        <input type="hidden" id="minDate2" value="${beforcurrentDate}"/>
                        <span class="check_font">选择月份:</span>

                        <input type="text" class="Wdate" id="ym" onfocus="WdatePicker({dateFmt:'yyyy年M月',maxDate:'#F{$dp.$D(\'maxDate2\')}',minDate:'#F{$dp.$D(\'minDate2\')}',readOnly:true})" value="${ym}" style="width:184px;height:28px;"/>

                        <span class="check_font">呼叫中心：</span>
                        <select id="callCenter">
                            <option value="">全部</option>
                            <c:forEach items="${callCenter}" var="cc">
                                <option value="${cc.subid}" <c:if test="${cc.subid eq ccid}">selected="selected"</c:if>>${cc.ccname}</option>
                            </c:forEach>
                        </select>

                        <button type="button" class="md_btn f_right" onclick="search()">查&nbsp;询</button>

                    </div>
                    <div class="bill_date clear">
                        <span class="c7_common marTop10" style="display:inline-block;">专线月租费：<fmt:formatNumber value="${callRate.relayRent*callRate.relayCnt}" pattern="0.00"/>元</span>
                        <div class="record_down f_right">
                            <%--<span class="open_down_billed"> <a href="javascript:void(0);"  onclick="openAlert('#shadeWin,#Monthly_download_billed');"><i class="common_icon download_txt"></i>导出账单</a></span>--%>
                                <a href="javascript:void(0);" onclick="downloadReport('');" class="open_down_billed f_right"><i class="common_icon download_txt"></i>导出账单</a>

                        </div>
                        <%--<span>"专&nbsp;线&nbsp;月&nbsp;租&nbsp;费"&nbsp;&nbsp;2000元</span>--%>
                        <table class="table_style"  id="table" style="margin-bottom: 60px;">
                            <thead>
                                <tr class="table-title">
                                    <td rowspan="2">日&nbsp;期</td>
                                    <td rowspan="2">呼&nbsp;叫&nbsp;中&nbsp;心</td>
                                    <td rowspan="2">运&nbsp;营&nbsp;商</td>
                                    <td rowspan="2">呼&nbsp;叫&nbsp;类&nbsp;型</td>
                                    <td colspan="3">通&nbsp;话&nbsp;明&nbsp;细</td>
                                    <td colspan="3">费&nbsp;用&nbsp;项&nbsp;目</td>
                                </tr>
                                <tr>
                                    <td>接通数</td>
                                    <td>总通话时长(秒)</td>
                                    <td>计费通话时长(分钟)</td>
                                    <td>单价(元/分钟)</td>
                                    <td>通话费用(元)</td>
                                    <td>总消费金额(元)</td>
                                </tr>
                            </thead>
                            <tbody>

                            <c:if test="${null == srs || srs.size() ==0}">
                                <tr>
                                    <td  colspan="10">没有数据</td>
                                </tr>
                            </c:if>

                            <c:if test="${null != srs || srs.size() !=0}">
                                <c:forEach items="${srs}" var="sr" varStatus="status">
                                    <tr>
                                        <%--<c:if test="${status.index%8 == 0}">--%>
                                        <td >${ym}</td>

                                        <c:if test="${not empty ccid }">
                                        <td >${sr.ccname}</td>
                                        </c:if>

                                        <c:if test="${ empty ccid }">
                                            <td >全部</td>
                                        </c:if>
                                        <%--</c:if>--%>

                                        <%--<c:if test="${status.index%2 == 0}">--%>
                                        <td >
                                            <c:if test="${sr.operator eq '02'}">电信</c:if>
                                            <c:if test="${sr.operator eq '01'}">联通</c:if>
                                            <c:if test="${sr.operator eq '00'}">移动</c:if>
                                            <c:if test="${sr.operator eq '03'}">其他</c:if>
                                            <c:if test="${sr.operator eq '06'}">其他</c:if>
                                        </td>
                                        <%--</c:if>--%>

                                        <%--<c:if test="${status.index%4 == 0}">--%>
                                        <td >
                                            <c:if test="${sr.abline eq 'I'}">呼入</c:if>
                                            <c:if test="${sr.abline eq 'O'}">呼出</c:if>
                                        </td>
                                        <%--</c:if>--%>

                                        <td>${sr.succcnt}</td>
                                        <td>${sr.thscsum}</td>
                                        <td><fmt:formatNumber value="${Float.parseFloat(sr.jfscsum)}" pattern="0.000"/></td>

                                        <td >
                                        <c:if test="${sr.abline eq 'I'}"><fmt:formatNumber value="${callRate.callin * callRate.callinDiscount /1000}" pattern="0.000"/></c:if>
                                        <c:if test="${sr.abline eq 'O'}"><fmt:formatNumber value="${callRate.callout * callRate.calloutDiscount /1000}" pattern="0.000"/></c:if>
                                        </td>
                                        <%--<td>${sr.fee}</td>--%>
                                        <%--<td>${sr.fee}</td>--%>

                                        <td><fmt:formatNumber value="${sr.fee}" pattern="0.000"/></td>
                                        <td><fmt:formatNumber value="${sr.fee}" pattern="0.000"/> </td>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                        </table>
                        <div id="pagination" class="f_right app_sorter"></div>
                    </div>
                </div>
                <div class="modal-box  phone_t_code" id="Monthly_download_billed" style="display: none;">
                    <div class="modal_head">
                        <h4>下载历史话单</h4>
                        <div class="p_right"><a href="javascript:void(0);" onclick="closeAlert('#shadeWin,#Monthly_download_billed');" class="common_icon close_txt"></a></div>
                    </div>
                    <div class="modal-common_body mdl_pt_body">
                        <div class="download_billed">
                            <div class="download_time_left f_left">
                                <c:forEach items="${months}" var="m" varStatus="status">
                                    <c:if test="${m[1] < 10}">
                                        <div class="down_common"><span>${m[0]}年&nbsp;&nbsp;${m[1]}月</span>下载</div>
                                    </c:if>
                                    <c:if test="${m[1] >= 10}">
                                        <div class="down_common"><span>${m[0]}年${m[1]}月</span>下载</div>
                                    </c:if>
                                    <c:if test="${status.index == 5}">
                                        </div><div class="download_time_right f_right">
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</section>

<script type="text/javascript" src="http://g.alicdn.com/sj/dpl/1.5.1/js/sui.min.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
<script>


    function search(){
        //
        window.location.href = '<c:url value="/znydd/bill/monthProc"/>?cc=' + $("#callCenter").val() + "&ym=" + $("#ym").val();
    }

    function closeAlert(fid) {
        $(fid).hide();
    }

    function openAlert(fid) {
        $(fid).show();
    }

    // 导出报表
    function downloadReport(type) {
        window.open("<c:url value='/znydd/bill/monthProc/downloadReport'/>?cc=" + $("#callCenter").val() + "&ym=" + $("#ym").val());
        <%--$.fn.downloadReport("<c:url value='/znydd/bill/monthProc/downloadReport'/>");--%>
    }

</script>
</body>
</html>

