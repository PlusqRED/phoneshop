<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>

<jsp:useBean id="order" class="com.es.core.model.order.Order" scope="request"/>
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
            <c:forEach var="cartItem" varStatus="count" items="${order.orderItems}">
                <jsp:useBean id="cartItem" class="com.es.core.model.order.OrderItem" scope="request"/>
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
                <td colspan="6">$${order.subtotal}</td>
            </tr>
            <tr>
                <td colspan="5">Delivery</td>
                <td colspan="6">$${order.deliveryPrice}</td>
            </tr>
            <tr>
                <td colspan="5">Total</td>
                <td colspan="6">$${order.totalPrice}</td>
            </tr>
        </table>
        <br>
        <p>
            <span>First name: ${order.firstName}</span>
        </p>
        <p>
            <span>Last name: ${order.lastName}</span>

        </p>
        <p>
            <span>Address: ${order.deliveryAddress}</span>

        </p>
        <p>
            <span>Phone: ${order.contactPhoneNo}</span>

        </p>
        <p>
            <span>${order.additionalInformation}</span>
        </p>
        <p>
            <a href="${pageContext.request.contextPath}/productList?back=true" class="btn btn-primary">Back to
                shopping</a>
        </p>
    </div>
</tags:master>
