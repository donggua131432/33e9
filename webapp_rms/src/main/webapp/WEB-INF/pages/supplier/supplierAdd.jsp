<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>客户添加</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
    <script type="text/javascript">
        $(function() {
            $('.skin-minimal input').iCheck({
                checkboxClass: 'icheckbox-blue',
                radioClass: 'iradio-blue',
                increaseArea: '20%'
            });

            $("#addSupplier").Validform({
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
                    "zh1-10":/^[\u4E00-\u9FA5\uf900-\ufa2d]{1,12}$/,
                    "empty": /^\s*$/
                }
            });
        });
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/supplier/saveSupplier" />" method="post" class="form form-horizontal" id="addSupplier">
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>供应商名称：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="" placeholder="请输入供应商名称" id="supName" name="supName" datatype="zh1-30|n1-30|s1-30" maxlength="30" errormsg="供应商名称不合法！" nullmsg="供应商名称不能为空"/>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>供应商联系人：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="" placeholder="请输入供应商联系人,最大不超过30个汉字" id="contacts" name="contacts" datatype="zh1-6|n1-6|s1-6" maxlength="6" errormsg="供应商联系人不合法！" nullmsg="供应商联系人不能为空"/>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>供应商地址：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="" placeholder="支持中英文数字,最大不超过100个汉字" id="address" name="address" datatype="zh1-100|n1-100|s1-100" maxlength="100" errormsg="供应商地址不合法！" nullmsg="供应商地址不能为空"/>
            </div>
            <div class="col-3"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red"></span>联系方式：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="" placeholder="最大不超过11个数字" id="mobile" name="mobile" datatype="empty|n9-11" maxlength="11" errormsg="联系方式不合法！" />
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>结算方式：</label>
            <div class="formControls col-4">
                <div class="skin-minimal">
                    <div class="radio-box">
                        <input type="radio" id="payType1" name="payType" value="00"  onclick="switchTab('#pattern_one', '#pattern_two', '#pattern_three');" checked="checked">
                        <label for="payType1">预付</label>
                    </div>
                    <div class="radio-box">
                        <input type="radio" id="payType2" name="payType" value="01" onclick="switchTab('#pattern_two', '#pattern_one', '#pattern_three');">
                        <label for="payType2">月结</label>
                    </div>
                </div>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>是否有保证金：</label>
            <div class="formControls col-4">
                <div class="skin-minimal">
                    <div class="radio-box">
                        <input type="radio" id="hasBond1" name="hasBond" value="1" onclick="switchTab('#pattern_two', '#pattern_one', '#pattern_three');" checked="checked">
                        <label for="hasBond1">是</label>
                    </div>
                    <div class="radio-box">
                        <input type="radio" id="hasBond2" name="hasBond" value="0" onclick="switchTab('#pattern_three', '#pattern_two', '#pattern_one');" >
                        <label for="hasBond2">否</label>
                    </div>
                </div>
            </div>
            <div class="col-2"></div>
        </div>

        <div style="margin-left: -25px;">
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>银行信息：</label>
            <label class="form-label col-"><span class="c-red">*</span>开户名称：</label>
            <div class="formControls col-6">
                <input type="text" class="input-text" value="" placeholder="支持中文字符，长度不超过30个汉字" id="bankAccName" name="bankAccName" datatype="zh1-30" maxlength="30" errormsg="开户名称不合法！" nullmsg="开户名称不能为空"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <div class="col-2"></div>
            <label class="form-label col-"><span class="c-red">*</span>收款账号：</label>
            <div class="formControls col-6">
                <input type="text" class="input-text" value="" placeholder="支持中文字符，长度不超过30个汉字" id="bankAcdRecipient" name="bankAcdRecipient" datatype="zh1-30" maxlength="30" errormsg="收款账号不合法！" nullmsg="收款账号不能为空"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <div class="col-2"></div>
            <label class="form-label col-"><span class="c-red">*</span>开户账号：</label>
            <div class="formControls col-6">
                <input type="text" class="input-text" value="" placeholder="只支持数字，长度不超过30个数字" id="bankAccNum" name="bankAccNum" datatype="n1-30" maxlength="30" errormsg="开户账号不合法！" nullmsg="开户账号不能为空"/>
            </div>
            <div class="col-2"></div>
        </div>
        </div>
        <div class="row cl">
            <div class="col-offset-5">
                <input class="btn btn-primary radius" style="width: 100px;" type="submit" value="提交">
            </div>
        </div>
    </form>
</div>
</body>
</html>
