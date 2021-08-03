<template>
  <div id="comment" v-if="comment.commentNumber==0 || !comment.parent">
    <div v-if="isShow" class="comment-area">
      <div class="head">
        <span id="left">{{ comment.userNickname }}<img id="messageBtn" src="@/assets/message.png" width="20px"/></span>
        <span id="right" v-text="changeDate(this.comment.date)"/>
      </div>
      <div
        v-if="comment.isdeleted"
        class="body"
        id="deleted"
        v-html="`삭제된 댓글입니다.`"
      ></div>
      <div v-else class="body" v-html="enterToBr(comment.content)"></div>
      <div class="foot">
        <span class="recc">
          <span><img src="@/assets/leaf_gray.png" width="15px">{{ comment.recommend }}</span>
          <span><img src="@/assets/comment.png" width="15px">{{ comment.commentCount }}</span>
        </span>
        <span class="btn" v-if="!comment.isdeleted">
          <label @click="modifyCommentView">수정</label> |
          <label @click="deleteComment">삭제</label>
        </span>
        <span class="btn" v-else>
          <label @click="recommendComment">좋아요</label> |
          <label @click="writeCommentChild">답글달기</label>
        </span>
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
  <div v-else id="comment" class="row">
    <div class="col-2">
      <img src="@/assets/comment_arrow.png" width="15px" />
    </div>
    <div class="col-10" v-if="isShow">
      <div class="head">
        <span id="left">{{ comment.userNickname }}<img id="messageBtn" src="@/assets/message.png" width="20px"/></span>
        <span id="right" v-text="changeDate(this.comment.date)"/>
      </div>
      <div
        v-if="comment.isdeleted"
        class="body"
        id="deleted"
        v-html="`삭제된 댓글입니다.`"
      ></div>
      <div v-else class="body" v-html="enterToBr(comment.content)"></div>
      <div class="foot">
        <span class="recc">
          <span><img src="@/assets/leaf_gray.png" width="15px">{{ comment.recommend }}</span>
        </span>
        <span class="btn" v-if="!comment.isdeleted">
          <label @click="modifyCommentView">수정</label> |
          <label @click="deleteComment">삭제</label>
        </span>
        <span class="btn" v-else>
          <label @click="recommendComment">좋아요</label> |
          <label @click="writeCommentChild">답글달기</label>
        </span>
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

export default {
  name: "comment",
  components: {},
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
    changeDate(str) {
      return str.substring(0, 10) + " " + str.substring(11, 19)
    },
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

<style scoped>
#comment {
  margin: 5px;
  text-align: left;
}
.head {
  display: block;
}
#left img {
  margin-left: 5px;
}
#right {
  float: right;
  display: inline-block;
  color: #999999;
}
.body {
  margin: 5px;
}
.recc > span {
  margin-right: 10px;
}
.foot img {
  margin-right: 5px;
}
.foot .btn {
  float: right;
  display: inline-block;
  color: #999999;
}
</style>
