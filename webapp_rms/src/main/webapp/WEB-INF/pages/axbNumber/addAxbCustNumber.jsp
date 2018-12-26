<%--
  Created by IntelliJ IDEA.
  User: dukai
  Date: 2016/6/1
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>添加用户号</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
        $(function(){
            $("#addMaskNumberForm").Validform({
                tiptype: 2,
                ajaxPost: true,
                callback:function(result){
                    setTimeout(function(){
                        $.Hidemsg();
                        if(result.status == 0){
                            //刷新父级页面
                            parent.search_param = {
                                params:{
                                    "appid":"${appid}"
                                }
                            };
                            parent.dataTables.fnDraw();
                            layer_close();
                        }
                    },2000);
                },
                datatype: {
                    "number":function(gets, obj, curform, datatype){
                        var reg = /^\d{5,30}$/;
                        if (!reg.test(gets)) {
                            return "格式不正确";
                        }
                    }
                }
            })
        })
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/axbCustNumResPool/addAxbCustNumber" />" method="post" class="form form-horizontal" id="addMaskNumberForm">
        <input type="hidden" name="appid" value="${appid}"/>
        <input type="hidden" name="sid" value="${sid}"/>
        <input type="hidden" name="uid" value="${uid}"/>
        <div class="row cl">
            <label class="form-label col-3">区号：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="请输入区号3-8位数字" id="areacode" name="areacode" maxlength="8" datatype="n3-8&_ajax" data-ajax="{'url':'<c:url value="/cityMgr/getCityNameByAreaCode"/>',msg:'区号不存在！'}" nullmsg="区号不能为空"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">号码：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" placeholder="请输入号码" id="number" name="number" datatype="number" maxlength="30" errormsg="格式不正确" nullmsg="号码不能为空"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-primary radius" style="width: 100px;" type="submit" value="提交">
            </div>
        </div>
    </form>
</div>
</body>
</html>
