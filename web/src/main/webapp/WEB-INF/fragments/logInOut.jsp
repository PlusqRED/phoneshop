<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${not empty pageContext.request.userPrincipal.name}">
        <form id="logoutForm" method="POST" action="${pageContext.request.contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>${pageContext.request.userPrincipal.name} <a class="btn btn-primary"
                                                         onclick="document.forms['logoutForm'].submit()">Logout</a>
            <c:if test="${pageContext.request.isUserInRole('ROLE_ADMIN')}">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/orders">Admin orders</a>
            </c:if>
        </h2>

    </c:when>
    <c:otherwise>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/login">Login</a>
    </c:otherwise>
</c:choose>
