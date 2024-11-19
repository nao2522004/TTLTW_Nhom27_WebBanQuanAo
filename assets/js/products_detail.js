// ===================================================== Products =====================================================

$(document).ready(function () {
    $('#mainCarousel').on('slide.bs.carousel', function (e) {
        var activeIndex = $(e.relatedTarget).index();

        $('#products .thumbnail-list img').removeClass('active');

        $('#products .thumbnail-list img').eq(activeIndex).addClass('active');
    });

    $('#products .thumbnail-list img').click(function () {
        var index = $(this).index();
        $('#mainCarousel').carousel(index);
    });

    $(document).click(function (event) {
        if (!$(event.target).closest('#products .thumbnail-list img').length && !$(event.target).closest('#mainCarousel img').length) {
            $('#products .thumbnail-list img').removeClass('active');
        }
    });

    $('#mainCarousel').on('slid.bs.carousel', function () {
        var currentIndex = $('#mainCarousel .carousel-item.active').index();
        $('#products .thumbnail-list img').removeClass('active');
        $('#products .thumbnail-list img').eq(currentIndex).addClass('active');
    });
});

$('#mainCarousel').carousel({
    interval: false
});

$('#products .choose_color span').click(function () {
    var colorIndex = $(this).index();

    $('#mainCarousel').carousel(colorIndex);
});
