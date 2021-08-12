<template>
  <div class="container mt-5">
    <h3 class="m-5 dict">농작물 도감</h3>
    <div class="" id="search-area">
      <img src="@/assets/search.png" width="20px" />
      <span>
        <input
          type="text"
          class="form-control"
          id="searchKey"
          v-model="searchKey"
          placeholder="이름으로 농작물 검색"
          width="80%"
        />
      </span>
    </div>
    <div class="mt-2" id="crops-area">
      <hr />
      <div
        v-for="(crop, index) in crops"
        :key="index"
        @click="movePage(crop.cropNumber)"
        v-show="(searchKey=='') || (crop.name.includes(searchKey))"
      >
        <div>
          <img id="thumbnail" :src="findCropImg(crop)" />
          <strong>{{ crop.name }}</strong>
        </div>
        <hr />
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      searchKey: "",
      crops: [],
      cropImg: [],
    };
  },
  created() {
    axios.get("guide/plant/summary").then((data) => {
      this.crops = data.data; 
    });
  },
  methods: {
    movePage(cropNumber) {
      this.$router.push(`/dict/detail/${cropNumber}`);
    },
    findCropImg(crop) {
      return (crop.image)? require("@/assets/crop/" + crop.image):require("@/assets/thumbnail.png");
    }
  },
};
</script>

<style>
.container > * {
  font-family: Noto Sans KR;
  font-style: normal;
}
.dict {
  font-weight: bold;
}
#search-area > * {
  display: inline-block;
}
#search-area > img {
  margin-right: 5px;
}
#crops-area > div {
  text-align: left;
}
#thumbnail {
  margin: 5px 20px;
  width: 45px; height: 45px;
  border-radius: 5px;
}
</style>
