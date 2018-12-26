<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
    <title>编辑呼叫中心</title>
</head>
<script type="text/javascript">
    $(function(){

        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });

        $("#form-member-add").Validform({
            tiptype:2,
            ajaxPost:true,
            callback:function(data){
                $.Showmsg(data.msg);
                setTimeout(function(){
                    $.Hidemsg();
                    if(data.code == "ok"){
                        //刷新父级页面
                        parent.location.replace(parent.location.href);
                        //关闭当前窗口
                        layer_close();
                    }
                },2000);
            }
        });

    })

    //配置添加呼叫中心
    function addCallCenterItem(obj){
        var itemCount = $("#editCallCenterDiv").find(".marTop10").length;
        if(itemCount <= 4){
            var itemHtml = '<div class="marTop10">' +
                    '<select name="subid" style="width: 170px;height: 28px;">' +
                    <c:forEach items="${ccInfoList}" var="ccInfoList">
                    '<option value="${ccInfoList.subid}">${ccInfoList.ccname}</option>' +
                    </c:forEach>
                    '</select>' +
                    '&nbsp;<input type="text" name="pri" value="'+ parseInt(itemCount+1) +'"  style="width: 70px;height: 22px;" readonly/>'+
                    '&nbsp;<button type="button" onclick="delCallCenterItem(this);" class="delBtn marLeft15">删除</button>' +
                    '&nbsp;<button type="button" onclick="addCallCenterItem(this);" class="addBtn marLeft15">添加</button></div>';
            $("#editCallCenterDiv").append(itemHtml);
            $(obj).remove();
        }else{
            $("#tipMsg").html('<span class="Validform_checktip Validform_wrong">只能配置5个呼叫中心</span>');
        }
    }

    //删除呼叫中心配置项
    function delCallCenterItem(obj){
        var itemCount = $("#editCallCenterDiv").find(".marTop10").length;
        if(itemCount > 1){
            $(obj).parent().remove();
            $("#editCallCenterDiv").find("button[class='addBtn marLeft15']").remove();
            $("#editCallCenterDiv").find(".marTop10").last().append('<button type="button" onclick="addCallCenterItem(this);" class="addBtn marLeft15">添加</button>');
            //重新给优先级赋值
            $("#editCallCenterDiv").find(":input[name='pri']").each(function(i){
                $(this).val(parseInt(i+1));
            });
        }else{
            $("#tipMsg").html('<span class="Validform_checktip Validform_wrong">不能删除，至少需要配置一个呼叫中心</span>');
        }
    }

    //提交呼叫中心信息
    function submitCallCenter(){
        var dispatchId="", subid="", subidArray=[], pri="", priArray=[], formData=[], submitFlag=true;
        //获取元素值
        $("#editCallCenterDiv").find(":input[name]").each(function(){
            if($(this).attr("name") == "dispatchId"){
                dispatchId = $(this).val();
            }else if($(this).attr("name") == "subid"){
                subid += $(this).val()+",";
                if($(this).val() == '' || $(this).val() =='null' || $(this).val() == null){
                    $("#tipMsg").html('<span class="Validform_checktip Validform_wrong">呼叫中心不能为空</span>');
                    submitFlag = false;
                    return;
                }
            }else if($(this).attr("name") == "pri"){
                pri += $(this).val()+",";
            }
        });
        //截掉字符串最后一个逗号
        subidArray = subid.substring(0,subid.length-1).split(",");
        priArray = pri.substring(0,pri.length-1).split(",");
        //判断重复项以及赋值
        for(var i=0; i<subidArray.length; i++){
            var dispatchInfo={};
            if(subid.replace(subidArray[i]+",","").indexOf(subidArray[i]+",") > -1) {
                $("#tipMsg").html('<span class="Validform_checktip Validform_wrong">'+getCcDispatchInfoArray()[subidArray[i]]+'重复，不能配置重复的呼叫中心'+'</span>');
                submitFlag = false;
                break;
            }
            dispatchInfo["dispatchId"] = dispatchId;
            dispatchInfo["subid"] = subidArray[i];
            dispatchInfo["pri"] = priArray[i];
            formData.push(dispatchInfo);
        }
        if(submitFlag == true){
            //console.log(JSON.stringify(formData));
            $.ajax({
                type:"post",
                url:"<c:url value="/ccdispatchinfo/edit"/>",
                dataType:"json",
                async:false,
                data:{dispatchListStr:JSON.stringify(formData),dispatchId:dispatchId},
                success : function(data) {
                    $.Showmsg(data.msg);
                    setTimeout(function(){
                        $.Hidemsg();
                        if(data.code == "ok"){
                            //刷新父级页面
                            parent.location.replace(parent.location.href);
                            //关闭当前窗口
                            layer_close();
                        }
                    },2000);
                }
            });
        }
    }

    //获取呼叫中心键值对数组

    function getCcDispatchInfoArray(){
        var ccDispatchInfoArray = {};
        <c:forEach items="${ccInfoList}" var="ccInfoList">
        ccDispatchInfoArray["${ccInfoList.subid}"] = "${ccInfoList.ccname}";
        </c:forEach>
        return ccDispatchInfoArray;
    }

