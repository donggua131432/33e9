<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/07/05
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <c:if test="${numType == '00'}">
        <title>号码黑名单添加</title>
    </c:if>
    <c:if test="${numType == '01'}">
        <title>号码白名单添加</title>
    </c:if>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function() {

            $("#addNumberBlack").Validform({
                tiptype: 2,
                ajaxPost: true,
                callback:function(result){
                    setTimeout(function(){
                        $.Hidemsg();
                        if(result.status == 0){
                            //刷新父级页面
                            parent.search_param = {
                                params:{
                                    "subid":"${subid}"
                                }
                            };
                            parent.dataTables.fnDraw();
                            layer_close();
                        }
                    },2000);
                },
                datatype: {
                    "checkMaskNumbers":function(gets, obj, curform, datatype){
                        var numbers = ","+gets+",", matchReg = /^[\d,]+$/, checkNum = 0, repeatNum="";
                        if (matchReg.test(gets)) {
                            var numArr = gets.split(",");
                            for(var i in numArr){
                                if(numArr[i].length < 3 || numArr[i].length > 30){
                                    checkNum = 1;
                                    break;
                                }

                                if(numbers.replace(","+numArr[i]+",",",").indexOf(","+numArr[i]+",") > -1 ){
                                    repeatNum = numArr[i];
                                    checkNum = 2;
                                    break;
                                }
                            }
                        }else{
                            return "请输入正确的号码串格式！";
                        }
                        if(checkNum == 0){
                            $("#numbers").val(gets);
                            return true;
                        }else if(checkNum == 1){
                            return "单个号码的大小为3-30位数字！";
                        }else if(checkNum == 2){
                            return repeatNum+"号码重复！";
                        }
                    }
                }
            })
        });
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/subBlackWhite/saveNubmer" />" method="post" class="form form-horizontal" id="addNumberBlack">
        <div class="row cl">
            <label class="form-label col-3">添加号码：</label>
            <div class="formControls col-7">
                <textarea rows="5" class="textarea" datatype="checkMaskNumbers"  placeholder="请输入号码多个用，分隔" dragonfly="true" onKeyUp="textarealength(this,1000)" nullmsg="号码不能为空"></textarea>
                <input type="hidden" id="numbers" name="numbers" />
                <input type="hidden" id="subid"   name="subid" value="${subid}" />
                <input type="hidden" id="numType" name="numType"  value="${numType}"/>
                <%--<p class="textarea-numberbar"><em class="textarea-length">0</em>/1000</p>--%>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">备注原因：</label>
            <div class="formControls col-7">
                <textarea id="remark" name="remark" rows="5" class="textarea" datatype="*"  maxlength="500" placeholder="请输填写备注信息" dragonfly="true" onKeyUp="textarealength(this,500)" nullmsg="备注不能为空"></textarea>
                <p class="textarea-numberbar"><em class="textarea-length">0</em>/500</p>
            </div>
            <div class="col-2"></div>
        </div>
        <div class="row cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-primary radius" style="width: 100px;" type="submit" value="提交">
            </div>
        </div>
    </form>
</div>
</body>
</html>
