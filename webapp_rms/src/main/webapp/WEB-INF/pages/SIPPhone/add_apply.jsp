<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>编辑SIP应用</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function() {
            initPCArea("pcode", "ccode", "请选择");

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
                    "amount": function (gets,obj,curform,regxp) {
                        var reg = /^[1-9]\d{0,4}$/;
                        if (!reg.test(gets)) {
                            return "请输入小于99999的整数";
                        }
                        return true;
                    },
                    "rate" : function(gets,obj,curform,regxp) {
                        var reg = /^[1-9]\d{0,4}$/;
                        if (!reg.test(gets)) {
                            return "请输入小于99999的整数";
                        }
                        var amount = curform.find("#amount").val();
                        if (amount) {amount = parseInt(amount)}
                        if (parseInt(gets) > amount) {
                            return "比率不应大于SIP号码数";
                        }
                        return true;
                    }
                }
            });

        });
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value='/spApply/addApply'/>" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${appid}" name="appid"/>
        <div class="form form-horizontal">

            <div class="row cl">
                <label class="form-label col-4">省份：</label>
                <div class="formControls col-4">
                    <select class="input-text" id="pcode" name="pcode" style="width: 180px;" datatype="*" nullmsg="请选择省份">
                    </select>
                </div>
                <div class="col-4"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-4">城市：</label>
                <div class="formControls col-4">
                    <select class="input-text" id="ccode" name="cityid" style="width: 180px;" datatype="*" nullmsg="请选择城市">
                    </select>
                </div>
                <div class="col-4"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-4">SIP号码数量：</label>
                <div class="formControls col-4">
                    <input type="text" name="amount" id="amount" class="input-text" style="width: 180px;" maxlength="5" datatype="amount"  errormsg="格式不正确" nullmsg="请输入SIP号码数量"/>
                </div>
                <div class="col-4"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-4">SIP号码:固话号码：</label>
                <div class="formControls col-4">
                    <input type="text" name="rate" id="rate" class="input-text" style="width: 145px;" maxlength="5" datatype="rate"  errormsg="格式不正确" nullmsg="请输入比率"/> &nbsp;:&nbsp; 1
                </div>
                <div class="col-4"></div>
            </div>
        </div>

        <div class="row cl">
            <div class="col-1"></div>
            <div class="col-7 col-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="button" value="&nbsp;&nbsp;取消&nbsp;&nbsp;" onclick="layer_close();"/>
            </div>
            <div class="col-2"></div>
        </div>
    </form>

</div>
</body>
</html>
