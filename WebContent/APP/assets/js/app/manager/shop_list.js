$(function(){

    $("#new_shop").click(function(){
       $get_modal("new_shop").modal("show");
    });

    var $grid = $("#shop_basic_grid");

    var columns = [
       {   "name": "shop.id",
           "visible": false
       },
       {   "title": "Name",
           "name" : "shop.name",
           "class": "align-left",
           "editable" : true
       },
       {   "title"    : "Address",
           "name"     : "shop.address",
           "class"    : "align-left",
           "editable" : true
       },
       {   "title"    : "Keeper",
           "name"     : "shop.keeper_id",
           "class"    : "align-right",
           "orderable": false,
           "editable" : true
       },
       {   "title"    : "Detail",
           "name"     : "shop.detail",
           "class"    : "align-left",
           "orderable": false,
           "editable" : true
       }
    ];

    $grid.on("click", ".delete", function(e){
    	var id = $(this).closest('tr').attr('id');
		$table_remove_row( $grid, "shop", id );
    });
    
    var panel = {
    	"dom" : "<span class='panel'>" +
    				"<a title='delete' class='btn btn-link btn-small btn-danger delete'>" +
    				"<i class='icon-trash'></i>" +
    			"</a></span>"
    };
    
    $init_table({
        grid: $grid,
        refresh: $("#shop_basic_tag"),
        url: '/shop/list',
        columns: columns,
        actions:{
            "select" : {
                "action": $table_single_select,
                "url_update" : "/shop/updates",
                "model.name" : "shop",
                "panel": panel
            }
        }
    });
});

