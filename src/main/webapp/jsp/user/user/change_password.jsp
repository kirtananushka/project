<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="change.password" bundle="${resourceBundle}" var="title"/>
<fmt:message key="password" bundle="${resourceBundle}" var="password"/>
<fmt:message key="password.old" bundle="${resourceBundle}" var="oldPassword"/>
<fmt:message key="password.new" bundle="${resourceBundle}" var="newPassword"/>
<fmt:message key="password.repeate" bundle="${resourceBundle}" var="repeatePassword"/>
<fmt:message key="password.expl" bundle="${resourceBundle}" var="passwordExpl"/>
<fmt:message key="form.button.reset" bundle="${resourceBundle}" var="reset"/>
<fmt:message key="change" bundle="${resourceBundle}" var="change"/>
<fmt:message key="form.field.required" bundle="${resourceBundle}" var="fieldRequired"/>
<fmt:message key="form.pass.dont.match" bundle="${resourceBundle}" var="passNotMatch"/>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
    <script defer src="${pageContext.request.contextPath}/js/checkValidationChangePass.js"></script>
</head>
<body>
<c:import url="/jsp/template/header.jsp"/>
<h1>${title}</h1>
<div class="outer">
    <div class="center">
        <div class="center grey shadow back-white">
            <form id="regForm" name="regForm" class="authentication registration"
                  action="${pageContext.request.contextPath}controller" method="post">
                <input type="hidden" name="command" value="update_password">

                <p class="noteRed">
                    <c:if test="${sessionScope.errRegMessage != null}">
                        <c:forEach var="errorMessages" items="${sessionScope.errRegMessage}">
                            <fmt:message key="${errorMessages}" bundle="${errorBundle}"/><br>
                        </c:forEach>
                    </c:if>
                </p>

                <label id="oldPasswordLabel" for="oldPassword">${oldPassword}: *</label>
                <input class="inputField" type="password" id="oldPassword"
                       name="oldPassword"
                       pattern="[\w]{8,20}"
                       value=""
                       placeholder="${oldPassword}" required
                       title="${oldPassword}">
                <br>

                <label id="passwordLabel" for="password">${newPassword}: *</label>
                <input class="inputField" type="password" id="password"
                       name="newPassword"
                       pattern="[\w]{8,20}"
                       value=""
                       placeholder="${newPassword}" required
                       title="${newPassword}">
                <label class="explanation">${passwordExpl}</label>
                <br>

                <label for="passwordRepeated">${repeatePassword}: *</label>
                <input class="inputField" type="password" id="passwordRepeated"
                       name="passwordRepeated"
                       pattern="[\w]{8,20}"
                       value=""
                       placeholder="${password}" required
                       title=${password}>
                <label id="passwordRepeatedLabel" class="explanationRed displayNone">${passNotMatch}</label>
                <br>
                <p class="noteSmall">${fieldRequired}</p>
                <div class="centerOnly">
                    <input id="submit" name="submit" class="button" type="submit" value="${change}">
                    <input id="reset" name="reset" class="button" type="reset" value="${reset}">
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>