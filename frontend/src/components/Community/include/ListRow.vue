<template>
  <div>
    <div id="post-head">
      <span><div class="category">{{ post.postType }}</div></span>
      <span>{{post.title}}</span>
      <span>{{post.userNickname}} | {{post.date}}</span>
    </div>
    <div id="post-body">
        <span v-text="splitContent(post.content)"></span>
        <span id="more" @click="movePage">...더보기</span>
    </div>
    <div id="post-foot">
        <span>{{post.view}}</span>
        <span>{{post.recommend}}</span>
        <span>{{}}</span>
    </div>
    <hr>
  </div>
</template>

<script>
export default {
  name: "listrow",
  data() {
    return {
      type: null,
    };
  },
  props: {
    post: Object,
  },
  created() {
  },
  methods: {
    movePage: function () {
      this.$router.push(`/community/detail/${this.post.postNumber}`);
    },
    splitContent(content) {
        return content.substring(0, 51);
    }
  },
};
</script>

<style></style>
