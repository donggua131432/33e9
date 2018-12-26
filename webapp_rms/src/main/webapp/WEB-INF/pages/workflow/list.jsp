<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>流程列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <style>
        .modal {
            width: 740px;
        }
    </style>
</head>
<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <%--<a href="javascript:;" onclick="data_del()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>--%>
            <a data-toggle="modal" href="#myModal" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 发布流程</a>
            <%--<a href="javascript:;" onclick="openUserDialog('发布流程','<c:url value="/workflow/toDeploy"/>','800','300')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 发布流程</a>--%>
        </span>
    </div>
    <div>
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="15%">名称</th>
                <th width="10%">KEY</th>
                <th width="10%">版本号</th>
                <th width="15%">XML</th>
                <th width="15%">图片</th>
                <th width="12%">部署时间</th>
                <th width="8%">操作</th>
            </tr>
            </thead>
        </table>
    </div>
</div>
<div id="myModal" class="modal hide" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <h3 id="myModalLabel">发布流程</h3><a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form action="<c:url value="/workflow/deploy"/>" method="post" class="form form-horizontal" id="form-member-add" enctype="multipart/form-data">
            <div class="row cl">
                <label class="form-label col-2">文件：</label>
                <div class="col-7">
                    <span class="btn-upload form-group">
                        <input class="input-text upload-url radius" type="text" name="uploadfile-1" id="uploadfile-1" readonly placeholder="只支持zip和bar格式的文件">
                        <a href="javascript:void(0);" class="btn btn-primary radius"><i class="iconfont">&#xf0020;</i> 浏览文件</a>
                        <input type="file" multiple name="file" id="file" class="input-file">
                    </span>
                </div>
                <div class="col-3">
                    <span id="file_error" class="Validform_checktip Validform_wrong" style="display: none;"></span>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary" type="button" onclick="subf();">上传</button> <button type="button" class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
    </div>
</div>
<script type="text/javascript">

    var datatables = "";
    var search_param = {};
    $(document).ready(function() {

        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/workflow/pageWorkflow'/>',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        // ac.name companyName, ua.sid, ai.appid appId, ai.app_name appName, ai.status, ai.create_date createDate
        var columns = [
            { data: 'name' },
            { data: 'key'},
            { data: 'version' },
            { data: 'resourceName'},
            { data: 'diagramResourceName'},
            { data: 'deploymentTime'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [2, 5],
                "sClass" : "text-c"
            },
            {
                "targets": [0,1,2,3,4,5],
                "orderable":false
            },
            {
                "targets": [3],
                "render": function(data, type, full) {
                    return "<a href='<c:url value="/workflow/resource"/>?deploymentId="+full.deploymentId+"&resourceName="+full.resourceName+"' target='_blank'>" + full.resourceName + "</a>";
                }
            },
            {
                "targets": [4],
                "render": function(data, type, full) {
                    return "<a href='<c:url value="/workflow/resource"/>?deploymentId="+full.deploymentId+"&resourceName="+full.diagramResourceName+"' target='_blank'>" + full.diagramResourceName + "</a>";
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c",
                "orderable":false,
                "render": function(data, type, full) {
                    var _ex_in = "";
                    _ex_in += '<a title="删除" href="javascript:;" onclick="delworkflow(' + full.deploymentId + ');" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">删除</i></a>';
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
            "aaSorting": [[ 5, "desc" ]], // 默认的排序列（columns）从0开始计数
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        // 搜索功能
        $("#sarch").on("click", function(){
            search_param = {
                params:{"companyName" : $("#companyName").val(),"appName" : $("#appName").val(),"status" : $("#status").val()},
                timemax:$("#timemax").val(),
                timemin:$("#timemin").val()
            };
            datatables.fnDraw();
        });
    });

    /*管理员-增加*/
    function openUserDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }

    // 表单校验
    function subf(){
        var submitF = true;
        $("input:file").each( function() {//遍历页面里所有的input:file
            var id = this.id;
            var ignore = $("#" + id).attr("ignore");
            if(!ignore){
                var value = this.value;
                if(!value){
                    $('#' + this.id + "_error").html("请选择上传的文件").show();
                    submitF = false;
                    return false;
                } else {
                    var suf = value.substring(value.lastIndexOf(".") + 1);
                    console.log(suf);
                    if (!/^zip|bar$/.test(suf)) {
                        $('#' + this.id + "_error").html("文件类型不正确").show();
                        submitF = false;
                        return false;
                    } else {
                        $('#' + this.id + "_error").hide();
                    }
                }
            }
        });

        if(submitF){
            $("#form-member-add").submit();
        }
    }

    // 删除流程
    function delworkflow(deploymentId) {
        layer.confirm('确认删除该文件？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type:"post",
                url:"<c:url value='/workflow/process/delete'/>",
                dataType:"json",
                data:{"deploymentId":deploymentId},
                success:function(data){
                    if (data.code == "ok") {
                        layer.alert("删除成功", {icon: 1});
                        datatables.fnDraw();
                    } else {
                        layer.alert("删除失败", {icon: 2});
                    }
                }
            });
        });
    }

</script>
</body>
</html>