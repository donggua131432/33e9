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
    <title>业务列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
</head>
<body>
<div class="pd-20">
    <span class="text-l">业务列表</span><br>
    <div class="text-c">

    <table class="table table-border table-bordered">
        <input type="hidden" id="uid" name="uid" value="${uid}" >
        <thead  class="text-c">
            <tr >
                <th>业务名称</th>
                <th>开通时间</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody  class="text-c">
            <tr>
                <td>接口业务</td>

                <c:if test="${RestType.sid eq null}">
                    <td>2016-06-18</td>
                    <td>已开通</td>
                </c:if>
                <c:if test="${RestType.sid != null}">
                    <td><fmt:formatDate value="${RestType.createDate}" pattern="yyyy-MM-dd"/></td>
                    <td>已开通</td>
                </c:if>
                <td>--</td>
            </tr>
            <tr>
                <td>智能云调度</td>
                <td><fmt:formatDate value="${znyddType.createDate}" pattern="yyyy-MM-dd"/></td>
                <c:if test="${znyddType.sid eq null}">
                    <td></td>
                    <td><button type="button" class="btn btn-success radius" id="open" name="open">开启</button></td>
                </c:if>
                <c:if test="${znyddType.sid != null}">
                    <td>已开通</td>
                    <td><button type="button" class="btn btn-warning radius" id="close" name="close"> 关闭</button></td>
                </c:if>
            </tr>
            <tr>
                <td>SIP接口</td>
                <td><fmt:formatDate value="${SipType.createDate}" pattern="yyyy-MM-dd"/></td>
                <c:if test="${SipType.sid eq null}">
                    <td></td>
                    <td><button type="button" class="btn btn-success radius" id="open1" name="open1"> 开启</button></td>
                </c:if>
                <c:if test="${SipType.sid != null}">
                    <td>已开通</td>
                    <td><button type="button" class="btn btn-warning radius" id="close1" name="close1"> 关闭</button></td>
                </c:if>
            </tr>

            <tr>
                <td>云话机</td>
                <td><fmt:formatDate value="${PhoneSip.createDate}" pattern="yyyy-MM-dd"/></td>
                <c:if test="${PhoneSip.sid eq null}">
                    <td></td>
                    <td><button type="button" class="btn btn-success radius" id="open2" name="open2"> 开启</button></td>
                </c:if>
                <c:if test="${PhoneSip.sid != null}">
                    <td>已开通</td>
                    <td><button type="button" class="btn btn-warning radius" id="close2" name="close2"> 关闭</button></td>
                </c:if>
            </tr>


            <tr>
                <td>ECC云总机</td>
                <td><fmt:formatDate value="${EccType.createDate}" pattern="yyyy-MM-dd"/></td>
                <c:if test="${EccType.sid eq null}">
                    <td></td>
                    <td><button type="button" class="btn btn-success radius" id="openEcc" name="openEcc"> 开启</button></td>
                </c:if>
                <c:if test="${EccType.sid != null}">
                    <td>已开通</td>
                    <td><button type="button" class="btn btn-warning radius" id="closeEcc" name="closeEcc"> 关闭</button></td>
                </c:if>
            </tr>


            <tr>
                <td>虚拟小号</td>
                <td><fmt:formatDate value="${AxbType.createDate}" pattern="yyyy-MM-dd"/></td>
                <c:if test="${AxbType.sid eq null}">
                    <td></td>
                    <td><button type="button" class="btn btn-success radius" id="openAxb" name="openAxb"> 开启</button></td>
                </c:if>
                <c:if test="${AxbType.sid != null}">
                    <td>已开通</td>
                    <td><button type="button" class="btn btn-warning radius" id="closeAxb" name="closeAxb"> 关闭</button></td>
                </c:if>
            </tr>


        </tbody>
    </table>
    </div>
</div>

