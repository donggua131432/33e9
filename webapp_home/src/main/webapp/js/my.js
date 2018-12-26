/**
 * Created by Administrator on 2016/1/15.
 */

$(document).ready(function() {
    /*function change() {
        $("section").height($(document).height()-180);
        $("#sec_main").height($(document).height()-90);
        $(".main1").height($(document).height()-90);
        $(".main2").height($(document).height()-90);
        $(".main3").height($(document).height()-90);
    }
    $(window).resize(change);
    change();

    $(window).bind('scroll', function(){change()});*/

    $(".pay_method").delegate("a","click",function(){
        $(this).addClass("hover_border");
        $(this).siblings("a").removeClass("hover_border");
        $(this).children("i").show();
        $(this).siblings("a").children("i").hide();
    });
    $(".rate_box").delegate(".call_rate","click",function(){
        $(this).addClass("rate_hover");
        $(this).siblings().removeClass("rate_hover")
    });

    //$(".rate_date>tr").hover(function(){
    //    $(this).addClass("tr_hover");
    //},function(){
    //    $(this).removeClass("tr_hover")
    //});

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


    //话务总览效果
    $(".dataRange").click(function(){
        if(!$(this).hasClass("dataRangeHover")){
            $(this).addClass("dataRangeHover");
            $(this).siblings().removeClass("dataRangeHover");
        }
    });

    //呼叫中心列表效果
    // var boo=new Boolean(true);
    $(".demo4>input").click(function(){
        if(this.checked){
            $("#closed").show();
            $("#open").hide();
            // boo=false;
            // console.log(this.checked);
        }else{
            $("#closed").hide();
            $("#open").show();
            // boo=true;
            // console.log(this.checked);
        }

    })

    $(".demo3>input").click(function(){
        if(this.checked){
            $("#close").hide();
            $("#opend").show();

            // boo=false;
            // console.log(this.checked);
        }else{
            $("#close").show();
            $("#opend").hide();
            // boo=true;
            // console.log(this.checked);
        }

    })

    //左侧导航栏选择效果
    /*$(".main1 li").click(function(){
        var idx=$(this).index()+1;
        var id=this.id;
        if(id=="zh"){
            $("#zn").find("i").removeClass("sec_im2Hover").addClass("sec_im2");
            $("#kf").find("i").removeClass("sec_im3Hover").addClass("sec_im3");
            $("#sp").find("i").removeClass("sec_im4Hover").addClass("sec_im4");
        }else if(id=="zn"){
            $("#zh").find("i").removeClass("sec_im1Hover").addClass("sec_im1");
            $("#kf").find("i").removeClass("sec_im3Hover").addClass("sec_im3");
            $("#sp").find("i").removeClass("sec_im4Hover").addClass("sec_im4");
        }else if(id=="kf"){
            $("#zh").find("i").removeClass("sec_im1Hover").addClass("sec_im1");
            $("#zn").find("i").removeClass("sec_im2Hover").addClass("sec_im2");
            $("#sp").find("i").removeClass("sec_im4Hover").addClass("sec_im4");
        }else if(id=="sp"){
            $("#zh").find("i").removeClass("sec_im1Hover").addClass("sec_im1");
            $("#zn").find("i").removeClass("sec_im2Hover").addClass("sec_im2");
            $("#kf").find("i").removeClass("sec_im3Hover").addClass("sec_im3");
        }else{return false}
        $(this).siblings("li").removeClass("hover");
        $(this).siblings("li").find("span").removeClass("fontHover").addClass("font");
        $(this).addClass("hover");
        $(this).find('i').removeClass("sec_im"+idx).addClass("sec_im"+idx+"Hover");
        $(this).find("span").removeClass("font").addClass("fontHover");
    })*/
});



$("#zh").click(function(){
    $("#Ac_manage").show();
    $("#ZNYDD").hide();
    $("#open_interface").hide();
});
$("#zn").click(function(){
    $("#Ac_manage").hide();
    $("#ZNYDD").show();
    $("#open_interface").hide();
});
$("#kf").click(function(){
    $("#Ac_manage").hide();
    $("#ZNYDD").hide();
    $("#open_interface").show();
});


$(".left").delegate("a","click",function(){
    $(this).addClass("p_hover");
    $(this).siblings().removeClass("p_hover");
    $(this).parent().siblings(".left").children("a").removeClass("p_hover");
    console.log($(this).parents("details").siblings("details").children(".left").children("a").removeClass("p_hover"))
});

function narrow(){
    $(".scaleImgBox").hide();
    $(".scaleImgBox img").attr("src", "");
}
//创建模板效果
$("#templateStatus").change(function(){
    var val=$(this).val();
    if(val==""||val==null)return false;
    else if(val=="文本文件"){
        $("#txt_template").css("display","block");
        $("#voice_template").css("display","none");
    }else if(val=="语音文件"){
        $("#txt_template").css("display","none");
        $("#voice_template").css("display","block");
    }
})

$(function(){
    //云话机数据统计效果

    /*$(".tabNav>li").click(function(){
        $(this).removeClass("tabListInitBg").addClass("tabListBg");
        $(this).siblings("li").removeClass("tabListBg").addClass("tabListInitBg")
        if($(this).text()=="回拨"){
            $(".huibo").css("display","block");
            $(".zhibo").css("display","none");
            $(".huihu").css("display","none");
        }else if($(this).text()=="直拨"){
            alert("nimei");
            $(".huibo").css("display","none");
            $(".zhibo").css("display","block");
            $(".huihu").css("display","none");
        }else if($(this).text()=="回呼"){
            $(".huibo").css("display","none");
            $(".zhibo").css("display","none");
            $(".huihu").css("display","block");
        }else{
            return false;
        }

    })*/;

//选择日期效果
    $(".dateAllCheck").click(function(){
        if($(this).get(0).checked){
            checkAll();
        }else{
            uncheckAll();
        }
    })

    $(".allCheck").click(function(){
        var spans=$(".dateBox>span");
        if($(this).get(0).checked==true) {
            for(var j=0;j<spans.length;j++){
                if(!(spans[j].className=="disableCss")){
                    spans[j].className="checkBg"
                }else if(spans[j].className=="disableCss"){
                    break;
                }
            }
        }else{
            spans.removeClass("checkBg")
        }
    });

    $(".dateBox>span").click(function(){
        if($(this).hasClass("checkBg")){
            $(this).removeClass("checkBg");
        }else{
            if($(this).hasClass("disableCss")){
                return false;
            }else{
                $(this).addClass("checkBg");
            }
        }
    })

});


function checkAll()
{
    var code_Values = document.all['weekday'];
    if(code_Values.length){
        for(var i=0;i<code_Values.length;i++)
        {
            code_Values[i].checked = true;
        }
    }else{
        code_Values.checked = true;
    }
}
function uncheckAll()
{
    var code_Values = document.all['weekday'];
    if(code_Values.length){
        for(var i=0;i<code_Values.length;i++)
        {
            code_Values[i].checked = false;
        }
    }else{
        code_Values.checked = false;
    }
}

