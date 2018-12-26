<%--
  Created by IntelliJ IDEA.
  User: DuKai
  Date: 2016/07/08
  Time: 9:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>城市配置列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
    <script type="text/javascript">
        var dataTables = "", search_param = {}, provinceCityArray = {}, tradeTypeArray={}, tradeTypeOption="", businessTypeArray={}, businessTypeOption="";
        $(function(){
            //dataTablesAjax 选项
            var dataTablesAjax = {
                url: '<c:url value='/cityMgr/pageCityList'/>',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param);
                }
            };

            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns = [
                {data: 'rowNO'},
                {data: 'cname'},
                {data: 'pname'},
                {data: 'areaCode'},
                {data: 'ccode'},
                {data: 'compName'},
                {data: 'operation'}
            ];
            // 对数据进行处理
            var columnDefs = [
                {
                    "targets": [0,1,2,3,4,5],
                    "orderable":false,
                    "sClass" : "text-c"
                },
                {"targets": [-1], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        var _ex_in = "";
                        if(full.ccode >= 1000){
                            _ex_in += '<a title="编辑" href="javascript:void(0);" onclick="openDialog(\'编辑城市信息\',\'<c:url value="/cityMgr/cityEditView?id='+full.id+'&operationType=edit"/>\',\'800\',\'400\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: green;">&#xe6df;</i>编辑</a>';
                        }
                        _ex_in += '<a title="查看" href="javascript:void(0);" onclick="openDialog(\'查看城市信息\',\'<c:url value="/cityMgr/cityEditView?id='+full.id+'&operationType=view"/>\',\'500\',\'350\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: green;">&#xe695;</i>查看</a>';
                        if(full.ctype==true) {
                            _ex_in += '<a title="删除" href="javascript:void(0);" onclick="delCity(\'' + full.id + '\',\'' + full.cname + '\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: red;">&#xe6e2;</i>删除</a>';
                        }
                        return  _ex_in;
                    }
                }
            ];

            //初始化表格渲染
            dataTables = $(".table-sort").dataTable({
                "processing": false, // 是否显示取数据时的那个等待提示
                "serverSide": true,//这个用来指明是通过服务端来取数据
                "bStateSave": true,//状态保存
                "bFilter": false, //隐藏掉自带的搜索功能
                "ordering": false,//关闭排序
//                "autoWidth": true,
                "dom": 'rtilp',
                "sErrMode": 'none',
                "columns": columns,
                ajax: dataTablesAjax,
                "columnDefs":columnDefs
            });

            // 搜索功能
            $("#searchButton").on("click", function(){
                console.info("搜索");
                var formData = {};
                $('#searchFrom').find('input[name]').each(function(){
                    formData["params["+$(this).attr('name')+"]"]=$(this).val().trim();
                });
                /*$('#searchFrom').find('select[name]').each(function(){
                    formData["params["+$(this).attr('name')+"]"]=$(this).val().trim();
                });*/
                search_param = formData;
                dataTables.fnDraw();
            });

            $("#companyName").select2({
                placeholder: "请选择客户名称",
                ajax: {
                    url: "<c:url value="/userAdmin/getCompanyNameAndSidByPage"/>",
                    dataType: 'json',
                    delay: 250,
                    data: function (params) {
                        return {
                            params: {name:params.term, busType:'01'}, // search term
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
            });
            $("#companyName").on("change",function(){
                $("#sid").val(this.value);
            });

            $("#province").select2({
                placeholder: "请选择省份名称",
                ajax: {
                    url: "<c:url value="/cityMgr/getInfoAppendToModel"/>",
                    dataType: 'json',
                    delay: 250,
                    /*data: function (params) {
                        return {
                            params: {name:params.term,busType:'2'}, // search term busType ：1 rest用户 2 智能云调度用户
                            page: params.page
                        };
                    },*/
                    processResults: function (data, params) {
                       /* params.page = params.page || 1;*/

                        var items = [];

                        $.each(data.provinceDic,function(i,v){
                            items.push({id:v.pcode, text:v.pname});
                        });
                        return {
                            results: items,
                           /* pagination: {
                                more: (params.page * 30) < data.total
                            }*/
                        };
                    },
                    cache: true
                }
            });
            $("#province").on("change",function(){
                $("#pcode").val(this.value);
            });
        });

        /** 删除城市 **/
        function delCity(id,cname){
            layer.confirm('记录一旦删除将不可以恢复。</br>确定要删除【'+ cname +'】吗？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    type:"post",
                    url:"<c:url value='/cityMgr/deleteCity'/>",
                    dataType:"json",
                    data:{"id":id},
                    success : function(data) {
                        layer.alert(data.info,{
                            icon: data.status == '0'? 1 : 2,
                            skin: 'layer-ext-moon'
                        },function (){
                            location.replace(location.href);
                        });
                    },
                    error: function(data){
                        layer.alert(data.info,{
                            icon: 2,
                            skin: 'layer-ext-moon'
                        });
                    }
                });
            });
        }

        /** 导出列表 */
        function downloadCityInfoReport() {
            if(dataTables._fnGetDataMaster().length < 1){
                layer.alert("没有数据",{icon:5});
                return;
            }
            var formData = "";
            $('#searchFrom').find('input[name]').each(function(){
                formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
            });
//            $('#searchFrom').find('select[name]').each(function(){
//                formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
//            });
            formData = formData.substring(1);
            window.open("<c:url value='/cityMgr/downloadCityInfo'/>?" + formData);
        }
    </script>
</head>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="text-c">
        <form id="searchFrom">
            <div class="row cl">
                城市名称：
                <input type="text" class="input-text" style="width:186px" placeholder="请输入城市名称" id="cname" name="cname"/>&nbsp;&nbsp;&nbsp;&nbsp;
                城市区号：
                <input type="text" class="input-text" style="width:186px" placeholder="请输入城市区号" id="areaCode" name="areaCode"/>&nbsp;&nbsp;&nbsp;&nbsp;
                城市编号：
                <input type="text" class="input-text" style="width:186px" placeholder="请输入城市编号" id="ccode" name="ccode"/>&nbsp;&nbsp;&nbsp;&nbsp;
                所属客户：
                <select class="input-text cl-1" id="companyName" name="companyName" datatype="*"  style="width: 200px;"></select>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="hidden" id="sid" name="sid"/>
                所属省份：
                <select id="province" name="province" class="input-text cl-1"  style="width:200px;height: 31px">
                   <%--<c:forEach items="${provinceDic}" var="province">
                        <option  id="${province.id}" value="${province.pcode}">${province.pname}</option>
                    </c:forEach>--%>
                </select>
                <input type="hidden" id="pcode" name="pcode"/>
                <button type="button" class="btn btn-success" id="searchButton"/><i class="Hui-iconfont">&#xe665;</i>查询</button>
            </div>

        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:void(0);" onclick="openDialog('添加城市','<c:url value="/cityMgr/addCity"/>','800','400')" class="btn btn-primary radius">
                <i class="Hui-iconfont">&#xe600;</i> 添加城市
            </a>
        </span>
        <span class="r">
            <a href="javascript:void(0);" onclick="downloadCityInfoReport();" class="btn btn-primary radius r">
                <i class="Hui-iconfont">&#xe600;</i> 导出
            </a>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr>
                    <th scope="col" colspan="9">城市管理列表</th>
                </tr>
                <tr class="text-c">
                    <th width="5%">序号</th>
                    <th width="15%">城市</th>
                    <th width="20%">所属省份</th>
                    <th width="15%">城市区号</th>
                    <th width="15%">城市编号</th>
                    <th width="15%">所属客户</th>
                    <th width="15%">操作</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
