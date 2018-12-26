<%@ page  pageEncoding="utf-8" contentType="application/xml;charset=utf-8" session="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<CallBack>
    <statusCode>${statusCode}</statusCode>
    <SoftVersion>${params.SoftVersion}</SoftVersion>
    <AccountSid> ${params.AccountSid}</AccountSid>
    <Sig>${params.Sig}</Sig>
</CallBack>
