(function () {

    // store the slider in a local variable
    var $window = $(window),
        flexslider = { vars: {} };

    // check grid size on resize event
    $window.resize(function () {
        var gridSize = getGridSize();

        flexslider.vars.minItems = gridSize;
        flexslider.vars.maxItems = gridSize;
    });
    // tiny helper function to add breakpoints
    function getGridSize() {
        return (window.innerWidth < 768) ? 2 :
            (window.innerWidth < 1024) ? 2 : 3;
    }

    var slide1 = $('.naviArea').flexslider({
        animation: "slide",
        controlNav: false,
        animationLoop: false,
        slideshow: false,
        itemWidth: 240,
        minItems: getGridSize(), // use function to pull in initial value
        maxItems: getGridSize(), // use function to pull in initial value
        //itemMargin: 5,
        asNavFor: '.imgArea',
        startAt: 1
    });

    var slide2 = $('.imgArea').flexslider({
        animation: "slide",
        controlNav: false,
        animationLoop: false,
        slideshow: false,
        sync: ".naviArea",
        smoothHeight: true,
        startAt: 1,
        start: function (slider) {
            $('body').removeClass('loading');
        }
    });


    var slide3 = $('.yearWrapper').flexslider({
        animation: "slide",
        controlNav: false,
        animationLoop: false,
        slideshow: false,
        itemWidth: 120,
        minItems: 4, // use function to pull in initial value
        maxItems: 6,
        itemMargin: 10
    });


    $('.yearCont dl dd ul .qus').on('click', function (e) {
        e.preventDefault();
        $(this).next('.ans').slideToggle('fast').siblings('.ans').slideUp('fast');
        $(this).toggleClass('open').siblings('.qus').removeClass('open');
        return false;
    });

    $('.years li').on('click', function (e) {
        e.preventDefault();
        var index = $('.years li').index(this);
        //console.log(index)
        $(this).addClass('on').siblings().removeClass('on')
        $('.histories .yearWrap.on .yearCont').eq(index).addClass('ons').siblings().removeClass('ons')
        $('.ans').slideUp('fast');
        $('.yearCont dl dd ul .qus').removeClass('open')
    })


}());

