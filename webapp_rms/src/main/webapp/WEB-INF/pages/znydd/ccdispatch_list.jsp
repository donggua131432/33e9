<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>话务调度列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-l">
        <form id="searchFrom">
            创建时间：
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'timemax\')||\'%y-%M-%d %H:%m:%s\'}'})" id="timemin" datatype="*" class="input-text Wdate" style="width:180px;">
            -
            <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timemin\')}',maxDate:'%y-%M-%d %H:%m:%s'})" id="timemax" class="input-text Wdate" style="width:180px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            account id：<input type="text" id="sid" name="sid" class="input-text" placeholder="account id" style="width:200px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            客户名称：<input type="text" id="name" name="name" class="input-text" placeholder="客户名称" style="width:200px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            应用名称：<input type="text" id="appName" name="appName" class="input-text" placeholder="应用名称" style="width:200px;">
            <br><br>
            APP ID：<input type="text" id="appid" name="appid" class="input-text" placeholder="APP ID" style="width:200px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            调度区域：<select id="areaId" name="areaId" style="width: 200px" datatype="*">
                        <option value=""></option>
                    </select>
            &nbsp;&nbsp;&nbsp;&nbsp;
            调度名称：<input type="text" id="dispatchName" name="dispatchName" class="input-text" placeholder="调度名称" style="width:200px;">
            <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:;" onclick="openCcDispatchDialog('新增话务调度','<c:url value="/ccdispatch/toAdd"/>','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 新增话务调度</a>
        </span>
        <span class="r">
            <a href="javascript:void(0);" onclick="downloadCcDispatchReport();" class="btn btn-primary radius r">
                <i class="Hui-iconfont">&#xe600;</i> 导出
            </a>
        </span>
    </div>
    <div>
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
                <tr class="text-c">
                    <th width="">序号</th>
                    <th width="">创建时间</th>
                    <th width="">客户名称</th>
                    <th width="">account id</th>
                    <th width="">应用名称</th>
                    <th width="">APP ID</th>
                    <th width="">调度名称</th>
                    <th width="">调度区域</th>
                    <th width="">开始时间</th>
                    <th width="">结束时间</th>
                    <th width="">日期类型</th>
                    <th width="">生效时间</th>
                    <th width="">呼叫中心</th>
                    <th width="8%">操作</th>
                </tr>
            </thead>
        </table>
    </div>
</div>

<script type="text/javascript">

    var datatables = "";
    var search_param = {};
    $(document).ready(function() {

        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/ccdispatch/pageList'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // tc.id,tc.ctime,tc.sid,tuc.name,ta.app_name,tc.appid,tc.ccname,
        // tc.subid,tc.cco_max_num,tc.default_cc,tc.relay1
        var columns = [
            { data: 'rowNO'},
            { data: 'ctime'},
            { data: 'name'},
            { data: 'sid' },
            { data: 'app_name'},
            { data: 'appid'},
            { data: 'dispatchName'},
            { data: 'aname'},
            { data: 'time_start'},
            { data: 'time_end'},
            { data: 'weekday'},
            { data: 'valid_date'},
            { data: 'ccname'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [1],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.ctime) {
                        return o.formatDate(full.ctime, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.ctime;
                }
            },
            {
                "targets": [8],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    var timeStart=full.time_start.toString();
                    if(timeStart != null && timeStart != ""){
                        if(timeStart.length==1){
                            timeStart="00:0"+timeStart;
                        }else if(timeStart.length==2){
                            timeStart="00:"+timeStart;
                        }else if(timeStart.length==3){
                            timeStart="0"+timeStart.substring(0,1)+":"+timeStart.substring(1);
                        }else if(timeStart.length==4){
                            timeStart=timeStart.substring(0,2)+":"+timeStart.substring(2);
                        }
                    }
                    return timeStart;
                }
            },
            {
                "targets": [9],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    var timeEnd=full.time_end.toString();
                    if(timeEnd != null && timeEnd != ""){
                        if(timeEnd.length==1){
                            timeEnd="00:0"+timeEnd;
                        }else if(timeEnd.length==2){
                            timeEnd="00:"+timeEnd;
                        }else if(timeEnd.length==3){
                            timeEnd="0"+timeEnd.substring(0,1)+":"+timeEnd.substring(1);
                        }else if(timeEnd.length==4){
                            timeEnd=timeEnd.substring(0,2)+":"+timeEnd.substring(2);
                        }
                    }
                    return timeEnd;
                }
            },
            {
                "targets": [10],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.weekday) {
                        var weekdayArray = parseInt(full.weekday).toString(2).split("");
                        var weekdayStr="";
                        for(var i=0; i<weekdayArray.length; i++){
                            if(weekdayArray[weekdayArray.length-1-i]==1){
                                if(i == 0){
                                    weekdayStr += "周一,";
                                }
                                if(i == 1){
                                    weekdayStr += "周二,";
                                }
                                if(i == 2){
                                    weekdayStr += "周三,";
                                }
                                if(i == 3){
                                    weekdayStr += "周四,";
                                }
                                if(i == 4){
                                    weekdayStr += "周五,";
                                }
                                if(i == 5){
                                    weekdayStr += "周六,";
                                }
                                if(i == 6){
                                    weekdayStr += "周日,";
                                }
                            }
                        }
                    }
                    weekdayStr = weekdayStr.substring(0,weekdayStr.length-1);
                    return weekdayStr;
                }
            },
            {
                "targets": [11],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.valid_date) {
                        return o.formatDate(full.valid_date, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.valid_date;
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var _ex_in = "";
                    _ex_in += '<a title="编辑调度" href="javascript:;" onclick="openCcDispatchDialog(\'编辑调度\',\'<c:url value="/ccdispatch/toEdit?id='+full.id+'&dispatchId='+full.dispatch_id+'&sid='+full.sid+'"></c:url>\',\'800\',\'500\')" class="ml-5" style="text-decoration:none">编辑调度</a>';
                    _ex_in += '<a title="删除" href="javascript:void(0);" onclick="delDispatch(\''+full.id+'\',\''+full.dispatch_id+'\')" class="ml-5" style="text-decoration:none"></i>删除调度</a>';

                    return _ex_in;
                }
            }
        ];

        datatables = $(".table-sort").dataTable({
            "processing": false, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "dom": 'rtilp<"clear">',
            "aaSorting": [[ 1, "desc" ]], // 默认的排序列（columns）从0开始计数
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                params:{"sid" : $("#sid").val(),"name" : $("#name").val(),"appName" : $("#appName").val(),
                    "appid" : $("#appid").val(),"areaId" : $("#areaId").val(),"dispatchName" : $("#dispatchName").val()},
                timemax:$("#timemax").val(),
                timemin:$("#timemin").val()
            };
            datatables.fnDraw();
        });
    });

    $("#areaId").select2({
        placeholder: "请选择调度区域",
        ajax: {
            type:"post",
            url: "<c:url value='/ccdispatch/queryCcAreaList1'/>",
            dataType: 'json',
            delay: 250,
            data: function (params) {
                return {
                    params: {name:params.term}, // search term
                    page: params.page
                };
            },
            processResults: function (data, params) {
                params.page = params.page || 1;
                var items = [];

                if(params.page==1){
                    items.push({id:"-1", text:"请选择调度区域"});
                }
                $.each(data.rows,function(i,v){
                    items.push({id:v.areaId, text:v.aname});
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
        //escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
        //minimumInputLength: 1,
        //templateResult: formatRepo, // omitted for brevity, see the source of this page
        //templateSelection: formatRepoSelection // omitted for brevity, see the source of this page
        //data:data
    });

    /*创建话务调度*/
    function openCcDispatchDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }

    /*删除话务调度*/
    function delDispatch(id,dispatchId) {
        layer.confirm('是否确认删除？', {
            btn: ['是','否'] //按钮
        }, function() {
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/ccdispatch/deleteDispatch'/>",

                data: {'id': id,'status':'01','dispatchId':dispatchId},//要发送的数据
                success: function (data) {
                    if (data.code == '00') {
                        datatables.fnDraw();
                        layer.alert("成功删除话务调度！", {icon: 1})
                    } else {
                        datatables.fnDraw();
                    }
                },
                error: function (data) {
                    alert("系统异常，请稍后重试！");
                }
            });
        });
    }

    /** 导出列表 */
    function downloadCcDispatchReport() {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        var formData = "";
        $('#searchFrom').find('input[name]').each(function(){
            formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
        });
        $('#searchFrom').find('select[name]').each(function(){
            formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
        });
        formData = formData.substring(1);
        window.open("<c:url value='/ccdispatch/downloadCcDispatch'/>?" + formData);
    }
</script>
</body>
</html>