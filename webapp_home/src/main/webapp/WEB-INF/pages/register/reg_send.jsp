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
    <script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/html5shiv.min.js"></script>
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
        <div class="register1_row2">
            <div class="register_send register2_one">
                <p>玖云平台&nbsp;将发送一封验证邮件到您的邮箱</p>
            </div>
            <div class="register_login">
                <form id="regForm" action="<c:url value='/reg/toSign'/>" method="post">
                    <div class="RE_one">
                        <label for="paw">登录账号</label>
                        <input type="text" id="paw" name="email" maxlength="36" size="28" value="" placeholder="请输入您的邮箱"/>
                        <span class="tip_text_box">
                            <c:if test="${not empty error}">
                                <label id="paw-error" class="error" for="paw">${error}</label>
                            </c:if>
                        </span>
                    </div>
                    <div class="RE_two">
                        <input type="checkbox" class="r_account" name="agreement" onchange="on_off(this);" value="Y"/>
                        <span class="terms">认同并遵守<a href="javascript:void(0);" onclick="window.open('<c:url value='/statement'/>','_blank');">《玖云平台服务条款》</a>的内容</span>
                    </div>
                    <span id="forcheckbox" class="tip_text_box">
                        <label class="error" style="float: none;display: none;">请同意并勾选玖云平台服务条款</label>
                    </span>
                    <div class="btn_center">
                        <button type="button" onclick="submitForm();" class="login_btn register1_btn" id="submitform" style="cursor: pointer;">发&nbsp;&nbsp;送</button>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>

<!-- footer start -->
<jsp:include page="../common/footer.jsp"/>
<!-- footer end -->

<script src="${appConfig.resourcesRoot}/master/js/bootstrap.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/jquery.easing.1.3.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/stepBar.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/login.js"></script>

<script src="${appConfig.resourcesRoot}/master/js/validation/jquery.validate.min.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/validation/messages_zh.min.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/validation/validate-extend.js"></script>

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
                    email:"邮箱异常，请重新填写",
                    remote:"账号已存在，请直接登录"
                }
            },
            errorPlacement: function(error, element) {
                error.appendTo(element.next());
            },
            errorClass : 'error',
            success : 'valid'
        });

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