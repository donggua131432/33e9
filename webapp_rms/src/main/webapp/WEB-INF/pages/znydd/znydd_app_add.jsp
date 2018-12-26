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
            placeholder: "请选择客户名称",
            ajax: {
                url: "<c:url value='/userAdmin/getCompanyNameAndSidByPage'/>",
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return {
                        params: {name:params.term,busType:'01'}, // search term busType ：1 rest用户 2 智能云调度用户
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
            //escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
            //minimumInputLength: 1,
            //templateResult: formatRepo, // omitted for brevity, see the source of this page
            //templateSelection: formatRepoSelection // omitted for brevity, see the source of this page
            //data:data
        });

        $("#companyName").on("change",function(){
            $("#sid").val(this.value);
        });
    });



</script>
<body>
<div class="pd-20">
    <form action="<c:url value='/znydd/addApp'/>" method="post" class="form form-horizontal" id="form-member-add">

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>客户名称：</label>
            <div class="formControls col-5">
                <select id="companyName" style="width: 306px" nullmsg="客户名称不能为空！" datatype="*"></select>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>Account ID：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="请输入账户ID" id="sid" name="sid" datatype="*2-32" nullmsg="账户ID不能为空！" readonly/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>应用名称：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="请输入应用名称" id="appName" name="appName" datatype="*2-32" nullmsg="应用名称不能为空！"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset"  onclick="layer_close();;"  value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>