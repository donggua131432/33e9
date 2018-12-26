<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>认证信息</title>
</head>
<body>
<div class="pd-20">

    <form class="form form-horizontal" id="form-edit" action="<c:url value='/developers/editAudit'/>" method="post">
        <input type="hidden" name="id" value="${authenCompany.id}"/>
        <input type="hidden" name="uid" value="${authenCompany.uid}">
        <div style="margin-top: 0px">
            <label class="form-label col-4">公司名称：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${authenCompany.name}
            </div>
        </div>
        <div class="cl">
            <label class="form-label col-4">客户账号：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${userAdmin.email}
            </div>
        </div>
        <div class="cl">
            <label class="form-label col-4">公司地址：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${authenCompany.address}
            </div>
        </div>

        <div class="cl">
            <label class="form-label col-4">所属行业：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${dicData.name}
            </div>
        </div>

        <div class="cl">
            <label class="form-label col-4">审核状态：</label>
            <div class="formControls col-8" style="line-height: 2">
                通过
            </div>
        </div>
        <br>
        <center><hr align="center" style="height:1px;border:none;border-top:1px dashed black;width: 80%" /></center>
        <br>
        <c:if test="${authenCompany.cardType == '0' }">
            <div class="row cl">
                <label class="form-label col-4">证件类型：</label>
                <div class="formControls col-8" style="line-height: 2">
                    三证合一(一照一码)
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">统一社会&nbsp;&nbsp;&nbsp;<br>信用代码：</label>
                <div class="formControls col-8" style="line-height: 2">
                        ${authenCompany.creditNo}
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-4">营业执照：</label>
                <div class="formControls col-8" style="line-height: 2">
                    <img src="<c:url value="/img/anthen/${authenCompany.businessLicensePic}"/>" class="thumbnail" alt="营业执照"/>
                </div>
            </div>
        </c:if>

        <c:if test="${authenCompany.cardType == '1' }">
            <div class="row cl">
                <label class="form-label col-4">证件类型：</label>
                <div class="formControls col-8" style="line-height: 2">
                    三证合一
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">注册号：</label>
                <div class="formControls col-8" style="line-height: 2">
                        ${authenCompany.registerNo}
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-4">税务登记号：</label>
                <div class="formControls col-8" style="line-height: 2">
                        ${authenCompany.taxRegNo}
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-4">营业执照：</label>
                <div class="formControls col-8" style="line-height: 2">
                    <img src="<c:url value="/img/anthen/${authenCompany.businessLicensePic}"/>" class="thumbnail" alt="营业执照"/>
                </div>
            </div>
        </c:if>

        <c:if test="${authenCompany.cardType == '2' }">
            <div class="row cl">
                <label class="form-label col-4">证件类型：</label>
                <div class="formControls col-8" style="line-height: 2">
                    三证分离
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">税务登记号：</label>
                <div class="formControls col-8" style="line-height: 2">
                        ${authenCompany.taxRegNo}
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">税务登记证：</label>
                <div class="formControls col-8" style="line-height: 2">
                    <img src="<c:url value="/img/anthen/${authenCompany.taxRegPic}"/>" class="thumbnail" alt="税务登记证"/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">营业执照号：</label>
                <div class="formControls col-8" style="line-height: 2">
                        ${authenCompany.businessLicenseNo}
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">营业执照：</label>
                <div class="formControls col-8" style="line-height: 2">
                    <img src="<c:url value="/img/anthen/${authenCompany.businessLicensePic}"/>" class="thumbnail" alt="营业执照"/>
                </div>
            </div>
        </c:if>

        <br>
        <center><hr align="center" style="height:1px;border:none;border-top:1px dashed black;width:80%" /></center>
        <br>

        <div class="cl">
            <label class="form-label col-4">法人代表：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${authenCompany.legalRepresentative}
            </div>
        </div>
        <div class="cl">
            <label class="form-label col-4">公司电话：</label>
            <div class="formControls col-8" style="line-height: 2">
                ${authenCompany.telno}
            </div>
        </div>
        <div class="cl">
            <label class="form-label col-4">公司网站：</label>
            <div class="formControls col-8" style="line-height: 2;">
                ${authenCompany.website}
            </div>
        </div>
        <div class="cl">
            <label class="form-label col-4">备注说明：</label>
            <div class="formControls col-8" style="line-height: 2;word-break: break-all; word-wrap: break-word;width: 300px;height: 100px">
                <c:out value="${authenCompanyRecords.comment}"/>
            </div>
        </div>

    </form>
</div>
</body>
</html>