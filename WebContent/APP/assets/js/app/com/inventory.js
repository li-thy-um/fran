var $format_inventory_status = function ( $tr ){
	var $td = $tr.find(".inven-status");
	var s_days = $td.html();
	var bar = $inventory_format( s_days );
	bar && $td.html(bar);
};

var $inventory_format = function( s_days ){
	
	var __bar = function( status, days, tag, addon ){
		return '<div style="margin-bottom:0px" class="progress '+
		status["class"] +'"><div class="bar" style="width: '+
		status["width"] +';">'+days+' '+tag+addon+'</div></div>'
	}
	
	var days = parseInt( s_days );
	
	if ( days != days ) return;
	
	if ( days == 2147483647 ){
		return "No record."; 
	}
	
	if( days > 30 ){
		var inven = this;
		
		return __bar( {"width":"100%", "class":"progress-danger"}, days, "Days",
				' (Inventory between ' + 0 +' and '+ 0 +' is appropriate)' );
	}
	
	var status = days > 7 ?
		{"width":"100%", "class": ""}
	: days > 3 ?
		{"width":"50%", "class": "progress-warning"}
	: {"width":"25%", "class": "progress-danger"} ;
	
	var tag = days > 1 ? 'Days' : 'Day';
	
	return __bar(  status, days, tag, '' );
}

var $inventory_status = function( name ) {
	return {
	    "title"    : "Enough For About",
        "name"     : name,
        "class"    : "align-center inven-status"
    }
};