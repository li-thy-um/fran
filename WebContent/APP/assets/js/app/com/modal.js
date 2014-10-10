var $get_modal = function( module ){
    return $('#'+module+'_modal');
};

var _init_modal_input_form = function( option ){
    var module = option.module;
    var url = option.url;
    var success = option.success;
    var label_width = option.label_width;
    var eidt_mode = option.edit_mode || 'inline';

    var $modal = $get_modal(module);
    var $form = $modal.find('form');
    var $ok_btn = $modal.find('.btn-ok');
    var $cancel = $modal.find('.btn-cancel');
    var $trs = $modal.find("tr:not(.hide)") || [];

    if( label_width ) {
        $trs.find('td:first').attr("width",label_width);
    }

    $modal.on('show', function(){
        _reset_modal_input_form( $modal, option.default_value_url, eidt_mode );
    });

    $init_submit_btn({
       btn: $ok_btn,
       table: $modal.find('table'),
       url: url,
       success: function(data){
           $cancel.click();
           success( data );
       }
    });

    $cancel.click(function(){
        $modal.modal('hide');
    });
};

var _init_modal_table = function( option ){
    var module = option.module;
    var $modal = $get_modal(module);
    var columns = option.columns;

    var table = $modal.find("table");

    $modal.on('show', function(){
        _reset_modal_table( table, columns, $modal );
    });
};

var _reset_modal_table = function( $table, columns, $modal ){
    var data = $modal.data("data");
    var total = data.pop();
    $modal.find(".modal-body").html("<table></table>");
    $table = $modal.find("table");

    $table.dataTable({
        "data": data,
        "order": [],
        "paging": false,
        "columns": columns,
        "searching": false,
        "info": false
    });

    $modal.find(".dataTables_scrollHeadInner").css("width", "100%");
    $modal.find(".dataTables_scrollHeadInner").find("table").css("width", "100%");

    var subtitle = $modal.data("subtitle");
    $modal.find(".modal-body").append("<br/>")
    .append("<div> Total Cash In: "+total["total_in"]+"</div>");
    $modal.find(".modal-header").append("<h6>"+subtitle+"</h6>");
};

var $init_modal = function( option ){
    var module = option.module;
    var $modal = $get_modal(module);

    $modal.modal({
        show:false,
        backdrop: "static"
    });

    if( option.type == "input_form" ){
        _init_modal_input_form(option);
    }
    if( option.type == "table" ){
        _init_modal_table(option);
    }
};

var _reset_modal_input_form = function( $modal, url, mode ){
    $init_input_form ({
        mode: 'inline',
        url: url,
        mode: mode,
        table: $modal.find('table') ,
        inputs: $modal.data("input"),
        labels: $modal.data("data")
    });
};