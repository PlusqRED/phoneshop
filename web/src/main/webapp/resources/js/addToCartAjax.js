function addToClick(productId) {
    $.post({
        url: "ajaxCart",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        data: JSON.stringify({productId: productId, quantity: $('#' + productId).val()}),
        dataType: "json",
        success: function (result) {
            console.log(result);
            if (result['errorMessage'] === null) {
                updateMinicart(result);
                $('#error' + productId).css("display", "none");
            } else {
                let errorMessage = $('#error' + productId);
                errorMessage.text(result['errorMessage']);
                errorMessage.show();
            }
        }
    });

    function updateMinicart(result) {
        $('#minicart').html('<strong>My cart: </strong>' + result['cartQuantity'] + '<strong> items: </strong>' + result['overallPrice'] + '$');
    }
}