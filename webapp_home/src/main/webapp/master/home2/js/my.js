/**
 * BZB: 2016/8/1.
 */
;
! function(){
    window.pageController={
        init:function(){
            this.page(),
                this.animation()
        },
        page:function(){
            var a=$('html,body');
            var b=$('.navText');
            var c= b.find("a");
            var t=0;
            var sTop=0;
            var scrolled=true;
            var arrows=$(".jumTop");
            var bannerTop=$(".slides");
            var header=$(".header");
            var ico=$(".ico");
            var $tab_li = $('#tab ul li');
            //首屏滑动滚动条效果
            $(window).scroll(function(){
                var winH=window.innerHeight;
                var sTop=$(window).scrollTop();
                if(scrolled){
                    if(sTop<winH && winH>670 && t<sTop){
                        scrolled=false;
                        a.stop();
                        a.animate({"scrollTop":winH},500,function(){
                            scrolled=true;
                        });

                    }
                    if(sTop<winH && t>sTop){
                        scrolled=false;
                        a.stop();
                        a.animate({"scrollTop":0},500,function(){
                            scrolled=true;
                        });
                    }
                }
                setTimeout(function(){t=sTop},0)
            });
            //点击下拉按钮屏幕下滑一屏效果
            arrows.click(function(){
                var winH=window.innerHeight;
                if(winH>670){
                    a.animate({scrollTop:winH},500);
                    //bannerTop.addClass("padTop90");
                }else{
                    return false;
                }

            });
            //菜单点击高亮
            c.click(function(){

            });

            //API接口鼠标悬停效果
            ico.hover(function(){
                if($(this).find("img").attr("src")=="images/img_1.png"){
                    $(this).find("img").attr("src","images/img_1hover.png")
                }else if($(this).find("img").attr("src")=="images/img_2.png"){
                    $(this).find("img").attr("src","images/img_2hover.png")
                }else if($(this).find("img").attr("src")=="images/img_3.png"){
                    $(this).find("img").attr("src","images/img_3hover.png")

                }
            },function(){
                if($(this).find("img").attr("src")=="images/img_1hover.png"){
                    $(this).find("img").attr("src","images/img_1.png")
                }else if($(this).find("img").attr("src")=="images/img_2hover.png"){
                    $(this).find("img").attr("src","images/img_2.png")
                }else if($(this).find("img").attr("src")=="images/img_3hover.png"){
                    $(this).find("img").attr("src","images/img_3.png")
         }
            });
            //o2o TAB切换
            $tab_li.click(function(){
                $(this).addClass('selected').siblings().removeClass('selected');
                var index = $tab_li.index(this);
                $('div.tab_box > div').eq(index).show().siblings().hide();
            });

        },
        animation:function(){

        }
    };
    $(function() {
        pageController.init();
        $(window).on("scroll", function() {
            var animations, name, winHeight = $(window).height(),
                scrollTop = $(window).scrollTop();
            animations = scrollController.animations;
            for (name in animations) {
                animations[name](winHeight, scrollTop);
            }
        });
        if ($(window).height() > 600) {
            $(window).trigger("scroll");
        }
        current();
    })

    function current(){
        var b=$('.navText');
        var c= b.find("a");
        var h=$(".loginbtn");
        var o= h.find("a");
        var url=window.location.href;
        o.removeClass("cur");
        c.removeClass("current");
        c.each(function(i,n){
            var _this = $(n);
            var href = _this.attr("href");
            if (url.indexOf(href) > -1) {
                _this.addClass('current');
            }
        });
        o.each(function(i,n){
            var _this = $(n);
            var href = _this.attr("href");
            if (url.indexOf(href) > -1) {
                _this.addClass('cur');
            }
        });
    }
}();
scrollController = {
    init: function (el, wh, st, delta) {
        try {
            delta = delta ||wh;
            return $(el).offset().top <= wh + st - delta;
        } catch (e) {
        }
    },
    init1: function (el, wh, st) {
        try {
            return wh+900 > wh + st;
        } catch (e) {
        }
    },
    animations: {
        a: function (wh, st) {
            var $el = $("#head"),
                b=$(".banner");
            if (scrollController.init($el, wh, st)) {
                $el.css({"position":"fixed","left":0,"top":0}).css("zIndex",9999);
                b.css("paddingTop",90);
            }
            if(scrollController.init1($el, wh, st)){
                $el.css("position","relative");
                b.css("paddingTop",0);
            }
        }
    }
};
