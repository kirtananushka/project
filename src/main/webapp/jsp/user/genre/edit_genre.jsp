<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="genre.edit" bundle="${resourceBundle}" var="title"/>
<fmt:message key="genre.edit.text" bundle="${resourceBundle}" var="text"/>
<fmt:message key="form.back" bundle="${resourceBundle}" var="ok"/>
<fmt:message key="form.next" bundle="${resourceBundle}" var="next"/>
<fmt:message key="form.button.reset" bundle="${resourceBundle}" var="reset"/>
<fmt:message key="nothing.found" bundle="${errorBundle}" var="nothingFound"/>


<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
</head>
<body>
<c:import url="/jsp/template/header.jsp"/>
<h1>${title}</h1>
<div class="outer">
    <div class="center-padding grey shadow width50 back-white">
        <div class="padding-top-40">
            <c:choose>
            <c:when test="${empty sessionScope.genresMap}">
                <div class="center-padding grey shadow width50 back-white">
                    <p class="center margin-bottom">${nothingFound}</p>
                </div>
            </c:when>
            <c:otherwise>
            <form id="editGenreForm" name="editGenreForm"
                  action="${pageContext.request.contextPath}controller"
                  method="post">
                <input type="hidden" name="command" value="prepare_genre_update">
                <p class="noteRed">
                    <c:if test="${sessionScope.errEditGenreMessage != null}">
                        <c:forEach var="errorMessages" items="${sessionScope.errEditGenreMessage}">
                            <fmt:message key="${errorMessages.key}" bundle="${errorBundle}" var="key"/>
                            ${key}<br>${errorMessages.value}<br>
                        </c:forEach>
                    </c:if>
                </p>
                <p>${text}</p>
                <select id="genre" name="genre" required
                        class="width95">
                    <c:forEach items="${sessionScope.genresMap}" var="genre">
                        <option value=${genre.key}>${genre.value}</option>
                    </c:forEach>
                </select>
                <div class="centerOnly">
                    <a class="button" href="${sessionScope.pageToReturn}">${ok}</a>
                    <input id="submit" name="submit" class="button" type="submit" value="${next}">
                    <input id="reset" name="reset" class="button" type="reset" value=${reset}>
                </div>
            </form>
        </div>
        </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>