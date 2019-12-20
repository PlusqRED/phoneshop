<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tags:master pageTitle="Order page">
    <div class="container-fluid">
        <jsp:include page="../fragments/logInOut.jsp"/>
        <a href="${pageContext.request.contextPath}/cart" class="btn btn-primary">Back to cart</a>
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
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="5">Subtotal</td>
                <td colspan="6"><fmt:formatNumber value="${requestScope['overallPrice']}" type="currency"
                                                  currencySymbol="$"/></td>
            </tr>
            <tr>
                <td colspan="5">Delivery</td>
                <td colspan="6"><fmt:formatNumber value="${requestScope['deliveryPrice']}" type="currency"
                                                  currencySymbol="$"/></td>
            </tr>
            <tr>
                <td colspan="5">Overall wrapping price</td>
                <td colspan="6"><fmt:formatNumber value="${requestScope['overallWrappingPrice']}" type="currency"
                                                  currencySymbol="$"/></td>
            </tr>
            <tr>
                <td colspan="5">Total</td>
                <td colspan="6"><fmt:formatNumber
                        value="${requestScope['overallPrice'] + requestScope['deliveryPrice'] + requestScope['overallWrappingPrice']}"
                        type="currency"
                        currencySymbol="$"/></td>
            </tr>
        </table>
        <br>
        <form action="${pageContext.request.contextPath}/order" method="post">
            <p>
                <label for="firstName">First name*</label>
                <input type="text" class="form-control" style="width: 300px" name="firstName" id="firstName" required>
            </p>
            <p>
                <label for="lastName">Last name*</label>
                <input type="text" class="form-control" style="width: 300px" name="lastName" id="lastName" required>
            </p>
            <p>
                <label for="deliveryAddress">Address*</label>
                <input type="text" class="form-control" style="width: 300px" name="deliveryAddress" id="deliveryAddress"
                       required>
            </p>
            <p>
                <label for="contactPhoneNo">Phone*</label>
                <input type="text" class="form-control" style="width: 300px" name="contactPhoneNo" id="contactPhoneNo"
                       pattern="[\+]\d{3}[\(]*\d{2}[\)]*\d{7}"
                       required>
            </p>
            <p>
                <textarea id="additionalInformation" name="additionalInformation"
                          placeholder="Additional information"></textarea>
            </p>
            <c:if test="${not empty param['err']}">
                <p>
                    <span style="color: red">${param['err']}</span>
                </p>
            </c:if>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <p>
                <button class="btn btn-primary" type="submit">Order</button>
            </p>
        </form>

    </div>
</tags:master>
