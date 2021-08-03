<template>
  <div class="container mt-5">
    <h2 class="m-5">농작물 도감</h2>
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
      >
        <div>
          <img id="thumbnail" src="@/assets/thumbnail.png" width="45px" />
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
    };
  },
  created() {
    axios.get("guide/plant/summary").then((data) => {
      this.crops = data.data;
      //   console.log(data);
    });
  },
  methods: {
    movePage(cropNumber) {
      this.$router.push(`/dict/detail/${cropNumber}`);
    },
  },
};
</script>

<style>
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
}
</style>
