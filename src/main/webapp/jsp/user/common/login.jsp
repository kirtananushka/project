<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="project.name" bundle="${resourceBundle}" var="projectName"/>
<fmt:message key="title.authentication" bundle="${resourceBundle}" var="title"/>
<fmt:message key="login" bundle="${resourceBundle}" var="login"/>
<fmt:message key="password" bundle="${resourceBundle}" var="password"/>
<fmt:message key="form.log.in" bundle="${resourceBundle}" var="logIn"/>
<fmt:message key="form.field.required" bundle="${resourceBundle}" var="fieldRequired"/>
<fmt:message key="wrong.login.password" bundle="${errorBundle}" var="wrongLoginPass"/>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
    <script defer src="${pageContext.request.contextPath}/js/checkValidationAuth.js"></script>
</head>
<body>
<c:import url="/jsp/template/header.jsp"/>
<h1>${title}</h1>
<div class="outer">
    <div class="center">
        <div class="dif-buf">
        </div>
        <div class="center grey shadow back-white">
            <form class="authentication" action="${pageContext.request.contextPath}controller" method="post">
                <input type="hidden" name="command" value="authentication">
                <p class="noteRed">
                    <c:if test="${sessionScope.errAuthMessage != null}">
                        <fmt:message key="${sessionScope.errAuthMessage}" bundle="${errorBundle}"/>
                    </c:if>
                </p>
                <label for="login">${login}: *</label>
                <input class="inputField" type="text" id="login"
                       name="login" pattern="[A-z][\w]{4,14}"
                       placeholder=${login} value="" required
                       title=${login}>
                <br>

                <label for="password">${password}: *</label>
                <input class="inputField" type="password" id="password"
                       name="password" pattern="[\w]{8,20}"
                       placeholder=${password} value="" required>
                <p class="noteSmall">${fieldRequired}</p>
                <div class="centerOnly">
                    <input class="button" type="submit" value=${logIn}>
                </div>
            </form>
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
