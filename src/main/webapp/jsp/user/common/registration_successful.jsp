<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="registration.success" bundle="${resourceBundle}" var="title"/>
<fmt:message key="registration.text" bundle="${resourceBundle}" var="text"/>
<fmt:message key="login" bundle="${resourceBundle}" var="login"/>
<fmt:message key="password" bundle="${resourceBundle}" var="password"/>
<fmt:message key="name" bundle="${resourceBundle}" var="name"/>
<fmt:message key="surname" bundle="${resourceBundle}" var="surname"/>
<fmt:message key="phone" bundle="${resourceBundle}" var="phone"/>
<fmt:message key="email" bundle="${resourceBundle}" var="email"/>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
</head>
<body>
<c:import url="/jsp/template/header.jsp"/>
<h1>${title}</h1>
<div class="outer">
    <div class="center-padding grey shadow width50 back-white">
        <p>${text}</p>
        <hr>
        <div class="centerOnlyNotFlex">
            <strong>${login}: </strong><c:out value="${sessionScope.newUser.login}"/><br>
            <strong>${name}: </strong><c:out value="${sessionScope.newUser.name}"/><br>
            <strong>${surname}: </strong><c:out value="${sessionScope.newUser.surname}"/><br>
            <strong>${email}: </strong><c:out value="${sessionScope.newUser.email}"/><br>
            <strong>${phone}: </strong><c:out value="${sessionScope.newUser.phone}"/><br>
            <strong>${password}: </strong><c:out value="${sessionScope.newUser.password}"/>
        </div>
    </div>
</div>
</body>
</html>
