<%--
  Created by IntelliJ IDEA.
  User: lixin
  Date: 2016/7/14
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>添加号码</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function(){

            initPCArea("pcode", "ccode", "请选择");

            $("#addFixPhoneForm").Validform({
                tiptype: 2,
                ajaxPost: true,
                callback:function(result){
                    setTimeout(function(){
                        $.Hidemsg();
                        if(result.status == 0){
                            //刷新父级页面
//                            parent.location.replace(parent.location.href);
//                            parent.dataTables.fnDraw();
                            parent.dataTables2.fnDraw();
                            layer_close();
//                            $("#sip_phone", window.opener.document).hide();
//                            $("#fix_phone", window.opener.document).hide();
                        }
                    },2000);
                },
                datatype: {
                    "zh1-10":/^[\u4E00-\u9FA5\uf900-\ufa2d]{1,12}$/,
                    "fixphone":function(gets, obj, curform, datatype){
                        var reg = /^\d{5,30}$/;

                        if (!reg.test(gets)) {
                            return "格式不正确";
                        }

                        var code = $("#ccode option:selected").attr("data-code");
                        if (code && gets.indexOf(code) != 0) {
                            return "区号不正确";
                        }

                        $.ajax({
                            type:"post",
                            url:'<c:url value="/pubNumResPool/checkFixPhone"/>',
                            dataType:"json",
                            async:false,
                            data:{'fixphone' : gets},
                            success : function(result) {
                                if (result.code == 'ok') {
                                    r = true;
                                } else {
                                    r = result.msg;
                                }
                            }
                        });
                        return r;
                    }
                }
            });
        })
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/pubNumResPool/addFixPhone" />" method="post" class="form form-horizontal" id="addFixPhoneForm">

        <div class="row cl">
            <label class="form-label col-3">省份：</label>
            <div class="formControls col-5">
                <select id="pcode" name="pcode" class="input-text cl-1" style="height: 31px" datatype="*" nullmsg="请选择省份">
                </select>
            </div>
            <div class="col-4"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">城市：</label>
            <div class="formControls col-5">
                <select id="ccode" name="cityid" class="input-text cl-1" style="height: 31px" datatype="*" nullmsg="请选择城市">
                </select>
            </div>
            <div class="col-4"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">号码：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" placeholder="请输入号码" id="number" name="number" datatype="fixphone" maxlength="30" errormsg="号码不合法！" nullmsg="号码不能为空"/>
            </div>
            <div class="col-4"> </div>
        </div>
        <%--<div class="row cl">
            <label class="form-label col-3">认证密码：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="" placeholder="请输入最大并发数" id="pwd" name="pwd" />
            </div>
            <div class="col-2"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">SIP REALM：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="" placeholder="请输入最大并发数" id="sipRealm" name="sipRealm" />
            </div>
            <div class="col-2"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">IP:PORT：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="" placeholder="请输入最大并发数" id="ipPort" name="ipPort" />
            </div>
            <div class="col-2"> </div>
        </div>--%>


        <%--<div class="row cl">
            <label class="form-label col-3">号码：</label>
            <div class="formControls col-7">
                <textarea id="" name="" rows="5" class="textarea" datatype="checkSipNumbers"  placeholder="请输入号码  多个用换行分隔" dragonfly="true" onKeyUp="textarealength(this,1000)" nullmsg="号码不</br>能为空"></textarea>
                <input type="hidden" id="numbers" name="numbers" />
            </div>
            <div class="col-2"></div>
        </div>--%>
        <div class="row cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-primary radius" style="width: 100px;" type="submit" value="提交">
                <input class="btn btn-warning radius" style="width: 100px;" onclick="layer_close();" type="button" value="取消">
            </div>
        </div>
    </form>
</div>
</body>
</html>
