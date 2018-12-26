<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>云话机通话记录</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
        <div class="row cl">
            时间：
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'{%y-1}-%M-%d %H:%m:%s',maxDate:'#F{$dp.$D(\'timemax\')||\'%y-%M-%d %H:%m:%s\'}'})" id="timemin" datatype="*" class="input-text Wdate" style="width:200px;">
            -
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timemin\')}',maxDate:'%y-%M-%d %H:%m:%s'})" id="timemax" class="input-text Wdate" style="width:200px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            通话类型：<select id="abLine" name="abLine" class="input-text" style="width:200px;">
                        <option value="">全部</option>
                        <option value="1">回拨A路</option>
                        <option value="2">回拨B路</option>
                        <option value="3">SIP phone回拨A路</option>
                        <option value="4">SIP phone回拨B路</option>
                        <option value="5">直拨</option>
                        <option value="6">SIP phone直拨</option>
                        <option value="7">回呼</option>
                    </select>
            &nbsp;&nbsp;&nbsp;&nbsp;
            接通状态：
            <select class="input-text cl-1" id="connectSucc" name="connectSucc" style="width: 200px;">
                <option value="">全部</option>
                <option value="0">未接通</option>
                <option value="1">接通</option>
            </select>
            &nbsp;&nbsp;&nbsp;&nbsp;
            通话时长：
            <input id="thsc_min" name="thsc_min" type="text" onkeyup="inputLimitRule('number',this);" class="input-text" style="width:80px;"/>
            -
            <input id="thsc_max" name="thsc_max" type="text" onkeyup="inputLimitRule('number',this);" class="input-text" style="width:80px;"/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            Callid：
            <input type="text" class="input-text" style="width:186px" placeholder="请输入Call ID" id="callSid" name="callSid"/>
        </div><br>

        <div class="row cl">
            <%--客户名称：<select id="companyName" name="companyName" class="input-text" style="width:200px;"></select>
            &nbsp;&nbsp;&nbsp;&nbsp;--%>
            Account ID：<input type="text" id="sid" name="sid" class="input-text" placeholder="account ID" style="width:200px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
                Appid：<input type="text" id="appid" name="appid" class="input-text" placeholder="Appid" style="width:200px;">
            <%--应用名称：<select id="appid" name="appid" class="input-text" style="width:200px"></select>--%>
            &nbsp;&nbsp;&nbsp;&nbsp;
            主叫号码：
            <input type="text" class="input-text" style="width:186px" placeholder="请输入主叫号码" id="zj" name="zj"/>&nbsp;&nbsp;&nbsp;&nbsp;
            被叫号码：
            <input type="text" class="input-text" style="width:186px" placeholder="请输入被叫号码" id="bj" name="bj"/>&nbsp;&nbsp;&nbsp;&nbsp;
            显号号码：
            <input type="text" class="input-text" style="width:186px" placeholder="请输入被叫号码" id="display" name="display"/>&nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-success radius" id="search"><i class="Hui-iconfont">&#xe665;</i>查询</button>
        </div>

        <div class="cl pd-5 bg-1 bk-gray mt-20">
            <a href="javascript:void(0);" onclick="downloadReportCommon();" class="btn btn-primary radius r"  style="margin-left: 20px;">导出</a>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <a href="javascript:void(0);" onclick="downloadRecord('');" class="btn btn-primary radius r">按月导出</a>
        </div>
        <div class="mt-20">
            <table class="table table-border table-bordered table-hover table-bg table-sort">
                <thead>
                <tr class="text-c">
                    <th style="width: 34px;">序号</th>
                    <th style="width: 100px;">客户</br>名称</th>
                    <th style="width: 100px;">应用</br>名称</th>
                    <th style="width: 40px;">通话</br>类型</th>
                    <th style="width: 200px;">callid</th>
                    <th style="width: 80px;">主叫</br>号码</th>
                    <th style="width: 80px;">被叫</br>号码</th>
                    <th style="width: 80px;">外显</br>号码</th>
                    <th style="width: 40px;">录音</br>状态</th>
                    <th style="width: 50px;">接通</br>状态</th>
                    <th style="width: 70px;">开始</br>时间</th>
                    <th style="width: 70px;">结束</br>时间</th>
                    <th style="width: 50px;">通话</br>时长</th>
                    <th style="width: 40px;">落地</br>线路</th>
                    <th style="width: 40px;">线路错误码</th>
                    <th style="width: 50px;">通话</br>费用</th>
                    <th style="width: 50px;">录音</br>费用</th>
                    <th style="width: 70px;">录音</br>下载</th>
                </tr>
                </thead>
            </table>
        </div>
