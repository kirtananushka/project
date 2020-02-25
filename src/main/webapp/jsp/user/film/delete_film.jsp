<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="film.delete" bundle="${resourceBundle}" var="title"/>
<fmt:message key="form.back" bundle="${resourceBundle}" var="ok"/>
<fmt:message key="form.delete" bundle="${resourceBundle}" var="delete"/>
<fmt:message key="nothing.found" bundle="${errorBundle}" var="nothingFound"/>

<%--<fmt:message key="film.title" bundle="${resourceBundle}" var="filmTitle"/>--%>
<%--<fmt:message key="form.update" bundle="${resourceBundle}" var="update"/>--%>
<%--<fmt:message key="form.button.reset" bundle="${resourceBundle}" var="reset"/>--%>
<%--<fmt:message key="film.genre" bundle="${resourceBundle}" var="filmGenre"/>--%>
<%--<fmt:message key="film.country" bundle="${resourceBundle}" var="filmCountry"/>--%>
<%--<fmt:message key="film.age" bundle="${resourceBundle}" var="filmAge"/>--%>
<%--<fmt:message key="film.year" bundle="${resourceBundle}" var="filmYear"/>--%>


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
        <c:when test="${empty sessionScope.filmObj}">
            <div class="center-padding grey shadow width50 back-white">
                <p class="center margin-bottom">${nothingFound}</p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="center-padding grey shadow width50 back-white">
                <div class="padding-top-40">
                    <form id="deleteFilmForm" name="deleteFilmForm"
                          action="${pageContext.request.contextPath}controller"
                          method="post">
                        <input type="hidden" name="command" value="delete_film">
                        <input type="hidden" name="img" value=${sessionScope.filmObj.img}>
                        <input type="hidden" name="filmId" value="${sessionScope.filmObj.id}">
                        <p class="noteRed">
                            <c:if test="${sessionScope.errDeleteFilmMessage != null}">
                                <c:forEach var="errorMessages" items="${sessionScope.errDeleteFilmMessage}">
                                    <fmt:message key="${errorMessages}" bundle="${errorBundle}"/><br>
                                </c:forEach>
                            </c:if>
                        </p>
                        <table class="all-films">
                            <tbody>
                            <tr>
                                <td class="image" rowspan="2"><img src="${sessionScope.filmObj.img}"
                                                                   alt="${sessionScope.filmObj.title}"></td>
                                <td class="film-title"><p class="film-title">${sessionScope.filmObj.title}</p></td>
                            </tr>
                            <tr>
                                <td>
                                    <table class="width100">
                                        <tr>
                                            <td class="width35">
                                                <c:forEach items="${sessionScope.filmObj.genres}" var="genre">
                                                    ${genre}<br></c:forEach>
                                            </td>
                                            <td class="width35">
                                                <c:forEach items="${sessionScope.filmObj.countries}" var="country">
                                                    ${country}<br></c:forEach>
                                            </td>
                                            <td class="width15">
                                                    ${sessionScope.filmObj.year}
                                            </td>
                                            <td class="width15">
                                                    ${sessionScope.filmObj.age}+
                                            </td>
                                        </tr>
                                    </table>
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