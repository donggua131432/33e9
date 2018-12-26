<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>区域管理页面</title>
    <!--<link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">-->
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui.min.css" />
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]-->
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/html5shiv.min.js"></script>
    <!--[endif]-->
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/laydate/laydate.js"></script>
    <!--<link rel="stylesheet" href="css/sui-append.min.css"/>
    <link rel="stylesheet" href="../css/my.css"/>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
    <script id="ccareacityTemplate" type="text/x-jquery-tmpl">
		<tr class="date_hg">
			<td>\${num}</td>
			<td>\${companyName}</td>
			<td>\${aname}</td>
			<td>\${remark}</td>
			<td>
				<button type="button" onclick="openDialog('编辑区域信息','<c:url value="/smartCloud/traffic/ccareacity/toEditArea?areaId=\${areaId}"/>','800','700')">编辑区域</button>
				<button type="button" onclick="todeleteArea('\${areaId}','\${aname}')">删除区域</button>
			</td>
		</tr>
	</script>
    <script type="text/javascript" >
        $(function(){
            $("#ccareacityTable").page({
                url:"<c:url value='/smartCloud/traffic/ccareacity/pageCcArea'/>",
                pageSize:30,
                integralBody:"#ccareacityTbody",// 将模版加入到某个元素的ID
                integralTemplate:"#ccareacityTemplate",// tmpl中的模版ID
                pagination:"#ccareacityPagination", // 分页选项容器
                dataRowCallBack:dataRowCallBack
            });
        });

        function dataRowCallBack(row,num){
            //序号列
            row.num = num;
            return row;
        }

        //查询
        function searchCcDispatch(){
            var formData = {};
            $("#searchForm").find(":input[id]").each(function(){
                formData["params["+$(this).attr('id')+"]"] = $.trim($(this).val());
            });
            $("#ccareacityTable").reload(formData);
        }

        /** 导出记录 **/
        function downloadCcDispatch() {
            var formData = "";
            $('#searchForm').find(':input[name]').each(function(){
                formData += "&params["+$(this).attr('name')+"]=" + $.trim($(this).val());
            });
            formData = formData.substring(1);
            window.open("<c:url value='/smartCloud/traffic/ccareacity/downloadareacity'/>?"+formData);
        }

        var areaid;
        function todeleteArea(id,aname){
            $(".delAreaName").find("span").html(aname);
            $("#shadeWin").show();
            $("#delTemplate").show();
            areaid=id;
        }

        function closeDiv(divid){
            $('#' + divid ).hide();
            $('#shadeWin').hide();
        }

        //删除区域
        function deleteArea(){
            var areaId = areaid;
            $.ajax({
                type:"post",
                url:"<c:url value="/smartCloud/traffic/ccareacity/deleteArea"/>",
                dataType:"json",
                async:false,
                data:{areaId:areaId},
                success : function(result) {
                    if(result.code == 0){
                        $("#ccareacityTable").reload();
                        closeDiv('delTemplate');
                        openMsgDialog(result.msg);
                    }else if(result.code == 1){
                        $("#delTemplate").hide();
                        $("#delNotTemplate").show();
                        showUsedDispatch(result.data);
                    }else {
                        openMsgDialog(result.msg);
                    }
                },
                error:function(){
                    openMsgDialog("数据请求失败！");
                }
            });
        }

        function showUsedDispatch(data){
            $("#box").empty();
            var datareturn = data;
            console.log(datareturn);
            var array = datareturn.split(",");
            console.log(array);
            for(var i=0;i<array.length;i++){
                if(array.length%2==0){
                    if(i%2==0){
                        $("#box").append($('<p><span>'+array[i]+'</span>、<span>'+array[i+1]+'</span></p>'));
                    }
                }else{
                    if(i==array.length-1){
                        $("#box").append($('<p><span>'+array[i]+'</span></p>'));
                    }else if(i%2==0&&i!=array.length-1) {
                        $("#box").append($('<p><span>'+array[i]+'</span>、<span>'+array[i+1]+'</span></p>'));
                    }

                }
            }
        }
    </script>
