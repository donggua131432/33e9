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
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
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


            $("#addCityManager").Validform({
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
            <%--cascadeArea("province","city","<c:url value='/dicdata/pid'/>");--%>


            $("#companyName").select2({
                placeholder: "请选择客户名称",
                ajax: {
                    url: "<c:url value="/userAdmin/getCompanyNameAndSidByPage"/>",
                    dataType: 'json',
                    delay: 250,
                    data: function (params) {
                        return {
                            params: {name:params.term,busType:'01'}, // search term busType ：1 rest用户 2 智能云调度用户
                            page: params.page
                        };
                    },
                    processResults: function (data, params) {
                        params.page = params.page || 1;

                        var items = [];

                        $.each(data.rows,function(i,v){
                            items.push({id:v.sid, text:v.name});
                        });

                        return {
                            results: items,
                            pagination: {
                                more: (params.page * 30) < data.total
                            }
                        };
                    },
                    cache: true
                }
            });

            $("#companyName").on("change",function(){
                $("#sid").val(this.value);
            });
        });
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/cityMgr/saveCity" />" method="post" class="form form-horizontal" id="addCityManager">
        <br/>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>城市名称：</label>
            <div class="formControls col-8">
                <input type="text" class="input-text" value="" placeholder="请输入城市名称" id="cname" name="cname" datatype="zh1-12" maxlength="10" errormsg="城市名称不合法！" nullmsg="城市名称不能为空"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2">所属省份：</label>
            <div class="formControls col-8">
                <select id="pcode" name="pcode" class="select" style="width:200px;height: 31px">
                    <c:forEach items="${provinceDic}" var="province">
                        <option  id="${province.id}" value="${province.pcode}">${province.pname}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>城市区号：</label>
            <div class="formControls col-8">
                <input type="text" class="input-text" value="" placeholder="请输入城市区号3-8位数字" id="areaCode" name="areaCode" maxlength="8" datatype="n3-8&_ajax" data-ajax="{'url':'<c:url value="/cityMgr/checkAreaCodeUnique"/>',msg:'城市区号不存在！'}" nullmsg="城市区号不能为空"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>城市编号：</label>
            <div class="formControls col-8">
                <input type="text" class="input-text" value="" placeholder="1-10位数字，全网唯一，不可重复。建议填写1000以上的数字。" id="ccode" name="ccode" datatype="n4-10" maxlength="10" errormsg="自定义编号建议从1000开始！" nullmsg="城市编号不能为空"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>客户名称：</label>
            <div class="formControls col-8">
                <select class="input-text" id="companyName" name="companyName" datatype="*" nullmsg="客户名称不能为空"></select>
            </div>
            <div class="col-2"></div>
        </div>
        <input type="hidden" id="sid" name="sid"/>

        <div class="row cl">
            <div class="col-offset-4">
                <input class="btn btn-primary radius" style="width: 100px;" type="submit" value="提交">
            </div>
        </div>
    </form>
</div>
</body>
</html>
