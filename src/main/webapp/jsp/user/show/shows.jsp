<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="shows" bundle="${resourceBundle}" var="title"/>
<fmt:message key="shows.text" bundle="${resourceBundle}" var="text"/>
<fmt:message key="nothing.found" bundle="${errorBundle}" var="nothingFound"/>
<fmt:message key="shows.cinema" bundle="${resourceBundle}" var="showsCinema"/>
<fmt:message key="shows.date" bundle="${resourceBundle}" var="showsDate"/>
<fmt:message key="shows.free.places" bundle="${resourceBundle}" var="freePlaces"/>
<fmt:message key="shows.time" bundle="${resourceBundle}" var="showsTime"/>
<fmt:message key="shows.cost" bundle="${resourceBundle}" var="showsCost"/>
<fmt:message key="form.button.search" bundle="${resourceBundle}" var="butSearch"/>
<fmt:message key="form.edit" bundle="${resourceBundle}" var="edit"/>
<fmt:message key="form.delete" bundle="${resourceBundle}" var="delete"/>
<fmt:message key="show.id" bundle="${resourceBundle}" var="showId"/>

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
        <c:choose>
            <c:when test="${!empty requestScope.showsMap}">
                <p class="center margin-bottom">${text}</p>
            </c:when>
            <c:otherwise>
                <p class="center margin-bottom">${nothingFound}</p>
            </c:otherwise>
        </c:choose>
        <div>
            <form id="searchForm" name="searchForm" class="searchForm"
                  action="${pageContext.request.contextPath}controller">
                <input type="hidden" name="command" value="shows_available">
                <div class="centerOnly">
                    <select id="date" name="date"
                            class="select min-width160">
                        <option disabled selected hidden
                                value=${requestScope.defaultDate}>${requestScope.defaultDate}</option>
                        <c:forEach items="${requestScope.datesList}" var="date">
                            <option value=${date}>${date}</option>
                        </c:forEach>
                    </select>
                    <select id="cinema" name="cinema">
                        <option disabled selected hidden value="">${showsCinema}</option>
                        <c:forEach items="${requestScope.cinemasMap}" var="cinema">
                            <option value=${cinema.key}>${cinema.value}</option>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="page" value="1">
                    <input type="submit" class="button" value=${butSearch}>
                </div>
            </form>
        </div>
        <c:if test="${!empty requestScope.showsMap}">
            <c:import url="/jsp/template/pagination.jsp"/>
        </c:if>
        <table class="all-films">
            <tbody>
            <c:forEach items="${requestScope.showsMap}" var="entry">
            <tr>
                <td colspan="2">
                    <hr>
                </td>
            </tr>
            <tr class="cinema-name">
                <td colspan="2">
                    <table class="width100">
                        <tr>
                            <td><p class="cinema-name">${entry.key}</p></td>
                            <fmt:parseDate value="${requestScope.defaultDate}" pattern="dd.MM.yyyy"
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
            <c:forEach items="${entry.value}" var="show">
                <tr>
                    <td class="image" rowspan="3"><img src="${show.film.img}" alt="${show.film.title}"></td>
                    <td class="film-title"><p class="film-title">${show.film.title}</p></td>
                </tr>
                <tr>
                    <td>
                        <table class="width100">
                            <tr>
                                <td class="width35"><c:forEach items="${show.film.genres}" var="genre">${genre}<br>
                                </c:forEach></td>
                                <td class="width35"><c:forEach items="${show.film.countries}" var="country">${country}
                                    <br>
                                </c:forEach></td>
                                <td class="width15">${show.film.year}</td>
                                <td class="width15">${show.film.age}+</td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table class="show-info">
                            <tr>
                                <fmt:parseDate value="${show.dateTime}" pattern="yyyy-MM-dd'T'HH:mm"
                                               var="parsedTime" type="both"/>
                                <td>${showsTime}:<br><span>
                                    <fmt:formatDate pattern="HH:mm"
                                                    value="${parsedTime}"/></span></td>
                                <td>${showsCost}:<br><span>${show.cost} BYN</span></td>
                                <td>${freePlaces}:<br><span>${show.freePlace}</span></td>
                            </tr>
                        </table>
                        <c:if test="${sessionScope.userAuthorizated.role eq 'ADMIN' or sessionScope.userAuthorizated.role eq 'MANAGER'}">
                            <table class="width100">
                                <tr class="gray">
                                    <td class="middle-center">${showId}: ${show.id}</td>
                                    <td class="middle-center">
                                        <a class="button"
                                           href="${pageContext.request.contextPath}
                                       /controller?command=edit_show&showId=${show.id}">${edit}</a>
                                        <a class="button"
                                           href="${pageContext.request.contextPath}
                                       /controller?command=show_to_delete&showId=${show.id}">${delete}</a>
                                    </td>
                                </tr>
                            </table>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <hr>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>