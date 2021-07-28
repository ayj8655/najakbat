<template>
  <div class="container">
    <div style="text-align: center">
      <h4 class="community">커뮤니티</h4>
    </div>
    <div class="menu">
      <div class="category">
        <span class="all">전체</span>
        <span class="free">자유</span>
        <span class="info">정보</span>
        <span class="question">질문</span>
        <span class="share">나눔</span>
      </div>
      <div class="etc">
        <span class="search">검색</span>
        <span class="write" @click="postWrite">글쓰기</span>
      </div>
    </div>
    <hr class="line1" />
    <div></div>
  </div>
</template>

<script>
export default {
  methods: {
    postWrite() {
      this.$router.push("/community/write");
    },
  },
};
</script>

<style></style>
