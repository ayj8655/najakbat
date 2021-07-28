<template>
  <div class="row">
    <div class="col-2">
      <img
        src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Curved_Arrow.svg/2290px-Curved_Arrow.svg.png"
        width="25px"
      />
    </div>
    <div class="col-10">
      <div class="head">
        <span>{{ comment.user_number }}</span>
        <span><img id="messageBtn" /></span>
        <span>{{ comment.regtime }}</span>
      </div>
      <div
        v-if="comment.isdeleted"
        class="deleted"
        v-html="`삭제된 댓글입니다.`"
      ></div>
      <div v-else class="body" v-html="enterToBr(comment.content)"></div>
      <div class="foot" v-if="!comment.isdeleted">
        <div>
          <span>{{ comment.recommend }}</span>
          <span></span>
        </div>
        <div class="btn" v-if="comment.user_number == this.$store.state.userinfo.id">
          <label @click="modifyCommentChildView">수정</label> |
          <label @click="deleteCommentChild">삭제</label>
        </div>
        <div class="btn" v-else>
          <label @click="recommendCommentChild">좋아요</label>
          <!-- | <label @click="writeComment">답글</label> -->
        </div>
      </div>
    </div>
    <hr>
  </div>
</template>

<script>
  // import http from "@/util/http-common";

  export default {
    name: "comment",
    data() {
      return {
        isShow2: true,
      };
    },
    props: {
      comment: Object,
    },
    methods: {
      modifyCommentChildView() {
        // this.$emit("modify-comment-child", {
        //   no: this.comment.no,
        //   articleno: this.comment.articleno,
        //   id: this.comment.id,
        //   content: this.comment.content,
        // });
      },
      deleteCommentChild() {
        console.log(this.comment.no);
        if (confirm("정말로 삭제하시겠습니까?")) {
          // http.delete(`comment/${this.comment.comment_number}`).then(() => {
          //   alert("삭제되었습니다.");
          //   window.location.reload();
          // });
        }
      },
      enterToBr(str) {
        if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
      },
    },
  };
</script>

<style></style>
