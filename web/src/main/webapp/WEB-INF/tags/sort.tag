<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="column" required="true" %>

<div>
    <b>${column}</b>
    <a href="${pageContext.request.contextPath}/productList?sort=asc&column=${column}">&#9650;</a>
    <a href="${pageContext.request.contextPath}/productList?sort=desc&column=${column}">&#9660;</a>
</div>

