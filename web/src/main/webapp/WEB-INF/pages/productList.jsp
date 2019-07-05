<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tags:master pageTitle="Product List">
    <c:url value="/resources/css/main.css" var="stylePath"/>
    <link rel="stylesheet" href="${stylePath}">
    <c:url value="/resources/js/addToCartAjax.js" var="addToCartAjaxUrl"/>
    <script src="${addToCartAjaxUrl}"></script>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <div class="container-fluid">
        <jsp:include page="../fragments/minicart.jsp"/>
        <jsp:include page="../fragments/logInOut.jsp"/>
        <p>
            Found
                <c:out value="${requestScope['totalNumberOfPhones']}"/> phones.
        <p>
        <div style="float: left">
            <form action="${pageContext.servletContext.contextPath}/productList?page=1&max=10">
                <input type="text" class="form-control" style="display: inline-block; margin-bottom: 10px"
                       placeholder="Search.." name="search" id="search" value="${param.search}">
                <button type="submit" class="btn btn-primary">Search</button>
            </form>
        </div>
        <c:choose>
            <c:when test="${not empty phones}">
                <table class="table table-hover table-bordered">
                    <thead>
                    <tr>
                        <td>Image</td>
                        <td>
                            <tags:sort column="Brand"/>
                        </td>
                        <td>
                            <tags:sort column="Model"/>
                        </td>
                        <td>
                            <tags:sort column="Price"/>
                        </td>
                        <td>Colors</td>
                        <td>
                            <tags:sort column="Display size"/>
                        </td>
                        <td>Quantity</td>
                        <td>Action</td>
                    </tr>
                    </thead>
                    <c:forEach var="phone" items="${phones}">
                        <tr>
                            <td>
                                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                            </td>
                            <td>${phone.brand}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/productDetails/${phone.id}">
                                        ${phone.model}
                                </a>
                            </td>
                            <td>
                                <fmt:formatNumber value="${phone.price}" type="currency" currencySymbol="$"/>
                            </td>
                            <td>
                                <c:if test="${empty phone.colors}">
                                    No colors
                                </c:if>
                                <c:forEach var="color" items="${phone.colors}">
                                    ${color.code}
                                    <hr>
                                </c:forEach>
                            </td>
                            <td>${phone.displaySizeInches}"</td>
                            <td>
                                <input class="form-control" id="${phone.id}" name="quantity" value="1"
                                       type="text"
                                       style="text-align: right;"/>
                                <br>
                                <span id="error${phone.id}" style="display: none; color: red"
                                      class="alert alert-danger"></span>
                            </td>
                            <td>
                                <button class="btn btn-primary" name="addToCartBtn"
                                        onclick="addToClick(${phone.id}, '${pageContext.request.contextPath}/ajaxCart')">
                                    Add
                                    to cart
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <h4 align="center" class="information">
                    No phones satisfying request!
                </h4>
            </c:otherwise>
        </c:choose>
        <jsp:include page="../fragments/pagination.jsp"/>
    </div>
</tags:master>
