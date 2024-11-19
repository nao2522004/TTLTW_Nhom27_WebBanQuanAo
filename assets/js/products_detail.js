// ===================================================== Products =====================================================

$(document).ready(function () {
    $('#mainCarousel').on('slide.bs.carousel', function (e) {
        var activeIndex = $(e.relatedTarget).index();

        $('#section_products .thumbnail-list img').removeClass('active');

        $('#section_products .thumbnail-list img').eq(activeIndex).addClass('active');
    });

    $('#section_products .thumbnail-list img').click(function () {
        var index = $(this).index();
        $('#mainCarousel').carousel(index);
    });

    $(document).click(function (event) {
        if (!$(event.target).closest('#section_products .thumbnail-list img').length && !$(event.target).closest('#mainCarousel img').length) {
            $('#section_products .thumbnail-list img').removeClass('active');
        }
    });

    $('#mainCarousel').on('slid.bs.carousel', function () {
        var currentIndex = $('#mainCarousel .carousel-item.active').index();
        $('#section_products .thumbnail-list img').removeClass('active');
        $('#section_products .thumbnail-list img').eq(currentIndex).addClass('active');
    });
});

$('#mainCarousel').carousel({
    interval: false
});

$('#section_products .choose_color span').click(function () {
    var colorIndex = $(this).index();

    $('#mainCarousel').carousel(colorIndex);
});

// ===================================================== Similar Section =====================================================

$('#carousel_similar').carousel({
    interval: false
});