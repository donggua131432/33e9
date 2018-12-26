<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>玖云平台-注册</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/login.css">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/control.css"/>
    <!--[if lt IE 9]>
    <script src="${appConfig.resourcesRoot}/master/js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>

<!-- header start -->
<jsp:include page="../common/header.jsp"/>
<!-- header end -->

<div class="website_container">
    <div class="main_box">
        <div class="login_common_row1">
            <div class="row1">
                <div class="rg">注册玖云平台</div>
                <div id="stepBar" class="ui-stepBar-wrap col-md-10">
                    <div class="ui-stepBar">
                        <div class="ui-stepProcess" ></div>
                    </div>
                    <div class="ui-stepInfo-wrap">
                        <table class="ui-stepLayout" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="ui-stepInfo">
                                    <a class="ui-stepSequence">1</a>
                                    <p class="ui-stepName">验证邮箱</p>
                                </td>
                                <td class="ui-stepInfo">
                                    <a class="ui-stepSequence">2</a>
                                    <p class="ui-stepName">填写信息</p>
                                </td>
                                <td class="ui-stepInfo">
                                    <a class="ui-stepSequence">3</a>
                                    <p class="ui-stepName">完成注册</p>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="register3_row2">
            <div class="register2_one">
                <p>已验证邮箱<span>${email}</span></p>
            </div>
            <div class="register3_zc">
                <form id="regForm" action="<c:url value='/reg/addAccount'/>" method="post">

                    <input id="pwd" name="pwd" type="hidden"/>
                    <input id="email" name="email" type="hidden" value="${email}"/>
                    <input id="vcode" name="vcode" type="hidden" value="${vcode}"/>

                    <div class="top mar_left">
                        <label for="password" class="code_left">密码</label>
                        <input type="password" id="password" name="password" size="28" maxlength="16" placeholder="8-16位英文加数字，不可使用各类符号"/>
                        <span class="tip_text_box">
                            <c:if test="${not empty msg}">
                                <label id="password-error" class="error" for="password">${msg}</label>
                            </c:if>
                        </span>
                    </div>
                    <div class="top mar_left">
                        <label for="repassword" class="R_paw">确认密码</label>
                        <input type="password" id="repassword" name="repassword" size="28" maxlength="16" placeholder="请重复您的密码"/>
                        <span class="tip_text_box">

                        </span>
                    </div>
                    <div class="top mar_left">
                        <label for="mobile" class="phone_left">手机号</label>
                        <input type="text" id="mobile" name="mobile" value="${mobile}" size="28" maxlength="11" placeholder="请输入您的手机号"/>
                        <span class="tip_text_box">
                             <label class="error  label3_left" style="display: none;">密码不一致</label>
                        </span>
                    </div>

                    <div class="top">
                        <c:if test="${empty ttl}">
                            <input type="hidden" id="ttl" value="0"/>
                            <button type="button" class="send_test_code" id="ttlbuttonsend" onclick="sendSms('mobile',this);">发&nbsp;送&nbsp;验&nbsp;证&nbsp;码</button>
                            <button type="button" class="sending_test_code" id="ttlbutton" style="display: none;">验证码已发送(120")</button>
                        </c:if>
                        <c:if test="${not empty ttl}">
                            <input type="hidden" id="ttl" value="${ttl}"/>
                            <button type="button" class="send_test_code" id="ttlbuttonsend" onclick="sendSms('mobile',this);" style="display: none;">发&nbsp;送&nbsp;验&nbsp;证&nbsp;码</button>
                            <button type="button" class="sending_test_code" id="ttlbutton">验证码已发送(${ttl}")</button>
                        </c:if>
                    </div>

                    <div class="top mar_left">
                        <label for="test_code" class="phone_left">验证码</label>
                        <input type="text" name="code" id="test_code" maxlength="6" size="28" placeholder="请填写您收到的验证码"/>
                        <span class="tip_text_box">
                            <c:if test="${not empty vcodeMsg}">
                                <label id="test_code-error" class="error" for="test_code">${vcodeMsg}</label>
                            </c:if>
                        </span>
                    </div>

                    <div class="top mar-left rg_left">
                        <span>感兴趣的产品</span>
                        <input name="dicId" type="checkbox" class="r_account" value="d7d1744be69f48d09760b2eae6af0999"/><b>专&nbsp;线&nbsp;语&nbsp;音</b>
                        <input name="dicId" type="checkbox" class="r_account" value="d7d1744be69f48d09760b2eae6af0888"/><b>智&nbsp;能&nbsp;云&nbsp;调&nbsp;度</b>
                    </div>
                    <button id="regformbtn" onclick="submitForm();" type="button" class="login_btn register3_btn">下&nbsp;一&nbsp;步</button>
                </form>
            </div>

        </div>
    </div>
</div>

<!-- footer start -->
<jsp:include page="../common/footer.jsp"/>
<!-- footer end -->

<script src="${appConfig.resourcesRoot}/master/js/jquery.easing.1.3.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/stepBar.js"></script>

<script src="${appConfig.resourcesRoot}/master/js/encrypt/RSA.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/encrypt/BigInt.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/encrypt/Barrett.js"></script>

<script src="${appConfig.resourcesRoot}/master/js/validation/jquery.validate.min.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/validation/messages_zh.min.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/validation/validate-extend.js"></script>

<script type="text/javascript">

    var regForm;
    var codeWait = 120;

    $(function(){
        var ttl = $("#ttl").val();
        if (ttl > 0) {
            codeWait = ttl;
            get_code_time(document.getElementById("ttlbutton"), document.getElementById("ttlbuttonsend"));
        }

        stepBar.init("stepBar",{
            step : 2,
            change : true,
            animation : true
        });

        regForm = $("#regForm").validate({
            rules: {
                password: {
                    required:true,
                    password:true
                },
                repassword: {
                    required:true,
                    password:false,
                    equalTo: "#password"
                },
                mobile:{
                    required:true,
                    mobile:true,
                    remote: {
                        url: "<c:url value='/reg/checkmoblie'/>",     //后台处理程序
                        type: "post",               //数据发送方式
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            mobile: function() {
                                return $("#mobile").val();
                            }
                        }
                    }
                },
                code:{
                    required:true,
                    //vcode:true
                    remote: {
                        url: "<c:url value='/reg/checkvcode'/>",     //后台处理程序
                        type: "post",               //数据发送方式
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            vcode: function() {
                                return $("#test_code").val();
                            },
                            mobile: function() {
                                return $("#mobile").val();
                            }
                        }
                    }
                }
            },
            messages: {
                password: {
                    required:"请输入密码",
                    password:"格式不符合"
                },
                repassword: {
                    required:"请重复您的密码",
                    equalTo:"密码不同，请检查您的密码"
                },
                mobile: {
                    required:"请输入手机号",
                    mobile:"手机号码无效",
                    remote:"手机号码已经被绑定"
                },
                code:{
                    required:"请输入验证码",
                    remote:"错误"
                }
            },
            errorPlacement: function(error, element) {
                error.appendTo(element.next());
            },
            errorClass : 'error',
            success : 'valid'
        });
    });

    // 发送
    function sendSms($id,objsend) {
        var mobile = $("#" + $id).val();
        var obj = document.getElementById("ttlbutton");

        if (regForm.element("#mobile")) {
            $.ajax({
                type : "POST",
                data :{ mobile : mobile },
                url : "<c:url value='/reg/sendSms'/>",
                dataType : 'json',
                success : function(msg) {
                    if (msg.code == "ok") {
                        // alert("发送成功！");
                        get_code_time(obj,objsend);
                    }

                    if (msg.code == "ttl") {
                        codeWait = msg.msg;
                        get_code_time(obj,objsend);
                    }
                }
            });
        }

    }

    // 提交表单
    function submitForm() {
        if(regForm.form()){

            bodyRSA();
            var password = encryptedString(key, $("#password").val());
            //console.info(password);
            //alert(password);
            $("#pwd").val(password);

            $("#regformbtn").attr("disabled", "disabled");
            $("#regForm").submit();
        }
    }

    function get_code_time (obj,objsend) {
        if (codeWait <= 0) {
            $(objsend).show();
            $(obj).hide().html("验证码已发送(120)" );
            codeWait = 120;
        } else {
            $(obj).show().html("验证码已发送(" + codeWait + "\")" );
            $(objsend).hide();

            codeWait--;
            setTimeout(function() {
                get_code_time(obj,objsend);
            }, 1000);
        }
    }

    //rsa begin
    var key ;

    function bodyRSA()	{
        setMaxDigits(130);
        key = new RSAKeyPair("${appConfig.rsaExponent}", "", "${appConfig.rsaModulus}");
    }
    //rsa end

</script>
</body>
</html>