//DEV OR PRODUCT
var $webContent = "";

$.editable_define = {};

$(function(){
    $.fn.editableform.buttons = $("#editabel_buttons").html();
    $(".log-out").click(_logout);
});

var $round = function( value ){
	if ( value instanceof String ){ value = parseFloat(value); }
	return Math.round(value * Math.pow(10, 2)) / Math.pow(10, 2);
};

var _logout = function(){
    $.ajax({
        type: "POST",
        url: $webContent+"/login/destroy",
        success: function(data) {
            window.location.reload();
        }
    });
};

var $flash_message = function( type, msg ){
    $("#msg").append(
        '<div class="hide alert alert-'+type+'">'
        +'<strong>'+msg+'<strong></div>');
    var $flash = $("#msg").find("div:last");
    $flash.slideDown();
    setTimeout(function() {
        $flash.slideUp({
            complete: function(){
                $flash.remove();
            }
        });
    },5000);
};

var $get_editable_define = function( define, after_success){
    var format = define['format'];
    var common = {
        type: define['type'],
        validate: function(value) {
        	var validator = $.extend({ "required": true }, define['validator']);
            return $.validate_field( value, validator );
        },
        success: function( response, newValue ) {
          var value = format ? format( newValue ) : newValue;
          after_success( value );
        }
    };
    return $.extend(common, define['extend']);
};

$.add_form_input = function( $form, name, val ){
    var value = $.trim(val);
    var $input = $form.find("[name='"+ name +"']");
    if( $input.length ){
        $input.val(value);
    }else{
        var input = '<input name="'+name+'" value ="'+value+'"></input>';
        $form.append(input);
    }
};

$.validate_field = function( value, option ){
    value = $.trim(value);
    var vali_msg = [];
    $.each(option, function( name, v ){
        var validator = _validator_define[name];
        if( validator.validate( value, v ) == false ){
            vali_msg.push( validator.msg( value, v ) );
        }
    });
    var msgs = "";
    for( i in vali_msg ){
        msgs += ( vali_msg[i] + '\n' );
    }
    if( vali_msg.length > 0 ) return msgs;
};

var _validator_define = {
    "email" : {
        validate : function( value ){
            return value.match(/^[a-z]([a-z0-9]*[-_]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\.][a-z]{2,3}([\.][a-z]{2})?$/i) != null;
        },
        msg : function( value ){
            return 'This field should be email.';
        }
    },
   "required" : {
       validate : function( value ){
           return value  != '';
       },
       msg : function( value ){
           return 'This field is required.';
       }
   },
   "isNumber" : {
       validate : function( value, v ){
    	   if( v == "PN") return value.match(/^-?[0-9]+$/) != null;
           return value.match(/^[0-9]+$/) != null;
       },
       msg : function( value, v ){
    	   if( v == "PN") return 'This field must be number. (With out point(.) )';
           return 'This field must be number. (With out +,-, or point(.) )';
       }
   },
   "min" : {
       validate : function( value, min ){
           return parseFloat(value) >= parseFloat(min);
       },
       msg : function( value, min ){
           return 'This field must > or = '+min;
       }
   },
   "maxLength" : {
       validate : function( value, max ){
           return value.length <= max;
       },
       msg : function( value, max ){
           return 'The input is too long. (Max length:'+max+')';
       }
   },
   "minLength" : {
       validate : function( value, min ){
           return value.length >= min;
       },
       msg : function( value, min ){
           return 'The input is too short. (Min length:'+min+')';
       }
   },
   "isMoney" : {
       validate : function( value ){
           return value.match(/^\d{1,10}(\.\d{1,2})?$/) != null;
       },
       msg : function( value ){
           return 'This field must be money. ( 2 scale )';
       }
   },
   "before" : {
       validate : function( value, getBefore ){
           var before = getBefore();
           if( before == "Empty" ) return true;
           var va = moment(parseInt(value)).format("YYYY-MM-DD");
           return va <= before;
       },
       msg : function( value, getBefore ){
           return 'The Date Must Before '+getBefore();
       }
   },
   "after" : {
       validate : function( value, getAfter ){
           var after = getAfter();
           if( after == "Empty" ) return true;
           var va = moment(parseInt(value)).format("YYYY-MM-DD");
           return va >= after;
       },
       msg : function( value, getAfter ){
           return 'The Date Must After '+getAfter();
       }
   },
};

$.ajax2_add = function( option ){
    var data = option.$form.serialize();
    var $btn = option.$btn;
    $.ajax({
        type: "POST",
        url: option.url,
        data: data,
        beforeSend:function(){
           $btn.hide();
           option.$loading.show();
        },
        complete:function(){
           option.$loading.hide();
           $btn.show();
        },
        error: function(data) {
            option.error(data);
        },
        success: function(data) {
            option.success( data );
        }
    });
};

$.ajax2_update = function( option ){
    $.ajax({
        type: "POST",
        url: option.url,
        data: option.data,
        error: function(data) {
            option.error(data);
        },
        success: function(data) {
            option.success( data );
        }
    });
};

$.ajax4_id = function( option ){
    $.ajax({
        type: "GET",
        url: option.url,
        dataType: "json",
        beforeSend:function(){
           if( option.$loading ) option.$loading.show();
        },
        complete:function(){
           if( option.$loading ) option.$loading.hide();
        },
        success: function( data ) {
           option.success( data );
        }
    });
};

$.ajax4_grid = function( option ) {
    $.ajax({
        type: "GET",
        url: option.url,
        dataType: "json",
        beforeSend:function(){
            option.$loading.show();
        },
        complete:function(){
            option.$loading.hide();
        },
        success: function( data ) {
            if( data.length > 0 ){
                var dataResult = [];
                var columns = option.columns;
                for( var i = 0; i < data.length; i++ ){
                    var row = [];
                    for ( var j = 0; j < columns.length; j++){
                        row.push( data[i][columns[j]["name"]] );
                    }
                    dataResult.push( row );
                }
                option.success( dataResult );
            }else{
                option.empty();
            }
        }
    });
 };