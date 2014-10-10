$(function(){
	var $container = $("#shop_sale_detail");
	
	$("#shop_sale_chart").click(function(){
		var shop_id = $container.data("id")
		$get_modal("shop_sale_chart").data("id", shop_id).modal("show");
	});
	
	$("#back_shop_sales").click(function(){
		$("#shop_sales_list").show();
		$("#shop_product_sales_grid").empty();
		$("#shop_sale_detail").hide();
	});
	
	$container.data("show", function( shop_id ){
		$container.data( "id", shop_id );
		
		var $grid = $("#shop_product_sales_grid");

	    var columns = [
	       {   "name": "product.id",
	           "visible": false
	       },
	       {   "title": "Code",
	           "name" : "product.cd",
	           "class": "align-left"
	       },
	       {   "title"    : "Product",
	           "name"     : "product.name",
	           "class"    : "align-left",
	           "orderable": false,
	       }
	    ].concat( $sales_table_columns("product") ); 
		
		$refresh_table({
    		grid: $grid,
    		url: '/product/sales?shop_id='+shop_id,
    		columns: columns,
    		set_row : function( $tr ){
    			$format_sales( $tr );
    		},
    		actions:{
    			"select" : {
                    "action": function(row_id, action){
                    	$get_modal("shop_product_sale_chart")
                    	.data("id", row_id )
                    	.data("shop_id", shop_id)
                    	.modal('show');
                    }
                }
    		}
    	});
	});
});