<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>解决方案</title>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/C_common.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/case.css"/>

    <script src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>

</head>
<body>

<jsp:include page="../common/j_header.jsp"/>

<div class="education_container1">
    <div class="main_box">
        <div class="ed_case_main1">
            <div class="One">
                <span data-url="<c:url value='/case/financial'/>" class="case_com_kj l_case_img1"></span>
                <span data-url="<c:url value='/case/logistics'/>" class="case_com_kj es_case_img2"></span>
                <span data-url="<c:url value='/case/estate'/>" class="case_com_kj ed_case_img3"></span>
                <span data-url="<c:url value='/case/education'/>" class="case_com_kj ed_case_img4"></span>
            </div>
            <div class="Two">
                <span class="case_common">教育行业解决方案</span>
            </div>
            <div class="Three">
                <hr color="#01BA8D"/>
            </div>
            <div class="ed_Four education_intro">
                <p>随着国家对教育产业投入的加大，全国各类学校的办学规模不打扩大，教学环境、教学设施不断得到改善</p>
                <p>大多数校园已建立起比较完善的信息数据网络，拥有自己的教育通信网</p>
            </div>
        </div>
    </div>
</div>
<div class="education_container2">
    <div class="main_box">
        <div class="ed_case_main2">
            <div class="f_m2One ed_m2_intro">
                <p>大众通信依托现代化通信技术，全面深入地利用以互联网、物联网、云计算、大数据等为代表的新技术，</p>
                <p>通过创建新的通信模式和技术手段，提高教育教学质量和效益，</p>
                <p>全面构建数字化、网络化、智能化和多媒体的现代教育通信解决方案</p>
            </div>
            <div class="ed_m2Two">
                <span class="ed_demand">教育行业五大需求</span>
            </div>
            <div class="ed_m2Three">
                <div class="education_demand f_left">
                    <span class="education ed_demand_img1"></span>
                    <p class="ed_tr_common_lg">教育部</p>
                    <div class="education_common_sm">
                        <p>推动教育信息化建设进程，</p>
                        <p>促进教育公平</p>
                    </div>
                </div>
                <div class="education_demand  f_left">
                    <span class="education ed_demand_img2"></span>
                    <p class="ed_tr_common_lg">学校</p>
                    <div class="education_common_sm">
                        <p>数字化办公，提升管理水</p>
                        <p>平，树立学校形象，加强校</p>
                        <p>园安全管理，防范安全事故</p>
                    </div>
                </div>
                <div class="education_demand f_left">
                    <span class="education ed_demand_img3"></span>
                    <p class="ed_tr_common_lg">老师</p>
                    <div class="education_common_sm">
                        <p>与家长和学生充分沟通，改</p>
                        <p>进教学方法，提高教学效</p>
                        <p>果，吸引优质生源</p>
                    </div>
                </div>
                <div class="education_demand  f_left">
                    <span class="education ed_demand_img4"></span>
                    <p class="ed_tr_common_lg">家长</p>
                    <div class="education_common_sm">
                        <p>及时了解孩子在校情况，积</p>
                        <p>极配合学校要求</p>
                    </div>
                </div>
                <div class="education_demand  f_left">
                    <span class="education ed_demand_img5"></span>
                    <p class="ed_tr_common_lg">学生</p>
                    <div class="education_common_sm">
                        <p>利用信息手段，主动、自主</p>
                        <p>学习，学习成绩得以提高</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="education_container3 clear">
    <div class="main_box">
        <div class="ed_case_main3">
            <div class="ed_m3One">
                <span class="solution">解决方案一</span>
            </div>
            <div class="ed_m3Two">
                <span class="ed_hotLine">家&nbsp;校&nbsp;热&nbsp;线</span>
            </div>
            <p class="ed_common_white_color">智&nbsp;能&nbsp;语&nbsp;音&nbsp;家&nbsp;校&nbsp;互&nbsp;动&nbsp;的&nbsp;领&nbsp;航&nbsp;者，开&nbsp;启&nbsp;家&nbsp;校&nbsp;互&nbsp;动&nbsp;新&nbsp;时&nbsp;代</p>
            <div class="ed_m3Three">
                <p>家校热线是集语音通信技术、互联网、物联网技术以及智能终端于一体的家校互动智能语音沟通方案及语音开发平台，</p>
                <p>为家校互动提供了一种开创性的便捷服务，建立起家长与学生、家长与学校、家长与家长之间互动交流的语音沟通平</p>
                <p>台、信息传播平台</p>
            </div>
        </div>
    </div>
