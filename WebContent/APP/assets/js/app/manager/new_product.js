$(function(){
    $.extend( $.editable_define, {
        "product.cd" : {
            "type" : "text",
            "validator" : { "maxLength": 20, "isNumber": true }
        },
        "product.name" : {
            "type" : "text",
            "validator" : { "maxLength": 20 }
        },
        "product.price" : {
            "type" : "text",
            "validator" : { "isMoney": true }
        },
        "product.detail" : {
            "type" : "textarea",
            "validator" : { "required": false, "maxLength":100 }
        }
    });


    $init_modal({
        type: "input_form",
        label_width: "40%",
        module: "new_product",
        url: 'product/create',
        success: function( data ){
            $("#product_basic_tag").click();
            $flash_message("success", "Success." );
        }
    });
});