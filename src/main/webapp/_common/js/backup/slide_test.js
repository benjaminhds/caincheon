$(document).ready(function() {

    var winW = window.innerWidth || $(window).width();
    var winH = window.innerHeight || $(window).height();
    var $slider_visual;
    var visual_ctl_bol = true;
    var num = 0;
    

    // Visual Slider - Pager
    
    $slider_visual_navi = $('.navis').bxSlider({
        mode: 'horizontal',
        pager: false,
        controls: false,
        slideWidth: 240,
        minSlides: 3,
        maxSlides: 3,
        moveSlides: 3,
        infiniteLoop: true,
        touchEnabled: false,
        onSlideBefore: function ($slideElement, oldIndex, newIndex){
          if(!visual_ctl_bol){
            var num = newIndex*3;
            eventCha(num);
            $slider_visual.goToSlide(num);
          }
        }
    });

    $('.navis li').each(function(index){
        $(this).find('a').on('click',function(){
            var num = parseInt($(this).attr('class').substring(3));   
            $('.blocks').stop().show(0);
            $slider_visual.goToSlide(num);
            eventCha(num);
            
			
			
            
        });
    });


    $('.navis').on('mouseenter', function(){
        $('.bt-arr').stop().show(0)
    });

    $('.visuals .btnLeft').on('click',function(e){
        e.preventDefault();
        visual_ctl_bol = false;    
        $slider_visual_navi.goToNextSlide();
        $('.bt-arr').stop().hide(0)
        $('.blocks').stop().show(0);
        return false;
    });

    $('.visuals .btnRight').on('click',function(e){
        e.preventDefault();
        visual_ctl_bol = false;
        $slider_visual_navi.goToPrevSlide();
        $('.bt-arr').stop().hide(0)
        $('.blocks').stop().show(0);
        return false;
    });


    // Visual Slider - IMG
    $slider_visual = $('.vis').bxSlider({
        mode: 'horizontal',
        auto: false,
        autoControls: false,
        pager: false,
        controls: false,
        speed: 400,
        easing: 'easeInOutExpo',
        useCSS: false,
        slideWidth: 1020,
        infiniteLoop: true,
        touchEnabled: false,
        onSliderLoad: function (currentIndex){
          $('.visuals .current strong').text(currentIndex + 1);
          eventCha(currentIndex);
        },
        onSlideBefore: function ($slideElement, oldIndex, newIndex){
          visual_ctl_bol = true;
          $('.visuals .current strong').text(newIndex + 1);
          eventCha(newIndex);
          $slider_visual_navi.goToSlide(Math.floor(newIndex/3));
        }
    });

    $('.visuals .current span').text($slider_visual.getSlideCount());

   
    // Visual Slider - Years
//    $yearSlide = $('.years').bxSlider({
//        mode: 'horizontal',
//        pager: false,
//        controls: false,
//        slideWidth: 240,
//        minSlides: 6,
//        maxSlides: 6,
//        moveSlides: 1,
//        infiniteLoop: true,
//        touchEnabled: false,
//        onSlideBefore: function ($slideElement, oldIndex, newIndex){
//          if(!visual_ctl_bol){
//            $yearSlide.goToSlide(num);
//          }
//        }
//    });

    $('.histories .btnLeft').on('click',function(e){
        e.preventDefault();
        visual_ctl_bol = false;    
        $yearSlide.goToNextSlide();
        return false;
    });

    $('.histories .btnRight').on('click',function(e){
        e.preventDefault();
        visual_ctl_bol = false;
        $yearSlide.goToPrevSlide();
        return false;
    });

    function eventCha(num){
      $('.navis li').removeClass('on');
      $('.navis').find('.num'+ num).parent().addClass('on');
      $('.vis li').removeClass('on');
      $('.vis li').eq(num + 1).addClass('on');

	  getYearData(num);
      setTimeout(function(){$yearSlide.reloadSlider();},600)
      setTimeout(function(){$('.blocks').stop().fadeOut(400);},400);
	  
    }   



}); //document ready

