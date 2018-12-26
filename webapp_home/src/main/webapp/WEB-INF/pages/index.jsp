<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta name="keywords" content="玖云平台是提供通话、短信、呼叫中心、流量、专线语音、通话录音、录音、回拨、隐私号等通讯能力与服务的开放平台">
    <meta name="description" content="玖云平台为企业及个人开发者提供各种通讯能力，包括网络通话、呼叫中心/IVR等，开发者通过嵌入云通讯API能够在应用中轻松实现各种通讯功能，包括语音验证码、语音对讲、群组语聊、点击拨号、云呼叫中心等功能;网络通话,网络电话,云呼叫中心,短信接口,pass平台,流量充值,短信发送平台,通讯云,玖云平台,云通讯,云通信,SDK,API,融合通信,政企通讯,云呼叫中心,呼叫中心建设,呼叫中心能力,通讯线路,虚拟运营商,短信服务,流量分发平台,网络直拨,匿名通话,公费电话,企业服务 ">
    <title>玖云平台-通讯能力与服务的开放平台</title>
    <script type="text/javascript">
        function getSearchString(keyname) {
            var keywords = (location.search.length > 0 ? location.search.substring(1) : '');
            keywords = decodeURIComponent(keywords);
            if (!!keywords && keywords.length > 0) {
                var kvArr = keywords.split('&');
                for (var i = 0; i < kvArr.length; i++) {
                    var val = kvArr[i].split('=');
                    if (val[0] == keyname) {
                        return unescape(val[1]);
                    }
                }
            }
            return '';
        }
        function jumpToUrl(url){
            var agentType = navigator.userAgent.toLowerCase();
            if(agentType.indexOf("ipad")>0 || agentType.indexOf("iphone")>0 || agentType.indexOf("itouch")>0 || agentType.indexOf("android")>0) {
                var to_pc = Boolean(getSearchString("to_pc"));
                if(!to_pc) {
                    window.location.href = url;
                }
            }
        }
        jumpToUrl('${appConfig.h5Home}');
    </script>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/bootstrap.css">
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/index.css"/>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/animate.css"/>

    <!--[if lte IE 9]>
    <script src="${appConfig.resourcesRoot}/master/js/html5shiv.min.js"></script>
    <script src="${appConfig.resourcesRoot}/master/js/respond.min.js"></script>
    <link rel="stylesheet" href="${appConfig.resourcesRoot}/master/css/indexIE.css"/>
    <![endif]-->
    <script type="text/javascript" src="${appConfig.resourcesRoot}/master/js/jquery-1.11.3.min.js"></script>
    <jsp:include page="common/baidu.jsp"/>
</head>
<body>
<div>
    <div class="services_box">
        <div class="Online_box">
               <span class="Nine_platform_main">
                   <span class="nine_top">玖&nbsp;云&nbsp;平&nbsp;台</span><br/>
                   <span>在&nbsp;线&nbsp;客&nbsp;服</span>
               </span>
               <span class="Nine_platform">
                   <img  id="onlineService" src="${appConfig.resourcesRoot}/master/img/Online_services.png" alt="图片"/>
               </span>
        </div>
        <div class="service_box">
               <span class="Service_tel_main">
                   <span class="tel1">客&nbsp;服&nbsp;电&nbsp;话：</span><br/>
                   <span class="tel2">0755-33548988</span>
               </span>
               <span class="Service_tel">
                   <img src="${appConfig.resourcesRoot}/master/img/service_tel%20.png" alt="图片"/>
               </span>
        </div>
    </div>
</div>
<div class="wrapper">
    <header id="header" style="position: fixed; z-index: 100;width:100%;height:88px;background:#1F242A;">
        <div class="header"></div>
        <nav style="height:88px;">
            <div style="width:1210px;margin: 0 auto;">
                <a href="${appConfig.url}" class="navbar-brand logo f_left"></a>
                <div class="f_right">
                    <span>
                        <a href="${appConfig.url}" class="nav_title active">首页</a>
                        <a href="<c:url value='/product/index'/>" class="nav_title">产品</a>
                        <a href="<c:url value='/case/financial'/>" class="nav_title">解决方案</a>
                        <a href="<c:url value='/api/UseGuide'/>" class="nav_title">API文档</a>
                        <a href="<c:url value='/price/index'/>" class="nav_title">价格</a>
                        <!--<li><a href="#">体验&下载<span></span></a></li>-->
                        <c:choose>
                            <c:when test="${not empty sessionScope.userInfo}">
                                <a href="<c:url value='/accMgr/index'/>" class="control_center">用户控制中心</a>
                                <a href="<c:url value='/auth/logout'/>" class="back">退出</a>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/reg/toSign'/>" class="register">注册</a>
                                <a href="<c:url value='/auth'/>" class="login">登录</a>
                            </c:otherwise>
                        </c:choose>
                    </span>
                </div>
            </div>
        </nav>
    </header>
