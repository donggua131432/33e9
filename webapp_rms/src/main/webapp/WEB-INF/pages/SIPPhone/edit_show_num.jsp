<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>编辑SIP应用</title>
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
                },
                datatype:{
                    "showNum" : function(gets,obj,curform,regxp) {

                        if (!gets) { // 可以为空
                            return true;
                        }

                        var reg = /^[0-9]{5,30}$/;
                        if (!reg.test(gets)) {
                            return "格式不正确";
                        }
                        /*var r = false;
                        $.ajax({
                            type:"post",
                            url:'<c:url value="/spApply/checkShowNum"/>',
                            dataType:"json",
                            async:false,
                            data:{'showNum' : gets, 'appid' : '${applyNum.appid}'},
                            success : function(result) {
                                if (result.code == 'ok') {
                                    r = true;
                                } else {
                                    r = result.msg;
                                }
                            }
                        });
                        return r;*/
                        return true;
                    }
                }
            });
        });
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/spApply/updateShowNum"/>" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${applyNum.id}" name="id"/>
        <div class="form form-horizontal">
            <div class="row cl">
                <label class="form-label col-4">SIP号码：</label>
                <div class="formControls col-5">
                    <c:out value="${applyNum.sipphone}"/>
                </div>
                <div class="col-3"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-4">固话号码：</label>
                <div class="formControls col-5">
                    <c:out value="${applyNum.fixphone}"/>
                    <input type="hidden" name="fixphone" class="input-text" value="${applyNum.fixphone}"/>
                </div>
                <div class="col-3"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-4">外显号码：</label>
                <div class="formControls col-5">
                    <input type="text" name="showNum" class="input-text" value="${applyNum.showNum}" datatype="showNum"/>
                </div>
                <div class="col-3"></div>
            </div>

        </div>

        <div class="row cl">
            <div class="col-1"></div>
            <div class="col-7 col-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="button" onclick="layer_close();" value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
            <div class="col-2"></div>
        </div>
    </form>

</div>
</body>
</html>
