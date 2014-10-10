$(function(){

    var $grid = $("#product_sales_grid");

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

    $init_table({
        grid: $grid,
        refresh: $("#product_sales_tag"),
        url: '/product/sales',
        columns: columns,
        set_row: function( $tr ){
        	$format_sales( $tr );
        },
        actions:{
            "select" : {
                "action": function(row_id, action){
                	var $modal = $get_modal("product_sale_chart");
                	$modal.data("id", row_id );
                	$modal.modal('show');
                }
            }
        }
    });
});

