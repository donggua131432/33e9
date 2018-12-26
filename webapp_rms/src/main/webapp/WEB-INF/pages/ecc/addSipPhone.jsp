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

            initPCArea("pcode", "ccode", "请选择");

            $("#addSipPhoneForm").Validform({
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
                    "ipPort":/^((1?\d{1,2}|2[1-5][1-5])\.){3}(1?\d{1,2}|2[1-5][1-5]):([1-5]?\d{1,4}|6[1-5][1-5][1-3][1-5])$/,
                    "sipphone":function(gets, obj, curform, datatype){
                        var reg = /^\d{5,30}$/;
                        if (!reg.test(gets)) {
                            return "格式不正确";
                        }

                        var code = $("#ccode option:selected").attr("data-code");
                        if (code && gets.indexOf(code) != 0) {
                            return "区号不正确";
                        }

                        $.ajax({
                            type:"post",
                            url:'<c:url value="/sipNumResPool/checkEccSipPhone"/>',
                            dataType:"json",
                            async:false,
                            data:{'sipphone' : gets},
                            success : function(result) {
                                if(result.code == 'NotRegister'){
                                    disabledRegisterInfo();
                                    r = true;
                                }
                                else if (result.code == 'ok') {
                                    showRegisterInfo();
                                    r = true;
                                } else {
                                    showRegisterInfo();
                                    r = result.msg;
                                }
                            }
                        });
                        return r;
                    }
                }
            });

        });

        function showRegisterInfo(){
            $('.Validform_wrong').show();
            $('#pwd').attr("disabled",false);
            $('#sipRealm').attr("disabled",false);
            $('#ipPort').attr("disabled",false);
            $('#pwd').attr('placeholder','请输入认证密码');
            $('#sipRealm').attr('placeholder','请输入SIP REALM');
            $('#ipPort').attr('placeholder','请输入IP:PORT');
            $("#addSipPhoneForm").Validform().unignore("#pwd");
            $("#addSipPhoneForm").Validform().unignore("#sipRealm");
            $("#addSipPhoneForm").Validform().unignore("#ipPort");
        }

        function disabledRegisterInfo(){
            $('#pwd').attr("disabled",true);
            $('#sipRealm').attr("disabled",true);
            $('#ipPort').attr("disabled",true);
            $('#pwd').attr('placeholder','无需注册');
            $('#pwd').attr('class','input-text');
            $('#sipRealm').attr('placeholder','无需注册');
            $('#sipRealm').attr('class','input-text');
            $('#ipPort').attr('placeholder','无需注册');
            $('#ipPort').attr('class','input-text');
            $("#addSipPhoneForm").Validform().ignore("#pwd");
            $("#addSipPhoneForm").Validform().ignore("#sipRealm");
            $("#addSipPhoneForm").Validform().ignore("#ipPort");
        }
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/sipNumResPool/addSipPhone"/>" method="post" class="form form-horizontal" id="addSipPhoneForm">

        <div class="row cl">
            <label class="form-label col-3">省份：</label>
            <div class="formControls col-5">
                <select id="pcode" name="pcode" class="input-text cl-1" style="height: 31px" datatype="*" nullmsg="请选择省份">
                </select>
            </div>
            <div class="col-4"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">城市：</label>
            <div class="formControls col-5">
                <select id="ccode" name="cityid" class="input-text cl-1" style="height: 31px" datatype="*" nullmsg="请选择省份">
                </select>
            </div>
            <div class="col-4"></div>
        </div>

      <%--  <div class="row cl">
        <label class="form-label col-3">状态：：</label>
            <div class="formControls col-5">
                <select class="input-text cl-1 status"  name="status" datatype="*"  style="width: 200px;">
                    <option   value="">请选择状态</option>
                    <option   value="01">已分配</option>
                    <option   value="02">已锁定</option>
                    <option   value="03">待分配</option>
                </select>
            </div>
            <div class="col-4"></div>
        </div>
--%>
        <div class="row cl">
            <label class="form-label col-3">号码：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" placeholder="请输入号码" id="sipPhone" name="sipPhone" datatype="sipphone" maxlength="30" errormsg="格式不正确" nullmsg="号码不能为空"/>
            </div>
            <div class="col-4"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">认证密码：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" placeholder="请输入认证密码" id="pwd" name="pwd" datatype="/^\S{1,20}$/" maxlength="20" errormsg="格式不正确" nullmsg="认证密码不能为空"/>
            </div>
            <div class="col-4"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">SIP REALM：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" placeholder="请输入SIP REALM" id="sipRealm" name="sipRealm" datatype="/^\S{1,20}$/" maxlength="20" errormsg="格式不正确" nullmsg="SIP REALM不能为空"/>
            </div>
            <div class="col-4"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">IP:PORT：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" placeholder="请输入IP:PORT" id="ipPort" name="ipPort" datatype="ipPort" maxlength="30" errormsg="格式不正确" nullmsg="IP:PORT不能为空"/>
            </div>
            <div class="col-4"></div>
        </div>

        <div class="row cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-primary radius" style="width: 100px;" type="submit" value="确定">
                <input class="btn btn-warning radius" style="width: 100px;" onclick="layer_close();" type="button" value="取消">
            </div>
        </div>
    </form>
</div>
</body>
</html>
