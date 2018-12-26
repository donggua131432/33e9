<%--
   Created by IntelliJ IDEA.
  User: lixin
  Date: 2016/7/14
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>编辑SIP应用</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function(){

            $("#form-member-add").Validform({
                tiptype:2,
                ajaxPost:true,
                callback:function(data){
                    $.Showmsg(data.msg);
                    setTimeout(function(){
                        $.Hidemsg();
                        if(data.code == "ok"){
                            //刷新父级页面
                            parent.location.replace(parent.location.href);
                            //关闭当前窗口
//                            layer_close();
                        }
                    },2000);
                }
            });


        });

        function addMaskNumber(){
            openDialog('单个添加','<c:url value="/sipNumPool/addSipNumber"/>?&appid=${appInfo.appid}','800','450');
        }

        function importMaskNumber(){
            openDialog('批量导入', '<c:url value="/sipNumPool/importSipNumber"/>?&appid=${appInfo.appid}', '600', '260');
        }
        function goNumlistNumber(){
            openDialog('全局号码池展示','<c:url value="/sipNumPool/goNumlist"/>?&appid=${appInfo.appid}&managerType=${managerType}','800','450');
        }


    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value='/sip/updateApp'/>" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${appInfo.id}" name="id"/>
        <input type="hidden" value="${managerType}" name="managerType"/>
        <div class="form form-horizontal" >
            <div class="row cl">
                <label class="form-label col-1">Account ID：</label>
                <div class="formControls col-3">
                    <span>${appInfo.sid}</span>
                </div>

                <label class="form-label col-2">客户名称：</label>
                <div class="formControls col-1">
                    <span>${appInfo.companyName}</span>
                </div>

                <label class="form-label col-2">APP ID：</label>
                <div class="formControls col-2">
                    <span>${appInfo.appid}</span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-1">应用名称：</label>
                <div class="formControls col-2">
                    <input type="text" class="input-text" value="${appInfo.appName}" placeholder="请输入应用名称" id="appName" name="appName" datatype="*2-32" nullmsg="应用名称不能为空！"/>
                </div>
                <div class="col-1"></div>

                <label class="form-label col-2">最大并发数：</label>
                <div class="formControls col-2">
                    <input type="text" class="input-text" value="${appInfo.maxConcurrent}" placeholder="请输入最大并发数" id="maxConcurrent" name="maxConcurrent" datatype="_ajax" data-ajax="{_regex:{type:'maxConcurrent',msg:'格式不正确'}}" maxlength="5" />
                </div>
                <div class="col-2"> </div>
            </div>

            <div class="row cl">
                <label class="form-label col-1">全局号码池：</label>
                <div class="formControls col-3">
                    <button type="button" class="btn btn-primary radius" onclick="addMaskNumber();"><i class="Hui-iconfont">&#xe600;</i>单个添加</button>
                    <button type="button" class="btn btn-primary radius" onclick="importMaskNumber();"><i class="Hui-iconfont">&#xe645;</i>批量导入</button>
                </div>

                <label class="form-label col-2">全局号码池列表：</label>
                <div class="formControls col-2">
                    <%--<a class="btn btn-primary radius" href="<c:url value='/sipNumPool/goNumlist'/>?&appid=${appInfo.appid}&managerType=${managerType}">查看号码&nbsp;&gt;&gt;</a>--%>
                    <a class="btn btn-primary radius" onclick="goNumlistNumber();"><i class="Hui-iconfont">&#xe645;</i>查看号码&nbsp;&gt;&gt;</a>

                </div>
                <div class="col-4"></div>
            </div>
        </div>
        <div class="row cl">
            <div class="col-1"> </div>
            <div class="col-7 col-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset"  value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
            <div class="col-2"> </div>
        </div>
    </form>

<!------------------------------------------------------------------------------------------------ 分界线 -------------------------------------------------------------------------------------->

    <h4 class="page_title"></h4>

    <div class="text-l">
        日期范围：
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'timemax\')||\'%y-%M-%d %H:%m:%s\'}'})" id="timemin" datatype="*" class="input-text Wdate" style="width:200px;">
        -
        <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'timemin\')}',maxDate:'%y-%M-%d %H:%m:%s'})" id="timemax" class="input-text Wdate" style="width:200px;">
        &nbsp;&nbsp;
        子账号名称：<input type="text" id="subName" name="subName" class="input-text" placeholder="子账号名称" style="width:200px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        子账号ID：<input type="text" id="subid" name="subid" class="input-text" placeholder="子账号ID" style="width:200px;">

        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="openUserDialog('新增子账号','<c:url value="/sip/toAddSub?appid=${appInfo.appid}&sid=${appInfo.sid}"></c:url>','800','650');" class="btn btn-primary radius l">新增子账号</a>
        <a href="javascript:void(0);" onclick="downloadReport('');" class="btn btn-primary radius r">导出</a>
    </div>

    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th rowspan="2" width="5%">序号</th>
                <th rowspan="2" width="10%">创建时间</th>
                <th rowspan="2" width="10%">子账号名称</th>
                <th rowspan="2" width="10%">子账号ID</th>
                <th rowspan="2" width="10%">中继名称</th>
                <th rowspan="2" width="10%">中继编号</th>
                <th rowspan="2" width="5%">随机选号</th>
                <th colspan="3" width="15%">费率</th>
                <th rowspan="2" width="5%">计费单位</th>
                <th rowspan="2" width="20%">操作</th>
            </tr>
            <tr class="text-c">
                <th>标准价</th>
                <th>折扣率</th>
                <th>折后价</th>
            </tr>
            </thead>
        </table>
    </div>

