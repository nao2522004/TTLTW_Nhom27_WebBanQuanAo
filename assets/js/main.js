let captions = {
    0: {
      title: "The Faubourg in Silky Tones",
      description: "A Twilly scarf around your neck honors the iconic Grand Apparat motif. Reflecting the original designs."
    },
    1: {
      title: "Another Slide Title",
      description: "This is another slide description to showcase the banner layout."
    }
  };

  const title = $('#customCarousel .carousel_caption h5');
  const des = $('#customCarousel .carousel_caption p');

  const updateCarouselDescription = function (event) {
    const slideIndex = event.to;

    $('#customCarousel .carousel_caption h5').css({ opacity: 0, transition: 'opacity 0.3s' });
    $('#customCarousel .carousel_caption p').css({ opacity: 0, transition: 'opacity 0.3s' });

    setTimeout(() => {
      title.text(captions[slideIndex].title);
      des.text(captions[slideIndex].description);

      $('#customCarousel .carousel_caption h5').css({ opacity: 1 });
      $('#customCarousel .carousel_caption p').css({ opacity: 1 });
    }, 300);
  };

  $('#customCarousel').on('slide.bs.carousel', updateCarouselDescription);