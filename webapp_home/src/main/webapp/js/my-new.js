/**
 * Created by Administrator on 2016/3/30.
 */

$(document).ready(function() {
    //function change() {
    //     $("section").height($(document).height()-160);
    //     $("#sec_main").height($(document).height()-80);
    //     $(".main1").height($(document).height()-80);
    //     $(".main2").height($(document).height()-80);
    //     $(".main3").height($(document).height()-80);
    // }
    // $(window).resize(change);
    // change();
    //$(window).bind('scroll', function(){change()});

    $(".pay_method").delegate("a","click",function(){
        $(this).addClass("hover_border");
        $(this).siblings("a").removeClass("hover_border");
        $(this).children("i").show();
        $(this).siblings("a").children("i").hide();
    })
    $(".rate_box").delegate(".call_rate","click",function(){
        $(this).addClass("rate_hover");
        $(this).siblings().removeClass("rate_hover")
    })

    $(".rate_date>tr").hover(function(){
        $(this).addClass("tr_hover");
    },function(){
        $(this).removeClass("tr_hover")
    })

    $(".msg_state").delegate(".btn_style","click",function(){
        $(this).addClass("hover_state_btn");
        $(this).siblings().removeClass("hover_state_btn")
    })

    $(".main1").delegate("li","click",function(){
        $(this).addClass("hover");
        $(this).siblings().removeClass("hover");
    });

    $("#zh").click(function(){
        $("#Ac_manage").show();
        $("#ZNYDD").hide();
    });
    $("#zn").click(function(){
        $("#Ac_manage").hide();
        $("#ZNYDD").show();
    });

    $(".left").delegate("a","click",function(){
        $(this).addClass("p_hover");
        $(this).siblings().removeClass("p_hover");
        //$(this).parent().siblings(".left").children("a").removeClass("p_hover");
        $(this).parent(".left").parent(".left-common").siblings(".left-common").children(".left").children("a").removeClass("p_hover")
    });
    $('.disableCss').removeAttr('href').removeAttr('onclick').css("text-decoration","none").css("color","#afafaf").css("outline","none").css("cursor","default");

    var state1=true;
    function fadeTo1(){
        if(state1){
            $(".detailBox1").removeClass("checkDetailB").addClass("smAddNumber");
            $(".detailMain1").slideUp(200);
            state1=false;
        }else{
            $(".detailBox1").removeClass("smAddNumber").addClass("checkDetailB");
            $(".detailMain1").slideDown(200);
            state1=true;
        }
    }
    $(".detailBox1").click(function(){
        fadeTo1();
    });

    $(".fourNum1").delegate("p","click",function(){
        $(this).addClass("addNumHover");
        $(this).siblings().removeClass("addNumHover");
    });

    var state2=true;
    function fadeTo2(){
        if(state2){
            $(".detailBox2").removeClass("checkDetailB").addClass("checkDetailR");
            $(".detailMain2").slideUp(200);
            state2=false;
        }else{
            $(".detailBox2").removeClass("checkDetailR").addClass("checkDetailB");
            $(".detailMain2").slideDown(200);
            state2=true;
        }
    }
    $(".detailBox2").click(function(){
        fadeTo2();
    });

    $(".numInput1").focus(function(){
        $(".genBtn1").addClass("blueBtn").removeClass("greyBtn");
        $(".genBtn2").addClass("greyBtn").removeClass("blueBtn")
    });
    $(".numInput2").focus(function(){
        $(".genBtn1").removeClass("blueBtn").addClass("greyBtn");
        $(".genBtn2").removeClass("greyBtn").addClass("blueBtn")
    });
    var state3=true;
    function fadeTo3(){
        if(state3){
            $(".detailBox3").removeClass("checkDetailB").addClass("checkDetailR");
            $(".detailMain3").slideUp(200);
            state3=false;
        }else{
            $(".detailBox3").removeClass("checkDetailR").addClass("checkDetailB");
            $(".detailMain3").slideDown(200);
            state3=true;
        }
    }
    $(".detailBox3").click(function(){
        fadeTo3();
    });

    $(".fourNum2").delegate("p","click",function(){
        $(this).addClass("clearNumHover");
        $(this).siblings().removeClass("clearNumHover");
    });
    var state4=true;
    function fadeTo4(){
        if(state4){
            $(".detailBox4").removeClass("checkDetailB").addClass("checkDetailR");
            $(".detailMain4").slideUp(200);
            state4=false;
        }else{
            $(".detailBox4").removeClass("checkDetailR").addClass("checkDetailB");
            $(".detailMain4").slideDown(200);
            state4=true;
        }
    }
    $(".detailBox4").click(function(){
        fadeTo4();
    });
    $.fn.scrollUnique = function() {
        return $(this).each(function() {
            var eventType = 'mousewheel';
            if (document.mozHidden !== undefined) {
                eventType = 'DOMMouseScroll';
            }
            $(this).on(eventType, function(event) {
                // 一些数据
                var scrollTop = this.scrollTop,
                    scrollHeight = this.scrollHeight,
                    height = this.clientHeight;

                var delta = (event.originalEvent.wheelDelta) ? event.originalEvent.wheelDelta : -(event.originalEvent.detail || 0);

                if ((delta > 0 && scrollTop <= delta) || (delta < 0 && scrollHeight - height - scrollTop <= -1 * delta)) {
                    // IE浏览器下滚动会跨越边界直接影响父级滚动，因此，临界时候手动边界滚动定位
                    this.scrollTop = delta > 0? 0: scrollHeight;
                    // 向上滚 || 向下滚
                    event.preventDefault();
                }
            });
        });
    };
    $(".fourNumberShow").scrollUnique();
    $(".showTable").scrollUnique();

    $(".amplification").click(function(){
        var src = $(this).next().find("img").attr("src");
        if (src) {
            $(".scaleImgBox img").attr("src", src);
            $(".scaleImgBox").show();
        }
    });

    $(".certificateImg").click(function(){
        var src = this.src;
        if (src) {
            $(".scaleImgBox img").attr("src", src);
            $(".scaleImgBox").show();
        }
    });

    document.onclick = function(event) {
        if (event) {
            var mark = $(event.target).attr("data-mark");
            if (mark != "mark") {
                narrow();
            }
        }
    }

});

function narrow(){
    $(".scaleImgBox").hide();
    $(".scaleImgBox img").attr("src", "");
}