</div>
</body>
<script type="text/javascript">

    var datatables = "";
    var search_param = {};
    $(document).ready(function() {

        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/sip/pageSubList?params[appid]=${appInfo.appid}'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // ac.name companyName, ua.sid, ai.appid appId, ai.app_name appName, ai.status, ai.create_date createDate
        var columns = [
            { data: 'rowNO' },
            { data: 'createTime'},
            { data: 'subName'},
            { data: 'subid' },
            { data: 'relayName'},
            { data: 'relayNum'},
            { data: 'numFlag'},
            { data: 'per'},
            { data: 'perDiscount'},
            { data: 'cycle'},
            { data: 'cycle'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,5,6,7,8,9,10],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [1],
                "render": function(data, type, full) {
                    if (full.createTime) {
                        return o.formatDate(full.createTime, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.createTime;
                }
            },
            {
                "targets": [6],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.numFlag == '01') {
                        return '<span class="label label-success radius">打开</span>';
                    }
                    if (full.numFlag == '00') {
                        return '<span class="label label-default radius">关闭</span>';
                    }
                    return full.numFlag;
                }
            },
            {
                "targets": [7],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.cycle == '6') {
                        return Number('${sipRate.per6}').toFixed(4);
                    }
                    if (full.cycle == '60') {
                        return Number('${sipRate.per60}').toFixed(4);
                    }
                    return '';
                }
            },
            {
                "targets": [8],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.cycle == '6') {
                        return full.per6Discount/10;
                    }
                    if (full.cycle == '60') {
                        return full.per60Discount/10;
                    }
                    return '';
                }
            },
            {
                "targets": [9],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.cycle == '6' && full.per6Discount) {
                        return Number((full.per6Discount * '${sipRate.per6}' /1000)).toFixed(4);
                    }
                    if (full.cycle == '60' && full.per60Discount) {
                        return Number((full.per60Discount * '${sipRate.per60}' /1000)).toFixed(4);
                    }
                    return '';
                }
            },
            {
                "targets": [-2],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.cycle == '6') {
                        return '6秒';
                    }
                    if (full.cycle == '60') {
                        return '分钟';
                    }
                    return full.cycle;
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var _ex_in = "";
                    _ex_in += '<a title="查看" href="javascript:;" onclick="openUserDialog(\'查看\',\'<c:url value="/sip/toShowSub?appid=${appInfo.appid}&subid=' + full.subid + '"/>\',\'800\',\'750\');" class="ml-5" style="text-decoration:none">查看</a>';
                    _ex_in += '<a title="编辑" href="javascript:;" onclick="openUserDialog(\'编辑\',\'<c:url value="/sip/toEditSub?appid=${appInfo.appid}&subid=' + full.subid + '"/>\',\'800\',\'800\');" class="ml-5" style="text-decoration:none">编辑</a>';
                    _ex_in += '<a title="删除" href="javascript:;" onclick="deleteSub(\''+full.subid+'\',\''+full.subName+'\');" class="ml-5" style="text-decoration:none">删除</a>';
                    _ex_in += '<a title="号码池管理" href="javascript:;" onclick="openRelayDialog(\'号码池管理\',\'<c:url value="/subNumPool/subNumPoolMgr?subid='+full.subid+'"></c:url>\',\'800\',\'500\')" class="ml-5" style="text-decoration:none">号码池管理</a>';
                    _ex_in += '<a title="黑白名单" href="javascript:;" onclick="openRelayDialog(\'黑白名单\',\'<c:url value="/subBlackWhite/subBlackWhtieMgr?subid='+full.subid+'"></c:url>\',\'800\',\'500\')" class="ml-5" style="text-decoration:none">黑白名单</a>';
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
                params:{
                    "subName" : $("#subName").val(),
                    "subid" : $("#subid").val()
                },
                timemax:$("#timemax").val(),
                timemin:$("#timemin").val()
            };
            datatables.fnDraw();
        });

        $(window).resize(function(){
            $(".table-sort").width("100%");
        });
    });

    // 导出报表
    function downloadReport(type) {
        if(datatables._fnGetDataMaster().length < 1){
            layer.alert("没有数据",{icon:5});
            return;
        }
        //
        window.open("<c:url value='/sip/downloadSubReport'/>?params[relayName]=" + $("#relayName").val()
                + "&params[relayNum]=" + $("#relayNum").val()
                + "&params[appid]=" + '${appInfo.appid}'
                + "&timemax=" + $("#timemax").val()
                + "&timemin=" + $("#timemin").val()
        );
    }

    /*增加*/
    function openUserDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }

    function openRelayDialog(title,url,w,h) {
        layer_full(title,url,w,h);
    }

    /*  */
    function deleteSub( subid, subName ){
        layer.confirm('是否删除 ' + subName + ' ？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/sip/deleteSub'/>",
                dataType:"json",
                data:{"subid":subid},
                success:function(data){
                    layer.msg(data.info, {icon: 1});
                    location.replace(location.href);
                }
            });
        });
    }
</script>
</html>
