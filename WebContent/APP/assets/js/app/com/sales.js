var $sales_table_columns = function( name ){
	
	return [
		{   "title"    : "Week Sales",
		    "name"     : name+".week_sales",
		    "class"    : "align-right"
		},
		{   "title"    : "Week Increase",
		    "name"     : name+".week_increase",
		    "class"    : "align-right increase"
		},
		{   "title"    : "Month Sales",
		    "name"     : name+".month_sales",
		    "class"    : "align-right"
		},
		{   "title"    : "Month Increase",
		    "name"     : name+".month_increase",
		    "class"    : "align-right increase"
		},
		{   "title"    : "Year Sales",
		    "name"     : name+".year_sales",
		    "class"    : "align-right"
		},
		{   "title"    : "Year Increase",
		    "name"     : name+".year_increase",
		    "class"    : "align-right increase"
		}
	];
};

var $isTotal = function( id ){
	return id.indexOf("total_562610360") != -1;
}

var $format_sales = function( $tr ){
	if( $isTotal( $tr.attr("id") ) ){
		$tr.css("background-color", "#eaeaea")
		   .css("cursor", "auto");
	}
	
	$tr.find(".increase").each(function(i,td){
		var $td = $(td);
		var increase = $td.html();
		var first_char = increase.charAt(0);
		if( increase == "-101" || increase == "New Sale"){
			$td.html( "New Sale" );
		}
		if( increase == "-102" || increase == "Not Sold Yet"){
			$td.html( "Not Sold Yet" );
		}
		
		increase = $td.html();
		if( increase != "" && increase.indexOf(" ") == -1 ){
			$td.append("%");
		}
		if( increase.indexOf(" ") == -1 ){
			if( first_char == "-" ){
    			$td.append(" ↓");
    			$td.css("color","green");
    		}else if( parseInt(first_char) ){
    			$td.append(" ↑");
    			$td.css("color","red");
    		}
		}
	});
}