
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../common/loadMeta.jsp"/>
    <title>客户添加</title>
    <jsp:include page="../common/loadCss.jsp"/>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/zTree/v3.5/css/demo.css"/>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/zTree/v3.5/css/zTreeStyle/zTreeStyle.css"/>
    <jsp:include page="../common/loadJs.jsp"/>
    <script type="text/javascript" src="${appConfig.resourcesRoot}/js/plugins/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
    <script src="${appConfig.resourcesRoot}/js/plugins/select2/select2.min.js"></script>
    <link type="text/css" rel="stylesheet" href="${appConfig.resourcesRoot}/js/plugins/select2/select2.css"/>
    <script type="text/javascript">

        // 身份-城市 树
        var pcsetting = {
            view: {
                selectedMulti: false
            },
            check: {
                enable: true
            },
            data:{
                simpleData:{
                    enable: true,
                    pIdKey: "pId"
                }
            }
        };

        // 用户区域
        var asetting = {
            view: {
                selectedMulti: false
            },
            check: {
                enable: true
            },
            data:{
                simpleData:{
                    enable: true,
                    pIdKey: "pId"
                }
            }
        };
        var pctree, atree;
        $(function() {

            $.fn.zTree.init($("#pctree"), pcsetting);
            $.fn.zTree.init($("#atree"), asetting);

            pctree = $.fn.zTree.getZTreeObj("pctree");
            atree = $.fn.zTree.getZTreeObj("atree");

            $("#addCustomerManager").Validform({
                tiptype:2,
                ajaxPost:true,
                callback:function(data){
                    setTimeout(function(){
                        $.Hidemsg();
                        if(data.code == 'ok'){
                            //刷新父级页面
                            parent.location.replace(parent.location.href);
                            layer_close();
                        } else if(data.code == 'error') {

                            var content = "下列城市已经在其他区域中：<br>";
                            $.each(data.data, function(i,n){
                                if (i%5 == 0) {
                                    content += "<br>";
                                }
                                content += "<span style='margin: 10px'>" + n.name + "</span>";
                            });

                            //页面层
                            layer.open({
                                type: 1,
                                skin: 'layui-layer-rim', //加上边框
                                area: ['420px', '240px'], //宽高
                                content: content
                            });
                        }
                    },2000);
                },
                beforeSubmit:function(curform){
                    //在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
                    //这里明确return false的话表单将不会提交;
                    var nodes = atree.getNodesByFilter(function(node){
                        return !node.isParent;
                    });

                    if(nodes.length == 0) {
                        layer.alert('请选择城市', {
                            icon: 0,
                            skin: 'layer-ext-moon' //该皮肤由layer.seaning.com友情扩展。关于皮肤的扩展规则，去这里查阅
                        });
                        return false;
                    }

                    $("#ccodes").val("");
                    var ccodes = "";
                    for (var i=0; i<nodes.length; i++) {
                        ccodes += "," + nodes[i].id;
                    }

                    $("#ccodes").val(ccodes.substring(1));

                    return true;
                },
                datatype: {
                    "uniqueAname": function (gets, obj, curform, datatype) {
                        var result = true;
                        var sid = $("#sid").val();
                        if (!/^[\u4E00-\u9FA5-a-zA-Z0-9\(\)]{2,10}$/.test(gets)) {
                            return "区域名称不合法";
                        }
                        if (sid) {
                            $.ajax({
                                async:false,
                                type: "post",
                                url: "<c:url value="/ccareacity/uniqueAname"/>",
                                dataType: "json",
                                data: {sid : sid, aname : gets},
                                success: function (data) {
                                    if (data.code == "ok") {
                                        result = true;
                                    }
                                    if (data.code == "error") {
                                        result = "该区域名称已存在";
                                    }
                                },
                                error: function () {
                                    result = "数据请求异常";
                                }
                            });
                        }
                        return result;
                    }
                }
            });

            $("#companyName").select2({
                placeholder: "请选择客户名称",
                ajax: {
                    url: "<c:url value="/userAdmin/getCompanyNameAndSidByPage"/>",
                    dataType: 'json',
                    delay: 250,
                    data: function (params) {
                        return {
                            params: {name:params.term,busType:'01'}, // search term busType
                            page: params.page
                        };
                    },
                    processResults: function (data, params) {
                        params.page = params.page || 1;

                        var items = [];

                        $.each(data.rows,function(i,v){
                            items.push({id:v.sid, text:v.name});
                        });

                        return {
                            results: items,
                            pagination: {
                                more: (params.page * 30) < data.total
                            }
                        };
                    },
                    cache: true
                }
                //escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
                //minimumInputLength: 1,
                //templateResult: formatRepo, // omitted for brevity, see the source of this page
                //templateSelection: formatRepoSelection // omitted for brevity, see the source of this page
                //data:data
            });

            $("#companyName").on("change",function(){
                $("#sid").val(this.value);
                reInitTree(this.value);
            });
        });

        // 禁用 节点
        function reInitTree(sid, areaId) {
            if (sid) {
                $.ajax({
                    async:false,
                    type: "post",
                    url: "<c:url value="/ccareacity/pctree"/>",
                    dataType: "json",
                    data: {sid : sid},
                    success: function (nodes) {
                        var pnodes = [];
                        if (nodes) {

                        }
                        setChDisabledAndRemvoeRTree(nodes);
                    },
                    error: function () {
                    }
                });
            }
        }

        // 禁用 所有的 已经选择的节点
        function setChDisabledAndRemvoeRTree(ids){
            pctree.destroy();
            atree.destroy();

            $.fn.zTree.init($("#pctree"), pcsetting, ids);
            $.fn.zTree.init($("#atree"), asetting);

            pctree = $.fn.zTree.getZTreeObj("pctree");
            atree = $.fn.zTree.getZTreeObj("atree");
        }

        // 向右栏 添加
        function addToArea () {
            var nodes = pctree.getCheckedNodes();
            if (!nodes) {
                return;
            }
            var newNodes = deepClone(nodes);
            var nNodes = [];
            var rNodes = atree.getNodes();
            var simplenode = atree.transformToArray(rNodes);

            for (var i=0;i<newNodes.length;i++) {
                newNodes[i].children = null;
                var disable = false;
                for(var j=0; j<simplenode.length; j ++){
                    disable = (simplenode[j].id == newNodes[i].id);
                    if (disable) {
                        break;
                    }
                }
                if (!disable){
                    nNodes.push(newNodes[i]);
                }

            }
            var pnodes = atree.getNodesByFilter(function(node){return node.isParent});
            console.log(pnodes);
            for (var m=nNodes.length-1; m>=0; m--) {
                for (var n=0; n<pnodes.length; n++) {
                    if (nNodes[m].pId == pnodes[n].id) {
                        atree.addNodes(pnodes[n], nNodes[m]);
                        nNodes.splice(m,1);
                        break;
                    }
                }
            }
            atree.addNodes(null, nNodes);
        }

        // 右栏 移除
        function deleteCity () {
            var nodes = atree.getCheckedNodes(true);
            for (var i=0; i<nodes.length; i++) {
                if (!nodes[i].isParent){
                    atree.removeNode(nodes[i]);
                }
            }
            var pnodes = atree.getCheckedNodes(true);

            for (var j=0; j<pnodes.length; j++) {
                if (pnodes[j].children.length == 0){
                    atree.removeNode(pnodes[j]);
                }
            }
        }

        /**
         * 深度复制
         * @param obj
         * @returns {*}
         */
        function deepClone(obj){
            var result,oClass=isClass(obj);
            //确定result的类型
            if(oClass==="Object"){
                result={};
            }else if(oClass==="Array"){
                result=[];
            }else{
                return obj;
            }
            for(key in obj){
                var copy=obj[key];
                if(isClass(copy)=="Object"){
                    result[key]=arguments.callee(copy);//递归调用
                }else if(isClass(copy)=="Array"){
                    result[key]=arguments.callee(copy);
                }else{
                    result[key]=obj[key];
                }
            }
            return result;
        }

        /**
         * 类型判断
         * @param o
         * @returns {*}
         */
        function isClass(o){
            if(o===null) return "Null";
            if(o===undefined) return "Undefined";
            return Object.prototype.toString.call(o).slice(8,-1);
        }
    </script>
