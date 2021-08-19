<template>
  <div>
    <header-nav></header-nav>
    <Sidebar id="side-bar" />
    <div id="main-body">
      <div class="mt-5 mb-5">
        <h3>실시간 인기 작물</h3>
        <p>최근 가장 인기 있는 농작물들</p>
        <div
          id="carouselExampleIndicators"
          class="carousel slide"
          data-bs-ride="carousel"
          data-bs-touch="true"
          v-if="tops.length>0"
        >
          <div class="carousel-indicators">
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
          </div>
          <div class="carousel-inner">
            <div :class="[{'carousel-item active':(index==0)},{'carousel-item':(index>0)}]" v-for="(t, index) in tops" :key="index" @click="movePage(t.cropNumber)">
              <img
                :src="t.image"
                class="d-block"
                alt="..."
                style="width:100%; height:250px; filter: brightness(50%);"
              />
              <div class="carousel-caption">
                <h5>{{ t.name }}</h5>
                <p>{{ t.description }}... 더보기</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="mb-5">
        <h3>{{ this.month }}월에 키우기 좋은 작물</h3>
        <p>제철 농작물을 추천 받고 키워 보세요!</p>
        <div id="row">
          <div
            class="items m-2"
            v-for="(crop, index) in cropInThisMonth"
            :key="index"
            @click="movePage(crop.cropNumber)"
          >
            <img :src="getCropImg(crop)" />
            <p>{{ crop.name }}</p>
          </div>
        </div>
      </div>
      <div class="mb-5">
        <h3>커뮤니티 인기 게시글</h3>
        <p>인기 있는 게시글을 추천 받아 보세요!</p>
        <div>
          <vueper-slides id="post-slide" fractions progress fixed-height="350px" autoplay>
            <vueper-slide v-for="(post, index) in posts" :key="index" style="background-color: #b6c790;">
              <template v-slot:content>
                <div class="post-box m-5">
                  <list-row :post="post" :photo="null"></list-row>
                </div>
              </template>
            </vueper-slide>
          </vueper-slides>
        </div>
      </div>
    </div>
    <div v-show="this.$store.state.sidebar == false">
      <div id="foot"></div>
      <menubar id="menubar"></menubar>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import HeaderNav from "../components/Menu/HeaderNav.vue";
import Menubar from "../components/Menu/Menubar.vue";
import Sidebar from "../components/Sidebar/Sidebar.vue";
import ListRow from "../components/Community/include/ListRow.vue";
import { VueperSlides, VueperSlide } from 'vueperslides'
import 'vueperslides/dist/vueperslides.css'

export default {
  name: "Main",
  components: { Menubar, HeaderNav, Sidebar, ListRow, VueperSlides, VueperSlide },
  data() {
    return {
      id: null,
      month: 0,
      tops: [],
      crops: [],
      posts: [],
      photos: [],
      flags: [],
      tallerFlag : false,
    };
  },
  created() {
    this.month = new Date().getMonth() + 1;
    axios.get("user/crop/top?size=3").then((data) => {
      this.tops = data.data;
      this.tops.forEach((t, index) => {
        this.tops[index].image = require("@/assets/crop/" + t.image);
        this.tops[index].description = t.description.substring(0, 41);
      });
      // console.log(this.tops);
    });
    axios.get("guide/plant/").then((data) => {
      this.crops = data.data;
      // console.log(this.cropInThisMonth);
    });
    axios.get("post/top?size=10").then((data)=> {
      this.posts = data.data.postList;
      let photosList = data.data.photosList;
      // console.log(photosList);
      this.posts.forEach((p, index) => {
        if(!this.photos[index]) {
          this.photos[index] = null;
          this.flags[index] = false;
          if(photosList.length) {
            for (let i = 0; i < photosList.length; i++) {
              if(p.postNumber==photosList[i].post.postNumber) {
                this.photos[index] = "https://mococobucket.s3.ap-northeast-2.amazonaws.com/post/" + photosList[i].saveFile;
                this.flags[index] = true;
              }
            }
          }
        }
      });
    });
  },
  methods: {
    movePage(cropNumber) {
      this.$router.push(`/dict/detail/${cropNumber}`);
    },
    getCropImg(crop) {
      return crop.image
        ? require("@/assets/crop/" + crop.image)
        : require("@/assets/thumbnail.png");
    },
      movePage2(postno) {
        console.log("hihi");
        this.$router.push(`/community/detail/${postno}`);
      },
  },
  computed: {
    cropInThisMonth() {
      var list = [];
      this.crops.forEach((c, index) => {
        if (c.growthDuration.substring(this.month - 1, this.month) == "1")
          list[list.length] = this.crops[index];
      });
      return list;
    },
  },
};
</script>

<style>
#side-bar {
  z-index: 1;
}
.carousel {
  z-index: 0;
}
#main-body {
  font-family: Noto Sans KR;
  font-style: normal;
}
h3 {
  font-weight: bold;
}
#foot {
  /* height: 150px; */
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

#post-slide {
  z-index: 0;
  /* min-height: 500px; */
}
.post-box {
  min-height: 250px;
  border: 5px solid #b6c790;
  border-radius: 15px;
  background: #ffffff;
}
</style>
