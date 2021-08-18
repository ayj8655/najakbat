<template>
  <div class="container mt-2">
    <div class="container mt-5" v-if="!isLogin">
      <img src="@/assets/자물쇠.png" alt="" class="w-50" />
      <h3 class="mt-3">회원 전용 페이지입니다</h3>
      <div class="d-grid gap-2 mt-5">
        <button class="btn btn-success" type="button" @click="pushLogin">
          로그인 하기
        </button>
        <button class="btn btn-success" type="button" @click="pushSignup">
          회원가입 하기
        </button>
      </div>
    </div>
    <div v-else>
      <div id="post-area">
        <div id="post-head">
          <div class="d-flex justify-content-start">
            <div id="category"><img :src="this.typeimg" width="35px" /></div>
            <div class="mx-1" v-if="this.post.finish">
              <img src="../../assets/complate.png" width="35px" />
            </div>
          </div>
          <div class="mt-2 mb-2" id="title">{{ this.post.title }}</div>
          <div>
            <span id="left">{{ this.post.userNickname }}</span>
            <span id="right">
              <img src="@/assets/view.png" width="15px" />{{ this.post.view }} |
              <span v-text="changeDate(post.date)" />
            </span>
          </div>
          <hr />
        </div>
        <div class="m-2" id="post-body">
          <div v-html="enterToBr(this.post.content)"></div>
          <Picture :images="post.photos" />
        </div>
        <div id="keyword-area">
          <div id="keyword-result" class="m-2">
            <!-- <p>키워드: </p> -->
            <div
              style="display: inline-block"
              id="keyword-result-item"
              v-for="(key, index) in keywords"
              :key="index"
            >
              # {{ key }}
            </div>
          </div>
        </div>
        <div id="post-foot">
          <span>
            <img
              src="@/assets/leaf_lightgreen.png"
              width="15px"
              v-if="this.recoPostFlag"
              @click="recommendPost()"
            />
            <img
              src="@/assets/leaf_gray.png"
              width="15px"
              v-else
              @click="recommendPost()"
            />
            {{ this.post.recommend }}
          </span>
          <span>
            <img src="@/assets/comment_green.png" width="15px" />{{
              this.comments.length
            }}
          </span>
          <div
            id="right"
            v-if="this.$store.state.userNumber == this.post.userNumber"
          >
            <span class="modifyBtn m-0" @click="modifyPost">수정</span> |
            <span class="deleteBtn m-0" @click="deletePost">삭제</span>
            <span
              class="m-0"
              @click="complatePost"
              v-if="
                !this.post.finish &&
                (this.post.postType == 3 || this.post.postType == 4)
              "
            >
              |<img src="@/assets/complate.png" width="35px"
            /></span>
          </div>
        </div>
      </div>
      <div id="comments-area">
        <hr />
        <comment
          v-for="(comment, index) in comments"
          :key="index"
          :comment="comment"
          :col="!comment.parent ? 12 : 11"
          :recoFlag="wasRecommended(comment.commentNumber)"
          @modify-comment="onModifyComment"
          @reload-comment="reloadComment"
        ></comment>
        <comment-write
          v-if="isModifyShow && this.modifyComment != null"
          :modifyComment="this.modifyComment"
          @modify-comment-cancel="onModifyCommentCancel"
          @reload-comment="reloadComment"
        />
        <comment-write
          :no="this.no"
          @reload-comment="reloadComment"
        ></comment-write>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Comment from "@/components/Community/include/Comment.vue";
import CommentWrite from "@/components/Community/include/CommentWrite.vue";
import Picture from "@/components/Community/include/Picture.vue";
import router from "@/router";

export default {
  name: "CommunityDetail",
  components: {
    Comment,
    CommentWrite,
    Picture,
  },
  props: {
    // post: Object,
  },
  data() {
    return {
      no: this.$route.params.no,
      post: Object,
      typeimg: null,
      photos: [],
      recoPostFlag: false,
      comments: [],
      recoComments: [],
      isModifyShow: false,
      modifyComment: Object,
      isLogin: this.$store.state.accessToken,
      keywords: [],
    };
  },
  created() {
    if (this.$store.state.accessToken) {
      axios.get(`post/${this.no}`).then(({ data }) => {
        this.post = data;
        this.keywords = this.post.keyword.split(",");
        // console.log(this.post);
        switch (this.post.postType) {
          case 1:
            this.typeimg = require("@/assets/category_free.png");
            break;
          case 2:
            this.typeimg = require("@/assets/category_info.png");
            break;
          case 3:
            this.typeimg = require("@/assets/category_question.png");
            break;
          case 4:
            this.typeimg = require("@/assets/category_share.png");
            break;
        }
      });
      axios
        .get(`post/like?user_number=${this.$store.state.userNumber}`)
        .then((data) => {
          // console.log(data.data);
          data.data.forEach((d) => {
            if (d.postNumber == this.no) this.recoPostFlag = true;
          });
        });
      axios.get(`comment/${this.no}`).then(({ data }) => {
        this.comments = data;
      });
      axios
        .get(`comment/${this.no}/${this.$store.state.userNumber}`)
        .then(({ data }) => {
          this.recoComments = data;
        })
        .catch((err) => {
          err;
        });
    }
  },
  methods: {
    pushLogin() {
      router.push({ name: "Login" });
    },
    pushSignup() {
      router.push({ name: "Signup" });
    },
    changeDate(str) {
      if (str) return str.substring(0, 10) + " " + str.substring(11, 19);
    },
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    recommendPost() {
      axios
        .put(
          `post/recommend/${this.no}?user_number=${this.$store.state.userNumber}`
        )
        .then((data) => {
          // console.log(data.data);
          if (data.data == "success") {
            this.recoPostFlag = !this.recoPostFlag;
            if (this.recoPostFlag) {
              this.post.recommend++;
            } else {
              this.post.recommend--;
            }
          }
        });
    },
    modifyPost() {
      this.$router.push(`/community/modify/${this.post.postNumber}`);
    },
    deletePost() {
      if (confirm("정말로 삭제하시겠습니까?")) {
        axios.delete(`post/${this.post.postNumber}`).then(() => {
          // alert("삭제되었습니다.");
          this.$router.push("/community/list");
        });
      }
    },
    wasRecommended(no) {
      let flag = false;
      this.recoComments.forEach((reco) => {
        if (reco == no) flag = true;
      });
      return flag;
    },
    onModifyComment(comment) {
      this.modifyComment = comment;
      this.isModifyShow = true;
    },
    onModifyCommentCancel(isShow) {
      this.isModifyShow = isShow;
    },
    complatePost() {
      axios
        .put(`post/finish/${this.no}`)
        .then((res) => {
          this.post.finish = true;
          res;
        })
        .catch((err) => {
          console.error(err);
        });
    },
    reloadComment() {
      this.comments = [];
      axios.get(`comment/${this.no}`).then(({ data }) => {
        this.comments = data;
      });
    },
  },
  computed: {},
};
</script>

<style scoped>
.container > * {
  font-family: Noto Sans KR;
  font-style: normal;
}
#post-area {
  text-align: left;
}
#title {
  font-style: large;
  font-weight: bold;
}
#left {
  color: #999999;
}
#right {
  float: right;
  display: inline-block;
  color: #999999;
}
#right img {
  margin: 5px;
}
#post-foot span {
  margin-right: 10px;
}
#post-foot img {
  margin-right: 5px;
}
#keyword-result-item {
  border: 1px solid #999999;
  border-radius: 15px;
  padding: 10px;
  margin: 5px;
  color: #999999;
}
</style>
