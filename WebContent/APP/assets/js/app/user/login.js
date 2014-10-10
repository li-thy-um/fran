$(function(){

    $.extend( $.editable_define, {
        "user.email" : {
            "type" : "text",
            "validator" : {
                "maxLength" : 20,
                "email" : true
            }
        },
        "user.psw" : {
            "type" : "password",
            "validator" : { "minLength": 6 }
        }
    });

    var $table = $('table.login');

    $init_input_form({
        mode: 'inline',
        table: $table
    });

    $init_submit_btn({
        btn: $("div.login .btn-ok"),
        table: $table,
        url: '/login/create',
        success: function( data ){
            var msg = "Welcome ";
            msg += data["user.name"];
            alert( msg );
            window.location.reload();
        }
    });

});