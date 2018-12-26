<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>API文档</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/API.css"/>
    <style>

    </style>
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
                        <div id="apima2" style="position: fixed;width: 810px;top: 85px;height:36px;background: #ECECEC;"></div>
                        <div id="api_ma2_1">
                            <div class="api_m2"><span>01&nbsp;开发者协议</span></div>
                            <div>
                                <div class="api_m2_box API1">
                                    <span class="api_com_font">玖云平台开发者协议</span>
                                    <p>开发者许可协议（后称“许可协议”）是大众通信科技有限公司（后称“大众通信”）与注册用户提供的玖云平台API开放接口的许可使用协议。本许可协议适用在本网站提供的API开放接口相关文档和指导的全部或其任何部分（后称“许可资料”）。注册用户是指按照本网站的要求提交用户注册信息，点击同意本许可协议并顺利注册成功成为本网站合法用户的自然人或者实体（后称“被许可人”）</p>
                                </div>
                                <div class="api_m2_box API2">
                                    <span class="api_com_font">1.接受条款</span>
                                    <p>阁下申请注册成为本网站用户时，应根据要求提供真实、准确、即时、完整的信息，包括但不限于联系人或管理人姓名或名称、联系地址、联系电话、电子邮箱地址等，企业用户需要提交公司营业执照、组织机构代码证、法人信息、银行开户信息等供API提供方进行实名认证和备案；如果前述您的信息发生变更，您应自行在线更新；如果因您的信息 不真实、不准确、不完整或联系人和/或管理人的行为或不作为而引起的任何后果由您自行承担。</p>
                                    <p>阁下通过在线点击同意本许可协议或者与大众通信签订本许可协议的书面协议并注册成为本网站的合法用户即表示阁下已同意接受本协议的所有内容并同意受其约束。请仔细阅本许可协议的所有内容。如果阁下不同意本许可协议，请不要点击同意，不要申请成为本网站的用户，更不要下载或使用本网站的任何许可资料，对已经下载的任何许可资料请全部销毁或删除。</p>
                                    <p>阁下应对本网站的账户信息和密码妥善保管，不得转让、转借其他第三人使用，如果因您维护不当或保管不当致使前述账户信息和密码丢失或泄漏所引起的任何损失及后果由您自行承担。</p>
                                </div>
                                <div class="api_m2_box API3">
                                    <span class="api_com_font">2.许可条款</span>
                                    <p>2.1大众通信在本网站上的所有许可资料仅许可给本网站的合法注册用户，对于非注册用户，不得使用本网站的许可资料或其任何部分。</p>
                                    <p>2.2 在被许可人完全遵守本许可协议的前提下，大众通信授予被许可人一个没有分许可权的，可撤回的，仅供您个人下载许可资料用于开发终端用户设备上使用的应用软件的普通的版权许可。除了前述使用许可外，大众通信不授予被许可人任何明示或默示的许可。大众通信也不授予被许可人使用许可资料的任何专利许可。</p>
                                    <p>2.3被许可人可以分发利用许可资料开发出来的用于终端用户设备的应用软件，只要1）该应用软件显著增加许可资料的功能，2）该应用软件不含替代本许可资料功能的模块，3）该应用软件附上本许可协议及其他法律声明，且4）被许可人承担因分发或使用该应用软件给大众通信造成的所有损失，包括但不限于法庭费用及律师费用。</p>
                                    <p>
                                        2.4 被许可人只能按照本许可协议的规定使用许可资料，除本协议明确规定外，不得：<br>
                                        1）将本许可资料用于除开发终端用户设备上使用的应用软件之外的使用目的；<br>
                                        2）将本许可资料进行任何形式的复制，修改，反编译，反向工程；<br>
                                        3）将本许可资料以任何形式提供给第三方使用。<br>
                                        4）删除本许可协议或者删除许可资料的任何法律声明。<br>
                                    </p>
                                </div>
                                <div class="api_m2_box API4">
                                    <span class="api_com_font">3.陈述与保证</span>
                                    <p>
                                        许可资料是大众通信的核心资产，其知识产权归大众通信技术有限公司所有。<br/>
                                        被许可人保证其在本网站上提供的注册信息是真实的、准确且完整的，不存在任何虚假和错误的信息。<br/>
                                        被许可人保证其有利用本许可资料开发其应用的资质和能力，并有能力接受本许可协议并受其约束。<br>
                                        被许可人承诺并保证遵守所有适用法的规定，包括用户隐私保护相关法律的规定。<br/>
                                        被许可人理解并同意许可资料“如是”提供，除非法律另有规定，大众通信不对许可资料提供任何明示或默示对许可资料的准确性，适销性，适用性，安全性，以及不侵犯任何第三方的知识产权的保证。<br/>
                                        被许可人理解并同意大众通信不对被许可人提供任何形式的维保服务。如需维保服务，请联系大众通信的销售人员。
                                    </p>
                                </div>
                                <div class="api_m2_box API5">
                                    <span class="api_com_font">4.许可期限</span>
                                    <p>
                                        4.1 本许可协议一直有效，除非因如下是由终止：<br/>
                                        1）被许可人违反许可协议的任何条款，本许可协议将自动终止。<br/>
                                        2）大众通信有权无需任何理由在通知被许可人后立即终止本许可协议。<br/>
                                        3）被许可人或被许可人的关联公司如对大众通信及其关联公司提起任何形式的法律程序，包括但不限于行政程序或法律诉讼，从被许可人或被许可人的关联公司提起该程序之日起，本许可协议自动终止，且视为大众通信对被许可人的使用许可自始不存在。大众通信有权就被许可人未经授权使用本许可资料的行为启动法律程序追究被许可人的侵权责任。<br/>
                                        4.2 本许可协议终止后，被许可人必须立即停止使用许可资料，并将许可资料销毁。本许可协议终止后，被许可人不能再次通过点击同意接收本许可协议等方式获得许可资料的使用许可。
                                    </p>
                                </div>
                                <div class="api_m2_box API6">
                                    <span class="api_com_font">5.审计</span>
                                    <p>为了确认被许可人遵守本协议的规定，大众通信有权自行或者授权独立第三方审计机构审计被许可人对许可资料的使用情况。被许可人必须提供审计所必须的所有协助，如审计结果表明，被许可人使用许可软件时存在违约行为，被许可人应承担审计费用，并根据本协议第6条规定承担违约责任。</p>
                                </div>
                                <div class="api_m2_box API7">
                                    <span class="api_com_font">6.责任</span>
                                    <p>
                                        大众通信不承担因被许可人使用许可资料的行为承担任何责任，也不对被许可人利用许可资料开发的任何引用承担任何责任。因使用许可资料的任何风险由被许可人自行承担。
                                        被许可人必须遵守本许可协议的所有条款，如被许可人违反许可协议导致大众通信遭受任何损失的，被许可人应予全额赔偿。</p>
                                    <p>
                                        对于违反本许可协议的被许可人，大众通信有权对其账户进行查封或注销，前述行为不影响大众通信依据进一步追究被许可人法律责任的权利。
                                    </p>
                                </div>
                                <div class="api_m2_box API8">
                                    <span class="api_com_font">7.继续有效</span>
                                    <p>本协议终止后，协议第2.3,3,4,5,6,7,8,9条继续有效。</p>
                                </div>
                                <div class="api_m2_box API9">
                                    <span class="api_com_font">8.法律适用及争议解决</span>
                                    <p>
                                        本协议受中华人民共和国法律管辖。在履行本协议过程中如发生纠纷，双方应通过友好协商解决。协商不成时，任何一方可直接向深圳市南山区人民法院提起诉讼。
                                    </p>
                                </div>
                                <div class="api_m2_box API10">
                                    <span class="api_com_font">9.关于协议条款</span>
                                    <p>
                                        如果本协议中的任何条款，包括附件被法律确认为无效或不可执行，那么，这些条款应仅在被确认为无效或不可执行的范围内删除而不影响本协议其他条款的效力。
                                    </p>
                                    <p>
                                        大众通信有权修改本许可协议并在网站上公开。新的许可协议将从网站上公开之日起三十日内生效。请被许可人定期查看本许可协议最新情况。如果被许可人不同意许可协议的任何修改，请立即停止使用所有的许可资料。
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="API_main3 f_left">
                        <div class="stb"> <span class="click_tb spread_tb"></span></div>
                        <div class="api_right_box StarePageHeight">
                            <div class="api_right_center">
                                <p class="b1"><b class="b"></b>玖云平台开发者协议</p>
                                <p class="b2"><b class="b"></b>1&nbsp;接受条款</p>
                                <p class="b3"><b class="b"></b>2&nbsp;许可条款</p>
                                <p class="b4"><b class="b"></b>3&nbsp;陈述与保证</p>
                                <p class="b5"><b class="b"></b>4&nbsp;许可期限</p>
                                <p class="b6"><b class="b"></b>5&nbsp;审计</p>
                                <p class="b7"><b class="b"></b>6&nbsp;责任</p>
                                <p class="b8"><b class="b"></b>7&nbsp;继续有效</p>
                                <p class="b9"><b class="b"></b>8&nbsp;法律适用及争议解决</p>
                                <p class="b10"><b class="b"></b>9&nbsp;关于协议条款</p>
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