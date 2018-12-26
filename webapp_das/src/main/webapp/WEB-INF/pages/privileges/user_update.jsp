<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/2/26
  Time: 13:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/css/H-ui.min.css"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <title>添加用户</title>
</head>
<script type="text/javascript">
    $(function(){

        $('.skin-minimal input').on('ifChecked', function(event){
            //如果是选中，点击后则为不选中
            $("#sysType").val($("#sysType").val()+$(this).val()+",");
        });
        $('.skin-minimal input').on('ifUnchecked', function(event){
            //如果不选中，点击后则为选中
            $("#sysType").val($("#sysType").val().replace($(this).val()+",",""));
        });

        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            increaseArea: '20%'
        });

        $('.skin-minimal1 input').iCheck({
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });

        $("input[value='${userInfo.sex}']").iCheck('check');
        $("select[name='departmentId']").val(${userInfo.departmentId});

        $("#form-member-update").Validform({
            tiptype:2,
            ajaxPost:true,
            callback:function(data){
                $.Showmsg(data.msg);
                setTimeout(function(){
                    $.Hidemsg();
                    if(data.status == 0){
                        //刷新父级页面
                        parent.location.replace(parent.location.href);
                        //关闭当前窗口
                        layer_close();
                    }
                },2000);
            },
            datatype:{
                "username":function(gets,obj,curform,regxp) {
                    //参数gets是获取到的表单元素值，obj为当前表单元素，curform为当前验证的表单，regxp['*6-16']为内置的一些正则表达式的引用;
                    var reg6_32 = /^[\w\W]{6,32}$/, returResult = "请输入6-32位字符";
                    if(reg6_32.test(gets)){
                        $.ajax({
                            type: "post",
                            url: "<c:url value='/user/checkUserInfo'/>",
                            dataType: "json",
                            async:false,
                            data: {'username': gets, 'id': '${userInfo.id}'},
                            success: function (data) {
                                if (data.status == 0) {
                                    returResult = true;
                                } else {
                                    returResult = data.info;
                                }
                            },
                            error: function () {
                                returResult = "数据请求异常";
                            }
                        })
                    }
                    return returResult;
                }
            }
        });

        //每次点击清除单选按钮组的样式
        $("input[type='radio']").bind("click",function(){
            $("input[type='radio']").parent().removeClass("checked");
        })

        //设置复选框的值
        $(".skin-minimal input").each(function(){
            if("${userInfo.sysType}".indexOf($(this).val()) >= 0){
                $(this).iCheck('check');
            }
        });

    })
</script>
<body>
<div class="pd-20">
    <form method="post" action="<c:url value='/user/updateUserInfo'/>" class="form form-horizontal" id="form-member-update">
        <input type="hidden" name="id" value="${userInfo.id}"/>
        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>真实姓名：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="${userInfo.nick}" placeholder="请输入真实姓名" id="nick" name="nick" datatype="*2-32" nullmsg="真实姓名不能为空！"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>性别：</label>
            <div class="formControls col-5 skin-minimal1">
                <div class="radio-box">
                    <input type="radio" id="sex-1" name="sex" datatype="*" value="男" nullmsg="请选择性别！">
                    <label for="sex-1">男</label>
                </div>
                <div class="radio-box">
                    <input type="radio" id="sex-2" name="sex" value="女">
                    <label for="sex-2">女</label>
                </div>
            </div>
            <div class="col-4"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>所属部门：</label>
            <div class="formControls col-5">
                <span class="select-box">
                    <select class="select" size="1" id="departmentId" name="departmentId">
                        <option value="12">三三得玖</option>
                        <option value="123">大众通信</option>
                    </select>
                </span>

            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>登录名：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="${userInfo.username}" placeholder="请输入登录名" id="username" name="username" datatype="username" nullmsg="登录名不能为空！">
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>设置密码：</label>
            <div class="formControls col-5">
                <input type="password" class="input-text" placeholder="请设置密码" id="password" name="password" datatype="*6-20" ignore="ignore"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>确认密码：</label>
            <div class="formControls col-5">
                <input type="password" class="input-text" placeholder="请确认密码" errormsg="您两次输入的新密码不一致！" datatype="*" ignore="ignore" recheck="password" id="repassword"/>
            </div>
            <div class="col-4"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>设置手机：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" placeholder="请输入手机号" value="${userInfo.phone}" datatype="m" id="phone" name="phone" errormsg="手机号格式不正确！" nullmsg="手机号不能为空！"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3"><span class="c-red">*</span>设置邮箱：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" placeholder="@" value="${userInfo.email}" datatype="e" id="email" name="email" nullmsg="邮箱不能为空！"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset"  onclick="layer_close();"  value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>
