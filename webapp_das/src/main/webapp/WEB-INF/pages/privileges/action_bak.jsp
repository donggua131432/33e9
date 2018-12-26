<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>权限管理</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/page-tmpl/jquery.tmpl.js"></script>
    <script src="${appConfig.resourcesRoot}/js/plugins/page-tmpl/paging.js"></script>
</head>

<body>

<jsp:include page="../common/nav.jsp"/>

<div class="pd-20">
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:;" onclick="admin_add('新增菜单','<c:url value="/menu/toAddMenu"/>?sysType=${sysType}','800','500')" class="btn btn-primary radius"> <i class="Hui-iconfont">&#xe600;</i> 新增菜单</a>
        </span>
    </div>

    <table id="example" class="table table-border table-bordered table-bg" cellspacing="0" width="100%">
        <thead class="text-c">
        <tr>
            <th>序号</th>
            <th>菜单名称</th>
            <th>路径</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="integralBody">

        </tbody>
    </table>

</div>

<script id="integralTemplate" type="text/x-jquery-tmpl">
    <tr>
        <td class="text-c">\${index + 1}</td>
        <td>\${name}</td>
        <td>\${url}</td>
        <td class="td-manage text-c">
            <a title="添加" href="javascript:;" onclick="admin_edit('管理员编辑','<c:url value="/menu/toEditMenu"></c:url>','\${id}','800','500')" class="ml-5" style="text-decoration:none">添加</a>
            <a title="展开" href="javascript:;" onclick="admin_open(this,'\${id}')" class="ml-5" style="text-decoration:none">展开</a>
        </td>
    </tr>
    {{if actions.length == 0}}
    <tr name="m\${id}" style="display:none;">
        <td colspan="4" class="text-c">暂无数据</td>
    </tr>
    {{else}}
    {{each(i,action) actions}}
    <tr name="m\${action.menuId}" style="display:none;">
        <td class="text-c">\${i + 1}</td>
        <td>\${action.typeName}</td>
        <td>\${action.url}</td>
        <td class="td-manage text-c">
            <a title="编辑" href="javascript:;" onclick="admin_edit('管理员编辑','<c:url value="/menu/toEditMenu"></c:url>','\${action.id}','800','500')" class="ml-5" style="text-decoration:none">编辑</a>
            <a title="删除" href="javascript:;" onclick="admin_del(this,'\${action.id}')" class="ml-5" style="text-decoration:none">删除</a>
        </td>
    </tr>
    {{/each}}
    {{/if}}
</script>

<script type="text/javascript">
    /*
     参数解释：
     title	标题
     url	请求的url
     id		需要操作的数据id
     w		弹出层宽度（缺省调默认值）
     h		弹出层高度（缺省调默认值）
     */
    /*管理员-增加*/
    function admin_add(title,url,w,h){
        layer_show(title,url,w,h);
    }
    /*管理员-删除*/
    function admin_del(obj,id){
        layer.confirm('确认要删除吗？',function(index){
            //此处请求后台程序，下方是成功后的前台处理……
            $.ajax({
                type : "POST",
                data :{ id : id },
                url : "<c:url value='/menu/deleteMenu'/>",
                dataType : 'json',
                success : function(msg) {
                    if (msg.code == "ok") {
                        // $(obj).parents("tr").remove();
                        table_page.reload();
                        layer.msg('已删除!',{icon:1,time:1000});
                    } else if (msg.code == "hasChildren") {
                        layer.msg('存在子菜单，不能删除!',{icon:1,time:2000});
                    } else {
                        layer.msg('删除失败，请再次尝试!',{icon:1,time:2000});
                    }
                }
            });

        });
    }

    /*管理员-删除*/
    function admin_open(obj,id){
        var tname = "m" + id;
        $("tr[name='" + tname + "']").show();
    }

    /*管理员-编辑*/
    function admin_edit(title,url,id,w,h){
        url = url + "?id=" + id;
        layer_show(title,url,w,h);
    }
    /*管理员-停用*/
    function admin_stop(obj,id){
        layer.confirm('确认要停用吗？',function(index){
            //此处请求后台程序，下方是成功后的前台处理……
            $.ajax({
                type : "POST",
                data :{ id : id, state : 1 },
                url : "<c:url value='/menu/updateMenuState'/>",
                dataType : 'json',
                success : function(msg) {
                    if (msg.code == "ok") {
                        // $(obj).parents("tr").remove();
                        table_page.reload();
                        layer.msg('已停用!',{icon: 5,time:1000});
                    } else {
                        layer.msg('停用失败，请再次尝试!',{icon:1,time:1000});
                    }
                }
            });

        });
    }

    /*管理员-启用*/
    function admin_start(obj,id){
        layer.confirm('确认要启用吗？',function(index){
            //此处请求后台程序，下方是成功后的前台处理……
            $.ajax({
                type : "POST",
                data : { id : id, state : 0 },
                url : "<c:url value='/menu/updateMenuState'/>",
                dataType : 'json',
                success : function(msg) {
                    if (msg.code == "ok") {
                        // $(obj).parents("tr").remove();
                        table_page.reload();
                        layer.msg('已启用!', {icon: 6,time:1000});
                    } else {
                        layer.msg('启用失败，请再次尝试!',{icon:1,time:1000});
                    }
                }
            });
        });
    }

    var table_page;

    $(document).ready(function() {
        table_page = $("#example").page({
            url:"<c:url value='/action/pageAction'/>?sysType=${sysType}",
            dataRowCallBack : dataRowCallBack
        });
    });

    var index = 0;
    function dataRowCallBack(row){
        row.index = index ++;
        return row;
    }
</script>
</body>
</html>