<%--
  Created by IntelliJ IDEA.
  User: Dukai
  Date: 2016/2/1
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<div class="dislpayArrow"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a></div>
<aside class="Hui-aside">
    <%--<input runat="server" id="divScrollValue" type="hidden" value=""/>--%>
    <div class="menu_dropdown bk_2">
        <c:forEach items="${sessionScope.menus}" var="menu">
            <dl id="menu-operate">
                <dt><i class="Hui-iconfont">${menu.icon}</i> ${menu.name}<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <c:if test="${menu.children.size() > 0}">
                    <dd>
                        <ul>
                        <c:forEach items="${menu.children}" var="me" varStatus="status">
                            <c:if test="${me.children.size() > 0}">
                                <li>
                                <dl id="menu-price">
                                    <dt>${me.name}<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                                    <dd>
                                        <ul>
                                            <c:set var="child" value="${me.children}"></c:set>
                                            <c:forEach items="${child}" var="m">
                                                <li><a id="code_${m.id}" _href="<c:url value='${m.url}?menuid=${m.id}'/>" data-title="${m.name}" href="javascript:void(0)">${m.name}</a>
                                                    <input id="menu_${m.id}" value="${menu.name}-${me.name}-${m.name}" type="hidden">
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </dd>
                                </dl>
                                </li>
                            </c:if>
                            <c:if test="${me.children.size() == 0}">
                                <li><dl><dt>
                                <a style="padding-left:0px;" id="code_${me.id}" _href="<c:url value='${me.url}?menuid=${me.id}'/>" data-title="${me.name}" href="javascript:void(0)">${me.name}</a>
                                <input id="menu_${me.id}" value="${menu.name}-${me.name}" type="hidden">
                                </dt></dl></li>
                            </c:if>
                        </c:forEach>
                        </ul>
                    </dd>
                </c:if>
            </dl>
        </c:forEach>

        <%--<dl id="menu-picture">
            <dt><i class="Hui-iconfont">&#xe613;</i> 图片管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a _href="picture-list.html" data-title="图片管理" href="javascript:void(0)">图片管理</a></li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-product">
            <dt><i class="Hui-iconfont">&#xe620;</i> 产品管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a _href="product-brand.html" data-title="品牌管理" href="javascript:void(0)">品牌管理</a></li>
                    <li><a _href="product-category.html" data-title="分类管理" href="javascript:void(0)">分类管理</a></li>
                    <li><a _href="product-list.html" data-title="产品管理" href="javascript:void(0)">产品管理</a></li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-page">
            <dt><i class="Hui-iconfont">&#xe626;</i> 页面管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a _href="page-home.html" href="javascript:void(0)">首页管理</a></li>
                    <li><a _href="page-flinks.html" href="javascript:void(0)">友情链接</a></li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-comments">
            <dt><i class="Hui-iconfont">&#xe622;</i> 评论管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a _href="http://h-ui.duoshuo.com/admin/" data-title="评论列表" href="javascript:;">评论列表</a></li>
                    <li><a _href="feedback-list.html" data-title="意见反馈" href="javascript:void(0)">意见反馈</a></li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-order">
            <dt><i class="Hui-iconfont">&#xe63a;</i> 财务管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a _href="order-list.html" href="javascript:void(0)">订单列表</a></li>
                    <li><a _href="recharge-list.html" href="javascript:void(0)">充值管理</a></li>
                    <li><a _href="invoice-list.html" href="javascript:void(0)">发票管理</a></li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-member">
            <dt><i class="Hui-iconfont">&#xe60d;</i> 会员管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a _href="member-list.html" data-title="会员列表" href="javascript:;">会员列表</a></li>
                    <li><a _href="member-del.html" data-title="删除的会员" href="javascript:;">删除的会员</a></li>
                    <li><a _href="member-level.html" data-title="等级管理" href="javascript:;">等级管理</a></li>
                    <li><a _href="member-scoreoperation.html" data-title="积分管理" href="javascript:;">积分管理</a></li>
                    <li><a _href="member-record-browse.html" data-title="浏览记录" href="javascript:void(0)">浏览记录</a></li>
                    <li><a _href="member-record-download.html" data-title="下载记录" href="javascript:void(0)">下载记录</a></li>
                    <li><a _href="member-record-share.html" data-title="分享记录" href="javascript:void(0)">分享记录</a></li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-admin">
            <dt><i class="Hui-iconfont">&#xe62d;</i> 管理员管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a _href="admin-role.html" data-title="角色管理" href="javascript:void(0)">角色管理</a></li>
                    <li><a _href="admin-permission.html" data-title="权限管理" href="javascript:void(0)">权限管理</a></li>
                    <li><a _href="admin-list.html" data-title="管理员列表" href="javascript:void(0)">管理员列表</a></li>
                </ul>
            </dd>
        </dl>
        <dl id="menu-tongji">
            <dt><i class="Hui-iconfont">&#xe61a;</i> 系统统计<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a _href="charts-1.html" data-title="折线图" href="javascript:void(0)">折线图</a></li>
                    <li><a _href="charts-2.html" data-title="时间轴折线图" href="javascript:void(0)">时间轴折线图</a></li>
                    <li><a _href="charts-3.html" data-title="区域图" href="javascript:void(0)">区域图</a></li>
                    <li><a _href="charts-4.html" data-title="柱状图" href="javascript:void(0)">柱状图</a></li>
                    <li><a _href="charts-5.html" data-title="饼状图" href="javascript:void(0)">饼状图</a></li>
                    <li><a _href="charts-6.html" data-title="3D柱状图" href="javascript:void(0)">3D柱状图</a></li>
                    <li><a _href="charts-7.html" data-title="3D饼状图" href="javascript:void(0)">3D饼状图</a></li>
                </ul>
            </dd>
        </dl>

        <dl id="menu-system">
            <dt><i class="Hui-iconfont">&#xe62e;</i>权限配置管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <!--
                    <li><a _href="system-shielding.html" data-title="用户管理" href="javascript:void(0)">用户管理</a></li>
                    <li><a _href="system-log.html" data-title="角色管理" href="javascript:void(0)">角色管理</a></li>
                    -->
                    <li><a _href="<c:url value='/menu'/>" data-title="菜单管理" href="javascript:void(0)">菜单管理</a></li>
                </ul>
            </dd>
        </dl>
        --%>
    </div>
</aside>
