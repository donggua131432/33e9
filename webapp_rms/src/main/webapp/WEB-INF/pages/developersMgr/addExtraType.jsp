<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/8/22
  Time: 10:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>开通增值服务</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>
<div class="pd-20">
    <span class="text-l">增值服务列表</span><br>
    <div class="text-c">

        <table class="table table-border table-bordered">
            <thead  class="text-c">
            <tr >
                <th>增值服务名称</th>
                <th>开通时间</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody  class="text-c">
            <tr>
                <td>B路录音</td>
                <td><fmt:formatDate value="${extraType.createDate}" pattern="yyyy-MM-dd"/></td>
                <c:if test="${extraType.sid eq null}">
                    <td></td>
                    <td><button type="button" class="btn btn-success radius" id="open" name="open">开启</button></td>
                </c:if>
                <c:if test="${extraType.sid != null}">
                    <td>已开通</td>
                    <td><button type="button" class="btn btn-warning radius" id="close" name="close"> 关闭</button></td>
                </c:if>
            </tr>
            <tr>
                <td>B路手机显号免审</td>
                <td><fmt:formatDate value="${extraType3.createDate}" pattern="yyyy-MM-dd"/></td>
                <c:if test="${extraType3.sid eq null}">
                    <td></td>
                    <td><button type="button" class="btn btn-success radius" id="open1" name="open1">开启</button></td>
                </c:if>
                <c:if test="${extraType3.sid != null}">
                    <td>已开通</td>
                    <td><button type="button" class="btn btn-warning radius" id="close1" name="close1"> 关闭</button></td>
                </c:if>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<script  type="text/javascript">


    //开通B路计费增值服务
    $('#open').click(function() {
        var sid = '${sid}';
        layer.confirm('是否确定开启该服务？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/extraType/openBtapes'/>",
                data:{'sid':sid},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data.code=='00') {
                        layer.msg(data.info, {icon: 1});
                        location.replace(location.href);
                    }
                    if(data.code=='01') {
                        layer.alert("对应的Account ID不存在", {icon: 2});
                    }
                    else{
                        layer.alert("正在处理...", {icon: 1});
                    }

                },
                error  : function(data){
                    alert("出现异常");
                }
            });
        });
    });

    //关闭B路计费增值服务
    $('#close').click(function() {
        var sid = '${sid}';
        layer.confirm('是否确定关闭该服务？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/extraType/closeBtapes'/>",
                data:{'sid':sid},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data.code=='00') {
                        layer.msg(data.info, {icon: 1});
                        location.replace(location.href);
                    }
                    if(data.code=='01') {
                        layer.alert("对应的Account ID不存在", {icon: 2});
                    }
                    else{
                        layer.alert("正在处理...", {icon: 1});
                    }

                },
                error  : function(data){
                    alert("出现异常");
                }
            });
        });
    });


    //开通B路手机显号免审
    $('#open1').click(function() {
        var sid = '${sid}';
        layer.confirm('是否确定开启该服务？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/extraType/openBphone'/>",
                data:{'sid':sid},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data.code=='00') {
                        layer.msg(data.info, {icon: 1});
                        location.replace(location.href);
                    }
                    if(data.code=='01') {
                        layer.alert("对应的Account ID不存在", {icon: 2});
                    }
                    else{
                        layer.alert("正在处理...", {icon: 1});
                    }

                },
                error  : function(data){
                    alert("出现异常");
                }
            });
        });
    });

    //关闭B路手机显号免审
    $('#close1').click(function() {
        var sid = '${sid}';
        layer.confirm('是否确定关闭该服务？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/extraType/closeBphone'/>",
                data:{'sid':sid},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data.code=='00') {
                        layer.alert("修改成功", {icon: 1});
                        location.replace(location.href);
                    }
                    if(data.code=='01') {
                        layer.alert("对应的Account ID不存在", {icon: 2});
                    }
                    else{
                        layer.alert("正在处理...", {icon: 1});
                    }

                },
                error  : function(data){
                    alert("出现异常");
                }
            });
        });
    });


</script>
</body>
</html>
