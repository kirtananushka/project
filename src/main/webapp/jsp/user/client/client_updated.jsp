<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="updating.successful" bundle="${resourceBundle}" var="title"/>
<fmt:message key="form.back" bundle="${resourceBundle}" var="ok"/>
<fmt:message key="client.id" bundle="${resourceBundle}" var="clientId"/>
<fmt:message key="login" bundle="${resourceBundle}" var="login"/>
<fmt:message key="name" bundle="${resourceBundle}" var="name"/>
<fmt:message key="surname" bundle="${resourceBundle}" var="surname"/>
<fmt:message key="phone" bundle="${resourceBundle}" var="phone"/>
<fmt:message key="email" bundle="${resourceBundle}" var="email"/>
<fmt:message key="form.edit" bundle="${resourceBundle}" var="register"/>
<fmt:message key="registration.date.short" bundle="${resourceBundle}" var="registrationDate"/>
<fmt:message key="verified" bundle="${resourceBundle}" var="verified"/>
<fmt:message key="active" bundle="${resourceBundle}" var="active"/>
<fmt:message key="user.role" bundle="${resourceBundle}" var="role"/>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
</head>
<body>
<c:import url="/jsp/template/header.jsp"/>
<h1>${title}</h1>
<div class="outer">
    <div class="center">
        <div class="center grey shadow back-white">
            <form id="regForm" name="regForm" class="authentication registration"
                  action="${pageContext.request.contextPath}controller"
                  method="post">
                <input type="hidden" name="command" value="edit_client">
                <input type="hidden" name="id" value="${sessionScope.clientUpd.id}">
                <p class="noteRed">
                    <c:if test="${sessionScope.errRegMessage != null}">
                        <c:forEach var="errorMessages" items="${sessionScope.errRegMessage}">
                            <fmt:message key="${errorMessages}" bundle="${errorBundle}"/><br>
                        </c:forEach>
                    </c:if>
                </p>
                <label id="idLabel" class="" for="clientId">${clientId}:</label>
                <input class="inputField" type="text" id="clientId"
                       name="id" value="${sessionScope.clientUpd.id}"
                       disabled title=${clientId}>
                <br>

                <label id="roleLabel" class="" for="role">${role}:</label>
                <input class="inputField" type="text" id="role"
                       name="role" value="${sessionScope.clientUpd.role}"
                       disabled title=${role}>
                <br>

                <label id="loginLabel" class="" for="login">${login}:</label>
                <input class="inputField" type="text" id="login"
                       name="login" value="${sessionScope.clientUpd.login}"
                       disabled title=${login}>
                <br>

                <label for="name">${name}:</label>
                <input class="inputField" type="text" id="name"
                       name="name" value="${sessionScope.clientUpd.name}" disabled
                       title=${name}>
                <br>

                <label for="surname">${surname}:</label>
                <input class="inputField" type="text" id="surname"
                       name="surname" value="${sessionScope.clientUpd.surname}" disabled
                       title=${surname}>
                <br>

                <label for="phone">${phone}:</label>
                <input class="inputField" type="tel" id="phone"
                       name="phone" value="${sessionScope.clientUpd.phone}" disabled
                       title=${phone}>
                <br>

                <label for="email">${email}:</label>
                <input class="inputField" type="text" id="email"
                       name="email" value="${sessionScope.clientUpd.email}" disabled
                       title=${email}>
                <br>

                <label for="registrationDate">${registrationDate}: </label>
                <fmt:parseDate value="${sessionScope.clientUpd.registrationDate}"
                               pattern="yyyy-MM-dd'T'HH:mm"
                               var="parsedDate" type="both"/>
                <input class="inputField" type="text" id="registrationDate"
                       name="registrationDate"
                       value="<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDate}"/>"
                       disabled>
                <br>

                <label for="verified">${verified}:</label>
                <c:choose>
                    <c:when test="${sessionScope.clientUpd.verified == true}">
                        <input class="checkbox" type="checkbox" id="verified" name="verified"
                               value="${sessionScope.clientUpd.verified}"
                               checked="checked" disabled>
                    </c:when>
                    <c:otherwise>
                        <input class="checkbox" type="checkbox" id="verified" name="verified"
                               value="${sessionScope.clientUpd.verified}" disabled>
                    </c:otherwise>
                </c:choose>
                <br>

                <label for="active">${active}:</label>
                <c:choose>
                    <c:when test="${sessionScope.clientUpd.active == true}">
                        <input class="checkbox" type="checkbox" id="active" name="active"
                               value="${sessionScope.clientUpd.active}"
                               checked="checked" disabled>
                    </c:when>
                    <c:otherwise>
                        <input class="checkbox" type="checkbox" id="active" name="active"
                               value="${sessionScope.clientUpd.active}"
                               disabled>
                    </c:otherwise>
                </c:choose>

                <div class="centerOnly">
                    <a class="button" href="${sessionScope.pageToReturn}">${ok}</a>
                    <input class="button" id="submit" name="submit" type="submit" value=${register}>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>