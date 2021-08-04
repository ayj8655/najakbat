<template>
  <div class="container mt-5">
    <div>
      <h3 class="name">{{this.crop.name}}</h3>
      <div class="m-4"><img :src="cropImg" width="80%" /></div>
      <div class="m-4" id="tabs-area">
        <span :class="[{ tabChange: tabs[0] }, { tabDefault : !tabs[0] }]" @click="changeTab(0)">설명</span>
        <span :class="[{ tabChange: tabs[1] }, { tabDefault : !tabs[1] }]" @click="changeTab(1)">재배</span>
        <span :class="[{ tabChange: tabs[2] }, { tabDefault : !tabs[2] }]" @click="changeTab(2)">관리</span>
        <span :class="[{ tabChange: tabs[3] }, { tabDefault : !tabs[3] }]" @click="changeTab(3)">요리</span>
      </div>
      <div class="m-4" id="contents-area">
        <div v-if="this.current==0" v-html="enterToBr(this.crop.description)" id="content-1"></div>
        <div v-if="this.current==1" v-html="enterToBr(this.crop.cultivation)" id="content-2"></div>
        <div v-if="this.current==2">
          <div class="mt-5 mb-5">
            <p><strong>햇빛 선호도</strong></p>
            <div id="sun-area">
              <span><img :src="suns[0]"></span>
              <span><img :src="suns[1]"></span>
              <span><img :src="suns[2]"></span>
              <span><img :src="suns[3]"></span>
              <span><img :src="suns[4]"></span>
            </div>
          </div>
          <div class="mb-5">
            <p><strong>물 선호도</strong></p>
            <div id="water-area">
              <span><img :src="waters[0]"></span>
              <span><img :src="waters[1]"></span>
              <span><img :src="waters[2]"></span>
              <span><img :src="waters[3]"></span>
              <span><img :src="waters[4]"></span>
            </div>
          </div>
          <div class="mb-5">
            <p><strong>생육 온도</strong></p>
            <div id="temp-area">
              <span v-text="getLowTemp(this.crop.temperature)"></span>
              <span><div id="temp" /></span>
              <span v-text="getHighTemp(this.crop.temperature)"></span>
            </div>
          </div>
        </div>
        <div v-if="this.current==3">
          <div v-for="(r, index) in recipes" :key="index" class="mt-2 mb-3">
            <p>{{r.name}}</p>
            <youtube
              :video-id="videoIds[index]"
              :player-vars="playerVars"
              @ready="ready"
              @playing="playing"
              @ended="ended"
              @error="error"
              ref="youtube"
              width="100%" height="100%" />
          </div>
        </div>
      </div>
    </div>
    <button class="btn btn-success m-2">정보 수정 요청하기</button>
  </div>
</template>

<script>
import axios from "axios";

import Vue from "vue";
import VueYoutube from "vue-youtube";
Vue.use(VueYoutube);

export default {
  data() {
    return {
      crop: null,
      cropImg: null,
      current: 0,
      tabs: [true, false, false, false],
      suns: [null, null, null, null, null],
      waters: [null, null, null, null, null],
      recipes: [],
      videoIds: []
    }
  },
  created() {
    axios.get(`guide/plant/${this.$route.params.no}`).then((data) => {
      this.crop = data.data;
      this.cropImg = (this.crop.image)? require("@/assets/crop/" + this.crop.image):require("@/assets/thumbnail.png");
      this.suns.forEach((s, index) => {
        this.suns[index] = (index < this.crop.sun)? require("@/assets/sun.png"):require("@/assets/sun_off.png");
      });
      this.waters.forEach((w, index) => {
        this.waters[index] = (index < this.crop.water)? require("@/assets/water_on.png"):require("@/assets/water_off.png");
      });
    });
    axios.get(`guide/plant/recipe/${this.$route.params.no}`).then((data) => {
      this.recipes = data.data;
      this.recipes.forEach((r, index) => {
        axios.get(`guide/recipe/${r.recipeNumber}`).then(({data}) => {
          this.videoIds[index] = this.getVideoId(data.url);
        });
      });
    });
  },
  methods: {
    changeTab(num) {
      this.tabs.forEach((t, index) => {
        this.tabs[index] = false;
      });
      this.tabs[num] = true;
      this.current = num;
    },
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    getLowTemp(str) {
      return str.split('~')[0] + "℃";
    },
    getHighTemp(str) {
      return str.split('~')[1] + "℃";
    },
    getVideoId(url) {
      var video_id = url.split('v=')[1];
      var ampersandPosition = video_id.indexOf('&');
      if(ampersandPosition != -1) {
        video_id = video_id.substring(0, ampersandPosition);
      }
      return video_id;
    }
  },
}
</script>

<style scoped>
.container > * {
  font-family: Noto Sans KR;
  font-style: normal;
}
.name {
  font-weight: bold;
}
.tabDefault {
  padding: 5px;
  margin: 5px;
  color: #ffffff;
  border-radius: 5px;
  background: #B6C790;
}
.tabChange {
  padding: 5px;
  margin: 5px;
  font-weight: bold;
  color: #ffffff;
  border-radius: 5px;
  background: #446631;
}
#contents-area > div {
  border: 2px solid #446631;
  box-sizing: border-box;
  border-radius: 5px;
  min-height: 200px;
  padding: 20px;
}
#content-1, #content-2 {
  text-align: left;
}
#temp {
  width: 50%;
  height: 0px;
  background: #999999;
  border: 5px solid #999999;
  border-radius: 5px;
  display: inline-block;
  margin: 0px 5px;
}
#sun-area img, #water-area img {
  width: 30px;
  margin: 5px;
}
</style>