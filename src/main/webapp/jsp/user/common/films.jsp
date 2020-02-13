<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="films" bundle="${resourceBundle}" var="title"/>
<fmt:message key="films.available.text" bundle="${resourceBundle}" var="text"/>
<fmt:message key="nothing.found" bundle="${errorBundle}" var="nothingFound"/>

<fmt:message key="${requestScope.defaultValue}" bundle="${resourceBundle}" var="defaultValue"/>
<fmt:message key="film.country" bundle="${resourceBundle}" var="filmCountry"/>
<fmt:message key="film.age" bundle="${resourceBundle}" var="filmAge"/>
<fmt:message key="film.year" bundle="${resourceBundle}" var="filmYear"/>
<fmt:message key="form.button.search" bundle="${resourceBundle}" var="butSearch"/>
<fmt:message key="form.button.reset" bundle="${resourceBundle}" var="butReset"/>


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
            <c:when test="${!empty requestScope.filmsList}">
                <p class="center margin-bottom">${text}</p>
            </c:when>
            <c:otherwise>
                <p class="center margin-bottom">${nothingFound}</p>
            </c:otherwise>
        </c:choose>

        <div>
            <form id="searchForm" name="searchForm" class="searchForm"
                  action="${pageContext.request.contextPath}controller">
                <input type="hidden" name="command" value="films_available">
                <input type="hidden" name="select" value="${requestScope.selectParam}">
                <div class="centerOnly">
                    <select id="searchSelect" name="item"
                            class="select min-width160"
                            onchange="submit();">
                        <option selected value="">${defaultValue}</option>
                        <c:forEach items="${requestScope.selectList}" var="select">
                            <option value=${select}>${select}</option>
                        </c:forEach>
                    </select>
                </div>
                <input type="hidden" name="page" value="1">
            </form>
        </div>
        <c:if test="${!empty requestScope.filmsList}">
            <c:import url="/jsp/template/films_pagination.jsp"/>
        </c:if>
        <table class="all-films">
            <c:forEach items="${requestScope.filmsList}" var="film">
                <tbody>
                <tr>
                    <td colspan="2">
                        <hr>
                    </td>
                </tr>
                <tr>
                    <td class="image" rowspan="2"><img src="${film.img}" alt=${film.title}></td>
                    <td class="film-title"><p class="film-title">${film.title}</p></td>
                </tr>
                <tr>
                    <td>
                        <table width="100%">
                            <tr>
                                <td width="35%">
                                    <c:forEach items="${film.genres}" var="genre">
                                        <a href="${pageContext.request.contextPath}
                                        /controller?command=films_available&select=genre&item=${genre}&page=1">
                                                ${genre}</a><br></c:forEach>
                                </td>
                                <td width="35%">
                                    <c:forEach items="${film.countries}" var="country">
                                        <a href="${pageContext.request.contextPath}
                                        /controller?command=films_available&select=country&item=${country}&page=1">
                                                ${country}</a><br></c:forEach>
                                </td>
                                <td width="15%"><a href="${pageContext.request.contextPath}
                                        /controller?command=films_available&select=year&item=${film.year}&page=1">
                                        ${film.year}</a>
                                </td>
                                <td width="15%"><a href="${pageContext.request.contextPath}
                                        /controller?command=films_available&select=age&item=${film.age}&page=1">
                                        ${film.age}+</a>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                </tbody>
            </c:forEach>
        </table>

    </div>

</div>
</body>
</html>