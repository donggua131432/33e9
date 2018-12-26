<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>API文档</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/API.css"/>
    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>

<jsp:include page="../common/header.jsp"/>

<div class="API_container">
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
                    <div class="api_m2"><span>平台审核规范</span></div>
                    <div>
                        <div class="api_m2_box API1">
                            <p>为了更好地为客户提供通讯服务，保障开发者的合法权益，玖云平台将对开发者使用过程中的一些信息进行审核，审核通过后才可以正式投入使用，为此造成的不便敬请谅解。下面详细介绍平台的审核规范。</p>
                            <span class="api_com_font">1.审核时间</span>
                            <p>
                                1. 工作日(周一至周五)：<br/>
                                客服人员将在工作日9:00—17:30定时审核，并在17:30前反馈审核结果，17:30后提交的资料将会在第二个工作日审核。 具体的审核结果，通过用户控制中心及时反馈。<br/>
                                2. 非工作日：<br/>
                                非工作不提供审核，所有非工作日提交的资料，将推迟到工作日进行审核。
                            </p>
                        </div>
                        <div class="api_m2_box API2" id="zzshbz">
                            <span class="api_com_font">2.资质审核标准</span>
                            <p>
                                资质认证主要证明开发者的开发身份，保证开发者在玖云平台的合法权益，开发者上传的资料必须真实有效，且提供的资料足以判断身份。<br/>
                                资质认证目前只提供企业认证：<br/>
                                企业认证需上传企业的有效证件，玖云平台支持三种不同类型的证件认证方式方法，分别为三证合一（一整依照）、三证合一、三证分离。<br/>
                                (1).证件必须为贵公司所有，证件的相关信息可在当地工商局查询，企业名称及相关证件号须与填写内容一致<br/>
                                (2).证件照片须为清晰的原件扫描件或者加盖公章复印件，且证件须在有效期内<br/>
                                (3).企业的营业执照、税务登记证须为同一家公司所有，证件信息一致<br/>
                            </p>
                        </div>
                        <div class="api_m2_box API3"  id="hmshbz">
                            <span class="api_com_font">3.号码审核标准</span>
                            <p>
                                号码由开发者上传，使用的是玖云平台提供的电话号码。其中包括400号码以及企业的官方固定电话号码等。号码需真实有效，并具有合法的使用权限。<br/>
                                号码审核需企业提供如下资料：<br/>
                                a.提供号码的开户证明<br/>
                                b.授权玖云平台使用的授权书，打印后加盖企业公章，提供清晰扫描件<br/>
                            </p>
                            <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/AttorneyModule.png" alt="授权书模板图片"/></p>
                            <p>
                                <a href="${appConfig.resourcesRoot}/master/file/e9cloud_authorization.docx" class="load" download="e9cloud_authorization.docx"><b class="icon downTxt"></b>授权书模板下载</a>
                            </p>
                            <p>
                                资料发送至玖云平台官方服务邮箱：support@33e9cloud.com<br/>
                                邮件主题：“号码审核”+公司名称+开发者注册邮箱<br/>
                                邮件内容：以上两份资料作为附件提交
                            </p>
                            <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/EmailWay.png" alt="邮件格式图片"/></p>
                        </div>
                        <div class="api_m2_box API4">
                            <span class="api_com_font" id="lsshbz">4.铃声审核标准</span>
                            <p>
                                铃声审核适用于开发者上传的自定义铃声。铃声必须清晰且不包含任何非法内容。<br/>
                                具体审核标准如下：<br/>
                                a.铃声文件大小不超过2M<br/>
                                b.铃声文件采用WAV格式<br/>
                                c.铃声文件内容清晰<br/>
                                d.铃声内容不包含任何营销推广类信息<br/>
                                e.铃声内容不包含任何非法内容 <br/>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="API_main3 f_left">
                <div class="stb"> <span class="click_tb spread_tb"></span></div>
                <div class="api_right_box FHeight">
                    <div class="api_right_center">
                        <p class="b1"><b class="b"></b>1&nbsp;审核时间</p>
                        <p class="b2"><b class="b"></b>2&nbsp;资质审核标准</p>
                        <p class="b3"><b class="b"></b>3&nbsp;号码审核标准</p>
                        <p class="b4"><b class="b"></b>4&nbsp;铃声审核标准</p>
                    </div>
                </div>
                <div class="xtb"><span class="click_tb close_tb"></span></div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

<script src="${appConfig.resourcesRoot}/master/js/api.js"></script>

</body>
</html>