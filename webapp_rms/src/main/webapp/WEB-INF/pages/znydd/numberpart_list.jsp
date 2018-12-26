<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>号段列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="text-c">
        <form id="searchFrom">
            省份名称：<input type="text" id="pname" name="pname" class="input-text" placeholder="省份名称" style="width:200px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            号段：<input type="text" id="telno" name="telno" class="input-text" placeholder="号段" style="width:200px;">
            &nbsp;&nbsp;&nbsp;&nbsp;
            运营商：<select id="operator" name="operator" class="input-text" style="width: 200px" datatype="*"></select>

            &nbsp;&nbsp;&nbsp;&nbsp;
            <button type="button" class="btn btn-success radius" id="sarch" name="sarch"><i class="Hui-iconfont">&#xe665;</i> 查询</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:;" onclick="openTelnoInfoDialog('新增号段','<c:url value="/numberpart/toAdd"/>','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 新增号段</a>
        </span>
        <span class="r">
            <a href="javascript:void(0);" onclick="downloadTelnoInfoReport();" class="btn btn-primary radius r">
                <i class="Hui-iconfont">&#xe600;</i> 导出
            </a>
        </span>
    </div>
    <div>
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
                <tr class="text-c">
                    <th width="5%">序号</th>
                    <th width="">省份名称</th>
                    <th width="">城市</th>
                    <th width="">城市区号</th>
                    <th width="">号段</th>
                    <th width="">运营商</th>
                    <th width="">操作</th>
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
            url: '<c:url value='/numberpart/pageList'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // id,pname,cname,telno,oname
        var columns = [
            { data: 'rowNO' },
            { data: 'pname'},
            { data: 'cname' },
            { data: 'areacode'},
            { data: 'telno'},
            { data: 'oname'},
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
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var _ex_in = "";
                    _ex_in += '<a title="编辑" href="javascript:;" onclick="openTelnoInfoDialog(\'编辑\',\'<c:url value="/numberpart/toEdit?id='+full.id+'"></c:url>\',\'800\',\'500\')" class="ml-5" style="text-decoration:none">编辑</a>';
                    _ex_in += '<a title="查看" href="javascript:;" onclick="openTelnoInfoDialog(\'查看\',\'<c:url value="/numberpart/toShow?id='+full.id+'"></c:url>\',\'600\',\'400\')" class="ml-5" style="text-decoration:none">查看</a>';
                    _ex_in += '<a title="删除" href="javascript:;" onclick="delNumberPart(\''+full.id+'\')" class="ml-5" style="text-decoration:none">删除</a>';
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
                params:{"pname" : $("#pname").val(),"telno" : $("#telno").val(),"operator" : $("#operator").val()}
            };
            datatables.fnDraw();
        });

        initSelect("operator", "<c:url value="/numberpart/queryOperatorList"/>", 'ocode', 'oname', '请选择运营商', '');
    });

    /*$("#operator").select2({
        placeholder: "请选择运营商",
        ajax: {
            type:"post",
            url: "<c:url value='/numberpart/queryOperatorList'/>",
            dataType: 'json',
            delay: 250,
            processResults: function (data) {
                var items = [];
                items.push({id:"all", text:"请选择运营商"});
                $.each(data,function(i,v){
                    items.push({id:v.ocode, text:v.oname});
                });

                return {
                    results: items
                };
            },
            cache: true
        }
        //escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
        //minimumInputLength: 1,
        //templateResult: formatRepo, // omitted for brevity, see the source of this page
        //templateSelection: formatRepoSelection // omitted for brevity, see the source of this page
        //data:data
    });*/

    /*新增号段*/
    function openTelnoInfoDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }

    /* 删除号段 */
    function delNumberPart(id ){
        layer.confirm('是否删除号段？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/numberpart/delete'/>",
                dataType:"json",
                data:{"id":id},
                success:function(data){
                    layer.msg(data.info, {icon: 1});
                    location.replace(location.href);
                }
            });
        });
    }

    /** 导出列表 */
    function downloadTelnoInfoReport() {
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
        window.open("<c:url value='/numberpart/downloadTelnoInfo'/>?" + formData);
    }
</script>
</body>
</html>