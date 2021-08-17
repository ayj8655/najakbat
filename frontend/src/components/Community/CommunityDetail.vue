<template>
  <div class="container mt-2">
    <div class="container mt-5" v-if="!isLogin">
      <img src="@/assets/자물쇠.png" alt="" class="w-50">
      <h3 class="mt-3">회원 전용 페이지입니다</h3>
      <div class="d-grid gap-2 mt-5">
        <button class="btn btn-success" type="button" @click="pushLogin">로그인 하기</button>
        <button class="btn btn-success" type="button" @click="pushSignup">회원가입 하기</button>
      </div>
    </div>
    <div v-else>
      <div id="post-area">
        <div id="post-head">
          <div class="d-flex justify-content-start">
            <div id="category"><img :src="this.typeimg" width="35px" /></div>
            <div class="mx-1" v-if="this.post.finish"><img src="../../assets/complate.png" width="35px" /></div>
          </div>
          <div class="mt-2 mb-2" id="title">{{ this.post.title }}</div>
          <div>
            <span id="left">{{ this.post.userNickname }}</span>
            <span id="right">
              <img src="@/assets/view.png" width="15px"/>{{ this.post.view }} | <span v-text="changeDate(post.date)" />
            </span>
          </div>
          <hr />
        </div>
        <div class="m-2" id="post-body">
          <div v-html="enterToBr(this.post.content)"></div>
        </div>
        <div class="mt-2 mb-3" id="post-photos" v-if="this.post.photos">
          <div id="row">
            <div class="items" v-for="(p, index) in photos" :key="index">
              <img :src="p" />
              <!-- <p>{{p.originFile}}</p> -->
            </div>
          </div>
        </div>
        <div id="post-foot">
          <span>
            <img src="@/assets/leaf_lightgreen.png" width="15px" v-if="this.recoPostFlag" @click="recommendPost()" />
            <img src="@/assets/leaf_gray.png" width="15px" v-else @click="recommendPost()" />
            {{this.post.recommend}}
          </span>
          <span>
            <img src="@/assets/comment_green.png" width="15px" />{{this.post.commentCount}}
          </span>
          <div id="right" v-if="this.$store.state.userNumber == this.post.userNumber">
            <span class="modifyBtn m-0" @click="modifyPost">수정</span> |
            <span class="deleteBtn m-0" @click="deletePost">삭제</span>
            <span class="m-0" @click="complatePost" v-if="!this.post.finish && (this.post.postType == 3 || this.post.postType == 4)"> |<img src="@/assets/complate.png" width="35px" /></span>
          </div>
        </div>
      </div>
      <div id="comments-area">
        <hr />
        <comment
          v-for="(comment, index) in comments"
          :key="index"
          :comment="comment"
          :col="(!comment.parent)? 12 : 11"
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
        <comment-write :no="this.no" @reload-comment="reloadComment"></comment-write>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Comment from "@/components/Community/include/Comment.vue";
import CommentWrite from "@/components/Community/include/CommentWrite.vue";
import router from "@/router"

export default {
  name: "CommunityDetail",
  components: {
    Comment,
    CommentWrite,
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
      isLogin: this.$store.state.accessToken
    };
  },
  created() {
    if (this.$store.state.accessToken) {
      axios.get(`post/${this.no}`).then(({ data }) => {
        this.post = data;
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
        // console.log(this.post);
        this.post.photos.forEach((p, index) => {
          this.photos[index] = require("@/assets/post/"+p.saveFile);
        });
      });
      axios.get(`comment/${this.no}`).then(({ data }) => {
        this.comments = data;
      });
      axios.get(`comment/${this.no}/${this.$store.state.userNumber}`)
      .then(({ data }) => {
        // console.log(this.$store.state.userNumber);
        this.recoComments = data;
      })
      .catch(err => {
        console.log(this.$store.state.userNumber);
        err
      })
    }
  },
  methods: {
    pushLogin() {
      router.push({name: 'Login'})
    },
    pushSignup() {
      router.push({name: 'Signup'})
    },
    changeDate(str) {
      if(str) return str.substring(0, 10) + " " + str.substring(11, 19);
    },
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    recommendPost() {
      axios.put(`recommend/${this.no}?user_number=${this.$store.state.userNumber}`).then((data)=>{
        console.log(data.data);
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
      this.recoComments.forEach(reco => {
        if(reco==no) flag = true;
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
      axios.put(`post/finish/${this.no}`)
      .then(res => {
        this.post.finish = true
        res
      })
      .catch(err => {
        console.error(err);
      })
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
#row {
  white-space: nowrap; /* 가로스크롤시 중요한 속성 */
  overflow-x: auto;
  overflow-y: hidden;
  padding: 10px 10px 5px;
  /* background: #efefef; */
  width: auto;
}
#row .items {
  display: inline-block;
  /* margin-left: 10px; */
  width: 160px;
}
#row .items img {
  width: 150px;
  border-radius: 5px;
  border: solid 1px #efefef;
}
#post-foot span {
  margin-right: 10px;
}
#post-foot img{
  margin-right: 5px;
}

</style>
