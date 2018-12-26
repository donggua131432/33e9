<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>话务总览</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<script type="text/javascript">
    $(function(){
        $(".btn-group span").on("click", function(){
            $(this).siblings().removeClass("btn-primary").addClass("btn-default");
            $(this).removeClass("btn-default").addClass("btn-primary");
        });
        //初始加载昨日数据
        showData('${maxDay}');

    });

    // 根据日期数静态切换展示数据
    function showData(dateNum) {
        //加载相应数据
        var data=loadData(dateNum);
        //alert(data.dateNum);
        //展示总收入、累计通话时长、计费通话时长、消费客户数
        $("#thscsum").text(data.thscsum+"秒");
        $("#jfscsum").text(data.jfscsum+"分钟");
        $("#pjsc").text(data.pjsc+"秒");
        if(data.callCompletingRate==null || data.callCompletingRate==0){
            $("#callCompletingRate").text("0%");
        }else{
            $("#callCompletingRate").text(data.callCompletingRate);
        }
        if(data.callAnswerRate==null || data.callAnswerRate==0){
            $("#callAnswerRate").text("0%");
        }else {
            $("#callAnswerRate").text(data.callAnswerRate);
        }


        //展示rest统计
        var tbodyRest="";
        $.each(data.restList,function(i,v){
            tbodyRest +='<tr>';
            tbodyRest +='<td style="text-decoration: none;text-align:center">'+ v.abline+'路</td>';
            tbodyRest +='<td style="text-decoration: none;text-align:center">'+ v.thscsum+'秒</td>';
            tbodyRest +='<td style="text-decoration: none;text-align:center">'+ v.jfscsum+'分钟</td>';
            if(v.pjsc==undefined){
                tbodyRest +='<td style="text-decoration: none;text-align:center">0秒</td>';
            }else{
                tbodyRest +='<td style="text-decoration: none;text-align:center">'+v.pjsc+'秒</td>';
            }
            tbodyRest +='<td style="text-decoration: none;text-align:center">'+v.rcdTime+'秒</td>';
            tbodyRest +='<td style="text-decoration: none;text-align:center">'+v.jflyscsum+'分钟</td>';
            tbodyRest +='<td style="text-decoration: none;text-align:center">'+v.rcdBillRate+'</td>';
            if(v.callCompletingRate==undefined){
                tbodyRest +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodyRest +='<td style="text-decoration: none;text-align:center">'+v.callCompletingRate+'</td>';
            }
            if(v.callAnswerRate==undefined){
                tbodyRest +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodyRest +='<td style="text-decoration: none;text-align:center">'+v.callAnswerRate+'</td>';
            }
            tbodyRest +='</tr>';
        });
        if(!tbodyRest){
            tbodyRest +='<tr>';
            tbodyRest +='<td colspan="9">无数据</td>';
            tbodyRest +='</tr>';
        }

        $("#rest").html(tbodyRest);

        //展示cc统计
        var tbodyCc="";
        $.each(data.ccList,function(i,v){
            tbodyCc +='<tr>';
            tbodyCc +='<td style="text-decoration: none;text-align:center">'+ v.abline+'</td>';
            tbodyCc +='<td style="text-decoration: none;text-align:center">'+ v.thscsum+'秒</td>';
            tbodyCc +='<td style="text-decoration: none;text-align:center">'+ v.jfscsum+'分钟</td>';
            if(v.pjsc==undefined){
                tbodyCc +='<td style="text-decoration: none;text-align:center">0秒</td>';
            }else{
                tbodyCc +='<td style="text-decoration: none;text-align:center">'+v.pjsc+'秒</td>';
            }
            if(v.callCompletingRate==undefined){
                tbodyCc +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodyCc +='<td style="text-decoration: none;text-align:center">'+v.callCompletingRate+'</td>';
            }
            if(v.callAnswerRate==undefined){
                tbodyCc +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodyCc +='<td style="text-decoration: none;text-align:center">'+v.callAnswerRate+'</td>';
            }
            tbodyCc +='</tr>';
        });
        if(!tbodyCc){
            tbodyCc +='<tr>';
            tbodyCc +='<td colspan="6">无数据</td>';
            tbodyCc +='</tr>';
        }

        $("#cc").html(tbodyCc);

        //展示sip统计
        var tbodySip="";
        $.each(data.sipList,function(i,v){
            tbodySip +='<tr>';
            tbodySip +='<td style="text-decoration: none;text-align:center">'+ v.abline+'</td>';
            if(v.thscsum==undefined){
                tbodySip +='<td style="text-decoration: none;text-align:center">0秒</td>';
            }else{
                tbodySip +='<td style="text-decoration: none;text-align:center">'+ v.thscsum+'秒</td>';
            }
            if(v.jfscsum==undefined){
                tbodySip +='<td style="text-decoration: none;text-align:center">0分钟</td>';
            }else{
                tbodySip +='<td style="text-decoration: none;text-align:center">'+ v.jfscsum+'分钟</td>';
            }
            if(v.pjsc==undefined){
                tbodySip +='<td style="text-decoration: none;text-align:center">0秒</td>';
            }else{
                tbodySip +='<td style="text-decoration: none;text-align:center">'+v.pjsc+'秒</td>';
            }

            tbodySip +='<td style="text-decoration: none;text-align:center">'+v.sipNum+'</td>';

            if(v.callCompletingRate==undefined){
                tbodySip +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodySip +='<td style="text-decoration: none;text-align:center">'+v.callCompletingRate+'</td>';
            }
            if(v.callAnswerRate==undefined){
                tbodySip +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodySip +='<td style="text-decoration: none;text-align:center">'+v.callAnswerRate+'</td>';
            }

            tbodySip +='</tr>';
        });
        if(!tbodySip){
            tbodySip +='<tr>';
            tbodySip +='<td colspan="7">无数据</td>';
            tbodySip +='</tr>';
        }

        $("#sip").html(tbodySip);

        //展示Voice统计
        var tbodyVoice="";
        $.each(data.voiceList,function(i,v){
            tbodyVoice +='<tr>';
            tbodyVoice +='<td style="text-decoration: none;text-align:center">'+ v.abline+'</td>';
            if(v.thscsum==undefined){
                tbodyVoice +='<td style="text-decoration: none;text-align:center">0秒</td>';
            }else{
                tbodyVoice +='<td style="text-decoration: none;text-align:center">'+ v.thscsum+'秒</td>';
            }
            if(v.jfscsum==undefined){
                tbodyVoice +='<td style="text-decoration: none;text-align:center">0分钟</td>';
            }else{
                tbodyVoice +='<td style="text-decoration: none;text-align:center">'+ v.jfscsum+'分钟</td>';
            }
            if(v.pjsc==undefined){
                tbodyVoice +='<td style="text-decoration: none;text-align:center">0秒</td>';
            }else{
                tbodyVoice +='<td style="text-decoration: none;text-align:center">'+v.pjsc+'秒</td>';
            }
            if(v.pjcs==undefined){
                tbodyVoice +='<td style="text-decoration: none;text-align:center">0次</td>';
            }else{
                tbodyVoice +='<td style="text-decoration: none;text-align:center">'+v.pjcs+'次</td>';
            }
            if(v.callCompletingRate==undefined){
                tbodyVoice +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodyVoice +='<td style="text-decoration: none;text-align:center">'+v.callCompletingRate+'</td>';
            }
            if(v.callAnswerRate==undefined){
                tbodyVoice +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodyVoice +='<td style="text-decoration: none;text-align:center">'+v.callAnswerRate+'</td>';
            }
            tbodyVoice +='</tr>';
        });
        if(!tbodyVoice){
            tbodyVoice +='<tr>';
            tbodyVoice +='<td colspan="7">无数据</td>';
            tbodyVoice +='</tr>';
        }

        $("#voice").html(tbodyVoice);

        //展示mask统计
        var tbodyMask="";
        $.each(data.maskList,function(i,v){
            tbodyMask +='<tr>';
            tbodyMask +='<td style="text-decoration: none;text-align:center">'+ v.abline+'</td>';
            tbodyMask +='<td style="text-decoration: none;text-align:center">'+ v.thscsum+'秒</td>';
            tbodyMask +='<td style="text-decoration: none;text-align:center">'+ v.jfscsum+'分钟</td>';
            if(v.pjsc==undefined){
                tbodyMask +='<td style="text-decoration: none;text-align:center">0秒</td>';
            }else{
                tbodyMask +='<td style="text-decoration: none;text-align:center">'+v.pjsc+'秒</td>';
            }
            tbodyMask +='<td style="text-decoration: none;text-align:center">'+v.rcdTime+'秒</td>';
            tbodyMask +='<td style="text-decoration: none;text-align:center">'+v.jflyscsum+'分钟</td>';
            tbodyMask +='<td style="text-decoration: none;text-align:center">'+v.rcdBillRate+'</td>';
            if(v.callCompletingRate==undefined){
                tbodyMask +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodyMask +='<td style="text-decoration: none;text-align:center">'+v.callCompletingRate+'</td>';
            }
            if(v.callAnswerRate==undefined){
                tbodyMask +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodyMask +='<td style="text-decoration: none;text-align:center">'+v.callAnswerRate+'</td>';
            }
            tbodyMask +='<td style="text-decoration: none;text-align:center">'+v.maskNum+'</td>';
            tbodyMask +='</tr>';
        });
        if(!tbodyMask){
            tbodyMask +='<tr>';
            tbodyMask +='<td colspan="10">无数据</td>';
            tbodyMask +='</tr>';
        }

        $("#mask").html(tbodyMask);

        //展示sipp统计
        var tbodySipp="";
        $.each(data.sippList,function(i,v){
            tbodySipp +='<tr>';
            tbodySipp +='<td style="text-decoration: none;text-align:center">'+ v.abline+'</td>';
            tbodySipp +='<td style="text-decoration: none;text-align:center">'+ v.thscsum+'秒</td>';
            tbodySipp +='<td style="text-decoration: none;text-align:center">'+ v.jfscsum+'分钟</td>';
            if(v.pjsc==undefined){
                tbodySipp +='<td style="text-decoration: none;text-align:center">0秒</td>';
            }else{
                tbodySipp +='<td style="text-decoration: none;text-align:center">'+v.pjsc+'秒</td>';
            }
            tbodySipp +='<td style="text-decoration: none;text-align:center">'+v.lyscsum+'秒</td>';
            tbodySipp +='<td style="text-decoration: none;text-align:center">'+v.jflyscsum+'分钟</td>';
            if(v.callCompletingRate==undefined){
                tbodySipp +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodySipp +='<td style="text-decoration: none;text-align:center">'+v.callCompletingRate+'</td>';
            }
            if(v.callAnswerRate==undefined){
                tbodySipp +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodySipp +='<td style="text-decoration: none;text-align:center">'+v.callAnswerRate+'</td>';
            }
            tbodySipp +='</tr>';
        });
        if(!tbodySipp){
            tbodySipp +='<tr>';
            tbodySipp +='<td colspan="8">无数据</td>';
            tbodySipp +='</tr>';
        }

        $("#sipp").html(tbodySipp);

        //展示ecc统计
        var tbodyEcc="";
        $.each(data.eccList,function(i,v){
            tbodyEcc +='<tr>';
            tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.rcdtype+'</td>';
            tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.thscsum+'秒</td>';
            tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.jfscsum+'分钟</td>';
            if(v.pjsc==undefined){
                tbodyEcc +='<td style="text-decoration: none;text-align:center">0秒</td>';
            }else{
                tbodyEcc +='<td style="text-decoration: none;text-align:center">'+v.pjsc+'秒</td>';
            }
            tbodyEcc +='<td style="text-decoration: none;text-align:center">'+v.lyscsum+'秒</td>';
            tbodyEcc +='<td style="text-decoration: none;text-align:center">'+v.jflyscsum+'分钟</td>';
            if(v.callCompletingRate==undefined){
                tbodyEcc +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodyEcc +='<td style="text-decoration: none;text-align:center">'+v.callCompletingRate+'</td>';
            }
            if(v.callAnswerRate==undefined){
                tbodyEcc +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodyEcc +='<td style="text-decoration: none;text-align:center">'+v.callAnswerRate+'</td>';
            }
            tbodyEcc +='</tr>';
        });
        if(!tbodyEcc){
            tbodyEcc +='<tr>';
            tbodyEcc +='<td colspan="8">无数据</td>';
            tbodyEcc +='</tr>';
        }

        $("#Ecc").html(tbodyEcc);

        //展示voiceverify统计
        var tbodyVoiceverify="";
        $.each(data.voiceverifyList,function(i,v){
            tbodyVoiceverify +='<tr>';
            tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">'+ v.fee_mode+'</td>';
            if(v.thscsum == undefined){
                tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">0秒</td>';
            }else{
                tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">'+ v.thscsum+'秒</td>';
            }
            if(v.jfts ==undefined){
                tbodyVoiceverify +='<td style="text-decoration: none;text-align:center"></td>';
            }else {
                tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">'+ v.jfts+'条</td>';
            }
            if(v.jfscsum ==undefined){
                tbodyVoiceverify +='<td style="text-decoration: none;text-align:center"></td>';
            }else{
                tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">'+ v.jfscsum+'分钟</td>';
            }

            if(v.pjsc==undefined){
                tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">0秒</td>';
            }else{
                tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">'+v.pjsc+'秒</td>';
            }
            if(v.callCompletingRate==undefined){
                tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">'+v.callCompletingRate+'</td>';
            }
            if(v.callAnswerRate==undefined){
                tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">0%</td>';
            }else{
                tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">'+v.callAnswerRate+'</td>';
            }
            tbodyVoiceverify +='</tr>';
        });
        if(!tbodyVoiceverify){
            tbodyVoiceverify +='<tr>';
            tbodyVoiceverify +='<td colspan="7">无数据</td>';
            tbodyVoiceverify +='</tr>';
        }

        $("#Voiceverify").html(tbodyVoiceverify);
    }

    // 加载数据
    function loadData(dateNum) {
        var reportType = $("#reportType").val();
        if(dateNum==null){
            if(reportType=="1"){
                dateNum = $("#day").val();
            }else {
                dateNum = $("#month").val();
            }
        }else{
            if(reportType=="1"){
                $("#day").val(dateNum);
            }else {
                $("#month").val(dateNum);
            }
        }
        var data;
        $.ajax({
            async: false,
            type: "POST",
            url: "<c:url value='/overviews/tel'/>",
            data: {"dateNum":dateNum,"reportType":reportType},
            success: function(msg){
                data = msg;
            }
        });
        return data;
    }

    //转换报表类型
    function selectReportType(){
        var reportType = $("#reportType").val();
        if(reportType == "1"){
            $("#spanDayReport").show();
            $("#spanMonthReport").hide();
            showData($("#maxDay").val());
        }else{
            $("#spanDayReport").hide();
            $("#spanMonthReport").show();
            showData($("#maxMonth").val());
        }
        $("#stafDay,#stafDay1,#monthStafDay,#sid,#name").val("");
    }

