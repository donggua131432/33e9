function update(){
    $("#wrapper").css("height",window.innerHeight);
    $("#header").css("height","6%");
    $("#section").css("height","64%");
    $(".footer").css("height","30%");
}
setInterval(update,100);

$("input").focus(function(){
   $(this).addClass("shadow")
});
$("input").blur(function(){
   $(this).removeClass("shadow")
});

