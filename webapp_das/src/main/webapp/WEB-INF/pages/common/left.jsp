<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    </div>
</aside>
