<a href="${pageContext.request.contextPath}/cart">
    <div id="minicart" class="minicart bordered-el">
        <strong>My cart:</strong> ${sessionScope['cartProductQuantity']}
        <strong>items:</strong> ${sessionScope['overallPrice']}$
    </div>
</a>
