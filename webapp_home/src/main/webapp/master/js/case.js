/**
 * Created by Administrator on 2016/2/17.
 */
$(function(){
    $(".One span").click(function(){
        var url = $(this).attr("data-url");
        window.location.href = url;
    });
});