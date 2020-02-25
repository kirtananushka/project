<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:message key="project.name" bundle="${resourceBundle}" var="projectName"/>
<fmt:message key="menu.main" bundle="${resourceBundle}" var="menuMain"/>
<fmt:message key="films" bundle="${resourceBundle}" var="films"/>
<fmt:message key="films.active" bundle="${resourceBundle}" var="filmsActive"/>
<fmt:message key="shows.menu" bundle="${resourceBundle}" var="shows"/>
<fmt:message key="title.send.message" bundle="${resourceBundle}" var="sendMessage"/>
<fmt:message key="film.create" bundle="${resourceBundle}" var="addFilm"/>
<fmt:message key="show.create" bundle="${resourceBundle}" var="addShow"/>
<fmt:message key="shows.cinema" bundle="${resourceBundle}" var="cinema"/>
<fmt:message key="cinema.create" bundle="${resourceBundle}" var="addCinema"/>
<fmt:message key="cinema.edit" bundle="${resourceBundle}" var="editCinema"/>
<fmt:message key="cinema.delete" bundle="${resourceBundle}" var="deleteCinema"/>
<fmt:message key="cinema.restore" bundle="${resourceBundle}" var="restoreCinema"/>
<fmt:message key="genre.create" bundle="${resourceBundle}" var="addGenre"/>
<fmt:message key="genre.edit" bundle="${resourceBundle}" var="editGenre"/>
<fmt:message key="clients" bundle="${resourceBundle}" var="clients"/>
<fmt:message key="clients.active" bundle="${resourceBundle}" var="clientsActive"/>

<div class="header">
    <div class="locale">
        <form class="locale" action="${pageContext.request.contextPath}controller" method="post">
            <input type="hidden" name="command" value="change_locale">
            <input class="localeButton duration locale-header" type="submit" value="EN/RU">
        </form>
    </div>
    <p class="title">${projectName}</p>


    <ul class="menu">
        <li><a href="${pageContext.request.contextPath}\">${menuMain}</a></li>
        <%--        <c:choose>--%>
        <%--            <c:when test="${sessionScope.user != null}">--%>
        <%--                <h1>${hi}${sessionScope.user.login}!</h1>--%>
        <%--            </c:when>--%>
        <%--            <c:otherwise>--%>
        <%--                <h1>${title}</h1>--%>
        <%--            </c:otherwise>--%>
        <%--        </c:choose>--%>
        <c:if test="${sessionScope.user.role eq 'ADMIN'}">
            <li><a>Admin</a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}controller?command=create_genre">${addGenre}</a></li>
                </ul>
            </li>
        </c:if>
        <c:if test="${sessionScope.user.role eq 'MANAGER'}">
            <li><a href="${pageContext.request.contextPath}\">Manager</a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}controller?command=create_genre">${addGenre}</a></li>
                </ul>
            </li>
        </c:if>
        <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'MANAGER'}">
            <li><a>${cinema}</a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}
                    controller?command=create_cinema">${addCinema}</a></li>
                    <li><a href="${pageContext.request.contextPath}
                    controller?command=edit_cinema">${editCinema}</a></li>
                    <li><a href="${pageContext.request.contextPath}
                    controller?command=delete_cinema">${deleteCinema}</a></li>
                    <li><a href="${pageContext.request.contextPath}
                    controller?command=restore_cinema">${restoreCinema}</a></li>
                </ul>
            </li>
        </c:if>
        <li><a href="${pageContext.request.contextPath}
        controller?command=films_available&page=1">${films}</a>
            <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'MANAGER'}">
                <ul>
                    <li><a href="${pageContext.request.contextPath}
                    controller?command=create_film">${addFilm}</a></li>
                    <li><a href="${pageContext.request.contextPath}
                    controller?command=films_active&page=1">${filmsActive}</a></li>
                    <li><a href="${pageContext.request.contextPath}
                    controller?command=create_genre">${addGenre}</a></li>
                    <li><a href="${pageContext.request.contextPath}
                    controller?command=edit_genre">${editGenre}</a></li>
                </ul>
            </c:if>
        </li>
        <li><a href="${pageContext.request.contextPath}
        controller?command=shows_available&page=1">${shows}</a>
            <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'MANAGER'}">
                <ul>
                    <li><a href="${pageContext.request.contextPath}
                    controller?command=create_show">${addShow}</a></li>

                </ul>
            </c:if>
        </li>
        <c:if test="${sessionScope.user.role eq 'ADMIN' or sessionScope.user.role eq 'MANAGER'}">
            <li><a href="${pageContext.request.contextPath}
        controller?command=find_active_clients&page=1">${clients}</a>
                <ul>
                    <li><a href="${pageContext.request.contextPath}
                    controller?command=find_active_clients&page=1">${clientsActive}</a></li>
                </ul>
            </li>
        </c:if>
        <li><a href="${pageContext.request.contextPath}send_message">${sendMessage}</a></li>
    </ul>
    <ctg:menu-right/>
</div>
<div class="shadowHeader"></div>
