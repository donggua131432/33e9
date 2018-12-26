<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/07/08
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>客户添加</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function() {
            <c:if test="${operationType == 'edit'}">
                $('.skin-minimal input').on('ifChecked', function(event){
                    //如果是选中，点击后则为不选中
                    $("#businessType").val($("#businessType").val()+$(this).val()+",");
                });
                $('.skin-minimal input').on('ifUnchecked', function(event){
                    //如果不选中，点击后则为选中
                    $("#businessType").val($("#businessType").val().replace($(this).val()+",",""));
                });
            </c:if>

            $('.skin-minimal input').iCheck({
                checkboxClass: 'icheckbox-blue',
                radioClass: 'iradio-blue',
                increaseArea: '20%'
            });

            $("#addCustomerManager").Validform({
                tiptype:2,
                ajaxPost:true,
                callback:function(data){
                    setTimeout(function(){
                        $.Hidemsg();
                        if(data.status == 0){
                            //刷新父级页面
                            parent.location.replace(parent.location.href);
                            layer_close();
                        }
                    },2000);
                },
                datatype: {
                    "checkedCallSid":function(gets, obj, curform, datatype){
                        var sid = gets.trim(), checkStatus = false, matchReg = /^([0-9a-zA-Z]{32})$/;
                        if(matchReg.test(sid)){
                            $.ajax( {
                                type:"post",
                                url:"<c:url value='/userAdmin/getUserAdminWithCompany'/>",
                                dataType:"json",
                                async:false,
                                data:{'sid':sid},
                                success : function(data) {
                                    if(data.status == 0){
                                        $("#nameSpan").text(data.userAdminResult.authenCompany.name).removeAttr("style");
                                        $("#customerName").val(data.userAdminResult.authenCompany.name);
                                        checkStatus = true;
                                    }
                                }
                            });
                        }else{
                            $("#nameSpan").text("客户名称自动匹配").css("color","#cccccc");
                            $("#customerName").val("");
                            return "开发者ID不合法！";
                        }
                        if(checkStatus == true){
                            return true;
                        }else{
                            $("#nameSpan").text("客户名称自动匹配").css("color","#cccccc");
                            $("#customerName").val("");
                            return "开发者ID不存在！";
                        }
                    }
                }
            });

            //省市级联
            cascadeArea("province","city","<c:url value='/dicdata/pid'/>");


            var provinceCityArray = {}, tradeTypeArray={};
            //省市信息
            <c:forEach items="${provinceCityDic}" var="provinceCity">
            provinceCityArray["${provinceCity.code}"] = "${provinceCity.name}";
            </c:forEach>
            //所属行业类型
            <c:forEach items="${tradeTypeDic}" var="tradeType">
            tradeTypeArray["${tradeType.code}"] = "${tradeType.name}";
            </c:forEach>
            $("#tradeSpan").text(tradeTypeArray["${customerManager.trade}"]);
            $("#provinceCitySpan").text(provinceCityArray["${customerManager.province}"]+provinceCityArray["${customerManager.city}"]);

            //设置复选框的值
            $(".skin-minimal input").each(function(){
                if("${customerManager.businessType}".indexOf($(this).val()) >= 0){
                    $(this).iCheck('check');
                }
            });
        });
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/customerMgr/updateCustomer" />" method="post" class="form form-horizontal" id="addCustomerManager">
        <input type="hidden" name="id" value="${customerManager.id}"/>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>Account ID：</label>
            <div class="formControls col-8">
                    ${customerManager.accountId}
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>客户名称：</label>
            <div class="formControls col-8">
                    ${customerManager.customerName}
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">所属行业：</label>
            <div class="formControls col-8">
                <span id="tradeSpan"></span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">客户所在地：</label>
            <div class="formControls col-8">
                <span id="provinceCitySpan"></span>
            </div>
        </div>
        <c:if test="${operationType == 'edit'}">
            <div class="row cl">
                <label class="form-label col-4">合作业务类型：</label>
                <div class="formControls col-8">
                    <div class="skin-minimal">
                        <div class="check-box">
                            <input type="checkbox" id="checkbox-1" value="01"/>
                            <label for="checkbox-1">专线语音</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" id="checkbox-2" value="02"/>
                            <label for="checkbox-2">智能云调度</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" id="checkbox-3" value="03">
                            <label for="checkbox-3">专号通</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" id="checkbox-4" value="04">
                            <label for="checkbox-4">SIP</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" id="checkbox-5" value="05">
                            <label for="checkbox-5">云话机</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" id="checkbox-6" value="06">
                            <label for="checkbox-6">语音通知</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" id="checkbox-7" value="07">
                            <label for="checkbox-7">云总机</label>
                        </div>
                    </div>
                    <input type="hidden" id="businessType" name="businessType"/>
                </div>
                <%--<div class="col-2"></div>--%>
            </div>

            <div class="row cl">
                <label class="form-label col-4">签约状态：</label>
                <div class="formControls col-8">
                    <select id="signed" name="signed" class="select" style="width:200px;height: 31px" errormsg="请选择签约状态！" nullmsg="请选择签约状态！">
                        <option value="">请选择</option>
                        <c:forEach items="${signedDic}" var="signed">
                            <c:if test="${customerManager.signed eq signed.code}">
                                <option id="${signed.id}" value="${signed.code}" selected>${signed.name}</option>
                            </c:if>
                            <c:if test="${customerManager.signed ne signed.code}">
                                <option id="${signed.id}" value="${signed.code}">${signed.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-4"><span class="c-red">*</span>客户经理：</label>
                <div class="formControls col-6">
                    <input type="text" class="input-text" value="${customerManager.customerManager}" placeholder="请输入客户经理姓名" id="customerManager" name="customerManager" datatype="*2-10" maxlength="10" errormsg="客户经理姓名不合法！" nullmsg="客户经理姓名不能为空"/>
                </div>
                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <div class="col-offset-4">
                    <input class="btn btn-primary radius" style="width: 100px;" type="submit" value="提交">
                </div>
            </div>
        </c:if>

        <c:if test="${operationType == 'view'}">
            <div class="row cl">
                <label class="form-label col-4">合作业务类型：</label>
                <div class="formControls col-8">
                    <div class="skin-minimal">
                        <div class="check-box">
                            <input type="checkbox" disabled value="01"/>
                            <label>专线语音</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" disabled value="02"/>
                            <label>智能云调度</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" disabled value="03">
                            <label>专号通</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" disabled value="04">
                            <label>SIP</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" disabled value="05">
                            <label>云话机</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" disabled value="06">
                            <label>语音通知</label>
                        </div>
                        <div class="check-box">
                            <input type="checkbox" disabled value="07">
                            <label>云总机</label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4">签约状态：</label>
                <div class="formControls col-8">
                    <c:forEach items="${signedDic}" var="signed">
                        <c:if test="${customerManager.signed eq signed.code}">
                            ${signed.name}
                        </c:if>
                    </c:forEach>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-4"><span class="c-red">*</span>客户经理：</label>
                <div class="formControls col-8">
                        ${customerManager.customerManager}
                </div>
            </div>
        </c:if>
    </form>
</div>
</body>
</html>
