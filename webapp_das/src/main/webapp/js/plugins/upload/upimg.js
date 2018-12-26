jQuery.fn.extend({
    uploadPreview: function (opts) {
        var _self = this,
            _this = $(this);
        opts = jQuery.extend({
            Img: "ImgPr",
            Width: 100,
            Height: 100,
            ImgType: ["gif", "jpeg", "jpg", "png"],
            Callback: function () {}
        }, opts || {});
        _self.getObjectURL = function (file) {
            var url = null;
            if (window.createObjectURL != undefined) {
                url = window.createObjectURL(file)
            } else if (window.URL != undefined) {
                url = window.URL.createObjectURL(file)
            } else if (window.webkitURL != undefined) {
                url = window.webkitURL.createObjectURL(file)
            }
            return url
        };
        _this.change(function () {
            var sObj = $('#'+this.id+"_error").siblings('span');
            sObj.hide();
            $('#'+this.id+"_error").attr('class','').html("");
            if (this.value) {
                if (!RegExp("\.(" + opts.ImgType.join("|") + ")$", "i").test(this.value.toLowerCase())) {

                    $('#'+this.id+"_error").attr('class','Validform_checktip Validform_wrong').html("选择文件错误,图片类型必须是" + opts.ImgType.join("，") + "中的一种");
                    this.value = "";
                    return false
                }
                if(this.files){ // element.files 是chrome，firefox等浏览器的对戏那个，如果是ie的话他的值就是false
                    console.info("element.files");
                    var thisFile = this.files[0]; // 获取文件的对像
                    fileSize = thisFile.size;//获取当前上传的文件的大小
                } else { // 如果是ie
                    console.info("element.files else");
                }
                if(fileSize > 2097152){
                    $('#'+this.id+"_error").attr('class','Validform_checktip Validform_wrong').html("文件大小超过2M");
                    return false;
                }
                var explorer =navigator.userAgent ;
                if (explorer.indexOf("MSIE") >= 0) {
                    try {
                        $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]))
                    } catch (e) {
                        var src = "";
                        var obj = $("#" + opts.Img);
                        var div = obj.parent("div")[0];
                        _self.select();
                        if (top != self) {
                            window.parent.document.body.focus()
                        } else {
                            _self.blur()
                        }
                        src = document.selection.createRange().text;
                        document.selection.empty();
                        obj.hide();
                        obj.parent("div").css({
                            'filter': 'progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)',
                            'width': opts.Width + 'px',
                            'height': opts.Height + 'px'
                        });
                        div.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = src
                    }
                } else {
                    $("#" + opts.Img).css({'width': opts.Width + 'px','height': opts.Height + 'px'}).show();
                    $("#" + opts.Img).attr('src', _self.getObjectURL(this.files[0]));
                }
                opts.Callback()
            }
        })
    }
});