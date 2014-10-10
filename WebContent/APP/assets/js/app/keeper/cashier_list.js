$(function(){
	
	$("#new_cashier").click(function(){
        $get_modal("new_cashier").modal("show");
    });
	
    var $grid = $("#cashier_grid");

    var columns = [
       {   "name": "user.id",
           "visible": false
       },
       {   "title": "Name",
           "name" : "user.name",
           "class": "align-left",
           "editable" : true
       },
       {   "title"    : "E-mail",
           "name"     : "user.email",
           "class"    : "align-left",
           "orderable": false,
           "editable" : true
       },
       {   "title"    : "Shop",
           "name"     : "user.shop_id",
           "class"    : "align-left",
           "orderable": false,
           "editable" : true
       }
    ];

    $grid.on("click", ".delete", function(e){
    	var id = $(this).closest('tr').attr('id');
		$table_remove_row( $grid, "user", id );
    });
    
    var panel = {
    	"dom" : "<span class='panel'>" +
    				"<a title='delete' class='btn btn-link btn-small btn-danger delete'>" +
    				"<i class='icon-trash'></i>" +
    			"</a></span>"
    };
    
    $init_table({
        grid: $grid,
        refresh: $(".refresh-user"),
        url: '/user/list',
        columns: columns,
        actions:{
            "select" : {
                "action": $table_single_select,
                "url_update" : "/user/updates",
                "model.name" : "user",
                "panel" : panel
            }
        }
    });
});