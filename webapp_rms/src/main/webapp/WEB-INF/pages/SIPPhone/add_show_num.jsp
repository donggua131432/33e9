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
        $(function(){
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
                    "sipphone": function (gets,obj,curform,regxp) {
                        $("#sipphoneId").val('');
                        var reg = /^[0-9]{5,30}$/;
                        if (!reg.test(gets)) {
                            return "格式不正确";
                        }
                        var r = false;
                        var cityid = curform.find("#ccode").val();
                        $.ajax({
                            type:"post",
                            url:'<c:url value="/spApply/checkSipphone"/>',
                            dataType:"json",
                            async:false,
                            data:{'sipphone' : gets, 'cityid' : cityid},
                            success : function(result) {
                                if (result.code == 'ok') {
                                    $("#sipphoneId").val(result.data);
                                    r = true;
                                } else {
                                    r = result.msg;
                                }
                            }
                        });
                        return r;
                    },
                    "fixphone": function (gets,obj,curform,regxp) {
                        $("#fixphoneId").val('');
                        var reg = /^[0-9]{5,30}$/;
                        if (!reg.test(gets)) {
                            return "格式不正确";
                        }
                        var r = false;
                        var cityid = curform.find("#ccode").val();
                        $.ajax({
                            type:"post",
                            url:'<c:url value="/spApply/checkFixphone"/>',
                            dataType:"json",
                            async:false,
                            data:{'fixphone' : gets, 'cityid' : cityid, 'appid' : '${appid}'},
                            success : function(result) {
                                if (result.code == 'ok') {
                                    $("#fixphoneId").val(result.data);
                                    r = true;
                                } else {
                                    r = result.msg;
                                }
                            }
                        });
                        return r;
                    },
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
                            data:{'showNum' : gets, 'appid' : '${appid}'},
                            success : function(result) {
                                if (result.code == 'ok') {
                                    r = true;
                                } else {
                                    r = result.msg;
                                }
                            }
                        });*/
                        return true;
                    }
                }
            });
        });
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value='/spApply/addShowNum'/>" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${appid}" name="appid" id="appid"/>
        <input type="hidden" name="sipphoneId" id="sipphoneId"/>
        <input type="hidden" name="fixphoneId" id="fixphoneId"/>
        <div class="form form-horizontal" >
            <div class="row cl">
                <label class="form-label col-2">省份：</label>
                <div class="formControls col-7">
                    <select class="input-text" id="pcode" name="pcode" datatype="*" nullmsg="请选择省份">
                    </select>
                </div>
                <div class="col-3"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-2">城市：</label>
                <div class="formControls col-7">
                    <select class="input-text" id="ccode" name="cityid" datatype="*" nullmsg="请选择城市">
                    </select>
                </div>
                <div class="col-3"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-2">SIP号码：</label>
                <div class="formControls col-7">
                    <input type="text" class="input-text" name="sipphone" maxlength="30" datatype="sipphone" nullmsg="请输入SIP号码" errormsg="格式不正确"/>
                </div>
                <div class="col-3"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-2">固话号码：</label>
                <div class="formControls col-7">
                    <input type="text" class="input-text" name="fixphone" maxlength="30" datatype="fixphone" nullmsg="请输入固话号码" errormsg="格式不正确"/>
                </div>
                <div class="col-3"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-2">外显号码：</label>
                <div class="formControls col-7">
                    <input type="text" class="input-text" name="showNum" maxlength="30" datatype="showNum" errormsg="格式不正确"/>
                </div>
                <div class="col-3"></div>
            </div>

        </div>

        <div class="row cl">
            <div class="col-1"></div>
            <div class="col-7 col-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="button" onclick="layer_close()" value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
            <div class="col-2"></div>
        </div>
    </form>

</div>
</body>
</html>