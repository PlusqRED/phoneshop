function addToClick(productId) {
    $.post({
        url: "ajaxCart",
        data: {productId: productId, quantity: $('#' + productId).val()},
        dataType: "json",
        success: function (result) {
            if (result['valid']) {
                updateMinicart(result);
                $('#error' + productId).css("display", "none");
            } else {
                $('#error' + productId).show();
            }
        }
    });

    function updateMinicart(result) {
        $('#minicart').html('<strong>My cart: </strong>' + result['cartProductQuantity'] + '<strong> items: </strong>' + result['overallPrice'] + '$');
    }
}