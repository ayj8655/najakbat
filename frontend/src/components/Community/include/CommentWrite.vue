<template>
  <div>
    <div class="regist_form">
      <textarea
        id="content"
        name="content"
        v-model="content"
        placeholder="댓글 내용을 작성하세요."
      ></textarea>
      <button type="button" class="btn btn-success" :disabled="this.content.length == 0" @click="registComment">
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
      if (!localStorage.getItem("userNumber")) {
        // alert("로그인 후 이용해주세요.");
        this.$router.push("/user/login");
      } else {
        axios
          .post(`comment/?postno=${this.no}&user_number=${this.$store.state.userNumber}&parent=0`,
            {
              postno: this.no,
              userNumber: Number(this.$store.state.userNumber),
              userNickname: this.$store.state.userNickname,
              parent: 0,
              content: this.content,
            },
          )
          .then((data) => {
            // alert("댓글이 등록되었습니다.");
            // window.location.reload();
            if(data.data=="success") {
              this.content = "";
              this.$emit("reload-comment");
            }
          });
      }
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
