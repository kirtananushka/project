<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="films" bundle="${resourceBundle}" var="title"/>
<fmt:message key="form.edit" bundle="${resourceBundle}" var="edit"/>
<fmt:message key="form.delete" bundle="${resourceBundle}" var="delete"/>
<fmt:message key="film.id" bundle="${resourceBundle}" var="filmId"/>
<fmt:message key="nothing.found" bundle="${errorBundle}" var="nothingFound"/>
<fmt:message key="${requestScope.defaultValue}" bundle="${resourceBundle}" var="defaultValue"/>


<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <title>${title}</title>
</head>
<body>
<c:import url="/jsp/template/header.jsp"/>
<h1>${title}</h1>
<div class="outer">
    <div class="center-padding grey shadow width50 back-white padding-top-40">
        <c:if test="${empty requestScope.filmsList}">
            <p class="center margin-bottom">${nothingFound}</p>
        </c:if>

        <div>
            <form id="searchForm" name="searchForm" class="searchForm"
                  action="${pageContext.request.contextPath}controller">
                <input type="hidden" name="command" value="films_active">
                <input type="hidden" name="select" value="${requestScope.selectParam}">
                <div class="centerOnly">
                    <select id="searchSelect" name="item"
                            class="select min-width160"
                            onchange="submit();">
                        <option selected value="">${defaultValue}</option>
                        <c:forEach items="${requestScope.selectMap}" var="select">
                            <option value=${select.key}>${select.value}</option>
                        </c:forEach>
                    </select>
                </div>
                <input type="hidden" name="page" value="1">
            </form>
        </div>
        <c:if test="${!empty requestScope.filmsList}">
            <c:import url="/jsp/template/pagination.jsp"/>
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
                    <td class="image" rowspan="2"><img src="${film.img}" alt="${film.title}"></td>
                    <td class="film-title"><p class="film-title">${film.title}</p></td>
                </tr>
                <tr>
                    <td>
                        <table class="width100">
                            <tr>
                                <td class="width35">
                                    <c:forEach items="${film.genres}" var="genre">
                                        <a href="${pageContext.request.contextPath}
                                        /controller?command=films_active&select=genre&item=${genre}&page=1">
                                                ${genre}</a><br></c:forEach>
                                </td>
                                <td class="width35">
                                    <c:forEach items="${film.countries}" var="country">
                                        <a href="${pageContext.request.contextPath}
                                        /controller?command=films_active&select=country&item=${country}&page=1">
                                                ${country}</a><br></c:forEach>
                                </td>
                                <td class="width15"><a href="${pageContext.request.contextPath}
                                        /controller?command=films_active&select=year&item=${film.year}&page=1">
                                        ${film.year}</a>
                                </td>
                                <td class="width15"><a href="${pageContext.request.contextPath}
                                        /controller?command=films_active&select=age&item=${film.age}&page=1">
                                        ${film.age}+</a>
                                </td>
                            </tr>
                        </table>
                        <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'MANAGER'}">
                            <table class="width100">
                                <tr class="gray">
                                    <td class="middle-center">${filmId}: ${film.id}</td>
                                    <td class="middle-center">
                                        <a class="button"
                                           href="${pageContext.request.contextPath}
                                       /controller?command=edit_film&filmId=${film.id}">${edit}</a>
                                        <a class="button"
                                           href="${pageContext.request.contextPath}
                                       /controller?command=film_to_delete&filmId=${film.id}">${delete}</a>
                                    </td>
                                </tr>
                            </table>
                        </c:if>
                    </td>
                </tr>
                </tbody>
            </c:forEach>
        </table>

    </div>

</div>
</body>
</html>