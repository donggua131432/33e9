
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>角色权限分配</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/zTree/v3.5/css/demo.css"/>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/zTree/v3.5/css/zTreeStyle/zTreeStyle.css"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/plugins/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>

    <script type="text/javascript">

        // 权限树
        var setting = {
            view: {
                selectedMulti: false
            },
            check: {
                enable: true
            },
            async: {
                enable: true,
                url:'<c:url value="/action/atree"/>?roleId=${role.id}'
            },
            data:{
                simpleData:{
                    enable: true,
                    pIdKey: "pId"
                }
            }
        };

        var tree;
        $(function() {

            $.fn.zTree.init($("#tree"), setting);
            tree = $.fn.zTree.getZTreeObj("tree");

        });

        // 提交表单
        function submitForm() {
            var nodes = tree.getNodesByFilter(function(node){
                return !node.isParent && node.checked;
            });

            var aid = "";
            $.each(nodes, function(i,n){
                aid += "&aid=" + n.custom
            });

            $.ajax({
                type: "POST",
                url: "<c:url value="/roleMgr/addAction"/>",
                data: "roleId=${role.id}" + aid,
                success: function(msg){
                    if(msg.code == 'ok'){
                        //刷新父级页面
                        parent.location.replace(parent.location.href);
                        layer_close();
                    } else if(msg.code == 'error') {
                        layer.alert("修改失败", {icon:1});
                    }
                }
            });
        }
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/roleMgr/addAction"/>" method="post" class="form form-horizontal" id="addCustomerManager">
        <input id="roleId" name="roleId" type="hidden" value="${role.id}"/>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>角色名称：</label>
            <div class="formControls col-8">
                <input class="input-text" readonly value="${role.name}"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row">
            <div class="col-2"></div>
            <div class="col-8">
                <div style="height: 400px;">
                    <ul id="tree" class="ztree" style="width:485px; overflow:auto;"></ul>
                </div>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <div class="col-offset-4">
                <input onclick="submitForm();" class="btn btn-primary radius" style="width: 100px;" type="button" value="提交">
            </div>
        </div>
    </form>
</div>
</body>
</html>
