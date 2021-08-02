<template>
  <div class="comment" v-if="comment.commentNumber==0 || !comment.parent">
    <div v-if="isShow" class="comment-area">
      <div class="head">
        <span>{{ comment.userNickname }}</span>
        <span><img id="messageBtn" src="@/assets/message.png" width="20px"/></span>
        <span>{{ comment.date }}</span>
      </div>
      <div
        v-if="comment.isdeleted"
        class="deleted"
        v-html="`삭제된 댓글입니다.`"
      ></div>
      <div v-else class="body" v-html="enterToBr(comment.content)"></div>
      <div class="foot">
        <div>
          <span><img src="@/assets/leaf_gray.png" width="15px">{{ comment.recommend }}</span>
          <!-- <span>{{ comment.commentCount }}</span> -->
        </div>
        <div class="btn" v-if="!comment.isdeleted">
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
    <div v-else class="regist_form">
      <textarea
        id="content"
        name="content"
        v-model="modifyComment.content"
        cols="35"
        rows="2"
      ></textarea>
      <button class="small" @click="updateComment">수정</button>
      <button class="small" @click="updateCommentCancel">취소</button>
      <hr>
    </div>
  </div>
  <div v-else class="row">
    <div class="col-2">
      <img src="@/assets/comment_arrow.png" width="15px" />
    </div>
    <div class="col-10" v-if="isShow">
      <div class="head">
        <span>{{ comment.userNickname }}</span>
        <span><img id="messageBtn" src="@/assets/message.png" width="20px"/></span>
        <span>{{ comment.date }}</span>
      </div>
      <div
        v-if="comment.isdeleted"
        class="deleted"
        v-html="`삭제된 댓글입니다.`"
      ></div>
      <div v-else class="body" v-html="enterToBr(comment.content)"></div>
      <div class="foot">
        <div>
          <span><img src="@/assets/leaf_gray.png" width="15px">{{ comment.recommend }}</span>
          <span></span>
        </div>
        <div class="btn" v-if="!comment.isdeleted">
          <label @click="modifyCommentView">수정</label> |
          <label @click="deleteComment">삭제</label>
        </div>
        <div class="btn" v-else>
          <label @click="recommendComment">좋아요</label> |
          <label @click="writeCommentChild">답글달기</label>
        </div>
      </div>
    </div>
    <div v-else class="col-10 regist_form">
      <textarea
        id="content"
        name="content"
        v-model="modifyComment.content"
        cols="35"
        rows="2"
      ></textarea>
      <button class="small" @click="updateComment">수정</button>
      <button class="small" @click="updateCommentCancel">취소</button>
    </div>
    <hr />
  </div>
</template>

<script>
import axios from "axios";
// import http from "@/util/http-common";
// import CommentChild from "@/components/Community/include/CommentChild";
// import CommentChildWrite from "@/components/Community/include/CommentChildWrite";

export default {
  name: "comment",
  components: {
    // CommentChild,
    // CommentChildWrite,
  },
  props: {
    comment: Object,
  },
  data() {
    return {
      isShow: true,
      isModifyShow2: false,
      modifyComment: Object,
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
      // this.$emit("modify-comment", {
      //   no: this.comment.comment_number,
      //   content: this.comment.content,
      // });
      this.modifyComment = this.comment;
      this.isShow = false;
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
    updateComment() {
      axios
        .put(`comment/${this.modifycomment.comment_number}`, {
          comment_number: this.modifycomment.comment_number,
          content: this.modifyComment.content,
        })
        .then(() => {
          alert("댓글이 수정되었습니다.");
          window.location.reload();
        });
    },
    updateCommentCancel() {
      this.isShow = true;
    },
  },
};
</script>

<style></style>
