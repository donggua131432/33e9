/**
 * Created by Administrator on 2016/8/9.
 */
$(function() {
    var bodyHeight = 0;
    var b = $('.navText');
    var c = b.find("a");
    c.click(function () {
        c.removeClass('current');
        $(this).addClass('current');
    });

        if ($(".dl>dt").children("span:last-child").hasClass("bouLt1")) {
            $(".dl:gt(0)>dd").hide();
        }
        $(".VersionBox").hide();

        $(".dl>dt").click(function () {
            if ($(this).children("span:last-child").hasClass("bouLt2")) {
                $(this).children("span:last-child").removeClass("bouLt2").addClass("bouLt1");
                $(this).parent("dl").children("dd").slideUp(200);
            } else {
                $(this).parent("dl").siblings("dl").children("dd").slideUp(200);
                $(this).parent("dl").siblings("dl").children("dt").children("span:last-child").removeClass("bouLt2").addClass("bouLt1");
                $(this).children("span:last-child").removeClass("bouLt1").addClass("bouLt2");
                $(this).parent("dl").children("dd").slideDown(200);
            }
        });
        $("dl").delegate("dd", "click", function () {
            $(this).addClass("dd_hover");
            $(this).siblings("dd").removeClass("dd_hover");
            $(this).parent("dl").siblings("dl").children("dd").removeClass("dd_hover");
        });

        $(".checkMore").click(function () {
            $(this).hide();
            $(this).parent("div").parent("div").css("height", "100%");
            $(this).parent().next().slideDown(500);
        })

        $(".back").click(function () {
            $(this).parent().next().slideUp(500);
            $(this).parent("div").parent("div").css("height", 56);
            $(this).parent("div").prev("div").find("span:last-child").show();
        });

        $("dl").delegate("dd", "click", function () {
            /*$(this).addClass("dd_hover") ;
             $(this).siblings("dd").removeClass("dd_hover") ;
             $(this).parent("dl").siblings("dl").children("dd").removeClass("dd_hover");*/
            var url = $(this).attr("data-url");
            if (url) {
                window.location.href = url;
            }
        });

        $("a").bind("focus", function () {
            $(this).blur();
        });

        // �������
        $(".close_tb").on("click", function () {
            $('body,html').animate({scrollTop: 0}, 500);
        });

        // �Ҳ���Ŀ����¼�
        $(".api_right_center p").on('click', function () {

            //$(window).unbind('scroll');
            isclick = true;

            var i = $(this).index() + 1;
            if (i == 1) {
                $('body,html').animate({scrollTop: 0}, 500, function () {
                    isclick = false;
                    /*$(window).bind('scroll',scrolls);*/
                });
            } else {
                var h = $(".API" + i).offset().top;
                $('body,html').animate({scrollTop: h - 120}, 500, function () {
                    isclick = false;
                    /*$(window).bind('scroll',scrolls);*/
                });
            }
        });

        for (var i = 1; i < $(".API_main2 .api_m2_box").length + 1; i++) {
            var h = $(".API" + i).offset().top;
            his.push(h);
        }
        $(window).bind('scroll', scrolls);

        his[0] = 0; // ��һ������
        his.push(9000000); // ���һ������ // ����һ���㹻���ֵ

        scrolls();
        scrollsTo();

        $(".api_right_center p").eq(0).addClass('api_center_hover').find('b').addClass('api_b_hover');

    });
    /*var pathName = window.location.pathname;
     var paths = ["RESTInterface", "dedicatedVoice", "specialInterface", "specialNumAppleInterface", "specialNumCheckInterface", "specialNumReleaseInterface", "AppMapNumCheckInterface", "CancelCallback", "recordDownLoad"];


     var pflag = false;
     for (var i in paths) {
     if (pathName.indexOf(paths[i]) > 0) {
     pflag = true;
     break;
     }
     }*/

    var offsetB = 809; //: 809; // 1109 // 1120

    var his = [];
    var isclick = false;

    function scrolls() {

        bodyHeight = $('body').height();

        var t = $(window).scrollTop() + 150;

        if (!isclick) {
            $.each(his, function (i, n) {
                if (t > n && t <= his[i + 1]) {
                    $(".api_right_center p").eq(i).siblings().removeClass('api_center_hover').find('b').removeClass('api_b_hover');
                    $(".api_right_center p").eq(i).addClass('api_center_hover').find('b').addClass('api_b_hover');
                }
            });
        }
//            var s = bodyHeight - 809;
        var s = bodyHeight - offsetB;
        //if ((t - 30) > s) {
        //    $(".API_main3,.API_main1").css({"position": "absolute", top: s + "px"});
        //    $("#apima2").css({"position": "absolute", top: (s - 155) + "px"});
        //} else {
        //    $(".API_main3,.API_main1").css({"position": "fixed"});
        //    $("#apima2").css({"position": "fixed", top: "85px"});
        //}
    }

    // ֱ�Ӷ�λ��ĳ��λ��
    function scrollsTo() {
        var target = getQueryString("target");
        if (target) {
            var $target = $("#" + target);
            $('body,html').animate({scrollTop: $target.offset().top - 120}, 500);
        }
    }

    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
