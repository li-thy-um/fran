$(function(){

    var $grid = $("#shop_inven_grid");

    var columns = [
       {   "name": "shop.id",
           "visible": false
       },
       {   "title": "Name",
           "name" : "shop.name",
           "class": "align-left"
       },
       {   "title"    : "Address",
           "name"     : "shop.address",
           "class"    : "align-left",
           "orderable": false
       }
    ];
    
    columns.push( $inventory_status( "shop.inventory" ) );
    
    var _product_inven_list = function( id, action ){
    	$("#shop_inven_list").hide();
    	$("#product_inven_list").show().data("show")(id);
    }
    
    $init_table({
        grid: $grid,
        refresh: $("#shop_inventory_tag"),
        url: '/shop/inventory',
        columns: columns,
        set_row : function( $tr ){
        	$format_inventory_status( $tr );
		},
        actions:{
            "select" : {
                "action": _product_inven_list,
            }
        }
    });
});

