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

            $("#updateRelayGroup3").Validform({
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
    <form action="<c:url value="/relaygroup3/updateRelayGroup3" />" method="post" class="form form-horizontal" id="updateRelayGroup3">
            <input type="hidden" name="id" value="${relayGroup3.id}"/>
            <c:if test="${operationType == 'edit'}">
                <div class="row cl">
                    <label class="form-label col-4"><span class="c-red">*</span>中继群编号：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text" value="${relayGroup3.tgNum}" placeholder="请输入中继群编号" id="tgNum" name="tgNum" datatype="zh1-30|n1-30|s1-30" maxlength="30" errormsg="中继群编号不合法！" nullmsg="中继群编号不能为空"/>
                    </div>
                    <div class="col-2"></div>
                </div>

                <div class="row cl">
                    <label class="form-label col-4"><span class="c-red">*</span>中继群名：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text" value="${relayGroup3.tgName}" placeholder="请输入中继群名,最大不超过30个汉字" id="tgName" name="tgName" datatype="zh1-6|n1-6|s1-6" maxlength="6" errormsg="供应商联系人不合法！" nullmsg="供应商联系人不能为空"/>
                    </div>
                    <div class="col-2"></div>
                </div>



                <div class="row cl">
                    <label class="form-label col-4"><span class="c-red"></span>号段起始号码：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text" value="${relayGroup3.codeStart}" placeholder="最大不超过11个数字" id="codeStart" name="codeStart" datatype="empty|n9-11" maxlength="11" errormsg="号段起始号码不合法！" />
                    </div>
                    <div class="col-2"></div>
                </div>

                <div class="row cl">
                    <label class="form-label col-4"><span class="c-red"></span>号段结束号码：</label>
                    <div class="formControls col-6">
                        <input type="text" class="input-text" value="${relayGroup3.codeEnd}" placeholder="最大不超过11个数字" id="codeEnd" name="codeEnd" datatype="empty|n9-11" maxlength="11" errormsg="号段结束号码不合法！" />
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
                <label class="form-label col-4"><span class="c-red"></span>中继群编号：</label>
                <div class="formControls col-6">
                    <c:out value="${relayGroup3.tgNum}"/>
                </div>
                <div class="col-2"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-4"><span class="c-red"></span>中继群名：</label>
                <div class="formControls col-6">
                    <c:out value="${relayGroup3.tgName}"/>
                </div>
                <div class="col-2"></div>
            </div>

            <div class="row cl">
                <label class="form-label col-4"><span class="c-red"></span>号段起始号码：</label>
                <div class="formControls col-6">
                    <c:out value="${relayGroup3.codeStart}"/>
                </div>
                <div class="col-2"></div>
            </div>
            <div class="row cl">
                <label class="form-label col-4"><span class="c-red"></span>号段结束号码：</label>
                <div class="formControls col-6">
                    <c:out value="${relayGroup3.codeEnd}"/>
                </div>
                <div class="col-2"></div>
            </div>


        </c:if>
    </form>
</div>
</body>
</html>
