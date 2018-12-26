<%--
  Created by IntelliJ IDEA.
  User: wzj
  Date: 2016/2/1
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<nav class="breadcrumb"></nav>
<script type="text/javascript">
    $(function(){
        try {
            var menuid = "menu_" + getQueryString("menuid");
            var arrstr = parent.document.getElementById(menuid).value;
            var nav = document.getElementsByClassName("breadcrumb")[0];
            var arrs = arrstr.split("-");
            var _html = '<i class="Hui-iconfont">&#xe67f;</i> 首页';

            for (var i in arrs) {
                _html += ' <span class="c-gray en">></span> ' + arrs[i];
            }
            _html += '<a class="btn btn-success radius r mr-20" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a>';
            nav.innerHTML = _html;
        } catch (e) {
            console.log(e);
        }

    });
</script>