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

<!--注册registered-->
<div class="register">
    <h2 class="f48 text_center">注册账号</h2>
    <div class="registMian">
        <ul class="registBg">
            <li class="registMr blueFont letter">验证邮箱</li>
            <li class="registMr letter">填写信息</li>
            <li class="lette">完成注册</li>
        </ul>
    </div>
    <div class="regist_tit">
        <form id="regForm" action="<c:url value='/reg/toSign'/>" method="post">
            <h3 class="text_center letter">玖云平台将发送一封验证邮件到您的邮箱</h3>
            <ul class="regist_iput">
                <li>
                    <label class="letter">登录账号:</label>
                    <input id="paw" name="email" maxlength="36" size="28" type="text" class="inputBk input_w400 va-m"  placeholder="请输入您的邮箱" >
                </li>
                <li class="errHeight errText">
                    <div>
                        <c:if test="${not empty email}">
                            <label class="error" style="float: none">${email}</label>
                        </c:if>
                    </div>
                </li>
                <li class="c9 regist_rigM">
                    <input type="checkbox" class="r_account" name="agreement" onchange="on_off(this);" value="Y"/>
                     认同并遵守<a href="javascript:void(0);"  class="blueFont" onclick="window.open('<c:url value='/statement'/>','_blank');">《玖云平台服务条款》</a>的内容
                </li>
                <li class="errHeight errText" id="forcheckbox">
                    <div>
                        <label class="error" style="float: none;display: none;">请同意并勾选玖云平台服务条款</label>
                    </div>
                </li>
                <li class="regist_rigM"><a href="javascript:submitForm();" class="blueBtn Btn_w400 send">发送</a> </li>
            </ul>
        </form>
    </div>
</div>
<!--注册 registered end-->

<!-- footer start -->
<jsp:include page="../../common/home2/footer.jsp"/>
<!-- footer end -->

<script src="${appConfig.resourcesRoot}/master/js/bootstrap.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/jquery.easing.1.3.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/login.js"></script>

<script src="${appConfig.resourcesRoot}/master/js/validation/jquery.validate.min.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/validation/messages_zh.min.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/validation/validate-extend.js"></script>

<script type="text/javascript">

    var regForm;

        regForm = $("#regForm").validate({
            rules: {
                email: {
                    required:true,
                    email:true,
                    remote: {
                        url: "<c:url value='/reg/checkemail'/>",     //后台处理程序
                        type: "post",               //数据发送方式
                        dataType: "json",           //接受数据格式
                        data: {                     //要传递的数据
                            email: function() {
                                return $("#paw").val();
                            }
                        }
                    }
                }
            },
            messages: {
                email: {
                    required:"请输入您的邮箱",
                    email:"邮箱格式不正确，请重新填写",
                    remote:"账号已存在，请直接登录"
                }
            },
            errorPlacement: function(error, element) {
                error.appendTo(element.parent().next().children());
            }
        });


    // 同意协议按钮开关
    function on_off(obj) {
        if(obj.checked) { // 同意协议
            $("#forcheckbox label").hide();
            return true;
        } else { // 没有同意协议
            $("#forcheckbox label").text("请同意并勾选玖云平台服务条款").show();
            return false;
        }
    }

    // 提交
    function submitForm() {
        if(regForm.form() && on_off($("input:checkbox")[0])){
            $("#regForm").submit();
        }
    }
</script>
</body>
</html>