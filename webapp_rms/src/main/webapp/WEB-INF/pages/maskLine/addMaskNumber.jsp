<%--
  Created by IntelliJ IDEA.
  User: dukai
  Date: 2016/6/1
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>添加隐私号</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
        $(function(){
            $("#cityid").on("change",function(){
                if($(this).val() != ""){
                    $("#areaCodeSpan").text($(this).find("option:selected").attr("areaCode")).removeAttr("style");
                    $("#areaCode").val($(this).find("option:selected").attr("areaCode"));
                }else{
                    $("#areaCodeSpan").text("区号自动匹配").css("color", "#cccccc");
                    $("#areaCode").val("");
                }
            });

            $("#addMaskNumberForm").Validform({
                tiptype: 2,
                ajaxPost: true,
                callback:function(result){
                    setTimeout(function(){
                        $.Hidemsg();
                        if(result.status == 0){
                            //刷新父级页面
                            parent.search_param = {
                                params:{
                                    "appid":"${appid}"
                                }
                            };
                            parent.dataTables.fnDraw();
                            layer_close();
                        }
                    },2000);
                },
                datatype: {
                    "checkMaskNumbers":function(gets, obj, curform, datatype){
                        var numbers = gets.trim().replace(/[\r\n]/g,","), matchReg = /^[\d,]+$/, checkNum = 0;
                        if (matchReg.test(numbers)) {
                            var numArr = numbers.split(",");
                            for(var i in numArr){
                                if(numArr[i].length < 5 || numArr[i].length > 30){
                                    checkNum = 1;
                                    break;
                                }
                            }
                        }else{
                            return "请输入正确的号码串格式！";
                        }
                        if(checkNum == 0){
                            $("#numbers").val(numbers);
                            return true;
                        }else if(checkNum == 1){
                            return "单个号码的大小为5-30位数字！";
                        }

                    }
                }
            })
        })
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/maskNum/addMaskNumbers" />" method="post" class="form form-horizontal" id="addMaskNumberForm">
        <input type="hidden" name="sid" value="${sid}"/>
        <input type="hidden" name="appid" value="${appid}"/>
        <input type="hidden" name="uid" value="${uid}"/>
        <div class="row cl">
            <label class="form-label col-3">城市：</label>
            <div class="formControls col-5">
                <span class="select-box" style="width:200px;">
                    <select id="cityid" name="cityid" class="select" datatype="*" nullmsg="城市不能为空">
                        <option value="">请选择城市</option>
                        <c:forEach items="${cityList}" var="city">
                            <option value="${city.id}" areaCode="${city.areaCode}">${city.city}</option>
                        </c:forEach>
                    </select>
				</span>
            </div>
            <div class="col-4"></div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">区号：</label>
            <div class="formControls col-5">
                <span id="areaCodeSpan" style="color: #cccccc">区号自动匹配</span>
                <input type="hidden" id="areaCode" name="areaCode" />
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">号码：</label>
            <div class="formControls col-7">
                <textarea id="" name="" rows="5" class="textarea" datatype="checkMaskNumbers"  placeholder="请输入号码  多个用换行分隔" dragonfly="true" onKeyUp="textarealength(this,1000)" nullmsg="号码不</br>能为空"></textarea>
                <input type="hidden" id="numbers" name="numbers" />
                <%--<p class="textarea-numberbar"><em class="textarea-length">0</em>/1000</p>--%>
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
