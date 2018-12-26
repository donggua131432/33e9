<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>通话记录</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <style>
        .wordbreak{word-break:break-all;}
    </style>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-l">
        时间：
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'{%y-1}-%M-%d %H:%m:%s',maxDate:'#F{$dp.$D(\'timemax\')||\'%y-%M-%d %H:%m:%s\'}'})" id="timemin" datatype="*" class="input-text Wdate" style="width:200px;">
        -
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timemin\')}',maxDate:'%y-%M-%d %H:%m:%s'})" id="timemax" class="input-text Wdate" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        通话类型：<select id="rcdType" name="rcdType" class="input-text" style="width:200px;">
                    <option value="">全部</option>
                    <option value="CallInSip">呼入总机SIP</option>
                    <option value="CallInNonSip">呼入总机非SIP</option>
                    <option value="CallInDirect">呼入直呼</option>
                    <option value="CallOutLocal">呼出市话</option>
                    <option value="CallOutNonLocal">呼出长途</option>
                 </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        接通状态：<select id="connectSucc" name="connectSucc" class="input-text" style="width:200px;">
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
        Callid：<input type="text" id="callSid" name="callSid" class="input-text" placeholder="call ID" style="width:190px">
    </div><br>
        <div class="text-l">
        Account ID：<input type="text" id="sid" name="sid" class="input-text" placeholder="account ID" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        Appid：<input type="text" id="appid" name="appid" class="input-text" placeholder="Appid" style="width:190px">
        &nbsp;&nbsp;&nbsp;&nbsp;
        主叫号码：<input type="text" id="zj" name="zj" class="input-text" placeholder="主叫号码" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        被叫号码：<input type="text" id="bj" name="bj" class="input-text" placeholder="被叫号码" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        外显号码：<input type="text" id="display" name="display" class="input-text" placeholder="显号号码" style="width:200px;">
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div><br>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="downloadReportCommon();" class="btn btn-primary radius r"  style="margin-left: 20px;">导出</a>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="javascript:void(0);" onclick="downloadRecord('');" class="btn btn-primary radius r">按月导出</a>

    </div>

    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="3%">序号</th>
                <th width="8%">客户名称</th>
                <th width="8%">应用名称</th>
                <th width="3%">通话<br>类型</th>
                <th width="9%">callid</th>
                <th width="5%">主叫号码</th>
                <th width="5%">被叫号码</th>
                <th width="2%">外显号码</th>
                <th width="4%">录音<br>状态</th>
                <th width="5%">接通<br>状态</th>
                <th width="10%">开始时间</th>
                <th width="10%">结束时间</th>
                <th width="5%">通话时长</th>
                <th width="4%">落地线路</th>
                <th width="4%">线路返回真实错误码</th>
                <th width="4%">通话<br>费用</th>
                <th width="4%">录音<br>费用</th>
                <th width="5%">录音<br>下载</th>
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
    var appNames = [];
    var index = 1;
    var companyNames = [];

    $(document).ready(function() {
        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/recordlist/pageIvrCallRecord'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            { data: 'num'},
            { data: 'sid'},
            { data: 'appid'},
            { data: 'rcdType'},
            { data: 'callSid', class:"wordbreak"},
            { data: 'zj'},
            { data: 'bj'},
            { data: 'display'},
            { data: 'rcdLine'},
            { data: 'connectSucc'},
            { data: 'qssj'},
            { data: 'jssj'},
            { data: 'thsc'},
            { data: 'outgoing'},
            { data: 'reason'},
            { data: 'fee'},
            { data: 'recordingFee'},
            { data: ''}
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
                            if (n.appid == data) {
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
                    if (full.rcdType == 'CallInSip') {
                        return '呼入总机SIP';
                    }
                    if (full.rcdType == 'CallInNonSip') {
                        return '呼入总机非SIP';
                    }
                    if (full.rcdType == 'CallInDirect') {
                        return '呼入直呼';
                    }
                    if (full.rcdType == 'CallOutLocal') {
                        return '呼出市话';
                    }
                    if (full.rcdType == 'CallOutNonLocal') {
                        return '呼出长途';
                    }
                    return full.rcdType;
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
                "targets": [9],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.connectSucc == 1) {
                        return '接通';
                    }
                    if (full.connectSucc == 0) {
                        return '未接通';
                    }
                    return full.rcdType;
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
        loadApp("");
        datatables = $(".table-sort").dataTable({
            "processing": true, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "dom": 'rtilp<"clear">',
            "aaSorting": [[ 10, "desc" ]], // 默认的排序列（columns）从0开始计数
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

            var timemin = $("#timemin").val();
            var timemax = $("#timemax").val();
            if(timemin == "" || timemax == ""){
                layer.alert("查询时间不能为空",{
                    icon: 0,
                    skin: 'layer-ext-moon'
                });
                return;
            }

            index = 1;

            var conds = [{
                key:"closureTime",
                type:"datetime",
                max:$("#timemax").val(),
                min:$("#timemin").val()
            },{
                key:"appid",
                value:$("#appid").val()
            },{
                key:"sid",
                value:$("#sid").val()
            },{
                key:"rcdType",
                value:$("#rcdType").val()
            },{
                key:"callSid",
                value:$("#callSid").val()
            },{
                key:"connectSucc",
                type:"number",
                value:$("#connectSucc").val()
            },{
                key:"display",
                value:$("#display").val()
            },{
                key:"zj",
                value:$("#zj").val()
            },{
                key:"bj",
                value:$("#bj").val()
            },{
                key:"thsc",
                type:"number",
                min:$("#thsc_min").val(),
                max:$("#thsc_max").val()
            }];

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
                    loadApp(this.value);
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
        var param = "?code=07&feeid="+ feeid + "&pageCount=" + total+"&conds=" + condParam;

        openDialog("导出","<c:url value='/recordlist/toShowDownPage'/>"+param,"600","400");

    }

    function loadApp(sid){
        $.ajax({
            type: "post",
            url: "<c:url value="/recordlist/loadApp"/>",
            dataType: "json",
            async: false,
            data: {"sid" : sid,"busType": "06"},
            success: function (data) {
                if (data.code == "ok") {
                    if(sid!="" && sid!=null){
                        feeid = data.data.userAdmins.feeid;
                    }
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
            data:{"stype":"07", feeid : feeid},
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