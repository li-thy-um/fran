$(function(){
	$("#back_shop_inven").click(function(){
		$("#shop_inventory_tag").click();
		$("#shop_inven_list").show();
    	$("#product_inven_list").find("#product_inven_grid").empty();
    	$("#product_inven_list").hide();
	});


    var $grid = $("#product_inven_grid");

    var amount_edit = $("#new_product_inven") .length ? true : false;
    
    var columns = [
       {   "title": "Action",
    	   "name": "inventory.id",
    	   "class": "align-left",
           "visible": false
       },
       {   "title": "Name",
           "name" : "product.name",
           "class": "align-left"
       },
       {   "title": "Bar Code",
           "name" : "product.cd",
           "class": "align-left"
       },
       {   "title"    : "Amount",
           "name"     : "inventory.amount",
           "class"    : "align-center",
           "editable" : amount_edit
       }
    ];
    
    columns.push( $inventory_status( "inventory.status" ) );
    
    $("#new_product_inven").click(function(){
    	$get_modal("new_inventory").modal("show");
    });
    
    $grid.on("click", ".delete", function(e){
    	var id = $(this).closest('tr').attr('id');
		$table_remove_row( $grid, "inventory", id );
    });
    
    var panel = {
    	"dom" : "<span class='panel'>" +
    				"<a title='delete' class='btn btn-link btn-small btn-danger delete'>" +
    				"<i class='icon-trash'></i>" +
    			"</a></span>"
    };

    var select = $("#new_product_inven").length ? {
        "action": $table_single_select,
        "url_update" : "/inventory/updates",
        "model.name" : "inventory",
        "panel" : panel,
        "format" : function( s_days ){
        	return $inventory_format( s_days );
        }
    } : {
    	"action": function(){}
    };
    
    $("#product_inven_list").data("show", function(id){
    	$get_modal("new_inventory").data("input", {
    		"inventory.shop_id" : id
    	});
    	
    	$refresh_table({
    		grid: $grid,
    		url: '/inventory/list?shop_id='+id,
    		columns: columns,
    		set_row : function( $tr ){
    			$format_inventory_status( $tr );
    		},
    		actions:{
    			"select" : select
    		}
    	});
    });
   
});