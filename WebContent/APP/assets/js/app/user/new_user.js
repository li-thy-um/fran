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
        }
    });

    $init_modal({
        type: "input_form",
        module: "new_user",
        url: 'user/create',
        success: function( data ){
        	 $(".refresh-user").click();
             $flash_message("success", "Success." );
        }
    });
});