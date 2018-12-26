<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>营收总览</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<script type="text/javascript">
    $(function(){

        $(".btn-group span").on("click", function(){
            $(this).siblings().removeClass("btn-primary").addClass("btn-default");
            $(this).removeClass("btn-default").addClass("btn-primary");
        });
        //初始加载上月数据
        showData('${maxDay}');
    });

    // 根据日期数静态切换展示数据
    function showData(statDay) {
        //加载相应数据
        var data=loadData(statDay);
        //展示总应收账款
        $("#fee").text(data.fee+"元");
        //展示总充值金额
        $("#money").text(data.money+"元");
        //展示注册用户数
        $("#mapC").text(data.mapC+"个");
        //展示新增用户数
        $("#ConNew").text(data.ConNew+"个");
        //展示活跃用户数
        $("#ConActive").text(data.ConActive+"个");

        //加载相应数据
        //应用占比饼图展示
        var legend=[];
        var series=[];

        if(data.typeList.length == 0){
            legend.push("无");
            series.push("无数据");
        }

        $.each(data.typeList,function(i,v){
            legend.push(v.typeName);
            series.push({value: ifnull(v.fee,0), name:v.typeName});
        });
        initOption("应收占比",legend,series);



        //展示消费top 10
        var tbody="";
        $.each(data.topList,function(i,v){
            tbody +='<tr>';
            tbody +='<td>'+ v.rowno+'</td>';
            tbody +='<td>'+v.name+'</td>';
            tbody +='<td>'+ v.sid+'</td>';
            tbody +='<td>'+v.fee+'</td>';
            tbody +='<td>'+v.thscsum/10000+'</td>';
            tbody +='<td>'+v.jfscsum/10000+'</td>';
            tbody +='<td>'+ v.typeName+'</td>';
            tbody +='</tr>';
        });
        if(!tbody){
            tbody +='<tr>';
            tbody +='<td colspan="7">无数据</td>';
            tbody +='</tr>';
        }
        $("#top").html(tbody);


        //展示rest统计

        $.each(data.restFee,function(i,v){
            $("#sumfee").text("应收总额："+v.fee+"元");
        });

        var tbodyRest="";
            tbodyRest +='<tr>';
            $.each(data.restFee,function(i,v){
                tbodyRest +='<td style="text-decoration: none;text-align:center">'+ v.feecall+'元</td>';
            });

            $.each(data.restAbline,function(i,v){
                if (v.abline == 'A'){
                    tbodyRest +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
                }
                if (v.abline == 'B') {
                    tbodyRest += '<td style="text-decoration: none;text-align:center">' + v.pjdj + '元</td>';
                }
            });
            $.each(data.restFee,function(i,v){
                tbodyRest +='<td style="text-decoration: none;text-align:center">'+ v.recordingfee+'元</td>';
            });
            $.each(data.restFee,function(i,v){
                tbodyRest +='<td style="text-decoration: none;text-align:center">'+ v.pjlydj+'元</td>';
            });
                tbodyRest +='<td style="text-decoration: none;text-align:center">'+ data.restCount+'个</td>';

            tbodyRest +='</tr>';

        if(!tbodyRest){
            tbodyRest +='<tr>';
            tbodyRest +='<td colspan="6">无数据</td>';
            tbodyRest +='</tr>';
        }
        $("#rest").html(tbodyRest);


        //专号通--展示---mask
        $("#sumMaskFee").text("应收总额："+data.sumMask+"元");

        var tbodyMask="";
        tbodyMask +='<tr>';
        $.each(data.maskFee,function(i,v){
            tbodyMask +='<td style="text-decoration: none;text-align:center">'+ v.fee+'元</td>';
        });

        $.each(data.maskAbline,function(i,v){
            if (v.abline == 'A'){
                tbodyMask +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';

            }
            if (v.abline == 'B') {
                tbodyMask += '<td style="text-decoration: none;text-align:center">' + v.pjdj + '元</td>';
            }
            if (v.abline == 'C') {
                tbodyMask += '<td style="text-decoration: none;text-align:center">' + v.pjdj + '元</td>';
            }
        });
        $.each(data.maskFee,function(i,v){
            tbodyMask +='<td style="text-decoration: none;text-align:center">'+ v.recordingfee+'元</td>';
        });
        $.each(data.maskFee,function(i,v){
            tbodyMask +='<td style="text-decoration: none;text-align:center">'+ v.pjlydj+'元</td>';
        });
        $.each(data.maskNumFee,function(i,v){
            tbodyMask +='<td style="text-decoration: none;text-align:center">'+ v.fee+'元</td>';
        });
        $.each(data.maskRent,function(i,v){
            tbodyMask +='<td style="text-decoration: none;text-align:center">'+ v.rent+'元</td>';
        });

        tbodyMask +='<td style="text-decoration: none;text-align:center">'+ data.maskCount+'个</td>';

        tbodyMask +='</tr>';

        if(!tbodyMask){
            tbodyMask +='<tr>';
            tbodyMask +='<td colspan="9">无数据</td>';
            tbodyMask +='</tr>';
        }
        $("#mask").html(tbodyMask);


        //语音通知--展示---voice

        $.each(data.voiceFee,function(i,v){
            $("#sumVoiceFee").text("应收总额："+v.fee+"元");
        });

        var tbodyVoice="";
        tbodyVoice +='<tr>';
        $.each(data.voiceFee,function(i,v){
            tbodyVoice +='<td style="text-decoration: none;text-align:center">'+ v.fee+'元</td>';
            tbodyVoice +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
        });

        tbodyVoice +='<td style="text-decoration: none;text-align:center">'+ data.voiceCount+'个</td>';

        tbodyVoice +='</tr>';

        if(!tbodyVoice){
            tbodyVoice +='<tr>';
            tbodyVoice +='<td colspan="3">无数据</td>';
            tbodyVoice +='</tr>';
        }
        $("#voice").html(tbodyVoice);





        //Sip业务--展示---Sip
        $.each(data.sipFee,function(i,v){
            $("#sumSipFee").text("应收总额："+v.fee+"元");
        });


        var tbodySip="";
        tbodySip +='<tr>';
        $.each(data.sipFee,function(i,v){
            tbodySip +='<td style="text-decoration: none;text-align:center">'+ v.fee+'元</td>';
        });
        $.each(data.sipAbline,function(i,v){

            if (v.cycle == '60') {
                tbodySip += '<td style="text-decoration: none;text-align:center">' + v.pjdj + '元</td>';
            }

            if (v.cycle == '6'){
                tbodySip +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
            }
        });
        tbodySip +='<td style="text-decoration: none;text-align:center">'+ data.sipCount+'个</td>';

        tbodySip +='</tr>';

        if(!tbodySip){
            tbodySip +='<tr>';
            tbodySip +='<td colspan="4">无数据</td>';
            tbodySip +='</tr>';
        }
        $("#sip").html(tbodySip);


        //智能云调度业务--展示
        $("#sumCcFee").text("应收总额："+data.sumCc+"元");
        var tbodyCc="";
        tbodyCc +='<tr>';
        $.each(data.ccFee,function(i,v){
            tbodyCc +='<td style="text-decoration: none;text-align:center">'+ v.fee+'元</td>';
        });
        $.each(data.ccAbline,function(i,v){
            if (v.abline == 'I'){
                tbodyCc +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
            }
            if (v.abline == 'O') {
                tbodyCc += '<td style="text-decoration: none;text-align:center">' + v.pjdj + '元</td>';
            }
        });
        $.each(data.ccNumFee,function(i,v){
            tbodyCc +='<td style="text-decoration: none;text-align:center">'+ v.fee+'元</td>';
        });

        tbodyCc +='<td style="text-decoration: none;text-align:center">'+ data.ccCount+'个</td>';

        tbodyCc +='</tr>';

        if(!tbodyCc){
            tbodyCc +='<tr>';
            tbodyCc +='<td colspan="5">无数据</td>';
            tbodyCc +='</tr>';
        }
        $("#znydd").html(tbodyCc);



        //云话机业务

        $.each(data.spFee,function(i,v){
            $("#sumSpFee").text("应收总额："+v.fee+"元");
        });

        var tbodySp="";
        tbodySp +='<tr>';
        $.each(data.spFee,function(i,v){
            tbodySp +='<td style="text-decoration: none;text-align:center">'+ v.feecall+'元</td>';
        });
        $.each(data.spAbline,function(i,v){
            if (v.rcdtype == '回拨A'){
                if(v.pjdj==undefined){
                    tbodySp +='<td style="text-decoration: none;text-align:center">0元</td>';
                }else{
                    tbodySp +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
                }
            }
            if (v.rcdtype == '回拨B') {
                if(v.pjdj==undefined){
                    tbodySp +='<td style="text-decoration: none;text-align:center">0元</td>';
                }else{
                    tbodySp +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
                }
            }
            if (v.rcdtype == 'SipPhone回拨A'){
                if(v.pjdj==undefined){
                    tbodySp +='<td style="text-decoration: none;text-align:center">0元</td>';
                }else{
                    tbodySp +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
                }
            }
            if (v.rcdtype == 'SipPhone回拨B') {
                if(v.pjdj==undefined){
                    tbodySp +='<td style="text-decoration: none;text-align:center">0元</td>';
                }else{
                    tbodySp +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
                }
            }
            if (v.rcdtype == '直拨'){
                if(v.pjdj==undefined){
                    tbodySp +='<td style="text-decoration: none;text-align:center">0元</td>';
                }else{
                    tbodySp +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
                }
            }
            if (v.rcdtype == 'SipPhone直拨') {
                if(v.pjdj==undefined){
                    tbodySp +='<td style="text-decoration: none;text-align:center">0元</td>';
                }else{
                    tbodySp +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
                }
            }
            if (v.rcdtype == '回呼'){
                if(v.pjdj==undefined){
                    tbodySp +='<td style="text-decoration: none;text-align:center">0元</td>';
                }else{
                    tbodySp +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
                }
            }
        });
        $.each(data.spFee,function(i,v){
            tbodySp +='<td style="text-decoration: none;text-align:center">'+ v.recordingfee+'元</td>';
        });
        $.each(data.spFee,function(i,v){
            tbodySp +='<td style="text-decoration: none;text-align:center">'+ v.pjlydj+'元</td>';
        });
        tbodySp +='<td style="text-decoration: none;text-align:center">'+ data.spCount+'个</td>';
        tbodySp +='</tr>';
        if(!tbodySp){
            tbodySp +='<tr>';
            tbodySp +='<td colspan="11">无数据</td>';
            tbodySp +='</tr>';
        }
        $("#sp").html(tbodySp);





        //云总机业务

        if (data.rrType == '1'){
            $.each(data.eccFee,function(i,v){
                $("#sumEccFee").text("应收总额："+v.fee+"元");
            });
        }

        if (data.rrType == '2'){
          $("#sumEccFeeMon").text("应收总额："+data.sumEccMon+"元");
        }

        var tbodyEcc="";
        tbodyEcc +='<tr>';
        $.each(data.eccFee,function(i,v){
            tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.feecall+'元</td>';
        });
        $.each(data.eccAbline,function(i,v){
            if (v.rcdtype == '呼入总机SIP'){
                if(v.pjdj==undefined){
                    tbodyEcc +='<td style="text-decoration: none;text-align:center">0元</td>';
                }else{
                    tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
                }
            }
            if (v.rcdtype == '呼入总机非SIP') {
                if(v.pjdj==undefined){
                    tbodyEcc +='<td style="text-decoration: none;text-align:center">0元</td>';
                }else{
                    tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
                }
            }
            if (v.rcdtype == '呼入直呼'){
                if(v.pjdj==undefined){
                    tbodyEcc +='<td style="text-decoration: none;text-align:center">0元</td>';
                }else{
                    tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
                }
            }
            if (v.rcdtype == '呼出市话') {
                if(v.pjdj==undefined){
                    tbodyEcc +='<td style="text-decoration: none;text-align:center">0元</td>';
                }else{
                    tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
                }
            }
            if (v.rcdtype == '呼出长途'){
                if(v.pjdj==undefined){
                    tbodyEcc +='<td style="text-decoration: none;text-align:center">0元</td>';
                }else{
                    tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.pjdj+'元</td>';
                }
            }
        });
        $.each(data.eccFee,function(i,v){
            tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.recordingfee+'元</td>';
        });
        $.each(data.eccFee,function(i,v){
            tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.pjlydj+'元</td>';
        });

        if (data.rrType == '2'){
            $.each(data.sipRent,function(i,v){
                tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.sipcost+'元</td>';
                tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.pjsipcost+'元</td>';
            });
            $.each(data.zjRent,function(i,v){
                tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.zjcost+'元</td>';
                tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.pjzjcost+'元</td>';
            });

            $.each(data.minCost,function(i,v){
                tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ v.minconsume+'元</td>';
            });
        }

        tbodyEcc +='<td style="text-decoration: none;text-align:center">'+ data.eccCount+'个</td>';
        tbodyEcc +='</tr>';
        if(!tbodyEcc){
            tbodyEcc +='<tr>';
            if (data.rrType == '2') {
                tbodyEcc += '<td colspan="14">无数据</td>';
            }
            if (data.rrType == '1') {
                tbodyEcc += '<td colspan="9">无数据</td>';
            }
            tbodyEcc +='</tr>';
        }
        $("#ecc").html(tbodyEcc);

        //语音验证码业务--展示---voiceverify
        $.each(data.voiceverifyFee,function(i,v){
            $("#sumVoiceverifyFee").text("应收总额："+v.fee+"元");
        });


        var tbodyVoiceverify="";
        tbodyVoiceverify +='<tr>';
        $.each(data.voiceverifyFee,function(i,v){
            tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">'+ v.fee+'元</td>';
        });
        if(data.voiceverifyPjSum.length>0){
            $.each(data.voiceverifyPjSum,function(i,v){

                if (v.fee_mode == '00') {
                    tbodyVoiceverify += '<td style="text-decoration: none;text-align:center">' + v.fzpjdj.toFixed(2) + '元</td>';
                }

                if (v.fee_mode == '01'){
                    tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">'+ v.tspjdj.toFixed(2)+'元</td>';
                }
            });
            tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">'+ data.voiceverifyCount+'个</td>';

            tbodyVoiceverify +='</tr>';
        }else {
            tbodyVoiceverify += '<td style="text-decoration: none;text-align:center">0元</td>';
            tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">0元</td>';
            tbodyVoiceverify +='<td style="text-decoration: none;text-align:center">'+ data.voiceverifyCount+'个</td>';
            tbodyVoiceverify +='</tr>';
        }


        if(!tbodyVoiceverify){
            tbodyVoiceverify +='<tr>';
            tbodyVoiceverify +='<td colspan="4">无数据</td>';
            tbodyVoiceverify +='</tr>';
        }
        $("#voiceverify").html(tbodyVoiceverify);


    }



    // 加载数据
    function loadData(statDay) {
        var data;
        var rType = $("#reportType").val();

        if(statDay==null){
            if(rType=="1"){
                statDay = $("#statday2").val();
            }else {
                statDay = $("#statday").val();
            }
        }else{
            if(rType=="1"){
                $("#statday2").val(statDay);
            }else {
                $("#statday").val(statDay);
            }
        }


        $.ajax({
            async: false,
            type: "POST",
            url: "<c:url value='/overviews/rev'/>",
            data: {"statDay":statDay,"rType":rType},
            success: function(msg){
                data = msg;
            }
        });
        return data;
    }

    // 初始化配置项
    function initOption(title,legend,series) {
        var option = {
            title : {
                text: title,//'应用占比',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: legend
            },
            series : [
                {
                    name: title,
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data: series,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('thscTotal'));

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }


    //转换报表类型
    function selectReportType(){
        var reportType = $("#reportType").val();
        rType = $("#reportType").val();
        if(reportType == "1"){
            rType = reportType
            $("#spanDayReport").show();
            $("#spanMonthReport").hide();
            showData($("#maxDay").val());

            $("#month1").hide();
            $("#month2").hide();
            $("#month3").hide();
            $("#month4").hide();
            $("#month5").hide();
            $("#sumEccFeeMon").hide();
            $("#sumEccFee").show();
        }else{
            $("#spanDayReport").hide();
            $("#spanMonthReport").show();
            showData($("#maxMonth").val());

            $("#month1").show();
            $("#month2").show();
            $("#month3").show();
            $("#month4").show();
            $("#month5").show();
            $("#sumEccFeeMon").show();
            $("#sumEccFee").hide();
        }
//        $("#stafDay,#stafDay1,#monthStafDay,#sid,#name").val("");
    }

</script>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <span>
        类型：
        <select type="text" id="reportType" name="reportType" placeholder="报表类型" class="select" onchange="selectReportType(this);" style="width:160px;height: 28px">
            <option value="2" >月报</option>
            <option value="1" selected>日报</option>
        </select>
    </span>


    <span  id="spanMonthReport" style="display: none;" >
        月份
        <span >
            <input type="hidden" id="maxMonth" value="${maxMonth}"/>
            <input type="hidden" id="minMonth" value="${minMonth}"/>
            <input autocomplete="off" type="text" value="${maxMonth}" class="input-text Wdate" id="statday" name="statday"
                   onfocus="WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'maxMonth\')}',
								   readOnly:true,isShowClear:false,isShowOK:false,isShowToday:false})" style="width:186px;height:28px;"/>
        </span>
    </span>
    <span id="spanDayReport">
        <span>日期</span>
            <input type="hidden" id="maxDay" value="${maxDay}"/>
            <input type="hidden" id="minDay" value="${minDay}"/>
            <input autocomplete="off" type="text" value="${maxDay}" class="input-text Wdate" id="statday2" name="statday2"
                   onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'maxDay\')}',
								   readOnly:true,isShowClear:false,isShowOK:false,isShowToday:false})" style="width:186px;height:28px;"/>
    </span>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <button type="button" class="btn btn-success radius" id="sarch" name="sarch" onclick="showData();"><i class="Hui-iconfont">&#xe665;</i> 查询</button>



    <!-- 分割线 -->
    <hr style="border:1px #ddd dotted;" class="mt-20"/>
    <!-- 分割线 -->

    <h4>平台应收概况</h4>
    <div class="row cl mt-20">
        <div class="panel panel-primary col-5-1">
            <div class="panel-header">应收账款</div>
            <div style="height: 80px; text-align: center;">
                <h4 style="margin-top: 50px;" id="fee">78760元</h4>
            </div>
        </div>
        <div class="panel panel-secondary col-5-1 ml-50">
            <div class="panel-header">线路总成本</div>
            <div style="height: 80px; text-align: center;">
                <h4 style="margin-top: 50px;">暂无</h4>
            </div>
        </div>
        <div class="panel panel-success col-5-1 ml-50">
            <div class="panel-header">毛利</div>
            <div style="height: 80px; text-align: center;">
                <h4 style="margin-top: 50px;">暂无</h4>
            </div>
        </div>
        <div class="panel panel-warning col-5-1 ml-50">
            <div class="panel-header">充值总额度</div>
            <div style="height: 80px; text-align: center;">
                <h4 style="margin-top: 50px;" id="money">23546元</h4>
            </div>
        </div>
    </div>
    <div class="row cl mt-20">
        <div class="panel panel-primary col-5-1">
            <div class="panel-header">注册用户数</div>
            <div style="height: 80px; text-align: center;">
                <h4 style="margin-top: 50px;" id="mapC">1312个</h4>
            </div>
        </div>
        <div class="panel panel-secondary col-5-1 ml-50">
            <div class="panel-header">新增用户数</div>
            <div style="height: 80px; text-align: center;">
                <h4 style="margin-top: 50px;" id="ConNew">34个</h4>
            </div>
        </div>
        <div class="panel panel-success col-5-1 ml-50">
            <div class="panel-header">活跃用户数</div>
            <div style="height: 80px; text-align: center;">
                <h4 style="margin-top: 50px;" id="ConActive">4个</h4>
            </div>
        </div>
    </div>

    <!-- 分割线 -->
    <hr style="border:1px #ddd dotted;" class="mt-20"/>
    <!-- 分割线 -->


    <h4>应收占比数据展示</h4>
    <div class="row cl mt-20">
        <div class="panel panel-success">
            <%--<div class="panel-header">应收占比</div>--%>
            <div  id="thscTotal" style="height: 300px; text-align: center;">

            </div>
        </div>
    </div>


    <!-- 分割线 -->
    <%--<hr style="border:1px #ddd dotted;" class="mt-20"/>--%>
    <%--<!-- 分割线 -->--%>

    <div class="mt-15">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="1" bgcolor="#0000FF">专线语音</th>
                <th scope="col" colspan="5" id="sumfee">专线语音</th>
            </tr>
            <tr class="text-c">
                <th width="10%">应收通话账款</th>
                <th width="20%">平均单价（A路）</th>
                <th width="20%">平均单价（B路）</th>
                <th width="20%">应收录音账款</th>
                <th width="20%">平均录音单价</th>
                <th width="10%">使用用户数</th>
            </tr>
            </thead>
            <tbody id="rest">

            </tbody>
        </table>
    </div>

    <%--<!-- 分割线 -->--%>
    <%--<hr style="border:1px #ddd dotted;" class="mt-20"/>--%>
    <%--<!-- 分割线 -->--%>
    <div class="mt-15">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="1">专号通</th>
                <th scope="col" colspan="8" id="sumMaskFee">专号通</th>
            </tr>
            <tr class="text-c">
                <th rowspan="2" width="10%">应收通话账款</th>
                <th colspan="3" width="10%">平均单价</th>
                <th rowspan="2" width="10%">应收录音账款</th>
                <th rowspan="2" width="10%">平均录音单价</th>
                <th rowspan="2" width="10%">应收号码占用账款</th>
                <th rowspan="2" width="10%">平均号码月租</th>
                <th rowspan="2" width="10%">使用用户数</th>
            </tr>
            <tr class="text-c">
                <th width="10%">A路</th>
                <th width="10%">B路</th>
                <th width="10%">回呼</th>
            </tr>
            </thead>
            <tbody id="mask">

            </tbody>
        </table>
    </div>

    <%--<!-- 分割线 -->--%>
    <%--<hr style="border:1px #ddd dotted;" class="mt-20"/>--%>
    <%--<!-- 分割线 -->--%>
    <div class="mt-15 form-label col-6" >
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="1">语音通知</th>
                <th scope="col" colspan="2" id="sumVoiceFee">语音通知</th>
            </tr>
            <tr class="text-c" >
                <th width="10%">应收通话账款</th>
                <th width="10%">平均单价</th>
                <th width="10%">使用用户数</th>
            </tr>
            </thead>
            <tbody id="voice">

            </tbody>
        </table>
    </div>
    <div class="col-6"></div><br>


    <div><br><br></div>


    <div class="mt-15 form-label col-8">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="1">SIP 接口</th>
                <th scope="col" colspan="3" id="sumSipFee">SIP 接口</th>
            </tr>
            <tr class="text-c">
                <th width="10%">应收通话账款</th>
                <th width="10%">平均单价（分钟）</th>
                <th width="10%">平均单价（6秒）</th>
                <th width="10%">使用用户数</th>
            </tr>
            </thead>
            <tbody id="sip">

            </tbody>
        </table>
    </div>
    <div class="col-4"></div><br>


    <div><br><br></div>


    <div class="mt-15 form-label col-10">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="1">智能云调度</th>
                <th scope="col" colspan="4" id="sumCcFee">智能云调度</th>
            </tr>
            <tr class="text-c">
                <th width="10%">应收通话账款</th>
                <th width="10%">平均单价（呼入）</th>
                <th width="10%">平均单价（呼出）</th>
                <th width="10%">应收线路占用费</th>
                <th width="10%">使用用户数</th>
            </tr>
            </thead>
            <tbody id="znydd">

            </tbody>
        </table>
    </div>
    <div class="col-2"></div><br>


    <div><br><br></div>


    <%--<!-- 分割线 -->--%>
    <%--<hr style="border:1px #ddd dotted;" class="mt-20"/>--%>
    <%--<!-- 分割线 -->--%>
    <div class="mt-15 form-label col-10">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="1">云话机</th>
                <th scope="col" colspan="10" id="sumSpFee">云话机</th>
            </tr>
            <tr class="text-c">
                <th rowspan="2" width="8%">应收通话账款</th>
                <th colspan="7" width="8%">平均单价</th>
                <th rowspan="2" width="8%">应拨录音账款</th>
                <th rowspan="2" width="8%">平均录音单价</th>
                <th rowspan="2" width="8%">使用用户数</th>
            </tr>
            <tr class="text-c">
                <th width="8%">回拨A</th>
                <th width="8%">回拨B</th>
                <th width="8%">SIP phone回拨A</th>
                <th width="8%">SIP phone回拨B</th>
                <th width="8%">直拨</th>
                <th width="8%">SIP phone直拨</th>
                <th width="8%">回呼</th>
            </tr>
            </thead>
            <tbody id="sp">

            </tbody>
        </table>
    </div>


    <%--<!-- 分割线 -->--%>
    <div class="mt-15 form-label col-10">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="1">云总机</th>
                <th scope="col" colspan="13" id="sumEccFeeMon" style="display: none;">云总机(月)</th>
                <th scope="col" colspan="8" id="sumEccFee" >云总机(日)</th>
            </tr>

            <tr class="text-c">
                <th rowspan="2" width="8%">应收<br>通话账款</th>
                <th colspan="5" width="8%">平均单价</th>
                <th rowspan="2" width="8%">应收录音<br>账款</th>
                <th rowspan="2" width="8%">平均录音<br>单价</th>
                <th rowspan="2" width="8%" id="month1" style="display: none;">应收SIP<br>号码月租</th>
                <th rowspan="2" width="8%" id="month2" style="display: none;">平均SIP<br>号码月租</th>
                <th rowspan="2" width="8%" id="month3" style="display: none;">应收总机<br>号码月租</th>
                <th rowspan="2" width="8%" id="month4" style="display: none;">平均总机<br>号码月租</th>
                <th rowspan="2" width="8%" id="month5" style="display: none;">应收抵消补扣</th>
                <th rowspan="2" width="8%">使用<br>用户数</th>
            </tr>
            <tr class="text-c">
                <th width="8%">呼入总机-SIP</th>
                <th width="8%">呼入总机-非SIP</th>
                <th width="8%">呼入-直呼</th>
                <th width="8%">呼出-市话</th>
                <th width="8%">呼出-长途</th>
            </tr>
            </thead>
            <tbody id="ecc">

            </tbody>
        </table>
    </div>

    <div class="mt-15 form-label col-8">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr>
                <th scope="col" colspan="1">语音验证码</th>
                <th scope="col" colspan="3" id="sumVoiceverifyFee">语音验证码</th>
            </tr>
            <tr class="text-c">
                <th width="10%">应收通话账款</th>
                <th width="10%">平均单价（分钟）</th>
                <th width="10%">平均单价（条）</th>
                <th width="10%">使用用户数</th>
            </tr>
            </thead>
            <tbody id="voiceverify">

            </tbody>
        </table>
    </div>
    <div class="col-4"></div><br>


    <div><br><br></div>





    <div class="mt-15 form-label col-10">
        <h4>消费前TOP10客户</h4>
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="5%">排名</th>
                <th width="20%">客户名称</th>
                <th width="20%">account ID</th>
                <th width="10%">应收账款(元)</th>
                <th width="19%">累积通话时长(万秒)</th>
                <th width="10%">计费通话时长(万分钟)</th>
                <th width="25%">使用产品</th>
            </tr>
            </thead>
            <tbody id="top" class="text-c">

            </tbody>
        </table>
    </div>
</div>
</body>
</html>