</div>
<div class="head_main">
    <div class="container">
        <div class="row main1">
            <div id="sp" style="position: relative; z-index: 1;" class="col-md-offset-2 col-md-8 col-md-offset-3 col-sm-offset-3 col-sm-6 col-sm-offset-3 col-xs-offset-3 col-xs-6 col-xs-offset-3 head_box">
                <p class="logo_big"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/logoL.png" alt="图片"/></p>
                <p class="logo_font"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/bannerFont.png" alt="图片"/></p>
                <p class="k_begin"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/QStartO.png" alt="快速开始图片"/></p>
                <p class="arrows_box"><img class="img-responsive arrows" src="${appConfig.resourcesRoot}/master/img/jiantou.png" alt="图片"/></p>
            </div>
            <div id="parent1">
                <div id="child"  class="ablr"></div>
                <div id="child1" class="ablr"></div>
                <div id="child2" class="ablr"></div>
                <div id="child3" class="ablr"></div>
                <div id="child4" class="ablr"></div>
                <div id="child5" class="ablr"></div>
                <div id="child6" class="ablr"></div>
                <div id="child7" class="ablr"></div>
                <div id="child17" class="ablr"></div>
                <div id="child8" class="abla"></div>
                <div id="child9" class="abla"></div>
                <div id="child10" class="abla"></div>
                <div id="child11" class="abla"></div>
                <div id="child12" class="abla"></div>
                <div id="child13" class="abla"></div>
                <div id="child14" class="abla"></div>
                <div id="child15" class="abla"></div>
                <div id="child16" class="abla"></div>
            </div>
        </div>
    </div>
</div>
<!--专通号-->
<div class="sec_main2" id="sec_main0">
    <div class="DeigMian">
        <dl class="DeiUl">
            <dt class="f_left"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/Designed_pic.png" alt="专号通" title="专号通"  class="DeiImg"/></dt>
            <dd class="f_right">
                <p class="DeTit">专号通</p>
                <p>用户在指定时间内可通过平台指派的专号联系，</p>
                <p>同时隐藏用户之间的真实联系方式从而避免用户</p>
                <p class="Dbtn-bottom">被恶意来电骚扰、以及信息泄露等风险</p>
                <p><a href="<c:url value='/product/designed'/>" class="hov">了解详情</a></p>
            </dd>
        </dl>
    </div>
</div>
<!--专通号 end-->
<div class="sec_main1">
    <dd class="DeigMian">
        <dl class="DeiUl">
            <dd class="f_left">
                <p class="DeTit">专线语音</p>
                <p>调用云通信平台的开放接口发起双向呼叫，</p>
                <p>业务系统能快速集成通信功能，</p>
                <p>常用于电销、客服、通知等场景，</p>
                <p class="Dbtn-bottom">零设备投入、零维护，按需所取、按量付费</p>
                <p><a href="<c:url value='/product/hbrest'/>" class="hov">了解详情</a></p>
            </dd>
            <dt class="f_right"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/hbrest.png" alt="专线语音" title="专线语音"  class="DeiImg"/></dt>
        </dl>
    </dd>
</div>
<div class="sec_main2">
    <div class="DeigMian">
        <dl class="DeiUl">
            <dt class="f_left"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/znydd.png" alt="智能云调度" title="智能云调度"  class="DeiImg"/></dt>
            <dd class="f_right Left20">
                <p class="DeTit TextTop">智能云调度</p>
                <p>提供呼叫中心码号代申请、语音短信落地一站式</p>
                <p>服务；实现调度网络全国覆盖、无缝接入话务调</p>
                <p>度云端处理、高并发、高可靠支持跨区域、跨运</p>
                <p>营商、按时间、按用户类别智能调度；支持移动</p>
                <p class="Dbtn-bottom">远程座席、易扩展性、更便携</p>
                <p><a href="<c:url value='/product/znydd'/>" class="hov">了解详情</a></p>
            </dd>
        </dl>
    </div>
</div>

