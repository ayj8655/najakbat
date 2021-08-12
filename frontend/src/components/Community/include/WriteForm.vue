<template>
  <div id="writeform" class="mt-5 mb-5">
    <div class="form-group" align="left">
      <label for="sel1">게시글 분류</label>
      <select class="form-control" id="sel1" v-model="postType">
        <option value="0">게시글 분류를 선택하세요.</option>
        <option value="자유">자유</option>
        <option value="정보">정보</option>
        <option value="질문">질문</option>
        <option value="나눔">나눔</option>
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
      <div class="mb-3">
        <label for="formFileMultiple" class="form-label"
          >사진 첨부: </label
        >
        <input
          class="form-control"
          type="file"
          id="formFileMultiple"
          ref="files"
          v-on:change="fileSlc"
          multiple
        />
      </div>
    </form>
    <div class="form-group" align="left">
      <label for="sel1">농작물 태그</label>
      <select class="form-control" id="sel1" v-model="keyword">
        <option v-for="(crop, index) in crops" :key="index" :value="crop.cropNumber">
          {{ crop.name }}
        </option>
      </select>
    </div>
    <div v-if="type == 'create'">
      <button
        @click="checkValid"
        type="button"
        id="writeBtn"
        class="btn btn-success m-2"
      >
        작성
      </button>
    </div>
    <div v-else>
      <button class="btn btn-success m-2" @click="checkValid">수정</button>
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
      user: null,
      postNumber: this.$route.params.no,
      userNumber: this.$store.state.userNumber,
      userNickname: this.$store.state.userNickname,
      postType: 0,
      title: null,
      content: null,
      files: [],
      keyword: null,
      crops: [],
    };
  },
  created() {
    axios.get("guide/plant/summary").then((data) => {
      this.crops = data.data;
    });
    if (this.type === "modify") {
      axios.get(`post/${this.postNumber}`).then(({ data }) => {
        console.log(data);
        switch (data.postType) {
          case 1:
            this.postType = "자유";
            break;
          case 2:
            this.postType = "정보";
            break;
          case 3:
            this.postType = "질문";
            break;
          case 4:
            this.postType = "나눔";
            break;
        }
        this.title = data.title;
        this.content = data.content;
        this.keyword = data.keyword;
        this.files = data.photos;
      });
    }
  },
  methods: {
    checkValid() {
      let err = true;
      let msg = "";
      !(this.postType != "0") &&
        ((msg = "게시글 분류를 선택해주세요."), (err = false));
      err && !this.title && ((msg = "제목을 입력해주세요."), (err = false));
      err && !this.content && ((msg = "내용을 입력해주세요."), (err = false));
      if (!err) alert(msg);
      else this.type == "create" ? this.writePost() : this.modifyPost();
    },
    writePost() {
      console.log(this.files);
      const formData = new FormData();
      formData.append("type", this.postType);
      formData.append("title", this.title);
      formData.append("content", this.content);
      formData.append("keyword", this.keyword);
      formData.append("user_nickname", this.userNickname);
      formData.append("user_number", this.userNumber);
      this.files.forEach((f, i) => {
        formData.append("image", this.files[i]);
      });
      axios
        .post("post/", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((data) => {
          if (data.data == "success") this.$router.push("/community/list");
        });
    },
    modifyPost() {
      axios
        .put(`post/${this.postNumber}`, {
          postNumber: this.postNumber,
          userNumber: this.userNumber,
          userNickname: this.userNickname,
          title: this.title,
          content: this.content,
          keyword: this.keyword,
        })
        .then(() => {
          this.$router.push(`/community/detail/${this.postNumber}`);
        });
    },
    fileSlc() {
      this.files = this.$refs.files.files;
      // console.log(this.$refs);
      // console.log(this.files);
    },
  },
};
</script>

<style scoped>
.writeform > * {
  font-family: Noto Sans KR;
  font-style: normal;
}
</style>
