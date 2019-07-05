function addToClick(productId, url) {
    $.post({
        url: url,
        dataType: 'json',
        contentType: 'json',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        data: JSON.stringify({productId: productId, quantity: $('#' + productId).val()}),
        beforeSend: function (xhr) {
            let token = $("meta[name='_csrf']").attr("content");
            let header = $("meta[name='_csrf_header']").attr("content");
            xhr.setRequestHeader(header, token);
        },
        success: function (result) {
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