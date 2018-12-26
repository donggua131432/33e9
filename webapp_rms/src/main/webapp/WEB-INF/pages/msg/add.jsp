<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript" charset="utf-8" src="${appConfig.resourcesRoot}/js/plugins/ueditor/1.4.3/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${appConfig.resourcesRoot}/js/plugins/ueditor/1.4.3/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${appConfig.resourcesRoot}/js/plugins/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
    <title>新建消息</title>
</head>
<script type="text/javascript">
    var isSubmit = false;
    $(function(){
        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });

        $("#form-member-add").Validform({
            tiptype:2,
            ajaxPost:true,
            beforeSubmit:function(curform){
                //在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
                //这里明确return false的话表单将不会提交;

                if (isSubmit) {
                    return false;
                }

                var flag = true;

                if ($("input[name='busType']:checked").length == 0) {
                    $("#busTypeError").html('<span class="Validform_checktip Validform_wrong">请选择客户类型！</span>');
                    flag = false;
                } else {
                    $("#busTypeError").html('<span class="Validform_checktip Validform_right"></span>');
                }

                if (ue.hasContents()) {
                    $("#contentError").html('<span class="Validform_checktip Validform_right"></span>');
                } else {
                    $("#contentError").html('<span class="Validform_checktip Validform_wrong">消息内容不能为空！</span>');
                    flag = false;
                }

                isSubmit = flag;

                return flag;
            },
            callback:function(data){
                $.Showmsg(data.msg);
                setTimeout(function(){
                    $.Hidemsg();
                    if(data.code == "ok"){
                        //刷新父级页面
                        parent.location.replace(parent.location.href);
                        //关闭当前窗口
                        layer_close();
                    } else {
                        isSubmit = false;
                    }
                },2000);
            }
        });

        //实例化编辑器
        //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
        var ue = UE.getEditor('editor');
    });

</script>
<body>
<div class="pd-20">
    <form action="<c:url value='/msgMgr/addMsg'/>" method="post" class="form form-horizontal" id="form-member-add">

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>消息类型：</label>
            <div class="formControls col-5">
                <select id="msgCode" name="msgCode" style="width: 390px;height: 31px;" datatype="*" nullmsg="请选择消息类型">
                    <option value="">请选择消息类型</option>
                    <c:forEach items="${dicDatas}" var="dd">
                        <option value="${dd.code}">${dd.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>发布时间：</label>
            <div class="formControls col-5">
                <input type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-%d %H:%m:%s'})" id="sendTime" name="sendTime" datatype="*" class="input-text Wdate" nullmsg="请选择发布时间">
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>客户类型：</label>
            <div class="formControls col-5">
                <div class="skin-minimal">
                    <div class="check-box">
                        <input type="checkbox" name="busType" value="02" id="rest">
                        <label for="rest">专线语音</label>
                    </div>
                    <div class="check-box">
                        <input type="checkbox" name="busType" value="03" id="sip">
                        <label for="sip">SIP接口</label>
                    </div>
                    <div class="check-box">
                        <input type="checkbox" name="busType" value="01" id="znydd">
                        <label for="znydd">智能云调度</label>
                    </div>
                </div>
            </div>
            <div class="col-3" id="busTypeError"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>标题：</label>
            <div class="formControls col-5">
                <input type="text" class="input-text" value="" placeholder="请输入标题" id="title" name="title" datatype="*2-32" nullmsg="标题不能为空！" maxlength="30"/>
            </div>
            <div class="col-3"> </div>
        </div>

        <div class="row cl">
            <label class="form-label col-4"><span class="c-red">*</span>内容编辑：</label>
            <div class="formControls col-5">
            </div>
            <div class="col-3" id="contentError"></div>
        </div>

        <div class="row cl">
            <div class="formControls col-12">
                <script id="editor" name="content" type="text/plain" style="width:950px;height:300px;"></script>
            </div>
        </div>

        <div class="row cl">
            <div class="col-8 col-offset-4">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;"/>
                <input class="btn btn-default radius" type="reset"  onclick="layer_close();" value="&nbsp;&nbsp;取消&nbsp;&nbsp;"/>
            </div>
        </div>
    </form>
</div>
</body>
</html>