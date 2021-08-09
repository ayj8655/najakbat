<template>
  <div class="row">
    <div class="row-2">
      <img
        src="https://upload.wikimedia.org/wikipedia/commons/thumb/1/1f/Curved_Arrow.svg/2290px-Curved_Arrow.svg.png"
        width="25px"
      />
    </div>
    <div class="col-10">
      <div v-if="this.modifyComment != null" class="regist_form">
        <textarea
          id="content"
          name="content"
          v-model="modifyComment.content"
          cols="35"
          rows="2"
        ></textarea>
        <button class="small" @click="updateCommentCancel">취소</button>
        <button class="small" @click="updateComment">수정</button>
      </div>
      <div v-else class="regist_form">
        <textarea
          id="content"
          name="content"
          v-model="content"
          placeholder="답글 내용을 작성하세요."
        ></textarea>
        <button type="button" class="btn btn-success" @click="registComment">
          등록
        </button>
      </div>
    </div>
  </div>
</template>

<script>
// import http from "@/util/http-common";
export default {
  name: "CommentChildWrite",
  props: {
    modifyCommentChild: { type: Object },
  },
  data() {
    return {
      // no: this.$route.params.no,
      // id: "",
      // content: "",
    };
  },
  methods: {},
};
</script>

<style></style>
