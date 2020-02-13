<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>

<fmt:message key="welcome.title" bundle="${resourceBundle}" var="title"/>
<fmt:message key="welcome.hi" bundle="${resourceBundle}" var="hi"/>
<fmt:message key="welcome.text" bundle="${resourceBundle}" var="text"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
</head>
<body>
<c:import url="/jsp/template/header.jsp"/>
<c:choose>
    <c:when test="${sessionScope.user != null}">
        <h1>${hi}${sessionScope.user.login}!</h1>
    </c:when>
    <c:otherwise>
        <h1>${title}</h1>
    </c:otherwise>
</c:choose>

<div class="outer">
    <div class="center100">
        <div class="dif-buf">
        </div>
        <div class="displayBlock center-padding grey shadow back-blue">
            <table width="600">
                <tr>
                    <td width="300"><img style="opacity: 0.8;"
                                         src="${pageContext.request.contextPath}/image/cinema2.png"
                                         alt="Cinema"></td>
                    <td width="300"><p>${text}</p></td>
                </tr>
            </table>
        </div>
        <div class="dif-buf">
            <form class="locale" action="${pageContext.request.contextPath}controller" method="post">
                <input type="hidden" name="command" value="change_locale">
                <input class="localeButton duration" type="submit" value="EN/RU">
            </form>
        </div>
    </div>
</div>
</body>
</html>
