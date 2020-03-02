<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="title.send.message" bundle="${resourceBundle}" var="title"/>
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
<fmt:message key="message.send" bundle="${resourceBundle}" var="send"/>
<fmt:message key="form.button.reset" bundle="${resourceBundle}" var="reset"/>
<fmt:message key="form.field.required" bundle="${resourceBundle}" var="fieldRequired"/>
<fmt:message key="form.message.text" bundle="${resourceBundle}" var="messageText"/>
<fmt:message key="message.text.placeholder" bundle="${resourceBundle}" var="messageTextPlaceholder"/>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
    <script defer src="${pageContext.request.contextPath}js/checkValidationMessage.js"></script>
</head>
<body>
<c:import url="/jsp/template/header.jsp"/>
<h1>${title}</h1>
<div class="outer">
    <div class="center">
        <div class="center grey shadow back-white">
            <form id="sendMessageForm" name="sendMessageForm" class="authentication registration"
                  action="${pageContext.request.contextPath}controller"
                  method="post">
                <input type="hidden" name="command" value="send_message">
                <p class="noteRed">
                    <c:if test="${sessionScope.errSendMessage != null}">
                        <c:forEach var="errorMessages" items="${sessionScope.errSendMessage}">
                            <fmt:message key="${errorMessages}" bundle="${errorBundle}"/><br>
                        </c:forEach>
                    </c:if>
                </p>
                <label for="msgName">${name}: *</label>
                <input class="inputField" type="text" id="msgName"
                       name="msgName"
                       pattern="[\p{L} -]{1,255}"
                       placeholder=${namePlaceholder}
                               value="${sessionScope.defName}" required
                       title=${name}>
                <label class="explanation">${nameExpl}</label>
                <br>

                <label for="msgSurname">${surname}: *</label>
                <input class="inputField" type="text" id="msgSurname"
                       name="msgSurname"
                       pattern="[\p{L} -]{1,255}"
                       placeholder=${surnamePlaceholder}
                               value="${sessionScope.defSurname}" required
                       title=${surname}>
                <label class="explanation">${nameExpl}</label>
                <br>

                <label for="msgPhone">${phone}: *</label>
                <input class="inputField" type="tel" id="msgPhone"
                       name="msgPhone"
                       pattern="375\d{9}"
                       placeholder=${phonePlaceholder}
                               value="${sessionScope.defPhone}" required
                       title=${phone}>
                <label class="explanation">${phoneExpl}</label>
                <br>

                <label for="msgEmail">${email}: *</label>
                <input class="inputField" type="text" id="msgEmail"
                       name="msgEmail"
                       pattern="([\w-]+\.)*[\w-]+@[\w-]+(\.[\w-]+)*\.[a-z]{2,6}"
                       placeholder=${emailPlaceholder}
                               value="${sessionScope.defEmail}" required
                       title=${email}>
                <br>
                <div class="vertical-top">
                    <label for="msgMessage">${messageText}: *</label>
                    <textarea class="inputField textarea" type="text" id="msgMessage"
                              name="msgMessage" minlength="1" maxlength="500"
                              placeholder=${messageTextPlaceholder}
                                      required
                              title=${messageText}>${sessionScope.defMessage}</textarea>
                    <br>
                </div>
                <p class="noteSmall">${fieldRequired}</p>
                <div class="centerOnly">
                    <input id="submit" name="submit" class="button" type="submit" value=${send}>
                    <input id="reset" name="reset" class="button" type="reset" value=${reset}>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>