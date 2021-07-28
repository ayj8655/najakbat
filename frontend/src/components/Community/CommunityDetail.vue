<template>
  <div class="container">
    <div id="post-area">
      <div id="post-head">
        <div id="category"></div>
        <div>{{ post.title }}</div>
        <div>
          <span>{{ post.user_number }}</span>
          <span>{{ post.view }} | {{ post.regtime }}</span>
        </div>
        <hr />
      </div>
      <div id="post-body">
        <div v-html="enterToBr(article.content)"></div>
      </div>
      <div id="post-foot">
        <span>{{ post.recommend }}</span>
        <span>{{ this.comments.length }}</span>
        <div v-if="this.$store.user.user_number == this.post.user_number">
          <span>
            <div class="modifyBtn" @click="modifyPost">수정</div>
            <div class="deleteBtn" @click="deletePost">삭제</div>
          </span>
        </div>
      </div>
    </div>
    <div id="comments-area">
      <hr />
      <comment
        v-for="(comment, index) in comments"
        :key="index"
        :v-if="!comment.parent"
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
//   import http from "@/util/http-common";
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
    // http.get(`/post/${this.no}`).then(({ data }) => {
    //   this.post = data;
    // });
    //   http.get(`/comment/${this.no}`).then(({ data }) => {
    //     this.comments = data;
    //   });
  },
  methods: {
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
    modifyPost() {
      this.$router.push(`/community/modify/${this.post.post_number}`);
    },
    deletePost() {
        if (confirm("정말로 삭제하시겠습니까?")) {
          // http.delete(`/post/${this.post.post_number}`).then(() => {
          //   alert("삭제되었습니다.");
          //   this.$router.push("/community/list");
          // });
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
