$(document).ready(function() {

    var winW = window.innerWidth || $(window).width();
    var winH = window.innerHeight || $(window).height();
    var $slider_visual;
    var visual_ctl_bol = true;
    var num = 0;
    var is_loaded = false;

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
            is_loaded = false;        
            $('.blocks').stop().show(0);
            //alert(is_loaded)
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
        auto: false,
        autoControls: false,
        pager: false,
        controls: false,
        speed: 400,
        slideWidth: 1020,
        infiniteLoop: true,
        touchEnabled: false,
        adaptiveHeight:true,
        autoReload: false,
        breaks: [{
            screen: 0,
            slides: 1
        }, {
            screen: 480,
            slides: 1
        }, {
            screen: 768,
            slides: 1
        }],
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

   
    if (is_loaded = true) {
       // Visual Slider - Years
        $yearSlide = $('.years').bxSlider({

            mode: 'horizontal',
            pager: false,
            controls: false,
            slideWidth: 117,
            minSlides: 6,
            maxSlides: 6,
            moveSlides: 1,
            infiniteLoop: false,
            touchEnabled: false,
            onSlideBefore: function ($slideElement, oldIndex, newIndex){
              if(!visual_ctl_bol){
                var num = newIndex*3;
                eventCha(num);
                $yearSlide.goToSlide(num);
              }
            }
        });

    }

    $('.histories .btnLeft').on('click',function(e){
        e.preventDefault();
        $yearSlide.goToPrevSlide();
        return false;
    });

    $('.histories .btnRight').on('click',function(e){
        e.preventDefault();
        $yearSlide.goToNextSlide();
        return false;
    });

    function eventCha(num){
      $('.navis li').removeClass('on');
      $('.navis').find('.num'+ num).parent().addClass('on');
      $('.vis li').removeClass('on');
      $('.vis li').eq(num + 1).addClass('on');
      $('.yearWrap').eq(num).addClass('on').siblings().removeClass('on');
      $('.yearWrap').eq(num).children('.yearCont').eq(0).addClass('ons')

      getYearData(num);
      setTimeout(function(){$('.blocks').stop().fadeOut(400);}, 400)
      
    }   

    //ajax reload years


    function getYearData(num) {
        is_loaded = false;
        
        var url = "history_years_"+num+".html";

        $.ajax({
            url: url,
            cache: false,   
            data: {},
            type: "POST",
            dataType: "html",               
            success: function(val) {    
                $(".years").empty();    
                $(".years").append(val);
                is_loaded = true;         
                $yearSlide.reloadSlider();
                $('.years li').bind('click', function(e){
                    e.preventDefault();
                    var index = $('.years li').index(this);
                    //console.log(index)
                    $(this).addClass('on').siblings().removeClass('on')
                    $('.histories .yearWrap.on .yearCont').eq(index).addClass('ons').siblings().removeClass('ons')
                    $('.ans').slideUp('fast');
                    $('.yearCont dl dd ul .qus').removeClass('open')
                })


            },
            error: function(request,status,error) {
                alert("error");
            }               
        });
    }

   

    //question
    $('.yearCont dl dd ul .qus').on('click', function(e){
        e.preventDefault();
        $(this).next('.ans').slideToggle('fast').siblings('.ans').slideUp('fast');
        $(this).toggleClass('open').siblings('.qus').removeClass('open');
        return false;
    });
}); //document ready

