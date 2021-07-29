<template>
  <div class="container">
    <div style="text-align: center">
      <h4 class="community">커뮤니티</h4>
    </div>
    <div class="menu">
      <div class="category">
        <span class="all">전체</span>
        <span class="free">자유</span>
        <span class="info">정보</span>
        <span class="question">질문</span>
        <span class="share">나눔</span>
      </div>
      <div class="etc">
        <span class="search">검색</span>
        <span class="write" @click="postWrite">글쓰기</span>
      </div>
    </div>
    <div class="post-area">
      <list-row
        v-for="(post, index) in this.list"
        :key="index"
        :post="post"
      ></list-row>
      <infinite-loading
        @infinite="infiniteHandler"
        spinner="circles"
      ></infinite-loading>
    </div>
    <hr class="line1" />
    <div></div>
  </div>
</template>

<script>
import axios from "axios";
import ListRow from "@/components/Community/include/ListRow.vue";
import InfiniteLoading from "vue-infinite-loading";

export default {
  data() {
    return {
      list: [],
      limit: 0,
    };
  },
  components: {
    ListRow,
    InfiniteLoading,
  },
  created: {},
  methods: {
    postWrite() {
      this.$router.push("/community/write");
    },
    infiniteHandler($state) {
      axios.get("post/infinite", {
          params: {
            limit: this.limit,
          },
        })
        .then((response) => {
          setTimeout(() => {
            if (response.data.length) {
              this.list = this.list.concat(response.data);
              this.limit += 3;
              $state.loaded();
            } else {
              $state.complete();
            }
          }, 1000);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style></style>
