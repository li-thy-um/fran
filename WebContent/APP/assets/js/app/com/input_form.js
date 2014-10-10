var $init_input_form = function( option ){
    var $table = option.table;
    var $form = $table.find('form');
    var inputs = option.inputs;
    var labels = option.labels;
    var url = option.url;

    option.mode && ( $.fn.editable.defaults.mode = option.mode );

    /* Reset Label */
    $table.find('.mlabel').each(function(i, td){
        var $td = $(td);
        var name = $td.attr("name");
        $td.html( labels[name] );
    });

    /* Initial Fields */
    $fields = $table.find('tr:not(.hide) .mfield').children();
    if( url ){
        $.ajax4_id({
            url: $webContent+url,
            success: function( data ){
                _initial_fields( $fields, $form, data );
            }
        });
    }else{
        _initial_fields($fields, $form, {});
    }

    /* Add Default Input */
    inputs && $.each(inputs, function(name, value){
            $.add_form_input($form, name, value);
    });
};

var _initial_fields = function( $fields, $form, values ){
    $fields.each(function(i, field){
        var $field = $(field);

        /* Reset TD */
        var $td = $field.parent();
        var name = $field.attr("name");
        var default_value = values[name] || "";
        var _a_ = "<a href='#' name='"+name+"'>"+default_value+"</a>";
        $td.html( _a_ );
        $field = $td.find("a");

        /* Set Edit-able */
        var define = $get_editable_define( $.editable_define[name], function( value ){
            $.add_form_input( $form, name, value );
            _activate_next_field( $field );
        });
        $field.editable( define );

        /* Activate the first field */
        if(i==0){
            setTimeout(function(){
                $field.click();
            }, 200);
        }
    });
};

var _activate_next_field = function( $field ){
    var $next = $field.closest("tr").next().find(".mfield>a");
    $next = $($next);
    if( $next.hasClass("editable-empty") ){
        setTimeout( function(){$next.click();}, 200);
    }
};