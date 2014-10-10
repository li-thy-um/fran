var $table_remove_row =function( $grid, name, id ){
	if (!confirm("Are you sure?")) return false; 
	
	var $row = $("#"+id).hide();

	$.ajax({
        type: "POST",
		url: $webContent+"/"+name+"/destroy?"+name+".id="+id,
		success: function( model ){
			if( model["error"] ){
				$row().show();
				$flash_message("danger", model['error'] );
			}else{
				$flash_message("success", "Delete success." );
			}
		}
	});
};

var _row_append_panel = function( $row, panel ){
	var $td = $row.find("td:first");
	$td.prepend( panel["dom"] );
};

var _row_remove_panel = function( $row, panel ){
	$row.find(".panel").remove();
}

var $table_single_select = function( id, action ){
    var selected = $("tr.m-selected").first();
    var row_arr = [ $('#'+id) ];

    // un-select the other selected row
    ( selected.length > 0 && selected.attr('id') != id ) && [
    	row_arr.push( selected )
    ];

    $.each( row_arr, function( i, $row ){
    	_table_toggle_row( $row, action );
    });
};

var _table_toggle_row = function ( $row, action ){	
    $row.toggleClass("m-selected");
    var select = $row.hasClass("m-selected");
    
    select || _table_update( $row, action );
    $row.find('form').empty();
        
    var toggle_editable = select ? _table_set_editable : _table_clear_editable;
    $row.find(".m-editable").each( function(i, td){
        var $td = $(td);
        var place = ( i == 0 ? 'right' : 'top' );
        $td.data("place", place);
        toggle_editable( $td );
    });
    
    var panel = action["panel"];
    var toggle_panel = select ? _row_append_panel : _row_remove_panel;
    panel && toggle_panel( $row, panel );
};

var _table_clear_editable = function( $td ){
    var value = $td.find(".editable").html();
    $td.html(value);
};

var _table_set_editable = function( $td ){
    $.fn.editable.defaults.mode = 'inline';
    $.fn.editable.defaults.placement = $td.data("place");

    var value = $td.html();
    var $a = $td.html("<a href='#'>"+value+"</a>").find("a");

    var $form = $td.closest('tr').find('form');
    var name = $td.attr("name");
    
    var onSuccess = function(value){
    	$.add_form_input( $form, name, value );
    };
    
    var define = $get_editable_define( $.editable_define[name], onSuccess );
   
    $a.editable( define );
};

var _table_update = function( $tr, action ){
	if( !action || !action["url_update"] ) return;

    var post_data = $tr.find('form').serialize();
    var id = $tr.attr("id");

    /* Check if need to update */
    if( post_data == "" ) return;

    /* Add id */
    post_data += ( "&"+action["model.name"]+".id=" + id );
    
    /* Request Update */
    $.ajax2_update({
        url: action["url_update"],
        data: post_data,
        success: function( data ){
          if( data['error'] ){
        	  $flash_message( "error", data['error'] );
          }else{
              $flash_message( "success", "Change Updated" );
          }
          _table_row_refresh(id, false, action);
        },
        error: function(data){
          $flash_message( "error", "Sorry, Update Fail, Please Refresh and Try Again." );
        }
    });
};

var $init_table = function( option ){
    var $refresh = option.refresh;
    $refresh.click(function(){
        _refresh_table( option );
    }).click();
};

var $refresh_table = function(option){
	_refresh_table(option);
}

var _table_row_refresh = function( id, show, action ){
    var name = action['model.name'];
	var $tr = $("#"+id);
    $.ajax4_id({
        $loading: $tr.next().find(".m-loading"),
        url: $webContent+"/"+name+"/refresh"+"?"+name+".id="+id,
        success: function(data){
            _table_set_row( $tr, data, show, action["format"]);
        }
    });
};

var _table_set_row = function( $row, data, show, format ){
  var msg = "";
  $row.find("td").each( function( i, td ){
    var $td = $(td);
    var $a = $td.find("a");
    var rplc = data[$td.attr("name")];
    var target = $a.length > 0 ? $a : $td;
    if( target.html() != rplc ){
    	if( format ) rplc = format( rplc ); 
        target.html( rplc );
        if( show ){
            msg += columns[i+1]["title"] + ": ";
            msg += target.html()+" has been updated to "+rplc+"<br/>";
        }
    }
  });
  if(msg) $flash_message("warning", msg);
};

var _refresh_table = function( option ){
    var url = $webContent + option.url;
    var $loading = option.grid.parent().find('.loading').hide();
    var columns = option.columns;

    option.grid.html('<table></table>');

    $.ajax4_grid({
        url: $webContent+url,
        $loading: $loading,
        columns: columns,
        success: function( data ){
            option.data = data;
            _show_table( option );
        },
        empty: function(){
            option.data = [];
            _show_table( option );
        }
    });
};

var _show_table = function( option ){
    var data = option.data;
    var columns = option.columns;
    var set_row = option.set_row;
    var actions = option.actions;
    
    columns[0]["searchable"] = false;

    var $table = option.grid.find('table');
    var fnRowCallback = function(  nRow, aData, iDisplayIndex  ){
        var $tr = $(nRow);
        $tr.attr("id",aData[0]);

        /* Initial Action. */
        $tr.attr("name", "select");
        $tr.addClass("m-action");

        /* Initial Edit-able Columns. */
        $tr.find('td').each( function( i, e ){
          var $e = $(e);
          var define = columns[i+1];
          $e.attr('name', define["name"]);
          define["editable"] ? $e.addClass('m-editable') : null;
        });

        /* Options row set */
    	$tr.css("cursor","pointer");
        set_row && set_row( $tr );

        /* Initial Row Form */
        $tr.append("<form class='hide'></form>");

        return nRow;
    };

    setTimeout(function(){
    	 $table.dataTable({
    	        "data": data,
    	        "order": [],
    	        "scrollX": true,
    	        "scrollY": 400,
    	        "scrollCollapse": false,
    	        "paging": false,
    	        "columns": columns,
    	        "fnRowCallback": fnRowCallback
    	    });
    },10)

    $table.on( 'click', '.m-action', function(e){
        var name = $(this).attr("name");
        var isTr = ( this.nodeName == 'TR' );

        if( isTr && e.target.nodeName != "TD" ) return;

        var $tr = isTr ?
            $(this) : $(this).closest(".popover").prev();

        actions[name]["action"]( $tr.attr("id"), actions[name] );
    });

};