<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!--<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">-->
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
        <div class="login2_row2">
            <div class="register2_one">
                <p>您的邮箱<span>${email}</span>验证邮件已发送</p>
                <p>请登录您的邮箱,点击邮件内注册链接完成后续步骤</p>
            </div>
            <div class="login2_two">
                <button type="button" class="login_btn register2_btn" onclick="toemail();">登&nbsp;录&nbsp;邮&nbsp;箱</button>
            </div>
        </div>
    </div>
</div>

<!-- footer start -->
<jsp:include page="../common/footer.jsp"/>
<!-- footer end -->
<script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/stepBar.js"></script>
<script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/login.js"></script>
<script type="text/javascript">
    $(function(){
        stepBar.init("stepBar",{
            step : 1,
            change : true,
            animation : true
        });
    });

    function toemail(){
        window.open('${emailurl}', '_blank');
    }
</script>
</body>
</html>