<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/3/28
  Time: 14:23
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
    <title>号码管理</title>
    <link rel="stylesheet" href="http://g.alicdn.com/sj/dpl/1.5.1/css/sui.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/sui-append.min.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/css/my-new.css"/>

    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/common/common.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/my-new.js"></script>
    <script type="text/javascript">
        $(function(){
            whetherAddNumber(${pendingCount});
        })

        //设置添加窗口的显示和隐藏
        function whetherAddNumber(pendingCount){
            if(pendingCount == 0){
                $("div[class='detailMain1']").show();
                $("div[class='numChecking']").hide();
            }else{
                $(".detailBox1").off("click");
                $(".detailBox1").removeClass("checkDetailB").addClass("smAddNumber");
                $("div[class='detailMain1']").hide();
                $("div[class='numChecking']").show();
            }
        }

        //打开弹出框
        function openNumberDialog(openType){
            var dialogStatus = true;
            if(openType == "delNum"){
                if($("#auditPassDiv").find("p[class='clearNumHover']").length == 0){
                    dialogStatus = false;
                    $("#displayNumDiv").show().fadeOut(3000);
                }else if(openType == "clearAllNum"){
                    dialogStatus = true;
                }
            }

            if(dialogStatus == true){
                $("#shadeWin").show();
                $("#"+openType).show();
            }
        }
        //关闭弹出框
        function colseNumberDialog(openType){
            if(openType == "localSubmission"){
                $("#errorNumTable").text("").show();
                $("#errorAllNumTable").text("").hide();
            }
            $("#shadeWin").hide();
            $("#"+openType).hide();
        }

        //添加号码
        function addNumber(obj,numberType){
            //去除重复提交
            if (typeof($(obj).attr("addNum")) == "undefined"){
                $(obj).attr("addNum","");
                //获取已经添加的号码池
                var numberArray = newAddNumberPool();
                //判断是单个号码提交还是号码段提交
                if(numberType == "single") {
                    $(obj).attr("addNum");
                    var number = $("#number").val().trim();
                    if (number == "") {
                        $("#singleDiv").text("号码不允许为空");
                    } else if (number.length < 5) {
                        $("#singleDiv").text("号码位数不能小于5位");
                    } else if ($.inArray(number, numberArray) < 0) {
                        $("div[class='fourNumberLeft fourNum1']").append("<p numbertype='01'>" + number + "</p>");
                        $("#number").val("");
                        $("#singleDiv").text("");
                    } else {
                        $("#singleDiv").text("此号码已添加");
                    }
                }else if(numberType == "section") {
                    var numberStart = $("#numberStart").val().trim(), numberEnd = $("#numberEnd").val().trim(),
                            newNumArray = [], strNum = "", existNum = [], diffNum = 1000,
                            strNumStart = numberStart.substring(0,numberStart.length-4),
                            strNumEnd = numberEnd.substring(0,numberEnd.length-4);
                    if (numberStart == "") {
                        $("#sectionDiv").text("开始号码不允许为空");
                    }else if(numberStart.length < 5){
                        $("#sectionDiv").text("开始号码位数不能小于5位");
                    }else if(numberEnd == "") {
                        $("#sectionDiv").text("结束号码不允许为空");
                    }else if(numberEnd.length < 5){
                        $("#sectionDiv").text("结束号码位数不能小于5位");
                    }else if(strNumStart == strNumEnd){
                        diffNum = parseInt(numberEnd.substr(numberEnd.length-4,4))-parseInt(numberStart.substr(numberStart.length-4,4));
                        if(diffNum > 0 && diffNum <= 999){
                            //比较新添加的号码段中的号码是否已经添加过
                            newNumArray = sectionToArrayNum(numberStart+"-"+numberEnd);
                            $.each(newNumArray,function(index,val){
                                //判断该号码是否存在数组中
                                if($.inArray(val,numberArray) >= 0){
                                    existNum.push(val);
                                }
                            })
                            //如果数组中没有值表示没有重复号码
                            if(existNum.length <= 0){
                                $("div[class='fourNumberLeft fourNum1']").append("<p numbertype='02'>"+numberStart+"-"+numberEnd+"</p>");
                                $("#numberStart, #numberEnd").val("");
                                $("#sectionDiv").text("");
                            }else{
                                /*$.each(existNum,function(index, val){
                                    if((index+1)%5 == 0){
                                        strNum += val+"\n";
                                    }else{
                                        strNum += val+"   ";
                                    }
                                });*/
                                $("#sectionDiv").text("此号段存在重复添加的号码");
                            }
                        }else if(diffNum > 999){
                            $("#sectionDiv").text("两个号码区间跨度不能大于999个");
                        }else{
                            $("#sectionDiv").text("结尾号码要大于开始号码");
                        }
                    }else{
                        $("#sectionDiv").text("两个号码不在同一个号码区段");
                    }
                }
                $(obj).removeAttr("addNum");
            }
        }

        //获取新添加的号码池中的号码
        function newAddNumberPool(){
            var numberPool = [];
            $("div[class='fourNumberLeft fourNum1']").find("p").each(function(){
                if($(this).attr("numbertype") == '02'){
                    //合并数组
                    $.merge(numberPool, sectionToArrayNum($(this).text()));
                }else{
                    numberPool.push($(this).text());
                }
            });
            return numberPool;
        }

        //将区间号码转换成号码数组
        function sectionToArrayNum(sectionNum){
            var sectionNumber = sectionNum.split("-"),
                    //将字符串拆分成数组
                    diffNumArray = sectionNumber[0].split(""),
                    diffStr = "", i=0,j= 0,numberArray = [],diffNum = 1000,diffLength= 0,diffNumStr="",
                    strNumStart = sectionNumber[0].substring(0,sectionNumber[0].length-4),
                    strNumEnd = sectionNumber[1].substring(0,sectionNumber[1].length-4),
                    intNumStart = parseInt(sectionNumber[0].substr(sectionNumber[0].length-4,4));

            if(strNumStart == strNumEnd){
                diffNum = parseInt(sectionNumber[1].substr(sectionNumber[1].length-4,4))-intNumStart;
            }

            //两个号码之间的跨度要大于零小于等于999
            if(diffNum > 0 && diffNum <= 999){
                //获取号码前的零
                if(diffNumArray.length <= 4){
                    for (j; j<diffNumArray.length; j++ ){
                        if(diffNumArray[j] == "0"){
                            diffStr += "0";
                        }else{
                            break;
                        }
                    }
                }

                //拼接号码字符串
                for (i; i<= diffNum; i++){
                    if(sectionNumber[0].length <= 4){
                        numberArray.push(diffStr+(intNumStart+i).toString());
                    }else{
                        diffLength = 4-(intNumStart+i).toString().length;
                        if(diffLength == 1){
                            diffNumStr = "0"+(intNumStart+i).toString();
                        }else if(diffLength == 2){
                            diffNumStr = "00"+(intNumStart+i).toString();
                        }else if(diffLength == 3){
                            diffNumStr = "000"+(intNumStart+i).toString();
                        }else{
                            diffNumStr = (intNumStart+i).toString();
                        }
                        numberArray.push(strNumStart+diffNumStr);
                    }
                }
            }
            //console.log(numberArray);
            return numberArray;
        }

        //移除添加的临时号
        function deleteTemporaryNumber(){
            var selectLength = $("div[class='fourNumberLeft fourNum1']").find("p[class='addNumHover']");
            if(selectLength.length > 0){
                selectLength.remove();
            }else{
                $("#addNumDiv").text("请选择要移除的号码").show().fadeOut(3000);
            }
        }

        //批量提交号码
        function submitNumber(obj){
            //去除重复提交
            if (typeof($(obj).attr("subNum")) == "undefined"){
                $(obj).attr("subNum","");

                var numberPool = [],
                     alertNum = "", appNumberData = [], appNumber={},submitStatus=true,repeatNumber="";

                $("div[class='fourNumberLeft fourNum1']").find("p").each(function(){
                    appNumber = {};
                    appNumber["appid"] = "${appid}";
                    appNumber["number"] = $(this).text();
                    appNumber["numberType"] = $(this).attr("numbertype");
                    appNumberData.push(appNumber);
                    //获取已经添加的号码池
                    $(this).attr("numbertype") == '02' ? $.merge(numberPool, sectionToArrayNum($(this).text())) : numberPool.push($(this).text());
                });

                if(appNumberData.length > 0 && submitStatus == true){
                    $.ajax({
                        type:"post",
                        url:"<c:url value='/numMgr/submitAppNumber'/>",
                        dataType:"json",
                        async:false,
                        data:{'appid':"${appid}",numberArrayStr:numberPool.join(","),'appNumberJsonStr':JSON.stringify(appNumberData)},
                        success : function(data) {
                            if(data.status == 0){
                                if(data["numberData"].length > 0) {
                                    $.each(data["numberData"], function (index, val) {
                                        if((index + 1) <= 7){
                                            if ((index + 1) % 2 == 0) {
                                                alertNum += "<td width='255'>"+val.number+"</td></tr>";
                                            } else {
                                                alertNum += "<tr><td width='255'>"+val.number+"</td>";
                                            }
                                        }

                                        if ((index + 1) % 2 == 0) {
                                            repeatNumber += "<td width='255'>"+val.number+"</td></tr>";
                                        } else {
                                            repeatNumber += "<tr><td width='255'>"+val.number+"</td>";
                                        }
                                    });
                                    $("#errorNumSpan").text(data["numberData"].length);
                                    if(data["numberData"].length > 7){
                                        $("#errorNumTable").append(alertNum+"<td><a href='javascript:void(0);' onclick='showRepeatNumber()' class='checkMore'>查看更多&gt;&gt;</a></td></tr>");
                                        $("#errorAllNumTable").append(repeatNumber+"</tr>");
                                    }else{
                                        $("#errorNumTable").append(alertNum+"</tr>");

                                    }
                                    openNumberDialog("localSubmission");
                                }else{
                                    //向待审核栏中添加号码
                                    $("div[class='fourNumberLeft fourNum1']").find("p").each(function(){
                                        $("div[class='fourNumberLeftWAIT']").append("<p>"+$(this).text()+"</p>");
                                    });
                                    //隐藏添加信息框
                                    var countNum = $("div[class='fourNumberLeft fourNum1']").find("p").length;
                                    whetherAddNumber(countNum);
                                    $("span[class='numberAmount blueColor']").text(countNum);
  //                                alert(data.info);
                                    $("#shadeWin").show();
                                    $("#msgDialog2").show();
                                }
                            }else if(data.status == 1){
                                //向待审核栏中添加号码
                                $("div[class='fourNumberLeft fourNum1']").find("p").each(function(){
                                      $("div[class='fourNumberLeftWAIT']").append("<p>"+$(this).text()+"</p>");
                                });
                                //隐藏添加信息框
                                var countNum = $("div[class='fourNumberLeft fourNum1']").find("p").length;
                                whetherAddNumber(countNum);
                                $("span[class='numberAmount blueColor']").text(countNum);
//                                alert(data.info);
                                $("#shadeWin").show();
                                $("#msgDialog").show();
                            }
                        },
                        error: function(){
                            console.log("数据请求失败！");
                        }
                    });
                }else{
                    $("#addNumDiv").text("请先添加号码后提交").show().fadeOut(3000);
                }
                $(obj).removeAttr("subNum");
            }
        }

        //显示所有号码
        function showRepeatNumber(){
            $("#errorNumTable").hide();
            $("#errorAllNumTable").show();
        }

        //查看重复号码详情
        /*function getRepeatDetail(){
            var numberPool = [];
            $("div[class='fourNumberLeft fourNum1']").find("p").each(function(){
                //获取已经添加的号码池
                $(this).attr("numbertype") == '02' ? $.merge(numberPool, sectionToArrayNum($(this).text())) : numberPool.push($(this).text());
            });
            $("#repeatForm").find("input[name='numberArrayStr']").val(numberPool.join(","));
            $("#repeatForm").submit();
        }*/


        //清空全部 移除号码
        function deleteAuditPasNumber(deleteType){
            if(deleteType == "delete"){
                var numElement = $("#auditPassDiv").find("p[class='clearNumHover']"),numId = "",spanElement = $("span[class='numberAmount greenColor']");
                if(numElement.length > 0){
                    numId = numElement.attr("appNumberId");
                    $.ajax({
                        type:"post",
                        url:"<c:url value='/numMgr/deleteAppNumber'/>",
                        dataType:"json",
                        data:{'numId':numId},
                        success : function(data) {
                            if(data.status == "1"){
                                numElement.remove();
                                if(numElement.text().indexOf("-")>0){
                                    spanElement.text(parseInt(spanElement.text())-sectionToArrayNum(numElement.text()).length);
                                }else{
                                    spanElement.text(parseInt(spanElement.text())-numElement.length);
                                }
                                colseNumberDialog('delNum');
                            }
                        },
                        error : function(data){
                            console.log("数据请求失败！");
                        }
                    });
                }else{
                    $("#displayNumDiv").show().fadeOut(3000);
                }
            }else if(deleteType == "clear"){
                $.ajax({
                    type:"post",
                    url:"<c:url value='/numMgr/clearAppNumber'/>",
                    dataType:"json",
                    data:{'appid':"${appid}"},
                    success : function(data) {
                        if(data.status == "1"){
                            $("#auditPassDiv").html("");
                            $("span[class='numberAmount greenColor']").text(0);
                            colseNumberDialog('clearAllNum');
                        }
                    },
                    error : function(data){
                        console.log("数据请求失败！");
                    }
                });
            }
        }
        //关闭消息框
        function closeMsgDialog(){
            $("#shadeWin").hide();
            $("#msgDialog").hide();
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
        <script type="text/javascript">showSubMenu('kf','appMgr','numMgr');</script>
        <!-- 左侧菜单 end -->

        <div class="main3">
            <!--移除号码-->
            <div class="modal-box  phone_t_code" id="delNum" style="display: none;">
                <div class="modal_head">
                    <h4>移除号码</h4>
                    <div class="p_right"><a href="javascript:void(0);" onclick="colseNumberDialog('delNum');" class="common_icon close_txt"></a></div>
                </div>
                <div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
                    <div class="app_delete_main">
                        <p class="app_delete_common"><i class="attention"></i>该号码一旦删除后，将不能恢复</p>
                        <p class="app_delete_common">确定要删除该号码？</p>
                    </div>
                </div>
                <div class="mdl_foot" style="margin-left:125px;">
                    <button type="button" onclick="deleteAuditPasNumber('delete');" class="true">确&nbsp;&nbsp;认</button>
                    <button type="button" onclick="colseNumberDialog('delNum');" class="false">取&nbsp;&nbsp;消</button>
                </div>
            </div>
            <!--查询弹层-->
            <div id="msgDialog" class="modal-box  phone_t_code" style="display: none;">
                <div class="modal_head">
                    <h4>号码管理</h4>
                    <div class="p_right"><a href="javascript:void(0);" onclick="closeMsgDialog();" class="common_icon close_txt"></a></div>
                </div>
                <div class="modal-common_body mdl_pt_body">
                    <div class="test_success test_success_left">
                        <i class="attention"></i>
                        <span>您的号码提交成功！</span>
                    </div>
                </div>
            </div>
            <div id="msgDialog2" class="modal-box  phone_t_code" style="display: none;">
                <div class="modal_head">
                    <h4>号码管理</h4>
                    <div class="p_right"><a href="javascript:void(0);" onclick="closeMsgDialog();" class="common_icon close_txt"></a></div>
                </div>
                <div class="modal-common_body mdl_pt_body">
                    <div class="test_success test_success_left">
                        <i class="attention"></i>
                        <span>号码已提交，请耐心等待审核！</span>
                    </div>
                </div>
            </div>
            <!--清空全部号码-->
            <div class="modal-box  phone_t_code" id="clearAllNum" style="display: none;">
                <div class="modal_head">
                    <h4>清空全部号码</h4>
                    <div class="p_right"><a href="javascript:void(0);" onclick="colseNumberDialog('clearAllNum');" class="common_icon close_txt"></a></div>
                </div>
                <div class="modal-common_body mdl_pt_body" style="margin-left:-15px;">
                    <div class="app_delete_main">
                        <p class="app_delete_common"><i class="attention"></i>号码一旦清空后，将不能恢复</p>
                        <p class="app_delete_common">确定要清空全部号码？</p>
                    </div>
                </div>
                <div class="mdl_foot" style="margin-left:125px;">
                    <button type="button" onclick="deleteAuditPasNumber('clear');" class="true">确&nbsp;&nbsp;认</button>
                    <button type="button" onclick="colseNumberDialog('clearAllNum');" class="false">取&nbsp;&nbsp;消</button>
                </div>
            </div>
            <!--批量提交号码-->
            <div class="modal-box  phone_t_code" id="localSubmission" style="width:600px; height: 400px; display: none;">
                <div class="modal_head">
                    <h4>批量提交</h4>
                    <div class="p_right" style="left:570px"><a href="javascript:void(0);" onclick="colseNumberDialog('localSubmission');" class="common_icon close_txt"></a></div>
                </div>
                    <div class="modal-common_body mdl_pt_body" style="position:relative; height:240px; width: 548px; overflow-y:auto;">
                    <p class="app_delete_common"><i class="attention"></i>发现<span id="errorNumSpan">0</span>个问题号码</p>
                    <table class="numTable" id="errorNumTable"></table>
                    <table class="numTable" id="errorAllNumTable" style="display: none;"></table>

                    <form action="<c:url value='/numMgr/getRepeatList'/>" id="repeatForm" method="post" style="display: none;">
                        <input type="hidden" name="appid" value="${appid}"/>
                        <input type="hidden" name="numberArrayStr" value=""/>
                    </form>

                    <div class="questionReason">
                        <span>问题原因:</span>
                        <p>以上号码，可用显号池中已存在，不可以重复提交，请修改后重新提交，谢谢！</p>
                    </div>
                </div>
                <div class="mdl_foot" style="margin:0 240px;">
                    <button type="button" onclick="colseNumberDialog('localSubmission');" class="true">返&nbsp;&nbsp;回</button>
                </div>
            </div>


            <div class="container">
                <div class="msg">
                    <h3>号码管理>${appName}</h3>
                    <jsp:include page="../common/message.jsp"/>
                </div>

                <div class="numberVideoShow">
                    <div class="app_note">
                        <span class="WarmPrompt">温馨提醒：</span>
                        <p>1.号码审核需企业提供如下资料：提供《号码的开户证明》和《授权玖云平台使用的授权书》，打印后加盖企业公章，提供清晰扫描件。</p>
                        <p>2.资料以邮件的形式发送至玖云平台官方服务邮箱：support@33e9cloud.com</p>
                        <p>3.邮件格式：邮件主题：“号码审核”+公司名称+开发者注册邮箱，详情请见<a href="<c:url value='/api/PlatformAudit'/>" >平台审核规范</a>。</p>
                    </div>
                    <!-- 添加号码 -->
                    <div class="f_left videoShow widthSM">
                        <div class="Light_grey">
                            <div class="numCommon">
                                <span class="lg_common_icon lgAdd_txt"></span>
                                <span class="numMangeComFont">添加号码</span>
                            </div>
                            <div class="numBottom">
                                <b class="addNumTxt"></b>
                            </div>
                        </div>
                        <div class="dark">
                            <span>添加号码</span>
                            <b class="detailBox1 smAddNumberADD checkDetailB"></b>
                        </div>

                        <div class="numChecking" style="display: none;">
                            <i class="attention"></i>
                            <span>您的号码正在审核中，请耐</span>
                            <p>心等待……</p>
                        </div>

                        <div class="detailMain1">
                            <div class="numberBox">
                                <div class="numberLeft">
                                    <input type="text" maxlength="30" id="number" onkeyup="inputLimitRule('number',this);" class="numInput numInput1" placeholder="示例:4008006001"/>
                                    <button class="genBtn1 greyBtn" onclick="addNumber(this,'single');">+</button><br/>
                                    <div id="singleDiv" class="numberMsg"></div>
                                    <span class="start">从</span><br/>
                                    <input type="text" maxlength="30" id="numberStart" onkeyup="inputLimitRule('number',this);" placeholder="示例:4008006001" class="numInput numInput2"/><br/>
                                    <span>至</span><br/>
                                    <input type="text" maxlength="30" id="numberEnd" onkeyup="inputLimitRule('number',this);" placeholder="4008006009" class="numInput "/>
                                    <button class="genBtn2 greyBtn" onclick="addNumber(this,'section');">+</button>
                                    <div id="sectionDiv" class="numberMsg"></div>
                                    <div class="operaNumber">
                                        <span onclick="deleteTemporaryNumber();" style="cursor:pointer;"><b class="NumberDeal delNum_txt"></b>移除号码</span>
                                        <span onclick="submitNumber(this);" style="color:#5365FD;cursor:pointer;"><b class="NumberDeal submission_txt"></b>批量提交</span>
                                    </div>
                                </div>
                            </div>
                            <div class="fourNumberShow">
                                <div id="addNumDiv" class="deleteNumberMsg"></div>
                                <div class="fourNumberLeft fourNum1"></div>
                            </div>
                        </div>
                    </div>

                    <!-- 待审核号码 -->
                    <div class="f_left videoShow widthSM">
                        <div class="Light_grey">
                            <div class="numCommon">
                                <b class="common_icon waitCheck_txt"></b>
                                <span class="numMangeComFont">待审核记录</span>
                            </div>
                            <div class="numBottom">
                                <span class="numberAmount blueColor">${pendingCount}</span>
                                <span>条</span>
                            </div>
                        </div>
                        <div class="dark">
                            <span>查看详情</span>
                            <b class="detailBox2 checkDetailALL checkDetailB"></b>
                        </div>
                        <div class="detailMain2 fourNumberShow fourWaitCheckNum">
                            <div class="fourNumberLeftWAIT">
                                <c:forEach items="${pendingList}" var="appNumber">
                                    <p>${appNumber.number}</p>
                                </c:forEach>
                            </div>
                        </div>
                    </div>

                    <!-- 可用显号 -->
                    <div class="f_left videoShow widthSM">
                        <div class="Light_grey">
                            <div class="numCommon">
                                <b class="common_icon userShow_txt"></b>
                                <span class="numMangeComFont">可用显号</span>
                            </div>
                            <div class="numBottom">
                                <span class="numberAmount greenColor">${auditPassCount}</span>
                                <span>个</span>
                            </div>
                        </div>
                        <div class="dark">
                            <span>查看详情</span>
                            <b class="detailBox3 checkDetailALL checkDetailB"></b>
                        </div>
                        <div class="detailMain3">
                            <div class="numberBox numberBoxBg">
                                <div class="numberLeft">
                                    <div class="clearOperaNumber">
                                        <span onclick="openNumberDialog('clearAllNum');" style="cursor:pointer;"><b class="NumberDeal clearNum_txt"></b>清空全部</span>
                                        <span onclick="openNumberDialog('delNum');" style="cursor:pointer;"><b class="NumberDeal delNum_txt" style="margin-left:70px;"></b>移除号码</span>
                                    </div>
                                </div>
                            </div>
                            <div class="fourNumberShow clearNumber">
                                <div id="displayNumDiv" class="deleteNumberMsg">请选择要删除的记录</div>
                                <div class="fourNumberLeft fourNum2" id="auditPassDiv">
                                    <c:forEach items="${auditPassList}" var="appNumber">
                                        <p appNumberId="${appNumber.id}">${appNumber.number}</p>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 审核未通过号码 -->
                    <div class="f_left widthLG">
                        <div class="Light_grey">
                            <div class="numCommon">
                                <b class="common_icon noPass_txt"></b>
                                <span class="numMangeComFont">审核未通过记录</span>
                            </div>
                            <div class="numBottomL">
                                <span class="numberAmount redColor">${auditNoPassCount}</span>
                                <span>条</span>
                            </div>
                        </div>
                        <div class="dark">
                            <span>查看详情</span>
                            <b class="detailBox4 checkDetailALL checkDetailB checkDetailRL"></b>
                        </div>
                        <div class="detailMain4">
                            <div class="noPassNav">
                                <span>号码</span>
                                <span>审核时间</span>
                                <span>备注</span>
                            </div>
                            <div class="showTable">
                                <table class="numberTAB">
                                    <c:forEach items="${auditNoPassList}" var="appNumber">
                                        <tr>
                                            <td style="width:130px;">${appNumber.number}</td>
                                            <td style="width:130px;">${appNumber.reviewtime}</td>
                                            <td><c:out value="${appNumber.remark}"/></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
