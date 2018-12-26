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
    <title>新增号段</title>
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

        /*$("#pcode").select2({
            placeholder: "请选择省",
            ajax: {
               /!* type:"post",*!/
                url: "<c:url value='/numberpart/queryProvinceListByPname'/>",
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return {
                        params: {pname:params.term}, // search term
                        page: params.page
                    };
                },
                processResults: function (data, params) {
                    params.page = params.page || 1;
                    var items = [];
                    $.each(data,function(i,v){
                        items.push({id:v.pcode, text:v.pname});
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

        $("#ccode").select2({
            placeholder: "请选择市",
            ajax: {
                type:"post",
                url: "<c:url value='/numberpart/queryCityList'/>",
                dataType: 'json',
                delay: 250,
                data:{"pcode":function(){
                    return $("#pcode").val();
                }},
                processResults: function (data) {
                    var items = [];
                    $.each(data,function(i,v){
                        items.push({id:v.ccode, text:v.cname});
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
        });

        $("#operator").select2({
            placeholder: "请选择运营商",
            ajax: {
                type:"post",
                url: "<c:url value='/numberpart/queryOperatorList'/>",
                dataType: 'json',
                delay: 250,
                processResults: function (data) {
                    var items = [];
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
        });

        $("#pcode").on("change",function(){
            //清空市的所有option
            $("#ccode").empty();
            $("#ccode").select2({
                placeholder: "请选择市",
                ajax: {
                    type:"post",
                    url: "<c:url value='/numberpart/queryCityList'/>",
                    dataType: 'json',
                    delay: 250,
                    data:{"pcode":function(){
                        return $("#pcode").val();
                    }},
                    processResults: function (data) {
                        var items = [];
                        $.each(data,function(i,v){
                            items.push({id:v.ccode, text:v.cname});
                        });

                        return {
                            results: items
                        };
                    },
                    cache: true
                }
            });
            $("#pname").val(function(){
                return $("#pcode option:selected").text();
            });


        });

        $("#ccode").on("change",function(){
            $("#cname").val(function(){
                return $("#ccode option:selected").text();
            });
        });*/

        initPCArea("pcode", "ccode", "请选择", "", "", true);

        initSelect("operator", "<c:url value="/numberpart/queryOperatorList"/>", 'ocode', 'oname', '请选择运营商', '');

        $("#pcode").on("change",function(){
            $("#pname").val(function(){
                return $("#pcode option:selected").text();
            });
        });

        $("#ccode").on("change",function(){
            $("#cname").val(function(){
                return $("#ccode option:selected").text();
            });
        });

    });



</script>
<body>
<div class="pd-20">
    <form action="<c:url value='/numberpart/add'/>" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="" name="pname" id="pname"/>
        <input type="hidden" value="" name="cname" id="cname"/>
       <%-- <input type="hidden" name="areacode" id="areacode"/>--%>
        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>号段：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="请输入号段" id="telno" name="telno" datatype="*2-12&_ajax" data-ajax="{'url':'<c:url value="/numberpart/checkTelnoUnique"/>',msg:'该号段已经存在'}" nullmsg="号段不能为空！"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>归属地：</label>
            <div class="formControls col-5">
                <select id="pcode" name="pcode" class="input-text" style="width: 106px" nullmsg="归属地-省不能为空！" datatype="*"></select>
                省
                <select id="ccode" name="ccode" class="input-text" style="width: 106px" nullmsg="归属地-市不能为空！" datatype="*"></select>
                市
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>运营商：</label>
            <div class="formControls col-5">
                <select id="operator" name="operator" class="input-text" style="width: 306px" nullmsg="运营商不能为空！" datatype="*"></select>
                <%--<select id="operator" name="operator" style="width: 200px;height: 31px;">--%>
                    <%--<option value="">请选择运营商</option>--%>
                    <%--<option value="00">中国移动</option>--%>
                    <%--<option value="01">中国联通</option>--%>
                    <%--<option value="02">中国电信</option>--%>
                    <%--<option value="03">广电</option>--%>
                <%--</select>--%>
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