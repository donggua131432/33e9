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
    <form action="" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${spApply.id}" name="id"/>
        <div class="form form-horizontal" >
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
            <div class="row cl">
                <label class="form-label col-2">审核状态：</label>
                <div class="formControls col-2">
                    <div style="margin-top: 3px;">
                        <c:if test="${spApply.auditStatus eq '00'}">待分配</c:if>
                        <c:if test="${spApply.auditStatus eq '01'}">已分配</c:if>
                        <c:if test="${spApply.auditStatus eq '02'}">审核不通过</c:if>
                    </div>
                </div>

                <label class="form-label col-2">审核原因：</label>
                <div class="formControls col-6">
                    <div style="margin-top: 3px;word-break: break-all;">
                        <c:out value="${spApply.auditCommon}"/>
                    </div>
                </div>
            </div>
        </div>
    </form>

    <!------------------------------------------------------------------------------------------------ 分界线 -------------------------------------------------------------------------------------->

    <h4 class="page_title"></h4>

    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <a href="javascript:void(0);" onclick="downloadReport('');" class="btn btn-primary radius r">导出</a>
    </div>

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
                <th width="10%">创建时间</th>
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
            { data: 'ipPort'},
            { data: 'atime'}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,5,6,7,8,9,10],
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
        window.open("<c:url value='/spApply/downloadReport'/>?params[applyId]=" + '${spApply.id}');
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
