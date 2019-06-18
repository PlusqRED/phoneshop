<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>

<tags:master pageTitle="Order page">
    <div class="container-fluid">
        <a href="${pageContext.request.contextPath}/cart" class="btn btn-primary">Back to cart</a>
        <table class="table table-hover table-bordered" style="margin-top: 1%">
            <thead>
            <tr>
                <td>Brand</td>
                <td>Model</td>
                <td>Colors</td>
                <td>Display size</td>
                <td>Quantity</td>
                <td>Price</td>
            </tr>
            </thead>
            <c:forEach var="cartItem" varStatus="count" items="${cartItems}">
                <jsp:useBean id="cartItem" class="com.es.core.model.cart.CartItem" scope="request"/>
                <tr>
                    <td>${cartItem.phone.brand}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/productDetails/${cartItem.phone.id}">
                                ${cartItem.phone.model}
                        </a>
                    </td>
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
                    <td>${cartItem.quantity}</td>
                    <td>$${cartItem.phone.price}</td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="5">Subtotal</td>
                <td colspan="6">$${requestScope['overallPrice']}</td>
            </tr>
            <tr>
                <td colspan="5">Delivery</td>
                <td colspan="6">$${requestScope['deliveryPrice']}</td>
            </tr>
            <tr>
                <td colspan="5">Total</td>
                <td colspan="6">$${requestScope['overallPrice'] + requestScope['deliveryPrice']}</td>
            </tr>
        </table>
        <br>
        <form action="${pageContext.request.contextPath}/order" method="post">
            <p>
                <label for="firstName">First name*</label>
                <input type="text" name="firstName" id="firstName" required>
            </p>
            <p>
                <label for="lastName">Last name*</label>
                <input type="text" name="lastName" id="lastName" required>
            </p>
            <p>
                <label for="deliveryAddress">Address*</label>
                <input type="text" name="deliveryAddress" id="deliveryAddress" required>
            </p>
            <p>
                <label for="contactPhoneNo">Phone*</label>
                <input type="text" name="contactPhoneNo" id="contactPhoneNo" pattern="[\+]\d{3}[\(]*\d{2}[\)]*\d{7}"
                       required>
            </p>
            <p>
                <textarea id="additionalInformation" name="additionalInformation"
                          placeholder="Additional information"></textarea>
            </p>
            <p>
                <button class="btn btn-primary" type="submit">Order</button>
            </p>
        </form>

    </div>
</tags:master>