<div class="banner">
    <div class="container">
        <div class="row ban_1">
            <h1>合作伙伴</h1>
        </div>
        <div class="row ban_2">
            <span class="bottom">玖云平台&nbsp;与您&nbsp;一起成长</span>
        </div>
        <div class="row ban_3">
            <div class="col-md-3 col-sm-3 col-xs-6 ">
                <a target="_blank" href="https://www.icbc-axa.com"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/gongyinganshengrenshou.png" alt="图片"/></a>
                <a target="_blank" href="http://www.ccnew.com"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/zhongyuanzhengjuan.png" alt="图片"/></a>
                <a target="_blank" href="http://www.lianjia.com"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/lianjia.png" alt="图片"/></a>
            </div>
            <div class="col-md-3 col-sm-3 col-xs-6">
                <a target="_blank" href="http://new.gf.com.cn"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/guangfazhengquan.png" alt="图片"/></a>
                <a target="_blank" href="http://www.swsc.com.cn"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/xinanzhengquan.png" alt="图片"/></a>
                <a target="_blank" href="http://sz.centanet.com"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/zhongyuandichan.png" alt="图片"/></a>
            </div>
            <div class="col-md-3 col-sm-3 col-xs-6">
                <a target="_blank" href="http://www.guosen.com.cn"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/guoxinzhengquan.png" alt="图片"/></a>
                <a target="_blank" href="http://www.zto.com"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/zhongtongkuaidi.png" alt="图片"/></a>
                <a target="_blank" href="http://www1.fang.com"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/soufangwang.png" alt="图片"/></a>
            </div>
            <div class="col-md-3 col-sm-3 col-xs-6">
                <a target="_blank" href="http://www.cindasc.com"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/xindazhengjuan.png" alt="图片"/></a>
                <a target="_blank" href="http://www.deppon.com"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/debang.png" alt="图片"/></a>
                <a target="_blank" href="http://shenzhen.qfang.com"><img class="img-responsive" src="${appConfig.resourcesRoot}/master/img/Qfangwang.png" alt="图片"/></a>
            </div>
        </div>
    </div>
</div>

<div class="footer">
    <div class="foot_box">
        <div class="foot1">
            <div class="linePro list_right f_left">
                <h4 class="HLeft">产品</h4>
                <p><a href="<c:url value='/product/hbrest'/>">专线语音</a></p>
                <p><a href="<c:url value='/product/designed'/>">专号通</a></p>
                <p><a href="<c:url value='/product/znydd'/>">智能云调度</a></p>
                <p><a href="<c:url value='/price/index'/>">价格</a></p>
            </div>
            <div class="lineCase list_right f_left">
                <h4 class="HRight">解决方案</h4>
                <p><a href="<c:url value='/case/financial'/>">金融行业</a></p>
                <p><a href="<c:url value='/case/logistics'/>">物流行业</a></p>
                <p><a href="<c:url value='/case/estate'/>">地产行业</a></p>
                <%--<p><a href="<c:url value='/case/education'/>">教育行业</a></p>--%>
            </div>
            <div class="line1 list_right f_left" style="width:310px;">
                <h4>API文档</h4>
                <div class="SWidth1 f_left">
                    <p class="ALeft"><a href="<c:url value="/api/UseGuide"/>">新手指南</a></p>
                    <p><a href="<c:url value="/api/PlatformAudit"/>">平台政策规范</a></p>
                    <p><a href="<c:url value="/api/RESTInterface"/>">REST&nbsp;API</a></p>
                    <p><a href="<c:url value="/api/CallbackInterface"/>">AS回调通知接口</a></p>
                </div>
                <div class="SWidth2 f_right">
                    <p class="ALeft"><a href="<c:url value="/api/globalErrorCode"/>">全局错误码</a></p>
                    <p><a href="javascript:void(0);">常见问题</a></p>
                </div>
            </div>
            <div class="line2 list_right f_left">
                <h4 class="HRight">联系</h4>
                <p><a href="javascript:void(0);" id="contactus">联系客服</a></p>
                <p><a href="<c:url value='/contactus'/>">联系我们</a></p>
                <p><a href="<c:url value='/aboutas'/>">关于我们</a></p>
                <p><a href="javascript:void(0);">0755-33548988</a></p>
            </div>
            <div class="line3 list_right f_left">
                <h4>微信</h4>
                <div class="ewm">
                    <img src="${appConfig.resourcesRoot}/master/img/ewm.png" alt="二维码"/>
                </div>
            </div>
            <div class="line4 list_right f_left">
                <h4>分享</h4>
                <div class="list">
                    <a href="javascript:void(0);" name="weixin" target="_blank"><img src="${appConfig.resourcesRoot}/master/img/weixin.png" alt="微信"/></a>
                    <a href="javascript:void(0);" name="tsina" target="_blank"><img src="${appConfig.resourcesRoot}/master/img/weibo.png" alt="微博"/></a>
                    <a href="javascript:void(0);" name="cqq" target="_blank"><img src="${appConfig.resourcesRoot}/master/img/QQ.png" alt=" QQ "/></a>
                </div>
            </div>
        </div>
        <div class="foot2 clear">
            <p>Copyright &copy;深圳市大众通信技术有限公司.All Rights Reserved.玖云平台 版权所有<br/><a href="http://www.miitbeian.gov.cn" target="_blank">粤ICP备08007507号-2</a></p>
        </div>
    </div>
</div>

</body>

<script src="${appConfig.resourcesRoot}/master/js/bootstrap.js"></script>

