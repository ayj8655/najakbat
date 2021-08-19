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
        <font-awesome-icon
          :icon="['fas', 'pencil-alt']"
          size="lg"
          @click="postWrite"
          class="pen-color"
        />
      </span>
      <div class="row mt-3" id="search-area" v-show="isSearch">
        <span class="col-4">
          <select class="form-control mr-2" name="key" id="skey" v-model="skey">
            <option value="name">닉네임</option>
            <option value="title">제목</option>
            <option value="content">내용</option>
            <option value="keyword">키워드</option>
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
            @keypress.enter="findForKeyword"
          />
        </span>
      </div>

      <hr />
    </div>
    <div class="post-area" v-if="!loadingFlag">
      <list-row
        v-for="(post, index) in list"
        :key="index"
        :post="post"
        :photo="photolist[index]"
        v-show="
          sword == '' ||
          (skey == 'name' && post.userNickname.includes(sword)) ||
          (skey == 'title' && post.title.includes(sword)) ||
          (skey == 'content' && post.content.includes(sword)) ||
          skey != 'keyword'
        "
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
      photolist: [],
      limit: 0,
      type: 0,
      listOrigin: [],
      photolistOrigin: [],
      loadingFlag : false,
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
      this.loadingFlag = false;
      this.list = [];
      this.photolist = [];
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
      this.photolistOrigin = this.photolist;
      this.loadingFlag = true;
      axios
        .get(`post/type/${text}`)
        .then(({ data }) => {
          this.list = null;
          this.list = data.postList;
          this.photolist = null;
          this.photolist = [];
          this.list.forEach((l, index) => {
            if(!isNaN(l)) {
              this.list[index] = null;
            } else {
              if (!this.photolist[index]) {
                this.photolist[index] = null;
                if (l.photos && l.photos.length) {
                  this.photolist[index] =
                    "https://mococobucket.s3.ap-northeast-2.amazonaws.com/post/" +
                    l.photos[0].saveFile;
                }
              }
            }
          });
          this.loadingFlag = false;
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
          // console.log(response.data.postList);
          // console.log(response.data.photosList);
          setTimeout(() => {
            if (response.data.postList.length) {
              this.list = this.list.concat(response.data.postList);
              this.list.forEach((l, index) => {
                if (!this.photolist[index]) {
                  this.photolist[index] = null;
                  if (response.data.photosList.length) {
                    for (let i = 0; i < response.data.photosList.length; i++) {
                      if (
                        l.postNumber ==
                        response.data.photosList[i].post.postNumber
                      ) {
                        this.photolist[index] =
                          "https://mococobucket.s3.ap-northeast-2.amazonaws.com/post/" +
                          response.data.photosList[i].saveFile;
                      }
                    }
                  }
                }
              });
              this.limit += 1;
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
    findForKeyword() {
      if(this.skey=="keyword") {
        this.listOrigin = this.list;
        this.photolistOrigin = this.photolist;
        this.list = null;
        this.photolist = null;
        axios.get(`post/search/${this.sword}`).then((data)=>{
          this.list = data.data.postList;
          this.list.forEach((l, index) => {
            this.photolist[index] = null;
            if(!isNaN(l)) {
                this.list[index] = null;
              } else {
                  if (l.photos && l.photos.length) {
                    this.photolist[index] =
                      "https://mococobucket.s3.ap-northeast-2.amazonaws.com/post/" +
                      l.photos[0].saveFile;
                  }
                }
              
          });
        });
      }
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
  margin-right: 5px;
}
.pen-color {
  color: #b6c790;
}
</style>
