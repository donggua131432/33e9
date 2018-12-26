$(function(){
    /*function update(){
        $("#wrapper").css("height",window.innerHeight);
        $("#header").css("height","6%");
        $("#section").css("height","64%");
        $(".footer").css("height","30%");
    }
    setInterval(update,100);*/

    $("input").focus(function(){
        $(this).addClass("shadow")
    });
    $("input").blur(function(){
        $(this).removeClass("shadow")
    });
    /*var SH=$(window).height();
    var fh=$(".footer").height();
    var hH=$(".header").height();
    $(".website_container").height(SH-fh-hH+64);*/
})


