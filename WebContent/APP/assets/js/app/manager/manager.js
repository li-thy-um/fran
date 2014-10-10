$(function(){
	$(".app-tag").click(function(){
		var tag = $(this).attr("data-click");
		$(tag).click();
	});
});