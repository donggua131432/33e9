<%--
   Created by IntelliJ IDEA.
  User: lixin
  Date: 2016/7/14
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>编辑SIP应用</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function(){
            var validform = $("#route").Validform({
                tiptype:2,
                ajaxPost:true,
                datatype:{
                    "routeCode":/^0[0-9]{3}$/,
                    "routeCodeRule":/^0/,
                    "empty": /^\s*$/
                } ,
                callback:function(data){
                    $.Showmsg(data.msg);
                    setTimeout(function(){
                        $.Hidemsg();
                        if(data.code == "ok"){
                            //刷新父级页面
                            parent.location.replace(parent.location.href);
                            //关闭当前窗口
//                            layer_close();
                        }
                    },2000);
                }
            });
            $('.skin-minimal input').iCheck({
                checkboxClass: 'icheckbox-blue',
                radioClass: 'iradio-blue',
                increaseArea: '20%'
            });
            $('.skin-minimal input').on('ifChecked', function(event){
                //如果是选中，点击后则为不选中
                if($(this).attr("id")=="isRoute1"){
                    console.info($(this).attr("id"));
                    $("#routeCodeDiv").css("display","block");
//                    $("#routeCode").show();
                    $.ajax({
                        type:"post",
                        url:"<c:url value='/sip/updateRoute'/>",
                        dataType:"json",
                        data:{"id":$('#id').val(),"isRoute":$('#isRoute1').val(),"routeCode":$('#routeCode').val()},
                        success:function(data){
                                layer.msg("使用专用路由", {icon: 1});
//                                layer.tips(data.info,'#isRoute1',{icon: 1})

                        }
                    });
                }else{
                    $('#routeCode').val("");
                    $("#routeCodeDiv").hide();
                    $.ajax({
                        type:"post",
                        url:"<c:url value='/sip/updateRoute'/>",
                        dataType:"json",
                        data:{"id":$('#id').val(),"isRoute":$('#isRoute2').val()},
                        success:function(data){
                                layer.msg("不使用专用路由", {icon: 1});
//                                layer.tips(data.info,'#isRoute2',{icon: 1})
                        }
                    });
                }
            });
            $('.skin-minimal input').on('ifUnchecked', function(event){
                //如果不选中，点击后则为选中
            });
            <c:if test="${appInfo.isRoute=='01'}">
                $("#isRoute1").iCheck('check')
            </c:if>
            $("#routeCode").on("input",function(){
                /*$.ajax({
                    type:"post",
                    <%--url:"<c:url value='/openi/updateIsRoute'/>",--%>
                    dataType:"json",
                    data:{"id":$('#id').val(),"routeCode":$('#routeCode').val()},
                    success:function(data){
                        layer.msg("APP专用路由编码设置成功", {icon: 1});
//                        layer.tips(data.info,'#routeCode',{icon: 1})
                    }
                });*/
                //ajaxPost(flag,sync,url)
                validform.submitForm(false,"<c:url value='/sip/updateRoute'/>");
            });
        });
    </script>
</head>
    <body>
        <form id="route">
            <div class="pd-20">
                <div class="form form-horizontal">
                    <input type="hidden"  id="id" name="id" value="${appInfo.id}"/>
                    <div class="row cl">
                        <label class="form-label col-4">Account ID：</label>
                        <div class="formControls col-8">
                            <span>${appInfo.sid}</span>
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-4">客户名称：</label>
                        <div class="formControls col-8">
                            <span>${appInfo.companyName}</span>
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-4">APP ID：</label>
                        <div class="formControls col-8">
                            <span>${appInfo.appid}</span>
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-4">应用名称：</label>
                        <div class="formControls col-8">
                            <span>${appInfo.appName}</span>
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-4">最大并发数：</label>
                        <div class="formControls col-8">
                            <span>${appInfo.maxConcurrent}</span>
                        </div>
                    </div>

                    <div class="row cl">
                        <div class="col-3"></div>
                        <div class="formControls col-4">
                            <div class="skin-minimal">
                                <div class="radio-box">
                                    <input type="radio" id="isRoute2" name="isRoute" value="00"   checked="checked">
                                    <label for="isRoute2">不使用专用路由</label>
                                </div>
                            </div>
                        </div>
                        <div class="col-5"></div>
                    </div>

                    <div class="row cl">
                        <div class="col-3"></div>
                        <div class="formControls col-4">
                            <div class="skin-minimal">
                                <div class="radio-box">
                                    <input type="radio" id="isRoute1" name="isRoute" value="01" >
                                    <label for="isRoute1">使用专用路由</label>
                                </div>
                            </div>
                        </div>
                        <div class="col-5"></div>
                    </div>

                    <div class="row cl" id="routeCodeDiv" style="display: none">
                       <%-- <label class="form-label col-2" for="routeCode"><span class="c-red"></span>APP专用路由编码：</label>--%>
                           <div class="col-3"></div>
                        <div class="formControls col-3">
                            <input type="text" datatype="routeCodeRule,n1-4|empty" errormsg="路由编码：四位数字（首位可为0），如不够四位，前面补0！" title="路由编码：四位数字（首位可为0），如不够四位，前面补0" class="input-text" value="${appInfo.routeCode}" placeholder="APP专用路由编码" id="routeCode" name="routeCode"   maxlength="4"/>
                        </div>
                       <div class="col-6"></div>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>
