<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="nothing.found" bundle="${errorBundle}" var="nothingFound"/>
<fmt:message key="users.all" bundle="${resourceBundle}" var="title"/>
<fmt:message key="user.id" bundle="${resourceBundle}" var="id"/>
<fmt:message key="login" bundle="${resourceBundle}" var="login"/>
<fmt:message key="name" bundle="${resourceBundle}" var="name"/>
<fmt:message key="surname" bundle="${resourceBundle}" var="surname"/>
<fmt:message key="email.email" bundle="${resourceBundle}" var="email"/>
<fmt:message key="phone" bundle="${resourceBundle}" var="phone"/>
<fmt:message key="verified" bundle="${resourceBundle}" var="verified"/>
<fmt:message key="active" bundle="${resourceBundle}" var="active"/>
<fmt:message key="registration.date.short" bundle="${resourceBundle}" var="registrationDate"/>
<fmt:message key="form.edit" bundle="${resourceBundle}" var="edit"/>
<fmt:message key="user.role" bundle="${resourceBundle}" var="role"/>
<fmt:message key="all.orders" bundle="${resourceBundle}" var="orders"/>

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
        <c:choose>
            <c:when test="${empty requestScope.usersList}">
                <p class="center margin-bottom">${nothingFound}</p>
            </c:when>
            <c:otherwise>
                <c:import url="/jsp/template/pagination.jsp"/>
                <c:forEach items="${requestScope.usersList}" var="user">
                    <table class="table-view width100">
                        <tr>
                            <td class="width20 middle-left">
                                <strong>${login}</strong>
                            </td>
                            <td class="middle-left"><strong>
                                <input class="width95" type="text" id="login"
                                       name="login" value="${user.login}" disabled>
                            </strong></td>
                            <td class="width20 middle-left">
                                    ${id}
                            </td>
                            <td class="middle-left">
                                <input class="width95" type="text" id="userId"
                                       name="id" value="${user.id}" disabled>
                            </td>
                        </tr>
                        <tr>
                            <td class="width20 middle-left">
                                    ${name}
                            </td>
                            <td class="middle-left">
                                <input class="width95" type="text" id="name"
                                       name="name" value="${user.name}" disabled>
                            </td>
                            <td class="width20 middle-left">
                                    ${surname}
                            </td>
                            <td class="middle-left">
                                <input class="width95" type="text" id="surname"
                                       name="surname" value="${user.surname}" disabled>
                            </td>
                        </tr>
                        <tr>
                            <td class="width20 middle-left">
                                    ${email}
                            </td>
                            <td class="middle-left">
                                <input class="width95" type="text" id="email"
                                       name="email" value="${user.email}" disabled>
                            </td>
                            <td class="width20 middle-left">
                                    ${phone}
                            </td>
                            <td class="middle-left">
                                <c:if test="${user.role != 'ADMIN'}">
                                    <input class="width95" type="text" id="phone"
                                           name="phone" value="${user.phone}" disabled>
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="width20 middle-left">
                                    ${registrationDate}
                            </td>
                            <td class="middle-left">
                                <fmt:parseDate value="${user.registrationDate}"
                                               pattern="yyyy-MM-dd'T'HH:mm"
                                               var="parsedDate" type="both"/>
                                <input class="width95" type="text" id="registrationDate"
                                       name="registrationDate"
                                       value="<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDate}"/>"
                                       disabled>
                            </td>
                            <td class="width20 middle-left">
                                    ${role}
                            </td>
                            <td class="middle-left">
                                <input class="width95" type="text" id="role"
                                       name="role" value="${user.role}" disabled>
                            </td>
                        </tr>
                        <tr>
                            <td class="width20 middle-left">
                                    ${verified}
                            </td>
                            <td class="middle-left">
                                <c:choose>
                                    <c:when test="${user.verified == true}">
                                        <input class="checkbox" type="checkbox" id="verified" name="verified"
                                               value="${user.verified}"
                                               checked="checked" disabled>
                                    </c:when>
                                    <c:otherwise>
                                        <input class="checkbox" type="checkbox" id="verified" name="verified"
                                               value="${user.verified}" disabled>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td class="width20 middle-left">
                                    ${active}
                            </td>
                            <td class="middle-left">
                                <c:choose>
                                    <c:when test="${user.active == true}">
                                        <input class="checkbox" type="checkbox" id="active" name="active"
                                               value="${user.active}"
                                               checked="checked" disabled>
                                    </c:when>
                                    <c:otherwise>
                                        <input class="checkbox" type="checkbox" id="active" name="active"
                                               value="${user.active}" disabled>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4">
                                <hr>
                            </td>
                        </tr>
                        <tr class="gray">
                            <td colspan="4" class="middle-center">
                                <a class="button"
                                   href="${pageContext.request.contextPath}
                                       /controller?command=edit_user&id=${user.id}">${edit}</a>
                                <c:if test="${user.role == 'CLIENT'}">
                                    <a class="button"
                                       href="${pageContext.request.contextPath}
                                   /controller?command=single_client_orders&id=${user.id}&page=1">${orders}</a>
                                </c:if>
                            </td>
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