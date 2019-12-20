<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav aria-label="...">
    <c:if test="${not empty requestScope['controller']}">
        <ul class="pagination">
            <li class="page-item">
                <a class="page-link" href="${pageContext.request.contextPath}/productList?pagAct=back" tabindex="-1">Previous</a>
            </li>
            <c:forEach var="page" items="${requestScope['controller']}">
                <li class="page-item <c:if test="${requestScope['currentPageIndex'] == page}">active</c:if>">
                    <a class="page-link" href="${pageContext.request.contextPath}/productList?page=${page}">${page}</a>
                </li>
            </c:forEach>
            <li class="page-item">
                <a class="page-link" href="${pageContext.request.contextPath}/productList?pagAct=forward">Next</a>
            </li>
        </ul>
    </c:if>
</nav>