$(function(){
	$.extend( $.editable_define, {
        "total.pay" : {
            "type" : "text",
            "validator" : {"isMoney": true },
            "extend": {
                success: function(response, newValue){
                    var total = parseFloat( $("#total_price").html() );
                    var change_back = $round( parseFloat(newValue) - total );
                    $("#change_back").html( change_back < 0 ? "Not Enough" : change_back );
                    if( change_back >= 0) {
                    	$("#change_back").css("color","green");
                    }
                }
            }
        }
    });

    $init_modal({
        type: "input_form",
        module: "new_transaction",
        url: "/transaction/create",
        success: function( data ){
            $flash_message("success", "Tranaction Success.");
            $("#product_list").find("tr .close").click();
        }
    });
});