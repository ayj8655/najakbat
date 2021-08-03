<template>
  <div class="container mt-4">
    <div style="text-align: center">
      <h4 class="mb-5 community">커뮤니티</h4>
    </div>
    <div class="menu">
      <span class="category">
        <span class="all" @click="showAllPost">전체</span>
        <span class="free" @click="changeType(1, '자유')">자유</span>
        <span class="info" @click="changeType(2, '정보')">정보</span>
        <span class="question" @click="changeType(3, '질문')">질문</span>
        <span class="share" @click="changeType(4, '나눔')">나눔</span>
      </span>
      <span class="etc">
        <span class="search">검색</span>
        <span class="write" v-if="this.$store.userNumber" @click="postWrite">글쓰기</span>
      </span>
      <hr>
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
        v-if="this.type == 0"
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
  name: 'MyCommunityList',
  props: ['userNumber'],
  data() {
    return {
      list: [],
      limit: 0,
      type: 0,
    };
  },
  components: {
    ListRow,
    InfiniteLoading,
  },
  methods: {
    postWrite() {
      this.$router.push("/community/write");
    },
    showAllPost() {
      this.type = 0;
      this.limit = 0;
      this.list = [];
    },
    changeType(type, text) {
      this.type = type;
      axios.get(`post/type/${text}`).then(({ res }) => {
        this.list = res.data;
      })
      .catch((error) => {
          console.log(error);
        });
    },
    infiniteHandler($state) {
      let userNumber = this.$props.userNumber
      axios
        .get(`post/user/${userNumber}`, {
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
  computed: {

  },
};
</script>

<style scoped>
.container > * {
  font-family: Noto Sans KR;
  font-style: normal;
}
.community {
  font-weight: bold;
}
.menu {
  text-align: left;
}
.category span {
  margin-right: 5px;
  color:#B6C790;
}
.etc {
  float: right;
  display: inline-block;
}
.etc span {
  margin-left: 5px;
}
</style>
