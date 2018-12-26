<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>菜单管理</title>
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
                <th>名称</th>
                <th>级别</th>
                <th>路径</th>
                <th>图标</th>
                <th>排序</th>
                <th>状态</th>
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
        <td>{{html indent}}\${name}</td>
        <td class="text-c">
            {{if level == 1}}
            <span class="label label-warning radius">\${levelStr}</span>
            {{/if}}
            {{if level == 2}}
            <span class="label label-primary radius">\${levelStr}</span>
            {{/if}}
            {{if level == 3}}
            <span class="label label-secondary radius">\${levelStr}</span>
            {{/if}}
        </td>
        <td>\${url}</td>
        <td class="text-c"><i class="Hui-iconfont">{{html icon}}</i></td>
        <td class="text-c">
            {{if level == 1}}
            <span class="label label-warning radius">\${orderBy}</span>
            {{/if}}
            {{if level == 2}}
            <span class="label label-primary radius">\${orderBy}</span>
            {{/if}}
            {{if level == 3}}
            <span class="label label-secondary radius">\${orderBy}</span>
            {{/if}}
        </td>
        <td class="text-c">
            {{if state == 0}}
            <span class="label label-success radius">\${stateStr}</span>
            {{else}}
            <span class="label label-defaunt radius">\${stateStr}</span>
            {{/if}}
        </td>
        <td class="td-manage text-c">
            {{if state == 0}}
            <a style="text-decoration:none" onClick="admin_stop(this,'\${id}')" href="javascript:;" title="停用"><i class="Hui-iconfont">&#xe631;</i></a>
            {{else}}
            <a style="text-decoration:none" onClick="admin_start(this,'\${id}')" href="javascript:;" title="启用"><i class="Hui-iconfont">&#xe615;</i></a>
            {{/if}}
            <a title="编辑" href="javascript:;" onclick="admin_edit('管理员编辑','<c:url value="/menu/toEditMenu"/>','\${id}','800','500')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
            <a title="删除" href="javascript:;" onclick="admin_del(this,'\${id}')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a>
        </td>
    </tr>
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
    /*管理员-编辑*/
    function admin_edit(title,url,id,w,h){
        url = url + "?id=" + id + "&sysType=${sysType}";
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
            url:"<c:url value='/menu/page'/>?sysType=${sysType}",
            dataRowCallBack : dataRowCallBack
        });
    });

    var indent = '<span style="width:20px;display:inline-block;"></span><img src="${appConfig.resourcesRoot}/images/joinmiddle.gif" style="vertical-align: middle;"><span style="width:5px;display:inline-block;"></span>';

    var indent_20 = '<span style="width:20px;display:inline-block;"></span><img src="${appConfig.resourcesRoot}/images/join.gif" style="vertical-align: middle;"><span style="width:5px;display:inline-block;"></span>';
    var indent_21 = '<span style="width:20px;display:inline-block;"></span><img src="${appConfig.resourcesRoot}/images/joinbottom.gif" style="vertical-align: middle;"><span style="width:5px;display:inline-block;"></span>';
    var indent_30 = indent + '<span style="width:20px;display:inline-block;"></span><img src="${appConfig.resourcesRoot}/images/join.gif" style="vertical-align: middle;"><span style="width:5px;display:inline-block;"></span>';
    var indent_31 = indent + '<span style="width:20px;display:inline-block;"></span><img src="${appConfig.resourcesRoot}/images/joinbottom.gif" style="vertical-align: middle;"><span style="width:5px;display:inline-block;"></span>';
    var indent_32 = '<span style="width:60px;display:inline-block;"></span><img src="${appConfig.resourcesRoot}/images/join.gif" style="vertical-align: middle;"><span style="width:5px;display:inline-block;"></span>';
    var indent_33 = '<span style="width:60px;display:inline-block;"></span><img src="${appConfig.resourcesRoot}/images/joinbottom.gif" style="vertical-align: middle;"><span style="width:5px;display:inline-block;"></span>';

    var index = 0;
    function dataRowCallBack(row){
        row.index = index ++;
        if (row.level == 1) {
            row.levelStr = "一级菜单";
            row.indent = "";
        } else if (row.level == 2) {
            row.levelStr = "二级菜单";
            if (row.last) {
                row.indent = indent_21;
            } else {
                row.indent = indent_20;
            }

        } else if (row.level == 3) {
            row.levelStr = "三级菜单";
            if (row.parentLast) {
                if (row.last) {
                    row.indent = indent_33;
                } else {
                    row.indent = indent_32;
                }
            } else {
                if (row.last) {
                    row.indent = indent_31;
                } else {
                    row.indent = indent_30;
                }
            }

        }

        if (row.state == 0) {
            row.stateStr = "可用";
        } else if (row.state == 1) {
            row.stateStr = "禁用";
        }

        return row;
    }
</script>
</body>
</html>