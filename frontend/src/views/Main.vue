<template>
  <div>
    <header-nav></header-nav>
    <Sidebar id="side-bar" />
    <div id="main-body">
      <div class="mt-5 mb-5">
        <h3>실시간 인기 작물</h3>
        <p>최근 3개월간 인기 있는 농작물들</p>
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
              <img
                src="@/assets/bg_tomato.png"
                class="d-block w-100"
                alt="..."
              />
              <div class="carousel-caption">
                <h5>방울토마토</h5>
                <p>cherry tomato</p>
              </div>
            </div>
            <div class="carousel-item">
              <img
                src="@/assets/bg_tomato.png"
                class="d-block w-100"
                alt="..."
              />
            </div>
            <div class="carousel-item">
              <img
                src="@/assets/bg_tomato.png"
                class="d-block w-100"
                alt="..."
              />
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
      <div class="mb-5">
        <h3>{{this.month}}월에 키우기 좋은 작물</h3>
        <p>제철 농작물을 추천 받고 키워 보세요!</p>
        <div id="row">
          <div class="items" v-for="(crop, index) in cropInThisMonth" :key="index" @click="movePage(crop.cropNumber)">
            <img :src="getCropImg(crop)" />
            <p>{{crop.name}}</p>
          </div>
        </div>
      </div>
      <div class="mb-5">
        <h3>커뮤니티 인기 게시글</h3>
        <p>인기 있는 게시글을 추천 받아 보세요!</p>
        <div></div>
      </div>
    </div>
    <div id="foot"></div>
    <menubar id="menubar"></menubar>
  </div>
</template>

<script>
import axios from "axios";
import HeaderNav from "../components/Menu/HeaderNav.vue";
import Menubar from "../components/Menu/Menubar.vue";
import Sidebar from "../components/Sidebar/Sidebar.vue";
export default {
  components: { Menubar, HeaderNav, Sidebar },
  data() {
    return {
      id: null,
      month: 0,
      crops: [],
    };
  },
  created() {
    this.month = (new Date().getMonth() + 1);
    axios.get("guide/plant/").then((data) => {
      this.crops = data.data;
      // console.log(this.cropInThisMonth);
    });
  },
  methods: {
    movePage(cropNumber) {
      this.$router.push(`/dict/detail/${cropNumber}`);
    },
    getCropImg(crop) {
      return (crop.image)? require("@/assets/crop/"+crop.image):require("@/assets/thumbnail.png");
    }
  },
  computed: {
    cropInThisMonth() {
      var list = [];
      this.crops.forEach((c, index) => {
        if((c.growthDuration).substring(this.month-1, this.month)=="1")
          list[list.length] = this.crops[index];
      });
      return list;
    },
  }
};
</script>

<style>
#side-bar {
  z-index: 1;
}
.carousel {
  z-index: -1;
}
#main-body{
  font-family: Noto Sans KR;
  font-style: normal;
}
h3 {
  font-weight: bold;
}
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

#row .items img {
  width: 75px;
  height: 75px;
  border-radius: 5px;
  border: solid 1px #efefef;
}
</style>
