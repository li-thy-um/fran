$(function(){
    $('#product').typeahead({
        source : _product_query,
        updater: _add_product_to_list,
        highlighter: _product_info,
        minLength: 3
    });

    $('tbody').on('click', '.close', _cashier_remove_row);
    $('tbody').on('keyup change', '.amount>input', _cashier_update_table);


    $("#submit_trans").click(function(){
        var $modal = $get_modal("new_transaction");
        $modal.data("data", {
           "total" : "<span id='total_price'>"+__calculate_total( $("#product_list"))+"</span>",
           "change" : "<span id='change_back' style='color:red;'>Not Enough</span>",
           "pay" : "<input type='number' value='0'></input>",
        });

        var tps = $("#product_list").find("tr");
        var inputs = {"size" : tps.length};

        tps.each(function(i,tr){
        	var $tr = $(tr);
        	var p = $tr.data("product");
        	inputs["tp["+i+"].product_id"] = p["product.id"];
            inputs["tp["+i+"].cd"] = p["product.cd"];
            inputs["tp["+i+"].name"] = p["product.name"];
            inputs["tp["+i+"].image"] = p["product.image"];
            inputs["tp["+i+"].price"] = p["product.price"];
            inputs["tp["+i+"].detail"] = p["product.detail"];
            inputs["tp["+i+"].amount"] = $tr.data("amount");
        });
        $modal.data("input", inputs);
        $modal.modal("show");
    });


});

var _cashier_update_table = function(e){
	var $tr = $(this).closest("tr");
	var amount = $tr.find(".amount>input").val();
	if( parseInt(amount) != parseInt(amount) || amount < 0 ) amount = 0;
	_cashier_update_row( $tr, amount);
	_cashier_change_total( $("#product_list"), $("#total"));
};

var _cashier_remove_row = function(e){
	var $tr = $(this).closest("tr");
	_cashier_update_row( $tr, 0 );
	_cashier_change_total( $("#product_list"), $("#total"));
	$tr.remove();
	if($("#product_list").find("tr").length == 0 ){
		_cashier_product_list_("hide");
	}
};

var _cashier_product_list_ = function( act ){
	$("#product_list").prev()[act]();
	$("#submit_trans")[act]();
};

var _add_product_to_list = function( json ){
    var $list = $("#product_list");

    var p = $.parseJSON(json);

    var p_row = $list.find("#"+p["product.id"]);

    if( p_row && p_row.length > 0 ){
        _cashier_update_row( p_row, parseInt(p_row.data("amount")) + 1 );
    }else{
    	_cashier_add_row( $list, p );

    }

    var size = $list.find("tr").length;
    if( size > 0 ) {
    	_cashier_product_list_("show");
    }

    _cashier_change_total( $list, $("#total") );
};


var __calculate_total = function( list ){
    var total = 0.00;
    list.find("tr").each( function( i, row){
        total += parseFloat( $(row).data("total") );
    });
    return $round(total);
};

var _cashier_change_total = function( list, span ){
    span.html("Total: " + __calculate_total(list));
};

var _cashier_update_row = function( row , amount ){
    var price = row.data("price");
    row.data("amount", amount);
    row.data("total", amount * price );
    row.find(".amount>input").val( $round(row.data("amount")) );
    row.find(".total").html( $round(row.data("total")) );
};

var _cashier_add_row = function( $list, p ){
    $list.append( _to_product_item( p ) );
    $list.find("#"+p["product.id"])
         .data("product", p)
         .data("amount", 1)
         .data("price", p["product.price"])
         .data("total", p["product.price"]);
};


var _product_info = function( json ){
    var p = $.parseJSON(json);
    return p["product.name"]+"("+ p["product.cd"]+")";
};

var _product_query = function( query, process ){
    var $loading = $(".loading");
    var $not_found = $(".not-found");
	$.ajax({
        type: "GET",
        url: $webContent+"/product/query?cd="+query,
        dataType: "json",
        beforeSend:function(){
        	$not_found.hide();
            $loading.show();
        },
        complete:function(){
            $loading.hide();
        },
        success: function( data ) {
        	if( data.length == 0 ){
        		$not_found.show();
        		return;
        	}
            process(
            	$.map(data,function(product){
            		return JSON.stringify(product);
            	})
            );
        }
    });
};

var _to_product_item = function( p ){
	var __label = function( amount ){
		return '<input type="number" value="'+amount+'" style="width:50px;"></input>';
	};

	var __delete = function(){
		return '<button type="button" class="close" aria-hidden="true">&times</button>';
	};

	var amount = 1;
	var price = parseFloat(p["product.price"]);
    var item = "<tr id='"+p["product.id"]+"'>";
    item +="<td>" + p["product.name"]+ "</td>";
    item +="<td>" + p["product.cd"]+ "</td>";
    item +="<td>" + p["product.price"]+ "</td>";
    item +="<td class='amount' style='text-align:center;'>";
    item += __label( amount );
    item += "</td>";
    item +="<td class='total' style='text-align:right;'>"+$round(amount * price)+"</td>";
    item +="<td style='text-align:right;'>";
    item += __delete();
    item +="</td>";
    item += "</tr>";
    return item;
};
