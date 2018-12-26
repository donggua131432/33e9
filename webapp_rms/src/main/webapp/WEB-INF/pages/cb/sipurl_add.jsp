<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>添加</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
    <script type="text/javascript">
        $(function() {

            $("#addCityManager").Validform({
                tiptype:2,
                ajaxPost:true,
                callback:function(data){
                    $.Showmsg(data.msg);
                    setTimeout(function(){
                        $.Hidemsg();
                        if(data.code == 'ok'){
                            //刷新父级页面
                            parent.location.replace(parent.location.href);
                            layer_close();
                        }
                    },2000);
                },
                datatype: {
                    "zh1-10":/^[\u4E00-\u9FA5\uf900-\ufa2d]{1,12}$/,
                    "checkNum":function(gets, obj, curform, datatype){

                        var checkStatus = true;

                        if (!gets) {
                            return "请输入号码";
                        }

                        if (!/^[0-9]{5,30}$/.test(gets)) {
                            return "格式错误";
                        }

                        $.ajax({
                            type:"post",
                            url:"<c:url value='/sipurl/checkNum'/>",
                            dataType:"json",
                            async:false,
                            data:{'num':gets, id: '${sipurl.id}'},
                            success : function(data) {
                                if(data.code != 'ok'){
                                    checkStatus = data.msg;
                                }
                            }
                        });

                        return checkStatus;
                    }
                }
            });

        });
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value='/sipurl/addOrUpdateSipUrl'/>" method="post" class="form form-horizontal" id="addCityManager">
        <input type="hidden" name="id" value="${sipurl.id}"/>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>SipUrl：</label>
            <div class="formControls col-6">
                <input type="text" class="input-text" value="${sipurl.sipurl}" placeholder="请输入SipUrl" id="sipurl" name="sipurl" datatype="/^[\u4E00-\u9FA5-0-9a-zA-Z\(\)\.\-\_\[\]\~\!\*\')]+$/" maxlength="100" nullmsg="SipUrl不能为空" errormsg="格式错误！"/>
            </div>
            <div class="col-4"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>号码：</label>
            <div class="formControls col-6">
                <input type="text" class="input-text" value="${sipurl.num}" placeholder="请输入号码" id="num" name="num" maxlength="30" datatype="checkNum" nullmsg="号码不能为空" errormsg="格式错误"/>
            </div>
            <div class="col-4"></div>
        </div>

        <div class="row cl">
            <div class="col-offset-4">
                <input class="btn btn-primary radius" style="width: 100px;" type="submit" value="提交">
            </div>
        </div>
    </form>
</div>
</body>
</html>
