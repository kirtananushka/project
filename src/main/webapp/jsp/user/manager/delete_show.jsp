<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="show.delete" bundle="${resourceBundle}" var="title"/>
<fmt:message key="form.back" bundle="${resourceBundle}" var="ok"/>
<fmt:message key="form.delete" bundle="${resourceBundle}" var="delete"/>
<fmt:message key="nothing.found" bundle="${errorBundle}" var="nothingFound"/>
<fmt:message key="shows.free.places" bundle="${resourceBundle}" var="freePlaces"/>
<fmt:message key="shows.time" bundle="${resourceBundle}" var="showsTime"/>
<fmt:message key="shows.cost" bundle="${resourceBundle}" var="showsCost"/>

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
                <div class="padding-top-40">
                    <form id="deleteShowForm" name="deleteShowForm"
                          action="${pageContext.request.contextPath}controller"
                          method="post">
                        <input type="hidden" name="command" value="delete_show">
                        <input type="hidden" name="img" value=${sessionScope.showObj.film.img}>
                        <input type="hidden" name="showId" value="${sessionScope.showObj.id}">
                        <p class="noteRed">
                            <c:if test="${sessionScope.errDeleteShowMessage != null}">
                                <c:forEach var="errorMessages" items="${sessionScope.errDeleteShowMessage}">
                                    <fmt:message key="${errorMessages}" bundle="${errorBundle}"/><br>
                                </c:forEach>
                            </c:if>
                        </p>
                        <table class="all-films">
                            <tbody>
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
                                <td class="image" rowspan="3">
                                    <img src="${sessionScope.showObj.film.img}"
                                         alt="${sessionScope.showObj.film.title}">
                                </td>
                                <td class="film-title">
                                    <p class="film-title">${sessionScope.showObj.film.title}</p>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table class="width100">
                                        <tr>
                                            <td class="width35"><c:forEach
                                                    items="${sessionScope.showObj.film.genres}"
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
                        </table>
                        <div class="centerOnly">
                            <a class="button" href="${sessionScope.pageToReturn}">${ok}</a>
                            <input id="submit" name="submit" class="button" type="submit" value="${delete}">
                        </div>
                    </form>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>