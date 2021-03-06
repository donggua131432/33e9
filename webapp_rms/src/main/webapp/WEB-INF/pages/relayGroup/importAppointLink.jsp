<%--
  Created by IntelliJ IDEA.
  User: lixin
  Date: 2016/7/18
  Time: 13:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>导入SIP业务手机号</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript">
        $(function(){
            var $list = $("#fileList"),$btn = $("#btn-star"), state = "pending", uploader;
            uploader = WebUploader.create({
                auto: false,
                swf: '${appConfig.resourcesRoot}/js/plugins/webuploader/0.1.5/Uploader.swf',
                // 文件接收服务端。
                server: '<c:url value="/appointLink/appointLinkExcelUpload"/>',
                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick:'#filePicker',
                fileNumLimit:1,
                fileSingleSizeLimit:5242880,
                duplicate:true,
                // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
                resize: false,
                // 指定文件类型。
                accept: {
                    title: 'fileType',
                    extensions: 'xls,xlsx'
                }
            });
            uploader.on( 'fileQueued', function( file ) {
                var $li = $( '<div id="' + file.id + '" class="item">' +
                        '<div class="pic-box"><img></div>'+
                        '<div class="info">' + file.name + '</div>' +
                        '<p class="state">等待上传...</p>'+
                        '</div>' );
                $list.append($li);
            });

            uploader.on("beforeFileQueued",function(){
                uploader.reset();
                $list.empty();
            });

            // 文件上传过程中创建进度条实时显示。
            uploader.on( 'uploadProgress', function( file, percentage ) {
                var $li = $( '#'+file.id ),$percent = $li.find('.progress-box .sr-only');
                // 避免重复创建
                if ( !$percent.length ) {
                    $percent = $('<div class="progress-box"><span class="progress-bar radius"><span class="sr-only" style="width:0%"></span></span></div>').appendTo( $li ).find('.sr-only');
                }
                $li.find(".state").text("上传中");
                $percent.css( 'width', percentage * 100 + '%' );
            });

            // 文件上传成功，给item添加成功class, 用样式标记上传成功。
            uploader.on( 'uploadSuccess', function(file,response) {
                $( '#'+file.id ).addClass('upload-state-success').find(".state").text(response.info);
                if(response.status == "2"){
                    window.location.href="<c:url value='/relaygroup${g}/downLoadErrorList'/>";
                }

                //刷新父级页面
                parent.search_param = {
                    params:{
                        "appid":"${appid}"
                    }
                };
                parent.dataTables.fnDraw();
                //layer_close();
            });

            // 文件上传失败，显示上传出错。
            uploader.on( 'uploadError', function(file, response) {
                $( '#'+file.id ).addClass('upload-state-error').find(".state").text(response.info);
            });

            uploader.on( 'error', function(file) {
                if(file == "Q_TYPE_DENIED") layer.msg("请上传Excel格式文件！");
            });

            // 完成上传完了，成功或者失败，先删除进度条。
            uploader.on( 'uploadComplete', function( file ) {
                $( '#'+file.id ).find('.progress-box').fadeOut();
            });

            uploader.on('all', function (type) {
                if (type === 'startUpload') {
                    state = 'uploading';
                } else if (type === 'stopUpload') {
                    state = 'paused';
                } else if (type === 'uploadFinished') {
                    state = 'done';
                }

                if (state === 'uploading') {
                    $btn.text('暂停上传');
                } else {
                    $btn.text('开始上传');
                }
            });

            $btn.on('click', function () {
                if (state === 'uploading') {
                    uploader.stop();
                } else {
                    uploader.upload();
                }
            });
        })

    </script>
</head>
<body>
<div class="pd-20">
    <div class="form form-horizontal">
        <div class="row cl">
            <label class="form-label col-3">批量导入：</label>
            <div class="formControls col-9">
                <div class="uploader-thum-container">
                    <div id="filePicker">浏览文件</div>
                    <button id="btn-star" class="btn btn-primary size-M radius ml-10">开始上传</button>
                    <div class="c-red" style="float: right; margin-top: 4px;">(提示：支持Excel2003及以上格式)</div>
                    <div style="height: 60px;">
                        <div id="fileList" class="uploader-list"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-3">下载导入模板：</label>
            <div class="formControls col-9" style="margin-top: 2px;">
                <c:if test="${g eq '1'}">
                    <a href="${appConfig.resourcesRoot}/download/导入头域号码段模板.xls" style="color: blue;">模板下载</a>
                </c:if>
                <c:if test="${g eq '2'}">
                    <a href="${appConfig.resourcesRoot}/download/导入运营商被叫前缀表模板.xls" style="color: blue;">模板下载</a>
                </c:if>
                <c:if test="${g eq '3'}">
                    <a href="${appConfig.resourcesRoot}/download/导入主叫强显号模板.xls" style="color: blue;">模板下载</a>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>
