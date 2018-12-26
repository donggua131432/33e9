<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>开发者资料</title>
</head>
<body>
<div class="pd-20">
    <form action="" method="post" class="form form-horizontal" id="form-member-add">
        <div class="row cl">
            <div class="formControls col-6">
                <div class="row cl">
                    <label class="form-label col-3">客户编码：</label>
                    <div class="formControls col-8">
                        <span class="mt-3">
                            <c:out value="${userAdmin.uid}"/>
                        </span>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-3">AccountID：</label>
                    <div class="formControls col-8">
                        <span class="mt-3">
                            <c:out value="${userAdmin.sid}"/>
                        </span>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-3">客户账号：</label>
                    <div class="formControls col-8">
                        <span class="mt-3">
                            <c:out value="${userAdmin.email}"/>
                        </span>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-3">客户名称：</label>
                    <div class="formControls col-8">
                        <span class="mt-3">
                            <c:out value="${authenCompany.name}"/>
                        </span>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-3">所属行业：</label>
                    <div class="formControls col-8">
                        <span class="mt-3">
                            <c:out value="${authenCompany.dicData.name}"/>
                        </span>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-3">账户余额：</label>
                    <div class="formControls col-8">
                        <span class="mt-3">
                            <c:out value="${userAdmin.fee}"/>&nbsp;元
                        </span>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-3">认证状态：</label>
                    <div class="formControls col-8">
                        <span class="mt-3">
                            <c:choose>
                                <c:when test="${userAdmin.authStatus eq '2'}">
                                    <span class="label label-success radius">已认证</span>
                                </c:when>
                                <c:when test="${userAdmin.authStatus eq '1'}">
                                    <span class="label label-warning radius">认证中</span>
                                </c:when>
                                <c:when test="${userAdmin.authStatus eq '0'}">
                                    <span class="label label-defaunt radius">未认证</span>
                                </c:when>
                                <c:when test="${userAdmin.authStatus eq '3'}">
                                    <span class="label label-defaunt radius">认证不通过</span>
                                </c:when>
                            </c:choose>
                        </span>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-3">已开通业务：</label>
                    <div class="formControls col-8">
                        <span class="mt-3">
                                    <span class="label label-secondary radius">专线语音</span>
                                <c:if test="${count1 eq '1'}">
                                    <span class="label label-secondary radius">智能云调度</span>
                                </c:if>
                                <c:if test="${count3 eq '1'}">
                                    <span class="label label-secondary radius">sip业务</span>
                                </c:if>
                        </span>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-3">开通增值服务：</label>
                    <div class="formControls col-8">
                        <span class="mt-3">
                                <c:if test="${countB eq '1'}">
                                    <span class="label label-secondary radius">B路录音</span>
                                </c:if>
                                <c:if test="${countB != '1'}">
                                    <span class="label label-secondary radius">无</span>
                                </c:if>
                        </span>
                    </div>
                </div>


                <div class="row cl">
                    <label class="form-label col-3">手机号：</label>
                    <div class="formControls col-8">
                        <span class="mt-3">
                            <c:out value="${userAdmin.mobile}"/>
                        </span>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-3">联系地址：</label>
                    <div class="formControls col-8">
                        <span class="mt-3">
                            <c:out value="${userExternInfo.address}"/>
                        </span>
                    </div>
                </div>
            </div>

            <div class="formControls col-6">

                <c:choose>
                    <c:when test="${authenCompany.cardType eq '0'}">
                        <div class="row cl">
                            <label class="form-label col-3">证件类型：</label>
                            <div class="formControls col-8">
                                <span class="mt-3">
                                三证合一(一照一码)
                                </span>
                            </div>
                        </div>

                        <div class="row cl">
                            <label class="form-label col-3">统一社会&nbsp;&nbsp;&nbsp;<br/>信用代码：</label>
                            <div class="formControls col-8">
                                <span class="mt-3">
                                        ${authenCompany.creditNo}
                                </span>
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-3">营业执照：</label>
                            <div class="formControls col-8">
                                <span class="mt-3">
                                    <img src="<c:url value="/img/anthen/${authenCompany.businessLicensePic}"/>" alt="营业执照" class="thumbnail"/>
                                </span>
                            </div>
                        </div>

                    </c:when>
                    <c:when test="${authenCompany.cardType eq '1'}">
                        <div class="row cl">
                            <label class="form-label col-3">证件类型：</label>
                            <div class="formControls col-8">
                                <span class="mt-3">
                                三证合一
                                </span>
                            </div>
                        </div>

                        <div class="row cl">
                            <label class="form-label col-3">注册号：</label>
                            <div class="formControls col-8">
                                <span class="mt-3">
                                        ${authenCompany.registerNo}
                                </span>
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-3">税务登记号：</label>
                            <div class="formControls col-8">
                                <span class="mt-3">
                                        ${authenCompany.taxRegNo}
                                </span>
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-3">营业执照：</label>
                            <div class="formControls col-8">
                                <span class="mt-3">
                                    <img src="<c:url value="/img/anthen/${authenCompany.businessLicensePic}"/>" alt="营业执照" class="thumbnail"/>
                                </span>
                            </div>
                        </div>
                    </c:when>
                    <c:when test="${authenCompany.cardType eq '2'}">

                        <div class="row cl">
                            <label class="form-label col-3">证件类型：</label>
                            <div class="formControls col-8">
                                <span class="mt-3">
                                三证分离
                                </span>
                            </div>
                        </div>

                        <div class="row cl">
                            <label class="form-label col-3">税务登记号：</label>
                            <div class="formControls col-8">
                                <span class="mt-3">
                                        ${authenCompany.taxRegNo}
                                </span>
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-3">税务登记证：</label>
                            <div class="formControls col-8">
                                <span class="mt-3">
                                    <img src="<c:url value="/img/anthen/${authenCompany.businessLicensePic}"/>" alt="税务登记证" class="thumbnail"/>
                                </span>
                            </div>
                        </div>

                        <div class="row cl">
                            <label class="form-label col-3">营业执照号：</label>
                            <div class="formControls col-8">
                                <span class="mt-3">
                                        ${authenCompany.businessLicenseNo}
                                </span>
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-3">营业执照：</label>
                            <div class="formControls col-8">
                                <span class="mt-3">
                                    <img src="<c:url value="/img/anthen/${authenCompany.businessLicensePic}"/>" alt="营业执照" class="thumbnail"/>
                                </span>
                            </div>
                        </div>

                    </c:when>
                </c:choose>

                <div class="row cl">
                    <label class="form-label col-3">法人代表：</label>
                    <div class="formControls col-8">
                    <span class="mt-3">
                        <c:out value="${authenCompany.legalRepresentative}"/>
                    </span>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-3">公司地址：</label>
                    <div class="formControls col-8">
                    <span class="mt-3">
                        <c:out value="${authenCompany.address}"/>
                    </span>
                    </div>
                </div>

            </div>

        </div>

    </form>
</div>
</body>
</html>