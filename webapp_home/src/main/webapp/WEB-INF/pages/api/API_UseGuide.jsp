<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>开发者账户使用指南</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/API.css"/>
    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>

<jsp:include page="../common/header.jsp"/>

<div class="API_container">
    <div class="main_box">
        <div class="main_box">
            <div class="API_box">
                <div class="API_main1 f_left">
                    <div class="api_left api_left_color">
                        <b class="api_txt"></b>
                        <span class="l_api">所有文档</span>
                    </div>

                    <jsp:include page="../common/api_nav.jsp"/>

                </div>
                <div class="API_main2 f_left" style="clear: both">
                    <div style="position:fixed;width:810px;top:85px;height:36px;background:#ECECEC;"></div>
                    <div id="api_ma2_1">
                        <div class="api_m2"><span>开发者账户使用指南</span></div>
                        <div>
                            <div class="api_m2_box API1">
                                <span class="api_com_font">1.注册开发者账户</span>
                                <p class="APImdFont">1.1.注册</p>
                                <p>
                                    浏览玖云平台官网首页&nbsp;<a href="http://www.33e9cloud.com" target="_blank" class="AVisited">http://www.33e9cloud.com</a>，点击“注册”按钮进行注册
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/startPage.png" alt="授权书模板图片"/></p>
                                <p class="APImdFont">1.2.填写验证邮箱</p>
                                <p>
                                    邮箱必须真实有效，可以正常收发邮件，建议使用常用邮箱，玖云平台将会通过此邮箱通知开发者相关信息。同时，此邮箱将作为您的登陆用户名。<br/>
                                    点击“发送”，玖云平台将发送一封验证激活邮件到您的邮箱。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/registerOne.png" alt="注册页面图片"/></p>
                                <p class="APImdFont">1.3.登录邮箱验证</p>
                                <p>
                                    点击“登录邮箱”，跳转到注册邮箱登录界面，收取邮件进行验证激活。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/registerTwo.png" alt="注册页面图片"/></p>

                                <p class="APImdFont">1.4.填写详细注册信息</p>
                                <p>
                                    邮箱验证成功后，需进行相关信息填写完成注册。<br/>
                                    注册成为开发者前需进行手机号码验证，点击“发送验证码”后，玖云平台将向您的手机发送一条短信验证码。<br/>
                                    验证成功后，玖云平台将通过此号码与您保持沟通。<br/>
                                    信息填写无误后，点击“下一步”，完成注册。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/registerThree.png" alt="注册页面图片"/></p>
                                <p class="APImdFont">1.5.完成注册</p>
                                <p>
                                    完成注册后，页面跳转至登录页面，请输入用户密码进行首次登录。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/registerFour.png" alt="注册页面图片"/></p>
                            </div>
                            <div class="api_m2_box API2" id="zzrz">
                                <span class="api_com_font">2.资质认证</span>
                                <p>
                                    开发者注册完成后可登陆“用户控制中心”查看基本信息。使用平台功能，则必须先进行实名认证，目前玖云平台只提供企业认证。
                                </p>
                                <p class="APImdFont">2.1.资质认证入口</p>
                                <p>
                                    若用户未完成认证，则可以通过以下两个入口进入资质认证界面。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/accountNoTest.png" alt="账户主页图片"/></p>
                                <p>
                                    (1).账户主页的快捷入口<br/>
                                    (2).账户管理中，基本资料目录下的认证信息标签<br/>
                                    若已经完成认证，则只能通过账户管理中，基本资料目录下的认证信息标签来点击查看
                                </p>
                                <p class="APImdFont">2.2.认证信息</p>
                                <p>
                                    开发者必须提供真实有效的公司信息，填写完成后，会进入审核流程。平台审核通过后才可以正式使用。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/testMsg.png" alt="认证消息图片"/></p>
                                <p>
                                    (1).公司名称：需要填入公司真实名称，与营业执照上的名字一致<br/>
                                    (2).公司地址：需要填入公司真实地址，与营业执照上的资质一致<br/>
                                    (3).所属行业：默认为空，建议开发者根据公司实际情况选择，以便我们为您提供更好的服务<br/>
                                    (4).证件类型<br/>
                                    &nbsp;&nbsp; &nbsp;&nbsp;a.三证合一（一照一码）：需要填入真实统一社会信用代码，并上传证件图片，图片要求真实公司营业执照扫描件，或复印件加盖公司公章扫描件，文字清晰可见<br/>
                                    &nbsp;&nbsp; &nbsp;&nbsp;<img src="${appConfig.resourcesRoot}/master/img/IdTypeOne.png" class="imgTop" alt="图片"/>
                                </p>
                                <p>
                                    &nbsp;&nbsp; &nbsp;&nbsp;b.三证合一：需要填入真实注册号、税务登记号，并上传营业执照图片，图片要求真实公司营业执照扫描件，或复印件加盖公司公章扫描件，文字清晰可见<br/>
                                    &nbsp;&nbsp; &nbsp;&nbsp;<img src="${appConfig.resourcesRoot}/master/img/IdTypeTwo.png" class="imgTop" alt="图片"/>
                                </p>
                                <p>
                                    &nbsp;&nbsp; &nbsp;&nbsp;c.三证分离：需要填入真实税务登记号、营业执照号，并分别上传两证图片，图片要求真实公司营业执照及税务登记证扫描件，或复印件加盖公司公章扫描件，文字清晰可见<br/>
                                    &nbsp;&nbsp; &nbsp;&nbsp;<img src="${appConfig.resourcesRoot}/master/img/IdTypeThree.png" class="imgTop" alt="图片"/>
                                </p>
                                <p>
                                    (5).法定代表人：需要填入法定代表人真实名称，与营业执照上的法人一致<br/>
                                    (6).公司电话：需要填入公司真实电话号码，<span class="redFont">选填</span><br/>
                                    (7).公司网址：需要填入公司真实网址，<span class="redFont">选填</span>
                                </p>
                                <p class="APImdFont">2.3.审核状态</p>
                                <p>
                                    玖云平台会对开发者认证信息进行审核，并及时返回审核状态。<br/>
                                    <a href="<c:url value="/api/PlatformAudit"/>" class="AVisited">查看平台审核标准</a>
                                </p>
                                <p>
                                    (1).待审核<br/>
                                    开发者提交资质审核后，认证信息界面进入待审核状态，该状态开发者不能进行信息修改。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/MsgChecking.png" alt="信息待审核图片"/></p>
                                <p>
                                    (2).认证通过<br/>
                                    认证通过后，开发者可以正式开始使用玖云平台提供的功能。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/MsgPass.png" alt="信息审核通过图片"/></p>
                                <p>
                                    (3).审核不通过<br/>
                                    如用户提供的资料不清晰，不全面或不真实等，平台将会对信息审核不通过。开发者可通过备注信息查看审核不通过的原因。审核不通过时，用户可以点击“重新认证”对原有信息进行修改，并重新提交认证。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/MsgNoPass.png" alt="信息审核不通过图片"/></p>
                            </div>
                            <div class="api_m2_box API3">
                                <span class="api_com_font">3.账户管理</span>
                                <p>
                                    登录成功进入“用户控制中心”。第一板块为账户管理，下面介绍这一部分的详细内容。
                                </p>
                                <p class="APImdFont">3.1.账户主页</p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/accountTested.png" alt="账户主页图片"/></p>
                                <p>
                                    (1).消费概览<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;a.账户余额：当前账号的余额<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;b.昨日消费：昨日消费总额<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;c.本月消费：截至昨天，本月消费总额<br/>
                                    (2).Account Sid<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;a.开发者在玖云平台的唯一标示，调用平台接口产品时使用<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;b.开发者账号ID在网站注册后，系统自动生成，不可以更改<br/>
                                    (3).Auth Token<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;a.开发者账号ID（Account Sid）的密码，使用平台接口产品进行鉴权时使用<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;b.token在网站注册后，系统自动生成，可根据需求进行重置<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;c.token为隐藏显示状态需要手机验证后可查看，点击查看显示如图：<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;<img src="${appConfig.resourcesRoot}/master/img/testNumber.png" alt="图片"/><br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;d.验证手机后token为显示状态，并可进行重置；重置为不可逆操作，重置后原有token失效，需谨慎使用<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;<img src="${appConfig.resourcesRoot}/master/img/token.png" alt="图片"/><br/>
                                    (4).REST URL<br/>
                                    调用API接口的请求地址，当前版统一URL为&nbsp;<a href="https://api.33e9cloud.com" target="_blank" class="AVisited">https://api.33e9cloud.com</a>
                                </p>
                                <p class="APImdFont">3.2.基本资料</p>
                                <p>
                                    该页显示开发者基本信息。点击“修改信息”，可重新编辑信息。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/baseMsg.png" alt="基础资料图片"/></p>
                                <p>
                                    (1).更换邮箱<br/>
                                    邮箱为开发者登录的用户名，邮箱更换后，用户名随之变更。更换的新邮箱需要重新进行验证激活，验证成功后才可以使用。若新邮箱验证失败，或未验证，则继续使用原有邮箱进行账户操作。<br/>
                                    <img src="${appConfig.resourcesRoot}/master/img/replaceEmail.png" alt="更换邮箱图片"/><br/>
                                    (2).更换手机号<br/>
                                    玖云平台开发者账户绑定的手机号码可以进行变更。更换的新手机号码要重新进行验证，验证成功后才可以使用。若新手机号码验证失败，或未验证，则继续使用原有手机号码。<br/>
                                    <img src="${appConfig.resourcesRoot}/master/img/replaceNumber.png" alt="更换手机号图片"/><br/>
                                    (3).其他基础信息<br/>
                                    其他信息包含QQ号码及联系地址，为可选项，建议开发者填写。<br/>
                                    (4).其他联系人<br/>
                                    其他联系人手机号码为备用联系号码，如遇紧急情况无法联系到账户绑定号码，我们会与您设定的应急联系人联系；建议开发者填写。
                                </p>
                                <p class="APImdFont">3.3.密码设置</p>
                                <p>
                                    该页可进行开发者账户密码修改。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/makePassword.png" alt="设置密码图片"/></p>
                                <p class="APImdFont">3.4.充值记录</p>
                                <p>
                                    该页主要用于查询近12个月的所有充值记录，具有记录导出功能。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/rechargeRecord.png" alt="充值记录图片"/></p>
                                <p>导出充值记录为Excel文档，具体内容字段如下图：</p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/rechargeList.png" alt="导出充值记录单图片"/></p>
                                <p class="APImdFont">3.5.资费列表</p>
                                <p>
                                    该页用于显示产品的标准价格，以及客户折扣率。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/chargesList.png" alt="资费列表图片"/></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="API_main3 f_left">
                    <div class="stb"> <span class="click_tb spread_tb"></span></div>
                    <div class="api_right_box THeight">
                        <div class="api_right_center">
                            <p class="b1"><b class="b"></b>1&nbsp;注册开发者账户</p>
                            <p class="b2"><b class="b"></b>2&nbsp;资质认证</p>
                            <p class="b3"><b class="b"></b>3&nbsp;账户管理</p>
                        </div>
                    </div>
                    <div class="xtb"><span class="click_tb close_tb"></span></div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

<script src="${appConfig.resourcesRoot}/master/js/api.js"></script>

</body>
</html>