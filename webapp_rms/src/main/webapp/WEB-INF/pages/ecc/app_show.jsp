<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>查看云总机应用</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        var datatables = "";
        var search_param = {};

        $(function(){
            checkedBox('recordingTypes', '${appInfo.appInfoExtra.recordingType}');
        });

    </script>
</head>
<body>
<div class="pd-20">
        <div class="form form-horizontal" >
            <div class="row cl">
                <label class="form-label col-2">客户名称：</label>
                <div class="formControls col-4">
                    <span>${appInfo.companyName}</span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">Account ID：</label>
                <div class="formControls col-4">
                    <span>${appInfo.sid}</span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">应用名称：</label>
                <div class="formControls col-3">
                    <span>${appInfo.appName}</span>
                </div>
                <div class="col-1"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">APP ID：</label>
                <div class="formControls col-4">
                    <span>${appInfo.appid}</span>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-2">录音开关：</label>
                <div class="formControls col-3">
                    <div class="skin-minimal">
                        <div class="check-box">
                            <input type="checkbox" id="recordingType-3" name="recordingTypes" value="3" disabled>
                            <label for="recordingType-3">呼入录音</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" id="recordingType-4" name="recordingTypes" value="4" disabled>
                            <label for="recordingType-4">SIP phone呼出录音</label>
                        </div>
                    </div>
                </div>
                <div class="col-1"></div>
            </div>


            <div class="row cl">
                <label class="form-label col-2">语音编码：</label>
                <div class="formControls col-3">
                    <span>${appInfo.appInfoExtra.voiceCode}</span>
                </div>
                <div class="col-1"></div>
            </div>
            <div class="row cl">
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


    <h4 class="page_title"></h4>

        <h5>总机设置</h5>
        <div class="form form-horizontal" >
            <div class="row cl">
                <label class="form-label col-2">省份：</label>
                <div class="formControls col-1">
                    ${eccInfo.pname}
                </div>

                <label class="form-label col-1">城市：</label>
                <div class="formControls col-1">
                    ${eccInfo.cname}
                </div>
                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">总机号码：</label>
                <div class="formControls col-3">
                    ${eccInfo.switchboard}
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">转接开关：</label>
                <div class="formControls col-3">
                            <c:if test="${eccInfo.transfer==true}">
                                开启
                            </c:if>
                            <c:if test="${eccInfo.transfer==false}">
                                关闭
                            </c:if>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">转接号码：</label>
                <div class="formControls col-3">
                    ${eccInfo.transferNum}
                </div>
            </div>

        </div>
        <h5>IVR设置</h5>

        <div class="form form-horizontal" >

            <div class="row cl">
                <label class="form-label col-2">IVR提示音：</label>
                <div class="formControls col-3">
                                <c:if test="${eccInfo.ivrVoiceNeed==true}">
                                    需要
                                </c:if>
                                <c:if test="${eccInfo.ivrVoiceNeed==false}">
                                    不需要
                                </c:if>
                </div>
                <div class="col-1"></div>
            </div>


            <div class="row cl">
                <label class="form-label col-2">IVR提示音名称：</label>
                <div class="formControls col-3">
                    <div class="skin-minimal">
                        ${eccInfo.ivrVoiceName}
                    </div>
                </div>
                <div class="col-1"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-2">IVR提示音查看：</label>
                <div class="formControls col-3">
                    <c:if test="${empty eccInfo.ivrVoiceUrl}">
                        <a style="color: blue" href="<c:url value="/eccAppInfo/downloadIvr?url=zj_ivr_welcome.wav"/>" download="IVR默认提示音.wav">下载</a>
                    </c:if>
                    <c:if test="${not empty eccInfo.ivrVoiceUrl}">
                        <a style="color: blue" href="<c:url value="/eccAppInfo/downloadIvr?url=${eccInfo.ivrVoiceUrl}"/>" download="${eccInfo.ivrVoiceName}">下载</a>
                    </c:if>
            </div>
                <div class="col-1"></div>
            </div>

        </div>

    <!------------------------------------------------------------------------------------------------ 分界线 -------------------------------------------------------------------------------------->

    <h4 class="page_title"></h4>

    <div class="text-l">
        接听号码：<input type="text" id="phone" name="phone" class="input-text" placeholder="接听号码" style="width:180px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        分机号码：<input type="text" id="subNum" name="subNum" class="input-text" placeholder="分机号码" style="width:180px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        外显号码：<input type="text" id="showNum" name="showNum" class="input-text" placeholder="外显号码" style="width:180px;">
        &nbsp;&nbsp;&nbsp;&nbsp;
        接听类型：<select id="numType" name="numType" class="input-text" style="width:180px;">
        <option value="">全部</option>
        <option value="01">SIP号码</option>
        <option value="02">手机</option>
        <option value="03">固话</option>
    </select>
        <br/><br/>
        省份：<select id="pcode" name="pcode" class="input-text" style="width:180px;"></select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        城市：<select id="ccode" name="ccode" class="input-text" style="width:180px;"></select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        号码状态：<select id="callSwitchFlag" name="callSwitchFlag" class="input-text" style="width:180px;">
        <option value="">全部</option>
        <option value="00">开启</option>
        <option value="01">禁用</option>
    </select>
        &nbsp;&nbsp;&nbsp;&nbsp;
        长途权限：<select id="longDistanceFlag" name="longDistanceFlag" class="input-text" style="width:180px;">
        <option value="">全部</option>
        <option value="00">开启</option>
        <option value="01">关闭</option>
    </select>
        <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
    </div>

    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="5%"><input type="checkbox" value=""/>序号</th>
                <th width="10%">接听号码类型</th>
                <th width="8%">接听号码</th>
                <th width="8%">外显号码</th>
                <th width="7%">分机号</th>
                <th width="5%">省份</th>
                <th width="5%">城市</th>
                <th width="7%">认证密码</th>
                <th width="5%">SIP REALM</th>
                <th width="10%">IP：PORT</th>
                <th width="10%">创建时间</th>
                <th width="5%">长途开关</th>
                <th width="5%">号码状态</th>
                <%--<th width="10%">操作</th>--%>
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
            url: '<c:url value='/eccAppInfo/pageExtention?params[appid]=${appInfo.appid}'/>',
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
            { data: 'numType'},
            { data: 'fixphone'},
            { data: 'showNum' },
            { data: 'subNum' },
            { data: 'pname'},
            { data: 'cname'},
            { data: 'pwd'},
            { data: 'sipRealm'},
            { data: 'ipPort'},
            { data: 'atime'},
            { data: 'longDistanceFlag'},
            { data: 'callSwitchFlag'}/*,
            { data: ''}*/
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
                "targets": [1],
                "render": function(data, type, full) {
                    if (data == '01') {
                        return 'SIP号码';
                    }
                    if (data == '02') {
                        return '手机';
                    }
                    if (data == '03') {
                        return '固话';
                    }
                    return '';
                }
            },
            {
                "targets": [2],
                "render": function(data, type, full) {
                    if (full.numType == '01') {
                        return full.sipphone
                    }
                    return full.fixphone;
                }
            },
            {
                "targets": [3,7,8,9],
                "render": function(data, type, full) {
                    if (full.numType == '01') {
                        return data;
                    }
                    return '';
                }
            },
            {
                "targets": [10],
                "render": function(data, type, full) {
                    if (full.addtime) {
                        return o.formatDate(full.addtime, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.addtime;
                }
            },
            {
                "targets": [11],
                "render": function(data, type, full) {
                    if (full.numType == '01') {
                        if (data == '00') {
                            return '<span class="label label-success radius">开启</span>';
                        }
                        if (data == '01') {
                            return '<span class="label label-defaunt radius">关闭</span>';
                        }
                        return data;
                    }
                    return "";
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
                    "phone" : $("#phone").val(),
                    "subNum" : $("#subNum").val(),
                    "showNum" : $("#showNum").val(),
                    "numType" : $("#numType").val(),
                    "pcode" : $("#pcode").val(),
                    "ccode" : $("#ccode").val(),
                    "callSwitchFlag" : $("#callSwitchFlag").val(),
                    "longDistanceFlag" : $("#longDistanceFlag").val()
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
        window.open("<c:url value='/eccAppInfo/downloadReport'/>?params[appid]=" + '${appInfo.appid}'
                + "&params[phone]=" + $("#phone").val()
                + "&params[subNum]=" + $("#subNum").val()
                + "&params[showNum]=" + $("#showNum").val()
                + "&params[numType]=" + $("#numType").val()
                + "&params[pcode]=" + $("#pcode").val()
                + "&params[ccode]=" + $("#ccode").val()
                + "&params[callSwitchFlag]=" + $("#callSwitchFlag").val()
                + "&params[longDistanceFlag]=" + $("#longDistanceFlag").val()
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
        layer.confirm('是否删除这 ' + length + ' 个分机号？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/eccAppInfo/deleteSubNums'/>?" + showNumIds,
                dataType:"json",
                //data:{"subid":''},
                success:function(data){
                    layer.msg(data.info, {icon: 1});
                    datatables.fnDraw();
                }
            });
        });
    }

    function updateSipstatus(status,type,sipphone,msg,id) {
        msg = msg + '<br>接听号码：' + sipphone;
        layer.confirm(msg, {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/eccAppInfo/updateSubNumStatus'/>?id=" + id + "&" + type + "=" + status,
                dataType:"json",
                //data:{name:type,value:status},
                success:function(data){
                    layer.msg(data.msg, {icon: 1});
                    datatables.fnDraw();
                }
            });
        });
    }
</script>
</html>