</div>
<div class="education_container4">
    <div class="main_box">
        <div class="ed_case_main4">
            <div class="case_advantage_list">
                <ul>
                    <li class="f_left">
                        <div class="f_left">
                            <span class="case_advantage education1"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="education_common_color">紧急通知公告</p>
                            <span class="education_common_font">
                                 如有突发紧急事项（如台风、暴雨、重大事故等），<br/>学校可一次性通过家校热线系统给所有家长群呼播报<br/>语音紧急通知，<br/>让家长学生第一时间了解最新的紧急事项
                            </span>
                        </div>
                    </li>
                    <li class="f_left">
                        <div class="f_left">
                            <span class="case_advantage education2"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="education_common_color">在校信息查询</p>
                            <span class="education_common_font">
                                无需上网，家长直接拨打教室电话，<br/>可通过“家校热线”语音系统查询孩子的作业、<br/>考勤、成绩、课表、老师评语、学校通知等信息
                            </span>
                        </div>
                    </li>
                    <li class="f_left">
                        <div class="f_left">
                            <span class="case_advantage education3"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="education_common_color">智能电话转接</p>
                            <span class="education_common_font">
                                无需通讯录，只需拨打教室电话，<br/>老师或家长可直接呼叫通讯录里的其他老师和家长<br/>此外，学生可通过语音提示呼叫、<br/>拼音查询呼叫转接给家长和教师，<br/>解决了老师家长通讯录信息的泄露隐忧
                            </span>
                        </div>
                    </li>
                    <li class="f_left">
                        <div class="f_left">
                            <span class="case_advantage education4"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="education_common_color">教室电话通信</p>
                            <span class="education_common_font">
                                教室安装电话后，解决了学生与家长的沟通难题，<br/>学生只可拨打家长设置的亲情号码，<br/>
                                防止学生滥用电话，同时只许学校电话、<br/>亲情号码呼入，防止外界电话骚扰
                            </span>
                        </div>
                    </li>
                    <li class="f_left">
                        <div class="f_left">
                            <span class="case_advantage education5"></span>
                        </div>
                        <div class="f_left txt_left">
                            <p class="education_common_color">学生手表服务</p>
                            <span class="education_common_font">
                                双向通话、安全定位，可实现圈子语聊服务和接收<br/>学校紧急语音通知
                            </span>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="education_container5">
    <div class="main_box">
        <div class="ed_case_main5">
            <div class="ed_m5One">
                <span class="ed_demand">家&nbsp;校&nbsp;热&nbsp;线&nbsp;七&nbsp;大&nbsp;优&nbsp;势</span>
            </div>
            <div class="ed_m5Two">
                <div class="f_left hotLine_value">
                    <div class="number_sorting"><img src="${appConfig.resourcesRoot}/master/img/ed_one.png" alt="序号图"/></div>
                    <p>平台统一管控；平台统一管控教室电话的呼入时间和呼入名单，不影响课堂教学秩序</p>
                </div>
                <div class="f_left hotLine_value">
                    <div class="number_sorting"><img src="${appConfig.resourcesRoot}/master/img/ed_two.png" alt="序号图"/></div>
                    <p>解决学校、学生和家长的沟通难题；学校想严禁学生进校带手机，但禁止不了学生、家长有沟通刚需，但担心手机会给学习、成长带来诸多潜在的不利影响；家校热线解决沟通需求，增强家校互动，可极大提高教育信息化水平</p>
                </div>
                <div class="f_left hotLine_value">
                    <div class="number_sorting"><img src="${appConfig.resourcesRoot}/master/img/ed_three.png" alt="序号图"/></div>
                    <p>打破家校互动行业的困局，受政策限制收费影响，以及受QQ、微信、微博等免费社交平台冲击，2014年家校互动行业发展举步维艰，难以解决的收费难题是困扰行业发展的主因；家校热线区别于传统的家校互动产品，以全新的产品形象，开创一种全新的教育商业模式</p>
                </div>
                <div class="f_left hotLine_value">
                    <div class="number_sorting"><img src="${appConfig.resourcesRoot}/master/img/ed_four.png" alt="序号图"/></div>
                    <p>提升了家长学生对学校教学管理的参与度，丰富了家校互动的手段，学生手表可接收文字和语音短信，沟通更具多样性</p>
                </div>
                <div class="f_left hotLine_value num_sort_left">
                    <div class="number_sorting "><img src="${appConfig.resourcesRoot}/master/img/ed_fine.png" alt="序号图"/></div>
                    <p>独创的家校互动语音产品，市场空白，无竞争性产品；家校热线是三三得玖教育科技公司针对空白市场推出的全新差异化产品，市场无竞争产品，市场推广更优势</p>
                </div>
                <div class="f_left hotLine_value">
                    <div class="number_sorting"><img src="${appConfig.resourcesRoot}/master/img/ed_six.png" alt="序号图"/></div>
                    <p>无法复制的通信技术和通信网络优势；家校热线不仅需要深厚的语音通信技术、家校互动平台开发技术、支付系统开发技术，也需要全面的通信网络资源；三三得玖教育科技深耕教育市场15年，拥有无法复制的语音通信技术、通信网络优势</p>
                </div>
                <div class="f_left hotLine_value">
                    <div class="number_sorting"><img src="${appConfig.resourcesRoot}/master/img/ed_seven.png" alt="序号图"/></div>
                    <p>市场前景广阔、投资回报高；据统计：全国各类教室共有约1100万间，全国近2亿多的在校学生，家校热线服务市场拥有上百亿的市场空间</p>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="education_container6 clear">
    <div class="main_box">
        <div class="ed_case_main6">
            <div class="ed_m3One">
                <span class="solution">解决方案二</span>
            </div>
            <div class="ed_m3Two">
                <span class="ed_hotLine">星&nbsp;星&nbsp;学&nbsp;生&nbsp;手&nbsp;表</span>
            </div>
            <p class="ed_common_white_color">全&nbsp;球&nbsp;首&nbsp;创&nbsp;教&nbsp;育&nbsp;社&nbsp;交&nbsp;学&nbsp;生&nbsp;手&nbsp;表</p>
            <div class="ed_m3Three">
                <p>“星星”学生手表，是一款以学生为沟通中心，打造学生全新的教育社交圈，形成学生与老师、家长和学生之间多方沟</p>
                <p>通闭环，融合教学信息服务、安全定位、双向通话、语音群聊、考勤打卡等多项功能，同时老师和家长可以通过后台统</p>
                <p>一管理的学生手表</p>
            </div>
        </div>
    </div>
