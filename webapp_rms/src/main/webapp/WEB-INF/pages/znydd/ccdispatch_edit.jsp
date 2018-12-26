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
    <title>编辑话务调度</title>
</head>
<script type="text/javascript">
    var isSubmit = false;
    var validateform;
    $(function(){
        selectWeekday('${weekdays}');

        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });

        validateform =$("#form-member-add").Validform({
            tiptype:2,
            ajaxPost:true,
            beforeSubmit:function(curform){
                //在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
                //这里明确return false的话表单将不会提交;
                if (isSubmit) {
                    return false;
                }
                isSubmit=true;
                return true;
            },
            callback:function(data){
                $.Showmsg(data.msg);
                setTimeout(function(){
                    $.Hidemsg();
                    if(data.code == "ok"){
                        //刷新父级页面
                        parent.location.replace(parent.location.href);
                        //关闭当前窗口
                        layer_close();
                    } else {
                        isSubmit = false;
                    }
                },2000);
            },
            datatype: {
                "boxCheck": function (){
                    var chk_value =[];
                    var weekday=0;
                    $('input[name="weekdaybox"]:checked').each(function(){
                        chk_value.push($(this).val());
                        //进行二进制位运算
                        weekday=weekday+(1 << $(this).val());
                    });
                    if(chk_value.length>0){
                        $("#weekday").val(weekday);
                        return true;
                    }else {
                        $("#boxMsg").html('<span class="Validform_checktip Validform_wrong">请至少选择一个日期类型</span>');
                        return false;
                    }
                },
                "timeCheck": function (){
                    var timeStart=$("#timeStart_s").val().replace(":","");
                    var timeEnd=$("#timeEnd_s").val().replace(":","");

                    if(parseInt(timeStart)<parseInt(timeEnd)){
                        $("#timeStart").val(parseInt(timeStart));
                        $("#timeEnd").val(parseInt(timeEnd));
                        return true;
                    }else {
                        $("#timeEndMsg").html('<span class="Validform_checktip Validform_wrong">结束时间不能小于开始时间</span>');
                        return false;
                    }
                },
                "uniqueAname": function (gets, obj, curform, datatype) {
                    var result = true;
                    if (!/^[\u4E00-\u9FA5-a-zA-Z0-9\(\)]{1,20}$/.test(gets)) {
                        return "调度名称不合法";
                    }else{

                    }

                    $.ajax({
                        async:false,
                        type: "post",
                        url: "<c:url value="/ccdispatch/uniqueAname"/>",
                        dataType: "json",
                        data: {sid : '${ccDispatch.sid}', dispatchName : gets,id:"${ccDispatch.id}"},
                        success: function (data) {
                            if (data.code == "ok") {
                                result = true;
                            }
                            if (data.code == "error") {
                                result = "该调度名称已存在";
                            }
                        },
                        error: function () {
                            result = "数据请求异常";
                        }
                    });

                    return result;
                }
            }
        });

        $("#areaId-sid").select2({
            placeholder: "${ccDispatch.aname}",
            ajax: {
                type:"post",
                url: "<c:url value="/ccdispatch/queryCcAreaList"/>",
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return {
                        params: {name:params.term,sid:"${ccDispatch.sid}",id:"${ccDispatch.id}"},
                        page: params.page
                    };
                },
                processResults: function (data, params) {
                    params.page = params.page || 1;

                    var items = [];

                    $.each(data.rows,function(i,v){
                        items.push({id:v.area_id+"-"+v.sid+"-"+v.name, text:v.aname});
                    });

                    return {
                        results: items,
                        pagination: {
                            more: data.toIndex < data.total
                        }
                    };
                },
                cache: true
            }
        });

        $("#areaId-sid").on("change",function(){
            var va=this.value;
            var arr=va.split("-");
            $("#areaId").val(arr[0]);
            $("#sid").val(arr[1]);
            //页面显示客户姓名和sid
            $("#sid_l").html(arr[1]);
            $("#name_l").html(arr[2]);
        });

        // 初选 日期类型
        function selectWeekday(arr) {
            if (arr) {
                var arrs = arr.split(",");
                $("input[name='weekdaybox']").val(arrs);
            }
        }

        $("#chk_all").click(function(){
            if($(this).is(":checked")){
                $("input[name='weekdaybox']").prop("checked",true);
            }else{
                $("input[name='weekdaybox']").prop("checked",false);
            }
        });
    })

    //配置添加呼叫中心
    function addCallCenterItem(obj){
        var itemCount = $("#editCallCenterDiv").find(".marTop10").length;
        if(itemCount <= 4){
            var itemHtml = '<div class="marTop10">' +
                    '呼叫中心：<select name="subid" style="width: 170px;height: 28px;">' +
                    <c:forEach items="${ccInfoList}" var="ccInfoList">
                    '<option value="${ccInfoList.subid}">${ccInfoList.ccname}</option>' +
                    </c:forEach>
                    '</select>' +
                    '&nbsp;优先级：<input type="text" name="pri" value="'+ parseInt(itemCount+1) +'"  style="width: 70px;height: 22px;" readonly/>'+
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

    //提交话务调度
    function submitCallCenter(){
        if (!validateform.check(false)) {
            return;
        }
        var dispatchId="",subid="", dispatchName="",sid="",areaId="",timeStart="",timeEnd="",validDate="",
                subidArray=[], pri="", priArray=[], formData=[], submitFlag=true;


        //获取呼叫中心元素值
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
        var ccdispatch={};
        ccdispatch["id"]=$("#id").val();
        ccdispatch["dispatchName"]=$("#dispatchName").val();
        ccdispatch["sid"]=$("#sid").val();
        ccdispatch["areaId"]=$("#areaId").val();
        ccdispatch["timeStart"]=$("#timeStart").val();
        ccdispatch["timeEnd"]=$("#timeEnd").val();
        ccdispatch["weekday"]=$("#weekday").val();
        ccdispatch["validDate"]=$("#validDate").val();
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
                type: "post",
                url: "<c:url value="/ccdispatch/edit"/>",
                dataType: "json",
                async: false,
                data: {dispatchListStr: JSON.stringify(formData), ccStr: JSON.stringify(ccdispatch),dispatchId:dispatchId},
                success: function (result) {
                    $.Showmsg(result.msg);
                    setTimeout(function () {
                        $.Hidemsg();
                        if (result.code == "ok") {
                            //刷新父级页面
                            parent.location.replace(parent.location.href);
                            //关闭当前窗口
                            layer_close();
                        }else if (result.code == "recommit"){
                            $("select[name='subid']").children().remove();
                            var optionsHtml = "";
                            $.each(result.data,function(i,item){
                                optionsHtml += '<option value='+item.subid+'>'+item.ccname+'</option>';
                            });

                            $("select[name='subid']:eq(0)").html(optionsHtml);
                        }
                    }, 2000);
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
    <form action="<c:url value='/ccdispatch/edit'/>" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" value="${ccDispatch.id}" id ="id" name="id"/>
        <input type="hidden" value="${ccDispatch.sid}" id="sid" name="sid"/>
        <input type="hidden" value="${ccDispatch.areaId}" id="areaId" name="areaId"/>
        <div class="row cl">
            <label class="form-label col-3">调度名称：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" placeholder="请输入调度名称" value="${ccDispatch.dispatchName}" id="dispatchName" name="dispatchName" datatype="uniqueAname" maxlength="20"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">客户名称：</label>
            <div class="formControls col-5">
                <label id="name_l">${ccDispatch.name}</label>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">account id：</label>
            <div class="formControls col-5">
                <label id="sid_l">${ccDispatch.sid}</label>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">调度区域：</label>
            <div class="formControls col-5">
                <select id="areaId-sid" style="width: 306px" nullmsg="调度区域不能为空！" datatype="*">
                    <c:if test="${not empty ccDispatch.aname}">
                        <option value="${ccDispatch.areaId}">${ccDispatch.aname}</option>
                    </c:if>
                </select>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">开始时间：</label>
            <div class="formControls col-5">
                <input type="hidden" value="" id="timeStart" name="timeStart"/>
                <input type="text" onfocus="WdatePicker({dateFmt:'HH:mm'})" id="timeStart_s" value="${ccDispatch.timeStartShow}" class="input-text Wdate" style="width:220px;" datatype="*"  nullmsg="请输入开始时间">
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">结束时间：</label>
            <div class="formControls col-5">
                <input type="hidden" value="" id="timeEnd" name="timeEnd"/>
                <input type="text" onfocus="WdatePicker({dateFmt:'HH:mm'})" id="timeEnd_s" value="${ccDispatch.timeEndShow}" class="input-text Wdate" style="width:220px;" datatype="timeCheck"  nullmsg="请输入结束时间">
            </div>
            <div class="col-4" id="timeEndMsg"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">日期类型：</label>
            <div class="formControls col-6">
                <input type="checkbox" name="checkall" id="chk_all" value=""><label for="chk_all">全选</label></th>
                <input type="checkbox" name="weekdaybox" id="Monday" value="0"><label for="Monday">周一</label>
                <input type="checkbox" name="weekdaybox" id="Tuesday" value="1"><label for="Tuesday">周二</label>
                <input type="checkbox" name="weekdaybox" id="Wednesday" value="2"><label for="Wednesday">周三</label>
                <input type="checkbox" name="weekdaybox" id="Thursday" value="3"><label for="Thursday">周四</label>
                <input type="checkbox" name="weekdaybox" id="Friday" value="4"><label for="Friday">周五</label>
                <input type="checkbox" name="weekdaybox" id="Saturday" value="5"><label for="Saturday">周六</label>
                <input type="checkbox" name="weekdaybox" id="Sunday" value="6"><label for="Sunday">周日</label>
                <input type="hidden" id="weekday" name="weekday" value="" datatype="boxCheck" nullmsg="请至少选择一个日期类型"/>
            </div>
            <div class="col-4" id="boxMsg"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">生效时间：</label>
            <div class="formControls col-5">
                <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:%m:%s}'})" id="validDate" name="validDate" value="<fmt:formatDate value='${ccDispatch.validDate}' pattern='yyyy-MM-dd HH:mm:ss'/>" class="input-text Wdate" style="width:220px;" nullmsg="请至少选择一个日期类型">
            </div>
            <div class="col-4"> </div>
        </div>

        <!-- 分割线 -->
        <hr style="border:1px #ddd dotted;margin-top: 10px;"/>
        <!-- 分割线 -->
        <div class="row cl">
            <label class="form-label col-3">呼叫管理中心</label>
        </div>
        <div class="row cl">
            <label class="form-label col-2"></label>
            <div  id="editCallCenterDiv" class="formControls col-9">
                <input type="hidden" value="${dispatchId}" id="dispatchId" name="dispatchId"/>
                <c:if test="${empty ccDispatchInfoList}">
                    <div class="marTop10">
                        呼叫中心：<select name="subid" style="width: 170px;height: 28px;" nullmsg="呼叫中心不能为空！" datatype="*">
                            <c:forEach items="${ccInfoList}" var="ccInfo">
                                <option value="${ccInfo.subid}">${ccInfo.ccname}</option>
                            </c:forEach>
                        </select>
                        优先级：<input type="text" value="1" style="width: 70px;height: 22px;" readonly name="pri"/>
                        <button type="button" onclick="delCallCenterItem(this);" class="delBtn marLeft15">删除</button>
                        <button type="button" onclick="addCallCenterItem(this);" class="addBtn marLeft15">添加</button>

                    </div>
                </c:if>

                    <c:forEach items="${ccDispatchInfoList}" var="ccDispatchInfo" varStatus="status">
                        <div class="marTop10">
                            呼叫中心：<select name="subid" style="width: 170px;height: 28px;" nullmsg="呼叫中心不能为空！" datatype="*">
                                <c:forEach items="${ccInfoList}" var="ccInfo">
                                    <c:if test="${ccInfo.subid eq ccDispatchInfo.subid}">
                                        <option value="${ccInfo.subid}" selected>${ccInfo.ccname}</option>
                                    </c:if>
                                    <c:if test="${ccInfo.subid ne ccDispatchInfo.subid}">
                                        <option value="${ccInfo.subid}">${ccInfo.ccname}</option>
                                    </c:if>
                                </c:forEach>
                            </select>

                            优先级：<input type="text" value="${ccDispatchInfo.pri}" style="width: 70px;height: 22px;" readonly name="pri"/>
                            <button type="button" onclick="delCallCenterItem(this);" class="delBtn marLeft15">删除</button>
                            <c:if test="${status.last}">
                                <button type="button" onclick="addCallCenterItem(this);" class="addBtn marLeft15">添加</button>
                            </c:if>
                        </div>
                    </c:forEach>
            </div>
            <div class="col-1"> </div>
        </div>
        <div class="row cl">
            <label class="form-label col-3"></label>
            <div class="col-9" id="tipMsg"> </div>
        </div>

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