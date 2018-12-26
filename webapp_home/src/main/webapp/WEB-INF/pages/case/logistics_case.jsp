<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="keywords" content="隐私号功能、一键呼叫、物联卡、扫描拨号、行为轨迹管理、电话录音">
    <meta name="description" content="大众通信悉心洞察客户需求，凭借业界领先的技术，将客户的物流信息转换为语音，通过电话播报的模式，将多种信息通过语音播报给查单客户，并在客户挂机后，自动发送一条挂机短信给客户进行提醒；">
    <title>物流行业</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/case.css"/>

    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="../common/baidu.jsp"/>
</head>
<body>

<jsp:include page="../common/header.jsp"/>

<div class="logistics_container1">
    <div class="main_box">
        <div class="l_case_main1">
            <div class="One">
                <span data-url="<c:url value='/case/financial'/>" class="case_com_kj l_case_img1"></span>
                <span data-url="<c:url value='/case/logistics'/>" class="case_com_kj l_case_img2"></span>
                <span data-url="<c:url value='/case/estate'/>" class="case_com_kj f_case_img3"></span>
                <%--<span data-url="<c:url value='/case/education'/>" class="case_com_kj f_case_img4"></span>--%>
            </div>
            <div class="Two">
                <span class="case_common">物流行业解决方案</span>
            </div>
            <div class="Three">
                <hr color="#4E7DD4"/>
            </div>
            <div class="Four logistics_intro">
                <p>物流是商业发达社会必然高速度发展的产业之一，</p>
                <p>信息管理系统功能主要集中在内部资源整合，</p>
                <p>如运输管理、订单管理等，但这仅是快递企业信息化建设的第一层次</p>
            </div>
        </div>
    </div>
</div>
<div class="logistics_container2">
    <div class="main_box">
        <div class="l_case_main2">
            <div class="f_m2One">
                <p>当今的快递企业业务涵盖仓储，包装，分拣，</p>
                <p>供应链整合和运输等各个环节，快递企业一般都会在各个城市建立门店、办事处、合作网络，</p>
                <p>而且随着快递的发展，服务不仅仅是要求物品从供应地向接收地的实体流动过程，</p>
                <p>同时也是信息流和资金流的过程</p>
            </div>
            <div class="l_m2Two">
                <span class="l_trouble">行业痛点</span>
            </div>
            <div class="f_m3Three logisticsThree">
                <div class="logistics_trouble f_left">
                    <span class="trouble_intro l_trouble_img1"></span>
                    <p class="l_tr_common_lg">语音播报实现难</p>
                    <div class="tr_common_sm">
                        <p>物流行业的很多客户有电话中进行物</p>
                        <p>流单查询的需求，目前物流公司的系</p>
                        <p>统很多均不支持该功能，如果找第三</p>
                        <p>方公司来进行开发，那么接口、成本</p>
                        <p>等问题均不易解决</p>
                    </div>
                </div>
                <div class="logistics_trouble trouble_dashed f_left">
                    <span class="trouble_intro l_trouble_img2"></span>
                    <p class="l_tr_common_lg">门店通信监管难</p>
                    <div class="tr_common_sm">
                        <p>物流企业门店一般使用当地固话进行</p>
                        <p>营销或客服等通信应用；但因全国门</p>
                        <p>店使用的电话分属各地不同运营商，</p>
                        <p>且比较分散，不便于公司管理部门方</p>
                        <p>便高效的管控</p>
                    </div>
                </div>
                <div class="logistics_trouble trouble_dashed f_left">
                    <span class="trouble_intro l_trouble_img3"></span>
                    <p class="l_tr_common_lg">外呼号码统一难</p>
                    <div class="tr_common_sm">
                        <p>由于各门店、工作人员使用的号码千</p>
                        <p>差万别，导致号码的辨识度非常低；</p>
                        <p>因此物流企业门店、物流人员在联系</p>
                        <p>客户时，经常会有客户拒接电话的情</p>
                        <p>况发生，严重影响了其工作效率</p>
                    </div>
                </div>
                <div class="logistics_trouble  f_left">
                    <span class="trouble_intro l_trouble_img4"></span>
                    <p class="l_tr_common_lg">核心业务数据共享难</p>
                    <div class="tr_common_sm">
                        <p>呼叫中心营销性通信业务，一般由C</p>
                        <p>C系统管理；但该系统只能管理呼叫</p>
                        <p>中心的营销业务的通信信息，无法与</p>
                        <p>全国门店的分散的业务通信系统进行</p>
                        <p>对接，无法通过CC系统来管理，而</p>
                        <p>形成信息孤岛</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="logistics_container3 clear">
    <div class="main_box">
        <div class="l_case_main3">
            <div class="f_m3One">
                <span class="case_common">业界领先的物流通信解决方案</span>
            </div>
            <div class="f_m3Two">
                <hr color="#4E7DD4"/>
            </div>
            <div class="l_m3Three">
                <p>大众通信悉心洞察客户需求，凭借业界领先的技术，</p>
                <p>将客户的物流信息转换为语音，通过电话播报的模式，将多种信息通过语音播报给查单客户，</p>
                <p>并在客户挂机后，自动发送一条挂机短信给客户进行提醒；</p>
                <p>此外，无论是客户还是工作人员，其工作通话均可进行录音记录</p>
            </div>
        </div>
    </div>
