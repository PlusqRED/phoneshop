<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>


<tags:master pageTitle="Product List">

    <c:url value="/resources/js/addToCartAjax.js" var="addToCartAjaxUrl"/>
    <script src="${addToCartAjaxUrl}"></script>

    <div class="container-fluid">
        <jsp:include page="../fragments/minicart.jsp"/>
        <p>
            Found
                <c:out value="${requestScope['totalNumberOfPhones']}"/> phones.
        <div align="right" class="search-container">
            <form action="${pageContext.servletContext.contextPath}/productList?page=1&max=10">
                <input type="text" placeholder="Search.." name="search" id="search" value="${param.search}">
                <button type="submit">Search</button>
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
                            <td>${phone.model}</td>
                            <td>$${phone.price}</td>
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
                                <input class="text-input" id="${phone.id}" name="quantity" value="1"
                                       type="text"
                                       style="text-align: right"/>
                                <br>
                                <span id="error${phone.id}" style="display: none; color: red">Quantity error!</span>
                            </td>
                            <td>
                                <button class="addToCartBtn" name="addToCartBtn" onclick="addToClick(${phone.id})">Add
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
