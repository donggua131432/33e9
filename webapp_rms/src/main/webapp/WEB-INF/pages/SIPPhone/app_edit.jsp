<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>编辑SIP应用</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        var datatables = "";
        var search_param = {};

        $(function(){

            initDicData("voiceCode", "voiceCode", false, '${appInfo.appInfoExtra.voiceCode}');

            checkedBox('recordingTypes', '${appInfo.appInfoExtra.recordingType}');
            checkedBox('valueAddeds', '${appInfo.appInfoExtra.valueAdded}');

            // 初选 被叫号码类型
            function selectCalledType(arr) {
                if (arr) {
                    var arrs = arr.split(",");
                    $("input[name='calledType']").val(arrs);
                }
            }

            $('.skin-minimal input').iCheck({
                checkboxClass: 'icheckbox-blue',
                radioClass: 'iradio-blue',
                increaseArea: '20%'
            });

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
                },
            datatype: {
                "checkAppName":function(gets, obj, curform, datatype){
                    var appName = gets.trim(), matchReg = /^[\s]*[\u4E00-\u9FA50-9a-zA-Z\d\(\)]*[\s]*$/, checkNum = 0;
                    if (matchReg.test(appName)) {
                        if(appName.length < 1 || appName.length > 33){
                            checkNum = 1;
                        }
                    }else{
                        return "应用名称只能由数字、字母、汉字组成！";
                    }
                    if(checkNum == 0){
                        $("#appName").val(appName);
                        return true;
                    }else if(checkNum == 1){
                        return "应用名称应为2-32位数字、字母、汉字组成！";
                    }

                }
            }
            });

        });

        function importMaskNumber(){
            openDialog('批量导入', '<c:url value="/sipNumPool/importSipNumber"/>?&appid=${appInfo.appid}', '600', '260');
        }
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value='/sp/updateApp'/>" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${appInfo.id}" name="id"/>
        <input type="hidden" value="${appInfo.appid}" name="appid"/>
        <input type="hidden" value="${managerType}" name="managerType"/>
        <div class="form form-horizontal" >
            <div class="row cl">
                <label class="form-label col-2">客户名称：</label>
                <div class="formControls col-4">
                    <span>${appInfo.companyName}</span>
                </div>

                <label class="form-label col-2">Account ID：</label>
                <div class="formControls col-4">
                    <span>${appInfo.sid}</span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">应用名称：</label>
                <div class="formControls col-3">
                    <input type="text" class="input-text" value="${appInfo.appName}" placeholder="请输入应用名称" id="appName" name="appName" datatype="checkAppName" nullmsg="应用名称不能为空！" maxlength="32"/>
                </div>
                <div class="col-1"></div>

                <label class="form-label col-2">APP ID：</label>
                <div class="formControls col-4">
                    <span>${appInfo.appid}</span>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-2">增值业务：</label>
                <div class="formControls col-3">
                    <div class="skin-minimal">
                        <div class="check-box">
                            <input type="checkbox" id="valueAdded-0" name="valueAddeds" value="0">
                            <label for="valueAdded-0">SIP phone间回拨</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" id="valueAdded-1" name="valueAddeds" value="1">
                            <label for="valueAdded-1">SIP phone间直拨</label>
                        </div>
                    </div>
                </div>
                <div class="col-1"></div>

                <label class="form-label col-2">录音开关：</label>
                <div class="formControls col-3">
                    <div class="skin-minimal">
                        <div class="check-box">
                            <input type="checkbox" id="recordingType-2" name="recordingTypes" value="0">
                            <label for="recordingType-2">回拨录音</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" id="recordingType-0" name="recordingTypes" value="1">
                            <label for="recordingType-0">直拨录音</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" id="recordingType-1" name="recordingTypes" value="2">
                            <label for="recordingType-1">回呼录音</label>
                        </div>

                    </div>
                </div>
                <div class="col-1"></div>
            </div>


            <div class="row cl">
                <label class="form-label col-2">语音编码：</label>
                <div class="formControls col-3">
                    <select class="input-text" id="voiceCode" name="voiceCode" datatype="*" nullmsg="请选择">
                    </select>
                </div>
                <div class="col-1"></div>

                <label class="form-label col-2">状态：</label>
                <div class="formControls col-2">
                    <div style="margin-top: 3px;">
                        <c:if test="${appInfo.status eq '00'}">
                            <span class="label label-success radius">正常</span>
                        </c:if>
                        <c:if test="${appInfo.status eq '01'}">
                            <span class="label label-warning radius">冻结</span>
                        </c:if>
                        <c:if test="${appInfo.status eq '02'}">
                            <span class="label label-defaunt radius">禁用</span>
                        </c:if>
                    </div>
                </div>
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
        &nbsp;&nbsp;&nbsp;&nbsp;
        SIP号码：<input type="text" id="sipphone" name="sipphone" class="input-text" placeholder="SIP号码" style="width:180px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        固话号码：<input type="text" id="fixphone" name="fixphone" class="input-text" placeholder="固话号码" style="width:180px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        外显号码：<input type="text" id="showNum" name="showNum" class="input-text" placeholder="外显号码" style="width:180px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        状态：<select id="auditStatus" name="auditStatus" class="input-text" style="width:180px;">
            <option value="">全部</option>
            <option value="00">待审核</option>
            <option value="01">正常</option>
            <option value="02">审核不通过</option>
        </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        省份：<select id="pcode" name="pcode" class="input-text" style="width:180px;"></select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        城市：<select id="ccode" name="ccode" class="input-text" style="width:180px;"></select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="deleteShowNums();" class="btn btn-primary radius">删除</a>
        <a href="javascript:void(0);" onclick="openUserDialog('单个添加','<c:url value="/spApply/toAddShowNum?appid=${appInfo.appid}&sid=${appInfo.sid}"/>','600','400');" class="btn btn-primary radius">单个添加</a>
        <a href="javascript:void(0);" onclick="openUserDialog('批量分配','<c:url value="/spApply/toAddApply?appid=${appInfo.appid}&sid=${appInfo.sid}"/>','600','400');" class="btn btn-primary radius">批量分配</a>
        <a href="javascript:void(0);" onclick="openDialog('批量导入','<c:url value="/spImport/toImportShowNum?appid=${appInfo.appid}&sid=${appInfo.sid}"/>','600','260');" class="btn btn-primary radius">批量导入</a>
        <a href="javascript:void(0);" onclick="downloadReport('');" class="btn btn-primary radius r">导出</a>
    </div>

    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="5%"><input type="checkbox" value=""/>序号</th>
                <th width="10%">SIP号码</th>
                <th width="8%">固话号码</th>
                <th width="8%">外显号码</th>
                <th width="7%">外显号码状态</th>
                <th width="5%">省份</th>
                <th width="5%">城市</th>
                <th width="7%">认证密码</th>
                <th width="5%">SIP REALM</th>
                <th width="10%">IP：PORT</th>
                <th width="10%">创建时间</th>
                <th width="5%">长途开关</th>
                <th width="5%">号码状态</th>
                <th width="10%">操作</th>
            </tr>
            </thead>
        </table>
    </div>

