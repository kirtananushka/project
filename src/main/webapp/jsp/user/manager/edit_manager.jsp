<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="form.edit" bundle="${resourceBundle}" var="title"/>
<fmt:message key="manager.id" bundle="${resourceBundle}" var="managerId"/>
<fmt:message key="login" bundle="${resourceBundle}" var="login"/>
<fmt:message key="name" bundle="${resourceBundle}" var="name"/>
<fmt:message key="surname" bundle="${resourceBundle}" var="surname"/>
<fmt:message key="phone" bundle="${resourceBundle}" var="phone"/>
<fmt:message key="email" bundle="${resourceBundle}" var="email"/>
<fmt:message key="name.expl" bundle="${resourceBundle}" var="nameExpl"/>
<fmt:message key="name.placeholder" bundle="${resourceBundle}" var="namePlaceholder"/>
<fmt:message key="surname.placeholder" bundle="${resourceBundle}" var="surnamePlaceholder"/>
<fmt:message key="phone.expl" bundle="${resourceBundle}" var="phoneExpl"/>
<fmt:message key="phone.placeholder" bundle="${resourceBundle}" var="phonePlaceholder"/>
<fmt:message key="email.placeholder" bundle="${resourceBundle}" var="emailPlaceholder"/>
<fmt:message key="form.field.required" bundle="${resourceBundle}" var="fieldRequired"/>
<fmt:message key="form.update" bundle="${resourceBundle}" var="register"/>
<fmt:message key="registration.date.short" bundle="${resourceBundle}" var="registrationDate"/>
<fmt:message key="verified" bundle="${resourceBundle}" var="verified"/>
<fmt:message key="active" bundle="${resourceBundle}" var="active"/>
<fmt:message key="user.role" bundle="${resourceBundle}" var="role"/>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
    <script defer src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script defer src="${pageContext.request.contextPath}js/checkValidationWhileManagerUpdating.js"></script>
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
                <input type="hidden" name="command" value="update_manager">
                <input type="hidden" name="id" value="${sessionScope.manager.id}">
                <input type="hidden" name="role" value="${sessionScope.manager.role}">
                <p class="noteRed">
                    <c:if test="${sessionScope.errUpdateManagerMessage != null}">
                        <c:forEach var="errorMessages" items="${sessionScope.errUpdateManagerMessage}">
                            <fmt:message key="${errorMessages}" bundle="${errorBundle}"/><br>
                        </c:forEach>
                    </c:if>
                </p>
                <label id="idLabel" class="" for="managerId">${managerId}:</label>
                <input class="inputField" type="text" id="managerId"
                       name="id" value="${sessionScope.manager.id}"
                       disabled title=${managerId}>
                <br>

                <label id="roleLabel" class="" for="role">${role}:</label>
                <input class="inputField" type="text" id="role"
                       name="role" value="${sessionScope.manager.role}"
                       disabled title=${role}>
                <br>

                <label id="loginLabel" class="" for="login">${login}:</label>
                <input class="inputField" type="text" id="login"
                       name="login" value="${sessionScope.manager.login}"
                       disabled title=${login}>
                <br>

                <label for="name">${name}: *</label>
                <input class="inputField" type="text" id="name"
                       name="name" pattern="[\p{L} -]{1,255}"
                       placeholder=${namePlaceholder}
                               value="${sessionScope.manager.name}" required
                       title=${name}>
                <label class="explanation">${nameExpl}</label>
                <br>

                <label for="surname">${surname}: *</label>
                <input class="inputField" type="text" id="surname"
                       name="surname"
                       pattern="[\p{L} -]{1,255}"
                       placeholder=${surnamePlaceholder}
                               value="${sessionScope.manager.surname}" required
                       title=${surname}>
                <label class="explanation">${nameExpl}</label>
                <br>

                <label for="phone">${phone}: *</label>
                <input class="inputField" type="tel" id="phone"
                       name="phone"
                       pattern="375\d{9}"
                       placeholder=${phonePlaceholder}
                               value="${sessionScope.manager.phone}" required
                       title=${phone}>
                <label class="explanation">${phoneExpl}</label>
                <br>

                <label for="email">${email}: *</label>
                <input class="inputField" type="text" id="email"
                       name="email"
                       pattern="([\w-]+\.)*[\w-]+@[\w-]+(\.[\w-]+)*\.[a-z]{2,6}"
                       placeholder=${emailPlaceholder}
                               value="${sessionScope.manager.email}" required
                       title=${email}>
                <br>

                <label for="registrationDate">${registrationDate}: </label>
                <fmt:parseDate value="${sessionScope.manager.registrationDate}"
                               pattern="yyyy-MM-dd'T'HH:mm"
                               var="parsedDate" type="both"/>
                <input class="inputField" type="text" id="registrationDate"
                       name="registrationDate"
                       value="<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDate}"/>"
                       disabled>
                <br>

                <label for="verified">${verified}:</label>
                <c:choose>
                    <c:when test="${sessionScope.manager.verified == true}">
                        <input class="checkbox" type="checkbox" id="verified" name="verified"
                               value="${sessionScope.manager.verified}"
                               checked="checked" disabled>
                    </c:when>
                    <c:otherwise>
                        <input class="checkbox" type="checkbox" id="verified" name="verified"
                               value="${sessionScope.manager.verified}" disabled>
                    </c:otherwise>
                </c:choose>
                <br>

                <label for="active">${active}: *</label>
                <c:choose>
                    <c:when test="${sessionScope.manager.active == true}">
                        <input class="checkbox" type="checkbox" id="active" name="active"
                               value="${sessionScope.manager.active}"
                               checked="checked">
                    </c:when>
                    <c:otherwise>
                        <input class="checkbox" type="checkbox" id="active" name="active"
                               value="${sessionScope.manager.active}">
                    </c:otherwise>
                </c:choose>

                <p class="noteSmall">${fieldRequired}</p>
                <div class="centerOnly">
                    <input id="submit" name="submit" class="button" type="submit" value=${register}>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>