</div>
<div class="education_container7">
    <div class="main_box">
        <div class="ed_case_main7">
            <div class="ed_m7One">
                <span class="student_watch">"星星"学生手表—设计理念</span>
                <p>以学生为中心设计，全方位提高孩子的参与度</p>
            </div>
            <div class="ed_m7Two">
                <img src="${appConfig.resourcesRoot}/master/img/ed_sjln.png" alt="设计理念图"/>
            </div>
        </div>
    </div>
</div>
<div class="education_container8">
    <div class="main_box">
        <div class="ed_case_main8">
            <div class="ed_m8One">
                <span class="watch_function">"星星"学生手表—手表功能</span>
            </div>
            <div class="ed_m8Two">
                <div class="student_watch_box f_left">
                    <div class="function_title"><span>以孩子为中心进行设计构建沟通闭环</span></div>
                    <p>“星星”学生手表的设计中心是孩子，通过“星星”将构建以孩子为中心的沟通闭环；以往对家校互动沟通，主要是家长和学校沟通，而作为沟通对重点——孩子，却没有参与沟通；“星星”学生手表，改变以往沟通现场，形成以孩子为中心的沟通闭环，学校通过教育通平台，老师和家长通过云屋APP，共同构建沟通闭环</p>
                </div>
                <div class="student_watch_box f_left">
                    <div class="function_title"><span>以教学服务为出发点学校可控手表</span></div>
                    <p>“星星”学生手表，以教育服务为基础出发点，让手表成为教育服务的工具；现在对学生手表（儿童手表）是学校不可控的，有些学生会因玩弄手表而影响课堂学习，学校对学生手表持排斥的态度；“星星”学生手表，学校可以通过“教育通”后台对手表进行控制，设置可通话和服务时间段，避免学生手表干扰课堂；学校还可以通过“星星”学生手表进行考勤管理、缴费管理、安全管理和紧急信息发布等各项功能；“星星”学生手表不仅是家长进行沟通和安全管理的工具，还是学校进行管理和教学服务的工具</p>
                </div>
                <div class="student_watch_box f_left">
                    <div class="function_title"><span>以在线学习为增值服务手表是学习小帮手</span></div>
                    <p>目前市面上的绝大部分学生手表都将手表作为家长对孩子进行安全控制的工具，没有相关的增值服务；“星星”云平台拥有非常丰富的在线学习资源，不仅覆盖幼儿教育和K12学科教育内容，还涵盖了传统文化、音乐等兴趣教育内容；孩子可以通过“星星”学生手表，在云平台进行听英语、听故事、学词汇、学常识等在线学习，让学生手表成为学习的小帮手</p>
                </div>
                <div class="student_watch_box f_left">
                    <div class="function_title"><span>以服务价值为盈利点颠覆学生手表盈利模式</span></div>
                    <p>目前，市面上的学生手表是一种零售产品，主要盈利来源于产品差价；“星星”学生手表，将产品和通信服务捆绑，加盟商不仅可以获取产品销售盈利，还可以获取产品服务盈利；“星星”学生手表运营模式，颠覆了已有学生手表的盈利模式，给加盟商创造了持续性盈利能力</p>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="education_container9 clear">
    <div class="main_box">
        <div class="ed_case_main9">
            <div class="ed_m9One">
                <span class="student_watch">"星星"学生手表—五大核心优势</span>
            </div>
            <div class="ed_m9Two">
                <div class="f_left ed_hotLine_value">
                    <div class="value_number_sorting"><img src="${appConfig.resourcesRoot}/master/img/ed_one.png" alt="序号图"/></div>
                    <div class="ed_value_top">
                        <p>可三重管控手表，不影响</p>
                        <p>课堂教学</p>
                    </div>
                </div>
                <div class="f_left ed_hotLine_value">
                    <div class="value_number_sorting"><img src="${appConfig.resourcesRoot}/master/img/ed_two.png" alt="序号图"/></div>
                    <div class="ed_value_top">
                        <p>手表可随时接收学校紧急</p>
                        <p>通知通告</p>
                    </div>
                </div>
                <div class="f_left ed_hotLine_value">
                    <div class="value_number_sorting"><img src="${appConfig.resourcesRoot}/master/img/ed_three.png" alt="序号图"/></div>
                    <div class="ed_value_top">
                        <p>可建立学校通讯录、孩子</p>
                        <p>朋友圈，云屋免费推送信</p>
                        <p>息</p>
                    </div>
                </div>
                <div class="f_left ed_hotLine_value">
                    <div class="value_number_sorting"><img src="${appConfig.resourcesRoot}/master/img/ed_four.png" alt="序号图"/></div>
                    <div class="ed_value_top">
                        <p>可实现安全管理和实时到</p>
                        <p>校考勤</p>
                    </div>
                </div>
                <div class="f_left ed_hotLine_value">
                    <div class="value_number_sorting "><img src="${appConfig.resourcesRoot}/master/img/ed_fine.png" alt="序号图"/></div>
                    <div class="ed_value_top">
                        <p>校内定位精准度可达10米</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="../common/j_footer.jsp"/>

<script src="${appConfig.resourcesRoot}/master/js/case.js"></script>

</body>
</html>