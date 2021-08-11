<template>
  <div class="bg">
    <div align="left"><img src="@/assets/modal_back.png" width="25px" @click="moveBack"></div>
  </div>
</template>

<script>
export default {
  data() {
    return {
    }
  },
  methods: {
    moveBack() {
      this.$router.push("/mycrop");
    }
  },
}
</script>

<style>
.bg {
  min-height: 812px;
  background-color: #446631;
}
.bg > * {
  font-family: Noto Sans KR;
  font-style: normal;
}
</style>