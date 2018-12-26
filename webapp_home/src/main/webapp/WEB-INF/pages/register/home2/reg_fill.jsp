<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta name="keywords" content="玖云平台是提供通话、短信、呼叫中心、流量、专线语音、通话录音、录音、回拨、隐私号等通讯能力与服务的开放平台" />
    <meta name="description" content="玖云平台为企业及个人开发者提供各种通讯能力，包括网络通话、呼叫中心/IVR等，开发者通过嵌入云通讯API能够在应用中轻松实现各种通讯功能，包括语音验证码、语音对讲、群组语聊、点击拨号、云呼叫中心等功能;网络通话,网络电话,云呼叫中心,短信接口,pass平台,流量充值,短信发送平台,通讯云,玖云平台,云通讯,云通信,SDK,API,融合通信,政企通讯,云呼叫中心,呼叫中心建设,呼叫中心能力,通讯线路,虚拟运营商,短信服务,流量分发平台,网络直拨,匿名通话,公费电话,企业服务 " />
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="renderer" content="webkit">
    <title>玖云平台-注册</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/home2/css/base.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/home2/css/default.css"/>
    <style type="text/css">
        .current{color:#2d8be3 !important;}
    </style>
    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../../common/baidu.jsp"/>
</head>
<body>
<jsp:include page="../../common/home2/header.jsp"/>

<!-- header end -->

<!--注册-填写信息registered-->
<div class="register">
    <h2 class="f48 text_center">注册账号</h2>
    <div class="registMian">
        <ul class="registBg Bg2">
            <li class="registMr blueFont letter">验证邮箱</li>
            <li class="registMr blueFont letter">填写信息</li>
            <li class="lette">完成注册</li>
        </ul>
    </div>
    <div class="regist_tit">
        <form id="regForm" action="<c:url value='/reg/addAccount'/>" method="post">

            <input id="pwd" name="pwd" type="hidden"/>
            <input id="email" name="email" type="hidden" value="${email}"/>
            <input id="vcode" name="vcode" type="hidden" value="${vcode}"/>
            <ul class="regist_iput inform">
                <li>
                    <label class="letter">已验证邮箱:</label>
                    <span>${email}</span>
                </li>
                <li>
                    <label class="letter">密码: </label>
                    <input type="password"  id="password" name="password" size="28" maxlength="16" placeholder="8-16位英文加数字，不可使用各类符号" class="inputBk input_w400 va-m"  >
                </li>
                <li class="errHeight errText">
                    <div>
                        <c:if test="${not empty msg}">
                            <label id="password-error" class="error" for="password">${msg}</label>
                        </c:if>
                    </div>
                </li>
                <li>
                    <label class="letter">确认密码: </label>
                    <input  type="password" id="repassword" name="repassword" size="28" maxlength="16" placeholder="请重复您的密码" class="inputBk input_w400 va-m"   >
                </li>
                <li class="errHeight errText">
                    <div>

                    </div>
                </li>
                <li>
                    <label class="letter">手机号: </label>
                    <input type="text" class="inputBk input_w400 va-m"  id="mobile" name="mobile" value="${mobile}" size="28" maxlength="11" placeholder="请输入您的手机号" >
                    <c:if test="${empty ttl}">
                        <input type="hidden" id="ttl" value="0"/>
                        <span class="codes regist_rigM"><a  href="javascript:sendSms('mobile',this);" id="ttlbuttonsend" class="blueBtn codes codesBtn text_center letter">发送验证码</a></span>
                        <span class="codes regist_rigM"><a href="javascript:;" class="grayBtn codes codesBtn text_center Ha_sent"  id="ttlbutton" style="display: none;">验证码已发送（120"）</a></span>
                    </c:if>
                    <c:if test="${not empty ttl}">
                        <input type="hidden" id="ttl" value=""${ttl}"/>
                        <span class="codes regist_rigM"><a  href="javascript:sendSms('mobile',this);" id="ttlbuttonsend" class="blueBtn codes codesBtn text_center letter" style="display: none;">发送验证码</a></span>
                        <span class="codes regist_rigM"><a href="javascript:;" class="grayBtn codes codesBtn text_center Ha_sent"  id="ttlbutton" style="display: none;">验证码已发送(${ttl}")</a></span>
                    </c:if>
                </li>
                <li class="errHeight errText">
                    <div>

                    </div>
                </li>
                <li>
                    <label class="letter">验证码:  </label>
                    <input name="code" id="test_code" maxlength="6" size="28" type="text" class="inputBk input_w400 va-m"  placeholder="请填写收到的验证码" >
                </li>
                <li class="errHeight errText">
                    <div>

                    </div>
                </li>
                <li class="regist_rigM"><a href="javascript:submitForm();" class="blueBtn Btn_w400 send">下一步</a> </li>
            </ul>
        </form>
    </div>
</div>
<!--注册-填写信息registered end-->

<!-- footer start -->
<jsp:include page="../../common/home2/footer.jsp"/>
<!-- footer end -->

<script src="${appConfig.resourcesRoot}/master/js/jquery.easing.1.3.js"></script>

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
                    remote:"验证码错误"
                }
            },
            errorPlacement: function(error, element) {
                error.appendTo(element.parent().next().children());
            }
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
                        $('#ttlbuttonsend').hide();
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