</head>
<body>
<!--遮罩start-->
<div id="shadeWin" class="shade_on_win" style="display: none;"></div>
<!--遮罩end-->
<!-- header start -->
<jsp:include page="../common/controlheader.jsp"/>
<!-- header end -->
<section>
    <div id="sec_main">
        <!-- 左侧菜单 start -->
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('zn','smart_cloud','ccareacity');</script>
        <!-- 左侧菜单 end -->
        <div class="main3">
            <!--消息显示-->
            <div class="modal-box  phone_t_code" id="showMsgDialog" style="display: none;">
                <div class="modal_head">
                    <h4>消息提示</h4>
                    <div class="p_right"><a href="javascript:void(0);" onclick="closeMsgDialog();" class="common_icon close_txt"></a></div>
                </div>
                <div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
                    <div class="app_delete_main" style="height: 50px; padding: 20px;">
                        <p class="app_delete_common"><i class="attention"></i><span id="alertMsg"></span></p>
                    </div>
                </div>
                <div class="mdl_foot" style="margin-left:220px;">
                    <button type="button" onclick="closeMsgDialog();" class="true">确&nbsp;&nbsp;定</button>
                </div>
            </div>
            <div class="container7">
                <div class="msg">
                    <h3>区域管理</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="demand">
                    <form id="searchForm">
                        <span class="c7_common">区域名称</span>
                        <input class="l_input_style" type="text" name="aname" id="aname"/>
                        <button type="button" class="md_btn marLeft90" onclick="searchCcDispatch();">查询</button>
                    </form>
                </div>

                <div class="date clear">
                    <button type="button" class="addDemand" onclick="openDialog('添加区域','<c:url value="/smartCloud/traffic/ccareacity/toAddArea"/>','800','700')">添加区域</button>
                    <div class="record_down f_right" style="margin-top:8px;">
                        <span class="open_down_billed" onclick="downloadCcDispatch();"><i class="common_icon download_txt"></i>导出报表</span>
                    </div>
                    <table class="table_style" id="ccareacityTable">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>所属企业</th>
                            <th>区域名称</th>
                            <th>区域描述</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="ccareacityTbody">

                        </tbody>
                    </table>
                    <div class="f_right app_sorter"  id="ccareacityPagination"></div>
                </div>
               </div>
                <!--删除区域-->
                <div class="modal-box  phone_t_code" id="delTemplate" style="display: none;">
                    <div class="modal_head">
                        <h4>删除区域</h4>
                        <div class="p_right"><a href="#" class="common_icon close_txt" onclick="closeDiv('delTemplate');"></a></div>
                    </div>
                    <div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
                        <div class="app_delete_main">
                            <p class="app_delete_common"><i class="attention"></i>确定要删除该区域？</p>
                            <p class="delAreaName">区域名称：<span></span></p>
                        </div>
                    </div>
                    <div class="mdl_foot" style="margin-left:140px;">
                        <button type="button" class="true" onclick="deleteArea()">确&nbsp;&nbsp;认</button>
                        <button type="button" class="false" onclick="closeDiv('delTemplate');">取&nbsp;&nbsp;消</button>
                    </div>
                </div>
            <!--删除区域-->
            <div class="modal-box  phone_t_code" id="delNotTemplate" style="display: none;">
                <div class="modal_head">
                    <h4>删除区域</h4>
                    <div class="p_right"><a href="#" class="common_icon close_txt" onclick="closeDiv('delNotTemplate');"></a></div>
                </div>
                <div class="modal-common_body mdl_pt_body" style="height:300px;overflow:auto">
                    <div class="app_delete_main" style="margin-left:-15px;margin-right:20px;">
                        <p class="app_delete_common"><i class="attention"></i>该区域被以下话务调度引用，无法删除！</p>
                        <div id="box"></div>
                    </div>
                </div>
                <div class="mdl_foot" style="margin-left:140px;">
                    <button type="button" class="true" onclick="closeDiv('delNotTemplate');">确&nbsp;&nbsp;认</button>
                    <button type="button" class="false" onclick="closeDiv('delNotTemplate');">取&nbsp;&nbsp;消</button>
                </div>
            </div>
            </div>
        </div>
</section>

</body>
</html>
