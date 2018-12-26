<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>账户充值</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function(){
            $("#form-admin-add").Validform({
                tiptype:2,
/*                callback:function(form){
                    form[0].submit();
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.$('.btn-refresh').click();
                    parent.layer.close(index);
                }*/
                ajaxPost:true,
                callback:function(msg){
                    if (msg.code == "ok") {
                        rms_admin_tab('f6f19128bd0447e4a830a0b0c8766a8c');
                        refresh();
                    }
                },
                datatype: {
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
                                        checkStatus = true;
                                    }
                                }
                            });
                        }else {
                            $("#name").val("");
                            return "账户信息输入有误，请重新输入!";
                        }


                        if(checkStatus == true){
                            return true;
                        }else{
                            $("#name").val("");
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
    <span class="c-gray en">&gt;</span> 账户充值
    <a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新">
        <i class="Hui-iconfont">&#xe68f;</i>
    </a>
</nav>
<div class="pd-20">
    <form action="<c:url value='/financial/recharge'/>" method="post" class="form form-horizontal" id="form-admin-add">
        <div class="row cl">
            <label class="form-label col-2" style="width: 100px"><span class="c-red">*</span>充值账号：</label>
            <div class="formControls col-7" style="width: 30%">
                <input type="text" class="input-text" value=""  placeholder="请输入充值账号" id="sid" name="sid" datatype="checkedSid" nullmsg="充值账号不能为空" errormsg="账户信息输入有误，请重新输入!"/>
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
            <label class="form-label col-2" style="width: 100px"><span class="c-red">*</span>收款时间：</label>
            <div class="formControls col-7" style="width: auto">
                <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss', maxDate: '%y-%M-%d %H:%m:%s'})" id="collectionDate" name="collectionTime" style="width:186px;" value="" datatype="*" class="input-text Wdate" nullmsg="收款时间不能为空"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2" style="width: 100px"><span class="c-red">*</span>充值金额：</label>
            <div class="formControls col-7" style="width: 300px">
                <input type="text" style="width:270px" class="input-text" value=""  placeholder="" id="fee" name="money" datatype="/^(?:0\.\d{0,3}[1-9]|(?!0)\d{1,13}(?:\.\d{0,3}[1-9])?)$/" nullmsg="充值金额不能为空" errormsg="请输入小数位不超过四位的数值，不支持负数"/>
                <span>元</span>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2" style="width: 100px"><span class="c-red">*</span>确认金额：</label>
            <div class="formControls col-7" style="width: 300px">
                <input type="text" style="width:270px" class="input-text" value=""  placeholder="" id="comfirm-fee" name="comfirm-fee"  datatype="*" recheck="money" nullmsg="确认金额不能为空" errormsg="充值金额与确认金额不一致，请确认调整。"/>
                <span>元</span>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2" style="width: 100px"><span class="c-red">*</span>充值类型：</label>
            <div class="formControls col-7" style="width: 300px">
                <select id="type" name="type" class="input-text" datatype="*"  nullmsg="请选择充值类型" style="width:200px ;height:32px">
                    <option value="">请选择</option>
                    <option value="00">测试金</option>
                    <option value="01">授信额度</option>
                    <option value="02">充值</option>
                    <option value="03">补退款</option>
                </select>
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
