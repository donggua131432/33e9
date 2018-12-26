<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/12/27
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>修改任务</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function(){
            $("#form-admin-add").Validform({
                tiptype:2,
                ajaxPost:true,
                callback:function(data){
                    setTimeout(function(){
                        $.Hidemsg();
                        if(data.status == 0){
                            //刷新父级页面
                            parent.location.replace(parent.location.href);
                            layer_close();
                        }
                    },2000);
                },
                datatype: {
                    "checkURLs":function(gets, obj, curform, datatype){
                        var matchReg=/^(http):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i
                        if (matchReg.test(gets)) {
                            return;
                        }
                        else{
                            return "请输入正确的URL格式！";
                        }
                    }
                }
            });
        });

        //配置添加URL
        function addURLItem(obj){
            var itemCount = $("#editCallCenterDiv").find(".mb-15").length;
            if(itemCount <= 19){
                var itemHtml = '<div class="mb-15">' +
                        '<input type="hidden" value="-1" name="ids"/><input type="text" class="input-text"  style="width: 550px" value="" placeholder="请输入URL" id="url" name="urls" datatype="checkURLs" nullmsg="URL不能为空！" />'+
                        '&nbsp;<button type="button" onclick="delURLItem(this);" class="delBtn marLeft15">删除</button>' +
                        '&nbsp;<button type="button" onclick="addURLItem(this);" class="addBtn marLeft15">添加</button> </div>';
                $("#editCallCenterDiv").append(itemHtml);
                $(obj).remove();
            }else{
                $("#tipMsg").html('<span class="Validform_checktip Validform_wrong">只能配置20个URL</span>');
            }
        }

        //删除呼叫中心配置项
        function delURLItem(obj){
            var itemCount = $("#editCallCenterDiv").find(".mb-15").length;
            if(itemCount > 1){
                $(obj).parent().remove();
                $("#editCallCenterDiv").find("button[class='addBtn marLeft15']").remove();
                $("#editCallCenterDiv").find(".mb-15").last().append('<button type="button" onclick="addURLItem(this);" class="addBtn marLeft15">添加</button>');
            }else{
                $("#tipMsg").html('<span class="Validform_checktip Validform_wrong">不能删除，至少需要配置一个URL</span>');
            }
        }
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value='/openTask/updateTask'/>" method="post" class="form form-horizontal" id="form-admin-add">
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>任务名称：</label>
            <div class="formControls col-8">
                <input type="hidden" value="${taskHttp.taskId}" name="taskId"/>
                <input type="text" class="input-text"  style="width: 550px" value="${taskHttp.name}" placeholder="请输入任务名称" id="name" name="name" datatype="*" nullmsg="任务名称不能为空！" />
            </div>
            <div class="col-2"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-2">任务描述：</label>
            <div class="formControls col-8">
                <textarea style="width: 350px; height: 100px"   id="remark" name="remark" maxlength="100"><c:out value='${taskHttp.remark}'/></textarea>
            </div>
            <div class="col-2"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>URL：</label>
            <div class="formControls col-8 " id="editCallCenterDiv">
                <c:forEach items="${urlHttpList}" var="urlHttpList" varStatus="status">
                    <div class="mb-15">
                        <input type="hidden" value="${urlHttpList.id}" name="ids"/>
                        <input type="text" class="input-text" value="${urlHttpList.url}" placeholder="请输入URL" id="url" name="urls" style="width: 550px" datatype="checkURLs" nullmsg="URL不能为空！" />
                        <button type="button" onclick="delURLItem(this);" class="delBtn marLeft15">删除</button>
                        <c:if test="${status.last}">
                            <button type="button" onclick="addURLItem(this);" class="addBtn marLeft15">添加</button>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
            <div class="col-2"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"></label>
            <div class="formControls col-8" id="tipMsg"></div>
            <div class="col-2"> </div>
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
