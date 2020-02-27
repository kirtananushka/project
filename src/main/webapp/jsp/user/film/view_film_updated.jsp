<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="bundle/pagecontent" var="resourceBundle"/>
<fmt:setBundle basename="bundle/err" var="errorBundle"/>

<fmt:message key="info.updated" bundle="${resourceBundle}" var="title"/>
<fmt:message key="form.back" bundle="${resourceBundle}" var="ok"/>
<fmt:message key="nothing.found" bundle="${errorBundle}" var="nothingFound"/>
<fmt:message key="form.edit" bundle="${resourceBundle}" var="edit"/>
<fmt:message key="load.image" bundle="${resourceBundle}" var="loadImage"/>
<fmt:message key="form.load" bundle="${resourceBundle}" var="load"/>


<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/indexstyle.css">
    <script defer src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script defer src="${pageContext.request.contextPath}js/checkUploadImage.js"></script>
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
                    <table class="all-films">
                        <tbody>
                        <tr>
                            <td class="image"
                                rowspan="2"><img src="${sessionScope.img}" alt="${sessionScope.filmObj.title}">
                            </td>
                            <td class="film-title"><p class="film-title">${sessionScope.filmObj.title}</p></td>
                        </tr>
                        <tr>
                            <td>
                                <table class="width100">
                                    <tr>
                                        <td class="width35">
                                            <c:forEach items="${sessionScope.filmObj.genres}" var="genre">${genre}
                                                <br></c:forEach>
                                        </td>
                                        <td class="width35">
                                            <c:forEach items="${sessionScope.filmObj.countries}"
                                                       var="country">${country}<br></c:forEach>
                                        </td>
                                        <td class="width15">${sessionScope.filmObj.year}</td>
                                        <td class="width15">${sessionScope.filmObj.age}+</td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="centerOnly">
                        <a class="button" href="${sessionScope.pageToReturn}">${ok}</a>
                        <c:if test="${sessionScope.userAuthorizated.role eq 'ADMIN' or sessionScope.userAuthorizated.role eq 'MANAGER'}">
                            <a class="button" href="${pageContext.request.contextPath}
                                       /controller?command=edit_film&filmId=${sessionScope.filmObj.id}">${edit}</a>
                        </c:if>
                    </div>
                    <hr>
                    <div class="center">
                        <form action="${pageContext.request.contextPath}/upload" method="post"
                              enctype="multipart/form-data" class="upload-form">
                            <table class="width100">
                                <tr>
                                    <td class="middle-center">
                                        <label>${loadImage}</label>
                                        <input type="file" id="file" name="file"
                                               onchange="checkUploadImage()"
                                               accept="image/jpeg,image/png,image/gif"/><br>
                                        <button type="submit" class="button" id="upload-submit"
                                                disabled>${load}</button>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>