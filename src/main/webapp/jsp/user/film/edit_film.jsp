<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="film.edit" bundle="${resourceBundle}" var="title"/>
<fmt:message key="film.title" bundle="${resourceBundle}" var="filmTitle"/>
<fmt:message key="form.back" bundle="${resourceBundle}" var="ok"/>
<fmt:message key="form.update" bundle="${resourceBundle}" var="update"/>
<fmt:message key="nothing.found" bundle="${errorBundle}" var="nothingFound"/>
<fmt:message key="form.button.reset" bundle="${resourceBundle}" var="reset"/>
<fmt:message key="film.genre" bundle="${resourceBundle}" var="filmGenre"/>
<fmt:message key="film.country" bundle="${resourceBundle}" var="filmCountry"/>
<fmt:message key="film.age" bundle="${resourceBundle}" var="filmAge"/>
<fmt:message key="film.year" bundle="${resourceBundle}" var="filmYear"/>


<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/indexstyle.css">
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
                    <form id="editFilmForm" name="editFilmForm"
                          action="${pageContext.request.contextPath}controller"
                          method="post">
                        <input type="hidden" name="command" value="update_film">
                        <input type="hidden" name="img" value=${sessionScope.img}>
                        <input type="hidden" name="filmId" value="${sessionScope.filmObj.id}">
                        <p class="noteRed">
                            <c:if test="${sessionScope.errUpdateFilmMessage != null}">
                                <c:forEach var="errorMessages" items="${sessionScope.errUpdateFilmMessage}">
                                    <fmt:message key="${errorMessages.key}" bundle="${errorBundle}" var="key"/>
                                    ${key}<br>${errorMessages.value}<br>
                                </c:forEach>
                            </c:if>
                        </p>
                        <table class="all-films">
                            <tbody>
                            <tr>
                                <td class="image" rowspan="2"><img src="${sessionScope.img}"
                                                                   alt="${sessionScope.filmObj.title}"></td>
                                <td>
                                    <input name="title" id="title" type="text"
                                           value="${sessionScope.filmObj.title}"
                                           pattern=".{1,255}" placeholder="${filmTitle}"
                                           required class="film-title width95">
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table class="width100">
                                        <tr>
                                            <td class="middle-center">${filmGenre}</td>
                                            <td class="middle-center">${filmCountry}</td>
                                            <td class="middle-center">${filmYear}</td>
                                            <td class="middle-center">${filmAge}</td>
                                        </tr>

                                        <tr>
                                            <td class="width35">
                                                <select id="genres" name="genres[]" size="10" multiple
                                                        class="select min-width160" required>
                                                    <c:forEach items="${sessionScope.selectedGenresMap}" var="item">
                                                        <option selected value=${item.key}>${item.value}</option>
                                                    </c:forEach>
                                                    <c:forEach items="${sessionScope.unselectedGenresMap}" var="item">
                                                        <option value=${item.key}>${item.value}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td class="width35">
                                                <select id="countries" name="countries[]" size="10" multiple
                                                        class="select min-width160" required>
                                                    <c:forEach items="${sessionScope.selectedCountriesMap}" var="item">
                                                        <option selected value=${item.key}>${item.value}</option>
                                                    </c:forEach>
                                                    <c:forEach items="${sessionScope.unselectedCountriesMap}"
                                                               var="item">
                                                        <option value=${item.key}>${item.value}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td class="width15"><select id="year" name="year"
                                                                        class="min-width80" required>
                                                <option selected hidden value=${sessionScope.filmObj.year}>
                                                        ${sessionScope.filmObj.year}</option>
                                                <c:forEach items="${sessionScope.yearsList}" var="year">
                                                    <option value=${year}>${year}</option>
                                                </c:forEach>
                                            </select></td>
                                            <td><select id="age" name="age"
                                                        class="min-width80" required>
                                                <option selected hidden value=${sessionScope.filmObj.age}>
                                                        ${sessionScope.filmObj.age}</option>
                                                <c:forEach items="${sessionScope.agesList}" var="age">
                                                    <option value=${age}>${age}</option>
                                                </c:forEach>
                                            </select></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <div class="centerOnly">
                            <a class="button" href="${sessionScope.pageToReturn}">${ok}</a>
                            <input id="submit" name="submit" class="button" type="submit" value="${update}">
                            <input id="reset" name="reset" class="button" type="reset" value=${reset}>
                        </div>
                    </form>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>