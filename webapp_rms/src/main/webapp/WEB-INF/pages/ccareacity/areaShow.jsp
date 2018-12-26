
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>客户添加</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/zTree/v3.5/css/demo.css"/>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/zTree/v3.5/css/zTreeStyle/zTreeStyle.css"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/plugins/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript">

        // 用户区域
        var asetting = {
            view: {
                selectedMulti: false
            },
            async: {
                enable: true,
                url:"<c:url value="/ccareacity/atree"/>?areaId=${area.areaId}"
            },
            data:{
                simpleData:{
                    enable: true,
                    pIdKey: "pId"
                }
            }
        };
        var atree;
        $(function() {
            $.fn.zTree.init($("#atree"), asetting);
            atree = $.fn.zTree.getZTreeObj("atree");
        });
    </script>
</head>
<body>
<div class="pd-20">
    <form action="" method="post" class="form form-horizontal" id="addCustomerManager">
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>客户名称：</label>
            <div class="formControls col-8">
                ${userAdmin.authenCompany.name}
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2">account id：</label>
            <div class="formControls col-8">
                ${area.sid}
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>区域名称：</label>
            <div class="formControls col-8">
                ${area.aname}
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row">
            <div class="col-3">

            </div>
            <div class="col-5">
                <div style="height: 400px">
                    <ul id="atree" class="ztree" style="width:300px; overflow:auto;"></ul>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