<script type="text/javascript">


    //开通隐私小号业务
    $('#openAxb').click(function() {
        var sid = '${sid}';
        layer.confirm('是否确定开启该业务？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/busType/openAxb'/>",
                data:{'sid':sid},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data.code=='00') {
                        layer.msg(data.info, {icon: 1});
                        location.replace(location.href);
                    }
                    if(data.code=='02') {
                        layer.alert("业务应用已存在，请在对应应用列表中手动启用", {icon: 1},function(){location.replace(location.href);});
                    }
                    else{
                        layer.alert("请稍候..", {icon: 1});
                    }

                },
                error  : function(data){
                    alert("出现异常");
                }
            });
        });
    });

    //关闭隐私小号业务
    $('#closeAxb').click(function() {
        var sid = '${sid}';
        layer.confirm('是否确定关闭该业务？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/busType/closeAxb'/>",
                data:{'sid':sid},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data.code=='00') {
                        layer.msg(data.info, {icon: 1});
                        location.replace(location.href);
                    }
                    if(data.code=='01') {
                        layer.alert("对应的Account ID不存在", {icon: 2});
                    }
                    if(data.code=='02') {
                        layer.alert("该业务存在关联号码池，无法关闭。请先执行删除关联号码池的操作", {icon: 2});
                    }
                    else{
                        layer.alert("请稍候..", {icon: 1});
                    }

                },
                error  : function(data){
                    alert("出现异常1");
                }
            });
        });
    });


    //开通云总机ECC业务
    $('#openEcc').click(function() {
        var sid = '${sid}';
        layer.confirm('是否确定开启该业务？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/busType/openEcc'/>",
                data:{'sid':sid},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data.code=='00') {
                        layer.msg(data.info, {icon: 1});
                        location.replace(location.href);
                    }
                    if(data.code=='02') {
                        layer.alert("业务应用已存在，请在对应应用列表中手动启用", {icon: 1},function(){location.replace(location.href);});
                    }
                    else{
                        layer.alert("请稍候..", {icon: 1});
                    }

                },
                error  : function(data){
                    alert("出现异常");
                }
            });
        });
    });

    //关闭云总机ECC业务
    $('#closeEcc').click(function() {
        var sid = '${sid}';
        layer.confirm('是否确定关闭该业务？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/busType/closeEcc'/>",
                data:{'sid':sid},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data.code=='00') {
                        layer.msg(data.info, {icon: 1});
                        location.replace(location.href);
                    }
                    if(data.code=='01') {
                        layer.alert("对应的Account ID不存在", {icon: 2});
                    }
                    if(data.code=='02') {
                        layer.alert("该业务存在关联应用，无法关闭。请先执行删除关联应用的操作", {icon: 2});
                    }
                    else{
                        layer.alert("请稍候..", {icon: 1});
                    }

                },
                error  : function(data){
                    alert("出现异常1");
                }
            });
        });
    });


    //开通云话机业务
    $('#open2').click(function() {
        var sid = '${sid}';
        layer.confirm('是否确定开启该业务？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/busType/openPhone'/>",
                data:{'sid':sid},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data.code=='00') {
                        layer.msg(data.info, {icon: 1});
                        location.replace(location.href);
                    }
                    if(data.code=='02') {
                        layer.alert("业务应用已存在，请在对应应用列表中手动启用", {icon: 1},function(){location.replace(location.href);});
                    }
                    else{
                        layer.alert("请稍候..", {icon: 1});
                    }

                },
                error  : function(data){
                    alert("出现异常");
                }
            });
        });
    });

    //关闭云话机业务
    $('#close2').click(function() {
        var sid = '${sid}';
        layer.confirm('是否确定关闭该业务？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/busType/closePhone'/>",
                data:{'sid':sid},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data.code=='00') {
                        layer.msg(data.info, {icon: 1});
                        location.replace(location.href);
                    }
                    if(data.code=='01') {
                        layer.alert("对应的Account ID不存在", {icon: 2});
                    }
                    if(data.code=='02') {
                        layer.alert("该业务存在关联应用，无法关闭。请先执行删除关联应用的操作", {icon: 2});
                    }
                    else{
                        layer.alert("请稍候..", {icon: 1});
                    }

                },
                error  : function(data){
                    alert("出现异常1");
                }
            });
        });
    });



    //开通sip业务
    $('#open1').click(function() {
        var sid = '${sid}';
        layer.confirm('是否确定开启该业务？', {
            btn: ['是','否'] //按钮
        }, function(){
        $.ajax({
            type: "post",//使用post方法访问后台
            dataType: "json",//返回json格式的数据
            url: "<c:url value='/busType/openSip'/>",
            data:{'sid':sid},//要发送的数据
            success: function(data){//data为返回的数据，在这里做数据绑定
                if(data.code=='00') {
                    layer.msg(data.info, {icon: 1});
                    location.replace(location.href);
                }
                if(data.code=='02') {
                    layer.alert("业务应用已存在，请在对应应用列表中手动启用", {icon: 1},function(){location.replace(location.href);});
                }
                else{
                    layer.alert("请稍候..", {icon: 1});
                }

            },
            error  : function(data){
                alert("出现异常");
            }
        });
        });
    });

    //关闭sip业务
    $('#close1').click(function() {
        var sid = '${sid}';
        layer.confirm('是否确定关闭该业务？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/busType/closeSip'/>",
                data:{'sid':sid},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data.code=='00') {
                        layer.msg(data.info, {icon: 1});
                        location.replace(location.href);
                    }
                    if(data.code=='01') {
                        layer.alert("对应的Account ID不存在", {icon: 2});
                    }
                    if(data.code=='02') {
                        layer.alert("该业务存在关联应用，无法关闭。请先执行删除关联应用的操作", {icon: 2});
                    }
                    else{
                        layer.alert("请稍候..", {icon: 1});
                    }

                },
                error  : function(data){
                    alert("出现异常1");
                }
            });
        });
    });



    //开通智能云调度业务
    $('#open').click(function() {
        var sid = '${sid}';
        layer.confirm('是否确定开启该业务？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/busType/openZnydd'/>",
                data:{'sid':sid},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data.code=='00') {
                        layer.msg(data.info, {icon: 1});
                        location.replace(location.href);
                    }
                    if(data.code=='01') {
                        layer.alert("对应的Account ID不存在", {icon: 2});
                    }
                    if(data.code=='02') {
                        layer.alert("业务应用已存在，请在对应应用列表中手动启用", {icon: 1},function(){location.replace(location.href);});
                    }
                    else{
                        layer.alert("请稍候..", {icon: 1});
                    }

                },
                error  : function(data){
                    alert("出现异常");
                }
            });
        });
    });

    //关闭智能云调度业务
    $('#close').click(function() {
        var sid = '${sid}';
        layer.confirm('是否确定关闭该业务？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                type: "post",//使用post方法访问后台
                dataType: "json",//返回json格式的数据
                url: "<c:url value='/busType/closeZnydd'/>",
                data:{'sid':sid},//要发送的数据
                success: function(data){//data为返回的数据，在这里做数据绑定
                    if(data.code=='00') {
                        layer.msg(data.info, {icon: 1});
                        location.replace(location.href);
                    }
                    if(data.code=='01') {
                        layer.alert("对应的Account ID不存在", {icon: 2});
                    }
                    if(data.code=='02') {
                        layer.alert("该业务存在关联应用，无法关闭。请先执行删除关联应用的操作", {icon: 2});
                    }
                    else{
                        layer.alert("请稍候..", {icon: 1});
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
