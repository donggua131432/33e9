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
    <title>客户管理列表</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>

    <script type="text/javascript">
        var dataTables = "", search_param = {}, provinceCityArray = {}, tradeTypeArray={}, tradeTypeOption="", businessTypeArray={}, businessTypeOption="";
        $(function(){
            //dataTablesAjax 选项
            var dataTablesAjax = {
                url: '<c:url value='/customerMgr/pageCustomerList'/>',
                type: 'POST',
                "data": function(d){
                    var _page = datatables_ajax_data(d);
                    return $.extend({},_page, search_param);
                }
            };

            // columns 列选项 和表头对应，和后台传回来的数据属性对应
            var columns = [
                {data: 'rowNO'},
                {data: 'create_time'},
                {data: 'account_id'},
                {data: 'customer_name'},
                {data: 'trade'},
                {data: 'provinceCity'},
                {data: 'business_type'},
                {data: 'signed'},
                {data: 'customer_manager'},
                {data: 'operation'}
            ];

            // 对数据进行处理
            var columnDefs = [
                {"targets": [1],"data": "id","sClass" : "text-c",
                    "render": function(data, type, full) {
                        if (full.create_time) {
                            return o.formatDate(full.create_time, "yyyy-MM-dd hh:mm:ss");
                        }
                        return full.create_time;
                    }
                },
                {"targets": [4],"data": "id","sClass" : "text-c",
                    "render": function(data, type, full) {
                        if(full.trade){
                            return tradeTypeArray[full.trade];
                        }
                        return "";
                    }
                },
                {"targets": [5],"data": "id","sClass" : "text-c",
                    "render": function(data, type, full) {
                        var provinceCityStr = "";
                        if(full.province){
                            provinceCityStr += provinceCityArray[full.province];
                        }
                        if(full.city){
                            provinceCityStr += provinceCityArray[full.city];
                        }
                        return provinceCityStr;
                    }
                },
                {"targets": [6],"data": "id","sClass" : "text-c",
                    "render": function(data, type, full) {
                        var businessTypeStr = "",businessTypeArr="";
                        if(full.business_type){
                            businessTypeArr = full.business_type.split(",");
                            $.each(businessTypeArr,function(i,item){
                                if(i == (businessTypeArr.length-1)){
                                    businessTypeStr += businessTypeArray[item];
                                }else{
                                    businessTypeStr += businessTypeArray[item]+",";
                                }
                            })
                        }
                        return businessTypeStr;
                    }
                },
                {
                    "targets": [7],
                    "sClass" : "text-c",
                    "render": function(data, type, full) {
                        if(data == '00') {
                            return "未签约";
                        }
                        if(data == '01') {
                            return "签约中";
                        }
                        if(data == '02') {
                            return "已签约";
                        }
                        if(data == '03') {
                            return "终止的";
                        }
                        return "";
                    }
                },
                {"targets": [8],"data": "id","sClass" : "text-c",
                    "render": function(data, type, full) {
                        if(full.trade){
                            return full.customer_manager;
                        }
                        return "";
                    }
                },
                {"targets": [9], "data": "id", "sClass" : "text-c",
                    "render": function(data, type, full) {
                        return  '<a title="编辑" href="javascript:void(0);" onclick="openDialog(\'编辑客户信息\',\'<c:url value="/customerMgr/customerEditView?id='+full.id+'&operationType=edit"/>\',\'1000\',\'550\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: green;">&#xe6df;</i>编辑</a>'+
                                 '<a title="查看" href="javascript:void(0);" onclick="openDialog(\'查看客户信息\',\'<c:url value="/customerMgr/customerEditView?id='+full.id+'&operationType=view"/>\',\'1000\',\'550\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: green;">&#xe695;</i>查看</a>'+
                                 '<a title="删除" href="javascript:void(0);" onclick="delNumberBlack(\''+full.id+'\',\''+full.customer_name+'\')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont" style="font-size: 18px; color: red;">&#xe6e2;</i>删除</a>';
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
                "dom": 'rtilp',
                "sErrMode": 'none',
                "columns": columns,
                ajax: dataTablesAjax,
                "columnDefs":columnDefs
            });

            // 搜索功能
            $("#searchButton").on("click", function(){
                var formData = {};
                $('#searchFrom').find('input[name]').each(function(){
                    formData["params["+$(this).attr('name')+"]"]=$(this).val().trim();
                });
                $('#searchFrom').find('select[name]').each(function(){
                    formData["params["+$(this).attr('name')+"]"]=$(this).val().trim();
                });
                search_param = formData;
                dataTables.fnDraw();
            });


            //省市信息
            <c:forEach items="${provinceCityDic}" var="provinceCity">
                provinceCityArray["${provinceCity.code}"] = "${provinceCity.name}";
            </c:forEach>

            //所属行业类型
            <c:forEach items="${tradeTypeDic}" var="tradeType">
                tradeTypeArray["${tradeType.code}"] = "${tradeType.name}";
                tradeTypeOption += '<option value="${tradeType.code}">${tradeType.name}</option>';
            </c:forEach>
            $("#trade").append(tradeTypeOption);

            //合作业务类型
            <c:forEach items="${coopBusinessTypeDic}" var="businessType">
                businessTypeArray["${businessType.code}"] = "${businessType.name}";
                businessTypeOption += '<option value="${businessType.code}">${businessType.name}</option>';
            </c:forEach>
            $("#businessType").append(businessTypeOption);

            //省市级联
            cascadeArea("province","city","<c:url value='/dicdata/pid'/>",true);
        });

        /** 删除号码黑名单 **/
        function delNumberBlack(id,customer_name){
            layer.confirm('记录一旦删除将不可以恢复。</br>确定要删除【'+ customer_name +'】吗？', {
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    type:"post",
                    url:"<c:url value='/customerMgr/deleteCustomer'/>",
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
        function downloadNumBlackReport() {
            if(dataTables._fnGetDataMaster().length < 1){
                layer.alert("没有数据",{icon:5});
                return;
            }
            var formData = "";
            $('#searchFrom').find('input[name]').each(function(){
                formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
            });
            $('#searchFrom').find('select[name]').each(function(){
                formData += "&params["+$(this).attr('name')+"]=" + $(this).val().trim();
            });
            formData = formData.substring(1);
            window.open("<c:url value='/customerMgr/downloadCustomerInfo'/>?" + formData);
        }
    </script>
</head>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="text-c">
        <form id="searchFrom">
            Account ID：
            <input type="text" class="input-text" style="width:280px" placeholder="请输入客户ID" id="accountId" name="accountId"/>
            客户名称：
            <input type="text" class="input-text" style="width:186px" placeholder="请输入客户名称" id="customerName" name="customerName"/>
            所属行业：
            <select id="trade" name="trade" class="select" style="width:200px;height: 31px">
                <option value="">全部</option>
            </select></br></br>
            客户所在地：
            <select id="province" name="province" class="select" style="width:200px;height: 31px">
                <option value="">全部</option>
                <c:forEach items="${provinceDic}" var="province">
                    <option id="${province.id}" value="${province.code}">${province.name}</option>
                </c:forEach>
            </select>
            <select id="city" name="city" class="select" style="width:200px;height: 31px">
                <option value="">全部</option>
            </select>
            客户经理：
            <input type="text" class="input-text" style="width:280px" placeholder="请输入客户经理姓名" id="customerManager" name="customerManager"/>
            合作业务类型：
            <select id="businessType" name="businessType" class="select" style="width:200px;height: 31px">
                <option value="">全部</option>
            </select>
            <button type="button" class="btn btn-success" id="searchButton"/><i class="Hui-iconfont">&#xe665;</i>查询</button>
        </form>
    </div>
    <div class="cl pd-5 bg-1 bk-gray mt-20">
        <span class="l">
            <a href="javascript:void(0);" onclick="openDialog('新增客户','<c:url value="/customerMgr/addCustomer"/>','1000','550')" class="btn btn-primary radius">
                <i class="Hui-iconfont">&#xe600;</i> 新增客户
            </a>
        </span>
        <span class="r">
            <a href="javascript:void(0);" onclick="downloadNumBlackReport();" class="btn btn-primary radius r">
                <i class="Hui-iconfont">&#xe600;</i> 导出
            </a>
        </span>
    </div>
    <div class="mt-20">
        <table class="table table-border table-bordered table-bg table-hover table-sort">
            <thead>
                <tr>
                    <th scope="col" colspan="10">客户管理列表</th>
                </tr>
                <tr class="text-c">
                    <th>序号</th>
                    <th>创建时间</th>
                    <th>Account ID</th>
                    <th>客户名称</th>
                    <th>所属行业</th>
                    <th>客户所在地</th>
                    <th>合作业务类型</th>
                    <th>签约状态</th>
                    <th>客户经理</th>
                    <th>操作</th>
                </tr>
            </thead>
        </table>
    </div>
</div>
</body>
</html>
