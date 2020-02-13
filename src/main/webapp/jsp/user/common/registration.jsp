<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="project.name" bundle="${resourceBundle}" var="projectName"/>
<fmt:message key="title.registration" bundle="${resourceBundle}" var="title"/>
<fmt:message key="login" bundle="${resourceBundle}" var="login"/>
<fmt:message key="password" bundle="${resourceBundle}" var="password"/>
<fmt:message key="password.repeate" bundle="${resourceBundle}" var="repeatePassword"/>
<fmt:message key="name" bundle="${resourceBundle}" var="name"/>
<fmt:message key="surname" bundle="${resourceBundle}" var="surname"/>
<fmt:message key="phone" bundle="${resourceBundle}" var="phone"/>
<fmt:message key="email" bundle="${resourceBundle}" var="email"/>
<fmt:message key="login.expl" bundle="${resourceBundle}" var="loginExpl"/>
<fmt:message key="password.expl" bundle="${resourceBundle}" var="passwordExpl"/>
<fmt:message key="name.expl" bundle="${resourceBundle}" var="nameExpl"/>
<fmt:message key="name.placeholder" bundle="${resourceBundle}" var="namePlaceholder"/>
<fmt:message key="surname.placeholder" bundle="${resourceBundle}" var="surnamePlaceholder"/>
<fmt:message key="phone.expl" bundle="${resourceBundle}" var="phoneExpl"/>
<fmt:message key="phone.placeholder" bundle="${resourceBundle}" var="phonePlaceholder"/>
<fmt:message key="email.placeholder" bundle="${resourceBundle}" var="emailPlaceholder"/>
<fmt:message key="form.login.exists" bundle="${resourceBundle}" var="loginExists"/>
<fmt:message key="form.button.register" bundle="${resourceBundle}" var="register"/>
<fmt:message key="form.button.reset" bundle="${resourceBundle}" var="reset"/>
<fmt:message key="form.field.required" bundle="${resourceBundle}" var="fieldRequired"/>
<fmt:message key="form.pass.dont.match" bundle="${resourceBundle}" var="passNotMatch"/>
<fmt:message key="invalid.login" bundle="${errorBundle}" var="wrongLoginPass"/>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
    <script defer src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script defer src="${pageContext.request.contextPath}js/checkValidation.js"></script>
    <script defer src="${pageContext.request.contextPath}js/checkLoginExists.js"></script>
</head>
<body>
<c:import url="/jsp/template/header.jsp"/>
<h1>${title}</h1>
<div class="outer">
    <div class="center">
        <div class="dif-buf">
        </div>
        <div class="center grey shadow back-white">
            <form id="regForm" name="regForm" class="authentication registration"
                  action="${pageContext.request.contextPath}controller"
                  method="post">
                <input type="hidden" name="command" value="registration">
                <p class="noteRed">
                    <c:if test="${sessionScope.errRegMessage != null}">
                        <c:forEach var="errorMessages" items="${sessionScope.errRegMessage}">
                            <fmt:message key="${errorMessages}" bundle="${errorBundle}"/><br>
                        </c:forEach>
                    </c:if>
                </p>
                <label id="loginLabel" class="" for="login">${login}: *</label>
                <input class="inputField" type="text" id="login"
                       name="login"
                       pattern="[A-z][\w]{4,14}"
                       placeholder=${login}
                               value="${sessionScope.defLogin}"
                       autofocus required autocomplete="off"
                       title=${login}>
                <label id="loginExplanation" class="explanation">${loginExpl}</label>
                <label id="loginExplanationError" class="explanationRed displayNone">${loginExists}</label>
                <br>

                <label for="name">${name}: *</label>
                <input class="inputField" type="text" id="name"
                       name="name"
                       pattern="[\p{L} -]{1,255}"
                       placeholder=${namePlaceholder}
                               value="${sessionScope.defName}" required
                       title=${name}>
                <label class="explanation">${nameExpl}</label>
                <br>

                <label for="surname">${surname}: *</label>
                <input class="inputField" type="text" id="surname"
                       name="surname"
                       pattern="[\p{L} -]{1,255}"
                       placeholder=${surnamePlaceholder}
                               value="${sessionScope.defSurname}" required
                       title=${surname}>
                <label class="explanation">${nameExpl}</label>
                <br>

                <label for="phone">${phone}: *</label>
                <input class="inputField" type="tel" id="phone"
                       name="phone"
                       pattern="375\d{9}"
                       placeholder=${phonePlaceholder}
                               value="${sessionScope.defPhone}" required
                       title=${phone}>
                <label class="explanation">${phoneExpl}</label>
                <br>

                <label for="email">${email}: *</label>
                <input class="inputField" type="text" id="email"
                       name="email"
                       pattern="([\w-]+\.)*[\w-]+@[\w-]+(\.[\w-]+)*\.[a-z]{2,6}"
                       placeholder=${emailPlaceholder}
                               value="${sessionScope.defEmail}" required
                       title=${email}>
                <br>

                <label id="passwordLabel" for="password">${password}: *</label>
                <input class="inputField" type="password" id="password"
                       name="password"
                       pattern="[\w]{8,20}"
                       placeholder=${password}
                               value="${sessionScope.defPassword}" required
                       title=${password}>
                <label class="explanation">${passwordExpl}</label>
                <br>

                <label for="passwordRepeated">${repeatePassword}: *</label>
                <input class="inputField" type="password" id="passwordRepeated"
                       name="passwordRepeated"
                       pattern="[\w]{8,20}"
                       placeholder=${password}
                               value="${sessionScope.defPasswordRepeated}" required
                       title=${password}>
                <label id="passwordRepeatedLabel" class="explanationRed displayNone">${passNotMatch}</label>
                <br>
                <p class="noteSmall">${fieldRequired}</p>
                <div class="centerOnly">
                    <input id="submit" name="submit" class="button" type="submit" value=${register}>
                    <input id="reset" name="reset" class="button" type="reset" value=${reset}>
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