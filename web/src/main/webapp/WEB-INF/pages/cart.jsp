<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>

<jsp:useBean id="cartItems" class="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Cart">
    <c:url value="/resources/css/main.css" var="stylePath"/>
    <link rel="stylesheet" href="${stylePath}">
    <c:url value="/resources/js/addToCartAjax.js" var="addToCartAjaxUrl"/>
    <script src="${addToCartAjaxUrl}"></script>

    <div class="container-fluid">
        <a href="${pageContext.request.contextPath}/productList?back=true" class="bordered-el"
           style="margin-bottom: 4%">Back to product list</a>
        <c:choose>
            <c:when test="${not empty cartItems}">
                <form action="${pageContext.request.contextPath}/cart/update" method="post">
                    <table class="table table-hover table-bordered" style="margin-top: 1%">
                        <thead>
                        <tr>
                            <td>Brand</td>
                            <td>Model</td>
                            <td>Price</td>
                            <td>Colors</td>
                            <td>Display size</td>
                            <td>Quantity</td>
                            <td>Action</td>
                        </tr>
                        </thead>
                        <c:forEach var="cartItem" items="${cartItems}">
                            <jsp:useBean id="cartItem" class="com.es.core.model.cart.CartItem" scope="request"/>
                            <tr>
                                <td>${cartItem.phone.brand}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/productDetails/${cartItem.phone.id}">
                                            ${cartItem.phone.model}
                                    </a>
                                </td>
                                <td>$${cartItem.phone.price}</td>
                                <td>
                                    <c:if test="${empty cartItem.phone.colors}">
                                        No colors
                                    </c:if>
                                    <c:forEach var="color" items="${cartItem.phone.colors}">
                                        ${color.code}
                                        <hr>
                                    </c:forEach>
                                </td>
                                <td>${cartItem.phone.displaySizeInches}"</td>
                                <td>
                                    <input name="id" type="hidden" value="${cartItem.phone.id}">
                                    <input class="text-input" id="${cartItem.phone.id}" name="quantity"
                                           value="${cartItem.quantity}"
                                           type="text"
                                           style="text-align: right"/>
                                    <br>
                                    <span id="error${cartItem.phone.id}" style="display: none; color: red"></span>
                                </td>
                                <td>
                                    <button type="submit"
                                            formaction="${pageContext.request.contextPath}/cart/delete/${cartItem.phone.id}"
                                            formmethod="post">
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td colspan="2">Total</td>
                            <td colspan="3">$${requestScope['overallPrice']}</td>
                        </tr>
                    </table>
                    <button type="submit">
                        Update
                    </button>
                    <button type="submit">
                        Order
                    </button>
                </form>
            </c:when>
            <c:otherwise>
                <h4 align="center" class="information">
                    Cart is empty!
                </h4>
            </c:otherwise>
        </c:choose>
    </div>
</tags:master>
