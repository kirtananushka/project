<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="cinema.restore" bundle="${resourceBundle}" var="title"/>
<fmt:message key="cinema.restore.text" bundle="${resourceBundle}" var="text"/>
<fmt:message key="form.back" bundle="${resourceBundle}" var="ok"/>
<fmt:message key="form.restore" bundle="${resourceBundle}" var="restore"/>
<fmt:message key="form.button.reset" bundle="${resourceBundle}" var="reset"/>


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
            <form id="restoreCinemaForm" name="restoreCinemaForm"
                  action="${pageContext.request.contextPath}controller"
                  method="post">
                <input type="hidden" name="command" value="return_cinema">
                <p class="noteRed">
                    <c:if test="${sessionScope.errRestoreCinemaMessage != null}">
                        <c:forEach var="errorMessages" items="${sessionScope.errRestoreCinemaMessage}">
                            <fmt:message key="${errorMessages.key}" bundle="${errorBundle}" var="key"/>
                            ${key}<br>${errorMessages.value}<br>
                        </c:forEach>
                    </c:if>
                </p>
                <p>${text}</p>
                <select id="cinema" name="cinema" required
                        class="width95">
                    <c:forEach items="${sessionScope.cinemasMap}" var="cinema">
                        <option value=${cinema.key}>${cinema.value}</option>
                    </c:forEach>
                </select>
                <div class="centerOnly">
                    <a class="button" href="${sessionScope.pageToReturn}">${ok}</a>
                    <input id="submit" name="submit" class="button" type="submit" value="${restore}">
                    <input id="reset" name="reset" class="button" type="reset" value=${reset}>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>