</div>
<div class="logistics_container4">
    <div class="main_box">
        <div class="l_case_main4">
            <div class="logistics_plan">
                <p>大众通信将传统的电子商务、CRM技术与通信技术进行有效的结合，优化了传统的快递信息化手段，将快递企</p>
                <p>业遍布在各地的呼叫中心、门店、快递人员与客户紧 密相联，消除信息孤岛，形成了一个更高效率的信息传输</p>
                <p>通道，同时所有电话无论手机还是办公固话均通过平台进行录音，还可实现挂机短信等服务功能</p>
                <p class="logistics_plan_top">在快递员的手持终端（巴枪）或手机里使用物联卡，同时，在手持终端或手机快递APP里集成我司通信功能，</p>
                <p>实现货单信息查询、扫描呼叫、语音录音、短信通知、语音通知、屏蔽客户信息、人员定位等特色功能</p>
            </div>
            <div class="case_advantage_list logisticsAdvantageLeft">
                <ul>
                    <li class="f_left">
                        <div class="f_left">
                            <span class="case_advantage logistic1"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="logistics_common_color">隐号功能</p>
                            <span class="logistics_common_font">
                                平台向快递员及客户同时发起呼叫，<br/>
                                快递员端显示平台号码，客户端显示快递公司号码
                            </span>
                        </div>
                    </li>
                    <li class="f_left">
                        <div class="f_left">
                            <span class="case_advantage logistic2"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="logistics_common_color">一键呼叫</p>
                            <span class="logistics_common_font">
                                扫描即可拨号、发短信<br/>
                                语音通知电话，可同时通知多个客户
                            </span>
                        </div>
                    </li>
                    <li class="f_left">
                        <div class="f_left">
                            <span class="case_advantage logistic3"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="logistics_common_color">物联卡</p>
                            <span class="logistics_common_font">
                                物联卡为企业提供批量短信和语音短信的费用，<br/>
                                流量费、通信费用比个人便宜
                            </span>
                        </div>
                    </li>
                    <li class="f_left">
                        <div class="f_left">
                            <span class="case_advantage logistic4"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="logistics_common_color">扫描拨号</p>
                            <span class="logistics_common_font">
                               扫描即可拨号发短信，<br/>
                               让快递人员可同时通知多个客户
                            </span>
                        </div>
                    </li>
                    <li class="f_left">
                        <div class="f_left">
                            <span class="case_advantage logistic5"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="logistics_common_color">行为轨迹管理</p>
                                <span class="logistics_common_font">
                                    实时获取快递员位置，精确分配工作，调配人手<br/>
                                    所有通信均有详单、录音可查，加强服务管理
                                </span>
                        </div>
                    </li>
                    <li class="f_left">
                        <div class="f_left">
                            <span class="case_advantage logistic6"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="logistics_common_color">电话录音</p>
                                <span class="logistics_common_font">
                                    快递员的服务电话、服务短信均有记录<br/>提升快递员的服务质量
                                </span>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/footer.jsp"/>

<script src="${appConfig.resourcesRoot}/master/js/case.js"></script>

</body>
</html>