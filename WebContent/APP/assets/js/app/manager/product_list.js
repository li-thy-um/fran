$(function(){

    $("#new_product").click(function(){
       $get_modal("new_product").modal("show");
    });

    var $grid = $("#product_basic_grid");

    $.extend( $.editable_define, {
        "product.status" : {
            "type" : "select",
            "extend" : {
               source: [
                   {value: 'Y', text: 'Y'},
                   {value: 'N', text: 'N'},
               ]
            }
        },
    });

    var columns = [
       {   "name": "product.id",
           "visible": false
       },
       {   "title": "Bar Code",
           "name" : "product.cd",
           "class": "align-left"
       },
       {   "title"    : "Product",
           "name"     : "product.name",
           "class"    : "align-left",
           "orderable": false,
           "editable" : true
       },
       {   "title"    : "Price",
           "name"     : "product.price",
           "class"    : "align-right",
           "editable" : true
       },
       {   "title"    : "Detail",
           "name"     : "product.detail",
           "class"    : "align-left",
           "orderable": false,
           "editable" : true
       },
       {   "title"    : "On Shelf (Y/N)",
           "name"     : "product.status",
           "class"    : "align-center",
           "orderable": false,
           "editable" : true
       }
    ];

    $init_table({
        grid: $grid,
        refresh: $("#product_basic_tag"),
        url: '/product/list',
        columns: columns,
        actions:{
            "select" : {
                "action": $table_single_select,
                "url_update" : "/product/updates",
                "model.name" : "product"
            }
        }
    });
});