</script>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="row cl pt-20">
        <div class="col-offset-0">类型:</div>
        <div class="col-offset-0 ml-20">
            <select type="text" id="reportType" name="reportType" placeholder="报表类型" class="select" onchange="selectReportType(this);" style="width:160px;height: 31px">
                <option value="1" selected>日报</option>
                <option value="2">月报</option>
            </select>
            <span id="spanDayReport">
                日期：
                <input type="hidden" id="maxDay" value="${maxDay}"/>
                <input autocomplete="off" type="text" value="${maxDay}" class="input-text Wdate" id="day" name="maxDay"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'maxDay\')||\'%y-%M-%d\'}',
                               readOnly:true,isShowClear:false,isShowOK:false,isShowToday:false})" style="width:186px;"/>
            </span>
            <span id="spanMonthReport" style="display: none;">
                月份：
                <input type="hidden" id="maxMonth" value="${maxMonth}"/>
                <input autocomplete="off" type="text" value="${maxMonth}" class="input-text Wdate" id="month" name="maxMonth"
                       onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'maxMonth\')}',
                               readOnly:true,isShowClear:false,isShowOK:false,isShowToday:false})" style="width:186px;"/>
            </span>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-success radius" id="sarch" name="sarch" onclick="showData();"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
            <%--<span class="btn btn-primary radius" style="width: 120px;" onclick="showData(-1);">昨日</span>
            <span class="btn btn-default radius" style="width: 120px;" onclick="showData(-7);">近7日</span>
            <span class="btn btn-default radius" style="width: 120px;" onclick="showData(-30);">近30日</span>--%>
        </div>
    </div>

    <div class="row cl mt-20">
        <div class="panel panel-primary col-5-1">
            <div class="panel-header">累计通话时长</div>
            <div style="height: 80px; text-align: center;">
                <h4 style="margin-top: 50px;" id="thscsum">0秒</h4>
            </div>
        </div>
        <div class="panel panel-secondary col-5-1 ml-50">
            <div class="panel-header">计费通话时长（除6秒）</div>
            <div style="height: 80px; text-align: center;">
                <h4 style="margin-top: 50px;" id="jfscsum">0秒</h4>
            </div>
        </div>
        <div class="panel panel-success col-5-1 ml-50">
            <div class="panel-header">平均通话时长</div>
            <div style="height: 80px; text-align: center;">
                <h4 style="margin-top: 50px;" id="pjsc">0秒</h4>
            </div>
        </div>
        <div class="panel panel-warning col-5-1 ml-50">
            <div class="panel-header">接通率</div>
            <div style="height: 80px; text-align: center;">
                <h4 style="margin-top: 50px;" id="callCompletingRate">0%</h4>
            </div>
        </div>
        </div>
        <div class="row cl mt-20">
            <div class="panel panel-warning col-5-1">
                <div class="panel-header">应答率</div>
                <div style="height: 80px; text-align: center;">
                    <h4 style="margin-top: 50px;" id="callAnswerRate">0%</h4>
                </div>
            </div>
        </div>
    </div>

    <!-- 分割线 -->
    <hr style="border:1px #ddd dotted;" class="mt-20"/>
    <!-- 分割线 -->
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="9">专线语音</th>
            </tr>
            <tr class="text-c">
                <th width="5%"></th>
                <th>累计通话时长</th>
                <th>计费通话时长</th>
                <th>平均通话时长</th>
                <th>录音时长</th>
                <th>计费录音时长</th>
                <th>录音比</th>
                <th>接通率</th>
                <th>应答率</th>
            </tr>
            </thead>
            <tbody id="rest">

            </tbody>
        </table>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="10">专号通</th>
            </tr>
            <tr class="text-c">
                <th width="5%"></th>
                <th>累计通话时长</th>
                <th>计费通话时长</th>
                <th>平均通话时长</th>
                <th>录音时长</th>
                <th>计费录音时长</th>
                <th>录音比</th>
                <th>接通率</th>
                <th>应答率</th>
                <th>隐私号码数</th>
            </tr>
            </thead>
            <tbody id="mask">

            </tbody>
        </table>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="7">语音通知</th>
            </tr>
            <tr class="text-c">
                <th width="5%"></th>
                <th>累计通话时长</th>
                <th>计费通话时长</th>
                <th>平均通话时长</th>
                <th>平均试呼次数</th>
                <th>接通率</th>
                <th>应答率</th>
            </tr>
            </thead>
            <tbody id="voice">

            </tbody>
        </table>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="7">SIP接口</th>
            </tr>
            <tr class="text-c">
                <th width="5%"></th>
                <th>累计通话时长</th>
                <th>计费通话时长</th>
                <th>平均通话时长</th>
                <th>中继数量</th>
                <th>接通率</th>
                <th>应答率</th>
            </tr>
            </thead>
            <tbody id="sip">

            </tbody>
        </table>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="6">智能云调度</th>
            </tr>
            <tr class="text-c">
                <th width="5%"></th>
                <th>累计通话时长</th>
                <th>计费通话时长</th>
                <th>平均通话时长</th>
                <th>接通率</th>
                <th>应答率</th>
            </tr>
            </thead>
            <tbody id="cc">

            </tbody>
        </table>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="8">云话机</th>
            </tr>
            <tr class="text-c">
                <th width="5%"></th>
                <th>累计通话时长</th>
                <th>计费通话时长</th>
                <th>平均通话时长</th>
                <th>录音时长</th>
                <th>计费录音时长</th>
                <th>接通率</th>
                <th>应答率</th>
            </tr>
            </thead>
            <tbody id="sipp">

            </tbody>
        </table>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="8">云总机</th>
            </tr>
            <tr class="text-c">
                <th width="5%"></th>
                <th>累计通话时长</th>
                <th>计费通话时长</th>
                <th>平均通话时长</th>
                <th>录音时长</th>
                <th>计费录音时长</th>
                <th>接通率</th>
                <th>应答率</th>
            </tr>
            </thead>
            <tbody id="Ecc">

            </tbody>
        </table>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="7">语音验证码</th>
            </tr>
            <tr class="text-c">
                <th width="5%"></th>
                <th>累计通话时长</th>
                <th>计费条数</th>
                <th>计费通话时长</th>
                <th>平均通话时长</th>
                <th>接通率</th>
                <th>应答率</th>
            </tr>
            </thead>
            <tbody id="Voiceverify">

            </tbody>
        </table>
    </div>
</div>
</body>
</html>
