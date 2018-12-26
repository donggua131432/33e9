<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!--<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">-->
    <title>玖云平台-密码找回</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/login.css">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/control.css"/>
    <!--[if lt IE 9]>
    <script src="${appConfig.resourcesRoot}/master/js/html5shiv.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
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
        <div class="login3_row2">
            <div class="login3_one register2_one">
                <p>您的邮箱<span>${user.email}</span></p>
            </div>
            <div class="login3_two">
                <form action="<c:url value='/retrieve/updatepwd'/>" method="post" id="regForm" name="regForm">
                    <div class="login3_paw">
                        <label for="paw" class="login3_label">密码</label>
                        <input type="password" id="paw" name="password" placeholder="8-16位英文加数字,不可使用各类符号"/>
                        <span class="tip_text_box">
                        </span>
                    </div>
                    <div class="login_r_paw">
                        <label for="r_paw" class="login3_label">确认密码</label>
                        <input type="password" id="r_paw" name="passwords" placeholder="请重复您的密码"/>
                        <span class="tip_text_box">
                        </span>
                    </div>
                    <div class="btn_center">
                        <button type="button" class="login_btn login3_btn" onclick="sumc(this)">提&nbsp;&nbsp;交</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

<script src="${appConfig.resourcesRoot}/master/js/validation/jquery.validate.min.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/validation/validate-extend.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/validation/messages_zh.min.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/jquery.easing.1.3.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/stepBar.js"></script>
<script src="${appConfig.resourcesRoot}/master/js/login.js"></script>
<script type="text/javascript">
    var regForm;
    $(function () {
        stepBar.init("stepBar", {
            step: 3,
            change: true,
            animation: true
        });
    });
    regForm = $("#regForm").validate({
        rules: {
            password: {
                required: true,
                password: true
            },
            passwords: {
                required: true,
                password: true,
                equalTo: "#paw"
            }
        },
        messages: {
            password: {
                required: "请输入您的密码",
                password: "格式不符合"
            },
            passwords: {
                required: "请重复您的密码",
                equalTo: "密码不同，请检查您的密码"
            }
        },
        errorPlacement: function(error, element) {
            error.appendTo(element.next());
        },
        errorClass : 'error',
        success : 'valid'

    });

    function sumc(obj) {
        if(regForm.form())
        {
            obj.disabled=true;
            $("#regForm").submit();
        }
    }

</script>
</body>
</html>