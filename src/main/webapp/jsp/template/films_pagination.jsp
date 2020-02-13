<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:message key="page.next" bundle="${resourceBundle}" var="pageNext"/>
<fmt:message key="page.previous" bundle="${resourceBundle}" var="pagePrevious"/>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title></title>
</head>
<body>
<div class="pagination-center">
    <div class="pagination">
        <div class="min-width50" style="display: inline-block; text-align: right">
            <c:if test="${requestScope.pageNumber > 1}">
                <a href="${pageContext.request.contextPath}
            ${requestScope.pageUrl}${requestScope.pageNumber - 1}">${pagePrevious}</a>
            </c:if>
        </div>
        <div style="display: inline-block">
            <c:forEach begin="1" end="${requestScope.totalPages}" var="pageNum">
                <c:choose>
                    <c:when test="${requestScope.pageNumber eq pageNum}">
                        <a class="active" href="${pageContext.request.contextPath}
                   ${requestScope.pageUrl}${pageNum}">${pageNum}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}
                   ${requestScope.pageUrl}${pageNum}">${pageNum}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <div class="min-width50" style="display: inline-block">
            <c:if test="${requestScope.pageNumber < requestScope.totalPages}">
                <a href="${pageContext.request.contextPath}
           ${requestScope.pageUrl}${requestScope.pageNumber + 1}">${pageNext}</a>
            </c:if>
        </div>
    </div>
</div>
</body>
</html>
