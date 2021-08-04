<template>
  <div class="container mt-4">
    <div style="text-align: center">
      <h3 class="m-5 community">커뮤니티</h3>
    </div>
    <div class="menu">
      <span class="category">
        <span
          :class="{ colorChange: colorChange[0] }"
          id="all"
          @click="showAllPost"
          >전체</span
        >
        <span
          :class="{ colorChange: colorChange[1] }"
          id="free"
          @click="changeType(1, '자유')"
          >자유</span
        >
        <span
          :class="{ colorChange: colorChange[2] }"
          id="info"
          @click="changeType(2, '정보')"
          >정보</span
        >
        <span
          :class="{ colorChange: colorChange[3] }"
          id="question"
          @click="changeType(3, '질문')"
          >질문</span
        >
        <span
          :class="{ colorChange: colorChange[4] }"
          id="share"
          @click="changeType(4, '나눔')"
          >나눔</span
        >
      </span>
      <span class="etc">
        <span class="search" @click="changeFlag"
          ><img :src="searchImg" width="30px"
        /></span>
        <span class="write" v-if="this.$store.userNumber" @click="postWrite"
          >글쓰기</span
        >
      </span>
      <div class="row mt-3" id="search-area" v-show="isSearch">
        <span class="col-4">
          <select class="form-control mr-2" name="key" id="skey" v-model="skey">
            <option value="name">닉네임</option>
            <option value="title">제목</option>
            <option value="content">내용</option>
          </select>
        </span>
        <span class="col-8">
          <input
            type="text"
            class="form-control mr-2"
            placeholder="검색어 입력."
            name="word"
            id="sword"
            v-model="sword"
          />
        </span>
      </div>

      <hr />
    </div>
    <div class="post-area">
      <list-row
        v-for="(post, index) in this.list"
        :key="index"
        :post="post"
        v-show="((sword=='') || (skey=='name' && post.userNickname.includes(sword)) || (skey=='title' && post.title.includes(sword) || (skey=='content' && post.content.includes(sword))))"
      />
      <infinite-loading
        @infinite="infiniteHandler"
        spinner="circles"
        v-if="this.type == 0"
      ></infinite-loading>
    </div>
    <!-- <hr class="line1" /> -->
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
      type: 0,
      listOrigin: [],
      colorChange: [true, false, false, false, false],
      isSearch: false,
      skey: "name",
      sword: "",
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
      this.list = [];
      this.type = 0;
      this.limit = 0;
      this.colorChange.forEach((c, index) => {
        this.colorChange[index] = false;
      });
      this.colorChange[0] = true;
    },
    changeType(type, text) {
      this.type = type;
      this.colorChange.forEach((c, index) => {
        this.colorChange[index] = false;
        // console.log(c);
      });
      this.colorChange[type] = true;
      this.listOrigin = this.list;
      this.list = [];
      axios
        .get(`post/type/${text}`)
        .then(({ data }) => {
          this.list = data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    changeFlag() {
      this.isSearch = !this.isSearch;
    },
    infiniteHandler($state) {
      axios
        .get("post/infinite", {
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
    searchImg() {
      return this.isSearch
        ? require("@/assets/search_darkgreen.png")
        : require("@/assets/search_green.png");
    },
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
.colorChange {
  font-weight: bold;
  color: #446631;
}
.category {
  color: #b6c790;
}
.category > span {
  margin-right: 5px;
}
.etc {
  float: right;
  display: inline-block;
}
.etc span {
  margin-left: 5px;
}
</style>
