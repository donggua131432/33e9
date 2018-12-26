<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
    <meta name="keywords" content="玖云平台是提供通话、短信、呼叫中心、流量、专线语音、通话录音、录音、回拨、隐私号等通讯能力与服务的开放平台" />
    <meta name="description" content="玖云平台为企业及个人开发者提供各种通讯能力，包括网络通话、呼叫中心/IVR等，开发者通过嵌入云通讯API能够在应用中轻松实现各种通讯功能，包括语音验证码、语音对讲、群组语聊、点击拨号、云呼叫中心等功能;网络通话,网络电话,云呼叫中心,短信接口,pass平台,流量充值,短信发送平台,通讯云,玖云平台,云通讯,云通信,SDK,API,融合通信,政企通讯,云呼叫中心,呼叫中心建设,呼叫中心能力,通讯线路,虚拟运营商,短信服务,流量分发平台,网络直拨,匿名通话,公费电话,企业服务 " />
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="renderer" content="webkit">
    <title>玖云平台-密码找回</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/home2/css/base.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/home2/css/default.css"/>
    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../../common/baidu.jsp"/>
</head>
<body>

<jsp:include page="../../common/home2/header.jsp"/>

<!--忘记密码registered-->
<div class="register">
    <h2 class="f48 text_center">密码找回</h2>
    <div class="registMian">
        <ul class="registBg passBg passBtu">
            <li class="passMr blueFont letter">填写邮箱</li>
            <li class="passMr letter">发送完成</li>
            <li class="passMr lette">重置密码</li>
            <li class="lette">完成重置</li>
        </ul>
    </div>
    <div class="regist_tit">
        <form action="<c:url value='/retrieve/sendEmails'/>" id="regForm" method="post">
            <ul class="regist_iput">
                <li>
                    <label class="letter">注册邮箱:</label>
                    <input id="email" name="email"  type="text" class="inputBk input_w400 va-m "  placeholder="请输入您的注册邮箱" >
                    <c:if test="${not empty email}">
                        <label class="error">${email}</label>
                    </c:if>
                </li>
                <li class="errHeight errText">
                    <div>
                        <c:if test="${not empty email}">
                            <label class="error" style="float: none">${email}</label>
                        </c:if>
                    </div>
                </li>
                <li>
                    <label class="letter">验证码:</label>
                    <input id="T_code" name="verifyCode"  type="text" class="inputBk input_w400 va-m input_w190"/>
                    <span class="T_tow"><img width="110" height="40" src="${appConfig.resourcesRoot}/kaptcha.jpg" onclick="changeVerifyCode()" id="yzmImg"/></span>
                    <a href="javascript:changeVerifyCode();" class="blueBtn RefreshBtn radius4">刷新</a>
                </li>
                <li class="errHeight errText">
                    <div>
                        <c:if test="${not empty email}">
                            <label class="error" style="float: none">${email}</label>
                        </c:if>
                    </div>
                </li>
                <li class="regist_rigM"><a href="javascript:sendEmailclick(this);" class="blueBtn Btn_w400 send">提交</a> </li>
            </ul>
        </form>
    </div>
</div>
<!--忘记密码 registered end-->

<jsp:include page="../../common/home2/footer.jsp"/>

<script src="${appConfig.resourcesRoot}/master/js/validation/jquery.validate.min.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/validation/messages_zh.min.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/validation/validate-extend.js"></script>
<%--
<script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/bootstrap.js"></script>--%>
<script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/stepBar.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/login.js"></script>
<script type="text/javascript">
    var regForm;
    $(function(){

        stepBar.init("stepBar",{
            step : 1,
            change : true,
            animation : true
        });

        regForm = $("#regForm").validate({
            rules: {
                email: {
                    required:true,
                    email:true,
                    remote: {
                        url: "<c:url value='/retrieve/validate'/>",     //后台处理程序
                        type: "post",               //数据发送方式
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            email: function() {
                                return $("#email").val();
                            }
                        }
                    }
                },
                verifyCode : {
                    required:true,
                    remote: {
                        url: "<c:url value='/retrieve/checkVcode'/>",     //后台处理程序
                        type: "post",               //数据发送方式
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            vcode: function() {
                                return $("#T_code").val();
                            }
                        }
                    }
                }

            },
            messages: {
                email: {
                    required:"请输入您的注册邮箱",
                    email:"邮箱格式不正确，请重新输入",
                    remote:"邮箱未被注册"
                },
                verifyCode : {
                    required:"请输入您的验证码",
                    remote:"验证码错误，请刷新验证码"
                }
            },
            errorPlacement: function(error, element) {
                error.appendTo(element.parent().next().children());
            }
        });

    });

    function changeVerifyCode() {
        //点击切换验证码
        $("#yzmImg").attr("src", "${appConfig.resourcesRoot}/kaptcha.jpg?" + Math.floor(Math.random() * 100));
    }

    //提交
    function sendEmailclick(obj) {
       if(regForm.form()){
//           obj.disabled = true;

           $("#regForm").submit();
       }
    }

</script>
<%--<script type="text/javascript">
    if( !('placeholder' in document.createElement('input')) ){

        $('input[placeholder],textarea[placeholder]').each(function(){
            var that = $(this),
                    text= that.attr('placeholder');
            if(that.val()===""){
                that.val(text).addClass('placeholder');
            }
            that.focus(function(){
                        if(that.val()===text){
                            that.val("").removeClass('placeholder');
                        }
                    })
                    .blur(function(){
                        if(that.val()===""){
                            that.val(text).addClass('placeholder');
                        }
                    })
                    .closest('form').submit(function(){
                if(that.val() === text){
                    that.val('');
                }
            });
        });
    }
</script> --%>
</body>
</html>
