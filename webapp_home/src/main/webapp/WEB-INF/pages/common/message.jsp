<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<p><i></i><a id="messageA" href="<c:url value="/msgMgr/query"/>">未读信息</a></p>
<script>
    $(function(){
        $.ajax({
            type: "post",//使用post方法访问后台
            dataType: "json",//返回json格式的数据
            url: "<c:url value='/msgMgr/getcount'/>",
            success: function (data) {
                $('#messageA').text(data.countB + "个未读消息");
                if(!data.hasRead && data.countB > 0) {
                    var unRead = '<div class="modal-box phone_t_code" id="unRead">';
                    unRead += '<div class="modal_head">';
                    unRead += '<h4><i class="common_icon nomsg_txt"></i>消息提醒</h4>';
                    unRead += '<div class="p_right"><a href="javascript:;" onclick="clmsg();" class="common_icon close_txt"></a></div>';
                    unRead += '</div>';
                    unRead += '<div class="modal-common_body mdl_pt_body">';
                    unRead += '<p class="sm_font">你有未读消息</p>';
                    unRead += '<div class="msg_notice">';
                    unRead += '<div class="nordmsg_center">';
                    unRead += '<span class="red_font">' + data.countB + '</span>';
                    unRead += '<span class="unit">条</span>';
                    unRead += '</div>';
                    unRead += '<button type="button" onclick="toMsg();" class="qu_reading">立即阅读</button>';
                    unRead += '</div>';
                    unRead += '</div>';
                    unRead += '</div>';

                    $("body").prepend('<div id="shadeWin_msg" class="shade_on_win"></div>');
                    $("div[class^='container']").append(unRead);
                }
            }
        });
    });

    // 跳转到消息页面
    function toMsg() {
        window.location.href = "<c:url value="/msgMgr/query"/>";
    }

    // 跳转到消息页面
    function clmsg() {
        $("#unRead").hide();
        $("#shadeWin_msg").hide();
    }
</script>