</div>
</body>
<script type="text/javascript">


    $(document).ready(function() {

        initPCArea("pcode", "ccode", "全部");

        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/spApply/pageNumList?params[appid]=${appInfo.appid}'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // ac.name companyName, ua.sid, ai.appid appId, ai.app_name appName, ai.status, ai.create_date createDate
        // an.long_distance_flag longDistanceFlag, an.call_switch_flag callSwitchFlag,
        var columns = [
            { data: 'rowNO' },
            { data: 'sipphone'},
            { data: 'fixphone'},
            { data: 'showNum' },
            { data: 'auditStatus' },
            { data: 'pname'},
            { data: 'cname'},
            { data: 'pwd'},
            { data: 'sipRealm'},
            { data: 'ipPort'},
            { data: 'atime'},
            { data: 'longDistanceFlag'},
            { data: 'callSwitchFlag'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,5,6,7,8,9,10,11,12],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [0],
                "render": function(data, type, full) {
                    return '<input type="checkbox" name="shownumid" value="'+full.id+'"/>&nbsp;&nbsp;' + data;
                }
            },
            {
                "targets": [3],
                "render": function(data, type, full) {
                    return full.aShowNum ? full.aShowNum : full.showNum;
                }
            },
            {
                "targets": [4],
                "render": function(data, type, full) {
                    if (full.auditStatus == '00') {
                        return '待审核';
                    }
                    if (full.auditStatus == '01') {
                        return '正常';
                    }
                    if (full.auditStatus == '02') {
                        return '审核不通过';
                    }
                    return '正常';
                }
            },
            {
                "targets": [10],
                "render": function(data, type, full) {
                    if (full.atime) {
                        return o.formatDate(full.atime, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.atime;
                }
            },
            {
                "targets": [11],
                "render": function(data, type, full) {
                    if (data == '00') {
                        return '<span class="label label-success radius">开启</span>';
                    }
                    if (data == '01') {
                        return '<span class="label label-defaunt radius">关闭</span>';
                    }
                    return data;
                }
            },
            {
                "targets": [12],
                "render": function(data, type, full) {
                    if (data == '00') {
                        return '<span class="label label-success radius">开启</span>';
                    }
                    if (data == '01') {
                        return '<span class="label label-defaunt radius">禁用</span>';
                    }
                    return data;
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false, // callSwitchFlag
                "render": function(data, type, full) {
                    var _ex_in = "";
                    _ex_in += '<a title="编辑" href="javascript:;" onclick="openUserDialog(\'编辑\',\'<c:url value="/spApply/toEditShowNum?id=' + full.id + '"/>\',\'400\',\'300\');" class="ml-5" style="text-decoration:none">编辑</a>';

                    if (full.longDistanceFlag == '00') {
                        _ex_in += '<a title="长途开关" href="javascript:;" onclick="updateSipstatus(\'01\',\'longDistanceFlag\',\''+ full.sipphone +'\',\'确定关闭长途？\',\''+ full.id +'\');" class="ml-5" style="text-decoration:none">长途关闭</a>';
                    } else {
                        _ex_in += '<a title="长途开关" href="javascript:;" onclick="updateSipstatus(\'00\',\'longDistanceFlag\',\''+ full.sipphone +'\',\'确定开启长途？\',\''+ full.id +'\');" class="ml-5" style="text-decoration:none">长途开启</a>';
                    }
                    if (full.callSwitchFlag == '00') {
                        _ex_in += '<a title="号码开关" href="javascript:;" onclick="updateSipstatus(\'01\',\'callSwitchFlag\',\''+ full.sipphone +'\',\'确定禁用号码？\',\''+ full.id +'\');" class="ml-5" style="text-decoration:none">号码禁用</a>';
                    } else {
                        _ex_in += '<a title="号码开关" href="javascript:;" onclick="updateSipstatus(\'00\',\'callSwitchFlag\',\''+ full.sipphone +'\',\'确定开启号码？\',\''+ full.id +'\');" class="ml-5" style="text-decoration:none">号码开启</a>';
                    }

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
            "aaSorting": [[ 10, "desc" ]], // 默认的排序列（columns）从0开始计数
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                params:{
                    "auditStatus" : $("#auditStatus").val(),
                    "sipphone" : $("#sipphone").val(),
                    "fixphone" : $("#fixphone").val(),
                    "showNum" : $("#showNum").val(),
                    "pcode" : $("#pcode").val(),
                    "ccode" : $("#ccode").val()
                }
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
        window.open("<c:url value='/spApply/downloadReport'/>?params[appid]=" + '${appInfo.appid}'
                + "&params[auditStatus]=" + $("#auditStatus").val()
                + "&params[sipphone]=" + $("#sipphone").val()
                + "&params[fixphone]=" + $("#fixphone").val()
                + "&params[showNum]=" + $("#showNum").val()
                + "&params[pcode]=" + $("#pcode").val()
                + "&params[ccode]=" + $("#ccode").val()
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
    function deleteShowNums(){
        var showNumIds = "",length = 0;
        $("input[name='shownumid']:checked").each(function(){
            showNumIds += "&id=" + this.value;
            length ++;
        });
        if (!showNumIds) {
            layer.alert("请选择要删除的数据", {icon:0});
            return
        }
        showNumIds = showNumIds.substring(1);
        layer.confirm('是否删除这 ' + length + ' 个外显号？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/spApply/deleteShowNums'/>?" + showNumIds,
                dataType:"json",
                //data:{"subid":''},
                success:function(data){
                    layer.msg(data.info, {icon: 1});
                    location.replace(location.href);
                }
            });
        });
    }

    function updateSipstatus(status,type,sipphone,msg,id) {
        msg = msg + '<br>SIP号码：' + sipphone;
        layer.confirm(msg, {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/spApply/updateSipstatus'/>?id=" + id + "&" + type + "=" + status,
                dataType:"json",
                //data:{name:type,value:status},
                success:function(data){
                    layer.msg(data.msg, {icon: 1});
                    location.replace(location.href);
                }
            });
        });
    }
</script>
</html>
