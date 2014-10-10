$(function(){

    $.extend( $.editable_define, {
        "user.name" : {
            "type" : "text",
            "validator" : { "maxLength": 20 }
        },
        "user.email" : {
            "type" : "text",
            "validator" : {
                "maxLength" : 20,
                "email" : true
            }
        },
        "user.shop_id" : {
        	"type" : "select2",
            "extend" : {
               source: $webContent+"/user/avaliableShop",
               select2: {
                   placeholder: 'Select Shop',
                   width: 250,
                   allowClear: true
               }
            }        
        },
    });

    $init_modal({
        type: "input_form",
        module: "new_cashier",
        url: 'user/create',
        success: function( data ){
        	 $(".refresh-user").click();
             $flash_message("success", "Success." );
        }
    });
});