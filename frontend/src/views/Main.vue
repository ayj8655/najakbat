<template>
  <div>
    <header-nav></header-nav>
    <div class="mt-5 mb-5">
      <h2>실시간 인기 작물</h2>
      <h6>최근 3개월간 인기 있는 농작물들</h6>
      <div
        id="carouselExampleIndicators"
        class="carousel slide"
        data-bs-ride="carousel"
      >
        <div class="carousel-indicators">
          <button
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide-to="0"
            class="active"
            aria-current="true"
            aria-label="Slide 1"
          ></button>
          <button
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide-to="1"
            aria-label="Slide 2"
          ></button>
          <button
            type="button"
            data-bs-target="#carouselExampleIndicators"
            data-bs-slide-to="2"
            aria-label="Slide 3"
          ></button>
        </div>
        <div class="carousel-inner">
          <div class="carousel-item active">
            <img src="@/assets/bg_tomato.png" class="d-block w-100" alt="..." />
            <div class="carousel-caption">
              <h5>방울토마토</h5>
              <p>cherry tomato</p>
            </div>
          </div>
          <div class="carousel-item">
            <img src="@/assets/bg_tomato.png" class="d-block w-100" alt="..." />
          </div>
          <div class="carousel-item">
            <img src="@/assets/bg_tomato.png" class="d-block w-100" alt="..." />
          </div>
        </div>
        <button
          class="carousel-control-prev"
          type="button"
          data-bs-target="#carouselExampleIndicators"
          data-bs-slide="prev"
        >
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button
          class="carousel-control-next"
          type="button"
          data-bs-target="#carouselExampleIndicators"
          data-bs-slide="next"
        >
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>
    </div>
    <div class="m-5">
      <h2>8월에 키우기 좋은 작물</h2>
      <h6>제철 농작물을 추천 받고 키워 보세요!</h6>
      <div id="row">
        <div class="items">
          <img src="@/assets/thumbnail.png" />
          <p>1st depth</p>
        </div>
        <div class="items">
          <img src="@/assets/thumbnail.png" />
          <p>2nd depth</p>
        </div>
        <div class="items">
          <img src="@/assets/thumbnail.png" />
          <p>3rd depth</p>
        </div>
        <div class="items">
          <img src="@/assets/thumbnail.png" />
          <p>4th depth</p>
        </div>
        <div class="items">
          <img src="@/assets/thumbnail.png" />
          <p>5th depth</p>
        </div>
        <div class="items">
          <img src="@/assets/thumbnail.png" />
          <p>6th depth</p>
        </div>
        <div class="items">
          <img src="@/assets/thumbnail.png" />
          <p>7th depth</p>
        </div>
        <div class="items">
          <img src="@/assets/thumbnail.png" />
          <p>8th depth</p>
        </div>
      </div>
    </div>
    <div class="m-5">
      <h2>커뮤니티 인기 게시글</h2>
      <h6>인기 있는 게시글을 추천 받아 보세요!</h6>
      <div></div>
    </div>
    <div id="foot"></div>
    <menubar id="menubar"></menubar>
  </div>
</template>

<script>
// import axios from "axios";
import HeaderNav from "../components/Menu/HeaderNav.vue";
import Menubar from "../components/Menu/Menubar.vue";
export default {
  components: { Menubar, HeaderNav },
  data() {
    return {
      id: null,
    };
  },
  created() {},
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

#row {
  white-space: nowrap; /* 가로스크롤시 중요한 속성 */
  overflow-x: auto;
  overflow-y: hidden;
  padding: 10px 10px 5px;
  /* background: #efefef; */
  width: auto;
}

#row .items {
  display: inline-block;
  /* margin-left: 10px; */
  width: 100px;
}

#row .items:first-child {
  margin-left: 0;
}

#row .items p {
  margin-bottom: 8px;
  text-indent: 7px;
}

#row .items ul {
  border-radius: 3px;
  border: 1px solid #b5b5b5;
  height: 135px;
  overflow-y: scroll;
  padding: 3px 3px 8px;
  background: #fff;
}

#row .items ul li a {
  display: block;
  overflow: hidden;
  margin-top: 8px;
  padding: 3px;
  color: black;
  font-size: 12px;
  text-decoration: none;
}

#row .items ul li:first-child a {
  margin-top: 3px;
}

#row .items ul li.on a {
  border: 1px solid #c9cccf;
  border-radius: 3px;
  font-weight: bold;
  background-color: #efefef;
}
</style>
