<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>玖云平台-密码找回</title>
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

<jsp:include page="../common/header.jsp"/>

<div class="website_container">
    <div class="main_box">
        <div class="login_common_row1">
            <div class="row1">
                <div class="fw">密码找回</div>
                <div id="stepBar" class="ui-stepBar-wrap col-md-10">
                    <div class="ui-stepBar">
                        <div class="ui-stepProcess" ></div>
                    </div>
                    <div class="ui-stepInfo-wrap">
                        <table class="ui-stepLayout" border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <td class="ui-stepInfo">
                                    <a class="ui-stepSequence">1</a>
                                    <p class="ui-stepName">填写邮箱</p>
                                </td>
                                <td class="ui-stepInfo">
                                    <a class="ui-stepSequence">2</a>
                                    <p class="ui-stepName">发送完成</p>
                                </td>
                                <td class="ui-stepInfo">
                                    <a class="ui-stepSequence">3</a>
                                    <p class="ui-stepName">密码重置</p>
                                </td>
                                <td class="ui-stepInfo">
                                    <a class="ui-stepSequence">4</a>
                                    <p class="ui-stepName">完成重置</p>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div class="login1_row2">
            <form action="<c:url value='/retrieve/sendEmails'/>" id="regForm" method="post">
                <div class="Registered_mail">
                    <label for="email" class="login_1_left">请输入您的注册邮箱</label>
                    <input type="text" id="email" name="email" placeholder="请输入您的注册邮箱"/>
                    <span class="tip_text_box" >
                        <c:if test="${not empty email}">
                            <label class="error">${email}</label>
                        </c:if>
                    </span>
                </div>
                <div class="Test_code">
                    <label for="T_code">验证码</label>
                    <input type="text" id="T_code" class="T_one" name="verifyCode"/>
                    <span class="T_tow"><img width="80" height="30" src="${appConfig.resourcesRoot}/kaptcha.jpg" onclick="changeVerifyCode()" id="yzmImg" style="cursor: pointer;"/></span>
                    <button class="upDate" type="button" onclick="changeVerifyCode();">刷&nbsp;新</button>
                    <span class="tip_text_box">
                        <c:if test="${not empty yzm}">
                            <label id="T_code-error" class="error" for="T_code">验证码错误，请刷新验证码</label>
                        </c:if>
                    </span>
                </div>
                <div class="btn_center">
                    <button type="button" class="login_btn login1_btn" onclick="sendEmailclick(this)">提&nbsp;&nbsp;交</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

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
                    email:"账号异常，请重新输入",
                    remote:"邮箱未被注册"
                },
                verifyCode : {
                    required:"请输入您的验证码",
                    remote:"验证码错误，请刷新验证码"
                }
            },
            errorPlacement: function(error, element) {
                error.appendTo(element.nextAll(".tip_text_box"));
            },
            errorClass : 'error',
            success : 'valid'
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
