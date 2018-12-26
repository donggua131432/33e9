<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
    <title>创建呼叫中心</title>
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
            },beforeSubmit:function(curform){
                var relaysV = '';
                var weightsV = '';
                //在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
                //这里明确return false的话表单将不会提交;
                $("#editRelaychDiv div[name='relayDiv']").each(function(index,obj){
                    var $obj = $(obj);
                    var relayV = $obj.find("select[name='relay']").val();
                    var weightV = $obj.find("input[name='weight']").val();
                    relaysV +=relayV+"-";
                    weightsV+=weightV+"-";
                });
                $('#relays').val(relaysV.substring(0,relaysV.length-1));
                $('#weights').val(weightsV.substring(0,weightsV.length-1));
                return true;
            }, datatype: {
                "weight" : function(gets,obj,curform,regxp){
                    obj.parent().next().html("");
                    if (!/^[0-9]$/.test(gets)) {
                        obj.parent().next().append('<span class="Validform_checktip Validform_wrong">请输入数字</span>');
                        return "请输入数字";
                    }
                    return true;
                },
                "relayUnique" : function(gets,obj,curform,regxp){
                    obj.parent().next().html("");
                    var isUnique = false;
                    var array =  $("select[name='relay']").not(obj).each(function(i){
                                if(gets== this.value){
                                    isUnique = true;
                                }
                       });
                    if(isUnique){
                        obj.parent().next().append('<span class="Validform_checktip Validform_wrong">中继群不能重复选择</span>');
                        return "中继群不能重复选择";
                    }else{
                        obj.removeClass();
                            $.ajax({
                                type:"post",
                                url:"<c:url value='/ccinfo/checkRelayIsLimit'/>",
                                dataType:"json",
                                data:{"relayNum":gets,"relayId":"${ccInfo.id}"},
                                success:function(data){
                                    if(data.code == "error"){
                                        obj.parent().next().append('<span class="Validform_checktip Validform_wrong">'+data.msg+'</span>');
                                        return data.msg;
                                    }
                                }
                            });
                        }
                    return true;
                }
            }
        });

        $("#name").select2({
            placeholder: "请选择客户名称",
            ajax: {
                url: "<c:url value='/userAdmin/getCompanyNameAndSidByPage'/>",
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return {
                        params: {name:params.term,busType:'01'}, //
                        page: params.page
                    };
                },
                processResults: function (data, params) {
                    params.page = params.page || 1;

                    var items = [];

                    $.each(data.rows,function(i,v){
                        items.push({id:v.sid, text:v.name});
                    });

                    return {
                        results: items,
                        pagination: {
                            more: (params.page * 30) < data.total
                        }
                    };
                },
                cache: true
            }
            //escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
            //minimumInputLength: 1,
            //templateResult: formatRepo, // omitted for brevity, see the source of this page
            //templateSelection: formatRepoSelection // omitted for brevity, see the source of this page
            //data:data
        });

        $("#name").on("change",function(){
            var sid=this.value;
            $("#sid").val(sid);
            $.ajax({
                type:"post",
                url:"<c:url value='/ccinfo/getAppInfoBySid'/>",
                dataType:"json",
                data:{"sid":sid},
                success:function(data){
                    $("#appName").html(data.appName);
                    $("#appid").val(data.appid);
                    var url="<c:url value='/ccinfo/checkCcnameUnique?appid='/>"+data.appid;
                    $("#ccname").attr("data-ajax","{'url':'"+url+"',msg:'该呼叫中心名称已经存在'}");
                }
            });
        });

    });

    //配置添加中继群
    function addRelayItem(obj){
        if ($("div[name='relayDiv']").length <= 4) {
            var itemHtml = '<div class="row cl" name="relayDiv">\
                <label class="form-label col-3">中继群：</label>\
                <div class="formControls col-7">\
                    <select  name="relay"  style="width: 170px;height: 28px;" datatype="relayUnique"  nullmsg="请输入中继群">\
                    <c:forEach items="${relays1}" var="relay">\
                        <option value="${relay.relayNum}">${relay.relayName}</option>\
                    </c:forEach>\
                    </select>\
                    权重：\
                    <input type="text" value="1" style="width: 70px;height: 22px;"  name="weight" max="9" datatype="weight" maxlength="1" nullmsg="请输入数字"/>\
                    <button type="button" onclick="delRelayItem(this);" class="delBtn marLeft15">删除</button>\
                    <button type="button" onclick="addRelayItem(this);" class="addBtn marLeft15">添加</button>\
                </div>\
                <div class="col-2"></div>\
            </div>';
            $("#editRelaychDiv").append(itemHtml);
            $(obj).remove();
        }
    }

    //删除中继群
    function delRelayItem(obj){
        var itemCount =$("div[name='relayDiv']").length;
        console.log(itemCount);
        if(itemCount > 1){
            $(obj).parents("div[name='relayDiv']").remove();
            $("#editCallCenterDiv").find("button[class='addBtn marLeft15']").remove();
            var lastButton = $("#editRelaychDiv").find(':button').last();
            if(lastButton.hasClass('delBtn marLeft15')){
                lastButton.after('&nbsp;<button type="button" onclick="addRelayItem(this);" class="addBtn marLeft15">添加</button>');
            }

        }
    }

