<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>费用扣款</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function(){
            $("#form-admin-add").Validform({
                tiptype:2,
               /* callback:function(form){
                    form[0].submit();
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.$('.btn-refresh').click();
                    parent.layer.close(index);
                }*/
                ajaxPost:true,
                callback:function(msg){
                    if (msg.code == "ok") {
                        rms_admin_tab('7bc9c282008f49e5ac4a83bbca11db03');
                        refresh();
                    }
                },
                datatype:{
                    "checkedSid":function(gets, obj, curform, datatype){
                        var sid = gets.trim(), checkStatus = false, matchReg = /^([0-9a-zA-Z]{32})$/;
                        if(matchReg.test(sid)){
                            $.ajax( {
                                type:"post",
                                url:"<c:url value='/authenCompany/getAuthenCompanyBySID'/>",
                                dataType:"json",
                                async:false,
                                data:{'sid':sid},
                                success : function(data) {
                                    if(data.status == 0){
                                        $("#name").val(data.authenCompany.name);
                                        $("#balance").val(data.userAdmin.fee);
                                        checkStatus = true;
                                    }

                                }
                            });
                        }else{
                            $("#name").val("");
                            $("#balance").val("");
                            return "账户信息输入有误，请重新输入!";
                        }
                        if(checkStatus == true){
                            return true;
                        }else{
                            $("#name").val("");
                            $("#balance").val("");
                            return "账户信息输入有误,不存在或未进行资质认证!";
                        }
                    }
                }
            });

        });

    </script>
</head>
<body>
<nav class="breadcrumb">
    <i class="Hui-iconfont">&#xe67f;</i> 首页
    <span class="c-gray en">&gt;</span> 财务管理
    <span class="c-gray en">&gt;</span> 费用扣款
    <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新">
        <i class="Hui-iconfont">&#xe68f;</i>
    </a>
</nav>
<div class="pd-20">
    <form action="<c:url value='/financial/payment'/>" method="post" class="form form-horizontal" id="form-admin-add">
        <div class="row cl">
            <label class="form-label col-2" style="width: 100px"><span class="c-red">*</span>账号ID：</label>
            <div class="formControls col-7" style="width: 30%">
                <input type="text" class="input-text" value=""  placeholder="请输入账号ID" id="sid" name="sid" datatype="checkedSid" nullmsg="AccountID不能为空" errormsg="账户信息输入有误，请重新输入!"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2" style="width: 100px">客户名称：</label>
            <div class="formControls col-7" style="width: 30%">
                <input type="text" class="input-text" value="" disabled="disabled" placeholder="客户名称自动匹配" id="name" name="name"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2" style="width: 100px">账户余额：</label>
            <div class="formControls col-7" style="width: 300px">
                <input type="text" style="width:270px" class="input-text" value="" disabled="disabled" placeholder="账户余额自动匹配" id="balance" name="balance"/>
                <span>元</span>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2" style="width: 100px"><span class="c-red">*</span>扣款金额：</label>
            <div class="formControls col-7" style="width: 300px">
                <input type="text" style="width:270px" class="input-text" value=""  placeholder="" id="fee" name="money" datatype="/^(?:0\.\d{0,3}[1-9]|(?!0)\d{1,13}(?:\.\d{0,3}[1-9])?)$/" nullmsg="扣款金额不能为空" errormsg="请输入小数位不超过四位的数值，不支持负数"/>
                <span>元</span>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2" style="width: 100px"><span class="c-red">*</span>确认金额：</label>
            <div class="formControls col-7" style="width: 300px">
                <input type="text" style="width:270px" class="input-text" value=""  placeholder="" id="comfirm-fee" name="name" recheck="money" datatype="*"  nullmsg="确认金额不能为空" errormsg="确认金额与扣款金额不一致，请确认调整。"/>
                <span>元</span>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2" style="width: 100px">备注：</label>
            <div class="formControls col-7">
                <textarea style="width: 300px; height: 100px" name="comment" maxlength="100"></textarea>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <div class="col-9 col-offset-3" style="margin-left: 10%">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
                <input class="btn btn-default radius" type="reset" value="&nbsp;&nbsp;取消&nbsp;&nbsp;">
            </div>
        </div>
    </form>
</div>

</body>
</html>
