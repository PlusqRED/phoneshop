<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="order" class="com.es.core.model.order.Order" scope="request"/>
<tags:master pageTitle="Admin order overview page">
    <div class="container-fluid">
        <jsp:include page="../fragments/logInOut.jsp"/>
        <h3>Order number: ${order.id}</h3>
        <h3 style="text-align: right">Order status: ${order.status}</h3>
        <table class="table table-hover table-bordered" style="margin-top: 1%">
            <thead>
            <tr>
                <td>Brand</td>
                <td>Model</td>
                <td>Colors</td>
                <td>Display size</td>
                <td>Wrapping</td>
                <td>Wrapping info</td>
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
                    <td>
                        <c:if test="${cartItem.wrapping}">Yes</c:if>
                        <c:if test="${not cartItem.wrapping}">No</c:if>
                    </td>
                    <td>
                        <c:if test="${cartItem.wrappingAdditional != ''}">${cartItem.wrappingAdditional}</c:if>
                        <c:if test="${cartItem.wrappingAdditional == ''}">No info</c:if>
                    </td>
                    <td>${cartItem.quantity}</td>
                    <td><fmt:formatNumber value="${cartItem.phone.price}" type="currency" currencySymbol="$"/></td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="5">Subtotal</td>
                <td colspan="6"><fmt:formatNumber value="${order.subtotal}" type="currency" currencySymbol="$"/></td>
            </tr>
            <tr>
                <td colspan="5">Delivery</td>
                <td colspan="6"><fmt:formatNumber value="${order.deliveryPrice}" type="currency"
                                                  currencySymbol="$"/></td>
            </tr>
            <tr>
                <td colspan="5">Total</td>
                <td colspan="6"><fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="$"/></td>
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
        <a href="${pageContext.request.contextPath}/admin/orders" class="btn btn-primary">Back</a>
        <form style="display: inline-block;">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" style="display: inline-block;" class="btn btn-primary"
                    formaction="${pageContext.request.contextPath}/admin/orders/${order.id}?status=delivered"
                    formmethod="post">
                Delivered
            </button>

            <button type="submit" class="btn btn-primary"
                    formaction="${pageContext.request.contextPath}/admin/orders/${order.id}?status=rejected"
                    formmethod="post">
                Rejected
            </button>
        </form>
        </p>


    </div>
</tags:master>