<%--<script charset="utf-8" src="http://wpa.b.qq.com/cgi/wpa.php"></script>--%>
<script type="text/javascript">
    //单个
    BizQQWPA.addCustom({
        aty: '0', //指定工号类型,0-自动分流，1-指定工号，2-指定分组
        a:'0',
        nameAccount: '800164600', //营销 QQ 号码
        selector: 'onlineService' //WPA 被放置的元素
    });

    //单个
    BizQQWPA.addCustom({
        aty: '0', //指定工号类型,0-自动分流，1-指定工号，2-指定分组
        a:'0',
        nameAccount: '800164600', //营销 QQ 号码
        selector: 'contactus' //WPA 被放置的元素
    });

    $(".Nine_platform").hover(function(){
        $(".Nine_platform_main").animate({right:57},{"duration":200});
    },function(){
        $(".Nine_platform_main").animate({"right":-118},{"duration":200}).delay(3)
    });
    $(".Service_tel").hover(function(){
        $(".Service_tel_main").animate({"right":57},{"duration":200});
    },function(){
        $(".Service_tel_main").animate({"right":-153},{"duration":200});
    });
</script>
<script>
    $(function(){
        function update(){
            var sph = $("#sp").height();
            // console.log(sph);
            var inh = window.innerHeight - 79;
            var arrp = [430, 160, 60, 80];
            var arri = [430, 160, 60, 23];
            var arrt = [20, 20, 35, 90];

            // console.log(inh);
            var i = inh*1.0/900;

            $(".main1").css("height",window.innerHeight - 40);
            $("#parent1").css("height",document.body.clientWidth).css({"position" : "absolute", "top" : 0, "left" : 0});

            $("#sp > p").height(function(){
                var index = $(this).index();
                $(".img-responsive", this).height(arri[index] * i);

                $(this).css("margin-top", arrt[index] * i + "px");
                return arrp[index] * i;
            });

        }
        //setInterval(update,1);
        // 监听窗口改变事件
        update();
        $(window).resize(update);

        var p = 0, t = 0, scrolled = true;
        $(window).scroll(function(){
            var tpp = $("#sec_main0").offset().top;
            p = $(this).scrollTop();

            if (scrolled) {
                // 延迟加载
                // setTimeout(function(){

                    if (p < tpp && t < p) {
                        scrolled = false;
                        $("html,body").stop();
                        $("html,body").animate({scrollTop: tpp}, 400, function(){ scrolled = true; });
                    }

                    if (p < tpp && t > p) {
                        scrolled = false;
                        $("html,body").stop();
                        $("html,body").animate({scrollTop: 0}, 400, function(){ scrolled = true; });
                    }
                    //setTimeout(function(){t=p;},0);
                // },100);
            }
            setTimeout(function(){t=p;},0);
        });

        $(".k_begin>img").hover(function(){
            $(".k_begin>img").attr("src","${appConfig.resourcesRoot}/master/img/QStartT.png");
        },function(){
            $(".k_begin>img").attr("src","${appConfig.resourcesRoot}/master/img/QStartO.png");
        }).click(function(){
            window.location.href = "<c:url value='/reg/toSign'/>"
        });

        $(".ban_3 a").hover(function(){
            $(this).addClass("change").animate({"width":"101%"},{"duration":100}).animate({"height":"101%"},{"duration":100});
        },function(){
            $(this).removeClass("change").animate({"width":"100%"},{"duration":100}).animate({"height":"100%"},{"duration":100});
        });

        // 下滑箭头，上下浮动效果
        function change(){
            $(".arrows").animate({"top":30,"scale":0.1,"opacity":0},500).animate({"top":0,"scale":1,"opacity":1},1000)
        }
        setInterval(change,100);

        // 下滑箭头点击时
        $(".arrows_box").click(function(){
            $("html,body").animate({scrollTop : $("#sec_main0").offset().top},500);
        });

        $('a').bind("focus", function(){
            $(this).blur();
        });

        // 分享图标变亮
        $(".list img").hover(function(){
            var src =  $(this).attr("src");
            $(this).attr("src", src.replace(".png","1.png"));
        },function(){
            var src =  $(this).attr("src");
            $(this).attr("src", src.replace("1.png",".png"));
        });

        // 分享
        var jiathis_url = "http://www.jiathis.com/send/?";
        var jiathis_title = "玖云平台";
        var jiathis_summary = "通讯无界  云端共享";
        var url = "http://www.33e9cloud.com";

        // 添加分享链接
        $('div.list a').each(function(){
            var webid = $(this).attr('name');
            var jiathis_herf = jiathis_url + "webid=" + webid + "&url=" + url + "&title=" + jiathis_title + "&summary=" + jiathis_summary + "&uid=1";

            $(this).attr('href', jiathis_herf);
        });

    })
</script>
</html>