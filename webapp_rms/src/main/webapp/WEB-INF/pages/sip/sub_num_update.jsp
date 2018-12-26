<%--
   Created by IntelliJ IDEA.
  User: lixin
  Date: 2016/7/15
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>修改号码信息</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function(){

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
//                            layer_close();
                        }
                    },2000);
                }
            });


        });



    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value='/subNumPool/updateMax'/>" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${sipRelayNumPool.id}" name="id"/>
        <div class="form form-horizontal" >
            <div class="row cl">
                <label class="form-label col-4">最大并发数：</label>
                <div class="formControls col-4">
                    <input type="text" class="input-text" value="${sipRelayNumPool.maxConcurrent}" placeholder="请输入最大并发数" id="maxConcurrent" name="maxCon" datatype="_ajax" data-ajax="{_regex:{type:'maxConcurrent',msg:'格式不正确'}}"  maxlength="5"  />
                </div>
                <div class="col-4"> </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">号码：</label>
                <div class="formControls col-4">
                    <span>${sipRelayNumPool.number}</span>
                </div>
                <div class="col-4"> </div>
            </div>
        </div>
        <div class="row cl">
            <div class="col-1"> </div>
            <div class="col-7 col-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset"    value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
            <div class="col-2"> </div>
        </div>
    </form>

</div>
</body>
</html>
