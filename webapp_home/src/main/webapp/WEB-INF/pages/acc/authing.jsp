<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/3/5
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>控制中心</title>
    <link href="${appConfig.resourcesRoot}/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="${appConfig.resourcesRoot}/js/common/jquery-1.11.3.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/common/ajaxdicdata.js"></script>
    <script src="${appConfig.resourcesRoot}/js/my.js"></script>
</head>
<body>
<jsp:include page="../common/controlheader.jsp"/>
<section>
    <div id="sec_main">
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('zh','acc_manage','authen');</script>
        <div class="main3">
            <div class="container2" >
                <div class="msg">
                    <h3>认证信息</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="validation_msg">
                    <div class="app_note">
                        <span class="WarmPrompt">温馨提醒：</span>
                        <p>1.每个客户须完成企业资质信息认证通过后才能创建应用及进行使用。</p>
                        <p>2.认证名称将作为企业资质审核和开发票的重要依据</p>
                        <p>3.每日16：30分前提交认证申请的将在当日反馈审核结果，超过此时间段，将在次日反馈审核结果，节假日顺延。</p>
                    </div>
                    <p class="id_test">
                        <i class="common_icon de_txt"></i>企业认证信息
                        <span class="alert_base_msg id_left">
                            <i class="common_icon state_txt"></i>状态：
                            <img src="${appConfig.resourcesRoot}/img/checking.png" alt="图片"/>
                            <!--<img src="img/checking.png" alt="图片"/>-->
                            <!--<img src="img/pass.png" alt="图片"/>-->
                        </span>
                    </p>
                    <div class="warpper">
                        <form action="">
                            <div class="developers">
                                <span class="common">开发者类型</span>
                                <input type="radio" name="developers" checked="checked"/><span class="gs">公司</span>
                            </div>

                            <!--公司页面内容-->
                            <div class="company">
                                <div class="cpy_intro">
                                    <label>公司名称：</label>
                                    <span class="intro_msg_common"><c:out value="${company.name}"/></span><br/>
                                    <label>公司地址：</label>
                                    <span class="intro_msg_common"><c:out value="${company.address}"/></span><br/>
                                    <label>所属行业：</label>
                                    <span class="intro_msg_common"><c:out value="${company.dicData.name}"/></span>
                                </div>
                                <c:if test="${company.cardType =='0'}">
                                    <div class="organization">
                                        <label>证件类型：</label>
                                        <span class="intro_msg_common">三证合一(一照一码)</span><br/>
                                        <label>统一社会<br/>信用代码：</label>
                                        <span class="intro_msg_common"><c:out value="${company.creditNo}"/></span> <br/>
                                        <label>营业执照：</label>
                                        <span class="amplification" data-mark="mark"><i class="amplification_txt"></i>查看大图</span>
                                        <p class="p_left"> <img data-mark="mark" class="certificateImg clear" src="<c:url value="/nasimg/anthen/${company.businessLicensePic}"/>" alt="营业执照"/></p>

                                    </div>
                                </c:if>
                                <c:if test="${company.cardType =='1'}">
                                    <div class="organization">
                                        <label>证件类型：</label>
                                        <span class="intro_msg_common">三证合一</span>
                                        <div id="pattern_two">
                                            <label class="register_right">注册号：</label>
                                            <span class="intro_msg_common"><c:out value="${company.registerNo}"/></span><br/>
                                            <label>税务登记号：</label>
                                            <span class="intro_msg_common tax_left"><c:out value="${company.taxRegNo}"/></span><br/>
                                            <label>营业执照：</label>
                                            <span class="amplification" data-mark="mark"><i class="amplification_txt"></i>查看大图</span>
                                            <p class="p_left"> <img data-mark="mark" class="certificateImg clear" src="<c:url value="/nasimg/anthen/${company.businessLicensePic}"/>" alt="营业执照"/></p>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${company.cardType =='2'}">
                                    <div class="organization">
                                        <label>证件类型：</label>
                                        <span class="intro_msg_common">三证分离</span><br/>
                                        <div id="pattern_three">
                                            <label>税务登记号：</label>
                                            <span class="intro_msg_common tax_left"><c:out value="${company.taxRegNo}"/></span><br/>
                                            <label>税务登记证：</label>
                                            <span class="amplification" style="margin-left: 130px" data-mark="mark"><i class="amplification_txt"></i>查看大图</span>
                                            <p class="p_left"><img data-mark="mark" class="certificateImg clear" src="<c:url value="/nasimg/anthen/${company.taxRegPic}"/>" alt="税务登记证"/></p>

                                            <label>营业执照号：</label>
                                            <span class="intro_msg_common tax_left"><c:out value="${company.businessLicenseNo}"/></span><br/>
                                            <label>营业执照：</label>
                                            <span class="amplification" data-mark="mark"><i class="amplification_txt"></i>查看大图</span>
                                            <p class="p_left"><img data-mark="mark" class="certificateImg clear" src="<c:url value="/nasimg/anthen/${company.businessLicensePic}"/>" alt="营业执照"/></p>
                                        </div>
                                    </div>
                                </c:if>

                                <div class="Legal">
                                    <label>法定代表人：</label>
                                    <span class="test_msg_common test_cpy_common"><c:out value="${company.legalRepresentative}"/></span> <br/>
                                    <label>公司电话：</label>
                                    <span class="test_msg_common"><c:out value="${company.telno}"/></span><br/>
                                    <label>公司网站：</label>
                                    <span class="test_msg_common"><c:out value="${company.website}"/></span>
                                </div>

                            </div>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>
</section>
<script type="text/javascript">
    ajaxDicData('${appConfig.authUrl}',"appType","tradeId",'0');
    $(function(){
        var siginForm = $("#authForm").validate({
            rules: {
                name: {
                    required: true
                },
                address: {
                    required: true
                },
                tradeId: {
                    required: true
                }

            },
            messages: {
                name: {
                    required: '请输入企业名称！'
                },
                address: {
                    required: '请输入企业地址！'
                },
                tradeId: {
                    required: '请选择行业'
                }
            }
        });

        $('#authButton').click(function() {
            if(siginForm.form()) {
                $('#authForm').submit();
            }
        });
    });

    // 切换tab
    function switchTab(sid, hid,fid) {

        $(sid).show();
        $(hid).hide();
        $(fid).hide();
    }
</script>

<div class="scaleImgBox" data-mark="mark" style="display:none; position: absolute; border:5px solid #CCCCCC; left: 524px; top:385px; width: 700px;  background:white">
    <button type='button' class='narrow' onclick='narrow()'><i class='narrow_txt'></i>查看原图</button>
    <img data-mark="mark" src='' class='certificateImgTwo' style="width: 700px;"/>
</div>

</body>
</html>

