<template>
  <div>
    <header-nav></header-nav>
    <div class="mt-5 mb-5">
      <h2>실시간 인기 작물</h2>
      <h6>최근 3개월간 인기 있는 농작물들</h6>
      <div id="demo" class="carousel slide" data-ride="carousel">
        <!-- Indicators -->
        <ul class="carousel-indicators">
          <li data-target="#demo" data-slide-to="0" class="active"></li>
          <li data-target="#demo" data-slide-to="1"></li>
          <li data-target="#demo" data-slide-to="2"></li>
        </ul>
        <!-- The slideshow -->
        <div class="carousel-inner">
          <div class="carousel-item active">
            <img src="@/assets/bg_tomato.png" alt="" width="100%" />
            <div class="carousel-caption">
              <h3>방울토마토</h3>
              <p>cherry tomato</p>
            </div>
          </div>
          <div class="carousel-item">
            <img src="" alt="" />
          </div>
          <div class="carousel-item">
            <img src="" alt="" />
          </div>
        </div>
        <!-- Left and right controls -->
        <a class="carousel-control-prev" href="#demo" data-slide="prev">
          <span class="carousel-control-prev-icon"></span>
        </a>
        <a class="carousel-control-next" href="#demo" data-slide="next">
          <span class="carousel-control-next-icon"></span>
        </a>
      </div>
    </div>
    <div class="m-5">
      <h2>8월에 키우기 좋은 작물</h2>
      <h6>제철 농작물을 추천 받고 키워 보세요!</h6>
    </div>
    <div class="m-5">
      <h2>커뮤니티 인기 게시글</h2>
      <h6>인기 있는 게시글을 추천 받아 보세요!</h6>
    </div>
    <div id="foot"></div>
    <menubar id="menubar"></menubar>
  </div>
</template>

<script>
import HeaderNav from "../components/Menu/HeaderNav.vue";
import Menubar from "../components/Menu/Menubar.vue";
export default {
  components: { Menubar, HeaderNav },
};
</script>

<style>
#foot {
  height: 150px;
}
#menubar {
  position: fixed;
  background-color: #b6c790;
  bottom: 0;
}
</style>
