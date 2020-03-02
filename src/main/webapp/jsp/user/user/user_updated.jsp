<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="updating.successful" bundle="${resourceBundle}" var="title"/>
<fmt:message key="form.back" bundle="${resourceBundle}" var="ok"/>
<fmt:message key="user.id" bundle="${resourceBundle}" var="userId"/>
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/indexstyle.css">
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
                <input type="hidden" name="command" value="edit_user">
                <input type="hidden" name="id" value="${sessionScope.userUpd.id}">
                <p class="noteRed">
                    <c:if test="${sessionScope.errUpdateUserMessage != null}">
                        <c:forEach var="errorMessages" items="${sessionScope.errUpdateUserMessage}">
                            <fmt:message key="${errorMessages}" bundle="${errorBundle}"/><br>
                        </c:forEach>
                    </c:if>
                </p>
                <label id="idLabel" class="" for="userId">${userId}:</label>
                <input class="inputField" type="text" id="userId"
                       name="id" value="${sessionScope.userUpd.id}"
                       disabled title=${userId}>
                <br>

                <label id="roleLabel" class="" for="role">${role}:</label>
                <input class="inputField" type="text" id="role"
                       name="role" value="${sessionScope.userUpd.role}"
                       disabled title=${role}>
                <br>

                <label id="loginLabel" class="" for="login">${login}:</label>
                <input class="inputField" type="text" id="login"
                       name="login" value="${sessionScope.userUpd.login}"
                       disabled title=${login}>
                <br>

                <label for="name">${name}:</label>
                <input class="inputField" type="text" id="name"
                       name="name" value="${sessionScope.userUpd.name}" disabled
                       title=${name}>
                <br>

                <label for="surname">${surname}:</label>
                <input class="inputField" type="text" id="surname"
                       name="surname" value="${sessionScope.userUpd.surname}" disabled
                       title=${surname}>
                <br>

                <c:if test="${sessionScope.user.role != 'ADMIN'}">
                    <label for="phone">${phone}:</label>
                    <input class="inputField" type="tel" id="phone"
                           name="phone" value="${sessionScope.userUpd.phone}" disabled
                           title=${phone}>
                    <br>
                </c:if>

                <label for="email">${email}:</label>
                <input class="inputField" type="text" id="email"
                       name="email" value="${sessionScope.userUpd.email}" disabled
                       title=${email}>
                <br>

                <label for="registrationDate">${registrationDate}: </label>
                <fmt:parseDate value="${sessionScope.userUpd.registrationDate}"
                               pattern="yyyy-MM-dd'T'HH:mm"
                               var="parsedDate" type="both"/>
                <input class="inputField" type="text" id="registrationDate"
                       name="registrationDate"
                       value="<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDate}"/>"
                       disabled>
                <br>

                <label for="verified">${verified}:</label>
                <c:choose>
                    <c:when test="${sessionScope.userUpd.verified == true}">
                        <input class="checkbox" type="checkbox" id="verified" name="verified"
                               value="${sessionScope.userUpd.verified}"
                               checked="checked" disabled>
                    </c:when>
                    <c:otherwise>
                        <input class="checkbox" type="checkbox" id="verified" name="verified"
                               value="${sessionScope.userUpd.verified}" disabled>
                    </c:otherwise>
                </c:choose>
                <br>

                <label for="active">${active}:</label>
                <c:choose>
                    <c:when test="${sessionScope.userUpd.active == true}">
                        <input class="checkbox" type="checkbox" id="active" name="active"
                               value="${sessionScope.userUpd.active}"
                               checked="checked" disabled>
                    </c:when>
                    <c:otherwise>
                        <input class="checkbox" type="checkbox" id="active" name="active"
                               value="${sessionScope.userUpd.active}"
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