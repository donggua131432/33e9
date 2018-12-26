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
                    "phone": function (gets,obj,curform,regxp) {
                        $("#sipphoneId").val('');
                        $("#fixphoneId").val('');
                        var reg = /^[0-9]{5,30}$/;

                        var numType = $("#numType").val();
                        if (!numType) {
                            return "请选择接听号码类型";
                        }

                        if (!reg.test(gets)) {
                            return "格式不正确";
                        }
                        var r = false;
                        var cityid = '${eccInfo.cityid}';
                        $.ajax({
                            type:"post",
                            url:'<c:url value="/eccAppInfo/checkPhone"/>',
                            dataType:"json",
                            async:false,
                            data:{'phone' : gets, numType : numType, 'eccid' : '${eccInfo.id}', appid : '${eccInfo.appid}'},
                            success : function(result) {
                                if (result.code == 'ok') {
                                    if (numType == '01') {
                                        $("#sipphoneId").val(result.msg);
                                    } else {
                                        $("#fixphoneId").val(result.msg);
                                    }
                                    r = true;
                                } else {
                                    r = result.msg;
                                }
                            }
                        });
                        return r;
                    },
                    "subNum": function (gets,obj,curform,regxp) {

                        if (!gets) { // 可以为空
                            return true;
                        }

                        var reg = /^[0-9]{1,8}$/;
                        if (!reg.test(gets)) {
                            return "格式不正确";
                        }
                        var r = false;

                        $.ajax({
                            type:"post",
                            url:'<c:url value="/eccAppInfo/checkSubNum"/>',
                            dataType:"json",
                            async:false,
                            data:{'subNum' : gets, 'eccid' : '${eccInfo.id}'},
                            success : function(result) {
                                if (result.code == 'ok') {
                                    r = true;
                                } else {
                                    r = result.msg;
                                }
                            }
                        });
                        return r;
                    },
                    "showNum" : function(gets,obj,curform,regxp) {
                        $("#shownumId").val("");
                        if (!gets) { // 可以为空
                            return true;
                        }

                        var reg = /^[0-9]{5,30}$/;
                        if (!reg.test(gets)) {
                            return "格式不正确";
                        }
                        var r = false;
                        var cityid = '${eccInfo.cityid}';
                        $.ajax({
                            type:"post",
                            url:'<c:url value="/eccAppInfo/checkShowNum"/>',
                            dataType:"json",
                            async:false,
                            data:{'showNum' : gets, 'eccid' : '${eccInfo.id}', 'appid' : '${eccInfo.appid}'},
                            success : function(result) {
                                if (result.code == 'ok') {
                                    r = true;
                                    $("#shownumId").val(result.msg);
                                } else {
                                    r = result.msg;
                                }
                            }
                        });
                        return r;
                    }
                }
            });

            $("#numType").on("change", function(){
                if (this.value == '01') {
                    $("#showNum").attr("disabled", false).removeAttr("ignore");
                } else {
                    $("#showNum").attr("disabled", true).attr("ignore", "ignore").val("");
                }
            });
        });
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value='/eccAppInfo/addSubNum'/>" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${eccInfo.appid}" name="appid" id="appid"/>
        <input type="hidden" value="${eccInfo.id}" name="eccid" id="eccid"/>
        <input type="hidden" value="${eccInfo.cityid}" name="cityid" id="cityid"/>
        <input type="hidden" name="sipphoneId" id="sipphoneId"/>
        <input type="hidden" name="fixphoneId" id="fixphoneId"/>
        <input type="hidden" name="shownumId" id="shownumId"/>
        <div class="form form-horizontal" >
            <div class="row cl">
                <label class="form-label col-3">接听号码类型：</label>
                <div class="formControls col-6">
                    <select class="input-text" id="numType" name="numType" datatype="*" nullmsg="请选择接听号码类型">
                        <option value="">请选择</option>
                        <option value="01">SIP号码</option>
                        <option value="02">手机</option>
                        <option value="03">固话</option>
                    </select>
                </div>
                <div class="col-3"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-3">接听号码：</label>
                <div class="formControls col-6">
                    <input type="text" class="input-text" name="phone" maxlength="30" datatype="phone" nullmsg="请输入接听号码" errormsg="格式不正确"/>
                </div>
                <div class="col-3"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-3">外显号码：</label>
                <div class="formControls col-6">
                    <input type="text" class="input-text" id="showNum" name="showNum" maxlength="30" datatype="showNum" placeholder="不填写默认为总机号码" errormsg="格式不正确"/>
                </div>
                <div class="col-3"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-3">分机号码：</label>
                <div class="formControls col-6">
                    <input type="text" class="input-text" name="subNum" maxlength="8" datatype="subNum" nullmsg="请输入分机号码" errormsg="格式不正确"/>
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