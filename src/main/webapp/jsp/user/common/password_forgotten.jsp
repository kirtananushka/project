<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="access.restoration" bundle="${resourceBundle}" var="title"/>
<fmt:message key="access.restoration.text" bundle="${resourceBundle}" var="text"/>
<fmt:message key="login" bundle="${resourceBundle}" var="login"/>
<fmt:message key="email.email" bundle="${resourceBundle}" var="email"/>
<fmt:message key="form.next" bundle="${resourceBundle}" var="next"/>
<fmt:message key="form.field.required" bundle="${resourceBundle}" var="fieldRequired"/>
<fmt:message key="incorrect.data" bundle="${errorBundle}" var="incorrectData"/>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
    <script defer src="${pageContext.request.contextPath}/js/checkValidationAuth.js"></script>
</head>
<body>
<c:import url="/jsp/template/header.jsp"/>
<h1>${title}</h1>
<div class="outer">
    <div class="center">
        <div class="center grey shadow back-white">
            <form class="authentication" action="${pageContext.request.contextPath}controller" method="post">
                <input type="hidden" name="command" value="send_password">
                <p class="noteRed">
                    <c:if test="${sessionScope.errSendNewPasswordMessage != null}">
                        ${incorrectData}
                    </c:if>
                </p>
                <p class="center">${text}</p>
                <label for="login">${login}: *</label>
                <input class="inputField" type="text" id="login"
                       name="login" pattern="[A-z][\w]{4,14}"
                       placeholder=${login} value="" required
                       title=${login}>
                <br>

                <label for="email">${email}: *</label>
                <input class="inputField" type="email" id="email"
                       name="email"
                       pattern="([\w-]+\.)*[\w-]+@[\w-]+(\.[\w-]+)*\.[a-z]{2,6}"
                       placeholder=${email} value="" required
                       title=${email}>
                <p class="noteSmall">${fieldRequired}</p>
                <div class="centerOnly">
                    <input class="button" type="submit" value=${next}>
                </div>
            </form>
        </div>

    </div>
</div>
</body>
</html>
