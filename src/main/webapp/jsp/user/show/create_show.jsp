<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="show.create" bundle="${resourceBundle}" var="title"/>
<fmt:message key="form.back" bundle="${resourceBundle}" var="ok"/>
<fmt:message key="form.add" bundle="${resourceBundle}" var="add"/>
<fmt:message key="nothing.found" bundle="${errorBundle}" var="nothingFound"/>
<fmt:message key="shows.free.places" bundle="${resourceBundle}" var="freePlaces"/>
<fmt:message key="shows.time" bundle="${resourceBundle}" var="showsTime"/>
<fmt:message key="shows.cost" bundle="${resourceBundle}" var="showsCost"/>
<fmt:message key="show.id" bundle="${resourceBundle}" var="showId"/>
<fmt:message key="form.button.reset" bundle="${resourceBundle}" var="reset"/>
<fmt:message key="units.cost" bundle="${resourceBundle}" var="unitsCost"/>
<fmt:message key="units.time" bundle="${resourceBundle}" var="unitsTime"/>
<fmt:message key="shows.cinema" bundle="${resourceBundle}" var="cinema"/>
<fmt:message key="shows.date" bundle="${resourceBundle}" var="date"/>
<fmt:message key="film.title" bundle="${resourceBundle}" var="filmTitle"/>

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
        <div class="padding-top-40">
            <form id="createShowForm" name="createShowForm"
                  action="${pageContext.request.contextPath}controller"
                  method="post">
                <input type="hidden" name="command" value="add_show">
                <p class="noteRed">
                    <c:if test="${sessionScope.errCreateShowMessage != null}">
                        <c:forEach var="errorMessages" items="${sessionScope.errCreateShowMessage}">
                            <fmt:message key="${errorMessages.key}" bundle="${errorBundle}" var="key"/>
                            ${key}<br>${errorMessages.value}<br>
                        </c:forEach>
                    </c:if>
                </p>
                <table class="all-films">
                    <tbody>
                    <tr class="cinema-name">
                        <td>
                            <table class="width100">
                                <tr>
                                    <td>
                                        <p class="cinema-name">
                                            <select id="cinema" name="cinema" required
                                                    class="min-width80 background-transparent">
                                                <option selected hidden value="">${cinema}</option>
                                                <c:forEach items="${sessionScope.cinemasMap}" var="cinema">
                                                    <option value=${cinema.key}>${cinema.value}</option>
                                                </c:forEach>
                                            </select>
                                        </p>
                                    </td>
                                    <td>
                                        <p class="cinema-date">
                                            <select id="date" name="date" required
                                                    class="min-width80 background-transparent">
                                                <option selected hidden value="">${date}</option>
                                                <c:forEach items="${sessionScope.datesList}" var="date">
                                                    <option value=${date}>${date}</option>
                                                </c:forEach>
                                            </select>
                                        </p>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td class="film-title max-width700">
                            <p class="film-title">
                                <select id="title" name="title" required
                                        class="min-width160 max-width600 background-transparent">
                                    <option selected hidden value="">${filmTitle}</option>
                                    <c:forEach items="${sessionScope.titlesMap}" var="title">
                                        <option value=${title.key}>${title.value}</option>
                                    </c:forEach>
                                </select>
                            </p>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <hr>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">
                            <table>
                                <tr>
                                    <td colspan="2" class="middle-center"></td>
                                </tr>
                                <tr>
                                    <td class="middle-left">
                                        <span>${showsTime} ${unitsTime}:</span>
                                    </td>
                                    <td class="middle-left">
                                        <select id="hour" name="hour" required
                                                class="min-width50">
                                            <c:forEach items="${sessionScope.hoursList}" var="hour">
                                                <option value=${hour}>${hour}</option>
                                            </c:forEach>
                                        </select>
                                        <select id="minute" name="minute" required
                                                class="min-width50">
                                            <c:forEach items="${sessionScope.minutesList}" var="minute">
                                                <option value=${minute}>${minute}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="middle-left">
                                        <span>${showsCost}, BYN ${unitsCost}:</span>
                                    </td>
                                    <td class="middle-left">
                                        <select id="ruble" name="ruble" required
                                                class="min-width50">
                                            <c:forEach items="${sessionScope.hundredList}" var="ruble">
                                                <option value=${ruble}>${ruble}</option>
                                            </c:forEach>
                                        </select>
                                        <select id="copeck" name="copeck" required
                                                class="min-width50">
                                            <c:forEach items="${sessionScope.dozenList}" var="copeck">
                                                <option value=${copeck}>${copeck}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="middle-left">
                                        <span>${freePlaces}:</span>
                                    </td>
                                    <td>
                                        <select id="freePlace" name="freePlace" required
                                                class="min-width50">
                                            <c:forEach items="${sessionScope.hundredList}" var="freePlace">
                                                <option value=${freePlace}>${freePlace}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <hr>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <div class="centerOnly">
                    <a class="button" href="${sessionScope.pageToReturn}">${ok}</a>
                    <input id="submit" name="submit" class="button" type="submit" value="${add}">
                    <input id="reset" name="reset" class="button" type="reset" value=${reset}>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>