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
            $("#auditStatus").on("change", function(){
                if (this.value == '01') {
                    $("#allot").show();
                } else {
                    $("#allot").hide();
                }
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
                }
            });

            // 批量分配
            $("#allot").on("click", function(){
                $.ajax({
                    type: "post",
                    url: "<c:url value='/spApply/allotPage'/>",
                    data: {id:'${spApply.id}'},
                    dataType: "json",
                    success: function(msg){

                        if (msg.code == 'ok') {
                            if (datatables) {
                                datatables.fnClearTable(true);
                                datatables.fnAddData(msg.data,true);
                            } else {
                                initTables(msg.data);
                            }
                            $("#sipFix").val(msg.msg);
                        } else {
                            layer.alert(msg.msg,{icon:5});
                            datatables.fnClearTable(true);
                            $("#sipFix").val("");

                        }
                    }
                });
            });

            // 批量分配
            $("#submit").on("click", function(){

                var auditStatus = $("#auditStatus").val();
                var auditCommon = $("#auditCommon").val();

                if (auditStatus == '02' && !auditCommon) {
                    layer.alert("审核不通过时，请备注原因",{icon:0});
                    return;
                }

                var sipFix = $("#sipFix").val();
                if (auditStatus == '01' && !sipFix) {
                    layer.alert("请先分配号码",{icon:0});
                    return;
                }

                $.ajax({
                    type: "post",
                    url: "<c:url value='/spApply/allotNum'/>",
                    data: {
                        id : '${spApply.id}',
                        appid : '${spApply.appid}',
                        cityid : '${spApply.cityid}',
                        auditStatus : auditStatus,
                        auditCommon : auditCommon,
                        sipFix : sipFix
                    },
                    dataType: "json",
                    success: function(msg){
                        if (msg.code == 'ok') {
                            parent.location.replace(parent.location.href);
                        } else {
                            layer.alert(msg.msg,{icon:5});
                            //datatables.clear().draw();
                        }
                    }
                });
            });
        });
    </script>
</head>
<body>
<div class="pd-20">
    <form action="" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${spApply.id}" name="id"/>
        <input type="hidden" value="" name="sipFix" id="sipFix"/>
        <input type="hidden" value="${spApply.appid}" name="appid" id="appid"/>
        <div class="form form-horizontal">
            <div class="row cl">
                <label class="form-label col-2">省份：</label>
                <div class="formControls col-2">
                    <div style="margin-top: 3px;">${spApply.pname}</div>
                </div>

                <label class="form-label col-2">城市：</label>
                <div class="formControls col-2">
                    <div style="margin-top: 3px;">${spApply.cname}</div>
                </div>

                <label class="form-label col-2">申请人：</label>
                <div class="formControls col-2">
                    <div style="margin-top: 3px;">
                        <c:if test="${spApply.operator eq '00'}">客户</c:if>
                        <c:if test="${spApply.operator eq '01'}">运营人员</c:if>
                    </div>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-2">SIP号码数量：</label>
                <div class="formControls col-2">
                    <div style="margin-top: 3px;">
                        <c:out value="${spApply.amount}"/>
                    </div>
                </div>

                <label class="form-label col-2">SIP号码:固话号码：</label>
                <div class="formControls col-2">
                    <div style="margin-top: 3px;">
                        <c:out value="${spApply.rate}"/>:1
                    </div>
                </div>
                <div class="col-1"></div>
            </div>
            <div class="row cl" style="height: 31px;">
                <label class="form-label col-2">审核状态：</label>
                <div class="formControls col-10">
                    <select class="select" id="auditStatus" name="auditStatus" style="width: 240px;">
                        <option value="01">审核通过</option>
                        <option value="02">审核不通过</option>
                    </select>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input id="allot" class="btn btn-primary radius" type="button" value="&nbsp;&nbsp;分配号码&nbsp;&nbsp;"/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">审核原因：</label>
                <div class="formControls col-10">
                    <textarea class="textarea" id="auditCommon" name="auditCommon" maxlength="150" style="width: 450px;height: 120px" placeholder="如果审核不通过，请备注原因"></textarea>
                </div>
            </div>

            <div class="row cl">
                <div class="col-1"></div>
                <div class="col-7 col-offset-3">
                    <input class="btn btn-primary radius" type="button" id="submit" value="&nbsp;&nbsp;确定&nbsp;&nbsp;"/>
                    <input class="btn btn-default radius" type="button" onclick="layer_close();" value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
                </div>
                <div class="col-2"></div>
            </div>
        </div>
    </form>

    <!------------------------------------------------------------------------------------------------ 分界线 -------------------------------------------------------------------------------------->

    <h4 class="page_title"></h4>

    <div class="mt-20">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="5%">序号</th>
                <th width="10%">SIP号码</th>
                <th width="10%">固话号码</th>
                <th width="10%">外显号码</th>
                <th width="10%">外显号码状态</th>
                <th width="5%">省份</th>
                <th width="5%">城市</th>
                <th width="15%">认证密码</th>
                <th width="5%">SIP REALM</th>
                <th width="10%">IP：PORT</th>
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

    function initTables(data) {

// datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/spApply/pageNumList?params[applyId]=${spApply.id}'/>',
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
            { data: 'sipphone'},
            { data: 'fixphone'},
            { data: 'showNum' },
            { data: 'auditStatus' },
            { data: 'pname'},
            { data: 'cname'},
            { data: 'pwd'},
            { data: 'sipRealm'},
            { data: 'ipPort'}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,5,6,7,8,9],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [3],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    return full.aShowNum ? full.aShowNum : full.showNum;
                }
            },
            {
                "targets": [4],
                "sClass" : "text-c",
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
                    return full.auditStatus;
                }
            },
            {
                "targets": [5],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    return '${spApply.pname}';
                }
            },
            {
                "targets": [6],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    return '${spApply.cname}';
                }
            }
        ];

         datatables = $(".table-sort").dataTable({
             //"processing": false, // 是否显示取数据时的那个等待提示
             //"serverSide": true,//这个用来指明是通过服务端来取数据
             //"bStateSave": true,//状态保存
             "bFilter": false, //隐藏掉自带的搜索功能
             "dom": 'rtilp<"clear">',
             //"aaSorting": [[ 10, "desc" ]], // 默认的排序列（columns）从0开始计数
             //"fnServerData": retrieveData,
             "columns": columns,
             data: data,
             "columnDefs":columnDefs
         });
    }

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

    function limitNum(){
        var len = $("#comment").val().length;
        if(len > 300){
            $("#comment").val($("#comment").val().substring(0,300));
        }
    }

    function checkComment(){
        var comment = $('#comment').val();
        if(!new RegExp("^[ ]+$").test(comment)){
            $(".comment").css("display", "none");
        }
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
