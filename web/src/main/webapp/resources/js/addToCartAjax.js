function addToClick(productId, url) {
    let quantity = $('#' + productId).val();
    let data;
    if(typeof(wrapping) != "undefined") {
        data = JSON.stringify({
            productId: productId,
            quantity: quantity,
            wrapping: wrapping.checked,
            wrappingAdditional: wrappingAdditional.value})
    } else {
        data = JSON.stringify({
            productId: productId,
            quantity: quantity,
            wrapping: false,
            wrappingAdditional: ''})
    }
    $.post({
        url: url,
        dataType: 'json',
        contentType: 'json',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        data: data,
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
