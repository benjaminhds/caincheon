$(window).load(function(){
    if ($('.tabs li').length === 6) {
        $('.tabs').addClass('ea6');
    }else if($('.tabs li').length === 5) {
        $('.tabs').addClass('ea5');
    }else if($('.tabs li').length === 4) {
        $('.tabs').addClass('ea4');
    }else if($('.tabs li').length === 3) {
        $('.tabs').addClass('ea3');
    }else if($('.tabs li').length === 2) {
        $('.tabs').addClass('ea2');
    }
   
});
$(document).ready(function() {

    var winW = window.innerWidth || $(window).width();
    var winH = window.innerHeight || $(window).height();
    var $goTop = $('.goTop');
    var $header = $('header');
    var $subBg = $('.subBg');
    var $subM = $('header .heads nav > ul > li > dl');
    var $nav = $('header .heads nav > ul');

    $goTop.on('click', function(e) {
        e.preventDefault();
        $('html,body').animate({ scrollTop: 0 }, 1000);
    });

    $goTop.css('display', 'none');
    $(window).scroll(function() {
        if ($(window).scrollTop() < winH) {
            $goTop.stop().fadeOut('slow');
        } else if ($(window).scrollTop() > winH) {
            $goTop.stop().fadeIn('slow');
        }
    });

    setTimeout(function(){ 
        $('.subVisual > div.visBg').addClass('on');
    }, 5000);
    



    //slideOne
    var $sldOne = $('.slideOne figure > span');
    $sldOne.bxSlider({
        mode: 'horizontal',
        auto: true,
        pager: false,
        controls: false,
        infiniteLoop: true,
        speed: 2000
    });

    $('.slideOne i.prv').on('click', function() {
        $sldOne.goToPrevSlide();
        return false;
    });

    $('.slideOne i.nxt').on('click', function() {
        $sldOne.goToNextSlide();
        return false;
    });


      
    if (winW >= 1025) {

        $('.skipNavi').keydown(function() {
            var keyCode = (event.keyCode ? event.keyCode : event.which);
            if (keyCode == 13) {
                $('html,body').animate({ scrollTop: $('.secWrap').offset().top - 100 }, 1000);
            }
            return false;
        });

        $nav.off('click mouseover').on('mouseover', function() {
            $subBg.stop().fadeIn('fast');
            $subM.stop().fadeIn('fast');
        });
        $('nav').off('click mouseover mouseleave').on('mouseleave', function() {
            $subBg.stop().fadeOut(100);
            $subM.stop().fadeOut(100);
        });

        $subM.off('click').on('mouseover', function() {
            var index = $subM.index(this);
            $subM.find('dt').removeClass('on');
            $subM.eq(index).find('dt').addClass('on');
        });

        $('.tabs li').css('display','inline-block');       
        $('.tabs li.on').off('click');
        $('.tabs li.on').removeClass('arr');

        
    } else {

        $nav.css('height', winH - 78)
        $nav.find('li > a').off('mouseover click').on('click', function(e) {
            $nav.find('dl').stop().slideUp('slow');
            $(this).parent('li').find('dl').stop().slideToggle('slow');
            e.preventDefault();
        });
        $('.hamburger').off('click').on('click', function() {
            $('html,body').toggleClass('fixed');
            $('.layerBg').stop().fadeToggle('slow');
            $('nav').toggleClass('open');
            $nav.toggleClass('open');
            $(this).toggleClass('open');
        });

        $('.layerBg').on('click', function() {
            $('html,body').removeClass('fixed');
            $(this).stop().fadeOut('slow')
            $nav.removeClass('open');
            $('nav').removeClass('open');
            $('.hamburger').removeClass('open');
        });

     
          
    }

     if(winW <= 480){

        $('.tabs li').css('display','none');
        $('.tabs li.on').css('display','inline-block');
        $('.tabs li.on').off('click').on('click', function(e){                
            e.preventDefault();
            $(this).siblings('li').slideToggle(200);
            $(this).toggleClass('arr');             
        });
    }else if(winW >=480){
        //sh Tabs

        $('.tabs li').css('display','inline-block');
        $('.tabs li.on').css('display','inline-block');
        $('.tabs li.on').off('click');
        $('.tabs li.on').removeClass('arr');
      
    }

    jQuery(window).on('resize', function() {
        var winW = window.innerWidth || $(window).width();
        var winH = window.innerHeight || $(window).height();
        var $goTop = $('.goTop');
        var $header = $('header');
        var $subBg = $('.subBg');
        var $subM = $('header .heads nav > ul > li > dl');
        var $nav = $('header .heads nav > ul');
        if (winW >= 1025) {

            $('html,body').removeClass('fixed');
            $('.layerBg').stop().hide(0);
            $('.hamburger').removeClass('open');            
            $nav.removeClass('open');
            $('nav').removeClass('open');
            $nav.css('height','auto');
            $nav.find('dl').attr('style', '');
            $nav.find('li > a').off('click');

            $nav.off('click mouseover').on('mouseover', function() {
                $subBg.fadeIn('slow');
                $subM.fadeIn('slow');
            });
            $('nav').off('click mouseover mouseleave').on('mouseleave', function() {
                $subBg.stop().fadeOut(300);
                $subM.stop().fadeOut(300);
            });

            $subM.off('click').on('mouseover', function() {
                var index = $subM.index(this);
                $subM.find('dt').removeClass('on');
                $subM.eq(index).find('dt').addClass('on');
            });

            $('.tabs li').css('display','inline-block');
            $('.tabs li.on').css('display','inline-block');
            $('.tabs li.on').off('click');
            $('.tabs li.on').removeClass('arr');

        
        } else{
            
            $subBg.stop().hide(0);
            $subM.off('mouseover mouseleave');            
            $('nav').off('mouseleave mouseover');
            $nav.css('height', winH - 78);
            $nav.off('click mouseover').on('click', function(e) {});

            $nav.find('li > a').off('mouseover click').on('click', function(e) {
                $nav.find('dl').stop().slideUp('slow');
                $(this).parent('li').find('dl').stop().slideToggle('slow');
                e.preventDefault();
            });
            $('.hamburger').off('click').on('click', function() {
                $('html,body').toggleClass('fixed');
                $('.layerBg').stop().fadeToggle('slow');
                $nav.toggleClass('open');
                $('nav').toggleClass('open');
                $(this).toggleClass('open');
            });

            $('.layerBg').on('click', function() {
                $('html,body').removeClass('fixed');
                $(this).stop().fadeOut('slow')
                $nav.removeClass('open');
                $('nav').removeClass('open');
                $('.hamburger').removeClass('open');
            });

            
        }


        if(winW <= 480){
            $('.tabs li').css('display','none');
            $('.tabs li.on').css('display','inline-block');
            $('.tabs li.on').off('click').on('click', function(e){                
                e.preventDefault();
                $(this).siblings('li').slideToggle(200);
                $(this).toggleClass('arr');             
            });
        }else if(winW >=480){
            //sh Tabs
  
            $('.tabs li').css('display','inline-block');
            $('.tabs li.on').css('display','inline-block');
            $('.tabs li.on').off('click');
            $('.tabs li.on').removeClass('arr');
        }

    });


    //faq
    $('.answer').stop().slideUp(0)
    $('.faq .question .icoMisa, .faq .question .icos').on('click', function(e){
        e.preventDefault();
        $(this).parents('.question').toggleClass('ons').next('.answer').slideToggle(0);
        $(this).parents('.question').next('.answer').siblings('.answer').slideUp(0)
        $(this).parents('.question').siblings('.question').removeClass('ons')
        
    });


}); //document ready
