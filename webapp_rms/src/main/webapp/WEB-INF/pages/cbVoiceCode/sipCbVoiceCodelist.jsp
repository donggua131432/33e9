<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>个人资料</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">

        $(function(){
            initDicData("voiceCode", "voiceCode", false, '${cbVoiceCode.voiceCode}');
        });

        //修改信息
        function update(){
            $.ajax({
                type:"post",
                url:"<c:url value='/cbVoiceCode/editCbVoiceCode'/>",
                dataType:"json",
                data:{"id":${cbVoiceCode.id}, "voiceCode":$("#voiceCode").val()},
                <%--data:"id=${userInfo.id}&"+$("#"+dialogId).find('form').serialize(),--%>
                success : function(data) {
                    $.Showmsg(data.msg);
                    setTimeout(function(){
                        $.Hidemsg();
                        if(data.code == "ok"){
                            //刷新父级页面
                            location.replace(location.href);
                            //关闭当前窗口
    //                        layer_close();
                        }
                    },2000);
                },
                error: function(){
                    layer.msg("数据请求失败！");
                }
            })
        }
    </script>
</head>
<body>
<jsp:include page="../common/nav.jsp"/>
<div class="pd-20">
    <div class="panel panel-default">
        <div class="panel-body">
            <div class="form form-horizontal" style="padding-bottom: 30px;">
                <div class="row cl">
                    <label class="form-label col-2">SIP业务：</label>
                    <div class="formControls col-7 " >
                        ${cbVoiceCode.voiceCode}
                        <label style="margin-left: 85px;"><a data-toggle="modal" href="#updateDialog" class="btn btn-primary size-MINI radius">编辑</a></label>
                    </div>
                    <div class="col-2"></div>
                </div>

            </div>
        </div>
    </div>
</div>

<!- 修改-->
<div id="updateDialog" class="modal hide fade" action="<c:url value='/cbVoiceCode/editCbVoiceCode'/>" method="post" >
    <div class="modal-header">
        <h4>编辑SIP业务</h4>
        <a class="close" data-dismiss="modal" aria-hidden="true" href="javascript:void(0);">×</a>
    </div>
    <div class="modal-body">
        <form>
            <div class="pd-20">
                <div class="row cl">
                    <label class="form-label col-3">SIP业务：</label>
                    <div class="formControls col-5">
                        <select class="input-text" id="voiceCode" name="voiceCode" datatype="*" nullmsg="请选择">
                        </select>
                    </div>
                    <div class="col-4"></div>
                </div>
            </div>
        </form>
    </div>
    <div class="modal-footer">
        <button class="btn btn-primary size-M radius" onclick="update()">提交</button>
        <button class="btn size-M radius" data-dismiss="modal" aria-hidden="true">取消</button>
    </div>
</div>

</body>
</html>

