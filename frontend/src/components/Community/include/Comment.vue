<template>
<div>
  <div class="comment-area">
    <div class="head">
      <span>{{ comment.user_number }}</span>
      <span><img id="messageBtn"></span>
      <span>{{ comment.regtime }}</span>
    </div>
    <div
      v-if="comment.isdeleted"
      class="deleted"
      v-html="`삭제된 댓글입니다.`"
    ></div>
    <div v-else class="body" v-html="enterToBr(comment.content)"></div>
    <div class="foot">
      <div>
        <span>{{comment.recommend}}</span>
        <span></span>
      </div>
      <div class="btn" v-if="comment.id == this.$store.state.userinfo.id">
        <label @click="modifyCommentView">수정</label> |
        <label @click="deleteComment">삭제</label>
      </div>
      <div class="btn" v-else>
        <label @click="recommendComment">좋아요</label> |
        <label @click="writeComment">답글</label>
      </div>
    </div>
    <hr>
  </div>
  <div id="childs-area">
    <comment-child></comment-child>
    <comment-child-write></comment-child-write>
  </div>
</div>
</template>

<script>
// import http from "@/util/http-common";
import CommentChild from "@/components/Community/include/CommentChild";
import CommentChildWrite from "@/components/Community/include/CommentChildWrite";

export default {
  name: "comment",
  components: {
    CommentChild,
    CommentChildWrite,
  },
  props: {
    comment: Object,
  },
  data() {
    return {
      childs: [],
    };
  },
  methods: {
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    modifyCommentView() {
      // this.$emit("modify-comment", {
      //   no: this.comment.comment_number,
      //   articleno: this.comment.articleno,
      //   id: this.comment.id,
      //   content: this.comment.content,
      // });
    },
    deleteComment() {
      console.log(this.comment.comment_number);
      // if (confirm("정말로 삭제하시겠습니까?")) {
      //   http
      //     .delete(`comment/${this.comment.articleno}/${this.comment.comment_number}`)
      //     .then(() => {
      //       alert("삭제되었습니다.");
      //       window.location.reload();
      //     });
      // }
    },
  },
};
</script>

<style></style>
