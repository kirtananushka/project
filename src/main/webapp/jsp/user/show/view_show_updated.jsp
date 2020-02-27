<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="info.updated" bundle="${resourceBundle}" var="title"/>
<fmt:message key="form.back" bundle="${resourceBundle}" var="ok"/>
<fmt:message key="nothing.found" bundle="${errorBundle}" var="nothingFound"/>
<fmt:message key="form.edit" bundle="${resourceBundle}" var="edit"/>
<fmt:message key="shows.free.places" bundle="${resourceBundle}" var="freePlaces"/>
<fmt:message key="shows.time" bundle="${resourceBundle}" var="showsTime"/>
<fmt:message key="shows.cost" bundle="${resourceBundle}" var="showsCost"/>
<fmt:message key="film.edit" bundle="${resourceBundle}" var="editFilm"/>
<fmt:message key="film.id" bundle="${resourceBundle}" var="filmId"/>


<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
</head>
<body>
<c:import url="/jsp/template/header.jsp"/>
<h1>${title}</h1>
<div class="outer">
    <c:choose>
        <c:when test="${empty sessionScope.showObj}">
            <div class="center-padding grey shadow width50 back-white">
                <p class="center margin-bottom">${nothingFound}</p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="center-padding grey shadow width50 back-white">
                <table class="all-films">
                    <div class="padding-top-40">
                        <tr>
                            <td colspan="2">
                                <hr>
                            </td>
                        </tr>
                        <tr class="cinema-name">
                            <td colspan="2">
                                <table class="width100">
                                    <tr>
                                        <td><p class="cinema-name">${sessionScope.showObj.cinemaName}</p></td>
                                        <fmt:parseDate value="${sessionScope.showObj.dateTime}"
                                                       pattern="yyyy-MM-dd'T'HH:mm"
                                                       var="parsedDate" type="both"/>
                                        <td><p class="cinema-date">
                                            <fmt:formatDate pattern="d MMMM, EEEE" value="${parsedDate}"/>
                                        </p></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <hr>
                            </td>
                        </tr>
                        <tr>
                            <td class="image" rowspan="3"><img src="${sessionScope.showObj.film.img}"
                                                               alt="${sessionScope.showObj.film.title}"></td>
                            <td class="film-title"><p class="film-title">${sessionScope.showObj.film.title}</p></td>
                        </tr>
                        <tr>
                            <td>
                                <table class="width100">
                                    <tr>
                                        <td class="width35"><c:forEach items="${sessionScope.showObj.film.genres}"
                                                                       var="genre">${genre}<br>
                                        </c:forEach></td>
                                        <td class="width35"><c:forEach
                                                items="${sessionScope.showObj.film.countries}"
                                                var="country">${country}<br>
                                        </c:forEach></td>
                                        <td class="width15">${sessionScope.showObj.film.year}</td>
                                        <td class="width15">${sessionScope.showObj.film.age}+</td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <c:if test="${sessionScope.userAuthorizated.role eq 'ADMIN' or sessionScope.userAuthorizated.role eq 'MANAGER'}">
                                    <table class="width100">
                                        <tr>
                                            <td class="middle-left width35">${filmId}:
                                                    ${sessionScope.showObj.film.id}</td>
                                            <td><a class="button"
                                                   href="${pageContext.request.contextPath}
                                       /controller?command=edit_film&filmId=${sessionScope.showObj.film.id}">${editFilm}</a>
                                            </td>
                                        </tr>
                                    </table>
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td></td>
                            <td>
                                <table class="show-info">
                                    <tr>
                                        <fmt:parseDate value="${sessionScope.showObj.dateTime}"
                                                       pattern="yyyy-MM-dd'T'HH:mm"
                                                       var="parsedTime" type="both"/>
                                        <td>${showsTime}:<br><span>
                                    <fmt:formatDate pattern="HH:mm"
                                                    value="${parsedTime}"/></span></td>
                                        <td>${showsCost}:<br><span>${sessionScope.showObj.cost} BYN</span></td>
                                        <td>${freePlaces}:<br><span>${sessionScope.showObj.freePlace}</span></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <hr>
                            </td>
                        </tr>
                        </tbody>
                    </div>
                </table>
                <div class="centerOnly">
                    <a class="button" href="${sessionScope.pageToReturn}">${ok}</a>
                    <c:if test="${sessionScope.userAuthorizated.role eq 'ADMIN' or sessionScope.userAuthorizated.role eq 'MANAGER'}">
                        <a class="button" href="${pageContext.request.contextPath}
                                       /controller?command=edit_show&showId=${sessionScope.showObj.id}">${edit}</a></td>
                    </c:if>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>