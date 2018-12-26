<%--
   Created by IntelliJ IDEA.
  User: lixin
  Date: 2016/7/14
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>编辑SIP应用</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function(){

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
//                            layer_close();
                        }
                    },2000);
                }
            });


        });
    </script>
</head>
<body>
<div class="pd-20">
    <div class="form form-horizontal">
        <div class="row cl">
            <label class="form-label col-4">客户名称：</label>
            <div class="formControls col-8">
                <span>${appInfo.companyName}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">Account ID：</label>
            <div class="formControls col-8">
                <span>${appInfo.sid}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">应用名称：</label>
            <div class="formControls col-8">
                <span>${appInfo.appName}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">APP ID：</label>
            <div class="formControls col-8">
                <span>${appInfo.appid}</span>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-4">应用状态：</label>
            <div class="formControls col-8">
                <c:if test="${appInfo.status == '00'}">
                    <span class="label label-success radius">正常</span>
                </c:if>
                <c:if test="${appInfo.status == '01'}">
                    <span class="label label-warning radius">冻结</span>
                </c:if>
                <c:if test="${appInfo.status == '02'}">
                    <span class="label label-defaunt radius">禁用</span>
                </c:if>
            </div>
        </div>
        <div class="row cl">
            <div class="form-label col-13">
                <div class="skin-minimal">
                    <div class="radio-box" style="margin-left:-30px;width: 660px;">
                        <c:if test="${appInfo.isDefined == '0'}">
                            <input type="radio" id="relayNumType1" name="relayNumType" onclick="useSysNum(this,'${appInfo.appid}');" checked="checked">
                            <label for="relayNumType1">使用系统强显号码池</label>
                        </c:if>
                        <c:if test="${appInfo.isDefined == '1'}">
                            <input type="radio" id="relayNumType1" name="relayNumType" onclick="useSysNum(this,'${appInfo.appid}');">
                            <label for="relayNumType1">使用系统强显号码池</label>
                        </c:if>
                        <c:if test="${appInfo.isDefined == null}">
                            <input type="radio" id="relayNumType1" name="relayNumType" onclick="useSysNum(this,'${appInfo.appid}');">
                            <label for="relayNumType1">使用系统强显号码池</label>
                        </c:if>
                        <c:if test="${appInfo.isDefined == ''}">
                            <input type="radio" id="relayNumType1" name="relayNumType" onclick="useSysNum(this,'${appInfo.appid}');">
                            <label for="relayNumType1">使用系统强显号码池</label>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="row cl">
            <div class="form-label col-3">
                <div class="skin-minimal">
                    <div class="radio-box" style="margin-left:-30px;width: 645px;">
                        <c:if test="${appInfo.isDefined == '0'}">
                            <input type="radio" id="relayNumType2" name="relayNumType"  onclick="showTable();">
                            <label for="relayNumType2">自定义强显号码池</label>
                        </c:if>
                        <c:if test="${appInfo.isDefined == '1'}">
                            <input type="radio" id="relayNumType2" name="relayNumType"  onclick="showTable();" checked="checked">
                            <label for="relayNumType2">自定义强显号码池</label>
                        </c:if>
                        <c:if test="${appInfo.isDefined == null}">
                            <input type="radio" id="relayNumType2" name="relayNumType"  onclick="showTable();">
                            <label for="relayNumType2">自定义强显号码池</label>
                        </c:if>
                        <c:if test="${appInfo.isDefined == ''}">
                            <input type="radio" id="relayNumType2" name="relayNumType"  onclick="showTable();">
                            <label for="relayNumType2">自定义强显号码池</label>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>


<!------------------------------------------------------------------------------------------------ 分界线 -------------------------------------------------------------------------------------->

    <h4 class="page_title"></h4>

    <div class="mt-20" id="showTable" style="visibility: hidden">
        <table class="table table-border table-bordered table-hover table-bg table-sort">
            <thead>
            <tr class="text-c">
                <th width="10%">序号</th>
                <th width="20%">创建时间</th>
                <th width="20%">中继名称</th>
                <th width="20%">中继ID</th>
                <th width="10%">中继方向</th>
                <th width="20%">操作</th>
            </tr>
            </thead>
        </table>
    </div>

