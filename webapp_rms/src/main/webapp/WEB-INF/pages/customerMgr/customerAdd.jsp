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

            $('.skin-minimal input').on('ifChecked', function(event){
                //如果是选中，点击后则为不选中
                $("#businessType").val($("#businessType").val()+$(this).val()+",");
            });
            $('.skin-minimal input').on('ifUnchecked', function(event){
                //如果不选中，点击后则为选中
                $("#businessType").val($("#businessType").val().replace($(this).val()+",",""));
            });

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
        });
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/customerMgr/saveCustomer" />" method="post" class="form form-horizontal" id="addCustomerManager">
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>Account ID：</label>
            <div class="formControls col-8">
                <input type="text" class="input-text" value="" placeholder="请输入开发者Account ID" id="accountId" name="accountId" datatype="checkedCallSid" errormsg="Account ID不合法！" nullmsg="Account ID不能为空"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>客户名称：</label>
            <div class="formControls col-8">
                <span id="nameSpan" style="color: #cccccc">客户名称自动匹配</span>
                <input type="hidden" id="customerName" name="customerName"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2">所属行业：</label>
            <div class="formControls col-8">
                <select id="trade" name="trade" class="select" style="width:200px;height: 31px">
                    <c:forEach items="${tradeTypeDic}" var="tradeType">
                        <option value="${tradeType.code}">${tradeType.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2">客户所在地：</label>
            <div class="formControls col-8">
                <select id="province" name="province" class="select" style="width:200px;height: 31px">
                    <c:forEach items="${provinceDic}" var="province">
                        <option  id="${province.id}" value="${province.code}">${province.name}</option>
                    </c:forEach>
                </select>
                <select id="city" name="city" class="select" style="width:200px;height: 31px">
                    <c:forEach items="${cityDic}" var="city">
                        <option  id="${city.id}" value="${city.code}">${city.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2">合作业务类型：</label>
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
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2">签约状态：</label>
            <div class="formControls col-8">
                <select id="signed" name="signed" class="select" style="width:200px;height: 31px" datatype="*1-10" errormsg="请选择签约状态！" nullmsg="请选择签约状态！">
                    <option value="">请选择</option>
                    <c:forEach items="${signedDic}" var="signed">
                        <option id="${signed.id}" value="${signed.code}">${signed.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>客户经理：</label>
            <div class="formControls col-8">
                <input type="text" class="input-text" value="" placeholder="请输入客户经理姓名" id="customerManager" name="customerManager" datatype="*2-10" maxlength="10" errormsg="客户经理姓名不合法！" nullmsg="客户经理姓名不能为空"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <div class="col-offset-2">
                <input class="btn btn-primary radius" style="width: 100px;" type="submit" value="提交">
            </div>
        </div>
    </form>
</div>
</body>
</html>
