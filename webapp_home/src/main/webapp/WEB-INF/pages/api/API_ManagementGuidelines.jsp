<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>开放接口管理指南</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/API.css"/>
    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>

<jsp:include page="../common/header.jsp"/>

<div class="API_container">
    <div class="main_box">
        <div class="main_box">
            <div class="API_box">
                <div class="API_main1 f_left">
                    <div class="api_left api_left_color">
                        <b class="api_txt"></b>
                        <span class="l_api">所有文档</span>
                    </div>

                    <jsp:include page="../common/api_nav.jsp"/>

                </div>
                <div class="API_main2 f_left" style="clear: both">
                    <div style="position:fixed;width:810px;top:85px;height:36px;background:#ECECEC;"></div>
                    <div id="api_ma2_1">
                        <div class="api_m2"><span>开放接口管理指南</span></div>
                        <div>
                            <div class="api_m2_box API1">
                                <span class="api_com_font">1.应用管理</span>
                                <p class="APImdFont">1.1.创建应用</p>
                                <p>
                                    开发者可以在用户控制中心，为自己的账户创建多个应用。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/createAppOne.png" alt="创建应用图片"/></p>
                                <p>
                                    (1).应用名称<br/>
                                    自定义名称，最长不超过20个汉字<br/>
                                    (2).应用类型<br/>
                                    该项为可选项，用户根据自己的应用属于的类型，建议选择<br/>
                                    (3).回调<br/>
                                    用户可根据自己的需求设置回调地址，如勾选“开启”，并正确配置回调地址。开发者调用REST接口成功后，玖云平台会进行过程回调，开发者可以根据回调内容结束接口运行结果等信息。 <a href="<c:url value="/api/CallbackInterface"/>" target="_blank" class="AVisited">查看回调通知接口</a><br/>
                                    (4).未进行企业认证不能进行应用创建
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/createAppTwo.png" alt="创建应用图片"/></p>
                                <p class="APImdFont">1.2.应用列表</p>
                                <p>
                                    开发者可在应用列表界面查询所有应用，并对应用进行管理，其中包括查询、编辑、查看详情以及删除等操作。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/AppList.png" alt="应用列表图片"/></p>
                                <p>
                                    应用较多时，平台提供两种查询条件进行筛选：<br/>
                                    (1).应用类型<br/>
                                    根据创建应用时选择的所属行业进行查询<br/>
                                    (2).应用名称<br/>
                                    根据应用名称进行查询，支持模糊查找，已删除应用仍在列表中显示，并支持查询
                                </p>
                            </div>
                            <div class="api_m2_box API2">
                                <span class="api_com_font">2.专线语音</span>
                                <p class="APImdFont">2.1.号码管理</p>
                                <p>
                                    玖云平台提供的专线语音接口，支持号码显示功能。使用的号码必须合法且提交审核通过后才可正式使用。每个号码的作用域为应用，同一个号码可以提交到多个应用中进行使用。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/numManagementOne.png" alt="号码管理图片"/></p>
                                <p>
                                    进入号码管理界面后，展示的为所有应用列表。点击应用对应的“点击管理”按钮，可对该应用的号码进行管理。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/numManagementTwo.png" alt="号码管理图片"/></p>
                                <p>
                                    号码管理界面共分为四部分，下面分别介绍四部分的功能：<br/>
                                    (1).添加号码<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;a.单个号码添加<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;开发者可填入具有合法使用权限的号码，点击“+”将号码添加进批量提交号码池中。<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;b.号段添加<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;开发者可填入连续号段，例如9588812300—9588812400。以此格式用户可以快速的添加100号码。<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;c.批量提交<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;单个号码以及号段添加后，都处于待定批量添加的号码池中，用户进行二次检查后，点击批量提交。号码提交审核期间，不能再次进行号码提交。<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;d.移除号码<br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;单个号码以及号段添加后，都处于待定批量添加的号码池中，用户可以进行二次检查，如号码有误，可选中错误号码或者号段，点击“移除号码”将其从号码池中删除。<br/>
                                    (2).待审核号码<br/>
                                    开发者添加号码并批量提交后，号码处于待审核状态。待审核记录中会显示所有正在审核的号码及号段。此时不能进行删除或修改操作。<br/>
                                    (3).审核通过可显号码<br/>
                                    显示所有审核通过的号码，可对号码进行查询及删除操作。<br/>
                                    (4).审核未通过记录<br/>
                                    对于所有审核不通过的号码及审核不通过原因，将在第四部分显示。
                                </p>
                                <p class="APImdFont" id="lsgl">2.2.铃声管理</p>
                                <p>
                                    玖云平台提供的专线语音接口，支持自定义接通等待铃声功能。铃声内容必须合法且提交审核通过后才可正式使用。每个铃声的作用域为应用，每一个应用只能对应一个铃声，开发者可以修改替换原有铃声。 若开发者未进行铃声自定义，则使用平台默认铃声。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/ringManagementOne.png" alt="铃声管理图片"/></p>
                                <p>
                                    进入铃声管理界面后，展示的为所有应用列表。若应用未上传铃声，则显示为“新建”；若应用已上传铃声并审核通过，则显示为“编辑”、“查看”、“删除”操作。下面详细介绍各个操作：<br/>
                                    (1).新建<br/>
                                    开发者可上传铃声文件，铃声只支持原生WAV格式，最大支持2M。提交后后台会进行审核，审核通过后可正式使用。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/ringNewCreate.png" alt="铃声新建图片"/></p>
                                <p>
                                    (2).编辑<br/>
                                    开发者可对审核通过的铃声进行编辑，即替换原有铃声。替换后需重新审核，审核期间原铃声继续生效。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/ringAmend.png" alt="铃声编辑图片"/></p>
                                <p>
                                    (3).查看<br/>
                                    开发者可查看已上传铃声，并支持铃声下载。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/ringCheckOut.png" alt="铃声查看图片"/></p>
                                <p>
                                    (4).删除<br/>
                                    开发者可删除已上传铃声。删除后，使用平台默认铃声。
                                </p>
                                <p class="APImdFont">2.3.消费总览</p>
                                <p>
                                    专线语音月结账单中，点击“消费总览”可查看消费的基本情况。目前最大支持查看当前月份之前的三个月的记录（不包含当月）。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/consumptionOverView.png" alt="消费总览图片"/></p>
                                <p>
                                    消费总览界面包含三部分。下面分别介绍三部分功能：<br/>
                                    (1).消费概览<br/>
                                    显示该月消费总金额，单位为（万元）<br/>
                                    (2).近期消费走势<br/>
                                    显示该月的每日消费走势图<br/>
                                    (3).应用消费情况<br/>
                                    显示该月每个账户下每个应用的消费总金额，以柱状图方式显示
                                </p>
                                <p class="APImdFont">2.4.通话记录</p>
                                <p>
                                    开放接口月结账单中，点击“通话记录”可查看详细通话记录。进入界面，默认数据为空，用户可根据查询条件进行通话记录查询。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/calledRecord.png" alt="通话记录图片"/></p>
                                <p>
                                    (1).查询时间<br/>
                                    支持三个月内的通话记录查询<br/>
                                    (2).应用名称<br/>
                                    开发者账户下可有多个应用，每次查询必须选定一个应用进行通话记录查询<br/>
                                    (3).主叫号码<br/>
                                    用户请求接口时的主叫电话号码<br/>
                                    (4).被叫号码<br/>
                                    用户请求接口时的被叫电话号码<br/>
                                    (5).Call&nbsp;Id<br/>
                                    用户请求接口时生成的标记该通话的唯一标示<br/>
                                    (6).通话时长<br/>
                                    用户可根据通话时长进行查询，单位为秒，例如：查询通话时间为30秒—59秒的所有通话记录<br/>
                                    (7).接通状态<br/>
                                    接通状态分为“接通”及“未接通”两种<br/>
                                    (8).话单文件导出<br/>
                                    玖云平台支持12个月的话单下载，下载到的话单文件为zip格式，解压后为txt格式文件
                                </p>
                                <p class="APImdFont">2.5.数据日报</p>
                                <p>
                                    专线语音数据分析中，点击“数据日报”可查看近3个月内的通话日报表。且支持12个月的数据日报导出。
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/dataDiary.png" alt="数据日报图片"/></p>
                                <p>
                                    导出的数据日报表为Excel文档，具体内容字段如下图：
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/dataDiaryList.png" alt="导出数据日报单图片"/></p>
                            </div>
                            <div class="api_m2_box API3">
                                <span class="api_com_font">3.专号通</span>
                                <p class="APImdFont">3.1.号码展示</p>
                                <p>
                                    专号通业务为玖云平台提供的保护呼叫双方号码隐私的通信接口。为实现号码隐藏，玖云平台将为客户提供隐私号码池，用于呼叫时显号。号码全部由玖云平台提供，开发者可在用户控制中心进行查看，不支持编辑操作
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/numberShow.png" alt="号码展示"/></p>
                                <p>
                                    (1).应用名称<br/>
                                    开发者账户下可有多个应用，每次查询必须选定一个应用进行通话记录查询<br/>
                                    (2).APP&nbsp;ID<br/>
                                    应用对应的唯一表示<br/>
                                    (3).城市<br/>
                                    隐私号码池具有城市属性，即可根据城市来分配及使用号码<br/>
                                    (4).区号<br/>
                                    城市对应的区号
                                    (5).号码<br/>
                                    具体电话号码
                                </p>
                                <p class="APImdFont">3.2.铃声管理</p>
                                <p>
                                    此处上传铃声为回呼失效提示。专号通接通等待铃声与专线语音共用有，同一个应用只能支持上传一个呼叫等待铃声<br/>具体操作与专线语音相同
                                </p>
                                <p class="APImdFont">3.3.通话记录</p>
                                <p>
                                    专号通通话分为隐私回拨及隐私回呼两种。隐私回拨通话记录分A、B路展示，即一通电话产生两条通话记录；隐私回呼通话记录为一条
                                </p>
                                <p class="picWidth"><img src="${appConfig.resourcesRoot}/master/img/maskCalledRecord.png" alt="通话记录"/></p>
                                <p>
                                    (1).序号<br/>
                                    通话记录序号<br/>
                                    (2).应用名称<br/>
                                    通话所属应用的名称<br/>
                                    (3).Call&nbsp;ID<br/>
                                    通话ID，唯一标示<br/>
                                    (4).城市<br/>
                                    隐私号码所属城市，也可为全国
                                    (5).业务类型<br/>
                                    分为隐私回呼及隐私回拨两种<br/>
                                    (6).呼叫类型<br/>
                                    若为隐私回拨则分为呼入及呼出，隐私回呼不分类<br/>
                                    (7).主叫号码<br/>
                                    主叫电话号码<br/>
                                    (8).被叫号码<br/>
                                    被叫电话号码<br/>
                                    (9).隐私号码<br/>
                                    主被叫映射的隐私号码<br/>
                                    (10).接通状态<br/>
                                    分为已接通及未接通两种<br/>
                                    (11).通话开始时间<br/>
                                    若为隐私回拨则为该路通话开始时间，若为隐私回呼则为被叫接听时间<br/>
                                    (12).通话结束时间<br/>
                                    通话结束时间<br/>
                                    (13).通话实际时长<br/>
                                    实际通话时长按秒计<br/>
                                    (14).通话计费时长<br/>
                                    计费通话时长按分钟计，不满一分钟按一分钟计算<br/>
                                    (15).通话费用<br/>
                                    实际通话费用<br/>
                                    (16).录音费用<br/>
                                    若开启录音则为录音费用<br/>
                                    (17).录音下载<br/>
                                    若开启录音则可以进行录音点击下载
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="API_main3 f_left">
                    <div class="stb"> <span class="click_tb spread_tb"></span></div>
                    <div class="api_right_box THeight">
                        <div class="api_right_center">
                            <p class="b1"><b class="b"></b>1&nbsp;应用管理</p>
                            <p class="b2"><b class="b"></b>2&nbsp;专线语音</p>
                            <p class="b3"><b class="b"></b>3&nbsp;专号通</p>
                        </div>
                    </div>
                    <div class="xtb"><span class="click_tb close_tb"></span></div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

<script src="${appConfig.resourcesRoot}/master/js/api.js"></script>

</body>
</html>