</script>
<body>
<div class="pd-20">
    <form action="<c:url value='/ccinfo/add'/>" method="post" class="form form-horizontal" id="form-member-add">
        <input type="hidden" name="relays" id="relays" value=""/>
        <input type="hidden" name="weights" id="weights" value=""/>
        <div class="row cl">
            <label class="form-label col-3">客户名称：</label>
            <div class="formControls col-5">
                <select id="name" style="width: 306px" nullmsg="客户名称不能为空！" datatype="*"></select>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">Account ID：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="请输入账户ID" id="sid" name="sid" datatype="*2-32" nullmsg="账户ID不能为空！" readonly/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">应用名称：</label>
            <div class="formControls col-5">
                <label class="form-label col-3" id="appName"></label>
            </div>
            <div class="col-4"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">APP ID：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="请输入应用ID" id="appid" name="appid" datatype="*2-32" nullmsg="应用ID不能为空！" readonly/>
            </div>
            <div class="col-4"> </div>
        </div>

        <br>
        <HR style="border:1px dashed #987cb9" width="100%" color=#987cb9 SIZE=1>

        <div class="row cl">
            <label class="form-label col-3">呼叫中心名称：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="请输入呼叫中心名称" id="ccname" name="ccname" datatype="*2-32&_ajax" data-ajax="{'url':'<c:url value="/ccinfo/checkCcnameUnique"/>',msg:'该呼叫中心名称已经存在'}" nullmsg="呼叫中心名称不能为空！"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">最大话务员数量：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="请输入最大话务员数量" id="ccoMaxNum" name="ccoMaxNum" ignore="ignore" datatype="n1-6"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">是否开启缺省：</label>
            <div class="formControls col-6">
                <div class="skin-minimal">
                    <div class="check-box">
                        <input type="radio" name="defaultCc" value="1">
                        <label>是</label>
                    </div>
                    <div class="check-box">
                        <input type="radio" name="defaultCc" value="0" checked="checked">
                        <label>否</label>
                    </div>
                </div>
            </div>
            <div class="formControls col-3"></div>
        </div>

        <div id="editRelaychDiv" >
            <c:if test="${not empty relays1}">
                <div class="row cl" name="relayDiv">

                    <label class="form-label col-3">中继群：</label>
                    <div class="formControls col-7">

                            <select  name="relay"  style="width: 170px;height: 28px;" datatype="relayUnique" nullmsg="请输入中继群">
                            <c:forEach items="${relays1}" var="relay">
                                <option value="${relay.relayNum}">${relay.relayName}</option>
                            </c:forEach>
                            </select>
                        权重：
                            <input type="text" value="1" style="width: 70px;height: 22px;"  name="weight" max="9" datatype="weight" maxlength="1" nullmsg="请输入数字"/>
                            <button type="button" onclick="delRelayItem(this);" class="delBtn marLeft15">删除</button>
                            <button type="button" onclick="addRelayItem(this);" class="addBtn marLeft15">添加</button>
                    </div>
                    <div class="col-2"></div>
                </div>
            </c:if>
        </div>

        <div class="row cl">
            <label class="form-label col-3">逃生号码：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="请输入逃生号码" id="lifeRelay" name="lifeRelay" ignore="ignore" datatype="/^[1][0-9]{10}$/|/^[0][0-9]{10,11}$/|/^[4][0][0][0-9]{1,20}$/"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">DTMF收号：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="请输入DTMF收号" id="dtmfNum" name="dtmfNum" datatype="/^\s*$/|/^[0-9,|,\*]{3,255}$/"/>
            </div>
            <div class="col-4"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-3">备注：</label>
            <div class="formControls col-7">
                <textarea id="remark" name="remark" rows="5" class="textarea"  maxlength="250" placeholder="请输填写备注信息" dragonfly="true" onKeyUp="textarealength(this,500)"></textarea>
                <p class="textarea-numberbar"><em class="textarea-length">0</em>/250</p>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <div class="col-9 col-offset-3">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset"  onclick="layer_close();;"  value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>