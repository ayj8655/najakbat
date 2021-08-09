<template>
  <div>
    <div class="regist_form">
      <textarea
        id="content"
        name="content"
        v-model="content"
        placeholder="댓글 내용을 작성하세요."
      ></textarea>
      <button type="button" class="btn btn-success" @click="registComment">
        등록
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "CommentWrite",
  props: {
    modifyComment: { type: Object },
  },
  data() {
    return {
      no: this.$route.params.no,
      content: "",
    };
  },
  methods: {
    registComment() {
      axios
        .post(`comment/`, {
          postNumber: this.no,
          // userNumber: 1,
          // userNickname: "ssafy",
          content: this.content,
        })
        .then(() => {
          alert("댓글이 등록되었습니다.");
          window.location.reload();
        });
      // if (this.$store.state.userinfo.id) {
      // } else {
      //   alert("로그인 후 이용해주세요.");
      //   this.$router.push("/user/login");
      // }
    },
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
      this.$emit("modify-comment-cancel", false);
    },
  },
};
</script>

<style scoped>
.regist {
  padding: 10px;
}
.regist_form {
  text-align: center;
  border-radius: 5px;
  background-color: lightgray;
  padding: 10px;
}

textarea {
  width: 80%;
  padding: 10px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  color: #787878;
}

button {
  float: right;
}
</style>
