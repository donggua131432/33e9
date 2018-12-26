/**
 * Created by Administrator on 2016/2/16.
 */
$(function(){

    $(".Product_hover1").hover(function(){
        var rootUrl = $(this).attr("data-url");
        $(".common_title1").removeClass("rest_pic1").addClass("rest_pic2");
        $(".pro_common1").addClass("pro_common_hover");
        $(".Product_hover1>p").addClass("pro_common_hover");
        $(".img1").attr("src", rootUrl + "img/rest_picTwo.png")

    },function(){
        var rootUrl = $(this).attr("data-url");
        $(".common_title1").removeClass("rest_pic2").addClass("rest_pic1");
        $(".pro_common1").removeClass("pro_common_hover");
        $(".Product_hover1>p").removeClass("pro_common_hover");
        $(".img1").attr("src", rootUrl + "img/rest_pic1.png")
    }).click(function(){
        toskip($(this).find('a').attr('href'));
    });

    $(".Product_hover2").hover(function(){
        var rootUrl = $(this).attr("data-url");
        $(".common_title2").removeClass("Designed_pic1").addClass("Designed_pic2");
        $(".pro_common2").addClass("pro_common_hover");
        $(".Product_hover2>p").addClass("pro_common_hover");
        $(".img2").attr("src", rootUrl + "img/Designed_pic22.png")
    },function(){
        var rootUrl = $(this).attr("data-url");
        $(".common_title2").removeClass("Designed_pic2").addClass("Designed_pic1");
        $(".pro_common2").removeClass("pro_common_hover");
        $(".Product_hover2>p").removeClass("pro_common_hover");
        $(".img2").attr("src", rootUrl + "img/Designed_pic2.png")
    }).click(function(){
        toskip($(this).find('a').attr('href'));
    });

    $(".Product_hover3").hover(function(){
        var rootUrl = $(this).attr("data-url");
        $(".common_title3").removeClass("ydd_pic1").addClass("ydd_pic2");
        $(".pro_common3").addClass("pro_common_hover");
        $(".Product_hover3>p").addClass("pro_common_hover");
        $(".img3").attr("src", rootUrl + "img/Traffic_scheduling2.png")
    },function(){
        var rootUrl = $(this).attr("data-url");
        $(".common_title3").removeClass("ydd_pic2").addClass("ydd_pic1");
        $(".pro_common3").removeClass("pro_common_hover");
        $(".Product_hover3>p").removeClass("pro_common_hover");
        $(".img3").attr("src", rootUrl + "img/Traffic_scheduling1.png")
    }).click(function(){
        toskip($(this).find('a').attr('href'));
    });

    $(".spread_tb").click(function(){
        $(".api_right_box").animate({top:0},200);
        $(".xtb").animate({top:0},200)
    });

    $(".close_tb").click(function(){
        $(".api_right_box").animate({top:-425},200);
        $(".xtb").animate({top:-398},200)
    });
    $(".api_right_center").delegate("p","click",function(){
        $(this).addClass("api_center_hover");
        $(this).children("b").addClass("api_b_hover");
        $(this).siblings().removeClass("api_center_hover");
        $(this).siblings().children("b").removeClass("api_b_hover");
    });

    $("a").bind("focus",function(){
        $(this).blur();
    });

});

function toskip(url){
    window.open(url,"_self")
}
