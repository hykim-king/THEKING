document.addEventListener("DOMContentLoaded", function () {
  setTimeout(() => {
    const track = document.querySelector(".carousel-track");
    const slides = document.querySelectorAll(".carousel-track a");
    const leftBtn = document.querySelector(".carousel-btn.left");
    const rightBtn = document.querySelector(".carousel-btn.right");

    if (!track || slides.length === 0 || !leftBtn || !rightBtn) {
      console.warn("슬라이드 요소가 로드되지 않았습니다.");
      return;
    }

    const slideWidth = slides[0].offsetWidth + 40;
    const totalSlides = slides.length;
    let currentIndex = 0;
    let intervalId;

    function moveToSlide(index) {
      track.style.transform = `translateX(-${index * slideWidth}px)`;
    }

    function nextSlide() {
      currentIndex = (currentIndex + 1) % totalSlides;
      moveToSlide(currentIndex);
    }

    function prevSlide() {
      currentIndex = (currentIndex - 1 + totalSlides) % totalSlides;
      moveToSlide(currentIndex);
    }

    function startAutoSlide() {
      intervalId = setInterval(nextSlide, 4000);
    }

    function stopAutoSlide() {
      clearInterval(intervalId);
    }

    rightBtn.addEventListener("click", function () {
      stopAutoSlide();
      nextSlide();
      startAutoSlide();
    });

    leftBtn.addEventListener("click", function () {
      stopAutoSlide();
      prevSlide();
      startAutoSlide();
    });

    startAutoSlide();
  }, 100); // 이미지 로딩 시간 확보
});