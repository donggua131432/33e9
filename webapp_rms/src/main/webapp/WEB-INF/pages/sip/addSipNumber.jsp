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
    <title>添加sip业务号码</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
        $(function(){

            $("#addSipNumberForm").Validform({
                tiptype: 2,
                ajaxPost: true,
                callback:function(result){
                    setTimeout(function(){
                        $.Hidemsg();
                        if(result.status == 0){
                            //刷新父级页面
                            parent.location.replace(parent.location.href);
//                            parent.dataTables.fnDraw();
                            layer_close();
                        }
                    },2000);
                },
                datatype: {
                    "checkSipNumbers":function(gets, obj, curform, datatype){
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
    <form action="<c:url value="/sipNumPool/addNumbers" />" method="post" class="form form-horizontal" id="addSipNumberForm">

        <input type="hidden" name="appid" value="${appid}"/>
        <div class="row cl">
            <label class="form-label col-3">最大并发数：</label>
            <div class="formControls col-7">
                <input type="text" class="input-text" value="" placeholder="请输入最大并发数" id="maxConcurrent" name="maxConcurrent" datatype="_ajax" data-ajax="{_regex:{type:'maxConcurrent',msg:'格式不正确'}}" maxlength="5"  />
            </div>
            <div class="col-2"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">号码：</label>
            <div class="formControls col-7">
                <textarea id="" name="" rows="5" class="textarea" datatype="checkSipNumbers"  placeholder="请输入号码  多个用换行分隔" dragonfly="true" onKeyUp="textarealength(this,1000)" nullmsg="号码不</br>能为空"></textarea>
                <input type="hidden" id="numbers" name="numbers" />
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
