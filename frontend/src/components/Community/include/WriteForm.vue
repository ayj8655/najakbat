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
          <label for="formFileMultiple" class="form-label">사진 첨부</label>
          <div v-if="alertFlag" class="text-danger">최대 다섯 장까지 첨부 가능합니다. :(</div>
          <div v-else class="text-success">최대 다섯 장까지 첨부 가능합니다. :)</div>
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
      <div v-if="originFiles.length" align="left">
        <div id="file-item" v-for="(file, index) in originFiles" :key="index">
          <span id="file-name">{{
            file.originFile
          }}</span>
          <span id="file-delete" @click="removeFile(0, index)">×</span>
        </div>
      </div>
      <div v-if="files.length" align="left">
        <div id="file-item" v-for="(file, index) in files" :key="index" v-show="fileFlags[index]">
          <span id="file-name">{{
            file.name
          }}</span>
          <span id="file-delete" @click="removeFile(1, index)">×</span>
        </div>
      </div>
    </div>
    <div class="form-group mt-4 mb-5" align="left">
      <label for="sel1">키워드 등록</label>
          <div v-if="keywordFlag" class="text-danger">최대 5개 키워드 등록 가능합니다 :(</div>
          <div v-else class="text-success">최대 5개 키워드 등록 가능합니다 :)</div>
      <!-- <select class="form-control" id="sel1" v-model="keyword">
        <option v-for="(crop, index) in crops" :key="index" :value="crop.cropNumber">
          {{ crop.name }}
        </option>
      </select> -->
      <input
        type="text"
        class="form-control"
        id="keyword"
        name="keyword"
        v-model="keyword"
        placeholder="키워드를 입력하세요."
        @keypress="putIntoKeywords($event)"
      />
      <div id="keyword-result" class="mt-4 mb-3">
        <div
          style="display: inline-block"
          id="keyword-result-item"
          v-for="(key, index) in keywords"
          :key="index"
        >
          {{ key }}<span id="key-delete" @click="deleteKey(index)">×</span>
        </div>
      </div>
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
      originFiles: [],
      files: [],
      deletedFiles: [],
      alertFlag: false,
      fileFlags: [],
      keyword: "",
      keywords: [],
      keywordOrigin: "",
      keywordFlag: false,
      // crops: [],
    };
  },
  created() {
    // axios.get("guide/plant/summary").then((data) => {
    //   this.crops = data.data;
    // });
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
        this.originFiles = data.photos.length ? data.photos : [];
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
      // console.log(this.files);
      const formData = new FormData();
      formData.append("type", this.postType);
      formData.append("title", this.title);
      formData.append("content", this.content);
      formData.append("keyword", this.keywordOrigin);
      formData.append("user_nickname", this.userNickname);
      formData.append("user_number", this.userNumber);
      this.files.forEach((f, i) => {
        if(this.fileFlags[i]) formData.append("image", this.files[i]);
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
      formData.append("keyword", this.keywordOrigin);
      formData.append("user_nickname", this.userNickname);
      formData.append("user_number", this.userNumber);
      if (this.files.length) {
        this.files.forEach((u, i) => {
          if(this.fileFlags[i]) formData.append("insertimage", this.files[i]);
        });
      }
      if (this.deletedFiles.length) {
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
      let inputFiles = this.$refs.files.files;
      let fileCnt = 0;
      this.fileFlags.forEach(f => {
        if(f) fileCnt += 1;
      });
        if (this.originFiles.length + fileCnt + inputFiles.length > 5) {
          this.alertFlag = true;
          inputFiles = null;
        } else {
          this.alertFlag = false;
          this.files = inputFiles;
          for (let index = 0; index < this.files.length; index++){
            this.fileFlags[index] = true;
          }
        }
    },
    removeFile(num, index) {
      if(num==0) { //this.type === "modify"
        this.deletedFiles[this.deletedFiles.length] = this.originFiles[index].photoNumber;
        this.originFiles.splice(index, 1);
      } else {
        let fs = this.files;
        this.files = {};
        this.fileFlags[index] = false;
        this.files = fs;
        // console.log(index);
      }
    },
    putIntoKeywords(event) {
      if(this.keywords.length>=5) {
        this.keywordFlag = true;
        return;
      }
      if (event.keyCode === 32 || event.keyCode == 44) {
        let key =
          this.keyword.includes(",") || this.keyword.includes(" ")
            ? this.keyword.substring(1, this.keyword.length)
            : this.keyword;
        if (key.length > 0 && this.keywords.indexOf(key)==-1) {
          this.keywords[this.keywords.length] = key;
          this.keywordOrigin += key + ",";
        }
        this.keyword = "";
        // console.log(this.keywordOrigin);
      }
    },
    deleteKey(index) {
      this.keywords.splice(index, 1);
      this.keywordOrigin = "",
      this.keywords.forEach(k => {
        this.keywordOrigin += k;
        this.keywordOrigin += ",";
      });
    }
  },
};
</script>

<style scoped>
#file-item,
#keyword-result-item {
  border: 1px solid #999999;
  border-radius: 15px;
  padding: 10px;
  margin: 5px;
}
#file-delete,
#key-delete {
  background: gray;
  color: white;
  margin: 5px;
  padding: 5px;
  border-radius: 5px;
}
</style>
