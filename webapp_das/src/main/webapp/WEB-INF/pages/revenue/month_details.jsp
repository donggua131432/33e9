<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>营收报表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">

        $(function(){

        });

        /*修改并发数*/
        function openDialog(title,url,w,h){
            layer_show(title,url,w,h);
        }

        function downloadReport() {
            window.open('<c:url value="/revenue/report/downloadMonthDetails"/>?ym=${ym}&feeid=${feeid}&sid=${sid}');
        }
    </script>
</head>
<body>
<div class="pd-20">
    <form action="" method="post" class="form form-horizontal">
        <h4 class="text-c">消费月账单<span>${ym}</span></h4>
        <div class="row cl">
            <label class="col-4">客户名称：${month.companyName}</label>
            <label class="col-5">客户账号：${month.sid}</label>
            <label class="form-label col-2"><input class="btn btn-primary radius" style="width: 100px;" type="button" onclick="downloadReport();" value="导出"/></label>
        </div>

        <table class="table table-border table-bordered table-hover mt-20">
            <thead>
            <tr>
                <td>本月应收账款（元）</td>
                <td>本月充值金额（元）</td>
                <td>期初余额（元）</td>
                <td>账户余额（元）</td>
                <td>累计消费总额（元）</td>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>${month.fee}</td>
                <td>${month.recharge}</td>
                <td>${month.pbalance}</td>
                <td>${month.bbalance}</td>
                <td>${month.tfee}</td>
            </tr>
            </tbody>
        </table>

        <c:if test="${not empty rests}">
            <div class="row cl">
                <label class="col-3 text-l">专线语音</label>
            </div>
            <table class="table table-border table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <td>类型</td>
                    <td>累计通话时长（秒）</td>
                    <td>计费通话时长（分钟）</td>
                    <td>计费录音时长（分钟）</td>
                    <td>通话费用（元）</td>
                    <td>录音费用（元）</td>
                    <td>合计（元）</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${rests.rows}" var="rest">
                    <tr>
                        <td>
                            <c:if test="${rest.abline eq 'A'}">A路</c:if>
                            <c:if test="${rest.abline eq 'B'}">B路</c:if>
                        </td>
                        <td>${rest.thscsum}</td>
                        <td>${rest.jfscsum}</td>
                        <td>${rest.jflyscsum}</td>
                        <td>${rest.fee}</td>
                        <td>${rest.recordingfee}</td>
                        <td>${rest.tfee}</td>
                    </tr>
                </c:forEach>

                <tr>
                    <td>总计</td>
                    <td>${rests.total.thscsum}</td>
                    <td>${rests.total.jfscsum}</td>
                    <td>${rests.total.jflyscsum}</td>
                    <td>${rests.total.fee}</td>
                    <td>${rests.total.recordingfee}</td>
                    <td>${rests.total.tfee}</td>
                </tr>
                </tbody>
            </table>
        </c:if>

        <c:if test="${not empty masks}">
            <div class="row cl">
                <label class="col-3 text-l">专号通</label>
            </div>
            <table class="table table-border table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <td>类型</td>
                    <td>累计通话时长（秒）</td>
                    <td>计费通话时长（分钟）</td>
                    <td>计费录音时长（分钟）</td>
                    <td>通话费用（元）</td>
                    <td>录音费用（元）</td>
                    <td>合计（元）</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${masks.rows}" var="row">
                    <tr>
                        <td>
                            <c:if test="${row.abline eq 'A'}">A路</c:if>
                            <c:if test="${row.abline eq 'B'}">B路</c:if>
                            <c:if test="${row.abline eq 'C'}">回呼</c:if>
                        </td>
                        <td>${row.thscsum}</td>
                        <td>${row.jfscsum}</td>
                        <td>${row.jflyscsum}</td>
                        <td>${row.fee}</td>
                        <td>${row.recordingfee}</td>
                        <td>${row.tfee}</td>
                    </tr>
                </c:forEach>
                <tr>
                    <td>号码月租</td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td>${masks.rent}</td>
                </tr>
                <tr>
                    <td>总计</td>
                    <td>${masks.total.thscsum}</td>
                    <td>${masks.total.jfscsum}</td>
                    <td>${masks.total.jflyscsum}</td>
                    <td>${masks.total.fee}</td>
                    <td>${masks.total.recordingfee}</td>
                    <td>${masks.total.tfee}</td>
                </tr>
                </tbody>
            </table>
        </c:if>

        <c:if test="${not empty sips}">
            <div class="row cl">
                <label class="col-3 text-l">SIP接口</label>
            </div>
            <table class="table table-border table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <td>类型</td>
                    <td>累计通话时长（秒）</td>
                    <td>计费通话时长（分钟）</td>
                    <td>通话费用（元）</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${sips.rows}" var="row">
                    <tr>
                        <td>${row.subName}</td>
                        <td>${row.thscsum}</td>
                        <td>${row.jfscsum60}</td>
                        <td>${row.fee}</td>
                    </tr>
                </c:forEach>
                <tr>
                    <td>总计</td>
                    <td>${sips.total.thscsum}</td>
                    <td>${sips.total.jfscsum60}</td>
                    <td>${sips.total.fee}</td>
                </tr>

                </tbody>
            </table>
        </c:if>

        <c:if test="${not empty ccs}">
            <div class="row cl">
                <label class="col-3 text-l">智能云调度</label>
            </div>
            <table class="table table-border table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <td>类型</td>
                    <td>运营商</td>
                    <td>累计通话时长（秒）</td>
                    <td>计费通话时长（分钟）</td>
                    <td>通话费用（元）</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${ccs.rows}" var="row">
                    <tr>
                        <td>
                            <c:if test="${row.abline eq 'I'}">呼入</c:if>
                            <c:if test="${row.abline eq 'O'}">呼出</c:if>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${row.operator eq '00'}">移动</c:when>
                                <c:when test="${row.operator eq '01'}">联通</c:when>
                                <c:when test="${row.operator eq '02'}">电信</c:when>
                                <c:otherwise>其他</c:otherwise>
                            </c:choose>
                            <%--<c:if test="${row.operator eq '00'}">移动</c:if>
                            <c:if test="${row.operator eq '01'}">联通</c:if>
                            <c:if test="${row.operator eq '02'}">电信</c:if>
                            <c:if test="${row.operator eq '06'}">其他</c:if>--%>
                        </td>
                        <td>${row.thscsum}</td>
                        <td>${row.jfscsum}</td>
                        <td>${row.fee}</td>
                    </tr>
                </c:forEach>
                <tr>
                    <td>线路月租</td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td>${ccs.rent}</td>
                </tr>
                <tr>
                    <td>总计</td>
                    <td></td>
                    <td>${ccs.total.thscsum}</td>
                    <td>${ccs.total.jfscsum}</td>
                    <td>${ccs.total.fee}</td>
                </tr>
                </tbody>
            </table>
        </c:if>

        <c:if test="${not empty sps}">
            <div class="row cl">
                <label class="col-3 text-l">云话机</label>
            </div>
            <table class="table table-border table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <td>类型</td>
                    <td>累计通话时长（秒）</td>
                    <td>计费通话时长（分钟）</td>
                    <td>计费录音时长（分钟）</td>
                    <td>通话费用（元）</td>
                    <td>录音费用（元）</td>
                    <td>合计（元）</td>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${sps.rows}" var="row">
                    <tr>
                        <td>
                            <c:if test="${row.type eq 'restA'}">回拨A路</c:if>
                            <c:if test="${row.type eq 'restB'}">回拨B路</c:if>
                            <c:if test="${row.type eq 'sipA'}">Sipphone间回拨A</c:if>
                            <c:if test="${row.type eq 'sipB'}">Sipphone间回拨B</c:if>
                            <c:if test="${row.type eq 'restO'}">直拨</c:if>
                            <c:if test="${row.type eq 'sipO'}">Sipphone间直拨</c:if>
                            <c:if test="${row.type eq 'C'}">回呼</c:if>
                        </td>
                        <td>${row.thscsum}</td>
                        <td>${row.jfscsum}</td>
                        <td>${row.jflyscsum}</td>
                        <td>${row.fee}</td>
                        <td>${row.recordingfee}</td>
                        <td>${row.tfee}</td>
                    </tr>
                </c:forEach>

                <tr>
                    <td>号码月租</td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td>${sps.rent}</td>
                </tr>
                <tr>
                    <td>低消补扣</td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td>${sps.minconsume}</td>
                </tr>
                <tr>
                    <td>总计</td>
                    <td>${sps.total.thscsum}</td>
                    <td>${sps.total.jfscsum}</td>
                    <td>${sps.total.jflyscsum}</td>
                    <td>${sps.total.fee}</td>
                    <td>${sps.total.recordingfee}</td>
                    <td>${sps.total.tfee}</td>
                </tr>
                </tbody>
            </table>
        </c:if>

        <c:if test="${not empty eccs}">
            <div class="row cl">
                <label class="col-3 text-l">云总机</label>
            </div>
            <table class="table table-border table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <td>类型</td>
                    <td>累计通话时长（秒）</td>
                    <td>计费通话时长（分钟）</td>
                    <td>计费录音时长（分钟）</td>
                    <td>通话费用（元）</td>
                    <td>录音费用（元）</td>
                    <td>合计（元）</td>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${eccs.rows}" var="row">
                    <tr>
                        <td>
                            <c:if test="${row.type eq 'CallInSip'}">呼入总机-SIP</c:if>
                            <c:if test="${row.type eq 'CallInNonSip'}">呼入总机-非SIP</c:if>
                            <c:if test="${row.type eq 'CallInDirect'}">呼入直呼</c:if>
                            <c:if test="${row.type eq 'CallOutLocal'}">呼出市话</c:if>
                            <c:if test="${row.type eq 'CallOutNonLocal'}">呼出长途</c:if>
                        </td>
                        <td>${row.thscsum}</td>
                        <td>${row.jfscsum}</td>
                        <td>${row.jflyscsum}</td>
                        <td>${row.fee}</td>
                        <td>${row.recordingfee}</td>
                        <td>${row.tfee}</td>
                    </tr>
                </c:forEach>

                <tr>
                    <td>总机月租</td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td>${eccs.zjrent}</td>
                </tr>
                <tr>
                    <td>SIP号码月租</td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td>${eccs.rent}</td>
                </tr>
                <tr>
                    <td>低消补扣</td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td class="danger"></td>
                    <td>${eccs.minconsume}</td>
                </tr>
                <tr>
                    <td>总计</td>
                    <td>${eccs.total.thscsum}</td>
                    <td>${eccs.total.jfscsum}</td>
                    <td>${eccs.total.jflyscsum}</td>
                    <td>${eccs.total.fee}</td>
                    <td>${eccs.total.recordingfee}</td>
                    <td>${eccs.total.tfee}</td>
                </tr>
                </tbody>
            </table>
        </c:if>


        <c:if test="${not empty voiceverify}">
            <div class="row cl">
                <label class="col-3 text-l">语音验证码</label>
            </div>
            <table class="table table-border table-bordered table-hover mt-3">
                <thead>
                <tr>
                    <td>计费方式</td>
                    <td>累计通话时长（秒）</td>
                    <td>计费通话时长（分钟）</td>
                    <td>话务总量</td>
                    <td>通话费用（元）</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${voiceverify.rows}" var="voiceverify">
                    <tr>
                        <td>
                            <c:if test="${voiceverify.feemode eq '00'}">按分钟计费</c:if>
                            <c:if test="${voiceverify.feemode eq '01'}">按条计费</c:if>
                        </td>
                        <td>${voiceverify.thscsum}</td>
                        <c:if test="${voiceverify.feemode eq '00'}"><td>${voiceverify.jfscsum}</td></c:if>
                        <c:if test="${voiceverify.feemode eq '01'}"><td class="danger"></td></c:if>
                        <td>${voiceverify.callcnt}</td>
                        <td>${voiceverify.fee}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>

    </form>
</div>
</body>
</html>
