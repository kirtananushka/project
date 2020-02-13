<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:message key="project.name" bundle="${resourceBundle}" var="projectName"/>
<fmt:message key="menu.main" bundle="${resourceBundle}" var="menuMain"/>
<fmt:message key="films" bundle="${resourceBundle}" var="films"/>
<fmt:message key="title.send.message" bundle="${resourceBundle}" var="sendMessage"/>


<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/image/favicon.ico">
    <title>${projectName}</title>
</head>
<body>
<div class="header">
    <div class="locale">
        <form class="locale" action="${pageContext.request.contextPath}controller" method="post">
            <input type="hidden" name="command" value="change_locale">
            <input class="localeButton duration locale-header" type="submit" value="EN/RU">
        </form>
    </div>
    <p class="title">${projectName}</p>


    <ul class="menu">
        <li><a href="${pageContext.request.contextPath}\">${menuMain}</a></li>
        <%--        <c:choose>--%>
        <%--            <c:when test="${sessionScope.user != null}">--%>
        <%--                <h1>${hi}${sessionScope.user.login}!</h1>--%>
        <%--            </c:when>--%>
        <%--            <c:otherwise>--%>
        <%--                <h1>${title}</h1>--%>
        <%--            </c:otherwise>--%>
        <%--        </c:choose>--%>
        <c:if test="${sessionScope.user.role eq 'ADMIN'}">
            <li><a href="${pageContext.request.contextPath}\">Admin</a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/user/admin/test">${menuMain}</a></li>
                    <li><a href="${pageContext.request.contextPath}\">${menuMain}</a></li>
                    <li><a href="${pageContext.request.contextPath}\">${menuMain}</a></li>
                </ul>
            </li>
        </c:if>
        <%--        <li><a--%>
        <%--                href="${pageContext.request.contextPath}/controller?command=search_films&genre=&country=&year=&age=&page=1">${films}</a></li>--%>
        <li><a href="${pageContext.request.contextPath}/controller?command=films_available&page=1">${films}</a></li>
        <li><a href="#">Contact</a></li>
        <li><a href="${pageContext.request.contextPath}\send_message">${sendMessage}</a></li>
    </ul>
    <ctg:menu-right/>
</div>
<div class="shadowHeader"></div>
</body>
</html>
