<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="profile" bundle="${resourceBundle}" var="title"/>
<fmt:message key="login" bundle="${resourceBundle}" var="login"/>
<fmt:message key="name" bundle="${resourceBundle}" var="name"/>
<fmt:message key="surname" bundle="${resourceBundle}" var="surname"/>
<fmt:message key="phone" bundle="${resourceBundle}" var="phone"/>
<fmt:message key="email" bundle="${resourceBundle}" var="email"/>
<fmt:message key="registration.date.short" bundle="${resourceBundle}" var="registrationDate"/>
<fmt:message key="form.back" bundle="${resourceBundle}" var="ok"/>
<fmt:message key="form.edit" bundle="${resourceBundle}" var="register"/>
<fmt:message key="form.delete" bundle="${resourceBundle}" var="delete"/>

<html>
<body>
<c:import url="/jsp/template/header.jsp"/>
<h1>${title}</h1>

<div class="outer">
    <div class="center">
        <div class="center grey shadow back-white">
            <table class="edit">
                <tr>
                    <td>
                        <form id="regForm" name="regForm" class="authentication registration"
                              action="${pageContext.request.contextPath}controller"
                              method="post">
                            <input type="hidden" name="command" value="edit_profile">
                            <input type="hidden" name="id" value="${sessionScope.userObj.id}">
                            <input type="hidden" name="role" value="${sessionScope.userObj.role}">

                            <p class="noteRed">
                                <c:if test="${sessionScope.errUpdateUserMessage != null}">
                                    <c:forEach var="errorMessages" items="${sessionScope.errUpdateUserMessage}">
                                        <fmt:message key="${errorMessages}" bundle="${errorBundle}"/><br>
                                    </c:forEach>
                                </c:if>
                            </p>

                            <label id="loginLabel" class="" for="login">${login}:</label>
                            <input class="inputField" type="text" id="login"
                                   name="login" value="${sessionScope.userObj.login}"
                                   disabled title=${login}>
                            <br>

                            <label for="name">${name}:</label>
                            <input class="inputField" type="text" id="name"
                                   name="name" value="${sessionScope.userObj.name}" disabled
                                   title=${name}>
                            <br>

                            <label for="surname">${surname}:</label>
                            <input class="inputField" type="text" id="surname"
                                   name="surname" value="${sessionScope.userObj.surname}" disabled
                                   title=${surname}>
                            <br>

                            <c:if test="${sessionScope.userObj.role != 'ADMIN'}">
                                <label for="phone">${phone}:</label>
                                <input class="inputField" type="tel" id="phone"
                                       name="phone" value="${sessionScope.userObj.phone}" disabled
                                       title=${phone}>
                                <br>
                            </c:if>

                            <label for="email">${email}:</label>
                            <input class="inputField" type="text" id="email"
                                   name="email" value="${sessionScope.userObj.email}" disabled
                                   title=${email}>
                            <br>

                            <label for="registrationDate">${registrationDate}: </label>
                            <fmt:parseDate value="${sessionScope.userObj.registrationDate}"
                                           pattern="yyyy-MM-dd'T'HH:mm"
                                           var="parsedDate" type="both"/>
                            <input class="inputField" type="text" id="registrationDate"
                                   name="registrationDate"
                                   value="<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDate}"/>"
                                   disabled>

                            <div class="centerOnly">
                                <a class="button" href="${sessionScope.pageToReturn}">${ok}</a>
                                <input class="button" id="submitUpdate" name="submit" type="submit" value=${register}>
                            </div>

                        </form>
                    </td>
                </tr>
                <tr>
                    <td>
                        <hr>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="centerOnly">
                            <form id="delForm" name="delForm" class="authentication registration del-form"
                                  action="${pageContext.request.contextPath}controller"
                                  method="post">
                                <input type="hidden" name="command" value="delete_user">
                                <input type="hidden" name="id" value="${sessionScope.userObj.id}">
                                <input class="button" id="submitDelete" name="submit" type="submit" value=${delete}>
                            </form>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>


</body>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
</head>
</html>