</div>

<script type="text/javascript">
    var dataTables = "";
    search_param = {};
    var feeid = "";
    var appNames = [];
    var companyName = "";
    var scode = "00";
    var condsPara="";
    $(document).ready(function(){
        var dataTablesAjax = {
            url: '<c:url value="/recordlist/pageSpCallRecord"/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            { data: 'num' },
            { data: 'sid'},
            { data: 'appid'},
            { data: 'abLine'},
            { data: 'callSid', class:"wordbreak"},
            { data: 'zj' },
            { data: 'bj'},
            { data: 'display' },
            { data: 'rcdLine' },
            { data: 'connectSucc'},
            { data: 'qssj'},
            { data: 'jssj' },
            { data: 'thsc' },
            { data: 'outgoing' },
            { data: 'reason' },
            { data: 'fee'},
            { data: 'recordingFee'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17],
                "sClass" : "text-c"
            },
            {
                "targets": [1],
                "render": function(data, type, full) {
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
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if(full.rcdType == 'sipprest'){
                        if(full.abLine == 'A'){
                            if(full.sippB=='true'){
                                return 'SIP phone回拨间A路';
                            }else if(full.sippB=='false'){
                                return '回拨A路';
                            }
                        }
                        if(full.abLine == 'B'){
                            if(full.sippB=='true'){
                                return 'SIP phone回拨间B路';
                            }else if(full.sippB=='false'){
                                return '回拨B路';
                            }
                        }
                    }
                    if (full.rcdType == 'sippout') {
                        if(full.sippB=='true'){
                            return 'SIP phone直拨';
                        }else if(full.sippB=='false'){
                            return '直拨';
                        }
                    }
                    if (full.rcdType == 'sippin') {
                        return '回呼';
                    }
                    return "";
                }
            },
            {
                "targets": [8],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.rcdLine == '0') {
                        return '否';
                    }else {
                        return '是';
                    }
                    return full.rcdLine;
                }
            },
            {
                "targets": [10,11],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (data) {
                        return o.formatDate(data, "yyyy-MM-dd hh:mm:ss");
                    }
                    return data;
                }
            },
            {
                "targets": [9],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.connectSucc == 1) {
                        return '接通';
                    }
                    if (full.connectSucc == 0) {
                        return '未接通';
                    }
                    return full.connectSucc;
                }
            }
            ,
            {
                "targets": [13],
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
                "targets": [14],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.reason == undefined) {
                        return '';
                    }else{
                        return full.reason;
                    }
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var _ex_in = "";
                    var currentDate = new Date().format("yyyy-MM-dd"), days=0;
                    //获取时间差
                    if(full.jssj){
                        days = getDateDiff(o.formatDate(full.jssj, "yyyy-MM-dd"),currentDate);
                    }
                    if(days <= 30){
                        if(full.url!=null && full.url!=""){
                            _ex_in = '<a style="text-decoration:none" href="'+'${appConfig.nasDownLoadPath}' + full.url  + '?auth=${appConfig.rcdDownloadKey}" title="点击下载" class="ml-5">录音下载</a>';
                        }else{
                            _ex_in = '无录音文件';
                        }
                    }else{
                        _ex_in = '文件已过期';
                    }
                    return _ex_in;
                }
            }
        ];
        loadApp($("#companyName").val());
        //初始化表格渲染
        dataTables = $(".table-sort").dataTable({
            "processing": true, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "dom": 'rtilp<"clear">',
            "aaSorting": [[ 10, "desc" ]], // 默认的排序列（columns）从0开始计数
            "columns": columns,
            "ajax": dataTablesAjax,
            "columnDefs":columnDefs,
            "createdRow": function ( row, data, index ) {
                $('td', row).eq(0).text(index + 1);
            }
        });

        // 搜索功能
        $("#search").on("click", function(){
            var timemin = $("#timemin").val();
            var timemax = $("#timemax").val();
            /*if(timemin == "" || timemax == ""){
                layer.alert("查询时间不能为空",{
                    icon: 0,
                    skin: 'layer-ext-moon'
                });
                return;
            }*/
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

            var abLine = $("#abLine").val();
            var rcdType="";
            var abLineSearch="";
            var sippA = "";
            var sippB = "";
            if(abLine=="1"){
                rcdType = "sipprest";
                abLineSearch = "A";
                sippB = false;
            }
            if(abLine=="2"){
                rcdType = "sipprest";
                abLineSearch = "B";
                sippB = false;
            }
            if(abLine=="3"){
                rcdType = "sipprest";
                abLineSearch = "A";
                sippB = true;
            }
            if(abLine=="4"){
                rcdType = "sipprest";
                abLineSearch = "B";
                sippB = true;
            }
            if(abLine=="5"){
                rcdType = "sippout";
                sippB = false;
            }
            if(abLine=="6"){
                rcdType = "sippout";
                sippB = true;
            }
            if(abLine=="7"){
                rcdType = "sippin";
            }

            index = 1;

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
                key:"appid",
                value:$("#appid").val()
            },{
                key:"callSid",
                value:$("#callSid").val()
            },{
                key:"connectSucc",
                type:"number",
                value:$("#connectSucc").val()
            },{
                key:"sid",
                value:$("#sid").val()
            },{
                key:"zj",
                value:$("#zj").val()
            },{
                key:"bj",
                value:$("#bj").val()
            },{
                key:"display",
                value:$("#display").val()
            },{
                key:"rcdType",
                value:rcdType
            },{
                key:"abLine",
                value:abLineSearch
            },{
                key:"sippA",
                type:"boolean",
                value:sippA
            },{
                key:"sippB",
                type:"boolean",
                value:sippB
            }];

            condstr = JSON.stringify(conds);
            condsPara = condstr;
            search_param = {
                condstr : condstr,
                feeid : feeid,
                sid:$("#sid").val()
            };
            dataTables.fnDraw();
        });


        $("#companyName").select2({
            placeholder: "请选择客户名称",
            ajax: {
                url: "<c:url value='/userAdmin/getCompanyNameAndSidByPage'/>",
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return {
                        params: {name:params.term,busType:"05"},
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
                loadAppByUser("");
            }else {
                $("#sid").val(this.value);
                loadAppByUser(this.value);
            }
        });

        // 加载用户的信息
        $("#sid").on("blur", function(){
            var sType = 'sid';
            if(this.value !=""){
                if (/^[0-9A-Za-z]{32}$/.test(this.value)) {
                    loadAppByUser(this.value,sType);
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

    });

    // 加载应用列表
    function loadAppByUser(sid){
        $.ajax({
            type:"post",
            url:"<c:url value="/recordlist/loadApp"/>",
            dataType:"json",
            async:false,
            data:{"sid" : sid, "busType" : "05"},
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
                        fillApps(data.data.appInfos);
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

    function loadApp(sid){
        $.ajax({
            type: "post",
            url: "<c:url value="/recordlist/loadApp"/>",
            dataType: "json",
            async: false,
            data: {"sid": sid,"busType": "05"},
            success: function (data) {
                if (data.code == "ok") {
                    if (data.data.appInfos) {
                        appNames = data.data.appInfos;
                        fillApps(data.data.appInfos);
                    }
                    if (data.data.userAdmins) {
                        companyNames = data.data.userAdmins;
                    }
                }
            }
        })
    }

    function fillApps(appInfos) {
        $("#appid").html("");
        $("#appid").append('<option value="">全部</option>');
        $.each(appInfos, function(i, app){
            $("#appid").append('<option value="'+ app.appid +'">' + app.appName + '</option>');
        });
    }

    /** 导出列表 */
    function downloadReportCommon() {
        var data_table = "";
        if(scode == "00"){
            data_table = dataTables;
        }else if(scode == "01"){
            data_table = dataTables2;
        }else  if(scode == "02"){
            data_table = dataTables3;
        }

        if(data_table._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        var total=data_table.fnSettings()._iRecordsDisplay;
        if(!condsPara){
            layer.alert("请输入查询条件",{icon:5});
            return;
        }
        var condParam  = encodeURI(condsPara);
        var param = "?code=06&feeid="+ feeid + "&pageCount=" + total+"&conds=" + condParam;

        openDialog("导出","<c:url value='/recordlist/toShowDownPage'/>"+param,"600","400");

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
            data:{"stype":"06", feeid : feeid},
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

    /*修改并发数*/
    function openUserDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }
</script>

</body>
</html>
