var _refresh_chart = function( $modal ){
	$modal.find(".chart-area").empty();
	$modal.find(".chart-area").prev().show();
	
	var id = $modal.data("id");
	var render = $modal.data("render");
	var name = $modal.data("name")
	
	id && $.add_form_input( $modal.find(".date-form form"), name+".id", id );
	
	var shop_id = $modal.data("shop_id");
	shop_id && $.add_form_input( $modal.find(".date-form form"), "shop_id", shop_id );
	
	
	var date = $modal.data("date")[id] ||
	{
		"begin" : moment().day(0),
		"end"   : moment().day(6),
		
		go: function( act, step, type ){
			this["begin"][act](step, type);
			this["end"][act](step, type);
		}
	}
	$modal.data("date")[$modal.data("id")] = date; 
	
	var fmt = "YYYY-MM-DD";
	$modal.find(".date").html( date["begin"].format(fmt) + " to " + date["end"].format(fmt) );
    
	var $form = $modal.find(".date-form form");
	$.add_form_input( $form, "year", date["begin"].year() );
    $.add_form_input( $form, "week", date["begin"].week() );
	
	$.ajax({
		type:"POST",
		url: $webContent+"/"+name+"/salesChart",
		data: $form.serialize(),
		success: function( data ){
			$modal.find(".chart-area").prev().hide();
			render(data, $modal.find(".chart-area") );
		}
	})
};
