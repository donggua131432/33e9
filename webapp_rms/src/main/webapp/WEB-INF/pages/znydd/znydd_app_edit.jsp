<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
    <title>添加用户</title>
</head>
<script type="text/javascript">
    $(function(){

        initSelect("answerTrunk", "<c:url value="/relay/getRelaysForAnswerTrunk"/>", "relayNum", "relayName", "请选择", "${appInfo.answerTrunk}");

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
                        layer_close();
                    }
                },2000);
            }
        });

        $("#companyName").select2({
            placeholder: "${appInfo.companyName}",
            ajax: {
                url: "<c:url value='/userAdmin/getCompanyNameAndSidByPage'/>",
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return {
                        params: {name:params.term,busType:'01'}, // search term
                        page: params.page
                    };
                },
                processResults: function (data, params) {
                    params.page = params.page || 1;
                    var items = [];
                    $.each(data.rows,function(i,v){
                        items.push({id:v.sid, text:v.name});
                    });
                    return {
                        results: items,
                        pagination: {
                            more: (params.page * 30) < data.total
                        }
                    };
                },
                cache: true
            }
        });

        $("#companyName").on("change",function(){
            $("#sid").val(this.value);
        });
    })
</script>
<body>
<div class="pd-20">
    <form action="<c:url value='/znydd/editApp'/>" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${appInfo.id}" name="id"/>
        <div class="row cl">
            <label class="form-label col-4">客户名称：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${appInfo.companyName}"/>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">账户 ID：</label>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${appInfo.sid}"/>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4">APP ID：</label>
            <%--<div class="formControls col-5">
                <input type="text" class="input-text" value="${appInfo.appid}" placeholder="请输入APP ID" id="appid" name="appid" datatype="*2-32" nullmsg="APP ID不能为空！" readonly/>
            </div>
            <div class="col-3"> </div>--%>
            <div class="formControls col-8">
                <span class="mt-3">
                    <c:out value="${appInfo.appid}"/>
                </span>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>应用名称：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="${appInfo.appName}" placeholder="请输入应用名称" id="appName" name="appName" datatype="*2-32" nullmsg="应用名称不能为空！"/>
            </div>
            <div class="col-3"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>应用号码：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="${appInfo.callNo}" placeholder="请输入应用号码,不能超过10位数字" id="callNo" name="callNo" datatype="*2-10" nullmsg="应用号码不能为空！"  errormsg="应用号码不合法！" />
            </div>
            <div class="col-3"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red"></span>呼叫中心代答中继：</label>
            <div class="formControls col-5">
                <select class="input-text" id="answerTrunk" name="answerTrunk"></select>
            </div>
            <div class="col-3"> </div>
        </div>

        <div class="row cl">
            <div class="col-4 col-offset-5">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset"  onclick="layer_close();;"  value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>