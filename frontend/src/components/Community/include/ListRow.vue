<template>
  <div>
    <div id="post-head">
      <span><div class="category">{{ this.type }}</div></span>
      <span>{{post.title}}</span>
      <span>{{post.user_number}} | {{post.date}}</span>
    </div>
    <div id="post-body">
        <span v-text="spliteContent(post.content)"></span>
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
    switch (this.post.post_type) {
      case 1:
        this.type = "자유";
        break;
      case 2:
          this.type = "정보";
        break;
      case 3:
          this.type = "질문";
        break;
      case 4:
          this.type = "나눔";
        break;
    }
  },
  methods: {
    movePage: function () {
      this.$router.push(`/community/detail/${this.post.post_number}`);
    },
    splitContent(content) {
        var short_content = content.substring(0, 51);
        short_content += "...";
        return short_content;
    }
  },
};
</script>

<style></style>
