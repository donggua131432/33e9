<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/7/12
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="keywords" content="">
    <meta name="description" content="">
    <title>子账号列表</title>
    <link href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>
    <!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <![endif]-->
    <script src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/jquery.validate.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/messages_zh.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/validation/validate-extend.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/laydate/laydate.js"></script>
    <script id="sipRepeaterListTemplate" type="text/x-jquery-tmpl">
        <tr class="date_hg">
            <td>\${num}</td>
            <td>\${create_time}</td>
            <td>\${sub_name}</td>
            <td>\${subid}</td>
            <td>\${num_flag}</td>
            <td>\${fee}</td>
            <td>\${discount}</td>
            <td>\${cycle}</td>
            <td><a href="\${url}">查看</a></td>
        </tr>
    </script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/jquery.tmpl.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/page/paging.js"></script>
    <script type="text/javascript">
        var subInfoArray={};
        var createTime = {
            elem: '#createTime',
            format: 'YYYY-MM-DD hh:mm:ss',
            max: laydate.now(), //设定最大日期为当前日期
            istime: true,
            istoday: true,
            choose: function(datas){
                createTime1.min = datas; //开始日选好后，重置结束日的最小日期
                createTime1.start = datas //将结束日的初始值设定为开始日
            }
        };

        var createTime1 = {
            elem: '#createTime1',
            format: 'YYYY-MM-DD hh:mm:ss',
            max: laydate.now(), //设定最大日期为当前日期
            istime: true,
            istoday: true,
            choose: function(datas){
                createTime.max = datas; //结束日选好后，重置开始日的最大日期
            }
        };


        $(function(){
            laydate(createTime);
            laydate(createTime1);

            //sip应用列表
            subInfoArray = getAppInfo("<c:url value='/sipTrafficStatistics/getSipRelayInfo'/>","subid","subName");

            $("#sipRepeaterListTable").page({
                url:"<c:url value='/sipRepeaterList/getSipRepeaterList'/>",
                pageSize:30,
                integralBody:"#sipRepeaterListTbody",// 将模版加入到某个元素的ID
                integralTemplate:"#sipRepeaterListTemplate",// tmpl中的模版ID
                pagination:"#sipRepeaterListPagination", // 分页选项容器
                param:{"appid":"${appid}"},
                dataRowCallBack:dataRowCallBack
            });
        });



        function dataRowCallBack(row,num) {
            //序号列
            row.num = num;

            if (row.num_flag == "00") {
                row.num_flag = "关闭";
            } else if (row.num_flag == "01") {
                row.num_flag = "开启";
            }

            if(row.cycle){
                if(row.cycle == 6){
                    row.fee = "${sipStandardRate.per6}";
                    row.discount = row.per6_discount/10;
                }else if(row.cycle == 60){
                    row.fee = "${sipStandardRate.per60}";
                    row.discount = row.per60_discount/10;
                }
            }else{
                row.cycle = "${sipStandardRate.cycle}";
                if("${sipStandardRate.cycle}" == 6){
                    row.fee = "${sipStandardRate.per6}";
                    row.discount = new Number("${sipStandardRate.per6Discount/10}").toFixed(0);
                }else if("${sipStandardRate.cycle}" == 60){
                    row.fee = "${sipStandardRate.per60}";
                    row.discount = new Number("${sipStandardRate.per60Discount/10}").toFixed(0);
                }
            }

            if (row.create_time) {
                row.create_time = o.formatDate(row.create_time, "yyyy-MM-dd hh:mm:ss");
            }

            row.url = "<c:url value='/sipRepeaterList/sipRepeaterConfig'/>?subid="+row.subid;
            return row;
        }


        //查询
        function searchSipRepeaterList(){
            var formData = {};
            $("#searchForm").find(":input[id]").each(function(){
                formData["params["+$(this).attr('id')+"]"] = $.trim($(this).val());
            });

            //提示开始时间不能大于结束时间
            if(formData["params[createTime]"] != "" && formData["params[createTime1]"] != ""){
                var createTime = new Date(formData["params[createTime]"].replace(/\-/g, '\/'));
                var createTime1 = new Date(formData["params[createTime1]"].replace(/\-/g, '\/'));
                if(createTime > createTime1){
                    openMsgDialog("查询时间的开始时间不能大于结束时间！");
                    return;
                }
            }

            $("#sipRepeaterListTable").reload(formData);
        }
    </script>
</head>
<body>
<!-- header start -->
<jsp:include page="../common/controlheader.jsp"/>
<!-- header end -->
<section>
    <div id="sec_main">
        <!-- 左侧菜单 start -->
        <jsp:include page="../common/controlleft.jsp"/>
        <script type="text/javascript">showSubMenu('sp','sipMgr','sipRepeaterList');</script>
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

            <div class="container" >
                <div class="msg">
                    <h3>子账号列表</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>
                <div class="application_msg">
                    <form id="searchForm">
                        <div class="app_select">
                            <span class="open_width">
                                <span class="check_font">时间：</span>
                                <input type="text" id="createTime" name="createTime" class="sm_input_style"/>
                                <span class="check_font">至</span>
                                <input type="text" id="createTime1" name="createTime1" class="sm_input_style"/>
                            </span>
                            <span class="check_font call_left">子账号名称：</span>
                            <select id="subid" name="subid">
                                <option selected value="">全部</option>
                            </select>
                            <button type="button" onclick="searchSipRepeaterList();" class="md_btn call_left">查询</button>
                        </div>
                    </form>
                    <!--中继列表-->
                    <div class="application clear">
                        <table class="table_style" id="sipRepeaterListTable">
                            <thead>
                            <tr>
                                <th rowspan="2" style="width:5%">序号</th>
                                <th rowspan="2" style="width:15%">创建时间</th>
                                <th rowspan="2" style="width:15%">子账号名称</th>
                                <th rowspan="2" style="width:27%">子账号&nbsp;ID</th>
                                <th rowspan="2" style="width:7%; letter-spacing: 0px;">随机选号</th>
                                <th colspan="3" style="width:26%">费率</th>
                                <th rowspan="2" style="width:5%">操作</th>
                            </tr>
                            <tr>
                                <th style="letter-spacing: 0px;">标准价(元)</th>
                                <th style="letter-spacing: 0px;">折扣率(%)</th>
                                <th style="letter-spacing: 0px;">计费单位(秒)</th>
                            </tr>
                            </thead>
                            <tbody id="sipRepeaterListTbody">
                                <tr>
                                    <td>1</td>
                                    <td>2016-06-20 01:05:03</td>
                                    <td>中继001</td>
                                    <td>sdtfryht678</td>
                                    <td>是</td>
                                    <td>xxx</td>
                                    <td>xxx</td>
                                    <td>分钟</td>
                                    <td>
                                        <button type="button">查看</button>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="f_right app_sorter"  id="sipRepeaterListPagination"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