</head>
<body>
<div class="pd-20">
    <form action="<c:url value="/ccareacity/addArea"/>" method="post" class="form form-horizontal" id="addCustomerManager">
        <input id="ccodes" name="ccodes" type="hidden"/>
        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>客户名称：</label>
            <div class="formControls col-8">
                <select class="input-text" id="companyName" name="companyName" datatype="*" nullmsg="客户名称不能为空"></select>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2">account id：</label>
            <div class="formControls col-8">
                <input type="text" class="input-text" value="" placeholder="请输入账户ID" id="sid" name="sid" datatype="*2-32" nullmsg="账户ID不能为空！" readonly/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row cl">
            <label class="form-label col-2"><span class="c-red">*</span>区域名称：</label>
            <div class="formControls col-8">
                <input type="text" class="input-text" value="" placeholder="请输入区域名称" id="aname" name="aname" datatype="uniqueAname" maxlength="10" errormsg="区域名称不合法！" nullmsg="区域名称不能为空"/>
            </div>
            <div class="col-2"></div>
        </div>

        <div class="row">
            <div class="col-5">
                <div style="height: 400px;">
                    <ul id="pctree" class="ztree" style="width:300px; overflow:auto;"></ul>
                </div>
            </div>
            <div class="col-2">
                <div style="height: 200px;padding-top: 150px;padding-left: 35px;">
                    <button type="button" style="width: 50px;" onclick="addToArea();">&gt;&gt;</button>
                    <br><br><br><br>
                    <button type="button" style="width: 50px;" onclick="deleteCity();">&lt;&lt;</button>
                </div>
            </div>
            <div class="col-5">
                <div style="height: 400px">
                    <ul id="atree" class="ztree" style="width:300px; overflow:auto;"></ul>
                </div>
            </div>
        </div>

        <div class="row cl">
            <div class="col-offset-4">
                <input class="btn btn-primary radius" style="width: 100px;" type="submit" value="提交">
            </div>
        </div>
    </form>
</div>
</body>
</html>
