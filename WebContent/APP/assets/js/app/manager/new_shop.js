$(function(){
    $.extend( $.editable_define, {
        "shop.name" : {
            "type" : "text",
            "validator" : { "maxLength": 20 }
        },
        "shop.address" : {
            "type" : "textarea",
            "validator" : { "maxLength": 100 }
        },
        "shop.keeper_id" : {
        	"type" : "select2",
            "extend" : {
               source: $webContent+"/user/avaliableKeeper",
               select2: {
                   placeholder: 'Select Keeper',
                   width: 250,
                   allowClear: true
               }
            }        
        },
        "shop.detail" : {
            "type" : "textarea",
            "validator" : { "required": false, "maxLength":100 }
        }
    });


    $init_modal({
        type: "input_form",
        label_width: "40%",
        module: "new_shop",
        url: 'shop/create',
        success: function( data ){
            $("#shop_basic_tag").click();
            $flash_message("success", "Success." );
        }
    });
});