</script>
<body>
<div class="pd-20">
    <form action="<c:url value='/ccdispatchinfo/edit'/>" method="post" class="form form-horizontal" id="form-member-add">

        <div class="row cl">
            <div class="formControls col-9">
                呼叫中心
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                优先级
            </div>
        </div>

        <div class="marTop30" id="editCallCenterDiv">
            <input type="hidden" value="${dispatchId}" id="dispatchId" name="dispatchId"/>
            <c:if test="${empty ccDispatchInfoList}">
                    <div class="marTop10">
                        <select name="subid" style="width: 170px;height: 28px;" nullmsg="呼叫中心不能为空！" datatype="*">
                            <c:forEach items="${ccInfoList}" var="ccInfo">
                                <option value="${ccInfo.subid}">${ccInfo.ccname}</option>
                            </c:forEach>
                        </select>
                        <input type="text" value="1" style="width: 70px;height: 22px;" readonly name="pri"/>
                        <button type="button" onclick="delCallCenterItem(this);" class="delBtn marLeft15">删除</button>
                        <button type="button" onclick="addCallCenterItem(this);" class="addBtn marLeft15">添加</button>

                    </div>
            </c:if>
            <c:forEach items="${ccDispatchInfoList}" var="ccDispatchInfo" varStatus="status">
                    <div class="marTop10">
                        <select name="subid" style="width: 170px;height: 28px;" nullmsg="呼叫中心不能为空！" datatype="*">
                            <c:forEach items="${ccInfoList}" var="ccInfo">
                                <c:if test="${ccInfo.subid eq ccDispatchInfo.subid}">
                                    <option value="${ccInfo.subid}" selected>${ccInfo.ccname}</option>
                                </c:if>
                                <c:if test="${ccInfo.subid ne ccDispatchInfo.subid}">
                                    <option value="${ccInfo.subid}">${ccInfo.ccname}</option>
                                </c:if>
                            </c:forEach>
                        </select>

                        <input type="text" value="${ccDispatchInfo.pri}" style="width: 70px;height: 22px;" readonly name="pri"/>
                        <button type="button" onclick="delCallCenterItem(this);" class="delBtn marLeft15">删除</button>
                        <c:if test="${status.last}">
                            <button type="button" onclick="addCallCenterItem(this);" class="addBtn marLeft15">添加</button>
                        </c:if>
                    </div>
            </c:forEach>
        </div>

        <div class="col-9" id="tipMsg"> </div>

        <div class="row cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-primary radius" type="button" onclick="submitCallCenter();" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset"  onclick="layer_close();;"  value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>