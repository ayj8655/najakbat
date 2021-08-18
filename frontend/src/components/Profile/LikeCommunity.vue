<template>
  <div class="container mt-4">
    <div class="menu">
     
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
          (skey == 'content' && post.content.includes(sword))
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
  name: 'MyCommunityList',
  props: ['userNumber'],
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
      myNumber: null
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
        .then(res => {
          let userNumber = this.$props.userNumber
          var chooseList = res.data.postList;
          var i = 0
          this.list = null;
          this.list = [];
          this.photolist = null;
          this.photolist = [];
          chooseList.forEach((l, index) => {
            if(!isNaN(l)) {
              chooseList[index] = null;
            } else {
              if (userNumber == l.userNumber) {
                this.list.push(l)
                this.photolist[i] = null;
                if (l.photos && l.photos.length) {
                  this.photolist[i] =
                    "https://mococobucket.s3.ap-northeast-2.amazonaws.com/post/" +
                    l.photos[0].saveFile;
                }
                i++
                
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
      let userNumber = this.$props.userNumber
      axios
        .get(`post/userrecommend/${userNumber}`, {
          params: {
            limit: this.limit,
          },
        })
        .then((response) => {
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
  },
  computed: {
    searchImg() {
      return this.isSearch
        ? require("@/assets/search_darkgreen.png")
        : require("@/assets/search_green.png");
    },
  },
  created() {
    axios.get('user/my')
    .then(res => {
      this.myNumber = res.data.userNumber
    })
    .catch(err => {
      console.error(err);
    })
  }
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
