<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>

<jsp:useBean id="phone" class="com.es.core.model.phone.Phone" scope="request"/>
<tags:master pageTitle="Product details">
    <c:url value="/resources/css/main.css" var="stylePath"/>
    <link rel="stylesheet" href="${stylePath}">
    <c:url value="/resources/js/addToCartAjax.js" var="addToCartAjaxUrl"/>
    <script src="${addToCartAjaxUrl}"></script>

    <div class="container-fluid">
        <jsp:include page="../fragments/minicart.jsp"/>
        <a href="${pageContext.request.contextPath}/productList?back=true" class="btn btn-primary">Back to product
            list</a>


        <div class="modal-body">
            <div class="first-column">
                <h2>${phone.model}</h2>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}"
                     alt="phone_image">
                <p>${phone.description}</p>
                <div style="display: inline-block" class="bordered-el">
                    <h2 style="margin-bottom: 1%"><b>Price:</b> ${phone.price}$</h2>
                    <input class="form-control" style="width: 80%; margin-bottom: 10%" id="${phone.id}" name="quantity"
                           value="1"
                           type="text"/>
                    <button class="btn btn-primary"
                            onclick="addToClick(${phone.id}, '${pageContext.request.contextPath}/ajaxCart')">Add to
                        cart
                    </button>
                    <br>
                    <span id="error${phone.id}" style="display: none; color: red"></span>
                </div>
            </div>
            <div class="second-column">
                <h3>Display</h3>
                <table style="margin-bottom: 5%" class="table table-hover table-bordered">
                    <tr>
                        <th>Size</th>
                        <td>${phone.displaySizeInches}</td>
                    </tr>
                    <tr>
                        <th>Resolution</th>
                        <td>${phone.displayResolution}</td>
                    </tr>
                    <tr>
                        <th>Technology</th>
                        <td>${phone.displayTechnology}</td>
                    </tr>
                    <tr>
                        <th>Pixel density</th>
                        <td>${phone.pixelDensity}</td>
                    </tr>
                </table>
                <h3>Dimensions & weight</h3>
                <table style="margin-bottom: 5%" class="table table-hover table-bordered">
                    <tr>
                        <th>Length</th>
                        <td>${phone.lengthMm}mm</td>
                    </tr>
                    <tr>
                        <th>Width</th>
                        <td>${phone.widthMm}mm</td>
                    </tr>
                    <tr>
                        <th>Color</th>
                        <td>
                            <c:if test="${empty phone.colors}">
                                No colors
                            </c:if>
                            <c:forEach var="color" items="${phone.colors}">
                                ${color.code}
                            </c:forEach>
                        </td>
                    </tr>
                    <tr>
                        <th>Weight</th>
                        <td>${phone.weightGr}</td>
                    </tr>
                </table>
                <h3>Camera</h3>
                <table style="margin-bottom: 5%" class="table table-hover table-bordered">
                    <tr>
                        <th>Front</th>
                        <td>${phone.frontCameraMegapixels} megapixels</td>
                    </tr>
                    <tr>
                        <th>Back</th>
                        <td>${phone.backCameraMegapixels} megapixels</td>
                    </tr>
                </table>
                <h3>Battery</h3>
                <table style="margin-bottom: 5%" class="table table-hover table-bordered">
                    <tr>
                        <th>Talk time</th>
                        <td>${phone.talkTimeHours} hours</td>
                    </tr>
                    <tr>
                        <th>Stand by time</th>
                        <td>${phone.standByTimeHours} hours</td>
                    </tr>
                    <tr>
                        <th>Battery capacity</th>
                        <td>${phone.batteryCapacityMah}mAh</td>
                    </tr>
                </table>
                <h3>Other</h3>
                <table style="margin-bottom: 5%" class="table table-hover table-bordered">
                    <tr>
                        <th>Device type</th>
                        <td>${phone.deviceType}</td>
                    </tr>
                    <tr>
                        <th>Bluetooth</th>
                        <td>${phone.bluetooth}</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</tags:master>
