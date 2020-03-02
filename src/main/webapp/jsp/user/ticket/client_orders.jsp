<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="tickets.ordered" bundle="${resourceBundle}" var="title"/>
<fmt:message key="tickets.ordered" bundle="${resourceBundle}" var="text"/>
<fmt:message key="nothing.found" bundle="${errorBundle}" var="nothingFound"/>
<fmt:message key="info.order.number" bundle="${resourceBundle}" var="orderNumber"/>
<fmt:message key="info.order.date" bundle="${resourceBundle}" var="orderDate"/>
<fmt:message key="info.order.show.date" bundle="${resourceBundle}" var="dateShow"/>
<fmt:message key="info.order.show.start" bundle="${resourceBundle}" var="startShow"/>
<fmt:message key="info.order.cinema.name" bundle="${resourceBundle}" var="cinemaName"/>
<fmt:message key="info.order.film.title" bundle="${resourceBundle}" var="filmTitle"/>
<fmt:message key="info.order.number.seats" bundle="${resourceBundle}" var="numberSeats"/>
<fmt:message key="info.order.cost.ticket" bundle="${resourceBundle}" var="costTicket"/>
<fmt:message key="info.order.total.cost" bundle="${resourceBundle}" var="totalCost"/>

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
        <c:choose>
            <c:when test="${!empty requestScope.ordersList}">
                <p class="center margin-bottom">${text}</p>
            </c:when>
            <c:otherwise>
                <p class="center margin-bottom">${nothingFound}</p>
            </c:otherwise>
        </c:choose>
        <c:if test="${!empty requestScope.ordersList}">
            <c:import url="/jsp/template/pagination.jsp"/>
        </c:if>
        <c:forEach items="${requestScope.ordersList}" var="order">
            <table class="all-films">
                <tbody>
                <tr>
                    <td colspan="2">
                        <hr>
                    </td>
                </tr>
                <tr>
                    <td class="width50">${orderNumber}</td>
                    <td class="width50">${order.id}</td>
                </tr>
                <tr>
                    <td>${orderDate}</td>
                    <td><fmt:parseDate value="${order.orderingDate}" pattern="yyyy-MM-dd'T'HH:mm"
                                       var="parsedDate" type="both"/>
                        <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDate}"/>
                    </td>
                </tr>
                <tr>
                    <td>${dateShow}</td>
                    <td><fmt:parseDate value="${order.show.dateTime}" pattern="yyyy-MM-dd'T'HH:mm"
                                       var="parsedDate" type="both"/>
                        <fmt:formatDate pattern="dd.MM.yyyy, EEEE" value="${parsedDate}"/>
                    </td>
                </tr>
                <tr>
                    <td>${startShow}</td>
                    <td><fmt:parseDate value="${order.show.dateTime}" pattern="yyyy-MM-dd'T'HH:mm"
                                       var="parsedDate" type="both"/>
                        <fmt:formatDate pattern="HH:mm" value="${parsedDate}"/>
                    </td>
                </tr>
                <tr>
                    <td>${cinemaName}</td>
                    <td>${order.show.cinemaName}</td>
                </tr>
                <tr>
                    <td>${filmTitle}</td>
                    <td>${order.show.film.title}</td>
                </tr>
                <tr>
                    <td>${numberSeats}</td>
                    <td>${order.ticketsNumber}</td>
                </tr>
                <tr>
                    <td>${costTicket}</td>
                    <td>${order.ticketCost} BYN</td>
                </tr>
                <tr>
                    <td>${totalCost}</td>
                    <td>${order.ticketCost * order.ticketsNumber} BYN</td>
                </tr>
                </tbody>
            </table>
        </c:forEach>
    </div>
</div>
</body>
</html>