<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>临时文件管理</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
    <script type="text/javascript">
        var dataTables = "", search_param = {};
        $(function(){
            //dataTablesAjax 选项
            var dataTablesAjax = {
                url: '<c:url value='/recordlist/pageFileList'/>',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param);
                }
            };

            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns = [
                {data: 'fileName'},
                {data: 'createTime'},
                {data: 'userId'},
                {data: 'source'},
                {data: 'operation'}
            ];
            // 对数据进行处理
            var columnDefs = [
                {
                    "targets": [0,1,2,3,4],
                    "orderable":false,
                    "sClass" : "text-c"
                },
                {"targets": [1],"sClass" : "text-c",
                    "render": function(data, type, full) {
                        if (full.createTime) {
                            return o.formatDate(full.createTime, "yyyy-MM-dd hh:mm:ss");
                        }
                        return full.createTime;
                    }
                },
                {"targets": [3],"sClass" : "text-c",
                    "render": function(data, type, full) {
                        if (full.source=='01') {
                            return '专线语音';
                        }else if (full.source=='03') {
                            return '专号通';
                        }else if (full.source=='02') {
                            return 'SIP对接';
                        }else if (full.source=='04') {
                            return '智能云调度';
                        }else if (full.source=='05') {
                            return '语音通知';
                        }else if (full.source=='06') {
                            return '云话机';
                        }else if (full.source=='07') {
                            return '云总机';
                        }else if (full.source=='08') {
                            return '虚拟小号';
                        }else if (full.source=='09') {
                            return '语音验证码';
                        }
                        return "专线语音";
                    }
                },
                {"targets": [-1], "sClass" : "text-c",
                    "render": function(data, type, full) {
                        var _ex_in = "";
                        var filePath = full.filePath;
                        if(full.status=='00'){
                            _ex_in += '生成中';
                        }else if(full.status=='01'){
                            _ex_in += '<a title="下载" href="<c:url value='/recordlist/downLoadReprot?filePath='/>'+filePath+'"  class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: green;">&#xe695;</i>下载</a>';
                        }
                        _ex_in += '<a title="删除" href="javascript:void(0);" onclick="delFile(\'' + full.id + '\',\'' + full.fileName + '\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: red;">&#xe6e2;</i>删除</a>';
                        return  _ex_in;
                    }
                }
            ];

            //初始化表格渲染
            dataTables = $(".table-sort").dataTable({
                "processing": false, // 是否显示取数据时的那个等待提示
                "serverSide": true,//这个用来指明是通过服务端来取数据
                "bStateSave": true,//状态保存
                "bFilter": false, //隐藏掉自带的搜索功能
                "ordering": false,//关闭排序
//                "autoWidth": true,
                "dom": 'rtilp',
                "sErrMode": 'none',
                "columns": columns,
                ajax: dataTablesAjax,
                "columnDefs":columnDefs
            });


        });

        /** 删除角色 **/
        function delFile(id,name){
            layer.confirm('记录一旦删除将不可以恢复。</br>确定要删除【'+ name +'】吗？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    type:"post",
                    url:"<c:url value='/recordlist/delFile'/>",
                    dataType:"json",
                    data:{"id":id},
                    success : function(data) {
                        layer.alert(data.msg,{
                            icon: data.code == 'ok'? 1 : 2,
                            skin: 'layer-ext-moon'
                        },function (){
                            location.replace(location.href);
                        });
                    },
                    error: function(data){
                        layer.alert("删除失败！",{
                            icon: 2,
                            skin: 'layer-ext-moon'
                        });
                    }
                });
            });
        }

    </script>
</head>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">

    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr class="text-c">
                    <th width="20%">文件名</th>
                    <th width="15%">生成时间</th>
                    <th width="10%">生成用户</th>
                    <th width="20%">数据来源</th>
                    <th width="45%">操作</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
