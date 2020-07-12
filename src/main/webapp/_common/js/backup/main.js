$(document).ready(function() {
    var winW = window.innerWidth || $(window).width();
    var winH = window.innerHeight || $(window).height();
    var $goTop = $('.goTop');
    var $header = $('header');

    $goTop.css('display', 'none');
    //셀렉트박스
    $('.sec04 > article > ol > li > ul > li').slimScroll({
        height: '100px',
        size: '10px',
        position: 'right',
        color: '#799bb3',
        alwaysVisible: false,
        railVisible: true,
        railColor: '#576f80',
        railOpacity: 1,
        wheelStep: 10,
        allowPageScroll: false,
        disableFadeOut: false,
        touchScrollStep: 50
    });

    $('.sec04 > article > ol > li').on('click', function() {
        $(this).children('ul').slideToggle(700);
        $(this).siblings('li').children('ul').stop().slideUp(700);
    });
    $('.sec04 > article > ol > li').on('mouseleave', function(){
        $(this).children('ul').slideUp();
    })

    //교구앨범
    var $album = $('.album ul');
    $album.bxSlider({
        auto: false,
        pager: false,
        controls: false,
        moveSlides: 1,
        autoReload: true,
        infiniteLoop: true,
        breaks: [{
            screen: 0,
            slides: 1
        }, {
            screen: 480,
            slides: 1
        }, {
            screen: 768,
            slides: 2
        }, {
            screen: 1280,
            slides: 3
        }]
    });
    $('.album .arr i.prv').on('click', function() {
        $album.goToPrevSlide();
        return false;
    });
    $('.album .arr i.nxt').on('click', function() {
        $album.goToNextSlide();
        return false;
    });

    //교구영상
    var $movie = $('.movie ol ul');
    $movie.bxSlider({
        auto: false,
        pager: false,
        controls: false,
        moveSlides: 3,
        autoReload: true,
        infiniteLoop: true,
        breaks: [{
            screen: 0,
            slides: 1,
            moveSlides:1
        }, {
            screen: 480,
            slides: 1,
            moveSlides:1
        }, {
            screen: 768,
            slides: 2,
            moveSlides:1
        }, {
            screen: 1024,
            slides: 3,
            moveSlides:1
        }, {
            screen: 1280,
            slides: 3
        }]
    });

    $('.movie .arr i.prv').on('click', function() {
        $movie.goToPrevSlide();
        return false;
    });
    $('.movie .arr i.nxt').on('click', function() {
        $movie.goToNextSlide();
        return false;
    });

    //교구장 동정
    var $gujang = $('.sec02 figure > .slides');
    $gujang.bxSlider({
        auto: false,
        pager: false,
        controls: false,
        moveSlides: 1,
        autoReload: true,
        infiniteLoop: true,
        breaks: [{
            screen: 0,
            slides: 1
        }, {
            screen: 480,
            slides: 1
        }, {
            screen: 768,
            slides: 1
        }, {
            screen: 1024,
            slides: 1
        }]
    });
    $('.sec02 i.prv').on('click', function() {
        $gujang.goToPrevSlide();
        return false;
    });
    $('.sec02 i.nxt').on('click', function() {
        $gujang.goToNextSlide();
        return false;
    });
    var $sungji = $('.sec04 > article > ul');  
    //교구성지
    $sungji.bxSlider({
        auto: false,
        pager: false,
        controls: false,
        moveSlides: 1,
        autoReload: true,
        infiniteLoop: true,
        slideMargin:20,
        breaks: [{
            screen: 0,
            slides: 1
        }, {
            screen: 480,
            slides: 1
        }, {
            screen: 768,
            slides: 2
        }, {
            screen: 1024,
            slides: 3
        }]
    });

    var $news = $('.news .left > ul li');
    $news.on('click', function(e) {
        e.preventDefault();
        var $ico = $('.right > div.tabCont > div');
        var index = $news.index(this);
        $news.removeClass('on');
        $ico.removeClass('on');
        $(this).addClass('on');
        $ico.eq(index).addClass('on');
    });

    if (winW >= 1025) {
        $('.visual').css('height', winH);
         $header.removeClass('fixed');
        $(window).scroll(function() {
            if ($(window).scrollTop() < winH) {
                $header.removeClass('fixed');
            } else if ($(window).scrollTop() > winH) {
                $header.addClass('fixed');
            }
        });
    } else {
        $header.addClass('fixed');
        $('.visual').css('height', 'auto');
        $(window).scroll(function() {
            if ($(window).scrollTop() < winH) {
                $header.addClass('fixed');
            } else if ($(window).scrollTop() > winH) {
                $header.addClass('fixed');
            }
        });
    }

    jQuery(window).on('resize', function() {
        var winW = window.innerWidth || $(window).width();
        var winH = window.innerHeight || $(window).height();
        var $goTop = $('.goTop');
        var $header = $('header');

        if (winW >= 1025) {
             $header.removeClass('fixed');
            $('.visual').css('height', winH);
            $(window).scroll(function() {
                if ($(window).scrollTop() < winH) {
                    $header.removeClass('fixed');
                } else if ($(window).scrollTop() > winH) {
                    $header.addClass('fixed');
                }
            });
        } else {
            $header.addClass('fixed');
            $('.visual').css('height', 'auto');
            $(window).scroll(function() {
                if ($(window).scrollTop() < winH) {
                    $header.addClass('fixed');
                } else if ($(window).scrollTop() > winH) {
                    $header.addClass('fixed');
                }
            });

        }
    });
}); //document-ready end


$(window).load(function(){
    var winW = window.innerWidth || $(window).width();
    var winH = window.innerHeight || $(window).height();
    var $header = $('header');
    var fullPageCreated = false;

    function createFullpage() {
        if (fullPageCreated === false) {
            fullPageCreated = true;
            $('#wrap').fullpage({
                verticalCentered: false,
                scrollingSpeed: 1500,
                scrollOverflow: true,
                fitToSection: false,
                css3: true,
                resize: true,
                afterLoad: function(anchorLink, index) {
                    if (index == 2) {
                        $header.addClass('fixed').css('z-index', '999999999');
                    }
                },
                onLeave: function(index, nextIndex, direction) {
                    if (index == 2 && nextIndex == 1) {
                        $header.removeClass('fixed');
                    }
                }
            });

        }
    }

    $('.visual .mouse').on('click', function(){
        
        $.fn.fullpage.moveSectionDown();
    })

    if (winW >= 1025) {
            createFullpage();
        } else if(winW < 1025) {
            if (fullPageCreated == true) {
                fullPageCreated = false;
                $.fn.fullpage.destroy('all');
            }
        }

    $(window).resize(function() {
        var winW = window.innerWidth || $(window).width();
        var winH = window.innerHeight || $(window).height();
        
        if (winW >= 1025) {
            createFullpage();
        } else if(winW < 1025) {
            if (fullPageCreated == true) {
                fullPageCreated = false;
                $.fn.fullpage.destroy('all');
            }
        }

    });
  })
