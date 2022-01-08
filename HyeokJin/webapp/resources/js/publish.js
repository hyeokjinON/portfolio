$(document).ready(function(){
/* enerDoc 탭메뉴01 */
	if($("ul.tabm").size() != 0){
	 $("ul.tabm li a").click(function(){
	  var idx = $(this).parent().index('ul.tabm li');
	  $("ul.tabm li").removeClass("on");
	  $(this).parent().addClass("on");
	  $(".tab_con").hide();
	  $(".tab_con").eq(idx).show();
	 })
	}
})
/* enerDoc 탭메뉴02 */
$(function(){
	if($("ul.tabms").size() != 0){
	 $("ul.tabms li a").click(function(){
	  var idx = $(this).parent().index('ul.tabms li');
	  $("ul.tabms li").removeClass("on");
	  $(this).parent().addClass("on");
	  $(".tab_cons").hide();
	  $(".tab_cons").eq(idx).show();
	 })
	}
})
/* enerDoc 탭메뉴03 */
$(function(){
	if($("ul.tabmss").size() != 0){
	 $("ul.tabmss li a").click(function(){
	  var idx = $(this).parent().index('ul.tabmss li');
	  $("ul.tabmss li").removeClass("on");
	  $(this).parent().addClass("on");
	  $(".tab_conss").hide();
	  $(".tab_conss").eq(idx).show();
	 })
	}
})

/* 이용가니드 탭메뉴02 */
$(function(){
	if($("ul.gude_tab").size() != 0){
	 $("ul.gude_tab li a").click(function(){
	  var idx = $(this).parent().index('ul.gude_tab li');
	  $("ul.gude_tab li").removeClass("on");
	  $(this).parent().addClass("on");
	  $(".gutab_cons").hide();
	  $(".gutab_cons").eq(idx).show();
	 })
	}
})

/* 마이페이지 탭메뉴02 */
$(function(){
	if($("ul.tabms").size() != 0){
	 $("ul.tabms li a").click(function(){
	  var idx = $(this).parent().index('ul.tabms li');
	  $("ul.tabms li").removeClass("on");
	  $(this).parent().addClass("on");
	  $(".tab_myeng").hide();
	  $(".tab_myeng").eq(idx).show();
	 })
	}
})

/* 로그인 */
$(document).ready(function(){
	$("a.accbtn").click(function(){
		$(".accbox").slideToggle("fast");
	});
});

	/* 모달 팝업 */
$(document).ready(function(){
	$(".open").click(function(e){
		var popLink = $(this).attr('id');
		$("." + popLink).modal();
		e.preventDefault();
	});
	$(".mcls").click(function(e){
		$.modal.close();
		e.preventDefault();
	});
	$('.bgmodal').click(function(){
		$('.explpop').fadeOut();
		return false;
	});
});

/* 마이페이지 팝업 */
$(document).ready(function(){
	$(" a.allbtn").click(function(){
		$(".rndbox").slideToggle("fast");
	});
	$('.allbtn').click(function(e){
		$(this).toggleClass('on');
		e.preventDefault();
	});
});

/* qna */
/*$(document).on("click", ".qust_list .tit", function(e) {
	$(this).toggleClass('on');
	if($('.qust_list .tit').hasClass('on')){
		$('.qust_list .tit').removeClass('on');   
		$(this).toggleClass('on');
	}
	e.preventDefault();
});*/

/* 고객동의 */
$(document).on("click", ".agreelist .tit", function(e) {
	$(this).toggleClass('on');
	if($('.agreelist .tit').hasClass('on')){
		$('.agreelist .tit').removeClass('on');   
		$(this).toggleClass('on');
	}
	e.preventDefault();
});

/* 마이에너지 직원용 */
$(document).ready(function(){
	$(".tbcons").hide();
		$(".tbtit").click(function(e){
			$(this).next().slideToggle("fast").siblings(".tbcons").slideUp("fast");
			$(this).toggleClass("active").siblings(".tbtit.active").removeClass("active");
			e.preventDefault();
	})
$("a.tbtit").click(function(){
	$(".tbcons").slideToggle("fast");
	});
});

/* 상품가입 */
$(document).on("click", ".product_list .pro_tit", function(e) {
	$(this).toggleClass('on');
	if($('.product_list .pro_tit').hasClass('on')){
		$('.product_list .pro_tit').removeClass('on');   
		$(this).toggleClass('on');
	}
	e.preventDefault();
});

/* 서비스 매핑 탭 */
$(document).ready(function(){
	if($("ul.mptab").size() != 0){
	 $("ul.mptab li a").click(function(){
	  var idx = $(this).parent().index('ul.mptab li');
	  $("ul.mptab li").removeClass("on");
	  $(this).parent().addClass("on");
	  $(".mpcon").hide();
	  $(".mpcon").eq(idx).show();
	 })
	}
})

/* gnb */
$(document).ready(function(){
	$("a.gnbbtn").click(function(){
		//$(".gnb").slideToggle("slow");
		$(".gnb").toggleClass("active");
	});
});

/* 회원가입 말풍선 */
$(document).ready(function(){
	$(".morebtn").each(function(index) {
		$(this).mouseover(function() {
			$(".txtmore").css("display", "block");
		});
		$(this).mouseout(function() {
			$(".txtmore" ).css("display", "none");
		});
	});
});

/* 상품가입 말풍선 */
$(document).ready(function(){
	$(".my_morebtn").each(function(index) {
		$(this).mouseover(function() {
			$(".my_txtmore").css("display", "block");
		});
		$(this).mouseout(function() {
			$(".my_txtmore" ).css("display", "none");
		});
	});
});

/* 가입 대기 목록 */
$(document).on("click", ".waitlist .wait_btn", function(e) {
	$(this).toggleClass('on');
	if($('.waitlist .wait_btn').hasClass('on')){
		$('.waitlist .wait_btn').removeClass('on');   
		$(this).toggleClass('on');
	}
	e.preventDefault();
});