<template>
  <div id="post-area" v-if="this.post!==null">
    <div class="m-2" id="post-head">
      <span id="left1">
        <img :src="this.typeimg" width="35px" />
        <img
          src="@/assets/complate.png"
          width="35px"
          class="ms-1"
          v-if="post.isFinish"
        />
        <img
          src="@/assets/complate.png"
          width="35px"
          class="ms-1"
          v-if="post.finish"
        />
        {{ post.title }}
      </span>
      <span id="right1">
        {{ post.userNickname }} | <span v-text="changeDate(post.date)" />
      </span>
    </div>
    <div class="m-3" id="post-body">
      <span v-text="splitContent(post.content)"></span>
      <span id="more" @click="movePage">...더보기</span>
    </div>
    <div id="thumbnail-area" v-if="this.photo">
      <img :src="photo">
    </div>
    <div id="keyword-area" v-if="this.keywords[0] != ''">
      <div id="keyword-result" class="m-2">
        <!-- <p>키워드: </p> -->
        <div
          style="display: inline-block"
          id="keyword-result-item"
          v-for="(key, index) in keywords"
          :key="index"
        >
          # {{ key }}
        </div>
      </div>
    </div>
    <div class="m-2" id="post-foot">
      <span id="left2"
        ><img src="@/assets/view_green.png" width="15px" />{{ post.view }}</span
      >
      <span id="right2">
        <span
          ><img src="@/assets/leaf_lightgreen.png" width="15px" />{{
            post.recommend
          }}</span
        >
        <span
          ><img src="@/assets/comment_green.png" width="15px" />{{
            post.commentCount
          }}</span
        >
      </span>
    </div>
    <hr />
  </div>
  <div v-else></div>
</template>

<script>
export default {
  name: "listrow",
  data() {
    return {
      type: null,
      typeimg: null,
      keywords: [],
    };
  },
  props: {
    post: Object,
    photo: String,
  },
  created() {
    // console.log(this.post);
    // console.log(this.photo);
    if(this.post!==null) {
      this.keywords = this.post.keyword.split(",");
      if(this.keywords[this.keywords.length-1]=="") this.keywords.splice(this.keywords.length-1, 1);
      switch (this.post.postType) {
        case 1:
          this.typeimg = require("@/assets/category_free.png");
          break;
        case 2:
          this.typeimg = require("@/assets/category_info.png");
          break;
        case 3:
          this.typeimg = require("@/assets/category_question.png");
          break;
        case 4:
          this.typeimg = require("@/assets/category_share.png");
          break;
      }
    // return this.typeimg;
    }
  },
  methods: {
    changeDate(str) {
      if(str) return str.substring(0, 10) + " " + str.substring(11, 19);
    },
    movePage: function () {
      this.$router.push(`/community/detail/${this.post.postNumber}`);
    },
    splitContent(content) {
      if(content) return content.substring(0, 51);
    },
  },
};
</script>

<style scoped>
#post-area {
  font-family: Noto Sans KR;
  margin: 5px;
  text-align: left;
}
#left1 {
  font-family: Noto Sans KR;
  font-style: normal;
  font-weight: bold;
}
#right1 {
  font-size: 9px;
  float: right;
  display: inline-block;
}
#left2 {
  font-family: Noto Sans KR;
  font-style: normal;
}
#right2 {
  float: right;
  display: inline-block;
}
#more {
  color: #999999;
}
#post-foot span {
  margin: 5px;
}
#post-foot img {
  margin-right: 5px;
}
#thumbnail-area {
  display: block; 
  overflow: hidden; 
  max-height: 200px; 
  width: 100%;
  border: 1px solid #a9a9a9;
  border-radius: 15px;
}
#thumbnail-area img {
  display: block; /* Otherwise it keeps some space around baseline */ 
  width: 100%; /* Scale up to fill container width */ 
  min-height: 100%; /* Scale up to fill container height */ 
  -ms-interpolation-mode: bicubic; /* Scaled images look a bit better in IE now */
}
#keyword-result-item {
  /* border: 1px solid #999999; */
  border-radius: 15px;
  padding: 10px;
  margin: 5px;
  background-color: #CAC8C8;
  color:#ffffff;
}
</style>
