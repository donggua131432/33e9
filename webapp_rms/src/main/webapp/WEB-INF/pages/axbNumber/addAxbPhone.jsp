<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>添加号码</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function(){

            $("#addAxbPhoneForm").Validform({
                tiptype: 2,
                ajaxPost: true,
                callback:function(result){
                    setTimeout(function(){
                        $.Hidemsg();
                        if(result.status == 0){
                            //刷新父级页面
                            parent.location.replace(parent.location.href);
//                            parent.dataTables.fnDraw();
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

                        $.ajax({
                            type:"post",
                            url:'<c:url value="/axbNumResPool/checkAxbNumber"/>',
                            dataType:"json",
                            async:false,
                            data:{'number' : gets,'areacode' : $("#areacode").val()},
                            success : function(result) {
                                if (result.code == 'ok') {
                                    r = true;
                                } else {
                                    r = result.msg;
                                }
                            }
                        });
                        return r;
                    }
                }
            });

        });
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/axbNumResPool/addAxbPhone"/>" method="post" class="form form-horizontal" id="addAxbPhoneForm">
        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>区号：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="请输入区号3-8位数字" id="areacode" name="areacode" maxlength="8" datatype="n3-8&_ajax" data-ajax="{'url':'<c:url value="/cityMgr/getCityNameByAreaCode"/>',msg:'区号不存在！'}" nullmsg="区号不能为空"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>号码：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" placeholder="请输入号码" id="number" name="number" datatype="number" maxlength="30" errormsg="格式不正确" nullmsg="号码不能为空"/>
            </div>
            <div class="col-4"></div>
        </div>

        <div class="row cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-primary radius" style="width: 100px;" type="submit" value="提交">
                <input class="btn btn-warning radius" style="width: 100px;" onclick="layer_close();" type="button" value="取消">
            </div>
        </div>
    </form>
</div>
</body>
</html>
