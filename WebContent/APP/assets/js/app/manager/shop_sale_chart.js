$(function(){
	var $modal = $get_modal("shop_sale_chart");
	
	$modal.modal({
		backdrop: "static",
		show: false,
	}).on('show', function(){
		_refresh_chart( $modal );
	});

	$modal.data("date",{});
	$modal.data("name", "shop");
	$modal.data("render", _render_shop_chart )
	
	$modal.on("click", ".btn-act", function(e){
		var $btn = $(this);
		var id = $modal.data("id");
		var act = $btn.data("act");
		var date = $modal.data("date")[id];
		
		// NO future week charts.
		if( act == "add" && date["end"].valueOf() > moment().valueOf() ){
			return;
		}
		
		date.go(act,1,"w");
		_refresh_chart($modal);
	})

});

var _render_shop_chart = function( data, $chart ){
	$chart.highcharts({
        chart: {
            zoomType: 'x',
            width: 500,
            height: 400,
        },
        title: {
        	text: data["s"]["shop.name"]
        },
        yAxis: [ {
            title: {
                text: 'Sales',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            },
            labels: {
                format: '${value}',
                style: {
                    color: Highcharts.getOptions().colors[0]
                }
            }            
        }],
        xAxis:{
        	 categories: data["labels"]
        },
        tooltip: {
            shared: true
        },
        legend: {
            layout: 'vertical',
            align: 'left',
            x: 0,
            verticalAlign: 'top',
            y: 0,
            floating: true,
            backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
        },
        series: [{
            name: 'Sales',
            type: 'column',
            yAxis: 0,
            data: data["sales"],
            tooltip: {
                valuePrefix: '$'
            }

        }]
    });
};