<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:message key="error500.title" bundle="${errorBundle}" var="title"/>
<fmt:message key="error500.text" bundle="${errorBundle}" var="text"/>
<fmt:message key="form.goto.main" bundle="${resourceBundle}" var="toMain"/>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
</head>
<body>
<c:import url="/jsp/template/header.jsp"/>
<h1>${title}</h1>
<h1>${title}</h1>
<div class="outer">
    <div class="center-padding grey shadow width50 break back-white">
        <p>${text}</p>
        <p>
            <c:if test="${pageContext.errorData.requestURI != null}">
            <strong>URI:</strong><br>
                ${pageContext.errorData.requestURI}<br>
        <hr>
        </c:if>
        <c:if test="${pageContext.errorData.servletName != null}">
            <strong>Servlet name/type:</strong><br>
            ${pageContext.errorData.servletName}<br>
            <hr>
        </c:if>
        <c:if test="${pageContext.errorData.statusCode != null}">
            <strong>Status code/type:</strong><br>
            ${pageContext.errorData.statusCode}<br>
            <hr>
        </c:if>
        <c:if test="${pageContext.errorData.throwable != null}">
            <strong>Exception:</strong><br>
            ${pageContext.errorData.throwable}<br>
            <hr>
        </c:if>
        <c:if test="${pageContext.exception.message != null}">
            <strong>Message:</strong><br>
            ${pageContext.exception.message}<br>
            <hr>
        </c:if>
        <c:if test="${pageContext.exception.stackTrace != null}">
            <strong>Stacktrace:</strong><br>
            <table>
                <c:forEach var="stackTraceElement" items="${pageContext.exception.stackTrace}">
                    <tr class="zebra">
                        <td>${stackTraceElement}</td>
                    </tr>
                </c:forEach>
            </table>
            <hr>
        </c:if>
        </p>
        <form class="authentication" action="${pageContext.request.contextPath}/"
              method="post">
            <div class="centerOnly">
                <input class="button" type="submit" value=${toMain}>
            </div>
        </form>
    </div>
</div>
</body>
</html>
