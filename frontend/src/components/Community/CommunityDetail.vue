<template>
  <div class="container mt-2">
    <div id="post-area">
      <div id="post-head">
        <div id="category"><img :src="this.typeimg" width="35px" /></div>
        <div class="mt-2 mb-2" id="title">{{ this.post.title }}</div>
        <div>
          <span id="left">{{ this.post.userNickname }}</span>
          <span id="right">
            <img src="@/assets/view.png" width="15px"/>{{ this.post.view }} | <span v-text="changeDate(post.date)" />
          </span>
        </div>
        <hr />
      </div>
      <div class="mt-5 mb-5" id="post-body">
        <div v-html="enterToBr(this.post.content)"></div>
      </div>
      <div id="post-foot">
        <span><img src="@/assets/leaf_lightgreen.png" width="15px" />{{this.post.recommend}}</span>
        <span>
          <img src="@/assets/comment_green.png" width="15px" />{{this.post.commentCount}}
        </span>
        <div id="right" v-if="this.$store.state.userNumber == this.post.userNumber">
          <span class="modifyBtn" @click="modifyPost">수정</span> |
          <span class="deleteBtn" @click="deletePost">삭제</span>
        </div>
      </div>
    </div>
    <div id="comments-area">
      <hr />
      <comment
        v-for="(comment, index) in comments"
        :key="index"
        :comment="comment"
        :recoFlag="wasRecommended(comment.commentNumber)"
        @modify-comment="onModifyComment"
      ></comment>
      <comment-write
        v-if="isModifyShow && this.modifyComment != null"
        :modifyComment="this.modifyComment"
        @modify-comment-cancel="onModifyCommentCancel"
      />
      <comment-write :no="this.no"></comment-write>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Comment from "@/components/Community/include/Comment.vue";
import CommentWrite from "@/components/Community/include/CommentWrite.vue";

export default {
  name: "CommunityDetail",
  components: {
    Comment,
    CommentWrite,
  },
  props: {
    // post: Object,
  },
  data() {
    return {
      no: this.$route.params.no,
      post: Object,
      typeimg: null,
      comments: [],
      recoComments: [],
      isModifyShow: false,
      modifyComment: Object,
    };
  },
  created() {
    axios.get(`post/${this.no}`).then(({ data }) => {
      this.post = data;
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
    });
    axios.get(`comment/${this.no}`).then(({ data }) => {
      this.comments = data;
    });
    axios.get(`comment/${this.no}/${this.$store.state.userNumber}`).then(({ data }) => {
      this.recoComments = data;
    });
  },
  methods: {
    changeDate(str) {
      if(str) return str.substring(0, 10) + " " + str.substring(11, 19);
    },
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    modifyPost() {
      this.$router.push(`/community/modify/${this.post.postNumber}`);
    },
    deletePost() {
      if (confirm("정말로 삭제하시겠습니까?")) {
        axios.delete(`post/${this.post.postNumber}`).then(() => {
          // alert("삭제되었습니다.");
          this.$router.push("/community/list");
        });
      }
    },
    wasRecommended(no) {
      let flag = false;
      this.recoComments.forEach(reco => {
        if(reco==no) flag = true;
      });
      return flag;
    },
    onModifyComment(comment) {
      this.modifyComment = comment;
      this.isModifyShow = true;
    },
    onModifyCommentCancel(isShow) {
      this.isModifyShow = isShow;
    },
  },
  computed: {},
};
</script>

<style scoped>
.container > * {
  font-family: Noto Sans KR;
  font-style: normal;
}
#post-area {
  text-align: left;
}
#title {
  font-style: large;
  font-weight: bold;
}
#left {
  color: #999999;
}
#right {
  float: right;
  display: inline-block;
  color: #999999;
}
#right img {
  margin: 5px;
}
#post-foot span {
  margin-right: 10px;
}
#post-foot img{
  margin-right: 5px;
}
</style>
