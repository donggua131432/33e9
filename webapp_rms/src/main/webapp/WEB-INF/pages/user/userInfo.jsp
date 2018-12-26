<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/2/24
  Time: 10:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>个人资料</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        var sixToSixteenMatch = /^[\w\W]{6,16}$/,/** 匹配6到16位任意数 **/
             phoneMatch = /^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}|17[0-9]{9}$/;  /** 匹配手机号 **/
        $(function(){
            $("form").Validform({
                tiptype:2,
                ajaxPost:true
            });
        });

        //修改信息
        function updateUserInfo(dialogId){
            var submitStatus = true;
            if(dialogId == 'updatePhoneDialog'){
                if($('#phone').val().trim().match(phoneMatch) == null) submitStatus = false;
                if(checkUserInfo(document.getElementById("phone")) == 1) submitStatus =false;
            }
            if(dialogId == 'updatePwdDialog'){
                $("form:eq(1)").submit();
                if($('#oldPassword').val().trim().match(sixToSixteenMatch) == null) submitStatus = false;
                if($('#password').val().trim().match(sixToSixteenMatch) == null) submitStatus = false;
                if($('#password').val().trim() != $('#password2').val().trim()) submitStatus = false;
                if(checkUserInfo(document.getElementById("oldPassword"))== 1) submitStatus =false;
            }
            if(submitStatus == true){
               $.ajax({
                    type:"post",
                    url:"<c:url value='/user/updateUserInfo'/>",
                    dataType:"json",
                    data:"id=${userInfo.id}&"+$("#"+dialogId).find('form').serialize(),
                    success : function(data) {
                        layer.msg(data.info);
                        $("#"+dialogId).modal('hide');
                        location.replace(location.href);
                    },
                    error: function(){
                        layer.msg("数据请求失败！");
                    }
                })
            }
        }

        //检查原始密码
        function checkUserInfo(obj){
            var data={}, submitStatus = true, resultStatus = 1;
            if($(obj).attr("name") == "phone"){
                data = {"phone":$(obj).val().trim()};
                if($(obj).val().trim().match(phoneMatch) == null) submitStatus = false;
            }else if($(obj).attr("name") == "oldPassword"){
                data = {"username":"${userInfo.username}","password":$(obj).val()};
                if($(obj).val().match(sixToSixteenMatch) == null) submitStatus = false;
            }
            if(submitStatus == true){
                $.ajax({
                    type:"post",
                    url:"<c:url value='/user/checkUserInfo'/>",
                    dataType:"json",
                    async:false,
                    data:data,
                    success : function(data) {
                        var tipClass = (data.status == 0) ? tipClass = "Validform_checktip Validform_right" : tipClass = "Validform_checktip Validform_wrong";
                        $(obj).parent().next().html('<span class="'+tipClass+'">'+data.info+'</span>');
                        resultStatus = data.status;
                    },
                    error: function(data){
                        $(obj).parent().next().html('<span class="Validform_checktip Validform_wrong">'+data.info+'</span>');
                        resultStatus = data.status;
                    }
                })
            }
            return resultStatus;
        }
    </script>
</head>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="panel panel-default">
        <div class="panel-header">用户基本信息</div>
        <div class="panel-body">
            <div class="form form-horizontal" style="padding-bottom: 30px;">
                <div class="row cl">
                    <label class="form-label col-2">管理员身份：</label>
                    <div class="formControls col-7">
                        ${userInfo.roleName}
                    </div>
                    <div class="col-2"></div>
                </div>
                <div class="row cl">
                    <label class="form-label col-2">管理员账号：</label>
                    <div class="formControls col-7">
                        ${userInfo.username}
                    </div>
                    <div class="col-2"></div>
                </div>
                <div class="row cl">
                    <label class="form-label col-2">真实姓名：</label>
                    <div class="formControls col-7">
                        ${userInfo.nick}
                    </div>
                    <div class="col-2"></div>
                </div>
                <div class="row cl">
                    <label class="form-label col-2">联系方式：</label>
                    <div class="formControls col-7">
                        ${userInfo.phone}
                        <label style="margin-left: 85px;"><a data-toggle="modal" href="#updatePhoneDialog" class="btn btn-primary size-MINI radius">更改</a></label>
                        </br><span style="color: #999; font-size: 11px;">用户接受安全，业务警报，重要变更等通知</span>
                    </div>
                    <div class="col-2"></div>
                </div>
                <div class="row cl">
                    <label class="form-label col-2">密码：</label>
                    <div class="formControls col-7">
                        ************
                        <label style="margin-left: 100px;"><a data-toggle="modal" href="#updatePwdDialog" class="btn btn-primary size-MINI radius">更改</a></label>
                    </div>
                    <div class="col-2"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<!- 修改联系方式 -->
<div id="updatePhoneDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改联系方式</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">手机号码：</label>
                    <div class="formControls col-6">
                        <input type="text" placeholder="请输入手机号码" class="input-text radius size-S" value="${userInfo.phone}" onblur="checkUserInfo(this);" id="phone" name="phone"  datatype="m" errormsg="手机号格式错误" nullmsg="手机不能为空"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateUserInfo('updatePhoneDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>


<!- 修改密码 -->
<div id="updatePwdDialog" class="modal hide fade">
    <div class="modal-header">
        <h4>修改密码</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form action="">
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">原始密码：</label>
                    <div class="formControls col-6">
                        <input type="password" placeholder="请输入原始密码" class="input-text radius size-S" id="oldPassword" name="oldPassword" onblur="checkUserInfo(this);" datatype="*6-20" nullmsg="密码不能为空"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">新密码：</label>
                    <div class="formControls col-6">
                        <input type="password" placeholder="请输入新密码" class="input-text radius size-S"  id="password" name="password" datatype="*6-20" nullmsg="密码不能为空"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-2">确认密码：</label>
                    <div class="formControls col-6">
                        <input type="password" placeholder="请确认新密码" class="input-text radius size-S" errormsg="您两次输入的新密码不一致！" datatype="*" nullmsg="请再输入一次新密码！" recheck="password" id="password2"/>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="updateUserInfo('updatePwdDialog')">确定</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>
</body>
</html>