</div>
</body>
<script type="text/javascript">

    var datatables = "";
    var search_param = {};
    $(document).ready(function() {
        if($("#relayNumType2").is(':checked')) {
            var divObj = document.getElementById("showTable");
            if (divObj.style.visibility == "hidden") {
                divObj.style.visibility = "visible";
            } else {
                divObj.style.visibility = "hidden";
            }
        }
        // datatables ajax 选项
        var datatables_ajax = {
            url: '<c:url value='/openi/pageRelayList'/>?appid=${appInfo.appid}',
            type: 'POST',
            "data": function(d){
                var _page = datatables_ajax_data(d);
                return $.extend({},_page, search_param);
            }
        };

        // columns 列选项 和表头对应，和后台传回来的数据属性对应
        var columns = [
            { data: 'rowNO' },
            { data: 'createTime'},
            { data: 'relayName'},
            { data: 'relayNum' },
            { data: 'relayType'},
            { data: ''}
        ];

        // 对数据进行处理
        var columnDefs = [
            {
                "targets": [0,1,2,3,4,-1],
                "orderable":false,
                "sClass" : "text-c"
            },
            {
                "targets": [1],
                "render": function(data, type, full) {
                    if (full.createTime) {
                        return o.formatDate(full.createTime, "yyyy-MM-dd hh:mm:ss");
                    }
                    return full.createTime;
                }
            },
            {
                "targets": [4],
                "sClass" : "text-c",
                "render": function(data, type, full) {
                    if (full.relayType == '10') {
                        return '<span class="label label-success radius">出中继</span>';
                    }
                    if (full.relayType == '11') {
                        return '<span class="label label-default radius">双向中继</span>';
                    }
                    return full.relayType;
                }
            },
            {
                "targets": [-1],
                "sClass" : "text-c", // 样式（居中）
                "render": function(data, type, full) {
                    if(full.isChecked=='1') {
                        return "<td><input id='check" + full.rowNO + "' name='check' type='checkbox' value='" + full.relayNum + "'  onclick='addAppRelay(this.id);' checked></td>";
                    }
                    if(full.isChecked=='0') {
                        return "<td><input id='check" + full.rowNO + "' name='check' type='checkbox' value='" + full.relayNum + "'  onclick='addAppRelay(this.id);'></td>";
                    }
                }
            }
        ];

        datatables = $(".table-sort").dataTable({
            "processing": false, // 是否显示取数据时的那个等待提示
            "serverSide": true,//这个用来指明是通过服务端来取数据
            "bStateSave": true,//状态保存
            "bFilter": false, //隐藏掉自带的搜索功能
            "dom": 'rtilp<"clear">',
            //"fnServerData": retrieveData,
            "columns": columns,
            ajax: datatables_ajax,
            "columnDefs":columnDefs
        });

        $(window).resize(function(){
            $(".table-sort").width("100%");
        });

        check = document.getElementById("relayNumType1").checked;
    });

    // 点击自定义强显号码池按钮
    function showTable() {
        check = false;
        var divObj=document.getElementById("showTable");

        if (divObj.style.visibility == "visible") {
        } else {
            divObj.style.visibility = "visible";
        }


    }

    function addAppRelay(id) {
        var appid = "${appInfo.appid}";
        id = "#"+id;
        var relayNum = $(id).val();
        if($(id).is(':checked')){
            $.ajax({
                type:"post",
                url:"<c:url value='/openi/addAppRelay'/>",
                dataType:"json",
                data:{"appid":appid,"relayNum":relayNum},
                success:function(data){
                    layer.msg(data.info, {icon: 1});
                    location.replace(location.href);
                }
            });
        }else{
            $.ajax({
                type:"post",
                url:"<c:url value='/openi/deleteAppRelay'/>",
                dataType:"json",
                data:{"appid":appid,"relayNum":relayNum},
                success:function(data){
                    layer.msg(data.info, {icon: 1});
                    location.replace(location.href);
                }
            });
        }

    }

    var check = true;
    // 点击系统默认强显号码池按钮
    function useSysNum(obj,appid,e) {

        if (check && obj.checked == true) {
            return;
        }

        layer.confirm('是否使用系统默认强显号码池？' , {
            btn: ['是','否'] //按钮
        }, function(){
            check = true;
            var divObj=document.getElementById("showTable");
            if(divObj.style.visibility=="visible"){
                divObj.style.visibility="hidden";
            }

            $.ajax({
                type:"post",
                url:"<c:url value='/openi/useSysNum'/>",
                dataType:"json",
                data:{"appid":appid},
                success:function(data){
                    layer.msg(data.info, {icon: 1});
                    location.replace(location.href);
                }
            });
        }, function(){
            document.getElementById("relayNumType2").checked = true;
            obj.checked = false;
            check = false;
        });

    }

    /*增加*/
    function openUserDialog(title,url,w,h){
        layer_show(title,url,w,h);
    }

    function openRelayDialog(title,url,w,h) {
        layer_full(title,url,w,h);
    }

</script>
</html>
