$(function(){

	var psw = {
        "type" : "password",
        "validator" : { "minLength": 6 }
    };
	
    $.extend( $.editable_define, {
        "old_psw" : psw,
        "psw" : psw,
        "pswc" : psw
    });

    $init_modal({
        type: "input_form",
        module: "change_psw",
        label_width: "40%",
        url: 'user/changePsw',
        success: function( data ){
             $flash_message("success", "Success." );
        }
    });
    
    $('.profile').click( function(){
    	$get_modal('change_psw').modal("show");
    });
});