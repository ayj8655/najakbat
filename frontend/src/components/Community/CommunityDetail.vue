<template>
  <div class="container">
    <div id="post-area">
      <div id="post-head">
        <div id="category"></div>
        <div>{{ this.post.title }}</div>
        <div>
          <span>{{ this.post.userNickname }}</span> |
          <span>
            <!-- <img src="@/assets/view.png"/> -->
            {{ this.post.view }} | {{ this.post.regtime }}
          </span>
        </div>
        <hr />
      </div>
      <div id="post-body">
        <div v-html="enterToBr(this.post.content)"></div>
      </div>
      <div id="post-foot">
        <span><img />{{ this.post.recommend }}</span> |
        <span>
          <!-- <img src="@/assets/comment.png"/> -->
          {{ this.post.commentCount }}
        </span>
        <div>
          <!-- <div v-if="this.$store.user.userNumber == this.post.userNumber"> -->
          <span class="modifyBtn" @click="modifyPost">수정</span> |
          <span class="deleteBtn" @click="deletePost">삭제</span>
        </div>
      </div>
    </div>
    <div id="comments-area">
      <hr />
      <comment
        v-for="(comment, index) in comments"
        :key="index"
        :comment="comment"
        @modify-comment="onModifyComment"
      ></comment>
      <comment-write
        v-if="isModifyShow && this.modifyComment != null"
        :modifyComment="this.modifyComment"
        @modify-comment-cancel="onModifyCommentCancel"
      />
      <comment-write :no="this.no"></comment-write>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import Comment from "@/components/Community/include/Comment.vue";
import CommentWrite from "@/components/Community/include/CommentWrite.vue";

export default {
  name: "CommunityDetail",
  components: {
    Comment,
    CommentWrite,
  },
  props: {
    post: Object,
  },
  data() {
    return {
      no: this.$route.params.no,
      comments: [],
      isModifyShow: false,
      modifyComment: Object,
    };
  },
  created() {
    axios.get(`post/${this.no}`).then(({ data }) => {
      this.post = data;
    });
    axios.get(`comment/${this.no}`).then(({ data }) => {
      this.comments = data;
      console.log(data);
    });
  },
  methods: {
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    modifyPost() {
      this.$router.push(`/community/modify/${this.post.postNumber}`);
    },
    deletePost() {
      if (confirm("정말로 삭제하시겠습니까?")) {
        axios.delete(`post/${this.post.postNumber}`).then(() => {
          alert("삭제되었습니다.");
          this.$router.push("/community/list");
        });
      }
    },
    onModifyComment(comment) {
      this.modifyComment = comment;
      this.isModifyShow = true;
    },
    onModifyCommentCancel(isShow) {
      this.isModifyShow = isShow;
    },
  },
};
</script>

<style></style>
