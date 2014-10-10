$(function(){
    $.extend( $.editable_define, {
        "inventory.product_id" : {
			"type" : "select2",
			"extend" : {
			   source: $webContent+"/product/avaliableProduct",
			   select2: {
			       placeholder: 'Select Product',
			       width: 250,
			       allowClear: true
			   }
			}        
        },
        "inventory.amount" : {
            "type" : "text",
            "validator" : { "isNumber": "PN", "maxLength":5 }
        }
    });

    $init_modal({
        type: "input_form",
        label_width: "40%",
        module: "new_inventory",
        url: 'inventory/create',
        success: function( data ){
            $("#product_inven_list").data("show")(data["inventory.shop_id"]);
            $flash_message("success", "Success." );
        }
    });
});