<template>
  <div class="comment">
    <div v-show="isShow" class="comment-area">
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
      <div class="foot">
        <div>
          <span>{{ comment.recommend }}</span>
          <span></span>
        </div>
        <div
          class="btn"
          v-if="!comment.isdeleted"
        >
          <label @click="modifyCommentView">수정</label> |
          <label @click="deleteComment">삭제</label>
        </div>
        <div class="btn" v-else>
          <label @click="recommendComment">좋아요</label> |
          <label @click="writeCommentChild">답글달기</label>
        </div>
      </div>
      <hr />
    </div>
    <div id="childs-area">
      <comment-child
        v-for="(child, index) in childs"
        :key="index"
        :comment="child"
        @modify-comment-child="onModifyCommentChild"
      />
      <comment-child-write></comment-child-write>
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
      isShow: true,
      childs: [],
      isModifyShow2: false,
      modifyCommentChild: Object,
    };
  },
  created() {
    // http.get(`comment/${this.comment.comment_number}`).then(({data}) => {
    // this.childs = data;
    // });
  },
  methods: {
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    modifyCommentView() {
      this.$emit("modify-comment", {
        no: this.comment.comment_number,
        content: this.comment.content,
      });
    },
    deleteComment() {
      console.log(this.comment.comment_number);
      // if (confirm("정말로 삭제하시겠습니까?")) {
      //   http
      //     .delete(`comment/${this.comment.comment_number}`)
      //     .then(() => {
      //      // alert("삭제되었습니다.");
      //       window.location.reload();
      //     });
      // }
    },
    recommendComment() {},
    writeCommentChild() {},
    onModifyCommentChild(child) {
      this.modifyCommentChild = child;
      this.isModifyShow2 = true;
    },
    onModifyCommentChildCancel(isShow) {
      this.isModifyShow2 = isShow;
    },
  },
};
</script>

<style></style>
