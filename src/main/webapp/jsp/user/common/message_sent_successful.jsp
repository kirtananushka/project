<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="message.send.successful" bundle="${resourceBundle}" var="title"/>
<fmt:message key="message.send.successful" bundle="${resourceBundle}" var="text"/>
<fmt:message key="form.goto.main" bundle="${resourceBundle}" var="main"/>


<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
</head>
<body>
<c:import url="/jsp/template/header.jsp"/>
<h1>${title}</h1>
<div class="outer">
    <div class="center-padding grey shadow width50 back-white">
        <p>${text}</p>
        <form class="authentication" action="${pageContext.request.contextPath}/index">
            <div class="centerOnly">
                <input class="button" type="submit" value=${main}>
            </div>
        </form>
    </div>
</div>
</body>
</html>
