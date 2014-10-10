$(function(){

    var $grid = $("#user_sales_grid");

    var columns = [
       {   "name": "user.id",
           "visible": false
       },
       {   "title": "Name",
           "name" : "user.name",
           "class": "align-left"
       }
    ].concat( $sales_table_columns("user") );

    $init_table({
        grid: $grid,
        refresh: $("#user_sales_tag"),
        url: '/user/sales',
        columns: columns,
        set_row: function( $tr ){
        	$format_sales( $tr );
        },
        actions:{
            "select" : {
                "action": function(row_id, action){
                	var $modal = $get_modal("user_sale_chart");
                	$modal.data("id", row_id );
                	$modal.modal('show');
                }
            }
        }
    });
});

