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

            $("#updateSupplierManager").Validform({
                tiptype:2,
                ajaxPost:true,
                datatype: {"zh1-10":/^[\u4E00-\u9FA5\uf900-\ufa2d]{1,12}$/,
                            "empty": /^\s*$/},
                callback:function(data){
                    setTimeout(function(){
                        $.Hidemsg();
                        if(data.status == 0){
                            //刷新父级页面
                            parent.location.replace(parent.location.href);
                            layer_close();
                        }
                    },2000);
                }
            });
        });
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/supplier/updateSupplier" />" method="post" class="form form-horizontal" id="updateSupplierManager">
        <input type="hidden" name="supId" value="${supplier.supId}"/>
        <c:if test="${operationType == 'edit'}">
            <div class="row cl">
                <label class="form-label col-4">供应商名称：</label>
                <div class="formControls col-6">
                        <c:out value="${supplier.supName}"/>
                </div>
                <div class="col-2"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-4"><span class="c-red">*</span>供应商联系人：</label>
                <div class="formControls col-6">
                    <input type="text" class="input-text" value="${supplier.contacts}" placeholder="请输入供应商联系人,最大不超过30个汉字" id="contacts" name="contacts" datatype="zh1-6|n1-6|s1-6" maxlength="6" errormsg="供应商联系人不合法！" nullmsg="供应商联系人不能为空"[-/>
                </div>
                <div class="col-2"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-4"><span class="c-red">*</span>供应商地址：</label>
                <div class="formControls col-6">
                    <input type="text" class="input-text" value="${supplier.address}" placeholder="支持中英文数字,最大不超过100个汉字" id="address" name="address" datatype="zh1-100|n1-100|s1-100" maxlength="100" errormsg="供应商地址不合法！" nullmsg="供应商地址不能为空"/>
                </div>
                <div class="col-2"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-4"><span class="c-red"></span>联系方式：</label>
                <div class="formControls col-6">
                    <input type="text" class="input-text" value="${supplier.mobile}" placeholder="最大不超过11个数字" id="mobile" name="mobile" datatype="empty|n9-11" maxlength="11" errormsg="联系方式不合法！" />
                </div>
                <div class="col-2"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-4"><span class="c-red">*</span>结算方式：</label>
                <div class="formControls col-6">
                    <div class="skin-minimal">
                        <div class="radio-box">
                            <input type="radio" id="payType1" name="payType" value="00"  onclick="switchTab('#pattern_one', '#pattern_two', '#pattern_three');"  <c:if test="${supplier.payType=='00'}">checked</c:if>>
                            <label for="payType1">预付</label>
                        </div>
                        <div class="radio-box">
                            <input type="radio" id="payType2" name="payType" value="01" onclick="switchTab('#pattern_two', '#pattern_one', '#pattern_three');"  <c:if test="${supplier.payType=='01'}">checked</c:if>>
                            <label for="payType2">月结</label>
                        </div>
                    </div>
                </div>
                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-4"><span class="c-red">*</span>是否有保证金：</label>
                <div class="formControls col-6">
                    <div class="skin-minimal">
                        <div class="radio-box">
                            <input type="radio" id="hasBond1" name="hasBond" value="1" onclick="switchTab('#pattern_two', '#pattern_one', '#pattern_three');" <c:if test="${supplier.hasBond=='1'}">checked</c:if>>
                            <label for="hasBond1">是</label>
                        </div>
                        <div class="radio-box">
                            <input type="radio" id="hasBond2" name="hasBond" value="0" onclick="switchTab('#pattern_three', '#pattern_two', '#pattern_one');" <c:if test="${supplier.hasBond=='0'}">checked</c:if>>
                            <label for="hasBond2">否</label>
                        </div>
                    </div>
                </div>
                <div class="col-2"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-3"><span class="c-red">*</span>银行信息：</label>
                <label class="form-label col-"><span class="c-red">*</span>开户名称：</label>
                <div class="formControls col-5">
                    <input type="text" class="input-text" value="${supplier.bankAccName}" placeholder="支持中文字符，长度不超过30个汉字" id="bankAccName" name="bankAccName" datatype="zh1-30" maxlength="30" errormsg="开户名称不合法！" nullmsg="开户名称不能为空"/>
                </div>
                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <div class="col-3"></div>
                <label class="form-label col-"><span class="c-red">*</span>收款账号：</label>
                <div class="formControls col-5">
                    <input type="text" class="input-text" value="${supplier.bankAcdRecipient}" placeholder="支持中文字符，长度不超过30个汉字" id="bankAcdRecipient" name="bankAcdRecipient" datatype="zh1-30" maxlength="30" errormsg="收款账号不合法！" nullmsg="收款账号不能为空"/>
                </div>
                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <div class="col-3"></div>
                <label class="form-label col-"><span class="c-red">*</span>开户账号：</label>
                <div class="formControls col-5">
                    <input type="text" class="input-text" value="${supplier.bankAccNum}" placeholder="只支持数字，长度不超过30个数字" id="bankAccNum" name="bankAccNum" datatype="n1-30" maxlength="30" errormsg="开户账号不合法！" nullmsg="开户账号不能为空"/>
                </div>
                <div class="col-2"></div>
            </div>

            <div class="row cl">
                <div class="col-offset-4">
                    <input class="btn btn-primary radius" style="width: 100px;" type="submit" value="提交">
                </div>
            </div>
        </c:if>







    <%--查看页面内容展现--%>
        <c:if test="${operationType == 'view'}">
            <div class="row cl">
                <label class="form-label col-4"><span class="c-red"></span>供应商名称：</label>
                <div class="formControls col-6">
                    <c:out value="${supplier.supName}"/>
                </div>
                <div class="col-2"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-4"><span class="c-red"></span>供应商联系人：</label>
                <div class="formControls col-6">
                    <c:out value="${supplier.contacts}"/>
                </div>
                <div class="col-2"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-4"><span class="c-red"></span>供应商地址：</label>
                <div class="formControls col-6">
                    <c:out value="${supplier.address}"/>
                </div>
                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-4"><span class="c-red"></span>联系联系方式：</label>
                <div class="formControls col-6">
                    <c:out value="${supplier.mobile}"/>
                </div>
                <div class="col-2"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-4"><span class="c-red"></span>结算方式：</label>
                <div class="formControls col-6">
                    <div class="skin-minimal">
                        <div class="radio-box">
                            <input type="radio"  id="payType2_1"  name="payType" value="0" readonly="readonly" onclick="switchTab('#pattern_one', '#pattern_two', '#pattern_three');" <c:if test="${supplier.payType=='00'}">checked</c:if>>
                            <label for="payType2_1">预付</label>
                        </div>
                        <div class="radio-box">
                            <input type="radio"  id="payType2_2" name="payType" value="1" onclick="switchTab('#pattern_two', '#pattern_one', '#pattern_three');" readonly <c:if test="${supplier.payType=='01'}">checked</c:if>>
                            <label for="payType2_2">月结</label>
                        </div>
                    </div>
                </div>
                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-4"><span class="c-red"></span>是否有保证金：</label>
                <div class="formControls col-6">
                    <div class="skin-minimal">
                        <div class="radio-box">
                            <input type="radio"  id="hasBond1_1" name="hasBond" value="1" onclick="switchTab('#pattern_two', '#pattern_one', '#pattern_three');" <c:if test="${supplier.hasBond=='1'}">checked</c:if>>
                            <label for="hasBond1_1">是</label>
                        </div>
                        <div class="radio-box">
                            <input type="radio" id="hasBond2_2" name="hasBond" value="2" onclick="switchTab('#pattern_three', '#pattern_two', '#pattern_one');"  <c:if test="${supplier.hasBond=='0'}">checked</c:if>>
                            <label for="hasBond2_2">否</label>
                        </div>
                    </div>
                </div>
                <div class="col-2"></div>
            </div>


            <div class="row cl">
                <label class="form-label col-3"><span class="c-red"></span>银行信息：</label>
                <label class="form-label col-"><span class="c-red"></span>开户名称：</label>
                <div class="formControls col-5">
                    <c:out value="${supplier.bankAccName}"/>
                </div>
                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <div class="col-3"></div>
                <label class="form-label col-"><span class="c-red"></span>收款账号：</label>
                <div class="formControls col-5">
                    <c:out value="${supplier.bankAcdRecipient}"/>
                </div>
                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <div class="col-3"></div>
                <label class="form-label col-"><span class="c-red"></span>开户账号：</label>
                <div class="formControls col-5">
                    <c:out value="${supplier.bankAccNum}"/>
                </div>
                <div class="col-2"></div>
            </div>

        </c:if>
    </form>
</div>
</body>
</html>
