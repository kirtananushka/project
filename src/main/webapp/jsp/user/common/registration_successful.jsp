<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>

<fmt:message key="registration.success" bundle="${resourceBundle}" var="title"/>
<fmt:message key="registration.text" bundle="${resourceBundle}" var="text"/>
<fmt:message key="button.ok" bundle="${resourceBundle}" var="ok"/>
<fmt:message key="login" bundle="${resourceBundle}" var="login"/>
<fmt:message key="name" bundle="${resourceBundle}" var="name"/>
<fmt:message key="surname" bundle="${resourceBundle}" var="surname"/>
<fmt:message key="phone" bundle="${resourceBundle}" var="phone"/>
<fmt:message key="email" bundle="${resourceBundle}" var="email"/>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
</head>
<body>
<c:import url="/jsp/template/header.jsp"/>
<h1>${title}</h1>
<div class="outer">
    <div class="center-padding grey shadow width50 back-white padding-top-40">
        <table>
            <tr>
                <td class="middle-left">
                    <p>${text}</p>
                </td>
            </tr>
            <tr>
                <td>
                    <form id="regForm" name="regForm" class="authentication registration">
                        <label id="loginLabel" class="" for="login">${login}:</label>
                        <input class="inputField" type="text" id="login"
                               name="login" value="${sessionScope.newUser.login}"
                               readonly title=${login}>
                        <br>
                        <label id="nameLabel" class="" for="name">${name}:</label>
                        <input class="inputField" type="text" id="name"
                               name="name" value="${sessionScope.newUser.name}"
                               readonly title=${name}>
                        <br>
                        <label id="surnameLabel" class="" for="surname">${surname}:</label>
                        <input class="inputField" type="text" id="surname"
                               name="surname" value="${sessionScope.newUser.surname}"
                               readonly title=${surname}>
                        <br>
                        <label id="emailLabel" class="" for="email">${email}:</label>
                        <input class="inputField" type="text" id="email"
                               name="email" value="${sessionScope.newUser.email}"
                               readonly title=${email}>
                        <br>
                        <label id="phoneLabel" class="" for="phone">${phone}:</label>
                        <input class="inputField" type="text" id="phone"
                               name="phone" value="${sessionScope.newUser.phone}"
                               readonly title=${phone}>

                        <div class="centerOnly">
                            <a class="button" href="${sessionScope.pageToReturn}">${ok}</a>
                        </div>
                    </form>
                </td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
