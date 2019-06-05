<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product List">
    <div class="container">
        <h2 align="center" class="title">
            Hello from product list!
        </h2>
        <p>
            Found
                <c:out value="${phones.size()}"/> phones.
        <div align="right" class="search-container">
            <form action="${pageContext.servletContext.contextPath}/productList">
                <input type="text" placeholder="Search.." name="search">
                <button type="submit">Search</button>
            </form>
        </div>
        <c:choose>
            <c:when test="${not empty phones}">
                <table class="table table-hover table-bordered">
                    <thead>
                    <tr>
                        <td>Image</td>
                        <td>Brand</td>
                        <td>Model</td>
                        <td>Price</td>
                        <td>Colors</td>
                    </tr>
                    </thead>
                    <c:forEach var="phone" items="${phones}">
                        <tr>
                            <td>
                                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
                            </td>
                            <td>${phone.brand}</td>
                            <td>${phone.model}</td>
                            <td>$ ${phone.price}</td>
                            <td>
                                <c:forEach var="color" items="${phone.colors}">
                                    ${color.code}
                                </c:forEach>
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
    </div>
</tags:master>
