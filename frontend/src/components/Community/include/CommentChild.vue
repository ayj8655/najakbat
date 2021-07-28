<template>
  <div class="row">
    <div class="col-2">
      <img
        src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Curved_Arrow.svg/2290px-Curved_Arrow.svg.png"
        width="25px"
      />
    </div>
    <div class="col-10">
      <div class="head">
        <span>{{ comment.user_number }}</span>
        <span><img id="messageBtn" /></span>
        <span>{{ comment.regtime }}</span>
      </div>
      <div
        v-if="comment.isdeleted"
        class="deleted"
        v-html="`삭제된 댓글입니다.`"
      ></div>
      <div v-else class="body" v-html="enterToBr(comment.content)"></div>
      <div class="foot">
        <div>
          <span>{{ comment.recommend }}</span>
          <span></span>
        </div>
        <div class="btn" v-if="comment.id == this.$store.state.userinfo.id">
          <label @click="modifyCommentView">수정</label> |
          <label @click="deleteComment">삭제</label>
        </div>
        <div class="btn" v-else>
          <label @click="recommendComment">좋아요</label>
          <!-- | <label @click="writeComment">답글</label> -->
        </div>
      </div>
    </div>
    <hr>
  </div>
</template>

<script>
export default {};
</script>

<style></style>
