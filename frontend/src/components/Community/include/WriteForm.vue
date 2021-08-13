<template>
  <div id="writeform" class="mt-5 mb-5">
    <div class="form-group mt-3" align="left">
      <label for="sel1">게시글 분류 <span class="text-danger">*</span></label>
      <select class="form-control" id="sel1" v-model="postType">
        <option value="0">게시글 분류를 선택하세요.</option>
        <option value="자유">자유</option>
        <option value="정보">정보</option>
        <option value="질문">질문</option>
        <option value="나눔">나눔</option>
      </select>
    </div>
    <div class="form-group mt-3" align="left">
      <label for="title">제목 <span class="text-danger">*</span></label>
      <input
        type="text"
        class="form-control"
        id="title"
        name="title"
        v-model="title"
        placeholder="제목을 입력하세요."
      />
    </div>
    <div class="form-group mt-3" align="left">
      <label for="content">내용 <span class="text-danger">*</span></label>
      <textarea
        class="form-control"
        rows="15"
        id="content"
        name="content"
        v-model="content"
        placeholder="내용을 입력하세요."
      ></textarea>
    </div>
    <div class="mt-3" id="files-area">
      <form>
        <div class="mb-3" align="left">
          <label for="formFileMultiple" class="form-label"
            >사진 첨부</label
          >
          <div class="text-success">최대 다섯 장까지 첨부 가능합니다. :)</div>
          <input
            class="form-control"
            type="file"
            id="files"
            ref="files"
            v-on:change="fileSlc"
            multiple
          />
        </div>
      </form>
      <div v-if="files.length" align="left">
        <div v-for="(file, index) in files" :key="index">
          <span id="file-name" v-if="file.originFile">{{file.originFile}}</span>
          <span id="file-name" else>{{file.name}}</span>
          <span id="file-delete" @click="removeFile(file)">-</span>
        </div>
      </div>
    </div>
    <div class="form-group mt-3" align="left">
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
      deletedFiles: [],
      updatedFiles: [],
      files: [],
      alertFlag: false,
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
        // console.log(data);
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
        this.files = (data.photos.length)? data.photos:[];
      });
    }
  },
  methods: {
    removeFile(item) {
      this.deletedFiles[this.deletedFiles.length] = item.photoNumber;
      let index = this.files.indexOf(item);
      this.files.splice(index, 1);
      // console.log(this.files);
      // console.log(this.deletedFiles);
    },
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
      // console.log(this.files);
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
      const formData = new FormData();
      formData.append("type", this.postType);
      formData.append("title", this.title);
      formData.append("content", this.content);
      formData.append("keyword", this.keyword);
      formData.append("user_nickname", this.userNickname);
      formData.append("user_number", this.userNumber);
      if(this.updatedFiles.length) {
        this.updatedFiles.forEach((u, i) => {
          formData.append("insertimage", this.updatedFiles[i]);
        });
      }
      if(this.deletedFiles.length) {
        this.deletedFiles.forEach((d, i) => {
          formData.append("deleteimage", this.deletedFiles[i]);
        });
      }
      axios
        .put(`post/${this.postNumber}`, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then(() => {
          this.$router.push(`/community/detail/${this.postNumber}`);
        });
    },
    fileSlc() {
      if (this.type === "create") {
        this.files = this.$refs.files.files;
        if(this.files.length > 5) {
          this.alertFlag = true;
          this.files = [];
        } else {
          this.alertFlag = false;
        }
      } else {
        this.updatedFiles = this.$refs.files.files;
          if(this.files.length+this.updatedFiles.length > 5) {
            this.alertFlag = true;
            this.updatedFiles = [];
          } else {
            this.alertFlag = false;
          }
      }
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
