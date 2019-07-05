<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="orders" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="localDateTimeFormat" class="java.text.SimpleDateFormat" scope="request"/>
<tags:master pageTitle="Order page">
    <div class="container-fluid">
        <jsp:include page="../fragments/logInOut.jsp"/>
        <c:choose>
            <c:when test="${not empty orders}">
                <table class="table table-hover table-bordered" style="margin-top: 1%">
                    <thead>
                    <tr>
                        <td>Order number</td>
                        <td>Customer</td>
                        <td>Phone</td>
                        <td>Address</td>
                        <td>Date</td>
                        <td>Total price</td>
                        <td>Status</td>
                    </tr>
                    </thead>
                    <c:forEach var="order" varStatus="count" items="${orders}">
                        <tr>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/orders/${order.id}">${order.id}</a>
                            </td>
                            <td>${order.firstName} ${order.lastName}</td>
                            <td>${order.contactPhoneNo}</td>
                            <td>${order.deliveryAddress}</td>
                            <td>${localDateTimeFormat.parse(order.date)}</td>
                            <td><fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="$"/></td>
                            <td>${order.status}</td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <h2 style="text-align: center">
                    No orders!
                </h2>
            </c:otherwise>
        </c:choose>

    </div>
</tags:master>
