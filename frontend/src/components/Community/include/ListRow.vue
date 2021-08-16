<template>
  <div id="post-area">
    <div class="m-2" id="post-head">
      <span id="left1">
        <img :src="this.typeimg" width="35px" />
        <img src="@/assets/complate.png" width="35px" class="ms-1" v-if="post.isFinish" />
        <img src="@/assets/complate.png" width="35px" class="ms-1" v-if="post.finish" />
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
    <div class="m-2" id="post-foot">
      <span id="left2"><img src="@/assets/view_green.png" width="15px" />{{ post.view }}</span>
      <span id="right2">
        <span><img src="@/assets/leaf_lightgreen.png" width="15px" />{{post.recommend}}</span>
        <span><img src="@/assets/comment_green.png" width="15px" />{{post.commentCount}}</span>
      </span>
    </div>
    <hr />
  </div>
</template>

<script>
export default {
  name: "listrow",
  data() {
    return {
      type: null,
      typeimg: null,
    };
  },
  props: {
    post: Object,
  },
  created() {
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
    return this.typeimg;
  },
  methods: {
    changeDate(str) {
      return str.substring(0, 10) + " " + str.substring(11, 19);
    },
    movePage: function () {
      this.$router.push(`/community/detail/${this.post.postNumber}`);
    },
    splitContent(content) {
      return content.substring(0, 51);
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
#post-foot img{
  margin-right: 5px;
}
</style>
