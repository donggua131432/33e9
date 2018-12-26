<%@ page  pageEncoding="utf-8" contentType="application/json;charset=utf-8" session="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
{
"statusCode": "${statusCode}",
 "CallBack": {
    "SoftVersion": "${params.SoftVersion}",
    "AccountSid": " ${params.AccountSid}",
     "Sig": "${params.Sig}"
}
}
