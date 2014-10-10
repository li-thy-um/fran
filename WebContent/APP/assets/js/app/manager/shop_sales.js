$(function(){

    var $grid = $("#shop_sales_grid");

    var columns = [
       {   "name": "shop.id",
           "visible": false
       },
       {   "title": "Name",
           "name" : "shop.name",
           "class": "align-left"
       }
    ].concat( $sales_table_columns("shop") );

    $init_table({
        grid: $grid,
        refresh: $("#shop_sales_tag"),
        url: '/shop/sales',
        columns: columns,
        set_row: function( $tr ){
        	$format_sales( $tr );
        },
        actions:{
            "select" : {
                "action": function(row_id, action){
                	if( $isTotal(row_id) ){ return; }
                	$("#shop_sales_list").hide();
                	$("#shop_sale_detail").show().data("show")(row_id);
                }
            }
        }
    });
});

