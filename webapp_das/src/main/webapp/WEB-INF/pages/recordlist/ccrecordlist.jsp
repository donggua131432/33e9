<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>应用列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-l">
        时间：
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'timemax\')||\'%y-%M-%d %H:%m:%s\'}'})" id="timemin" datatype="*" class="input-text Wdate" style="width:200px;">
        -
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timemin\')}',maxDate:'%y-%M-%d %H:%m:%s'})" id="timemax" class="input-text Wdate" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        接通状态：<select id="connectSucc" name="connectSucc" class="input-text" style="width:200px;">
        <option value="">请选择</option>
        <option value="0">未接通</option>
        <option value="1">接通</option>
    </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        呼叫类型：<select id="abLine" name="abLine" class="input-text" style="width:200px;">
        <option value="">请选择</option>
        <option value="I">呼入</option>
        <option value="O">呼出</option>
    </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        省份：<select id="pname" name="pname" class="input-text" style="width:200px">
        <option value="">请选择</option>
        <c:forEach items="${provice}" var="provice">
            <option value="${provice.pname}">${provice.pname}</option>
        </c:forEach>
    </select>
        &nbsp;&nbsp;&nbsp;&nbsp;

            运营商：<select id="operator" name="operator" class="input-text" style="width:200px;">
            <option value="">请选择</option>
            <option value="00">移动</option>
            <option value="01">联通</option>
            <option value="02">电信</option>
            <option value="06">其他</option>
        </select>
            &nbsp;&nbsp;&nbsp;&nbsp;
        <%--客户名称：<select id="companyName" name="companyName" class="input-text" style="width:200px;"></select>
        <input id="sid" name="sid" type="hidden"/>
        &nbsp;&nbsp;&nbsp;&nbsp;--%>
    </div><br>
    <div class="text-l">
        Sub ID：<input type="text" id="subid" name="subid" class="input-text" placeholder="Sub ID" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        入中继ID：<input type="text" id="incoming" name="incoming" class="input-text" placeholder="入中继ID" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        出中继ID：<input type="text" id="outgoing" name="outgoing" class="input-text" placeholder="出中继ID" style="width:200px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        Account ID：<input type="text" id="sid" name="sid" class="input-text" placeholder="account ID" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        Appid：<input type="text" id="appid" name="appid" class="input-text" placeholder="APP ID" style="width:200px">


    </div>

    <br>
    <div class="text-l">
        主叫号码：<input type="text" id="aTelno" name="aTelno" class="input-text" placeholder="主叫号码" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        被叫号码：<input type="text" id="bTelno" name="bTelno" class="input-text" placeholder="被叫号码" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        通话时长：
        <input id="thsc_min" name="thsc_min" type="text" onkeyup="inputLimitRule('number',this);" class="input-text" style="width:80px;"/>
        -
        <input id="thsc_max" name="thsc_max" type="text" onkeyup="inputLimitRule('number',this);" class="input-text" style="width:80px;"/>
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="downloadReportCommon();" class="btn btn-primary radius r" style="margin-left: 20px;">导出</a>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0);" onclick="downloadRecord('');" class="btn btn-primary radius r">按月导出</a>
    </div>

    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="5%">序号</th>
                <th width="8%">客户名称</th>
                <th width="8%">应用名称</th>
                <th width="8%">呼叫中心名称</th>
                <th width="5%">呼叫类型</th>

                <th width="5%">所属运营商</th>

                <th width="5%">省份</th>
                <th width="5%">城市</th>
                <th width="5%">主叫号码</th>
                <th width="5%">被叫号码</th>
                <th width="5%">接通状态</th>

                <th width="5%">开始时间</th>
                <th width="5%">结束时间</th>
                <th width="4%">通话时长</th>
                <%--<th width="6%">落地线路</th>--%>
                <%--<th width="6%">线路错误码</th>--%>
                <th width="6%">入中继ID</th>
                <th width="6%">出中继ID</th>
                <th width="6%">出中继错误码</th>
                <th width="10%">通话费用</th>
            </tr>
            </thead>
        </table>
    </div>
</div>

