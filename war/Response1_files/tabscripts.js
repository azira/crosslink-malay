// JavaScript Document

$(document).ready(function() {
    $('.tab-content > div').not(':first').hide();
	$('.vote-tab a').click(function(e){
		$('.vote-tab a').removeClass('active');
		$(this).addClass('active');
		$('.tab-content > div').hide();
		$($(this).attr('href')).show();
		e.preventDefault();
		if($('.vote-tab li:last-child a').hasClass('active')){
			$('.vote-tab').css('background-color','#d00');
		} else {
			$('.vote-tab').css('background-color','#666');
	}
	});
	
});

