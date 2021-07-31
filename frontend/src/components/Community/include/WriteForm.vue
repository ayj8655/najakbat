<template>
  <div id="writeform" class="mt-5 mb-5">
    <div class="form-group"  align="left">
      <label for="sel1">게시글 분류</label>
      <select class="form-control" id="sel1" v-model="postType">
        <option value="0">게시글 분류를 선택하세요.</option>
        <option value=1>자유</option>
        <option value=2>정보</option>
        <option value=3>질문</option>
        <option value=4>나눔</option>
      </select>
    </div>
    <div class="form-group" align="left">
      <label for="title">제목:</label>
      <input
        type="text"
        class="form-control"
        id="title"
        name="title"
        v-model="title"
        placeholder="제목을 입력하세요."
      />
    </div>
    <div class="form-group" align="left">
      <label for="content">내용:</label>
      <textarea
        class="form-control"
        rows="15"
        id="content"
        name="content"
        v-model="content"
        placeholder="내용을 입력하세요."
      ></textarea>
    </div>
    <form>
  <div class="custom-file">
      <label for="customFile">사진첨부:</label>
    <input type="file" class="custom-file-input" id="customFile">
    <label class="custom-file-label" for="customFile">첨부할 사진을 선택하세요. (최대 5개)</label>
  </div>
</form>
    <div class="form-group" align="left">
      <label for="sel1">농작물 태그</label>
      <select class="form-control" id="sel1" v-model="keyword">
        <option>농작물 태그를 원하시면 선택하세요.</option>
        <option>자유</option>
        <option>정보</option>
        <option>질문</option>
        <option>나눔</option>
      </select>
    </div>
    <div v-if="type == 'create'">
      <button
        @click="checkValid"
        type="button"
        id="writeBtn"
        class="btn btn-primary m-2"
      >
        작성
      </button>
    </div>
    <div v-else>
      <button class="btn btn-danger m-2" @click="checkValid">수정</button>
      <!-- <button type="button" class="btn btn-warning m-2" @click="deleteArticle">삭제</button> -->
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "writeform",
  props: {
    type: String,
  },
  data() {
    return {
      postNumber: this.$route.params.no,
      userNumber: this.$store.userNumber,
      postType: 0,
      title: null,
      content: null,
      files: null,
      keyword: null,
    }
  },
  created() {
      if (this.type === "modify") {
        axios.get(`post/${this.postNumber}`).then(({ data }) => {
          this.postType = data.postType;
          this.title = data.title;
          this.content = data.content;
          this.keyword = data.keyword;
        });
      }
    },
  methods: {
    checkValid() {
        let err = true;
        let msg = "";
        !(this.postType>0) && ((msg="게시글 분류를 선택해주세요."), (err = false));
        err && !this.title && ((msg = "제목을 입력해주세요."), (err = false));
        err && !this.content && ((msg = "내용을 입력해주세요."), (err = false));
        if (!err) alert(msg);
        else this.type == "create" ? this.writePost() : this.modifyPost();
    },
    writePost() {
        axios
          .post("post/", {
            userNumber: this.userNumber,
            userNickname: "hi",
            postType: this.postType,
            title: this.title,
            content: this.content,
            keyword: this.keyword,
          })
          .then(() => {
            this.$router.push("/community/list");
          });
      },
      modifyPost() {
        axios
          .put(`post/${this.postNumber}`, {
            postNumber: this.postNumber,
            userNumber: this.userNumber,
            title: this.title,
            content: this.content,
            keyword: this.keyword,
          })
          .then(() => {
            this.$router.push(`/community/detail/${this.postNumber}`);
          });
      },
  },
};
// $(".custom-file-input").on("change", function() {
//   var fileName = $(this).val().split("\\").pop();
//   $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
// });
</script>

<style></style>