<script type="text/javascript">

    var datatables = "";
    var search_param = {};
    var feeid = "";
    var condstr = "";
    var companyName = "";
    var companyNames = [];
    var subInfoArray2 = {};
    var ccName = "";
    var ccNames = [];
    var appNames = [];

    $(document).ready(function() {
        subInfoArray2 = getDataList("<c:url value='/recordlist/getAllCcInfo1'/>","subid","ccname");
        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/recordlist/pageCcCallRecord'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // ac.name companyName, ua.sid, ai.appid appId, ai.app_name appName, ai.status, ai.create_date createDate
        var columns = [
            { data: 'num'},
            { data: 'sid'},
            { data: 'appid'},
            { data: 'ccname'},
            { data: 'abLine'},
            { data: 'operator'},
            { data: 'pname'},
            { data: 'cname'},
            { data: 'zj'},
            { data: 'bj'},
            { data: 'connectSucc'},

            { data: 'qssj'},
            { data: 'jssj'},
            { data: 'thsc'},
            /*{ data: 'outgoing'},*/
            { data: 'incoming'},
            { data: 'outgoing'},
            { data: 'reason'},
            { data: 'fee'}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [1],
                "render": function(data, type, full) {
                    //                    return companyName;
//                    var companyName = data;
                    if (companyNames){
                        $.each(companyNames, function(i,n){
                            if (n.feeid == full.feeid) {
                                companyName = n.name;
                            }
                        });
                    }
                    return companyName;
                }
            },
            {
                "targets": [2],
                "render": function(data, type, full) {
                    var appName = "";
                    if (appNames){
                        $.each(appNames, function(i,n){
                            if (n.appid == full.appid) {
                                appName = n.appName;
                            }
                        });
                    }
                    return appName;
                }
            },

            {
                "targets": [3],
                "render": function(data, type, full) {
                    if (full.subid){

                        full.ccname = subInfoArray2[full.subid];
                    }
                    return full.ccname;
                }
            },




            {
                "targets": [4],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.abLine == 'I') {
                        return '呼入';
                    }
                    if (full.abLine == 'O') {
                        return '呼出';
                    }
                    return full.abLine;
                }
            },
            {
                "targets": [5],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.operator == '00') {
                        return '移动';
                    }
                    if (full.operator == '01') {
                        return '联通';
                    }
                    if (full.operator == '02') {
                        return '电信';
                    }
                    return "其他";
