var $init_submit_btn = function( option ){
    var $btn = option.btn;
    var $table = option.table;
    var $loading = $btn.prev();
    var url = option.url;
    var success = option.success;

    $btn.click(function(){
        /* Validate Input*/
        $('body').click(); //To close the opened edit-able.

        var invalid = $table.find("tr:not(:hidden) .editable-empty").first();
        if( invalid.length > 0 ){
            setTimeout(function() {
               invalid.click();
               invalid.parent().children().find(".editable-submit").click();
            },200);
            return;
        }

        /* Do Action */
        $.ajax2_add({
            url: $webContent+url,
            $form: $table.find('form'),
            $loading: $loading,
            $btn: $btn,
            success: function( data ){
              if( data['error'] ){
                  var msg = data['error'] ;
                  alert(msg);
                  return;
              }

              /* Do success */
              success( data );
            },
            error: function(data){
              alert("Sorry, server error. \n Please refresh and try again!");
            }
        });
    });
};