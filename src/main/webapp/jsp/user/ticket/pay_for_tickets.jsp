<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="tickets.buy" bundle="${resourceBundle}" var="title"/>
<fmt:message key="tickets.number" bundle="${resourceBundle}" var="ticketsNumber"/>
<fmt:message key="form.buy.button" bundle="${resourceBundle}" var="buy"/>
<fmt:message key="form.back" bundle="${resourceBundle}" var="ok"/>
<fmt:message key="nothing.found" bundle="${errorBundle}" var="nothingFound"/>
<fmt:message key="shows.free.places" bundle="${resourceBundle}" var="freePlaces"/>
<fmt:message key="shows.time" bundle="${resourceBundle}" var="showsTime"/>
<fmt:message key="shows.cost" bundle="${resourceBundle}" var="showsCost"/>
<fmt:message key="full.cost" bundle="${resourceBundle}" var="fullCost"/>
<fmt:message key="form.next" bundle="${resourceBundle}" var="next"/>
<fmt:message key="credit.card.number" bundle="${resourceBundle}" var="creditCardNumber"/>
<fmt:message key="pay" bundle="${resourceBundle}" var="pay"/>


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
                <form id="payForm" name="payForm" class="authentication registration"
                      action="${pageContext.request.contextPath}controller"
                      method="post">
                    <input type="hidden" name="command" value="make_payment">
                    <input type="hidden" name="showId" value="${sessionScope.showObj.id}">
                    <input type="hidden" name="ticketsNumber" value="${sessionScope.ticketsNumber}">
                    <input type="hidden" name="cost" value="${sessionScope.cost}">
                    <c:if test="${sessionScope.errCreateOrderMessage != null}">
                        <p class="noteRed">
                            <c:forEach var="errorMessages" items="${sessionScope.errCreateOrderMessage}">
                                <fmt:message key="${errorMessages.key}" bundle="${errorBundle}" var="key"/>
                                ${key}<br>${errorMessages.value}<br>
                            </c:forEach>
                        </p>
                    </c:if>
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
                                    <table class="show-info">
                                        <tr>
                                            <fmt:parseDate value="${sessionScope.showObj.dateTime}"
                                                           pattern="yyyy-MM-dd'T'HH:mm"
                                                           var="parsedTime" type="both"/>
                                            <td>${showsTime}:<br><span>
                                    <fmt:formatDate pattern="HH:mm"
                                                    value="${parsedTime}"/></span></td>
                                            <td>${showsCost}:<br><span>${sessionScope.showObj.cost} BYN</span></td>
                                            <td></td>
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
                        <label id="numberLabel" class="" for="ticketsNumber">${ticketsNumber}:</label>
                        <input id="ticketsNumber" name="ticketsNumber" required disabled
                               class="width15 non-active height40" value="${sessionScope.ticketsNumber}">
                        <label id="costLabel" class="" for="cost">${fullCost}:</label>
                        <input type="text" id="cost" name="cost" required disabled
                               class="width15 non-active height40" value="${sessionScope.cost}" readonly>
                    </div>
                    <hr>
                    <div class="centerOnly">
                        <label id="creditCardLabel" class="width95" for="creditCardNumber">${creditCardNumber}:</label>
                        <br>
                        <input id="creditCardNumber" name="creditCardNumber" required
                               type="text" pattern="\d{16}"
                               placeholder="${creditCardNumber}" class="width35 height40" value="">
                    </div>
                    <hr>
                    <div class="centerOnly">
                        <a class="button"
                           href="${pageContext.request.contextPath}
                     /controller?command=buy_tickets&showId=${sessionScope.showObj.id}">${ok}</a>
                        <input id="submit" name="submit" class="button" type="submit" value=${pay}>
                    </div>
                </form>
            </div>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>