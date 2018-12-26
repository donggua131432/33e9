<%--
  Created by IntelliJ IDEA.
  User: dukai
  Date: 2016/6/1
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>添加语音通知费率</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function(){
            $("#checkForever").click(function(){
                if($(this).is(":checked") == false){
                    $("#startDate,#endDate").removeAttr("disabled");
                    $(this).attr("checked",false);
                    $("#forever").val(0);
                }else{
                    $("#startDate,#endDate").attr("disabled","disabled").val("");
                    $(this).attr("checked",true);
                    $("#dateMsg").html('<span class="Validform_checktip Validform_right"></span>');
                    $("#forever").val(1);
                }
            })

            $("#addVoiceRateForm").Validform({
                tiptype:2,
                ajaxPost:true,
                callback:function(data){
                    setTimeout(function(){
                        $.Hidemsg();
                        if(data.status == 0){
                            //刷新当前页面
                            location.replace(location.href);

                            //刷新父级页面
                            //parent.location.replace(parent.location.href);
                            //layer_close();
                        }
                    },2000);
                },
                datatype: {
                    "f1_2-2" : /^((3000)|((3000)[.]{1}([0]{0,1})?)|([0-2]?[0-9]{1,3})|(([0-2]?[0-9]{1,3})[.]{1}([0-9]{0,1})?))$/,
                    "dateCheck": function (gets, obj, curform, datatype){
                        if($("#checkForever").is(":checked")){
                            $("#startDate").removeClass("Validform_error");
                            $("#endDate").removeClass("Validform_error");
                            return true;
                        }else{
                            if($("#startDate").val() != '' &&  $("#endDate").val() != ''){
                                $("#startDate").removeClass("Validform_error");
                                $("#endDate").removeClass("Validform_error");
                                return true;
                            }else{
                                $("#dateMsg").html('<span class="Validform_checktip Validform_wrong">请填写时间或选择永久有效</span>');
                                return false;
                            }
                        }
                    },
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
                                        $("#name").text(data.userAdminResult.authenCompany.name).removeAttr("style");
                                        checkStatus = true;
                                    }
                                }
                            });
                        }else{
                            $("#name").text("客户名称自动匹配").css("color","#cccccc");
                            return "开发者ID不合法！";
                        }
                        if(checkStatus == true){
                            return true;
                        }else{
                            $("#name").text("客户名称自动匹配").css("color","#cccccc");
                            return "开发者ID不存在！";
                        }
                    }
                }
            });
        });

        function closeFeeRateDialog(){
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
        }

        function discountPrice(obj){
            var discount =  numberResult($(obj).val());
            if(discount >= 0){
                if($(obj).attr("id") == "per60Discount"){
                    $("input[name = 'per60Discount']").val($.calcEval(discount+"*10"));
                    $("#per60DiscountPrice").text($.calcEval("${voiceRate.per60}*"+discount+"/100").toFixed(4));
                }
            }
        }

    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value='/voiceRate/submitVoiceRate'/>" method="post" class="form form-horizontal" id="addVoiceRateForm">
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>Account ID：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="" placeholder="请输入开发者Account ID" id="sid" name="sid" datatype="checkedCallSid" errormsg="Account ID不合法！" nullmsg="Account ID不能为空"/>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>客户名称：</label>
            <div class="formControls col-7">
                <span id="name" style="color: #cccccc">客户名称自动匹配</span>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>有效时间：</label>
            <div class="formControls col-7">
                <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{\'%y-%M-%d\'}',maxDate:'#F{$dp.$D(\'endDate\')}'})" readonly="readonly" id="startDate" name="startDate" datatype="dateCheck"  style="width:186px;" class="input-text Wdate" nullmsg="请填写时间或选择永久有效"/>-
                <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})" readonly="readonly" id="endDate" name="endDate" datatype="dateCheck" style="width:186px;" class="input-text Wdate" nullmsg="请填写时间或选择永久有效"/>
                <div>
                    <input type="checkbox" id="checkForever" datatype="dateCheck" nullmsg="请填写时间或选择永久有效"/>
                    <label class="c-666" for="checkForever">永久有效</label>
                    <input type="hidden" id="forever" name="forever"/>
                </div>
            </div>
            <div id="dateMsg" class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2">费用项目：</label>
            <div class="formControls col-10">
                <table style="margin-top: -10px;">
                    <tr>
                        <td width="50%">
                            <div class="row cl">
                                <div class="formControls col-10">
                                    <dl class="permission-list">
                                        <dd>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">标准价格：</label></dt>
                                                <dd class="form-text"><label>${voiceRate.per60}</label>元/分钟</dd>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折扣率：</label></dt>
                                                <dd><label class=""><input type="text" class="input-text radius size-S" style="width: 100px;" maxlength="6" datatype="f1_2-2" value="100" id="per60Discount" onkeyup="discountPrice(this);" onpaste="return false" nullmsg="折扣率不允许为空" errormsg="请输入小于等于3000且精度不超过1位的小数！"/>%</label></dd>
                                                <input type="hidden" name="per60Discount" value="1000"/>
                                            </dl>
                                            <dl class="cl permission-list2">
                                                <dt><label class="form-label">折后价：</label></dt>
                                                <dd class="form-text"><label id="per60DiscountPrice">${voiceRate.per60}</label>元/分钟</dd>
                                            </dl>
                                        </dd>
                                    </dl>
                                </div>
                                <div class="col-2"></div>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="row cl">
            <div class="col-9 col-offset-2">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset"  onclick="closeFeeRateDialog();"  value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>
