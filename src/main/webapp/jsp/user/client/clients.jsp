<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<%--<fmt:setBundle basename="bundle/err" var="errorBundle"/>--%>

<%--<fmt:message key="project.name" bundle="${resourceBundle}" var="projectName"/>--%>
<fmt:message key="nothing.found" bundle="${resourceBundle}" var="nothingFound"/>
<fmt:message key="clients.active" bundle="${resourceBundle}" var="title"/>
<fmt:message key="client.id" bundle="${resourceBundle}" var="id"/>
<fmt:message key="login" bundle="${resourceBundle}" var="login"/>
<fmt:message key="name" bundle="${resourceBundle}" var="name"/>
<fmt:message key="surname" bundle="${resourceBundle}" var="surname"/>
<fmt:message key="email.email" bundle="${resourceBundle}" var="email"/>
<fmt:message key="phone" bundle="${resourceBundle}" var="phone"/>
<fmt:message key="verified" bundle="${resourceBundle}" var="verified"/>
<fmt:message key="active" bundle="${resourceBundle}" var="active"/>
<fmt:message key="registration.date.short" bundle="${resourceBundle}" var="registrationDate"/>


<%--<fmt:message key="password" bundle="${resourceBundle}" var="password"/>--%>
<%--<fmt:message key="password.repeate" bundle="${resourceBundle}" var="repeatePassword"/>--%>
<%--<fmt:message key="login.expl" bundle="${resourceBundle}" var="loginExpl"/>--%>
<%--<fmt:message key="password.expl" bundle="${resourceBundle}" var="passwordExpl"/>--%>
<%--<fmt:message key="name.expl" bundle="${resourceBundle}" var="nameExpl"/>--%>
<%--<fmt:message key="name.placeholder" bundle="${resourceBundle}" var="namePlaceholder"/>--%>
<%--<fmt:message key="surname.placeholder" bundle="${resourceBundle}" var="surnamePlaceholder"/>--%>
<%--<fmt:message key="phone.expl" bundle="${resourceBundle}" var="phoneExpl"/>--%>
<%--<fmt:message key="phone.placeholder" bundle="${resourceBundle}" var="phonePlaceholder"/>--%>
<%--<fmt:message key="email.placeholder" bundle="${resourceBundle}" var="emailPlaceholder"/>--%>
<%--<fmt:message key="form.login.exists" bundle="${resourceBundle}" var="loginExists"/>--%>
<%--<fmt:message key="form.button.register" bundle="${resourceBundle}" var="register"/>--%>
<%--<fmt:message key="form.button.reset" bundle="${resourceBundle}" var="reset"/>--%>
<%--<fmt:message key="form.field.required" bundle="${resourceBundle}" var="fieldRequired"/>--%>
<%--<fmt:message key="form.pass.dont.match" bundle="${resourceBundle}" var="passNotMatch"/>--%>
<%--<fmt:message key="invalid.login" bundle="${errorBundle}" var="wrongLoginPass"/>--%>

<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
</head>
<body>
<c:import url="/jsp/template/header.jsp"/>
<h1>${title}</h1>
<div class="outer">
    <div class="center-padding grey shadow width50 back-white padding-top-40">
        <c:choose>
            <c:when test="${empty requestScope.clientsList}">
                <p class="center margin-bottom">${nothingFound}</p>
            </c:when>
            <c:otherwise>

                <c:import url="/jsp/template/pagination.jsp"/>

                <c:forEach items="${requestScope.clientsList}" var="client">
                    <table class="clients-view width100">
                        <tr>
                            <td class="width20 middle-left">
                                    ${id}
                            </td>
                            <td class="middle-left">
                                <input class="width95" type="text" id="clientId"
                                       name="clientId" value="${client.id}" disabled>
                            </td>
                            <td class="width20 middle-left">
                                    ${login}
                            </td>
                            <td class="middle-left">
                                <input class="width95" type="text" id="login"
                                       name="login" value="${client.login}" disabled>
                            </td>
                        </tr>
                        <tr>
                            <td class="width20 middle-left">
                                    ${name}
                            </td>
                            <td class="middle-left">
                                <input class="width95" type="text" id="name"
                                       name="name" value="${client.name}" disabled>
                            </td>
                            <td class="width20 middle-left">
                                    ${surname}
                            </td>
                            <td class="middle-left">
                                <input class="width95" type="text" id="surname"
                                       name="surname" value="${client.surname}" disabled>
                            </td>
                        </tr>
                        <tr>
                            <td class="width20 middle-left">
                                    ${email}
                            </td>
                            <td class="middle-left">
                                <input class="width95" type="text" id="email"
                                       name="email" value="${client.email}" disabled>
                            </td>
                            <td class="width20 middle-left">
                                    ${phone}
                            </td>
                            <td class="middle-left">
                                <input class="width95" type="text" id="phone"
                                       name="phone" value="${client.phone}" disabled>
                            </td>
                        </tr>
                        <tr>
                            <td class="width20 middle-left">
                                    ${registrationDate}
                            </td>
                            <td class="middle-left">
                                <fmt:parseDate value="${client.registrationDate}"
                                               pattern="yyyy-MM-dd'T'HH:mm"
                                               var="parsedDate" type="both"/>
                                <input class="width95" type="text" id="registrationDate"
                                       name="registrationDate"
                                       value="<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDate}"/>"
                                       disabled>
                            </td>
                            <td>
                            </td>
                            <td>
                            </td>
                        </tr>
                        <tr>
                            <td class="width20 middle-left">
                                    ${verified}
                            </td>
                            <td class="middle-left">
                                <input class="checkbox" type="checkbox" id="isVerified"
                                       name="isVerified" value="${client.verified}" disabled
                                       <c:if test="${client.verified = true}">checked</c:if>>
                            </td>
                            <td class="width20 middle-left">
                                    ${active}
                            </td>
                            <td class="middle-left">
                                <input class="checkbox" type="checkbox" id="isActive"
                                       name="isActive" value="${client.active}" disabled
                                       <c:if test="${client.active = true}">checked</c:if>>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <hr>
                            </td>
                        </tr>
                    </table>
                </c:forEach>


            </c:otherwise>
        </c:choose>

    </div>

</div>
</body>
</html>