//                    return full.operator;
                }
            },

            {
                "targets": [11],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.qssj) {
                        return o.formatDate(full.qssj, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.qssj;
                }
            },
            {
                "targets": [12],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.jssj) {
                        return o.formatDate(full.jssj, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.jssj;
                }
            },
            {
                "targets": [13],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.thsc) {
                        return full.thsc+"s";
                    }
                    return full.thsc;
                }
            },

            {
                "targets": [10],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.connectSucc == '1') {
                        return '接通';
                    }
                    if (full.connectSucc == '0') {
                        return '未接通';
                    }
                    return full.connectSucc;
                }
            },
            {
                "targets": [15],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.outgoing == undefined) {
                        return '';
                    }else{
                        return full.outgoing;
                    }
                }
            },
            {
                "targets": [16],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.reason == undefined) {
                        return '';
                    }else{
                        return full.reason;
                    }
                }
            }
        ];
        loadApp($("#sid").val());
        datatables = $(".table-sort").dataTable({
            "processing": true, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "dom": 'rtilp<"clear">',
            "aaSorting": [[ 11, "desc" ]], // 默认的排序列（columns）从0开始计数
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs,
            "createdRow": function ( row, data, index ) {
                $('td', row).eq(0).text(index + 1);
            }
        });


        // 搜索功能
        $("#sarch").on("click", function(){
            var min = $("#thsc_min").val();
            var max = $("#thsc_max").val();
            if(min != "" && max != ""){
                if(parseInt(min) > parseInt(max)) {
                    layer.alert("最小通话时长不能大于最大通话时长！",{
                        icon: 0,
                        skin: 'layer-ext-moon'
                    });
                    return;
                }
            }

            index = 1;

            var timemin = $("#timemin").val();
            var timemax = $("#timemax").val();
            if(timemin == "" || timemax == ""){
                layer.alert("查询时间不能为空",{
                    icon: 0,
                    skin: 'layer-ext-moon'
                });
                return;
            }
            var operator =  $("#operator").val();
            var operatorArray;
            if(operator == "06"){
                operatorArray ={
                    key:"operator",
                    type :"array",
                    symbol :"nin",
                    value:"00,01,02"
                };
            }else{
                operatorArray={
                    key:"operator",
                    value:operator
                };
            }

            var conds = [{
                key:"closureTime",
                type:"datetime",
                max:$("#timemax").val(),
                min:$("#timemin").val()
            },{
                key:"thsc",
                type:"number",
                min:$("#thsc_min").val(),
                max:$("#thsc_max").val()
            },{
                key:"subid",
                value:$("#subid").val()
            },{
                key:"appid",
                value:$("#appid").val()
            },
            {
                key:"incoming",
                value:$("#incoming").val()
            },
            {
                key:"outgoing",
                value:$("#outgoing").val()
            },
            {
                key:"abLine",
                value:$("#abLine").val()
            },
            {
                key:"pname",
                value:$("#pname").val()
            },
            {
                key:"connectSucc",
                type:"number",
                value:$("#connectSucc").val()
            },{
                key:"zj",
                value:$("#aTelno").val()
            },{
                key:"bj",
                value:$("#bTelno").val()
            },
                operatorArray
            ];


            // key, keys, value, min, max, symbol, type(datetime,date,time,number,string)
            condstr = JSON.stringify(conds);
            search_param = {
                condstr : condstr,
                feeid : feeid,
                sid:$("#sid").val()
            };

            datatables.fnDraw();
        });

        // 加载用户的信息
        $("#sid").on("blur", function(){
            if(this.value !=""){
                if (/^[0-9A-Za-z]{32}$/.test(this.value)) {
                    loadAppAndCity(this.value);
                } else if(this.value) {
                    layer.alert("account ID 格式不正确",{
                        icon: 0,
                        skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
                    });
                }
            }else {
                feeid = "";
                loadApp("");
            }
        });
        $("#companyName").select2({
            placeholder: "请选择客户名称",
            ajax: {
                url: "<c:url value='/userAdmin/getCompanyNameAndSidByPage'/>",
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return {
                        params: {name:params.term,busType:"01"}, // search term busType ：1 rest用户 2 智能云调度用户
                        page: params.page
                    };
                },
                processResults: function (data, params) {
                    params.page = params.page || 1;

                    var items = [];
                    if(params.page==1){
                        items.push({id:"-1", text:"全部"});
                    }
                    $.each(data.rows,function(i,v){
                        items.push({id:v.sid, text:v.name});
                    });

                    return {
                        results: items,
                        pagination: {
                            more: data.toIndex < data.total
                        }
                    };
                },
                cache: true
            }
        });

        $("#companyName").on("change",function(){
            if(this.value=="-1"){
                $("#sid").val("");
                loadAppAndCity("");
            }else {
                $("#sid").val(this.value);
                loadAppAndCity(this.value);
            }
        });


    });

    /** 导出列表 */
    function downloadReportCommon() {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        var total=datatables.fnSettings()._iRecordsDisplay;
        if(!condstr){
            layer.alert("请输入查询条件",{icon:5});
            return;
        }
        var condParam  = encodeURI(condstr);
        var param = "?code=04&feeid="+ feeid + "&pageCount=" + total+"&conds=" + condParam;

        openDialog("导出","<c:url value='/recordlist/toShowDownPage'/>"+param,"600","400");

    }


    //获取AppInfo键值对数组的信息
    function getDataList(url, id, name, opts) {
        var appInfoArray = {};
        $.ajax({
            type: "post",
            url: url,
            dataType: "json",
            async: false,
            data: opts,
            success: function (data) {
                $(data).each(function (i) {
                    appInfoArray[data[i][id]] = data[i][name];
                });
            },
            error: function (data) {
                console.log("数据请求失败！");
            }
        });
        return appInfoArray;
    }


    function loadApp(sid){
        $.ajax({
            type: "post",
            url: "<c:url value="/userAdmin/loadAppAndCity"/>",
            dataType: "json",
            async: false,
            data: {"sid": sid, "busType": "01"},
            success: function (data) {
                if (data.code == "ok") {
                    if (data.data.appInfos) {
                        appNames = data.data.appInfos;
                    }
                    if (data.data.userAdmins) {
                        companyNames = data.data.userAdmins;
                    }
                }
            }
        })
    }

    var hasSid = false;
    // 填充根据sid得到feeid，用以导出月话单用
    function loadAppAndCity(sid){
        $.ajax({
            type:"post",
            url:"<c:url value="/userAdmin/loadAppAndCity"/>",
            dataType:"json",
            async:false,
            data:{"sid" : sid, "busType" : "01"},
            success : function(data) {
                if(data.code == "ok") {
                    hasSid = true;
                    sid = sid;
                    if(sid!=""){
                        feeid = data.data.userAdmin.feeid;
                        companyName = data.data.userAdmin.name;
                    }
                    if (data.data.appInfos) {
                        appNames = data.data.appInfos;
                    }
                } else {
                    hasSid = false;
                    feeid = "";
                    layer.alert("account ID 不存在",{
                        icon: 0,
                        skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
                    });
                }
            },
            error:function(){
                console.log("数据请求失败！");
                hasSid = false;
                feeid = "";
            }
        });
    }

    // 下载话单
    function downloadRecord() {
        var content = "您暂无历史话单";
        var sid = $("#sid").val();
        if (feeid == ""||sid=="") {
            layer.alert("请先输入正确的account ID",{
                icon: 0,
                skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
            });
            return;
        }

        $.ajax({
            type:"post",
            url:"<c:url value="/monthFile/getStafMonthFileList"/>",
            dataType:"json",
            async:false,
            data:{"stype":"04", feeid : feeid},
            success : function(data) {
                if(data.length > 0) {
                    content = "<br>";
                    var month = monthArray("${currentDate}");
                    for (var i in month) {
                        var c = false;
                        $(data).each(function (j,n) {
                            if (month[i] == o.formatDate(n.sdate, "yyyy年MM月")) {
                                content += "<span style='margin-left:30px;margin-right:50px;'>" + month[i] + "<a style='padding-left:10px' href='<c:url value='/monthFile/monthFileDownload'/>?file=" + n.fname + "'>下载</a></span>";
                                c = true;
                                return false;
                            }
                        });
                        if (!c) {
                            content += "<span style='margin-left:30px;margin-right:50px;'>" + month[i] + "<span style='padding-left:10px;visibility: hidden;'>下载</span></span>";
                        }
                        if(i%3 == 2 ){
                            content += "<br><br>"
                        }
                        else {
                            content += ""
                        }
                    }
                }
            },
            error:function(){
                console.log("数据请求失败！");
            }
        });

        //页面层
        layer.open({
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['600px', '300px'], //宽高
            content: content
        });
    }
</script>
</body>
</html>